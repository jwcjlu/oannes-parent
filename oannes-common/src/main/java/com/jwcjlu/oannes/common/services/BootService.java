package com.jwcjlu.oannes.common.services;


import java.io.IOException;

public interface BootService {
    void initialize() throws Throwable;

    void boot();

    void onComplete();

    void shutdown() throws IOException;

}
