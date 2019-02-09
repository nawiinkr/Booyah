var app = angular.module('app', ['ngRoute', 'directivesModule','serviceModule', 'infinite-scroll']);

app.config(['$routeProvider', '$locationProvider', function ($routeProvider, $locationProvider) {
     
    $routeProvider.when('/home', {
        templateUrl: 'common/templates/home.html',
        controller: 'homeController',
        reloadOnUrl: true
    }).when('/user', {
    	templateUrl: 'common/templates/user.html',
        controller: 'userController'
    }).when('/show/:id', {
    	templateUrl: 'common/templates/showDetails.html',
        controller: 'showDetailsController'
    }).when('/favorites', {
    	templateUrl: 'common/templates/favorites.html',
        controller: 'favoritesController',
        resolve:{
            loggedIn:onlyLoggedIn
        }
    }).when('/search', {
    	templateUrl: 'common/templates/searchPage.tpl.html',
        controller: 'homeController'
    }).when('/watchlists', {
    	templateUrl: 'common/templates/watchlistsPage.html',
        controller: 'watchlistsController',
        resolve:{
            loggedIn:onlyLoggedIn
        }
    }).otherwise({
        redirectTo: "/home"
        });
    
    $locationProvider.html5Mode({
    	enabled : true,
    	requireBase : false
    });
  }]);

app.run(function($rootScope, $http, $timeout){
	
	$rootScope.userInfo = {
			loggedIn : false
	};
	
	$rootScope.search = "";
	$rootScope.sideMenuOpen = false;
	$rootScope.notificationMessage = "";
	
	$rootScope.showNotification = function(msg){
		$rootScope.notificationMessage = msg;
		$timeout(function () {
			$rootScope.notificationMessage = "";
	    }, 2000);
	}
});

var onlyLoggedIn = function ($location,$q,authService) {
    var deferred = $q.defer();
    authService.checkSession().then(function(response){
    	if(response.data == "Error"){
    		deferred.reject();
            $location.url('/home');
    	}else{
    		deferred.resolve();
    	}
    }, function(){
    	alert("Server error!!");
    });
    return deferred.promise;
};


