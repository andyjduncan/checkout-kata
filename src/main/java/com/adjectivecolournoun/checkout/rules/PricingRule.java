package com.adjectivecolournoun.checkout.rules;

import java.util.List;

public interface PricingRule {

    boolean appliesTo(String sku);

    Long total(List<String> basketItems);
}
