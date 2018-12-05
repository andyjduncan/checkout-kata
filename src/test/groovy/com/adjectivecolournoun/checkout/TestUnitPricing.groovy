package com.adjectivecolournoun.checkout

import com.adjectivecolournoun.checkout.rules.UnitPricingRule
import spock.lang.Shared
import spock.lang.Specification

class TestUnitPricing extends Specification {

    @Shared
    private Random random = new Random()

    private final basket = new Basket()

    void 'returns the price of a single item as the total'() {
        given:
        final sku = 'A'
        final skuPrice = price()
        final pricingRules = [new UnitPricingRule(sku, skuPrice)]

        when:
        basket.addItem sku

        then:
        basket.calculateTotal(pricingRules) == skuPrice
    }

    void 'returns the sum total of a single sku added more than once'() {
        given:
        final sku = 'A'
        final skuPrice = price()
        final skuCount = random.nextInt(10) + 2
        final pricingRules = [new UnitPricingRule(sku, skuPrice)]

        when:
        skuCount.times {
            basket.addItem sku
        }

        then:
        basket.calculateTotal(pricingRules) == skuCount * skuPrice
    }

    void 'returns the sum total of multiple skus, regardless of order added'() {
        given:
        final pricingRules = [
                new UnitPricingRule('A', skuPriceA),
                new UnitPricingRule('B', skuPriceB)
        ]

        when:
        skus.each {
            basket.addItem it
        }

        then:
        basket.calculateTotal(pricingRules) == expectedTotal

        where:
        skus            | skuPriceA | skuPriceB | expectedTotal
        ['A', 'B']      | price()   | price()   | skuPriceA + skuPriceB
        ['B', 'A']      | price()   | price()   | skuPriceA + skuPriceB
        ['A', 'B', 'A'] | price()   | price()   | (2 * skuPriceA) + skuPriceB
        ['A', 'B', 'B'] | price()   | price()   | skuPriceA + (2 * skuPriceB)
    }

    private long price() {
        random.nextInt(1_000) as long
    }
}
