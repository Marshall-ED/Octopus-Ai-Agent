package com.hezhu.octopusaiagent.tools;

import org.springframework.ai.tool.annotation.Tool;

/**
 * @Author Marshall
 * @Date 2025/8/16 17:44
 * @Description: 终止工具（让自主规划智能体可以合理中断）
 */
public class TerminateTool {

    @Tool(description = """  
            Terminate the interaction when the request is met OR if the assistant cannot proceed further with the task.  
            "When you have finished all the tasks, call this tool to end the work.  
            """)
    public String doTerminate() {
        return "任务结束";
    }
}
