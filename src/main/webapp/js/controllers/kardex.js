'use strict';

/* Controllers */

var app = angular.module('ngprodusimpa.controllers');

app.controller('KardexController', [
    '$scope', '$rootScope', '$routeParams', '$location', '$http', 'SesionesControl', 'AgendadoFactory', 'KardexFactory', function($scope, $rootScope, $routeParams, $location, $http, SesionesControl, AgendadoFactory, KardexFactory) {

        $scope.ingreso = {};
        $scope.ingresos = [];

        $scope.reset = function() {
            $scope.ingresos = [];
        };

        $scope.downloadPDF = function() {

        	if ($scope.tipo == 'fecha'){
	            $scope.message = null;
	            if ($scope.fecha != '') {
	                bloquearVista("Buscando ingreso...", null);
	                KardexFactory.getReportIngreso({ 'descrip' : $scope.fecha }, function(data) {
	                    if (data.success) {
	
	                        window.open("data:application/pdf;base64," + data.msg, "_blank");
	
	                    }
	                    desbloquearVista();
	                }, function(error) {
	                    $scope.error = error.data;
	                    desbloquearVista();
	                });
	
	            } else {
	                $scope.ingreso = {};
	                $scope.ingresos = [];
	            }
        	}
        	
        	if ($scope.tipo == 'refInterna'){
	            $scope.message = null;
	            if ($scope.fecha != '') {
	                bloquearVista("Buscando ingreso...", null);
	                KardexFactory.getReportIngresoRefInterna({ 'descrip' : $scope.refInterna }, function(data) {
	                    if (data.success) {
	
	                        window.open("data:application/pdf;base64," + data.msg, "_blank");
	
	                    }
	                    desbloquearVista();
	                }, function(error) {
	                    $scope.error = error.data;
	                    desbloquearVista();
	                });
	
	            } else {
	                $scope.ingreso = {};
	                $scope.ingresos = [];
	            }
        	}
        };
        $scope.tipo = 'fecha';
        $scope.searchIngreso = function(campo) {
        	
        	if (campo == 'fecha'){
        		$scope.tipo = 'fecha';
        		$scope.message = null;
                if ($scope.fecha != '') {
                    bloquearVista("Buscando ingreso...", null);
                    KardexFactory.findIngreso({ 'descrip' : $scope.fecha }, function(data) {
                        if (data.success) {
                            $scope.ingresos = data.list;
                            if (data.list.length < 1) {
                                console.log('sss');
                                $scope.message = "No se han encontrado ingresos con los valores de búsqueda cargados.. (fecha = '" + $scope.fecha + "')";
                            }
                        }
                        desbloquearVista();
                    }, function(error) {
                        $scope.error = error.data;
                        desbloquearVista();
                    });

                } else {
                    $scope.ingreso = {};
                    $scope.ingresos = [];
                }
        		
        	}
        	
        	if (campo == 'refInterna'){
        		$scope.tipo = 'refInterna';
        		$scope.message = null;
                if ($scope.fecha != '') {
                    bloquearVista("Buscando ingreso...", null);
                    KardexFactory.findIngresoRefInterna({ 'descrip' : $scope.refInterna }, function(data) {
                        if (data.success) {
                            $scope.ingresos = data.list;
                            if (data.list.length < 1) {
                                console.log('sss');
                                $scope.message = "No se han encontrado ingresos con los valores de búsqueda cargados.. (fecha = '" + $scope.fecha + "')";
                            }
                        }
                        desbloquearVista();
                    }, function(error) {
                        $scope.error = error.data;
                        desbloquearVista();
                    });

                } else {
                    $scope.ingreso = {};
                    $scope.ingresos = [];
                }
        		
        	}
        	
            
        }

    }
]);
