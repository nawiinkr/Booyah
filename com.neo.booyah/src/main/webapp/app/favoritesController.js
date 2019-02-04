angular.module("app").controller("favoritesController", function ($scope, $location, $http, $routeParams, authService, FavoritesCrudService) {
	$scope.favoriteShows = [];
	
	authService.checkSession().then(function(response){
		if(response.data == "Error"){
			
		}else{
			//add to favorites
			return FavoritesCrudService.getAllFavorites().then(function(response){
				$scope.favoriteShows = response.data;
			}, function(){
				alert("Get all favorites API failed");
			});
		}
	});
});