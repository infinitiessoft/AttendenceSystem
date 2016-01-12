angular
		.module('edit-record',
				[ 'formly', 'formlyBootstrap', 'ngMessages', 'ngAnimate' ])
		.constant('formlyExampleApiCheck', apiCheck())
		.config(
				function config(formlyConfigProvider, formlyExampleApiCheck) {
					formlyConfigProvider
							.setType({
								name : 'afterField',
								apiCheck : function() {
									return {
										data : {
											fieldToMatch : formlyExampleApiCheck.string
										}
									}
								},
								apiCheckOptions : {
									prefix : 'afterField type'
								},
								defaultOptions : function matchFieldDefaultOptions(
										options) {
									return {
										extras : {
											validateOnModelChange : true
										},

										validators : {
											fieldMatch : {
												expression : function(
														viewValue, modelValue,
														fieldScope) {
													var value = modelValue;
													var model = options.data.modelToMatch;

													if (!model || !value) {
														return false;
													}

													var other = model[options.data.fieldToMatch];
													var otherStr = other;
													if (other) {
														other = other.valueOf();
													}

													var valueStr = value;
													if (value) {
														value = value.valueOf();
													}

													return value && other
															&& value >= other;
												},
												message : '"Invalid End Date"'
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
				'edit-record',
				function($rootScope, $scope, $routeParams, $location,
						formlyVersion, record, recordtypeService, recordService) {
					var id = ($routeParams.id) ? parseInt($routeParams.id) : 0;
					$rootScope.title = (id > 0) ? 'Edit AttendRecord'
							: 'Add AttendRecord';
					$rootScope.buttonText = (id > 0) ? 'Update' : 'Add';
					$scope.types = [];
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

					vm.model = record.data;

					recordtypeService.list().then(function(status) {
						var deps = status.data.content;
						angular.forEach(deps, function(dep) {
							$scope.types.push({
								name : dep.name,
								value : dep.id
							});
						});
					});

					vm.fields = [ {
						className : 'row',
						fieldGroup : [ {
							className : 'col-xs-3',
							key : 'startDate',
							type : 'datepicker',
							templateOptions : {
								label : 'Start date',
								type : 'text',
								datepickerPopup : 'dd-MMMM-yyyy',
								required : true
							},
							parsers : [ toStartTime ],
							formatters : [ toStartTime ]
						}, {
							className : 'col-xs-3',
							key : 'startDate',
							type : 'timepicker',
							templateOptions : {
								label : 'Start time',
								required : true
							}
						}, {
							className : 'col-xs-3',
							key : 'endDate',
							optionsTypes : [ 'afterField' ],
							type : 'datepicker',
							templateOptions : {
								label : 'End date',
								type : 'text',
								datepickerPopup : 'dd-MMMM-yyyy',
								required : true
							},
							data : {
								fieldToMatch : 'startDate',
								modelToMatch : vm.model
							},
							parsers : [ toEndTime ],
							formatters : [ toEndTime ]
						}, {
							className : 'col-xs-3',
							key : 'endDate',
							optionsTypes : [ 'afterField' ],
							type : 'timepicker',
							templateOptions : {
								label : 'End time',
								required : true
							},
							data : {
								fieldToMatch : 'startDate',
								modelToMatch : vm.model
							}
						} ]
					}, {
						key : 'type',
						fieldGroup : [ {
							key : 'id',
							type : 'select',
							templateOptions : {
								required : true,
								label : 'Type',
								options : $scope.types
							}
						} ],
					}, {
						key : 'reason',
						type : 'input',
						templateOptions : {
							label : 'Reason',
							placeholder : 'Formly is terrific!',
							type : 'text',
						}
					} ];

					function toStartTime(value) {
						if (value) {
							value.setHours(10);
							value.setMinutes(0);
						}
						return value;
					}

					function toEndTime(value) {
						if (value) {
							value.setHours(18);
							value.setMinutes(0);
						}
						return value;
					}

					function onSubmit() {
						if (vm.form.$valid) {
							if (id > 0) {
								recordService.update(id, vm.model).then(
										function(status) {
											$location.path('/list-records');
										});
							} else {
								recordService.insert(vm.model).then(
										function(status) {
											$location.path('/list-records');
										});
							}

						}
					}

				});
