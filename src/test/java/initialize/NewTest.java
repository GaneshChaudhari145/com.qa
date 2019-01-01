package initialize;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.mail.internet.MimeMultipart;
import javax.sql.DataSource;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

import basepage.LoginPage;
import property.LoadProperties;
import retry.Retry;
import util.ReadExcel;
import util.WriteExcel;


public class NewTest {
	public static WebDriver driver;
	public  ExtentReports extent;
	public  ExtentTest extenttest;

	XSSFWorkbook wb;
	XSSFSheet sheet;
	@Test(description="Invalid Login Scenario",retryAnalyzer=Retry.class)
	public void InvalidLogincenario() throws IOException, InterruptedException {

		extenttest=extent.startTest("Invalid Login Scenario");
		LoginPage login =new LoginPage(driver);
		WriteExcel writeExcel = new WriteExcel();
		ReadExcel readExcel = new ReadExcel();
		wb = ReadExcel.getWb();
		sheet = wb.getSheet("Login");
		Iterator<Row> rowiterator = sheet.rowIterator();
		rowiterator.next();
		while (rowiterator.hasNext()) {
			Row row = rowiterator.next();
			login.loginApplication(row.getCell(1).getStringCellValue());
			writeExcel.fillExcelFile(row, login.fetchvalue());
		}
		writeExcel.writeResultFile(ReadExcel.getWb());
	}

	@BeforeTest
	public void setExtent() {
		extent=new ExtentReports(System.getProperty("user.dir")+"/test-output/ExtentReport.html",true);
		extent.addSystemInfo("Host Name","AQM");
		extent.addSystemInfo("User Name","Temp");
		extent.addSystemInfo("Enviornment","QA");
	}

	@BeforeMethod
	public void launch() throws IOException {
		
		Properties prop=LoadProperties.propertyLoader();
		System.setProperty("webdriver.chrome.driver","D:\\chromedriver_win32 (1)\\chromedriver.exe");
		
		DesiredCapabilities capability=DesiredCapabilities.chrome();
		capability.setBrowserName("chrome");
		capability.setPlatform(Platform.WIN10);
		String gridURL = prop.getProperty("GridURL");
		driver=new RemoteWebDriver(new URL(gridURL), capability);
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30,TimeUnit.SECONDS);
		driver.get(prop.getProperty("Url"));
	}

	@AfterMethod
	public void afterTest(ITestResult result) throws InterruptedException, IOException, DocumentException, MessagingException {
		Thread.sleep(3000);
		if(result.getStatus()==ITestResult.FAILURE) {
			extenttest.log(LogStatus.FAIL, "Test case Failed is"+result.getName());
			extenttest.log(LogStatus.FAIL, "Test case Failed is"+result.getThrowable());
			String screenshotpath=NewTest.getScreenshot(driver, result.getName());
			//extenttest.log(LogStatus.FAIL, extenttest.addScreencast(screenshotpath));///for video
			extenttest.log(LogStatus.FAIL,extenttest.addScreenCapture(screenshotpath));//for screenshot

			getScreenshotinPDF(driver, result.getName());///for PDf
		}else if(result.getStatus()==ITestResult.SKIP) {
			extenttest.log(LogStatus.SKIP, "Test case Skipped is"+result.getName());
		}
		else if(result.getStatus()==ITestResult.SUCCESS) {
			extenttest.log(LogStatus.PASS, "Test case Passed is"+result.getName());
		}
		extent.endTest(extenttest);	
		//driver.close();
		driver.quit();
		String to[] = {"sunny.patil@aqmtechnologies.com"};
		String cc[] = {"ganesh.chaudhari@aqmtechnologies.com"};
		//send("ganesh.chaudhari@aqmtechnologies.com", to,"PDF Report","Check the PDF attachment.",cc);
	}
	public static void send(String from, String tos[], String subject,String text,String ccs[]) throws MessagingException, IOException, DocumentException {
		String screenshotName;
		String dateName;
		Properties props = new Properties();
	    props.put("mail.smtp.host", "smtp.rediffmailpro.com");
	    props.put("mail.smtp.socketFactory.port", "465");
	    props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
	    props.put("mail.smtp.auth", "true");
	    props.put("mail.smtp.port", "465");
	    Session session = Session.getDefaultInstance(props,
	        new javax.mail.Authenticator() {
	          protected PasswordAuthentication getPasswordAuthentication() {
	            return new PasswordAuthentication(
	            "ganesh.chaudhari@aqmtechnologies.com",
	            "Gane$h66");// change accordingly
	          }
	        });
	    try {
		    MimeMessage message = new MimeMessage(session);
		      message.setFrom(new InternetAddress(from));// change accordingly
		      for (String to : tos) {
		        message.addRecipient(Message.RecipientType.TO,
		            new InternetAddress(to));
		      }
		      for(String cc : ccs) {
		    	  message.addRecipient(Message.RecipientType.CC, new InternetAddress(cc));
		      }
		      message.setSubject(subject);
		  
			   String filename ="D:\\AQM\\com.qa\\FailedScreeenShotPDFInvalidLogincenario.pdf";
		     
		      BodyPart objMessageBodyPart = new MimeBodyPart();
		      // Option 3: Send text along with attachment
		      objMessageBodyPart.setContent(
		          "<h1>Mail from Selenium Project!</h1></br>" + text, "text/html");
		      Multipart multipart = new MimeMultipart();
		      multipart.addBodyPart(objMessageBodyPart);
		      objMessageBodyPart = new MimeBodyPart();
		      FileDataSource source = new FileDataSource(filename);
		      objMessageBodyPart.setDataHandler(new DataHandler(source));
		      objMessageBodyPart.setFileName(filename);
		      multipart.addBodyPart(objMessageBodyPart);
		      message.setContent(multipart);
		      Transport.send(message);
		      System.out.println("message sent successfully");
		    } catch (MessagingException e) {
		      throw new RuntimeException(e);
		    }
	}
	@AfterTest
	public void endReport() {
		extent.flush();
		extent.close();
	}

	public static String  getScreenshot(WebDriver driver,String screenshotName) throws IOException {
		String dateName=new SimpleDateFormat("YYYYMMDD").format(new Date());
		TakesScreenshot ts= (TakesScreenshot)driver;
		File src=ts.getScreenshotAs(OutputType.FILE);
		String destination=System.getProperty("user.dir")+"/FailedScreeenShot"+screenshotName+dateName+".png";
		File finaldestination=new File(destination);
		FileUtils.copyFile(src, finaldestination);
		return destination;
	}
		//static byte[] input = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
		public static String  getScreenshotinPDF(WebDriver driver,String screenshotName) throws IOException, DocumentException{
			byte[] input = ((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES);
		Document document = new Document();
		String output = System.getProperty("user.dir")+"/FailedScreeenShotPDF"+screenshotName+".pdf";
		FileOutputStream fos = new FileOutputStream(output);
		PdfWriter writer = PdfWriter.getInstance(document, fos);
		writer.open();
		document.open();
		Image im = Image.getInstance(input);
		//im.scaleToFit(PageSize.A4.getWidth()/2, PageSize.A4.getHeight()/2);
		im.scaleToFit(PageSize.A7.getWidth(), PageSize.A7.getWidth());
		document.add(im);
		document.add(new Paragraph(" "));
		document.close();
		writer.close();
		return output;
	}
}
