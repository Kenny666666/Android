package com.demo.factorys;

import com.demo.MailSender;
import com.demo.SmsSender;
import com.demo.interfaces.Sender;

/**
 * 建工厂类
 * @author Hugs
 * @2014-11-14
 */
public class SendFactory {
	public Sender produce(String type) {
		if ("mail".equals(type)) {
			return new MailSender();
		} else if ("sms".equals(type)) {
			return new SmsSender();
		} else {
			System.out.println("请输入正确的类型!");
			return null;
		}
	}
}
