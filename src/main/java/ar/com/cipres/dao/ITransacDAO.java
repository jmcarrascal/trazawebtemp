package ar.com.cipres.dao;

import java.sql.Date;
import java.util.List;

import ar.com.cipres.model.ObjDescripCant;
import ar.com.cipres.model.Transac;
import ar.com.cipres.model.report.ExportTransacAfiliados;


public interface ITransacDAO extends IGenericDAO<Transac> {
	
	
	public List<Transac> getCuentaCorrienteVenta(Integer[] listTipoComprVentas, Integer genteNr);

	public List<Object> getAgendadoTransacList(int genteId, String texto, int tipoComprobante, int Estado, int pageNumber, int pageSize);

	public Long getAgendadoTransacListCount(int genteId, String texto, int tipoComprobante, int Estado, int pageNumber, int pageSize);
	
	public List<Object> getTransacAfiliados(int genteId, Date fechaDesde, Date fechaHasta);
	
	public List<ExportTransacAfiliados> getTransacAfiliadosItems(int genteId, Date fechaDesde, Date fechaHasta, String list,Integer tipoComprob);

	public List<Transac> getPedidoPendienteRemitir(java.util.Date currentDateTime0, String prefijo);

	public Long getPedidoPendienteRemitirSize(java.util.Date currentDateTime0, String nrprefijo);

	public Long getPedidoPendienteRemitirPickSize(java.util.Date currentDateTime0, String nrprefijo);

	public List<ObjDescripCant> getPedidoPendienteViajesSize();

	public List<Object> getArticuloTransacList(String articuloId, String texto, int tipoComprobante, int pageNumber,
			int pageSize);

	public Long getArticuloTransacListCount(String articuloId, String texto, int tipoComprobante, int pageNumber,
			int pageSize);

	public List<Object> getDomiciliosTransacList(int articuloId, String texto, int tipoComprobante, int pageNumber,
			int pageSize);

	public Long getDomiciliosTransacListCount(int articuloId, String texto, int tipoComprobante, int pageNumber,
			int pageSize);

	public Transac getByTransacAnterior(Integer idMadre);

	public List<Object> getRemitosAfiliados(int genteNr, Date date, Date date2);

	public void updateFechaEntrega(String nrRemito, Integer tipoComprob, java.util.Date fechaEntrega);

	public List<Transac> getTransacSinEstadoViajes(Integer transporte, java.util.Date fechaDesde);

	public void updateFechaTransac(Integer transacNr, Integer tipoComprob, java.util.Date fechaEntrega);
	
	public boolean ordenCompraRepetida(String ordenCompra, Integer idGente);
}
