"use strict";

/* Controllers */

var app = angular.module('ngprodusimpa.controllers');

app.controller('DomiciliosListController', ['$scope', '$rootScope', '$routeParams', '$location', '$http', '$timeout', 'SesionesControl', 'AgendadoFactory', DomiciliosListController]);    
function DomiciliosListController($scope, $rootScope, $routeParams, $location, $http, $timeout, SesionesControl, AgendadoFactory) {
	$scope.agendado = {};
	$scope.agendadoEdit = {};
	$scope.domicilios = [];
	$scope.domicilio = {};

	$scope.pageNumber = 1;
	$scope.total_items = 0;
	$scope.defaultListSize = 20;
	$scope.stopFindItemInit = true;
	$scope.stopFindItemTemp = false;
	$scope.stopFindItem = false;
	
	$scope.tiposDeDomicilios = [{ "codigo" : -1, "descrip" : "Todos" }, { "codigo" : 1, "descrip" : "Principal" }, { "codigo" : 2, "descrip" : "Entrega" }, 
	                            { "codigo" : 3, "descrip" : "Cobranza" }, { "codigo" : 4, "descrip" : "Mailing" }];
	$scope.busqueda = {
		texto: "",
		tipoDomicilio: -1,
	};	 
	var agendadoId = undefined;
	
	// A borrar
	if ($routeParams.agendadoId == -500) {
		agendadoId = 1;
	} else {
		agendadoId = $routeParams.agendadoId;
	}
	// hasta aca
	AgendadoFactory.getAgendado({ 'id' : agendadoId || 0 }, function(data) {
		if (data.success) {
			$scope.agendado = data.object;
			$scope.searchDomicilios('init');
			console.log("getAgendado: ", data);
			// a borrar
			if ($routeParams.agendadoId == -500) {
				console.log("Agendado: ", $scope.agendado);
				$scope.agendado.descripC = "Domicilios incompletos" 
			} 
			// hasta aca
		}
	}, function(error) {
		$scope.error = error.data;
	});
	
	
	if ($routeParams.agendadoId == -1){
		$scope.agendadoEmply = true;
	}else{
		$scope.agendadoEmply = false;
	}

	$scope.newDomicilio = function() {
		$scope.domicilio.id = 0;
		$scope.domicilio.genteId = $scope.agendado.id;
		$scope.domicilio.show(function modalOk() {
			$scope.searchDomicilios('init');
		});
	};

	$scope.showComprobanteAgrupados = function(domicilio) {
		console.log("Domicilio...");
		window.location = "#/domicilio/crm/" + domicilio.id + "/transacciones";
	};
	
	$scope.showComprobante = function(domicilio) {
		console.log("Domicilio...");
		window.location = "#/domicilio/oficial/" + domicilio.id + "/transacciones";
	};
	


	$scope.preparedEdit = function(id) {
		$scope.domicilio.id = id;
		$scope.domicilio.show(function modalOk() {
			for (var i = 0; i < $scope.domicilios.length; i++) {
				if ($scope.domicilios[i].id == $scope.domicilio.id) {
					$scope.domicilios[i] = $scope.domicilio.data;
					break;
				}
			}
		});		
		$('#_value').val("");
		setTimeout(function(){
			$('#_value').focus();
		}, 300);
		
		// A borrar
		$scope.searchDomicilios('init');
	}

	$scope.preparedEditAgendado = function() {
		$scope.agendadoEdit.id = $scope.agendado.id;
		$scope.agendadoEdit.show(function modalOk() {
			$scope.agendado = $scope.agendadoEdit.data;
		});
	}

	$scope.get_items = function(){
		if ($scope.stopFindItemTemp == true) {
			return;
		}
		$scope.stopFindItemTemp = true;
		if (!$scope.stopFindItemInit) {
			$scope.pageNumber = Math.ceil($scope.domicilios.length / $scope.defaultListSize) + 1;
			$scope.searchDomicilios('paging');
		}
	}

	$scope.searchDomicilios = function(status) {
		if (status == 'init') {
			$scope.domicilios = [];
			$scope.pageNumber = 1;
			$scope.stopFindItem = false;
		}
		$scope.stopFindItemInit = false;
		$scope.stopFindItemTemp = true;
		$scope.message = null;
		bloquearVista("Buscando domicilios...", null);

		if ($scope.stopFindItem) {
			desbloquearVista();
		}
		else {
			if ($routeParams.agendadoId == -500) {
				$scope.agendado.id = $routeParams.agendadoId;
				$scope.pageNumber = 1;
				$scope.defaultListSize = 20;
			}
			AgendadoFactory.getAgendadoDomicilios({ 
				"genteId": $scope.agendado.id,
				"busqueda": $scope.busqueda,
				"pageNumber": $scope.pageNumber,
				"pageSize": $scope.defaultListSize,
			}, function(data) {
				if (data.success) {
					if (!data.list || data.list.length < 1) {
						$scope.domicilios = [];
						$scope.message = "No se han encontrado domicilios con los valores de búsqueda cargados...";
						$scope.stopFindItemTemp = false;
					}
					else {
						if ($scope.domicilios.length === 0) {
							$scope.domicilios = data.list;
						}
						else {
							$scope.domicilios = $scope.domicilios.concat(data.list);
						}
						$scope.total_items = data.total_items;
						$scope.stopFindItemTemp = false;
						if ($scope.total_items <= $scope.domicilios.length) {
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

app.controller('DomiciliosEditController', ['$scope', '$rootScope', '$routeParams', '$location', '$http', '$timeout', 'SesionesControl', 'ProvinciaFactory', 
                                            'TransporteFactory', 'AgendadoFactory', DomiciliosEditController]);
function DomiciliosEditController($scope, $rootScope, $routeParams, $location, $http, $timeout, SesionesControl, ProvinciaFactory, TransporteFactory, AgendadoFactory) {
	$scope.domicilio = {};
	$scope.error = null;
	$scope.filter = {};
	$scope.provincias = [];
	$scope.transportes = [];
	$scope.modaldata = {};	// Se guarda la informacion para devolver a la lista las modificaciones y no tener que recargarla
	$scope.locations=[];
	$scope.domicilioSearch = {};
	
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

		ProvinciaFactory.getProvincias({}, function(data) {
			$scope.desbloquearVista();
			if (data.success) {
				$scope.provincias = data.list;
			}
		}, function(error) {
			$scope.desbloquearVista();
			$scope.error = error.data;
		});
		TransporteFactory.getTransportes({}, function(data) {
			$scope.desbloquearVista();
			if (data.success) {
				$scope.transportes = data.list;
			}
		}, function(error) {
			$scope.desbloquearVista();
			$scope.error = error.data;
		});
	}

	// $scope.$on("newAnguCompleteVal", function(event, data){
	// 	$scope.get_coordenadas(data);
	// });

	// $scope.get_coordenadas = function(string){
	// 	// console.info("get coordenadas", string);
	// 	if(string != ''){
	// 		GeoLocationFactory.getLocation({ 'address' : string}, function(data) {
	// 			$scope.locations=[];
	// 			$scope.aux_results = data.results;
	// 			if($scope.aux_results.length>0){
	// 				$scope.locations = $scope.aux_results.map(function(result) {
	// 					result['searchString'] = string;
	// 					return result;
	// 				});
	// 			}
	// 			// console.info("locations", $scope.locations);

	// 		}, function(error) {
	// 			console.info("location data: ", error);
	// 			$scope.error = error.data;
	// 		});
	// 	}
	// }

	$scope.updateDomicilio = function(obj){
		console.info("actual", $scope.domicilio);
		console.info("new", obj);

		var aux_dom = {};
		

		obj['originalObject']['address_components'].forEach(function(value, key){
			if(value.types.indexOf('street_number') != -1){
				aux_dom.altura = value.long_name;
			}
			else if(value.types.indexOf('route') != -1){
				aux_dom.calle = value.long_name;
			}
			else if(value.types.indexOf('sublocality') != -1){
				aux_dom.barrio = value.long_name;
			}
			else if(value.types.indexOf('locality') != -1){
				aux_dom.localidad = value.long_name;
			}
			else if(value.types.indexOf('administrative_area_level_2') != -1){
				aux_dom.comuna = value.long_name;
				aux_dom.partido = value.long_name;
			}
			else if(value.types.indexOf('administrative_area_level_1') != -1){
				aux_dom.provincia = value.long_name;
			}
			else if(value.types.indexOf('postal_code') != -1){
				aux_dom.cp = value.long_name;
			}
			else if(value.types.indexOf('postal_code_suffix') != -1){
				aux_dom.cp_sufijo = value.long_name;
			}
			else if(value.types.indexOf('country') != -1){
				aux_dom.pais = value.long_name;
			}
			
			

		});

		$scope.domicilio.calleAltura = aux_dom.altura;
		$scope.domicilio.domiciliosAnexo.strProvincia = aux_dom.provincia;
		$scope.domicilio.codigoPostal = aux_dom.cp;
		$scope.domicilio.domicilio = aux_dom.calle + ' ' + aux_dom.altura;
		$scope.domicilio.localidad = aux_dom.localidad;
		$scope.domicilio.pais = aux_dom.pais;


		// $scope.provincias.forEach(function(value, key){
		// 	console.info(value);
		// 	if(value.descrip.toLowerCase() == aux_dom.provincia.toLowerCase()){
		// 		$scope.domicilio.provinciaId = value.codigo;
		// 		$scope.domicilio.provincia = value;
		// 	}
		// });

		// coordenadas separadas por pipe
		console.log(obj['originalObject']['geometry']['location']['lat']);
		console.log(obj['originalObject']['geometry']['location']['lng']);
		
		$scope.domicilio.domiciliosAnexo.latitud = obj['originalObject']['geometry']['location']['lat'];
		$scope.domicilio.domiciliosAnexo.longitud = obj['originalObject']['geometry']['location']['lng']		
	}

	$scope.onshow = function(modaldata) {
		$scope.error = null;
		$scope.filter = {};
		$scope.modaldata = modaldata;
		$('#completeDomicilio_value').val('');
		if ($scope.modaldata.id != 0) {
			bloquearVista("Leyendo domicilio...", null);
			AgendadoFactory.getAgendadoDomicilio({ 'id' : $scope.modaldata.id || 0 }, function(data) {
				$scope.domicilio = data.object;
				if ($scope.domicilio['domiciliosAnexo'] == undefined || $scope.domicilio['domiciliosAnexo'] == null){
					$scope.domicilio['domiciliosAnexo'] = {};
				}
				// try{
				// 	// console.info($scope.domicilio.pais.split("(")[1]);
				// 	$scope.domicilio.coordenadas = $scope.domicilio.pais.split("(")[1].split(")")[0];
				// }
				// catch(err){
				// 	console.info(err);
				// }
				//$scope.domicilio.pais = $scope.domicilio.pais.split("(")[0];
				desbloquearVista();
			}, function(error) {
				desbloquearVista();
				$scope.error = error.data;
			});
		}
		else {
			$scope.domicilio = {
				genteId: $scope.modaldata.genteId,
			};			
		}
	};
	
	$scope.onsubmit = function(callback) {
		bloquearVista("Guardando domicilio...", null);
		// $scope.domicilio.pais = $scope.domicilio.pais + "(" +$scope.domicilio.coordenadas + ")";
		AgendadoFactory.saveAgendadoDomicilio($scope.domicilio, 
			function(data) {
				$scope.modaldata.data = $scope.domicilio;
				$scope.modaldata.data.descripC = "[" + $scope.domicilio.id + "] " + $scope.domicilio.domicilio;
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

	$scope.$watch('domicilioSearch', function(newVal, oldVal){
        console.info("NewDomicilioSelected", newVal);
        if(newVal && Object.keys(newVal).length != 0){
            $scope.updateDomicilio(newVal);
        }
    }, true);

	
	$scope.init();
}

