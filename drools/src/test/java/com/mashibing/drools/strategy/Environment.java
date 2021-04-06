package com.mashibing.drools.strategy;

/**
 * @author 孙志强
 * @title: Environment
 * @projectName drools-demo
 * @description: TODO
 * @date 2019/9/1118:22
 */
public class Environment {
    private Strategy strategy;

    public Environment(Strategy strategy) {
        this.strategy = strategy;
    }

    public int calulate(int num1, int num2) {
        return strategy.calc(num1, num2);
    }
}
