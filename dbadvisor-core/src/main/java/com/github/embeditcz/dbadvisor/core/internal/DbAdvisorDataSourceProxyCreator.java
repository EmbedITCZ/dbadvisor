package com.github.embeditcz.dbadvisor.core.internal;

import javax.sql.DataSource;

import lombok.RequiredArgsConstructor;
import net.ttddyy.dsproxy.support.ProxyDataSourceBuilder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
class DbAdvisorDataSourceProxyCreator implements BeanPostProcessor {

    private final DbAdvisorQueryExecutionListener dbAdvisorQueryExecutionListener;

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof DataSource) {
            return ProxyDataSourceBuilder.create(beanName, (DataSource) bean)
                    .logQueryToSysOut() // TODO tmp for debugging
                    .listener(dbAdvisorQueryExecutionListener)
                    .build();
        }
        return bean;
    }

}