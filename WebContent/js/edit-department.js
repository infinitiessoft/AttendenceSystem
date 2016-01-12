angular.module('edit-department', []).controller(
		'edit-department',
		function($rootScope, $scope, $routeParams, $location, formlyVersion,
				department, departmentService) {
			var id = ($routeParams.id) ? parseInt($routeParams.id) : 0;
			$rootScope.listPage = '#!/list-departments'
			$rootScope.title = (id > 0) ? 'Edit Department' : 'Add Department';
			$rootScope.buttonText = (id > 0) ? 'Update' : 'Add';
			var data = department.data;
			var vm = this;
			$scope.vm = vm;
			vm.onSubmit = onSubmit;
			vm.author = {
				name : 'pohsun',
				url : ''
			};
			vm.env = {
				angularVersion : angular.version.full,
				formlyVersion : formlyVersion
			};

			vm.model = data;
			vm.options = {
				formState : {
					awesomeIsForced : false
				}
			};

			vm.fields = [ {
				key : 'name',
				type : 'input',
				templateOptions : {
					label : 'Name',
					placeholder : 'name',
					type : 'text',
					required : true
				}
			} ];
			function onSubmit() {
				if (vm.form.$valid) {
					if (id > 0) {
						departmentService.update(id, vm.model).then(
								function(status) {
									$location.path('/list-departments');
								});
					} else {
						departmentService.insert(vm.model).then(
								function(status) {
									$location.path('/list-departments');
								});
					}
				}
			}
		});
