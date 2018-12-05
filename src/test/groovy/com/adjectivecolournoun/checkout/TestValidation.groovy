package com.adjectivecolournoun.checkout

import com.adjectivecolournoun.checkout.exceptions.RepeatedSkuException
import com.adjectivecolournoun.checkout.exceptions.UnpricedSkuException
import com.adjectivecolournoun.checkout.rules.PricingRule
import groovy.transform.Immutable
import spock.lang.Specification

class TestValidation extends Specification {

    private final basket = new Basket()

    void 'fails if a sku in the basket does not have a matching rule'() {
        given:
        basket.addItem 'A'

        when:
        basket.calculateTotal([])

        then:
        thrown(UnpricedSkuException)
    }

    void 'fails if a sku in the basket is referenced in multiple pricing rules'() {
        given:
        basket.addItem 'A'

        when:
        basket.calculateTotal([
                new DummyPricingRule('A'),
                new DummyPricingRule('A')
        ])

        then:
        thrown(RepeatedSkuException)
    }
}

@Immutable
class DummyPricingRule implements PricingRule {

    String sku

    @Override
    boolean appliesTo(String sku) {
        this.sku == sku
    }

    @Override
    Long total(List<String> basketItems) {
        throw new RuntimeException('Not implemented!')
    }
}
