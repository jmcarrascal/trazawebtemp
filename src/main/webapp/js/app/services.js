'use strict';

/* Services */

/*
 http://docs.angularjs.org/api/ngResource.$resource

 Default ngResources are defined as

 'get':    {method:'GET'},
 'save':   {method:'POST'},
 'query':  {method:'GET', isArray:true},
 'remove': {method:'DELETE'},
 'delete': {method:'DELETE'}
 'audit':  {method:'GET'},

 */

var services = angular.module('ngprodusimpa.services', ['ngResource']);

services.constant('USER_PROFILES', {
	 administrador: '1',
	 operador:      '2',
	 ingreso_devoluciones:    '3',
	 oficial_cuentas:      '4',
	 ventas: '5'
});


// var prefix = 'http://demo-desa.cipres.io/cipres';
var prefix = '/cipres';

services.constant('ROOT_PATH', prefix + '/');


services.factory('LoginService', function($resource) {
	return $resource(prefix + '/rest/user/:action', {},
			{
				authenticate: {
					method: 'POST',
					params: {'action' : 'authenticate'},
					headers : {'Content-Type': 'application/x-www-form-urlencoded'}
				}
			}
		);
});

services.factory('AuthenticateService', function($resource) {
	return $resource(prefix + '/rest/user/authenticate/:action', {}, {
		authenticate: {
			method: 'POST',
			params: {'action' : 'hash'},
			headers : {'Content-Type': 'application/x-www-form-urlencoded'}
		}
	});
});


services.factory("SesionesControl", function(){
	var auth_key='isAuthenticated';
    return {
        //obtenemos una sesión //getter
        get : function(key)
        {
            return sessionStorage.getItem(key);
        },
        //creamos una sesión //setter
        set : function(key, val)
        {
            return sessionStorage.setItem(key, val);
        },
        //limpiamos una sesión
        unset : function(key)
        {
            return sessionStorage.removeItem(key);
        },
        clear : function()
        {
            return sessionStorage.clear();
        },
        setAuthenticated : function(value){
        	return sessionStorage.setItem(auth_key, value);
        },
        isAuthenticated : function()
        {
            return sessionStorage.getItem(auth_key);
        }
    };
});

services.factory("PersistentControl", function(){
    return {
        //obtenemos una sesión //getter
        get : function(key)
        {
            return localStorage.getItem(key);
        },
        //creamos una sesión //setter
        set : function(key, val)
        {
            return localStorage.setItem(key, val);
        },
        //limpiamos una sesión
        unset : function(key)
        {
            return localStorage.removeItem(key);
        },
        clear : function()
        {
            return localStorage.clear();
        }
    };
});



services.factory('MedicamentosARecepcionarFactory', function ($resource) {
	return $resource(prefix + '/rest/traza/:action/:remito', {}, {
		findRemitoARecepcionar:   { method: 'GET',  params: {'action' : 'findRemitoARecepcionar'}, isArray:true },
		getRomaneo:   { method: 'GET',  params: {'action' : 'getRomaneo'}},
		confirmTransacAnmat: { method: 'POST', params: {'action' : 'confirmTransacAnmat'} },
        update: { method: 'PUT',  params: {'action' : 'update'} },
        create: { method: 'POST', params: {'action' : 'create'} },
        all:    { method: 'POST', params: {'action' : 'all'}, isArray:true },
    });
});


services.factory('ArticuloFactory', function ($resource) {
    return $resource(prefix + '/rest/articulo/:action/:id', {}, {
        findArticulo:   { method: 'POST',  params: {'action' : 'findArticulo'} },
        findArticuloByKey:   { method: 'GET',  params: {'action' : 'findArticuloByKey'} },
        getArticuloMadreAll:   { method: 'GET',  params: {'action' : 'getArticuloMadreAll'}},
        saveArticulo:   { method: 'POST',  params: {'action' : 'saveArticulo'} }
    });
});

services.factory('ArticulosExistenciasFactory', function ($resource) {
    return $resource(prefix + '/rest/articulo/:action', {}, {
        findArticulos:   { method: 'POST',  params: {'action' : 'findArticulos'} }
    });
});

services.factory('FamiliaFactory', function ($resource) {
    return $resource(prefix + '/rest/familia/:action/:id', {}, {
        getFamilias:   { method: 'GET',  params: {'action' : 'getFamilias'}},        
    });
});

services.factory('SubFamiliaFactory', function ($resource) {
    return $resource(prefix + '/rest/subFamilia/:action/:id', {}, {
        getSubFamilias:   { method: 'GET',  params: {'action' : 'getSubFamilias'}}, 
    });
});



services.factory('StockFactory', function ($resource) {
	return $resource(prefix + '/rest/stock/:action/:id', {}, {
		findStockByCodBarras:   { method: 'GET',  params: {'action' : 'findStockByCodBarras'} },
		findStockByCodBarrasAll:   { method: 'GET',  params: {'action' : 'findStockByCodBarrasAll'} },
		findStockByGtinAll:   { method: 'GET',  params: {'action' : 'findStockByGtinAll'} },
		get_indicadores_ingresos:{ method: 'GET',  params: {'action' : 'get_indicadores_ingresos'} },
		get_indicadores_ingresos_vencidos:{ method: 'GET',  params: {'action' : 'get_indicadores_ingresos_vencidos'} },
		get_indicadores_ingresos_egresos:{ method: 'GET',  params: {'action' : 'get_indicadores_ingresos_egresos'} },
		get_indicadores_ingresos_saldo_vencidos:{ method: 'GET',  params: {'action' : 'get_indicadores_ingresos_saldo_vencidos'} },
		get_indicadores_ingresos_saldo_cuarentena:{ method: 'GET',  params: {'action' : 'get_indicadores_ingresos_saldo_cuarentena'} },
		get_indicadores_ingresos_saldo_recupero:{ method: 'GET',  params: {'action' : 'get_indicadores_ingresos_saldo_recupero'} },
		get_indicadores_ingresos_saldo_disponible:{ method: 'GET',  params: {'action' : 'get_indicadores_ingresos_saldo_disponible'} },
		get_indicadores_por_existencia:{ method: 'POST',  params: {'action' : 'get_indicadores_por_existencia'} },
		get_indicadores_por_existencia_por_mes:{ method: 'POST',  params: {'action' : 'get_indicadores_por_existencia_por_mes'} },		
		get_indicadores_operadores:{ method: 'POST',  params: {'action' : 'get_indicadores_operadores'} },		
		get_indicadores_cuarentena:{ method: 'GET',  params: {'action' : 'get_indicadores_cuarentena'} },		
		get_indicadores_porcentaje_vencidos:{ method: 'GET',  params: {'action' : 'get_indicadores_porcentaje_vencidos'} },				
		get_indicadores_porcentaje_cuarentena:{ method: 'GET',  params: {'action' : 'get_indicadores_porcentaje_cuarentena'} },
		get_indicadores_porcentaje_recupero:{ method: 'GET',  params: {'action' : 'get_indicadores_porcentaje_recupero'} },
		get_indicadores_porcentaje_desvio:{ method: 'GET',  params: {'action' : 'get_indicadores_porcentaje_desvio'} },
		getRomaneo:   { method: 'GET',  params: {'action' : 'getRomaneo'}},
		getAllExistencias:   { method: 'GET',  params: {'action' : 'getAllExistencias'}, isArray:true },
		
		ingresarMercaderia: { method: 'POST', params: {'action' : 'ingresarMercaderia'} },
        update: { method: 'PUT',  params: {'action' : 'update'} },
        create: { method: 'POST', params: {'action' : 'create'} },
        all:    { method: 'POST', params: {'action' : 'all'}, isArray:true },
    });
});



services.factory('TransacFactory', function ($resource) {
	return $resource(prefix + '/rest/transac/:action/:id/:nombre', {}, {
		getRomaneoOc:   { method: 'GET',  params: {'action' : 'getRomaneoOc'}},
		getPdfComprobante:   { method: 'GET',  params: {'action' : 'getComprobPDFTransac'}},
		getComprobante:   { method: 'GET',  params: {'action' : 'getByTransacNr'}},
		getTransacFilesList:   { method: 'GET',  params: {'action' : 'getTransacFilesList'}},
		getTransacFile:   { method: 'GET',  params: {'action' : 'getTransacFile'}},
		getTiposComprobante:   { method: 'GET',  params: {'action' : 'getTiposComprobante'}},
		exportTransacMedifeInterno:   { method: 'POST',  params: {'action' : 'exportTransacMedifeInterno'}},		
		exportTransacIndosur:   { method: 'POST',  params: {'action' : 'exportTransacIndosur'}},
		getTransacMedifeInterno:   { method: 'POST',  params: {'action' : 'getTransacMedifeInterno'}},
		getTransacRemitos:   { method: 'POST',  params: {'action' : 'getTransacRemitos'}},		
		get_indicadores_pedido_s_remitar:{ method: 'GET',  params: {'action' : 'get_indicadores_pedido_s_remitar'} },
		get_indicadores_pedido_s_remitar_pick:{ method: 'GET',  params: {'action' : 'get_indicadores_pedido_s_remitar_pick'} },
		get_indicadores_pedido_s_viaje:{ method: 'GET',  params: {'action' : 'get_indicadores_pedido_s_viaje'} },
		getArticuloTransacciones:{ method: 'POST',  params: {'action' : 'getArticuloTransacciones'}},
		getDomicilioTransacciones:{ method: 'POST',  params: {'action' : 'getDomiciliosTransacciones'}},
		getDomiciliosTransaccionesAgrupados:{ method: 'POST',  params: {'action' : 'getDomiciliosTransaccionesAgrupados'}},
		getOrdenCompraRepetida:{ method: 'GET', url:'/cipres/rest/transac/:action/:idAgendado/:idOC',  params: {'action' : 'ordenCompraRepetida'}},
		getTipoComprobante:   { method: 'GET',  params: {'action' : 'getTipoComprobante'}},
		cancelTransac: { method: 'POST',  params: {'action' : 'cancelarPedidoVta'}},
		editTransac: { method: 'POST',  params: {'action' : 'editTransac'}},
		saveTransac: { method: 'POST',  params: {'action' : 'saveTransac'}}
    });
});

services.factory('AgendadoFactory', function ($resource) {
	return $resource(prefix + '/rest/agendado/:action/:id/:type/:alternativo', {}, {
		findAgendadoShort:   { method: 'POST',  params: {'action' : 'findAgendadoShort'}},
		findAgendado:   { method: 'POST',  params: {'action' : 'findAgendado'}},
		saveAgendado:   { method: 'POST',  params: {'action' : 'saveAgendado'}},
		getAgendado:   { method: 'GET',  params: {'action' : 'getAgendado'}},
		getAgendadoDomicilio:   { method: 'GET',  params: {'action' : 'getAgendadoDomicilio'}},
		getAgendadoDomicilioByAlternativo:   { method: 'GET',  params: {'action' : 'getAgendadoDomicilioByAlternativo'}},
		saveAgendadoDomicilio:   { method: 'POST',  params: {'action' : 'saveAgendadoDomicilio'}},
		getAgendadoDomicilios:   { method: 'POST',  params: {'action' : 'getAgendadoDomicilios'}},
		getAgendadoProveedorAll:   { method: 'GET',  params: {'action' : 'getAgendadoProveedorAll'}},
		getAgendadoTransacciones:{ method: 'POST',  params: {'action' : 'getAgendadoTransacciones'}},
		
	});
});

services.factory('PublicFactory', function ($resource) {
	return $resource(prefix + '/rest/public/:action/:id/:type', {}, {		
		getObraSocialAll:   { method: 'GET',  params: {'action' : 'getObraSocialAll'}},
		getPedidos:   { method: 'POST',  params: {'action' : 'getPedidos'}},
		getObraSocialRnos:   { method: 'GET',  params: {'action' : 'getObraSocialRnos'}}
	});
});




// services.factory('GeoLocationFactory', function ($resource) {
// 	return $resource(prefix + '/rest/agendado/:action/:address', {}, {
// 		getLocation:   { method: 'GET',  params: {'action': 'searchDomicilio'}, headers: {}}
//     });
// });


services.factory('KardexFactory', function ($resource) {
	return $resource(prefix + '/rest/kardex/:action/:id', {}, {
		findIngreso:   { method: 'POST',  params: {'action' : 'findKardex'}},
		findIngresoRefInterna:   { method: 'POST',  params: {'action' : 'findIngresoRefInterna'}},
		getReportIngreso:   { method: 'POST',  params: {'action' : 'reportIngreso'}},
		getReportIngresoRefInterna:   { method: 'POST',  params: {'action' : 'reportIngresoRefInterna'}},
		

		});
});

services.factory('VendedorFactory', function ($resource) {
	return $resource(prefix + '/rest/vendedor/:action/:id', {}, {
		getVendedores:   { method: 'GET',  params: {'action' : 'getVendedores'}},
	});
});

services.factory('RelacionFactory', function ($resource) {
	return $resource(prefix + '/rest/relacion/:action/:id', {}, {
		getRelaciones:   { method: 'GET',  params: {'action' : 'getRelaciones'}},
	});
});


services.factory('PlanFactory', function ($resource) {
	return $resource(prefix + '/rest/plan/:action/:id', {}, {
		getPlanes:   { method: 'GET',  params: {'action' : 'getPlanes'}},
	});
});

services.factory('ProvinciaFactory', function ($resource) {
	return $resource(prefix + '/rest/provincia/:action/:id', {}, {
		getProvincias:   { method: 'GET',  params: {'action' : 'getProvincias'}},
	});
});

services.factory('ZonaFactory', function ($resource) {
	return $resource(prefix + '/rest/zona/:action/:id', {}, {
		getZonas:   { method: 'GET',  params: {'action' : 'getZonas'}},
	});
});

services.factory('IvaCodigoFactory', function ($resource) {
	return $resource(prefix + '/rest/ivacodigo/:action/:id', {}, {
		getIvaCodigos:   { method: 'GET',  params: {'action' : 'getIvaCodigos'}},
	});
});

services.factory('ImpuestosFactory', function ($resource) {
	return $resource(prefix + '/rest/impuestos/:action/:id', {}, {
		getImpuestosCombo:   { method: 'GET',  params: {'action' : 'getImpuestosCombo'}},
	});
});


services.factory('CondicionVentaFactory', function ($resource) {
	return $resource(prefix + '/rest/condicionventa/:action', {}, {
		getCondicionVentas:   { method: 'GET',  params: {'action' : 'getCondicionVentas'}},
	});
});

services.factory('TransporteFactory', function ($resource) {
	return $resource(prefix + '/rest/transporte/:action/:id', {}, {
		getTransportes:   { method: 'GET',  params: {'action' : 'getTransportes'}},
	});
});

services.factory('MedicamentosARecepcionarDosParamFactory', function ($resource) {
	return $resource(prefix + '/rest/traza/:action/:comprob/:tipo', {}, {
		findRemitoARecepcionar:   { method: 'GET',  params: {'action' : 'findRemitoARecepcionar'}, isArray:true },

    });
});

services.factory('PrintFactory', function ($resource) {
	return $resource('http://localhost:12555/:action/:id', {}, {
		printLabel: { method: 'POST', params: {'action' : 'printLabel'} }
    });
});

services.factory('ExistenciaFactory', function ($resource) {
	return $resource(prefix + '/rest/existencias/:action/:id', {}, {
        getExistencias: { method: 'GET',  params: {'action' : 'getExistencias'}},
    });
});


services.factory('UsuarioFactory', function ($resource) {
	return $resource(prefix + '/rest/usuario/:action/:id', {}, {
		show:   { method: 'GET',  params: {'action' : 'show'} },
        remove: { method: 'POST', params: {'action' : 'remove'} },
        update: { method: 'PUT',  params: {'action' : 'update'} },
        create: { method: 'POST', params: {'action' : 'create'} },
        all:    { method: 'POST', params: {'action' : 'all'}, isArray:true },
        getOperadores: { method: 'POST', params: {'action' : 'getOperadores'}, isArray:true },
        getOperadoresSk: { method: 'GET', params: {'action' : 'getOperadoresSk'}, isArray:true },
        changePassw : { method: 'POST', params: {'action' : 'changePassw'} }
    });
});

services.factory('RolFactory', function ($resource) {
	return $resource(prefix + '/rest/rol/:action/:id', {}, {
		show:   { method: 'GET',  params: {'action' : 'show'} },
        remove: { method: 'POST', params: {'action' : 'remove'} },
        update: { method: 'PUT',  params: {'action' : 'update'} },
        create: { method: 'POST', params: {'action' : 'create'} },
        all:    { method: 'POST', params: {'action' : 'all'}, isArray:true },
    });
});

services.factory('PedidoFactory', function() {
	var pedido;
  return {
    setPedido : function(obj) {
    	pedido = obj;
    	
    },
    getPedido: function() {
    	return pedido;
    }
  }
});

services.factory('FocusFactory', function($timeout, $window) {
    return function(id) {
        $timeout(function() {
          var element = $window.document.getElementById(id);
          if(element)
            element.focus();
        });
      };
    });

