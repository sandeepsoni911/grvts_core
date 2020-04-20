'use strict';
angular.module('mainApp').controller('opportunityCtrl', function ($rootScope, $scope, $firebaseObject, $firebaseArray, $firebaseAuth) {
  $('.global-Mask').show();
  firebase.auth().getRedirectResult().then(function(result) {
    if(!result.credential && !firebase.auth().currentUser) {
      var provider = new firebase.auth.GoogleAuthProvider();
      provider.addScope('https://www.googleapis.com/auth/plus.login');
      firebase.auth().signInWithRedirect(provider);
    } else {
      $scope.executeAfterLogin();
    }
  }).catch(function(err) {
    console.error(err);
    $('.global-Mask').hide();
    $scope.$apply(function() {
      $('.errorCls').show();
      $('.dataCls').hide();
      $scope.errorMsg = "Error! "+err.message;
    });
  });

  $scope.executeAfterLogin = function() {
    $scope.getUrlVars = function() {
      var query_string = {};
      var query = window.location.search.substring(1);
      var vars = query.split("&");
      for (var i=0;i<vars.length;i++) {
        var pair = vars[i].split("=");
        if (typeof query_string[pair[0]] === "undefined") {
          query_string[pair[0]] = decodeURIComponent(pair[1]);
        } else if (typeof query_string[pair[0]] === "string") {
          var arr = [ query_string[pair[0]],decodeURIComponent(pair[1]) ];
          query_string[pair[0]] = arr;
        } else {
          query_string[pair[0]].push(decodeURIComponent(pair[1]));
        }
      }
      return query_string;
    }

    $scope.urlVars = $scope.getUrlVars();
    var urlCrmOpportunityId = $scope.urlVars.crmOpportunityId;
    if(urlCrmOpportunityId) {
      var dbRef = firebase.database().ref();
      var searchQry = dbRef.child("search").orderByChild('crmOpportunityId').startAt(urlCrmOpportunityId).endAt(urlCrmOpportunityId+'\uf8ff').limitToFirst(1);
      var searchObj = $firebaseArray(searchQry);
      searchObj.$loaded().then(function() {
        $('.global-Mask').hide();
        if(searchObj.length > 0) {
          $('.dataCls').show();
          var agentId = searchObj[0].agentId;
          var opportunityId = searchObj[0].opportunityId;
          var contactId = searchObj[0].contactId;
          $scope.agentEmail = searchObj[0].agentEmail;
          $scope.crmOpportunityId = searchObj[0].crmOpportunityId;
          $scope.lookupContactInfo(agentId, contactId);
          $scope.lookupOppInfo(agentId, opportunityId);
          $scope.lookupAgentNotes(agentId, opportunityId);
        } else {
          $('.errorCls').show();
          $('.dataCls').hide();
          $scope.errorMsg = "Invalid URL! CRM Oppurtunity Id is not valid/found.";
        }
      }).catch(function(err) {
		$('.global-Mask').hide();
        console.error(err);
        $('.errorCls').show();
        $('.dataCls').hide();
        $scope.errorMsg = "Error! You are not authorized to access.";
      });

      $scope.lookupContactInfo = function (agentId, contactId) {
        dbRef.child("agents/"+agentId+"/contacts/"+contactId).on('child_changed',function() {
          $scope.fetchContactInfo(agentId, contactId);
        });
        dbRef.child("agents/"+agentId+"/contacts/"+contactId).on('child_added',function() {
          $scope.fetchContactInfo(agentId, contactId);
        });
        $scope.fetchContactInfo(agentId, contactId);
      }
      $scope.fetchContactInfo = function(agentId, contactId) {
        var contactObj = $scope.fireBaseCall(agentId, contactId, 'contacts');
        contactObj.$loaded().then(function() {
          $scope.contactName = contactObj.firstName + ' ' + contactObj.lastName;
          $scope.contactNumber = contactObj.phoneNumbers ? (contactObj.phoneNumbers[0].number) : '';
          $scope.contactEmail = contactObj.emails ? (contactObj.emails[0]) : '';
        }).catch(function(err) {
          console.error(err);
        });
      }

      $scope.lookupOppInfo = function (agentId, opportunityId) {
        dbRef.child("agents/"+agentId+"/opportunities/"+opportunityId).on('child_changed',function() {
          $scope.fetchOppInfo(agentId,opportunityId);
        });
        dbRef.child("agents/"+agentId+"/opportunities/"+opportunityId).on('child_added',function() {
          $scope.fetchOppInfo(agentId,opportunityId);
        });
        $scope.fetchOppInfo(agentId,opportunityId);
      }
      $scope.fetchOppInfo =  function(agentId, opportunityId) {
        var oppInfo = $scope.fireBaseCall(agentId, opportunityId, 'opportunities');
        oppInfo.$loaded().then(function() {
            var res = arguments[0];
            $scope.budget = res && res.budget ? res.budget : '';
            $scope.oppType = res && res.opportunityType ? res.opportunityType : '';
            $scope.notes = res && res.notes ? res.notes : '';
            $scope.preApprovedForMortgage = res && res.preApprovedForMortgage ? res.preApprovedForMortgage : '';
            $scope.stage = res && res.stage ? res.stage : '';
            $scope.buyerReadinessTimeline = res && res.buyerReadinessTimeline ? res.buyerReadinessTimeline : '';
            $scope.areaOfInterest = res && res.areaOfInterest ? res.areaOfInterest : '';
        }).catch(function(err) {
          console.error(err);
        });
      }

      $scope.lookupAgentNotes = function (agentId, opportunityId) {
        dbRef.child("agents/"+agentId+"/agentNotes").orderByChild('opportunityId').equalTo(opportunityId).on('child_changed',function() {
          $scope.fetchAgentNotes(agentId,opportunityId);
        });
        dbRef.child("agents/"+agentId+"/agentNotes").orderByChild('opportunityId').equalTo(opportunityId).on('child_added',function() {
          $scope.fetchAgentNotes(agentId,opportunityId);
        });
        $scope.fetchAgentNotes(agentId,opportunityId);
      }
      $scope.fetchAgentNotes = function(agentId, opportunityId) {
        dbRef.child("agents/"+agentId+"/agentNotes").orderByChild('opportunityId').equalTo(opportunityId).once('value').then(function(snapshot) {
          if(snapshot.val() != null){
            $scope.$apply(function(){
              $scope.cols =['details', 'createdDtmFormatted'];
              $scope.colsName = ['Details', 'Created Date'];
              $scope.notesDetails = $scope.iterationObj(snapshot.val());
            });
          }
        }).catch(function(err) {
          console.error(err);
        });
      }

      $scope.fireBaseCall = function(searchVar, reponseSearchVal, searchNode) {
        var objRef = dbRef.child("agents/"+searchVar+"/"+searchNode+"/"+reponseSearchVal),
        obj = $firebaseObject(objRef);
        return obj;
      }
      $scope.checkTime = function(val){
        if(val < 10) {
          val = "0" + val;
        }
        return val;
      }
      $scope.iterationObj = function(obj){
        var arr = [];
        angular.forEach(obj, function(value, key) {
          if(value.createdDtm) {
            var dt = new Date();
            var date = new Date(value.createdDtm + (dt.getTimezoneOffset()*60000) + (-5 *  3600000));
            value.createdDtmFormatted = $scope.checkTime((date.getMonth() + 1)) + '/' + $scope.checkTime(date.getDate()) + '/' +  date.getFullYear() + " " +  $scope.checkTime(date.getHours()) + ":" + $scope.checkTime(date.getMinutes()) + ":" + $scope.checkTime(date.getSeconds());
          }
          arr.push(value);
        });
        return arr;
      }
    } else {
      $scope.$apply(function() {
        $('.errorCls').show();
        $('.dataCls').hide();
        $scope.errorMsg = "Invalid URL! CRM Oppurtunity Id is missing.";
      });
      $('.global-Mask').hide();
    }

    $scope.logMouseEvent = function() {
      var target = event.target,
      element = angular.element(target);
      if (target.offsetWidth < target.scrollWidth) {
        element.attr('title', element.text());
      } else {
        element.removeAttr("title");
      }
    }
  };
});


