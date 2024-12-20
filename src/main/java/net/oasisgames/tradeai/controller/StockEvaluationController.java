package net.oasisgames.tradeai.controller;

import lombok.RequiredArgsConstructor;
import net.oasisgames.tradeai.common.dto.NewsSourceArticlesResponse;
import net.oasisgames.tradeai.common.dto.StockEvaluationAPIResponse;
import net.oasisgames.tradeai.common.dto.TradeInfoAPIResponse;
import net.oasisgames.tradeai.service.NewsService;
import net.oasisgames.tradeai.service.OpenAIService;
import net.oasisgames.tradeai.service.StockService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@CrossOrigin
public class StockEvaluationController {

    private final NewsService newsService;
    private final StockService stockService;
    private final OpenAIService openAIService;

    @GetMapping("/evaluate")
    public ResponseEntity<StockEvaluationAPIResponse> getStockEvaluation(@RequestParam String symbol, @RequestParam double target) {
        NewsSourceArticlesResponse newsSourceArticlesResponse = newsService.getNewsSource(symbol);
        TradeInfoAPIResponse tradeInfoAPIResponse = stockService.getTradeInfo(symbol);
        return ResponseEntity.ok().body(openAIService.getStockEvaluation(
                newsSourceArticlesResponse,
                tradeInfoAPIResponse,
                target));
    }

}
