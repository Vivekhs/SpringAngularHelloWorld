'use strict';
 
angular.module('myApp').factory('EmpService', ['$http', '$q', function($http, $q){
 
    var REST_SERVICE_URI = 'http://localhost:8102/SpringMVCHibernateCRUD/rest/';
    const headers = new Headers({ 'Content-Type': 'application/json' });

    var factory = {
    		fetchAllemps: fetchAllemps,
    		createEmp: createEmp,
        updateUser:updateUser,
        deleteUser:deleteUser
    };
 
    return factory;
 
    function fetchAllemps() {
    	console.log("hello")
        var deferred = $q.defer();
      /*  $http({
            method: 'GET',
            url: REST_SERVICE_URI+"emp",
            headers: {
            	 'Accept': 'application/json'
            }
        })*/
    	   $http.get(REST_SERVICE_URI+"emp")
            .then(
            function (response) {
            	console.log(JSON.stringify(response) + "response");
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while fetching Users');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }
 
    function createEmp(emp) {
        var deferred = $q.defer();
        $http.post(REST_SERVICE_URI+"saveEmployee", emp)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while creating Emp');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }
 
 
    function updateUser(emp, id) {
        var deferred = $q.defer();
        $http.put(REST_SERVICE_URI+"editEmployee/"+id, emp)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while updating User');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }
 
    function deleteUser(id) {
        var deferred = $q.defer();
        $http.delete(REST_SERVICE_URI+"deleteEmployee/"+id)
            .then(
            function (response) {
                deferred.resolve(response.data);
            },
            function(errResponse){
                console.error('Error while deleting User');
                deferred.reject(errResponse);
            }
        );
        return deferred.promise;
    }
 
}]);