package com.adjectivecolournoun.checkout

import com.adjectivecolournoun.checkout.rules.MultibuyPricingRule
import spock.lang.Shared
import spock.lang.Specification

class TestMultibuyPricing extends Specification {

    @Shared
    private Random random = new Random()

    private final basket = new Basket()

    void 'returns a total based on the unit price if the qualifying number is not reached'() {
        given:
        final unitPrice = price() + 2
        final discountPrice = unitPrice - 1
        final qualifyingNumber = random.nextInt(10) + 2
        final pricingRules = [
                new MultibuyPricingRule('A', unitPrice, discountPrice, qualifyingNumber)
        ]

        when:
        (qualifyingNumber - 1).times {
            basket.addItem 'A'
        }

        then:
        basket.calculateTotal(pricingRules) == (qualifyingNumber - 1) * unitPrice
    }

    void 'returns the sum total of the discounted price for each qualifying group of items'() {
        given:
        final unitPrice = price() + 2
        final discountPrice = unitPrice - 1
        final qualifyingNumber = random.nextInt(10) + 2
        final qualifyingGroups = random.nextInt(10) + 1
        final pricingRules = [
                new MultibuyPricingRule('A', unitPrice, discountPrice, qualifyingNumber)
        ]

        when:
        (qualifyingGroups * qualifyingNumber).times {
            basket.addItem 'A'
        }

        then:
        basket.calculateTotal(pricingRules) == qualifyingGroups * discountPrice
    }

    void 'returns the sum total of the discounted price plus the unit price for ungrouped items'() {
        given:
        final unitPrice = price() + 2
        final discountPrice = unitPrice - 1
        final qualifyingNumber = random.nextInt(10) + 2
        final qualifyingGroups = random.nextInt(10) + 1
        final unitPriceItems = random.nextInt(qualifyingNumber)
        final pricingRules = [
                new MultibuyPricingRule('A', unitPrice, discountPrice, qualifyingNumber)
        ]

        when:
        (qualifyingGroups * qualifyingNumber).times {
            basket.addItem 'A'
        }
        unitPriceItems.times {
            basket.addItem 'A'
        }

        then:
        basket.calculateTotal(pricingRules) == (qualifyingGroups * discountPrice) + (unitPriceItems * unitPrice)
    }

    private long price() {
        random.nextInt(1_000) as long
    }
}
