package sugama_tourists_selenium;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.logging.Logger;
/**
 * @author Kiran K 
 */
public class config_reader {

	public config_reader() {
		// TODO Auto-generated constructor stub
	}
	 public static ArrayList<String> get_prop()
	    {
	    	Properties prop = new Properties();
	    	ArrayList<String> s= new ArrayList<String>();
	    	try {
	               //load a properties file
	    		prop.load(new FileInputStream("config"));
	    			
	               //get the property value and print it out
	               s.add(prop.getProperty("Source"));
	               s.add(prop.getProperty("Destination"));
	               s.add(prop.getProperty("Month"));
	               s.add(prop.getProperty("Date"));
	               s.add(prop.getProperty("output_path"));
	               
	               s.add(prop.getProperty("shutdown"));
	              
	    	} catch (IOException ex) {
	    		System.out.println("error reading config class");
	    		ex.printStackTrace();
	        }
			return s;
	    }
}
