'use strict';

/* Controllers */

var app = angular.module('ngprodusimpa.controllers');

app.controller('IndicadoresIngresosPorExistenciaController', [
		'$scope',
		'$rootScope',
		'$routeParams',
		'$location',
		'$http',
		'SesionesControl',
		'StockFactory',
		'UsuarioFactory',
		function($scope, $rootScope, $routeParams, $location, $http,
				SesionesControl, StockFactory, UsuarioFactory) {
			
			$scope.mes_filter ="";
			$scope.existencia = {};
			$scope.existencia.selected = [];

			
			bloquearVista("Recuperando las existencias...", null);
			StockFactory.getAllExistencias({
			}, function(data) {
				$scope.existenciaList = data;
				desbloquearVista();
			}, function(error) {
				$scope.error = error.data;
				desbloquearVista();
			});
			
			
			$scope.generateIndice = function(){
				$scope.mes_filter =""
				$scope.existenciaStr = "";
				if ($scope.existencia.selected.length < 1){
					$scope.error = "Debe ingresar al menos una existencia"
				}
				else{
					for (var i = 0; i < $scope.existencia.selected.length; i++) { 					
						if ($scope.existenciaStr == ""){
							$scope.existenciaStr = $scope.existencia.selected[i]["nr"];
						}else{
							$scope.existenciaStr = $scope.existenciaStr + "," + $scope.existencia.selected[i]["nr"];
						}
					}
				}
				console.log($scope.existenciaStr);
				$scope.loadIndicator();
			};
			$scope.loadIndicator = function() {
				bloquearVista("Recuperando los indices...", null);

				StockFactory.get_indicadores_por_existencia({
					'anio' : $scope.anio,
					'existencias' : $scope.existenciaStr
				}, function(data) {
					if (data.success) {
						$scope.labels_cant_por_existencia = data.labels;
						$scope.series_cant_por_existencia = data.series;
						$scope.data_cant_por_existencia = data.data;
						desbloquearVista();
					} else {
						desbloquearVista();
						$scope.error = data.msg;
					}
					
				}, function(error) {
					$scope.error = error.data;
					desbloquearVista();
				});

			};

			$scope.onClick = function(points, evt) {
				console.log(points);
				bloquearVista("Recuperando los indices...", null);
				if (points[0] != undefined){
					$scope.mes_filter = points[0].label
					StockFactory.get_indicadores_por_existencia_por_mes({
						'anio' : $scope.anio,
						'existencias' : $scope.existenciaStr,
						'mes':$scope.mes_filter
					}, function(data) {
						if (data.success) {
							$scope.labels_cant_por_existencia_mes = data.labels;
							$scope.series_cant_por_existencia_mes = data.series;
							$scope.data_cant_por_existencia_mes = data.data;
							desbloquearVista();
						} else {
							desbloquearVista();
							$scope.error = data.msg;
						}
						
					}, function(error) {
						$scope.error = error.data;
						desbloquearVista();
					});
				}
				
				
			};

		} ]);
