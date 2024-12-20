package net.oasisgames.tradeai.service;

import com.crazzyghost.alphavantage.AlphaVantage;
import com.crazzyghost.alphavantage.Config;
import com.crazzyghost.alphavantage.parameters.Interval;
import com.crazzyghost.alphavantage.parameters.OutputSize;
import com.crazzyghost.alphavantage.timeseries.response.TimeSeriesResponse;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import net.oasisgames.tradeai.common.dto.TradeInfoAPIResponse;
import net.oasisgames.tradeai.common.entity.StockUnitInfo;
import net.oasisgames.tradeai.common.entity.TradeInfo;
import net.oasisgames.tradeai.common.mapper.StockUnitInfoMapper;
import net.oasisgames.tradeai.common.mapper.TradeInfoMapper;
import net.oasisgames.tradeai.repository.StockUnitInfoRepository;
import net.oasisgames.tradeai.repository.TradeInfoRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
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

    private void initialize() {
        Config alphaVantageConfig = Config.builder().key(apiKey).build();
        AlphaVantage.api().init(alphaVantageConfig);
    }

    public TradeInfoAPIResponse getTradeInfo(@NotNull String symbol) {
        initialize();
        TradeInfo tradeInfo = getStock(symbol);
        List<StockUnitInfo> stocks = stockUnitInfoRepository.findAllByTradeInfo(tradeInfo);
        TradeInfoAPIResponse tradeInfoDTO = tradeInfoMapper.toTradeInfoDTO(tradeInfo);
        tradeInfoDTO.setStockUnitInfo(stocks.stream().map(stockUnitInfoMapper::toDto).toList());
        return tradeInfoDTO;
    }

    private TradeInfo getStock(String symbol) {
        TradeInfo output = tradeInfoRepository.findTradeInfoBySymbol(symbol);
        if (tradeInfoRepository.findTradeInfoBySymbol(symbol) != null) {
            if (output.getTimeChecked().isBefore(LocalDate.now())) {
                tradeInfoRepository.delete(output);
                stockUnitInfoRepository.deleteAllByTradeInfo(output);
            } else return output;
        }
        TimeSeriesResponse response = AlphaVantage.api().timeSeries()
                .intraday()
                .forSymbol(symbol)
                .interval(Interval.FIVE_MIN)
                .outputSize(OutputSize.COMPACT)
                .fetchSync();
        TradeInfo tradeInfo = TradeInfo.builder()
                .symbol(response.getMetaData().getSymbol())
                .tradeInformation("Trade Information")
                .timeChecked(LocalDate.now())
                .build();
        output = tradeInfoRepository.saveAndFlush(tradeInfo);
        Set<StockUnitInfo> stocks = response.getStockUnits()
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
        return output;
    }
}
