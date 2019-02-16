var directives = angular.module('directivesModule',[]);

directives.config(['$locationProvider',function($locationProvider){
	$locationProvider.html5Mode({
    	enabled : true,
    	requireBase : false
    });
}]);
directives.directive('login', function() {
    return {
        restrict: 'E',
        templateUrl: 'directives/login/login.tpl.html'
    }
});

directives.directive('register', function() {
    return {
        restrict: 'E',
        templateUrl: 'directives/register/registration.tpl.html'
    }
});

directives.directive('posterTemplate', function() {
    return {
        restrict: 'E',
        templateUrl: 'directives/poster/posterTemplate.tpl.html'
    }
});

directives.directive('searchField', function() {
    return {
        restrict: 'E',
        templateUrl: 'directives/search/searchField.tpl.html'
    }
});

directives.directive('watchlistTemplate', function() {
    return {
        restrict: 'E',
        templateUrl: 'directives/watchlist/watchlist.tpl.html'
    }
});

directives.directive('validateBirth', [function () {
	
    function validateDOB(value) {
        //replace me with correct logic
        return value != undefined && value != null && (new Date().getTime() > value.getTime());
    }
    
    function checkAge(value) {
        //replace me with correct logic
        return value != undefined && value != null && _calculateAge(value) >=13;
    }
    
    function _calculateAge(birthday) { // birthday is a date
        var ageDifMs = Date.now() - birthday.getTime();
        var ageDate = new Date(ageDifMs); // miliseconds from epoch
        return Math.abs(ageDate.getUTCFullYear() - 1970);
    }

    return {
        restrict: 'A',
        require: 'ngModel',
        link: function (scope, ele, attrs, ngModel) {
            ngModel.$validators.invalidDOB = function (modelValue) {
                return validateDOB(modelValue);
            }
            
            ngModel.$validators.ageRestrict = function (modelValue) {
                return checkAge(modelValue);
            }
        }
    }
}]);

directives.directive('loading',   ['$http' ,function ($http)
    {
    return {
        restrict: 'A',
        link: function (scope, elm, attrs)
        {
            scope.isLoading = function () {
                return $http.pendingRequests.length > 0;
            };

            scope.$watch(scope.isLoading, function (v)
            {
                if(v){
                    elm.show();
                }else{
                    elm.hide();
                }
            });
        }
    };

}]);

directives.directive('fileUpload', function() {
    return {
        restrict: 'E',
        templateUrl: 'directives/fileUploader/fileUploaderTemplate.tpl.html'
    }
});

directives.directive('fileModel', ['$parse', function ($parse) {
    return {
        restrict: 'A',
        link: function(scope, element, attrs) {
           var model = $parse(attrs.fileModel);
           var modelSetter = model.assign;
           
           element.bind('change', function() {
              scope.$apply(function() {
                 modelSetter(scope, element[0].files[0]);
              });
           });
        }
     };
  }]);

directives.directive('validFile', function($parse) {
    return {
        require: 'ngModel',
        restrict: 'A',
        link: function(scope, el, attrs, ngModel) {
            var model = $parse(attrs.ngModel);
            var modelSetter = model.assign;
            var maxSize = 1000000;
            el.bind('change', function() {

                scope.$apply(function() {
                    scope.profileImage.maxSizeError = false;
                    if (el[0].files.length > 1) {
                        modelSetter(scope, el[0].files);
                    } else {
                        modelSetter(scope, el[0].files[0]);
                    }
                    var fileSize = el[0].files[0].size;
                    if (fileSize > maxSize) {
                        scope.profileImage.maxSizeError = true;
                    }
                });
            });
        }
    }
});