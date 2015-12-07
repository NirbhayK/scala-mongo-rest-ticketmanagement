define(['app'], function (app) {
    app.factory('ticketModel', function (appService) {
      var normalizeId = function(items){
          items.map(function(item){
            item._id = item._id.$oid;
            return item;
          });
      };

      var getCategories = function(){
        return appService.retrieve("categories", "GET").then(function(result){
          normalizeId(result);
          return result;
        }, function(err){
          return false;
        });
      };


      var getTicketsById = function(id){
        return appService.retrieve("tickets/id/"+id, "GET").then(function(result){
          result._id = result._id.$oid;
          return result;
        }, function(err){
          return false;
        });
      };


      var getStatuses = function(){
          return appService.retrieve("ticketstatuses", "GET").then(function(result){
            normalizeId(result);
            return result;
          }, function(err){
            return false;
          });
        };

       var getPriorities = function(){
         return appService.retrieve("priorities", "GET").then(function(result){
           normalizeId(result);
           return result;
         }, function(err){
           return false;
         });
       };

     var getTickets = function(){
       var methodName = "tickets"
       return appService.retrieve(methodName, "GET").then(function(result){
         normalizeId(result);
         return result;
       }, function(err){
         return false;
       });
     };

     var getAgents = function() {
        var methodName = "agents"
        return appService.retrieve(methodName, "GET").then(function(result){
          normalizeId(result);
          return result;
        }, function(err){
          return false;
        });
     };

      
      var addTicket = function(postData){
        return appService.retrieve("tickets", "POST",postData).then(function(result){
          return result;
        }, function(err){
          return false;
        });
      };

    var updateTicket = function(id, postData){
      return appService.retrieve("tickets/"+id, "PUT",postData).then(function(result){
        return result;
      }, function(err){
        return false;
      });
    };

   var addComment = function(comment){
      return appService.retrieve("comments", "POST",comment).then(function(result){
        return result;
      }, function(err){
        return false;
      });
   };

   var loadComments = function(ticketId){
      return appService.retrieve("comments/"+ticketId, "GET").then(function(result){
        normalizeId(result);
        result.commentDate = Date.parse(result.commentDate);
        return result;
      }, function(err){
        return false;
      });
   };
      return{
        getCategories: getCategories,
        getAgents:getAgents,
        getTickets:getTickets,
        getStatuses:getStatuses,
        getPriorities:getPriorities,
        getTicketsById:getTicketsById,
        addTicket:addTicket,
        updateTicket:updateTicket,
        addComment:addComment,
        loadComments:loadComments
      }
    });
});