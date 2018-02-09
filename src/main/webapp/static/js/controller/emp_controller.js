'use strict';
 
angular.module('myApp').controller('EmpController', ['$scope', 'EmpService', function($scope, EmpService) {
    var self = this;
    self.emp={id:null,name:'',email:'', address:'', telephone:''};
    self.emps=[];
 
    self.submit = submit;
    self.edit = edit;
    self.remove = remove;
    self.reset = reset;
 
 
    fetchAllemps();
 
    function fetchAllemps(){
        EmpService.fetchAllemps()
            .then(
            function(d) {
                self.emps = d;
            },
            function(errResponse){
                console.error('Error while fetching emps');
            }
        );
    }
 
    function createUser(emp){
    	console.log(JSON.stringify(emp));
        EmpService.createEmp(emp)
            .then(
            fetchAllemps,
            function(errResponse){
                console.error('Error while creating emp');
            }
        );
    }
 
    function updateUser(emp, id){
        EmpService.updateUser(emp, id)
            .then(
            fetchAllemps,
            function(errResponse){
                console.error('Error while updating emp');
            }
        );
    }
 
    function deleteUser(id){
        EmpService.deleteUser(id)
            .then(
            fetchAllemps,
            function(errResponse){
                console.error('Error while deleting emp');
            }
        );
    }
 
    function submit() {
        if(self.emp.id===null){
            console.log('Saving New emp', self.emp);
            createUser(self.emp);
        }else{
            updateUser(self.emp, self.emp.id);
            console.log('emp updated with id ', self.emp.id);
        }
        reset();
    }
 
    function edit(id){
        console.log('id to be edited', id);
        for(var i = 0; i < self.emps.length; i++){
            if(self.emps[i].id === id) {
                self.emp = angular.copy(self.emps[i]);
                break;
            }
        }
    }
 
    function remove(id){
        console.log('id to be deleted', id);
        if(self.emp.id === id) {//clean form if the emp to be deleted is shown there.
            reset();
        }
        deleteUser(id);
    }
 
 
    function reset(){
        self.emp={id:null,name:'',email:'', address:'', telephone:''};
        $scope.myForm.$setPristine(); //reset Form
    }
 
}]);