package ar.com.cipres.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import ar.com.cipres.dao.IKardexDAO;
import ar.com.cipres.model.Indicador;
import ar.com.cipres.model.Kardex;
import ar.com.cipres.util.ObjCodigoDescrip;

@Repository
public class KardexDAO extends GenericDAO<Kardex> implements IKardexDAO {
	
	private String[] defaultOrderCriteria = { "articNr" };
	
	public KardexDAO() {
		super(Kardex.class);
	}
	
	protected String[] getDefaultOrderCriteria() {
		return defaultOrderCriteria;
	}
	
	public List<Object[]> getKarGroup() {
		List<Object[]> result =  null;
		try{								
			Query query = sessionFactory.
				      getCurrentSession().
				      createQuery("select count(k) as contador,k.transacOrigen, k.id.stock.id from Kardex k where k.transacOrigen <> 0 and k.fechaComprob > '19-07-2015' group by k.transacOrigen, k.id.stock.id");
			//select count(*) as contador, transacorigen, clavearticulo from kardex where transacnr > 2875410 and transacorigen <> 0 and fechacomprob > '14-07-2015' group by transacorigen,clavearticulo  order by contador desc

				    
			result=(List<Object[]>) query.list();
			
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			return result;
			}
	}
	
	
	public List<Indicador> getIndicadoresIngresos(Integer anio, Integer trazable) {
		List<Indicador> result =  null;
		try{								
			Query query = sessionFactory.
				      getCurrentSession().
			 createQuery("select new ar.com.cipres.model.Indicador(SUM(k.cantidad1) as ejeY, datepart_month(k.fechaComprob) as ejeX) "
			      		+ " from Kardex k,  Stock as s where k.id.stock.id = s.id and datepart_year(k.fechaComprob) = " + anio + " and s.trazable = " + trazable + " and (k.tipoComprobante = 20 or k.tipoComprobante = 2 or k.tipoComprobante = 24)"
			      		+ " group by datepart_month(k.fechaComprob) order by datepart_month(k.fechaComprob)");
			
			result=(List<Indicador>) query.list();
			
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			return result;
			}
	}
	
	public List<Indicador> getIndicadoresIngresosExistencia(Integer anio, Integer exiNr) {
		List<Indicador> result =  null;
		try{								
			Query query = sessionFactory.
				      getCurrentSession().
			 createQuery("select new ar.com.cipres.model.Indicador(SUM(k.cantidad1) as ejeY, datepart_month(k.fechaComprob) as ejeX) "
			      		+ " from Kardex k where datepart_year(k.fechaComprob) = " + anio + " and k.existenciaNr = " + exiNr + " and (k.tipoComprobante = 20 or k.tipoComprobante = 2 or k.tipoComprobante = 24)"
			      		+ " group by datepart_month(k.fechaComprob) order by datepart_month(k.fechaComprob)");
			
			result=(List<Indicador>) query.list();
			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			return result;
			}
	}
	
	public List<Indicador> getIndicadoresIngresosMesExistencia(Integer anio, Integer exiNr, Integer mes) {
		List<Indicador> result =  null;
		try{								
			Query query = sessionFactory.
				      getCurrentSession().
			 createQuery("select new ar.com.cipres.model.Indicador(SUM(k.cantidad1) as ejeY, datepart_day(k.fechaComprob) as ejeX) "
			      		+ " from Kardex k where datepart_year(k.fechaComprob) = " + anio + " and datepart_month(k.fechaComprob) = " + mes + " and k.existenciaNr = " + exiNr + " and (k.tipoComprobante = 20 or k.tipoComprobante = 2 or k.tipoComprobante = 24)"
			      		+ " group by datepart_day(k.fechaComprob) order by datepart_day(k.fechaComprob)");
			
			result=(List<Indicador>) query.list();
			
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			return result;
			}
	}
	
	public Double getSumKar(String clave, Integer transac) {
		Double sum = 0d;
		try{								
			Query query = sessionFactory.
				      getCurrentSession().
				      createQuery("select SUM(k.cantidad1) from Kardex k where k.id.stock.id = '"+clave+"' and k.transacOrigen = '"+transac+"' group by k.transacOrigen, k.id.stock.id");
				    
			sum=(Double) query.list().get(0);
			
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			return sum;
			}
	}

	
	public List<Indicador> getIndicadoresIngresosEgresos(Integer anio,
			String tipoComprob) {
		List<Indicador> result =  null;
		try{								
			Query query = sessionFactory.
				      getCurrentSession().
			 createQuery("select new ar.com.cipres.model.Indicador(SUM(k.cantidad1) as ejeY, datepart_month(k.fechaComprob) as ejeX) "
			      		+ " from Kardex k,  Stock as s where k.id.stock.id = s.id and datepart_year(k.fechaComprob) = " + anio + " and k.tipoComprobante in ("+tipoComprob+") "
			      		+ " group by datepart_month(k.fechaComprob) order by datepart_month(k.fechaComprob)");
			
			result=(List<Indicador>) query.list();
			
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			return result;
			}
	}

	
	public List<Integer> getOperadoresIngresos(Integer anio) {
		List<Integer> result =  null;		
		try{								
			Query query = sessionFactory.
				      getCurrentSession().
			 createQuery("select k.operadorNr"
			      		+ " from Kardex k where datepart_year(k.fechaComprob) = " + anio + " and (k.tipoComprobante = 20 or k.tipoComprobante = 2 or k.tipoComprobante = 24)"
			      		+ " group by k.operadorNr");
			
			result=(List<Integer>) query.list();
			
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			return result;
			}		
		
	}

	
	public List<Indicador> getIndicadoresIngresosOperador(Integer anio,
			Integer operNr) {
		List<Indicador> result =  null;
		try{								
			Query query = sessionFactory.
				      getCurrentSession().
			 createQuery("select new ar.com.cipres.model.Indicador(SUM(k.cantidad1) as ejeY, datepart_month(k.fechaComprob) as ejeX) "
			      		+ " from Kardex k where datepart_year(k.fechaComprob) = " + anio + " and k.operadorNr = " + operNr + " and (k.tipoComprobante = 20 or k.tipoComprobante = 2 or k.tipoComprobante = 24)"
			      		+ " group by datepart_month(k.fechaComprob) order by datepart_month(k.fechaComprob)");
			
			result=(List<Indicador>) query.list();
			
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			return result;
			}
	}

	@Override
	public List<Object> findIngreso(ObjCodigoDescrip objCodigoDescrip, Integer operadorNr) {
		// TODO Auto-generated method stub
		return null;
	}

	
	public List<Indicador> getIndicadoresIngresosEgresos(Integer anio, String tipoComprob, Integer exiNr) {
		List<Indicador> result =  null;
		try{								
			Query query = sessionFactory.
				      getCurrentSession().
			 createQuery("select new ar.com.cipres.model.Indicador(SUM(k.cantidad1) as ejeY, datepart_month(k.fechaComprob) as ejeX) "
			      		+ " from Kardex k,  Stock as s where k.id.stock.id = s.id and datepart_year(k.fechaComprob) = " + anio + " and k.tipoComprobante in ("+tipoComprob+") and k.existenciaNr in ("+exiNr+") "
			      		+ " group by datepart_month(k.fechaComprob) order by datepart_month(k.fechaComprob)");
			
			result=(List<Indicador>) query.list();
			
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			return result;
			}
	}

	@Override
	public List<Indicador> getIndicadoresIngresosEgresosAnioMenorIgual(Integer anio, String tipoComprob, Integer exiNr) {
		List<Indicador> result =  null;
		try{								
			Query query = sessionFactory.
				      getCurrentSession().
			 createQuery("select new ar.com.cipres.model.Indicador(SUM(k.cantidad1) as ejeY, datepart_month(k.fechaComprob) as ejeX) "
			      		+ " from Kardex k,  Stock as s where k.id.stock.id = s.id and datepart_year(k.fechaComprob) <= " + anio + " and k.tipoComprobante in ("+tipoComprob+") and k.existenciaNr in ("+exiNr+") "
			      		+ " group by datepart_month(k.fechaComprob) order by datepart_month(k.fechaComprob)");
			
			result=(List<Indicador>) query.list();
			
			
		}catch(Exception e){
			System.out.println(e.getMessage());
		}finally{
			return result;
			}
	}

	public List<Indicador> getIndicadoresIngresosExistencia(Integer anio, List<Integer> exiListInt) {
		List<Indicador> result =  null;
		try{	
			int i = 0;
			String exiOr = "(";
			for(Integer exi :exiListInt){
				if (i == 0){
					exiOr = "k.existenciaNr = " + exi + " ";
				}else{
					exiOr = exiOr + " or k.existenciaNr = " + exi + " ";
				}
				i++;
			}
			exiOr = exiOr + ") ";
			
			Query query = sessionFactory.
				      getCurrentSession().
			 createQuery("select new ar.com.cipres.model.Indicador(SUM(k.cantidad1) as ejeY, datepart_month(k.fechaComprob) as ejeX) "
			      		+ " from Kardex k where datepart_year(k.fechaComprob) = " + anio + " and " + exiOr + " and (k.tipoComprobante = 20 or k.tipoComprobante = 2 or k.tipoComprobante = 24)"
			      		+ " group by datepart_month(k.fechaComprob) order by datepart_month(k.fechaComprob)");
			
			result=(List<Indicador>) query.list();
			
			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			return result;
			}
	}


	public List<Kardex> getByTransacItem(Integer transacNr, String claveMedicamento) {
		List<Kardex> kardexList = new ArrayList<Kardex>();
		try {
			String sql = "select k from Kardex k where k.transacOrigen = :transacNr and k.id.stock.id = :claveMedicamento";
			Query query = sessionFactory.getCurrentSession().createQuery(sql).setInteger("transacNr", transacNr).setString("claveMedicamento", claveMedicamento);

			kardexList = query.list();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		} finally {
		}
		return kardexList;
	}
	
	

	
}
 