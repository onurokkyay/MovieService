package com.krawen.movieservice.message;

public class Sms implements Message {

	@Override
	public void send() {
	System.out.println("Sending sms message");
	}

}
