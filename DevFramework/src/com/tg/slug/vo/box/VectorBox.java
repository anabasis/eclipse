package com.tg.slug.vo.box;

/**
 * @(#) VectorBox.java
 * @version KDSKIT
 * Copyright
 * All rights reserved.
 * �ۼ� :
 * @author �赿��, dongskim@solupia.co.kr
 *         SOLUPIA e-Biz Team
 *
 */
import java.util.Vector;
import java.lang.reflect.Array;

/**
 * Stock�� Vector elements�� ���� ������ �� �ִ� Method�� �����ϴ� Ŭ�����̴�.
 *
 */
public class VectorBox extends Box implements java.io.Serializable  {
    /**
     * �Ķ���ͷ� name�� �޾� variable�� setting�Ѵ�.
	 * @param name String
	 */
	public VectorBox(String name) {
		super(name);
	}
    /**
    * ��޹��� key(Vector name)�� index�� �ش�Ǵ� Value�� ���ڿ��� return�Ѵ�.
    * ���ο� getString()�� �̿��Ͽ� �����Ǿ� �ִ�.
    * @param key Vector name
    * @param index Value index
    * @return java.lang.String
    */
	public String get(String key, int index) {
		return getString(key, index);
	}
    /**
    * ��޹��� key(Vector name)�� index�� �ش�Ǵ� Value�� boolean�� return�Ѵ�.
    * @param key Vector name
    * @param index Value index
    * @return boolean
    */
	public boolean getBoolean(String key, int index) {
		String value = getString(key, index);
		boolean isTrue = false;
		try {
			isTrue = (new Boolean(value)).booleanValue();
		} catch(Exception e){}

		return isTrue;
	}
    /**
    * ��޹��� key(Vector name)�� index�� �ش�Ǵ� Value�� double�� return�Ѵ�.
    * @param key Vector name
    * @param index Value index
    * @return double
    */
	public double getDouble(String key, int index) {
		String value = removeComma(getString(key, index));
		if ( value.equals("") ) {
			return 0;
		}

		double num = 0;
		try {
			num = Double.valueOf(value).doubleValue();
		} catch(Exception e) {
			num = 0;
		}

		return num;
	}
    /**
    * ��޹��� key(Vector name)�� index�� �ش�Ǵ� Value�� float�� return�Ѵ�.
    * @param key Vector name
    * @param index Value index
    * @return float
    */
	public float getFloat(String key, int index) {
		return (float)getDouble(key, index);
	}
    /**
    * ��޹��� key(Vector name)�� index�� �ش�Ǵ� Value�� int�� return�Ѵ�.
    * @param key Vector name
    * @param index Value index
    * @return int
    */
	public int getInt(String key, int index) {
		double value = getDouble(key, index);
		return (int)value;
	}
    /**
    * ��޹��� key(Vector name)�� index�� �ش�Ǵ� Value�� long�� return�Ѵ�.
    * @param key Vector name
    * @param index Value index
    * @return long
    */
	public long getLong(String key, int index) {
		String value = removeComma(getString(key, index));
		if ( value.equals("") ) {
			return 0L;
		}

		long lvalue = 0L;
		try {
			lvalue = Long.valueOf(value).longValue();
		} catch(Exception e) {
			lvalue = 0L;
		}

		return lvalue;
	}
    /**
    * ��޹��� key(Vector name)�� index�� �ش�Ǵ� Value�� ���ڿ��� return�Ѵ�.
    * @param key Vector name
    * @param index Value index
    * @return java.lang.String
    */
	public String getString(String key, int index) {
		String value = null;
		Vector v = super.getVector(key);

		try {
			Object o = (Object)v.elementAt(index);
			Class c = o.getClass();

			if ( o == null ) {
				value = "";
			} else if( c.isArray() ) {
				int length = Array.getLength(o);
				if ( length == 0 ) {
					value = "";
				} else {
					Object item = Array.get(o, 0);
					if ( item == null ) {
						value = "";
					} else {
						value = item.toString();
					} // end if ( item == null )
				} // end if ( length == 0 )
			} else {
				value = o.toString();
			} // end if ( o == null )

		} catch(Exception e) {
			value = "";
		}

		return value;
	}
    /**
    * ��޹��� key(Vector name)�� Vector size�� return�Ѵ�.
    * @param key Vector name
    * @return int Vector size
    */
	public int size(String key) {
		return super.getVector(key).size();
	}

    /**
     * Insert the method's description here.
     * Creation date: (2002-02-21 ���� 12:02:11)
     */
    public VectorBox() {}
}
