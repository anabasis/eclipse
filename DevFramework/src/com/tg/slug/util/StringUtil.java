package com.tg.slug.util;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.StringTokenizer;
import java.util.Vector;

public class StringUtil
{
    /**
	* 1. ��� : �����ڰ� �� ��Ʈ���� �����ڱ������� �߶� String Array�� ������ִ� Method
    * 2. ó�� ���� :
    *     - �����ڰ� �� ��Ʈ���� �����ڱ������� �߶� String Array�� ������ִ� Method
    * 3. ���ǻ���
	*
	*	@param inStr ��� ��Ʈ��
    *   @param delim �߶� ���� ���� ������
    *   @return �߶� ��Ʈ���� ��� �ִ� String Array
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
	 * 1. ��� : �־��� ���ڿ��� ���ڷθ� �����Ǿ� �ִ��� Ȯ���Ѵ�.
	 *           ����� ������ false�� ��ȯ�Ѵ�.
	 * 2. ó�� ���� :
     *    - char���� 48���� 57�����̸� �����̴�.
	 *
	 * @param	str			���ڿ�
	 * @return 	���ڿ��� ��� ���ڷθ� �̷������ true
	 */
	public static boolean isNumeric(String str) {
		if (str==null || str.equals("")) return false;

		for (int i=0; i<str.length(); i++) {
			if ((int)str.charAt(i) < 48 || (int)str.charAt(i) > 57) return false;
		}
		return true;
	}

    /**
	 * 1. ��� : �־��� ���ڿ��� ���ڷθ� �����Ǿ� �ִ��� Ȯ���Ѵ�.
	 *           ����� true�� ��ȯ�Ѵ�.
	 * 2. ó�� ���� :
     *    - �Էµ� String�� �빮�ڷ� ��� ��ȯ�Ѵ�. (���ĺ��� 65(A)���� 90(Z)�����̴�.)
     *    - char���� 65�̻��̸� �����̴�.
	 *
	 * @param	str			���ڿ�
	 * @return 	���ڿ��� ��� ���ڷθ� �̷������ true
	 */
	public static boolean isAlpha(String str) {
		if (str==null || str.equals("")) return true;

		for (int i=0; i<str.length(); i++) {
			if ((int)str.charAt(i) < 65 && ((int)str.charAt(i) != 32)) return false;
		}
		return true;
	}

    /**
     * 1.���: Delimiter�� ���е� String�� �޾Ƶ鿩 String[]�� ��ȯ�Ѵ�.
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
         * 1.���: �Էµ� String���� 0�� ������ ��ȯ�Ѵ�.
         */
        int patternMatchCnt = 0;
		//System.out.println(str.length());

        for(int i=0; i<str.length(); i++)
        {
            if(str.charAt(i) == delim)
            {
                patternMatchCnt++;
            }
        }
		//System.out.println(patternMatchCnt);
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
	 * ���ڿ��� �־��� �������� ��ġ��Ų��.
	 * ���) matchFormat("20010101", "####/##/##") -> "2001/01/01"
	 *       matchFormat("12345678", "##/## : ##") -> "12/34 : 56"
	 * </pre>
	 * @param	str			���ڿ�
	 * @param	format		��� ��������('#'���ڿ� �� ���ڰ� ��ġ, �׿� ���ڴ� �״�� ǥ��)
	 * @return 	�������� ��ȯ�� ���ڿ�
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
     * UTF8 > KSC5601�� ��ȯ
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
     * KSC5601 > UTF8  �� ��ȯ
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
	*	���ʿ� ����� ä�� ��ü width���� ��ŭ�� ���ڿ��� ��ȯ�Ѵ�.
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
	*	���ڿ��� �����ʿ� ����� ä�� ��ü width���� ��ŭ�� ���ڿ��� ��ȯ�Ѵ�.
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
     * 1. ��� : byte array�� ���ϴ� Encoding�� ��Ʈ������ ��ȯ�ϴ� Method
     * 2. ó�� ���� :
     *
	 *   �ֿ� Character Set( Parameter�� Name�Ǵ� Alias �Է� )
	 *   Name                    Alias                                   Description
	 *   --------------------    -------------------------------------   ----------------------------
	 *   ANSI_X3.4-1968          ASCII, US-ASCII, IBM367, cp367          ���� ASCII
	 *   KS_C_5601-1987          KSC_5601                                �ѱ� �ϼ���
	 *   EUC-KR                  csEUCKR                                 �ѱ� ������
	 *   ISO-2022-JP             csISO2022JP                             �Ͼ�
	 *   ISO-2022-JP-2           csISO2022JP2                            �Ͼ�
	 *   GB_2312-80              csISO58GB231280                         �߱���
	 *   ISO_8859-1:1987         ISO_8859-1, ISO-8859-1, IBM819, CP819   ���� ��ƾ1
	 *   UTF-8                                                           �����ڵ�(8��Ʈ)
	 *   UTF-16                                                          �����ڵ�(16��Ʈ)

	 * 3. ���ǻ���
	 *
	 *   ��� : http://www.iana.org/assignments/character-sets
	 *
	 *	@param bytes ��ȯ��� byte array
	 *  @param charsetName byte array decoding�� character set
	 *  @return byte array�� ��ȯ�� ��� ��Ʈ��
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
     * 1. ��� : ��罺Ʈ��(16���)�� byte arrary�� ��ȯ�ϴ� Method
     * 2. ó�� ���� :
     *     - ��罺Ʈ��(16���)�� byte arrary�� ��ȯ�ϴ� Method
     * 3. ���ǻ���
	*
    *   @param hexStr ��ȯ��� 16��� ��Ʈ��
    *   @return 16��� ��Ʈ���� ��ȯ�� ��� byte array
	*/
    public static byte[] hex2Bytes(String hexStr) {
        byte retByte[] = new byte[hexStr.length() / 2];
        for(int j = 0; j < retByte.length; j++)
            retByte[j] = (byte)Integer.parseInt(hexStr.substring(2 * j, 2 * j + 2), 16);
        return retByte;
    }


	/**
    * 1. ��� : byte arrary�� ��罺Ʈ��(16���)���� ��ȯ�ϴ� Method
    * 2. ó�� ���� :
    *     - byte arrary�� ��罺Ʈ��(16���)���� ��ȯ�ϴ� Method
    * 3. ���ǻ���
	*
	*	@param inBytes ��ȯ��� byte array
    *   @return byte array�� ��ȯ�� 16��� ��� ��Ʈ��
	*/
    public static String bytes2Hex(byte inBytes[]) {
        String s = "";
        for(int i = 0; i < inBytes.length; i++)
            s = s + Integer.toHexString((inBytes[i] & 0xf0) >> 4) + Integer.toHexString(inBytes[i] & 0xf);

        return s.toUpperCase();
    }


   /**
	* 1. ��� : String�� ��罺Ʈ��(16���)���� ��ȯ�ϴ� Method
    * 2. ó�� ���� :
    *     - String�� ��罺Ʈ��(16���)���� ��ȯ�ϴ� Method
    * 3. ���ǻ���
	*
	*	@param inStr ��ȯ��� ��Ʈ��
    *   @return ��ȯ���Ʈ���� ��ȯ�� 16��� ��� ��Ʈ��
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
	* 1. ��� : ��Ʈ������ Ư�� ���� ������ ��ȯ�ϴ� Method(����ڵ��� ����� �����Ǿ���� Ȯ���� �� ���)
    * 2. ó�� ���� :
    *     - ��Ʈ������ Ư�� ���� ������ ��ȯ�ϴ� Method(����ڵ��� ����� �����Ǿ���� Ȯ���� �� ���)
    * 3. ���ǻ���
	*
	*	@param inStr ������ ��Ʈ��
    *   @param ch ����� ����
    *   @return int ������(���ڰ���)
	*/
    public static int getCharCount(String inStr, char ch) {
		char[] arrChar	= inStr.toCharArray();
		int count = 0;
		for (int i=0; i < arrChar.length ; i++)
			if ( arrChar[i] == ch) count++;
		return count;
	}




    /**
     * 1. ��� : String �� null �̸� ""(Null String)�� �����ϴ� Method
     * 2. ó�� ���� :
     *     - String �� null �̸� ""(Null String)�� �����ϴ� Method
     * 3. ���ǻ���
	 *
     * @param inStr �Է� ��Ʈ��
     * @return Null ó���� ��Ʈ��
     */
    public static String getNullStr( String inStr )
    {
    	return inStr==null?"":inStr;
    }

    /**
     * 1. ��� : Object�� null �̸� ""(Null String)�� �����ϴ� Method
     * 2. ó�� ���� :
     *     - Object�� null �̸� ""(Null String)�� �����ϴ� Method
     * 3. ���ǻ���
	 *
     * @param inObj �Է� Object
     * @return Null ó���� ��Ʈ��
     */
    public static String getNullStr( Object inObj )
    {
    	return inObj==null?"":(String)inObj;
    }

    /**
     * 1. ��� : �־��� String���� �־��� ���̸�ŭ Padding/Trim�Ѵ�.
     * 2. ó�� ���� :
     *     - �־��� String���� �־��� ���̸�ŭ Padding/Trim�Ѵ�.
     * 3. ���ǻ���
	 *
     * @param svalue - �Է½�Ʈ��
     * @param isRightJustify - true�̸� RIGHT JUSTIFY, false�̸� LEFT JUSTIFY
     * @param padding - Padding ����
     * @param length - ������ ��Ʈ���� ����Ʈ��
     * @return �����õ� String���
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
     * 1. ��� : �־��� String���� �־��� ���̸�ŭ �ڸ���.
     * 2. ó�� ���� :
     *     - �־��� String���� �־��� ���� ��ŭ �ڸ���.
     * 3. ���ǻ���
	 *
     * @param svalue - �Է½�Ʈ��
     * @param length - ������ ��Ʈ���� ����Ʈ��
     * @return �����õ� String���� ���� String
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
     * 1. ��� : �־��� String���� �־��� ���̸�ŭ �Ȱ��� �߶� �迭�� �ִ´�.
     * 2. ó�� ���� :
     *     - �־��� String���� �־��� ���̸�ŭ �ڸ���.
     * 3. ���ǻ���
	 *
     * @param svalue - �Է½�Ʈ��
     * @param length - ������ ��Ʈ���� ����Ʈ��
     * @return �����õ� String����ǹ迭
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
     * 1. ��� : �־��� String���� delimeter�� ������ String[]�� ���Ѵ�
     * 2. ó�� ���� :
     *     - �־��� String���� delimeter�� ������ String[]�� ���Ѵ�
     * 3. ���ǻ���
	 *
     * @param input - �Է½�Ʈ��
     * @param delimeter - delimeter
     * @return �����õ� String����ǹ迭
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
     * 1. ��� : �־��� String���� delimeter�� ������ XML Tag�� �߰��Ѵ�.
     * 2. ó�� ���� :
     *     - �־��� String���� delimeter�� ������ tag Prefix�� �������� ���Ͽ� XML ������ String�� ���Ѵ�.
     * 3. ���ǻ���
	 *
     * @param input - �Է½�Ʈ��
     * @param delimeter - delimeter
     * @param tagPrefix - tag Prefix
     * @return XML���·� �����õ� String���
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
     * 1. ��� : �־��� byte[]���� delimeter�� ������ XML Tag�� �߰��Ѵ�.
     * 2. ó�� ���� :
     *     - �־��� byte[]���� delimeter�� ������ tag Prefix�� �������� ���Ͽ� XML ������ byte[]�� ���Ѵ�.
     * 3. ���ǻ���
	 *
     * @param input - �Է� byte[]
     * @param delimeter - delimeter
     * @param tagPrefix - tag Prefix
     * @return XML���·� �����õ� byte[]���
     */
    public static byte[] delimeterToXmlBytes(byte[] input, String delimeter, String tagPrefix) {
        if(input == null) return new byte[0];
        String xmlStr = delimeterToXml(new String(input), delimeter, tagPrefix);
        return xmlStr.getBytes();
    }

}
