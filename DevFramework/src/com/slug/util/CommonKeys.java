package com.slug.util;

/**
* 1. ��� : ��ġ�����ӿ����� �������� ����ϴ� ����� ������ Interface
* 2. ó�� ���� :
*     -
* 3. ���ǻ���
*
* @author  : YCLee 06/01/24
* @version : v 1.0.0
* @since   :JDK v1.4.2
*/
public class CommonKeys
{
    /* *******************************************************************
     *  Master Layer ���� ���
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
     * Scheduler Layer: �����췯 ���
     */
    public static final String LAYER_SCHEDULER = "SC";

    /**
     * Flow Controller Layer: ��ü
     */
    public static final String LAYER_FLOW_CONTROLLER = "FC";


    /* *******************************************************************
     *  Layer ���� ���
     ******************************************************************* */

    /**
     * Scheduler Layer: �����췯 ��� - Job Queue ���̺� ���
     */
    public static final String SUB_LAYER_SCHEDULER_QUEUE = "SQ";

    /**
     * Scheduler Layer: �����췯 ��� - Job Processing ���̺� ���
     */
    public static final String SUB_LAYER_SCHEDULER_PROCESSING = "SP";




    /* *******************************************************************
     *  ���� ���� ���� ���
     ******************************************************************* */
    /**
     * �䱸�۽�
     */
    public static final String PROCESS_REQUEST_SEND = "RS";

    /**
     * �䱸����
     */
    public static final String PROCESS_REQUEST_RECEIVE = "RR";

    /**
     * ����۽�
     */
    public static final String PROCESS_RESPONSE_SEND = "AS";

    /**
     * �������
     */
    public static final String PROCESS_RESPONSE_RECEIVE = "AR";

    /**
     * VAN �۽�
     */
    public static final String PROCESS_VAN_SEND = "VS";

    /**
     * VAN ����
     */
    public static final String PROCESS_VAN_RECEIVE = "VR";















}
