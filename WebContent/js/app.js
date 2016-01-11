angular
		.module(
				'attendance',
				[ 'ngRoute', 'ngCookies',
						'formly', 'formlyBootstrap', 'ui.bootstrap',
						'smart-table', 'auth',  'navigation','list-employees', 'edit-employee','edit-profile', 'list-departments', 'edit-department' ,'list-roles', 'edit-role' ,'list-recordtypes', 'edit-recordtype' ,'list-records','edit-record' ,'list-leavesettings' ,'edit-leavesetting' ,'list-employeeleaves' ,'edit-employeeleave','list-events','list-memberrecords','edit-memberrecord','list-memberevents'])
		.config(
				[
						'$routeProvider',
						'$locationProvider',
						'$httpProvider',
						'formlyConfigProvider',
						function($routeProvider, $locationProvider,
								$httpProvider,formlyConfigProvider) {

							$routeProvider.when('/', {
								templateUrl : 'partials/calendar.html',
								controller : 'navigation'
							});

							$routeProvider
									.when(
											'/edit-employee/:id',
											{
												templateUrl : 'partials/edit.html',
												controller : 'edit-employee',
												resolve : {
													employee : function(
															employeeService,
															$route) {
														var id = $route.current.params.id;
														if(id == 0){
															return {data:{}};
														}
														return employeeService.get(id);
													}
												}
											});
							$routeProvider
							.when(
									'/edit-profile/:id',
									{
										templateUrl : 'partials/edit.html',
										controller : 'edit-profile',
										resolve : {
											employee : function(
													employeeService,
													$route) {
												var id = $route.current.params.id;
												if(id == 0){
													return {data:{}};
												}
												return employeeService.get(id);
											}
										}
									});

							$routeProvider.when('/login', {
								templateUrl : 'partials/login.html',
								controller : 'navigation'
							});
							
							$routeProvider.when('/list-employees', {
								templateUrl : 'partials/list-employees.html',
								controller : 'list-employees'
							});
							
							$routeProvider.when('/list-departments', {
								templateUrl : 'partials/list-departments.html',
								controller : 'list-departments'
							});
							
							$routeProvider
							.when(
									'/edit-department/:id',
									{
										templateUrl : 'partials/edit.html',
										controller : 'edit-department',
										resolve : {
											department : function(
													departmentService,
													$route) {
												var id = $route.current.params.id;
												if(id == 0){
													return {data:{}};
												}
												return departmentService.get(id);
											}
										}
									});
							
							$routeProvider.when('/list-roles', {
								templateUrl : 'partials/list-roles.html',
								controller : 'list-roles'
							});
							
							$routeProvider
							.when(
									'/edit-role/:id',
									{
										templateUrl : 'partials/edit.html',
										controller : 'edit-role',
										resolve : {
											role : function(
													roleService,
													$route) {
												var id = $route.current.params.id;
												if(id == 0){
													return {data:{}};
												}
												return roleService.get(id);
											}
										}
									});
							
							$routeProvider.when('/list-recordtypes', {
								templateUrl : 'partials/list-recordtypes.html',
								controller : 'list-recordtypes'
							});
							
							$routeProvider
							.when(
									'/edit-recordtype/:id',
									{
										templateUrl : 'partials/edit.html',
										controller : 'edit-recordtype',
										resolve : {
											recordtype : function(
													recordtypeService,
													$route) {
												var id = $route.current.params.id;
												if(id == 0){
													return {data:{}};
												}
												return recordtypeService.get(id);
											}
										}
									});
							
							$routeProvider.when('/list-leavesettings', {
								templateUrl : 'partials/list-leavesettings.html',
								controller : 'list-leavesettings'
							});
							
							$routeProvider
							.when(
									'/edit-leavesetting/:id',
									{
										templateUrl : 'partials/edit.html',
										controller : 'edit-leavesetting',
										resolve : {
											leavesetting : function(
													leavesettingService,
													$route) {
												var id = $route.current.params.id;
												if(id == 0){
													return {data:{}};
												}
												return leavesettingService.get(id);
											}
										}
									});
							
							$routeProvider.when('/list-employeeleaves', {
								templateUrl : 'partials/list-employeeleaves.html',
								controller : 'list-employeeleaves'
							});
							
							$routeProvider
							.when(
									'/edit-employeeleave/:id',
									{
										templateUrl : 'partials/edit.html',
										controller : 'edit-employeeleave',
										resolve : {
											employeeleave : function(
													employeeleaveService,
													$route) {
												var id = $route.current.params.id;
												if(id == 0){
													return {data:{}};
												}
												return employeeleaveService.get(id);
											}
										}
									});
							
							$routeProvider.when('/list-records', {
								templateUrl : 'partials/list-records.html',
								controller : 'list-records'
							});
							$routeProvider
							.when(
									'/edit-record/:id',
									{
										templateUrl : 'partials/edit.html',
										controller : 'edit-record',
										resolve : {
											record : function(
													recordService,
													$route) {
												var id = $route.current.params.id;
												if(id == 0){
													return {data:{}};
												}
												return recordService.get(id);
											}
										}
									});
							$routeProvider.when('/list-memberrecords', {
								templateUrl : 'partials/list-memberrecords.html',
								controller : 'list-memberrecords'
							});
							$routeProvider
							.when(
									'/edit-memberrecord',
									{
										templateUrl : 'partials/edit.html',
										controller : 'edit-memberrecord',
										resolve : {
											record : function(
													recordService,
													$route) {
													return {data:{}};
											}
										}
									});
							
							$routeProvider.when('/list-events', {
								templateUrl : 'partials/list-events.html',
								controller : 'list-events'
							});
							
							$routeProvider.when('/list-memberevents', {
								templateUrl : 'partials/list-memberevents.html',
								controller : 'list-memberevents'
							});

							$routeProvider.otherwise('/');
							
							formlyConfigProvider.setWrapper({
								name : 'validation',
								types : [ 'input', 'datepicker', 'select','timepicker' ],
								templateUrl : 'error-messages.html'
							});


							$httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
							$locationProvider.hashPrefix('!');

							/*
							 * Register error provider that shows message on
							 * failed requests or redirects to login page on
							 * unauthenticated requests
							 */
							$httpProvider.interceptors.push(function($q,
									$rootScope, $location) {
								return {
									'responseError' : function(rejection) {
										var data = rejection.data;
										var status = rejection.status;
										var config = rejection.config;
										var method = config.method;
										var url = config.url;

										if (status == 401) {
											$location.path("/login");
										} else {
											$rootScope.error = method + " on "
													+ url
													+ " failed with status "
													+ status+", message: "+JSON.stringify(data);
										}

										return $q.reject(rejection);
									}
								};

							});
						} ]

		)
		.factory(
				'employeeService',
				[
						'$http',
						function($http) {
							var serviceBase = 'rest/employee/';
							var obj = {};
							obj.list = function(queries) {
								return $http.get(serviceBase, {params:queries});
							};

							obj.get = function(id) {
								return $http.get(serviceBase  + id);
							};

							obj.insert = function(employee) {
								return $http.post(serviceBase, employee);
							};

							obj.update = function(id, employee) {
								return $http.put(serviceBase  + id,
										employee).then(function(results) {
									return results;
								});
							};
							
							obj.remove = function(id) {
								return $http.delete(serviceBase + id).then(function(status) {
									return status;
								});
							};

							return obj;
						} ]).factory(
								'departmentService',
								[
										'$http',
										function($http) {
											var serviceBase = 'rest/department/';
											var obj = {};
											obj.list = function(queries) {
												return $http.get(serviceBase, {params:queries});
											};

											obj.get = function(id) {
												return $http.get(serviceBase  + id);
											};

											obj.insert = function(department) {
												return $http.post(serviceBase, department).then(
														function(results) {
															return results;
														});
											};

											obj.update = function(id, department) {
												return $http.put(serviceBase  + id,
														department).then(function(results) {
													return results;
												});
											};
											
											obj.remove = function(id) {
												return $http.delete(serviceBase + id).then(function(status) {
													return status;
												});
											};

											return obj;
										} ])
										.factory(
								'employeeRoleService',
								[
										'$http',
										function($http) {
											var serviceBase = 'rest/employee/';
											var obj = {};
											obj.list = function(employeeid) {
												return $http.get(serviceBase+employeeid+"/roles");
											};

											obj.get = function(employeeid,roleid) {
												return $http.get(serviceBase+employeeid+"/roles/"+roleid);
											};

											obj.insert = function(employeeid,roleid) {
												return $http.put(serviceBase+employeeid+"/roles/"+roleid);
											};
											
											obj.remove = function(id) {
												return $http.delete(serviceBase+employeeid+"/roles/"+roleid);
											};

											return obj;
										} ]).factory(
												'roleService',
												[
														'$http',
														function($http) {
															var serviceBase = 'rest/role/';
															var obj = {};
															obj.list = function(queries) {
																return $http.get(serviceBase, {params:queries});
															};

															obj.get = function(id) {
																return $http.get(serviceBase  + id);
															};

															obj.insert = function(role) {
																return $http.post(serviceBase, role).then(
																		function(results) {
																			return results;
																		});
															};

															obj.update = function(id, role) {
																return $http.put(serviceBase  + id,
																		role).then(function(results) {
																	return results;
																});
															};
															
															obj.remove = function(id) {
																return $http.delete(serviceBase + id).then(function(status) {
																	return status;
																});
															};

															return obj;
														} ]).factory(
												'recordtypeService',
												[
														'$http',
														function($http) {
															var serviceBase = 'rest/recordtype/';
															var obj = {};
															obj.list = function(queries) {
																return $http.get(serviceBase, {params:queries});
															};

															obj.get = function(id) {
																return $http.get(serviceBase  + id);
															};

															obj.insert = function(recordtype) {
																return $http.post(serviceBase, recordtype).then(
																		function(results) {
																			return results;
																		});
															};

															obj.update = function(id, recordtype) {
																return $http.put(serviceBase  + id,
																		recordtype).then(function(results) {
																	return results;
																});
															};
															
															obj.remove = function(id) {
																return $http.delete(serviceBase + id).then(function(status) {
																	return status;
																});
															};

															return obj;
														} ]).factory(
																'leavesettingService',
																[
																		'$http',
																		function($http) {
																			var serviceBase = 'rest/leavesetting/';
																			var obj = {};
																			obj.list = function(queries) {
																				return $http.get(serviceBase, {params:queries});
																			};

																			obj.get = function(id) {
																				return $http.get(serviceBase  + id);
																			};
																			
																			obj.insert = function(leavesetting) {
																				return $http.post(serviceBase, leavesetting).then(
																						function(results) {
																							return results;
																						});
																			};

																			obj.update = function(id, leavesetting) {
																				return $http.put(serviceBase  + id,
																						leavesetting).then(function(results) {
																					return results;
																				});
																			};
																			
																			obj.remove = function(id) {
																				return $http.delete(serviceBase + id).then(function(status) {
																					return status;
																				});
																			};

																			return obj;
																		} ]).factory(
																				'employeeleaveService',
																				[
																						'$http',
																						function($http) {
																							var serviceBase = 'rest/employeeleave/';
																							var obj = {};
																							obj.list = function(queries) {
																								return $http.get(serviceBase, {params:queries});
																							};

																							obj.get = function(id) {
																								return $http.get(serviceBase  + id);
																							};
																							
																							obj.insert = function(employeeleave) {
																								return $http.post(serviceBase, employeeleave).then(
																										function(results) {
																											return results;
																										});
																							};

																							obj.update = function(id, employeeleave) {
																								return $http.put(serviceBase  + id,
																										employeeleave).then(function(results) {
																									return results;
																								});
																							};
																							
																							obj.remove = function(id) {
																								return $http.delete(serviceBase + id).then(function(status) {
																									return status;
																								});
																							};

																							return obj;
																						} ])
		.run(
				function($rootScope, $location, $cookieStore, $http,
						formlyConfig, auth, formlyConfig, formlyValidationMessages) {

					formlyConfig.extras.errorExistsAndShouldBeVisibleExpression = 'fc.$touched || form.$submitted';

					formlyValidationMessages.addStringMessage('required',
							'This field is required');
					
					auth.init('/', '/login', 'logout');
					
					/* Reset error when a new view is loaded */
					$rootScope.$on('$viewContentLoaded', function() {
						delete $rootScope.error;
					});
					
					$rootScope.back = function() { 
					    window.history.back();
					  };

					$rootScope.hasRole = function(role) {

						if ($rootScope.user === undefined) {
							return false;
						}

						if ($rootScope.user.roles[role] === undefined) {
							return false;
						}

						return $rootScope.user.roles[role];
					};

					$rootScope.initialized = true;

					var attributes = [ 'date-disabled', 'custom-class',
							'show-weeks', 'starting-day', 'init-date',
							'min-mode', 'max-mode', 'format-day',
							'format-month', 'format-year', 'format-day-header',
							'format-day-title', 'format-month-title',
							'year-range', 'shortcut-propagation',
							'datepicker-popup', 'show-button-bar',
							'current-text', 'clear-text', 'close-text',
							'close-on-date-selection',
							'datepicker-append-to-body' ,'meridians',
						    'readonly-input',
						    'mousewheel',
						    'arrowkeys'];

					var bindings = [ 'datepicker-mode', 'min-date', 'max-date', 'hour-step',
									    'minute-step','show-meridian' ];

					var ngModelAttrs = {};

					angular.forEach(attributes, function(attr) {
						ngModelAttrs[camelize(attr)] = {
							attribute : attr
						};
					});

					angular.forEach(bindings, function(binding) {
						ngModelAttrs[camelize(binding)] = {
							bound : binding
						};
					});

					formlyConfig.setType({
						name : 'datepicker',
						templateUrl : 'template/datepicker.html',
						wrapper : [ 'bootstrapLabel', 'bootstrapHasError' ],
						defaultOptions : {
							ngModelAttrs : ngModelAttrs,
							templateOptions : {
								datepickerOptions : {
									format : 'yyyy-MM-dd',
									initDate : new Date()
								}
							}
						},
						  controller: [
						               '$scope', function ($scope) {
						                 $scope.datepicker = {};

						                 // make sure the initial value is of
											// type DATE!
						                 var currentModelVal = $scope.model[$scope.options.key];
						                 if (typeof (currentModelVal) == 'string'){
						                   $scope.model[$scope.options.key] = new Date(currentModelVal);
						                 }


						                 $scope.datepicker.opened = false;

						                 $scope.datepicker.open = function ($event) {
						                   $scope.datepicker.opened = true;
						                 };
						               }
						             ]
					});
					
					  formlyConfig.setType({
					    name: 'timepicker',
					    template: '<timepicker ng-model="model[options.key]"></timepicker>',
					    wrapper: ['bootstrapLabel', 'bootstrapHasError'],
					    defaultOptions: {
					      ngModelAttrs: ngModelAttrs,
					      templateOptions: {
					        datepickerOptions: {}
					      }
					    },
						  controller: [
						               '$scope', function ($scope) {
						                 $scope.datepicker = {};

						                 // make sure the initial value is of
											// type DATE!
						                 var currentModelVal = $scope.model[$scope.options.key];
						                 if (typeof (currentModelVal) == 'string'){
						                   $scope.model[$scope.options.key] = new Date(currentModelVal);
						                 }


						                 $scope.datepicker.opened = false;

						                 $scope.datepicker.open = function ($event) {
						                   $scope.datepicker.opened = true;
						                 };
						               }
						             ]
					  });

					function camelize(string) {
						string = string.replace(/[\-_\s]+(.)?/g, function(
								match, chr) {
							return chr ? chr.toUpperCase() : '';
						});
						// Ensure 1st char is always lowercase
						return string.replace(/^([A-Z])/, function(match, chr) {
							return chr ? chr.toLowerCase() : '';
						});
					}

				});
