package net.oasisgames.tradeai.service;

import net.oasisgames.tradeai.common.dto.NewsSourceAPIRequest;
import net.oasisgames.tradeai.common.dto.NewsSourceArticlesResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class NewsService {

    public final RestClient restClient = RestClient.create();
    @Value("${newsapi.key}")
    private String newsApiKey;

    public NewsSourceArticlesResponse getNewsSource(String symbol) {
        var newsSourceApiRequest = new NewsSourceAPIRequest();
        newsSourceApiRequest.setApiKey(newsApiKey);
        newsSourceApiRequest.setKeyword(symbol);
        var output = restClient.method(HttpMethod.GET)
                .uri("https://eventregistry.org/api/v1/article/getArticles")
                .body(newsSourceApiRequest)
                .retrieve()
                .toEntity(NewsSourceArticlesResponse.class);
        return output.getBody();
    }

}
