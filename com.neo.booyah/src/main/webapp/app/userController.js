angular.module("app").controller("userController", function ($scope, $location, $http, UserCrudService, $filter, $rootScope) {
	
	$scope.userData = {};
	$scope.showFileUploadDialog = false;
	$scope.profileImage = {
			file : undefined,
			maxSizeError: false
	};
	
	getUser();
	
	
	
	function getUser(){
		UserCrudService.getUserDetails(getUserSuccess.bind($scope)).then(function(){
			$http({
		    	url : "rest/Users/getProfileImage",
		    	method : 'GET'
		    }).then(function(response){
		    	if(response.data.avatar){
			    	document.getElementById("userProfilePic").src = "data:image/png;base64," + response.data.avatar;
		    	}
		    });
		});
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
	
	$scope.setFileUploaderVisible = function(bool){
		$scope.showFileUploadDialog = bool;
	}
	
	$scope.uploadProfileImage =function(){
		var file = $scope.profileImage.file;
        var uploadUrl = "rest/Users/upload";
        
        var fd = new FormData();
        fd.append('file', file);
        //fd.append('type', "multipart/form-data");
        
        $http({
        	url : uploadUrl,
        	method : 'POST',
        	data : fd,
        	 transformRequest: angular.identity,
             headers: {'Content-Type': undefined}
        }).then(function(){
        	$scope.setFileUploaderVisible(false);
	    	updateUserSuccess();
        });
	}
	
	$scope.removeProfilePicture = function(){
		$http({
	    	url : "rest/Users/removeProfileImage",
	    	method : 'GET'
	    }).then(function(response){
	    	$rootScope.showNotification("Profile Picture removed successfully");
	    	document.getElementById("userProfilePic").src = "assets/img/userdummy.png";
			getUser();
	    });
	}
	
	
});