# Retry Pattern

This is based on Java 11.

We have 2 microservices Product Service & Rating Service.

Product Microservice is based on Rating microservice. When we query product details, it requests rating service to fetch reviews of the product.


A library **resilience4j** is used for implementing this pattern. 

With this library we will configure and apply retry logic when rating service fails to respond to product service. 

In RatingController, there is a logic to fail randomly.

Annotation for Retry pattern is defined in RatingServiceClient. Here fallbackMethod specifies the method to execute when main method fails to execute for some reason.

In application.yaml file, we specified the configuration for retry logic.

For Rating Service, We do max 2 retries with 3 seconds delay.
We have specified the type of exceptions for which the framework will automatically retry. 
And for the rest of exceptions we are returning the empty list of rating. 