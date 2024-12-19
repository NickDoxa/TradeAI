package net.oasisgames.tradeai.common.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "stock_unit_info")
public class StockUnitInfo {

    @Id
    @UuidGenerator
    private UUID stockUnitId;

    @ManyToOne
    @JoinColumn(name = "trade_info_id", nullable = false)
    private TradeInfo tradeInfo;

    private double open;

    private double close;

    private double high;

    private double low;

}
