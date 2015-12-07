define(['app', 'models/login', 'models/user'], function (app) {
    app.controller('LoginController', ["$scope", "$state", "loginModel", "userModel", function ($scope, $state, loginModel, userModel) {
      $scope.loginAs = "";
      $scope.onLogin = function(){
     		if($scope.email){
     			loginModel.login({email: $scope.email}).then(function(result){
     				if(result){
                        userModel.setUser(result);
     					$state.transitionTo("main.home");
     				}else{
     					alert("Invalid Agent.");
     					$scope.password = "";
     				}
     			});
     		}else{
     			alert("Please Enter Email.");
     		}
       }
    }]);
}); 