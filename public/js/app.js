define(['angularAMD', 'angular-router'], function (angularAMD) {
	var myApp = angular.module('testApp', ["ui.router"]);
    var underscore = angular.module('underscore', []);

    underscore.factory('_', ['$window', function($window) {
      return $window._; // assumes underscore has already been loaded on the page
    }]);

    myApp.config(function($stateProvider, $urlRouterProvider, $locationProvider){

      // For any unmatched url, send to /home
      $urlRouterProvider.otherwise("/login")
      
      $stateProvider
        .state('login', {
            url: "/login",
            controller: "LoginController",
            templateUrl: "assets/js/templates/login.html"
        })
        .state('main', {
            abstract: true,
            url: '/main',
            controller: "MainController",
            templateUrl: "assets/js/templates/main.html"
        })
        .state('main.home', {
            url: "/home",
            controller: "HomeController",
            templateUrl: "assets/js/templates/home.html"
        })

        .state('main.ticket', {
                    url: "/ticket",
                    controller: "TicketController",
                    templateUrl: "assets/js/templates/ticket.html"
                })

          .state('main.ticketupdate', {
                            url: "/ticketupdate/:id",
                            controller: "TicketUpdateController",
                            templateUrl: "assets/js/templates/ticketupdate.html"
                        })
    });

    myApp.controller("MainController", ["$scope", "$state", "userModel", function($scope, $state, userModel){
      $scope.headerText = "HOME";
    }]);
    
    // Bootstrap Angular when DOM is ready
    return angularAMD.bootstrap(myApp);
});



