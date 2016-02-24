package com.dbsys.rs.serve;

public class DbConnectionConfig {
//	public static final String HOST = "mysql31535-core-unitedvision.whelastic.net";
	public static final String HOST = "localhost";
	public static final String PROPERTY_NAME_DATABASE_DRIVER = "com.mysql.jdbc.Driver";
    public static final String PROPERTY_NAME_DATABASE_URL = String.format("jdbc:mysql://%s:3306/rumkit", HOST);
    public static final String PROPERTY_NAME_DATABASE_USERNAME = "rumkit_user";
    public static final String PROPERTY_NAME_DATABASE_PASSWORD = "liun";
}
