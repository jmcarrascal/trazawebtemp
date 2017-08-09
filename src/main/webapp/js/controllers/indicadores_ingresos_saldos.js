'use strict';

/* Controllers */

var app = angular.module('ngprodusimpa.controllers');

app.controller('IndicadoresIngresosSaldosController', [ '$scope', '$rootScope', '$routeParams', '$location', '$http', 'SesionesControl', 'StockFactory',
		function($scope, $rootScope, $routeParams, $location, $http, SesionesControl, StockFactory){
			
			$scope.loadIndicator = function(){
				bloquearVista("Recuperando los indices...", null);
				StockFactory.get_indicadores_ingresos_saldo_vencidos({
					'id' : $scope.anio
				}, function(data){
					$scope.labels_cant_saldo = data.labels;
					$scope.series_cant_saldo = data.series;
					$scope.data_cant_saldo = data.data;
					desbloquearVista();
				}, function(error){
					$scope.error = error.data;
					desbloquearVista();
				});
				bloquearVista("Recuperando los indices...", null);
				StockFactory.get_indicadores_ingresos_saldo_cuarentena({
					'id' : $scope.anio
				}, function(data){
					$scope.labels_cant_saldo_cuarentena = data.labels;
					$scope.series_cant_saldo_cuarentena = data.series;
					$scope.data_cant_saldo_cuarentena = data.data;
					desbloquearVista();
				}, function(error){
					$scope.error = error.data;
					desbloquearVista();
				});
				bloquearVista("Recuperando los indices...", null);
				StockFactory.get_indicadores_ingresos_saldo_recupero({
					'id' : $scope.anio
				}, function(data){
					$scope.labels_cant_saldo_recupero = data.labels;
					$scope.series_cant_saldo_recupero = data.series;
					$scope.data_cant_saldo_recupero = data.data;
					desbloquearVista();
				}, function(error){
					$scope.error = error.data;
					desbloquearVista();
				});
				bloquearVista("Recuperando los indices...", null);
				StockFactory.get_indicadores_ingresos_saldo_disponible({
					'id' : $scope.anio
				}, function(data){
					$scope.labels_cant_saldo_disponible = data.labels;
					$scope.series_cant_saldo_disponible = data.series;
					$scope.data_cant_saldo_disponible = data.data;
					desbloquearVista();
				}, function(error){
					$scope.error = error.data;
					desbloquearVista();
				});
			};
			
			$scope.onClick = function(points, evt){
				console.log(points, evt);
			};
			
		} ]);
