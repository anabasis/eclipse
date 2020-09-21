/** 
* 1. 기능 : Lifecycle 예외 처리를 위한 Exception 클래스
* 2. 처리 개요 : Lifecycle 예외 처리를 위한 Exception 클래스
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




public final class LifecycleException extends Exception {



	/** 
    * 1. 기능 :  Construct a new LifecycleException with no other information.
    * 2. 처리 개요 : Default Constructor
    *     - 
    * 3. 주의사항 
    *
    **/
    public LifecycleException() {

        this(null, null);

    }


    /** 
    * 1. 기능 :  Construct a new LifecycleException for the specified message.
    * 2. 처리 개요 : 파라미터의 예외 발생 원인을 초기화한다.
    *     - 
    * 3. 주의사항 
    *
    * @param message Message describing this exception
     **/
    public LifecycleException(String message) {

        this(message, null);

    }

    /** 
    * 1. 기능 :  Construct a new LifecycleException for the specified throwable.
    * 2. 처리 개요 : 파라미터의 오류(Throwable)을 Wrapper하기 위해 멤버에 초기화 한다.
    *     - 
    * 3. 주의사항 
    *
    * @param throwable Throwable that caused this exception
    **/
    public LifecycleException(Throwable throwable) {

        this(null, throwable);

    }

    /** 
    * 1. 기능 :  Construct a new LifecycleException for the specified message and throwable.
    * 2. 처리 개요 : 파라미터의 값을 멤버 변수에 초기화 한다.
    *     - 
    * 3. 주의사항 
    *
    * @param message Message describing this exception
    * @param throwable Throwable that caused this exception
    **/
    public LifecycleException(String message, Throwable throwable) {

        super();
        this.message = message;
        this.throwable = throwable;

    }


    //------------------------------------------------------ Instance Variables


    /**
     * The error message passed to our constructor (if any)
     */
    protected String message = null;


    /**
     * The underlying exception or error passed to our constructor (if any)
     */
    protected Throwable throwable = null;


    //---------------------------------------------------------- Public Methods

    /** 
    * 1. 기능 :  Returns the message associated with this exception, if any.
    * 2. 처리 개요 : 오류 발생 원인을 반환한다.
    *     - 
    * 3. 주의사항 
    *
    * @return   Exception 발생 원인 
    **/
    /**
     * 
     */
    public String getMessage() {

        return (message);

    }

    /** 
    * 1. 기능 :  Returns the throwable that caused this exception, if any.
    * 2. 처리 개요 : 발생한 오류를 표현하는 Throwable
    *     - 
    * 3. 주의사항 
    *
    * @return 발생한 오류를 표현하는 Throwable
    **/
    public Throwable getThrowable() {

        return (throwable);

    }

    /** 
    * 1. 기능 :  Return a formatted string that describes this exception.
    * 2. 처리 개요 : 
    *     - 
    * 3. 주의사항 
    *
    * @return 
    **/
    public String toString() {

        StringBuffer sb = new StringBuffer("LifecycleException:  ");
        if (message != null) {
            sb.append(message);
            if (throwable != null) {
                sb.append(":  ");
            }
        }
        if (throwable != null) {
            sb.append(throwable.toString());
        }
        return (sb.toString());

    }


}