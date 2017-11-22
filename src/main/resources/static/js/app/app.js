var app = angular.module('app', [ 'ngResource', 'ngRoute' ]);

app.constant('appConfig', {
	appName : 'MongoDBClient_Server',
	appNamePerformance: 'Mongo DB client (server)'
})

.config([ '$routeProvider', function($routeProvider) {
	appService.setRoute($routeProvider);
} ])

.service('appEnvironment',
		[ '$location', function($location) {
			return appService.appEnvironment($location);
		} ])
		
.service('mongoDbManagment',
		[ 'mongoDbService', function(mongoDbService) {
			return appModel.mongoDbManagment(mongoDbService);
		} ])
.service('paymentManagment',
		[ 'paymentService', function(paymentService) {
			return appModel.paymentManagment(paymentService);			
		} ])
		
.factory('mongoDbService',
		[ '$resource', 'appEnvironment', function($resource, appEnvironment) {
			return appService.mongoDbService($resource, appEnvironment);
		} ])
.factory('paymentService',
		[ '$resource', 'appEnvironment', function($resource, appEnvironment) {
			return appService.paymentService($resource, appEnvironment);
		} ])
		
app.controller('mongoDbServiceController', [ '$scope', 'mongoDbManagment',
	appControllers.mongoDbServiceController ]);
app.controller('paymentServiceController', [ '$scope', 'paymentManagment',
	appControllers.paymentServiceController ]);
