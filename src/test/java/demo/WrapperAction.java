package demo;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class WrapperAction {

    public static void clickLink(WebElement we, WebDriver driver) {
        try {
            if (we.isEnabled()) {
                we.click();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getActiveYear(WebElement yearElement, WebDriver driver) {

        String activeYear = null;
        try {
            activeYear = yearElement.getText();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return activeYear;
    }

    public static String getepoch() {
        long currentTimeStamp = System.currentTimeMillis();
        String epoch = String.valueOf(currentTimeStamp);
        return epoch;
    }

    // Converting map to a JSON payload as string
    public static void convertJSON(ArrayList<HashMap<String, Object>> al, ObjectMapper mapper) {
        try {
            String employeePrettyJson = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(al);
            System.out.println(employeePrettyJson);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Writing JSON on a file
    public static void writeJSON(ArrayList<HashMap<String, Object>> al, ObjectMapper mapper, File fileName) {
        try {
            mapper.writerWithDefaultPrettyPrinter().writeValue(fileName, al);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean fileExists(File fileName) {
        if (fileName.exists())
            return true;
        else
            return false;
    }

    public static boolean fileEmpty(File fileName) {
        if (fileName.length() > 0)
            return true;
        else
            return false;
    }
}
