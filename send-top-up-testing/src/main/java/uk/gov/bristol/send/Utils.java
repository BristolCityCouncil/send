package uk.gov.bristol.send;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class Utils {

    private Utils() {}

    public static void driverWait(long timeMs) {
        try {
            Thread.sleep(timeMs);
        } catch (InterruptedException ex) {
            throw new SENDException("Webdriver sleep thread interruption", ex);
        }
    }

    /**
     * Method to perform an element click() equivalent using a JScript library rather than WebDriver.
     * Useful in cases where for whatever reason a click() is not working, although there seem to be no logical reason
     * for this to be the case.
     * @param webDriver
     * @param webElement element to perform the action on
     */
    public static void jsClick(WebDriver webDriver, WebElement webElement) {
        ((JavascriptExecutor)webDriver).executeScript("arguments[0].click();", webElement);
    }

    /**
     * Method to ensure an element is in view where actions are failing - equivalent using a JScript library rather than WebDriver.
     * Useful in cases where for whatever reason the equivalent is not working. Often the reason required is unclear
     * and not always a simple case of 'Element is not visible'.
     * @param webDriver
     * @param webElement element to perform the action on
     */
    public static void jsScrollIntoView(WebDriver webDriver, WebElement webElement){
        ((JavascriptExecutor)webDriver).executeScript("arguments[0].scrollIntoView();", webElement);
    }
}
