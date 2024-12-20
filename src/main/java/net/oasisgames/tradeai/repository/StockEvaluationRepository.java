package net.oasisgames.tradeai.repository;

import net.oasisgames.tradeai.common.dto.StockEvaluation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StockEvaluationRepository extends JpaRepository<StockEvaluation, UUID> {}
