package com.jwcjlu.oannes.config.spring;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * 
 *  File: BeanDefineConfigue.java
 * 
 *  Copyright (c) 2017, globalegrow.com All Rights Reserved.
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
@Component
public class ApplicationInitializationEndPrintLogo implements  ApplicationListener<ContextRefreshedEvent> {//ContextRefreshedEvent为初始化完毕事件，spring还有很多事件可以利用 

    private Logger logger=Logger.getLogger(ApplicationInitializationEndPrintLogo.class);
//  @Autowired 
//  private IRoleDao roleDao; 


/** 
* 当一个ApplicationContext被初始化或刷新触发 
*/ 
@Override 
public void onApplicationEvent(ContextRefreshedEvent event) { 
//  roleDao.getUserList();//spring容器初始化完毕加载用户列表到内存 
    logger.info("************************************************************************************************************************"); 
    logger.info("************************************************************************************************************************"); 
    logger.info("************************************************************************************************************************"); 
    logger.info("************************************************************************************************************************"); 
    logger.info("************************************************************************************************************************"); 
    logger.info("************************************************************************************************************************"); 
    logger.info("************************************************************************************************************************"); 
    logger.info("************************************************************************************************************************"); 
    logger.info("************************************************************************************************************************"); 
    logger.info("************************************************************************************************************************"); 
    logger.info("************************************************************************************************************************"); 
    logger.info("************************************************************************************************************************"); 

} 
}

