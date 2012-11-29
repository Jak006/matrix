package com.immomo.matrix.sample;

import com.immomo.matrix.sample.calculator.service.CalculatorService;
import com.immomo.matrix.sample.helloworld.service.HelloWorldService;
import com.immomo.matrix.service.consumer.ServiceConsumerFactory;

/**
 * @author mixueqiang
 * @since 2012-10-20
 * 
 */
public class TcpClient {

    private static ServiceConsumerFactory serviceConsumerFactory = new ServiceConsumerFactory("tcp_client.properties");

    public static void main(String[] args) throws Exception {
        HelloWorldService helloWorldService = (HelloWorldService) serviceConsumerFactory.getInstance(
                "HelloWorldApplication", "com.immomo.matrix.sample.helloworld.service.HelloWorldService");
        System.out.println(helloWorldService.sayHello("mixueqiang"));

        CalculatorService calculatorService = (CalculatorService) serviceConsumerFactory.getInstance(
                "CalculatorApplication", "com.immomo.matrix.sample.calculator.service.CalculatorService");
        System.out.println(calculatorService.add(10, 10000));

        // Destroy all service instances in your application.
        serviceConsumerFactory.destroy();
    }

}
