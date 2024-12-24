package com.designershop.exceptions;

import com.designershop.enums.ErrorSourceEnum;
import lombok.Getter;
import org.apache.logging.log4j.Level;

/**
 * @author Ivan Wang
 * @version 1.0
 * @date 2024/12/22
 */
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
        return ErrorSourceEnum.E.name();
    }

    @Override
    public Level getLogLevel() {
        return Level.INFO;
    }
}
