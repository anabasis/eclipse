package com.slug.web.proxy;

/**
 * @(#) Proxy.java
 * @version webApp
 * Copyright
 * All rights reserved.
 * 작성 :
 * @author
 *         
 *
 */

import javax.naming.*;
import javax.ejb.*;
import java.util.*;
import javax.rmi.PortableRemoteObject;
import java.rmi.RemoteException;

import com.slug.config.*;
import com.slug.exception.PException;
import com.slug.logging.*;

/**
 * JNDI Look up 및 narrow 서비스를 제공하는 클래스이다.
 *
 */
public class Proxy {

    private String provider = null;

	/**
	 * default 제품의 url/context로 Remote handle를 생성한다.
	 */
	public Proxy() {
		this("default");
	}

	/**
	 * 특정 제품의 url/context로 Remote handle를 생성한다.
	 */
	public Proxy(String newProvider) {
		this.provider = newProvider;
	}

	/**
	 * JNDI Look up 및 remote object를 요구하는 타입으로 cast할 수 있는 object로
	 * narrow 한다.
	 * @return java.lang.Object
	 * @param  jndiNm  java.lang.String
	 * @param  classNm java.lang.Class
	 * @exception com.proxy.ProxyException
	 */
    public Object getRemoteHandler(String jndiNm, Class classType) throws RemoteProxyException,AppConfigException {
        Object         remote = null;
        Properties     props = null;
        InitialContext ctx   = null;

        String FACTORY_NAME  = null;
        String PROVIDER_URL  = null;

        try {
        	AppConfigManager conf = AppConfigManager.getInstance();

            FACTORY_NAME  = conf.get("webApp.remote." + this.provider + ".contextFactory");
            PROVIDER_URL  = conf.get("webApp.remote." + this.provider + ".providerUrl");

        } catch (AppConfigException le) {
            FACTORY_NAME   = null;
            PROVIDER_URL   = null;
			throw new AppConfigException("\n(M) [Proxy.getLocalHandler() Using ERROR] fail in DLocalProxy.getRemoteHandler()\n Check the Proxy module ", le);
        }

        try {

            if (FACTORY_NAME != null) {
                props = new Properties();
                props.put(Context.INITIAL_CONTEXT_FACTORY, FACTORY_NAME);
                props.put(Context.PROVIDER_URL, PROVIDER_URL);

                ctx = new InitialContext(props); // obtain initial context

            } else {

                ctx = new InitialContext();
            }

            Object objref = ctx.lookup(jndiNm);

            remote = PortableRemoteObject.narrow(objref, classType);

        } catch (NamingException e) {
            throw new RemoteProxyException("\n(M) [Proxy.getRemoteHandler() Using ERROR] fail in Proxy.getRemoteHandler()\n Check the Proxy module "+" => " + e);
        }

        return remote;
    }

    /**
     * JNDI Look up 및 local object를 요구하는 타입으로 cast할 수 있는 object로
     * narrow 한다.
     * @return java.lang.Object
     * @param  jndiNm  java.lang.String
     * @param  classNm java.lang.Class
     * @exception com.proxy.DLocalProxyException
     */


    public Object getLocalHandler(String jndiNm, Class classType) throws RemoteProxyException, AppConfigException {

        Object         local = null;
        Properties     props = null;
        InitialContext ctx   = null;

        String FACTORY_NAME  = null;

        try {
        	AppConfigManager conf = AppConfigManager.getInstance();

            FACTORY_NAME  = conf.get("webApp.remote." + this.provider + ".contextFactory");

        } catch (AppConfigException le) {
			FACTORY_NAME   = null;
            //PROVIDER_URL   = null;
			throw new AppConfigException("\n(M) [Proxy.getLocalHandler() Using ERROR] fail in DLocalProxy.getRemoteHandler()\n Check the Proxy module ", le);
        }

        try {

            if (FACTORY_NAME != null) {

                props = new Properties();
                props.put(Context.INITIAL_CONTEXT_FACTORY, FACTORY_NAME);
                ctx = new InitialContext(props); // obtain initial context

            } else {

                ctx = new InitialContext();
            }

            Object objref = ctx.lookup(jndiNm);

            local = PortableRemoteObject.narrow(objref, classType);

        } catch (NamingException e) {
            throw new RemoteProxyException("\n(M) [Proxy.getLocalHandler() Using ERROR] fail in Proxy.getLocalHandler()\n Check the Proxy module ", e);
        }

        return local;
    }



}
