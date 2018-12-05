package com.adjectivecolournoun.checkout;

import com.adjectivecolournoun.checkout.exceptions.PricingRulesValidationException;
import com.adjectivecolournoun.checkout.exceptions.RepeatedSkuException;
import com.adjectivecolournoun.checkout.exceptions.UnpricedSkuException;
import com.adjectivecolournoun.checkout.rules.PricingRule;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class Basket {

    private final List<String> basketItems = new ArrayList<>();

    public void addItem(final String sku) {
        basketItems.add(sku);
    }

    public Long calculateTotal(final List<PricingRule> pricingRules) throws PricingRulesValidationException {
        validatePricingRules(pricingRules);

        return calculateTotalFromRules(pricingRules);
    }

    private void validatePricingRules(final List<PricingRule> pricingRules) {
        validateAllSkusArePriced(pricingRules);

        validateNoSkusPricedMoreThanOnce(pricingRules);
    }

    private void validateAllSkusArePriced(final List<PricingRule> pricingRules) {
        Predicate<String> unmatchedSku = s -> pricingRules.stream().noneMatch(pricingRule -> pricingRule.appliesTo(s));
        basketItems.stream()
                .distinct()
                .filter(unmatchedSku)
                .findAny()
                .ifPresent(s ->  { throw new UnpricedSkuException(); });
    }

    private void validateNoSkusPricedMoreThanOnce(final List<PricingRule> pricingRules) {
        Function<String, Long> rulesApplyingToSku = s -> pricingRules.stream().filter(pricingRule -> pricingRule.appliesTo(s)).count();
        basketItems.stream()
                .distinct()
                .map(rulesApplyingToSku)
                .filter(l -> l > 1)
                .findAny()
                .ifPresent(s -> { throw new RepeatedSkuException(); });
    }

    private Long calculateTotalFromRules(final List<PricingRule> pricingRules) {
        return pricingRules.stream()
                .mapToLong(pricingRule -> pricingRule.total(basketItems))
                .sum();
    }
}
