package com.example.demo.sentinel.config;

import com.alibaba.csp.sentinel.datasource.FileRefreshableDataSource;
import com.alibaba.csp.sentinel.datasource.FileWritableDataSource;
import com.alibaba.csp.sentinel.datasource.WritableDataSource;
import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.transport.util.WritableDataSourceRegistry;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import lombok.SneakyThrows;
import org.springframework.beans.factory.SmartInitializingSingleton;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangtao
 * @since 2024/5/15 14:17
 */
//@Component
public class SentinelPullFileBeanPostProcessor implements SmartInitializingSingleton {
//    private static final String RULE_FILE_PATH = System.getProperty("user.home") + File.separator;

    private static final String RULE_FILE_PATH = "E:/tmp/sentinel-rules/";

    private static final String FLOW_RULE_FILE_NAME = "FlowRule.json";

    private final static String RESOURCE_NAME = "resource";

    private <T> String encodeJson(T t) {
        return JSON.toJSONString(t);
    }

    @SneakyThrows
    @Override
    public void afterSingletonsInstantiated() {
        String ruleFilePath = RULE_FILE_PATH + FLOW_RULE_FILE_NAME;

        //创建流控规则的可读数据源
        FileRefreshableDataSource flowRuleRDS = new FileRefreshableDataSource(
                ruleFilePath, source -> JSON.parseObject((String) source,
                new TypeReference<List<FlowRule>>() {
                })
        );

        // 将可读数据源注册至FlowRuleManager 这样当规则文件发生变化时，就会更新规则到内存
        FlowRuleManager.register2Property(flowRuleRDS.getProperty());

        WritableDataSource<List<FlowRule>> flowRuleWDS = new FileWritableDataSource<>(
                ruleFilePath, this::encodeJson
        );

        // 将可写数据源注册至 transport 模块的 WritableDataSourceRegistry 中.
        // 这样收到控制台推送的规则时，Sentinel 会先更新到内存，然后将规则写入到文件中.
        WritableDataSourceRegistry.registerFlowDataSource(flowRuleWDS);
    }

    //    @PostConstruct
    public void init() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule = new FlowRule();
        rule.setResource("/demo/echo");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // Set limit QPS to 20.
        rule.setCount(1);
        rules.add(rule);

        FlowRule rule2 = new FlowRule();
        rule2.setResource(RESOURCE_NAME);
        rule2.setGrade(RuleConstant.FLOW_GRADE_QPS);
        // Set limit QPS to 20.
        rule2.setCount(1);
        rules.add(rule2);

        FlowRuleManager.loadRules(rules);
    }
}
