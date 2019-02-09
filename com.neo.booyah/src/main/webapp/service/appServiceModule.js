var app = angular.module('app');
app.service('commonService', ['$http', function($http){
	this.login = {};
	this.login.showLoginDialog = false;
	this.login.signupScreen = false;
	this.setBusy = false;
	
	this.get = function(url, successCallback, message, data){
		return $http({
			url : url,
			method : 'GET',
			data : data
		}).then(successCallback, function(){
			alert(message);
		});
	}
	
	this.post = function(url, successCallback, message, data){
		return $http({
			url : url,
			method : 'POST',
			data : data
		}).then(successCallback,  function(){
			alert(message);
		});
	}
}]);