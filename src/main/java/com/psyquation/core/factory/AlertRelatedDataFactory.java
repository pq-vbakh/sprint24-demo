package com.psyquation.core.factory;

import com.psyquation.core.model.AlertRelatedData;
import java.time.OffsetDateTime;

public class AlertRelatedDataFactory {

    public static AlertRelatedData createEquityData(String serverName, Integer login, Double equity) {
        return AlertRelatedData.builder()
            .brokerName("AxiCorp Financial Services Pty Ltd")
            .serverName(serverName)
            .login(login)
            .equity(equity)
            .timestamp(OffsetDateTime.now())
            .build();
    }

    public static AlertRelatedData createTradeDurationData(String serverName, String symbol, Long duration) {
        return AlertRelatedData.builder()
            .brokerName("AxiCorp Financial Services Pty Ltd")
            .serverName(serverName)
            .order(1L)
            .tradeDuration(duration)
            .tradeSymbol(symbol)
            .timestamp(OffsetDateTime.now())
            .build();
    }
}
