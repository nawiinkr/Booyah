angular.module("app").controller("sideMenuController", function ($scope, $location, authService, commonService) {
	$scope.menuOptions = ["Home", "Genres", "Favorites", "Watchlist"];
	
	$scope.login= commonService.login;
	
	$scope.navigateFromMenu = function navigateFromMenu(that, menuOption){
		switch(menuOption){
			case "Home" :
				$location.path("/");
				return;
			case "Genres" :
				$location.path("/genres");
				return;
		}
		
		authService.checkSession().then(function(response){
			if(response.data == "Error"){
				//show Login dialog
				$scope.login.showLoginDialog = true;
			}else{
				switch(menuOption){
				case "Favorites" :
					$location.path("/favorites");
					break;
				case "Watchlist" :
					$location.path("/watchlist");
					break;
			}
			}
		});
	}
});