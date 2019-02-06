angular.module('app').controller('headerController', ['$scope','$http', '$rootScope', 'authService', '$location', 'commonService','$route',function($scope, $http, $rootScope, authService, $location, commonService, $route) {
	var that = $scope;
	$scope.loggedIn = false;
	$scope.liveResults = [];
	$scope.signinSubmitButtonLabel = "Sign In";
	$scope.signupSubmitButtonLabel = "Sign Up";
	$scope.watchlists = [];
	$scope.searchQ = "";
	$scope.loginCreds = {
			user : "",
			password : ""
	}
	$scope.signup = {
	};
	$scope.login= commonService.login;
	
	$scope.userMenuOptions = [
		{
			id : "Profile",
			text : "Profile",
			callback : function(){
			},
			weight : 1
		},{
			id : "logOut",
			text : "Sign Out",
			callback : function(){
				userLogout();
			},
			weight : 100
		},
	];
	
	$scope.navigateToFavorites = function(){
		authService.checkSession().then(function(response){
			if(response.data == "ok"){
				$location.path("/favorites");
			}else{
				$scope.loginDialog(true);
			}
		});
	}
	
	$scope.userMenuOptionClick = function(option){
		authService.checkSession().then(function(response){
			if(response.data == "ok"){
				option.callback();
			}else{
				$scope.loginDialog(true);
			}
		});
	}
	
	$scope.isLoggedIn = function(){
		return this.loggedIn;
	}
	$scope.loginDialog = function(bool){
		this.login.showLoginDialog = bool;
		if(!bool){
			this.login.signupScreen = bool;
		}
	}
	
	$scope.search = function(){
		if($scope.searchQ.length){
			$location.url("/search?q="+$scope.searchQ);
		}
	}
	
	$scope.openPoster = function(){
		var show = arguments[0].show;
		$location.path("/show/"+show.showId);
	}
	
	$scope.getLiveResults = function(query){
		if(query.length >= 3){
			$http({
				url : "rest/ShowService/shows?max=5",
				method : 'POST',
				data : {
					"query" : query,
					"user" : localStorage.getItem("token") != null ? localStorage.getItem("token") : ""
				}
			}).then(function(response){
				$scope.liveResults = response.data;
			});
		}else{
			$scope.liveResults = [];
		}
	}
	
	$scope.toggleSignupScreen = function(){
		this.login.signupScreen = !this.login.signupScreen;
	}
	
	$scope.takeMeHome = function(){
		$location.url("/home");
	}
	$scope.createUser = function(){
		//var that = this;
		$scope.signupSubmitButtonLabel = "Please wait...";
		$http({
			url : "rest/Users/create",
			data : that.signup,
			method : 'POST'
		}).then(function(response){
			if(response.data == "ok"){
				afterAccountCreation.call(that);
			}
		}, function(error){
			alert("unable to create user at the moment!!");
		}).finally(function(){
			$scope.signupSubmitButtonLabel = "Sign Up";
		});
	}
	
	$scope.userLogin = function(){
		//return if user clicks the sign in button when form is already submitting
		if($scope.signinSubmitButtonLabel != "Sign In"){
			return;
		}
		$scope.signinSubmitButtonLabel = "Please wait...";
		authService.login(this.loginCreds).then(function(response){
			if(response.data !== "error"){
				localStorage.setItem("token", response.data);
				afterLoginSuccessful.call(that, true);
			}else{
				alert("Wrong credentials");
			}
		},function(error){
			alert("Unable to login right now");
		}).finally(function(){
			$scope.signinSubmitButtonLabel = "Sign In"
		});
		
	}
	
	userLogout = function(){
		//var that = this;
		//that.toggleProfileDropdown();
		authService.logout().then(function(response){
			if(response.data == "ok"){
				afterLogoutSuccessful.call(that, true);
			}else{
				alert("Logout action failed !!");
			}
		},function(error){
			alert("Unable to login right now");
		});
	}
	
	$scope.openSideMenu = function(event){
		$rootScope.sideMenuOpen = true;
		event.stopPropagation();
	}
	
	$scope.closeSideMenu = function(event){
		$rootScope.sideMenuOpen = false;
		event.stopPropagation();
	}
	
	$scope.toggleProfileDropdown = function(event){
		document.getElementById("profileDropdown").classList.toggle("profile-dropdown-show");
		event.stopPropagation();
	}
	
	window.onclick = function() {
		if($rootScope.sideMenuOpen){
			$rootScope.sideMenuOpen = false;
			$rootScope.$apply();
		}
		var profileDropdown = document.getElementById("profileDropdown");
		if(profileDropdown.classList.contains("profile-dropdown-show")){
			profileDropdown.classList.remove("profile-dropdown-show");
		}
	};
	
	function afterAccountCreation() {
		this.signup = {};
		this.login.showLoginDialog = false;
		this.login.signupScreen = false;
	}
	
	function afterLoginSuccessful(reload) {
		this.loginCreds = {};
		this.login.showLoginDialog = false;
		this.login.signupScreen = false;
		this.loggedIn = true;
		if(reload){
			$rootScope.showNotification("Logged in successfully");
			$route.reload();
		}
		//loadWatchlists();
	}
	
	function afterLogoutSuccessful(reload) {
		this.loggedIn = false;
		if(reload){
			$rootScope.showNotification("Logged out successfully");
			$route.reload();
		}
		
	}
	
	function loadWatchlists(){
				$http({
					url : "rest/Users/getWatchlists",
					method : "POST"
				}).then(function(response){
					$scope.watchlists = response.data;
				});
		}
	
	
	authService.checkSession().then(function(response){
		if(response.data == "ok"){
			afterLoginSuccessful.call(that);
		}else{
			afterLogoutSuccessful.call(that);
		}
	},function(error){
		alert("check session failed");
	});
	
	
}]);