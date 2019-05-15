package com.psyquation.core.model;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;
import java.io.Serializable;
import java.time.OffsetDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AlertRelatedData implements Serializable {

    private String brokerName;
    private String serverName;
    private Integer login;
    private Double equity;
    private Double maxDD;
    private Double performance;
    private Double peak;
    private Double var;
    private Long order;
    private Long tradeDuration;
    private String tradeSymbol;
    @JsonSerialize(using = OffsetDateTimeSerializer.class)
    @JsonDeserialize(using = OffsetDateTimeDeserializer.class)
    private OffsetDateTime timestamp;
}