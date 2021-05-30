# Circuit Breaker Pattern
This pattern also helps make an application Resilient. It specially handles the cases where faults are recovered in variable amount of time. This pattern is helpful where Retry pattern is not performing well because of inconsistant time of fault recovery. 

It prevents an application from repeatedly trying to execute an operation that is going to fail. With this pattern we detect when a fault is fixed. If the issue is resolved, the application will only then try to execute the operation otherwise it will wait.

There are 3 states in this pattern. 

**Open:** In this state, the requests to Application fails and an exception is returned.

**Closed:** The request to the Application is successfully routed to operation. Here we keep track of percentage of failed requests. And a specific threshold is set. If the failure rate is below the threshold, it will keep routing the request to operation. If the failure rate is equal or greater than the threshold, it will update the state to Open. 

**Half-Open:** A pre-defined number of requests are allowed to route to the operation. If the request is still failing, the state is again set to Open. If the request is successful, we assume that the fault is fixed and state is updated to Closed.



## Example
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
