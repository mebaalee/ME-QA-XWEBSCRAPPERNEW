package demo;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

import java.io.File;
import java.time.Duration;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class TestCases {
    WebDriver driver;
    String userDir = System.getProperty("user.dir");

    @BeforeSuite
    public void setup() {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterTest
    public void tearDown() {
        driver.close();
        driver.quit();
    }

    @Test(priority = 1)
    public void hockeyTeamDetails() throws InterruptedException {
        try {
            System.out.println("Test case 01 Starts : Get the Team details and Store the file in the root directory ");
            driver.get("https://www.scrapethissite.com/pages/");

            //Click on the Hockey team link on the page
            driver.findElement(By.xpath("//a[contains(text(),'Hockey Teams: Forms, Searching and Pagination')]")).click();

            //New ArrayList contains HaspMap with String and Object
            ArrayList<HashMap<String, Object>> al = new ArrayList<>();

            //New HashMap contains String and Object
            HashMap<String, Object> hm = new HashMap<>();

            //New Object mapper
            ObjectMapper mapper = new ObjectMapper();

            //Iterate through the pages up to 5
            for (int i = 1; i <= 5; i++) {
                //Locate nextButton
                WebElement we = driver.findElement(By.xpath("//a[normalize-space()='" + i + "']"));
                //Clicking the next page button
                WrapperAction.clickLink(we, driver);


                //Storing Win%, Year, Name in a List
                List<WebElement> winList = driver.findElements(By.xpath("//table[@class='table']/tbody/tr/td[contains(@class,'pct text')]"));
                List<WebElement> yearList = driver.findElements(By.xpath("//table[@class='table']/tbody/tr/td[@class='year']"));
                List<WebElement> nameList = driver.findElements(By.xpath("//table[@class='table']/tbody/tr/td[@class='name']"));

                //Iterate through the List to get details
                for (int j = 0; j < winList.size(); j++) {
                    String win = winList.get(j).getText();
                    double percentage = (Double.parseDouble(win)) * 100;
                    String year = yearList.get(j).getText();
                    String name = nameList.get(j).getText();
                    String time = WrapperAction.getepoch();

                    //Adding hm values to a List
                    if (percentage < 40 && percentage > 0) {
                        hm.put("Name", name);
                        hm.put("Year", year);
                        hm.put("Win%", win);
                        hm.put("Time", time);
                        al.add(hm);
                    }
                }
            }

            //creating a new file in the local
            File fileName = new File(userDir + "\\src\\test\\resources\\hockey-team-winner-data.json");
            //Converting map to JSON payload as string
            WrapperAction.convertJSON(al, mapper);
            //Writing JSON on the local  file
            WrapperAction.writeJSON(al, mapper, fileName);
            //Asserts specified file exists
            boolean exists = WrapperAction.fileExists(fileName);
            Assert.assertTrue(exists, "File not exists in the local");
            //Asserts the file contains text
            boolean text = WrapperAction.fileEmpty(fileName);
            Assert.assertTrue(text, "Text not Present in the file");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test(priority = 2)
    public void OscarFilmsDetails() {
        try {
            System.out.println("Test case 02 Starts : Get the Oscar Film details and Store the file in the root directory ");
            driver.get("https://www.scrapethissite.com/pages/");

            //Click on the Oscar film link
            WebElement we = driver.findElement(By.xpath("//a[contains(text(),'Oscar Winning Films: AJAX and Javascript')]"));
            WrapperAction.clickLink(we, driver);

            //Storing years in a List
            List<WebElement> years = driver.findElements(By.xpath("//div[contains(@class,'col-md-12 text-center')]/a"));

            //New ArrayList contains HaspMap with String and Object
            ArrayList<HashMap<String, Object>> al = new ArrayList<>();

            //New HashMap contains String and Object
            HashMap<String, Object> hm = new HashMap<>();

            //New Object mapper
            ObjectMapper mapper = new ObjectMapper();

            //Click on the next year
            for (WebElement year : years) {
                if (year.isEnabled()) {
                    year.click();
                    Thread.sleep(3000);

                    //Storing titles, nominations,awards,active year in a List
                    List<WebElement> filmList = driver.findElements(By.xpath("//table[@class ='table']/tbody/tr/td[@class='film-title']"));
                    List<WebElement> nominationList = driver.findElements(By.xpath("//table[@class ='table']/tbody/tr/td[@class='film-nominations']"));
                    List<WebElement> awardsList = driver.findElements(By.xpath("//table[@class ='table']/tbody/tr/td[@class='film-awards']"));
                    WebElement yearElement = driver.findElement(By.xpath("//a[@class ='year-link active']"));
                    WrapperAction.getActiveYear(yearElement, driver);

                    //Iterate through the List to get details
                    for (int i = 0; i <= 4; i++) {
                        String film = filmList.get(i).getText();
                        String nominations = nominationList.get(i).getText();
                        String awards = awardsList.get(i).getText();
                        String time = WrapperAction.getepoch();

                        //Adding hm values to a List
                        hm.put("Film", film);
                        hm.put("Nominations", nominations);
                        hm.put("Awards", awards);
                        hm.put("Time", time);
                        al.add(hm);
                    }
                    //creating a new file in the local
                    File fileName = new File(userDir + "\\src\\test\\resources\\oscar-winner-data.json");
                    //Converting map to JSON payload as string
                    WrapperAction.convertJSON(al, mapper);
                    //Writing JSON on the local  file
                    WrapperAction.writeJSON(al, mapper, fileName);
                    //Asserts specified file exists
                    boolean exists = WrapperAction.fileExists(fileName);
                    Assert.assertTrue(exists, "File not exists in the local");
                    //Asserts the file contains text
                    boolean text = WrapperAction.fileEmpty(fileName);
                    Assert.assertTrue(text, "Text not Present in the file");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
