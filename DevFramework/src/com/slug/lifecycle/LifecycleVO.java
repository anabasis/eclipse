package com.slug.lifecycle;
/** 
* 1. 기능 : Lifecycle Rule 정보를 정의한 Java Value Object 클래스
* 2. 처리 개요 : Lifecycle Rule 정보를 정의한다.
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


public class LifecycleVO implements Serializable, Comparable
{ 
    /**
     * Lifecycle 클래스
     */
    private String lifecycleClass;          // 생명주기클래스명[TSEAIFR05.LifeCyclClsName]
    /**
     * 로딩 / 초기화 순서
     */
    private int loadSequence;               // 로딩순서[TSEAIFR05.LodinSeq]
    
    /**
     * 생명주기사용구분코드
     */
    private String lifeCyclUseDstcd;        // Migration - 추가  생명주기사용구분코드[TSEAIFR05.LifeCyclUseDstcd]    
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
    public LifecycleVO() {
        this("", -1);
    }


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
    public LifecycleVO(String lifecycleClass, int loadSequence) {
        this.lifecycleClass = lifecycleClass;
        this.loadSequence = loadSequence;       
    }    
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
    public LifecycleVO(String lifecycleClass, int loadSequence, String lifeCyclUseDstcd) {
        this.lifecycleClass = lifecycleClass;
        this.loadSequence = loadSequence;
        this.lifeCyclUseDstcd = lifeCyclUseDstcd;           // Migration - ADD
    }
    
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
    public void setLifecycleClass(String lifecycleClass) {
        this.lifecycleClass = lifecycleClass; 
    }
    
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
    public String getLifecycleClass() {
        return this.lifecycleClass;   
    }
    
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
    public void setLoadSequence(int loadSequence) {
        this.loadSequence = loadSequence;   
    }
    
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
    public int getLoadSequence() {
        return this.loadSequence;   
    }
    
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
    public int compareTo(Object obj)
    {
        if( !(obj instanceof LifecycleVO) ) {
            return -1;
        }
        LifecycleVO l = (LifecycleVO)obj;
        return this.getLoadSequence() - l.getLoadSequence();
    }
    
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
    public boolean equals(Object obj) {
        if(obj instanceof LifecycleVO) {
            LifecycleVO l = (LifecycleVO)obj;
            // Migration - 추가
            return ( l.getLifecycleClass().equals(this.getLifecycleClass()) && (l.getLoadSequence()== this.getLoadSequence()) && (l.getLifeCyclUseDstcd()== this.getLifeCyclUseDstcd()) );
            //return ( l.getLifecycleClass().equals(this.getLifecycleClass()) && (l.getLoadSequence()== this.getLoadSequence()) );
        } else {
            return false;   
        }
    }
    
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("LifecycleVO[ lifecycleClass=").append(lifecycleClass);
        sb.append(", loadSequence=").append(loadSequence);
        sb.append(", lifeCyclUseDstcd=").append(lifeCyclUseDstcd);   // Migration - 추가
        sb.append(" ]");
        return sb.toString();   
    }
    
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
    // Migration - 추가
    public void setLifeCyclUseDstcd(String lifeCyclUseDstcd) {
        this.lifeCyclUseDstcd = lifeCyclUseDstcd; 
    }


    
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
    // Migration - 추가
    public String getLifeCyclUseDstcd() {
        return this.lifeCyclUseDstcd;   
    }
} 
