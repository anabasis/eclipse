package com.slug.util;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

public class StringUtil
{
    /**
	* 1. 기능 : 구분자가 들어간 스트링을 구분자기준으로 잘라 String Array를 만들어주는 Method
    * 2. 처리 개요 :
    *     - 구분자가 들어간 스트링을 구분자기준으로 잘라 String Array를 만들어주는 Method
    * 3. 주의사항
	*
	*	@param inStr 대상 스트링
    *   @param delim 잘라낼 때의 기준 구분자
    *   @return 잘라낸 스트링을 담고 있는 String Array
	*/
    public static String[] getStrArray(String inStr, String delim) {
        String[] retStr = null;

        if ( inStr == null || inStr.equals("") ) {
            return (new String[] {});
        } else {
           retStr = inStr.split(delim);
        }

        return retStr;
    }

    
    /**
	 * 1. 기능 : 주어진 문자열이 숫자로만 구성되어 있는지 확인한다.
	 *           공백이 있으면 false를 반환한다.
	 * 2. 처리 개요 :
     *    - char값이 48에서 57사이이면 숫자이다.
	 *
	 * @param	str			문자열
	 * @return 	문자열이 모두 숫자로만 이루어진경우 true
	 */
	public static boolean isNumeric(String str) {
		if (str==null || str.equals("")) return false;

		for (int i=0; i<str.length(); i++) {
			if ((int)str.charAt(i) < 48 || (int)str.charAt(i) > 57) return false;
		}
		return true;
	}

    /**
	 * 1. 기능 : 주어진 문자열이 문자로만 구성되어 있는지 확인한다.
	 *           공백은 true를 반환한다.
	 * 2. 처리 개요 :
     *    - 입력된 String을 대문자로 모두 변환한다. (알파벳은 65(A)에서 90(Z)까지이다.)
     *    - char값이 65이상이면 문자이다.
	 *
	 * @param	str			문자열
	 * @return 	문자열이 모두 문자로만 이루어진경우 true
	 */
	public static boolean isAlpha(String str) {
		if (str==null || str.equals("")) return true;

		for (int i=0; i<str.length(); i++) {
			if ((int)str.charAt(i) < 65 && ((int)str.charAt(i) != 32)) return false;
		}
		return true;
	}

    /**
     * 1.기능: Delimiter로 구분된 String을 받아들여 String[]을 반환한다.
     */
    public String[] parseStringWithDelimiter(String str, String delim)
    {
		String[] rtArray = {};
		int arrayCnt = getMissSeqNoCnt(str, delim.charAt(0));
		if(arrayCnt == 0){
			//rtArray = {};
		}else{
			rtArray = new String[arrayCnt];
			StringTokenizer st = new StringTokenizer(str, delim);
			int i=0;

			while (st.hasMoreTokens())
			{
				rtArray[i] = st.nextToken();
				i++;
			}
		}
        return rtArray;
    }

    private int getMissSeqNoCnt(String str, char delim)
    {
        /**
         * 1.기능: 입력된 String에서 0의 갯수만 반환한다.
         */
        int patternMatchCnt = 0;
		
        for(int i=0; i<str.length(); i++)
        {
            if(str.charAt(i) == delim)
            {
                patternMatchCnt++;
            }
        }
		return patternMatchCnt;
    }

    public static int stoi(String s)
    {
        if(s == null || s.equals(""))
            return 0;
        else
            return Integer.valueOf(replaceStr(s, ",", "").trim()).intValue();
    }

    public static String itos(int i)
    {
        return (new Integer(i)).toString();
    }

    public static String replaceStr(String s, String s1, String s2) throws NullPointerException
    {
        if(s == null || s.length() == 0)
            return "";
        if(s1 == null || s1.equals("") || s2 == null)
            return s;
        int i = 0;
        StringBuffer stringbuffer = new StringBuffer();
        while((i = s.indexOf(s1)) >= 0)
        {
            stringbuffer.append(s.substring(0, i)).append(s2);
            s = s.substring(i + s1.length());
        }
        stringbuffer.append(s);
        return stringbuffer.toString();
    }

	public static String nvl(String str) {
		return nvl(str, "");
	}

	public static String nvl(String str, String defaultValue) {
		if (str == null || str.equals("")) return defaultValue;
		return str;
	}

	public static String nvlTrim(String str) {
		return nvlTrim(str, "");
	}

	public static String nvlTrim(String str, String defaultValue) {
		if (str == null || str.trim().equals("")) return defaultValue;
		return str.trim();
	}

	/**
	 * <pre>
	 * 문자열을 주어진 포맷으로 일치시킨다.
	 * 용례) matchFormat("20010101", "####/##/##") -> "2001/01/01"
	 *       matchFormat("12345678", "##/## : ##") -> "12/34 : 56"
	 * </pre>
	 * @param	str			원본문자열
	 * @param	format		결과 포맷형태('#'문자에 원본 문자가 위치, 그외 문자는 그대로 표시)
	 * @return 	포맷으로 변환된 문자열
	 */
	public static String matchFormat(String str, String format) {
		if(str == null || str.length() == 0 ) return str;
		int len = format.length();
		char[] result = new char[len];
		for(int i=0,j=0; i<len; i++,j++) {
			if(format.charAt(i)=='#') {
				try {
					result[i]= str.charAt(j);
				}catch(StringIndexOutOfBoundsException e) {
					result[i]= '\u0000';
				}
			} else {
				result[i]= format.charAt(i);
				j--;
			}
		}
		return new String(result);
	}

    /*
     * UTF8 > KSC5601로 변환
    */
    public static String _K(String s)
    {
        //if(OSUtil.getOSType() ==1) return s;
        try { s= new String (s.getBytes ("8859_1"), "KSC5601"); }
        catch (java.io.UnsupportedEncodingException e) 	{ }
        catch (NullPointerException e) { }
        return s;
    }

    /*
     * KSC5601 > UTF8  로 변환
    */
    public static String _K2(String s)
    {
        //if(OSUtil.getOSType() ==1) return s;
        try { s = new String (s.getBytes ("KSC5601"), "8859_1"); }
        catch (java.io.UnsupportedEncodingException e) 	{ }
        catch (NullPointerException e) { }
        return s;
    }

		/**
	*	왼쪽에 공백을 채워 전체 width길이 만큼의 문자열을 반환한다.
	*/
	public static String rpad( String origin, int width ) {
		return lpad(origin, width, ' ');
    }

	public static String rpad(String origin, int width, char filler) {
		if( origin == null ) origin = "";
		StringBuffer buf = new StringBuffer();
        int space = width - origin.length();
        while ( space-- > 0 ) {
            buf.append(filler);
        }
		buf.append(origin);
        return buf.toString();
	}

	/**
	*	문자열의 오른쪽에 공백을 채워 전체 width길이 만큼의 문자열을 반환한다.
	*/
	public static String lpad(String origin, int width) {
		return rpad(origin, width, ' ');
	}

	public static String lpad(String origin, int width, char filler) {
		if( origin == null ) origin = "";
		StringBuffer buf = new StringBuffer(origin);
		int space = width - origin.length();
		while( space--  > 0) {
			buf.append(filler);
		}
		return buf.toString();
	}
	
	public static char getDelimite()
	{

	if(OSUtil.getOSType() ==1) return '\\';
	else  return '/';

	}
	
	/**
     * 1. 기능 : byte array를 원하는 Encoding의 스트링으로 변환하는 Method
     * 2. 처리 개요 :
     *
	 *   주요 Character Set( Parameter로 Name또는 Alias 입력 )
	 *   Name                    Alias                                   Description
	 *   --------------------    -------------------------------------   ----------------------------
	 *   ANSI_X3.4-1968          ASCII, US-ASCII, IBM367, cp367          영문 ASCII
	 *   KS_C_5601-1987          KSC_5601                                한글 완성형
	 *   EUC-KR                  csEUCKR                                 한글 조합형
	 *   ISO-2022-JP             csISO2022JP                             일어
	 *   ISO-2022-JP-2           csISO2022JP2                            일어
	 *   GB_2312-80              csISO58GB231280                         중국어
	 *   ISO_8859-1:1987         ISO_8859-1, ISO-8859-1, IBM819, CP819   영문 라틴1
	 *   UTF-8                                                           유니코드(8비트)
	 *   UTF-16                                                          유니코드(16비트)

	 * 3. 주의사항
	 *
	 *   참고 : http://www.iana.org/assignments/character-sets
	 *
	 *	@param bytes 변환대상 byte array
	 *  @param charsetName byte array decoding용 character set
	 *  @return byte array를 변환한 결과 스트링
	 */
    public static java.lang.String bytes2String(byte[] bytes, String charsetName) {
        StringBuffer strBuffer = new StringBuffer(30);
        try {
            strBuffer.append(new String(bytes, charsetName));
        } catch (UnsupportedEncodingException uee) {
        } catch (Exception e) {}

        return strBuffer.toString();
    }


	/**
     * 1. 기능 : 헥사스트링(16진수)을 byte arrary로 변환하는 Method
     * 2. 처리 개요 :
     *     - 헥사스트링(16진수)을 byte arrary로 변환하는 Method
     * 3. 주의사항
	*
    *   @param hexStr 변환대상 16진수 스트링
    *   @return 16진수 스트링을 변환한 결과 byte array
	*/
    public static byte[] hex2Bytes(String hexStr) {
        byte retByte[] = new byte[hexStr.length() / 2];
        for(int j = 0; j < retByte.length; j++)
            retByte[j] = (byte)Integer.parseInt(hexStr.substring(2 * j, 2 * j + 2), 16);
        return retByte;
    }


	/**
    * 1. 기능 : byte arrary를 헥사스트링(16진수)으로 변환하는 Method
    * 2. 처리 개요 :
    *     - byte arrary를 헥사스트링(16진수)으로 변환하는 Method
    * 3. 주의사항
	*
	*	@param inBytes 변환대상 byte array
    *   @return byte array를 변환한 16진수 결과 스트링
	*/
    public static String bytes2Hex(byte inBytes[]) {
        String s = "";
        for(int i = 0; i < inBytes.length; i++)
            s = s + Integer.toHexString((inBytes[i] & 0xf0) >> 4) + Integer.toHexString(inBytes[i] & 0xf);

        return s.toUpperCase();
    }


   /**
	* 1. 기능 : String을 헥사스트링(16진수)으로 변환하는 Method
    * 2. 처리 개요 :
    *     - String을 헥사스트링(16진수)으로 변환하는 Method
    * 3. 주의사항
	*
	*	@param inStr 변환대상 스트링
    *   @return 변환대상스트링을 변환한 16진수 결과 스트링
	*/
    public static String str2Hex(String inStr) {
        if ( inStr == null || inStr.equals("") )
        {
            return "";
        }
        String retStr = bytes2Hex( inStr.getBytes() );
        return retStr;
    }


   /**
	* 1. 기능 : 스트링에서 특정 문자 개수를 반환하는 Method(제어문자등이 전송중 누락되었는지 확인할 때 사용)
    * 2. 처리 개요 :
    *     - 스트링에서 특정 문자 개수를 반환하는 Method(제어문자등이 전송중 누락되었는지 확인할 때 사용)
    * 3. 주의사항
	*
	*	@param inStr 계수대상 스트링
    *   @param ch 계수할 문자
    *   @return int 계수결과(문자개수)
	*/
    public static int getCharCount(String inStr, char ch) {
		char[] arrChar	= inStr.toCharArray();
		int count = 0;
		for (int i=0; i < arrChar.length ; i++)
			if ( arrChar[i] == ch) count++;
		return count;
	}




    /**
     * 1. 기능 : String 이 null 이면 ""(Null String)을 리턴하는 Method
     * 2. 처리 개요 :
     *     - String 이 null 이면 ""(Null String)을 리턴하는 Method
     * 3. 주의사항
	 *
     * @param inStr 입력 스트링
     * @return Null 처리된 스트링
     */
    public static String getNullStr( String inStr )
    {
    	return inStr==null?"":inStr;
    }

    /**
     * 1. 기능 : Object가 null 이면 ""(Null String)을 리턴하는 Method
     * 2. 처리 개요 :
     *     - Object가 null 이면 ""(Null String)을 리턴하는 Method
     * 3. 주의사항
	 *
     * @param inObj 입력 Object
     * @return Null 처리된 스트링
     */
    public static String getNullStr( Object inObj )
    {
    	return inObj==null?"":(String)inObj;
    }

    /**
     * 1. 기능 : 주어진 String값을 주어진 길이많큼 Padding/Trim한다.
     * 2. 처리 개요 :
     *     - 주어진 String값을 주어진 길이많큼 Padding/Trim한다.
     * 3. 주의사항
	 *
     * @param svalue - 입력스트링
     * @param isRightJustify - true이면 RIGHT JUSTIFY, false이면 LEFT JUSTIFY
     * @param padding - Padding 문자
     * @param length - 리턴할 스트링의 바이트수
     * @return 포맷팅된 String결과
     */
    public static String stringFormat(String svalue, boolean isRightJustify, char padding, int length) {
        if ( svalue == null ) return svalue;

        String mpad = new String();
        int pLength = 0;

        pLength = length - svalue.getBytes().length;

        if (pLength == 0) {
            return svalue;
        }
        else if ( pLength < 0) {
            byte[] abytes = null;
            if (isRightJustify) {
            	abytes = (new String( svalue.getBytes(), -(pLength), length)).getBytes();
            } else {
            	abytes = (new String( svalue.getBytes(), 0, length)).getBytes();
            }

            if ( abytes.length == length ) {
                return  new String( abytes );
            } else {
              svalue = new String( abytes );
              pLength = length - svalue.length();
            }
        }

        for(int i =0; i < pLength ; i++)
            mpad += padding;

        if ( isRightJustify ) {
            return (mpad + svalue);
        } else {
            return (svalue + mpad);
        }
    }

    /**
     * 1. 기능 : 주어진 String값을 주어진 길이많큼 자른다.
     * 2. 처리 개요 :
     *     - 주어진 String값을 주어진 길이 많큼 자른다.
     * 3. 주의사항
	 *
     * @param svalue - 입력스트링
     * @param length - 리턴할 스트링의 바이트수
     * @return 포맷팅된 String결과와 남은 String
     */
    public static String[] chunkString(String sValue, int length)
    {
        String[] split = new String[2];

        byte[] bytesValue = sValue.getBytes();

        int doubleBytes = 0;

        if(bytesValue.length < length) {
    		split[0] = sValue;
    		split[1] = "";
    		return split;
    	}

        for(int i = 0; i<length; i++) {
	        if(((bytesValue[i] & 0x80)> 0)) {
	        	doubleBytes++;
	        }
        }

        if( (doubleBytes%2) > 0) {
        	length = length - 1;
    	}

    	byte[] retBytes 	= new byte[length];
    	byte[] remainBytes 	= new byte[bytesValue.length - length];

        System.arraycopy(bytesValue, 0, retBytes, 0, retBytes.length);
    	split[0] = new String(retBytes);

    	System.arraycopy(bytesValue, length, remainBytes, 0, remainBytes.length);
    	split[1] = new String(remainBytes);

    	return split;
    }

    /**
     * 1. 기능 : 주어진 String값을 주어진 길이많큼 똑같이 잘라서 배열에 넣는다.
     * 2. 처리 개요 :
     *     - 주어진 String값을 주어진 길이많큼 자른다.
     * 3. 주의사항
	 *
     * @param svalue - 입력스트링
     * @param length - 리턴할 스트링의 바이트수
     * @return 포맷팅된 String결과의배열
     */
    public static String[] chunkStringTotal(String sValue, int length){
        String[] data =null;
        HashMap hm = new HashMap();
        int i = 0;

        do {
             data = chunkString(sValue, length);
             hm.put(Integer.toString(i), data[0]);
             i++;
             sValue = data[1];
        }
        while (sValue.getBytes().length > length );

        if (sValue.length() > 0) {
            hm.put(Integer.toString(i), sValue);
        }

        String [] result = new String [hm.size()];
        for (int j = 0; j < hm.size(); j++) {
            result[j] = (String)hm.get(Integer.toString(j));
        }

        return result;

    }

    /**
     * 1. 기능 : 주어진 String값을 delimeter로 나누어 String[]를 생성한다
     * 2. 처리 개요 :
     *     - 주어진 String값을 delimeter로 나누어 String[]를 생성한다
     * 3. 주의사항
	 *
     * @param input - 입력스트링
     * @param delimeter - delimeter
     * @return 포맷팅된 String결과의배열
     */
    public static String[] stringTokenizer(String input, String delimeter)
    {
        StringTokenizer st = new StringTokenizer(input, delimeter);
        Vector 			vt = new Vector();

        String temp = null;

        while (st.hasMoreTokens()) {
            temp = st.nextToken();
            vt.addElement(temp);
        }

        String[] out = new String[vt.size()];
        for ( int i = 0; i < vt.size() ; i ++ ) {
        	out[i] = (String)vt.elementAt(i);
        }
        return out;
    }

    /**
     * 1. 기능 : 주어진 String값을 delimeter로 나누어 XML Tag를 추가한다.
     * 2. 처리 개요 :
     *     - 주어진 String값을 delimeter로 나누어 tag Prefix에 순차값을 생성하여 XML 형태의 String을 생성한다.
     * 3. 주의사항
	 *
     * @param input - 입력스트링
     * @param delimeter - delimeter
     * @param tagPrefix - tag Prefix
     * @return XML형태로 포맷팅된 String결과
     */
    public static String delimeterToXml(String input, String delimeter, String tagPrefix)
    {
        StringBuffer sb = new StringBuffer();

        if(input == null || input.length() == 0) return "";

        String[] tokens = stringTokenizer(input, delimeter);
        for(int i=0; i< tokens.length; i++) {
			sb.append("<" + tagPrefix + (i+1) + ">" + tokens[i] + "</" + tagPrefix + (i+1) + ">");
		}
        return sb.toString();
    }

    /**
     * 1. 기능 : 주어진 byte[]값을 delimeter로 나누어 XML Tag를 추가한다.
     * 2. 처리 개요 :
     *     - 주어진 byte[]값을 delimeter로 나누어 tag Prefix에 순차값을 생성하여 XML 형태의 byte[]을 생성한다.
     * 3. 주의사항
	 *
     * @param input - 입력 byte[]
     * @param delimeter - delimeter
     * @param tagPrefix - tag Prefix
     * @return XML형태로 포맷팅된 byte[]결과
     */
    public static byte[] delimeterToXmlBytes(byte[] input, String delimeter, String tagPrefix) {
        if(input == null) return new byte[0];
        String xmlStr = delimeterToXml(new String(input), delimeter, tagPrefix);
        return xmlStr.getBytes();
    }

}
