angular.module("app").controller("searchController", function ($scope, $location, $http, $routeParams, authService, FavoritesCrudService) {
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
		$scope.shows = response.data;
	})
	
});