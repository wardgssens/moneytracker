# Project 5-Software Design: Money tracker



## Functional requirements
- Adding and removing people from the application.
- Each person can put in a ticket
  - Who paid?
  - How much?
- Tickets for different kinds of events
  - Airplane ticket
  - Restaurant bills
  - Taxi payments
  - Concerts
  - ...
- Different kinds of tickets
  - Everybody should pay the same &rarr; split the ticket evenly
  - Everybody shoudl pay a different amount &rarr; split the ticket in different amounts
- Generate a global bill, who should who and how much?
- A functional GUI: functionality > aesthetics

## Non-functional requirements
- Databases
  - One database for Persons
  - One database for Tickets
- Design patterns
  - Singleton
  - Observer
  - Abstract factory
  - MVC
  - One of these: Strategy, Decorator, Command, Adapter, Facade, Proxy, Iterator, State, Template method, Composite
- UML diagrams
  - Class diagram of the whole application
  - Use case diagram of the whole application
  - Sequence diagram of process *calculate for whole trip*
- Tests
  - Unit test for one class
  - At least one integration test