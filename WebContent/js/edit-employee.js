angular
		.module('edit-employee', [ 'formly', 'formlyBootstrap' ])
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
				})
		.controller(
				'edit-employee',
				function($rootScope, $scope, $stateParams, $state, $q,
						formlyVersion, employee, employeeService,
						departmentService, roleService, employeeRoleService) {
					var id = ($stateParams.id) ? parseInt($stateParams.id) : 0;
					$rootScope.title = (id > 0) ? 'Edit Employee'
							: 'Add Employee';
					$rootScope.buttonText = (id > 0) ? 'Update' : 'Add';
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
					vm.roles = {
						role : {}
					};
					
					if (id > 0) {
						employeeRoleService.list(id).then(function(status) {
							var roles = status.data.content;
							angular.forEach(roles, function(role) {
								vm.roles.role = role;
							});
						});
					}

					vm.fields = [ {
						key : 'name',
						type : 'input',
						templateOptions : {
							label : 'Name',
							placeholder : 'Name',
							type : 'text',
							required : true
						}
					}, {
						key : 'username',
						type : 'input',
						templateOptions : {
							label : 'Username',
							placeholder : 'Username',
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
						key : 'dateOfJoined',
						type : 'datepicker',
						templateOptions : {
							label : 'Date of joined',
							type : 'text',
							datepickerPopup : 'dd-MMMM-yyyy',
							required : true
						}
					}, {
						key : 'email',
						type : 'input',
						templateOptions : {
							label : 'Email',
							placeholder : 'E-mail',
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
					}, {
						key : 'department',
						fieldGroup : [ {
							key : 'id',
							type: 'ui-select-single-search',
							templateOptions : {
								optionsAttr: 'bs-options',
								ngOptions: 'option[to.valueProp] as option in to.options | filter: $select.search',
								valueProp: 'id',
						        labelProp: 'name',
						        placeholder: 'Search',
						        options: [],
						        refresh: refreshDepartments,
						        refreshDelay: 0,
								required : true,
								label : 'Department'
							}
						} ],
					}
					, {
						key : 'employee',
						fieldGroup : [ {
							key : 'id',
							type: 'ui-select-single-search',
							templateOptions : {
								optionsAttr: 'bs-options',
								ngOptions: 'option[to.valueProp] as option in to.options | filter: $select.search',
								valueProp: 'id',
						        labelProp: 'name',
						        placeholder: 'Search',
						        options: [],
						        refresh: refreshEmployees,
						        refreshDelay: 0,
								required : true,
								label : 'Response To'
							}
						} ],
					}
					, {
						key : 'role',
						model : vm.roles,
						fieldGroup : [ {
							key : 'id',
							type: 'ui-select-single-search',
							templateOptions : {
								optionsAttr: 'bs-options',
								ngOptions: 'option[to.valueProp] as option in to.options | filter: $select.search',
								valueProp: 'id',
						        labelProp: 'name',
						        placeholder: 'Search',
						        options: [],
						        refresh: refreshRoles,
						        refreshDelay: 0,
								required : true,
								label : 'Role'
							}
						} ]
					} ];
					
					function refreshDepartments(name, field) {
					      var promise;
					      if (!name) {
					    	  if(id != 0){
					    		  promise = $q.when({data: {content: [employee.data.department]}});
					    	  }else{
					    		  promise = departmentService.list({});
					    	  }
					      } else {
					        var params = {name: name};
					        promise = departmentService.list(params);
					      }
					      return promise.then(function(response) {
					        field.templateOptions.options = response.data.content;
					      });
					 }
					
					function refreshEmployees(name, field) {
					      var promise;
					      if (!name) {
					    	  if(id != 0){
					    		  promise = $q.when({data: {content: [employee.data.employee]}});
					    	  }else{
					    		  promise = employeeService.list({});
					    	  }
					      } else {
					        var params = {name: name};
					        promise = employeeService.list(params);
					      }
					      return promise.then(function(response) {
					        field.templateOptions.options = response.data.content;
					      });
					 }
					
					function refreshRoles(name, field) {
					      var promise;
					      if (!name) {
					    	  if(id != 0){
					    		  promise = $q.when({data: {content: [vm.roles.role]}});
					    	  }else{
					    		  promise = roleService.list({});
					    	  }
					      } else {
					        var params = {name: name};
					        promise = roleService.list(params);
					      }
					      return promise.then(function(response) {
					        field.templateOptions.options = response.data.content;
					      });
					 }
					
					function onSubmit() {
						if (vm.form.$valid) {
							if (id > 0) {
								employeeService.update(id, vm.model).then(
										function(status) {
											$state.go('dashboard.list-employees');
										});
							} else {
								employeeService
										.insert(vm.model)
										.success(
												function(status) {
													var employeeid = status.id;
													var roleid = vm.roles.role.id;
													employeeRoleService
															.insert(employeeid,
																	roleid)
															.then(
																	function(
																			status) {
																		$state.go('dashboard.list-employees');
																	});
												});
							}
						}
					}

				});
