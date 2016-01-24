angular.module('edit-role', [])
		.controller(
				'edit-role',
				function($scope, $stateParams, $state, formlyVersion, role,
						roleService) {
					var id = ($stateParams.id) ? parseInt($stateParams.id) : 0;
					$scope.title = (id > 0) ? 'Edit Role' : 'Add Role';
					$scope.buttonText = (id > 0) ? 'Update' : 'Add';
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
								roleService.update(id, vm.model).then(
										function(status) {
											$state.go('dashboard.list-roles');
										});
							} else {
								roleService.insert(vm.model).then(
										function(status) {
											$state.go('dashboard.list-roles');
										});
							}
						}
					}
				});
