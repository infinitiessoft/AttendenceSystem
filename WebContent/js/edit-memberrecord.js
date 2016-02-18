angular
		.module('edit-memberrecord',
				[ 'formly', 'formlyBootstrap', 'ngMessages', 'ngAnimate' ])
		.constant('formlyExampleApiCheck', apiCheck())
		.config(
				function config(formlyConfigProvider, formlyExampleApiCheck) {

					formlyConfigProvider.setWrapper({
						name : 'textLabelWrapper',
						templateUrl : 'templates/text-label-wrapper.html'
					});

					formlyConfigProvider.setType({
						name : 'text',
						templateUrl : 'templates/text-template.html',
						wrapper : [ 'textLabelWrapper', 'bootstrapHasError' ],
						overwriteOk : true
					});

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
				'edit-memberrecord',
				function($rootScope, $scope, $stateParams, $state,
						formlyVersion, record, recordtypeService,
						memberRecordService, generalService) {
					var id = ($stateParams.id) ? parseInt($stateParams.id) : 0;
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

					vm.fields = [
							{
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
									parsers : [ toStartTime ]
								}, {
									className : 'col-xs-3',
									key : 'startDate',
									type : 'timepicker',
									defaultValue : new Date(),
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
									parsers : [ toEndTime ]
								}, {
									className : 'col-xs-3',
									key : 'endDate',
									optionsTypes : [ 'afterField' ],
									type : 'timepicker',
									defaultValue : new Date(),
									templateOptions : {
										label : 'End time',
										required : true
									},
									data : {
										fieldToMatch : 'startDate',
										modelToMatch : vm.model
									}
								} ]
							},
							{
								noFormControl : true,
								type : 'text',
								key : 'duration',
								templateOptions:{
							          label: 'Duration',
							    },
								controller : /* @ngInject */function($scope,
										generalService) {
									$scope
											.$watchCollection(
													'[model.startDate, model.endDate]',
													function(newValues,
															oldValues, theScope) {
														console.debug('watch:'
																+ newValues);
														if (newValues[0]
																&& newValues[1]
																&& newValues[1] > newValues[0]) {
															generalService
																	.countDuration(
																			{
																				startDate : newValues[0],
																				endDate : newValues[1]
																			})
																	.then(
																			function(
																					response) {
																				$scope.model[$scope.options.key] = response.data.days;
																			},
																			function(
																					response) {
																				$scope.model[$scope.options.key] = 0;
																			});
														} else {
															$scope.model[$scope.options.key] = 0;
														}
													});
								}
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
								type : 'textarea',
								templateOptions : {
									label : 'Reason',
									placeholder : 'Reason',
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
								memberRecordService
										.update(id, vm.model)
										.then(
												function(status) {
													$state
															.go('dashboard.list-memberrecords');
												});
							} else {
								memberRecordService
										.insert(vm.model)
										.then(
												function(status) {
													$state
															.go('dashboard.list-memberrecords');
												});
							}

						}
					}

				});
