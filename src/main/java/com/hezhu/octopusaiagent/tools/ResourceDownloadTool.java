package com.hezhu.octopusaiagent.tools;

import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpUtil;
import com.hezhu.octopusaiagent.constant.FileConstant;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.io.File;

/**
 * @Author Marshall
 * @Date 2025/8/13 14:46
 * @Description: 资源下载
 */
import cn.hutool.core.io.FileUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;

public class ResourceDownloadTool {

    @Tool(description = "Download a resource from a given URL")
    public String downloadResource(
            @ToolParam(description = "URL of the resource to download") String url,
            @ToolParam(description = "Name of the file to save the downloaded resource") String fileName) {

        String fileDir = FileConstant.FILE_SAVE_DIR + "/download";
        String filePath = fileDir + "/" + fileName;

        try {
            FileUtil.mkdir(fileDir);

            HttpResponse response = HttpRequest.get(url)
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/123.0 Safari/537.36")
                    .header("Referer", "https://www.codefather.cn/") // 根据实际站点调整
                    .execute();

            if (response.isOk()) {
                response.writeBody(new File(filePath));
                return "Resource downloaded successfully to: " + filePath;
            } else {
                return "Error downloading resource: Server returned status code " + response.getStatus();
            }

        } catch (Exception e) {
            return "Error downloading resource: " + e.getMessage();
        }
    }
}

