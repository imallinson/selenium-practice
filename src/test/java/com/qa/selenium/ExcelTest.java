package com.qa.selenium;

import static org.junit.Assert.assertEquals;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.support.PageFactory;

@RunWith(Parameterized.class)
public class ExcelTest {
	private WebDriver driver;
	private static FileInputStream file;
	private static XSSFWorkbook workbook;
	private static XSSFSheet sheet;

	@Parameters
	public static Collection<Object[]> data() throws IOException {
		file = new FileInputStream(Constants.EXCELWB);
		workbook = new XSSFWorkbook(file);
		sheet = workbook.getSheetAt(0);
		
		Object[][] ob = new Object[sheet.getPhysicalNumberOfRows() - 1][4];

		for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
			ob[i - 1][0] = sheet.getRow(i).getCell(0).getStringCellValue();
			ob[i - 1][1] = sheet.getRow(i).getCell(1).getStringCellValue();
			ob[i - 1][2] = sheet.getRow(i).getCell(2).getStringCellValue();
			ob[i - 1][3] = i;
		}
		return Arrays.asList(ob);
	}
	
	private String username;
	private String password;
	private String expected;
	private int rowNum;

	public ExcelTest(String username, String password, String expected, int rowNum) {
		this.username = username;
		this.password = password;
		this.expected = expected;
		this.rowNum = rowNum;
	}
	
	@Before
	public void setup() throws IOException {
		System.setProperty("phantomjs.binary.path", Constants.PHANTOMJS);
		driver = new PhantomJSDriver();
	}

	@After
	public void teardown() {
		driver.quit();
	}

	@Test
	public void loginTest() throws IOException {
		driver.get(Constants.DEMOADD);
		DemoSiteAdd addUser = PageFactory.initElements(driver, DemoSiteAdd.class);
		addUser.addUser(username, password);
		
		driver.get(Constants.DEMOLOG);
		DemoSiteLog login = PageFactory.initElements(driver, DemoSiteLog.class);
		String actual = login.login(username, password);
		

		XSSFRow row = sheet.getRow(rowNum);
		
		XSSFCell actualCell = row.getCell(3);
		if (actualCell == null) {
			actualCell = row.createCell(3);
		}
		
		actualCell.setCellValue(actual);
		
		XSSFCell resultCell = row.getCell(4);
		if (resultCell == null) {
			resultCell = row.createCell(4);
		}
		
		try {
			assertEquals(expected, actual);
			resultCell.setCellValue("pass");
		} catch (AssertionError e) {
			resultCell.setCellValue("fail");
			Assert.fail();
		}

	}
	
	@AfterClass
	public static void last() throws IOException {
		FileOutputStream fileOut = new FileOutputStream(Constants.EXCELWB);

		workbook.write(fileOut);
		fileOut.flush();

		workbook.close();
		file.close();
	}

}
