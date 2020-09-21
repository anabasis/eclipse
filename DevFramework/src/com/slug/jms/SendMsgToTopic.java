package com.slug.jms;

import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicPublisher;
import javax.jms.TopicSession;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.slug.config.AppConfigManager;
import com.slug.exception.PException;

public class SendMsgToTopic
{
	// Defines the JNDI context factory.
	private static String JNDI_FACTORY = null;
	// Defines the JMS connection factory for the topic.
	private static String JMS_FACTORY = null;
	// Defines the topic.
	private static String TOPIC_NAME = null;
	private static InitialContext ctx;

	private static TopicConnectionFactory tconFactory;
	private static TopicConnection tcon;
	private static TopicSession tsession;
	private static TopicPublisher tpublisher;
	private static Topic topic;
	private static TextMessage msg;

	public SendMsgToTopic(){
		try{
			if(this.TOPIC_NAME == null)
				setTopicConfigInfo();
			if(ctx == null)
				this.ctx = getInitialContext();
			initSndTopicProcess(this.ctx, this.TOPIC_NAME);

		}catch(Exception e){}
	}

	private void setTopicConfigInfo() throws PException{
		AppConfigManager conf = AppConfigManager.getInstance();
		 this.TOPIC_NAME  = conf.getString("webApp.cnc.online.rcv.topic.name");
		 this.JMS_FACTORY = conf.getString("webApp.cnc.online.rcv.topic.factory.name");
		 this.JNDI_FACTORY = conf.getString("webApp.datasource.default.contextFactory");
	}



	/**
	 * Creates all the necessary objects for sending
	 * messages to a JMS Topic.
	 *
	 * @param ctx JNDI initial context
	 * @param topicName name of topic
	 * @exception NamingException if problem occurred with the JNDI context interface
	 * @exception JMSException if JMS fails to initialize due to internal error
	 *
	 */
	public void initSndTopicProcess(Context ctx, String topicName) throws NamingException, JMSException
	{
		this.tconFactory = (TopicConnectionFactory)ctx.lookup(JMS_FACTORY);
		this.tcon        = tconFactory.createTopicConnection();
		this.tsession    = tcon.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		this.topic       = (Topic)ctx.lookup(topicName);

		this.tpublisher = tsession.createPublisher(topic);
		this.msg        = tsession.createTextMessage();
		this.tcon.start();
	}

	/**
	 * Sends a message to a JMS topic.
	 *
	 * @param message message to be sent
	 * @exception JMSException if JMS fails to send message due to internal error
	 *
	 */
	public void sendRcvMsgToTopic(String message) throws JMSException {
        
    //    Logging.debug.println("< SendMsgToTopic > [sendRcvMsgToTopic] message >>"+message);

        this.msg.setText(message);
		this.tpublisher.publish(msg);
 
	}

	/**
	 * Closes JMS objects.
	 *
	 * @exception JMSException if JMS fails to close objects due to internal error
	 */
	public void close() throws JMSException {
		tpublisher.close();
		tsession.close();
		tcon.close();
	}

	/**
	 * Get initial JNDI context.
	 *
	 * @param url Weblogic URL.
	 * @exception  NamingException if problem occurs with JNDI context interface
	 */
	protected static InitialContext getInitialContext() throws NamingException
	{
		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY, JNDI_FACTORY);
		//env.put(Context.PROVIDER_URL, url);
		env.put("weblogic.jndi.createIntermediateContexts", "true");
		return new InitialContext(env);
	}

}
