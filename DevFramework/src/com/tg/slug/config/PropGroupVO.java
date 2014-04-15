/**
* 1. 기능 : 프라퍼티 정보를 를 정의한 Java Value Object 클래스
* 2. 처리 개요 : 프라퍼티 정보를 정의한다.
* *     -
* 3. 주의사항
*
* @author  :
* @version : v 1.0.0
* @see : 관련 기능을 참조
* @since   :
* @deprecated :
*/
package com.tg.slug.config;

import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;

import com.tg.slug.util.StringUtil;


public class PropGroupVO implements Serializable
{
    private String name;                //프라퍼티 그룹명
    private String description;         //프라퍼티 그룹설명
    private Properties props;           //프라퍼티 명, 프라퍼티2값

    public PropGroupVO() {
        this("");
    }

    public PropGroupVO(String name) {
        this(name,"");
    }

    public PropGroupVO(String name, String description) {
        this.name = name;
        this.description = description;
        this.props = new Properties();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return StringUtil.nvl(this.description);
    }

    public String getProperty(String key) {
        return (String)props.get(key);
    }

    public String getProperty(String key, String defaultValue) {
        String value = (String)props.get(key);
        return (value==null) ? defaultValue : value;
    }

    public void addProperty(String key, String value) {
        props.put(key, value);
    }

    public void setProperty(String key, String value) {
        props.put(key,value);
    }

    public void removeProperty(String key) {
        props.remove(key);
    }

    public String[] keys() {
        Iterator it = this.props.keySet().iterator();
        String[] propKeys = new String[this.props.size()];
        for(int i = 0; it.hasNext(); i++){
            propKeys[i] = (String)it.next();
        }
        Arrays.sort(propKeys);
        return propKeys;
    }

    public Properties getProperties() {
        return props;
    }

    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("PropGroupVO [ name=").append(name);
        sb.append(", description=").append(description);
        sb.append(", Properties=").append(props);
        sb.append(" ]");

        return sb.toString();
    }

    public void setProperties(Properties prop){
        this.props = prop;
    }

}
