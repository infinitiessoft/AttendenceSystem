'use strict';
/**
 * @ngdoc overview
 * @name sbAdminApp
 * @description
 * # sbAdminApp
 *
 * Main module of the application.
 */
angular
  .module('sbAdminApp', [
    'oc.lazyLoad',
    'ui.router',
    'ui.bootstrap',
    'angular-loading-bar', 'ngCookies', 'formly', 'formlyBootstrap', 'ui.bootstrap',
	'smart-table', 'auth',  'navigation','list-employees', 'edit-employee','edit-profile', 'list-departments', 'edit-department' ,'list-roles', 'edit-role' ,'list-recordtypes', 'edit-recordtype' ,'list-records','edit-record' ,'list-leavesettings' ,'edit-leavesetting' ,'list-employeeleaves' ,'edit-employeeleave','list-events','list-memberrecords','edit-memberrecord','list-memberevents'
  ])
  .config(['$stateProvider','$urlRouterProvider','$ocLazyLoadProvider','$httpProvider',
			'formlyConfigProvider',function ($stateProvider,$urlRouterProvider,$ocLazyLoadProvider,$httpProvider,formlyConfigProvider) {
    
    $ocLazyLoadProvider.config({
      debug:false,
      events:true,
    });

    $urlRouterProvider.otherwise('/dashboard/home');

    $stateProvider
      .state('dashboard', {
        url:'/dashboard',
        templateUrl: 'views/dashboard/main.html',
        resolve: {
            loadMyDirectives:function($ocLazyLoad){
                return $ocLazyLoad.load(
                {
                    name:'sbAdminApp',
                    files:[
                    'scripts/directives/header/header.js',
                    'scripts/directives/header/header-notification/header-notification.js',
                    'scripts/directives/sidebar/sidebar.js',
                    'scripts/directives/sidebar/sidebar-search/sidebar-search.js'
                    ]
                }),
                $ocLazyLoad.load(
                {
                   name:'toggle-switch',
                   files:["bower_components/angular-toggle-switch/angular-toggle-switch.min.js",
                          "bower_components/angular-toggle-switch/angular-toggle-switch.css"
                      ]
                }),
                $ocLazyLoad.load(
                {
                  name:'ngSanitize',
                  files:['bower_components/angular-sanitize/angular-sanitize.js']
                }),
                $ocLazyLoad.load(
                {
                  name:'ngTouch',
                  files:['bower_components/angular-touch/angular-touch.js']
                })
            }
        }
    })
      .state('dashboard.home',{
        url:'/home',
        templateUrl:'partials/calendar.html'
      })
      .state('dashboard.edit-profile',{
        templateUrl:'partials/edit.html',
        controller: 'edit-profile',
        url:'/edit-profile',
        resolve : {
			employee : function(
					auth,
					employeeService) {

				var id = auth.user.principal.id;
				if(id == 0){
					return {data:{}};
				}
				return employeeService.get(id);
			}
		}
    })
      .state('dashboard.list-memberrecords',{
        templateUrl:'partials/list-memberrecords.html',
        controller: 'list-memberrecords',
        url:'/list-memberrecords'
    })
    .state('dashboard.edit-memberrecord',{
        templateUrl:'partials/edit.html',
        controller: 'edit-memberrecord',
        url:'/edit-memberrecord/:id',
		resolve : {
			record : function(
					memberRecordService,
					$stateParams) {
				var id = $stateParams.id;
				if(id == 0){
					return {data:{}};
				}
				return memberRecordService.get(id);
			}
		}
    })
    .state('dashboard.list-memberevents',{
    	templateUrl:'partials/list-memberevents.html',
		controller : 'list-memberevents',
        url:'/list-memberevents'
    })
    .state('dashboard.list-records',{
        templateUrl:'partials/list-records.html',
        controller: 'list-records',
        url:'/list-records'
    })
    .state('dashboard.edit-record',{
        templateUrl:'partials/edit.html',
        controller: 'edit-record',
        url:'/edit-record/:id',
        resolve : {
			record : function(
					recordService,
					$stateParams) {
				var id = $stateParams.id;
				if(id == 0){
					return {data:{}};
				}
				return recordService.get(id);
			}
		}
    })
    .state('dashboard.list-events',{
        templateUrl:'partials/list-events.html',
        controller: 'list-events',
        url:'/list-events'
    })
    .state('dashboard.list-employees',{
        templateUrl:'partials/list-employees.html',
        controller: 'list-employees',
        url:'/list-employees'
    })
    .state('dashboard.edit-employee',{
        templateUrl:'partials/edit.html',
        controller: 'edit-employee',
        url:'/edit-employee/:id',
        resolve : {
			employee : function(
					employeeService,
					$stateParams) {
				var id = $stateParams.id;
				if(id == 0){
					return {data:{}};
				}
				return employeeService.get(id);
			}
		}
    })
    .state('dashboard.list-departments',{
        templateUrl:'partials/list-departments.html',
        controller: 'list-departments',
        url:'/list-departments'
    })
    .state('dashboard.edit-department',{
        templateUrl:'partials/edit.html',
        controller: 'edit-department',
        url:'/edit-department/:id',
        resolve : {
			department : function(
					departmentService,
					$stateParams) {
				var id = $stateParams.id;
				if(id == 0){
					return {data:{}};
				}
				return departmentService.get(id);
			}
		}
    })
    .state('dashboard.list-recordtypes',{
        templateUrl:'partials/list-recordtypes.html',
        controller: 'list-recordtypes',
        url:'/list-recordtypes'
    })
    .state('dashboard.edit-recordtype',{
        templateUrl:'partials/edit.html',
        controller: 'edit-recordtype',
        url:'/edit-recordtype/:id',
        resolve : {
			recordtype : function(
					recordtypeService,
					$stateParams) {
				var id = $stateParams.id;
				if(id == 0){
					return {data:{}};
				}
				return recordtypeService.get(id);
			}
		}
    })
    .state('dashboard.list-roles',{
        templateUrl:'partials/list-roles.html',
        controller: 'list-roles',
        url:'/list-roles'
    })
    .state('dashboard.edit-role',{
        templateUrl:'partials/edit.html',
        controller: 'edit-role',
        url:'/edit-role/:id',
        resolve : {
			role : function(
					roleService,
					$stateParams) {
				var id = $stateParams.id;
				if(id == 0){
					return {data:{}};
				}
				return roleService.get(id);
			}
		}
    })
     .state('dashboard.list-leavesettings',{
        templateUrl:'partials/list-leavesettings.html',
        controller: 'list-leavesettings',
        url:'/list-leavesettings'
    })
    .state('dashboard.edit-leavesetting',{
        templateUrl:'partials/edit.html',
        controller: 'edit-leavesetting',
        url:'/edit-leavesetting/:id',
        resolve : {
			leavesetting : function(
					leavesettingService,
					$stateParams) {
				var id = $stateParams.id;
				if(id == 0){
					return {data:{}};
				}
				return leavesettingService.get(id);
			}
		}
    })
     .state('dashboard.list-employeeleaves',{
        templateUrl:'partials/list-employeeleaves.html',
        controller: 'list-employeeleaves',
        url:'/list-employeeleaves'
    })
    .state('dashboard.edit-employeeleave',{
        templateUrl:'partials/edit.html',
        controller: 'edit-employeeleave',
        url:'/edit-employeeleave/:id',
        resolve : {
			employeeleave : function(
					employeeleaveService,
					$stateParams) {
				var id = $stateParams.id;
				if(id == 0){
					return {data:{}};
				}
				return employeeleaveService.get(id);
			}
		}
    })
      .state('login',{
        templateUrl:'views/pages/login.html',
        controller: 'navigation',
        url:'/login'
    });
    
    $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
//	$locationProvider.hashPrefix('!');
	/*
	 * Register error provider that shows message on
	 * failed requests or redirects to login page on
	 * unauthenticated requests
	 */
	$httpProvider.interceptors.push(function($q,
			$rootScope) {
		return {
			'responseError' : function(rejection) {
				var data = rejection.data;
				var status = rejection.status;
				var config = rejection.config;
				var method = config.method;
				var url = config.url;
				if (status == 401) {
					$state.go("login");
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
    
  }]).run(
			function($rootScope, $location, $cookieStore, $http,
					 auth, formlyConfig, formlyValidationMessages) {
				
				formlyConfig.extras.errorExistsAndShouldBeVisibleExpression = 'fc.$touched || form.$submitted';

				formlyValidationMessages.addStringMessage('required',
						'This field is required');
				
				auth.init('/home', '/login', 'logout');
				
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