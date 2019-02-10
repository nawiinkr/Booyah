angular.module("app").controller("userController", function ($scope, $location, $http, UserCrudService, $filter, $rootScope) {
	
	$scope.userData = {};
	getUser();
	
	function getUser(){
		UserCrudService.getUserDetails(getUserSuccess.bind($scope));
	}
	
	function getUserSuccess(response){
		if(response && response.data){
			this.userData.name = response.data.name;
			this.userData.email = response.data.email;
			this.userData.dob = new Date(response.data.dob);
			this.userData.password = "dummypassword";
			this.userData.userId = response.data.userId;
			this.userData.lastUpdate = response.data.timestamp;
			
		}
	}
	
	$scope.updateUserInfo = function(){
		UserCrudService.updateUserDetails($scope.userData, updateUserSuccess.bind($scope));
	}
	
	function updateUserSuccess(response){
		$rootScope.showNotification("Update profile successful");
		getUser();
	}
	
	
});