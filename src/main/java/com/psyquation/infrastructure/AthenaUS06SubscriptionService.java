package com.psyquation.infrastructure;

import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.psyquation.core.service.US06SubscriptionService;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import java.util.List;
import java.util.Map;

public class AthenaUS06SubscriptionService implements US06SubscriptionService {

    @Override
    public List<Map<String, AttributeValue>> initialSubscriptions(Long userId) {
        throw new NotImplementedException();
    }
}
