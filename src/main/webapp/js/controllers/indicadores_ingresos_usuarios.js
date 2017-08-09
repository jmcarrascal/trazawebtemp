'use strict';

/* Controllers */

var app = angular.module('ngprodusimpa.controllers');

app.controller('IndicadoresIngresosUsuariosController', [
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
			
			
//			$scope.operadores = [];
			$scope.operadorList =  [
					        {
					          "operNr": "1",
					          "operNombre": "Foo 1"
					        }
					    ];
			
			$scope.operador = {};
			$scope.operador.selected = [];

			
			bloquearVista("Recuperando los operadores...", null);
			UsuarioFactory.getOperadoresSk({
			}, function(data) {
				$scope.operadorList = data;
				desbloquearVista();
			}, function(error) {
				$scope.error = error.data;
				desbloquearVista();
			});
			
			
			$scope.generateIndice = function(){
				$scope.operadoresStr = "";
				if ($scope.operador.selected.length < 1){
					$scope.error = "Debe ingresar al menos un operador"
				}
				else{
					for (var i = 0; i < $scope.operador.selected.length; i++) { 					
						if ($scope.operadoresStr == ""){
							$scope.operadoresStr = $scope.operador.selected[i]["operNr"];
						}else{
							$scope.operadoresStr = $scope.operadoresStr + "," + $scope.operador.selected[i]["operNr"];
						}
					}
				}
				console.log($scope.operadoresStr);
				$scope.loadIndicator();
			};
			$scope.loadIndicator = function() {
				bloquearVista("Recuperando los indices...", null);

				StockFactory.get_indicadores_operadores({
					'anio' : $scope.anio,
					'operadores' : $scope.operadoresStr
				}, function(data) {
					if (data.success) {
						$scope.labels_cant_ingresos_operadores = data.labels;
						$scope.series_cant_ingresos_operadores = data.series;
						$scope.data_cant_ingresos_operadores = data.data;
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
				console.log(points, evt);
			};

		} ]);
