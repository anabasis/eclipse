package com.slug.web;
/**
 * @(#) MultipartRequest.java
 * @version
 * Copyright
 * All rights reserved.
 * 작성 :
 * @author
 *         
 *
 */

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

import javax.servlet.http.HttpServletRequest;

import com.slug.logging.Logging;
import com.slug.util.FileUtil;
import com.slug.util.OSUtil;
import com.slug.util.cmnUtil;



/**
 * Client프로그램에서 요청하는 request 정보를 가공하여 보여준다.<br>
 * HttpServletRequest에서 제공하는 기본기능인 getParameter등을 <br>
 * 일반적인 form request를 위하여 제공하고,<br>
 * file upload를 위한 별도의 작업을 수행한다.<br>
 * 하나의 form request마다 FormEntry를 생성하여 Vector로 관리한다음<br>
 * 생성된 Vector에서 자료를 추출하여 보여준다.<br>
 * 이런 기본적인 일들이 Constructor에서 모두 이루어지므로 사용자는<br>
 * 이 Class의 Instance를 생성한 후에 일반 HttpServletRequest를 사용하는것<br>
 * 처럼 편하게 사용하면 된다.<br>
 * 이 Constructor에서는 request를 InputStream으로 받고, ContentType을 추출하여<br>
 * MultipartInputStream을 생성하고 그것을 parse시켜 FormEntry객체를 생성하여<br>
 * Vector로 관리한다.<br>
 * 이 이후에 호출되는 Method들은 대부분 이 Vector를 처리하여 동작한다.<br>
 *
 * @see FormEntry
 */
public class ExtMultipartRequest {

    /**
     * HttpServletRequest에서 전달받은 request를 쓰기 위해서 정의 
     */
    private HttpServletRequest req;

    /**
     * MultipartInputStream를 생성한 Instance를 쓰기위해서 정의
     */
    private MultipartInputStream in;

    /**
     * Client가 전달한 form request로 생성한 FormEntry의 값을 Vector로 관리
     */
    private Vector parameters = new Vector();

    /**
     * File Upload시 화일을 저장하기위한 Server의 Directory를 지정
     */
    private String targetDir = ".";
    private String middleDir = ".";
    private String timeDir = ".";
    
    private String sys_file_name = "";
    
    public String getSysFileName(){
    	return this.sys_file_name;
    }
/**
 * 현재작업 디렉토리에 Upload한 화일을 저장하고,
 * Request를 처리하기 위한 작업을 수행한다.
 * 저장할 Server의 Directory를 결정하고 request의 Input stream을 parsing시켜
 * 요청값들을 Vector에 저장하여 사용할 수 있도록 한다.
 *
 * @param req Client로 부터 전달받은 HttpServletRequest의 form request
 */
public ExtMultipartRequest( HttpServletRequest req )  throws IOException {
 
    this( req, "." );
    
}
/**
 * 현재작업 디렉토리에 Upload한 화일을 저장하고
 * Request를 처리하기 위한 작업을 수행한다.
 * 저장할 Server의 Directory를 결정하고 request의 Input stream을 parsing시켜
 * 요청값들을 Vector에 저장하여 사용할 수 있도록 한다.
 *
 * @param req Client로 부터 전달받은 HttpServletRequest의 form request
 * @param dir Upload한 file을 저장할 Server의 Directory명
 */
public ExtMultipartRequest( HttpServletRequest req, String dir ) throws IOException{                     
    this.req = req;
    cmnUtil cmnUtil = new cmnUtil();

    if ( dir.equals("") ) {
        targetDir = ".";
    } else {
        targetDir = dir;
        timeDir = cmnUtil.getCurrYear()+cmnUtil.getCurrMonth();
    }
    in = new MultipartInputStream( req.getInputStream(), req.getContentType() );
    parseInputStream();
}


public String getTargetDir()
{
	return this.targetDir;   
}
/**
 * Mime의 Header String를 문자열중 content-type을 뽑아낸다.
 *
 * @return java.lang.String
 * @param line request의 Mime header전체문자열
 * @exception java.io.IOException String이 비었거나 정해진 header양식이 아닐때 발생
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
 * Content-Disposition의  정보를 String Array로 return한다.<br>
 * Return하는 정보는 form-data의 name, filename등이 있다.<br>
 * return하는 String Array는 순서대로 다음과 같은 값을 가진다.<br>
 *  retval[0] = disposition<br>
 *  retval[1] = name<br>
 *  retval[2] = filename(only file name)<br>
 *  retval[3] = filename(full path)<br>
 * <br>
 * 참고) body of the request<br>
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
 * @return Content-Disposition의 정보를 넣은 String Array
 * @param line readLine()을 통하여 읽어들인 Line
 * @exception java.io.IOException
 */
private String[] extractDispositionInfo(String line) throws IOException {
	
    //Loggingg.dev.println("< "+this.getClass().getName()+" > < extractDispositionInfo > "+line);

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
 * 전달받은 form data의 FormEntry의 Array를 Return한다.
 * 같은 parameter로 여러개의 parameter가 입력되었을 때
 * name과 일치한 모든 FormEntry를 Array로 return한다.
 *
 * @param name 찾고자 하는 parameter의 name
 * @return 입력받은 name의 FormEntry Array로 return
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
 * 전달받은 form data의 FormEntry를  Return한다.
 * 같은 parameter로 여러개의 parameter가 입력되었을 때
 * 첫번째 name과 일치한 FormEntry를 return한다.
 *
 * @param name 찾고자 하는 parameter의 name
 * @return 입력받은 name의 FormEntry를 return
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
 * 전달받은 form data의 value 으로 String으로 Return한다.
 * 같은 parameter로 여러개의 parameter가 입력되었을 때
 * 첫번째 name과 일치한 parameter value를 return한다.
 *
 * @param name 찾고자 하는 parameter의 name
 * @return 입력받은 name의 value를 String Data Type으로 return
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
 * 전달받은 form data의 name을 Enumeration Data Type으로 Return한다.
 * FormEntry 를 Add시킨 Vector값중에서 name만으로
 * Vector를 생성한후 Enumeration으로 return한다.
 *
 * @return form data의 name을 Enumeration Data Type을 return
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
 * FormEntry 를 Add시킨 Vector를 return한다.
 *
 * @return FormEntry 를 Add시킨 Vector를 return
 */
public Vector getParameters() {
    return parameters;
}
/**
 * 전달받은 form data의 value 으로 String으로 Return한다.
 * 같은 parameter로 여러개의 parameter가 입력되었을 때
 * 일치하는 모든 parameter value를 String Array로 return한다.
 *
 * @param name 찾고자 하는 parameter의 name
 * @return 입력받은 name의 모든 value를 String Array 로 return
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
 * Client로 부터 전달받은 InputStream을 FormEntry Instance로 <br>
 * 생성한다. 생성된 Instance는 Vector로 관리된다. <br>
 * 이 과정에서 text등의 일반 form data는 Vector로만 관리되지만 <br>
 * file upload는 Constructor에서 설정한 Server쪽 Directory에 <br>
 * 전송한 화일과 같은 이름으로 저장된다.<br>
 * 이때 화일이름이 중복되면 겹쳐서 쓰게 되므로 주의해야 한다.<br>
 * MultipartInputStream.readFormData()로 입력 Stream의 끝까지 <br>
 * Line 단위로 반복하여 읽는다.<br>
 * 반복이 진행되는 동안 입력 Stream의 Type에 따라서 다른 동작을 한다.<br>
 * Type의 변화는 MultipartInputStream.readFormData()에서 일어난다.<br>
 * <br>
 * T_MIME_HEADER Type일때는 LFormEntity 객체를 생성하고, <br>
 * content type, name등의 header정보를 얻어와서 <br>
 * FormEntry Instance에 설정한다.<br>
 * T_FORM_OCTET_DATA type일때는 File upload가 아닌경우 <br>
 * name에 해당되는 value를 설정하고,<br>
 * File upload일 경우 화일을 저장하고 저장한 절대경로, 화일크기를<br>
 * FormEntry Instance에 설정한다.<br>
 * T_DELIMITER, T_COLSE_DELIMITER인 경우 생성한 FormEntry Instance를<br>
 * Vector에 Add 시킨다.<br>
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
            	//Logging.dev.println("< "+this.getClass().getName()+" > < parseInputStream > T_MIME_HEADER Step:"+MultipartInputStream.T_MIME_HEADER);
                if ( lformEntry == null ) lformEntry = new FormEntry();
                //String mimeHeader = new String( buf, 0, result-2 ,"EUC-KR" );
                String mimeHeader = new String( buf, 0, result-2 ,"UTF-8" );
                String testHeader = mimeHeader.toLowerCase(); //form의 name이 lowerCase로 변경되는 것을 막기위해 수정 by 신택규
                //Logging.dev.println("< "+this.getClass().getName()+" > < parseInputStream > T_MIME_HEADER Step, Mime Header(mimeHeader): "+mimeHeader);
                //Logging.dev.println("< "+this.getClass().getName()+" > < parseInputStream > T_MIME_HEADER Step, Mime Header(testHeader): "+testHeader);

                if ( testHeader.startsWith( "content-disposition" ) ) {
                    String[] dispositionArr = extractDispositionInfo( mimeHeader );  
                    
                    //for(int i=0;i<dispositionArr.length;i++){
                    	//Logging.dev.println("######################## < parseInputStream > dispositionArr["+i+"]"+dispositionArr[i]);
                    	
                    //}
                    lformEntry.setName( dispositionArr[1] );
                    lformEntry.setFileName( dispositionArr[2] );  
                    lformEntry.setValue( dispositionArr[3] );
                } else if ( testHeader.startsWith( "content-type" ) ) {
                    lformEntry.setContentType(extractContentType( mimeHeader ));
                    //Loggingg.dev.println("< "+this.getClass().getName()+" > < parseInputStream > T_MIME_HEADER Step, Header Is start with contet-type");
                }
                break;

            case MultipartInputStream.T_MIME_HEADER_END:
                break;

            case MultipartInputStream.T_FORM_OCTET_DATA:
            
                String fileName = lformEntry.getFileName();   
                //Loggingg.dev.println("< "+this.getClass().getName()+" > < parseInputStream >File Name:"+fileName);
                if ( fileName == null ) {
                      lformEntry.appendValue( new String( buf, 0, result ,"UTF-8") ); 
                } else {
                    if ( savedFile == null ) {
                        // 파일명 앞에 PREFIX를 지정한다...........
                        cmnUtil cmnutil = new cmnUtil();
                        middleDir = getParameter("p_middle_dir");
                        
                        String prefixname = cmnutil.getCurrentTimeNoDash(); 
                                                            
                        fileName =prefixname + "_" + fileName;  
                    	//Loggingg.dev.println("######################## < MultipartInputStream > MultipartInputStream File:"+targetDir + File.separator+ fileName);
                        targetDir = targetDir+middleDir+OSUtil.getOsDelemeter()+timeDir;

            			FileUtil fu = new FileUtil();
            			fu.createDirectoryIfNeeded(targetDir);
            			
                 	
            			// sys_file_name
            			this.sys_file_name = fileName;

            			// file_path
                        lformEntry.setName("file_path");
                        lformEntry.setValue(targetDir);                    	
                        
                        savedFile = new File(targetDir + File.separator+ fileName);
                        fos = new FileOutputStream( savedFile );

                        lformEntry.setSavedFileName( savedFile.getAbsolutePath() );                         
                    }
                    fos.write( buf, 0, result );
                    lformEntry.incFileLength( result );
                    //Logging.debug.println("< "+this.getClass().getName()+" > < parseInputStream > Saved File Name:"+savedFile);
                    //Loggingg.dev.println( "File Writte: "+result );
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
