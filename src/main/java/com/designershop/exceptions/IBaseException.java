package com.designershop.exceptions;

import org.apache.logging.log4j.Level;

/**
 * @author Ivan Wang
 * @date 2024/12/22
 * @version 1.0
 */
public interface IBaseException {

    String getMessage();

    String[] getCustomMessages();

    String getSystemName();

    Level getLogLevel();
}
