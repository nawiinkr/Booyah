angular.module("app").controller("showDetailsController", function ($scope, $location, $http, $routeParams) {
	var id = $routeParams.id;
	$http.get("rest/ShowService/shows/" + id).then(function(response){
        $scope.showData = response.data[0];
    }, function(error){
    	alert("Call failed");
    });
});