package com.hezhu.octopusaiagent.agent;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * @Author Marshall
 * @Date 2025/8/16 15:53
 * @Description: 实现思考行动的循环模式
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Slf4j
public abstract class ReActAgent extends BaseAgent {

    /**
     * 处理当前状态并决定下一步行动
     *
     * @return 是否执行下一步行动 true表示执行，false表示不需要执行
     */
    public abstract boolean think();

    /**
     * 执行决定的行动
     *
     * @return 行动执行结果
     */
    public abstract String act();


    /**
     * 执行单个步骤：思考和行动
     * @return 步骤执行结果
     */
    @Override
    public String step() {
        try {
            boolean shouldAct = think();
            if (!shouldAct) {
                return "思考完成 - 无需行动";
            }
            return act();
        } catch (Exception e) {
            log.info("步骤执行失败！{}",e.getMessage());
            return "步骤执行失败：" + e.getMessage();
        }
    }
}
