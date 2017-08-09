package ar.com.cipres.rest.config;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.hibernate.EmptyInterceptor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import ar.com.cipres.model.Parametrizacion;

@PropertySource("classpath:database.properties")
public class HibernateInterceptor extends EmptyInterceptor {

	private DataSource dataSource;

	Parametrizacion param = new Parametrizacion();

	public String onPrepareStatement(String sql) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		//System.out.println(param.getValor());
		return sql;
	}

	@PostConstruct
	public void getCommon() {
		
		 System.setProperty("com.mchange.v2.log.FallbackMLog.DEFAULT_CUTOFF_LEVEL", "SEVERE");
		 System.setProperty("com.mchange.v2.log.MLog", "com.mchange.v2.log.FallbackMLog");
		 System.out.println("SET LEVEL LOG C3P0");
//		Connection conn;
//		try {
//			conn = dataSource.getConnection();
//			Statement stmt = conn.createStatement();
//			ResultSet rs = stmt.executeQuery("SELECT * from trazaweb.parametrizacion");
//
//			if (rs.next()) {
//				param.setValor(rs.getString("valor"));
//
//			}
//			rs.close();
//			stmt.close();
//			conn.close();
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}

	}

	public DataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}
}