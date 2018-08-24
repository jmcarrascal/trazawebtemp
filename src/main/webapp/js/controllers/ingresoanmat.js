'use strict';

/* Controllers */

var app = angular.module('ngprodusimpa.controllers');

app.controller('IngresoAnmatController', [ '$scope', '$rootScope', '$routeParams', '$location', '$http', 'MedicamentosARecepcionarFactory', 'StockFactory', 'SesionesControl',
		function($scope, $rootScope, $routeParams, $location, $http, MedicamentosARecepcionarFactory, StockFactory, SesionesControl){
			$('#hash').focus();
			$rootScope.userSession = [];
			$rootScope.userSession.rol = SesionesControl.get('user.rol');
			$scope.resetOc = function(){
				$scope.romaneo = null;
				$('#transacNr').val("");
				$('#transacNr').focus();
			}
			$scope.initRomaneo = function(){
				$('#hash').focus();
				
			}
			$scope.initOc = function(){
				$('#transacNr').val('');
				$('#transacNr').focus();
				$('#transacNr').val('');
				$scope.transacNr = "";
			}
			$scope.closeModal = function(){
				$('#hash').focus();
			}

			$scope.mostrarContador = function(){
				$scope.title = "Contador";
				$scope.url = "partials/traza/contador.html";
				$scope.ok_confirm = function(){
					
				};
				$rootScope.openModalConfirm($scope);
			}

			$scope.getRomaneo = function(){
				$scope.message = null;
				$scope.error = null;
				bloquearVista("Procesando...", null);
				$scope.transacNr = $('#transacNr').val();
				if ($scope.transacNr == 0) {
					$scope.romaneo = [];
					desbloquearVista();
				} else {
					MedicamentosARecepcionarFactory.getRomaneo({
						'remito' : $scope.transacNr
					}, function(data){
						desbloquearVista();
						if (data == undefined || data == null || data == [] || data == '') {
							$scope.error = "No se encontro la Orden de Compra: " + $scope.transacNr;
						} else {
							if (data.success == false) {
								$scope.error = data.msg;
							} else {
								$scope.romaneo = data.romaneoList;
								$scope.ingreso = {};
								$scope.ingreso.observaciones = data.observaciones + "  ";
								$scope.ingreso.existencia = data.existencia;
							}
						}
						
					}, function(error){
						$scope.error = error.data;
						desbloquearVista();
					});
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
				
				$scope.mySelections = [];
				
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
					i18n : "es",
					resizable : true,
					selectedItems : $scope.mySelections
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
						
						$scope[name]['items'] = $rootScope.medicamentosRecepcionar;
					}, 50);
				};
			};
			
			var columnDefs = [ {
				displayName : 'Nombre',
				field : '_nombre'
			}, {
				displayName : 'Serie',
				field : '_numero_serial'
			}, {
				displayName : 'GTIN',
				field : '_gtin'
			}, {
				displayName : 'N Lote',
				field : '_lote'
			}, {
				displayName : 'N Remito',
				field : '_n_remito'
			}, {
				displayName : 'Gln',
				field : '_gln_origen'
			}, {
				displayName : 'F Transac',
				field : '_f_transaccion'
			}, {
				displayName : 'F Vencimiento',
				field : '_vencimiento'
			}, {
				displayName : 'Seleccionado',
				field : 'selected'
			} ];
			
			$scope.selectAll = function(){
				$scope.isBulkLoad = true;
				bloquearVista("Procesando...", null);
				for (var int = 0; int < $rootScope.medicamentosRecepcionar.length; int++) {
					var array_element = $rootScope.medicamentosRecepcionar[int];
					if (array_element['selected'] != true) {
						$scope.hash = "01" + array_element["_gtin"] + "21" + array_element["_numero_serial"];
						$scope.addMedicamento();
						if ($scope.stopBulkLoad == true) {
							break;
						}
					}
				}
				$('#hash').val('');
				$('#hash').focus();
				desbloquearVista();
				console.log($scope.arraySelected.length);
			}
			$scope.arraySelected = [];
			$scope.stopBulkLoad = false;
			$scope.build_component_datagrid('trazaRemitoGrid', columnDefs);
			$scope['trazaRemitoGrid'].refresh();
			
			console.log($scope.arraySelected);
			
			$scope.medicamentosDuplicados = [];
			$scope.medicamento_duplicado = {};
			$scope.idArticulo = '';
			$scope['medicamento'] = {}
			// $scope['medicamento']['idArticulo'] = {}
			
			$scope.addMedicamento = function(){
				
				// Busco si el gtin y el serie pertenecen
				
				if ($scope.isBulkLoad != true) {
					$scope.hash = $('#hash').val();
					bloquearVista("Procesando...", null);
				}
				
				var hash = String($scope.hash);
				var finded = false;
				for (var int = 0; int < $rootScope.medicamentosRecepcionar.length; int++) {
					var array_element = $rootScope.medicamentosRecepcionar[int];
					
					if (hash.indexOf(array_element['_gtin']) != -1 && hash.toLowerCase().indexOf(array_element['_numero_serial'].toLowerCase()) != -1) {
						finded = true;
						console.log(hash);
						console.log(array_element);
						var serial_send = array_element['_numero_serial'];
						var element_unit = array_element;
						var listStock = [];
						var gtin_buscar = array_element['_gtin']
						StockFactory.findStockByGtinAll({
							"id" : gtin_buscar
						}, function(data){
							desbloquearVista();
							if (data.success) {
								listStock = data.list;
								if (data.list.length > 1) {
									$scope.medicamento_duplicado = element_unit;
									$scope.medicamentosDuplicados = data.list;
									$scope.stopBulkLoad = true;
									// $('#selectMedicamento').modal('show');

									$scope.title = "Selección de artículo";
									$scope.url = "partials/traza/modal_seleccion_medicamento_distinta_presentacion.html";
									$scope.ok_confirm = function(){
										
									};
									$rootScope.openModalConfirm($scope);

									
								} else {
									if (data.list.length == 0) {
										$scope.error = "El GTIN " + gtin_buscar + " no esta dado de alta en la tabla Stock";
									}else{
									console.log(data.list);
									$scope['medicamento']['idArticulo'] = data.list[0]['id']
									var element_back = data.list[0];
									element_back['_gtin'] = data.list[0]['gtin'];
									element_back['_numero_serial'] = serial_send;
									$scope.medicamento_duplicado = element_back;
									
									$scope.validateAddMedicamento($scope['medicamento']['idArticulo'], element_back, $scope.hash);
								}	
								}
							}
						}, function(error){
							$scope.error = error.data;
							desbloquearVista();
						});
						
					}
					
				}
				if (!finded) {
					$scope.message = null;
					$scope.error = "No se ha encontrado el medicamento en la lista";
					
				}
				if (!$scope.isBulkLoad) {
					desbloquearVista();
				}
				
			};
			
			$scope.build_component_datagrid('trazaRemitoGrid', columnDefs);
			$scope['trazaRemitoGrid'].refresh();
			
			$scope.ingresoAnmat = function(){
				if (false) {
					// if (!$scope.oneselected) {
					$scope.error = "Debe seleccionar al menos un medicamento";
				} else {
					bloquearVista("Enviando información a Anmat...", null);
					var sendData = {
						"transacNr" : $scope.transacNr,
						"arrayTransac" : $rootScope.medicamentosRecepcionar,
						// "arrayTransac" : $scope.arraySelected,
						"nrComprob" : $rootScope.nrComprob
					};
					MedicamentosARecepcionarFactory.confirmTransacAnmat(sendData, function(data){
						desbloquearVista();
						if (data.success) {
							$scope.message = data.msg;
							window.open("data:application/pdf;base64," + data.b64Report, "_blank");
							setTimeout(function(){
								$location.path("/find_traza_remito");
							}, 1000);
						} else {
							$scope.error = data.msg;
						}
					}, function(error){
						$scope.error = error.data;
						desbloquearVista();
					});
				}
				
			};
			
			$scope.selectedMedicamento = '';
			
			$scope.addUnitMedicamento = function(){
				var hash = String($scope.hash);
				$('#selectMedicamento').modal('hide');
				$scope.validateAddMedicamento($scope['medicamento']['idArticulo'], $scope.medicamento_duplicado, hash);
			}
			var finded = false;
			$scope.validateAddMedicamento = function(idArticulo, elementBack, hash){
				
				for (var int = 0; int < $rootScope.medicamentosRecepcionar.length; int++) {
					var array_element = $rootScope.medicamentosRecepcionar[int];
					
					
					if (array_element['_gtin'] == elementBack['_gtin'] && elementBack['_numero_serial'] == array_element['_numero_serial']) {
						
						
						if (array_element['selected'] == true) {
							$scope.message = null;
							$scope.error = "El medicamento ya ha sido ingresado";
							$scope.hash = "";
							$('#hash').val('');
							$('#hash').focus();
							break;
						} else {
							
							if ($scope.transacNr != 0) {
								
								// Pregunto si entra en la OC
								var errorRomaneo = false;
								var encontreRomaneo = false;
								for (var intR = 0; intR < $scope.romaneo.length; intR++) {
									if (elementBack['_gtin'] == $scope.romaneo[intR]['gtin']) {
										encontreRomaneo = true;
										console.log($scope.romaneo[intR]);
										if ($scope.romaneo[intR]['cant1'] > $scope.romaneo[intR]['cant1Entregado']) {
											$scope.romaneo[intR]['cant1Entregado'] = $scope.romaneo[intR]['cant1Entregado'] + 1;
											$scope.romaneo[intR]['ingresado'] = $scope.romaneo[intR]['ingresado'] + 1;
											errorRomaneo = false;
											
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
									break;
								} else {
									if (!errorRomaneo) {
										$scope.oneselected = true;
										$rootScope.medicamentosRecepcionar[int]['selected'] = true;
										$rootScope.medicamentosRecepcionar[int]['hash'] = hash;
										$rootScope.medicamentosRecepcionar[int]['idArticulo'] = $scope['medicamento']['idArticulo'];
										$scope.arraySelected.push({
											"selected" : true,
											"hash" : hash,
											"_id_transaccion" : $rootScope.medicamentosRecepcionar[int]['_id_transaccion'],
											"_gtin" : $rootScope.medicamentosRecepcionar[int]['_gtin'],
											"idArticulo" : $scope.idArticulo
										});
										$scope['trazaRemitoGrid'].refresh();
										$scope.error = null;
										$scope.message = "Se ha ingresado el medicamento: GTIN: " + elementBack['_gtin'];
										$('#hash').val('');
										$('#hash').focus();
										finded = true;
										
									}
									break;
								}
								
							}else{
								$scope.oneselected = true;
								$rootScope.medicamentosRecepcionar[int]['selected'] = true;
								$rootScope.medicamentosRecepcionar[int]['hash'] = hash;
								$rootScope.medicamentosRecepcionar[int]['idArticulo'] = $scope['medicamento']['idArticulo'];
								$scope.arraySelected.push({
									"selected" : true,
									"hash" : hash,
									"_id_transaccion" : $rootScope.medicamentosRecepcionar[int]['_id_transaccion'],
									"_gtin" : $rootScope.medicamentosRecepcionar[int]['_gtin'],
									"idArticulo" : $scope.idArticulo
								});
								$scope['trazaRemitoGrid'].refresh();
								$scope.error = null;
								$scope.message = "Se ha ingresado el medicamento: GTIN: " + elementBack['_gtin'];
								$('#hash').val('');
								$('#hash').focus();
								finded = true;
							}
						}
					}
				}
			}
		}

]);
