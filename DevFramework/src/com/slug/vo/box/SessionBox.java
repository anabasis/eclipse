package com.slug.vo.box;

/**
 * @(#) SessionBox.java
 * @version KDSKIT
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
import javax.servlet.http.HttpSession;

/**
 * Stock�� ��ӹ��� Ŭ�����̴�. HttpRequest�κ��� Data�� Parsing�Ͽ�
 * HttpSession�� �����ϰ�, �� Data�� ���� �ڵ鸵�� �����ϵ���
 * ����� �����Ѵ�.
 * Client�� Session�� ���ϱ� ���Ͽ� �̿��Ѵ�.
 *
 */
public class SessionBox extends Box {

    private HttpSession session = null;

    /**
     * Stock�� ��ӹ޾Ƽ� �װ��� Constructor�� �̿��Ѵ�.
     * �߰��� HttpSession Instance�� ���Ͽ� Session������� ����Ѵ�.
     */
    public SessionBox (HttpSession session, String name) {
        super(name);
        this.session = session;
    }

    /**
     * SessionBox��ü�� ����� Data�� DeepCopy�ϱ� ���� Method.
     * @return Copy�� ��� SessionBox Instance�� return�Ѵ�.
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
     * ��� ��ü�� name�� return�Ѵ�.
     * @return �� ��ü�� name�� String���� return�Ѵ�.
     */
    public String getName() {
        return name;
    }
    /**
     * SessionBox�� key�� ����� value�� return�ϴ� �޼ҵ�
     * @param key java.lang.String
     * @return java.lang.Object
     */
    public Object getObject(String key) {
        return super.get((Object)key);
    }
    /**
     * SessionBox�� key�� object value�� �����ϴ� �޼ҵ�
     * @param key java.lang.String
     * @param object java.lang.Object
     */
    public void putObject(String key, Object object) {
        super.put((Object)key, object);
    }
    /**
     * SessionBox�� ����� key=value������ DATA��θ� String����
     * return�ϴ� Method
     * Stock.toString()��  ���.
     * @return java.lang.String
     */
    public String toString() {
        return "SessionBox" + super.toString();
    }

}
