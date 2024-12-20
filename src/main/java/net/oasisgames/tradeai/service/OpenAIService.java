package net.oasisgames.tradeai.service;

import net.oasisgames.tradeai.common.dto.*;
import net.oasisgames.tradeai.common.mapper.StockEvaluationMapper;
import net.oasisgames.tradeai.repository.StockEvaluationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Instant;
import java.util.Arrays;

@Service
public class OpenAIService {

    @Autowired
    private StockEvaluationMapper stockEvaluationMapper;

    @Autowired
    private StockEvaluationRepository stockEvaluationRepository;

    @Qualifier("openaiRestTemplate")
    @Autowired
    private RestTemplate restTemplate;

    @Value("${spring.ai.openai.chat.options.model}")
    private String model;

    @Value("${spring.ai.openai.base-url}")
    private String apiUrl;

    public StockEvaluationAPIResponse getStockEvaluation(NewsSourceArticlesResponse newsSourceArticles,
                                                         TradeInfoAPIResponse tradeInfo,
                                                         double target) {
        StringBuilder evaluationMessage =
                new StringBuilder("Given a scale from 1 to 20, I need you to evaluate the probability of "
                        + tradeInfo.getSymbol()
                        + " hitting a stock price of " + target
                        + " during the next market day given this stock data on trades from the previous day\n");
        for (int i = 0; i < tradeInfo.getStockUnitInfo().size(); i++) {
            StockUnitInfoAPIResponse stockInfo = tradeInfo.getStockUnitInfo().get(i);
            evaluationMessage.append("Stock info #").append(i + 1).append(":\n")
                    .append("Stock open price was ").append(stockInfo.getOpen()).append("\n")
                    .append("Stock closed price was ").append(stockInfo.getClose()).append("\n")
                    .append("Stock high price was ").append(stockInfo.getHigh()).append("\n")
                    .append("Stock low price was ").append(stockInfo.getLow()).append("\n");
        }
        for (int i = 0; i < newsSourceArticles.getArticles().getResults().length; i++) {
            NewsSourceDataResponse newsSourceData = newsSourceArticles.getArticles().getResults()[i];
            evaluationMessage.append("And these articles pulled from recent sources\n")
                    .append("News source data #").append(i + 1).append(":\n")
                    .append("Article Title: ").append(newsSourceData.getTitle()).append("\n")
                    .append("Article Body: ").append(newsSourceData.getBody()).append("\n");
        }
        evaluationMessage.append("\n")
                .append("Please give your response with no markdown symbols (like #`* etc) and give your rating"
                        + " at the end of the message on a new line like so 'Probability Rating: x/20'");
        ChatgptAPIRequest request = new ChatgptAPIRequest(model, evaluationMessage.toString());
        ChatgptAPIResponse response = restTemplate.postForObject(apiUrl, request, ChatgptAPIResponse.class);
        if (response == null) {
            System.out.println("Chatgpt API response is null");
            return null;
        }
        StockEvaluation stockEvaluation = new StockEvaluation(
                null,
                tradeInfo.getSymbol(),
                response.getChoices().getFirst().getMessage().getContent(),
                Instant.now(),
                getEvaluationScore(response.getChoices().getFirst().getMessage().getContent()));
        System.out.println(getEvaluationScore(response.getChoices().getFirst().getMessage().getContent()));
        stockEvaluationRepository.saveAndFlush(stockEvaluation);
        return stockEvaluationMapper.toStockEvaluationAPIResponse(stockEvaluation);
    }

    private float getEvaluationScore(String evaluationMessage) {
        try {
            String[] split = evaluationMessage.split("Probability Rating:");
            return Float.parseFloat(split[1].split("/")[0]) / 20;
        } catch (NumberFormatException e) {
            System.out.println("Error parsing evaluation score: " + e.getMessage());
            return -1;
        }
    }

}
