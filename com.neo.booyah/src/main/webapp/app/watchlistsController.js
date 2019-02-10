angular.module("app").controller("watchlistsController", function ($scope, $location, $http, $routeParams, authService, WatchlistCrudService) {
	$scope.watchlists = [];
	$scope.selectedWatchlist = {};
	$scope.selectedWatchlistData = {};
	
	authService.checkSession().then(function(response){
		if(response.data == "Error"){
			
		}else{
			 WatchlistCrudService.getAllWatchlists().then(function(response){
				$scope.watchlists = response.data;
				
				if($scope.watchlists.length){
					$scope.selectedWatchlist = response.data[0];
					return WatchlistCrudService.getWatchlistData($scope.watchlists[0].watchlistId);
				}
				
			}, function(){
				alert("Get all Watchlists API failed");
			}).then(function(response){
				if(response && response.data){
					$scope.selectedWatchlistData = response.data;
				}
			},function(){
				alert("getting watchlist data failed")
			});
		}
	});
	
	$scope.watchlistSelectionChange = function(){
		var selectedWatchlist = $scope.selectedWatchlist;
		
		WatchlistCrudService.getWatchlistData(selectedWatchlist.watchlistId).then(function(response){
			$scope.selectedWatchlistData = response.data;
		});
		
	}
});