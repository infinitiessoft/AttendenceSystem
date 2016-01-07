angular.module('edit-role', []).controller(
		'edit-role',
		function($rootScope, $scope, $routeParams, $location, formlyVersion,
				role, roleService) {
			var id = ($routeParams.id) ? parseInt($routeParams.id) : 0;
			$rootScope.listPage = '#!/list-roles'
			$rootScope.title = (id > 0) ? 'Edit Role' : 'Add Role';
			$rootScope.buttonText = (id > 0) ? 'Update' : 'Add';
			var data = role.data;
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
						roleService.update(id, vm.model);
					} else {
						roleService.insert(vm.model);
					}
					$location.path('/list-roles');
				}
			}
		});
