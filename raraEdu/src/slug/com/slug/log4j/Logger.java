package com.slug.log4j;

import com.slug.config.PropManager;
import com.slug.util.StringUtil;

import java.io.File;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Properties;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Category;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.DailyRollingFileAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.Priority;
import org.apache.log4j.PropertyConfigurator;

/**
 * 1. 기능 : Log4j을 통한 화일로그를 관리하는 Utility Class
 * 2. 처리 개요 :
 *
 * 3. 주의사항
 *
 * @author  :
 * @version : v 1.0.0
 * @see :
 * @since   :
 */
public class Logger  implements LogKeys {

	//Logger list (프라퍼티에 설정된 Logger 및 Dafault Console Logger 를 담는다.)
    protected static Hashtable loggers = new Hashtable(5);

	//Default Console Logger 명
	private static String defConLoggerName = "DefaultConsoleLogger";

    //Log4j Looger 객체
    private org.apache.log4j.Logger logger;


	//Default Console Logger 생성 (DEBUG 레벨 설정)
    static {

		org.apache.log4j.Logger defConLogger = org.apache.log4j.Logger.getLogger(defConLoggerName);
		defConLogger.setAdditivity(false);
		defConLogger.setLevel(org.apache.log4j.Level.DEBUG);
		defConLogger.removeAllAppenders();
		ConsoleAppender defConAppender = new ConsoleAppender(new PatternLayout("<%d{yyyy-MM-dd HH:mm:ss.SSS}> %-5p [Def.Con.Logger] %m%n"));
		defConLogger.addAppender(defConAppender);

		loggers.put(defConLoggerName, new Logger(defConLogger));
    }

	//당 Logger 클래스에서 System.out 대신 사용할 Default Console Logger 객체 정의
	private static Logger selfLogger = getLogger();


	//기본 로그 정보 출력
	static {
		
        selfLogger.banner("EAI 프레임웍 log4j Logger 기본정보");
        selfLogger.debug("※ EAI Logger명 1 (Logger.LOGGER_DEFAULT)   : " + LOGGER_DEFAULT);
        //selfLogger.debug("※ EAI Logger명 2 (Logger.LOGGER_EXCEPTION) : " + LOGGER_EXCEPTION);
        selfLogger.debug("※ log4j ROOT LOGGER LEVEL                  : " + org.apache.log4j.Logger.getRootLogger().getLevel());
        selfLogger.debug("※ 디폴트 콘솔 Logger LEVEL                 : " + selfLogger.getLevel());
	}



	static public synchronized Logger getLogger() {
		return getLogger(null);
	}

	/**
     * 1. 기능 : Logger factory method
     * 2. 처리 개요 :
     *    - Logger Factory를 생성한다.
     * 3. 주의사항
     *
     * @param	name	Logger name
	 * @return	Logger object
     * @exception    JMSException
     **/
    static public synchronized Logger getLogger(String loggerName) {

		if (loggerName == null || loggerName.equals("") || loggerName.equals("root")) {
			return (Logger) loggers.get(defConLoggerName);
		}

        Logger logger = (Logger) loggers.get(loggerName);
        if (logger==null) {
			logger = (Logger) loggers.get(defConLoggerName);
			logger.warn("****** '"+ loggerName +"' 이름의 Logger 가 정의되지 않았음. 배치 디폴트 콘솔 Logger 리턴.");
		}
		return logger;
    }

	/**
	 *	Constructor
	 */
    private Logger(org.apache.log4j.Logger logger) {
		this.logger = logger;
    }

	public String getLevel() {
		return this.logger.getLevel().toString();
	}

    /**
     * 1. 기능 : PropManager를 통해 Log4j의 환경을 설정
     * 2. 처리 개요 :
     *    - PropManager를 통해 Log4j의 환경을 설정
     * 3. 주의사항
     *
     * @exception
     **/
    public static void doConfigure()
    {
        try
        {
			//========================================================
			//1. 파일 Logger 기본 프라퍼티 정보 체크
			//========================================================
			PropManager pmanager = PropManager.getInstance();
            Properties loggerInfo= pmanager.getProperties(LOGGER_INFO);

			//초기화시 메모리에 적재할 Logger 이름 리스트
            String loggerList    = loggerInfo.getProperty(INITIAL_LOGGER_LIST);
            if(loggerList==null || loggerList.equals("")) throw new RuntimeException("RECEAICUL005");

			//초기화시 Logger 에 설정해줄 파일 Appender 이름 리스트
            String appenderList  = loggerInfo.getProperty(INITIAL_APPENDER_LIST);
            System.out.println("appenderList : " + appenderList);
            if(appenderList==null || appenderList.equals("")) throw new RuntimeException("RECEAICUL006");


			//========================================================
			//2. 프라퍼티에 설정된 모든 Appender 생성 후 HashMap에 저장
			//========================================================
			//이후 Logger 생성시 필요한 Appender 찾아서 붙여줌
            String[] appenderNameList = StringUtil.getStrArray(appenderList, ",");
            HashMap appenderPool = new HashMap();

            for (int i=0; i<appenderNameList.length; i++) {
                FileAppender appender = createFileAppender(appenderNameList[i]);	//Appender 생성
                appenderPool.put( appenderNameList[i], appender );
            }

			//========================================================
			//3. 프라퍼티에 설정된 모든 파일 Logger 생성
			//========================================================
            String[] loggerNameList = StringUtil.getStrArray(loggerList, ",");		//log4j Logger 생성
            org.apache.log4j.Logger logger = null;

            for(int i=0; i<loggerNameList.length; i++) {
                logger = createLogger(loggerNameList[i]);

                String loggerAppenderName = pmanager.getProperty(loggerNameList[i], LOGGER_APPENDER_LIST);
				String[] loggerAppenderNameList = StringUtil.getStrArray(loggerAppenderName, ",");

				for (int j=0; j<loggerAppenderNameList.length; j++) {
					FileAppender appender = (FileAppender)appenderPool.get(loggerAppenderNameList[j]);
					if (appender==null) continue;
					logger.addAppender(appender);
					selfLogger.debug("  - 파일로거 '"+ logger.getName() +"' 에 '"+ loggerAppenderNameList[j] +"' Appender 설정.");
				}

				loggers.put(loggerNameList[i], new Logger(logger));
            }


        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 1. 기능 : Log4j의 FileAppender를 추가
     * 2. 처리 개요 :
     *    - Log4j API를 통해 FileAppender를 추가
     * 3. 주의사항
     *
     * @exception
     **/
    public static FileAppender createFileAppender(String appenderName) {

		PropManager pmanager = PropManager.getInstance();

		//로그파일 저장 디렉토리 절대경로
    System.out.println("#### appenderName : " + appenderName);        
		String logDirectory  = pmanager.getProperty(appenderName, APPENDER_LOG_DIRECOTRY);
    System.out.println("#### logDirectory : " + logDirectory);
		if (logDirectory==null || logDirectory.equals("")) throw new RuntimeException("RECEAICUL004");

		//로그파일 명
        String logFilename = pmanager.getProperty(appenderName, APPENDER_LOG_FILENAME);
        if (logFilename==null || logFilename.equals("")) throw new RuntimeException("RECEAICUL007");

		//로그파일 날자별 생성 규칙
        String datePattern = pmanager.getProperty(appenderName, APPENDER_DATE_PATTERN);
        if (datePattern==null || datePattern.equals("")) datePattern = "'.'yyyy-MM-dd";

		//로그 패턴 문자열
		String patternLayout = pmanager.getProperty(appenderName, APPENDER_PATTERN_LAYOUT);
		if (patternLayout==null || patternLayout.equals("")) patternLayout = "<%d{yyyy-MM-dd HH:mm:ss.SSS}> %-5p %m%n";

		//로그파일 로그Append 여부
        String fileAppend = pmanager.getProperty(appenderName, APPENDER_FILE_APPEND);
        if (fileAppend==null || fileAppend.equals("")) fileAppend = "true";


        //String localServer = EAIServerManager.getInstance().getLocalServerName();
        //File dir = new File(logDirectory + File.separatorChar + localServer);
        File dir = new File(logDirectory);

        if (!dir.exists()) {
            dir.mkdir();
        }
        File f = new File(dir, logFilename);
		if (!f.exists()) {
			File parent = f.getParentFile();
			if (!parent.exists()) {
				parent.mkdir();
			}
		}

		DailyRollingFileAppender appender = new DailyRollingFileAppender();
		appender.setName(appenderName);
		appender.setDatePattern(datePattern);
		appender.setLayout(new PatternLayout(patternLayout));
		try {
            appender.setAppend(new Boolean(fileAppend).booleanValue());
        } catch(Exception e) {
            appender.setAppend(true);
        }
		appender.setFile(f.getAbsolutePath());
		appender.activateOptions();
		selfLogger.debug("  - 파일로거 '"+ appenderName +"' Appender 생성됨.");

        return appender;
    }

   /**
     * 1. 기능 : Log4j의 logger를 추가
     * 2. 처리 개요 :
     *    - Log4j API를 통해 logger를 추가
     * 3. 주의사항
     *
     * @exception
     **/
    public static org.apache.log4j.Logger createLogger(String loggerName) {

		PropManager pmanager = PropManager.getInstance();


		//log4j Logger 로그레벨 속성
        String logLevel = pmanager.getProperty(loggerName, LOGGER_LEVEL);
        if (logLevel==null || logLevel.equals("")) logLevel = "debug";

		//log4j Logger 콘솔 출력여부
		String console = pmanager.getProperty(loggerName, LOGGER_CONSOLE);
		if (console==null || console.equals("")) console = "on";

		//log4j Logger aditivity 속성
        String aditivity = pmanager.getProperty(loggerName, LOGGER_ADITIVITY);
        if (aditivity==null || aditivity.equals("")) aditivity = "false";


        //String localServer = EAIServerManager.getInstance().getLocalServerName();
        org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(loggerName);
		selfLogger.debug("  - 파일로거 '"+ logger.getName() +"' Logger 생성됨.");

       logger.removeAllAppenders();

		try {
            logger.setAdditivity(new Boolean(aditivity).booleanValue());
        } catch(Exception e) {
            logger.setAdditivity(false);
        }

		if ((console!=null) && console.equals("on")) {
			ConsoleAppender conAppender = new ConsoleAppender(new PatternLayout("<%d{yyyy-MM-dd HH:mm:ss.SSS}> %-5p ["+ loggerName +"] %m%n"));
			logger.addAppender(conAppender);
			selfLogger.debug("  - 파일로거 '"+ logger.getName() +"' Logger 에 콘솔 Appender 설정.");
		}

		logger.setPriority (logLevel.toLowerCase().equals("debug")? Level.DEBUG :
		                    logLevel.toLowerCase().equals("info" )? Level.INFO  :
		                    logLevel.toLowerCase().equals("warn" )? Level.WARN  :
		                    logLevel.toLowerCase().equals("error")? Level.ERROR :
		                    logLevel.toLowerCase().equals("fatal")? Level.FATAL : Level.DEBUG );

		return logger;
    }





	/**
     * 1. 기능 : Banner 형태의 로그를 출력
     * 2. 처리 개요 :
     *    - Banner 형태의 로그를 출력
     * 3. 주의사항
     *
     * @param	msg	Object
     * @exception
     **/
    public void banner(Object msg) {
        try {
            this.logger.debug("===========================================================");
            this.logger.debug(msg);
            this.logger.debug("===========================================================");
        } catch (Throwable t) {
            printOut(t, msg);
        }
    }

	/**
     * 1. 기능 : debug Level의 로그를 출력
     * 2. 처리 개요 :
     *    - debug Level의 로그를 출력
     * 3. 주의사항
     *
     * @param	msg	Object
     * @exception
     **/
    public void debug(Object msg) {
        try {
            this.logger.debug(msg);
        } catch (Throwable t) {
            printOut(t, msg);
        }
    }

	/**
     * 1. 기능 : debug Level의 로그를 출력
     * 2. 처리 개요 :
     *    - debug Level의 로그를 출력
     * 3. 주의사항
     *
     * @param	code String
     * @param	msg	Object
     * @exception
     **/
    public void debug(String code, Object msg) {
        try {
            this.logger.debug(format(code, msg));
        } catch (Throwable t) {
            printOut(t, msg);
        }
    }

    /**
     * 1. 기능 : debug Level의 로그를 출력
     * 2. 처리 개요 :
     *    - debug Level의 로그를 출력
     * 3. 주의사항
     *
     * @param	msg Object
     * @param	t	Throwable
     * @exception
     **/
    public void debug(Object msg, Throwable t) {
        try {
            this.logger.debug(msg, t);
        } catch (Throwable th) {
            printOut(t, th, msg);
        }
    }

    /**
     * 1. 기능 : debug Level의 로그를 출력
     * 2. 처리 개요 :
     *    - debug Level의 로그를 출력 (ErrorCode를 사용할 경우)
     * 3. 주의사항
     *
     * @param	code String
     * @param	msg Object
     * @param	t	Throwable
     * @exception
     **/
    public void debug(String code, Object msg, Throwable t) {
        try {
            this.logger.debug(format(code,msg), t);
        } catch (Throwable th) {
            printOut(t, th, msg);
        }
    }

    /**
     * 1. 기능 : info Level의 로그를 출력
     * 2. 처리 개요 :
     *    - info Level의 로그를 출력
     * 3. 주의사항
     *
     * @param	msg Object
     * @exception
     **/
    public void info(Object msg) {
        try {
            this.logger.info(msg);
        } catch (Throwable t) {
            printOut(t, msg);
        }
    }

    /**
     * 1. 기능 : info Level의 로그를 출력
     * 2. 처리 개요 :
     *    - info Level의 로그를 출력 (ErrorCode를 사용할 경우)
     * 3. 주의사항
     *
     * @param	code String
     * @param	msg Object
     * @exception
     **/
    public void info(String code, Object msg) {
        try {
            this.logger.info(format(code,msg));
        } catch (Throwable t) {
            printOut(t, msg);
        }
    }

    /**
     * 1. 기능 : info Level의 로그를 출력
     * 2. 처리 개요 :
     *    - info Level의 로그를 출력
     * 3. 주의사항
     *
     * @param	msg Object
     * @param	t	Throwable
     * @exception
     **/
    public void info(Object msg, Throwable t) {
        try {
            this.logger.info(msg, t);
        } catch (Throwable th) {
            printOut(t, th, msg);
        }
    }

    /**
     * 1. 기능 : info Level의 로그를 출력
     * 2. 처리 개요 :
     *    - info Level의 로그를 출력 (ErrorCode를 사용할 경우)
     * 3. 주의사항
     *
     * @param	code String
     * @param	msg Object
     * @param	t	Throwable
     * @exception
     **/
    public void info(String code,Object msg, Throwable t) {
        try {
            this.logger.info(format(code,msg), t);
        } catch (Throwable th) {
            printOut(t, th, msg);
        }
    }

    /**
     * 1. 기능 : warn Level의 로그를 출력
     * 2. 처리 개요 :
     *    - warn Level의 로그를 출력
     * 3. 주의사항
     *
     * @param	msg Object
     * @exception
     **/
    public void warn(Object msg) {
        try {
            this.logger.warn(msg);
        } catch (Throwable t) {
            printOut(t, msg);
        }
    }

    /**
     * 1. 기능 : warn Level의 로그를 출력
     * 2. 처리 개요 :
     *    - warn Level의 로그를 출력 (ErrorCode를 사용할 경우)
     * 3. 주의사항
     *
     * @param	code String
     * @param	msg Object
     * @exception
     **/
    public void warn(String code, Object msg) {
        try {
            this.logger.warn(format(code,msg));
        } catch (Throwable t) {
            printOut(t, msg);
        }
    }

    /**
     * 1. 기능 : warn Level의 로그를 출력
     * 2. 처리 개요 :
     *    - warn Level의 로그를 출력
     * 3. 주의사항
     *
     * @param	msg Object
     * @param	t	Throwable
     * @exception
     **/
    public void warn(Object msg, Throwable t) {
        try {
            this.logger.warn(msg, t);
        } catch (Throwable th) {
            printOut(t, th, msg);
        }
    }

    /**
     * 1. 기능 : warn Level의 로그를 출력
     * 2. 처리 개요 :
     *    - warn Level의 로그를 출력 (ErrorCode를 사용할 경우)
     * 3. 주의사항
     *
     * @param	code String
     * @param	msg Object
     * @param	t	Throwable
     * @exception
     **/
    public void warn(String code, Object msg, Throwable t) {
        try {
            this.logger.warn(format(code,msg), t);
        } catch (Throwable th) {
            printOut(t, th, msg);
        }
    }

    /**
     * 1. 기능 : error Level의 로그를 출력
     * 2. 처리 개요 :
     *    - error Level의 로그를 출력
     * 3. 주의사항
     *
     * @param	msg Object
     * @exception
     **/
    public void error(Object msg) {
        try {
            //msg = " {"+UUIDGenerator.getUUID()+"} "+msg;
            this.logger.error(msg);
        } catch (Throwable t) {
            printOut(t, msg);
        }
    }

     /**
     * 1. 기능 : error Level의 로그를 출력
     * 2. 처리 개요 :
     *    - error Level의 로그를 출력
     * 3. 주의사항
     *
     * @param	msg Object
     * @param	t	Throwable
     * @exception
     **/
    public void error(String msg, Throwable t) {
        try {
            //msg = " {"+UUIDGenerator.getUUID()+"} "+msg;
            this.logger.error(msg, t);
        } catch (Throwable th) {
            printOut(t, th, msg);
        }
    }

     /**
     * 1. 기능 : error Level의 로그를 출력
     * 2. 처리 개요 :
     *    - error Level의 로그를 출력 (ErrorCode를 사용할 경우)
     * 3. 주의사항
     *
     * @param	code String
     * @param	msg Object
     * @exception
     **/
    public void error(String code, Object msg) {
        try {
            //msg = " {"+UUIDGenerator.getUUID()+"} "+msg;
            this.logger.error(format(code,msg));
        } catch (Throwable t) {
            printOut(t, msg);
        }
    }

     /**
     * 1. 기능 : error Level의 로그를 출력
     * 2. 처리 개요 :
     *    - error Level의 로그를 출력 (ErrorCode를 사용할 경우)
     * 3. 주의사항
     *
     * @param	code String
     * @param	msg Object
     * @param	t	Throwable
     * @exception
     **/
    public void error(String code, Object msg, Throwable t) {
        try {
            //msg = " {"+UUIDGenerator.getUUID()+"} "+msg;
            this.logger.error(format(code,msg), t);
        } catch (Throwable th) {
            printOut(t, th, msg);
        }
    }

     /**
     * 1. 기능 : fatal Level의 로그를 출력
     * 2. 처리 개요 :
     *    - fatal Level의 로그를 출력
     * 3. 주의사항
     *
     * @param	msg Object
     * @exception
     **/
    public void fatal(Object msg) {
        try {
            //msg = " {"+UUIDGenerator.getUUID()+"} "+msg;
            this.logger.fatal(msg);
        } catch (Throwable t) {
            printOut(t, msg);
        }
    }

    /**
     * 1. 기능 : fatal Level의 로그를 출력
     * 2. 처리 개요 :
     *    - fatal Level의 로그를 출력 (ErrorCode를 사용할 경우)
     * 3. 주의사항
     *
     * @param	code String
     * @param	msg Object
     * @exception
     **/
    public void fatal(String code, Object msg) {
        try {
            //msg = " {"+UUIDGenerator.getUUID()+"} "+msg;
            this.logger.fatal(format(code,msg));
        } catch (Throwable t) {
            printOut(t, msg);
        }
    }

         /**
     * 1. 기능 : fatal Level의 로그를 출력
     * 2. 처리 개요 :
     *    - fatal Level의 로그를 출력
     * 3. 주의사항
     *
     * @param	msg Object
     * @param	t	Throwable
     * @exception
     **/
     public void fatal(Object msg, Throwable t) {
        try {
            //msg = " {"+UUIDGenerator.getUUID()+"} "+msg;
            this.logger.fatal(msg, t);
        } catch (Throwable th) {
            printOut(t, th, msg);
        }
    }

	/**
     * 1. 기능 : fatal Level의 로그를 출력
     * 2. 처리 개요 :
     *    - fatal Level의 로그를 출력 (ErrorCode를 사용할 경우)
     * 3. 주의사항
     *
     * @param	code String
     * @param	msg Object
     * @param	t	Throwable
     * @exception
     **/
    public void fatal(String code, Object msg, Throwable t) {
        try {
            //msg = " {"+UUIDGenerator.getUUID()+"} "+msg;
            this.logger.fatal(format(code,msg), t);
        } catch (Throwable th) {
            printOut(t, th, msg);
        }
    }

    /**
     * 1. 기능 : debug Level의 로그를 출력 가능여부 확인
     * 2. 처리 개요 :
     *    - debug Level의 로그를 출력 가능여부 확인
     * 3. 주의사항
     *
     * @return	boolean
     * @exception
     **/
    public boolean isDebugEnabled() {
        return this.logger.isDebugEnabled();
    }

    /**
     * 1. 기능 : info Level의 로그를 출력 가능여부 확인
     * 2. 처리 개요 :
     *    - info Level의 로그를 출력 가능여부 확인
     * 3. 주의사항
     *
     * @return	boolean
     * @exception
     **/
    public boolean isInfoEnabled() {
        return this.logger.isInfoEnabled();
    }

    /**
     * 1. 기능 : warn Level의 로그를 출력 가능여부 확인
     * 2. 처리 개요 :
     *    - warn Level의 로그를 출력 가능여부 확인
     * 3. 주의사항
     *
     * @return	boolean
     * @exception
     **/
    public boolean isWarnEnabled() {
        return this.logger.isEnabledFor(Priority.WARN);
    }

    /**
     * 1. 기능 : Log4j를 통해 로그를 남길 수 없는 경우 표준출력 제공
     * 2. 처리 개요 :
     *    - Log4j를 통해 로그를 남길 수 없는 경우 표준출력 제공
     * 3. 주의사항
     *
     * @param	t	Throwable
     * @param	msg Object
     * @exception
     **/
    private void printOut(Throwable t, Object msg) {
        System.out.println("can't write to log file!! Because of " + t);
        System.out.println("the message will be printed to System.out");
        System.out.println(msg);
    }

    /**
     * 1. 기능 : Log4j를 통해 로그를 남길 수 없는 경우 표준출력 제공
     * 2. 처리 개요 :
     *    - Log4j를 통해 로그를 남길 수 없는 경우 표준출력 제공
     * 3. 주의사항
     *
     * @param	t	Throwable
     * @param	th	Throwable
     * @param	msg Object
     * @exception
     **/
    private void printOut(Throwable t, Throwable th, Object msg) {
        System.out.println("can't write to log file!! Because of " + t);
        System.out.println("the message will be printed to System.out");
        System.out.println(th);
        System.out.println(msg);
    }

    /**
     * 1. 기능 : ErrorCode와 메시지의 출력 포맷을 정의
     * 2. 처리 개요 :
     *    - ErrorCode와 메시지의 출력 포맷을 정의
     * 3. 주의사항
     *
     * @param	code String
     * @param	msg Object
     * @exception
     **/
    private String format(String code, Object msg) {
        StringBuffer sb = new StringBuffer();
        sb.append("[").append(code).append("] ").append(msg);
        return sb.toString();
    }

    /*
        debug(1000) : debug, info, warn, error, fatal
        info (2000) : info, warn, error, fatal
        warn (3000) : warn, error, fatal
        error(4000) : error, fatal
        fatal(5000) : fatal

        if(logger.isError) logger.error();
    */
    public boolean isDebug() {
        return this.logger.getLevel().toInt() <= Level.DEBUG_INT;  // 1000
    }
    public boolean isInfo() {
        return this.logger.getLevel().toInt() <= Level.INFO_INT;  // 2000
    }
    public boolean isWarn() {
        return this.logger.getLevel().toInt() <= Level.WARN_INT;  // 3000
    }
    public boolean isError() {
        return this.logger.getLevel().toInt() <= Level.ERROR_INT;  // 4000
    }
    public boolean isFatal() {
        return this.logger.getLevel().toInt() <= Level.FATAL_INT;  // 5000
    }
}