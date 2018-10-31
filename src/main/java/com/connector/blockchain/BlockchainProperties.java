package com.connector.blockchain;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class BlockchainProperties {
	
	public enum PropertyTypes {
		CONNECTION_URL,
		BUNKER_STORAGE_CONTRACT_ADDRESS
	}
	private InputStream inputStream;
	Properties prop;
	
	public String getProperties(PropertyTypes property) {
		try {
			this.prop = new Properties();
			String propFileName = "config.properties";
			inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
			if (inputStream != null) {
				prop.load(inputStream);
			} else {
				throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
			}
 		} catch (Exception e) {
			System.out.println("Exception: " + e);
		} finally {
			try {
				inputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}	
		}
		
		String requiredProps = null;
		switch(property) {
			case CONNECTION_URL: 
				requiredProps = prop.getProperty("CONNECTION_URL");
				break;
			case BUNKER_STORAGE_CONTRACT_ADDRESS:
				requiredProps = prop.getProperty("BUNKER_CONTRACT_ADDRESS");
				break;
		}
		return requiredProps;
	}

}