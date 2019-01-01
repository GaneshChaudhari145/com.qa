package property;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LoadProperties {
	public static Properties propertyLoader() throws IOException {
		Properties prop =new Properties();
		File efile=new File("D:\\AQM\\com.qa\\config.properties");
		FileInputStream fis =new FileInputStream(efile);
		prop.load(fis);
		return prop;
	}
}
