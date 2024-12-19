package net.oasisgames.tradeai.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.Instant;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TradeInfo {

    @Id
    @UuidGenerator
    private UUID tradeId;

    private String symbol;

    @Column(columnDefinition = "text")
    private String tradeInformation;

    private Instant timeChecked;

}
