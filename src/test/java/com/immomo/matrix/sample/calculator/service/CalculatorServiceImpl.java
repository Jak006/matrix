package com.immomo.matrix.sample.calculator.service;

/**
 * @author mixueqiang
 * @since 2012-10-31
 * 
 */
public class CalculatorServiceImpl implements CalculatorService {

    @Override
    public int add(int n1, int n2) {
        return n1 + n2;
    }

    @Override
    public int subtract(int n1, int n2) {
        return n1 - n2;
    }

}
