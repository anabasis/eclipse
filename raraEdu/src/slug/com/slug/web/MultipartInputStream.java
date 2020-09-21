package com.slug.web;

/**
 * @(#) MultipartInputStream.java
 * @version
 * Copyright
 * All rights reserved.
 * 작성 :
 * @author
 *         
 *
 */

import java.io.*;

import com.slug.logging.Logging;

/**
 * ServletInputStream으로 부터 의미있는 단위(Line)로 나누어 읽는 방법을 제공한다.<br>
 * unread()를 사용하기 위하여 PushbackInputStream 을 상속받았다.<br>
 * unread()가 사용되는 method는 readLine(), readFormData()이다.<br>
 * 내부적으로 상태를 관리하여 Buffer로 읽어들인 값의 type을 <br>
 * 알 수 있게 설계하였다.<br>
 * 상태는 입력 Stream의 부위에 따라 6개의 final로 integer로 관리된다.<br>
 * 상태와는 별도로 현재 작업하고 있는 stream의 type도 관리된다. <br>
 * 상태는 다음과 같다.<br>
 * S_PREAMBLE->S_MIME_HEADER->S_MIME_ENTRY->S_FORM_OCTET_DATA->S_DELIMITER->S_EPILOGUE<br>
 * S_ : 상태 상수,  T_ : TYPE 상수<br>
 * <br>
 * 참고) body of the request <br>
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
     * Type을 나타내는 final 변수(상수). 지정된 숫자는 의미없는 고유의 값
     * preamble data type
     */
    public static final int T_PREAMBLE               = 10 ;

    /**
     * '--' + boundary 값을 나타낸다.
     * 새로운 입력 parameter의 시작을 알린다.
     */
    public static final int T_DASH_BOUNDARY          = 20 ;

    /**
     * content-type, content-disposition값은 MIME-part-headers
     * MIME header type
     */
    public static final int T_MIME_HEADER            = 30 ;

    /**
     * MIME-part-headers 의 끝인 empty line (CRLF로 구성)
     * MIME header end type
     */
    public static final int T_MIME_HEADER_END        = 40 ;

    /**
     * 읽어들인 Buffer의 크기가 총 Header의 크기보다 작을 경우
     * 총 Header의 일부분을 의미하는 type.
     * (한 Header는 여러개의 Entry로 구성가능)
     * MIME header entry type
     */
    public static final int T_MIME_HEADER_ENTRY      = 50 ;

    /**
     * MIME header Entry의 끝부분
     * MIME header entry end type
     */
    public static final int T_MIME_HEADER_ENTRY_END  = 60 ;

    /**
     * Header가 끝난뒤에 다음 Delimiter까지 오는 DATA.
     * file일 경우 file 내용이 되고 Text등의 입력form인경우
     * 입력된 자료가 된다.
     * delimiter가 나올때까지의 CRLF는 무시된다.
     * form octet data type
     */
    public static final int T_FORM_OCTET_DATA        = 70 ;

    /**
     * CRLF+"--"+boundary
     * 각 form data들을 구분짓는 delimiter type
     */
    public static final int T_DELIMITER              = 80 ;

    /**
     * CRLF+"--"+boundary+"--"
     * Form data의 body가 끝났음을 의미함
     */
    public static final int T_COLSE_DELIMITER        = 90 ;

    /**
     * Close Delimiter 이후에 오는 모든 DATA
     * epilogue type
     */
    public static final int T_EPILOGUE               = 100 ;

    /**
     * Mime header가 나오기 이전의 DATA를 처리하는중을 의미
     * 시작상태
     */
    public static final int S_PREAMBLE               = 10 ;

    /**
     * Mime header DATA를 처리하는중을 의미
     */
    public static final int S_MIME_HEADER            = 20 ;

    /**
     * Mime header entry DATA를 처리하는중을 의미
     */
    public static final int S_MIME_HEADER_ENTRY      = 30 ;

    /**
     * 입력받은 DATA를 처리하는중을 의미
     */
    public static final int S_FORM_OCTET_DATA        = 40 ;

    /**
     * Delimiter 를 처리하는중을 의미
     */
    public static final int S_DELIMITER              = 50 ;

    /**
     * Delimiter이후의 모든 DATA를 처리하는중을 의미
     * 사실상의 종료상태
     */
    public static final int S_EPILOGUE               = 60 ;

    /**
     * 변하는 Type 값을 가지고 있는 변수
     * 초기값으로 T_PREAMBLE값을 가진다.
     */
    private int readDataType    = T_PREAMBLE;

    /**
     * 변하는 상태 값을 가지고 있는 변수
     * 초기값으로 S_PREAMBLE값을 가진다.
     */
    private int state           = S_PREAMBLE;

    /**
     * boundary로 쓸 문자열을 저장하는 변수.
     * delimiter는 "\r\m--boundary"이므로 boundary보다 4byte가 많다.
     */
    private byte[] boundary;
/**
 * InputStream type 파라메터를 받아서 PushbackInputStream의 Constructor를 <br>
 * 사용한다. PushbackInputStream은 Stream정보를 Buffer로 관리하여 <br>
 * 유연하게 unread() method를 사용하게 해준다.<br>
 * PushbackInputStream은 FilterdInputStream을 상속받으므로 Buffer를 사용한다는<br>
 * 차이점만 있을뿐 일반 InputStream처럼 사용할 수 있다.<br>
 * 또 contentType으로는 boundary string을 추출하여 사용할 수 있게 한다.<br>
 *<br>
 * @param in 사용자로 부터 전달받은 사용할 InputStream Type<br>
 * @param contentType contentType으로 부터 boundary를 알아내기 위하여 전달<br>
 * @see PushbackInputStream
 */
public MultipartInputStream(InputStream in, String contentType ) {
    super( in, 80);
    boundary = contentType.substring( contentType.indexOf("boundary=")+9).getBytes();
    Logging.dev.println("< "+this.getClass().getName()+" > boundary size: "+boundary.length);
}
/**
 * Byte Array로 되어있는 문자열의 끝이 CRLF(\r\n)으로 끝나는지 확인하여
 * 그 여부를 true, false로 return하여 준다.
 *
 * @param b[] 입력받은 문자열. byte array type.
 * @param off 문자열의 어느부분부터 시작할지 결정하여 주는 부분.
 * @param len 입력받은 byte array의 크기
 * @return CRLF로 끝나는 문자열인지 true, false 여부를 return한다.
 */
private boolean endsWithCRLF( byte[] b, int off, int len ) {
    return  b[off+len-2] == '\r' &&
        b[off+len-1] == '\n';
}
/**
 * readDataType을 return하여 준다.
 * read data type은 'T_'로 시작하는 final변수중의 하나의 값이다.
 *
 * @return readDataType
 */
public int getReadDataType()  {
    return readDataType;
}
/**
 * 현재 설정되어 있는 Type을 html형식으로 return한다.
 * 개발자의 debugging용으로 사용되어 진다.
 *
 * @return 현재 설정되어 있는 Type을 html형식으로 return한다.
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
 * state를  return하여 준다.
 * state는 'S_'로 시작하는 final변수중의 하나의 값이다.
 *
 * @return state
 */
public int getState() {
    return state;
}
/**
 * 현재 설정되어 있는 상태를 html형식으로 return한다.
 * 개발자의 debugging용으로 사용되어 진다.
 *
 * @return 현재 설정되어 있는 상태를 html형식으로 return한다.
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
 * Byte Array의 문자열을 String 으로 변경하여 html형식으로 보여준다.
 * '\r'경우 'CR'로, '\n'의 경우 'LF'로 변경시킨다.
 * 개발자의 debugging용으로 사용되어 진다.
 *
 * @param buf Byte array type의 문자열
 * @return 변경된 String값
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
 * Byte Array의 문자열을 String 으로 변경하여 html형식으로 보여준다.<br>
 * '\r'경우 'CR'로, '\n'의 경우 'LF'로 변경시킨다.<br>
 * 필요한 길이만큼만 변경한다.<br>
 * 개발자의 debugging용으로 사용되어 진다.<br>
 *
 * @param buf Byte array type의 문자열
 * @param len 변경을 원하는 크기
 * @return 변경된 String값
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
 * Byte Array로 되어있는 문자열이 boundary인지 여부를 true, false로 return하여준다.
 * boundary정보는 이미 boundary[]에 저장되어 있다.
 *
 * @param b[] 입력받은 문자열. byte array type.
 * @param off 문자열의 어느부분부터 시작할지 결정하여 주는 부분.
 * @param len 입력받은 byte array의 크기
 * @return boundary[]와 입력받은 문자열이 같으면 true,다르면 false를 return한다.
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
 * Byte Array로 되어있는 문자열이 close delimiter 인지 여부를 true, false로 return하여준다.
 * close delimeter는 CRLF + '--' + boundary string + '--' 으로 되어 있다.
 *
 * @param b[] 입력받은 문자열. byte array type.
 * @param off 문자열의 어느부분부터 시작할지 결정하여 주는 부분.
 * @param len 입력받은 byte array의 크기
 * @return 입력받은 문자열이 원하는 문자열과 같으면 true,다르면 false를 return한다.
 */
private boolean isCloseDelimiter( byte[] b, int off, int len ) {
    return  isDelimiter( b, off, len-2 ) &&
            b[off+len-2] == '-' &&
            b[off+len-1] == '-' ;
}
/**
 * Byte Array로 되어있는 문자열이 dash bondary인지 여부를 true, false로 return하여준다.
 * dash boundary는 '--' + boundary 이다.
 *
 * @param b[] 입력받은 문자열. byte array type.
 * @param off 문자열의 어느부분부터 시작할지 결정하여 주는 부분.
 * @param len 입력받은 byte array의 크기
 * @return boundary[]와 입력받은 문자열이'--'+boundary와 같으면 true,다르면 false를 return한다.
 */
private boolean isDashBoundary( byte[] b, int off, int len ) {
    return  b[off] == '-' &&
            b[off+1] == '-' &&
            isBoundary( b, off+2, len-2 );
}
/**
 * Byte Array로 되어있는 문자열이 delimiter 인지 여부를 true, false로 return하여준다.
 * delimeter는 CRLF + '--' + boundary string 으로 되어 있다.
 *
 * @param b[] 입력받은 문자열. byte array type.
 * @param off 문자열의 어느부분부터 시작할지 결정하여 주는 부분.
 * @param len 입력받은 byte array의 크기
 * @return 입력받은 문자열이 원하는 문자열과 같으면 true,다르면 false를 return한다.
 */
private boolean isDelimiter( byte[] b, int off, int len ) {
    return  b[off] == '\r' &&
            b[off+1] == '\n' &&
            isDashBoundary( b, off+2, len-2 );
}
/**
 * Byte Array로 되어있는 문자열이 내용은 없고 CRLF로만 끝나는지 여부를
 * true, false로 return하여 준다.
 *
 * @param b[] 입력받은 문자열. byte array type.
 * @param off 문자열의 어느부분부터 시작할지 결정하여 주는 부분.
 * @param len 입력받은 byte array의 크기
 * @return 내용은 없고 CRLF로만 끝나는 문자열인지 true, false 여부를 return한다.
 */
private boolean isEmptyLine( byte[] b, int off, int len ) {
    return len == 2 && endsWithCRLF( b, off, len );
}
/**
 * 읽고 있는 스트림의 상태에 따라서 readLine()을 하여 한 Line값을<br>
 * Byte Array에 저장한다.<br>
 * 상태에 따라 다르게 동작시키는 이유는 다음상태와 Type을 지정해주어<br>
 * 다른 상태로 들어오는 스트림의 정보를 정확하게 관리하기 위함이다.<br>
 * 상태는 다음과 같이 변한다.<br>
 * S_PREAMBLE->S_MIME_HEADER->S_MIME_ENTRY->S_FORM_OCTET_DATA->S_DELIMITER->S_EPILOGUE<br>
 * 한 상태에서 Line을 읽으면 다음상태에 대한 상태값과 Type이 세팅이 된다. <br>
 * 내용은 들어오는 Steam의 내용을 파악하고 있으면 쉽게 이해할 수 있다.<br>
 * byte의 길이 제한을 두어야 한다. <br>
 * 최소한 CRLF + -- boundary -- CRLF 이상은 되어야 한다.<br>
 * OCTET DATA를 읽을때 Delimiter가 아닌 CRLF만이라면 그냥 OCTET DATA일 뿐이다.<br>
 * 이럴때 무조건 unread()하여 그 다음 읽은 DATA가 Delimiter면 <br>
 * CRLF도 OCTET DATA가 되는것이고 아니면 OCTET Byte열이 끝나는것이다.<br>
 *<br>
 * 상태별로 다음상태를 읽었을때 동작은 다음과같다.<br>
 *<br>
 * S_PREAMBLE : isDashBoundary() && endsWithCRFL 이면 S_MIME_HEADER로 변경<br>
 *<br>
 * S_MIME_HEADER : isEmptyLine() 이면 S_FORM_OCTET_DATA,<br>
 *                 endWithCRLF() 이면 상태는 계속 S_MIME_HEADER,<br>
 *                 그 외에는 S_MIME_HEADER_ENTRY     <br>
 *<br>
 * S_MIME_HEADER_ENTRY : endWithCRLF() 이면 S_MIME_HEADER,<br>
 *                       그 외에는 상태는 계속 S_MIME_HEADER_ENTRY     <br>
 *<br>
 * S_FORM_OCTET_DATA : endWithCRLF() 이면 S_DELIMITER,<br>
 *                     그 외에는 상태는 계속 S_FORM_OCTET_DATA<br>
 *<br>
 * S_DELIMITER : isDelimiter() 이면 S_MIME_HEADER,<br>
 *               isCloseDelimiter() 이면 S_EPILOGUE<br>
 *               그 외에는 S_FORM_OCTET_DATA<br>
 *<br>
 * S_EPILOGUE :  상태는 계속 S_EPOLOGUE (실질적인 종료)<br>
 *<br>
 * @param b[] 읽은 값을 저장할 Byte Type의 배열.
 * @throws IOException 사용한 readLine()에서 error가 발생되었을때
 * @return 읽은 Byte count를 return하고 실패했을경우에는 -1을 return한다.
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
 * 스트림의 Byte를 정해진 순서대로 읽어 그 값을 Byte Array에 저장한다.<br>
 * 입력받은 length가  0일 경우 0을 return한다.<br>
 * 입력받은 length에 도달했거나 CRLF를 만났을때 값을 Byte Array에 저장하고<br>
 * 작업을 끝내지만 제일마지막 문자열을 '\r'로 끝났을경우는 뒤에 '/n'이 올지 <br>
 * 모르기때문에 unread()를 이용하여 '\r'을 안읽은 상태로 만든다.<br>
 * Byte Array에 저장되는 값은 CRLF이전의 값이다.<br>
 * 한 Line이상 읽었을때는 Line count를 return하고 실패했을때는 -1를 return한다. <br>
 *<br>
 * @param b[] 읽은 값을 저장할 Byte Type의 배열.
 * @param off 문자열의 어느부분부터 시작할지 결정하여 주는 부분.
 * @param len 입력받은 byte array의 크기
 * @throws IOException 사용한 read()에서 error가 발생되었을때
 * @return 읽은 Byte count를 return하고 실패했을경우에는 -1을 return한다.
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
            if ( off >= 2 && b[off-2] == '\r' ) { // 마지막이 \r\n이면
                break;
            }
        }
        if ( cnt == len) {
            break;
        }
    }

    // 마지막 byte가 '\r'이면 unread... '\r'과 '\n'은 같이 같이 읽어야 함.
    if ( b.length > 0 && b[b.length-1] == '\r' ) {
        unread( b[b.length-1] );
        //off--;
        cnt--;
    }

    return cnt > 0 ? cnt : -1;
}
/**
 * readLine()을 이용하여 2개의 Line을 읽어 성공여부를 return한다.
 * 한 Line이상 읽었을때는 Line count를 return하고 실패했을때는 -1를 return한다.
 *
 * @param b[] 입력받은 문자열. byte array type.
 * @param off 문자열의 어느부분부터 시작할지 결정하여 주는 부분.
 * @param len 입력받은 byte array의 크기
 * @return 읽은 Line count를 return하고 실패했을경우에는 -1을 return한다.
 */
private int readTwoLine(byte[] b, int off, int len )  throws IOException {
    int cnt = 0, result=0;
    // 처음 CRLF를 읽어 들임.
    for( int i = 0; i < 2 ; i++ ) {
        off += result; len -= result;
        if ( (result = readLine( b, off, len )) == -1 )
            break;
        cnt += result;
    }

    return cnt > 0 ? cnt : -1;
}
/**
 * readDataType의 값을 설정한다. 현재 읽고있는 스트림의 type을 결정하여 준다.
 *
 * @param readDataType integer값이지만 아무값을 넣는것이 아니라 final변수로
 * 선언되어 있는 'T_OOOOO'중에서 설정을 한다.
 */
private void setReadDataType( int readDataType ) {
    this.readDataType = readDataType;
}
/**
 * state의 값을 설정한다. 현재 읽고 있는 스트림의 상태를 결정하여 준다.
 *
 * @param state integer값이지만 아무값을 넣는것이 아니라 final변수로
 * 선언되어 있는 'S_OOOOO'중에서 설정을 한다.
 */
private void setState( int state ) {
    this.state = state;
}
}
