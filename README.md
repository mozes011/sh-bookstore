# sh-bookstore

local server for testing adding and purchasing books.
support the following requests
Add Book:
URL : http://localhost:8082/books/add
Type: POST
Request Body:
{ "name":"<book name>",
    "author":"",
    "quantity":<number>
}

result - String message Book was added.

Buy Book:
URL : http://localhost:8082/books/buy
Type: POST
Request Body:
{ "name":"<book name>",
    "author":"<book author>",
    "quantity":<number of copies to buy>
}

result - String message Book was successfully or not successfully purchased.

get available books:
URL : http://localhost:8082/books
Type: GET

result - JSON list with Object of Book (name,author ,quantity) which currently in stock.



Add Book:
URL : http://localhost:8082/books/add/bulk
Type: POST
Request Body:
[{ "name":"<book1 name>",
     "author":"",
     "quantity":<number>
 },
 { "name":"<book2 name>",
       "author":"",
       "quantity":<number>
   }]

result - String message Book was added.
