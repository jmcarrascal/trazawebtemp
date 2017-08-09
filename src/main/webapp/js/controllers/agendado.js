"use strict";

/* Controllers */

var app = angular.module('ngprodusimpa.controllers');

app.controller('AgendadoController', ['$scope', '$rootScope', '$routeParams', '$location', '$http', '$timeout', 'SesionesControl', 'AgendadoFactory', 'VendedorFactory',
                                      'RelacionFactory', 'PlanFactory', 'ProvinciaFactory', 'ZonaFactory', 'IvaCodigoFactory', 'CondicionVentaFactory', 'TransporteFactory', 'PedidoFactory',
                                      AgendadoController]);    
function AgendadoController($scope, $rootScope, $routeParams, $location, $http, $timeout, SesionesControl, AgendadoFactory, VendedorFactory, RelacionFactory, PlanFactory, 
		ProvinciaFactory, ZonaFactory, IvaCodigoFactory, CondicionVentaFactory, TransporteFactory, PedidoFactory) {
	
	
	$scope.agendados = [];
	$scope.agendado = {};
	$scope.pedidodeventa = {};

	$scope.pageNumber = 1;
	$scope.total_items = 0;
	$scope.defaultListSize = 10;
	$scope.stopFindItemInit = true;
	$scope.stopFindItemTemp = false;
	$scope.stopFindItem = false;
	$scope.title = "...";
	
	$scope.busqueda = {};
	RelacionFactory.getRelaciones({}, function(data) {
		if (data.success) {
			$scope.relaciones = data.list;
			$scope.relaciones.splice(0, 0, { "codigo": "0", "descrip": "Todos" });
			if ($location.path() === "/agendado/clientes") {
				$scope.busqueda.relacionId = "1";
				$scope.title = "Clientes";
			} else if ($location.path() === "/agendado/proveedores") {
				$scope.busqueda.relacionId = "2";
				$scope.title = "Proveedores";
			} 
			
		}
	}, function(error) {
		$scope.error = error.data;
	});
	$scope.newAgendado = function() {
		$scope.agendado.id = 0;
		$scope.agendado.show();
	};

	$scope.preparedEdit = function(id) {
		$scope.agendado.id = id;
		$scope.agendado.show(function modalOk() {
			for (var i = 0; i < $scope.agendados.length; i++) {
				if ($scope.agendados[i].id == $scope.agendado.id) {
					$scope.agendados[i] = $scope.agendado.data;
					break;
				}
			}
		});
	}

	$scope.get_items = function(){
		if ($scope.stopFindItemTemp == true) {
			return;
		}
		$scope.stopFindItemTemp = true;
		if (!$scope.stopFindItemInit) {
			$scope.pageNumber = Math.ceil($scope.agendados.length / $scope.defaultListSize) + 1;
			$scope.searchAgendado('paging');
		}
	}
	$scope.dataSaldo = {};
	
	$scope.searchAgendado = function(status) {
		if (status == 'init') {
			$scope.agendados = [];
			$scope.pageNumber = 1;
			$scope.stopFindItem = false;
		}
		$scope.stopFindItemInit = false;
		$scope.stopFindItemTemp = true;
		$scope.message = null;
		bloquearVista("Buscando agendado...", null);

		if ($scope.stopFindItem) {
			desbloquearVista();
		}
		else {
			$rootScope.filterAgendado = { 
					"busqueda" : $scope.busqueda,
					"pageNumber": $scope.pageNumber,
					"pageSize": $scope.defaultListSize,
				} 
			AgendadoFactory.findAgendado($rootScope.filterAgendado, function(data) {			
				if (data.success) {
					if (!data.list || data.list.length < 1) {
						$scope.agendados = [];
						$scope.message = "No se han encontrado agendados con los valores de búsqueda cargados...";
						$scope.stopFindItemTemp = false;
					}
					else {
						if ($scope.agendados.length === 0) {
							$scope.agendados = data.list;							
							for (var i = 0; i < $scope.agendados.length; i++) {
								var agendadoTmp = $scope.agendados[i];
								$scope.dataSaldo[agendadoTmp['id']] = [agendadoTmp['saldo'], (agendadoTmp.creditoCuenta + agendadoTmp.creditoCheques) - agendadoTmp['saldo']];								 
							}							
						}
						else {
							$scope.agendados = $scope.agendados.concat(data.list);
							for (var i = 0; i < $scope.agendados.length; i++) {
								var agendadoTmp = $scope.agendados[i];
								$scope.dataSaldo[agendadoTmp['id']] = [agendadoTmp['saldo'], (agendadoTmp.creditoCuenta + agendadoTmp.creditoCheques) - agendadoTmp['saldo']];								 
							}
							

						}
						$scope.total_items = data.total_items;
						$scope.stopFindItemTemp = false;
						if ($scope.total_items <= $scope.agendados.length) {
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
	
	$scope.getAgendadoSaldo = function(agendado) {
		var saldo = (agendado.creditoCuenta + agendado.creditoCheques) * 100 / 1000000;
		saldo = saldo < 0 ? 0 : saldo > 100 ? 100 : saldo;
		return saldo;
	};

	$scope.showDomicilios = function(agendado) {
		window.location = "#/agendado/" + agendado.id + "/domicilios";
	};
	
	$scope.showDomiciliosIncompletos = function(agendado) {
		window.location = "#/agendado/-500/domicilios";
	};

	$scope.showTransacciones = function(agendado) {
		window.location = "#/agendado/" + agendado.id + "/transacciones";
	};
	
	$scope.showPedidoDeVenta = function(agendado) {
		$scope.clearPedido();
		$location.path("/agendado/" + agendado.id + "/" +"8/"+"nueva_transaccion");
	};
	
	$scope.showProforma = function(agendado) {
		$scope.clearPedido();
		$location.path("/agendado/" + agendado.id + "/" +"16/"+"nueva_transaccion");
	};
	
	$scope.clearPedido = function () {
		PedidoFactory.setPedido({
			"pedido": undefined
		});
	}
	
	
	$scope.showComprobante = function(agendado) {
		window.location = "#/agendado/" + agendado.id + "/transacciones";
	};
	
	$scope.labelsSaldo = ["Saldo", "Credito"];
	
	
	if ($rootScope.filterAgendado != undefined){
		$scope.busqueda = $rootScope.filterAgendado.busqueda;
		$scope.pageNumber = $rootScope.filterAgendado.pageNumber;
		$scope.defaultListSize = $rootScope.filterAgendado.pageSize;
		$scope.searchAgendado('');
	}
	
	
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

app.controller('AgendadoEditController', ['$scope', '$rootScope', '$routeParams', '$location', '$http', '$timeout', 'SesionesControl', 'AgendadoFactory', 'VendedorFactory',
                                          'RelacionFactory', 'PlanFactory', 'ProvinciaFactory', 'ZonaFactory', 'IvaCodigoFactory', 'CondicionVentaFactory',
                                          AgendadoEditController]);
function AgendadoEditController($scope, $rootScope, $routeParams, $location, $http, $timeout, SesionesControl, AgendadoFactory, VendedorFactory, RelacionFactory, PlanFactory, 
		ProvinciaFactory, ZonaFactory, IvaCodigoFactory, CondicionVentaFactory) {
	$scope.booleanValues = [{ "codigo" : 0, "descrip" : "No" }, { "codigo" : -1, "descrip" : "Si" }];
	$scope.percibeIngresosBrutos = [{ "codigo" : 0, "descrip" : "No" }, { "codigo" : -1, "descrip" : "Multilateral General" }, { "codigo" : -2, "descrip" : "Local SALTA" }, 
	                                { "codigo" : -3, "descrip" : "Multilat y SALTA" }];
	$scope.vencimientoDias = [{ "codigo" : 0, "descrip" : "0" }, { "codigo" : 1, "descrip" : "1" }, { "codigo" : 7, "descrip" : "7" }, { "codigo" : 15, "descrip" : "15" }, 
	                          { "codigo" : 30, "descrip" : "30" }, { "codigo" : 45, "descrip" : "45" }, { "codigo" : 60, "descrip" : "60" }, { "codigo" : 120, "descrip" : "120" }];
	$scope.listaDePrecios = [{ "codigo" : 1, "descrip" : "Precio Vta 1" }, { "codigo" : 2, "descrip" : "Precio Vta 2" }, { "codigo" : 3, "descrip" : "Precio Vta 3" }, 
	                         { "codigo" : 4, "descrip" : "Precio Vta 4" }, { "codigo" : 5, "descrip" : "Precio Vta 5" }, { "codigo" : 6, "descrip" : "Precio Vta 6" },
	                         { "codigo" : 7, "descrip" : "Precio Compra" }];

	$scope.agendado = {};
	$scope.error = null;
	$scope.filter = {};
	$scope.vendedores = [];
	$scope.relaciones = [];
	$scope.planes = [];
	$scope.provincias = [];
	$scope.zonas = [];
	$scope.ivacodigos = [];
	$scope.condicionventas = [];
	$scope.modaldata = {};	// Se guarda la informacion para devolver a la lista las modificaciones y no tener que recargarla

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
		
		$scope.bloquearVista();

		VendedorFactory.getVendedores({}, function(data) {
			$scope.desbloquearVista();
			if (data.success) {
				$scope.vendedores = data.list;
			}
		}, function(error) {
			$scope.desbloquearVista();
			$scope.error = error.data;
		});
		RelacionFactory.getRelaciones({}, function(data) {
			$scope.desbloquearVista();
			if (data.success) {
				$scope.relaciones = data.list;
			}
		}, function(error) {
			$scope.desbloquearVista();
			$scope.error = error.data;
		});
		PlanFactory.getPlanes({}, function(data) {
			$scope.desbloquearVista();
			if (data.success) {
				$scope.planes = data.list;
				// borrar
//				$scope.planes = $scope.booleanValues;
			}
		}, function(error) {
			$scope.desbloquearVista();
			$scope.error = error.data;
		});
		ProvinciaFactory.getProvincias({}, function(data) {
			$scope.desbloquearVista();
			if (data.success) {
				$scope.provincias = data.list;
			}
		}, function(error) {
			$scope.desbloquearVista();
			$scope.error = error.data;
		});
		ZonaFactory.getZonas({}, function(data) {
			$scope.desbloquearVista();
			if (data.success) {
				$scope.zonas = data.list;
			}
		}, function(error) {
			$scope.desbloquearVista();
			$scope.error = error.data;
		});
		IvaCodigoFactory.getIvaCodigos({}, function(data) {
			$scope.desbloquearVista();
			if (data.success) {
				$scope.ivacodigos = data.list;
			}
		}, function(error) {
			$scope.desbloquearVista();
			$scope.error = error.data;
		});
		CondicionVentaFactory.getCondicionVentas({}, function(data) {
			$scope.desbloquearVista();
			if (data.success) {
				$scope.condicionventas = data.list;
			}
		}, function(error) {
			$scope.desbloquearVista();
			$scope.error = error.data;
		});
	}

	$scope.onshow = function(modaldata) {
		$scope.error = null;
		$scope.filter = {};
		$scope.modaldata = modaldata;
		
		if ($scope.modaldata.id != 0) {
			bloquearVista("Leyendo agendado...", null);
			AgendadoFactory.getAgendado({ 'id' : $scope.modaldata.id || 1 }, function(data) {
				$scope.agendado = data.object;
				desbloquearVista();
			}, function(error) {
				desbloquearVista();
				$scope.error = error.data;
			});
		}
		else {
			$scope.agendado = {};
		}
	};
	
	$scope.onsubmit = function(callback) {
		bloquearVista("Guardando agendado...", null);
		AgendadoFactory.saveAgendado($scope.agendado, 
			function(data) {
				$scope.modaldata.data = $scope.agendado;
				$scope.modaldata.data.descripC = "[" + $scope.agendado.id + "] " + $scope.agendado.razonSocial;
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
			}, 
			function(error) {
				desbloquearVista();
				$scope.agendado.error = error.data;
			});
	};
	
	
	
	$scope.init();
}

