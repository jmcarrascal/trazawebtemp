package ar.com.cipres.dao.impl;

import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import ar.com.cipres.dao.IUsuariosExistenciasDAO;
import ar.com.cipres.model.UsuariosExistencias;

@Repository
public class UsuariosExistenciasDAO extends GenericDAO<UsuariosExistencias> implements IUsuariosExistenciasDAO {

	public boolean deleteByIdUsuario(int idUsuario) {
		try {
			//List<UsuariosExistencias> lExistecias;
			//String sql = "select u from UsuariosExistencias u where u.idUsuario = " + idUsuario;
			Query sql = sessionFactory.getCurrentSession().createQuery("delete from UsuariosExistencias where idUsuario = :idUsuario");
			sql.setInteger("idUsuario", idUsuario);
			
			//Query query = sessionFactory.getCurrentSession().createQuery(sql);
			sql.executeUpdate();
			sessionFactory.getCurrentSession().flush();
			return true;
		} catch (Exception ex) {
			return false;
		}
	}

	@Override
	protected String[] getDefaultOrderCriteria() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UsuariosExistencias> getExistenciasByidUsuario(Integer idUsuario) {
		try {
			String sql = "select u from UsuariosExistencias u where u.idUsuario = " + idUsuario;		
			Query query = sessionFactory.getCurrentSession().createQuery(sql);
			return query.list();
		} catch (Exception ex) {
			return null;
		}
	}
}
