package com.slug.util;

/**
 * @(#) CharConversion.java
 * @version KDSKIT
 * Copyright
 * All rights reserved.
 * �ۼ� :
 * @author �赿��, dongskim@solupia.co.kr
 *         SOLUPIA e-Biz Team
 *
 */

import java.io.UnsupportedEncodingException;

/**
 * java���� �ѱ��� ����ϱ� ���Ͽ� ����ϴ� Class
 * ����(8859_1)�� �ѱ�(KSC5601)�� �ٸ� Character Set�� ����ϱ⶧����
 * �ѱ��� ����ϱ� ���ؼ��� �ٸ� Character Set�� ����ϴ� ���� �ʿ��ϴ�.
 * �� Class������ ������ �ѱ۷�, �ѱ��� �������� �ٲٴ� 2���� Method�� �����Ѵ�.
 *
 */
public final class CharConversion {

    /**
    * Don't let anyone instantiate this class
    */
    private CharConversion() {}
    /**
    * ������ �ѱ۷� Conversion���ִ� Method.
    * (8859_1 --> KSC5601)
    * @param english �ѱ۷� �ٲپ��� ���� String
    * @return �ѱ۷� �ٲپ��� String
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
    * �ѱ��� �������� Conversion���ִ� Method.
    * ( KSC5601 --> 8859_1 )
    * @param korean �������� �ٲپ��� �ѱ� String
    * @return ������ �ٲپ��� String
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
