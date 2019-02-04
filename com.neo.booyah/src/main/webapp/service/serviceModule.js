var serviceModule = angular.module('serviceModule', []);
serviceModule.service("authService",['$http', function($http){
	var baseUrl = "rest/auth";
	
	this.login = function(data){
		return $http({
			url : baseUrl + "/login",
			method : 'POST',
			data : data
		});
	}
	
	this.logout = function(data){
		return $http({
			url : baseUrl + "/logout",
			method : 'POST'
		});
	}
	
	this.checkSession = function(){
		return $http({
			url : baseUrl + "/checkSession",
			method : 'POST',
			data : {
				"user" : localStorage.getItem("token") != null ? localStorage.getItem("token") : ""
			}
		});
	}
	
}]);

serviceModule.service('FavoritesCrudService', [ '$http', function($http) {
	 
    this.addFavorite = function addFavorite(showId) {
        return $http({
            method : 'POST',
            url : 'rest/Users/manage/favorites/add',
            data : {
            	"showId" : showId
            }
        });
    }
    
    this.removeFavorite = function removeFavorite(showId) {
        return $http({
            method : 'POST',
            url : 'rest/Users/manage/favorites/remove',
            data : {
            	"showId" : showId
            }
        });
    }
    
    this.getAllFavorites = function getAllFavorites() {
        return $http({
            method : 'GET',
            url : 'rest/Users/manage/favorites/getAllFavorites'
        });
    }
    
} ]);