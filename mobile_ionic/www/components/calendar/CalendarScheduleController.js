(function() {
  'use strict';

  angular
    .module('kryzok.calendar.schedule', ['kryzok.calendar.service'])
    .controller('CalendarScheduleController', CalendarScheduleController);

  CalendarScheduleController.$inject = ['$state', '$scope', '$stateParams', '$ionicHistory', 'CalendarService'];
  function CalendarScheduleController($state, $scope, $stateParams, $ionicHistory, CalendarService) {
    console.log("in calendar schedule controller");

    var vm = this;
    $scope.schedule = [];

    vm.activate = function () {
      console.log("in activate of CalendarScheduleController");
      CalendarService.getActivities().then(function(returnedActivities) {
        console.log("in done function for activities");
        var scheduleId = $stateParams.scheduleId;
        if (scheduleId) {
          console.log("show schedule with id: " + scheduleId);
          $scope.schedule = returnedActivities[scheduleId];
          $scope.fromTime = $stateParams.fromTime;
          $scope.toTime = $stateParams.toTime;

          console.log($scope.schedule);
        }
      });
    };
    $scope.myGoBack = function () {
      $ionicHistory.goBack();
    };

    vm.activate();
  }
})();