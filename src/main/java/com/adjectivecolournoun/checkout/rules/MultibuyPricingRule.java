package com.adjectivecolournoun.checkout.rules;

import java.util.List;

public class MultibuyPricingRule implements PricingRule {

    private final String sku;

    private final Long unitPrice;

    private final Long discountedPrice;

    private final Long qualifyingNumber;

    public MultibuyPricingRule(String sku, Long unitPrice, Long discountedPrice, Long qualifyingNumber) {
        this.sku = sku;
        this.unitPrice = unitPrice;
        this.discountedPrice = discountedPrice;
        this.qualifyingNumber = qualifyingNumber;
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

        return discountedTotal(skuCount) +
                unitPriceTotal(skuCount);
    }

    private long discountedTotal(long skuCount) {
        return (skuCount / qualifyingNumber) * discountedPrice;
    }

    private long unitPriceTotal(long skuCount) {
        return (skuCount % qualifyingNumber) * unitPrice;
    }
}
