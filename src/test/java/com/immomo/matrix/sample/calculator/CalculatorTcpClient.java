package com.immomo.matrix.sample.calculator;

import com.immomo.matrix.sample.calculator.service.CalculatorService;
import com.immomo.matrix.service.ServiceConsumerFactory;

/**
 * @author mixueqiang
 * @since 2012-10-20
 * 
 */
public class CalculatorTcpClient {

    private static ServiceConsumerFactory serviceConsumerFactory = new ServiceConsumerFactory("tcp_client.properties");

    public static void main(String[] args) throws Exception {
        CalculatorService calculatorService = (CalculatorService) serviceConsumerFactory.getInstance(
                "CalculatorApplication", "com.immomo.matrix.sample.calculator.service.CalculatorService");

        for (int i = 0; i < 1000; i++) {
            calculatorService.add(i, 100);
        }

        long begin = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            calculatorService.add(i, 100);
        }
        long end = System.currentTimeMillis();
        System.out.println((end - begin) / 1000);

        // Destroy all service instances in your application.
        serviceConsumerFactory.destroy();
    }

}
