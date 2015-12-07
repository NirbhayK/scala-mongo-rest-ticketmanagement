define(['app', 'models/user', 'models/ticket'], function (app) {
    app.controller('TicketController', ["$scope", "$state", "userModel", "ticketModel", "$q", function ($scope, $state, userModel, ticketModel, $q) {
       
       $("#headerTitle").html("Add Ticket");

      var agentPromise = ticketModel.getAgents();
      var priorityPromise = ticketModel.getPriorities();
      var categoryPromise = ticketModel.getCategories();
      var statusPromise = ticketModel.getStatuses();

       $q.all([agentPromise,priorityPromise,categoryPromise,statusPromise]).then(function (results) {
          $scope.agents = results[0];
          $scope.priorities = results[1];
          $scope.categories = results[2];
          $scope.statusesticket= results[3];
      });




      $scope.onSubmit = function(){
        if(!$scope.selectedCategory){
          alert("Category is required.");
          return false;
        }
        if(!$scope.ticketTitle){
          alert("Title is required.");
          return false;
        }
        if(!$scope.customerEmail){
          alert("Customer email is required.");
          return false;
        }


        var postData = {};
        postData.customerName =  $scope.customerName;
        postData.customerEmail = $scope.customerEmail;
        postData.categoryId = $scope.selectedCategory;
        postData.priorityId = $scope.selectedPriority;
        postData.ticketTitle = $scope.ticketTitle;
        postData.ticketDescription = $scope.ticketDescription;
        postData.agentId = $scope.selectedAgent?$scope.selectedAgent:'00000000';
        postData.ticketStatusId = $scope.selectedStatus;

        ticketModel.addTicket(postData).then(function(result){
        if(result){
          alert("Ticket added successfully.");
        }else{
          alert("Unable to load .");
        }
      });
      };
       
    }]);

}); 