package org.cloud.util;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SentinelUtil implements InitializingBean {

    public void qpsLimit(){
        List<FlowRule> rules = new ArrayList<>();
        FlowRule chatRule = new FlowRule();
        chatRule.setResource("org/cloud");
        chatRule.setCount(1);
        chatRule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        chatRule.setLimitApp("default");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        qpsLimit();
    }
}
