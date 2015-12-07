define(['app','underscore', 'models/user', 'models/login'], function (app,_) {
    app.controller('HomeController', ["$scope", "$state", "userModel", "ticketModel","$q" , function ($scope, $state, userModel, ticketModel,$q) {
       
       $("#headerTitle").html("HOME");

        var agentPromise = ticketModel.getAgents();
        var priorityPromise = ticketModel.getPriorities();
        var categoryPromise = ticketModel.getCategories();
        var statusPromise = ticketModel.getStatuses();

        $q.all([agentPromise,priorityPromise,categoryPromise,statusPromise]).then(function (results) {
            $scope.agents = results[0];
            $scope.priorities = results[1];
            $scope.categories = results[2];
            $scope.statuses= results[3];

            ticketModel.getTickets(userModel.getUser()).then(function(result){
                if(result){
                    result.map(function(ticket){
                        ticket.agent = _.findWhere($scope.agents, {_id: ticket.agentId});
                        ticket.priority = _.findWhere($scope.priorities, {_id: ticket.priorityId});
                        ticket.category = _.findWhere($scope.categories, {_id: ticket.categoryId});
                        ticket.status = _.findWhere($scope.statuses, {_id: ticket.ticketStatusId});
                        ticket.agent = ticket.agent || { _id:0 , name: "unassigned"};
                        return ticket;
                      }
                    );
                    $scope.ticketList = result;
                }else{
                    alert("Unable to load tickets.");
                }
            });

        });




       $scope.onNewCollection = function(){
       		$state.transitionTo("main.newcollection");
       }
       
    }]);
}); 