package com.hezhu.octopusaiagent.advisior;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.client.advisor.api.*;
import org.springframework.ai.chat.model.MessageAggregator;
import reactor.core.publisher.Flux;

/**
 * @Author Marshall
 * @Date 2025/7/20 13:00
 * @Description: 自定义日志Advisor
 */
@Slf4j
public class MyLoggerAdvisor implements CallAroundAdvisor, StreamAroundAdvisor {


    @Override
    public String getName() {
        return this.getClass().getName();
    }

    @Override
    public int getOrder() {
        //值越小，优先级越高
        return 0;
    }
    private AdvisedRequest before(AdvisedRequest request){
        log.info("AI Request: {}",request.userText());
        return request;
    }
    private void observeAfter(AdvisedResponse advisedResponse){
        log.info("AI Response: {}",advisedResponse.response().getResult().getOutput().getText());
    }
    @Override
    public AdvisedResponse aroundCall(AdvisedRequest advisedRequest, CallAroundAdvisorChain chain) {
        advisedRequest = this.before(advisedRequest);
        AdvisedResponse advisedResponse = chain.nextAroundCall(advisedRequest);
        this.observeAfter(advisedResponse);
        return advisedResponse;
    }

    @Override
    public Flux<AdvisedResponse> aroundStream(AdvisedRequest advisedRequest, StreamAroundAdvisorChain chain) {
        advisedRequest = this.before(advisedRequest);
        Flux<AdvisedResponse> advisedResponses = chain.nextAroundStream(advisedRequest);
        //用消息聚合器，拼接响应信息
        return (new MessageAggregator()).aggregateAdvisedResponse(advisedResponses,this::observeAfter);
    }

}
