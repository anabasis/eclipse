package com.slug.vo.box;

/**
 * @(#) SessionBox.java
 * @version
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
import javax.servlet.http.HttpSession;

/**
 * Stock를 상속받은 클래스이다. HttpRequest로부터 Data를 Parsing하여
 * HttpSession에 저장하고, 이 Data에 대한 핸들링이 가능하도록
 * 기능을 제공한다.
 * Client의 Session을 관리하기 위하여 이용한다.
 *
 */
public class SessionBox extends Box {

    private HttpSession session = null;

    /**
     * Stock를 상속받아서 그것의 Constructor를 이용한다.
     * 추가로 HttpSession Instance를 생성하여 Session정보관리로 사용한다.
     */
    public SessionBox (HttpSession session, String name) {
        super(name);
        this.session = session;
    }

    /**
     * SessionBox객체에 저장된 Data를 DeepCopy하기 위한 Method.
     * @return Copy로 생성된 SessionBox Instance를 return한다.
     */
    public Object clone() {
        SessionBox sessionBox = new SessionBox(session, name);

        HashMap src = (HashMap)this;
        HashMap target = (HashMap)sessionBox;

        Set set = src.keySet();
        Iterator e = set.iterator();

        while(e.hasNext()) {
            String key = (String) e.next();
            Object value =  src.get(key);
            target.put(key,value);
        }

        return sessionBox;
    }
    /**
     * 생셩 객체의 name을 return한다.
     * @return 생성 객체의 name을 String으로 return한다.
     */
    public String getName() {
        return name;
    }
    /**
     * SessionBox에 key로 저장된 value를 return하는 메소드
     * @param key java.lang.String
     * @return java.lang.Object
     */
    public Object getObject(String key) {
        return super.get((Object)key);
    }
    /**
     * SessionBox에 key로 object value를 저장하는 메소드
     * @param key java.lang.String
     * @param object java.lang.Object
     */
    public void putObject(String key, Object object) {
        super.put((Object)key, object);
    }
    /**
     * SessionBox에 저장된 key=value형태의 DATA모두를 String으로
     * return하는 Method
     * Stock.toString()을  사용.
     * @return java.lang.String
     */
    public String toString() {
        return "SessionBox" + super.toString();
    }

}
