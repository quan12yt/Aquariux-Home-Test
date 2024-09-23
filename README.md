# Crypto Trading System - Test

### Project Features

1. User able to buy/sell the supported crypto trading pairs
2. User able to see the list of trading transactions
3. User able to see the crypto currencies wallet balance

### Project Requirement
1. Java 17
2. Spring boot v3.3.4
3. H2 Database
4. ...

### How to use from this sample project
##### Clone the repository
```
git clone https://github.com/quan12yt/Aquariux-Home-Test.git
```
##### Test api with postman
```
https://www.postman.com/downloads/
```

##### Sample User Data
```
These user's data will be addded to database after application's startup
id: 1, name: "Mark", balance: 50000
id: 2, name: "Tom", balance: 50000
id: 3, name: "Hank", balance: 50000
id: 4, name: "Luci", balance: 50000
```

### RestApi Enpoints

* Retrieve latest best buy and sell prices :GET  http://localhost:8080/v1/trade/{symbol}
````
 Success Response Example
   {
     "symbol": "BTCUSDT",
     "bestSellPrice": 63460.00,
     "bestBuyPrice": 63460.01
   }
  Error Response Example
   {
     "statusCode": 400,
     "message": "Invalid symbol"
   }
 ````
 -------------------------------------------------------------
* Perform BUY/SELL actions :POST http://localhost:8080/v1/trade
 ````
  Example Request
   {
     "method": "SELL",
     "userId": 1,
     "symbol": "BTCUSDT",
     "quantity": 0.2
   }
  Success Response Example
    {
      true
    }
  Error Response Example
   {
     "statusCode": 400,
     "message": "The remaining wallet balance is not enough"
   }
  AND MORE..  
   ````
  -------------------------------------------------------------

* Retrieve the user’s crypto currencies wallet balance : GET http://localhost:8080/v1/trade/cryptoBalance/{id}
 ````
 Success Response Example
   {
     "userName": "Mark",
     "balance": 41262.68,
     "holdingCrypto": [
       {
         "symbol": "BTCUSDT",
         "quantity": 0.10
       },
       {
         "symbol": "ETHUSDT",
         "quantity": 0.90
       }
    ]
   }
 Error Response Example
   {
     "statusCode": 400,
     "message": "Invalid userId"
   }
  ````
  -------------------------------------------------------------

* Retrieve the user trading history : GET http://localhost:8080/v1/trade/transactions/{id}
 ````
  Success Response Example
   [
     {
       "id": 1,
       "method": "BUY",
       "userId": 1,
       "symbol": "BTCUSDT",
       "price": 63600.92,
       "quantity": 0.10,
       "amount": 6360.09,
       "createdAt": "2024-09-23T16:54:13.29077"
     },
     {
       "id": 2,
       "method": "SELL",
       "userId": 1,
       "symbol": "BTCUSDT",
       "price": 63600.92,
       "quantity": 0.10,
       "amount": 6360.09,
       "createdAt": "2024-09-23T16:54:14.289175"
     }
   ]
  -------------------------------------------------------------
````
