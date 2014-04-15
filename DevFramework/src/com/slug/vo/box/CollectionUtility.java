package com.slug.vo.box;

/**
 * @(#) CollectionUtility.java
 * @version KDSKIT
 * Copyright
 * All rights reserved.
 * �ۼ� :
 * @author �赿��, dongskim@solupia.co.kr
 *         SOLUPIA e-Biz Team
 *
 */

import java.util.Enumeration;
import javax.servlet.http.*;



/**
 * �� Class�� Servlet�� support�ϱ� ���� class�̴�.<br>
 * HttpServletRequest�� ���� request�� name�� value�� <br>
 * HashMap�� Box Object�� �־ ����Ѵ�.<br>
 * ���� String���� �Ǿ��ִ°����� Conversion�ؼ� ����ϴ°���<br>
 * �ƴϰ� �����ϰ� getInt()������ �����ü� �ְ� �Ѵ�.<br>
 * ��� static method�� �����Ǿ� �ִ�.<br>
 *
 */

public class CollectionUtility implements  java.io.Serializable  {

    /**
     * You can't call the constructor.
     */
    //private CollectionUtility() {}
    public CollectionUtility() {}

    /**
     * �Ķ���ͷ� HttpServletRequest�� �޾� FORM INPUT Data�� parsing�Ͽ�
     * Box��ü�� ��� return�Ѵ�.
     * getParameterValues() ����Ѵ�.
     *
     * @param req javax.servlet.http.HttpServletRequest
     * @return Box Input data
     */
    public static Box getBox(HttpServletRequest req)  {
        Box box = new Box("requestBox");

        Enumeration e = req.getParameterNames();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            box.put(key, req.getParameterValues(key));
           
            
        }
        return box;
    }
    /**
     * �Ķ���ͷ� HttpServletRequest�� �޾� FORM INPUT Data�� parsing�Ͽ�
     * Box��ü�� ��� return�Ѵ�.
     * getAttributeNames() ����Ѵ�.
     *
     * @param req javax.servlet.http.HttpServletRequest
     * @return Box attribute data
     */

    public static Box getAttributeBox(HttpServletRequest req)  {
        Box box = new Box("attributeBox");

        Enumeration e = req.getAttributeNames();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            box.put(key, req.getAttribute(key));
        }
        return box;
    }

    /**
     * �Ķ���ͷ� HttpServletRequest�� �޾� FORM INPUT Data�� parsing�Ͽ�
     * Box��ü�� ��� return�Ѵ�.
     * getParameterValues() ����Ѵ�.
     *
     * @param req javax.servlet.http.HttpServletRequest
     * @return Box Input data
     */
    public static Box getBoxWithSession(HttpServletRequest req)  {
        Box box = new Box("requestSessionBox");
		String key = "";

        Enumeration e = req.getParameterNames();
        while (e.hasMoreElements()) {
            key = (String) e.nextElement();
            box.put(key, req.getParameterValues(key));
        }

		HttpSession session = req.getSession(true);
		java.util.Enumeration en = session.getAttributeNames();
		while (en.hasMoreElements()) {
			key = (String) en.nextElement();
			Object o = session.getAttribute(key);
			Class  c = o.getClass();
			if(!c.isArray()){
				//Session�� ����ִ� WebLogic Server��ǰ�� Session ���� ����
				if(!key.startsWith("BEA")){
					box.put(key,o);
				}
			}
		}// end while
		
        // SSO ������ Login ���� �������� ��
		//box.put("USER",req.getHeader("SM_USER"));
		
        return box;
    }


    /**
     * �Ķ���ͷ� HttpServletRequest�� �޾� cookie�� �����ͼ�
     * Box��ü�� ��� return�Ѵ�.
     * getCookies() ����Ѵ�.
     *
     * @param req javax.servlet.http.HttpServletRequest
     * @return Box cookies
     */
    public static Box getBoxFromCookie(HttpServletRequest req)  {
        Box cookieBox = new Box("cookieBox");
        Cookie[] cookies = req.getCookies();
        if (cookies == null) {
            return cookieBox;
        }

        for(int i=0; cookies!=null && i<cookies.length; i++) {
            String key = cookies[i].getName();
            String value = cookies[i].getValue();
            if (value == null) {
                value = "";
            }
            String[] values = new String[1];
            values[0] = value;
            cookieBox.put(key,values);
        }
        return cookieBox;
    }
    /**
     * HttpServletRequest�� ��� SessionBox�� return�Ѵ�.
     * getNewSessionBox()�� ����Ѵ�.
     * @param req javax.servlet.http.HttpServletRequest
     * @return SessionBox
     */
    public static SessionBox getNewSessionBox(HttpServletRequest req)  {
        return getNewSessionBox(req, "shared sessionBox");
    }
    /**
     * HttpServletRequest�� ��� SessionBox�� return�Ѵ�.
     * session�� ���ϰ� ���� session�� parameter�� ���� name����
     * SessionBox�� Value�� �־��ش�.
     * @param req javax.servlet.http.HttpServletRequest
     * @param name attribute name
     * @return SessionBox
     */
    public static SessionBox getNewSessionBox(HttpServletRequest req, String name)  {
        HttpSession session = req.getSession(true);
        SessionBox sessionBox = null;

        if (!session.isNew()) {

            Object o = session.getAttribute(name);
            if (o != null) {
                session.removeAttribute(name);
            } //end if (o != null)
        } //end if (!session.isNew())

        sessionBox = new SessionBox(session, name);
        session.setAttribute(name, sessionBox);

        Enumeration e = req.getParameterNames();
        while(e.hasMoreElements()){
            String key = (String)e.nextElement();
            sessionBox.put(key, req.getParameterValues(key));
        }

        return sessionBox;
    }
    /**
     * HttpServletRequest�� ��� SessionBox�� return�Ѵ�.
     * getSessionBox()�� ����Ѵ�.
     * @param req javax.servlet.http.HttpServletRequest
     * @return SessionBox
     */
    public static SessionBox getSessionBox(HttpServletRequest req)  {
        return getSessionBox(req, "shared sessionBox");
    }
    /**
     * HttpServletRequest�� ��� SessionBox�� return�Ѵ�.
     * ��Ǿ��ִ� session�� parameter�� ���� name����
     * SessionBox�� Value�� �־��ش�.
     * @param req javax.servlet.http.HttpServletRequest
     * @param name attribute name
     * @return SessionBox
     */
    public static SessionBox getSessionBox(HttpServletRequest req, String name)  {
        HttpSession session = req.getSession(true);
        SessionBox sessionBox = null;

        Object o = session.getAttribute(name);
        if (o != null) {
            if (o instanceof SessionBox) {
                sessionBox = (SessionBox)o;
            } else {
                session.removeAttribute(name);
            } // end if (o instanceof SessionBox)
        } // end if (o != null)

        if (sessionBox == null) {
            sessionBox = new SessionBox(session, name);
            session.setAttribute(name, sessionBox);
        }

        Enumeration e = req.getParameterNames();
        while (e.hasMoreElements()) {
            String key = (String)e.nextElement();
            sessionBox.put(key, req.getParameterValues(key));
        }

        return sessionBox;
    }

    /**
     * �Ķ���ͷ� HttpServletRequest�� �޾� FORM INPUT Data�� parsing�Ͽ�
     * VectorBox��ü�� ��� return�Ѵ�.
     * getParameterValues() ����Ѵ�.
     *
     * @param req javax.servlet.http.HttpServletRequest
     * @return VectorBox Input multi-data
     */
    public static VectorBox getVectorBox(HttpServletRequest req)  {
        VectorBox vectorBox = new VectorBox("requestBox");

        Enumeration e = req.getParameterNames();
        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();
            vectorBox.put(key, req.getParameterValues(key));
        }
        return vectorBox;
    }

    /**
     * Client�� Microsoft Internet Explorer5.0�̻� ������
     * ��û�Ͽ����� Check�Ѵ�.
     * @param req HttpServletRequest
     * @return boolean
     */
    public static boolean isOverIE50(HttpServletRequest req) {
        String user_agent = (String) req.getHeader("user-agent");

        if (user_agent == null) {
            return false;
        }

        int index = user_agent.indexOf("MSIE");
        if (index == -1) {
            return false;
        }

        int version = 0;
        try {
            version = Integer.parseInt(user_agent.substring(index+5, index+5+1));
        } catch(Exception e){}

        if (version < 5) {
            return false;
        }

        return true;
    }

    /**
     * �Ķ���ͷ� x-www-form-urlencoded format�� s�� �޾�
     * original string���� decoding�Ѵ�.
     *
     * @param   s   an encoded <code>String</code> to be translated.
     * @return  the original <code>String</code>.
     * @see     java.net.URLEncoder#encode(java.lang.String)
     */
    /*
    public static String decode(String s) {

        String decodedString = null;

        try {
            decodedString = URLDecoder.decode(s);
        } catch(Exception ex) {
        }

        return decodedString;
    }
    */
    /**
     * �Ķ���ͷ� s�� �޾� x-www-form-urlencoded format���� encoding�Ѵ�.
     *
     * @param   s   <code>String</code> to be translated.
     * @return  the translated <code>String</code>.
     * @see     java.net.URLEncoder#encode(java.lang.String)
     */
    /*
    public static String encode(String s) {
        return URLEncoder.encode(s);
    }
    */
}
