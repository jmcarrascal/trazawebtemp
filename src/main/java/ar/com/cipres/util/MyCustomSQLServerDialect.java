package ar.com.cipres.util;

import org.hibernate.dialect.SQLServerDialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;


public class MyCustomSQLServerDialect extends SQLServerDialect {
	public MyCustomSQLServerDialect() {
		super();
		registerFunction("datepart_day", new SQLFunctionTemplate(StandardBasicTypes.INTEGER,
				"datepart(day, ?1)"));
		registerFunction("datepart_month", new SQLFunctionTemplate(StandardBasicTypes.INTEGER,
				"datepart(month, ?1)"));
		registerFunction("datepart_year", new SQLFunctionTemplate(StandardBasicTypes.INTEGER,
				"datepart(year, ?1)"));
	}
}