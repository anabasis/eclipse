package com.tg.slug.logging;

/**
 * @(#) Logging.java
 * @version KDSKIT
 * Copyright
 * All rights reserved.
 * 작성 :
 * @author 김동식, dongskim@solupia.co.kr
 *         SOLUPIA e-Biz Team
 *
 */

import java.io.PrintWriter;
import java.util.Observable;
import java.util.Observer;

//import com.tg.jfound.config.AppConfig;

import com.tg.slug.config.AppConfigManager;
import com.tg.slug.io.NullPrintWriter;
import com.tg.slug.io.TeePrintWriter;

/**
 * FRAME WORK util를 구현할 때나 FRAME WORK util를 이용할 때 개발자가 debugging하기 쉽게 하기 위해서나
 * 일반적인 log를 필요로 하는 경우에 사용할 수 있다.
 * 
 * 
 */
public class Logging implements Observer {
	/**
	 * FRAME WORK utill Component 클래스를 잘못 사용한 경우를 명시하는 Constant. 개발자가 FRAME
	 * WORK을 이용하여 개발시 참조해야할 Log로 FRAME WORK Component 클래스 내에서 발생한다.
	 * 
	 */
	final static int DEV = 0;

	/**
	 * FRAME WORK util에서 가장 낮은 레벨의 심각한 ERROR를 명시하는 Constant.
	 */

	final static int SYS = 1;

	/**
	 * 일반 개발자들이 사용할 수 있는 것으로 개발자의 logic 오류거나, 혹은 업무적으로 절대 일어나서는 안될 ERROR를 명시하는
	 * Constant.
	 */

	final static int ERR = 2;

	/**
	 * Business적으로 충분히 일어날 수 있는 ERROR를 명시하는 Constant. 이러한 ERROR가 발생할 시 추후에 반드시
	 * 조치 되어야 할 성격의 상황일 때 사용한다.
	 */

	final static int WARN = 3;

	/**
	 * Information을 기록을 명시하는 Constant. 이 상황은 ERROR가 아니며, 사용자가 최초 시스템 접속 시 로그를
	 * 남기는 것과 같이 업무적으로 Log를 남겨야 할 때 사용한다.
	 */

	final static int INFO = 4;

	/**
	 * 개발시에 Debugging을 명시하는 Constant. 대부분 운영시에는 Log로 남기지 않을 성질의 것이 될 것이다.
	 */

	final static int DEBUG = 5;
	final static int LOG_COUNT = 6;

	/**
	 * Log Functionality의 작동여부를 결정하는 flag를 저장하는 Constant.
	 * webApp.logging.dev.trace webApp.logging.sys.trace
	 * webApp.logging.err.trace webApp.logging.warn.trace
	 * webApp.logging.info.trace webApp.logging.debug.trace
	 */

	final static String[] TRACE_FLAG = { "webApp.logging.dev.trace",
			"webApp.logging.sys.trace", "webApp.logging.err.trace",
			"webApp.logging.warn.trace", "webApp.logging.info.trace",
			"webApp.logging.debug.trace" };

	/**
	 * 해당 Log Functionality가 write하는 파일을 구별하기 위한 Constant. (sys, err, warn,
	 * info, debug, dbwrap, dev)
	 */
	final static String[] LOG_FILE_EXT = { "dev", "sys", "err", "warn", "info",
			"debug" };

	/**
	 * Log Functionality를 명시하는 PrintWriter로 Functionality 개수만큼 생성된다.
	 */
	static PrintWriter logArr[] = new PrintWriter[LOG_COUNT];

	/**
	 * dev Functionality를 명시하는 PrintWriter.
	 */
	public static PrintWriter dev = logArr[DEV];

	/**
	 * SYS Functionality를 명시하는 PrintWriter.
	 */
	public static PrintWriter sys = logArr[SYS];

	/**
	 * ERR Functionality를 명시하는 PrintWriter.
	 */
	public static PrintWriter err = logArr[ERR];

	/**
	 * WARN Functionality를 명시하는 PrintWriter.
	 */
	public static PrintWriter warn = logArr[WARN];

	/**
	 * INFO Functionality를 명시하는 PrintWriter.
	 */
	public static PrintWriter info = logArr[INFO];
	/**
	 * DEBUG Functionality를 명시하는 PrintWriter.
	 */
	public static PrintWriter debug = logArr[DEBUG];

	/**
	 * Log파일 format을 위한 class(LoggingFormat.class) instance.
	 */
	static LoggingFormat format;

	/**
	 * init()을 호출하여 초기화 한후 Configure addObserver에 Logging를 등록하여 configuration정보가
	 * 변경되면 Log속성 역시 refresh 되도록 하게 한다.
	 */
	static {
		try {
			System.out.println("Logging: STATIC BLOCK");
			init();
			// AppConfigManager conf = AppConfigManager.getInstance();
			// conf.addObserver( new Logging() );

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Logging() {
	}

	/**
	 * Log파일의 format을 담당하는 class(com.lgeds.sys.log.format에서 설정된 class)를 생성하여
	 * class 변수 LoggingFormat에 셋팅한다. 만약 해당 class가 존재하지 않는 등 Exception이 발생하면
	 * default format class인 LoggingFormat을 생성시킨다.
	 * 
	 * @return format을 담당하는 객체 instance.
	 */
	private static LoggingFormat getLogFormat() {
		LoggingFormat format = null;
		try {
			AppConfigManager conf = AppConfigManager.getInstance();
			Class formatClass = Class
					.forName(conf.get("webApp.logging.format"));
			format = (LoggingFormat) formatClass.newInstance();
		} catch (Exception e) {
			format = new LoggingFormat();
			System.out.println("LoggingFormat initialization fail : "
					+ e.getMessage());
			System.out
					.println("LoggingFormat is reinitialized with LoggingFormat");
		}

		return format;
	}

	/**
	 * Logging class를 초기화 하는 method로 Log Functionality 동작속성 및 configuration파일의
	 * webApp.logging.autoflush 속성에 따라 Log Functionality의 LoggingWriter를 생성시킨다.
	 * 단, SYS와 ERR Log는 LTreePrintWriter를 이용하여 파일과 console양방향으로 출력된다.
	 */
	private static void init() {

		try {
			AppConfigManager conf = AppConfigManager.getInstance();
			boolean autoFlush = conf.getBoolean("webApp.logging.autoflush"); // AUTO
																				// Flush

			format = getLogFormat(); // format

			for (int mode = 0; mode < LOG_COUNT; mode++) {
				System.out.println(TRACE_FLAG[mode] + " = "
						+ conf.get(TRACE_FLAG[mode]));
				switch (mode) {

				case DEV:
					if (conf.get(TRACE_FLAG[mode]).trim().equals("STDONLY")) {
						logArr[mode] = new LoggingDevWriter(System.out,
								autoFlush);
					} else if (conf.get(TRACE_FLAG[mode]).trim()
							.equals("FILEONLY")) {
						logArr[mode] = new LoggingFileWriter(mode, autoFlush);
					} else if (conf.get(TRACE_FLAG[mode]).trim()
							.equals("STDFILE")) {
						logArr[mode] = new TeePrintWriter(new LoggingDevWriter(
								System.out, autoFlush), new LoggingFileWriter(
								mode, autoFlush));
					} else {
						logArr[mode] = new NullPrintWriter();
					}

					break;
				case SYS:
					if (conf.get(TRACE_FLAG[mode]).trim().equals("STDONLY")) {
						logArr[mode] = new LoggingSysWriter(System.out,
								autoFlush);
					} else if (conf.get(TRACE_FLAG[mode]).trim()
							.equals("FILEONLY")) {
						logArr[mode] = new LoggingFileWriter(mode, autoFlush);
					} else if (conf.get(TRACE_FLAG[mode]).trim()
							.equals("STDFILE")) {
						logArr[mode] = new TeePrintWriter(new LoggingSysWriter(
								System.out, autoFlush), new LoggingFileWriter(
								mode, autoFlush));
					} else {
						logArr[mode] = new NullPrintWriter();
					}

					break;
				case ERR:

					if (conf.get(TRACE_FLAG[mode]).trim().equals("STDONLY")) {
						logArr[mode] = new LoggingErrWriter(System.out,
								autoFlush);
					} else if (conf.get(TRACE_FLAG[mode]).trim()
							.equals("FILEONLY")) {
						logArr[mode] = new LoggingFileWriter(mode, autoFlush);
					} else if (conf.get(TRACE_FLAG[mode]).trim()
							.equals("STDFILE")) {
						logArr[mode] = new TeePrintWriter(new LoggingErrWriter(
								System.out, autoFlush), new LoggingFileWriter(
								mode, autoFlush));
					} else {
						logArr[mode] = new NullPrintWriter();
					}

					break;

				case WARN:
					if (conf.get(TRACE_FLAG[mode]).trim().equals("STDONLY")) {
						logArr[mode] = new LoggingWarnWriter(System.out,
								autoFlush);
					} else if (conf.get(TRACE_FLAG[mode]).trim()
							.equals("FILEONLY")) {
						logArr[mode] = new LoggingFileWriter(mode, autoFlush);
					} else if (conf.get(TRACE_FLAG[mode]).trim()
							.equals("STDFILE")) {
						logArr[mode] = new TeePrintWriter(
								new LoggingWarnWriter(System.out, autoFlush),
								new LoggingFileWriter(mode, autoFlush));
					} else {
						logArr[mode] = new NullPrintWriter();
					}

					break;
				case INFO:
					if (conf.get(TRACE_FLAG[mode]).trim().equals("STDONLY")) {
						logArr[mode] = new LoggingInfoWriter(System.out,
								autoFlush);
					} else if (conf.get(TRACE_FLAG[mode]).trim()
							.equals("FILEONLY")) {
						logArr[mode] = new LoggingFileWriter(mode, autoFlush);
					} else if (conf.get(TRACE_FLAG[mode]).trim()
							.equals("STDFILE")) {
						logArr[mode] = new TeePrintWriter(
								new LoggingInfoWriter(System.out, autoFlush),
								new LoggingFileWriter(mode, autoFlush));
					} else {
						logArr[mode] = new NullPrintWriter();
					}

					break;

				case DEBUG:

					if (conf.get(TRACE_FLAG[mode]).trim().equals("STDONLY")) {
						logArr[mode] = new LoggingDebugWriter(System.out,
								autoFlush);
					} else if (conf.get(TRACE_FLAG[mode]).trim()
							.equals("FILEONLY")) {
						logArr[mode] = new LoggingFileWriter(mode, autoFlush);
					} else if (conf.get(TRACE_FLAG[mode]).trim()
							.equals("STDFILE")) {
						logArr[mode] = new TeePrintWriter(
								new LoggingDebugWriter(System.out, autoFlush),
								new LoggingFileWriter(mode, autoFlush));
					} else {
						logArr[mode] = new NullPrintWriter();
					}

					break;

				default:
					break;
				} // switch

			} // for

			dev = logArr[DEV];
			sys = logArr[SYS];
			err = logArr[ERR];
			warn = logArr[WARN];
			info = logArr[INFO];
			debug = logArr[DEBUG];

		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Logging: initialize success");
	}

	/**
	 * Logging class 초기화를 위해 init()을 호출한다.
	 * 
	 * @param o
	 *            the observable object.
	 * @param arg
	 *            notifyObservers method에 전달되어지는 argument.
	 */
	public void update(Observable o, Object arg) {
		init();
	}
}
