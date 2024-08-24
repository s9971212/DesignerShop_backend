package com.designershop.exceptions;

import org.apache.logging.log4j.Level;

public interface IBaseException {

    String getMessage();

    String[] getCustomMessages();

    String getSystemName();

    Level getLogLevel();
}
