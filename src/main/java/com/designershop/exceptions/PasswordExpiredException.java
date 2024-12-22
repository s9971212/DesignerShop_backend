package com.designershop.exceptions;

import com.designershop.enums.ErrorSourceEnum;
import lombok.Getter;
import org.apache.logging.log4j.Level;

/**
 * @author Ivan Wang
 * @date 2024/12/22
 * @version 1.0
 */
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
        return ErrorSourceEnum.U.name();
    }

    @Override
    public Level getLogLevel() {
        return Level.INFO;
    }
}
