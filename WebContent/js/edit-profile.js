angular
		.module('edit-profile', [ 'formly', 'formlyBootstrap' ])
		.constant('formlyExampleApiCheck', apiCheck())
		.config(
				function config(formlyConfigProvider, formlyExampleApiCheck) {
					// set templates here
					formlyConfigProvider
							.setType({
								name : 'matchField',
								apiCheck : function() {
									return {
										data : {
											fieldToMatch : formlyExampleApiCheck.string
										}
									}
								},
								apiCheckOptions : {
									prefix : 'matchField type'
								},
								defaultOptions : function matchFieldDefaultOptions(
										options) {
									return {
										extras : {
											validateOnModelChange : true
										},
										expressionProperties : {
											'templateOptions.disabled' : function(
													viewValue, modelValue,
													scope) {
												var matchField = find(
														scope.fields,
														'key',
														options.data.fieldToMatch);
												if (!matchField) {
													throw new Error(
															'Could not find a field for the key '
																	+ options.data.fieldToMatch);
												}
												var model = options.data.modelToMatch
														|| scope.model;
												var originalValue = model[options.data.fieldToMatch];
												var invalidOriginal = matchField.formControl
														&& matchField.formControl.$invalid;
												return !originalValue
														|| invalidOriginal;
											}
										},
										validators : {
											fieldMatch : {
												expression : function(
														viewValue, modelValue,
														fieldScope) {
													var value = modelValue
															|| viewValue;
													var model = options.data.modelToMatch
															|| fieldScope.model;
													return value === model[options.data.fieldToMatch];
												},
												message : options.data.matchFieldMessage
														|| '"Must match"'
											}
										}
									};

									function find(array, prop, value) {
										var foundItem;
										array.some(function(item) {
											if (item[prop] === value) {
												foundItem = item;
											}
											return !!foundItem;
										});
										return foundItem;
									}
								}
							});
				}).controller(
				'edit-profile',
				function($rootScope, $scope, $routeParams, $location,
						formlyVersion, employee, employeeService,
						departmentService, roleService, employeeRoleService) {
					var id = ($routeParams.id) ? parseInt($routeParams.id) : 0;
					$rootScope.title = 'Profile'
					$rootScope.buttonText = 'Update';
					$scope.departments = [];
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

					vm.model = employee.data;
					vm.confirmationModel = {};

					departmentService.list().then(function(status) {
						var deps = status.data.content;
						angular.forEach(deps, function(dep) {
							$scope.departments.push({
								name : dep.name,
								value : dep.id
							});
						});
					});

					vm.fields = [ {
						key : 'name',
						type : 'input',
						templateOptions : {
							label : 'Name',
							placeholder : 'name',
							type : 'text',
							required : true
						}
					}, {
						key : 'username',
						type : 'input',
						templateOptions : {
							label : 'Username',
							placeholder : 'Formly is terrific!',
							type : 'text',
							required : true
						}
					}, {
						key : 'password',
						type : 'input',
						templateOptions : {
							type : 'password',
							label : 'Password',
							placeholder : 'Must be at least 3 characters',
							required : true,
							minlength : 3
						}
					}, {
						key : 'confirmPassword',
						type : 'input',
						optionsTypes : [ 'matchField' ],
						model : vm.confirmationModel,
						templateOptions : {
							type : 'password',
							label : 'Confirm Password',
							placeholder : 'Please re-enter your password',
							required : true
						},
						data : {
							fieldToMatch : 'password',
							modelToMatch : vm.model
						}
					}, {
						key : 'email',
						type : 'input',
						templateOptions : {
							label : 'Email',
							placeholder : 'Formly is terrific!',
							type : 'email',
							required : true
						}
					}, {
						key : 'gender',
						type : 'radio',
						templateOptions : {
							label : 'Gender',
							'default' : 'male',
							required : true,
							options : [ {
								name : 'Female',
								value : 'female'
							}, {
								name : 'Male',
								value : 'male'
							} ]
						}
					} ];
					function onSubmit() {
						if (vm.form.$valid) {
							employeeService.update(id, vm.model);
							$location.path('/list-employees');
						}
					}

				});
