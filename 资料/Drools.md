# 规则引擎 drools

# 1 .场景

## 1.1需求

```
商城系统消费赠送积分
100元以下, 不加分 
100元-500元 加100分 
500元-1000元 加500分 
1000元 以上 加1000分
......


```

## 1.2传统做法

### 1.2.1 if...else

```
if (order.getAmout() <= 100){
    order.setScore(0);
    addScore(order);
}else if(order.getAmout() > 100 && order.getAmout() <= 500){
    order.setScore(100);
    addScore(order);
}else if(order.getAmout() > 500 && order.getAmout() <= 1000){
    order.setScore(500);
    addScore(order);
}else{
    order.setScore(1000);
    addScore(order);
}
```

### 1.2.2 策略

```
interface Strategy {
    addScore(int num1,int num2);
}

class Strategy1 {
    addScore(int num1);
}
......................
interface StrategyN {
    addScore(int num1);
}

class Environment {
    private Strategy strategy;

    public Environment(Strategy strategy) {
        this.strategy = strategy;
    }

    public int addScore(int num1) {
        return strategy.addScore(num1);
    }
}
```

### 1.2.3 问题？

```
以上解决方法问题思考：
如果需求变更，积分层次结构增加，积分比例调整？
数据库？

遇到的问题瓶颈：
第一，我们要简化if else结构,让业务逻辑和数据分离！
第二，分离出的业务逻辑必须要易于编写，至少单独编写这些业务逻辑，要比写代码快！
第三，分离出的业务逻辑必须要比原来的代码更容易读懂！
第四，分离出的业务逻辑必须比原来的易于维护，至少改动这些逻辑，应用程序不用重启！
```

# 2.是什么

## 2.1概念

规则引擎由[推理引擎](https://baike.baidu.com/item/推理引擎/6311829)发展而来，是一种嵌入在应用程序中的组件，实现了将业务决策从应用程序代码中分离出来，并使用预定义的语义模块编写业务决策。接受数据输入，解释业务规则，并根据业务规则做出业务决策

在很多企业的 IT 业务系统中，经常会有大量的业务规则配置，而且随着企业管理者的决策变化，这些业务规则也会随之发生更改。为了适应这样的需求，我们的 IT 业务系统应该能快速且低成本的更新。适应这样的需求，一般的作法是将业务规则的配置单独拿出来，使之与业务系统保持低耦合。目前，实现这样的功能的程序，已经被开发成为规则引擎。

## 2.2 起源

![img](E:\课件\activiti\\drools原理.png)

## 2.3 原理--基于 rete 算法的规则引擎

### 2.3.1 原理

在 AI 领域，产生式系统是一个很重要的理论，产生式推理分为正向推理和逆向推理产生式，其规则的一般形式是：IF 条件 THEN  操作。rete 算法是实现产生式系统中正向推理的高效模式匹配算法，通过形成一个 rete  网络进行模式匹配，利用基于规则的系统的时间冗余性和结构相似性特征 ，提高系统模式匹配效率

正向推理（Forward-Chaining）和反向推理（Backward-Chaining）

（1）正向推理也叫演绎法，由事实驱动，从一个初始的事实出发，不断地从应用规则得出结论。首先在候选队列中选择一条规则作为启用规则进行推理，记录其结论作为下一步推理的证据。如此重复这个过程，直到再无可用规则可被选用或者求得了所要求的解为止。

（2）反向推理也叫归纳法，由目标驱动，首先提出某个假设，然后寻找支持该假设的证据，若所需的证据都能找到，说明原假设是正确的，若无论如何都找不到所需要的证据，则说明原假设不成立，此时需要另作新的假设。



### 2.3.2 rete算法

Rete 算法最初是由卡内基梅隆大学的 Charles L.Forgy 博士在 1974 年发表的论文中所阐述的算法 ,  该算法提供了专家系统的一个高效实现。自 Rete 算法提出以后 , 它就被用到一些大型的规则系统中 , 像 ILog、Jess、JBoss  Rules 等都是基于 RETE 算法的规则引擎 。

Rete 在拉丁语中译为”net”，即网络。Rete 匹配算法是一种进行大量模式集合和大量对象集合间比较的高效方法，通过网络筛选的方法找出所有匹配各个模式的对象和规则。

其核心思想是将分离的匹配项根据内容动态构造匹配树，以达到显著降低计算量的效果。Rete 算法可以被分为两个部分：规则编译和规则执行 。当 Rete 算法进行事实的断言时，包含三个阶段：匹配、选择和执行，称做 match-select-act cycle。

## 2.4 Drools

![图 4. Drools 的原理示意图](https://www.ibm.com/developerworks/cn/opensource/os-drools/image004.png)

其中左侧代表规则库，右侧代表用户数据库，中间推理库





Drools 具有一个易于访问企业策略、易于调整以及易于管理的开源业务 [规则引擎](http://baike.baidu.com/view/1636209.htm)，符合业内标准，速度快、效率高。业务分析师或审核人员可以利用它轻松查看业务规则，从而检验已编码的规则是否执行了所需的业务规则。其前身是  Codehaus 的一个开源项目叫 Drools，后被纳入 JBoss 门下，更名为 JBoss Rules，成为了 JBoss  应用服务器的规则引擎。

Drools 被分为两个主要的部分：编译和运行时。编译是将规则描述文件按 ANTLR 3  语法进行解析，对语法进行正确性的检查，然后产生一种中间结构“descr”，descr 用 AST 来描述规则。目前，Drools  支持四种规则描述文件，分别是：drl 文件、 xls 文件、brl 文件和 dsl 文件，其中，常用的描述文件是 drl 文件和 xls  文件，而 xls 文件更易于维护，更直观，更为被业务人员所理解。运行时是将 AST传到 PackageBuilder，由  PackagBuilder来产生 RuleBase，它包含了一个或多个 Package 对象。



# 3 .怎么用

![img](https://img2018.cnblogs.com/blog/725429/201906/725429-20190610095733992-1966819355.jpg)

上图为实际用法：

## 3.1 订单实体类

```java
@Data
@Accessors(chain = true)
public class Order {
    private Date bookingDate;//下单日期
    private int amout;//订单原价金额
    private User user;//下单人
    private int score;//积分
}  
```

## 3.2 规则引擎文件

```java
package rules

import com.mashibing.drools.entity.Order

rule "zero"
    no-loop true
    lock-on-active true
    salience 1
    when
        $s : Order(amout <= 100)
    then
        $s.setScore(0);
        update($s);
end

rule "add100"
    no-loop true
    lock-on-active true
    salience 1
    when
        $s : Order(amout > 100 && amout <= 500)
    then
        $s.setScore(100);
        update($s);
end

rule "add500"
    no-loop true
    lock-on-active true
    salience 1
    when
        $s : Order(amout > 500 && amout <= 1000)
    then
        $s.setScore(500);
        update($s);
end

rule "add1000"
    no-loop true
    lock-on-active true
    salience 1
    when
        $s : Order(amout > 1000)
    then
        $s.setScore(1000);
        update($s);
end
```

## 3.3 客户端 

```java
	@Test
    public void javaOrderTest() throws Exception {
        List<Order> orderList = getInitData();
        for (int i=0; i<orderList.size(); i++){
            Order order = orderList.get(i);
            if (order.getAmout() <= 100){
                order.setScore(0);
                addScore(order);
            }else if(order.getAmout() > 100 && order.getAmout() <= 500){
                order.setScore(100);
                addScore(order);
            }else if(order.getAmout() > 500 && order.getAmout() <= 1000){
                order.setScore(500);
                addScore(order);
            }else{
                order.setScore(1000);
                addScore(order);
            }
        }
    }

    @Test
    public void droolsOrderTest() throws Exception {
        List<Order> orderList = getInitData();
        for (Order order: orderList) {
            // 1-规则引擎处理逻辑
            kieSession.insert(order);
            kieSession.fireAllRules();
            // 2-执行完规则后, 执行相关的逻辑
            addScore(order);
        }
        kieSession.dispose();
    }
```





参考文档：

【1】  百度百科：规则引擎 ：https://baike.baidu.com/item/%E8%A7%84%E5%88%99%E5%BC%95%E6%93%8E/3076955?fr=aladdin

【2】 开源规则引擎 drools：https://blog.csdn.net/sdmxdzb/article/details/81461744

