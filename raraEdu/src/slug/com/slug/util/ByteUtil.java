/*
 * @(#)ByteUtil.java	1.0 04/02/09
 *
 * Copyright (C) The SKC&C. All rights reserved.
 *
 * This software is published under the terms of the SKC&C Software
 * License version 0.0a, a copy of which has been included with this
 * distribution in the LICENSE.APL file.
 */


package com.slug.util;

import com.slug.util.StringUtil;
/**
* <PRE>
* Filename  : ByteUtil.java<br>
* Class     : ByteUtil    <br>
* Function  : Base64 Encoding/Decoding을 수행하는 클래스 <br>
* Comment   : <br>
* History	: 2004-02-21 2:48오후                 	<br>
* </PRE>
* @version  1.0
* @author carouser
*/
public class ByteUtil {

	// data의 offset이후의 모든 데이터를  split 하여 반환한다.
	public static String splitString(String data, int offset) throws Exception{
		int length = data.length() - offset;
		return splitString(data, offset, length);
	}

	/**
	*	입력받은 data와 offset, length를 이용하여 문자열의 일정한 부분을 charset에 의하여 분리하도록 한다.
	*	@param data 원본 문자열
	*	@param offset 기준위치
	*	@param length 길이
	*	@return 잘린 문자열 데이터
	*/
	public static String splitString(String data, int offset, int length)  throws Exception {
		return splitString(data, offset, length, "EUC-KR");
	}

	/**
	*	입력받은 data와 offset, length를 이용하여 문자열의 일정한 부분을 charset에 의하여 분리하도록 한다.
	*	@param data 원본 문자열
	*	@param offset 기준위치
	*	@param length 길이
	*	@return 잘린 문자열 데이터
	*/
	public static String splitString(String data, int offset, int length, String charset) throws Exception{
		byte [] b = null;
		byte [] ret = null;
		try{
			if (charset == null) {
                if(OSUtil.getOSType() ==1) //윈도우
                    charset = "KSC5601";
                else
                    charset = "8859_1";
			}
			b = data.getBytes(charset);
			ret = new byte[length];

			// 읽은 길이가 자르고 싶은 길이보다 클경우 exception나는 것을 방지(4월29일 최지웅)
			if( b.length < length) {
				b = new byte[length];
				try{
					System.arraycopy(data.getBytes(charset), offset, b, 0, (data.getBytes(charset)).length);
				}catch(ArrayIndexOutOfBoundsException e) {
					//return StringUtil.lpad(" ", length);
                    return null;
				}
			} else if( b.length <= offset ) { // 데이터길이보다 자르려는 offset이 커버리면 뒤의 데이터를 사이즈만큼의 공백으로 채운다.
				return StringUtil.lpad(" ", length);
			}

			System.arraycopy(b,	offset,	ret, 0, length);

		}catch(Exception e) {
			return StringUtil.lpad(" ", length);
			//throw new Exception("EM101", ExceptionUtil.getTraceElement(e));
		}
		return new String(ret);
	}




	/**
	*	입력받은 data와 offset, length를 이용하여 문자열의 일정한 부분을 charset에 의하여 분리하도록 한다.
	*	@param data 원본 문자열
	*	@param offset 기준위치
	*	@param length 길이
	*	@return 잘린 문자열 데이터
	*/
	public static String makeString(String data, int offset, int length)  throws Exception {
		return makeString(data, offset, length, "EUC-KR");
	}

	/**
	*	입력받은 data와 offset, length를 이용하여 문자열의 일정한 부분을 charset에 의하여 분리하도록 한다.
	*	@param data 원본 문자열
	*	@param offset 기준위치
	*	@param length 길이
	*	@return 잘린 문자열 데이터
	*/
	public static String makeString(String data, int offset, int length, String charset) throws Exception{
		byte [] b = null;
		byte [] ret = null;
		String result = "";
		try{
			if (charset == null) {
				charset = "EUC-KR";
			}
			b = data.getBytes();  // 길이 12
			ret = new byte[length];		// 길이 13

			if( b.length < length) {
				int space = length - b.length;
				System.arraycopy(data.getBytes(), offset, b, 0, (data.getBytes()).length);
				result = new String(b);
				result = result + StringUtil.lpad(" ", space);
				return result;
			} else if( b.length <= offset ) { // 데이터길이보다 자르려는 offset이 커버리면 뒤의 데이터를 사이즈만큼의 공백으로 채운다.
				return StringUtil.lpad(" ", length);
			} else {
				System.arraycopy(b,offset,ret,0,length);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		result = new String(ret);
		return result;
	}

    /**
     * 1.기능: OS에 따라서 인코딩을 변환하여 String에 대한 byte[]를 반환한다.
     */
    public static byte[] getBytes(String str) throws Exception
    {
        if(OSUtil.getOSType() ==1)  //윈도우
        {
            return str.getBytes("KSC5601");
        } else {
            return str.getBytes("8859_1");
        }
    }


          public static String _rpad(String data, int length, char filler) {
        return _rpad(data, 0, length, filler, null);
    }

    public static String _rpad(String data, int offset, int length, char filler, String charset) {
		byte [] b = null;
		byte [] ret = null;
		String result = "";
		try{
			if (charset == null) {
				charset = "EUC-KR";
			}

            if(data == null || data.equals(null) || data.equals("") ){
                data = "";
            }

			b = data.getBytes();  // 길이 12
			ret = new byte[length];		// 길이 13

			if( b.length < length) {
				int space = length - b.length;
				System.arraycopy(data.getBytes(), offset, b, 0, (data.getBytes()).length);
				result = new String(b);
				result = result + StringUtil.rpad("", space, filler);
				return result;
			} else if( b.length <= offset ) { // 데이터길이보다 자르려는 offset이 커버리면 뒤의 데이터를 사이즈만큼의 공백으로 채운다.
				return StringUtil.rpad("", length, filler);
			} else {
				System.arraycopy(	b,
                                    offset,
                                    ret,
                                    0,
                                    length);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		result = new String(ret);
		return result;
	}


    public static String _lpad(String data, int length, char filler) {
        return _lpad(data, 0, length, filler, null);
    }
    public static String _lpad(String data, int offset, int length, char filler, String charset) {
		byte [] b = null;
		byte [] ret = null;
		String result = "";
		try{
			if (charset == null) {
				charset = "EUC-KR";
			}

            if(data == null || data.equals(null) || data.equals("") ){
                data = "";
            }

			b = data.getBytes();  // 길이 12
			ret = new byte[length];		// 길이 13

			if( b.length < length) {
				int space = length - b.length;
				System.arraycopy(data.getBytes(), offset, b, 0, (data.getBytes()).length);
				result = new String(b);
				result = StringUtil.lpad("", space, filler) + result;
				return result;
			} else if( b.length <= offset ) { // 데이터길이보다 자르려는 offset이 커버리면 뒤의 데이터를 사이즈만큼의 공백으로 채운다.
				return StringUtil.lpad("", length, filler);
			} else {
				System.arraycopy(	b,
                                    offset,
                                    ret,
                                    0,
                                    length);
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		result = new String(ret);
		return result;
	}
}