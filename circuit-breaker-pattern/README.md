# Circuit Breaker Pattern

In this example, we have 2 microservices; Product Service & Rating Service.
Product Microservice depends on Rating microservice. 
When we query product details, it requests Rating service to fetch reviews of the Product.

A library **resilience4j** is used for implementing this pattern.
With this library we will configure and apply circuit breaking logic when Rating service fails to respond to Product service. 

In RatingController, there is a logic to fail randomly in order to produce the scenario.

Annotation for CircuitBreaker pattern is defined in RatingServiceClient. Here fallbackMethod specifies the method to execute when main method fails to execute for some reason. 

In application.yaml file, we specified the configuration for circuit-breaking logic. 
Here we are using `COUNT_BASED` sliding window to track number of requests. 
`waitDurationInOpenState` is set to 10 seconds that means it will wait for 10 seconds when Circuit Breaker is in CLOSED state. `failureRateThreshold` is set to 60%; means it will short-circuiting the requests when the failure rate is equal or greater than 60%.

When a request is short-circuited, it is routed to fallback method; that returns an empty list of reviews in order to ensure that Product Service keeps on working.
As a result of enabling Circuit Breaker pattern,the throughput is increased to /product/{id} endpoint.
