"use strict";

/* Controllers */

var app = angular.module('ngprodusimpa.controllers');

app.controller('TransaccionController', ['$scope', '$rootScope', '$routeParams', '$location', '$http', '$timeout', 'SesionesControl', 'AgendadoFactory', 'ArticuloFactory', 'VendedorFactory',
	'CondicionVentaFactory', 'TransacFactory', 'FocusFactory', 'PedidoFactory', 'Upload', TransaccionController
]);

function TransaccionController($scope, $rootScope, $routeParams, $location, $http, $timeout, SesionesControl, AgendadoFactory, ArticuloFactory, VendedorFactory, CondicionVentaFactory, TransacFactory, FocusFactory, PedidoFactory, Upload) {
	$scope.agendado = {};
	$scope.modaldata = {};
	$scope.items = [];
	$scope.articulos = [];
	$scope.domiciliosDeEntrega = [];
	$scope.domiciliosDeEntregaAlternativo = [];
	$scope.condicionesDeVenta = [];
	$scope.error = null;
	$scope.articuloSearchText = "";
	$scope.articuloSearchItem = {};
	$scope.subtotal = "";
	$scope.error = null;
	$scope.message = null;
	$scope.warning = null;
	$scope.vendedores = [];
	$scope.newTran = {};
	$scope.newTran.fechaEntrega = "";
	$scope.newTran.ordenCompra = "";
	$scope.dateMin = "";
	$scope.checked = true;
	$scope.fileUploader = [];
	var pedidoFac = undefined;
	var pedidoFiles = undefined;
	var isOrdenCompraValid = false;
	var filesToDelete = [];





	$scope.init = function () {
		if ($scope.$parent.formscope) {
			$scope.$parent.formscope.onshow = function (modaldata) {
				$scope.onshow(modaldata);
			};
			$scope.$parent.formscope.onsubmit = function (callback) {
				$scope.onsubmit(callback);
			};
		}
		
		$scope.error = null;
		pedidoFac = PedidoFactory.getPedido();
		$scope.items = [];
		
		if (pedidoFac != undefined ) { 
			if (pedidoFac.pedido == undefined) {
				pedidoFac = undefined;
			}
		}
		
		if (pedidoFac != undefined) {
			var items = [];
			$scope.newTran.vendedor = pedidoFac.pedido.object.vendedor;
			$scope.newTran.nrDomicilioEnt = pedidoFac.pedido.object.nrDomicilioEnt;
			if ($scope.newTran.nrDomicilioEnt == 0) {
				$scope.newTran.nrDomicilioEnt = undefined;
			}
			$scope.newTran.benef = pedidoFac.pedido.object.nrDomicilioAlt;
			if ($scope.newTran.benef == 0) {
				$scope.newTran.benef = undefined;
			}
			$scope.newTran.condiciones = pedidoFac.pedido.object.condiciones;
			$scope.agendado.id = pedidoFac.pedido.object.idGente;
		
			items = JSON.parse(pedidoFac.pedido.object.itemsList);
			
			for (var i = 0; i < items.length; i++) {
				var articulo = JSON.parse(items[i].articulo);
				var item = {
						
						clave: $scope.items.length,
						
						articulo: articulo,
						precioUnitario: "0",
						precioACobrar: items[i].precio,
						bonificacion: items[i].bonif,
						subTotal: "0",
						cantidad: items[i].cant1,
					};
					$scope.items.push(item);
					$scope.updateTotal();
			}	
			
			
			if (pedidoFac.transacNr != undefined) {
				pedidoFiles = TransacFactory.getTransacFilesList({
					'id': pedidoFac.transacNr
				}, function (data) {
					if (data.success) {
						var files = data.object;
						for (var key in files) {
							  var file = {
										"name": files[key]
							  };
							  $scope.fileUploader.push(file);
							}
					}
				
				}, function (error) {
				});
				if (pedidoFac.proforma == true ) {
					$scope.newTran.ordenCompra = pedidoFac.transacNr;
				} 
				else {
					$scope.newTran.ordenCompra =  parseInt(pedidoFac.pedido.object.ordenCompra);
					if ($scope.newTran.ordenCompra == "" ||  isNaN($scope.newTran.ordenCompra)) {
						$scope.checked = false;
					}
				}
				var fecha = pedidoFac.pedido.object.fechaEntrega;
				$scope.newTran.fechaEntrega = new Date(fecha);
			}
			$scope.newTran.observaciones = pedidoFac.pedido.object.observaciones;
			FocusFactory("cant-0");

		}
		else {
			$scope.addItem();
			FocusFactory("search-0");
		}
		
		AgendadoFactory.getAgendado({
			'id': $routeParams.agendadoId || 0
		}, function (data) {
			if (data.success) {
				$scope.agendado = data.object;
				$scope.newTran.vendedor = $scope.agendado.vendedorId;
				$scope.newTran.condiciones = $scope.agendado.condicionVenta;
			}
		}, function (error) {
			$scope.error = error.data;
		});

		CondicionVentaFactory.getCondicionVentas({
			'id': $routeParams.agendadoId || 0
		}, function (data) {
			if (data.success) {
				$scope.condicionesDeVenta = data.list;
			}
		}, function (error) {
			$scope.error = error.data;
		});

		AgendadoFactory.getAgendadoDomicilioByAlternativo({
			'id': $routeParams.agendadoId || 0,
			'type': 2,
			'alternativo': false
		}, function (data) {
			if (data.success) {
				$scope.domiciliosDeEntrega = data.list;
			}
		}, function (error) {
			$scope.error = error.data;
		});

		AgendadoFactory.getAgendadoDomicilioByAlternativo({
			'id': $routeParams.agendadoId || 0,
			'type': 2,
			'alternativo': true
		}, function (data) {
			if (data.success) {
				$scope.domiciliosDeEntregaAlternativo = data.list;
			}
		}, function (error) {
			$scope.error = error.data;
		});


		var tipoComprobante = $routeParams.tipoComprobante;
		TransacFactory.getTipoComprobante({
			'id': tipoComprobante || 0
		}, function (data) {
			if (data.success) {
				$scope.tipoComprobante = data.object;
			}
		}, function (error) {
			$scope.error = error.data;
		});

	};
	
	$scope.checkOrdenCom = function() {
		var ordenTransac = {
				'idOC' : $scope.newTran.ordenCompra,
				'idAgendado' :  $scope.agendado.id
		};
		TransacFactory.getOrdenCompraRepetida(ordenTransac, function (data) {
			$scope.message = null;
			isOrdenCompraValid = data.$resolved;
			if (data.isOrdenCompraValid == true) {
				$scope.message = "El número de Orden de Compra ya existe";
			}
		}, function (error) {
			isOrdenCompraValid = false;
			$scope.message = "Error al validar el número de Orden de Compra";
		});	
	}
	
	$scope.hideAlerts = function () {
		$scope.error = null;
		$scope.message = null;
		$scope.warning = null;
	}

	$scope.formSubmitTransac = function (form) {
		if (form.$valid && isOrdenCompraValid == false) {
			// Build Items
			var items = [];
			for (var i = 0; i < $scope.items.length; i++) {
				var item = {
					"cant1": $scope.items[i].cantidad,
					"precio": $scope.items[i].precioACobrar,
					"bonif": $scope.items[i].bonificacion,
					"clave": $scope.items[i].articulo.id
				};
				if ($scope.items[i].articulo.id == undefined || $scope.items[i].articulo.id == '') {

				} else {
					items.push(item); 
				}

			};
			if (items.length > 0) {
				// Build Transac
				var fecha = "";
				var day = $scope.newTran.fechaEntrega.getDate();
				var month = $scope.newTran.fechaEntrega.getMonth() + 1; // Hay
																		// que
																		// sumar
																		// 1
																		// porque
																		// el
																		// string
																		// epieza
																		// de 0
				var year = $scope.newTran.fechaEntrega.getFullYear();
				if (day < 10) {
					day = "0"+day;
				}
				if (month < 10) {
					month = "0" + month;
				}
				fecha = day + "" + month + "" + year;
				if (pedidoFac == undefined){
					pedidoFac = {
							"transacNr": undefined
					}
				}
				if (!$scope.checked) {
					$scope.newTran.ordenCompra = undefined;
				}
				
				var transac = {
					"vendedor": $scope.newTran.vendedor,
					"nrDomicilioEnt": $scope.newTran.nrDomicilioEnt,
					"benef": $scope.newTran.benef,
					"condiciones": $scope.newTran.condiciones,
					"gente": {
						"id": $scope.agendado.id
					},
					"tipoComprob": {
						"nr": $scope.tipoComprobante.nr
					},
					"itemsList": items,
					"fechaTemp": fecha,
					"ordenCompra": $scope.newTran.ordenCompra,
					"observaciones": $scope.newTran.observaciones,
					"transacNr": pedidoFac.transacNr
				};
				
				bloquearVista("Guardando transac...", null);
				if (pedidoFac.transacNr != undefined && pedidoFac.proforma != true){
					for (var i = $scope.fileUploader.length -1; i >= 0; i--) {
						  if ($scope.fileUploader[i].size == undefined) {
							  $scope.fileUploader.splice(i, 1);
						  }
						}
					let upload = Upload.upload({
					      url: '/cipres/rest/transac/editTransac',
					      data: {transac: JSON.stringify(transac), file: $scope.fileUploader, filesToDelete: JSON.stringify(filesToDelete)},
					      objectKey: '',
					    });
					upload.then(function (data) {
						data = data.data;
						if (data.success) {
							$scope.message = "Se ha editado el comprobante de tipo " + $scope.tipoComprobante.descripcion + ", con numero de transacción: " + data.object;
							$scope.clearPedido();
							if ($scope.tipoComprobante.nr === 8) {
								$scope.preparedEdit(data.object);
							}
						} else {
							$scope.message = null;
							$scope.error = "Se ha producido el siguiente error:" + data.msg;
						}
						desbloquearVista();
				    }
					, function (error) {
						desbloquearVista();
					});
				}
				else {
					let upload = Upload.upload({
					      url: '/cipres/rest/transac/saveTransac',
					      data: {transac: JSON.stringify(transac), file: $scope.fileUploader},
					      objectKey: '',
					    });
					upload.then(function (data) {
						data = data.data;
						if (data.success) {
							$scope.message = "Se ha generado el comprobante de tipo " + $scope.tipoComprobante.descripcion + ", con numero de transacción: " + data.object;	
							if (data.msg != "Ok") {
								$scope.warning = data.msg;
							} 
							$scope.clearPedido();
							if ($scope.tipoComprobante.nr === 8) {
								$scope.preparedEdit(data.object);
							}
							$scope.error = null;
						} else {
							$scope.warning = null;
							$scope.message = null;
							$scope.error = "Se ha producido el siguiente error:" + data.msg;
						}
						desbloquearVista();
					}
					, function (error) {
						desbloquearVista();
					});
					if (pedidoFac.proforma == true ){
						TransacFactory.cancelTransac({
							'id': pedidoFac.transacNr
						}, function (data) {
							if (data.success) {
								console.log("Se eliminó la proforma correctamente");
							}
						}, function (error) {
							$scope.error = error.data;
						});
					}
				}
			} else {
				$scope.error = "Al menos debe agregar un item al comprobante";
			}
		}
	};
	
	$scope.clearPedido = function () {
		isOrdenCompraValid = false;
		$scope.items = [];
		pedidoFac = undefined;
		$scope.newTran.nrDomicilioEnt = undefined;
		$scope.newTran.benef = undefined;
		$scope.subtotal = 0;
		$scope.subtotalImpuestos = 0;
		$scope.total = 0;
		$scope.newTran.fechaEntrega = undefined;
		$scope.newTran.observaciones = undefined;
		$scope.newTran.ordenCompra = undefined;
		$scope.error = null;
		$scope.addItem();
		$scope.fileUploader = [];

		$timeout(function () {
			FocusFactory("search-0");
		});
	}
	
	$scope.deleteFile = function (index) {
		if ( $scope.fileUploader[index].size === undefined) {
			filesToDelete.push($scope.fileUploader[index].name);
		}
		$scope.fileUploader.splice(index, 1);
		
	}
	$scope.preparedEdit = function (transacNr) {
		bloquearVista("Buscando comprobante...", null);

		TransacFactory.getPdfComprobante({
			'id': transacNr
		}, function (data) {
			if (data.success) {
				$scope.pdf = data.object;

				$scope.title = "Comprobante PDF";
				$scope.url = "partials/comprobantes/comprobante_pdf.html";
				$scope.ok_confirm = function () {};
				$rootScope.openModalConfirm($scope);
				setTimeout(function () {
					$("#comprobantePDF").attr("src", "data:application/pdf;base64," + $scope.pdf);
					desbloquearVista();
				}, 1000);
			}else{
				$scope.error = "Se ha producido el siguiente error: " + data.msg;
				desbloquearVista();
			}
		}, function (error) {
			$scope.error = error.data;
			desbloquearVista();
		});
	}

	$scope.setMin = function () {
		var dateToday = new Date;
		var year = dateToday.getFullYear();
		var month = dateToday.getMonth() + 1;
		var day = dateToday.getDate();
		if (day < 10) {
			day = "0" + day;
		}
		if (month < 10) {
			month = "0" + month;
		}
		$scope.dateMin = year + "-" + month + "-" + day;
	}

	$scope.addItemFromLastItem = function (lastItemIndex) {
		var item = {
			clave: $scope.items.length,
			articulo: {},
			precioUnitario: "0",
			precioACobrar: "0",
			bonificacion: 0,
			subTotal: "0",
		};
		$scope.items.push(item);
		$scope.searchArticulo(item, lastItemIndex + 1);
	};


	$scope.addItem = function () {
		$scope.items.push({
			clave: $scope.items.length,
			articulo: {},
			precioUnitario: "0",
			precioACobrar: "0",
			bonificacion: 0,
			subTotal: "0",
		});
		setTimeout(function () {
			// $('#search-0').focus();
		}, 300);
		// $("#modalSelectArticulo").modal("show");

	};

	$scope.removeItem = function (item) {
		if ($scope.items.length > 1) {
			var index = $scope.items.indexOf(item);
			if (index >= 0) {
				$scope.items.splice(index, 1);
				$scope.updateTotal();
			}
		}
	};



	$scope.updateSearchArticulo = function () {
		bloquearVista("Buscando ...", null);
		$scope.articulos = [];
		ArticuloFactory.findArticulo({
			busqueda: {
				nombreNumero: $scope.articuloSearchText,
				"genteId": $scope.agendado.id
			},
			"pageNumber": 1,
			"pageSize": 50,
		}, function (data) {
			if (data.success) {
				if (!data.list || data.list.length < 1) {
					$scope.error = "No se han encontrado artículos";
				} else {
					$scope.articulos = data.list;
					for (var i = 0; i < $scope.articulos.length; i++) {
						if ($scope.articulos[i].fechaVencimientoMasProxima != null) {
							var date = new Date($scope.articulos[i].fechaVencimientoMasProxima);
							date.setDate(date.getDate() + 1);
							$scope.articulos[i].fechaVencimientoMasProxima = date;
						}
					}

				}
			}
			desbloquearVista();
		}, function (error) {
			desbloquearVista();
		});
	};

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


	$scope.searchArticulo = function (item, index) {
		$scope.articuloSearchItem = item;
		// $("#modalPedidoDeVenta").hide();
		$scope.itemselectedtemp = index;
		$("#modalSelectArticulo").modal("show");
		angular.element('#searchByCodeName').focus();
	};

	$scope.selectSeachArticulo = function (articulo) {
		$scope.articuloSearchItem.articulo = articulo;
		$("#modalPedidoDeVenta").show();
		$("#modalSelectArticulo").modal("hide");
		var init = true;
		setTimeout(function () {
			$('#cant-' + $scope.itemselectedtemp).focus();
		}, 300);
		$("#searchByCodeName").val("");
		$scope.articulos = [];
		if (articulo.stock <= 0) {
			$scope.warning = "El artículo: '" + articulo.descripcion + "', no posee stock disponible";
		}
	};

	$scope.closeSearchArticulo = function () {
		$scope.articulos = [];
		$scope.articuloSearchText = "";
	};

	$scope.precioACobrarChanged = function (item) {
		var total = parseFloat(item.precioUnitario);
		var aCobrar = parseFloat(item.precioACobrar);
		var descuento = ((aCobrar * 100) / total);
		var bonificacion = 100 - descuento;

		item.bonificacion =  parseFloat(bonificacion).toFixed(2);
		$scope.updateTotal();
	}
	$scope.bonificacionChanged = function (item) {
		var total = parseFloat(item.precioUnitario);
		var bonificacion = parseFloat(item.bonificacion);
		var descuento = (total * bonificacion / 100);
		var aCobrar = total - descuento;

		item.precioACobrar = parseFloat(aCobrar).toFixed(2);
		$scope.updateTotal();
	}

	VendedorFactory.getVendedores({}, function (data) {

		if (data.success) {
			$scope.vendedores = data.list;
		}
	}, function (error) {

		$scope.error = error.data;
	});

	

	$scope.updateTotal = function (init) {
		$scope.subtotal = 0;
		$scope.subtotalImpuestos = 0;
		$scope.total = 0;
		for (var i = 0; i < $scope.items.length; i++) {
			if ($scope.items[i].cantidad > $scope.items[i].articulo.stock && $scope.items[i].articulo.stock != 0) {
				$scope.warning = "La cantidad seleccionada del artículo: '" + $scope.items[i].articulo.descripcion + "', superó el stock disponible";
			}
			if ($scope.items[i].articulo.id) {
				var cantidad = parseInt($scope.items[i].cantidad, 10);
				if (isNaN(cantidad)) {
					cantidad = 0;
				}
				$scope.items[i].cantidad = cantidad;
				var unidad = $scope.items[i].articulo["precio1"];

				$scope.items[i].precioUnitario = unidad.toFixed(2);

				if (init || $scope.items[i].precioACobrar == "0") {
					$scope.items[i].precioACobrar = unidad.toFixed(2);
				}

				var subtotal = $scope.items[i].precioACobrar * $scope.items[i].cantidad;

				$scope.items[i].porcentajeIva = $scope.items[i].articulo.impuestos.alicuota;
				var subtotalImpuesto = 0;
				if ($scope.items[i].porcentajeIva > 0) {
					var subtotalImpuesto = ($scope.items[i].precioACobrar * $scope.items[i].cantidad) * $scope.items[i].porcentajeIva / 100;
				}

				$scope.items[i].subTotal = subtotal.toFixed(2);

				$scope.subtotal += subtotal;

				$scope.subtotalImpuestos += subtotalImpuesto;


			}
		}
		$scope.subtotal = $scope.subtotal.toFixed(2);
		$scope.subtotalImpuestos = $scope.subtotalImpuestos.toFixed(2);
		var total = parseFloat($scope.subtotal) + parseFloat($scope.subtotalImpuestos);
		$scope.total = parseFloat(total).toFixed(2);

	};

	$scope.init();
}

"use strict";

/* Controllers */

var app = angular.module('ngprodusimpa.controllers');

app.controller('TransaccionesListController', ['$scope', '$rootScope', '$routeParams', '$location', '$http', '$timeout', 'SesionesControl', 'AgendadoFactory', 'TransacFactory', '$route', 'PedidoFactory',
	TransaccionesListController
]);

function TransaccionesListController($scope, $rootScope, $routeParams, $location, $http, $timeout, SesionesControl, AgendadoFactory, TransacFactory, $route, PedidoFactory) {
	$scope.agendado = {};
	$scope.agendadoEdit = {};
	$scope.comprobantePDF = {};
	$scope.transacciones = [];
	$scope.transaccion = {};
	$scope.tiposDeComprobante = [];
	// $scope.transaccionEdit = {};

	$scope.pageNumber = 1;
	$scope.total_items = 0;
	$scope.defaultListSize = 20;
	$scope.stopFindItemInit = true;
	$scope.stopFindItemTemp = false;
	$scope.stopFindItem = false;
	$scope.error = null;
	$scope.message = null;

	$scope.isAgendado = $route.current.$$route.originalPath.includes("agendado");
	$scope.isArticulo = $route.current.$$route.originalPath.includes("articulo");
	$scope.isDomicilio = $route.current.$$route.originalPath.includes("domicilio");
	$scope.articuloId = $routeParams.articuloId;
	$scope.domicilioId = $routeParams.domicilioId;
	$scope.vista = $routeParams.vista;
	$scope.nRTranToCancel = undefined;
	$scope.adjuntos = [];
	$scope.transacNr = undefined;

	$scope.tiposDeDomicilios = [{
		"codigo": -1,
		"descrip": "Todos"
	}, {
		"codigo": 1,
		"descrip": "Principal"
	}, {
		"codigo": 2,
		"descrip": "Entrega"
	}, {
		"codigo": 3,
		"descrip": "Cobranza"
	}, {
		"codigo": 4,
		"descrip": "Mailing"
	}];
	
	$scope.estados = [{
		"codigo": 1,
		"descrip": "Pendiente"
	}, {
		"codigo": 2,
		"descrip": "Completado"
	}];
	$scope.busqueda = {
		texto: "",
		tipoComprobante: 0,
		estado: 0,
	};
	AgendadoFactory.getAgendado({
		'id': $routeParams.agendadoId || 0
	}, function (data) {
		if (data.success) {
			$scope.agendado = data.object;
			$scope.searchTransacciones('init');
		}
	}, function (error) {
		$scope.error = error.data;
	});



	TransacFactory.getTiposComprobante({}, function (data) {
		// $scope.desbloquearVista();
		if (data.success) {
			$scope.tiposDeComprobante = data.list;
		}
	}, function (error) {
		// $scope.desbloquearVista();
		$scope.error = error.data;
	});

	$scope.newTransaccion = function () {
		// $scope.transaccionEdit.show(function modalOk() {
		// // $scope.searchDomicilios('init');
		// });
	};
	
	$scope.hideAlerts = function () {
		$scope.error = null;
		$scope.message = null;
	}

	$scope.clonarPedido = function (transacNr) {
		bloquearVista("Obteniendo datos...", null);
		TransacFactory.getComprobante({
			'id': transacNr	
		}, function (data) {
			if (data.success) {
				PedidoFactory.setPedido({
					"pedido": data
				});
				desbloquearVista();
				$location.path("/agendado/" + $scope.agendado.id + "/" + "8/" + "nueva_transaccion");
			}
		}, function (error) {
			$scope.error = error.data;
			desbloquearVista();
		});
	}
	
	$scope.proformaToPedido = function (transacNr) {
		bloquearVista("Obteniendo datos...", null);
		TransacFactory.getComprobante({
			'id': transacNr	
		}, function (data) {
			if (data.success) {
				PedidoFactory.setPedido({
					"pedido": data,
					"transacNr": transacNr,
					"proforma": true
				});
				desbloquearVista();
				$location.path("/agendado/" + $scope.agendado.id + "/" + "8/" + "nueva_transaccion");
			}
		}, function (error) {
			$scope.error = error.data;
			desbloquearVista();
		});
	}

	$scope.editarPedido = function (transacNr) {
		bloquearVista("Obteniendo datos...", null);
		TransacFactory.getComprobante({
			'id': transacNr
		}, function (data) {
			if (data.success) {
				PedidoFactory.setPedido({
					"pedido": data,
					"transacNr": transacNr
				});
				desbloquearVista();
				$location.path("/agendado/" + $scope.agendado.id + "/" + "8/" + "nueva_transaccion");
			}
		}, function (error) {
			$scope.error = error.data;
			desbloquearVista();
		});
	}

	$scope.cancelarPedido = function () {
		TransacFactory.cancelTransac({
			'id': $scope.nRTranToCancel
		}, function (data) {
			if (data.success) {
				$scope.transacciones = $scope.transacciones.filter(function(transac) {
					return transac.transacNr !== $scope.nRTranToCancel;
					
				});
				$scope.nRTranToCancel = undefined;
				$("#confirmCancel").modal("hide");
				$scope.message = "Anulado con éxito.";
				
			}
		}, function (error) {
			$scope.error = error.data;
		});
	}

	$scope.openConfirmCancelModal = function (transac) {
		$scope.nRTranToCancel = transac;
		$("#confirmCancel").modal("show");
	}
	
	$scope.openAdjuntoModal = function (transac) {
		$scope.transacNr = transac;
		TransacFactory.getTransacFilesList({
			'id': transac
		}, function (data) {
			if (data.success) {
				$scope.adjuntos = data.object;
			} else {
				$scope.adjuntos = undefined;
			}
		
		}, function (error) {
		});
		$("#adjuntosModal").modal("show");
	}

	$scope.preparedEdit = function (transacNr) {
		bloquearVista("Buscando comprobante...", null);

		TransacFactory.getPdfComprobante({
			'id': transacNr
		}, function (data) {
			if (data.success) {
				$scope.pdf = data.object;

				$scope.title = "Comprobante PDF";
				$scope.url = "partials/comprobantes/comprobante_pdf.html";
				$scope.ok_confirm = function () {};
				$rootScope.openModalConfirm($scope);
				setTimeout(function () {
					$("#comprobantePDF").attr("src", "data:application/pdf;base64," + $scope.pdf);
					desbloquearVista();
				}, 5000);
			}else{
				$scope.error = "Se ha producido el siguiente error: " + data.msg;
				desbloquearVista();
			}
		}, function (error) {
			$scope.error = error.data;
			desbloquearVista();
		});
	}

	$scope.preparedEditAgendado = function (id) {
		$scope.agendado.id = id;
		$scope.title = "Edición de Agendado";
		$scope.url = "partials/agendados/agendado_edit.html";
		$scope.ok_confirm = function () {
			for (var i = 0; i < $scope.agendados.length; i++) {
				if ($scope.agendados[i].id == $scope.agendado.id) {
					$scope.agendados[i] = $scope.agendado.data;
					break;
				}
			}
		};
		$rootScope.openModalConfirm($scope);
	}

	$scope.get_items = function () {
		if ($scope.stopFindItemTemp == true) {
			return;
		}
		$scope.stopFindItemTemp = true;
		if (!$scope.stopFindItemInit) {
			$scope.pageNumber = Math.ceil($scope.transacciones.length / $scope.defaultListSize) + 1;
			$scope.searchTransacciones('paging');
		}
	}

	$scope.searchTransacciones = function (status) {
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
			if ($route.current.$$route.originalPath.includes("agendado")) {
				AgendadoFactory.getAgendadoTransacciones({
					"genteId": $scope.agendado.id,
					"busqueda": $scope.busqueda,
					"pageNumber": $scope.pageNumber,
					"pageSize": $scope.defaultListSize,
				}, function (data) {
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
							if ($scope.total_items <= $scope.transacciones.length) {
								$scope.stopFindItem = true;
							}
						}
					}
					desbloquearVista();
				}, function (error) {
					$scope.error = error.data;
					desbloquearVista();
					$scope.stopFindItemTemp = false;
				});
			} else if ($route.current.$$route.originalPath.includes("articulo")) {
				TransacFactory.getArticuloTransacciones({
					"articuloId": $routeParams.articuloId,
					"busqueda": $scope.busqueda,
					"pageNumber": $scope.pageNumber,
					"pageSize": $scope.defaultListSize,
				}, function (data) {
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
							if ($scope.total_items <= $scope.transacciones.length) {
								$scope.stopFindItem = true;
							}
						}
					}
					desbloquearVista();
				}, function (error) {
					$scope.error = error.data;
					desbloquearVista();
					$scope.stopFindItemTemp = false;
				});
			} else if ($route.current.$$route.originalPath.includes("domicilio")) {
				if ($scope.vista == 'crm') {
					TransacFactory.getDomiciliosTransaccionesAgrupados({
						"domicilioId": $routeParams.domicilioId,
						"busqueda": $scope.busqueda,
						"pageNumber": $scope.pageNumber,
						"pageSize": $scope.defaultListSize,
					}, function (data) {
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
								if ($scope.total_items <= $scope.transacciones.length) {
									$scope.stopFindItem = true;
								}
							}
						}
						desbloquearVista();
					}, function (error) {
						$scope.error = error.data;
						desbloquearVista();
						$scope.stopFindItemTemp = false;
					});

				} else {
					TransacFactory.getDomicilioTransacciones({
						"domicilioId": $routeParams.domicilioId,
						"busqueda": $scope.busqueda,
						"pageNumber": $scope.pageNumber,
						"pageSize": $scope.defaultListSize,
					}, function (data) {
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
								if ($scope.total_items <= $scope.transacciones.length) {
									$scope.stopFindItem = true;
								}
							}
						}
						desbloquearVista();
					}, function (error) {
						$scope.error = error.data;
						desbloquearVista();
						$scope.stopFindItemTemp = false;
					});
				}
			}
		}
	};

	// Sort Menu
	$(document).scroll(function () {
		var y = $(this).scrollTop();
		if (y > 100) {
			$('.counter').addClass("counter-scroll");
		} else {
			$('.counter').removeClass("counter-scroll");
		}

	});
}

app.controller('TransaccionEditController', ['$scope', '$rootScope', '$routeParams', '$location', '$http', '$timeout', 'SesionesControl', 'ProvinciaFactory', 'TransporteFactory', 'AgendadoFactory',
	'TransacFactory', TransaccionEditController
]);

function TransaccionEditController($scope, $rootScope, $routeParams, $location, $http, $timeout, SesionesControl, ProvinciaFactory, TransporteFactory, AgendadoFactory, TransacFactory) {
	$scope.error = null;
	$scope.tiposDeComprobante = [];
	$scope.modaldata = {}; // Se guarda la informacion para devolver a la lista
	// las modificaciones y no tener que recargarla
	$scope.newTran = {};

	$scope.bloquearVistaCount = 0;
	$scope.bloquearVista = function () {
		if ($scope.bloquearVistaCount == 0) {
			bloquearVista("Buscando ...", null);
		}
		$scope.bloquearVistaCount++;
	}
	$scope.desbloquearVista = function () {
		$scope.bloquearVistaCount--;
		if ($scope.bloquearVistaCount == 0) {
			desbloquearVista();
		}
	}

	$scope.init = function (modaldata) {
		if ($scope.$parent.formscope) {
			$scope.$parent.formscope.onshow = function (modaldata) {
				$scope.onshow(modaldata);
			};
			$scope.$parent.formscope.onsubmit = function (callback) {
				$scope.onsubmit(callback);
			};
		}

		$scope.bloquearVista();
	}

	$scope.onshow = function (modaldata) {
		$scope.error = null;
		$scope.filter = {};
		$scope.modaldata = modaldata;

		TransacFactory.getTiposComprobante({}, function (data) {
			$scope.desbloquearVista();
			if (data.success) {
				$scope.tiposDeComprobante = data.list;
			}
		}, function (error) {
			$scope.desbloquearVista();
			$scope.error = error.data;
		});

		AgendadoFactory.getAgendado({
			'id': $routeParams.agendadoId || 0
		}, function (data) {
			if (data.success) {
				$scope.newTran['gente'] = data.object;
			}
		}, function (error) {
			$scope.error = error.data;
		});
	};

	$scope.onsubmit = function (callback) {
		// Build Items
		var items = [];
		for (var i = 0; i < $scope.items.length; i++) {
			var item = {
				"cant1": $scope.items[i].cantidad,
				"precio": $scope.items[i].precioACobrar,
				"bonif": $scope.items[i].bonificacion,
				"clave": $scope.items[i].articulo.id
			};
			items.push(item);
		};

		// Build Transac
		var transac = {
			"gente": {
				"id": $scope.agendado.id
			},
			"tipoComprob": {
				"nr": $scope.tipoComprobante.nr
			},
			"itemsList": items
		};
		// bloquearVista("Guardando domicilio...", null);

	};



	$scope.init();
}