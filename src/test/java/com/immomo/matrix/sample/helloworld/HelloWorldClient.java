package com.immomo.matrix.sample.helloworld;

import com.immomo.matrix.ServiceConsumerFactory;
import com.immomo.matrix.sample.helloworld.service.HelloWorldService;

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
                "HelloWorldApplication", "com.immomo.matrix.sample.helloworld.service.HelloWorldService");

        System.out.println(helloWorldService.sayHello("mixueqiang"));

        // Destroy all service instances in your application.
        serviceConsumerFactory.destroy();
    }

}