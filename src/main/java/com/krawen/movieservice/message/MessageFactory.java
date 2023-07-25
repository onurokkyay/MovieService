package com.krawen.movieservice.message;

public class MessageFactory {

	public static Message getMessage(String messageType) {
		if(messageType.equals("sms")) {
			return new Sms();
		}
		else if (messageType.equals("mail")) {
			return new Mail();
		}
		else {
			return null;
		}
		
	}

}
