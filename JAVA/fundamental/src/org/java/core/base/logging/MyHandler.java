package org.java.core.base.logging;

import java.util.logging.LogRecord;
import java.util.logging.StreamHandler;

public class MyHandler extends StreamHandler{
	
	@Override
	public void publish(LogRecord record){
		//add own logic to publish
		super.publish(record);
	}
	
	@Override
	public void flush(){
		super.flush();
	}
	
	@Override
	public void close(){
		super.close();
	}
}