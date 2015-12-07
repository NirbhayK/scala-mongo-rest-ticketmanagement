define(['app', 'jquery'], function (app, $) {
    app.service('appService', ['$http', '$q', function($http, $q) {
    	this.retrieve = function(methodName, methodType, postData) {
    		postData= JSON.stringify(postData);
            var req = {
                url:  methodName,
                type: methodType,
                contentType: "application/json", // content type sent to server
                dataType: "json", //Expected data format from server
                data: postData || {}
            };
            req.headers = {};
            req.headers["REST-API-KEY-HEADER"] = "xyz";
            req.headers["Accept"] = "application/json";
            //req.headers["X-Requested-With"] = "XMLHttpRequest";

        	 var d = $q.defer();

	        /*$http(req).success(function(response, status, headers, config){
                d.resolve(response);
            }).
            error(function(error, status, headers, config) {
                d.reject(error);
            });*/
            $("#overlay").show();
            $.ajax(req).done(function(result) {
                $("#overlay").hide();
                d.resolve(result);
            }).fail(function(error){
                $("#overlay").hide();
                d.reject(error);
            });

	        return d.promise;
    	}
	}]);
}); 