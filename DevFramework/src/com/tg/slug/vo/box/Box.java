package com.tg.slug.vo.box;
/**
 * @(#) Box.java
 * @version 1.0 - 20040305
 * Copyright
 * All rights reserved.
 * �ۼ� :
 * @author �赿��, dongskim@solupia.co.kr
 *         SOLUPIA e-Biz Team
 *
 */

import java.util.HashMap;
import java.util.Set;
import java.util.Iterator;
import java.util.Vector;
import java.lang.reflect.Array;
import java.lang.reflect.Field;

import com.tg.slug.logging.Logging;

/**
 * �� Class�� HashMap�� extend�Ͽ� key/value pair�� �����ϴ� class�̴�.<br>
 * Object�� store, retrieve�� �����Ͽ� servlet request�� parsing�ϰų�, <br>
 * session�� �̿��� ��� ���Ǵ� class�̴�.<br>
 * HashMap�� �⺻����� ����Ͽ� Data�� �ڵ鸵�� �� �ִ� ����� �����Ѵ�.<br>
 * JFoundEntity Class�� �ڵ����� �� �� �� �ִ� ��ɵ� �����Ѵ�.<br>
 *
 */
public class Box extends java.util.HashMap implements java.io.Serializable {
    

    protected String name = null;

    /**
     * �Ķ���ͷ� name�� �޾� variable�� setting�Ѵ�.
     * @param name String
     */

    public Box(String name) {
        super();
        this.name = name;
    }

    /**
     * Box ��ü�� ������ ���ο� Box��ü�� ���Ͽ� return�Ѵ�.
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
     * Box��ü�� EntityClass �� ������� �Ҷ� �����ϴ�.
     * Box��ü�� Attribute name�� entity Ŭ������ �ʵ���� ������
     * �ʵ� type�� �´� Data�� ��� entity Ŭ������ �ʵ带 ä���.
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
    * ��޹��� key���� �ش�Ǵ� Value�� ���ڿ��� return�Ѵ�.
    * ���ο� getString()�� �̿��Ͽ� �����Ǿ� �ִ�.
    * @param key String
    * @return java.lang.String
    */
    public String get(String key) {
        return getString(key);
    }

    /**
    * ��޹��� key���� �ش�Ǵ� Value�� boolean����return�Ѵ�.
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
    * ��޹��� key���� �ش�Ǵ� Value�� double�� return�Ѵ�.
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
    * ��޹��� key���� �ش�Ǵ� Value�� float�� return�Ѵ�.
    * @param key String
    * @return float
    */
    public float getFloat(String key) {
        return (float)getDouble(key);
    }

    /**
    * ��޹��� key���� �ش�Ǵ� Value�� Int�� return�Ѵ�.
    * @param key String
    * @return int
    */
    public int getInt(String key) {
        double value = getDouble(key);
        return (int)value;
    }

    /**
    * ��޹��� key���� �ش�Ǵ� Value�� long���� return�Ѵ�.
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
    * ��޹��� key���� �ش�Ǵ� Value�� String���� return�Ѵ�.
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
    * ��޹��� key���� �ش�Ǵ� Value�� Vector type���� return�Ѵ�.
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
    * ��޹��� key���� value�� �����Ѵ�.
    * @param key String
    * @param value String
    */
    public void put(String key, String value) {
        super.put(key, value);
    }
    /**
    * ��޹��� Vector�� key���� Vector�� �����Ѵ�.
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
    * ��޹��� String������ ','���� �����Ѵ��� ����� ���ڿ��� ��ȯ�Ѵ�.
    * �ݾ������� DATAó���� �����ϴ�.
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
    * �ش� key���� value�� �����Ѵ�.
    * @param key String
    * @param value String
    */
    public void set(String key, String value) {
        super.put(key, value);
    }

    /**
    * �ش� key���� Vector�� �����Ѵ�.
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
    * HttpServletRequest�� request�� ���� HashMap�� �о
    * name�� value�� ���ڿ��� ����� return�Ѵ�.
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
     * Creation date: (2002-02-21 ���� 12:01:55)
     */
    public Box() {}

    /**
     * Insert the method's description here.
     * Creation date: (2002-04-24 ���� 2:15:10)
     * @param size int
     */
    public Box(int size) {
        super(size);
    }

    /**
     * Insert the method's description here.
     * Creation date: (2002-04-24 ���� 1:16:25)
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
