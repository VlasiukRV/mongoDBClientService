DESCRIPTION

This application provide api for translate SQL query into query for mongodb.
For use this aplication you can find graphical application in https://github.com/VlasiukRV/mongoDBClient

For build application use maven.
ATTENTION!!! Before build application insatll your connection settings to mongoDB in application.properties sach as host, port, database.

HELP

1.API:
For get results query from mongoDB send http request to server
	- url: /api/sqlQuery;
	- method: GET; 
	- request param: query='SELECT * FROM payment'

2. Application hav user grafic interface for test mongoDB connection, for create test data base,... 
Grafic applicatioon allows you to perform the following actions:
	- start mongo db server (url: /mongodbservice/startDBServer method: PUT)
	- get mongo db server log: (url: /mongodbservice/getServerLog method: GET)
	- stop ongo db server (url: /mongodbservice/stopDBServer method: PUT )
	- test mongo db server connection (url:/testDBServer method:GET)
	- get data base list (url:/getDataBasesList method:GET)
	- create data base (url:/createDataBase?dataBaseName='<yourDbName>' method: POST)
	- drop data base (url:/dropDataBase?dataBaseName='<yourDbName>' method: POST)

3. For test connection to mongo db application provides api to generate test payment collection. By this api application generate collection "payment" in current dataBase. You can perfome the folowing actions:
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


Tested:

Spring Boot 1.5.8.RELEASE
MongoDB 3.4
Tomcat 7.0.68
Maven 4.0.0
