package net.oasisgames.tradeai.common.dto;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Data
public class TradeInfoAPIResponse {
    private UUID tradeId;
    private String symbol;
    private String tradeInformation;
    private List<StockUnitInfoAPIResponse> stockUnitInfo;
    private LocalDate timeChecked;
}