package net.oasisgames.tradeai.repository;

import net.oasisgames.tradeai.common.entity.TradeInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TradeInfoRepository extends JpaRepository<TradeInfo, UUID> {

    TradeInfo findTradeInfoBySymbol(String symbol);

}
