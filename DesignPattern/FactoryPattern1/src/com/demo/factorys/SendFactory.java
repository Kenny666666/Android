package com.demo.factorys;

import com.demo.MailSender;
import com.demo.SmsSender;
import com.demo.interfaces.Sender;

/**
 * 建工厂类
 * 
 * @author Hugs
 * @2014-11-14
 */
public class SendFactory {
	public Sender produceMail() {
		return new MailSender();
	}

	public Sender produceSms() {
		return new SmsSender();
	}
}
