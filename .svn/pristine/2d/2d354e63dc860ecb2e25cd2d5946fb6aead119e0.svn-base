
package com.velotech.fanselection.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ResourceUtil {

	private InputStream in = null;

	Properties props = new Properties();

	/**
	 * This method is used to get the values from the propertiesd file based on
	 * the key provided
	 * 
	 * @param bundle
	 *            - the key to be read from the properties file
	 * @return String - the value of the key
	 */
	public String getPropertyValues(String bundle) throws IOException {

		in = getClass().getResourceAsStream("/key.properties");
		props.load(in);
		String value = props.getProperty(bundle);
		return value;
	}

	public Properties getLDAPProperty() {

		in = getClass().getResourceAsStream("/login.properties");
		try {
			props.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return props;
	}

	public String getProductDrawings(String productDrawings) throws IOException {

		String value = null;

		in = getClass().getResourceAsStream("/application.properties");
		props.load(in);
		value = props.getProperty(productDrawings);
		return value;

	}

	public String getDatabaseurl() throws IOException {

		String value = null;

		in = getClass().getResourceAsStream("/application.properties");
		props.load(in);
		value = props.getProperty("spring.datasource.url");
		return value;

	}

	public String getDatabaseUsername() throws IOException {

		String value = null;

		in = getClass().getResourceAsStream("/application.properties");
		props.load(in);
		value = props.getProperty("spring.datasource.username");
		System.out.println(value);
		return value;
	}

	public String getDatabasePassword() throws IOException {

		String value = null;
		in = getClass().getResourceAsStream("/application.properties");
		props.load(in);
		value = props.getProperty("spring.datasource.password");
		return value;
	}

	public String getDatabaseDriverClassName() throws IOException {

		String value = null;

		in = getClass().getResourceAsStream("/application.properties");
		props.load(in);
		value = props.getProperty("spring.datasource.driverClassName");
		System.out.println(value);
		return value;
	}

	public String getSMBUrl() throws IOException {

		String value = null;

		in = getClass().getResourceAsStream("/application.properties");
		props.load(in);
		value = props.getProperty("smbUrl");
		System.out.println(value);
		return value;
	}

	public Properties getApplicationProperty() {

		try {
			in = getClass().getResourceAsStream("/application.properties");
			props.load(in);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return props;
	}
}
