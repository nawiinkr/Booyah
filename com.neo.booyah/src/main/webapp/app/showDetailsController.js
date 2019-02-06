angular.module("app").controller("showDetailsController", function ($scope, $location, $http, $routeParams) {
	var id = $routeParams.id;
	$http.get("rest/ShowService/shows/" + id).then(function(response){
        $scope.showData = response.data;
    }, function(error){
    	alert("Call failed");
    });
});