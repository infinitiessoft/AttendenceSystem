angular.module('edit-recordtype', []).controller(
		'edit-recordtype',
		function($rootScope, $scope, $routeParams, $location, formlyVersion,
				recordtype, recordtypeService) {
			var id = ($routeParams.id) ? parseInt($routeParams.id) : 0;
			$rootScope.listPage = '#!/list-recordtypes'
			$rootScope.title = (id > 0) ? 'Edit AttendRecordType'
					: 'Add AttendRecordType';
			$rootScope.buttonText = (id > 0) ? 'Update' : 'Add';
			var data = recordtype.data;
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
				if (id > 0) {
					recordtypeService.update(id, vm.model);
				} else {
					recordtypeService.insert(vm.model);
				}
				$location.path('/list-recordtypes');
			}
		});