package com.slug.util;

/**
 * @(#) CharConversion.java
 * @version
 * Copyright
 * All rights reserved.
 * 작성 :
 * @author
 *         
 *
 */

import java.io.UnsupportedEncodingException;

/**
 * java에서 한글을 사용하기 위하여 사용하는 Class
 * 영문(8859_1)과 한글(KSC5601)은 다른 Character Set을 사용하기때문에
 * 한글을 사용하기 위해서는 다른 Character Set을 사용하는 것이 필요하다.
 * 이 Class에서는 영문을 한글로, 한글을 영문으로 바꾸는 2개의 Method를 제공한다.
 *
 */
public final class CharConversion {

    /**
    * Don't let anyone instantiate this class
    */
    private CharConversion() {}
    /**
    * 영문을 한글로 Conversion해주는 Method.
    * (8859_1 --> KSC5601)
    * @param english 한글로 바꾸어질 영문 String
    * @return 한글로 바꾸어진 String
    */
    public static synchronized String E2K( String english ) {
        String korean = null;

        if (english == null ) {
            return null;
        }

        try {
            korean = new String(new String(english.getBytes("8859_1"), "KSC5601"));
        }
        catch( UnsupportedEncodingException e ){
            korean = new String(english);
        }
        return korean;
    }
    /**
    * 한글을 영문으로 Conversion해주는 Method.
    * ( KSC5601 --> 8859_1 )
    * @param korean 영문으로 바꾸어질 한글 String
    * @return 영문로 바꾸어진 String
    */
    public static synchronized String K2E( String korean ) {
        String english = null;

        if (korean == null ) {
            return null;
        }

        english = new String(korean);
        try {
            english = new String(new String(korean.getBytes("KSC5601"), "8859_1"));
        }
        catch( UnsupportedEncodingException e ){
            english = new String(korean);
        }
        return english;
    }
}
