package net.oasisgames.tradeai.common.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class StockEvaluation {

    @Id
    @UuidGenerator
    private UUID stockEvaluationId;

    private String symbol;

    @Column(columnDefinition = "text")
    private String evaluationResponse;

    private Instant evaluationTime;

    private float evaluationScore;

}
