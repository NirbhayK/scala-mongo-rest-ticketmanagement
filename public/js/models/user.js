define(['app'], function (app) {
    app.factory('userModel', function (appService) {
    	var _user = {};
    	var setUser = function(user){
    		//appService.retrieve();
    		_user = user;
    	};

    	var getUser = function(user){
    		return _user || {};
    	};
       
       	return{
       		setUser: setUser,
       		getUser: getUser
       	}
    });
});