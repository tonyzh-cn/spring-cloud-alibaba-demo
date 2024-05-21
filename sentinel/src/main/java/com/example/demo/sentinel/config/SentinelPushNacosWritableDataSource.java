package com.example.demo.sentinel.config;

import com.alibaba.cloud.sentinel.SentinelProperties;
import com.alibaba.cloud.sentinel.datasource.config.NacosDataSourceProperties;
import com.alibaba.csp.sentinel.datasource.WritableDataSource;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.transport.util.WritableDataSourceRegistry;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.SneakyThrows;
import org.springframework.beans.factory.SmartInitializingSingleton;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author zhangtao
 * @since 2024/5/15 14:17
 */
@Component
public class SentinelPushNacosWritableDataSource<T> implements WritableDataSource<T>, SmartInitializingSingleton {
    private ConfigService configService = null;

    @Resource
    private SentinelProperties sentinelProperties;

    private String serverAddr;
    private String groupId;
    private String dataId;

    private final Lock lock = new ReentrantLock();


    @PostConstruct
    public void init() throws NacosException {
        NacosDataSourceProperties ds1 = sentinelProperties.getDatasource().get("ds1").getNacos();
        serverAddr = ds1.getServerAddr();
        groupId = ds1.getGroupId();
        dataId = ds1.getDataId();

        Properties properties = new Properties();
        properties.setProperty(PropertyKeyConst.SERVER_ADDR, serverAddr);
        this.configService = NacosFactory.createConfigService(properties);
    }

    private <T> String encodeJson(T t) {
        return JSON.toJSONString(t);
    }

    @SneakyThrows
    @Override
    public void afterSingletonsInstantiated() {
        WritableDataSource<List<FlowRule>> flowRuleWDS = (WritableDataSource<List<FlowRule>>) this;

        WritableDataSourceRegistry.registerFlowDataSource(flowRuleWDS);
    }

    @Override
    public void write(T t) throws Exception {
        lock.lock();
        try {
            configService.publishConfig(dataId, groupId, this.encodeJson(t));
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void close() throws Exception {

    }
}
