package com.slug.log4j;

import java.util.Hashtable;


import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.Priority;




public class log4jHandler {

	/**
	 * ����
	 */
    private log4jHandler() {}
    
    /**
     * Logger Single instacne ����.
     */
    private static log4jHandler logger_ins = null;
    
    
    protected static Hashtable loggers = new Hashtable(5);

    //Default Console Logger ��
	private static String defConLoggerName = "DefaultConsoleLogger";
	
	//static org.apache.log4j.Logger defConLogger;

    //Log4j Looger ��ü
    private static org.apache.log4j.Logger logger;
	/**
     * SingleTon���� Logger�� ���Ѵ�. Logger�� null�� ��쿡��
     * Logger�� ���Ͽ� return�Ѵ�.
     *
     * @return AppConfig�� instance.
     * @throws AppConfigException configuration error�� �߻��� ���.
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
     * 1. ��� : Logger factory method
     * 2. ó�� ���� :
     *    - Logger Factory�� ���Ѵ�.
     * 3. ���ǻ���
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
			//logger.warn("****** '"+ loggerName +"' �̸��� Logger �� ���ǵ��� �ʾ���. ��ġ ����Ʈ �ܼ� Logger ����.");
		}
		return logger;
    }
	/**
     * 1. ��� : Banner ������ �α׸� ���
     * 2. ó�� ���� :
     *    - Banner ������ �α׸� ���
     * 3. ���ǻ���
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
     * 1. ��� : debug Level�� �α׸� ���
     * 2. ó�� ���� :
     *    - debug Level�� �α׸� ���
     * 3. ���ǻ���
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
     * 1. ��� : debug Level�� �α׸� ���
     * 2. ó�� ���� :
     *    - debug Level�� �α׸� ���
     * 3. ���ǻ���
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
     * 1. ��� : debug Level�� �α׸� ���
     * 2. ó�� ���� :
     *    - debug Level�� �α׸� ���
     * 3. ���ǻ���
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
     * 1. ��� : debug Level�� �α׸� ���
     * 2. ó�� ���� :
     *    - debug Level�� �α׸� ��� (ErrorCode�� ����� ���)
     * 3. ���ǻ���
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
     * 1. ��� : info Level�� �α׸� ���
     * 2. ó�� ���� :
     *    - info Level�� �α׸� ���
     * 3. ���ǻ���
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
     * 1. ��� : info Level�� �α׸� ���
     * 2. ó�� ���� :
     *    - info Level�� �α׸� ��� (ErrorCode�� ����� ���)
     * 3. ���ǻ���
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
     * 1. ��� : info Level�� �α׸� ���
     * 2. ó�� ���� :
     *    - info Level�� �α׸� ���
     * 3. ���ǻ���
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
     * 1. ��� : info Level�� �α׸� ���
     * 2. ó�� ���� :
     *    - info Level�� �α׸� ��� (ErrorCode�� ����� ���)
     * 3. ���ǻ���
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
     * 1. ��� : warn Level�� �α׸� ���
     * 2. ó�� ���� :
     *    - warn Level�� �α׸� ���
     * 3. ���ǻ���
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
     * 1. ��� : warn Level�� �α׸� ���
     * 2. ó�� ���� :
     *    - warn Level�� �α׸� ��� (ErrorCode�� ����� ���)
     * 3. ���ǻ���
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
     * 1. ��� : warn Level�� �α׸� ���
     * 2. ó�� ���� :
     *    - warn Level�� �α׸� ���
     * 3. ���ǻ���
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
     * 1. ��� : warn Level�� �α׸� ���
     * 2. ó�� ���� :
     *    - warn Level�� �α׸� ��� (ErrorCode�� ����� ���)
     * 3. ���ǻ���
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
     * 1. ��� : error Level�� �α׸� ���
     * 2. ó�� ���� :
     *    - error Level�� �α׸� ���
     * 3. ���ǻ���
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
     * 1. ��� : error Level�� �α׸� ���
     * 2. ó�� ���� :
     *    - error Level�� �α׸� ���
     * 3. ���ǻ���
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
     * 1. ��� : error Level�� �α׸� ���
     * 2. ó�� ���� :
     *    - error Level�� �α׸� ��� (ErrorCode�� ����� ���)
     * 3. ���ǻ���
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
     * 1. ��� : error Level�� �α׸� ���
     * 2. ó�� ���� :
     *    - error Level�� �α׸� ��� (ErrorCode�� ����� ���)
     * 3. ���ǻ���
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
     * 1. ��� : fatal Level�� �α׸� ���
     * 2. ó�� ���� :
     *    - fatal Level�� �α׸� ���
     * 3. ���ǻ���
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
     * 1. ��� : fatal Level�� �α׸� ���
     * 2. ó�� ���� :
     *    - fatal Level�� �α׸� ��� (ErrorCode�� ����� ���)
     * 3. ���ǻ���
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
     * 1. ��� : fatal Level�� �α׸� ���
     * 2. ó�� ���� :
     *    - fatal Level�� �α׸� ���
     * 3. ���ǻ���
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
     * 1. ��� : fatal Level�� �α׸� ���
     * 2. ó�� ���� :
     *    - fatal Level�� �α׸� ��� (ErrorCode�� ����� ���)
     * 3. ���ǻ���
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
     * 1. ��� : debug Level�� �α׸� ��� ���ɿ��� Ȯ��
     * 2. ó�� ���� :
     *    - debug Level�� �α׸� ��� ���ɿ��� Ȯ��
     * 3. ���ǻ���
     *
     * @return	boolean
     * @exception
     **/
    public boolean isDebugEnabled() {
        return this.logger.isDebugEnabled();
    }

    /**
     * 1. ��� : info Level�� �α׸� ��� ���ɿ��� Ȯ��
     * 2. ó�� ���� :
     *    - info Level�� �α׸� ��� ���ɿ��� Ȯ��
     * 3. ���ǻ���
     *
     * @return	boolean
     * @exception
     **/
    public boolean isInfoEnabled() {
        return this.logger.isInfoEnabled();
    }

    /**
     * 1. ��� : warn Level�� �α׸� ��� ���ɿ��� Ȯ��
     * 2. ó�� ���� :
     *    - warn Level�� �α׸� ��� ���ɿ��� Ȯ��
     * 3. ���ǻ���
     *
     * @return	boolean
     * @exception
     **/
    public boolean isWarnEnabled() {
        return this.logger.isEnabledFor(Priority.WARN);
    }

    /**
     * 1. ��� : Log4j�� ���� �α׸� ���� �� ��� ��� ǥ����� ����
     * 2. ó�� ���� :
     *    - Log4j�� ���� �α׸� ���� �� ��� ��� ǥ����� ����
     * 3. ���ǻ���
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
     * 1. ��� : Log4j�� ���� �α׸� ���� �� ��� ��� ǥ����� ����
     * 2. ó�� ���� :
     *    - Log4j�� ���� �α׸� ���� �� ��� ��� ǥ����� ����
     * 3. ���ǻ���
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
     * 1. ��� : ErrorCode�� �޽����� ��� ������ ����
     * 2. ó�� ���� :
     *    - ErrorCode�� �޽����� ��� ������ ����
     * 3. ���ǻ���
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
