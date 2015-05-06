package com.demo.factorys;

import com.demo.SmsSender;
import com.demo.interfaces.Provider;
import com.demo.interfaces.Sender;

public class SendSmsFactory implements Provider{

	@Override
	public Sender produce() {
		return new SmsSender(); 
	}

}
