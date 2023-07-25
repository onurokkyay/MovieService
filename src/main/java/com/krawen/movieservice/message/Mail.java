package com.krawen.movieservice.message;

public class Mail implements Message {

	@Override
	public void send() {
		System.out.println("Sending mail message");
	}

}
