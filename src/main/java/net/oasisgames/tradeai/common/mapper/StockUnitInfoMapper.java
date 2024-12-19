package net.oasisgames.tradeai.common.mapper;

import net.oasisgames.tradeai.common.dto.StockUnitInfoAPIResponse;
import net.oasisgames.tradeai.common.entity.StockUnitInfo;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface StockUnitInfoMapper {

    @Mapping(target = "tradeInfoId", ignore = true)
    StockUnitInfoAPIResponse toDto(StockUnitInfo stockUnitInfo);

}
