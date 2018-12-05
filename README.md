Checkout Kata
=============
A project to track SKUs in a shopping basket, and to calculate the basket
total given a set of pricing rules.

## Pricing Rules

### UnitPricingRule
A rule applying to a single SKU.  The total is the SKU price multiplied by the
number of instances of the SKU in the basket.

### MultibuyPricingRule
A rule applying to a single SKU.  When a qualifying number of the SKU are
present in the basket, a discounted price is applied.  The total is the
discounted price for each group of qualifying items, plus the single item price
for each remaining item.

## Tests
There are unit tests covering the behaviour of the rules and validation.  In
addition there are some tests covering some selected baskets of the "current"
prices.

## Assumptions
* SKU prices and basket totals can be held in a `Long`
* Pricing rules can be applied in isolation: i.e. the result of a rule is not
  affected by the behaviour of another rule
* The SKU for every item in the basket is priced by exactly one pricing
  rule