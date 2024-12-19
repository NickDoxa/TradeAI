package net.oasisgames.tradeai.common.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class StockUnitInfoAPIResponse {
    private UUID stockUnitId;
    private TradeInfoAPIResponse tradeInfoId;
    private double open;
    private double close;
    private double high;
    private double low;
}
