package com.jwcjlu.oannes.common.log.util;


/**
 * @Description log调用工具类。
 * @author liumingsen
 * @date 2016年5月18日
 */
public class LoggerUtil {
    private static LogService logService = new DefaultLogService();// 可以通过设置为不同logservice控制log行为。

    public static boolean isTraceEnabled() {
        return logService.isTraceEnabled();
    }

    public static boolean isDebugEnabled() {
        return logService.isDebugEnabled();
    }

    public static boolean isWarnEnabled() {
        return logService.isWarnEnabled();
    }

    public static boolean isErrorEnabled() {
        return logService.isErrorEnabled();
    }

    public static void trace(String msg) {
        logService.trace(classInfo()+msg);
    }

    public static void debug(String msg) {
        logService.debug(classInfo()+msg);
    }

    public static void debug(String format, Object... argArray) {
        logService.debug(format, argArray);
    }

    public static void debug(String msg, Throwable t) {
        logService.debug(classInfo()+msg, t);
    }

    public static void info(String msg) {
        logService.info(classInfo()+msg);
    }

    public static void info(String format, Object... argArray) {
        logService.info(format, argArray);
    }

    public static void info(String msg, Throwable t) {
        logService.info(classInfo()+msg, t);
    }

    public static void warn(String msg) {
        logService.warn(classInfo()+msg);
    }

    public static void warn(String format, Object... argArray) {
        logService.warn(format, argArray);
    }

    public static void warn(String msg, Throwable t) {
        logService.warn(classInfo()+msg, t);
    }

    public static void error(String msg) {
        logService.error(classInfo()+msg);
    }
    public static void error(Exception e) {
        logService.error(classInfo()+e.getMessage());
    }
    public static void error(String format, Object... argArray) {
        logService.error(format, argArray);
    }

    public static void error(String msg, Throwable t) {
        logService.error(classInfo()+msg, t);
    }

    public static void accessLog(String msg) {
        logService.accessLog(classInfo()+msg);
    }

    public static void accessProfileLog(String format, Object... argArray) {
        logService.accessProfileLog(format, argArray);
    }

    public static LogService getLogService() {
        return logService;
    }

    public static void setLogService(LogService logService) {
        LoggerUtil.logService = logService;
    }
    private static String classInfo(){
        StringBuilder sb=new StringBuilder();
        StackTraceElement stelement=Thread.currentThread().getStackTrace()[3];
        if(stelement!=null){
            sb.append(stelement.getClassName()).append("#");
            sb.append(stelement.getMethodName()).append("[LineNumber:");
            sb.append(stelement.getLineNumber()).append("]");
        }
        return sb.toString();
    }
    public static void  main(String[] args){
        hello();
    }
    
    public static void hello() {
    
        System.out.println(classInfo());
  
    }
}
