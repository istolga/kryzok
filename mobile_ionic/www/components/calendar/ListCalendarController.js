angular.module('kryzok.calendar', ['kryzok.calendar.service']).controller('ListCalendarCtr', ListCalendarController);

ListCalendarController.$inject = ['$scope', 'CalendarServ'];
function ListCalendarController($scope, CalendarService) {
    console.log("in list calendar controller");

    var vm = this;
    $scope.schedules = [];
    $scope.shouldShowDelete = true;
    $scope.listCanSwipe = true;

    vm.init = function () {
        vm.getSchedules();

    };
    vm.getSchedules = function() {
        return CalendarService.getSchedules().then(function(data) {
            console.log("in done function");
            $scope.schedules = data;
            console.log($scope.schedules[0].title);
            return $scope.schedules;
        });
    }
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

    vm.init();
}