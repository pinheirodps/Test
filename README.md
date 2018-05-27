Challenge:
This project was developed based on interfaces with an MVC architecture:
ServeltController, CarModel, Index.tlf

There were used some patterns and stereotypes like:
Service - All your business logic will be in Service classes.
Controller - Single handler for all kinds of requests coming to the application.
Repository - Responsible for the data layer
Builder - to create and configure objects (it was reused for the given code)

I used an implementation based on interfaces respecting object-oriented and generics.

Frameworks used:
JavaEE Servlet, Groovy, Junit, mockito

How did I implement this challenge?
0 - Servlet receives the request
1 - read template groovy
2 - fill the values on html
