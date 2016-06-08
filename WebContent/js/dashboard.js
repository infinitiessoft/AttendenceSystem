angular
		.module('dashboard', [])
		.controller(
				'dashboard',
				[
						'$scope',
						'$http',
						'auth',
						'generalService',
						'memberRecordService',
						'memberEventService',
						'memberLeaveService',
						'$timeout',
						function($scope, $http, auth, generalService,
								memberRecordService, memberEventService, memberLeaveService,
								$timeout) {
							var today = new Date();
							var startDate = new Date(auth.user.principal.dateofjoined);
							var endDate = new Date(auth.user.principal.dateofjoined);
							var start = new Date(today.getFullYear(), 0, 0);
							var diff = today - start;
							var oneDay = 1000 * 60 * 60 * 24;
							var todayofyear = Math.floor(diff / oneDay);
							
							diff = new Date(auth.user.principal.dateofjoined) - start;
							var startdayofyear = Math.floor(diff / oneDay);
							
							endDate.setDate(endDate.getDate()-1);
							if(startdayofyear< todayofyear){
								startDate.setYear(today.getFullYear()-1);
								endDate.setYear(today.getFullYear());
							}else{
								startDate.setYear(today.getFullYear());
								endDate.setYear(today.getFullYear()+1);
							}
							$scope.startDate = startDate.toISOString();
							$scope.endDate = endDate.toISOString();
							
							$scope.bar = {
								labels : [ 'Records', 'Events' ],
								series : [ 'Permit', 'Pending', 'Reject' ],
								data : [ [ 0, 0 ], [ 0, 0 ], [ 0, 0 ] ],
								colors : [
										{ // blue
											fillColor : "#428bca",
											strokeColor : "#428bca",
											pointColor : "#428bca",
											pointStrokeColor : "#fff",
											pointHighlightFill : "#fff",
											pointHighlightStroke : "rgba(151,187,205,0.8)"
										},
										{ // light grey
											fillColor : "gray",
											strokeColor : "gray",
											pointColor : "gray",
											pointStrokeColor : "#fff",
											pointHighlightFill : "#fff",
											pointHighlightStroke : "rgba(220,220,220,0.8)"
										},
										{ // red
											fillColor : "#d9534f",
											strokeColor : "#d9534f",
											pointColor : "#d9534f",
											pointStrokeColor : "#fff",
											pointHighlightFill : "#fff",
											pointHighlightStroke : "rgba(247,70,74,0.8)"
										},
										{ // green
											fillColor : "green",
											strokeColor : "green",
											pointColor : "green",
											pointStrokeColor : "#fff",
											pointHighlightFill : "#fff",
											pointHighlightStroke : "rgba(70,191,189,0.8)"
										},
										{ // yellow
											fillColor : "rgba(253,180,92,0.2)",
											strokeColor : "rgba(253,180,92,1)",
											pointColor : "rgba(253,180,92,1)",
											pointStrokeColor : "#fff",
											pointHighlightFill : "#fff",
											pointHighlightStroke : "rgba(253,180,92,0.8)"
										},
										{ // grey
											fillColor : "rgba(148,159,177,0.2)",
											strokeColor : "rgba(148,159,177,1)",
											pointColor : "rgba(148,159,177,1)",
											pointStrokeColor : "#fff",
											pointHighlightFill : "#fff",
											pointHighlightStroke : "rgba(148,159,177,0.8)"
										},
										{ // dark grey
											fillColor : "rgba(77,83,96,0.2)",
											strokeColor : "rgba(77,83,96,1)",
											pointColor : "rgba(77,83,96,1)",
											pointStrokeColor : "#fff",
											pointHighlightFill : "#fff",
											pointHighlightStroke : "rgba(77,83,96,1)"
										} ]
							};

							$scope.donutRecord = {
								labels : [],
								data : [],
								colors : [
										{ // green
											fillColor : "green",
											strokeColor : "green",
											pointColor : "green",
											pointStrokeColor : "#fff",
											pointHighlightFill : "#fff",
											pointHighlightStroke : "rgba(70,191,189,0.8)"
										},
										{ // red
											fillColor : "#d9534f",
											strokeColor : "#d9534f",
											pointColor : "#d9534f",
											pointStrokeColor : "#fff",
											pointHighlightFill : "#fff",
											pointHighlightStroke : "rgba(247,70,74,0.8)"
										},
										{ // blue
											fillColor : "#428bca",
											strokeColor : "#428bca",
											pointColor : "#428bca",
											pointStrokeColor : "#fff",
											pointHighlightFill : "#fff",
											pointHighlightStroke : "rgba(151,187,205,0.8)"
										},
										{ // yellow
											fillColor : "rgba(253,180,92,0.2)",
											strokeColor : "rgba(253,180,92,1)",
											pointColor : "rgba(253,180,92,1)",
											pointStrokeColor : "#fff",
											pointHighlightFill : "#fff",
											pointHighlightStroke : "rgba(253,180,92,0.8)"
										},
										{ // light grey
											fillColor : "gray",
											strokeColor : "gray",
											pointColor : "gray",
											pointStrokeColor : "#fff",
											pointHighlightFill : "#fff",
											pointHighlightStroke : "rgba(220,220,220,0.8)"
										},
										{ // grey
											fillColor : "rgba(148,159,177,0.2)",
											strokeColor : "rgba(148,159,177,1)",
											pointColor : "rgba(148,159,177,1)",
											pointStrokeColor : "#fff",
											pointHighlightFill : "#fff",
											pointHighlightStroke : "rgba(148,159,177,0.8)"
										},
										{ // dark grey
											fillColor : "rgba(77,83,96,0.2)",
											strokeColor : "rgba(77,83,96,1)",
											pointColor : "rgba(77,83,96,1)",
											pointStrokeColor : "#fff",
											pointHighlightFill : "#fff",
											pointHighlightStroke : "rgba(77,83,96,1)"
										} ]
							};

							$scope.donutEvent = {
								labels : [],
								data : [],
								colors : [
										{ // green
											fillColor : "green",
											strokeColor : "green",
											pointColor : "green",
											pointStrokeColor : "#fff",
											pointHighlightFill : "#fff",
											pointHighlightStroke : "rgba(70,191,189,0.8)"
										},
										{ // red
											fillColor : "#d9534f",
											strokeColor : "#d9534f",
											pointColor : "#d9534f",
											pointStrokeColor : "#fff",
											pointHighlightFill : "#fff",
											pointHighlightStroke : "rgba(247,70,74,0.8)"
										},
										{ // blue
											fillColor : "#428bca",
											strokeColor : "#428bca",
											pointColor : "#428bca",
											pointStrokeColor : "#fff",
											pointHighlightFill : "#fff",
											pointHighlightStroke : "rgba(151,187,205,0.8)"
										},
										{ // yellow
											fillColor : "rgba(253,180,92,0.2)",
											strokeColor : "rgba(253,180,92,1)",
											pointColor : "rgba(253,180,92,1)",
											pointStrokeColor : "#fff",
											pointHighlightFill : "#fff",
											pointHighlightStroke : "rgba(253,180,92,0.8)"
										},
										{ // light grey
											fillColor : "gray",
											strokeColor : "gray",
											pointColor : "gray",
											pointStrokeColor : "#fff",
											pointHighlightFill : "#fff",
											pointHighlightStroke : "rgba(220,220,220,0.8)"
										},
										{ // grey
											fillColor : "rgba(148,159,177,0.2)",
											strokeColor : "rgba(148,159,177,1)",
											pointColor : "rgba(148,159,177,1)",
											pointStrokeColor : "#fff",
											pointHighlightFill : "#fff",
											pointHighlightStroke : "rgba(148,159,177,0.8)"
										},
										{ // dark grey
											fillColor : "rgba(77,83,96,0.2)",
											strokeColor : "rgba(77,83,96,1)",
											pointColor : "rgba(77,83,96,1)",
											pointStrokeColor : "#fff",
											pointHighlightFill : "#fff",
											pointHighlightStroke : "rgba(77,83,96,1)"
										} ]
							};

							memberRecordService
									.metadata()
									.then(
											function(status) {
												$scope.bar.data[0][0] = status.data.permit;
												$scope.bar.data[1][0] = status.data.pending;
												$scope.bar.data[2][0] = status.data.reject;
												for ( var k in status.data) {
													if (status.data
															.hasOwnProperty(k)
															&& k != 'permit'
															&& k != 'pending'
															&& k != 'reject') {
														$scope.donutRecord.labels
																.push(k);
														$scope.donutRecord.data
																.push(status.data[k]);
													}
												}
											});

							memberEventService
									.metadata()
									.then(
											function(status) {
												$scope.bar.data[0][1] = status.data.permit;
												$scope.bar.data[1][1] = status.data.pending;
												$scope.bar.data[2][1] = status.data.reject;
												for ( var k in status.data) {
													if (status.data
															.hasOwnProperty(k)
															&& k != 'permit'
															&& k != 'pending'
															&& k != 'reject') {
														$scope.donutEvent.labels
																.push(k);
														$scope.donutEvent.data
																.push(status.data[k]*10);
													}
												}
											});
							
							var params = {'typeName':'annual'};
					   		memberLeaveService
					   			.list(params)
					   			.then(
					   					function(status) {
					   						console.log(status.data);
					   						var days = status.data.usedDays + "/" + status.data.leavesetting.days;
					   						$scope.annual = days;
					   					}
					   			);
					   		
					   		var params = {'typeName':'personal'};
					   		memberLeaveService
					   			.list(params)
					   			.then(
					   					function(status) {
					   						console.log(status.data);
					   						var days = status.data.usedDays + "/" + status.data.leavesetting.days;
					   						$scope.personal = days;
					   					}
					   			);
					   		var params = {'typeName':'sick'};
					   		memberLeaveService
					   			.list(params)
					   			.then(
					   					function(status) {
					   						console.log(status.data);
					   						var days = status.data.usedDays + "/" + status.data.leavesetting.days;
					   						$scope.sick = days;
					   					}
					   			);
					   		var params = {'typeName':'others'};
					   		memberLeaveService
					   			.list(params)
					   			.then(
					   					function(status) {
					   						console.log(status.data);
					   						var days = status.data.usedDays + "/" + status.data.leavesetting.days;
					   						$scope.others = days;
					   					}
					   			);

							var lastState = {
								pagination : {
									start : 0,
									number : 4
								},
								sort : {
									predicate : 'id',
									reverse : true
								},
								search : {
									predicateObject : {}
								}
							};

							var forEachSorted = function(obj, iterator, context) {
								var keys = sortedKeys(obj);
								for (var i = 0; i < keys.length; i++) {
									iterator.call(context, obj[keys[i]],
											keys[i]);
								}
								return keys;
							};

							var sortedKeys = function(obj) {
								var keys = [];
								for ( var key in obj) {
									if (obj.hasOwnProperty(key)) {
										keys.push(key);
									}
								}
								return keys.sort();
							};

							var buildUrl = function(url, params) {
								if (!params)
									return url;
								var parts = [];
								forEachSorted(params, function(value, key) {
									if (value == null || value == undefined)
										return;
									if (angular.isObject(value)) {
										value = angular.toJson(value);
									}
									parts.push(encodeURIComponent(key) + '='
											+ encodeURIComponent(value));
								});
								return url
										+ ((url.indexOf('?') == -1) ? '?' : '&')
										+ parts.join('&');
							};

							var queryParams = function(tableState) {
								var pagination = tableState.pagination;
								var start = pagination.start || 0;
								var pageSize = pagination.number || 4;
								var sort = tableState.sort.predicate;
								var dir = tableState.sort.reverse ? 'DESC'
										: 'ASC';
								var predicate = tableState.search.predicateObject;
								var page = (start / pageSize);
								if (page < 0) {
									page = 0;
								}
								lastState.pagination.start = start;
								lastState.pagination.number = pageSize;
								lastState.sort.predicate = sort;
								lastState.sort.reverse = tableState.sort.reverse;
								lastState.search = tableState.search;
								var filters = tableState.search.predicateObject
										|| {};
								filters.sort = sort;
								filters.pageSize = pageSize;
								filters.page = page;
								filters.dir = dir;

								return filters;
							}

							$scope.displayed = [];
							$scope.callServer = function callServer(tableState) {
								$scope.isLoading = true;
								var filters = queryParams(tableState);
								generalService
										.getRecordsToday(filters)
										.then(
												function(status) {
													$scope.displayed = status.data.content;
													tableState.pagination.numberOfPages = status.data.totalPages;
													$scope.isLoading = false;
												});
							};

						} ]);
