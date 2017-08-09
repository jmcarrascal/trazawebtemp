package ar.com.cipres.dao;

import java.util.List;

import ar.com.cipres.model.Indicador;
import ar.com.cipres.model.KarGroup;
import ar.com.cipres.model.Kardex;
import ar.com.cipres.util.ObjCodigoDescrip;


public interface IKardexDAO extends IGenericDAO<Kardex> {
	
	
	public List<Object[]> getKarGroup();
	
	public Double getSumKar(String clave, Integer transac);
	
	public List<Indicador> getIndicadoresIngresos(Integer anio, Integer trazable);
	
	public List<Indicador> getIndicadoresIngresosExistencia(Integer anio, Integer exiNr);

	public List<Indicador> getIndicadoresIngresosEgresos(Integer anio,
			String string);

	public List<Integer> getOperadoresIngresos(Integer anio);

	public List<Indicador> getIndicadoresIngresosOperador(Integer anio,
			Integer operNr);
	
	public List<Object> findIngreso(ObjCodigoDescrip objCodigoDescrip, Integer operadorNr);

	public List<Indicador> getIndicadoresIngresosEgresos(Integer anio, String string, Integer exiNr);

	public List<Indicador> getIndicadoresIngresosEgresosAnioMenorIgual(Integer anio, String string, Integer exiNr);
	
	public List<Indicador> getIndicadoresIngresosMesExistencia(Integer anio, Integer exiNr, Integer mes);

	public List<Indicador> getIndicadoresIngresosExistencia(Integer anio, List<Integer> exiListInt);

	public List<Kardex> getByTransacItem(Integer transacNr, String claveMedicamento);
}
