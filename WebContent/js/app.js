angular
		.module(
				'attendance',
				[ 'ngRoute', 'ngCookies',
						'formly', 'formlyBootstrap', 'ui.bootstrap',
						'smart-table', 'auth',  'navigation','list-employees', 'edit-employee', 'list-departments', 'edit-department' ])
		.config(
				[
						'$routeProvider',
						'$locationProvider',
						'$httpProvider',
						function($routeProvider, $locationProvider,
								$httpProvider) {

							$routeProvider.when('/', {
								templateUrl : 'partials/list-employees.html',
								controller : 'list-employees'
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

							$routeProvider.when('/login', {
								templateUrl : 'partials/login.html',
								controller : 'navigation'
							});

							$routeProvider.when('/home', {
								templateUrl : 'partials/list-employees.html',
								controller : 'list-employees'
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

							$routeProvider.otherwise({
								templateUrl : 'partials/list-employees.html',
								controller : 'list-employees'
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
													+ status;
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
							obj.list = function() {
								return $http.get(serviceBase);
							}

							obj.get = function(id) {
								return $http.get(serviceBase  + id);
							}

							obj.insert = function(employee) {
								return $http.post(serviceBase, employee).then(
										function(results) {
											return results;
										});
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
											obj.list = function() {
												return $http.get(serviceBase);
											}

											obj.get = function(id) {
												return $http.get(serviceBase  + id);
											}

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
		.run(
				function($rootScope, $location, $cookieStore, $http,
						formlyConfig, auth) {

					auth.init('/', '/login', 'logout');

					/* Reset error when a new view is loaded */
					$rootScope.$on('$viewContentLoaded', function() {
						delete $rootScope.error;
					});

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
							'datepicker-append-to-body' ];

					var bindings = [ 'datepicker-mode', 'min-date', 'max-date' ];

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
						controller : [ '$scope', function($scope) {
							$scope.datepicker = {};

							$scope.datepicker.opened = false;

							$scope.datepicker.open = function($event) {
								$scope.datepicker.opened = true;
							};
						} ]
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
