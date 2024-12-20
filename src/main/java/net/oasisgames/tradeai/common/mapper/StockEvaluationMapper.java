package net.oasisgames.tradeai.common.mapper;

import net.oasisgames.tradeai.common.dto.StockEvaluation;
import net.oasisgames.tradeai.common.dto.StockEvaluationAPIResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StockEvaluationMapper {

    @Mapping(target = "symbol", source = "symbol")
    @Mapping(target = "evaluationResponse", source = "evaluationResponse")
    @Mapping(target = "evaluationTime", source = "evaluationTime")
    @Mapping(target = "evaluationScore", source = "evaluationScore")
    StockEvaluationAPIResponse toStockEvaluationAPIResponse(StockEvaluation stockEvaluation);

}
