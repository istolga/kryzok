angular.module('kryzok.calendar.service', []).service('CalendarServ', CalendarService);

CalendarService.$inject = ["$http"];

function CalendarService($http) {
    return {
        getSchedules: getSchedules
    };
    function getSchedules() {
        //TODO insert real call
        console.log("in getSchedules");
        var headers = {
            'Access-Control-Allow-Origin' : '*',
            'Access-Control-Allow-Methods' : 'POST, GET, OPTIONS, PUT',
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        };
        return $http({
            method: "GET",
            headers: headers,
            url: 'http://browsenpm.org/package.json'
            //data: {"email":"my@email.com","password":"secret"}
        })
            .then(getSchedulesComplete)
            .catch(getSchedulesFailed);

        function getSchedulesComplete(response) {
            //return response.data.results;
            //TODO real data
            console.log("return data");
            return [{"title": "Soccer1", "fromDate": "2015-07-01-17:00", "toDate":"2015-08-15-19:00", "days":"Mon,Wed"},
                {"title": "Basketball", "fromDate": "2015-06-01-09:00", "toDate":"2015-08-15-10:00", "days":"Tue,Wed"},
                {"title": "Gymnastics", "fromDate": "2015-07-15-15:00", "toDate":"2015-08-15-16:00", "days":"Thu,Fri"}]
        }

        function getSchedulesFailed(error) {
            console.log("error");
            return getSchedulesComplete("any");
            //logger.error('XHR Failed for getSchedules.' + error.data);
        }
    }
}
