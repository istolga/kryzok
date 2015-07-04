(function() {
    'use strict';

    angular
        .module('kryzok.calendar', ['kryzok.calendar.service'])
        .controller('ListCalendarController', ListCalendarController);

    ListCalendarController.$inject = ['$scope', 'CalendarService'];
    function ListCalendarController($scope, CalendarService) {
        console.log("in list calendar controller");

        var vm = this;
        $scope.schedules = [];
        $scope.shouldShowDelete = true;
        $scope.listCanSwipe = true;

        vm.activate = function () {
            CalendarService.getSchedules().then(function(returnedSchedules) {
                console.log("in done function");
                $scope.schedules = returnedSchedules;
            });
        };

        $scope.share = function () {
            console.log("in share");
        };
        $scope.edit = function () {
            console.log("in edit");
        };
        $scope.delete = function () {
            console.log("in delete");
        };
        $scope.add = function () {
            console.log("in add");
        };

        vm.activate();
    }
})();