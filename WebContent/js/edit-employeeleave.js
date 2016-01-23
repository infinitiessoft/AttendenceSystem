angular
		.module('edit-employeeleave', [ 'formly', 'formlyBootstrap' ])
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
				'edit-employeeleave',
				function($rootScope, $scope, $stateParams, $state, $q,
						formlyVersion, employeeleave, employeeleaveService,
						employeeService, leavesettingService) {
					var id = ($stateParams.id) ? parseInt($stateParams.id) : 0;
					$rootScope.title = (id > 0) ? 'Edit Employeeleave'
							: 'Add Employeeleave';
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

					vm.model = employeeleave.data;
					vm.confirmationModel = {};

					vm.fields = [ {
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
					}, {
						key : 'leavesetting',
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
						        refresh: refreshLeavesettings,
						        refreshDelay: 0,
								required : true,
								label : 'Leavesetting'
							}
						} ]
					}, {
						key : 'usedDays',
						type : 'input',
						templateOptions : {
							label : 'usedDays',
							placeholder : 'used days',
							type : 'number',
							required : true
						}
					} ];
					
					function refreshEmployees(name, field) {
					      var promise;
					      if (!name) {
					    	  if(id != 0){
					    		  promise = $q.when({data: {content: [employeeleave.data.employee]}});
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
					
					function refreshLeavesettings(name, field) {
					      var promise;
					      if (!name) {
					    	  if(id != 0){
					    		  promise = $q.when({data: {content: [employeeleave.data.leavesetting]}});
					    	  }else{
					    		  promise = leavesettingService.list({});
					    	  }
					      } else {
					        var params = {name: name};
					        promise = leavesettingService.list(params);
					      }
					      return promise.then(function(response) {
					        field.templateOptions.options = response.data.content;
					      });
					 }
					
					function onSubmit() {
						console.log("Id : " + id);
						if (id > 0) {
							console.log("Update");
							employeeleaveService.update(id, vm.model).then(
									function(status) {
										$state.go('dashboard.list-employeeleaves');
									});
						} else {
							console.log("Insert");
							console.log(vm.leavesetting);
							employeeleaveService.insert(vm.model).then(
									function(status) {
										$state.go('dashboard.list-employeeleaves');
									});
						}
					}

				});
