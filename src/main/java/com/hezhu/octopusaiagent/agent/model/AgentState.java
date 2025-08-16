package com.hezhu.octopusaiagent.agent.model;

/**
 * @Author Marshall
 * @Date 2025/8/16 14:56
 * @Description: 代理执行状态的枚举类
 */
public enum AgentState {
    /**
     * 空闲状态
     */
    IDLE,

    /**
     * 运行中状态
     */
    RUNNING,

    /**
     * 已完成状态
     */
    FINISHED,

    /**
     * 错误状态
     */
    ERROR
}

