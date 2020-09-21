package com.slug.lifecycle;
/**
* 1. 기능 : Lifecycle Rule 정보를 DB로 부터 로딩해 메모리에 관리하는 Manager 클래스
* 2. 처리 개요 : Lifecycle Rule 정보를 메모리에 로딩해 관리한다.
* *     -
* 3. 주의사항
*
* @author  :
* @version : v 1.0.0
* @see : 관련 기능을 참조
* @since   :
* @deprecated :
*/
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.slug.exception.PException;
import com.slug.logging.Logging;


public class LifecycleManager implements Lifecycle
{

    /**
     * Lifecycle Sigleton Instance
     */
    private static LifecycleManager instance;
    /**
     * 기동 여부
     */
    private boolean started=false;
    /**
     * LifecycleSupport Instance
     */
    private LifecycleSupport lifecycle = new LifecycleSupport(this);

    /**
     * Lifecyclce Instance를 저장하는 List
     */
    private List lifecycles;
    /**
     * Lifecycle Rule 정보를 저장하는 List
     */
    private List infos;

    /**
     * LIfecycle Event를 수신 / 처리할 Listener 클래스
     */
    private LifecycleListener listener;

    /**
    * 1. 기능 :  Default Constructor
    * 2. 처리 개요 : Default Constructor
    *     -
    * 3. 주의사항
    *
    **/
    private LifecycleManager() {

    }

    /**
    * 1. 기능 :  LifecycleManager Singleton Instance를 반환하는 메서드
    * 2. 처리 개요 : LifecycleManager Singleton Instance를 반환한다.
    *     -
    * 3. 주의사항
    *
    * @return   LifecycleManager Singleton Instance
    **/
    public static LifecycleManager getInstance() {
        if(instance==null) {
            synchronized(LifecycleManager.class) {
                if(instance==null) {
                    instance = new LifecycleManager();
                }
            }
        }

        return instance;
    }
    
    
    /**
     * 1. 기능 :  LifecycleManager 기동 시 초기화 로직을 처리하는 메서드
     * 2. 처리 개요 : Lifecycle Rule 전체 정보를 로딩 초기화하고, 해당 Lifecycle 객체를 생성 초기화 한다.
     *     -
     * 3. 주의사항
     *
     * @exception    LifecycleException  이미 기동된 경우 or 설정된 Rule이 Lifecycle 클래스가 아닌 경우
     **/
     public void start(String prop_file) throws LifecycleException {

         if (started)
             throw new LifecycleException("RECEAICLC201");

         try {

             listener = new DefaultLifecycleListener();
             this.addLifecycleListener(listener);
             // Notify our interested LifecycleListeners
             lifecycle.fireLifecycleEvent(STARTING_EVENT, this);
             
             //Logging.dev.println("Null check -001");

             lifecycles = new ArrayList();
             ///*
             LifecycleVO vo1 = new LifecycleVO();
             vo1.setLifecycleClass("com.slug.config.AppConfigManager");
             vo1.setLoadSequence(100);
             vo1.setLifeCyclUseDstcd("System Applcation Configuration Loader");
  
             LifecycleVO vo2 = new LifecycleVO();
             vo2.setLifecycleClass("com.slug.dao.sql.StatementManager");
             vo2.setLoadSequence(101);
             vo2.setLifeCyclUseDstcd("SQL Statement Configuration Loader");

             
             infos = new ArrayList();
            
             addLifecycle(vo1);
             addLifecycle(vo2);
        
             //*/

             // EAIMessage Table Initializing ....
             //LifecycleDAO dao = null;
             /*
             try {
                 //dao = (LifecycleDAO)DAOFactory.newInstance().create(LifecycleDAO.class);

                 // LifecycleDAO를 이용하여 테이블에 등록된 값들을 가지고 온다.
                 //infos = dao.getAllLifecycles();
             } catch(LifecycleException e) {
                 throw new LifecycleException(e);
             }
             */


             Collections.sort(infos);

             LifecycleVO vo = null;
             Lifecycle lf = null;

             for(int i=0;i<infos.size();i++)
             {
                 vo = (LifecycleVO)infos.get(i);

                 //// 배치 프레임웍과 관련 없는 어댑터는 로딩하지 않도록 수정 - YCLee 05/12/21
                 //String indexStr = "com.kbstar.eai.batch";         // 배치관련 클래스는 모두 좌측의 패키지명을 필수로 가져야 한다.
                 //String adpaterClassName = vo.getLifecycleClass();
                 //String org_AdpaterClassName = adpaterClassName;

                 //adpaterClassName = adpaterClassName.substring(0, indexStr.length());

                 // 수정된 부분 End - 아래의 if 조건문 포함.

                 //if(adpaterClassName.equals(indexStr))
                 //{
             	///*
                     try
                     {
                         if(vo.getLoadSequence()<=0) {
                             String[] msgArgs = new String[1];
                             msgArgs[0] = vo.toString();
                             //Logging.dev.println("RDCEAICLC205");
                             continue;
                         }
                         Class cl = Class.forName(vo.getLifecycleClass());
                         Method m = cl.getDeclaredMethod("getInstance",new Class[]{});
                         Object obj  = m.invoke(null,new Object[]{});

                         if(obj instanceof Lifecycle) {
                             lf = (Lifecycle)obj;
                             lf.addLifecycleListener(listener);
                             lf.start(prop_file);
                             lifecycles.add(lf);
                         } else {
                             throw new PException("Fail to start the Lifecycle. - "+vo);
                         }
                     } catch(Exception e) {
                         String code = "RECEAICLC206";
                         //Logging.dev.println(e);
                         e.printStackTrace();
                     }
                    // */
                 //}
                 //else
                 //{
                 //    System.out.println("불필요한 어댑터를 로딩하지 않았습니다. : " + org_AdpaterClassName);
                 //}
             }

             // Notify our interested LifecycleListeners
             lifecycle.fireLifecycleEvent(STARTED_EVENT, this);
         } catch(Exception e) {
             throw new LifecycleException(e);
         } finally {
             started = true;
         }
     }

   /**
    * 1. 기능 :  LIfecycleManager가 종료할 때 초기호 작업에 대한 Undo로직을 처리하기 위한 메서드
    * 2. 처리 개요 : LifcycleManager가 초기화 한 Lifecycle 객체를 종료하고, 멤버변수의 List를 clear한다.
    *     -
    * 3. 주의사항
    *
    * @exception
    **/
    public void stop() throws LifecycleException {
        // Validate and update our current component state
        if (!started)
//            throw new LifecycleException("LifecycleManager is already stopped.");
            throw new LifecycleException("RECEAICLC203");
        // Notify our interested LifecycleListeners
        lifecycle.fireLifecycleEvent(STOPING_EVENT, this);

        Lifecycle lf = null;
        for(int i=lifecycles.size()-1;i>=0;i--) {
            lf = (Lifecycle)lifecycles.get(i);
            if(!lf.isStarted()) continue;

            String[] msgArgs = new String[1];
            msgArgs[0] = lf.getClass().getName();
         //   Logging.dev.println(ExceptionUtil.make("RDCEAICLC208", msgArgs));

            try {
                lf.stop();
                lf.removeLifecycleListener(listener);
            } catch(Exception e) {
                String code = "RECEAICLC209";
                 Logging.err.println(e);
                e.printStackTrace();
            }
        }

        this.infos.clear();
        this.lifecycles.clear();

        started = false;
        // Notify our interested LifecycleListeners
        lifecycle.fireLifecycleEvent(STOPPED_EVENT, this);
        this.removeLifecycleListener(this.listener);
    }

    /**
    * 1. 기능 :  기동여부를 반환하는 메서드
    * 2. 처리 개요 : 기동 여부를 반환한다.
    *     -
    * 3. 주의사항
    *
    * @return   기동여부
    * @exception
    **/
    public boolean isStarted() {
        return this.started;
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
    * 1. 기능 :  BizKeyManager의 초기화 여부를 반환하는 getter 메서드
    * 2. 처리 개요 : BizKeyManager의 초기화 여부를 반환한다.
    * 3. 주의사항
    *
    * @return   초기화 여부
    **/
    public void addLifecycle(LifecycleVO vo) {
        infos.add(vo);
    }

    /**
    * 1. 기능 :  Lifecycle Rule 정보를 변경하는 메서드
    * 2. 처리 개요 : Lifecycle Rule 정보를 변경한다.
    *     -
    * 3. 주의사항
    *
    * @param    vo  Lifecycle Rule 정보
    **/
    public void updateLifecycle(LifecycleVO vo) {
        LifecycleVO tmp = null;

    search:
        for(int i=0;i<infos.size();i++) {
            tmp = (LifecycleVO)infos.get(i);
            if (vo.getLifecycleClass().equalsIgnoreCase(tmp.getLifecycleClass())) {
                infos.remove(tmp);
                infos.add(vo);
                break search;
            }
        }
    }

    /**
    * 1. 기능 :  Lifecycle Rule 정보를 삭제하는 메서드
    * 2. 처리 개요 : Lifecycle Rule 정보를 삭제한다.
    *     -
    * 3. 주의사항
    *
    * @param    lifecycleClass  삭제할 LifecycleClass
    **/
    public void removeLifecycle(String lifecycleClass) {
        LifecycleVO vo = null;

    search:
        for(int i=0;i<infos.size();i++) {
            vo = (LifecycleVO)infos.get(i);
            if (lifecycleClass.equalsIgnoreCase(vo.getLifecycleClass())) {
                infos.remove(vo);
                break search;
            }
        }

    }

    /**
    * 1. 기능 :  메모리에 로딩 초기화한 Lifecycle 인스턴스 리스트를 반환하는 메서드
    * 2. 처리 개요 : 메모리에 로딩 초기화한 Lifecycle 인스턴스 리스트를 반환한다.
    *     -
    * 3. 주의사항
    *
    * @return   메모리에 로딩 초기화한 Lifecycle 인스턴스 리스트
    **/
    public List getLifecycleVOList() {
        return infos;
    }
}
