package Config;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class ActionKeywords {

    public static WebDriver driver;

    public static InputStream inputStream = null;
    private static Properties properties = new Properties();

    public ActionKeywords() {
        try {
            inputStream = new FileInputStream("D:\\intellij\\IMDBassignment\\src\\main\\resources\\PageObject.properties");
            properties.load(inputStream);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public static void openBrowser(String browser) {
        switch (browser.toLowerCase()) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", Constants.chromeDriver);
                driver = new ChromeDriver();
                break;
            case "firefox":
                driver = new FirefoxDriver();
                break;
        }
    }

    public static void navigate(String object) {
        driver.get(Constants.URL);
    }

    public static void mouseHover(String path) throws IOException {

        WebElement element = driver.findElement(By.xpath(properties.getProperty(path)));
        Actions action = new Actions(driver);
        action.moveToElement(element).build().perform();
    }

    public static void click(String object) throws InterruptedException, IOException {
        //This is fetching the xpath of the element from the Object Repository property file
        WebElement element2 = (new WebDriverWait(driver, 20))
                .until(ExpectedConditions.elementToBeClickable(By.xpath(properties.getProperty(object))));

        driver.findElement(By.xpath(properties.getProperty(object))).click();
        Thread.sleep(5000);

    }

    public static void readnSave(String path) throws SQLException, ClassNotFoundException {
        List<WebElement> rows = driver.findElements(By.xpath(properties.getProperty(path)));
        String str, rank, title, year;
        SQLiteJDBCDriverConnection.connect();
        SQLiteJDBCDriverConnection.dropTable();
        SQLiteJDBCDriverConnection.createTable();

        // for every line, store both columns
        for (WebElement row : rows) {
            //WebElement poster = row.findElement(By.xpath("./td[1]"));
            //System.out.print(poster.getText() + "\t");
            WebElement rowData = row.findElement(By.xpath("./td[2]"));
            str = rowData.getText();
            rank = str.substring(0, str.indexOf("."));
            title = str.substring(str.indexOf(".")+2,str.indexOf("("));
            year = str.substring(str.indexOf("(")+1, str.indexOf(")"));

            WebElement rating = row.findElement(By.xpath("./td[3]"));
            SQLiteJDBCDriverConnection.insertData(Integer.valueOf(rank),title,Integer.valueOf(year),Double.valueOf(rating.getText()));

        }
        SQLiteJDBCDriverConnection.readData();
        SQLiteJDBCDriverConnection.closeDB();
    }


    public static void closeBrowser() {
        driver.quit();
    }
}
