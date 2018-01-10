/*Below snippet of code picks the properties file from the class path provided while executing the jar File
Use the below command to execute the jar file:
java -cp ./:<location to properties files>:<location to jar file> <package_name>.<main class name> <parameters(if any)>
*/
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;

import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.PropertyConfigurator;

public class CommonUtility {
	
	Logger log = LoggerFactory.getLogger(CommonUtility.class);
	Properties props = loadProps();
	
	public void setLogProperties(){
		try{
			Properties logProperties=new Properties();
			BasicConfigurator.resetConfiguration();
			logProperties.load(ClassLoader.getSystemResourceAsStream("log4j.properties"));
			PropertyConfigurator.configure(logProperties);
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
    
	public Properties loadProps(){
		
		Properties props = new Properties();
		try{
			props.load(ClassLoader.getSystemResourceAsStream("config.properties"));
		}catch(FileNotFoundException fe){
			fe.printStackTrace();
		}catch(IOException ioe){
			ioe.printStackTrace();
		}
        return props;
	}
}
