# Retry Pattern
This pattern makes an Application Stable & Resilient by handling transient failures by applying an intelligent retry logic. There are 3 strategies in this pattern. 
1. **Cancel:** When the failure is not transient, there is no point of retrying the request. 
2. **Retry:** When the failure is really a transient failure, we can retry as its self correcting. 
3. **Retry after Delay:** Sometimes there are some issues with Network or the Service is overloaded with lot of requests, we can wait for some time and resend the request. 

This pattern is not suitable when
1. The issues are long lasting. 
2. Application failures are not transient.
3. The Application is facing frequent busy faults.



##Example 
We have 2 microservices Product Service & Rating Service.

Product Microservice depends on Rating microservice. When we query product details, it requests rating service to fetch reviews of the product.


A library **resilience4j** is used for implementing this pattern. 

With this library we will configure and apply retry logic when rating service fails to respond to product service. 

In RatingController, there is a logic to fail randomly.

Annotation for Retry pattern is defined in RatingServiceClient. Here fallbackMethod specifies the method to execute when main method fails to execute for some reason.

In application.yaml file, we specified the configuration for Retry logic.

For Rating Service, We do max 2 retries with 3 seconds delay.
We have specified the type of exceptions for which the framework will automatically retry. (Transient failures)
And for the rest of exceptions we are returning the empty list of rating. 
