Checkout Kata
=============
A project to track SKUs in a shopping basket, and to calculate the basket
total given a set of pricing rules.

## Assumptions
* SKU prices and basket totals can be held in a `Long`
* Pricing rules can be applied in isolation: i.e. the result of a rule is not
  affected by the behaviour of another rule
* Each SKU in the basket is priced by one, and only one, pricing rule
* Multi-buy discounts are applied repeatedly for each qualifying group of items

## Pricing Rules

### UnitPricingRule
A rule applying to a single SKU.  The total is the SKU price multiplied by the
number of instances of the SKU in the basket.

### MultibuyPricingRule
A rule applying to a single SKU.  When a qualifying number of the SKU are
present in the basket, a discounted price is applied.  The total is the
discounted price for each group of qualifying items, plus the single item price
for each remaining item.

## Extension
New rules can be added by implementing the PricingRule interface.  Single SKU
rules such as percentage or tiered discounts would be variations on the current
rules.
Since the appliesTo logic is implemented in the rule, more complex rules that
apply to multiple SKUs could be added.  For example, discounts for SKUs bought
together, that delegate to unit pricing rules if not satisfied.  The invariant
that one, and only one, pricing rule prices each SKU must hold.

## Tests
There are unit tests covering the behaviour of the rules and validation.  In
addition there are some tests covering some selected baskets of the "current"
prices.
