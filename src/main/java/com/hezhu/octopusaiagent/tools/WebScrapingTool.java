package com.hezhu.octopusaiagent.tools;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;

import java.io.IOException;

/**
 * @Author Marshall
 * @Date 2025/8/11 17:24
 * @Description: 网页抓取工具
 */
public class WebScrapingTool {

    @Tool(description = "Scrape the content of a web page")
    public String scrapeWebPage(@ToolParam(description = "URL of the web page to scrape") String url) {
        try {
            Document document = Jsoup.connect(url).get();
            return document.html();
        } catch (IOException e) {
            return "Error scraping web page: " + e.getMessage();
        }
    }
}

