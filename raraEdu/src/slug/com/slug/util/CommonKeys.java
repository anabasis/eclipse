package com.slug.util;

/**
* 1. 기능 : 배치프레임웍에서 공통으로 사용하는 상수를 정의한 Interface
* 2. 처리 개요 :
*     -
* 3. 주의사항
*
* @author  : YCLee 06/01/24
* @version : v 1.0.0
* @since   :JDK v1.4.2
*/
public class CommonKeys
{
    /* *******************************************************************
     *  Master Layer 구분 상수
     ******************************************************************* */

    /**
     * Inbound Layer: FileEventListener
     */
    public static final String LAYER_FILE_EVENT = "FE";

    /**
     * Inbound Layer: SocketServer Adapter
     */
    public static final String LAYER_SOCKETSERVER = "SS";

    /**
     * Scheduler Layer: 스케쥴러 관련
     */
    public static final String LAYER_SCHEDULER = "SC";

    /**
     * Flow Controller Layer: 전체
     */
    public static final String LAYER_FLOW_CONTROLLER = "FC";


    /* *******************************************************************
     *  Layer 구분 상수
     ******************************************************************* */

    /**
     * Scheduler Layer: 스케쥴러 관련 - Job Queue 테이블 관련
     */
    public static final String SUB_LAYER_SCHEDULER_QUEUE = "SQ";

    /**
     * Scheduler Layer: 스케쥴러 관련 - Job Processing 테이블 관련
     */
    public static final String SUB_LAYER_SCHEDULER_PROCESSING = "SP";




    /* *******************************************************************
     *  업무 유형 구분 상수
     ******************************************************************* */
    /**
     * 요구송신
     */
    public static final String PROCESS_REQUEST_SEND = "RS";

    /**
     * 요구수신
     */
    public static final String PROCESS_REQUEST_RECEIVE = "RR";

    /**
     * 응답송신
     */
    public static final String PROCESS_RESPONSE_SEND = "AS";

    /**
     * 응답수신
     */
    public static final String PROCESS_RESPONSE_RECEIVE = "AR";

    /**
     * VAN 송신
     */
    public static final String PROCESS_VAN_SEND = "VS";

    /**
     * VAN 수신
     */
    public static final String PROCESS_VAN_RECEIVE = "VR";















}
