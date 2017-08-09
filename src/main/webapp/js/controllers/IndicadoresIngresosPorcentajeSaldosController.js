'use strict';

/* Controllers */

var app = angular.module('ngprodusimpa.controllers');

app.controller('IndicadoresIngresosPorcentajeSaldosController', [
		'$scope',
		'$rootScope',
		'$routeParams',
		'$location',
		'$http',
		'SesionesControl',
		'StockFactory',
		function($scope, $rootScope, $routeParams, $location, $http,
				SesionesControl, StockFactory) {
			
			 $scope.onClick = function (points, evt) {
		          console.log(points, evt);
		        };
		        
		        
			$scope.loadIndicator = function() {
				bloquearVista("Recuperando los indices...", null);
				
				StockFactory.get_indicadores_porcentaje_vencidos({
					'id' : $scope.anio
				}, function(data) {					
					$scope.labels_porcentaje_vencidos = data.labels;
					$scope.series_porcentaje_vencidos = data.series;
					$scope.data_porcentaje_vencidos = data.data;
					desbloquearVista();
				}, function(error) {
					$scope.error = error.data;
					desbloquearVista();
				});
				
				bloquearVista("Recuperando los indices...", null);
				
				StockFactory.get_indicadores_porcentaje_cuarentena({
					'id' : $scope.anio
				}, function(data) {					
					$scope.labels_porcentaje_cuarentena = data.labels;
					$scope.series_porcentaje_cuarentena = data.series;
					$scope.data_porcentaje_cuarentena = data.data;
					desbloquearVista();
				}, function(error) {
					$scope.error = error.data;
					desbloquearVista();
				});
				
				bloquearVista("Recuperando los indices...", null);
				
				StockFactory.get_indicadores_porcentaje_recupero({
					'id' : $scope.anio
				}, function(data) {					
					$scope.labels_porcentaje_recupero = data.labels;
					$scope.series_porcentaje_recupero = data.series;
					$scope.data_porcentaje_recupero = data.data;
					desbloquearVista();
				}, function(error) {
					$scope.error = error.data;
					desbloquearVista();
				});
				bloquearVista("Recuperando los indices...", null);
				
				StockFactory.get_indicadores_porcentaje_desvio({
					'id' : $scope.anio
				}, function(data) {					
					$scope.labels_porcentaje_desvio = data.labels;
					$scope.series_porcentaje_desvio = data.series;
					$scope.data_porcentaje_desvio = data.data;
					desbloquearVista();
				}, function(error) {
					$scope.error = error.data;
					desbloquearVista();
				});

			};

			$scope.onClick = function(points, evt) {
				console.log(points, evt);
			};

		} ]);
