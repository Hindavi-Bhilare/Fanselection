
package com.velotech.fanselection.utils;

import java.sql.Types;

public class SQLServerUnicodeDialect extends org.hibernate.dialect.SQLServer2008Dialect {

	public SQLServerUnicodeDialect() {
		super();
		registerColumnType(Types.NUMERIC, "decimal($1,0)");
		registerColumnType(Types.BIGINT, "decimal($1,0)");
		registerColumnType(Types.DOUBLE, "decimal($p,$s)");
	}
}
