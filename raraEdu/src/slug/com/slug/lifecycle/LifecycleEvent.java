package com.slug.lifecycle;
/**
 * 1. 기능 : Lifecycle Event 를 정의한 클래스 
 * 2. 처리 개요 : Lifecycle Event를 표현한 Event 클래스
 * General event for notifying listeners of significant changes on a component
 * that implements the Lifecycle interface. In particular, this will be useful
 * on Containers, where these events replace the ContextInterceptor concept in
 * Tomcat 3.x. 3. 주의사항
 * 
 * @author : Craig R. McClanahan
 * @version : $Revision: 1.1 $ $Date: 2006/02/17 06:12:23 $
 * @see :
 * @since :
 * @deprecated :
 */

import java.util.EventObject;



public final class LifecycleEvent extends EventObject {

	// ----------------------------------------------------------- Constructors

	/**
	 * 1. 기능 : Construct a new LifecycleEvent with the specified parameters. 2.
	 * 처리 개요 : - Construct a new LifecycleEvent with the specified parameters.
	 * 3. 주의사항
	 * 
	 * @param lifecycle
	 *            Component on which this event occurred
	 * @param type
	 *            Event type (required)
	 **/
	public LifecycleEvent(Lifecycle lifecycle, String type) {

		this(lifecycle, type, null);

	}

	/**
	 * 1. 기능 : Construct a new LifecycleEvent with the specified parameters. 2.
	 * 처리 개요 : - Construct a new LifecycleEvent with the specified parameters.
	 * 3. 주의사항
	 * 
	 * @param lifecycle
	 *            Component on which this event occurred
	 * @param type
	 *            Event type (required)
	 * @param data
	 *            Event data (if any)
	 **/
	public LifecycleEvent(Lifecycle lifecycle, String type, Object data) {

		super(lifecycle);
		this.lifecycle = lifecycle;
		this.type = type;
		this.data = data;

	}

	// ----------------------------------------------------- Instance Variables

	/**
	 * The event data associated with this event.
	 */
	private Object data = null;

	/**
	 * The Lifecycle on which this event occurred.
	 */
	private Lifecycle lifecycle = null;

	/**
	 * The event type this instance represents.
	 */
	private String type = null;

	// ------------------------------------------------------------- Properties

	/**
	 * 1. 기능 : Return the event data of this event. 2. 처리 개요 : - Return the
	 * event data of this event. 3. 주의사항
	 * 
	 **/
	public Object getData() {

		return (this.data);

	}

	/**
	 * 1. 기능 : Return the Lifecycle on which this event occurred. 2. 처리 개요 : -
	 * Return the Lifecycle on which this event occurred. 3. 주의사항
	 * 
	 **/
	public Lifecycle getLifecycle() {

		return (this.lifecycle);

	}

	/**
	 * 1. 기능 : Return the event type of this event. 2. 처리 개요 : - Return the
	 * event type of this event. 3. 주의사항
	 **/
	public String getType() {

		return (this.type);

	}

}
