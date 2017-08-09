'use strict';

/* Controllers */

var app = angular.module('ngprodusimpa.controllers');

app.controller('IngresoNormalController', [
		'$scope',
		'$rootScope',
		'$filter',
		'$routeParams',
		'$location',
		'$http',
		'StockFactory',
		'TransacFactory',
		'SesionesControl',
		'PrintFactory',
		function($scope, $rootScope, $filter, $routeParams, $location, $http, StockFactory, TransacFactory, SesionesControl, PrintFactory){
			$('#transacnr').focus();
			$scope.mySelections = [];
			$scope['selectedMercaderia'] = {}
			$scope['selectedMercaderia']['originalObject'] = {}
			$scope['selectedLote'] = {}
			$scope['selectedLote']['originalObject'] = {}
			$scope['selectedLote']['originalObject']['id'] = {}
			$scope['selectedLote']['originalObject']['id']['despachos'] = {}
			$scope['selectedLote']['originalObject']['id']['despachos']['fechaIng'] = {}
			$scope.error = null;
			$scope.message = null;
			$scope.romaneo = null;
			$scope.action = 'new';
			$scope.loteExist = function(){
				if ($('#soloLote_value').val() != undefined && $('#soloLote_value').val() != '') {
					return true;
				} else {
					return false;
				}
			};
			
			$scope.$watch('selectedLote.originalObject.id.despachos.fechaIng', function(){
				if ($scope.selectedLote == null || ($scope.selectedLote == undefined)) {
					$('#txt_vencimiento').val('');
				} else {
					var fecha_ven = $filter('date')($scope.selectedLote.originalObject.id.despachos.fechaIng, "dd/MM/yyyy");
					$('#txt_vencimiento').val(fecha_ven);
					
					if ($scope.ingreso == undefined) {
						$scope.ingreso = {};
					}
					$scope.ingreso.vencimiento = fecha_ven;
				}
			});
			
			$scope['medicamento'] = {};
			
			$scope.setMedicamento = function(){				
				for (var int = 0; int < $scope.medicamentosDuplicados.length; int++) {
					if ($scope.medicamento.idArticulo == $scope.medicamentosDuplicados[int]['id']){
						$scope['selectedMercaderia'] = {};
						$scope['selectedMercaderia']['originalObject'] = {};
						$scope['selectedMercaderia']['originalObject'] = $scope.medicamentosDuplicados[int];
						console.log($scope['selectedMercaderia']['originalObject']);
					}															
				}
				setTimeout(function() { $('#txt_cantidad').focus() }, 100);
			}
			
			$scope.getArtCodBarras = function(){
				bloquearVista("Procesando...", null);
				console.log($scope.cod_barras);
				StockFactory.findStockByCodBarrasAll({
					'id' : $scope.cod_barras
				}, function(data){
					if (data.success) {						
						if (data.list.length > 1) {									
							console.log("HAY MMAS DE !");
							
							//$scope.medicamento_duplicado = array_element;
							$scope.medicamentosDuplicados = data.list;
							//$scope.stopBulkLoad = true;
							//$('#selectMedicamento').modal('show');
							//*/
							
						} else {							
							$scope['selectedMercaderia'] = {};
							$scope['selectedMercaderia']['originalObject'] = {};
							$scope['selectedMercaderia']['originalObject'] = data.list[0];
							$('#txt_cantidad').focus();

						}
						desbloquearVista();
					}
				}, function(error){
					$scope.error = error.data;
					desbloquearVista();
				});
				
			};
			$scope.muestroLote_ = true;
			$scope.muestroLote = function(){
				var tmp = {};
				if (!angular.equals($scope.selectedMercaderia.originalObject, {})) {
					return "false";
				} else {
					return "true";
				}
			}

			$scope.$watch('ingreso', function(){
				console.log("cambie scope.ingreso");
				setTimeout(function(){
					$scope.error = null;
				}, 1000);
				
			});
			
			$scope.ingresarMercaderia = function(){
				if ($scope.action == 'edit') {
					$scope.action = 'new';
					$scope.edit_row();
					return '';
				}
				// Valido exitencia del articulo
				if ($scope.selectedMercaderia == undefined || $scope.selectedMercaderia.originalObject == undefined || angular.equals($scope.selectedMercaderia.originalObject, {})) {
					$scope.error = "Debe ingresar un articulo";
					return ''
				}
				// Valido Cantidad
				if ($scope.ingreso == undefined || $scope.ingreso.cantidad == undefined || $scope.ingreso.cantidad == '') {
					$scope.error = "Debe ingresar una cantidad";
					return '';
				}
				
				// Valido Lote
				if ($scope.ingreso == undefined || $('#soloLote_value').val() == undefined || $('#soloLote_value').val() == '') {
					$scope.error = "Debe ingresar un lote";
					return '';
				}
				
				// Valido Vencimiento si hay lote
				if ($('#soloLote_value').val() != undefined && $('#soloLote_value').val() != '') {
					if ($('#txt_vencimiento').val() == '') {
						$scope.error = "Debe ingresar una fecha de vencimiento en el caso que defina un lote";
						return '';
					}
				}
				// Validar que este cargada toda la mercaderia
				// Armar el JSON
				var loteSend = "";
				if ($scope.selectedLote == null || $scope.selectedLote == undefined || $scope.selectedLote.originalObject == undefined || angular.equals($scope.selectedLote.originalObject, {})
						|| $scope.selectedLote.originalObject.id.despachos.soloLote == undefined) {
					loteSend = $('#soloLote_value').val();
				} else {
					console.log($scope.selectedLote.originalObject.id);
					loteSend = $scope.selectedLote.originalObject.id.despachos.soloLote;
				}
				if (loteSend == undefined) {
					$scope.error = "No quedo establecido el lote por favor debe ingresarlo";
					return '';
				}
				var item = {
					"articulo" : $scope.selectedMercaderia.originalObject,
					"cantidad" : $scope.ingreso.cantidad,
					"lote" : loteSend,
					"vencimiento" : $('#txt_vencimiento').val(),
					"traza_int" : $scope.ingreso.traza_int
				
				}
				console.log("CARGO ITEM:");
				console.log(item);
				if ($scope.selectedLote == null) {
					item['artDespa'] = null;
					
				} else {
					if ($scope.selectedLote.originalObject.id.despachos.soloLote == undefined) {
						item['artDespa'] = null;
					} else {
						item['artDespa'] = $scope.selectedLote.originalObject;
					}
				}
				$scope.selectedLote = null;
				
				// Validar si el vencimeinto es menor a un año
				if ($("#txt_vencimiento").val() != '') {
					
				}
				console.log("Romaneo");
				console.log($scope.romaneo);
				// Validar en la Orden de compra
				if ($scope.romaneo != null && $scope.ingreso.transacNr != 0) {
					// Pregunto si entra en la OC
					var errorRomaneo = false;
					var encontreRomaneo = false;
					var errorEncontrado = '';
					console.log(item);
					for (var intR = 0; intR < $scope.romaneo.length; intR++) {
						if (item['articulo']['id'].replace(' ', '') == $scope.romaneo[intR]['id']) {
							encontreRomaneo = true;
							console.log($scope.romaneo[intR]);
							if ($scope.romaneo[intR]['cant1'] > $scope.romaneo[intR]['cant1Entregado']) {
								var cantDisponible = $scope.romaneo[intR]['cant1'] - $scope.romaneo[intR]['cant1Entregado'];
								if (item['cantidad'] <= cantDisponible) {
									$scope.romaneo[intR]['cant1Entregado'] = $scope.romaneo[intR]['cant1Entregado'] + item['cantidad'];
									$scope.romaneo[intR]['ingresado'] = $scope.romaneo[intR]['ingresado'] + item['cantidad'];
									errorRomaneo = false;
									$scope.message = null;
									$scope.error = null;
								} else {
									errorRomaneo = true;
									$scope.error = 'La cantidad máxima a ingresar sobre este producto es: ' + cantDisponible + ' cuando la cantidad que intenta ingresar es: ' + item['cantidad'];
									
								}
								
							} else {
								errorRomaneo = true;
								$scope.message = null;
								$scope.error = "El medicamento no puede ingresar ya que la cantidad pedida supería a la ingresa";
								
							}
						}
					}
					
					if (!encontreRomaneo) {
						$scope.message = null;
						$scope.error = "El medicamento no se encuentra en la Orden de compra";
					} else {
						if (!errorRomaneo) {
							// Ingresar a mySelections
							$scope.mySelections.push(item);
							// Resetear el form
							$scope.selectedMercaderia = null;
							$scope.ingreso.cantidad = "";
							$scope['mercaderiaGrid'].refresh();
							$scope.ingreso.cantidad = '';
							$scope.searchStr = '';
							$('#_value').val('');
							$('#cod_barras').val('');
							$('#txt_cantidad').val('');
							$('#cod_sk').val('');
							$('#descrip_value').val('');
							$('#descrip').val('');
							$('#soloLote_value').val('');
							$('#txt_vencimiento').val('');
							$scope.medicamentosDuplicados = [];
							$scope.message = null;
							$scope.error = null;
							setTimeout(function() { $('#cod_barras').focus() }, 100);
							
						}
					}
					
				} else {
					console.log($scope.ingreso.transacnr);
					if ($scope.ingreso.transacnr == 0){
						// Ingresar a mySelections
						$scope.mySelections.push(item);
						// Resetear el form
						$scope.selectedMercaderia = null;
						$scope.ingreso.cantidad = "";
						$scope['mercaderiaGrid'].refresh();
						$scope.ingreso.cantidad = '';
						$scope.searchStr = '';
						
						$('#_value').val('');
						$('#cod_barras').val('');
						$('#txt_cantidad').val('');
						$('#cod_sk').val('');
						$('#descrip_value').val('');
						$('#descrip').val('');
						$('#soloLote_value').val('');
						$('#txt_vencimiento').val('');
						$scope.medicamentosDuplicados = [];
						$scope.message = null;
						$scope.error = null;
						setTimeout(function() { $('#cod_barras').focus() }, 100);
					}else{
						$scope.error = "Debe ingresar una orden de compra válida";
					}
				}
				
			};
			
			$scope.build_component_datagrid = function(name, columns_datagrid){
				$scope[name] = {};
				$scope[name]['items'] = [];
				
				// FILTERING
				$scope[name]['filterOptions'] = {
					filterText : '',
					useExternalFilter : false
				};
				
				// PAGING
				$scope[name]['totalServerItems'] = 0;
				$scope[name]['pagingOptions'] = {
					pageSizes : [ 10, 50, 100 ],
					pageSize : 10,
					currentPage : 1
				};
				
				// SORTING
				$scope[name]['sortOptions'] = {
					fields : [ $scope[name].filterOptions.filterText ],
					directions : [ "ASC" ]
				};
				
				$scope.remove = function(row){
					var temp = [];
					
					for (var i = 0; i < $scope.mySelections.length; i++) {
						
						if (row.articulo['id'] == $scope.mySelections[i]['articulo']['id']) {
							
						} else {
							temp.push($scope.mySelections[i]);
						}
						
						$scope['mercaderiaGrid']['items'] = temp;
						
						$scope.mySelections = temp;
						
					}
					
				}

				$scope.edit = function(row){
					$scope.action = 'edit';
					console.log(row);
					$('#addItem').modal('show');
					$scope.selectedMercaderia = {}
					$scope.selectedMercaderia.originalObject = row.articulo;
					$scope.ingreso.cantidad = row.cantidad;
					$('#soloLote_value').val(row.lote);
					$('#txt_vencimiento').val(row.vencimiento);
					$scope.itemselect = row;
					$scope.medicamentosDuplicados = [];
					$('#cod_barras').focus();
					
				}

				$scope.rePrintLabel = function(){
					var sendDataStr = $scope.tmp_labels;
					$.post("http://localhost:12555/printLabel", sendDataStr).done(function(result){
						console.log(result);
						$('#errorPrintLabel').modal('hide');
						$('#transacnr').focus();
					}).error(function(result){
						$('#errorPrintLabel').modal('show');
					});
				};
				
				$scope.edit_row = function(){
					
					var item = {
						"articulo" : $scope.selectedMercaderia.originalObject,
						"cantidad" : $scope.ingreso.cantidad,
						"lote" : $('#soloLote_value').val(),
						"vencimiento" : $('#txt_vencimiento').val(),
						"traza_int" : $scope.ingreso.traza_int
					
					}
					var temp = [];
					for (var i = 0; i < $scope.mySelections.length; i++) {
						
						if (item.articulo['id'] == $scope.mySelections[i]['articulo']['id']) {
							console.log(item);
							$scope.mySelections[i] = item;
							temp.push(item);
						} else {
							temp.push($scope.mySelections[i]);
						}
					}
					
					$scope['mercaderiaGrid']['items'] = temp;
					$('#addItem').modal('hide');
					
				}

				$scope.abrirModal = function(){
					if ($scope.ingreso.transacnr == 0){
						$scope.medicamentosDuplicados = []
						$('#addItem').modal('show');
						$('#cod_barras').focus();
						$scope.error =null;
						$('#cod_barras').val("");						
					}else{
						if ($scope.romaneo == null) {
							$scope.error = "Debe ingresar una orden de compra válida para poder ingresar medicamentos";
						} else {
							$scope.medicamentosDuplicados = []
							$('#addItem').modal('show');
							$('#cod_barras').focus();
							$scope.error =null;
							$('#cod_barras').val("");
						}
					}
				};
				
				$scope.cerrarModal = function(){
					$('#addItem').modal('hide');
					$('#transacnr').focus();
				};
				$scope.getOc = function(){
					
					if ($scope.ingreso != undefined && $scope.ingreso.transacnr != undefined && $scope.ingreso.transacnr != '') {
						if ($scope.ingreso.transacnr == 0) {
							$scope.romaneo = [];
						} else {
							bloquearVista("Buscando Orden de compra...");
							TransacFactory.getRomaneoOc({
								'id' : $scope.ingreso.transacnr
							}, function(data){
								desbloquearVista();
								if (data == undefined || data == null || data == [] || data == '') {
									$scope.error = "No se encontro la Orden de Compra: " + $scope.transacNr;
									$scope.romaneo = null;
								} else {
									if (data.success == false) {
										$scope.romaneo = null;
										$scope.error = data.msg;
										
									} else {
										// Load
										// OC en
										// la
										// calculadora
										$scope.error = null;
										$scope.romaneo = data.romaneoList;
										$scope.ingreso.observaciones = data.observaciones + "  ";
										$scope.ingreso.existencia = data.existencia;
										
										// $('#observaciones').val(data.observaciones);
									}
								}
								
							}, function(error){
								$scope.error = error.data;
								desbloquearVista();
							});
						}
						
					} else {
						$scope.romaneo = null;
					}
				}

				$scope.field_selection = [];
				
				$scope.mostrarContador = function(){
					$scope.title = "Contador";
					$scope.url = "partials/traza/contador.html";
					$scope.ok_confirm = function(){
						
					};
					$rootScope.openModalConfirm($scope);
				}

				// GRID OPTIONS
				$scope[name].gridOptions = {
					data : name + ".items",
					columnDefs : columns_datagrid,
					rowHeight : 35,
					enablePaging : true,
					enablePinning : false,
					pagingOptions : $scope[name].pagingOptions,
					filterOptions : $scope[name].filterOptions,
					keepLastSelected : true,
					multiSelect : false,
					enableColumnReordering : 'true',
					showColumnMenu : false,
					showFilter : true,
					showGroupPanel : false,
					showFooter : false,
					sortInfo : $scope[name].sortOptions,
					totalServerItems : name + ".totalServerItems",
					useExternalSorting : false,
					selectedItems : $scope.field_selection,
					i18n : "es",
					resizable : true
				};
				
				// REFRESH GRID
				$scope[name].refresh = function(){
					setTimeout(function(){
						var sb = [];
						for (var i = 0; i < $scope[name].sortOptions.fields.length; i++) {
							sb.push($scope[name].sortOptions.directions[i] === "desc" ? "-" : "+");
							sb.push($scope[name].sortOptions.fields[i]);
						}
						var filter_field = $scope[name].filterOptions.filterText;
						var filter_value = '';
						if ($scope[name].filterOptions.filterColumn == undefined) {
							
						} else {
							filter_value = $scope[name].filterOptions.filterColumn.name;
						}
						var p = {
							'filter_field' : filter_value,
							'filter_value' : $scope[name].filterOptions.filterText,
							'pageNumber' : $scope[name].pagingOptions.currentPage,
							'pageSize' : $scope[name].pagingOptions.pageSize,
							'sortInfo' : sb.join("")
						};
						
						$scope[name]['items'] = $scope.mySelections;
					}, 50);
				};
			};
			
			var columnDefs = [
					{
						displayName : '',
						width : 80,
						cellTemplate : '<a ng-click=edit(row.entity)> <i class="fa fa-pencil-square-o  fa-2x"></i></a>' + '<a ng-click=remove(row.entity)> <i class="fa fa-trash-o fa-2x"></i></a>'
					},
					{
						displayName : 'CodArticulo',
						field : 'articulo.id'
					},
					{
						displayName : 'Nombre',
						field : 'articulo.descripcion'
					},
					{
						displayName : 'GTIN',
						field : 'articulo.gtin'
					},
					{
						displayName : 'Cantidad',
						field : 'cantidad',
						cellTemplate : '<div class="ngCellText"><div ng-show="!row.entity.edit">{{row.getProperty(col.field)}}</div>'
								+ '<div ng-show="row.entity.edit" class="ngCellText"><input type="text" ng-model="row.entity.cantidad"/></div></div>'
					} ];
			
			$scope.ingresoRemito = function(ingresoParcial){
				
				var nrcomprob = '';
				if ($scope.ingreso == undefined) {
					$scope.error = "Debe ingresar el remito o la factura en el formato correspondiente";
					return '';
				}
				if ($scope.ingreso.remito == undefined || $scope.ingreso.remito == '') {
					if ($scope.ingreso.factura == undefined || $scope.ingreso.factura == '') {
						$scope.error = "Debe ingresar el remito o la factura en el formato correspondiente";
						return '';
					} else {
						nrcomprob = $scope.ingreso.factura;
					}
				} else {
					nrcomprob = $scope.ingreso.remito;
				}
				if ($scope.mySelections == undefined || $scope.mySelections.length < 1) {
					$scope.error = "Debe seleccionar al menos un medicamento";
					return '';
				} else {
					bloquearVista("Ingresando mercaderia al Sistema...", null);
					
					var sendData = {
						"transacNr" : $scope.ingreso.transacnr,
						"genteNr" : $scope.ingreso.genteNr,
						"arrayIngresoMercaderia" : $scope.mySelections,
						"nrComprob" : nrcomprob,
						"existencia" : 1,
						"observaciones" : $scope.ingreso.observacion_kardex
					};
					console.info(sendData);
					StockFactory.ingresarMercaderia(sendData, function(data){
						desbloquearVista();
						console.info(data);
						if (data.success) {
							$scope.message = data.msg;
							$scope.mySelections = [];
							
							$scope['mercaderiaGrid']['items'] = [];
							$scope['mercaderiaGrid'].refresh();
							// $scope.$apply();
							$location.path("/ingreso_mercaderia_normal");
							if (data.printLabel) {
								// var
								// sendDataObj =
								// {"labels":data.labels};
								var sendDataStr = JSON.stringify(data.labels);
								console.log(sendDataStr);
								
								$scope.tmp_labels = sendDataStr;
								bloquearVista("Imprimiendo Etiquetas....", null);
								$.post("http://localhost:12555/printLabel", sendDataStr).done(function(result){
									desbloquearVista();
									$('#errorPrintLabel').modal('hide');
									if (ingresoParcial) {
										$('#addItem').modal('show');
										$('#cod_barras').focus();
										$scope.ingreso.traza_int = false;
									} else {
										$('#transacnr').focus();
									}
									window.open("data:application/pdf;base64," + data.b64Report, "_blank");
									
								}).error(function(result){
									$('#errorPrintLabel').modal('show');
									desbloquearVista();
								});
							} else {
								window.open("data:application/pdf;base64," + data.b64Report, "_blank");
							}
							if (!ingresoParcial) {
								$scope.ingreso.observacion_kardex = '';
								$scope.ingreso.remito = '';
								$scope.ingreso.factura = '';
								$scope.ingreso.observaciones = '';
								$('#cod_barras').val('');
								$('#cod_sk').val('');
								$('#descrip_value').val('');
								$('#descrip').val('');
								$('#soloLote_value').val('');
								$('#txt_vencimiento').val('');
								$('#remito').val('');
								$('#factura').val('');
								$('#transacnr').val('');
								$('#genteNr').val('');
								$('#txt_cantidad').val('');
								$scope.error = null;
								$scope.ingreso = undefined;
								$('#transacnr').focus();
							} else {
								bloquearVista("Actualizando valores de Orden compra...");
								TransacFactory.getRomaneoOc({
									'id' : $scope.ingreso.transacnr
								}, function(data){
									desbloquearVista();
									if (data == undefined || data == null || data == [] || data == '') {
										$scope.error = "No se encontro la Orden de Compra: " + $scope.transacNr;
										$scope.romaneo = null;
									} else {
										if (data.success == false) {
											$scope.romaneo = null;
											$scope.error = data.msg;
											
										} else {
											// Load
											// OC
											// en
											// la
											// calculadora
											$scope.error = null;
											$scope.romaneo = data.romaneoList;
											$scope.ingreso.observaciones = data.observaciones + "  ";
											$scope.ingreso.existencia = data.existencia;
											
											// $('#observaciones').val(data.observaciones);
										}
									}
									
								}, function(error){
									$scope.error = error.data;
									desbloquearVista();
								});
							}
						} else {
							$scope.error = data.msg;
						}
					}, function(error){
						$scope.error = error.data;
						desbloquearVista();
					});
				}
				// Limpiar Form
			};
			
			$scope.build_component_datagrid('mercaderiaGrid', columnDefs);
			$scope['mercaderiaGrid'].refresh();
			
		} ]);
