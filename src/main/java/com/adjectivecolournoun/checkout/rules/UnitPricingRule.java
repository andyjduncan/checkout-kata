package com.adjectivecolournoun.checkout.rules;

import java.util.List;

public class UnitPricingRule implements PricingRule {

    private final String sku;

    private final Long price;

    public UnitPricingRule(String sku, Long price) {
        this.sku = sku;
        this.price = price;
    }

    @Override
    public boolean appliesTo(String sku) {
        return this.sku.equals(sku);
    }

    @Override
    public Long total(List<String> basketItems) {
        long skuCount = basketItems.stream()
                .filter(this::appliesTo)
                .count();
        return price * skuCount;
    }
}
