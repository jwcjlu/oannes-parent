package com.jwcjlu.oannes.common.services;


import java.io.IOException;

public interface BootService {
   default void initialize() throws Throwable{

   }

   default void boot(){

   }

   default void onComplete(){

   }

    default void shutdown() throws IOException{

    }
    default int order(){
       return 1;
    }

}
