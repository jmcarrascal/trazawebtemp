'use strict';

/* Controllers */

var app = angular.module('ngprodusimpa.controllers');

app.controller(
				'TrazaRemitoController',
				[
						'$scope',
						'$rootScope',
						'$routeParams',
						'$location',
						'$http',
						'SesionesControl',
						'MedicamentosARecepcionarDosParamFactory',
						function($scope, $rootScope, $routeParams, $location,
								$http, SesionesControl,
								MedicamentosARecepcionarDosParamFactory) {

							$rootScope.userSession = [];
							$rootScope.userSession.rol = SesionesControl
									.get('user.rol');
							$('#nroRemito').focus();
							$scope.nroRemito = "";
							$scope.submit = function() {
								$scope.message = null;
								$scope.error = null;
								bloquearVista("Procesando...", null);
								var comprob = '';
								var tipo = 'remito';
								console.log($scope.nroFactura);
								if ($scope.nroRemito == "") {
									tipo = 'factura'
									comprob = $scope.nroFactura;

								} else {
									tipo = 'remito'
									comprob = $scope.nroRemito;

								}
								console.log($scope.nroRemito);
								MedicamentosARecepcionarDosParamFactory
										.findRemitoARecepcionar(
												{
													'comprob' : comprob,
													'tipo' : tipo
												},
												function(data) {
													$rootScope.medicamentosRecepcionar = data;
													desbloquearVista();
													if (data == undefined
															|| data == null
															|| data == []
															|| data == '') {
														$scope.error = "No se encontro el comprobante: "
																+ comprob;
													} else {
														$rootScope.nrComprob = comprob;
														$location
																.path("/list_medicamento_recepcionar");
													}
												}, function(error) {
													$scope.error = error.data;
													desbloquearVista();
												});

							};

						} ]);
