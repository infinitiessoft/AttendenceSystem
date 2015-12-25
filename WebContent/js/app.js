angular
		.module(
				'exampleApp',
				[ 'ngRoute', 'ngCookies', 'exampleApp.services', 'formly',
						'formlyBootstrap', 'ui.bootstrap' ])
		.config(
				[
						'$routeProvider',
						'$locationProvider',
						'$httpProvider',
						function($routeProvider, $locationProvider,
								$httpProvider) {

							$routeProvider.when('/create', {
								templateUrl : 'partials/create.html',
								controller : CreateController
							});

							$routeProvider.when('/edit/:id', {
								templateUrl : 'partials/edit.html',
								controller : EditController
							});

							$routeProvider.when('/login', {
								templateUrl : 'partials/login.html',
								controller : LoginController
							});

							$routeProvider.otherwise({
								templateUrl : 'partials/index.html',
								controller : IndexController
							});

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

							/*
							 * Registers auth token interceptor, auth token is
							 * either passed by header or by query parameter as
							 * soon as there is an authenticated user
							 */
							$httpProvider.interceptors
									.push(function($q, $rootScope, $location) {
										return {
											'request' : function(config) {
												var isRestCall = config.url
														.indexOf('rest') == 0;
												if (isRestCall
														&& angular
																.isDefined($rootScope.authToken)) {
													var authToken = $rootScope.authToken;
													if (exampleAppConfig.useAuthTokenHeader) {
														config.headers['X-Auth-Token'] = authToken;
													} else {
														config.url = config.url
																+ "?token="
																+ authToken;
													}
												}
												return config
														|| $q.when(config);
											}
										};
									});

						} ]

		)
		.run(
				function($rootScope, $location, $cookieStore, UserService, formlyConfig) {

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

					$rootScope.logout = function() {
						delete $rootScope.user;
						delete $rootScope.authToken;
						$cookieStore.remove('authToken');
						$location.path("/login");
					};

					/* Try getting valid user from cookie or go to login page */
					var originalPath = $location.path();
					$location.path("/login");
					var authToken = $cookieStore.get('authToken');
					if (authToken !== undefined) {
						$rootScope.authToken = authToken;
						UserService.get(function(user) {
							$rootScope.user = user;
							$location.path(originalPath);
						});
					}

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

					console.log(ngModelAttrs);

					formlyConfig.setType({
						name : 'datepicker',
						templateUrl : 'template/datepicker.html',
						wrapper : [ 'bootstrapLabel', 'bootstrapHasError' ],
						defaultOptions : {
							ngModelAttrs : ngModelAttrs,
							templateOptions : {
								datepickerOptions : {
									format : 'MM.dd.yyyy',
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

function IndexController($scope, NewsService) {

	$scope.newsEntries = NewsService.query();

	$scope.deleteEntry = function(newsEntry) {
		newsEntry.$remove(function() {
			$scope.newsEntries = NewsService.query();
		});
	};
};

function EditController($scope, $routeParams, $location, NewsService,
		formlyVersion) {
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

	vm.model = {
		awesome : true
	};
	vm.options = {
		formState : {
			awesomeIsForced : false
		}
	};

	vm.fields = [
			{
				key : 'date1',
				type : 'datepicker',
				templateOptions : {
					label : 'Date 1',
					type : 'text',
					datepickerPopup : 'dd-MMMM-yyyy'
				}
			},
			{
				key : 'text',
				type : 'input',
				templateOptions : {
					label : 'Text',
					placeholder : 'Formly is terrific!',
					type: 'email',
					required: true
				}
			},
			{
				key : 'nested.story',
				type : 'textarea',
				templateOptions : {
					label : 'Some sweet story',
					placeholder : 'It allows you to build and maintain your forms with the ease of JavaScript :-)',
					description : ''
				},
				expressionProperties : {
					'templateOptions.focus' : 'formState.awesomeIsForced',
					'templateOptions.description' : function(viewValue,
							modelValue, scope) {
						if (scope.formState.awesomeIsForced) {
							return 'And look! This field magically got focus!';
						}
					}
				}
			},
			{
				key : 'awesome',
				type : 'checkbox',
				templateOptions : {
					label : ''
				},
				expressionProperties : {
					'templateOptions.disabled' : 'formState.awesomeIsForced',
					'templateOptions.label' : function(viewValue, modelValue,
							scope) {
						if (scope.formState.awesomeIsForced) {
							return 'Too bad, formly is really awesome...';
						} else {
							return 'Is formly totally awesome? (uncheck this and see what happens)';
						}
					}
				}
			},
			{
				key : 'whyNot',
				type : 'textarea',
				expressionProperties : {
					'templateOptions.placeholder' : function(viewValue,
							modelValue, scope) {
						if (scope.formState.awesomeIsForced) {
							return 'Too bad... It really is awesome! Wasn\'t that cool?';
						} else {
							return 'Type in here... I dare you';
						}
					},
					'templateOptions.disabled' : 'formState.awesomeIsForced'
				},
				hideExpression : 'model.awesome',
				templateOptions : {
					label : 'Why Not?',
					placeholder : 'Type in here... I dare you'
				},
				watcher : {
					listener : function(field, newValue, oldValue, formScope,
							stopWatching) {
						if (newValue) {
							stopWatching();
							formScope.model.awesome = true;
							formScope.model.whyNot = undefined;
							field.hideExpression = null;
							formScope.options.formState.awesomeIsForced = true;
						}
					}
				}
			} ];
	function onSubmit() {
		alert(JSON.stringify(vm.model), null, 2);
	}

	$scope.newsEntry = NewsService.get({
		id : $routeParams.id
	});

	$scope.save = function() {
		$scope.newsEntry.$save(function() {
			$location.path('/');
		});
	};
};

function CreateController($scope, $location, NewsService) {

	$scope.newsEntry = new NewsService();

	$scope.save = function() {
		$scope.newsEntry.$save(function() {
			$location.path('/');
		});
	};
};

function LoginController($scope, $rootScope, $location, $cookieStore,
		UserService, NewsService) {

	$scope.rememberMe = false;

	$scope.login = function() {
		UserService.authenticate($.param({
			username : $scope.username,
			password : $scope.password
		}), function(authenticationResult) {
			var authToken = authenticationResult.token;
			var id = authenticationResult.userId;
			$rootScope.authToken = authToken;
			if ($scope.rememberMe) {
				$cookieStore.put('authToken', authToken);
			}
			NewsService.get({
				id : id
			}, function(user) {
				$rootScope.user = user;
				$location.path('/');
			});
		});
	};
};

var services = angular.module('exampleApp.services', [ 'ngResource' ]);

services.factory('UserService', function($resource) {

	return $resource('rest/auth/:action', {}, {
		authenticate : {
			method : 'POST',
			params : {
				'action' : 'authenticate'
			},
			headers : {
				'Content-Type' : 'application/x-www-form-urlencoded'
			}
		},
	});
});

services.factory('NewsService', function($resource) {

	return $resource('rest/employee/:id', {
		id : '@id'
	});
});