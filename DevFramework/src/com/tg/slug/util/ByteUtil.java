/*
 * @(#)ByteUtil.java	1.0 04/02/09
 *
 * Copyright (C) The SKC&C. All rights reserved.
 *
 * This software is published under the terms of the SKC&C Software
 * License version 0.0a, a copy of which has been included with this
 * distribution in the LICENSE.APL file.
 */


package com.tg.slug.util;

import com.tg.slug.util.StringUtil;
/**
* <PRE>
* Filename  : ByteUtil.java<br>
* Class     : ByteUtil    <br>
* Function  : Base64 Encoding/Decoding�� �����ϴ� Ŭ���� <br>
* Comment   : <br>
* History	: 2004-02-21 2:48����                 	<br>
* </PRE>
* @version  1.0
* @author carouser
*/
public class ByteUtil {

	// data�� offset������ ��� �����͸�  split �Ͽ� ��ȯ�Ѵ�.
	public static String splitString(String data, int offset) throws Exception{
		int length = data.length() - offset;
		return splitString(data, offset, length);
	}

	/**
	*	�Է¹��� data�� offset, length�� �̿��Ͽ� ���ڿ��� ������ �κ��� charset�� ���Ͽ� �и��ϵ��� �Ѵ�.
	*	@param data �� ���ڿ�
	*	@param offset ������ġ
	*	@param length ����
	*	@return �߸� ���ڿ� ������
	*/
	public static String splitString(String data, int offset, int length)  throws Exception {
		return splitString(data, offset, length, "EUC-KR");
	}

	/**
	*	�Է¹��� data�� offset, length�� �̿��Ͽ� ���ڿ��� ������ �κ��� charset�� ���Ͽ� �и��ϵ��� �Ѵ�.
	*	@param data �� ���ڿ�
	*	@param offset ������ġ
	*	@param length ����
	*	@return �߸� ���ڿ� ������
	*/
	public static String splitString(String data, int offset, int length, String charset) throws Exception{
		byte [] b = null;
		byte [] ret = null;
		try{
			if (charset == null) {
                if(OSUtil.getOSType() ==1) //������
                    charset = "KSC5601";
                else
                    charset = "8859_1";
			}
			b = data.getBytes(charset);
			ret = new byte[length];

			// ���� ���̰� �ڸ��� ���� ���̺��� Ŭ��� exception���� ���� ����(4��29�� ������)
			if( b.length < length) {
				b = new byte[length];
				try{
					System.arraycopy(data.getBytes(charset), offset, b, 0, (data.getBytes(charset)).length);
				}catch(ArrayIndexOutOfBoundsException e) {
					//return StringUtil.lpad(" ", length);
                    return null;
				}
			} else if( b.length <= offset ) { // �����ͱ��̺��� �ڸ����� offset�� Ŀ������ ���� �����͸� �����ŭ�� ������� ä���.
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
	*	�Է¹��� data�� offset, length�� �̿��Ͽ� ���ڿ��� ������ �κ��� charset�� ���Ͽ� �и��ϵ��� �Ѵ�.
	*	@param data �� ���ڿ�
	*	@param offset ������ġ
	*	@param length ����
	*	@return �߸� ���ڿ� ������
	*/
	public static String makeString(String data, int offset, int length)  throws Exception {
		return makeString(data, offset, length, "EUC-KR");
	}

	/**
	*	�Է¹��� data�� offset, length�� �̿��Ͽ� ���ڿ��� ������ �κ��� charset�� ���Ͽ� �и��ϵ��� �Ѵ�.
	*	@param data �� ���ڿ�
	*	@param offset ������ġ
	*	@param length ����
	*	@return �߸� ���ڿ� ������
	*/
	public static String makeString(String data, int offset, int length, String charset) throws Exception{
		byte [] b = null;
		byte [] ret = null;
		String result = "";
		try{
			if (charset == null) {
				charset = "EUC-KR";
			}
			b = data.getBytes();  // ���� 12
			ret = new byte[length];		// ���� 13

			if( b.length < length) {
				int space = length - b.length;
				System.arraycopy(data.getBytes(), offset, b, 0, (data.getBytes()).length);
				result = new String(b);
				result = result + StringUtil.lpad(" ", space);
				return result;
			} else if( b.length <= offset ) { // �����ͱ��̺��� �ڸ����� offset�� Ŀ������ ���� �����͸� �����ŭ�� ������� ä���.
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
     * 1.���: OS�� ��� ���ڵ��� ��ȯ�Ͽ� String�� ���� byte[]�� ��ȯ�Ѵ�.
     */
    public static byte[] getBytes(String str) throws Exception
    {
        if(OSUtil.getOSType() ==1)  //������
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

			b = data.getBytes();  // ���� 12
			ret = new byte[length];		// ���� 13

			if( b.length < length) {
				int space = length - b.length;
				System.arraycopy(data.getBytes(), offset, b, 0, (data.getBytes()).length);
				result = new String(b);
				result = result + StringUtil.rpad("", space, filler);
				return result;
			} else if( b.length <= offset ) { // �����ͱ��̺��� �ڸ����� offset�� Ŀ������ ���� �����͸� �����ŭ�� ������� ä���.
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

			b = data.getBytes();  // ���� 12
			ret = new byte[length];		// ���� 13

			if( b.length < length) {
				int space = length - b.length;
				System.arraycopy(data.getBytes(), offset, b, 0, (data.getBytes()).length);
				result = new String(b);
				result = StringUtil.lpad("", space, filler) + result;
				return result;
			} else if( b.length <= offset ) { // �����ͱ��̺��� �ڸ����� offset�� Ŀ������ ���� �����͸� �����ŭ�� ������� ä���.
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