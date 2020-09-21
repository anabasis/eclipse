package com.slug.vo.box;

/**
 * @(#) VectorBox.java
 * @version
 * Copyright
 * All rights reserved.
 * 작성 :
 * @author
 *         
 *
 */
import java.util.Vector;
import java.lang.reflect.Array;

/**
 * Stock의 Vector elements를 쉽게 조작할 수 있는 Method를 제공하는 클래스이다.
 *
 */
public class VectorBox extends Box implements java.io.Serializable  {
    /**
     * 파라미터로 name을 받아 variable에 setting한다.
	 * @param name String
	 */
	public VectorBox(String name) {
		super(name);
	}
    /**
    * 전달받은 key(Vector name)의 index에 해당되는 Value를 문자열로 return한다.
    * 내부에 getString()을 이용하여 구현되어 있다.
    * @param key Vector name
    * @param index Value index
    * @return java.lang.String
    */
	public String get(String key, int index) {
		return getString(key, index);
	}
    /**
    * 전달받은 key(Vector name)의 index에 해당되는 Value를 boolean로 return한다.
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
    * 전달받은 key(Vector name)의 index에 해당되는 Value를 double로 return한다.
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
    * 전달받은 key(Vector name)의 index에 해당되는 Value를 float로 return한다.
    * @param key Vector name
    * @param index Value index
    * @return float
    */
	public float getFloat(String key, int index) {
		return (float)getDouble(key, index);
	}
    /**
    * 전달받은 key(Vector name)의 index에 해당되는 Value를 int로 return한다.
    * @param key Vector name
    * @param index Value index
    * @return int
    */
	public int getInt(String key, int index) {
		double value = getDouble(key, index);
		return (int)value;
	}
    /**
    * 전달받은 key(Vector name)의 index에 해당되는 Value를 long로 return한다.
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
    * 전달받은 key(Vector name)의 index에 해당되는 Value를 문자열로 return한다.
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
    * 전달받은 key(Vector name)의 Vector size를 return한다.
    * @param key Vector name
    * @return int Vector size
    */
	public int size(String key) {
		return super.getVector(key).size();
	}

    /**
     * Insert the method's description here.
     * Creation date: (2002-02-21 오후 12:02:11)
     */
    public VectorBox() {}
}
