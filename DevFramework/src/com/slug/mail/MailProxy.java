package com.slug.mail;

import java.util.Properties;

import javax.mail.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import com.slug.config.AppConfigManager;
import com.slug.exception.PException;
import com.slug.logging.Logging;

public class MailProxy
{
	// �������� ���� ������ �ΰ� �Ұ�쿡�� static �� ������ ������ �κ��� �����ؾ� �Ѵ�.
	private static InitialContext ctx = null;
	private static Session mailsession = null;

	public String getMailSessionDSName() throws PException
	{

		AppConfigManager conf = AppConfigManager.getInstance();
		 String dsName = conf.getString("webApp.mail.session.datasource.name");

		return dsName;
	}

    public Session getMailSessionProxy(String mailSessionDS) throws PException
    {


        try {

            if(ctx == null)
                ctx = new InitialContext();

			if(mailSessionDS == "default"){
				mailSessionDS = getMailSessionDSName();
				Logging.debug.println("< MailProxy > getMailSession(ds) mail session data source name :["+mailSessionDS+"]");
			}
            if(mailsession == null ){
                mailsession = (Session) ctx.lookup(mailSessionDS);
				Logging.debug.println("< MailProxy > getMailSession(ds) Create Mail Session :["+mailsession+"]");
			}else{
				Logging.debug.println("< MailProxy > getMailSession(ds) Delegrate Mail Session :["+mailsession+"]");
			}

        }catch (NamingException ne) {
            throw new PException("NamingException while getting Mail Session Name" + ne.getMessage() +"\n"+ ne);
        }
            return mailsession;
    }


	public Session getMailSession(String ds) throws PException{

		AppConfigManager conf = AppConfigManager.getInstance();

		String mailProtocol = conf.getString("webApp.mail.transport.protocol");
		String smtpHost     = conf.getString("webApp.mail.smtp.host");
		String fromMail     = conf.getString("webApp.mail.from");

		Session mailsession = getMailSessionProxy(ds);

		Properties props = new Properties();
		props.put("mail.transport.protocol", mailProtocol);
		props.put("mail.smtp.host", smtpHost);
		props.put("mail.from", fromMail);

		return mailsession.getInstance(props);
	}

/**
 * webApp.mail.session.datasource.name
 * webApp.mail.transport.protocol
 * webApp.mail.smtp.host
 * webApp.mail.from
 */
}
