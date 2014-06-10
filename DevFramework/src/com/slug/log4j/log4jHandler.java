package com.slug.log4j;

import java.util.Hashtable;


import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.Priority;




public class log4jHandler {

	/**
	 * 생성자
	 */
    private log4jHandler() {}
    
    /**
     * Logger Single instacne 변수.
     */
    private static log4jHandler logger_ins = null;
    
    
    protected static Hashtable loggers = new Hashtable(5);

    //Default Console Logger 명
	private static String defConLoggerName = "DefaultConsoleLogger";
	
	//static org.apache.log4j.Logger defConLogger;

    //Log4j Looger 객체
    private static org.apache.log4j.Logger logger;
	/**
     * SingleTon으로 Logger를 생성한다. Logger가 null일 경우에만
     * Logger를 생성하여 return한다.
     *
     * @return AppConfig의 instance.
     * @throws AppConfigException configuration error가 발생할 경우.
     */
    public static synchronized log4jHandler getInstance(String loggername) {
    	
    	if(logger_ins == null){
    		logger_ins = new log4jHandler();
    		initLogger(loggername);
    	}
    	
    	return logger_ins;
    }


    private static void initLogger(String loggername){
		defConLoggerName = loggername;
		org.apache.log4j.Logger defConLogger = org.apache.log4j.Logger.getLogger(loggername);
		defConLogger.setAdditivity(false);
		defConLogger.setLevel(org.apache.log4j.Level.DEBUG);
		defConLogger.removeAllAppenders();
		ConsoleAppender defConAppender = new ConsoleAppender(new PatternLayout("<%d{yyyy-MM-dd HH:mm:ss.SSS}> %-5p [Def.Con.Logger] %m%n"));
		defConLogger.addAppender(defConAppender);
	
		loggers.put(defConLoggerName,  defConLogger);

    
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
			//logger.warn("****** '"+ loggerName +"' 이름의 Logger 가 정의되지 않았음. 배치 디폴트 콘솔 Logger 리턴.");
		}
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
