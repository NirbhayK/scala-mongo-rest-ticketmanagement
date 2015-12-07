define(['app', 'models/user', 'models/ticket'], function (app) {
    app.controller('TicketUpdateController', ["$scope", "$state", "userModel", "ticketModel", "$q", "$stateParams", function ($scope, $state, userModel, ticketModel, $q, $stateParams) {
       
       $("#headerTitle").html("Edit Ticket");

      var agentPromise = ticketModel.getAgents();
      var priorityPromise = ticketModel.getPriorities();
      var categoryPromise = ticketModel.getCategories();
      var statusPromise = ticketModel.getStatuses();
      var ticketByIdPromise = ticketModel.getTicketsById($stateParams.id);
      var commentsPromise = ticketModel.loadComments($stateParams.id);

       $q.all([agentPromise,priorityPromise,categoryPromise,statusPromise, ticketByIdPromise,commentsPromise]).then(function (results) {
          $scope.agents = results[0];
          $scope.priorities = results[1];
          $scope.categories = results[2];
          $scope.statusesticket= results[3];

          var ticketToUpdate = results[4];
          $scope.id = ticketToUpdate._id;
          $scope.ticketTitle = ticketToUpdate.ticketTitle;
          $scope.ticketDescription = ticketToUpdate.ticketDescription;
          $scope.customerName = ticketToUpdate.customerName;
          $scope.customerEmail = ticketToUpdate.customerEmail;

          $scope.selectedCategory = ticketToUpdate.categoryId;
          $scope.selectedPriority = ticketToUpdate.priorityId;
          $scope.selectedAgent = ticketToUpdate.agentId;
          $scope.selectedStatus = ticketToUpdate.ticketStatusId;
          $scope.comments = results[5];
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

        var postData = {};
        postData.customerName =  $scope.customerName;
        postData.customerEmail = $scope.customerEmail;
        postData.categoryId = $scope.selectedCategory;
        postData.priorityId = $scope.selectedPriority;
        postData.ticketTitle = $scope.ticketTitle;
        postData.ticketDescription = $scope.ticketDescription;
        postData.agentId =$scope.selectedAgent?$scope.selectedAgent:'00000000';
        postData.ticketStatusId = $scope.selectedStatus;

        ticketModel.updateTicket($scope.id, postData).then(function(result){
            if(result){
              alert("Ticket updated successfully.");
            }else{
              alert("Unable to load Category.");
            }
          });
      };


    $scope.onComment = function(){
      if(!$scope.comment){
        alert("Please add comment.");
        return false;
      }


      var postData = {};
      postData.ticketId =  $scope.id;
      postData.commentBy = userModel.getUser().name;
      postData.comment = $scope.comment;
      postData.commentDate =  new Date().toISOString();

      ticketModel.addComment(postData).then(function(result){
          if(result){
            ticketModel.loadComments($stateParams.id).then(function(result){
                $scope.comments = result;
            });
          }else{
            alert("You have refreshed the page. Please login again to add comment.");
          }
        });
    };
       
    }]);

}); 