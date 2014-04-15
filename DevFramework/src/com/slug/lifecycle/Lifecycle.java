/** 
* 1. 기능 : 어플리케이션 기동/종료에 따른 비즈니스 로직 처리를 위해 정의한 인터페이스
* 2. 처리 개요 : 어플리케이션 기동/종료 및 이벤트 처리를 위한 메서드를 정의한다.
* *     - 
* 3. 주의사항 
*
* @author  : 
* @version : v 1.0.0
* @see : 관련 기능을 참조
* @since   : 
* @deprecated : 
*/
package com.slug.lifecycle;

public interface Lifecycle {

    /**
     * The LifecycleEvent type for the "component start" event.
     */
    public static final String STARTING_EVENT = "starting";


    /**
     * The LifecycleEvent type for the "component before start" event.
     */
    public static final String STARTED_EVENT = "started";


    /**
     * The LifecycleEvent type for the "component stop" event.
     */
    public static final String STOPING_EVENT = "stoping";


    
    /**
     * The LifecycleEvent type for the "component after stop" event.
     */
    public static final String STOPPED_EVENT = "stopped";


   

    /**
     * 1. 기능 : Add a LifecycleEvent listener to this component.
     *
     * @param listener The listener to add
     */
    public void addLifecycleListener(LifecycleListener listener);


    /**
     * 1. 기능 : Get the lifecycle listeners associated with this lifecycle. If this 
     * Lifecycle has no listeners registered, a zero-length array is returned.
     */
    public LifecycleListener[] findLifecycleListeners();


    /**
     * 1. 기능 : Remove a LifecycleEvent listener from this component.
     *
     * @param listener The listener to remove
     */
    public void removeLifecycleListener(LifecycleListener listener);


    /**
     * 1. 기능 : Prepare for the beginning of active use of the public methods of this
     * component.  This method should be called before any of the public
     * methods of this component are utilized.  It should also send a
     * LifecycleEvent of type START_EVENT to any registered listeners.
     *
     * @exception LifecycleException if this component detects a fatal error
     *  that prevents this component from being used
     */
    public void start(String prop_file) throws LifecycleException;


    /**
     * 1. 기능 : Gracefully terminate the active use of the public methods of this
     * component.  This method should be the last one called on a given
     * instance of this component.  It should also send a LifecycleEvent
     * of type STOP_EVENT to any registered listeners.
     *
     * @exception LifecycleException if this component detects a fatal error
     *  that needs to be reported
     */
    public void stop() throws LifecycleException;
    
    
    /** 
    * 1. 기능 :  기동 여부를 반환하느 메서드
    * 2. 처리 개요 : 기동 여부 변수를 반환한다.
    *     - 
    * 3. 주의사항 
    *
    * @return   기동 여부
    **/
    public boolean isStarted();

}
