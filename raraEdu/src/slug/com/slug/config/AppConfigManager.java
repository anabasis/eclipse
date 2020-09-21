package com.slug.config;

import java.io.File;
import java.io.FileInputStream;
import java.util.Enumeration;
import java.util.Observable;
import java.util.Properties;

import javax.servlet.ServletContext;

import com.slug.lifecycle.Lifecycle;
import com.slug.lifecycle.LifecycleException;
import com.slug.lifecycle.LifecycleListener;
import com.slug.lifecycle.LifecycleSupport;
import com.slug.util.CharConversion;

public class AppConfigManager extends Observable implements Lifecycle {
	/**
	 * AppConfigManager Single Instance
	 */
	private static AppConfigManager instance;// = new AppConfigManager();

	/**
	 * LifeccyleSupport object
	 */
	private LifecycleSupport lifecycle = new LifecycleSupport(this);

	private boolean started;

	private static String ROOT_PATH = null;

	private Properties props = null;

	private String conf_file_name = null;

	private long last_modified = 0;

	private AppConfigManager() {
		// servers = new HashMap();

	}

	public static AppConfigManager getInstance(String path)
			throws AppConfigException {

		if ((instance == null) && (ROOT_PATH == null)) {
			ROOT_PATH = path;
			instance = new AppConfigManager();
		}
		// return conf_ins;
		return instance;
	}

	public static AppConfigManager getInstance() throws AppConfigException {

		if ((instance == null) && (ROOT_PATH == null)) {
			instance = new AppConfigManager();
		}
		// return conf_ins;
		return instance;
	}

	public static String getAbsoluteRootPath() {

		return ROOT_PATH;
	}
	
	public void start(String prop_file) throws LifecycleException {
		if (started)
			throw new LifecycleException("RECEAICSM201");

		// Notify our interested LifecycleListeners
		lifecycle.fireLifecycleEvent(STARTING_EVENT, this);

		File default_file = new File("./", "webApp.conf");

		if (!"".equals(prop_file))
			conf_file_name = prop_file;
		else
			conf_file_name = System.getProperty("webApp.config.file",
					default_file.getAbsolutePath());

		try {
			refresh();
		} catch (AppConfigException ac) {
			ac.printStackTrace();
			throw (new LifecycleException(
					"Load Application Configuration Fail!!!"));
		}
		started = true;
		// Notify our interested LifecycleListeners
		lifecycle.fireLifecycleEvent(STARTED_EVENT, this);
	}

	public void stop() throws LifecycleException {
		// Validate and update our current component state
		if (!started)
			throw new LifecycleException("RECEAICSM203");

		// Notify our interested LifecycleListeners
		lifecycle.fireLifecycleEvent(STOPING_EVENT, this);

		started = false;
		// Notify our interested LifecycleListeners
		lifecycle.fireLifecycleEvent(STOPPED_EVENT, this);
	}

	public void addLifecycleListener(LifecycleListener listener) {
		lifecycle.addLifecycleListener(listener);
	}

	public LifecycleListener[] findLifecycleListeners() {
		return lifecycle.findLifecycleListeners();
	}

	public void removeLifecycleListener(LifecycleListener listener) {
		lifecycle.removeLifecycleListener(listener);
	}

	public boolean isStarted() {
		return this.started;
	}

	public String get(String key) throws AppConfigException {
		return getString(key);
	}

	public boolean getBoolean(String key) throws AppConfigException {
		try {
			System.out.println("key ----->" + key);
			System.out.println("props.getProperty(key) ----->"
					+ props.getProperty(key));
			return (new Boolean(props.getProperty(key))).booleanValue();
		} catch (Exception e) {
			throw new AppConfigException(
					"Check the webApp.conf File : Illegal Boolean Key : " + key,
					e);
		}
	}

	public int getInt(String key) throws AppConfigException {
		try {
			return Integer.parseInt(props.getProperty(key));
		} catch (Exception e) {
			throw new AppConfigException(
					"Check the webApp.conf File : Illegal Integer Key : " + key,
					e);
		}
	}

	public long getLong(String key) throws AppConfigException {
		try {
			return Long.parseLong(props.getProperty(key));
		} catch (Exception e) {
			throw new AppConfigException(
					"Check the webApp.conf File : Illegal Long Key : " + key, e);
		}
	}

	public Properties getProperties() {
		return props;
	}

	public String getString(String key) throws AppConfigException {
		try {
			String tmp = props.getProperty(key);
			if (tmp == null) {
				throw new Exception("value of key(" + key + ") is null");
			}
			return CharConversion.E2K(tmp);
		} catch (Exception e) {
			throw new AppConfigException(
					"Check the webApp.conf File : Illegal String Key : " + key,
					e);
		}
	}

	public synchronized void refresh() throws AppConfigException {

		File sys_file = new File(conf_file_name);
		if (!sys_file.canRead())
			throw new AppConfigException(conf_file_name + " is not readable");

		try {

			if ((last_modified != sys_file.lastModified()) || props == null) {
				last_modified = sys_file.lastModified();
				props = new Properties();

				FileInputStream sys_fin = new FileInputStream(sys_file);
				props.load(new java.io.BufferedInputStream(sys_fin));

				sys_fin.close();
				setChanged();
				notifyObservers();
			}

			for (Enumeration e = props.propertyNames(); e.hasMoreElements();) {
				String key = (String) e.nextElement();
				String value = props.getProperty(key);
				System.out.println(key + "=>[" + value + "]");
			}

		} catch (Exception e) {
			e.printStackTrace();
			throw new AppConfigException("Can't load configuration file", e);
		}
	}
}
