package com.group9.cleansweep.controlsystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class StatusCheck {
	private static Logger logger = LoggerFactory.getLogger(StatusCheck.class);

	public void setStatus(String statusCheckMessage) {
		logger.info(statusCheckMessage);
		
	}

}
