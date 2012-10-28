package com.immomo.matrix.sample;

import com.immomo.matrix.sample.service.HelloWorldService;
import com.immomo.matrix.service.ServiceConsumerFactory;

/**
 * @author mixueqiang
 * @since 2012-10-20
 * 
 */
public class HelloWorldClient {

    public static void main(String[] args) throws Exception {
        HelloWorldService helloWorldService = (HelloWorldService) ServiceConsumerFactory.getInstance("A",
                "com.immomo.matrix.sample.service.HelloWorldService");

        String response = helloWorldService.sayHello();
        System.out.println(response);
    }

}
