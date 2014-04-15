package com.tg.slug.log4j;

/**
 * 1. ��� : Log4j ȯ���� ���� ����� ������ interface
 * 2. ó�� ���� :
 *
 * 3. ���ǻ���
 *
 * @author  :
 * @version : v 1.0.0
 * @see :
 * @since   :
 */

public interface LogKeys
{

	//���Ϸΰ� ���� ������Ƽ �׷� ��
    public static final String LOGGER_INFO                  = "FileLoggerInfo";


    //�ʱ�ȭ �� ���� ���Ϸΰ� ���
    public static final String LOGGER_DEFAULT            = "FileLogger{DEFAULT}";
    public static final String LOGGER_ADAPTER            = "FileLogger{ADAPTER}";
    public static final String LOGGER_FILEEVENT           = "FileLogger{FILEEVENT}";
    public static final String LOGGER_EXCEPTION          = "FileLogger{EXCEPTION}";


	//���Ϸΰ� ���� ������Ƽ��
    public static final String INITIAL_LOGGER_LIST       = "initial.logger.list";    //���Ϸΰ� Logger ������Ƽ �׷�� ����Ʈ (�ʼ�)
    public static final String INITIAL_APPENDER_LIST     = "initial.appender.list";  //���Ϸΰ� Appender ������Ƽ �׷�� ����Ʈ (�ʼ�)


	//���Ϸΰ� log4j Logger ������Ƽ��
    public static final String LOGGER_LEVEL                    = "logger.level";           //log4j Logger �α׷��� �Ӽ� (������ִ� �� : debug, info, warn, error, fatal) (����, ����Ʈ debug)
    public static final String LOGGER_APPENDER_LIST      = "logger.appender.list";   //log4j Logger �� ������ Appender ������Ƽ �׷�� ����Ʈ (����, 1���̻� ����)
    public static final String LOGGER_CONSOLE             = "logger.console";         //log4j Logger �ܼ� ��¿��� (������ִ� �� : on, off) (����, ����Ʈ on)
    public static final String LOGGER_ADITIVITY              = "logger.aditivity";       //log4j Logger aditivity �Ӽ� (������ִ� �� : true, false) (����, ����Ʈ false)


	//���Ϸα� log4j Appender ������Ƽ��
    public static final String APPENDER_LOG_DIRECOTRY     = "appender.log.directory";   //�α����� ���� ���丮 ����� (�ʼ�)
    public static final String APPENDER_LOG_FILENAME       = "appender.log.filename";    //�α����� �� (�ʼ�)
    public static final String APPENDER_DATE_PATTERN       = "appender.date.pattern";    //�α����� ���ں� �� ��Ģ (����, ����Ʈ "'.'yyyy-MM-dd")
    public static final String APPENDER_PATTERN_LAYOUT   = "appender.pattern.layout";  //�α� ���� ���ڿ� (����, ����Ʈ "[%d{yyyy-MM-dd HH:mm:ss.SSS}] %-5p %m%n")
    public static final String APPENDER_FILE_APPEND           = "appender.file.append";     //�α����� �α�Append ���� (������ִ� �� : true, false) (����, ����Ʈ true)

}
