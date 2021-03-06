# Bulkhead Pattern
In Bulkhead Architecture, the application elements are isolated into pools so that if one pool is overloaded with requests, other should keep on working. With this, we can avoid cascading failures.
We partition the services into different groups. 

This ensures that resource usage of a service-client is not affecting the other client. 
This pattern is very helpful when we want to isolate and limit resources used by a back-end service.

## Example
We have 2 microservices Product Service & Rating Service.

Product Microservice is based on Rating microservice. When we query product details, it requests rating service to fetch reviews of the product.

A library **resilience4j** is used for implementing this pattern. 

With this library we will configure and apply bulkhead logic when rating service fails to respond to product service. 

In RatingController, there is a logic to fail randomly.

Annotation for bulkhead pattern is defined in RatingServiceClient. Here fallbackMethod specifies the method to execute when main method fails to execute for some reason.

In application.yaml file, we specified the configuration for bulkhead logic.

For Rating Service, We allowed maximum 10 concurrent requests `maxConcurrentCalls` to rating service.
Additional requests will wait for specified duration in `maxWaitDuration`. Otherwise they will be routed to fallback method.   
As a result of enabling Bulkhead pattern,the throughput is increased to `/products` endpoint.
For the rest of exceptions we are returning the empty list of rating.
