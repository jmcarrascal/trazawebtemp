package ar.com.cipres.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.springframework.stereotype.Repository;

import ar.com.cipres.dao.IDomiciliosDAO;
import ar.com.cipres.model.Domicilios;

@Repository
public class DomiciliosDAO extends GenericDAO<Domicilios> implements IDomiciliosDAO {

	private String[] defaultOrderCriteria = { "id" };

	protected String[] getDefaultOrderCriteria() {
		return defaultOrderCriteria;
	}

	public DomiciliosDAO() {
		super(Domicilios.class);
	}

	public Domicilios getDomicilioPrincipal(Integer genteNr) {
		List<Domicilios> domiciliosList = null;
		Domicilios domicilios = null;

		try {
			Query query = sessionFactory.getCurrentSession().createQuery(
					"select d from Domicilios d where d.genteId = " + genteNr + "and d.domicilioPrincipal = -1");
			domiciliosList = query.list();
			if (domiciliosList != null && domiciliosList.size() > 0) {
				domicilios = domiciliosList.get(0);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return domicilios;
	}

	public List<Object> getDomicilioList(Integer genteId) {
		List<Object> domiciliosList = null;

		try {
			Query query = sessionFactory.getCurrentSession()
					.createQuery("select d from Domicilios d where d.genteId = " + genteId);
			domiciliosList = query.list();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return domiciliosList;
	}

	public List<Object> getDomicilioList(Integer genteId, String texto, Boolean principal, Boolean entrega,
			Boolean cobranza, Boolean mailing, int pageNumber, int pageSize, Boolean alternativo) {
		List<Object> domiciliosList = null;

		try {
			String queryStr;

			if (genteId == -500) {
				// SI NO TIENE COMPLETA LA GEO REFERENCIACIÓN PROVISORIO HASTA
				// QUE ACTUALICEN TODOS LOS DOMICILIOS
				queryStr = "select d from Domicilios d where domicilio = '' OR localidad = '' OR provincia = '' OR pais = ''"
						+ " OR domicilio IS NULL OR localidad IS NULL OR provincia IS NULL OR pais IS NULL";

			} else {
				queryStr = "select d from Domicilios d where d.genteId = " + genteId + " ";
				if (texto.length() > 0) {
					queryStr += "and (d.descripcion like '%" + texto + "%' or d.domicilio like '%" + texto
							+ "%' or d.nrAfiliado like '%" + texto + "%' ) ";
				}
				if (principal) {
					queryStr += "and d.domicilioPrincipal = -1 ";
				}

				if (entrega) {
					queryStr += "and d.domicilioEntrega = -1 ";
					if (!alternativo) {
						queryStr += "and GLN is not null ";
					} else {
						queryStr += "AND (GLN is null OR GLN = '') ";
					}
				}
				if (cobranza) {
					queryStr += "and d.domicilioCobranza = -1 ";
				}
				if (mailing) {
					queryStr += "and d.domicilioMailing = -1 ";
				}
			}
			Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
			query.setFirstResult((pageNumber * pageSize) - pageSize);
			query.setMaxResults(pageSize);
			domiciliosList = query.list();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return domiciliosList;
	}

	public Long getDomicilioListCount(Integer genteId, String texto, Boolean principal, Boolean entrega,
			Boolean cobranza, Boolean mailing, Boolean alternativo) {
		Long count = 0l;

		try {
			String queryStr = "";

			if (genteId == -500) {
				// SI NO TIENE COMPLETA LA GEO REFERENCIACIÓN PROVISORIO HASTA
				// QUE ACTUALICEN TODOS LOS DOMICILIOS
				queryStr = "select d from Domicilios d where domicilio = '' OR localidad = '' OR provincia = '' OR pais = ''"
						+ " OR domicilio IS NULL OR localidad IS NULL OR provincia IS NULL OR pais IS NULL";

			} else {
				queryStr = "select count(d) from Domicilios d where d.genteId = " + genteId + " ";
				if (texto.length() > 0) {
					queryStr += "and (d.descripcion like '%" + texto + "%' or d.domicilio like '%" + texto
							+ "%' or d.nrAfiliado like '%" + texto + "%' ) ";
				}
				if (principal) {
					queryStr += "and d.domicilioPrincipal = -1 ";
				}
				if (entrega) {
					queryStr += "and d.domicilioEntrega = -1 ";
					if (!alternativo) {
						queryStr += "and GLN is not null ";
					} else {
						queryStr += "AND (GLN is null OR GLN = '') ";
					}
				}
				if (cobranza) {
					queryStr += "and d.domicilioCobranza = -1 ";
				}
				if (mailing) {
					queryStr += "and d.domicilioMailing = -1 ";
				}
			}
			Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
			count = (Long) query.list().get(0);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return count;
	}

	public Domicilios getDomicilioAgendadoAfiliado(int obraSocial, String afiliado) {

		List<Domicilios> domiciliosList = null;
		Domicilios domicilios = null;

		try {
			Query query = sessionFactory.getCurrentSession().createQuery("select d from Domicilios d where d.genteId = "
					+ obraSocial + "and LTRIM(RTRIM(d.nrAfiliado)) = :afiliado");
			query.setString("afiliado", afiliado);
			domiciliosList = query.list();
			if (domiciliosList != null && domiciliosList.size() > 0) {
				domicilios = domiciliosList.get(0);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return domicilios;
	}

	@Override
	public List<Domicilios> getAgendadoDomicilioTipo(Integer idGente, Boolean principal, Boolean entrega,
			Boolean cobranza, Boolean mailing) {
		List<Domicilios> domiciliosList = new ArrayList<Domicilios>();

		try {
			String queryStr = "select d from Domicilios d where d.genteId = " + idGente + " ";

			if (principal) {
				queryStr += "and d.domicilioPrincipal = -1 ";
			}
			if (entrega) {
				queryStr += "and d.domicilioEntrega = -1 ";
			}
			if (cobranza) {
				queryStr += "and d.domicilioCobranza = -1 ";
			}
			if (mailing) {
				queryStr += "and d.domicilioMailing = -1 ";
			}
			// TESTE PUSH
			Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
			domiciliosList = query.list();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return domiciliosList;
	}

	@Override
	public List<Domicilios> getAgendadoDomicilioByAlternativo(Integer idGente, Boolean principal, Boolean entrega,
			Boolean cobranza, Boolean mailing, Boolean alternativo) {
		List<Domicilios> domiciliosList = new ArrayList<Domicilios>();

		try {
			String queryStr = "select d from Domicilios d where d.genteId = " + idGente + " ";

			if (principal) {
				queryStr += "and d.domicilioPrincipal = -1 ";
			}
			if (entrega) {
				queryStr += "and d.domicilioEntrega = -1 ";
				if (!alternativo) {
					queryStr += "and GLN is not null ";
				} else {
					queryStr += "AND (GLN is null OR GLN = '') ";
				}
			}
			if (cobranza) {
				queryStr += "and d.domicilioCobranza = -1 ";
			}
			if (mailing) {
				queryStr += "and d.domicilioMailing = -1 ";
			}
			// TESTE PUSH
			Query query = sessionFactory.getCurrentSession().createQuery(queryStr);
			domiciliosList = query.list();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		return domiciliosList;
	}

}
