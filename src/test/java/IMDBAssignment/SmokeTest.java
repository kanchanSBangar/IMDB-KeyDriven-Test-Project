package IMDBAssignment;

import Config.ActionKeywords;
import Config.Constants;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utility.ExcelUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Instant;
import java.util.Properties;

public class SmokeTest {

    ExtentReports extent;
    ExtentTest logger;
    public static ActionKeywords actionKeywords;
    public static String sActionKeyword;
    public static String sPageObject;
    public static Method method[];

    public SmokeTest() throws NoSuchMethodException, SecurityException {
        actionKeywords = new ActionKeywords();
        method = actionKeywords.getClass().getMethods();

    }

    private static void execute_Actions() throws Exception {
        for (int i = 0; i < method.length; i++) {
            if (method[i].getName().equals(sActionKeyword)) {
                Class[] paramTypes = method[i].getParameterTypes();
                Object[] paramValues = new Object[1];

                if (paramTypes.length == 0) {
                    try {
                        method[i].invoke(actionKeywords);
                    } catch (Exception e) {

                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
                }
                if (paramTypes[0].equals(String.class)) {
                    paramValues[0] = sPageObject;
                } else if (paramTypes[0].equals(Integer.TYPE)) {
                    paramValues[0] = 2;
                }
                if (paramValues[0] != null) {

                    try {
                        method[i].invoke(actionKeywords,paramValues);
                    } catch (Exception e) {

                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    break;
                }
            }
        }
    }

    @BeforeTest
    public void startReport() throws IOException {

        extent = new ExtentReports(System.getProperty("user.dir") + "/test-output/smoke.html", true);
        extent
                .addSystemInfo("Host Name", "IMDN")
                .addSystemInfo("Environment", "TEST")
                .addSystemInfo("User Name", "KanchanBangar");
        extent.loadConfig(new File("extent-config.xml"));

    }

    @AfterTest
    public void endReport() {
        //Write info to your report
        extent.flush();
        //close() - To close all the operation
        extent.close();
    }

    @AfterMethod
    public void getResult(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getName());
            logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getThrowable());
        } else if (result.getStatus() == ITestResult.SKIP) {
            logger.log(LogStatus.SKIP, "Test Case Skipped is " + result.getName());
        }
        // ending test
        //endTest(logger) : It ends the current test and prepares to create HTML report
        extent.endTest(logger);
    }

    @Test
    public void InsertMovieListDetails_in_DB() throws Exception {
        logger = extent.startTest("Test 1 : Insert Movie list details in DB");
        logger.log(LogStatus.INFO, "Navigate to IMDB site");       


        String sPath = System.getProperty("user.dir") + "/testData/testData.xlsx";
        ExcelUtils.setExcelFile(sPath, "TestSteps");

        for (int iRow = 1; iRow <= 5; iRow++) {

            sActionKeyword = ExcelUtils.getCellData(iRow, Constants.Col_ActionKeyword);
            sPageObject = ExcelUtils.getCellData(iRow, Constants.Col_PageObject);


            execute_Actions();
        }
    }



}
