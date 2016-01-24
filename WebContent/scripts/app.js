'use strict';
/**
 * @ngdoc overview
 * @name sbAdminApp
 * @description # sbAdminApp
 * 
 * Main module of the application.
 */
angular
.module('sbAdminApp', [
                       'oc.lazyLoad',
                       'ui.router',
                       'ui.bootstrap',
                       'angular-loading-bar',  'formly', 'formlyBootstrap', 'ui.select', 'ngSanitize',
                       'smart-table', 'auth'
                       ])
                       .config(['$stateProvider','$urlRouterProvider','$ocLazyLoadProvider','$httpProvider',
                                'formlyConfigProvider', function ($stateProvider,$urlRouterProvider,$ocLazyLoadProvider,$httpProvider, formlyConfigProvider) {

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
                    							          'scripts/directives/sidebar/sidebar.js'
                    							          ]
                    						   }),
                    						   $ocLazyLoad.load(
                    								   {
                    									   name:'toggle-switch',
                    									   files:["lib/angular-toggle-switch.min.js",
                    									          "css/angular-toggle-switch.css"
                    									          ]
                    								   }),
                    								   $ocLazyLoad.load(
                    										   {
                    											   name:'ngCookies',
                    											   files:["lib/angular-cookies.min.js"
                    											          ]
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
                    			   loadMyDirectives:function($ocLazyLoad){
                    				   return $ocLazyLoad.load(
                    						   {
                    							   name:'edit-profile',
                    							   files:[
                    							          'js/edit-profile.js'
                    							          ]
                    						   })
                    			   },
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
                    		   url:'/list-memberrecords',
                    		   resolve: {
                    			   loadMyDirectives:function($ocLazyLoad){
                    				   return $ocLazyLoad.load(
                    						   {
                    							   name:'list-memberrecords',
                    							   files:[
                    							          'js/list-memberrecords.js'
                    							          ]
                    						   })
                    			   }
                    		   }
                    	   })
                    	   .state('dashboard.edit-memberrecord',{
                    		   templateUrl:'partials/edit.html',
                    		   controller: 'edit-memberrecord',
                    		   url:'/edit-memberrecord/:id',
                    		   resolve : {
                    			   loadMyDirectives:function($ocLazyLoad){
                    				   return $ocLazyLoad.load(
                    						   {
                    							   name:'edit-memberrecord',
                    							   files:[
                    							          'js/edit-memberrecord.js'
                    							          ]
                    						   })
                    			   },
                    			   record : function(
                    					   memberRecordService,
                    					   $stateParams) {
                    				   var id = $stateParams.id || 0;
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
                    		   url:'/list-memberevents',
                    		   resolve: {
                    			   loadMyDirectives:function($ocLazyLoad){
                    				   return $ocLazyLoad.load(
                    						   {
                    							   name:'list-memberevents',
                    							   files:[
                    							          'js/list-memberevents.js'
                    							          ]
                    						   })
                    			   }
                    		   }
                    	   })
                    	   .state('dashboard.list-records',{
                    		   templateUrl:'partials/list-records.html',
                    		   controller: 'list-records',
                    		   url:'/list-records',
                    		   resolve: {
                    			   loadMyDirectives:function($ocLazyLoad){
                    				   return $ocLazyLoad.load(
                    						   {
                    							   name:'list-records',
                    							   files:[
                    							          'js/list-records.js'
                    							          ]
                    						   })
                    			   }
                    		   }
                    	   })
                    	   .state('dashboard.edit-record',{
                    		   templateUrl:'partials/edit.html',
                    		   controller: 'edit-record',
                    		   url:'/edit-record/:id',
                    		   resolve : {
                    			   loadMyDirectives:function($ocLazyLoad){
                    				   return $ocLazyLoad.load(
                    						   {
                    							   name:'edit-record',
                    							   files:[
                    							          'js/edit-record.js'
                    							          ]
                    						   })
                    			   },
                    			   record : function(
                    					   recordService,
                    					   $stateParams) {
                    				   var id = $stateParams.id || 0;
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
                    		   url:'/list-events',
                    		   resolve: {
                    			   loadMyDirectives:function($ocLazyLoad){
                    				   return $ocLazyLoad.load(
                    						   {
                    							   name:'list-events',
                    							   files:[
                    							          'js/list-events.js'
                    							          ]
                    						   })
                    			   }
                    		   }
                    	   })
                    	   .state('dashboard.list-employees',{
                    		   templateUrl:'partials/list-employees.html',
                    		   controller: 'list-employees',
                    		   url:'/list-employees',
                    		   resolve: {
                    			   loadMyDirectives:function($ocLazyLoad){
                    				   return $ocLazyLoad.load(
                    						   {
                    							   name:'list-employees',
                    							   files:[
                    							          'js/list-employees.js'
                    							          ]
                    						   })
                    			   }
                    		   }
                    	   })
                    	   .state('dashboard.edit-employee',{
                    		   templateUrl:'partials/edit.html',
                    		   controller: 'edit-employee',
                    		   url:'/edit-employee/:id',
                    		   resolve : {
                    			   loadMyDirectives:function($ocLazyLoad){
                    				   return $ocLazyLoad.load(
                    						   {
                    							   name:'edit-employee',
                    							   files:[
                    							          'js/edit-employee.js'
                    							          ]
                    						   })
                    			   },
                    			   employee : function(
                    					   employeeService,
                    					   $stateParams) {
                    				   var id = $stateParams.id || 0;
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
                    		   url:'/list-departments',
                    		   resolve: {
                    			   loadMyDirectives:function($ocLazyLoad){
                    				   return $ocLazyLoad.load(
                    						   {
                    							   name:'list-departments',
                    							   files:[
                    							          'js/list-departments.js'
                    							          ]
                    						   })
                    			   }
                    		   }
                    	   })
                    	   .state('dashboard.edit-department',{
                    		   templateUrl:'partials/edit.html',
                    		   controller: 'edit-department',
                    		   url:'/edit-department/:id',
                    		   resolve : {
                    			   loadMyDirectives:function($ocLazyLoad){
                    				   return $ocLazyLoad.load(
                    						   {
                    							   name:'edit-department',
                    							   files:[
                    							          'js/edit-department.js'
                    							          ]
                    						   })
                    			   },
                    			   department : function(
                    					   departmentService,
                    					   $stateParams) {
                    				   var id = $stateParams.id || 0;
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
                    		   url:'/list-recordtypes',
                    		   resolve: {
                    			   loadMyDirectives:function($ocLazyLoad){
                    				   return $ocLazyLoad.load(
                    						   {
                    							   name:'list-recordtypes',
                    							   files:[
                    							          'js/list-recordtypes.js'
                    							          ]
                    						   })
                    			   }
                    		   }
                    	   })
                    	   .state('dashboard.edit-recordtype',{
                    		   templateUrl:'partials/edit.html',
                    		   controller: 'edit-recordtype',
                    		   url:'/edit-recordtype/:id',
                    		   resolve : {
                    			   loadMyDirectives:function($ocLazyLoad){
                    				   return $ocLazyLoad.load(
                    						   {
                    							   name:'edit-recordtype',
                    							   files:[
                    							          'js/edit-recordtype.js'
                    							          ]
                    						   })
                    			   },
                    			   recordtype : function(
                    					   recordtypeService,
                    					   $stateParams) {
                    				   var id = $stateParams.id || 0;
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
                    		   url:'/list-roles',
                    		   resolve: {
                    			   loadMyDirectives:function($ocLazyLoad){
                    				   return $ocLazyLoad.load(
                    						   {
                    							   name:'list-roles',
                    							   files:[
                    							          'js/list-roles.js'
                    							          ]
                    						   })
                    			   }
                    		   }
                    	   })
                    	   .state('dashboard.edit-role',{
                    		   templateUrl:'partials/edit.html',
                    		   controller: 'edit-role',
                    		   url:'/edit-role/:id',
                    		   resolve : {
                    			   loadMyDirectives:function($ocLazyLoad){
                    				   return $ocLazyLoad.load(
                    						   {
                    							   name:'edit-role',
                    							   files:[
                    							          'js/edit-role.js'
                    							          ]
                    						   })
                    			   },
                    			   role : function(
                    					   roleService,
                    					   $stateParams) {
                    				   var id = $stateParams.id || 0;
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
                    		   url:'/list-leavesettings',
                    		   resolve: {
                    			   loadMyDirectives:function($ocLazyLoad){
                    				   return $ocLazyLoad.load(
                    						   {
                    							   name:'list-leavesettings',
                    							   files:[
                    							          'js/list-leavesettings.js'
                    							          ]
                    						   })
                    			   }
                    		   }
                    	   })
                    	   .state('dashboard.edit-leavesetting',{
                    		   templateUrl:'partials/edit.html',
                    		   controller: 'edit-leavesetting',
                    		   url:'/edit-leavesetting/:id',
                    		   resolve : {
                    			   loadMyDirectives:function($ocLazyLoad){
                    				   return $ocLazyLoad.load(
                    						   {
                    							   name:'edit-leavesetting',
                    							   files:[
                    							          'js/edit-leavesetting.js'
                    							          ]
                    						   })
                    			   },
                    			   leavesetting : function(
                    					   leavesettingService,
                    					   $stateParams) {
                    				   var id = $stateParams.id || 0;
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
                    		   url:'/list-employeeleaves',
                    		   resolve: {
                    			   loadMyDirectives:function($ocLazyLoad){
                    				   return $ocLazyLoad.load(
                    						   {
                    							   name:'list-employeeleaves',
                    							   files:[
                    							          'js/list-employeeleaves.js'
                    							          ]
                    						   })
                    			   }
                    		   }
                    	   })
                    	   .state('dashboard.edit-employeeleave',{
                    		   templateUrl:'partials/edit.html',
                    		   controller: 'edit-employeeleave',
                    		   url:'/edit-employeeleave/:id',
                    		   resolve : {
                    			   loadMyDirectives:function($ocLazyLoad){
                    				   return $ocLazyLoad.load(
                    						   {
                    							   name:'edit-employeeleave',
                    							   files:[
                    							          'js/edit-employeeleave.js'
                    							          ]
                    						   })
                    			   },
                    			   employeeleave : function(
                    					   employeeleaveService,
                    					   $stateParams) {
                    				   var id = $stateParams.id || 0;
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
                    		   url:'/login',
                    		   resolve: {
                    			   loadMyDirectives:function($ocLazyLoad){
                    				   return $ocLazyLoad.load(
                    						   {
                    							   name:'navigation',
                    							   files:[
                    							          'js/navigation.js'
                    							          ]
                    						   })
                    			   }
                    		   }
                    	   });

                    	   $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
                    	   /*
							 * Register error provider that shows message on
							 * failed requests or redirects to login page on
							 * unauthenticated requests
							 */
                    	   $httpProvider.interceptors.push('authHttpResponseInterceptor');

                       } ])
                       .factory('authHttpResponseInterceptor',['$q','$location',function($q, $location) {
                    	   return {
                    		   response : function(response) {
                    			   if (response.status === 401) {
                    				   console.log("Response 401");
                    			   }
                    			   return response || $q.when(response);
                    		   },
                    		   responseError : function(rejection) {
                    			   if (rejection.status === 401) {
                    				   console.log("Response Error 401",
                    						   rejection);
                    				   $location.path('/login').search(
                    						   'returnTo', $location.path());
                    			   }
                    			   return $q.reject(rejection);
                    		   }
                    	   }
                       } ])
                       .run(
                    		   function($rootScope, $http,
                    				   auth, formlyConfig, formlyValidationMessages) {

                    			   formlyConfig.extras.removeChromeAutoComplete = true;

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
                    				   name: 'ui-select-single-search',
                    				   extends: 'select',
                    				   templateUrl: 'templates/ui-select-single-async-search.html'
                    			   });

                    			   formlyConfig.setType({
                    				   name : 'datepicker',
                    				   templateUrl : 'templates/datepicker.html',
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

                    				                	// make sure the initial
                    				                	// value is of
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

                    				                	// make sure the initial
                    				                	// value is of
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

                    		   })
                    		   .factory(
                    				   'employeeleaveService',
                    				   [
                    				    '$http',
                    				    function($http) {
                    				    	var serviceBase = 'rest/v1.0/admin/employeeleaves/';
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
                    				    .factory(
                    				    		'employeeService',
                    				    		[
                    				    		 '$http',
                    				    		 function($http) {
                    				    			 var serviceBase = 'rest/v1.0/admin/employees/';
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
                    				    		 } ])
                    				    		 .factory(
                    				    				 'leavesettingService',
                    				    				 [
                    				    				  '$http',
                    				    				  function($http) {
                    				    					  var serviceBase = 'rest/v1.0/admin/leavesettings/';
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
                    				    				  } ])
                    				    				  .factory(
                    				    						  'memberEventService',
                    				    						  [
                    				    						   'auth',
                    				    						   '$http',
                    				    						   function(auth, $http) {
                    				    							   var serviceBase = 'rest/v1.0/employees/'
                    				    								   + auth.user.principal.id + '/events/';
                    				    							   var obj = {};
                    				    							   obj.list = function(queries) {
                    				    								   return $http.get(serviceBase, {
                    				    									   params : queries
                    				    								   });
                    				    							   };

                    				    							   obj.get = function(id) {
                    				    								   return $http.get(serviceBase + id);
                    				    							   };

                    				    							   obj.update = function(id, record) {
                    				    								   return $http.put(serviceBase + id, record);
                    				    							   };

                    				    							   return obj;
                    				    						   } ])
                    				    						   .factory(
                    				    								   'memberRecordService',
                    				    								   [
                    				    								    'auth','$http',
                    				    								    function(auth, $http) {
                    				    								    	var serviceBase = 'rest/v1.0/employees/'+auth.user.principal.id+'/records/';
                    				    								    	var obj = {};
                    				    								    	obj.list = function(queries) {
                    				    								    		return $http.get(serviceBase, {params:queries});
                    				    								    	};

                    				    								    	obj.get = function(id) {
                    				    								    		return $http.get(serviceBase  + id);
                    				    								    	};

                    				    								    	obj.insert = function(record) {
                    				    								    		return $http.post(serviceBase, record);
                    				    								    	};

                    				    								    	obj.update = function(id, record) {
                    				    								    		return $http.put(serviceBase  + id,
                    				    								    				record);
                    				    								    	};

                    				    								    	obj.remove = function(id) {
                    				    								    		return $http.delete(serviceBase + id);
                    				    								    	};

                    				    								    	return obj;
                    				    								    } ])
                    				    								    .factory(
                    				    								    		'roleService',
                    				    								    		[
                    				    								    		 '$http',
                    				    								    		 function($http) {
                    				    								    			 var serviceBase = 'rest/v1.0/admin/roles/';
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
                    				    								    		 } ])
                    				    								    		 .factory(
                    				    								    				 'employeeRoleService',
                    				    								    				 [
                    				    								    				  '$http',
                    				    								    				  function($http) {
                    				    								    					  var serviceBase = 'rest/v1.0/admin/employees/';
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

                    				    								    					  obj.remove = function(employeeid,roleid) {
                    				    								    						  return $http.delete(serviceBase+employeeid+"/roles/"+roleid);
                    				    								    					  };

                    				    								    					  return obj;
                    				    								    				  } ])
                    				    								    				  .factory('eventService',
                    				    								    						  [ '$http', function($http) {
                    				    								    							  var serviceBase = 'rest/v1.0/admin/events/';
                    				    								    							  var obj = {};
                    				    								    							  obj.list = function(queries) {
                    				    								    								  return $http.get(serviceBase, {
                    				    								    									  params : queries
                    				    								    								  });
                    				    								    							  };

                    				    								    							  obj.get = function(id) {
                    				    								    								  return $http.get(serviceBase + id);
                    				    								    							  };

                    				    								    							  obj.update = function(id, record) {
                    				    								    								  return $http.put(serviceBase + id, record);
                    				    								    							  };

                    				    								    							  return obj;
                    				    								    						  } ])
                    				    								    						  .factory('recordService',
                    				    								    								  ['$http', function($http) {
                    				    								    									  var serviceBase = 'rest/v1.0/admin/records/';
                    				    								    									  var obj = {};
                    				    								    									  obj.list = function(queries) {
                    				    								    										  return $http.get(serviceBase, {params:queries});
                    				    								    									  };

                    				    								    									  obj.get = function(id) {
                    				    								    										  return $http.get(serviceBase  + id);
                    				    								    									  };

                    				    								    									  obj.insert = function(record) {
                    				    								    										  return $http.post(serviceBase, record);
                    				    								    									  };

                    				    								    									  obj.update = function(id, record) {
                    				    								    										  return $http.put(serviceBase  + id,
                    				    								    												  record);
                    				    								    									  };

                    				    								    									  obj.remove = function(id) {
                    				    								    										  return $http.delete(serviceBase + id);
                    				    								    									  };

                    				    								    									  obj.export = function(queries) {
                    				    								    										  return $http.get(serviceBase+'export', {params:queries});
                    				    								    									  };

                    				    								    									  return obj;
                    				    								    								  } ])
                    				    								    								  .factory('recordtypeService',
                    				    								    										  [
                    				    								    										   '$http',
                    				    								    										   function($http) {
                    				    								    											   var serviceBase = 'rest/v1.0/admin/recordtypes/';
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
                    				    								    										   } ])
                    				    								    										   .factory(
                    				    								    												   'departmentService',
                    				    								    												   [
                    				    								    												    '$http',
                    				    								    												    function($http) {
                    				    								    												    	var serviceBase = 'rest/v1.0/admin/departments/';
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
                    				    								    												    		'reportService',
                    				    								    												    		['$http', function($http) {
                    				    								    												    			var serviceBase = 'rest/v1.0/admin/reports/';
                    				    								    												    			var obj = {};
                    				    								    												    			obj.downloadRecords = function(queries) {
                    				    								    												    				return $http.get(serviceBase + 'records', {params:queries});
                    				    								    												    			};

                    				    								    												    			return obj;
                    				    								    												    		} ]);