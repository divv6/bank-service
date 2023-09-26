#### This project was created for a test assignment.
#### This project is a RESTful application that allows you to create accounts, retrieve all accounts, deposit money, withdraw money from an account, and transfer funds between accounts.
To run the application, you need to build it using the command "gradlew clean build" and run it in your development environment.

The project uses an H2 database, which is created when the application is launched and stores data in memory. When running the application locally, you can access the database at http://localhost:8080/h2-console/.

The username and password are set in the application.properties file.

The available functionality of the application includes:
1. POST /api/account/create  
Endpoint for creating an account.    
Request Body:  
{
    "name": "test",
    "pin": 1111
}
2. GET /api/account/  
Endpoint for getting all accounts.
3. POST /api/transaction/deposit  
Endpoint for depositing to the account.  
Request Body:  
{
    "number": 600000000000,
    "amount": 30
}
4. POST /api/transaction/withdraw  
Endpoint for withdrawing funds from the account.  
Request Body:  
{
    "number": 600000000000,
    "amount": 30,
    "pin": 1111
}
5. POST /api/transaction/transfer  
Endpoint for transferring between accounts   
Request Body:  
{
    "from": 600000000000,
    "to": 600000000001,
    "pin": 1111,
    "amount": 2
}

To confirm operations involving the withdrawal of funds from an account, a PIN code is used, which is specified when creating an account.
