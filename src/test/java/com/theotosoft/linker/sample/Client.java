package com.theotosoft.linker.sample;

import com.theotosoft.linker.sample.service.HelloWorldService;
import com.theotosoft.linker.service.ServiceConsumerFactory;

/**
 * @author mixueqiang
 * @since 2012-10-20
 * 
 */
public class Client {

    public static void main(String[] args) {
        HelloWorldService helloWorldService = (HelloWorldService) ServiceConsumerFactory.newInstance("A",
                        "com.theotosoft.linker.sample.service.HelloWorldService");
        helloWorldService.sayHello();
    }

}
