'use strict';

/* Controllers */

var app = angular.module('ngprodusimpa.controllers');

app.controller('ExportMedifeInternoController', [ '$scope', '$rootScope', '$routeParams', '$location', '$http', 'SesionesControl', 'TransacFactory','FileSaver', 'Blob',
		function($scope, $rootScope, $routeParams, $location, $http, SesionesControl, TransacFactory, FileSaver, Blob, ExportMedifeInternoController){
			
			$scope.facturas = [];
			
			
			$scope.selections = {
				    ids: []
				  };
			
			
			
			
			$scope.generateExport = function(){
				
				console.log($scope.selections);
				bloquearVista("Buscando facturas...", null);
				TransacFactory.exportTransacMedifeInterno({
					'fechaDesde' : $scope.busqueda.fechaDesde,
					'fechaHasta' : $scope.busqueda.fechaHasta,
					'genteNr' : $scope.busqueda.genteNr,
					'selectedList' : $scope.selections.ids
				}, function(data){
					
					desbloquearVista();					
					var data = new Blob([data.msg], { type: 'text/plain;charset=utf-8' });
				    FileSaver.saveAs(data, 'MEDIFE_' + $scope.busqueda.fechaDesde + '_' + $scope.busqueda.fechaDesde + '.txt');
				    
				}, function(error){
					$scope.error = error.data;
					desbloquearVista();
				});
			};
			
			
			$scope.generateExportIndosur = function(){
				
				console.log($scope.selections);
				bloquearVista("Buscando remitos...", null);
				TransacFactory.exportTransacIndosur({
					'fechaDesde' : $scope.busqueda.fechaDesde,
					'fechaHasta' : $scope.busqueda.fechaHasta,
					'genteNr' : $scope.busqueda.genteNr,
					'selectedList' : $scope.selections.ids
				}, function(data){
					
					desbloquearVista();					
					var data = new Blob([data.msg], { type: 'text/plain;charset=utf-8' });
				    FileSaver.saveAs(data, 'IOSPER_' + $scope.busqueda.fechaDesde + '_' + $scope.busqueda.fechaDesde + '.txt');
				    
				}, function(error){
					$scope.error = error.data;
					desbloquearVista();
				});
			};
			
			$scope.setValue = function(transacNr){
				console.log(transacNr);
				
				
			}
			
			
			
			$scope.getTransacRemitos = function(){
				bloquearVista("Buscando remitos...", null);
				TransacFactory.getTransacRemitos({
					'fechaDesde' : $scope.busqueda.fechaDesde,
					'fechaHasta' : $scope.busqueda.fechaHasta,
					'genteNr' : $scope.busqueda.genteNr
				}, function(data){
					$scope.facturas = data.list;
					desbloquearVista();
				}, function(error){
					$scope.error = error.data;
					desbloquearVista();
				});
			};
			
			
			
			$scope.getTransacMedifeInterno = function(){
				bloquearVista("Buscando facturas...", null);
				TransacFactory.getTransacMedifeInterno({
					'fechaDesde' : $scope.busqueda.fechaDesde,
					'fechaHasta' : $scope.busqueda.fechaHasta,
					'genteNr' : $scope.busqueda.genteNr
				}, function(data){
					$scope.facturas = data.list;
					desbloquearVista();
				}, function(error){
					$scope.error = error.data;
					desbloquearVista();
				});
			};
			
			
		} ]);
