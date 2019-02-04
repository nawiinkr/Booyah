angular.module("app").controller("homeController", function ($scope, $location, $http, authService, commonService, FavoritesCrudService, $rootScope) {
	$scope.postersLoaded = false;
	$scope.shows = [];
	$scope.watchlists = [];
	$scope.newWatchlist = {
			name : ""
	};
	$scope.selectedWatchlist = null;
	$scope.selectedShow = null;
	$scope.loadMoreInProgress = false;
	$scope.dummies = [{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{},{}];
	$scope.lazyLoad = {
			max : 20,
			skip: 0,
			moreToLoad: true
	};
	$scope.login= commonService.login;
	$scope.showWatchlistDialog = false;
	if($location.path() == "/search"){
		var params = $location.search();
		$scope.query = params.q;
		$scope.shows = [];
		$http({
			url : "rest/ShowService/shows",
			method : 'POST',
			data : {
				"query" : $scope.query,
				"user" : localStorage.getItem("token") != null ? localStorage.getItem("token") : ""
			}
		}).then(function(response){
			$scope.postersLoaded = true;
			$scope.shows = response.data;
		});
		
	}else{
		getShows();
	}
	
	function addShowToWatchlist(watchlistName, showId){
		return $http({
			url : "rest/Watchlist/addShow",
			method : 'POST',
			data : {
				"watchlistName" : watchlistName,
				"showId" : showId
			}
		});
	}
	
	function createNewWatchlist(name){
		return $http({
			url : "rest/Watchlist/create",
			method : 'POST',
			data : {
				"name" : name
			}
		});
	}
	
	$scope.saveWatchlist = function(){
		if($scope.selectedWatchlist != null){
			addShowToWatchlist($scope.selectedWatchlist.name, $scope.selectedShow.showId).then(function(response){
				if(response.data == "ok"){
					$scope.closeWatchlistDialog();
					$rootScope.showNotification("Show added to watchlist successfully");
				}else{
					$rootScope.showNotification("Adding show to watchlist failed!!");
				}
			});
		}else{
			if($scope.newWatchlist.name == ""){
				return;
			}
			createNewWatchlist($scope.newWatchlist.name).then(function(response){
				if(response.data == "ok"){
					addShowToWatchlist($scope.newWatchlist.name, $scope.selectedShow.showId).then(function(response){
						if(response.data == "ok"){
							$scope.closeWatchlistDialog();
							$rootScope.showNotification("Show added to watchlist successfully");
						}else{
							$rootScope.showNotification("Adding show to watchlist failed!!");
						}
					});
				}else{
					$rootScope.showNotification("Adding show to watchlist failed!!");
				}
			});
		}
		
	}
	
	$scope.selectThisWatchlist = function(watchlist){
		var parentScope = this.$parent; 
		parentScope.watchlists.forEach(function(temp){
			if(temp == watchlist){
				watchlist.selected = true;
				parentScope.selectedWatchlist = watchlist;
			}else{
				temp.selected = false;
			}
		});
		
	}
	
	$scope.clearListSelection = function(){
		this.watchlists.forEach(function(temp){
				temp.selected = false;
		});
		
		this.selectedWatchlist = null;
	}
	
	$scope.addToWatchlist = function(context, show){
		authService.checkSession().then(function(response){
			if(response.data == "Error"){
				//show Login dialog
				$scope.login.showLoginDialog = true;
			}else{
				$scope.selectedShow = show;
				$http({
					url : "rest/Users/getWatchlists",
					method : "POST"
				}).then(function(response){
					$scope.watchlists = response.data;
				}).finally(function(){
					$scope.showWatchlistDialog = true;
				});
			}
		});
	}
	
	$scope.closeWatchlistDialog = function(){
		$scope.showWatchlistDialog = false;
		$scope.newWatchlist.name = "";
		$scope.selectedWatchlist = null;
		$scope.selectedShow = null;
	}
	
	$scope.openPoster = function(){
		var show = arguments[0].show;
		$location.path("/show/"+show.showId);
	}
	
	$scope.loadMore = function(){
		if($scope.lazyLoad.moreToLoad && !$scope.loadMoreInProgress){
			$scope.loadMoreInProgress = true;
			getShows();
		}
	}
	
	$scope.addToFavourites = function(that, show){
		//var show = arguments[0];
		
		authService.checkSession().then(function(response){
			if(response.data == "Error"){
				//show Login dialog
				$scope.login.showLoginDialog = true;
			}else{
				//add to favorites
				return FavoritesCrudService.addFavorite(show.showId).then(function(response){
					if(response.data == "ok"){
						var s = $scope;
						$scope.shows[that.$index].isFavorite = show.showId;
						$rootScope.showNotification("Show added to favorites successfully");
					}else{
						alert("Failed to add favorite");
					}
				}, function(){
					alert("Add to favorite API failed");
				});
			}
		});
	}
	
	$scope.removeFromFavourites = function(that, show){
		//var show = arguments[0];
		authService.checkSession().then(function(response){
			if(response.data == "Error"){
				//show Login dialog
				$scope.login.showLoginDialog = true;
			}else{
				//add to favorites
				return FavoritesCrudService.removeFavorite(show.showId).then(function(response){
					if(response.data == "ok"){
						$scope.shows[that.$index].isFavorite = false;
						$rootScope.showNotification("Show removed from favorites successfully");
					}else{
						alert("Failed to remove favorite");
					}
				}, function(){
					alert("Remove to favorite API failed");
				});
			}
		});
	}
	function loadShowsInModel(response){
			if(response.data && response.data.length > 0){
				response.data.forEach(function(d){
					$scope.shows.push(d);
				});
				$scope.lazyLoad.skip += $scope.lazyLoad.max;
			}else{
				$scope.lazyLoad.moreToLoad = false;
			}
	        $scope.postersLoaded = true;
	        $scope.loadMoreInProgress = false;
	}
	
	function errorInLoadingShows(){
    	alert("Call failed");
    	$scope.postersLoaded = true;
    }
	
	function getShows(){
		return $http({
			url : getShowsUrl(),
			method : 'POST',
			data : {
				"user" : localStorage.getItem("token") != null ? localStorage.getItem("token") : ""
			}
		}).then(loadShowsInModel,errorInLoadingShows);
	}
	
	function getShowsUrl(){
		return "rest/ShowService/shows" + "?max=" + $scope.lazyLoad.max + "&skip=" + $scope.lazyLoad.skip ;
	}
	
	function addShows(){
		 return $http.get("rest/ShowService/addShows");
	}
	
});