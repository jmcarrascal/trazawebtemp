package ar.com.cipres.services;



import java.util.Date;
import java.util.List;

import org.codehaus.jettison.json.JSONObject;

import com.google.gson.JsonObject;

import ar.com.cipres.model.ChartBasic;
import ar.com.cipres.model.JsonResultRomaneo;
import ar.com.cipres.model.ObjectPaging;
import ar.com.cipres.model.TipoComprob;
import ar.com.cipres.model.TranAnexo;
import ar.com.cipres.model.Transac;




/**
 * 
 * @author Juan Manuel Carrascal
 */
public interface ITransacService {
	
	public JsonResultRomaneo getRomaneoOc(Integer transac_oc_long)  ;

	public List<Object> getSaldoActualByAgendados(List<Object> objectList);

	public String exportTransacMedifeInterno(JSONObject json);

	public List<Object> getTransacMedifeInterno(JSONObject json);

	public List<Object> getTransacReport(Integer transac_oc_long);
	
	public JsonObject getByTransacNr(Integer transac_oc_long);

	public List<Object> getTiposComprobante();

	public ChartBasic getIndicadoresPedidoSRemitar();

	public ChartBasic getIndicadoresPedidoSRemitarPick();

	public ChartBasic getIndicadoresRemitoSinViaje();

	public Transac saveTransac(Transac transac);
	
	public Transac editTransac(Transac transac);

	public ObjectPaging getArticuloTransacciones(JSONObject json);

	public ObjectPaging getDomiciliosTransacciones(JSONObject json);

	public TipoComprob getTipoComprobante(Integer id);

	public ObjectPaging getDomiciliosTransaccionesAgrupados(JSONObject json);

	public boolean cancelarPedidoVta(Integer transac_oc_long);

	public List<Object> getTransacRemitos(JSONObject json);

	public String exportTransacIndosur(JSONObject json);

	public void updateFechaEntrega(String nrRemito, Integer tipoComprob, Date fechaEntrega);

	public List<Transac> getTransacSinEstadoViajes(Integer transporte, Date restarFechasDias);

	public List<TranAnexo> getTranAnexoFecha1(Integer fromTransac);

	public boolean ordenCompraRepetida(String ordenCompra, Integer idGente);
	
	public void updateFechaEntregaTransac(Integer transacNr, Integer tipoComprob, java.util.Date fechaEntrega);

	public ObjectPaging getDomiciliosTransaccionesAgrupadosAfiliado(JSONObject json);

	
}
