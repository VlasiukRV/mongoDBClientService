
;(function(){
	var appControllers = new Object(null);
	
	appControllers.paymentServiceController = function ($scope, paymentManagment){
		initController();
		
		$scope.generateTestPayments = function() {
			paymentManagment.generateTestPayments(function(response) {
				displayResponse($scope, {response: response});
			});			
		};
		$scope.getTestPayments = function() {
			paymentManagment.getTestPayments(function(response) {
				var entityList = response.data;
				displayResponse($scope, {entityName:"Contractors", entityList: entityList});
			});			
		};
		
		function initController() {
			
		}
	};
	
	appControllers.mongoDbServiceController = function ($scope, mongoDbManagment){
		initController();
		
		$scope.startMongoDBServer = function() {
			mongoDbManagment.startMongoDBServer(function(response) {
				displayResponse($scope, {response: response});
			});			
		};
		$scope.stopMongoDBServer = function() {
			mongoDbManagment.stopMongoDBServer(function(response) {
				displayResponse($scope, {response: response});
			});			
		};
		
		$scope.getServerLog = function() {
			mongoDbManagment.getServerLog(function(response) {
				displayResponse($scope, {response: response});
			});			
		};
		
		$scope.testMongoDbConnection = function() {
			mongoDbManagment.testMongoDbConnection(function(response) {
				displayResponse($scope, {response: response});
			});			
		};
						
		$scope.getDataBasesList = function() {
			mongoDbManagment.getDataBasesList(function(response) {
				displayResponse($scope, {response: response});
			})
		};
		
		$scope.createDataBase = function(dataBaseName) {
			mongoDbManagment.createDataBase(dataBaseName, function(response) {
				displayResponse($scope, {response: response});
			});			
		};

		$scope.dropDataBase = function(dataBaseName) {
			mongoDbManagment.dropDataBase(dataBaseName, function(response) {
				mongoDbManagment.getDataBasesList(function(response) {
					displayResponse($scope, {response: response});
				})
			});			
		};
		
		function initController() {
			
		}		
	};
	
	function displayResponse(cont, response){
		for(key in response){
			cont[key] = response[key];
		}
	};			
	
	window.appControllers = appControllers;
}) ()

;(function(){
	var appModel = new Object(null);
	
	appModel.paymentManagment = function(_paymentService){
		var paymentService = _paymentService;
		return{
			generateTestPayments: function(callBack) {
				paymentService.generateTestPayments({}, {},
						function (response) {
							callBack(response);
			    		},
			    		function (httpResponse) {
			    			callBack({errorMessage: "http error: "});
			    		}
				)
			},
			getTestPayments: function(callBack) {
				paymentService.getTestPayments({}, {},
						function (response) {
							callBack(response);
			    		},
			    		function (httpResponse) {
			    			callBack({errorMessage: "http error: "});
			    		}
				)
			} 
		}
	}
	
	appModel.mongoDbManagment = function(_mongoDbService){
		
		var mongoDbService = _mongoDbService;
		return{
			startMongoDBServer: function(callBack) {			
				mongoDbService.startMongoDBServer({}, {},
					    function (response) {
							callBack(response);
					    },
					    function (httpResponse) {
					    	callBack({errorMessage: "http error: "});
					    }
				)				
			},
			stopMongoDBServer: function(callBack) {			
				mongoDbService.stopMongoDBServer({}, {},
					    function (response) {
							callBack(response);
					    },
					    function (httpResponse) {
					    	callBack({errorMessage: "http error: "});
					    }
				)				
			},
			getServerLog: function (callBack) {			
				mongoDbService.getServerLog({}, {},
					    function (response) {
							callBack(response);
					    },
					    function (httpResponse) {
					    	callBack({errorMessage: "http error: "});
					    }
				)
				
			},
			testMongoDbConnection: function(callBack) {			
				mongoDbService.testMongoDbConnection({}, {},
					    function (response) {
							callBack(response);
					    },
					    function (httpResponse) {
					    	callBack({errorMessage: "http error: "});
					    }
				)				
			},
			getDataBasesList: function(callBack) {			
				mongoDbService.getDataBasesList({}, {},
					    function (response) {
							callBack(response);
					    },
					    function (httpResponse) {
					    	callBack({errorMessage: "http error: "});
					    }
				)
			},
			createDataBase: function(dataBaseName, callBack) {
				mongoDbService.createDataBase({dataBaseName: dataBaseName}, {},
					    function (response) {
							callBack(response);
					    },
					    function (httpResponse) {
					    	callBack({errorMessage: "http error: "});
					    }
				);				
			},
			dropDataBase: function(dataBaseName, callBack) {
				mongoDbService.dropDataBase({dataBaseName: dataBaseName}, {},
					    function (response) {
							callBack(response);
					    },
					    function (httpResponse) {
					    	callBack({errorMessage: "http error: "});
					    }
				);				
			}
			
		}
	};
	
	window.appModel = appModel;
})()

;(function(){
	var appService = new Object(null);
	
    appService.appEnvironment = function ($location) {
        var location = $location;
        return {
            getAppHttpUrl: function (urlSuffix) {
                var appAddress = "http://" + location.$$host + ":" + location.$$port;

                return appAddress + "" + urlSuffix;
            }
        }
    };

	appService.setRoute = function (routeProvider) {
	    routeProvider
	        .when('/task01', {
	            templateUrl: '/task01'
	        })
	        ;
	    return routeProvider;        
	};

	appService.paymentService = function(resource, appEnvironment) {
		return resource(
				appEnvironment.getAppHttpUrl('/payment'),
			{
					
			},
			{
				generateTestPayments: {
					method: "PUT",
					url: appEnvironment.getAppHttpUrl('/payment/generateTestPayments')
				},
				getTestPayments: {
					method: "GET",
					url: appEnvironment.getAppHttpUrl('/payment/getTestPayments')
				}
			}
		);
	}
	
	appService.mongoDbService = function(resource, appEnvironment) {
	    return resource(
	    		appEnvironment.getAppHttpUrl('/mongodbservice'),
	        {
	            
	        },
	        {
	        	startMongoDBServer: {
	                method: "PUT",
	                url: appEnvironment.getAppHttpUrl('/mongodbservice/startDBServer')
	            },
	        	stopMongoDBServer: {
	                method: "PUT",
	                url: appEnvironment.getAppHttpUrl('/mongodbservice/stopDBServer')
	            },
	            getServerLog:{
	                method: "GET",
	                url: appEnvironment.getAppHttpUrl('/mongodbservice/getServerLog')	            	
	            },
	        	testMongoDbConnection: {
	                method: "GET",
	                url: appEnvironment.getAppHttpUrl('/mongodbservice/testDBServer')
	            },
	            getDataBasesList: {
	                method: "GET",
	                url: appEnvironment.getAppHttpUrl('/mongodbservice/getDataBasesList')	            	
	            },
	            createDataBase: {
	                method: "POST",
	                url: appEnvironment.getAppHttpUrl('/mongodbservice/createDataBase'),
	                params:{dataBaseName: "@dataBaseName"}                
	            },
	            dropDataBase: {
	                method: "POST",
	                url: appEnvironment.getAppHttpUrl('/mongodbservice/dropDataBase'),
	                params:{dataBaseName: "@dataBaseName"}                
	            }	            
	        }
	    );
	};
		
	window.appService = appService;
})();
