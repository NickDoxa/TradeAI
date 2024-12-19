package net.oasisgames.tradeai.common.mapper;

import net.oasisgames.tradeai.common.dto.TradeInfoAPIResponse;
import net.oasisgames.tradeai.common.entity.TradeInfo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TradeInfoMapper {

    TradeInfoAPIResponse toTradeInfoDTO(TradeInfo tradeInfo);
}
