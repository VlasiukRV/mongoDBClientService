DESCRIPTION

This application provides API to translate SQL query into the query for Mongodb.
To use this aplication please refer to https://github.com/VlasiukRV/mongoDBClient

To build application please use Maven.
IMPORTANT!!! Before building the application install your connection settings to mongoDB in application.properties sach as host, port, database.

HELP

1.API:
To get results query from MongoDB send http request to server
	- url: /api/sqlQuery;
	- method: GET; 
	- request param: query='SELECT * FROM payment'

2. Application has user grafic interface to test mongoDB connection, to create test data base,... 
Grafic application allows you to perform the following actions:
	- start mongo db server (url: /mongodbservice/startDBServer method: PUT)
	- get mongo db server log: (url: /mongodbservice/getServerLog method: GET)
	- stop ongo db server (url: /mongodbservice/stopDBServer method: PUT )
	- test mongo db server connection (url:/testDBServer method:GET)
	- get data base list (url:/getDataBasesList method:GET)
	- create data base (url:/createDataBase?dataBaseName='<yourDbName>' method: POST)
	- drop data base (url:/dropDataBase?dataBaseName='<yourDbName>' method: POST)

3. To test connection to mongo db, application provides API to generate test payment collection. By this API application generats collection "payment" in a current dataBase. You can perform the folowing actions:
	- generate collection "payment" (url: /payment/generateTestPayments method: PUT)
	- get list of test payments (url:/getTestPayments method:GET)
Paement collection has following structure:
	{
	 amount: <...>,
	 commission: <...>,
	 customer: {accountNumber:<...>,
		    name: <...>},
 	 description: <...>
	}


TESTED:
Spring Boot 1.5.8.RELEASE
MongoDB 3.4
Tomcat 7.0.68
Maven 4.0.0
