define(['app'], function (app) {
    app.factory('loginModel', function (appService) {
      var login = function(loginInfo){
        var methodName =  "agents"
        return appService.retrieve(methodName+"/"+loginInfo.email, "GET").then(function(result){
          return result;
        }, function(err){
          //alert("Unable to login "+ err);
          return false;
        });
      };
      

      return{
        login: login
      }
    });
});