'use strict';

// Declare app level module which depends on filters, and services
angular.module(
		'ngprodusimpa',
		[ 'ngMaterial', 'angucomplete', 'ngRoute', 'ngCookies', 'ngSanitize', 'ngprodusimpa.filters', 'ngprodusimpa.services', 'ngprodusimpa.directives', 'ui.date', 'ui.mask',
				'ngprodusimpa.controllers', 'tagged.directives.infiniteScroll', 'ui.bootstrap.dropdown', 'ui.bootstrap.modal', 'ui.bootstrap.transition', 'ui.bootstrap.datepicker',
				'ui.bootstrap.position', 'ui.bootstrap.tabs', 'ngGrid', 'ui.multiselect', 'angucomplete-alt', 'checklist-model', 'chart.js', 'ui.select', 'ngFileSaver',
				'CommonModule', 'ngFileUpload']).config(
		[ '$routeProvider', '$locationProvider', '$httpProvider', function($routeProvider, $locationProvider, $httpProvider){
			
			$routeProvider.when('/', {
				templateUrl : 'partials/dashboard.html',
				controller : ''
			});
			$routeProvider.when('/dashboard', {
				templateUrl : 'partials/dashboard.html',
				controller : ''
			});
			
			// ROL ADMIN
			$routeProvider.when('/usuario', {
				templateUrl : 'partials/usuario/list.html',
				controller : 'UsuarioController'
			});
			
			// ROL OPERADOR
			$routeProvider.when('/find_traza_remito', {
				templateUrl : 'partials/traza/find_remito_anmat.html',
				controller : 'TrazaRemitoController'
			});
			$routeProvider.when('/list_medicamento_recepcionar', {
				templateUrl : 'partials/traza/ingreso_traza.html',
				controller : 'IngresoAnmatController'
			});
			$routeProvider.when('/ingreso_mercaderia_normal', {
				templateUrl : 'partials/traza/ingreso_normal.html',
				controller : 'IngresoNormalController'
			});
			$routeProvider.when('/find_kardex', {
				templateUrl : 'partials/kardex/kardex.html',
				controller : 'KardexController'
			});
			$routeProvider.when('/re_print_label', {
				templateUrl : 'partials/printer/re_print_label.html',
				controller : 'RePrintLabelController'
			});
			
			// Agendados
			$routeProvider.when('/agendado/proveedores', {
				templateUrl : 'partials/agendados/agendado.html',
				controller : 'AgendadoController'
			});
			$routeProvider.when('/agendado/clientes', {
				templateUrl : 'partials/agendados/agendado.html',
				controller : 'AgendadoController'
			});
			$routeProvider.when('/agendado/:agendadoId/domicilios', {
				templateUrl : 'partials/domicilios/domicilios_list.html',
				controller : 'DomiciliosListController'
			});
			$routeProvider.when('/agendado/:agendadoId/transacciones', {
				templateUrl : 'partials/transacciones/transacciones_list.html',
				controller : 'TransaccionesListController'
			});
			$routeProvider.when('/agendado/:agendadoId/:tipoComprobante/nueva_transaccion', {
				templateUrl : 'partials/transacciones/transaccion.html',
				controller : 'TransaccionController'
			});
			
			// Domicilios
			$routeProvider.when('/domicilio/:vista/:domicilioId/transacciones', {
				templateUrl : 'partials/transacciones/transacciones_list.html',
				controller : 'TransaccionesListController'
			});
			// $routeProvider.when('/domicilio/:domicilioId/domiciliosTransacciones/:vista',
			// {templateUrl:
			// 'partials/transacciones/domicilios_transacciones_list.html',
			// controller: 'TransaccionesListController'});
			$routeProvider.when('/afiliado/crm/transacciones', {
				templateUrl : 'partials/transacciones/transacciones_afiliado_list.html',
				controller : 'TransaccionesAfiliadoListController'
			});
			
			// Articulos
			$routeProvider.when('/articulo', {
				templateUrl : 'partials/articulos/articulo.html',
				controller : 'ArticuloController'
			});
			$routeProvider.when('/articulo/:articuloId/transacciones', {
				templateUrl : 'partials/transacciones/transacciones_list.html',
				controller : 'TransaccionesListController'
			});
			
			$routeProvider.when('/articulos/existencias', {
				templateUrl : 'partials/articulos/articulos-existencias.html',
				controller : 'ArticulosExistenciasController'
			});
			
			// Reporte
			$routeProvider.when('/generateExportMedifeInterno', {
				templateUrl : 'partials/reportes/export_medife_interno.html',
				controller : 'ExportMedifeInternoController'
			});
			
			// ROL GERENCIAL
			$routeProvider.when('/indicadores_ingresos_vs_egresos', {
				templateUrl : 'partials/indicadores/indicadores_ingresos_vs_egresos.html',
				controller : 'IndicadoresIngresosVsEgresosController'
			});
			$routeProvider.when('/indicadores_ingresos_existencias', {
				templateUrl : 'partials/indicadores/indicadores_ingresos_existencias.html',
				controller : 'IndicadoresIngresosExistenciasController'
			});
			$routeProvider.when('/indicadores_ingresos_usuarios', {
				templateUrl : 'partials/indicadores/indicadores_ingresos_usuarios.html',
				controller : 'IndicadoresIngresosUsuariosController'
			});
			$routeProvider.when('/indicadores_ingresos_saldos', {
				templateUrl : 'partials/indicadores/indicadores_ingresos_saldos.html',
				controller : 'IndicadoresIngresosSaldosController'
			});
			$routeProvider.when('/indicadores_ingresos_por_existencia', {
				templateUrl : 'partials/indicadores/indicadores_ingresos_por_existencia.html',
				controller : 'IndicadoresIngresosPorExistenciaController'
			});
			$routeProvider.when('/indicadores_pedido_s_remitar', {
				templateUrl : 'partials/indicadores/indicadores_pedido_s_remitar.html',
				controller : 'IndicadoresPedidoSinRemitarController'
			});
			$routeProvider.when('/indicadores_pedido_s_viaje', {
				templateUrl : 'partials/indicadores/indicadores_pedido_s_viaje.html',
				controller : 'IndicadoresPedidoSinViajeController'
			});
			$routeProvider.when('/indicadores_ingresos_porcentaje_saldos', {
				templateUrl : 'partials/indicadores/indicadores_ingresos_porcentaje_saldos.html',
				controller : 'IndicadoresIngresosPorcentajeSaldosController'
			});
			
			// $routeProvider.when('/access-denied', {redirectTo: '/login'});
			$routeProvider.when('/login', {
				templateUrl : 'partials/login.html',
				controller : 'LoginController'
			});
			$routeProvider.otherwise({
				redirectTo : '/login'
			});
		} ]).config([ '$httpProvider', function($httpProvider){
	$httpProvider.defaults.xsrfHeaderName = 'X-CSRF-TOKEN';
	$httpProvider.interceptors.push(function(){
		return {
			response : function(response){
				if (response.headers('X-CSRF-TOKEN') != null) {
					$httpProvider.defaults.headers.common['X-CSRF-TOKEN'] = response.headers('X-CSRF-TOKEN');
				}
				return response;
			}
		}
	});
} ]).config(function(datepickerConfig, datepickerPopupConfig){
	// datepickerConfig.monthFormat = "MM";
	// datepickerConfig.dayTitleFormat = 'dd-MM-yyyy';
	datepickerPopupConfig.dateFormat = "dd-MM-yyyy";
	datepickerPopupConfig.currentText = "Hoy";
	datepickerPopupConfig.toggleWeeksText = "Semanas";
	datepickerPopupConfig.clearText = "Limpiar";
	datepickerPopupConfig.closeText = "Hecho";
	// datepickerPopupConfig.toggleWeeksText = null;
}).run(function($rootScope, $http, $route, $location, $window, $sanitize, LoginService, SesionesControl, PersistentControl, USER_PROFILES, ROOT_PATH, $modal, $mdSidenav){
	
	var prefix = '/cipres';
	$rootScope.services_prefix = prefix;
	
	/* Reset error when a new view is loaded */
	$rootScope.$on('$viewContentLoaded', function(){
		delete $rootScope.error;
	});
	
	$rootScope.$on('$routeChangeStart', function(scope, newRoute){
		
		if (newRoute['originalPath'] == '/afiliado/crm/transacciones') {
			
		} else {
			if (!SesionesControl.isAuthenticated()) {
				$location.path("/login");
			}
		}
	});
	$rootScope.userProfiles = USER_PROFILES;
	$rootScope.rootPath = ROOT_PATH;
	
	$rootScope.logout = function(){
		SesionesControl.clear();
		PersistentControl.unset('user.name');
		SesionesControl.unset('user.id');
		SesionesControl.unset('user.rol');
		SesionesControl.setAuthenticated(false);
	};
	
	$rootScope.currentYear = new Date().getFullYear();
	
	$rootScope.getUsername = function(){
		if ($rootScope.username == undefined)
			$rootScope.username = PersistentControl.get('user.name');
		return $rootScope.username;
	};
	
	$rootScope.seleccione = "Seleccione..";
	
	$rootScope.openModalConfirm = function($scope){
		var modalInstance = $modal.open({
			// templateUrl: 'partials/modal-confirm.html',
			templateUrl : $scope.url,
			controller : 'ModalConfirmCtrl',
			scope : $scope,
			resolve : {
				title : function(){
					return $scope.title;
				},
				msg_confirm : function(){
					return $scope.msg_confirm;
				},
			// ok_confirm: function() { return $scope.ok_confirm();}
			}
		});
	};
	
	$rootScope.openModalError = function($scope){
		var modalInstance = $modal.open({
			templateUrl : $scope.url,
			controller : 'ModalConfirmCtrl',
			scope : $scope,
			resolve : {
				title : function(){
					return $scope.title;
				},
				msg_error : function(){
					return $scope.msg_error;
				},
			}
		});
	};
	
	$rootScope.openModalSuccess = function($scope){
		var modalInstance = $modal.open({
			templateUrl : $scope.url,
			controller : 'ModalConfirmCtrl',
			scope : $scope,
			resolve : {
				title : function(){
					return $scope.title;
				},
				msg_success : function(){
					return $scope.msg_success;
				},
			}
		});
	};
	
	$rootScope.openModalDownload = function($scope){
		var modalInstance = $modal.open({
			templateUrl : $scope.url,
			controller : 'ModalDownloadCtrl',
			scope : $scope,
			resolve : {
				title : function(){
					return $scope.title;
				},
				uri : function(){
					return $scope.uri;
				},
				filename : function(){
					return $scope.filename;
				},
			}
		});
	};
	
	$rootScope.isAuthenticated = function(){
		return SesionesControl.isAuthenticated();
	};
	
	$rootScope.sanitizeString = function($value){
		return $sanitize($value);
	};
	
	$rootScope.clearMessages = function($scope){
		$scope.message = null;
		$scope.error = null;
	};
	
	if (SesionesControl.get('user.id') === undefined) {
		$location.path("/login");
	}
	
	$rootScope.isActive = function(route){
		return $location.path().indexOf(route) != -1;
	};
	
	$rootScope.toggleLeft = buildToggler('left');
	$rootScope.toggleRight = buildToggler('right');
	
	function buildToggler(componentId){
		return function(){
			$mdSidenav(componentId).toggle();
		}
	}
	
	$rootScope.isActive = function(route){
		return $location.path().indexOf(route) != -1;
	};
	
});
