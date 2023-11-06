## Local_E_Grocery_MIcro_Services_Project
![Local E-Grocery service drawio](https://github.com/Tirumalakumar16/Local_E_Grocery_MIcro_Services_Project/assets/114290389/cf51ee19-f27b-47b0-a3fa-8beef7ee347b)

## This Application is designed for to tackle in a situations like COVID-19.
## Local E Grocery service is used to order all grocery items by customers from respected local shops.

# Project Details 

## Identity Service ![identity-service-logo](https://github.com/Tirumalakumar16/Local_E_Grocery_MIcro_Services_Project/assets/114290389/e31faf0b-f869-4d7d-a25a-450133b5f02f)





#### User Registration:

1. Allow users to register for new accounts.
2. Ensure secure storage of user credentials and personal information.

#### User Authentication:

1. Implement user authentication mechanisms like username/password with JWT Token.
2. Verify user identities securely to prevent unauthorized access.

#### User Management:

1. Create, update, and delete user accounts.
2. Manage user roles and permissions within the system.


#### Token-Based Authentication:

1. Generate and validate access tokens for authenticated users.
2. Use tokens for secure communication between microservices.

#### Authorization:

1. Implement role-based access control (RBAC) or attribute-based access control (ABAC) for authorizing users' access to different microservices.
2. Define access policies for different resources.

#### Password Management:

1. Enable users to reset or recover forgotten passwords securely.

#### User Profile Management:

1. Allow users to manage their profiles, update personal information, and set preferences.
2. Store user-specific data like user profiles, preferences, and history.

#### Scalability:

1. Ensure that the identity service can scale horizontally to handle increasing user loads.
2. Implement load balancing for improved performance and fault tolerance.
#### Integration:

1. Integrate with other microservices for user-related operations, such as billing, addresses, and orders.

#### User Session Management:

1. Manage user sessions securely, including session timeout and token refresh.


## Cart Service

#### Shopping Cart Management:

1. Implement the core functionality of adding, removing, and updating items in the shopping cart.
2. Support multiple carts per user, allowing users to manage different shopping lists.

#### Persistence:

1. Store cart data persistently to ensure that the user's cart contents are retained even if they log out or close their session.

#### User Authentication:

1. Integrate with the identity service to associate shopping carts with authenticated users.
2. Ensure that only the authorized user can access and modify their cart.


## Address Service

#### Address CRUD Operations:

1. Implement Create, Read, Update, and Delete (CRUD) operations for user addresses.
2. Allow users to manage multiple addresses and set a default shipping address.

#### Persistence:

1. Store address data persistently, ensuring data integrity and availability.

#### User Authentication:

1. Integrate with the identity service to associate addresses with authenticated users.
2. Ensure that only authorized users can access and manage their addresses.

#### Shipping Information:

1. Associate addresses with orders and shipping information.
2. Provide an interface for users to select a shipping address during the checkout process.

#### Privacy and Security:

1. Ensure that user address data is kept secure and complies with data protection regulations.

#### Scalability:

1. Design the Address Service to scale horizontally to handle increasing user loads and peaks in traffic, especially during high-demand periods.

## API Gateway 

#### Request Routing:

1. Route incoming requests to the appropriate microservices based on the requested endpoint or URL.
   
#### Load Balancing:

1. Implement load balancing to distribute traffic evenly among multiple instances of a microservice, ensuring high availability and improved performance.
#### Authentication and Authorization:

1. Enforce authentication and authorization checks for incoming requests, ensuring that only authorized users can access specific endpoints.

#### Security:

1. Secure the API Gateway against common web vulnerabilities, such as cross-site scripting (XSS) and cross-site request forgery (CSRF).
2. Use security mechanisms like JWT tokens for secure communication with microservices.

#### Response Aggregation:

1. Aggregate data from multiple microservices to provide a unified response to the client, reducing the number of round-trips and improving performance.

#### Distributed Tracing:

1. Implement distributed tracing to track the flow of requests across microservices, making it easier to identify performance bottlenecks and troubleshoot issues.

#### Request Validation and Transformation:

1. Validate and transform incoming requests to ensure they meet the required format and standards for microservice consumption.

## Discovery Service      
![download](https://github.com/Tirumalakumar16/Local_E_Grocery_MIcro_Services_Project/assets/114290389/1647317a-2a23-4218-91a1-52a421e7275e)

#### Service Registration:

1. Allow microservices to register themselves with the Discovery Service when they start up.

#### Service Deregistration:

1. Enable microservices to gracefully deregister themselves when they shut down or become unavailable.

#### Service Discovery:

1. Provide a mechanism for microservices to discover the location and availability of other services in the ecosystem.

#### Load Balancing:

1. Implement load balancing to distribute traffic evenly among multiple instances of a microservice, enhancing availability and performance.

#### Consistency and Availability:

1. Ensure the Discovery Service itself is highly available and consistent to avoid single points of failure.

#### Auto-Scaling Integration:

1. Integrate with auto-scaling mechanisms to automatically register and deregister instances based on scaling events.

#### Service Naming Conventions:

1. Establish clear naming conventions for services and their instances to facilitate easy discovery.

## Product Service 

#### Product Catalog Management:

1. Implement Create, Read, Update, and Delete (CRUD) operations for managing product listings.

#### Product Search and Filtering:

1. Develop search and filtering capabilities to allow users to find products based on various criteria like shopName , category of the product.

#### Inventory Management:

1. Track product availability, stock levels, and inventory across multiple locations or stores.

#### Product Variants:

1. Support product variants, such as different sizes.

#### Security and Access Control:

1. Secure product data and restrict access based on user roles and permissions.

#### External Data Integration:

1. Integrate with external sources for product data, such as supplier information, to keep the catalog up-to-date.

#### Scalability:

1. Design the Product Service to scale horizontally to accommodate increasing product listings and user traffic.

## Order Service 

#### Order Creation:

1. Implement the ability for users to create new orders, including selecting products, quantities.

#### Order Modification:

1. Allow users to modify or update orders before they are finalized.

#### Order Validation:

1. Validate orders for product availability, pricing, from cart and also single product as well.

#### Checkout Process:

1. Develop a streamlined and secure checkout process that collects necessary user information and payment details.

#### Order Status Tracking:

1. Enable users to track the status of their orders, from processing to shipment and delivery.

#### Order History:

1. Maintain a history of previous orders for users to reference and re-order.

#### Inventory Management:

1. Update product stock levels based on order creation and fulfillment.

#### Security and Authorization:

1. Secure order data and ensure that only authorized users can access or modify their orders.

#### Integration with Other Services:

1. Seamlessly integrate with other microservices, such as the Product Service for product availability and the Payment Service for payment processing and cart service for checkout.


#### Scalability:

1. Design the Order Service to scale horizontally to handle increasing order volumes and peaks in traffic, especially during high-demand periods.

## Shop Service 

#### Shop management
1. Shop owner to register and manage their profile.

#### Inventory Management Service:

1. Monitor and update product availability based on orders and deliveries.
2. All Inventories are updated only by Seller/Owner.



