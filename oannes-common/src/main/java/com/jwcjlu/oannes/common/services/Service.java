package com.jwcjlu.oannes.common.services;


public interface Service extends BootService {
    public enum STATE {
        NOTINITED(0, "NOTINITED"),

        INITED(1, "INITED"),

        STARTED(2, "STARTED"),

        STOPPED(3, "STOPPED");

        private final int value;

        private final String statename;

        private STATE(int value, String name) {
            this.value = value;
            this.statename = name;
        }
        public int getValue() {
            return value;
        }

        @Override
        public String toString() {
            return statename;
        }
    }

    /**
     * 当前状态
     * @return
     */
    STATE currentState();

    /**
     * 服务名称
     * @return
     */

    String getName();

    /**
     * 服务初始化
     *
     */
    void serviceInit();

    /**
     * 服务启动
     */

    void serviceStart();

    /**
     * 关闭服务
     */
    void serviceStop();



    /**
     *
     * @param listener
     */
    void registerListener(ServiceListener listener);

    /**
     * get  starttime
     * @return
     */
    long getStartTime();
}
