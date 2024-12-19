package net.oasisgames.tradeai.service;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.AlphaVantageException;
import com.crazzyghost.alphavantage.Config;
import com.crazzyghost.alphavantage.parameters.Interval;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;
import jakarta.annotation.Nullable;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.oasisgames.tradeai.common.dto.StockUnitInfoAPIResponse;
import net.oasisgames.tradeai.common.dto.TradeInfoAPIResponse;
import net.oasisgames.tradeai.common.entity.StockUnitInfo;
import net.oasisgames.tradeai.common.entity.TradeInfo;
import net.oasisgames.tradeai.common.mapper.StockUnitInfoMapper;
import net.oasisgames.tradeai.common.mapper.TradeInfoMapper;
import net.oasisgames.tradeai.repository.StockUnitInfoRepository;
import net.oasisgames.tradeai.repository.TradeInfoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class StockService {

    private final TradeInfoRepository tradeInfoRepository;
    private final TradeInfoMapper tradeInfoMapper;
    private final StockUnitInfoRepository stockUnitInfoRepository;
    private final StockUnitInfoMapper stockUnitInfoMapper;

    @Value("${alphavantage.key}")
    private String apiKey;

    public void initialize() {
        Config alphaVantageConfig = Config.builder().key(apiKey).build();
        AlphaVantage.api().init(alphaVantageConfig);
    }

    public TradeInfoAPIResponse getTradeInfo(long delay, boolean withDelay, @Nullable String symbol) {
        try {
            if (withDelay) Thread.sleep(delay);
        } catch (InterruptedException e) {
            System.out.println("Error: " + e.getMessage());
        }
        TradeInfo tradeInfo = symbol == null ? tradeInfoRepository.findMostRecentTradeInfo() :
                tradeInfoRepository.findTradeInfoBySymbol(symbol);
        List<StockUnitInfo> stocks = stockUnitInfoRepository.findAllByTradeInfo(tradeInfo);
        TradeInfoAPIResponse tradeInfoDTO = tradeInfoMapper.toTradeInfoDTO(tradeInfo);
        tradeInfoDTO.setStockUnitInfo(stocks.stream().map(stockUnitInfoMapper::toDto).toList());
        return tradeInfoDTO;
    }

    public void getStock(String symbol) {
        AlphaVantage.api().timeSeries()
                .intraday()
                .forSymbol(symbol)
                .interval(Interval.FIVE_MIN)
                .outputSize(OutputSize.COMPACT)
                .onSuccess(e -> handleSuccess((TimeSeriesResponse) e))
                .onFailure(this::handleFailure)
                .fetch();
    }

    private void handleSuccess(TimeSeriesResponse timeSeriesResponse) {
        TradeInfo tradeInfo = TradeInfo.builder()
                .symbol(timeSeriesResponse.getMetaData().getSymbol())
                .tradeInformation("Trade Information")
                .timeChecked(Instant.now())
                .build();

        tradeInfoRepository.saveAndFlush(tradeInfo);

        Set<StockUnitInfo> stocks = timeSeriesResponse.getStockUnits()
                .stream()
                .map(stockUnit -> StockUnitInfo.builder()
                        .open(stockUnit.getOpen())
                        .close(stockUnit.getClose())
                        .low(stockUnit.getLow())
                        .high(stockUnit.getHigh())
                        .tradeInfo(tradeInfo)
                        .build())
                .collect(Collectors.toSet());

        stockUnitInfoRepository.saveAllAndFlush(stocks);
    }

    private void handleFailure(AlphaVantageException e) {
        System.out.println("Failed to retrieve stock data");
        System.out.println("Error: " + e.getMessage());
    }

}
