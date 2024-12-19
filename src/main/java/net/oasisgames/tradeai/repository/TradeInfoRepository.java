package net.oasisgames.tradeai.repository;

import net.oasisgames.tradeai.common.entity.TradeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TradeInfoRepository extends JpaRepository<TradeInfo, UUID> {

    @Query("SELECT t FROM TradeInfo t WHERE t.timeChecked = (SELECT MAX(t2.timeChecked) FROM TradeInfo t2)")
    TradeInfo findMostRecentTradeInfo();

    TradeInfo findTradeInfoBySymbol(String symbol);

}
