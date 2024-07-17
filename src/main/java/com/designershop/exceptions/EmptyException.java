package com.designershop.exceptions;

import org.apache.logging.log4j.Level;

/**
 * request或以此當key查出來資料為空
 */
public class EmptyException extends UserException {

	public EmptyException(String message) {
		super(message);
	}

	public EmptyException(String message, String... customMessages) {
		super(message);
		this.customMessages = customMessages;
	}

	public EmptyException(Throwable cause, String message, String... customMessages) {
		super(cause, message);
		this.customMessages = customMessages;
	}

	@Override
	public Level getLogLevel() {
		return Level.WARN;
	}
}
