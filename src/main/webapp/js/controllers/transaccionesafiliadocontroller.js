"use strict";

/* Controllers */

var app = angular.module('ngprodusimpa.controllers');

app.controller('TransaccionesAfiliadoListController', [ '$scope', '$rootScope', '$routeParams', '$location', '$http', '$timeout', 'SesionesControl', 'PublicFactory', 'TransacFactory', '$route',
		TransaccionesListController ]);
function TransaccionesListController($scope, $rootScope, $routeParams, $location, $http, $timeout, SesionesControl, PublicFactory, TransacFactory, $route){
	$scope.agendado = {};
	$scope.agendadoEdit = {};
	$scope.comprobantePDF = {};
	$scope.transacciones = [];
	$scope.transaccion = {};
	
	// $scope.transaccionEdit = {};
	$scope.gente = [];
	$scope.pageNumber = 1;
	$scope.total_items = 0;
	$scope.defaultListSize = 20;
	$scope.stopFindItemInit = true;
	$scope.stopFindItemTemp = false;
	$scope.stopFindItem = false;
	$scope.vista = "crm";
	$scope.busqueda = {};
	
	PublicFactory.getObraSocialAll({}, function(data){
		// $scope.desbloquearVista();
		if (data.success) {
			$scope.gente = data.list;
		}
	}, function(error){
		// $scope.desbloquearVista();
		$scope.error = error.data;
	});
	
	$scope.buscarTranscaciones = function(status){
		console.log($scope.stopFindItem);
		$scope.transacciones = [];
		if (status == 'init') {
			$scope.transacciones = [];
			$scope.pageNumber = 1;
			$scope.stopFindItem = false;
		}
		$scope.stopFindItemInit = false;
		$scope.stopFindItemTemp = true;
		$scope.message = null;
		bloquearVista("Buscando comprobantes...", null);
		
		if ($scope.stopFindItem) {
			desbloquearVista();
		} else {
			
			PublicFactory.getPedidos({				
				"agendado": $scope.busqueda.obraSocial,
				"afiliado":$scope.busqueda.afiliado,
				"busqueda" : {},
				"pageNumber" : $scope.pageNumber,
				"pageSize" : $scope.defaultListSize,
			}, function(data){
				if (data.success) {
					if (!data.list || data.list.length < 1) {
						$scope.transacciones = [];
						$scope.message = "No se han encontrado comprobantes con los valores de búsqueda cargados...";
						$scope.stopFindItemTemp = false;
					} else {
						if ($scope.transacciones.length === 0) {
							$scope.transacciones = data.list;
						} else {
							$scope.transacciones = $scope.transacciones.concat(data.list);
						}
						$scope.total_items = data.total_items;
						$scope.stopFindItemTemp = false;
						
					}
				}
				desbloquearVista();
			}, function(error){
				$scope.error = error.data;
				desbloquearVista();
				$scope.stopFindItemTemp = false;
			});
		}
		
	}
};

// Sort Menu
$(document).scroll(function(){
	var y = $(this).scrollTop();
	if (y > 100) {
		$('.counter').addClass("counter-scroll");
	} else {
		$('.counter').removeClass("counter-scroll");
	}
	
});

