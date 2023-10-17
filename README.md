## Local_E_Grocery_MIcro_Services_Project
![Local E-Grocery service drawio](https://github.com/Tirumalakumar16/Local_E_Grocery_MIcro_Services_Project/assets/114290389/cf51ee19-f27b-47b0-a3fa-8beef7ee347b)

## This Application is designed for to tackle in a situations like COVID-19.
## Local E Grocery service is used to order all grocery items by customers from respected local shops.

# Project Details 

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

1. Customers can have Multiple addresses.
2. Shop owners also manage their addresses.
3. Manage their own Addresses.

## Identity Service

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

