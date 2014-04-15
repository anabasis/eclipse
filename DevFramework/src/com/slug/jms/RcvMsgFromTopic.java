package com.slug.jms;

import java.util.Calendar;
import java.util.Hashtable;

import javax.jms.JMSException;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

//import com.tg.jfound.config.AppConfig;





import com.slug.config.AppConfigManager;
import com.slug.exception.PException;
import com.slug.logging.Logging;
import com.slug.util.StringUtil;


public class RcvMsgFromTopic
{

	// Defines the JNDI context factory.
	private static String JNDI_FACTORY = null;
	// Defines the JMS connection factory for the topic.
	private static String JMS_FACTORY = null;
	// Defines the topic.
	private static String TOPIC_NAME = null;

	private static InitialContext ctx;

	private TopicConnectionFactory tconFactory;
	private TopicConnection tcon;
	private TopicSession tsession;
	private TopicSubscriber tsubscriber;
	private Topic topic;
	private boolean quit = false;
	private long INI_OUT_TIME;// = 5000;
	private long LAST_OUT_TIME;// = 5000;
	private long START_TIME;
	//private long LAST_TIME;
	private long CURR_TIME;

	public RcvMsgFromTopic(){


		try{
			if(this.TOPIC_NAME == null)
				setTopicConfigInfo();
                
            if(ctx == null)
				ctx = getInitialContext();
            
            // Topic Timeout Setting    
            AppConfigManager conf = AppConfigManager.getInstance();
		    this.INI_OUT_TIME = StringUtil.stoi(conf.getString("webApp.cnc.online.rcv.timeout.milsec"));
		    Logging.dev.println("Config: "+conf.getString("webApp.cnc.online.rcv.timeout.milsec"));     
			
            this.LAST_OUT_TIME = this.INI_OUT_TIME;
			this.START_TIME = Calendar.getInstance().getTimeInMillis();
			initRcvTopicProcess(this.ctx, this.TOPIC_NAME);

		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public RcvMsgFromTopic(int out_time){

		try{

			if(this.TOPIC_NAME == null)
				setTopicConfigInfo();
			if(ctx == null)
				ctx = getInitialContext();

			this.INI_OUT_TIME = out_time;
			this.LAST_OUT_TIME = this.INI_OUT_TIME;
			this.START_TIME = Calendar.getInstance().getTimeInMillis();
			initRcvTopicProcess(this.ctx, this.TOPIC_NAME);

		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public String onGetRcvMsgFromTopic(String chkParam, int from, int to) throws JMSException{
		TextMessage msg;
		String rtnStr = "";
		String key = "";
		Logging.dev.println("Wait Message");

		while(!this.quit){

			Logging.dev.println("< RcvMsgFromTopic > onGetRcvMsgFromTopic Wait Time Recevied Message ["+this.LAST_OUT_TIME+"] Secs");
			msg = (TextMessage) tsubscriber.receive(this.LAST_OUT_TIME);
			if(msg == null || msg.equals("")){
                close();
				throw new JMSException("< RcvMsgFromTopic > onGetRcvMsgFromTopic Normal Time Out ["+this.INI_OUT_TIME+"] Secs");
			}else{
				key = new String(msg.getText().getBytes(),from,to);

				if(chkParam.equals(key)){
					rtnStr = msg.getText();
					Logging.dev.println("< RcvMsgFromTopic > onGetRcvMsgFromTopic Wait This Key["+chkParam+"] Rcv Key:["+key+"]RCV MSG:["+rtnStr+"]");
					close();
				}else{
					//this.LAST_TIME = ; Calendar.MILLISECOND
					this.CURR_TIME = Calendar.getInstance().getTimeInMillis();
					if(this.CURR_TIME - this.START_TIME < this.INI_OUT_TIME){
						this.LAST_OUT_TIME = this.INI_OUT_TIME - (this.CURR_TIME - this.START_TIME) ;
					}else{
						Logging.dev.println("< RcvMsgFromTopic > onGetRcvMsgFromTopic Time Out ["+this.INI_OUT_TIME+"] Secs");
						close();
					}
				}
			}

		}
		return rtnStr;
	}
	private void setTopicConfigInfo() throws PException{

		AppConfigManager conf = AppConfigManager.getInstance();
		this.TOPIC_NAME   = conf.getString("webApp.cnc.online.rcv.topic.name");
		this.JMS_FACTORY  = conf.getString("webApp.cnc.online.rcv.topic.factory.name");
		this.JNDI_FACTORY = conf.getString("webApp.datasource.default.contextFactory");

	}

	/**
	* Creates all the necessary objects for sending
	* messages to a JMS topic.
	*
	* @param  ctx  JNDI initial context
	* @param topicName	name of topic
	* @exception NamingException	if problem occurred with JNDI context interface
	* @exception JMSException if JMS fails to initialize due to internal error
	*/
	private void initRcvTopicProcess(Context ctx, String topicName) throws NamingException, JMSException
	{
		tconFactory = (TopicConnectionFactory)ctx.lookup(JMS_FACTORY);
		tcon = tconFactory.createTopicConnection();
		topic = (Topic)ctx.lookup(topicName);
		tsession = tcon.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
		tsubscriber = tsession.createSubscriber(topic);
		tcon.start();
	}

	/**
	* Closes JMS objects.
	*
	* @exception JMSException if JMS fails to close objects due to internal error
	*/
	public void close() throws JMSException {

		quit = true;

		tsubscriber.close();
		tsession.close();
		tcon.close();
	}

	private static InitialContext getInitialContext() throws NamingException
	{
		Hashtable env = new Hashtable();
		env.put(Context.INITIAL_CONTEXT_FACTORY, JNDI_FACTORY);
		env.put("weblogic.jndi.createIntermediateContexts", "true");
		return new InitialContext(env);
	}
}
