/**
* 1. 기능 : Property Rule 정보를 DB로 부터 로딩해 메모리에 관리하는 Manager 클래스
* 2. 처리 개요 : DB Rule 정보에 저장된 Property Rule 정보를 메모리에 로딩 관리한다.
* *     -
* 3. 주의사항
*
* @author  :
* @version : v 1.0.0
* @see : 관련 기능을 참조
* @since   :
* @deprecated :
*/
package com.slug.config;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;






import com.slug.lifecycle.Lifecycle;
import com.slug.lifecycle.LifecycleException;
import com.slug.lifecycle.LifecycleListener;
import com.slug.lifecycle.LifecycleSupport;
import com.slug.log4j.log4jHandler;
import com.slug.logging.Logging;



public class PropManager implements Lifecycle
{

//	private static Logger logger = log4jHandler.getLogger("PropManager.class");

    /**
     * PropManager Single Instance
     */
    private static PropManager instance = new PropManager();

    /**
     * LifecyleSupport object
     */
    private LifecycleSupport lifecycle = new LifecycleSupport(this);

    /**
     * PropertyChangeSupport object
     */
	private PropertyChangeSupport property = new PropertyChangeSupport(this);


    /**
     *  Property 정보를 저장하는 collection
     */
    private HashMap groups;

	/**
     * 기동 여부
     */
    private boolean started;

    /**
    * 1. 기능 :  Default Constructor
    * 2. 처리 개요 :
    *     -
    * 3. 주의사항
    *
    **/
    private PropManager() {
    }

    /**
    * 1. 기능 :  PropManager Singleton Object를 반환하는 getter method
    * 2. 처리 개요 : PropManager Singleton Object를 반환한다.
    *     -
    * 3. 주의사항
    *
    * @param
    * @return
    * @exception
    **/
    public static PropManager getInstance() {
        return instance;
    }

    // =================================================================================
    // 이 부분은 PorpertyChangeListener용으로 제공되는 메소드이다.

    /**
    * 1. 기능 :  PropertyChangeListener를 등록하는 메서드
    * 2. 처리 개요 : PropertyChangeListener를 등록하는 메서드
    *     -
    * 3. 주의사항
    *
    * @param listener PropertyChangeListener
    * @exception
    **/
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        property.addPropertyChangeListener(listener);
    }

    /**
    * 1. 기능 :  PropertyChangeListener를 삭제하는 메서드
    * 2. 처리 개요 : PropertyChangeListener를 삭제하는 메서드
    *     -
    * 3. 주의사항
    *
    * @param listener PropertyChangeListener
    * @exception
    **/
    public void removePropertyChangeListener(PropertyChangeListener listener) {
        property.removePropertyChangeListener(listener);
    }

    /**
     * 1. 기능 :  PropertyChangeEvent를 생성하는 메소드
     * 2. 처리 개요 : PropertyChangeEvent를 생성하는 메소드
     *     -
     * 3. 주의사항
     *
     * @param source		PropGroupVO
     * @param propertyName  프라퍼티명
     * @param oldValue		변경 이전값
     * @param newValue		변경 값
     * @return PropertyChangeEvent
     * @exception
     **/
    private PropertyChangeEvent createEvent(PropGroupVO source, String propertyName, Object oldValue, Object newValue) {
        return new PropertyChangeEvent(source, propertyName, oldValue, newValue);
    }

    /**
    * 1. 기능 :  PropManager의 초기화 여부를 반환하는 getter 메서드
    * 2. 처리 개요 : PropManager의 초기화 여부를 반환한다.
    * 3. 주의사항
    *
    * @return   초기화 여부
    **/
    public boolean isStarted() {
        return this.started;
    }


    // =================================================================================
    // 이 부분은 Lifecycle용으로 제공되는 메소드이다.
    /**
    * 1. 기능 :  Lifecycle의 start 메서드로 PropManager를 초기화하는 메서드
    * 2. 처리 개요 : PropertyDAO를 이용해 추출 Rule 정보 모두를 가져와 초기화한다.
    *     -
    * 3. 주의사항
    *
    * @param
    * @return
    * @exception
    **/
    public void start() throws LifecycleException {
        if (started)
            throw new LifecycleException("RECEAICPM201");

        // Notify our interested LifecycleListeners
        lifecycle.fireLifecycleEvent(STARTING_EVENT, null);
/*
        PropertyDAO dao = null;
        try {
            DAOFactory factory = DAOFactory.newInstance();
            dao = (PropertyDAO)factory.create(PropertyDAO.class);
           this.groups = dao.getAllProperties();
        } catch(DAOException e) {
            //throw new LifecycleException("RECEAICPM201");
            throw new LifecycleException(ExceptionUtil.getErrorCode(e,"RECEAICPM201"));
        }
*/
        started = true;

        // Notify our interested LifecycleListeners
        lifecycle.fireLifecycleEvent(STARTED_EVENT, null);
    }

    // =================================================================================
    // 이 부분은 Lifecycle용으로 제공되는 메소드이다.
    /**
    * 1. 기능 :  Lifecycle의 start 메서드로 PropManager를 초기화하는 메서드
    * 2. 처리 개요 : PropertyDAO를 이용해 추출 Rule 정보 모두를 가져와 초기화한다.
    *     -
    * 3. 주의사항
    *
    * @param
    * @return
    * @exception
    **/
    public void start(String prop_file) throws LifecycleException {
        if (started)
            throw new LifecycleException("RECEAICPM201");

        // Notify our interested LifecycleListeners
        lifecycle.fireLifecycleEvent(STARTING_EVENT, null);
/*
        PropertyDAO dao = null;
        try {
            DAOFactory factory = DAOFactory.newInstance();
            dao = (PropertyDAO)factory.create(PropertyDAO.class);
           this.groups = dao.getAllProperties();
        } catch(DAOException e) {
            //throw new LifecycleException("RECEAICPM201");
            throw new LifecycleException(ExceptionUtil.getErrorCode(e,"RECEAICPM201"));
        }
*/
        started = true;

        // Notify our interested LifecycleListeners
        lifecycle.fireLifecycleEvent(STARTED_EVENT, null);
    }

    /**
    * 1. 기능 :  Lifecycle의 stop 메서드로 PropManager를 종료하는 메서드
    * 2. 처리 개요 : 멤버에 캐싱한 프라퍼티 Rule 정보를 clear한다.
    *     -
    * 3. 주의사항
    *
    * @param
    * @return
    * @exception
    **/
    public void stop() throws LifecycleException {
        // Validate and update our current component state
        if (!started)
            throw new LifecycleException("RECEAICPM203");

        // Notify our interested LifecycleListeners
        lifecycle.fireLifecycleEvent(STOPING_EVENT, null);



        started = false;
        Logging.debug.println("PropManager] It is stopped.");
        // Notify our interested LifecycleListeners
        lifecycle.fireLifecycleEvent(STOPPED_EVENT, null);
    }

    /**
    * 1. 기능 :  LifecycleListener를 등록하는 메서드
    * 2. 처리 개요 : LifecycleListener를 등록한다.
    * 3. 주의사항
    *
    * @param    listener    LifecycleEvent를 수신한 LifecycleListener
    **/
    public void addLifecycleListener(LifecycleListener listener)
    {
        lifecycle.addLifecycleListener(listener);
    }

    /**
    * 1. 기능 :  등록된 LifecycleListener 리스트를 반환하는 메서드
    * 2. 처리 개요 : 등록된 LifecycleListener 리스트를 반환하다.
    * 3. 주의사항
    *
    * @return   등록된 LifecycleListener 리스트
    **/
    public LifecycleListener[] findLifecycleListeners()
    {
        return lifecycle.findLifecycleListeners();
    }

    /**
    * 1. 기능 :  등록된 LifecycleListener를 삭제하는 메서드
    * 2. 처리 개요 : 파라미터의 LifecycleListener를 삭제한다.
    * 3. 주의사항
    *
    * @param    listener    삭제할 LifecycleListener
    **/
    public void removeLifecycleListener(LifecycleListener listener)
    {
        lifecycle.removeLifecycleListener(listener);
    }

    /**
    * 1. 기능 :  Property 그룹정보를 등록하는 메서드
    * 2. 처리 개요 : Property 그룹정보를 등록하는 메서드.
    *     -
    * 3. 주의사항
    *
    * @param    name    프라퍼티 그룹명
    * @param    description    프라퍼티 그룹설명
    **/
    public void addPropGroupVO(String name, String description) {
        groups.put(name, new PropGroupVO(name,description));
    }

    /**
    * 1. 기능 :  EAI 서비스 코드에 대한 Property 정보를 반환하는 메서드
    * 2. 처리 개요 : 파라미터의 프라퍼티 그룹명에 대한 Property 정보를 반환한다.
    *     -
    * 3. 주의사항
    *
    * @param    name    프라퍼티 그룹명
    * @return   Property Rule 정보
    **/
    public PropGroupVO getPropGroupVO(String name) {
        return (PropGroupVO)groups.get(name);
    }

    /**
    * 1. 기능 :  프라퍼티 키에 대한 Property 값를 반환하는 메서드
    * 2. 처리 개요 : 파라미터의 프라퍼티 키에 대한 Property 값를 반환한다.
    *     -
    * 3. 주의사항
    *
    * @param    key    프라퍼티 키
    * @return   Property 값
    **/
    public String getProperty(String key) {

        return getProperty(com.slug.config.Keys.DEFAULT_SERVER, key);
    }

	/**
    * 1. 기능 :  프라퍼티 키에 대한 Property 값를 반환하는 메서드
    * 2. 처리 개요 : 파라미터의 프라퍼티 키에 대한 Property 값를 반환한다.
    *     -
    * 3. 주의사항
    *
    * @param    group    프라퍼티 그룹명
    * @param    key    프라퍼티 키
    * @return   Property 값
    **/
    public String getProperty(String group, String key) {
        PropGroupVO vo = (PropGroupVO)groups.get(group);
        if(vo==null) return null;
        return vo.getProperty(key);
    }

	/**
    * 1. 기능 :  프라퍼티 그룹명에 대한 Property 리스트를 반환하는 메서드
    * 2. 처리 개요 :프라퍼티 그룹명에 대한 Property 리스트를 반환한다.
    *     -
    * 3. 주의사항
    *
    * @param    group    프라퍼티 그룹명
    * @return   Property 값
    **/
    public Properties getProperties(String group) {
        PropGroupVO vo = (PropGroupVO)groups.get(group);
        if(vo==null) throw new RuntimeException("Cannot find the Property Group in PorpManager. - "+group);
        return vo.getProperties();
    }

	/**
    * 1. 기능 :  프라퍼티 정보를 등록하는 메서드
    * 2. 처리 개요 : 프라퍼티 정보를 등록한다.
    *     -
    * 3. 주의사항
    *
    * @param    group    프라퍼티 그룹명
    * @param    key      프라퍼티 키
    * @param    value    프라퍼티 값
    **/
    public void setProperty(String group, String key, String value) {
        PropGroupVO gvo = (PropGroupVO)groups.get(group);
        String oldValue = gvo.getProperty(key);
        gvo.setProperty(key,value);
        // EVENET 발생!!
        if (!oldValue.equals(value)) {
            property.firePropertyChange(createEvent(gvo, key, oldValue, value));
        }
    }

	/**
    * 1. 기능 :  프라퍼티 정보를 등록하는 메서드
    * 2. 처리 개요 : 프라퍼티 정보를 등록한다.
    *     -
    * 3. 주의사항
    *
    * @param    group    프라퍼티 그룹명
    * @param    vo      PropGroupVO
    **/
    public void setPropGroupVO(String group, PropGroupVO vo){
        groups.put(group, vo);
    }

	/**
    * 1. 기능 :  프라퍼티 정보를 삭제하는 메서드
    * 2. 처리 개요 : 프라퍼티 정보를 삭제한다.
    *     -
    * 3. 주의사항
    *
    * @param    name    프라퍼티 그룹명
    * @param    vo      PropGroupVO
    **/
    public void removePropGroupVO(String name){
        groups.remove(name);
    }

	/**
    * 1. 기능 :  프라퍼티 그룹명 리스트를 반환하는 메서드
    * 2. 처리 개요 : 프라퍼티 그룹명 리스트를를 반환한다.
    *     -
    * 3. 주의사항
    *
    * @return String[] 프라퍼티 그룹명 리스트
    **/
    public String[] getAllPropGroupNames() {
        Iterator it = this.groups.keySet().iterator();
        String[] name = new String[this.groups.size()];
        for(int i=0;it.hasNext();i++) {
            name[i] = (String)it.next();
        }

        Arrays.sort(name);
        return name;
    }
}
