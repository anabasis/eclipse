package com.slug.lifecycle;
/** 
* 1. 기능 : 
* 2. 처리 개요 : 
* *     - 
* 3. 주의사항 
*
* @author  : 
* @version : v 1.0.0
* @see : 관련 기능을 참조
* @since   : 
* @deprecated : 
*/
import java.io.Serializable;

/**
 * Support class to assist in firing LifecycleEvent notifications to
 * registered LifecycleListeners.
 *
 * @author Craig R. McClanahan
 * @version $Id: LifecycleSupport.java,v 1.1 2006/02/17 06:12:23 eai002 Exp $
 */

public final class LifecycleSupport implements Serializable {


    // ----------------------------------------------------------- Constructors


    /** 
    * 1. 기능 :  
    * 2. 처리 개요 : 
    *     - 
    * 3. 주의사항 
    *
    * @param 
    * @return 
    * @exception
    **/
    /**
     * Construct a new LifecycleSupport object associated with the specified
     * Lifecycle component.
     *
     * @param lifecycle The Lifecycle component that will be the source
     *  of events that we fire
     */
    public LifecycleSupport(Lifecycle lifecycle) {

        super();
        this.lifecycle = lifecycle;

    }


    // ----------------------------------------------------- Instance Variables


    /**
     * The source component for lifecycle events that we will fire.
     */
    private Lifecycle lifecycle = null;


    /**
     * The set of registered LifecycleListeners for event notifications.
     */
    private LifecycleListener listeners[] = new LifecycleListener[0];


    // --------------------------------------------------------- Public Methods


    /** 
    * 1. 기능 :  
    * 2. 처리 개요 : Add a lifecycle event listener to this component.
    *     - 
    * 3. 주의사항 
    *
    * @param listener The listener to add
     */
    public void addLifecycleListener(LifecycleListener listener) {

      synchronized (listeners) {
          LifecycleListener results[] = new LifecycleListener[listeners.length + 1];
          for (int i = 0; i < listeners.length; i++)
              results[i] = listeners[i];
          results[listeners.length] = listener;
          listeners = results;
      }

    }

    /** 
    * 1. 기능 :  
    * 2. 처리 개요 : Get the lifecycle listeners associated with this lifecycle. If this 
    * Lifecycle has no listeners registered, a zero-length array is returned.
    *     - 
    * 3. 주의사항 
    *
    **/
    public LifecycleListener[] findLifecycleListeners() {

        return listeners;

    }

    /** 
    * 1. 기능 :  
    * 2. 처리 개요 : Notify all lifecycle event listeners that a particular event has
    * occurred for this Container.  The default implementation performs
    * this notification synchronously using the calling thread.
    *     - 
    * 3. 주의사항 
    *
    * @param type Event type
    * @param data Event data
    **/
    public void fireLifecycleEvent(String type, Object data) {

        LifecycleEvent event = new LifecycleEvent(lifecycle, type, data);
        LifecycleListener interested[] = null;
        synchronized (listeners) {
            interested = (LifecycleListener[]) listeners.clone();
        }
        for (int i = 0; i < interested.length; i++)
            interested[i].lifecycleEvent(event);

    }

    /** 
    * 1. 기능 :  Remove a lifecycle event listener from this component.
    * 2. 처리 개요 : Remove a lifecycle event listener from this component.
    *     - 
    * 3. 주의사항 
    *
     * @param listener The listener to remove
    **/
    public void removeLifecycleListener(LifecycleListener listener) {

        synchronized (listeners) {
            int n = -1;
            for (int i = 0; i < listeners.length; i++) {
                if (listeners[i] == listener) {
                    n = i;
                    break;
                }
            }
            if (n < 0)
                return;
            LifecycleListener results[] =
              new LifecycleListener[listeners.length - 1];
            int j = 0;
            for (int i = 0; i < listeners.length; i++) {
                if (i != n)
                    results[j++] = listeners[i];
            }
            listeners = results;
        }

    }


}
