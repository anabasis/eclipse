package com.slug.mail;

import java.util.Date;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.slug.config.AppConfigManager;
import com.slug.exception.PException;
import com.slug.logging.Logging;
import com.slug.util.StringUtil;

public class SendMail
{
	private Session mailsession;
	public SendMail() throws PException{
		MailProxy proxy = new MailProxy();
		mailsession = proxy.getMailSession("default");
	}

	public SendMail(String ds) throws PException{
		MailProxy proxy = new MailProxy();
		mailsession = proxy.getMailSession(ds);
	}

	public void sendMail(String rcvMailAddress, String subject,String messageTxt) throws PException{

		Message msg = new MimeMessage(mailsession);
		MimeBodyPart mbp = new MimeBodyPart();
		Multipart mp = new MimeMultipart();
		Logging.dev.println(rcvMailAddress);
		if(rcvMailAddress.equals("default"))
		{
			// Configure ���� Read
			AppConfigManager conf = AppConfigManager.getInstance();
			rcvMailAddress = conf.getString("webApp.mail.to");
		}
		Logging.dev.println(rcvMailAddress);

		StringUtil stUtil = new StringUtil();
		String rcvAddress[] = stUtil.parseStringWithDelimiter(rcvMailAddress,";");
		Logging.dev.println(rcvAddress.length);

		for(int j = 0 ; j < rcvAddress.length ;j++)
			Logging.dev.println("Rcveived Mail Adress["+j+"]:"+rcvAddress[j]);

		try{
			for(int i = 0 ; i < rcvAddress.length ; i++){

				msg.setFrom();
				msg.setRecipients(Message.RecipientType.TO , InternetAddress.parse(rcvAddress[i], false));
				msg.setSubject(subject);
				msg.setSentDate(new Date());
				mbp.setText(messageTxt);
				mp.addBodyPart(mbp);
				msg.setContent(mp);
				Transport.send(msg);
			}
		}catch(MessagingException me){
			throw new PException("< SendMail > sendMail() MessagingException Occoureed");
		}

	}
}
