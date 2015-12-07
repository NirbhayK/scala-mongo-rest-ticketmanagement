define(['app'], function (app) {
    app.factory('roleModel', function (appService) {

        var getPriorities = function(){
           var methodName = "priorities"
           return appService.retrieve(methodName, "GET").then(function(result){
             return result;
           }, function(err){
             //alert("Unable to login "+ err);
             return false;
           });
         };

       	return{
       		getPriorities: getPriorities
       	}
    });
});