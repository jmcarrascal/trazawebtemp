package ar.com.cipres.dao;

import java.util.List;

import ar.com.cipres.model.Existencias;
import ar.com.cipres.model.UsuariosExistencias;

public interface IUsuariosExistenciasDAO extends IGenericDAO<UsuariosExistencias> {
	public List<UsuariosExistencias> getExistenciasByidUsuario(Integer idUsuario);

	public boolean deleteByIdUsuario(int idUsuario);
}
