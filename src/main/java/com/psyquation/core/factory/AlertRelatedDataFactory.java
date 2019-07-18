package com.psyquation.core.factory;

import com.psyquation.core.model.AlertRelatedData;
import java.time.OffsetDateTime;

public class AlertRelatedDataFactory {

    public static AlertRelatedData createEquityData(Integer login, Double equity) {
        return AlertRelatedData.builder()
            .brokerName("AxiCorp Financial Services Pty Ltd")
            .serverName("US06-Live")
            .login(login)
            .equity(equity)
            .timestamp(OffsetDateTime.now())
            .build();
    }

    public static AlertRelatedData createTradeDurationData(String symbol, Long duration) {
        return AlertRelatedData.builder()
            .brokerName("AxiCorp Financial Services Pty Ltd")
            .serverName("US06-Live")
            .order(1L)
            .tradeDuration(duration)
            .tradeSymbol(symbol)
            .timestamp(OffsetDateTime.now())
            .build();
    }
}
