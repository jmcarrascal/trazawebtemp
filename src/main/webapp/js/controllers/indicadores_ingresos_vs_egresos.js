'use strict';

/* Controllers */

var app = angular.module('ngprodusimpa.controllers');

app.controller('IndicadoresIngresosVsEgresosController', [
		'$scope',
		'$rootScope',
		'$routeParams',
		'$location',
		'$http',
		'SesionesControl',
		'StockFactory',
		function($scope, $rootScope, $routeParams, $location, $http,
				SesionesControl, StockFactory) {
			
			$scope.loadIndicator = function() {
				bloquearVista("Recuperando los indices...", null);
				StockFactory.get_indicadores_ingresos({
					'id' : $scope.anio
				}, function(data) {
					$scope.labels_cant_traza = data.labels;
					$scope.series_cant_traza = data.series;
					$scope.data_cant_traza = data.data;					
					desbloquearVista();
				}, function(error) {
					$scope.error = error.data;
					desbloquearVista();
				});
				StockFactory.get_indicadores_ingresos_vencidos({
					'id' : $scope.anio
				}, function(data) {					
					$scope.labels_cant_vencidos = data.labels;
					$scope.series_cant_vencidos = data.series;
					$scope.data_cant_vencidos = data.data;
					desbloquearVista();
				}, function(error) {
					$scope.error = error.data;
					desbloquearVista();
				});
				StockFactory.get_indicadores_ingresos_egresos({
					'id' : $scope.anio
				}, function(data) {					
					$scope.labels_cant_ingresos_egresos = data.labels;
					$scope.series_cant_ingresos_egresos = data.series;
					$scope.data_cant_ingresos_egresos = data.data;
					desbloquearVista();
				}, function(error) {
					$scope.error = error.data;
					desbloquearVista();
				});
				StockFactory.get_indicadores_cuarentena({
					'id' : $scope.anio
				}, function(data) {					
					$scope.labels_cant_cuarentena = data.labels;
					$scope.series_cant_cuarentena = data.series;
					$scope.data_cant_cuarentena = data.data;
					desbloquearVista();
				}, function(error) {
					$scope.error = error.data;
					desbloquearVista();
				});
				StockFactory.get_indicadores_operadores({
					'id' : $scope.anio
				}, function(data) {					
					$scope.labels_cant_ingresos_operadores = data.labels;
					$scope.series_cant_ingresos_operadores = data.series;
					$scope.data_cant_ingresos_operadores = data.data;
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
