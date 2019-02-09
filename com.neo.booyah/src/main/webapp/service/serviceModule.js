var serviceModule = angular.module('serviceModule', []);
serviceModule.service("authService",['$http', 'commonService', function($http, commonService){
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

serviceModule.service('FavoritesCrudService', [ '$http', 'commonService', function($http, commonService) {
	 
    this.addFavorite = function addFavorite(showId, successCallback) {
    	var url = 'rest/Users/manage/favorites/add';
    	var data = {
            	"showId" : showId
        };
        return commonService.post(url, successCallback, "Add to Favourites failed", data);
    	/*return $http({
            method : 'POST',
            url : 'rest/Users/manage/favorites/add',
            data : {
            	"showId" : showId
            }
        });*/
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

serviceModule.service('WatchlistCrudService', [ '$http', 'commonService', function($http, commonService) {
	 
    this.addWatchlist = function addWatchlist(name) {
        return $http({
            method : 'POST',
            url : 'rest/Watchlist/create',
            data : {
            	"showId" : name
            }
        });
    }
    
    this.removeWatchlist = function removeWatchlist(showId) {
        return $http({
            method : 'POST',
            url : 'rest/Watchlist/remove',
            data : {
            	"showId" : showId
            }
        });
    }
    
    this.getAllWatchlists = function getAllWatchlists() {
        return $http({
            method : 'POST',
            url : 'rest/Users/getWatchlists'
        });
    }
    
    this.getWatchlistData = function getWatchlistData(id){
    	 return $http({
             method : 'POST',
             url : 'rest/Watchlist/get',
             data : {
             	"watchlistId" : id
             }
         });
    }
    
} ]);