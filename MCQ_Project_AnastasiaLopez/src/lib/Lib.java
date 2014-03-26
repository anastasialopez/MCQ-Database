package lib;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Iterator;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/* Miscellaneous static auxiliary methods */

public class Lib {

	public static boolean DEBUG = false;

	/* Please adapt default parameters below for your database setting */

	public static final String DB_URL = "jdbc:mysql://localhost:3306/";
	public static final String DB_SCHEMA = "ce832";
	public static final String DB_USER = "root";
	public static final String DB_PASSWD = "mysql";
	public static final String DB_DRIVER_CLASS = "com.mysql.jdbc.Driver";

	public static Connection getConnection(String dbUrl, String dbSchema,
			String dbUser, String dbPassword) throws SQLException {
		try {
			Class.forName(DB_DRIVER_CLASS);
		} catch (ClassNotFoundException e) {
			e.printStackTrace(System.err);
		}
		Connection result = null;
		try {
			result = DriverManager
					.getConnection(dbUrl + dbSchema, dbUser, dbPassword);
		} catch (SQLException e) {
			System.out.println("Trying to connect to database with user = " + dbUser
					+ " and empty password");
			result = DriverManager.getConnection(dbUrl + dbSchema, dbUser, "");
		}
		return result;

	}

	public static Connection getConnection() throws SQLException {
		return getConnection(DB_URL, DB_SCHEMA, DB_USER, DB_PASSWD);
	}

	public static Connection getConnection(String dbSchema) throws SQLException {
		return getConnection(DB_URL, dbSchema, DB_USER, DB_PASSWD);
	}

	public static Connection getConnectionFromContext(String dbSchema)
			throws NamingException, SQLException {
		InitialContext initContext = new InitialContext();
		Context ctx = (Context) initContext.lookup("java:/comp/env");
		DataSource ds = (DataSource) ctx.lookup("jdbc/" + dbSchema);
		System.out.println("DataSource=" + ds);
		return ds.getConnection();
	}

	public static String toHtmlTableRow(Object... objs) {
		StringBuffer sb = new StringBuffer();
		sb.append("<tr>");
		for (Object obj : objs)
			sb.append("<td>" + obj + "</td>");
		sb.append("</tr>\n");
		return sb.toString();
	}

	/*
	 * static equal methods which deals with identity and null objects first
	 */

	public static boolean equals(Object x, Object y) {
		if (x == y)
			return true;
		if (x == null)
			return y == null;
		if (y == null)
			return false;
		return x.equals(y);
	}

	/*
	 * join objects into one string by interspersing with separator
	 * 
	 * skips over null objects
	 */

	public static <A> String join(String sep, Object... objs) {
		StringBuffer result = new StringBuffer();
		for (int i = 0; i < objs.length; i++) {
			Object obj = objs[i];
			if (obj != null) {
				result.append(obj);
				if (i < objs.length - 1) {
					result.append(sep);
				}
			}
		}
		return result.toString();
	}

	/*
	 * join objects into one string by interspersing with separator
	 * 
	 * skips over null objects (Iterable version)
	 */

	public static <A> String joinIterable(String sep, Iterable<A> objs) {
		StringBuffer result = new StringBuffer();
		Iterator<A> iter = objs.iterator();
		while (iter.hasNext()) {
			A a = iter.next();
			if (a != null) {
				result.append(a);
				if (iter.hasNext())
					result.append(sep);
			}
		}
		return result.toString();
	}

}
