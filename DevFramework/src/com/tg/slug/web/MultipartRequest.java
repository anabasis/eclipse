package com.tg.slug.web;
/**
 * @(#) MultipartRequest.java
 * @version KDSKIT
 * Copyright
 * All rights reserved.
 * �ۼ� :
 * @author �赿��, dongskim@solupia.co.kr
 *         SOLUPIA e-Biz Team
 *
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import com.tg.slug.logging.Logging;
import com.tg.slug.util.cmnUtil;



/**
 * Client���α׷����� ��û�ϴ� request ������ �����Ͽ� �����ش�.<br>
 * HttpServletRequest���� �����ϴ� �⺻����� getParameter���� <br>
 * �Ϲ����� form request�� ���Ͽ� �����ϰ�,<br>
 * file upload�� ���� ������ �۾��� �����Ѵ�.<br>
 * �ϳ��� form request���� FormEntry�� ���Ͽ� Vector�� ���Ѵ���<br>
 * ��� Vector���� �ڷḦ �����Ͽ� �����ش�.<br>
 * �̷� �⺻���� �ϵ��� Constructor���� ��� �̷�����Ƿ� ����ڴ�<br>
 * �� Class�� Instance�� ���� �Ŀ� �Ϲ� HttpServletRequest�� ����ϴ°�<br>
 * ó�� ���ϰ� ����ϸ� �ȴ�.<br>
 * �� Constructor������ request�� InputStream���� �ް�, ContentType�� �����Ͽ�<br>
 * MultipartInputStream�� ���ϰ� �װ��� parse���� FormEntry��ü�� ���Ͽ�<br>
 * Vector�� ���Ѵ�.<br>
 * �� ���Ŀ� ȣ��Ǵ� Method���� ��κ� �� Vector�� ó���Ͽ� �����Ѵ�.<br>
 *
 * @see FormEntry
 */
public class MultipartRequest {

    /**
     * HttpServletRequest���� ��޹��� request�� ���� ���ؼ� ����
     */
    private HttpServletRequest req;

    /**
     * MultipartInputStream�� ���� Instance�� �������ؼ� ����
     */
    private MultipartInputStream in;

    /**
     * Client�� ����� form request�� ���� FormEntry�� ���� Vector�� ��
     */
    private Vector parameters = new Vector();

    /**
     * File Upload�� ȭ���� �����ϱ����� Server�� Directory�� ����
     */
    private String targetDir = ".";
/**
 * �����۾� ���丮�� Upload�� ȭ���� �����ϰ�,
 * Request�� ó���ϱ� ���� �۾��� �����Ѵ�.
 * ������ Server�� Directory�� �����ϰ� request�� Input stream�� parsing����
 * ��û������ Vector�� �����Ͽ� ����� �� �ֵ��� �Ѵ�.
 *
 * @param req Client�� ���� ��޹��� HttpServletRequest�� form request
 */
public MultipartRequest( HttpServletRequest req )  throws IOException {
 
    this( req, "." );
    
}
/**
 * �����۾� ���丮�� Upload�� ȭ���� �����ϰ�
 * Request�� ó���ϱ� ���� �۾��� �����Ѵ�.
 * ������ Server�� Directory�� �����ϰ� request�� Input stream�� parsing����
 * ��û������ Vector�� �����Ͽ� ����� �� �ֵ��� �Ѵ�.
 *
 * @param req Client�� ���� ��޹��� HttpServletRequest�� form request
 * @param dir Upload�� file�� ������ Server�� Directory��
 */
public MultipartRequest( HttpServletRequest req, String dir ) throws IOException{                     
    this.req = req;

    if ( dir.equals("") ) {
        targetDir = ".";
    } else {

        targetDir = dir;           
    }
    //req.setCharacterEncoding("EUC-KR");

    in = new MultipartInputStream( req.getInputStream(), req.getContentType() );
    parseInputStream();
}


public String getTargetDir()
{
 return this.targetDir;   
}
/**
 * Mime�� Header String�� ���ڿ��� content-type�� �̾Ƴ���.
 *
 * @return java.lang.String
 * @param line request�� Mime header��ü���ڿ�
 * @exception java.io.IOException String�� ���ų� ������ header����� �ƴҶ� �߻�
 */
private String extractContentType(String line) throws IOException {
    String contentType = null;

    // Convert the line to a lowercase string
    String origline = line;
    line = origline.toLowerCase();

    // Get the content type, if any
    if (line.startsWith("content-type")) {
        int start = line.indexOf(" ");
        if (start == -1) {
            throw new IOException("Content type corrupt : " + origline);
        }
        contentType = line.substring(start + 1);
    }
    else if (line.length() != 0) {  // no content type, so should be empty
        throw new IOException("Malformed line after disposition : " + origline);
    }

    return contentType;
}
/**
 * Content-Disposition��  ������ String Array�� return�Ѵ�.<br>
 * Return�ϴ� ������ form-data�� name, filename���� �ִ�.<br>
 * return�ϴ� String Array�� ���� ������ ���� ���� �����.<br>
 *  retval[0] = disposition<br>
 *  retval[1] = name<br>
 *  retval[2] = filename(only file name)<br>
 *  retval[3] = filename(full path)<br>
 * <br>
 * ���) body of the request<br>
 * -----------------------------17813689118176<br>
 * Content-Disposition: form-data; name="text1"<br>
 * <br>
 * testing<br>
 * -----------------------------17813689118176<br>
 * Content-Disposition: form-data; name="file1"; filename="C:\webApp\test.txt"<br>
 * <br>
 * Hello world!<br>
 * -----------------------------17813689118176--<br>
 * <br>
 * @return Content-Disposition�� ������ ���� String Array
 * @param line readLine()�� ���Ͽ� �о���� Line
 * @exception java.io.IOException
 */
private String[] extractDispositionInfo(String line) throws IOException {

    String[] retval = new String[4];

    // Convert the line to a lowercase string without the ending \r\n
    // Keep the original line for error messages and for variable names.
    String origline = line;
    line = origline.toLowerCase();

    // Get the content disposition, should be "form-data"
    int start = line.indexOf("content-disposition: ");
    int end = line.indexOf(";");
    if (start == -1 || end == -1) {
        throw new IOException("Content disposition corrupt: " + origline);
    }
    String disposition = line.substring(start + 21, end);
    if (!disposition.equals("form-data")) {
        throw new IOException("Invalid content disposition: " + disposition);
    }

    // Get the field name
    start = line.indexOf("name=\"", end);  // start at last semicolon
    end = line.indexOf("\"", start + 7);   // skip name=\"
    if (start == -1 || end == -1) {
        throw new IOException("Content disposition corrupt: " + origline);
    }
    String name = origline.substring(start + 6, end);

    // Get the filename, if given
    String filename = null;
    String inputfilename = null;
    start = line.indexOf("filename=\"", end + 2);  // start after name
    end = line.indexOf("\"", start + 10);          // skip filename=\"
    if (start != -1 && end != -1) {                // note the !=
        inputfilename = origline.substring(start + 10, end);
        // The filename may contain a full path.  Cut to just the filename.
        int slash =
          Math.max(inputfilename.lastIndexOf('/'),
        inputfilename.lastIndexOf('\\'));
        if (slash > -1) {
            filename = inputfilename.substring(slash + 1);  // past last slash
        }
        if (inputfilename.equals("")) filename = null; // sanity check
    }

    // Return a String array: disposition, name, filename
    retval[0] = disposition;
    retval[1] = name;
    retval[2] = inputfilename;//filename;
    retval[3] = inputfilename;
    return retval;
}
/**
 * ��޹��� form data�� FormEntry�� Array�� Return�Ѵ�.
 * ���� parameter�� �������� parameter�� �ԷµǾ��� ��
 * name�� ��ġ�� ��� FormEntry�� Array�� return�Ѵ�.
 *
 * @param name ã���� �ϴ� parameter�� name
 * @return �Է¹��� name�� FormEntry Array�� return
 */
public FormEntry[] getLFormEntries(String  name) {
    Vector v = new Vector();

    for ( Enumeration e = parameters.elements(); e.hasMoreElements() ; ) {
        FormEntry lformEntry = (FormEntry)e.nextElement();
        if ( name.equals( lformEntry.getName() ) ) {
            v.add( lformEntry );
        }
    }

    FormEntry[] lf = new FormEntry[ v.size() ];
    System.arraycopy( v.toArray(), 0, lf, 0, lf.length );
    return lf;
}
/**
 * ��޹��� form data�� FormEntry��  Return�Ѵ�.
 * ���� parameter�� �������� parameter�� �ԷµǾ��� ��
 * ù��° name�� ��ġ�� FormEntry�� return�Ѵ�.
 *
 * @param name ã���� �ϴ� parameter�� name
 * @return �Է¹��� name�� FormEntry�� return
 */
public FormEntry getFormEntry(String  name) {
    FormEntry lformEntry = null;
    FormEntry rtnValue = null;

    for ( Enumeration e = parameters.elements(); e.hasMoreElements() ; ) {
        lformEntry = (FormEntry)e.nextElement();
        if ( name.equals( lformEntry.getName() ) ) {
            rtnValue = lformEntry;
            break;
        }
    }

    return rtnValue;
}
/**
 * ��޹��� form data�� value ���� String���� Return�Ѵ�.
 * ���� parameter�� �������� parameter�� �ԷµǾ��� ��
 * ù��° name�� ��ġ�� parameter value�� return�Ѵ�.
 *
 * @param name ã���� �ϴ� parameter�� name
 * @return �Է¹��� name�� value�� String Data Type���� return
 */
public String getParameter(String  name) {
    String value = null;

    for ( Enumeration e = parameters.elements(); e.hasMoreElements() ; ) {
        FormEntry lformEntry = (FormEntry)e.nextElement();

        if ( name.equals( lformEntry.getName() ) ) {
            value = lformEntry.getValue();
            break;
        }
    }

    return value;
}
/**
 * ��޹��� form data�� name�� Enumeration Data Type���� Return�Ѵ�.
 * FormEntry �� Add��Ų Vector���߿��� name������
 * Vector�� ������ Enumeration���� return�Ѵ�.
 *
 * @return form data�� name�� Enumeration Data Type�� return
 */
public Enumeration getParameterNames() {
    Vector names = new Vector();

    for ( Enumeration e = parameters.elements(); e.hasMoreElements() ; ) {
        FormEntry lformEntry = (FormEntry)e.nextElement();
        names.add( lformEntry.getName() );
    }

    return names.elements();
}
/**
 * FormEntry �� Add��Ų Vector�� return�Ѵ�.
 *
 * @return FormEntry �� Add��Ų Vector�� return
 */
public Vector getParameters() {
    return parameters;
}
/**
 * ��޹��� form data�� value ���� String���� Return�Ѵ�.
 * ���� parameter�� �������� parameter�� �ԷµǾ��� ��
 * ��ġ�ϴ� ��� parameter value�� String Array�� return�Ѵ�.
 *
 * @param name ã���� �ϴ� parameter�� name
 * @return �Է¹��� name�� ��� value�� String Array �� return
 */
public String[] getParameterValues(String name) {
    Vector v = new Vector();

    for ( Enumeration e = parameters.elements(); e.hasMoreElements() ; ) {
        FormEntry lformEntry = (FormEntry)e.nextElement();
        if ( name.equals( lformEntry.getName() ) ) {
            v.add( lformEntry.getValue() );
        }
    }

    String[] s = new String[ v.size() ];
    System.arraycopy( v.toArray(), 0, s, 0, s.length );
    return s;
}
/**
 * Client�� ���� ��޹��� InputStream�� FormEntry Instance�� <br>
 * ���Ѵ�. ��� Instance�� Vector�� ��ȴ�. <br>
 * �� �������� text���� �Ϲ� form data�� Vector�θ� ������� <br>
 * file upload�� Constructor���� ������ Server�� Directory�� <br>
 * ����� ȭ�ϰ� ���� �̸����� ����ȴ�.<br>
 * �̶� ȭ���̸��� �ߺ��Ǹ� ���ļ� ���� �ǹǷ� �����ؾ� �Ѵ�.<br>
 * MultipartInputStream.readFormData()�� �Է� Stream�� ������ <br>
 * Line ������ �ݺ��Ͽ� �д´�.<br>
 * �ݺ��� ����Ǵ� ���� �Է� Stream�� Type�� ��� �ٸ� ������ �Ѵ�.<br>
 * Type�� ��ȭ�� MultipartInputStream.readFormData()���� �Ͼ��.<br>
 * <br>
 * T_MIME_HEADER Type�϶��� LFormEntity ��ü�� ���ϰ�, <br>
 * content type, name���� header������ ���ͼ� <br>
 * FormEntry Instance�� �����Ѵ�.<br>
 * T_FORM_OCTET_DATA type�϶��� File upload�� �ƴѰ�� <br>
 * name�� �ش�Ǵ� value�� �����ϰ�,<br>
 * File upload�� ��� ȭ���� �����ϰ� ������ �����, ȭ��ũ�⸦<br>
 * FormEntry Instance�� �����Ѵ�.<br>
 * T_DELIMITER, T_COLSE_DELIMITER�� ��� ���� FormEntry Instance��<br>
 * Vector�� Add ��Ų��.<br>
 * <br>
 * @exception java.io.IOException
 */
private void parseInputStream() throws IOException{

    byte[] buf = new byte[16*1024];
    //byte[] buf = new byte[20];
    int result = 0;
    FormEntry lformEntry = null;

    File savedFile = null;
    FileOutputStream fos = null;
    //BufferedOutputStream bfos = null;// new BufferedOutputStream(fos, 8 * 1024); // 8K
    long fileLength =0L;

    while ( (result = in.readFormData( buf )) != -1 ) {

        switch( in.getReadDataType() ) {
            case MultipartInputStream.T_DASH_BOUNDARY:
                break;

            case MultipartInputStream.T_MIME_HEADER:
                Logging.dev.println("< "+this.getClass().getName()+" > < parseInputStream > T_MIME_HEADER Step:"+MultipartInputStream.T_MIME_HEADER);
                if ( lformEntry == null ) lformEntry = new FormEntry();
                String mimeHeader =
                    new String( buf, 0, result-2 ,"UTF-8" );
                String testHeader = mimeHeader.toLowerCase(); //form�� name�� lowerCase�� ����Ǵ� ���� �������� ���� by ���ñ�
                Logging.dev.println("< "+this.getClass().getName()+" > < parseInputStream > T_MIME_HEADER Step, Mime Header: "+mimeHeader);

                if ( testHeader.startsWith( "content-disposition" ) ) {
                    String[] dispositionArr = extractDispositionInfo( mimeHeader );  
                    
                    for(int i=0;i<dispositionArr.length;i++){
                    	Logging.dev.println("######################## < parseInputStream > dispositionArr["+i+"]"+dispositionArr[i]);
                    	
                    }
                    lformEntry.setName( dispositionArr[1] );
                    lformEntry.setFileName( dispositionArr[2] );
                    lformEntry.setValue( dispositionArr[3] );
                } else if ( testHeader.startsWith( "content-type" ) ) {
                    lformEntry.setContentType(extractContentType( mimeHeader ));
                    Logging.dev.println("< "+this.getClass().getName()+" > < parseInputStream > T_MIME_HEADER Step, Header Is start with contet-type");
                }
                break;

            case MultipartInputStream.T_MIME_HEADER_END:
                break;

            case MultipartInputStream.T_FORM_OCTET_DATA:
            
                String fileName = lformEntry.getFileName();   
                Logging.dev.println("< "+this.getClass().getName()+" > < parseInputStream >File Name:"+fileName);
                if ( fileName == null ) {
                      
                      lformEntry.appendValue( new String( buf, 0, result ,"UTF-8") ); 
                } else {
                    if ( savedFile == null ) {
                        // ���ϸ� �տ� PREFIX�� �����Ѵ�...........
                        cmnUtil cmnutil = new cmnUtil();
                        
                        String prefixname = cmnutil.getCurrentTimeNoDash(); 
                                                            
                        
                        fileName =prefixname + "_" + fileName;  
                                                              
                        savedFile = new File(targetDir + File.separator+ fileName);
                        //bfos = new BufferedOutputStream( new FileOutputStream( savedFile ),160 * 1024);
                        fos = new FileOutputStream( savedFile );

                        lformEntry.setSavedFileName( savedFile.getAbsolutePath() );                         
                        //2007.03.15 FilePath�� �и��ϱ� ���� ���� BY JINNY
                        //lformEntry.setSavedFileName( savedFile.getParent()); 
                    }
                    fos.write( buf, 0, result );
                    //bfos.write( buf, 0, result );
                    lformEntry.incFileLength( result );
                    Logging.dev.println("< "+this.getClass().getName()+" > < parseInputStream > Saved File Name:"+savedFile);
                    Logging.dev.println( "File Writte: "+result );
                }
                break;
            case MultipartInputStream.T_DELIMITER:
            case MultipartInputStream.T_COLSE_DELIMITER:
                parameters.add( lformEntry );
                /*
                if ( lformEntry.getFileLength() == 0 &&
                     savedFile != null ) savedFile.delete();
                */
                lformEntry = null;
                if ( fos != null ) fos.close();
                    fos = null;
                //if ( bfos != null ) bfos.close();
                //bfos = null;
                savedFile = null;
                fileLength = 0L;
                break;
        } //switch
    } // while
}
}
