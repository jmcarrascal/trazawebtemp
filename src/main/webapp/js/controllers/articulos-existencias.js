'use strict';

/* Controllers */

var app = angular.module('ngprodusimpa.controllers');

app.controller('ArticulosExistenciasController', ['$scope', '$rootScope', '$routeParams', '$location', '$http', 'ArticulosExistenciasFactory', 'SesionesControl', function($scope, $rootScope, $routeParams, $location, $http, ArticulosExistenciasFactory, SesionesControl) {

	var lastSearch = [];
	var userId =  SesionesControl.get('user.id');
	var canSearch = true;
	
	$scope.init = function () {
		$scope.search = [];
		$scope.itemsList = [];
	    $scope.totalServerItems = 0;
	    $scope.pagingOptions = {
	        pageSizes: [30, 50, 100],
	        pageSize: 30,
	        currentPage: 1
	    };			
	};
	
	$scope.searchArticulos = function (search, page) {
		console.log("Page: ", page);
		bloquearVista("Buscando artículos...", null);
		ArticulosExistenciasFactory.findArticulos({
			'search': search,
			'userId': userId,
			'pageSize': $scope.pagingOptions.pageSize,
			'currentPage': page
		}, function (data) {
			$scope.itemsList = data.list;
			lastSearch = search;
			$scope.totalServerItems = data.total_items;
			if (!$scope.$$phase) {
	            $scope.$apply();
	        }
			desbloquearVista();
		}, function (error) {
			desbloquearVista();
		});
	};
	
	
    
    $scope.$watch('pagingOptions', function (newVal, oldVal) {
        if (newVal !== oldVal && newVal.currentPage !== oldVal.currentPage) {
        	if  ($scope.pagingOptions.currentPage != undefined && $scope.pagingOptions.currentPage != 1) {
        		$scope.searchArticulos(lastSearch, $scope.pagingOptions.currentPage);
        	}
        }
    }, true);
    
    $scope.getStyle = function (data) {
		if (data < 1 ) {
			return {
				'color': 'red'
			}
		}
	}
    
    $scope.getDateStyle = function (data) {
		var today = new Date;
		var nextMonth = new Date;
		data = new Date(data);
		nextMonth.setMonth(nextMonth.getMonth() + 1);
		if (data <= nextMonth ) {
			if (data <= today) {
				return {
					'color': 'red'
				}
			}
			return {
				'color': 'orange'
			}
		}
	}
   
    $scope.gridOptions = {
        data: 'itemsList',
        enablePaging: true,
		showFooter: true,
		init: $scope.init(),
		enableColumnResize: true,
        totalServerItems: 'totalServerItems',
        pagingOptions: $scope.pagingOptions,
        columnDefs: [
                     { field: 'id', displayName: 'ID' },
                     { field: 'descripcion', 
                    	 displayName: 'Artículo',
                    	 width : 250},
                     { field: 'stockReal',   
                    	displayName: 'Stock Real',
                    	cellTemplate: '<div ng-style="getStyle(row.getProperty(col.field))">{{row.getProperty(col.field)}}</div>'},
                     { field: 'stock',  
                    	displayName: 'Stock Calculado',
                    	cellTemplate: '<div ng-style="getStyle(row.getProperty(col.field))">{{row.getProperty(col.field)}}</div>'},
                     { field: 'fechaVencimientoMasProxima', 
                    	displayName: 'Próx fecha venc',
                    	cellTemplate: '<div ng-if="row.getProperty(col.field) != null" ng-style="getDateStyle(row.getProperty(col.field))">{{row.getProperty(col.field) | date:"dd/MM/yyyy"}}</div>' + 
                    	 				'<div ng-if="row.getProperty(col.field) == null">-</div>'}
                   ]
                   
    };
    
}]);

