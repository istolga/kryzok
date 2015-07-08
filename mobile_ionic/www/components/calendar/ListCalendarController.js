(function() {
    'use strict';

    angular
        .module('kryzok.calendar', ['kryzok.calendar.service'])
        .controller('ListCalendarController', ListCalendarController);

    ListCalendarController.$inject = ['$state', '$scope', 'CalendarService'];
    function ListCalendarController($state, $scope, CalendarService) {
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
        $scope.viewItem = function(itemId) {
            console.log("in view item, will go to schedule");
            $state.go('app.schedule', {
                scheduleId: itemId
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
        $scope.search = function () {
        	$state.go('app.search', {
    			refresh: new Date().getTime()
    		});
        };

        vm.activate();
    }
})();