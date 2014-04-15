package com.slug.web;

/**
 * @(#) MultipartInputStream.java
 * @version KDSKIT
 * Copyright
 * All rights reserved.
 * �ۼ� :
 * @author �赿��, dongskim@solupia.co.kr
 *         SOLUPIA e-Biz Team
 *
 */

import java.io.*;

import com.slug.logging.Logging;

/**
 * ServletInputStream���� ���� �ǹ��ִ� ����(Line)�� ������ �д� ����� �����Ѵ�.<br>
 * unread()�� ����ϱ� ���Ͽ� PushbackInputStream �� ��ӹ޾Ҵ�.<br>
 * unread()�� ���Ǵ� method�� readLine(), readFormData()�̴�.<br>
 * ���������� ���¸� ���Ͽ� Buffer�� �о���� ���� type�� <br>
 * �� �� �ְ� �����Ͽ���.<br>
 * ���´� �Է� Stream�� ������ ��� 6���� final�� integer�� ��ȴ�.<br>
 * ���¿ʹ� ������ ���� �۾��ϰ� �ִ� stream�� type�� ��ȴ�. <br>
 * ���´� ������ ����.<br>
 * S_PREAMBLE->S_MIME_HEADER->S_MIME_ENTRY->S_FORM_OCTET_DATA->S_DELIMITER->S_EPILOGUE<br>
 * S_ : ���� ���,  T_ : TYPE ���<br>
 * <br>
 * ���) body of the request <br>
 * -----------------------------17813689118176<br>
 * Content-Disposition: form-data; name="text1"<br>
 * <br>
 * testing<br>
 * -----------------------------17813689118176<br>
 * Content-Disposition: form-data; name="file1"; filename="C:\test\test.txt"<br>
 * <br>
 * Hello world!<br>
 * -----------------------------17813689118176--<br>
 */
public class MultipartInputStream extends PushbackInputStream  {
    /**
     * Type�� ��Ÿ���� final ����(���). ������ ���ڴ� �ǹ̾�� ������ ��
     * preamble data type
     */
    public static final int T_PREAMBLE               = 10 ;

    /**
     * '--' + boundary ���� ��Ÿ����.
     * ���ο� �Է� parameter�� ������ �˸���.
     */
    public static final int T_DASH_BOUNDARY          = 20 ;

    /**
     * content-type, content-disposition���� MIME-part-headers
     * MIME header type
     */
    public static final int T_MIME_HEADER            = 30 ;

    /**
     * MIME-part-headers �� ���� empty line (CRLF�� ����)
     * MIME header end type
     */
    public static final int T_MIME_HEADER_END        = 40 ;

    /**
     * �о���� Buffer�� ũ�Ⱑ �� Header�� ũ�⺸�� ���� ���
     * �� Header�� �Ϻκ��� �ǹ��ϴ� type.
     * (�� Header�� �������� Entry�� ��������)
     * MIME header entry type
     */
    public static final int T_MIME_HEADER_ENTRY      = 50 ;

    /**
     * MIME header Entry�� ���κ�
     * MIME header entry end type
     */
    public static final int T_MIME_HEADER_ENTRY_END  = 60 ;

    /**
     * Header�� �����ڿ� ���� Delimiter���� ���� DATA.
     * file�� ��� file ������ �ǰ� Text���� �Է�form�ΰ��
     * �Էµ� �ڷᰡ �ȴ�.
     * delimiter�� ���ö������� CRLF�� ���õȴ�.
     * form octet data type
     */
    public static final int T_FORM_OCTET_DATA        = 70 ;

    /**
     * CRLF+"--"+boundary
     * �� form data���� �������� delimiter type
     */
    public static final int T_DELIMITER              = 80 ;

    /**
     * CRLF+"--"+boundary+"--"
     * Form data�� body�� �������� �ǹ���
     */
    public static final int T_COLSE_DELIMITER        = 90 ;

    /**
     * Close Delimiter ���Ŀ� ���� ��� DATA
     * epilogue type
     */
    public static final int T_EPILOGUE               = 100 ;

    /**
     * Mime header�� ������ ������ DATA�� ó���ϴ����� �ǹ�
     * ���ۻ���
     */
    public static final int S_PREAMBLE               = 10 ;

    /**
     * Mime header DATA�� ó���ϴ����� �ǹ�
     */
    public static final int S_MIME_HEADER            = 20 ;

    /**
     * Mime header entry DATA�� ó���ϴ����� �ǹ�
     */
    public static final int S_MIME_HEADER_ENTRY      = 30 ;

    /**
     * �Է¹��� DATA�� ó���ϴ����� �ǹ�
     */
    public static final int S_FORM_OCTET_DATA        = 40 ;

    /**
     * Delimiter �� ó���ϴ����� �ǹ�
     */
    public static final int S_DELIMITER              = 50 ;

    /**
     * Delimiter������ ��� DATA�� ó���ϴ����� �ǹ�
     * ��ǻ��� �������
     */
    public static final int S_EPILOGUE               = 60 ;

    /**
     * ���ϴ� Type ���� ������ �ִ� ����
     * �ʱⰪ���� T_PREAMBLE���� �����.
     */
    private int readDataType    = T_PREAMBLE;

    /**
     * ���ϴ� ���� ���� ������ �ִ� ����
     * �ʱⰪ���� S_PREAMBLE���� �����.
     */
    private int state           = S_PREAMBLE;

    /**
     * boundary�� �� ���ڿ��� �����ϴ� ����.
     * delimiter�� "\r\m--boundary"�̹Ƿ� boundary���� 4byte�� ����.
     */
    private byte[] boundary;
/**
 * InputStream type �Ķ���͸� �޾Ƽ� PushbackInputStream�� Constructor�� <br>
 * ����Ѵ�. PushbackInputStream�� Stream������ Buffer�� ���Ͽ� <br>
 * �����ϰ� unread() method�� ����ϰ� ���ش�.<br>
 * PushbackInputStream�� FilterdInputStream�� ��ӹ����Ƿ� Buffer�� ����Ѵٴ�<br>
 * �������� ������ �Ϲ� InputStreamó�� ����� �� �ִ�.<br>
 * �� contentType���δ� boundary string�� �����Ͽ� ����� �� �ְ� �Ѵ�.<br>
 *<br>
 * @param in ����ڷ� ���� ��޹��� ����� InputStream Type<br>
 * @param contentType contentType���� ���� boundary�� �˾Ƴ��� ���Ͽ� ���<br>
 * @see PushbackInputStream
 */
public MultipartInputStream(InputStream in, String contentType ) {
    super( in, 80);
    boundary = contentType.substring( contentType.indexOf("boundary=")+9).getBytes();
    Logging.dev.println("< "+this.getClass().getName()+" > boundary size: "+boundary.length);
}
/**
 * Byte Array�� �Ǿ��ִ� ���ڿ��� ���� CRLF(\r\n)���� �������� Ȯ���Ͽ�
 * �� ���θ� true, false�� return�Ͽ� �ش�.
 *
 * @param b[] �Է¹��� ���ڿ�. byte array type.
 * @param off ���ڿ��� ����κк��� �������� �����Ͽ� �ִ� �κ�.
 * @param len �Է¹��� byte array�� ũ��
 * @return CRLF�� ������ ���ڿ����� true, false ���θ� return�Ѵ�.
 */
private boolean endsWithCRLF( byte[] b, int off, int len ) {
    return  b[off+len-2] == '\r' &&
        b[off+len-1] == '\n';
}
/**
 * readDataType�� return�Ͽ� �ش�.
 * read data type�� 'T_'�� �����ϴ� final�������� �ϳ��� ���̴�.
 *
 * @return readDataType
 */
public int getReadDataType()  {
    return readDataType;
}
/**
 * ���� �����Ǿ� �ִ� Type�� html������� return�Ѵ�.
 * �������� debugging������ ���Ǿ� ���.
 *
 * @return ���� �����Ǿ� �ִ� Type�� html������� return�Ѵ�.
 */
public String getReadDataTypeDesc()  {
    String s = null;
    switch ( getReadDataType() ) {
        case  T_PREAMBLE:
            s = new String( "T_PREAMBLE" );
            break;
        case  T_DASH_BOUNDARY:
            s = new String( "T_DASH_BOUNDARY" );
            break;
        case  T_MIME_HEADER:
            s = new String( "T_MIME_HEADER" );
            break;
        case  T_MIME_HEADER_END:
            s = new String( "T_MIME_HEADER_END" );
            break;
        case  T_MIME_HEADER_ENTRY:
            s = new String( "T_MIME_HEADER_ENTRY" );
            break;
        case  T_MIME_HEADER_ENTRY_END:
            s = new String( "T_MIME_HEADER_ENTRY_END" );
            break;
        case  T_FORM_OCTET_DATA:
            s = new String( "T_FORM_OCTET_DATA" );
            break;
        case  T_DELIMITER:
            s = new String( "T_DELIMITER" );
            break;
        case  T_COLSE_DELIMITER:
            s = new String( "T_COLSE_DELIMITER" );
            break;
        case  T_EPILOGUE:
            s = new String( "T_EPILOGUE" );
            break;
    }
    return  "<font color=blue>" + s + "</font>";
}
/**
 * state��  return�Ͽ� �ش�.
 * state�� 'S_'�� �����ϴ� final�������� �ϳ��� ���̴�.
 *
 * @return state
 */
public int getState() {
    return state;
}
/**
 * ���� �����Ǿ� �ִ� ���¸� html������� return�Ѵ�.
 * �������� debugging������ ���Ǿ� ���.
 *
 * @return ���� �����Ǿ� �ִ� ���¸� html������� return�Ѵ�.
 */
public String getStateDesc() {
    String s = null;
    switch ( getState() ) {
        case  S_PREAMBLE:
            s = new String( "S_PREAMBLE" );
            break;
        case  S_MIME_HEADER:
            s = new String( "S_MIME_HEADER" );
            break;
        case  S_MIME_HEADER_ENTRY:
            s = new String( "S_MIME_HEADER_ENTRY" );
            break;
        case  S_FORM_OCTET_DATA:
            s = new String( "S_FORM_OCTET_DATA" );
            break;
        case  S_DELIMITER:
            s = new String( "S_DELIMITER" );
            break;
        case  S_EPILOGUE:
            s = new String( "S_EPILOGUE" );
            break;
    }
    return " <font color=blue>" + s + "</font>";
}
/**
 * Byte Array�� ���ڿ��� String ���� �����Ͽ� html������� �����ش�.
 * '\r'��� 'CR'��, '\n'�� ��� 'LF'�� �����Ų��.
 * �������� debugging������ ���Ǿ� ���.
 *
 * @param buf Byte array type�� ���ڿ�
 * @return ����� String��
 */
public String HEX( byte[] buf ) {
    StringBuffer sbuf = new StringBuffer();

    for ( int i = 0 ; i < buf.length ; i++ ) {
        if ( buf[i] == '\n' ) {
            sbuf.append("<font color=red>LF</font>\n");
        } else {
            if ( buf[i] == '\r' ) {
                sbuf.append("<font color=red>CR</font>");
            } else {
                sbuf.append( Integer.toHexString( new Byte(buf[i]).intValue() ) );
            }
        }
    }
    return sbuf.toString();
}
/**
 * Byte Array�� ���ڿ��� String ���� �����Ͽ� html������� �����ش�.<br>
 * '\r'��� 'CR'��, '\n'�� ��� 'LF'�� �����Ų��.<br>
 * �ʿ��� ���̸�ŭ�� �����Ѵ�.<br>
 * �������� debugging������ ���Ǿ� ���.<br>
 *
 * @param buf Byte array type�� ���ڿ�
 * @param len ������ ���ϴ� ũ��
 * @return ����� String��
 */
public String HEX( byte[] buf, int len ) {
    StringBuffer sbuf = new StringBuffer();

    for ( int i = 0 ; i < len ; i++ ) {
        if ( buf[i] == '\n' ) {
            sbuf.append("<font color=red>LF</font>\n");
        } else {
            if ( buf[i] == '\r' ) {
                sbuf.append("<font color=red>CR</font>");
            } else {
                sbuf.append( Integer.toHexString( new Byte(buf[i]).intValue() ) );
            }
        }
    }

    return sbuf.toString();
}
/**
 * Byte Array�� �Ǿ��ִ� ���ڿ��� boundary���� ���θ� true, false�� return�Ͽ��ش�.
 * boundary������ �̹� boundary[]�� ����Ǿ� �ִ�.
 *
 * @param b[] �Է¹��� ���ڿ�. byte array type.
 * @param off ���ڿ��� ����κк��� �������� �����Ͽ� �ִ� �κ�.
 * @param len �Է¹��� byte array�� ũ��
 * @return boundary[]�� �Է¹��� ���ڿ��� ������ true,�ٸ��� false�� return�Ѵ�.
 */
private boolean isBoundary( byte[] b, int off, int len ) {
    if ( len != boundary.length ) {
        return false;
    }
    for ( int i=0 ; i < len ; i++ ) {
        if ( boundary[i] != b[off+i] ) return false;
    }
    return true;
}
/**
 * Byte Array�� �Ǿ��ִ� ���ڿ��� close delimiter ���� ���θ� true, false�� return�Ͽ��ش�.
 * close delimeter�� CRLF + '--' + boundary string + '--' ���� �Ǿ� �ִ�.
 *
 * @param b[] �Է¹��� ���ڿ�. byte array type.
 * @param off ���ڿ��� ����κк��� �������� �����Ͽ� �ִ� �κ�.
 * @param len �Է¹��� byte array�� ũ��
 * @return �Է¹��� ���ڿ��� ���ϴ� ���ڿ��� ������ true,�ٸ��� false�� return�Ѵ�.
 */
private boolean isCloseDelimiter( byte[] b, int off, int len ) {
    return  isDelimiter( b, off, len-2 ) &&
            b[off+len-2] == '-' &&
            b[off+len-1] == '-' ;
}
/**
 * Byte Array�� �Ǿ��ִ� ���ڿ��� dash bondary���� ���θ� true, false�� return�Ͽ��ش�.
 * dash boundary�� '--' + boundary �̴�.
 *
 * @param b[] �Է¹��� ���ڿ�. byte array type.
 * @param off ���ڿ��� ����κк��� �������� �����Ͽ� �ִ� �κ�.
 * @param len �Է¹��� byte array�� ũ��
 * @return boundary[]�� �Է¹��� ���ڿ���'--'+boundary�� ������ true,�ٸ��� false�� return�Ѵ�.
 */
private boolean isDashBoundary( byte[] b, int off, int len ) {
    return  b[off] == '-' &&
            b[off+1] == '-' &&
            isBoundary( b, off+2, len-2 );
}
/**
 * Byte Array�� �Ǿ��ִ� ���ڿ��� delimiter ���� ���θ� true, false�� return�Ͽ��ش�.
 * delimeter�� CRLF + '--' + boundary string ���� �Ǿ� �ִ�.
 *
 * @param b[] �Է¹��� ���ڿ�. byte array type.
 * @param off ���ڿ��� ����κк��� �������� �����Ͽ� �ִ� �κ�.
 * @param len �Է¹��� byte array�� ũ��
 * @return �Է¹��� ���ڿ��� ���ϴ� ���ڿ��� ������ true,�ٸ��� false�� return�Ѵ�.
 */
private boolean isDelimiter( byte[] b, int off, int len ) {
    return  b[off] == '\r' &&
            b[off+1] == '\n' &&
            isDashBoundary( b, off+2, len-2 );
}
/**
 * Byte Array�� �Ǿ��ִ� ���ڿ��� ������ ��� CRLF�θ� �������� ���θ�
 * true, false�� return�Ͽ� �ش�.
 *
 * @param b[] �Է¹��� ���ڿ�. byte array type.
 * @param off ���ڿ��� ����κк��� �������� �����Ͽ� �ִ� �κ�.
 * @param len �Է¹��� byte array�� ũ��
 * @return ������ ��� CRLF�θ� ������ ���ڿ����� true, false ���θ� return�Ѵ�.
 */
private boolean isEmptyLine( byte[] b, int off, int len ) {
    return len == 2 && endsWithCRLF( b, off, len );
}
/**
 * �а� �ִ� ��Ʈ���� ���¿� ��� readLine()�� �Ͽ� �� Line����<br>
 * Byte Array�� �����Ѵ�.<br>
 * ���¿� ��� �ٸ��� ���۽�Ű�� ������ �������¿� Type�� �������־�<br>
 * �ٸ� ���·� ������ ��Ʈ���� ������ ��Ȯ�ϰ� ���ϱ� �����̴�.<br>
 * ���´� ������ ���� ���Ѵ�.<br>
 * S_PREAMBLE->S_MIME_HEADER->S_MIME_ENTRY->S_FORM_OCTET_DATA->S_DELIMITER->S_EPILOGUE<br>
 * �� ���¿��� Line�� ������ �������¿� ���� ���°��� Type�� ������ �ȴ�. <br>
 * ������ ������ Steam�� ������ �ľ��ϰ� ������ ���� ������ �� �ִ�.<br>
 * byte�� ���� ������ �ξ�� �Ѵ�. <br>
 * �ּ��� CRLF + -- boundary -- CRLF �̻��� �Ǿ�� �Ѵ�.<br>
 * OCTET DATA�� ������ Delimiter�� �ƴ� CRLF���̶�� �׳� OCTET DATA�� ���̴�.<br>
 * �̷��� ������ unread()�Ͽ� �� ���� ���� DATA�� Delimiter�� <br>
 * CRLF�� OCTET DATA�� �Ǵ°��̰� �ƴϸ� OCTET Byte���� �����°��̴�.<br>
 *<br>
 * ���º��� �������¸� �о����� ������ �������.<br>
 *<br>
 * S_PREAMBLE : isDashBoundary() && endsWithCRFL �̸� S_MIME_HEADER�� ����<br>
 *<br>
 * S_MIME_HEADER : isEmptyLine() �̸� S_FORM_OCTET_DATA,<br>
 *                 endWithCRLF() �̸� ���´� ��� S_MIME_HEADER,<br>
 *                 �� �ܿ��� S_MIME_HEADER_ENTRY     <br>
 *<br>
 * S_MIME_HEADER_ENTRY : endWithCRLF() �̸� S_MIME_HEADER,<br>
 *                       �� �ܿ��� ���´� ��� S_MIME_HEADER_ENTRY     <br>
 *<br>
 * S_FORM_OCTET_DATA : endWithCRLF() �̸� S_DELIMITER,<br>
 *                     �� �ܿ��� ���´� ��� S_FORM_OCTET_DATA<br>
 *<br>
 * S_DELIMITER : isDelimiter() �̸� S_MIME_HEADER,<br>
 *               isCloseDelimiter() �̸� S_EPILOGUE<br>
 *               �� �ܿ��� S_FORM_OCTET_DATA<br>
 *<br>
 * S_EPILOGUE :  ���´� ��� S_EPOLOGUE (�������� ����)<br>
 *<br>
 * @param b[] ���� ���� ������ Byte Type�� �迭.
 * @throws IOException ����� readLine()���� error�� �߻�Ǿ�����
 * @return ���� Byte count�� return�ϰ� ����������쿡�� -1�� return�Ѵ�.
 * @see PushbackInputStream
 */
public int readFormData( byte[] b ) throws IOException {
    int result = 0 ;
    int len = b.length;
    switch( getState() ) {
        case S_PREAMBLE:
            if ( (result = readLine( b, 0, len ) ) == -1 )
                break;
            if ( isDashBoundary( b, 0, result-2 ) &&
                endsWithCRLF( b, 0, result ) ) {
                setState( S_MIME_HEADER );
                setReadDataType( T_DASH_BOUNDARY );
            } else {
                setReadDataType( T_PREAMBLE );
            }
            break;

        case S_MIME_HEADER:
            if ( (result = readLine( b, 0, len ) ) == -1 )
                break;
            if ( isEmptyLine( b, 0, result ) ) {
                setState( S_FORM_OCTET_DATA );
                setReadDataType( T_MIME_HEADER_END );
            } else if ( endsWithCRLF( b, 0, result ) ) {
                setReadDataType( T_MIME_HEADER );
            } else {
                setState( S_MIME_HEADER_ENTRY );
                setReadDataType( T_MIME_HEADER_ENTRY );
            }
            break;

        case S_MIME_HEADER_ENTRY:
            if ( (result = readLine( b, 0, len ) ) == -1 )
                break;
            if ( endsWithCRLF( b, 0, result ) ) {
                setState( S_MIME_HEADER );
                setReadDataType( T_MIME_HEADER_ENTRY_END );
            } else {
                setReadDataType( T_MIME_HEADER_ENTRY );
            }
            break;

        case S_FORM_OCTET_DATA:
            if ( (result = readLine( b, 0, len ) ) == -1 )
                break;
            if ( endsWithCRLF( b, 0, result ) ) {
                setState( S_DELIMITER );
                unread( b, result - 2, 2 );
                result -= 2;
                setReadDataType( T_FORM_OCTET_DATA );
            } else {
                setReadDataType( T_FORM_OCTET_DATA );
            }
            break;

        case S_DELIMITER:
            if ( (result = readTwoLine( b, 0, len ) ) == -1 )
                break;
            if ( isDelimiter( b, 0, result-2 ) &&
                endsWithCRLF( b, 0, result ) ) {
                setState( S_MIME_HEADER );
                setReadDataType( T_DELIMITER );
            } else if ( isCloseDelimiter( b, 0, result ) ||
                       ( isCloseDelimiter(b,0,result-2) &&
                          endsWithCRLF(b,0,result) )
                      ) {
                setState( S_EPILOGUE );
                setReadDataType( T_COLSE_DELIMITER );
            } else if ( endsWithCRLF( b, 0, result ) ) {
                setState( S_FORM_OCTET_DATA );
                unread( b, result - 2, 2 );
                result -= 2;
                setReadDataType( T_FORM_OCTET_DATA );
            } else {
                setState( S_FORM_OCTET_DATA );
                setReadDataType( T_FORM_OCTET_DATA );
            }
            break;

        case S_EPILOGUE:
        default :
            result = readLine( b, 0, len );
            setReadDataType( T_EPILOGUE );
            break;
    }
    //switch

    //if ( result == -1 ) throw new EOFException();
    return result;
}
/**
 * ��Ʈ���� Byte�� ������ ���� �о� �� ���� Byte Array�� �����Ѵ�.<br>
 * �Է¹��� length��  0�� ��� 0�� return�Ѵ�.<br>
 * �Է¹��� length�� �����߰ų� CRLF�� �������� ���� Byte Array�� �����ϰ�<br>
 * �۾��� �������� ���ϸ����� ���ڿ��� '\r'�� ���������� �ڿ� '/n'�� ���� <br>
 * �𸣱⶧���� unread()�� �̿��Ͽ� '\r'�� ������ ���·� �����.<br>
 * Byte Array�� ����Ǵ� ���� CRLF������ ���̴�.<br>
 * �� Line�̻� �о������� Line count�� return�ϰ� ������������ -1�� return�Ѵ�. <br>
 *<br>
 * @param b[] ���� ���� ������ Byte Type�� �迭.
 * @param off ���ڿ��� ����κк��� �������� �����Ͽ� �ִ� �κ�.
 * @param len �Է¹��� byte array�� ũ��
 * @throws IOException ����� read()���� error�� �߻�Ǿ�����
 * @return ���� Byte count�� return�ϰ� ����������쿡�� -1�� return�Ѵ�.
 * @see PushbackInputStream
 */
private int readLine(byte[] b, int off, int len ) throws IOException {
    if (len == 0) {
        return 0;
    }
    int cnt = 0, c;

    while ((c = read()) != -1) {
        b[off++] = (byte)c;
        cnt++;
        if ( c == '\n' ) {
            if ( off >= 2 && b[off-2] == '\r' ) { // �������� \r\n�̸�
                break;
            }
        }
        if ( cnt == len) {
            break;
        }
    }

    // ������ byte�� '\r'�̸� unread... '\r'�� '\n'�� ���� ���� �о�� ��.
    if ( b.length > 0 && b[b.length-1] == '\r' ) {
        unread( b[b.length-1] );
        //off--;
        cnt--;
    }

    return cnt > 0 ? cnt : -1;
}
/**
 * readLine()�� �̿��Ͽ� 2���� Line�� �о� ����θ� return�Ѵ�.
 * �� Line�̻� �о������� Line count�� return�ϰ� ������������ -1�� return�Ѵ�.
 *
 * @param b[] �Է¹��� ���ڿ�. byte array type.
 * @param off ���ڿ��� ����κк��� �������� �����Ͽ� �ִ� �κ�.
 * @param len �Է¹��� byte array�� ũ��
 * @return ���� Line count�� return�ϰ� ����������쿡�� -1�� return�Ѵ�.
 */
private int readTwoLine(byte[] b, int off, int len )  throws IOException {
    int cnt = 0, result=0;
    // ó�� CRLF�� �о� ����.
    for( int i = 0; i < 2 ; i++ ) {
        off += result; len -= result;
        if ( (result = readLine( b, off, len )) == -1 )
            break;
        cnt += result;
    }

    return cnt > 0 ? cnt : -1;
}
/**
 * readDataType�� ���� �����Ѵ�. ���� �а��ִ� ��Ʈ���� type�� �����Ͽ� �ش�.
 *
 * @param readDataType integer�������� �ƹ����� �ִ°��� �ƴ϶� final������
 * ����Ǿ� �ִ� 'T_OOOOO'�߿��� ������ �Ѵ�.
 */
private void setReadDataType( int readDataType ) {
    this.readDataType = readDataType;
}
/**
 * state�� ���� �����Ѵ�. ���� �а� �ִ� ��Ʈ���� ���¸� �����Ͽ� �ش�.
 *
 * @param state integer�������� �ƹ����� �ִ°��� �ƴ϶� final������
 * ����Ǿ� �ִ� 'S_OOOOO'�߿��� ������ �Ѵ�.
 */
private void setState( int state ) {
    this.state = state;
}
}
