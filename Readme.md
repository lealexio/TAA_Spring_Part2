# TAA-TP3 Partie 1

The project is executed from ```com/tp3/part1/Part1Application.java```.
Four scenarios are launched:
* Scenario 1 : Fast order
* Scenario 2 : Classic order
* Scenario 3 : provider auto restock nutella
* Scenario 4 : provider auto restock coke

### Implémentation

When a customer buys an item, the store checks the stock, then the payment is made by the bank.
If the payment is accepted the store updates its stock, then checks if it has to restock thanks to the provider. 
If it needs to restock it contacts the provider who automatically fills the store's stock.
If the payment is refused nothing is done.

We have chosen to use a method named "providerRestock" in the store to re-stock products under a certain stock level.

## Spring values

The bank account ids of the blind and the customer are indicated in the properties file of spring.

### Tests

The various methods of the **Bank, Provider, Store** objects are tested in the ```src/test/java/com/tp3/part1``` package.

