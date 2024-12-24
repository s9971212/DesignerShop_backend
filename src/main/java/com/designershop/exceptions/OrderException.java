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
public class OrderException extends Exception implements IBaseException {

    protected String[] customMessages;

    public OrderException(String message) {
        super(message);
    }

    public OrderException(Throwable cause, String message) {
        super(message, cause);
    }

    public OrderException(Throwable cause, String message, String... customMessages) {
        super(message, cause);
        this.customMessages = customMessages;
    }

    @Override
    public String getSystemName() {
        return ErrorSourceEnum.OD.name();
    }

    @Override
    public Level getLogLevel() {
        return Level.INFO;
    }
}
