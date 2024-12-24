package com.designershop.exceptions;

import org.apache.logging.log4j.Level;

/**
 * @author Ivan Wang
 * @version 1.0
 * @date 2024/12/22
 */
public interface IBaseException {

    String getMessage();

    String[] getCustomMessages();

    String getSystemName();

    Level getLogLevel();
}
