package net.oasisgames.tradeai.common.dto;

import lombok.Data;

import java.time.Instant;

@Data
public class StockEvaluationAPIResponse {

    private String symbol;
    private String evaluationResponse;
    private Instant evaluationTime;

}
