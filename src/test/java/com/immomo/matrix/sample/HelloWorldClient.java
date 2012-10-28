package com.immomo.matrix.sample;

import com.immomo.matrix.ServiceConsumerFactory;
import com.immomo.matrix.sample.service.HelloWorldService;

/**
 * @author mixueqiang
 * @since 2012-10-20
 * 
 */
public class HelloWorldClient {

    private static ServiceConsumerFactory serviceConsumerFactory = new ServiceConsumerFactory(
            "matrix_client.properties");

    public static void main(String[] args) throws Exception {
        HelloWorldService helloWorldService = (HelloWorldService) serviceConsumerFactory.getInstance(
                "HelloWorldApplication", "com.immomo.matrix.sample.service.HelloWorldService");

        String response = helloWorldService.sayHello("mixueqiang");
        System.out.println(response);
    }

}
