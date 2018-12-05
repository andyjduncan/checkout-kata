package com.adjectivecolournoun.checkout

import com.adjectivecolournoun.checkout.rules.MultibuyPricingRule
import com.adjectivecolournoun.checkout.rules.UnitPricingRule
import spock.lang.Specification

class TestThisWeeksPrices extends Specification {

    private final basket = new Basket()

    final pricingRules = [
            new MultibuyPricingRule('A', 50, 130, 3),
            new MultibuyPricingRule('B', 30, 45, 2),
            new UnitPricingRule('C', 20),
            new UnitPricingRule('D', 15)
    ]

    void 'correctly prices baskets of SKU A'() {
        when:
        items.each {
            basket.addItem it
        }

        then:
        basket.calculateTotal(pricingRules) == expectedTotal

        where:
        items     | expectedTotal
        ['A']     | 50
        ['A'] * 2 | 100
        ['A'] * 3 | 130
        ['A'] * 4 | 180
    }

    void 'correctly prices baskets of SKU B'() {
        when:
        items.each {
            basket.addItem it
        }

        then:
        basket.calculateTotal(pricingRules) == expectedTotal

        where:
        items     | expectedTotal
        ['B']     | 30
        ['B'] * 2 | 45
        ['B'] * 3 | 75
    }

    void 'correctly prices baskets of SKU C'() {
        when:
        items.each {
            basket.addItem it
        }

        then:
        basket.calculateTotal(pricingRules) == expectedTotal

        where:
        items     | expectedTotal
        ['C']     | 20
        ['C'] * 2 | 40
    }

    void 'correctly prices baskets of SKU D'() {
        when:
        items.each {
            basket.addItem it
        }

        then:
        basket.calculateTotal(pricingRules) == expectedTotal

        where:
        items     | expectedTotal
        ['D']     | 15
        ['D'] * 2 | 30
    }

    void 'correctly prices some mixed baskets'() {
        when:
        items.each {
            basket.addItem it
        }

        then:
        basket.calculateTotal(pricingRules) == expectedTotal

        where:
        items                                 | expectedTotal
        ['A', 'B', 'C', 'D']                  | 115
        ['A'] * 4 + ['B'] * 3 + ['C'] + ['D'] | 180 + 75 + 20 + 15
        ['C', 'D', 'D', 'A', 'B', 'A', 'B']   | 195
    }
}