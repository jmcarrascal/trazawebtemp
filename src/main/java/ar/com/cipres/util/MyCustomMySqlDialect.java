package ar.com.cipres.util;

import org.hibernate.dialect.MySQL5Dialect;
import org.hibernate.dialect.function.SQLFunctionTemplate;
import org.hibernate.type.StandardBasicTypes;


public class MyCustomMySqlDialect extends MySQL5Dialect {
	public MyCustomMySqlDialect() {
		super();
		registerFunction("datepart_day", new SQLFunctionTemplate(StandardBasicTypes.INTEGER,
				"day(?1)"));
		registerFunction("datepart_month", new SQLFunctionTemplate(StandardBasicTypes.INTEGER,
				"month(?1)"));
		registerFunction("datepart_year", new SQLFunctionTemplate(StandardBasicTypes.INTEGER,
				"year(?1)"));
	}
}