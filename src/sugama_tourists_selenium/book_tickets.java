package sugama_tourists_selenium;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;

public class book_tickets {

	public static void book()
	{
		boolean success=false;
		ArrayList<String> config=config_reader.get_prop();
		WebDriver driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(5
				, TimeUnit.SECONDS);
		
        // Go to the Google Suggest home page
        driver.get("http://www.sugamatourists.com/");
        System.out.println("print choice");
        
        //source       
        WebElement points=driver.findElement(By.className("select2-choice"));        
        points.click();
       	List<WebElement> temp = driver.findElements(By.className("select2-result-label"));
        System.out.println("Select source:");
        for(WebElement temp1:temp)
        {
        	
        	if(temp1.getText().contains(config.get(0)))
        	{
        		System.out.println(temp1.getText());
        		temp1.click();
        		break;
        	}
        }
        
        //destinaitno
        WebElement dest=driver.findElement(By.id("select-update-distinations")).findElement(By.className("select2-choice"));
        dest.click();
       	List<WebElement> dest_temp = driver.findElements(By.className("select2-result-label"));
        System.out.println("Select destination:");
        for(WebElement temp1:dest_temp)
        {
        	
        	if(temp1.getText().contains(config.get(1)))
        	{
        		System.out.println(temp1.getText());
        		temp1.click();
        		break;
        	}
        }
        
        
        WebElement date_picker=driver.findElement(By.className("ui-datepicker-trigger"));
        date_picker.click();
        System.out.println("date clicked");
        WebElement month=driver.findElement(By.className("ui-datepicker-month"));
        System.out.println(month.getText());
        
        Select select = new Select(driver.findElement(By.className("ui-datepicker-month")));
        select.selectByVisibleText(config.get(2));
        
        List<WebElement> dates=driver.findElements(By.className("ui-state-default"));//("ui-datepicker-week-end"));
        for(WebElement date:dates)
        {
        	
        	if(date.getText().matches(config.get(3)))
        	{
        		System.out.println(date.getText());
        		date.click();
        		break;
        	}
        }
        WebElement submit=driver.findElement(By.id("search_submit_btn"));
        submit.click();
        System.out.println("done");
        
        System.out.println("second page: the search page");
        WebElement search_results=driver.findElement(By.id("search-results"));
        try
        {
        WebElement error=search_results.findElement(By.className("center"));
        
        System.out.println(error.getText());
        }
        catch(NoSuchElementException e)
        {
        	System.out.println("the contents for that day is present ");
        	try
        	{
        		 WebElement results=search_results.findElement(By.id("sorttable-div"));
        		 System.out.println("results table presnet");
        		 success=true;
        		 File scrFile = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        		 // 	Now you can do whatever you need to do with it, for example copy somewhere
        		 DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        		 Calendar cal = Calendar.getInstance();
        		 String date=dateFormat.format(cal.getTime());
        		 System.out.println(dateFormat.format(cal.getTime()));
        		 try {
					FileUtils.copyFile(scrFile, new File(config.get(4)+File.separator+date+".png"));
					driver.close();
			        driver.quit();
			        write_results(success,config);
			        if(config.get(5).matches("yes"))
			        {
						try {
							shutdown();
						} catch (RuntimeException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
			        }
			        return;
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					System.out.println("unable to save file ");
					e1.printStackTrace();
				}
        		 

        	}
        	catch(NoSuchElementException e1)
        	{
        		System.out.println("results table absent");
        	}
        }
        write_results(success,config);
       driver.close();
        driver.quit();
        
        
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		book();
		
	}
	public static void shutdown() throws RuntimeException, IOException {
	    String shutdownCommand;
	    String operatingSystem = System.getProperty("os.name");

	    if ("Linux".equals(operatingSystem) || "Mac OS X".equals(operatingSystem)) {
	        shutdownCommand = "shutdown -h now";
	    }
	    else if ("Windows".equals(operatingSystem)) {
	        shutdownCommand = "shutdown.exe -s -t 0";
	    }
	    else {
	        throw new RuntimeException("Unsupported operating system.");
	    }

	    Runtime.getRuntime().exec(shutdownCommand);
	    System.exit(0);
	}
	public static void write_results(boolean success,ArrayList<String> config) 
	
	{
		  DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
			 Calendar cal = Calendar.getInstance();
			 String date=dateFormat.format(cal.getTime());
	        write_to_file(date+"\t"+success+"\n",config.get(4)+File.separator+"logs.txt",true);
	       
	}
	public static void write_to_file(String content,String filename,Boolean flag)
	{
		try {			 
			//String content = "This is the content to write into file"; 
			File file = new File(filename);
			// if file doesnt exists, then create it
			if (!file.exists()) {
				file.createNewFile();
			} 
			FileWriter fw = new FileWriter(file.getAbsoluteFile(),flag);
			// if you want to append to existing file:
			//FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(content);
			bw.close(); 
			System.out.println("Done"); 
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
