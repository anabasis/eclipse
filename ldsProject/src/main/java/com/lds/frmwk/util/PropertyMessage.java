package com.lds.frmwk.util;

import org.springframework.context.support.MessageSourceAccessor;

public class PropertyMessage {
	static MessageSourceAccessor messageSourceAccessor = null;

	public MessageSourceAccessor getMessageSourceAccessor() {
		return messageSourceAccessor;
	}

	public void setMessageSourceAccessor(MessageSourceAccessor messageSourceAccessor) {
		PropertyMessage.messageSourceAccessor = messageSourceAccessor;
	}
	
	public static String getMessage(String key) {
		return messageSourceAccessor.getMessage(key);
	}
}
