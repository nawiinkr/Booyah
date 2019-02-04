var app = angular.module('app');
app.service('commonService', function(){
	this.login = {};
	this.login.showLoginDialog = false;
	this.login.signupScreen = false;
});