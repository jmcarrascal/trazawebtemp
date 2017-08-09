'use strict';

/* Controllers */

var app = angular.module('ngprodusimpa.controllers');

app.controller('IndicadoresPedidoSinViajeController', [
		'$scope',
		'$rootScope',
		'$routeParams',
		'$location',
		'$http',
		'SesionesControl',
		'TransacFactory',
		function($scope, $rootScope, $routeParams, $location, $http,
				SesionesControl, TransacFactory) {
			
			$scope.generarIndice = function(){				
				bloquearVista("Recuperando los indices...", null);
				TransacFactory.get_indicadores_pedido_s_viaje({					
				}, function(data){
					$scope.labels = data.object.labels;
					$scope.series = data.object.series;
					$scope.data = data.object.data;
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
