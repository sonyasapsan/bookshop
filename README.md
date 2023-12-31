<h1 align="center">
Book management system
</h1>
<p align="center">
    <img width="500" src="https://www.libraryvision.org/wp-content/uploads/2022/06/Book-scanning-software-featured.png" alt="Book shop">
  
## Intro
The objective of this project is to offer users a convenient solution in the realm of book sales. The project enables clients to swiftly peruse available books, 
add them to the shopping cart and make orders.
## Technologies used in the project
<p align="left">
    <img width="25" src="https://upload.wikimedia.org/wikipedia/uk/8/85/%D0%9B%D0%BE%D0%B3%D0%BE%D1%82%D0%B8%D0%BF_Java.png" alt="Java Logo">
    Java
</p>

<p align="left">
    <img width="25" src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQwsq-7f5BWyog4cdeT1sQaYLVzhJ0o37Up8TjHvVU08WUgfyyMMRMHTVwJ5XReSjyhZa0&usqp=CAU" alt="Spring logo">
    Spring
</p>

<p align="left">
    <img width="25" src="https://pbs.twimg.com/profile_images/1235868806079057921/fTL08u_H_400x400.png" alt="Spring Boot logo">
    Spring Boot
</p>

<p align="left">
    <img width="25" src="https://pbs.twimg.com/profile_images/1235945452304031744/w55Uc_O9_400x400.png" alt="Spring Data Jpa logo">
    Spring Data Jpa
</p>

<p align="left">
    <img width="25" src="https://pbs.twimg.com/profile_images/1235983944463585281/AWCKLiJh_400x400.png" alt="Spring Security logo">
    Spring Security
</p>

<p align="left">
    <img width="25" src="https://blog.kakaocdn.net/dn/bA0QdM/btqQCzxS7vv/RTB3bbZsu7EMKPBefuTn80/img.jpg" alt="Lombok logo">
    Lombok
</p>

<p align="left">
    <img width="25" src="https://dashboard.snapcraft.io/site_media/appmedia/2020/08/liquibase.jpeg.png" alt="Liquibase logo">
    Liquibase
</p>

<p align="left">
    <img width="25" src="https://logowik.com/content/uploads/images/mysql8604.logowik.com.webp" alt="MySql logo">
    MySql 
</p>

<p align="left">
     <img width="25" src="https://mapstruct.github.io/mapstruct.org.new/images/favicon.ico" alt="Mapstruct logo">
    Mapstruct
</p>

<p align="left">
     <img width="25" src="https://seeklogo.com/images/S/swagger-logo-A49F73BAF4-seeklogo.com.png" alt="Swagger logo">
    Swagger ui
</p>

### Available endpoints
#### Authentication controller

| Request type | Endpoint                     | Role  | Description                                                          |
|--------------|------------------------------|-------|----------------------------------------------------------------------|
| POST         | /register                    | ALL   | New user registration                                                |
| POST         | /login                       | ALL   | Receive access to user's profile                                     |

#### Book controller

| Request type | Endpoint                      | Role  | Description                                                         |
|--------------|-------------------------------|-------|---------------------------------------------------------------------|
| GET          | /books                        | ALL   | Get a list of available books                                       |
| GET          | /books/{id}                   | ALL   | Get a book by specific id                                           |
| GET          | /books/{author}               | ALL   | Get a book by specific author                                       |
| GET          | /books/search                 | ALL   | Get all books which meet some requirements                          |
| POST         | /books                        | ADMIN | Create a new book in the DB                                         |
| PUT          | /books/{id}                   | ADMIN | Update info for a book with a specific id                           |
| DELETE       | /books/{id}                   | ADMIN | Delete a book with a specific id                                    |

#### Category controller

| Request type | Endpoint                      | Role  | Description                                                         |
|--------------|-------------------------------|-------|---------------------------------------------------------------------|
| GET          | /categories                   | ALL   | Get all categories                                                  |
| GET          | /categories/{id}              | ALL   | Get a category by specific id                                       |
| GET          | /categories/{id}/books        | ALL   | Get all books by category                                           |
| POST         | /categories                   | ADMIN | Create a new category in the DB                                     |
| PUT          | /categories/{id}              | ADMIN | Update info for a category with a specific id                       |
| DELETE       | /categories/{id}              | ADMIN | Delete a category with a specific id                                |

#### Shopping cart controller

| Request type | Endpoint                      | Role  | Description                                                         |
|--------------|-------------------------------|-------|---------------------------------------------------------------------|
| POST         | /cart                         | USER  | Add a new cart to the shopping cart                                 |
| GET          | /cart                         | USER  | Retrieve user's shopping cart                                       |

#### Cart item controller

| Request type | Endpoint                      | Role  | Description                                                         |
|--------------|-------------------------------|-------|---------------------------------------------------------------------|
| PUT          | /cart/cart-items/{id}         | ADMIN | Update info for a cart item with a specific id                      |
| DELETE       | /cart/cart-items/{id}         | ADMIN | Delete a cart item with a specific id                               |

#### Order controller

| Request type | Endpoint                      | Role  | Description                                                         |
|--------------|-------------------------------|-------|---------------------------------------------------------------------|
| GET          | /orders                       | USER  | Get all orders                                                      |
| GET          | /orders/{id}/items            | USER  | Get items from order with certain id                                |
| GET          | /orders/{id}/items/{itemId}   | USER  | Get item from order by id                                           |
| POST         | /orders                       | USER  | Create a new order in the DB                                        |
| PATCH        | /orders/{id}                  | ADMIN | Update order status by a specific id                                |

