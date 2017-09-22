package com.jwcjlu.oannes.common.log.util;
/**
 * <pre>
 * 
 *  File: LogService.java
 * 
 *  Copyright (c) 2017, jwcjlu.com All Rights Reserved.
 * 
 *  Description:
 *  TODO
 * 
 *  Revision History
 *  Date,					Who,					What;
 *  2017年9月21日				jinwei				Initial.
 *
 * </pre>
 */
public interface LogService
{
    void trace(String msg);

    void trace(String format, Object... argArray);

    void debug(String msg);

    void debug(String format, Object... argArray);

    void debug(String msg, Throwable t);

    void info(String msg);

    void info(String format, Object... argArray);

    void info(String msg, Throwable t);

    void warn(String msg);

    void warn(String format, Object... argArray);

    void warn(String msg, Throwable t);

    void error(String msg);

    void error(String format, Object... argArray);

    void error(String msg, Throwable t);

    void accessLog(String msg);

    void accessProfileLog(String format, Object... argArray);

    boolean isTraceEnabled();

    boolean isDebugEnabled();

    boolean isWarnEnabled();

    boolean isErrorEnabled();


}

