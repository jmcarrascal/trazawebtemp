package ar.com.cipres.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import ar.com.cipres.authentication.crypto.MD5;
import ar.com.cipres.authentication.crypto.PasswordSalts;
import ar.com.cipres.dao.IUsuarioDAO;
import ar.com.cipres.model.Usuario;
import ar.com.cipres.util.RolValues;
import ar.com.cipres.wrapper.UsuarioWrapper;


@Repository
public class UsuarioDAO extends GenericDAO<Usuario> implements IUsuarioDAO {

	private String[] defaultOrderCriteria = { "apellido", "nombre" };

	public UsuarioDAO() {
		super(Usuario.class);
	}

	protected String[] getDefaultOrderCriteria() {
		return defaultOrderCriteria;
	}
	
	@Override
	public List<Usuario> getOperadores(Boolean locked) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(Usuario.class)
				.add(Restrictions.eq("rol.id", RolValues.OPERADOR.getId()))
				.addOrder(Order.asc("apellido"))
				.addOrder(Order.asc("nombre"));
		
		if (locked != null){
		    crit.add(Restrictions.eq("locked", locked));
		}
		
		return crit.list();
	}
	
	public Serializable save(Usuario usuario) {
		String salt = PasswordSalts.nextSalt();
		String saltMd5 = MD5.getMD5(salt);
		String passMd5 = MD5.getMD5(usuario.getPassword());
		String passSaltMd5 = MD5.getMD5(saltMd5.concat(passMd5));
		usuario.setSalt(salt);
		usuario.setPassword(passSaltMd5);
		return sessionFactory.getCurrentSession().save(usuario);
	}
	
	public void updateUser(UsuarioWrapper usuarioWrapper) throws DataAccessException {
		Usuario usuario = this.getByPrimaryKey(usuarioWrapper.getId());
		usuario.setApellido(usuarioWrapper.getApellido());
		usuario.setNombre(usuarioWrapper.getNombre());
		usuario.setRol(usuarioWrapper.getRol());
		usuario.setUsername(usuarioWrapper.getUsername());
		usuario.setLocked(usuarioWrapper.getLocked());
		usuario.setUsersk(usuarioWrapper.getUsersk());
		usuario.setPrintLabel(usuarioWrapper.getPrintLabel());
		usuario.setUseBulkLoad(usuarioWrapper.isUseBulkLoad());
		usuario.setVendedor(usuarioWrapper.isVendedor());
		this.update(usuario);
	}
	
	@Transactional(readOnly = false)
	public void changePassw(Integer id, String password) {
		String salt = PasswordSalts.nextSalt();
		String saltMd5 = MD5.getMD5(salt);
		String passMd5 = MD5.getMD5(password);
		String passSaltMd5 = MD5.getMD5(saltMd5.concat(passMd5));
		
		Usuario usuario = this.getByPrimaryKey(id);
		usuario.setSalt(salt);
		usuario.setPassword(passSaltMd5);
		this.update(usuario);
	}
	
	public Usuario getByUsername(String username) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(Usuario.class)
				.add(Restrictions.eq("username", username))
				.add(Restrictions.eq("locked", false));
		
		return (Usuario) crit.uniqueResult();
	}

	public List<Usuario> getVendedores(Boolean vendedor) {
		Criteria crit = sessionFactory.getCurrentSession().createCriteria(Usuario.class)
				.add(Restrictions.eq("vendedor", vendedor))
				.addOrder(Order.asc("apellido"))
				.addOrder(Order.asc("nombre"));
		
		return crit.list();
	}
	
	
}
