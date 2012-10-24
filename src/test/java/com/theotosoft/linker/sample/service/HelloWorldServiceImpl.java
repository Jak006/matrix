package com.theotosoft.linker.sample.service;

/**
 * @author mixueqiang
 * @since 2012-10-18
 * 
 */
public class HelloWorldServiceImpl implements HelloWorldService {

    @Override
    public String sayHello() {
        return "Hello, world!";
    }

    @Override
    public String sayHello(String name) {
        return "Hello, " + name;
    }

}
