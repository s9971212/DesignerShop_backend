package com.designershop.exceptions;

import org.apache.logging.log4j.Level;

import com.designershop.enums.ErrorSourceEunm;

import lombok.Getter;

@Getter
public class PasswordExpiredException extends Exception implements IBaseException {

	protected String[] customMessages;

	public PasswordExpiredException(String message) {
		super(message);
	}

	public PasswordExpiredException(Throwable cause, String message) {
		super(message, cause);
	}

	public PasswordExpiredException(Throwable cause, String message, String... customMessages) {
		super(message, cause);
		this.customMessages = customMessages;
	}

	@Override
	public String getSystemName() {
		return ErrorSourceEunm.U.name();
	}

	@Override
	public Level getLogLevel() {
		return Level.INFO;
	}
}