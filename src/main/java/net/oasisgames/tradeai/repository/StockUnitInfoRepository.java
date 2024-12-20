package net.oasisgames.tradeai.repository;

import net.oasisgames.tradeai.common.entity.StockUnitInfo;
import net.oasisgames.tradeai.common.entity.TradeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface StockUnitInfoRepository extends JpaRepository<StockUnitInfo, UUID> {

    List<StockUnitInfo> findAllByTradeInfo(TradeInfo tradeInfo);

    void deleteAllByTradeInfo(TradeInfo tradeInfo);

}
