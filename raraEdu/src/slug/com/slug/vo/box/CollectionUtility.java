package com.slug.vo.box;

/**
 * @(#) CollectionUtility.java
 * @version
 * Copyright
 * All rights reserved.
 * 작성 :
 * @author
 *         
 *
 */

import java.util.Enumeration;
import javax.servlet.http.*;



/**
 * 이 Class는 Servlet을 support하기 위한 class이다.<br>
 * HttpServletRequest로 받은 request의 name과 value를 <br>
 * HashMap에 Box Object로 넣어서 사용한다.<br>
 * 사용시 String으로 되어있는값들을 Conversion해서 사용하는것이<br>
 * 아니고 간단하게 getInt()식으로 가져올수 있게 한다.<br>
 * 모두 static method로 구성되어 있다.<br>
 *
 */

public class CollectionUtility implements  java.io.Serializable  {

    /**
     * You can't call the constructor.
     */
    //private CollectionUtility() {}
    public CollectionUtility() {}

    /**
     * 파라미터로 HttpServletRequest를 받아 FORM INPUT Data를 parsing하여
     * Box객체에 담아 return한다.
     * getParameterValues() 사용한다.
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
     * 파라미터로 HttpServletRequest를 받아 FORM INPUT Data를 parsing하여
     * Box객체에 담아 return한다.
     * getAttributeNames() 사용한다.
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
     * 파라미터로 HttpServletRequest를 받아 FORM INPUT Data를 parsing하여
     * Box객체에 담아 return한다.
     * getParameterValues() 사용한다.
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
				//Session에 담겨있는 WebLogic Server제품의 Session 정보 제외
				if(!key.startsWith("BEA")){
					box.put(key,o);
				}
			}
		}// end while
		
        // SSO 연동시 Login 정보 가져오는 부
		//box.put("USER",req.getHeader("SM_USER"));
		
        return box;
    }


    /**
     * 파라미터로 HttpServletRequest를 받아 cookie를 가져와서
     * Box객체에 담아 return한다.
     * getCookies() 사용한다.
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
     * HttpServletRequest로 생성된 SessionBox를 return한다.
     * getNewSessionBox()를 사용한다.
     * @param req javax.servlet.http.HttpServletRequest
     * @return SessionBox
     */
    public static SessionBox getNewSessionBox(HttpServletRequest req)  {
        return getNewSessionBox(req, "shared sessionBox");
    }
    /**
     * HttpServletRequest로 생성된 SessionBox를 return한다.
     * session을 생성하고 생성한 session에 parameter로 받은 name으로
     * SessionBox를 Value로 넣어준다.
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
     * HttpServletRequest로 생성된 SessionBox를 return한다.
     * getSessionBox()를 사용한다.
     * @param req javax.servlet.http.HttpServletRequest
     * @return SessionBox
     */
    public static SessionBox getSessionBox(HttpServletRequest req)  {
        return getSessionBox(req, "shared sessionBox");
    }
    /**
     * HttpServletRequest로 생성된 SessionBox를 return한다.
     * 생성되어있는 session에 parameter로 받은 name으로
     * SessionBox를 Value로 넣어준다.
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
     * 파라미터로 HttpServletRequest를 받아 FORM INPUT Data를 parsing하여
     * VectorBox객체에 담아 return한다.
     * getParameterValues() 사용한다.
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
     * Client가 Microsoft Internet Explorer5.0이상 브라우저에서
     * 요청하였는지 Check한다.
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
     * 파라미터로 x-www-form-urlencoded format의 s를 받아
     * original string으로 decoding한다.
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
     * 파라미터로 s를 받아 x-www-form-urlencoded format으로 encoding한다.
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
