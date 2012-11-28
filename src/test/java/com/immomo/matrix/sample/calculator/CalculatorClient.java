package com.immomo.matrix.sample.calculator;

import com.immomo.matrix.sample.calculator.service.CalculatorService;
import com.immomo.matrix.service.ServiceConsumerFactory;

/**
 * @author mixueqiang
 * @since 2012-10-20
 * 
 */
public class CalculatorClient {

    private static ServiceConsumerFactory serviceConsumerFactory = new ServiceConsumerFactory(
            "matrix_client.properties");

    public static void main(String[] args) throws Exception {
        CalculatorService calculatorService = (CalculatorService) serviceConsumerFactory.getInstance(
                "CalculatorApplication", "com.immomo.matrix.sample.calculator.service.CalculatorService");

        System.out.println(calculatorService.add(100, 10000));

        // Destroy all service instances in your application.
        serviceConsumerFactory.destroy();
    }

}
