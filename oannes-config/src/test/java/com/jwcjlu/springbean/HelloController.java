package com.jwcjlu.springbean;

import com.jwcjlu.oannes.config.OannConsumer;
import org.springframework.stereotype.Service;

@Service
public class HelloController {
    @OannConsumer(interfaces = HelloWorld.class)
    private HelloWorld helloWorld;

    public String say(String msg) {
        return helloWorld.say(msg);
    }
}
