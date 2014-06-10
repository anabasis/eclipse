package com.slug.vo.box;
/**
 * @(#) Box.java
 * @version 1.0 - 20040305
 * Copyright
 * All rights reserved.
 * 작성 :
 * @author
 *         
 *
 */

import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;
import java.util.Vector;
import java.lang.reflect.Array;
import java.lang.reflect.Field;

import com.slug.logging.Logging;

/**
 * 이 Class는 HashMap을 extend하여 key/value pair를 저장하는 class이다.<br>
 * Object의 store, retrieve가 용이하여 servlet request를 parsing하거나, <br>
 * session을 이용할 경우 사용되는 class이다.<br>
 * HashMap의 기본기능을 비롯하여 Data를 핸들링할 수 있는 기능을 제공한다.<br>
 * JFoundEntity Class를 자동으로 생성 할 수 있는 기능도 제공한다.<br>
 *
 */
public class Box extends java.util.HashMap implements java.io.Serializable {
    

    protected String name = null;

    /**
     * 파라미터로 name을 받아 variable에 setting한다.
     * @param name String
     */

    public Box(String name) {
        super();
        this.name = name;
    }

    /**
     * Box 객체의 복사한 새로운 Box객체를 생성하여 return한다.
     * @return Box Instance
     */
    public Object clone() {

        Box newbox = new Box(name);

        HashMap src = (HashMap)this;
        HashMap target = (HashMap)newbox;

        Set set = src.keySet();
        Iterator e = set.iterator();

        while(e.hasNext()) {
            String key = (String) e.next();
            Object value =  src.get(key);
            target.put(key,value);
        }

        return newbox;
    }
    /**
     * Box객체로 EntityClass 를 만들고자 할때 유용하다.
     * Box객체의 Attribute name과 entity 클래스의 필드명이 같으면
     * 필드 type에 맞는 Data를 얻어 entity 클래스의 필드를 채운다.
     * @param entity JFoundEntity Object
     */
    public void copyToEntity(Object entity) {
        if ( entity == null ) {
            Logging.dev.println("(M) [Box.copyToEntity() Using ERROR] : Trying to copy from box to null entity class");
            throw new NullPointerException("(M) [Box.copyToEntity() Using ERROR] : Trying to copy from box to null entity class");
        }

        Class c = entity.getClass();
        Field [] field = c.getFields();
        for (int i=0 ; i<field.length; i++) {
            try {
                String fieldtype = field[i].getType().getName();
                String fieldname = field[i].getName();

                if ( containsKey( fieldname ) ) {
                    if (fieldtype.equals("java.lang.String")) {
                        field[i].set(entity, getString(fieldname));
                    } else if (fieldtype.equals("int")) {
                        field[i].setInt(entity, getInt(fieldname));
                    } else if (fieldtype.equals("double")) {
                        field[i].setDouble(entity, getDouble(fieldname));
                    } else if (fieldtype.equals("long")) {
                        field[i].setLong(entity, getLong(fieldname));
                    } else if (fieldtype.equals("float")) {
                        field[i].set(entity, new Float(getDouble(fieldname)));
                    } else if (fieldtype.equals("boolean")) {
                        field[i].setBoolean(entity, getBoolean(fieldname));
                    } //end if ( fieldtype.equals("java.lang.String"))
                } // end if ( containsKey( fieldname ) )

            } catch(Exception e){
                //Debug.warn.println(this, e.getMessage());
            }
        }
    }
    /**
    * 전달받은 key값에 해당되는 Value를 문자열로 return한다.
    * 내부에 getString()을 이용하여 구현되어 있다.
    * @param key String
    * @return java.lang.String
    */
    public String get(String key) {
        return getString(key);
    }

    /**
    * 전달받은 key값에 해당되는 Value를 boolean으로return한다.
    * @param key String
    * @return boolean
    */
    public boolean getBoolean(String key) {
        String value = getString(key);
        boolean isTrue = false;
        try {
            isTrue = (new Boolean(value)).booleanValue();
        } catch(Exception e){}

        return isTrue;
    }

    /**
    * 전달받은 key값에 해당되는 Value를 double로 return한다.
    * @param key String
    * @return double
    */
    public double getDouble(String key) {
        String value = removeComma(getString(key));
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
    * 전달받은 key값에 해당되는 Value를 float로 return한다.
    * @param key String
    * @return float
    */
    public float getFloat(String key) {
        return (float)getDouble(key);
    }

    /**
    * 전달받은 key값에 해당되는 Value를 Int로 return한다.
    * @param key String
    * @return int
    */
    public int getInt(String key) {
        double value = getDouble(key);
        return (int)value;
    }

    /**
    * 전달받은 key값에 해당되는 Value를 long으로 return한다.
    * @param key String
    * @return long
    */
    public long getLong(String key) {
        String value = removeComma(getString(key));
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
    * 전달받은 key값에 해당되는 Value를 String으로 return한다.
    * @param key String
    * @return java.lang.String
    */
    public String getString(String key) {
        String value = null;

        try {
            Object o = (Object)super.get(key);
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
    * 전달받은 key값에 해당되는 Value를 Vector type으로 return한다.
    * @param key String
    * @return java.util.Vector
    */
    public Vector getVector(String key) {
        Vector vector = new Vector();

        try {
            Object o = (Object)super.get(key);
            Class c = o.getClass();
            if ( o != null ) {
                if( c.isArray() ) {
                    int length = Array.getLength(o);
                    if ( length != 0 ) {
                        for(int i=0; i<length;i++) {
                            Object item = Array.get(o, i);
                            if (item == null ) {
                                vector.addElement("");
                            } else {
                                vector.addElement(item.toString());
                            } // end if (item == null )
                        } // end for(int i=0; i<length;i++)
                    } // end if ( length != 0 )

                } else {
                    vector.addElement(o.toString());
                } // end if( c.isArray() )
            } // end if ( o != null )

        } catch(Exception e) {}

        return vector;
    }

    /**
    * 전달받은 key값과 value를 설정한다.
    * @param key String
    * @param value String
    */
    public void put(String key, String value) {
        super.put(key, value);
    }
    /**
    * 전달받은 Vector의 key값과 Vector를 설정한다.
    * @param key java.lang.String
    * @param vector java.util.Vector
    */
    public void putVector(String key, Vector vector) {

            String value[] = new String[vector.size()];
            for(int idx=0; idx<vector.size(); idx++)
            value[idx] = (vector.get(idx) == null ) ? "" : vector.get(idx).toString();
            put(key, value);
    }

    /**
    * 전달받은 String값에서 ','값을 제거한다음 변경된 문자열을 반환한다.
    * 금액형태의 DATA처리시 유용하다.
    * @param s String
    * @return java.lang.String
    */
    protected static String removeComma(String s) {
        if ( s == null ) {
            return null;
        }
        if ( s.indexOf(",") != -1 ) {
            StringBuffer buf = new StringBuffer();
            for(int i=0;i<s.length();i++) {
                char c = s.charAt(i);
                if ( c != ',') {
                    buf.append(c);
                } // end if ( c != ',')
            }// end for(int i=0;i<s.length();i++)

            return buf.toString();
        } // end if ( s.indexOf(",") != -1 )

        return s;
    }

    /**
    * 해당 key값에 value를 설정한다.
    * @param key String
    * @param value String
    */
    public void set(String key, String value) {
        super.put(key, value);
    }

    /**
    * 해당 key값에 Vector를 설정한다.
    * @param key java.lang.String
    * @param vector java.util.Vector
    */
    public void setVector(String key, Vector vector) {
        String value[] = new String[vector.size()];
        for(int idx=0; idx<vector.size(); idx++)
        value[idx] = (vector.get(idx) == null ) ? "" : vector.get(idx).toString();
        put(key, value);
    }

    /**
    * HttpServletRequest의 request로 만든 HashMap을 읽어서
    * name과 value로 문자열을 만들어 return한다.
    * @return java.lang.String
    */
    public synchronized String toString() {
        int max = size() - 1;
        StringBuffer buf = new StringBuffer();

        // key set
        Set kset = keySet();
        Iterator Keys = kset.iterator();

        // value set
        Set vset = entrySet();
        Iterator values = vset.iterator();

        buf.append("{");

        for (int i = 0; i <= max; i++) {
            String key = (String)Keys.next();
            String value = null;
            Object o = values.next();

            if (o == null) {
                value = "";

            } else {
                Class  c = o.getClass();
                if(c.isArray()) {
                    int length = Array.getLength(o);
                    if (length == 0) {
                        value = "";
                    } else if (length == 1) {
                        Object item = Array.get(o, 0);
                        if ( item == null ) {
                            value = "";
                        } else {
                            value = item.toString();
                        } // end if ( item == null )
                    } else {
                        StringBuffer valueBuf = new StringBuffer();
                        valueBuf.append("[");
                        for ( int j=0; j<length; j++) {
                            Object item = Array.get(o, j);
                            if ( item != null ) {
                                valueBuf.append(item.toString());
                            }
                            if ( j<length-1) {
                                valueBuf.append(",");
                            }
                        } // end for ( int j=0;j<length;j++)

                        valueBuf.append("]");
                        value = valueBuf.toString();
                    } // end if ( length == 0 )

                } else {
                    value = o.toString();
                } // end if( c.isArray() )
            } // end if ( o == null )

            buf.append(key + "=" + value);
            if (i < max) {
                buf.append(", ");
            }
        } // end for (int i = 0; i <= max; i++)
        buf.append("}");

        return "Box["+name+"]=" + buf.toString();
    }

    /**
     * Insert the method's description here.
     * Creation date: (2002-02-21 오후 12:01:55)
     */
    public Box() {}

    /**
     * Insert the method's description here.
     * Creation date: (2002-04-24 오후 2:15:10)
     * @param size int
     */
    public Box(int size) {
        super(size);
    }

    /**
     * Insert the method's description here.
     * Creation date: (2002-04-24 오후 1:16:25)
     * @return com.solupia.edu.jsp2Servlet.Box
     * @param newBox com.solupia.edu.jsp2Servlet.Box
     */
    public Box getBox(int index)  {

        Box box = new Box(20);

        Set set = super.keySet();
        Object[] keyArray = set.toArray();

        for(int inx = 0 ; inx < keyArray.length ; inx++){
            Object o = (Object)super.get(keyArray[inx]);
            Class c = o.getClass();

            if( c.isArray() ) {
                String[] value = (String[])o;
                box.put( keyArray[inx], value[index]);
            }
        }
        return box;

    }
}
