package com.slug.log4j;

/**
 * 1. 기능 : Log4j 환경을 위한 상수를 정의한 interface
 * 2. 처리 개요 :
 *
 * 3. 주의사항
 *
 * @author  :
 * @version : v 1.0.0
 * @see :
 * @since   :
 */

public interface LogKeys
{

	//파일로거 메인 프라퍼티 그룹 명
    public static final String LOGGER_INFO                  = "FileLoggerInfo";


    //초기화 시 생성할 파일로거 명들
    public static final String LOGGER_DEFAULT            = "FileLogger{DEFAULT}";
    public static final String LOGGER_ADAPTER            = "FileLogger{ADAPTER}";
    public static final String LOGGER_FILEEVENT           = "FileLogger{FILEEVENT}";
    public static final String LOGGER_EXCEPTION          = "FileLogger{EXCEPTION}";


	//파일로거 메인 프라퍼티들
    public static final String INITIAL_LOGGER_LIST       = "initial.logger.list";    //파일로거 Logger 프라퍼티 그룹명 리스트 (필수)
    public static final String INITIAL_APPENDER_LIST     = "initial.appender.list";  //파일로거 Appender 프라퍼티 그룹명 리스트 (필수)


	//파일로거 log4j Logger 프라퍼티들
    public static final String LOGGER_LEVEL                    = "logger.level";           //log4j Logger 로그레벨 속성 (가질수있는 값 : debug, info, warn, error, fatal) (선택, 디폴트 debug)
    public static final String LOGGER_APPENDER_LIST      = "logger.appender.list";   //log4j Logger 에 설정할 Appender 프라퍼티 그룹명 리스트 (선택, 1개이상 권장)
    public static final String LOGGER_CONSOLE             = "logger.console";         //log4j Logger 콘솔 출력여부 (가질수있는 값 : on, off) (선택, 디폴트 on)
    public static final String LOGGER_ADITIVITY              = "logger.aditivity";       //log4j Logger aditivity 속성 (가질수있는 값 : true, false) (선택, 디폴트 false)


	//파일로그 log4j Appender 프라퍼티들
    public static final String APPENDER_LOG_DIRECOTRY     = "appender.log.directory";   //로그파일 저장 디렉토리 절대경로 (필수)
    public static final String APPENDER_LOG_FILENAME       = "appender.log.filename";    //로그파일 명 (필수)
    public static final String APPENDER_DATE_PATTERN       = "appender.date.pattern";    //로그파일 날자별 생성 규칙 (선택, 디폴트 "'.'yyyy-MM-dd")
    public static final String APPENDER_PATTERN_LAYOUT   = "appender.pattern.layout";  //로그 패턴 문자열 (선택, 디폴트 "[%d{yyyy-MM-dd HH:mm:ss.SSS}] %-5p %m%n")
    public static final String APPENDER_FILE_APPEND           = "appender.file.append";     //로그파일 로그Append 여부 (가질수있는 값 : true, false) (선택, 디폴트 true)

}
