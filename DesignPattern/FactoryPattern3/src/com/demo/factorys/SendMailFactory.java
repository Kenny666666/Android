package com.demo.factorys;

import com.demo.MailSender;
import com.demo.interfaces.Provider;
import com.demo.interfaces.Sender;

/**
 * 建工厂类
 * 
 * @author Hugs
 * @2014-11-14
 */
public class SendMailFactory implements Provider{

	@Override
	public Sender produce() {
		return new MailSender(); 
	}
}
