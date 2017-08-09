package ar.com.cipres.dao;

import java.util.List;

import ar.com.cipres.model.Usuario;
import ar.com.cipres.wrapper.UsuarioWrapper;


public interface IUsuarioDAO extends IGenericDAO<Usuario> {
	
	public List<Usuario> getOperadores(Boolean locked);
	
	public List<Usuario> getVendedores(Boolean isvendedor);

	public void updateUser(UsuarioWrapper usuario);

	public void changePassw(Integer id, String password);

	public Usuario getByUsername(String username);
	
}
