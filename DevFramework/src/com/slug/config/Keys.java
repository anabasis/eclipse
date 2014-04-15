package com.slug.config; 

/** 
* 1. 기능 : server 패키지에서 사용하는 상수를 정의한 Interface
* 2. 처리 개요 : 
*     - server 패키지에서 사용하는 상수를 정의한 Interface
* 3. 주의사항 
*
* @author  : 이윤철
* @version : v 1.0.0
* @see : TestCallRunner, TestCallManager
* @since   :JDK v1.4.2
*/
public class Keys 
{ 
    /**
     * 모든 서버를 의미하는 Default Server 이름
     */
    public static final String DEFAULT_SERVER = "ALL";
    
    /**
     * WebLogic의 현 운영 서버 이름에 대한 Property Key
     */
    public static final String WLI_SERVER_KEY = "weblogic.Name";
    
    
    
} 
