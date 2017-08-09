"use strict";

/* Controllers */

var app = angular.module('ngprodusimpa.controllers');

app.controller('ArticuloController', [ '$scope', '$rootScope', '$routeParams',
		'$location', '$http', '$timeout', 'SesionesControl', 'ArticuloFactory',
		'FamiliaFactory', 'SubFamiliaFactory', ArticuloController ]);
function ArticuloController($scope, $rootScope, $routeParams, $location, $http,
		$timeout, SesionesControl, ArticuloFactory, FamiliaFactory,
		SubFamiliaFactory) {
	$scope.articulos = [];
	$scope.articulo = {};

	$scope.familias = [];
	$scope.subFamilias = [];

	$scope.busqueda = {};

	FamiliaFactory.getFamilias({}, function(data) {
		desbloquearVista();
		if (data.success) {
			$scope.familias = data.list;
		}
	}, function(error) {
		desbloquearVista();
		$scope.error = error.data;
	});

	SubFamiliaFactory.getSubFamilias({}, function(data) {
		desbloquearVista();
		if (data.success) {
			$scope.subFamilias = data.list;
		}
	}, function(error) {
		desbloquearVista();
		$scope.error = error.data;
	});

	$scope.newArticulo = function() {
		$scope.articulo.id = 0;
		$scope.articulo.show();
	};
	
	$scope.openListado = function () {
		$("#modalSelectArticulo").modal("show");
		angular.element('#searchByCodeName').focus();
	};
	
	

	$scope.preparedEdit = function(id) {
		$scope.articulo.id = id;
		$scope.articulo.show(function modalOk() {
			$scope.searchArticulo();
		});
	}

	$scope.showComprobante = function(articulo) {
		console.log("Articulo...");
		window.location = "#/articulo/" + articulo.id + "/transacciones";
	};

	$scope.get_items = function() {
		if ($scope.stopFindItemTemp == true) {
			return;
		}
		$scope.stopFindItemTemp = true;
		if (!$scope.stopFindItemInit) {
			$scope.pageNumber = ($scope.articulos.length / $scope.defaultListSize) + 1;
			$scope.searchArticulo('paging');
		}
	}

	$scope.defaultListSize = 10;

	$scope.pageNumber = 1;

	$scope.stopFindItemTemp = false;

	$scope.stopFindItem = false;

	$scope.stopFindItemInit = true;
	
	$scope.fixDate = function() {
		for (var i = 0; i < $scope.articulos.length; i++) {
			if ($scope.articulos[i].fechaVencimientoMasProxima != null) {
				var date = new Date($scope.articulos[i].fechaVencimientoMasProxima);
				date.setDate(date.getDate() + 1);
				$scope.articulos[i].fechaVencimientoMasProxima = date;
			}
		}
	}
	
	$scope.getStyle = function (fechaVencimientoMasProxima) {
		var today = new Date;
		var nextMonth = new Date;
		nextMonth.setMonth(nextMonth.getMonth() + 1);
		if (fechaVencimientoMasProxima <= nextMonth) {
			if (fechaVencimientoMasProxima <= today) {
				return {
					'color': 'red'
				}
			}
			return {
				'color': 'orange'
			}
		}
	}

	$scope.searchArticulo = function(status) {
		if (status == 'init') {
			$scope.articulos = [];
			$scope.pageNumber = 1;
		}
		$scope.stopFindItemInit = false;
		$scope.stopFindItemTemp = true;
		$scope.message = null;
		bloquearVista("Buscando articulos...", null);
		if ($scope['total_items'] <= $scope.articulos.length) {
			$scope.stopFindItem = true;
			desbloquearVista();
		} else {

			ArticuloFactory
					.findArticulo(
							{
								'busqueda' : $scope.busqueda,
								"pageNumber" : $scope.pageNumber,
								"pageSize" : $scope.defaultListSize
							},
							function(data) {
								if (data.success) {
									if (!data.list || data.list.length < 1) {
										$scope.message = "No se han encontrado articulos con los valores de búsqueda cargados...";
										$scope.stopFindItemTemp = false;
									} else {
										if ($scope.articulos.length == 0) {
											$scope['articulos'] = data.list;
											$scope.fixDate();
											
										} else {
											$scope['articulos'] = $scope['articulos']
													.concat(data.list);
											$scope.fixDate();
										}
										$scope['total_items'] = data.total_items;
										$scope.stopFindItemTemp = false;
										if ($scope['total_items'] <= $scope.articulos.length) {
											$scope.stopFindItem = true;
										}
									}
								}
								desbloquearVista();
							}, function(error) {
								$scope.error = error.data;
								desbloquearVista();
								$scope.stopFindItemTemp = false;
							});
		}

	};

	// Sort Menu
	$(document).scroll(function() {
		var y = $(this).scrollTop();
		if (y > 100) {
			$('.counter').addClass("counter-scroll");
		} else {
			$('.counter').removeClass("counter-scroll");
		}

	});
}

app.controller('ArticuloEditController', [ '$scope', '$rootScope',
		'$routeParams', '$location', '$http', '$timeout', 'SesionesControl',
		'ArticuloFactory', 'FamiliaFactory', 'SubFamiliaFactory',
		'AgendadoFactory', 'ImpuestosFactory', ArticuloEditController ]);
function ArticuloEditController($scope, $rootScope, $routeParams, $location,
		$http, $timeout, SesionesControl, ArticuloFactory, FamiliaFactory,
		SubFamiliaFactory, AgendadoFactory, ImpuestosFactory) {

	$scope.booleanValues = [ {
		"codigo" : 0,
		"descrip" : "No"
	}, {
		"codigo" : -1,
		"descrip" : "Si"
	} ];

	$scope.agendado = {};
	$scope.error = null;
	$scope.provincias = [];
	$scope.zonas = [];
	$scope.ivacodigos = [];
	$scope.condicionventas = [];
	$scope.proveedores = [];
	$scope.articuloMadreList = [];
	$scope.impuestosList = [];
	$scope.bloquearVistaCount = 0;
	$scope.bloquearVista = function() {
		if ($scope.bloquearVistaCount == 0) {
			bloquearVista("Buscando ...", null);
		}
		$scope.bloquearVistaCount++;
	}
	$scope.desbloquearVista = function() {
		$scope.bloquearVistaCount--;
		if ($scope.bloquearVistaCount == 0) {
			desbloquearVista();
		}
	}

	$scope.init = function(modaldata) {
		if ($scope.$parent.formscope) {
			$scope.$parent.formscope.onshow = function(modaldata) {
				$scope.onshow(modaldata);
			};
			$scope.$parent.formscope.onsubmit = function(callback) {
				$scope.onsubmit(callback);
			};
		}
		FamiliaFactory.getFamilias({}, function(data) {
			desbloquearVista();
			if (data.success) {
				$scope.familias = data.list;
			}
		}, function(error) {
			desbloquearVista();
			$scope.error = error.data;
		});
		SubFamiliaFactory.getSubFamilias({}, function(data) {
			desbloquearVista();
			if (data.success) {
				$scope.subFamilias = data.list;
			}
		}, function(error) {
			desbloquearVista();
			$scope.error = error.data;
		});
		AgendadoFactory.getAgendadoProveedorAll({}, function(data) {
			desbloquearVista();
			if (data.success) {
				$scope.proveedores = data.list;
			}
		}, function(error) {
			desbloquearVista();
			$scope.error = error.data;
		});
		ArticuloFactory.getArticuloMadreAll({}, function(data) {
			desbloquearVista();
			if (data.success) {
				$scope.articuloMadreList = data.list;
			}
		}, function(error) {
			desbloquearVista();
			$scope.error = error.data;
		});

		ImpuestosFactory.getImpuestosCombo({}, function(data) {
			desbloquearVista();
			if (data.success) {
				$scope.impuestosList = data.list;
			}
		}, function(error) {
			desbloquearVista();
			$scope.error = error.data;
		});

		$scope.bloquearVista();
	}

	$scope.onshow = function(modaldata) {
		$scope.error = null;
		$scope.filter = {};
		if (modaldata.id != 0) {
			ArticuloFactory.findArticuloByKey({
				'id' : modaldata.id
			}, function(data) {
				desbloquearVista();
				if (data.success) {
					$scope.articulo = data.object;
				} else {
					$scope.error = data.msg;
					if (callback) {
						callback(false);
					}
				}
			}, function(error) {
				desbloquearVista();
				$scope.articulo.error = error.data;
			});

			// bloquearVista("Leyendo articulo...", null);
			// $scope.articulo = data.object;

		} else {
			$scope.articulo = {};
		}
	};

	$scope.onsubmit = function(callback) {
		bloquearVista("Guardando articulo...", null);
		ArticuloFactory.saveArticulo($scope.articulo, function(data) {
			desbloquearVista();
			if (data.success) {
				if (callback) {
					callback(true);
				}
			} else {
				$scope.error = data.msg;
				if (callback) {
					callback(false);
				}
			}
		}, function(error) {
			desbloquearVista();
			$scope.agendado.error = error.data;
		});
	};

	$scope.init();
}