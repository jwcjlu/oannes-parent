package com.jwcjlu.oannes.transport.clientpool;

import com.jwcjlu.oannes.transport.Client;

public interface ClientPool {
    /**
     * 借用通道
     *
     * @return
     * @throws Exception
     */
    Client borrowObject() throws Exception;


    /**
     * 借用通道
     *
     * @return
     * @throws Exception
     */
    Client borrowObject( long timeout) throws Exception;

    /**
     * 归还通道
     *
     * @param obj
     */
    void returnObject(Client obj);

    /**
     * @param obj
     * @throws Exception
     */
    void invalidateObject(Client obj) throws Exception;

    /**
     *
     */
    void shutdown();

}
