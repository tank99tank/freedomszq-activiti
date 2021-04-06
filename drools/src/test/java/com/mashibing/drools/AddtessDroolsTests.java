package com.mashibing.drools;

import com.mashibing.drools.entity.Address;
import com.mashibing.drools.entity.AddressCheckResult;
import org.junit.jupiter.api.Test;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

/**
 * 需求
 * 计算额外积分金额 规则如下: 订单原价金额
 * 100以下, 不加分
 * 100-500 加100分
 * 500-1000 加500分
 * 1000 以上 加1000分
 */
@SpringBootTest
public class AddtessDroolsTests {
    @Resource
    private KieContainer kieContainer;
    @Resource
    private KieSession kieSession;


    @Test
    public void droolsOrderTest() {
        Address address = new Address();
        address.setPostcode(generateRandom(5));
        address.setStreet("测试1");
        AddressCheckResult result = new AddressCheckResult();
        kieSession.insert(address);
        kieSession.insert(result);
        
        int ruleFiredCount = kieSession.fireAllRules();
        System.out.println("触发了" + ruleFiredCount + "条规则");

        if(result.isPostCodeResult()){
            System.out.println("规则校验通过");
        }
    }
    /**
     * 生成随机数
     * @param num
     * @return
     */
    public String generateRandom(int num) {
        String chars = "0123456789";
        StringBuffer number=new StringBuffer();
        for (int i = 0; i < num; i++) {
            int rand = (int) (Math.random() * 10);
            number=number.append(chars.charAt(rand));
        }
        return number.toString();
    }


}
