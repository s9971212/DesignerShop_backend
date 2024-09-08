package com.designershop.exceptions;

import com.designershop.enums.ErrorSourceEunm;
import lombok.Getter;
import org.apache.logging.log4j.Level;

@Getter
public class EmptyException extends Exception implements IBaseException {

    protected String[] customMessages;

    public EmptyException(String message) {
        super(message);
    }

    public EmptyException(Throwable cause, String message) {
        super(message, cause);
    }

    public EmptyException(Throwable cause, String message, String... customMessages) {
        super(message, cause);
        this.customMessages = customMessages;
    }

    @Override
    public String getSystemName() {
        return ErrorSourceEunm.E.name();
    }

    @Override
    public Level getLogLevel() {
        return Level.INFO;
    }
}
