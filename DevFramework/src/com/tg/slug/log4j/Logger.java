package com.tg.slug.log4j;

import com.tg.slug.config.PropManager;
import com.tg.slug.util.StringUtil;

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
 * 1. ��� : Log4j�� ���� ȭ�Ϸα׸� ���ϴ� Utility Class
 * 2. ó�� ���� :
 *
 * 3. ���ǻ���
 *
 * @author  :
 * @version : v 1.0.0
 * @see :
 * @since   :
 */
public class Logger  implements LogKeys {

	//Logger list (������Ƽ�� ������ Logger �� Dafault Console Logger �� ��´�.)
    protected static Hashtable loggers = new Hashtable(5);

	//Default Console Logger ��
	private static String defConLoggerName = "DefaultConsoleLogger";

    //Log4j Looger ��ü
    private org.apache.log4j.Logger logger;


	//Default Console Logger �� (DEBUG ���� ����)
    static {

		org.apache.log4j.Logger defConLogger = org.apache.log4j.Logger.getLogger(defConLoggerName);
		defConLogger.setAdditivity(false);
		defConLogger.setLevel(org.apache.log4j.Level.DEBUG);
		defConLogger.removeAllAppenders();
		ConsoleAppender defConAppender = new ConsoleAppender(new PatternLayout("<%d{yyyy-MM-dd HH:mm:ss.SSS}> %-5p [Def.Con.Logger] %m%n"));
		defConLogger.addAppender(defConAppender);

		loggers.put(defConLoggerName, new Logger(defConLogger));
    }

	//�� Logger Ŭ�������� System.out ��� ����� Default Console Logger ��ü ����
	private static Logger selfLogger = getLogger();


	//�⺻ �α� ���� ���
	static {
		
        selfLogger.banner("EAI �����ӿ� log4j Logger �⺻����");
        selfLogger.debug("�� EAI Logger�� 1 (Logger.LOGGER_DEFAULT)   : " + LOGGER_DEFAULT);
        //selfLogger.debug("�� EAI Logger�� 2 (Logger.LOGGER_EXCEPTION) : " + LOGGER_EXCEPTION);
        selfLogger.debug("�� log4j ROOT LOGGER LEVEL                  : " + org.apache.log4j.Logger.getRootLogger().getLevel());
        selfLogger.debug("�� ����Ʈ �ܼ� Logger LEVEL                 : " + selfLogger.getLevel());
	}



	static public synchronized Logger getLogger() {
		return getLogger(null);
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
			logger.warn("****** '"+ loggerName +"' �̸��� Logger �� ���ǵ��� �ʾ���. ��ġ ����Ʈ �ܼ� Logger ����.");
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
     * 1. ��� : PropManager�� ���� Log4j�� ȯ���� ����
     * 2. ó�� ���� :
     *    - PropManager�� ���� Log4j�� ȯ���� ����
     * 3. ���ǻ���
     *
     * @exception
     **/
    public static void doConfigure()
    {
        try
        {
			//========================================================
			//1. ���� Logger �⺻ ������Ƽ ���� üũ
			//========================================================
			PropManager pmanager = PropManager.getInstance();
            Properties loggerInfo= pmanager.getProperties(LOGGER_INFO);

			//�ʱ�ȭ�� �޸𸮿� ������ Logger �̸� ����Ʈ
            String loggerList    = loggerInfo.getProperty(INITIAL_LOGGER_LIST);
            if(loggerList==null || loggerList.equals("")) throw new RuntimeException("RECEAICUL005");

			//�ʱ�ȭ�� Logger �� �������� ���� Appender �̸� ����Ʈ
            String appenderList  = loggerInfo.getProperty(INITIAL_APPENDER_LIST);
            System.out.println("appenderList : " + appenderList);
            if(appenderList==null || appenderList.equals("")) throw new RuntimeException("RECEAICUL006");


			//========================================================
			//2. ������Ƽ�� ������ ��� Appender �� �� HashMap�� ����
			//========================================================
			//���� Logger ��� �ʿ��� Appender ã�Ƽ� �ٿ���
            String[] appenderNameList = StringUtil.getStrArray(appenderList, ",");
            HashMap appenderPool = new HashMap();

            for (int i=0; i<appenderNameList.length; i++) {
                FileAppender appender = createFileAppender(appenderNameList[i]);	//Appender ��
                appenderPool.put( appenderNameList[i], appender );
            }

			//========================================================
			//3. ������Ƽ�� ������ ��� ���� Logger ��
			//========================================================
            String[] loggerNameList = StringUtil.getStrArray(loggerList, ",");		//log4j Logger ��
            org.apache.log4j.Logger logger = null;

            for(int i=0; i<loggerNameList.length; i++) {
                logger = createLogger(loggerNameList[i]);

                String loggerAppenderName = pmanager.getProperty(loggerNameList[i], LOGGER_APPENDER_LIST);
				String[] loggerAppenderNameList = StringUtil.getStrArray(loggerAppenderName, ",");

				for (int j=0; j<loggerAppenderNameList.length; j++) {
					FileAppender appender = (FileAppender)appenderPool.get(loggerAppenderNameList[j]);
					if (appender==null) continue;
					logger.addAppender(appender);
					selfLogger.debug("  - ���Ϸΰ� '"+ logger.getName() +"' �� '"+ loggerAppenderNameList[j] +"' Appender ����.");
				}

				loggers.put(loggerNameList[i], new Logger(logger));
            }


        } catch(Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 1. ��� : Log4j�� FileAppender�� �߰�
     * 2. ó�� ���� :
     *    - Log4j API�� ���� FileAppender�� �߰�
     * 3. ���ǻ���
     *
     * @exception
     **/
    public static FileAppender createFileAppender(String appenderName) {

		PropManager pmanager = PropManager.getInstance();

		//�α����� ���� ���丮 �����
    System.out.println("#### appenderName : " + appenderName);        
		String logDirectory  = pmanager.getProperty(appenderName, APPENDER_LOG_DIRECOTRY);
    System.out.println("#### logDirectory : " + logDirectory);
		if (logDirectory==null || logDirectory.equals("")) throw new RuntimeException("RECEAICUL004");

		//�α����� ��
        String logFilename = pmanager.getProperty(appenderName, APPENDER_LOG_FILENAME);
        if (logFilename==null || logFilename.equals("")) throw new RuntimeException("RECEAICUL007");

		//�α����� ���ں� �� ��Ģ
        String datePattern = pmanager.getProperty(appenderName, APPENDER_DATE_PATTERN);
        if (datePattern==null || datePattern.equals("")) datePattern = "'.'yyyy-MM-dd";

		//�α� ���� ���ڿ�
		String patternLayout = pmanager.getProperty(appenderName, APPENDER_PATTERN_LAYOUT);
		if (patternLayout==null || patternLayout.equals("")) patternLayout = "<%d{yyyy-MM-dd HH:mm:ss.SSS}> %-5p %m%n";

		//�α����� �α�Append ����
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
		selfLogger.debug("  - ���Ϸΰ� '"+ appenderName +"' Appender ���.");

        return appender;
    }

   /**
     * 1. ��� : Log4j�� logger�� �߰�
     * 2. ó�� ���� :
     *    - Log4j API�� ���� logger�� �߰�
     * 3. ���ǻ���
     *
     * @exception
     **/
    public static org.apache.log4j.Logger createLogger(String loggerName) {

		PropManager pmanager = PropManager.getInstance();


		//log4j Logger �α׷��� �Ӽ�
        String logLevel = pmanager.getProperty(loggerName, LOGGER_LEVEL);
        if (logLevel==null || logLevel.equals("")) logLevel = "debug";

		//log4j Logger �ܼ� ��¿���
		String console = pmanager.getProperty(loggerName, LOGGER_CONSOLE);
		if (console==null || console.equals("")) console = "on";

		//log4j Logger aditivity �Ӽ�
        String aditivity = pmanager.getProperty(loggerName, LOGGER_ADITIVITY);
        if (aditivity==null || aditivity.equals("")) aditivity = "false";


        //String localServer = EAIServerManager.getInstance().getLocalServerName();
        org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(loggerName);
		selfLogger.debug("  - ���Ϸΰ� '"+ logger.getName() +"' Logger ���.");

       logger.removeAllAppenders();

		try {
            logger.setAdditivity(new Boolean(aditivity).booleanValue());
        } catch(Exception e) {
            logger.setAdditivity(false);
        }

		if ((console!=null) && console.equals("on")) {
			ConsoleAppender conAppender = new ConsoleAppender(new PatternLayout("<%d{yyyy-MM-dd HH:mm:ss.SSS}> %-5p ["+ loggerName +"] %m%n"));
			logger.addAppender(conAppender);
			selfLogger.debug("  - ���Ϸΰ� '"+ logger.getName() +"' Logger �� �ܼ� Appender ����.");
		}

		logger.setPriority (logLevel.toLowerCase().equals("debug")? Level.DEBUG :
		                    logLevel.toLowerCase().equals("info" )? Level.INFO  :
		                    logLevel.toLowerCase().equals("warn" )? Level.WARN  :
		                    logLevel.toLowerCase().equals("error")? Level.ERROR :
		                    logLevel.toLowerCase().equals("fatal")? Level.FATAL : Level.DEBUG );

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