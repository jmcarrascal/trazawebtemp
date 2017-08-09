package ar.com.cipres.services;

import java.util.List;

import ar.com.cipres.util.ObjCodigoDescrip;

public interface IKardexService {
	
	
	public List<Object> findIngreso(ObjCodigoDescrip objCodigoDescrip);

	public String getReportIngreso(ObjCodigoDescrip objCodigoDescrip);

	public List<Object> findIngresoRefInterna(ObjCodigoDescrip objCodigoDescrip);

	public String reportIngresoRefInterna(ObjCodigoDescrip objCodigoDescrip);
}