package net.oasisgames.tradeai.common.dto;

import lombok.Data;

@Data
public class NewsSourceAPIRequest {

    private String keyword;
    private String action = "getArticles";
    private String apiKey;
    private String resultType = "articles";
    private int forceMaxDataTimeWindow = 31;
    private String[] dataType = new String[]{"news", "pr"};
    private String ignoreSourceGroupUri = "paywall/paywalled_sources";
    private int articlesPage = 1;
    private int articlesCount = 100;
    private String[] sourceLocationUri = new String[]
            {
            "http://en.wikipedia.org/wiki/United_States",
            "http://en.wikipedia.org/wiki/Canada",
            "http://en.wikipedia.org/wiki/United_Kingdom"
    };
    private String articlesSortBy = "date";

}
