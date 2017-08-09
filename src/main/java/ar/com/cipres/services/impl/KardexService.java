package ar.com.cipres.services.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ar.com.cipres.dao.IDespachosDAO;
import ar.com.cipres.dao.IKardexDAO;
import ar.com.cipres.dao.IUsuarioDAO;
import ar.com.cipres.model.Kardex;
import ar.com.cipres.model.Usuario;
import ar.com.cipres.services.IKardexService;
import ar.com.cipres.services.IReportService;
import ar.com.cipres.util.DateUtil;
import ar.com.cipres.util.FilterCriteria;
import ar.com.cipres.util.FormatUtil;
import ar.com.cipres.util.ObjCodigoDescrip;

@Service
@Transactional
public class KardexService implements IKardexService {
	

	@Autowired
	private IKardexDAO kardexDAO;
	
	@Autowired
	private IUsuarioDAO usuarioDAO;
	
	@Autowired
	private IDespachosDAO despachosDAO;
	
	@Autowired
	private IReportService reportService;

	public List<Object> findIngreso(ObjCodigoDescrip objCodigoDescrip) {
		
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String name = auth.getName();
		Usuario usuario = usuarioDAO.getByUsername(name);

		FilterCriteria filterdescrip = new FilterCriteria("fechaComprob",
				new java.sql.Date(DateUtil.getDate(objCodigoDescrip.getDescrip()).getTime()), new Short("0"));
		FilterCriteria filterOper = new FilterCriteria("operadorNr",
				usuario.getUsersk(), new Short("0"));

		List<FilterCriteria> filterCriteriaList = new ArrayList<FilterCriteria>();
		filterCriteriaList.add(filterdescrip);
		filterCriteriaList.add(filterOper);
		List<Object> ingresosList = kardexDAO.getAllObject(filterCriteriaList);

		return ingresosList;
	}

	
	public String getReportIngreso(ObjCodigoDescrip objCodigoDescrip) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String name = auth.getName();
		Usuario usuario = usuarioDAO.getByUsername(name);
		objCodigoDescrip.setDescrip(objCodigoDescrip.getDescrip().replaceAll("/", "-"));
		FilterCriteria filterdescrip = new FilterCriteria("fechaComprob",
				new java.sql.Date(DateUtil.getDate(objCodigoDescrip.getDescrip()).getTime()), new Short("0"));
		FilterCriteria filterOper = new FilterCriteria("operadorNr",
				usuario.getUsersk(), new Short("0"));

		List<FilterCriteria> filterCriteriaList = new ArrayList<FilterCriteria>();
		filterCriteriaList.add(filterdescrip);
		filterCriteriaList.add(filterOper);
		List<Kardex> ingresosList = kardexDAO.getAll(filterCriteriaList);
		for(Kardex kardex: ingresosList){
			try{
				kardex.setDespachos(despachosDAO.getByPrimaryKey(kardex.getSubtotal().intValue()));
			}catch(Exception e) {}
		}
		return reportService.createTablePDF(FormatUtil.convertKardexJsonReporteIngreso(ingresosList));
	}


	public List<Object> findIngresoRefInterna(ObjCodigoDescrip objCodigoDescrip) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String name = auth.getName();
		Usuario usuario = usuarioDAO.getByUsername(name);
		Double refInterna = FormatUtil.castRemitoAnmat(objCodigoDescrip.getDescrip());
		System.out.println(refInterna);
		FilterCriteria filterdescrip = new FilterCriteria("preFob", refInterna, new Short("0"));

		List<FilterCriteria> filterCriteriaList = new ArrayList<FilterCriteria>();
		filterCriteriaList.add(filterdescrip);

		List<Object> ingresosList = kardexDAO.getAllObject(filterCriteriaList);

		return ingresosList;
	}

	public String reportIngresoRefInterna(ObjCodigoDescrip objCodigoDescrip) {
		Authentication auth = SecurityContextHolder.getContext()
				.getAuthentication();
		String name = auth.getName();
		Usuario usuario = usuarioDAO.getByUsername(name);
		Double refInterna = FormatUtil.castRemitoAnmat(objCodigoDescrip.getDescrip());
		System.out.println(refInterna);
		FilterCriteria filterdescrip = new FilterCriteria("preFob", refInterna, new Short("0"));

		List<FilterCriteria> filterCriteriaList = new ArrayList<FilterCriteria>();
		filterCriteriaList.add(filterdescrip);
		
		List<Kardex> ingresosList = kardexDAO.getAll(filterCriteriaList);
		for(Kardex kardex: ingresosList){
			try{
				kardex.setDespachos(despachosDAO.getByPrimaryKey(kardex.getSubtotal().intValue()));
			}catch(Exception e) {}
		}
		return reportService.createTablePDF(FormatUtil.convertKardexJsonReporteIngreso(ingresosList));
	}
}