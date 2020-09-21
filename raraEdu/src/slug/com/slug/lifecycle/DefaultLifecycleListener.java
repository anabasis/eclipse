/**
* 1. 기능 : LifecyclEvent를 수신 처리하는 기본 리스너 클래스
* 2. 처리 개요 : LifecycleEvent를 받아 로깅한다.
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

import com.slug.logging.Logging;

public class DefaultLifecycleListener implements LifecycleListener
{

    /**
    * 1. 기능 :  LifecycleEvent를 수신하여 로깅하는 메서드
    * 2. 처리 개요 : LifecycleEvent를 받아 약속된 포맷으로 Logger를 통해 로깅한다.
    *     -
    * 3. 주의사항
    *
    * @param    event   Lifecycle의 start, stop Event 정보를 표현한 Value Object
    **/
    public void lifecycleEvent(LifecycleEvent event)
    {
        Object data = event.getData();
        if(data==null) {
//            String code = "RWCEAICLC001";
//            Logging.warn.println(ExceptionUtil.make(code, CodeMessageHandler.getMessage(code)));
        	//Logging.warn.println("<DefaultLifecycleListener> <lifecycleEvent> LifecycleEvent is null........");
        	return;
        }

        String type = event.getType();
        String name = data.getClass().getName();
        int idx = name.lastIndexOf(".");
        StringBuffer sb = new StringBuffer();
        sb.append((idx==-1)? name : name.substring(idx+1)).append("] ");
        sb.append("It is ").append(type).append(".");
        //Logging.sys.println(sb.toString());
        System.out.println(sb.toString());

    }
}
