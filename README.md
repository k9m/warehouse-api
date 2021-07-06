# Warehouse API

## Intro
This assignment will be used as a discussion during a technical interview.
Time constraints are part of software development and even though we don't expect a perfect solution, imagine your code to be on its way to production.
If you have to make compromises, provide a README to briefly explain pros and cons of your approach, what considerations you would make for another iteration and in general what your
future colleagues might be interested in if they had to pick up your code.

The primary values for the code we look for are: simplicity, readability, maintainability, testability. It should be easy to scan the code, and rather quickly understand what it’s doing.
Pay attention to naming.

You may choose any coding language, and we look forward to discussing your choice.

## The Task
The assignment is to implement a warehouse software. This software should hold articles, and the articles should contain an identification number, a name and available stock.
It should be possible to load articles into the software from a file, see the attached inventory.json.
The warehouse software should also have products, products are made of different articles. Products should have a name, price and a list of articles of which they are made from with a quantity.
The products should also be loaded from a file, see the attached products.json.

The warehouse should have at least the following functionality;
* ~~Get all products and quantity of each that is an available with the current inventory~~
* ~~Remove(Sell) a product and update the inventory accordingly~~

---
---
## Notes
* I've changed the json layout slightly to suit it better my implementation (and to JSON standards)
* The project was built in a contract-first manner, the public API and models are defined in `resources/contract.yml`
* The API interface and models are generated
* Swagger UI is accessible from `http://localhost:8080/swagger-ui.html`
* Integration tests use the Cucumber framework, feature files are in `src/test/resources`
* Jacoco coverage reports can be found at `build/jacoco-reports` (the build needs to be run with `verify` to enable IT tests)
* Import `src/main/resources/api/contract.yaml` to Postman to use the API

## Future Work
* Creating CRUD API for Inventory and Product
* Implementing pagination for responses
* Covering more edge cases with tests
* Implementing Oauth or JWT to secure the API
