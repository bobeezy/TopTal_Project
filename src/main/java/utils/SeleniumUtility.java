package main.java.utils;

import main.java.Engine.DriverFactory;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SeleniumUtility extends DriverFactory {

    public static int WaitTimeout = 6;
    public static String relativeScreenShotPath;

    //region <alertAccept>
    public static void alertAccept(String errorMessage) {

        try {
            System.out.println("Attempting to click OK in alert pop-up");

            //Get a handle to the open alert, prompt or confirmation
            Alert alert = driver.switchTo().alert();

            //Get the text of the alert or prompt
            alert.getText();

            //And acknowledge the alert (equivalent to clicking "OK")
            alert.accept();

            System.out.println("'Ok' Clicked successfully...proceeding");
            Assert.assertTrue("'Ok' Clicked successfully...proceeding", true);
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to click 'OK' in alert pop-up --- " + e.getMessage());
        }
    }
    //endregion

    //region <alertAcceptTwice>
    /**
     * This method is to click OK twice
     * and accept 2 consecutive alerts button
     */
    public static void alertAcceptTwice(int millisecondsWait, String errorMessage) {

        try {
            System.out.println("Attempting to click 1st OK in alert pop-up");

            //Get a handle to the open alert, prompt or confirmation
            Alert alert = driver.switchTo().alert();

            //Get the text of the alert or prompt
            alert.getText();

            //And acknowledge the alert (equivalent to clicking "OK")
            alert.accept();

            System.out.println("Attempting to click 2nd OK in alert pop-up");

            //And acknowledge the alert (equivalent to clicking "OK")
            SeleniumUtility.pause(millisecondsWait);
            alert.accept();

            System.out.println("'Ok' Clicked twice successfully...proceeding");
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to click 'OK' twice in alert pop-up twice --- " + e.getMessage());
        }
    }
    //endregion

    //region <alertDismiss>
    public static void alertDismiss(String errorMessage) {

        try {
            System.out.println("Attempting to click 'Cancel' in alert pop-up");

            //Get a handle to the open alert, prompt or confirmation
            Alert alert = driver.switchTo().alert();

            //Get the text of the alert or prompt
            alert.getText();

            //And Dismiss the alert (equivalent to clicking "Cancel")
            alert.dismiss();

            System.out.println("'Cancel' Clicked successfully...proceeding");
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to click 'Cancel' in alert pop-up --- " + e.getMessage());
        }
    }
    //endregion

    //region <alertGetText>
    public static void alertGetText(String errorMessage) {

        try {
            System.out.println("Attempting to capture the alert message");

            //Get a handle to the open alert, prompt or confirmation
            Alert alert = driver.switchTo().alert();

            //Get the text of the alert or prompt
            String text = alert.getText();
            System.out.println("Alert extracted text: '" + text + "'");

            System.out.println("Captured the alert message successfully...proceeding \n");
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to capture the alert message --- " + e.getMessage());
        }
    }
    //endregion

    //region <alertEnterText>
    public static void alertEnterText(String textToEnter, String errorMessage) {

        try {
            System.out.println("Attempting to send some data to alert box");

            //Get a handle to the open alert, prompt or confirmation
            Alert alert = driver.switchTo().alert();

            //Send text of the alert or prompt
            alert.sendKeys(textToEnter);

            System.out.println("Entered text '" + textToEnter + "' successfully...proceeding");
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to enter text '" + textToEnter + "' to alert box --- " + e.getMessage());
        }
    }
    //endregion

    //region <clearText>
    public static void clearText(By element, String errorMessage) {

        try {
            waitForElement(element, errorMessage);
            WebElement elementToTypeIn = driver.findElement(element);
            elementToTypeIn.clear();

            System.out.println("Cleared text on element : " + element);
            Assert.assertTrue("Cleared text on element : " + element, true);
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to clear text on element --- " + element + " - " + e.getMessage());
        }
    }
    //endregion

    //region <clearTextAndClickTextbox>
    public static void clearTextAndClickTextbox(By element, String errorMessage) {

        try {
            clearText(element, "Failed to clear " + errorMessage);

            clickElement(element, "Failed to click " + errorMessage);
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to clear text and click element --- " + element + " - " + e.getMessage());
        }
    }
    //endregion

    //region <clearTextAndEnterValue>
    public static void clearTextAndEnterValue(By element, String textToEnter, String errorMessage) {

        try {
            waitForElement(element, errorMessage);
            WebElement elementToTypeIn = driver.findElement(element);
            elementToTypeIn.click();
            elementToTypeIn.sendKeys(Keys.chord(Keys.CONTROL, "a")); //Ctrl+a
            elementToTypeIn.sendKeys(Keys.DELETE); //Delete
            Actions typeText = new Actions(driver);
            typeText.moveToElement(elementToTypeIn);
            typeText.click(elementToTypeIn);
            typeText.sendKeys(elementToTypeIn, textToEnter);
            typeText.click(elementToTypeIn);
            typeText.perform();

            elementToTypeIn = driver.findElement(element);

            elementToTypeIn.click();

            String retrievedText = elementToTypeIn.getAttribute("value");

            if (retrievedText != null) {

                if (retrievedText.equals(textToEnter)) {
                    System.out.println("Text entered matches text retrieved." + "\nText entered = " + textToEnter + "  : Text retrieved = " + retrievedText);
                    Assert.assertTrue("Text entered matches text retrieved." + "\nText entered = " + textToEnter + "  : Text retrieved = " + retrievedText, true);
                }
                else {
                    System.out.println("Text entered does not match text retrieved." + "\nText entered = " + textToEnter + "\nText retrieved = " + retrievedText);
                    Assert.assertTrue("Text entered matches text retrieved." + "\nText entered = " + textToEnter + "\nText retrieved = " + retrievedText, true);
                }
            }
            else {
                System.out.println("Null value Found");
                Assert.fail("\n[ERROR] Null value Found ---  ");
            }
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to enter text : \"" + textToEnter + "\" by Xpath  ---  " + element + "' - " + e.getMessage());
        }
    }
    //endregion

    //region <clearTextAndEnterValueByXpath>
    public static void clearTextAndEnterValueByXpath(String elementXpath, String textToEnter, String errorMessage) {

        try {
            waitForElementByXpath(elementXpath, errorMessage);
            WebElement elementToTypeIn = driver.findElement(By.xpath(elementXpath));
            elementToTypeIn.click();
            elementToTypeIn.sendKeys(Keys.chord(Keys.CONTROL, "a")); //Ctrl+a
            elementToTypeIn.sendKeys(Keys.DELETE); //Delete
            Actions typeText = new Actions(driver);
            typeText.moveToElement(elementToTypeIn);
            typeText.click(elementToTypeIn);
            typeText.sendKeys(elementToTypeIn, textToEnter);
            typeText.click(elementToTypeIn);
            typeText.perform();

            elementToTypeIn = driver.findElement(By.xpath(elementXpath));

            elementToTypeIn.click();

            String retrievedText = elementToTypeIn.getAttribute("value");

            if (retrievedText != null) {

                if (retrievedText.equals(textToEnter)) {
                    System.out.println("Text entered matches text retrieved." + "  Text entered = " + textToEnter + "  : Text retrieved = " + retrievedText);
                    Assert.assertTrue("Text entered matches text retrieved." + "  Text entered = " + textToEnter + "  : Text retrieved = " + retrievedText, true);
                }
                else {
                    System.out.println("Text entered does not match text retrieved." + "  Text entered = " + textToEnter + "  : Text retrieved = " + retrievedText);
                    Assert.assertTrue("Text entered matches text retrieved." + "  Text entered = " + textToEnter + "  : Text retrieved = " + retrievedText, true);
                }
            }
            else {
                System.out.println("Null value Found");
                Assert.fail("\n[ERROR] Null value Found ---  ");
            }
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to enter text : \"" + textToEnter + "\" by Xpath  ---  " + elementXpath + "' - " + e.getMessage());
        }
    }
    //endregion

    //region <clearTextAndPressEnter>
    public static void clearTextAndPressEnter(By element, String errorMessage) {

        try {
            clearText(element, errorMessage);

            sendKeys("ENTER");
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to clear text on element --- " + element + " - " + e.getMessage());
        }
    }
    //endregion

    //region <clearTextByXpath>
    public static void clearTextByXpath(String elementXpath, String errorMessage) {

        try {
            waitForElementByXpath(elementXpath, errorMessage);
            WebElement elementToTypeIn = driver.findElement(By.xpath(elementXpath));
            elementToTypeIn.clear();

            System.out.println("Cleared text on element by Xpath : " + elementXpath);
            Assert.assertTrue("Cleared text on element by Xpath : " + elementXpath, true);
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to clear text on element by Xpath  ---  " + elementXpath + " - " + e.getMessage());
        }
    }
    //endregion

    //region <clearBrowserCache>
    public static void clearBrowserCache() {

        try {
            driver.manage().deleteAllCookies();

            System.out.println("Clearing the browser cache...proceeding");
            pause(6000); //wait 6 seconds to clear cookies.

            Assert.assertTrue("Cleared the browser cache successfully", true);
        }
        catch (Exception e) {
            log("Failed to clear browser cache", "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to clear browser cache - " + e.getMessage());
        }
    }
    //endregion

    //region <clickElement>
    public static void clickElement(By element, String errorMessage) {

        try {
            waitForElement(element, errorMessage);
            WebDriverWait wait = new WebDriverWait(driver, WaitTimeout);
            wait.until(ExpectedConditions.elementToBeClickable(element));
            WebElement elementToClick = driver.findElement(element);
            elementToClick.click();

            System.out.println("Clicked element: " + element);
            Assert.assertTrue("Clicked element: " + element, true);
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to click on element --- " + element + " - " + e.getMessage());
        }
    }
    //endregion

    //region <clickElementByXpath>
    public static void clickElementByXpath(String elementXpath, String errorMessage) {

        try {
            waitForElementByXpath(elementXpath, errorMessage);
            WebDriverWait wait = new WebDriverWait(driver, WaitTimeout);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(elementXpath)));
            WebElement elementToClick = driver.findElement(By.xpath(elementXpath));
            elementToClick.click();

            System.out.println("Clicked element by Xpath : " + elementXpath);
            Assert.assertTrue("Clicked element by Xpath : " + elementXpath, true);
        }
        catch (Exception e) {
            System.out.println("Failed to click on element by Xpath - " + elementXpath + "' - " + e.getMessage());
            Assert.fail("\n[ERROR] Failed to click on element by Xpath  ---  " + elementXpath + "' - " + e.getMessage());
        }
    }
    //endregion

    //region <clickElementByActions>
    public static void clickElementByActions(By element, String errorMessage) {

        try {
            Actions actions = new Actions(driver);

            WebElement ele = driver.findElement(element);
            actions.moveToElement(ele);
            actions.click();
            actions.perform();

            System.out.println("Clicked element by Xpath : " + element);
            Assert.assertTrue("Clicked element by Xpath : " + element, true);
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to click on element --- " + element + "' - " + e.getMessage());
        }
    }
    //endregion

    //region <clickElementByActionsXpath>
    /**
     * Dynamic method to click an element by Xpath. Using a dynamic Xpath of the element, we just specify the value to click
     * */
    public static void clickElementByActionsXpath(String element, String valueToSelect, String errorMessage) {

        String updatedXpath = "";

        try {
            updatedXpath = updateXpathValueWithString(element, valueToSelect); //update default value saved in .properties
            Actions actions = new Actions(driver);
            WebElement elementToClick = driver.findElement(By.xpath(updatedXpath));
            actions.moveToElement(elementToClick);
            actions.click();
            actions.perform();

            System.out.println("Clicked element : " + updatedXpath);
            Assert.assertTrue("Clicked element : " + updatedXpath, true);
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to click on element --- " + updatedXpath + "' - " + e.getMessage());
        }
    }
    //endregion

    //region <clickElementWithJS>
    /**
     * To click an element using JavaScript command
     */
    public static void clickElementWithJS(By element, String errorMessage) {

        try {
            waitForElement(element, errorMessage);
            WebDriverWait wait = new WebDriverWait(driver, WaitTimeout);
            wait.until(ExpectedConditions.elementToBeClickable(element));
            WebElement elementToClick = driver.findElement(element);

            JavascriptExecutor executor = driver;
            executor.executeScript("arguments[0].click()", elementToClick);

            System.out.println("Clicked element : " + element);
            Assert.assertTrue("Clicked element : " + element, true);
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to click on element --- " + element + "' - " + e.getMessage());
        }
    }

    /**
     * Dynamic method to click an element by Xpath. Using a dynamic Xpath of the element,
     * we just specify the value to click
     */
    public static void clickElementWithJS(String element, String valueToSelect, String errorMessage) {

        String updatedXpath = "";

        try {
            updatedXpath = updateXpathValueWithString(element, valueToSelect); //update default value saved in .properties

            waitForElementByXpath(updatedXpath, errorMessage);
            WebDriverWait wait = new WebDriverWait(driver, WaitTimeout);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(updatedXpath)));
            WebElement elementToClick = driver.findElement(By.xpath(updatedXpath));

            JavascriptExecutor executor = driver;
            executor.executeScript("arguments[0].click()", elementToClick);

            System.out.println("Clicked element : " + updatedXpath);
            Assert.assertTrue("Clicked element : " + updatedXpath, true);
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to click on element --- " + updatedXpath + "' - " + e.getMessage());
        }
    }
    //endregion

    //region <clickOptionByXpath>
    /*Dynamic method to click an element by Xpath. Using a dynamic Xpath of the element, we just specify the 2 values to click*/
    public static void clickOptionByXpath(String elementXpath, String valueToSelect, String secondValueToSelect, String errorMessage) {

        String updatedXpath;
        String updatedSecondXpath = "";

        try {
            updatedXpath = updateXpathValueWithString(elementXpath, valueToSelect); //update default value saved in .properties
            updatedSecondXpath = updateXpathSecondValueWithString(updatedXpath, secondValueToSelect); //update default value saved in .properties

            waitForElementByXpath(updatedSecondXpath, errorMessage);
            WebDriverWait wait = new WebDriverWait(driver, WaitTimeout);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(updatedSecondXpath)));
            WebElement elementToClick = driver.findElement(By.xpath(updatedSecondXpath));
            elementToClick.click();

            System.out.println("Clicked element by Xpath : " + updatedSecondXpath);
            Assert.assertTrue("Clicked element by Xpath : " + updatedSecondXpath, true);
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to click on element by Xpath --- " + updatedSecondXpath + "' - " + e.getMessage());
        }
    }

    /*Dynamic method to click an element by Xpath. Using a dynamic Xpath of the element, we just specify the value to click*/
    public static void clickOptionByXpath(String element, String valueToSelect, String errorMessage) {

        String updatedXpath = "";

        try {
            updatedXpath = updateXpathValueWithString(element, valueToSelect); //update default value saved in .properties

            waitForElementByXpath(updatedXpath, errorMessage);
            WebDriverWait wait = new WebDriverWait(driver, WaitTimeout);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(updatedXpath)));
            WebElement elementToClick = driver.findElement(By.xpath(updatedXpath));
            elementToClick.click();

            System.out.println("Clicked element by Xpath : " + updatedXpath);
            Assert.assertTrue("Clicked element by Xpath : " + updatedXpath, true);
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to click on element by Xpath --- " + updatedXpath + "' - " + e.getMessage());
        }
    }
    //endregion

    //region <closeTab>
    public static void closeTab() {
        try {
            driver.close();

            System.out.println("Closed tab");
            Assert.assertTrue("Closed tab", true);
        }
        catch (Exception e) {
            log("Failed to close tab", "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to close tab - " + e.getMessage());
        }
    }
    //endregion

    //region <copyKeys>
    public static void copyKeys() {

        try {
            Actions action = new Actions(driver);
            action.sendKeys(Keys.CONTROL, "c"); //Ctrl+c
            action.perform();
        }
        catch (Exception e) {
            log("Failed to send keypress to element - Control + C", "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to send keypress to element - Control + C --- " + e.getMessage());
        }
    }
    //endregion

    //region <dismissAlert>
    public static void dismissAlert(String errorMessage) {

        try {
            System.out.println("Attempting to click CANCEL in alert pop-up");

            //Get a handle to the open alert, prompt or confirmation
            Alert alert = driver.switchTo().alert();

            //Get the text of the alert or prompt
            alert.getText();

            //And acknowledge the alert (equivalent to clicking "CANCEL")
            alert.dismiss();

            System.out.println("Cancel Clicked successfully...proceeding");
            Assert.assertTrue("Cancel Clicked successfully...proceeding", true);
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to click CANCEL in alert pop-up - " + e.getMessage());
        }
    }
    //endregion

    //region <doubleClickElementByXpath>
    public static void doubleClickElementByXpath(String elementLinkTextXpath, String errorMessage) {

        try {
            waitForElementByXpath(elementLinkTextXpath, errorMessage);
            Actions act = new Actions(driver);
            WebElement elementToClick = driver.findElement(By.xpath(elementLinkTextXpath));

            act.doubleClick(elementToClick).perform();
            System.out.println("Double-clicked element by Xpath : " + elementLinkTextXpath);
            Assert.assertTrue("Double-clicked element by Xpath : " + elementLinkTextXpath, true);
        }
        catch (Exception e) {
            log("Failed to double click element", "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to double click element by link text  ---  " + elementLinkTextXpath + "' - " + e.getMessage());
        }
    }
    //endregion

    //region <doubleClickElement>
    public static void doubleClickElement(By element, String errorMessage) {

        try {
            waitForElement(element, errorMessage);
            Actions act = new Actions(driver);
            WebElement elementToClick = driver.findElement(element);

            act.doubleClick(elementToClick).perform();
            System.out.println("Double-clicked element : " + element);
            Assert.assertTrue("Double-clicked element : " + element, true);
        }
        catch (Exception e) {
            log("Failed to double click element", "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to double click element  ---  " + element + "' - " + e.getMessage());
        }
    }
    //endregion

    //region <doubleClickOptionByXpath>
    public static void doubleClickOptionByXpath(String element, String valueToSelect, String errorMessage) {

        String updatedXpath = "";

        try {
            updatedXpath = updateXpathValueWithString(element, valueToSelect); //update default value saved in .properties

            waitForElementByXpath(updatedXpath, errorMessage);
            WebDriverWait wait = new WebDriverWait(driver, WaitTimeout);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(updatedXpath)));
            Actions act = new Actions(driver);
            WebElement elementToClick = driver.findElement(By.xpath(updatedXpath));

            act.doubleClick(elementToClick).perform();

            System.out.println("Clicked element by Xpath : " + updatedXpath);
            Assert.assertTrue("Clicked element by Xpath : " + updatedXpath, true);
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to click on element by Xpath --- " + updatedXpath + "' - " + e.getMessage());
        }
    }
    //endregion

    //region <doubleClickOptionByActionsXpath>
    /**
     * Dynamic method to double click an element by Xpath. Using a dynamic Xpath of the element, we just specify the value to click
     * */
    public static void doubleClickOptionByActionsXpath(String element, String valueToSelect, String errorMessage) {

        String updatedXpath = "";

        try {
            updatedXpath = updateXpathValueWithString(element, valueToSelect); //update default value saved in .properties

            waitForElementByXpath(updatedXpath, errorMessage);
            WebDriverWait wait = new WebDriverWait(driver, WaitTimeout);
            wait.until(ExpectedConditions.elementToBeClickable(By.xpath(updatedXpath)));

            Actions actions = new Actions(driver);
            WebElement elementToClick = driver.findElement(By.xpath(updatedXpath));

            actions.moveToElement(elementToClick);

            actions.doubleClick(elementToClick).perform();

            System.out.println("Clicked element : " + updatedXpath);
            Assert.assertTrue("Clicked element : " + updatedXpath, true);
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to click on element --- " + updatedXpath + "' - " + e.getMessage());
        }
    }
    //endregion

    //region <enterTextByXpath>
    public static void enterTextByXpath(String elementXpath, String textToEnter, String errorMessage) {

        try {
            if (textToEnter.equals("")) {
                System.out.println("There is No text to enter");
                Assert.assertTrue("There is No text to enter", true);
            }
            else if (textToEnter.equalsIgnoreCase("Clear")) {
                waitForElementByXpath(elementXpath, errorMessage);
                WebElement elementToTypeIn = driver.findElement(By.xpath(elementXpath));
                elementToTypeIn.clear();
                Assert.assertTrue("Cleared text field", true);
            }
            else {
                waitForElementByXpath(elementXpath, errorMessage);
                Actions action = new Actions(driver);
                WebElement elementToTypeIn = driver.findElement(By.xpath(elementXpath));
                elementToTypeIn.click();
                action.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).perform();
                elementToTypeIn.sendKeys(textToEnter);

                System.out.println("Entered text : \"" + textToEnter + "\" to : " + elementXpath);
                Assert.assertTrue("Entered text : \"" + textToEnter + "\" to : " + elementXpath, true);
            }
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to enter text : \"" + textToEnter + "\" by Xpath  ---  " + elementXpath + "' - " + e.getMessage());
        }
    }
    //endregion

    //region <enterText>
    public static void enterText(By element, String textToEnter, String errorMessage) {

        try {
            if (textToEnter.equals("")) {
                System.out.println("There is No text to enter");
                Assert.assertTrue("There is No text to enter", true);
            }
            else if (textToEnter.equalsIgnoreCase("Clear")) {
                waitForElement(element, errorMessage);
                WebElement elementToTypeIn = driver.findElement(element);
                elementToTypeIn.clear();
                Assert.assertTrue("Cleared text field", true);
            }
            else {
                waitForElement(element, errorMessage);
                Actions action = new Actions(driver);
                WebElement elementToTypeIn = driver.findElement(element);
                elementToTypeIn.click();
                action.keyDown(Keys.CONTROL).sendKeys("a").keyUp(Keys.CONTROL).perform();
                elementToTypeIn.sendKeys(textToEnter);

                System.out.println("Entered text : \"" + textToEnter + "\" to : " + element);
                Assert.assertTrue("Entered text : \"" + textToEnter + "\" to : " + element, true);
            }
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to enter text : \"" + textToEnter + "\" ---  " + element + "' - " + e.getMessage());
        }
    }
    //endregion

    //region <enterTextAndPressEnter>
    public static void enterTextAndPressEnter(By element, String textToEnter, String errorMessage) {

        try {
            enterText(element, textToEnter, errorMessage);

            sendKeys("ENTER");
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to enter text and press ENTER on element --- " + element + " - " + e.getMessage());
        }
    }
    //endregion

    //region <enterTextWithJavaScript>
    public static void enterTextWithJavaScript(By element, String textToEnter, String errorMessage) {

        try {
            WebElement elementToTypeIn = driver.findElement(element);
            JavascriptExecutor jse = driver;
            //Thread.sleep(500);
            jse.executeScript("arguments[0].setAttribute('value', " + textToEnter + " );", elementToTypeIn);

            System.out.println("Entered text : \"" + textToEnter + "\" to : " + element);
            Assert.assertTrue("Entered text : \"" + textToEnter + "\" to : " + element, true);
        }
        catch(Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to enter text : \"" + textToEnter + "\" ---  " + element + "' - " + e.getMessage());
        }
    }
    //endregion

    //region <extractListOfTextByXpath>
    /**
     * This method is used to extract Parcel(s) label
     * and store in list 'parcel.barcodeLabelList'
     */
//    public static String extractListOfTextByXpath(String elementXpath, String errorMessage) {
//
//        String retrievedText = "";
//
//        if (parcel.barcodeLabelList.size() != 0) {
//            parcel.barcodeLabelList.clear();
//        }
//
//        try {
//            waitForElementByXpath(elementXpath, errorMessage);
//            List<WebElement> listOfElements = driver.findElements(By.xpath(elementXpath));
//
//            for (int i = 0; i < listOfElements.size(); i++) {
//                retrievedText = listOfElements.get(i).getText();
//                String[] tempArray = retrievedText.split(" ");
//
//                retrievedText = tempArray[4].replaceAll("\\)", ""); //retrieve text at position 4 = "S910004655931)", then remove ")"
//                parcel.barcodeLabelList.add(retrievedText);
//
//                System.out.println("Text : " + retrievedText + " retrieved from element by Xpath - " + elementXpath);
//            }
//
//            return retrievedText;
//        }
//        catch (Exception e) {
//            log(errorMessage, "ERROR", "text");
//            Assert.fail("\n[ERROR] Failed to retrieve text from element - " + elementXpath + " - " + e.getMessage());
//
//            return retrievedText;
//        }
//    }
//
//    public static void extractListOfParcelTextByXpath(String elementXpath, String errorMessage) {
//
//        String retrievedText = "";
//
//        if (parcel.barcodeLabelList.size() != 0) {
//            parcel.barcodeLabelList.clear();
//        }
//
//        try {
//            waitForElementByXpath(elementXpath, errorMessage);
//            List<WebElement> listOfElements = driver.findElements(By.xpath(elementXpath));
//
//            for (int i = 0; i < listOfElements.size(); i++) {
//                retrievedText = listOfElements.get(i).getText();
//                String[] tempArray = retrievedText.split(" ");
//
//                retrievedText = tempArray[4].replaceAll("\\)", ""); //retrieve text at position 4 = "S910004655931)", then remove ")"
//                parcel.barcodeLabelList.add(retrievedText);
//
//                System.out.println("Text : " + retrievedText + " retrieved from element by Xpath - " + elementXpath);
//            }
//        }
//        catch (Exception e) {
//            log(errorMessage, "ERROR", "text");
//            Assert.fail("\n[ERROR] Failed to retrieve text from element - " + elementXpath + " - " + e.getMessage());
//        }
//    }
    //endregion

    //region <extractTextByXpath>
    public static String extractTextByXpath(String elementXpath, String errorMessage) {

        String retrievedText = "";
        try {
            waitForElementByXpath(elementXpath, errorMessage);
            WebElement elementToRead = driver.findElement(By.xpath(elementXpath));
            retrievedText = elementToRead.getText();

            System.out.println("Text : " + retrievedText + " retrieved from element by Xpath - " + elementXpath);
            Assert.assertTrue("Text : " + retrievedText + " retrieved from element - " + elementXpath, true);

            return retrievedText;
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to retrieve text from element - " + elementXpath + " - " + e.getMessage());

            return retrievedText;
        }
    }

    public static String extractTextByXpath(String element, String valueToSelect, String errorMessage) {

        String retrievedText = "";
        String updatedXpath = "";

        try {
            updatedXpath = updateXpathValueWithString(element, valueToSelect); //update default value saved in .properties

            waitForElementByXpath(updatedXpath, errorMessage);
            WebElement elementToRead = driver.findElement(By.xpath(updatedXpath));
            retrievedText = elementToRead.getText();

            System.out.println("Text : " + retrievedText + " retrieved from element by Xpath - " + updatedXpath);
            Assert.assertTrue("Text : " + retrievedText + " retrieved from element - " + updatedXpath, true);

            return retrievedText;
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to retrieve text from element - " + updatedXpath + " - " + e.getMessage());

            return retrievedText;
        }
    }
    //endregion

    //region <extractText>
    public static String extractText(By element, String errorMessage) {

        String retrievedText = "";
        try {
            waitForElement(element, errorMessage);
            WebElement elementToRead = driver.findElement(element);
            retrievedText = elementToRead.getText();

            System.out.println("Text : " + retrievedText + " retrieved from element - " + element);
            Assert.assertTrue("Text : " + retrievedText + " retrieved from element - " + element, true);

            return retrievedText;
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to retrieve text from element - " + element + " - " + e.getMessage());

            return retrievedText;
        }
    }
    //endregion

    //region <extractTextBetweenSpecialCharWithRegex>
    /**
     * To extract string between "(" and ")" using Regular Expression
     */
    public static String extractTextBetweenSpecialCharWithRegex(String string, String errorMessage) {

        String subString = "";

        try {
            subString = string.split("[()]")[1];
            System.out.println("Extracted substring \"" + subString + "\" from string \"" + string + "\"");
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to extract substring \"" + subString + "\" from string \"" + string + "\" --- " + e.getMessage());
        }

        return subString;
    }
    //endregion

    //region <extractTextBeforeSpaceWithRegex>
    /**
     * To remove anything after a space " " using Regular Expression
     */
    public static String extractTextBeforeSpaceWithRegex(String string, String errorMessage) {

        String subString = "";

        try {
            subString = string.replaceAll(" .+$", "");
            System.out.println("Extracted substring \"" + subString + "\" from string \"" + string + "\"");
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to extract substring \"" + subString + "\" from string \"" + string + "\" --- " + e.getMessage());
        }

        return subString;
    }
    //endregion

    //region <getCurrentTimeUsingCalendar>
    public static void getCurrentTimeUsingCalendar() {

        try {
            Calendar cal = Calendar.getInstance();
            Date date = cal.getTime();
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            String formattedDate = dateFormat.format(date);
            log("Current time of the day using Calendar - 24 hour format: " + formattedDate, "INFO", "text");
        }
        catch (Exception e) {
            log("Failed to get the current time", "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to get the current time --- " + e.getMessage());
        }
    }
    //endregion

    //region <getTextByAttribute>
    public static String getTextByAttribute(By element, String attribute, String errorMessage) {

        String retrievedText = "";
        try {
            waitForElement(element, errorMessage);
            WebElement elementToRead = driver.findElement(element);
            retrievedText = elementToRead.getAttribute(attribute);

            System.out.println("Text : " + retrievedText + " retrieved from element - " + element);
            Assert.assertTrue("Text : " + retrievedText + " retrieved from element - " + element, true);

            return retrievedText;
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to retrieve text from element - " + element + " - " + e.getMessage());

            return retrievedText;
        }
    }
    //endregion

    //region <hoverOverElement>
    public static void hoverOverElementAndClickSubElementByXpath(String elementXpath, String subElementToClickXpath, String errorMessage) {

        try {
            waitForElementByXpath(elementXpath, errorMessage);

            Actions hoverTo = new Actions(driver);
            hoverTo.moveToElement(driver.findElement(By.xpath(elementXpath)));
            hoverTo.perform();
            pause(1000);
            hoverTo.moveToElement(driver.findElement(By.xpath(subElementToClickXpath)));
            hoverTo.click();
            hoverTo.perform();

            System.out.println("Hovered over element - " + elementXpath + " and clicked sub element " + subElementToClickXpath);
            Assert.assertTrue("Hovered over element - " + elementXpath + " and clicked sub element " + subElementToClickXpath, true);
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to hover over element Xpath : " + elementXpath + " and click sub element " + subElementToClickXpath + " ---  " + e.getMessage());
        }
    }

    public static void hoverOverElementAndClickSubElement(By element, By subElementToClick, String errorMessage) {

        try {
            waitForElement(element, errorMessage);

            Actions hoverTo = new Actions(driver);
            hoverTo.moveToElement(driver.findElement(element));
            hoverTo.perform();
            pause(2000);
            hoverTo.moveToElement(driver.findElement(subElementToClick));
            hoverTo.click();
            hoverTo.perform();

            System.out.println("Hovered over element - " + element + " and clicked sub element " + subElementToClick);
            Assert.assertTrue("Hovered over element - " + element + " and clicked sub element " + subElementToClick, true);
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to hover over element : " + element + " and click sub element " + subElementToClick + " ---  " + e.getMessage());
        }
    }

    public static void hoverOverElement(By element, String errorMessage) {

        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(element));
            wait.until(ExpectedConditions.elementToBeClickable(element));
            WebElement ele = driver.findElement(element);
            Actions actions = new Actions(driver);
            actions.moveToElement(ele).build().perform();

            System.out.println("Hovered over element - " + element);
            Assert.assertTrue("Hovered over element - " + element, true);
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("[ERROR] Failed to hover over element : " + element + " ---  " + e.getMessage());
        }
    }

    public static void hoverOverElementByXpath(String elementXpath, String errorMessage) {

        try {
            waitForElementByXpath(elementXpath, errorMessage);

            Actions hoverTo = new Actions(driver);
            hoverTo.moveToElement(driver.findElement(By.xpath(elementXpath)));
            hoverTo.perform();

            System.out.println("Hovered over element - " + elementXpath);
            Assert.assertTrue("Hovered over element - " + elementXpath, true);
        }
        catch (Exception e) {
            System.out.println("Failed to hover over element Xpath - " + e.getMessage());
            Assert.fail("\n[ERROR] Failed to hover over element Xpath : " + elementXpath + " ---  " + e.getMessage());
        }
    }

    public static void hoverOverFirstElementThenHoverOverSecondElementAndClickSubElement(By elementOne, By elementTwo, By subElementToClick, String errorMessage) {

        try {
            waitForElement(elementOne, errorMessage);

            Actions hoverTo = new Actions(driver);
            hoverTo.moveToElement(driver.findElement(elementOne));
            hoverTo.perform();
            hoverTo.moveToElement(driver.findElement(elementTwo));
            hoverTo.perform();
            pause(1000);
            hoverTo.moveToElement(driver.findElement(subElementToClick));
            hoverTo.click();
            hoverTo.perform();

            System.out.println("Hovered over 1st element - " + elementOne + " then 2nd element - " + elementTwo + " and clicked sub element " + subElementToClick);
            Assert.assertTrue("Hovered over 1st element - " + elementOne + " then 2nd element - " + elementTwo + " and clicked sub element " + subElementToClick, true);
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to hover over 1st element - " + elementOne + " then 2nd element - " + elementTwo + " and clicked sub element " + subElementToClick + " ---  " + e.getMessage());
        }
    }
    //endregion

    //region <moveSliderLeft>
    public static void moveSliderLeft(By element, String errorMessage) {

        try {

            waitForElement(element, errorMessage);
            Actions action = new Actions(driver);
            WebElement elementToClick = driver.findElement(element);
            action.click(elementToClick).build().perform();

            Thread.sleep(1000);
            for (int i = 0; i < 10; i++) {
                action.sendKeys(Keys.ARROW_LEFT).build().perform();
                Thread.sleep(200);
            }
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to move Slider Left ---  " + e.getMessage());
        }
    }
    //endregion

    //region <moveSliderRight>
    public static void moveSliderRight(By element, String errorMessage) {

        try {

            waitForElement(element, errorMessage);
            Actions action = new Actions(driver);
            WebElement elementToClick = driver.findElement(element);
            action.click(elementToClick).build().perform();

            Thread.sleep(1000);
            for (int i = 0; i < 10; i++) {
                action.sendKeys(Keys.ARROW_RIGHT).build().perform();
                Thread.sleep(200);
            }
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to move Slider Right ---  " + e.getMessage());
        }
    }
    //endregion

    //region <moveToElementAndClickByActions>
    /**
     * This method is to move to Element then Click it using Actions
     */
    public static void moveToElementAndClickByActions(By element, String errorMessage) {

        try {
            moveToElementByActions(element, "Failed to move to " + errorMessage);

            clickElementByActions(element, "Failed to click " + errorMessage);

            waitForPageToLoad();
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to move & click element --- " + element + "' - " + e.getMessage());
        }
    }
    //endregion

    //region <moveToElementByActions>
    /**
     * This method is to move to Element using Actions
     */
    public static void moveToElementByActions(By element, String errorMessage) {

        try {
            Actions actions = new Actions(driver);

            WebElement ele = driver.findElement(element);
            actions.moveToElement(ele);
            actions.perform();

            System.out.println("Clicked element by Xpath : " + element);
            Assert.assertTrue("Clicked element by Xpath : " + element, true);
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to click on element --- " + element + "' - " + e.getMessage());
        }
    }
    //endregion

    //region <openUrlInNewTab>
    public static void openUrlInNewTab(String url, String errorMessage) {

        try {
            //Execute the JavaScript
            WebElement element = (WebElement)((JavascriptExecutor) driver).executeScript("window.open()");

            //Switch to the new tab
            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(1));

            //navigate to url
            driver.navigate().to(url);
            System.out.println("Opened new tab - " + driver.getTitle());
            Assert.assertTrue("Opened new tab - " + driver.getTitle(), true);
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to Open new tab ---  " + e.getMessage());
        }
    }
    //endregion

    //region <openUrlInNewTabByIndex>
    public static void openUrlInNewTabByIndex(String url, String index, String errorMessage) {

        try {
            //Execute the JavaScript
            WebElement element = (WebElement)((JavascriptExecutor) driver).executeScript("window.open()");

            //Switch to the new tab
            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(Integer.parseInt(index)));

            //navigate to url
            driver.navigate().to(url);
            System.out.println("Opened new tab - " + driver.getTitle());
            Assert.assertTrue("Opened new tab - " + driver.getTitle(), true);
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to Open new tab ---  " + e.getMessage());
        }
    }
    //endregion

    //region <pasteCopiedTextByXpath>
    public static void pasteCopiedTextByXpath(String elementXpath, String value, String errorMessage) {

        try {
            WebElement elementToTypeIn = driver.findElement(By.xpath(elementXpath));
            Actions typeText = new Actions(driver);
            elementToTypeIn.click();
            elementToTypeIn.sendKeys(Keys.chord(Keys.CONTROL, "a")); //Ctrl+a
            elementToTypeIn.sendKeys(Keys.DELETE); //Delete
            elementToTypeIn.sendKeys(Keys.chord(Keys.CONTROL, "v")); //Ctrl+v
            typeText.sendKeys(elementToTypeIn, value);

            System.out.println("Pasted copied text \"" + value + "\" by Xpath -- " + elementXpath);
            Assert.assertTrue("Pasted copied text \"" + value + "\" by Xpath -- " + elementXpath, true);
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to paste copied text by Xpath  ---  " + elementXpath + "' - " + e.getMessage());
        }
    }
    //endregion

    //region <pasteCopiedText>
    public static void pasteCopiedText(By element, String value, String errorMessage) {

        try {
            WebElement elementToTypeIn = driver.findElement(element);
            Actions typeText = new Actions(driver);
            elementToTypeIn.click();
            elementToTypeIn.sendKeys(Keys.chord(Keys.CONTROL, "a")); //Ctrl+a
            elementToTypeIn.sendKeys(Keys.DELETE); //Delete
            elementToTypeIn.sendKeys(Keys.chord(Keys.CONTROL, "v")); //Ctrl+v
            typeText.sendKeys(elementToTypeIn, value);

            System.out.println("Pasted copied text \"" + value + "\" -- " + element);
            Assert.assertTrue("Pasted copied text \"" + value + "\" -- " + element, true);
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to paste copied text  ---  " + element + "' - " + e.getMessage());
        }
    }
    //endregion

    //region <pause>
    public static void pause(int millisecondsWait) {

        try {
            Thread.sleep(millisecondsWait);
        }
        catch (Exception e) {
        }
    }
    //endregion

    //region <selectFromDropDownListByXpath>
    public static void selectFromDropDownListByXpath(String dropdownlistXpath, String valueToSelect, String errorMessage) {

        String updatedXpath = "";

        try {
            updatedXpath = updateXpathValueWithString(dropdownlistXpath, valueToSelect); //update default value saved in .properties
            waitForElementByXpath(updatedXpath, errorMessage);
            Select dropDownList = new Select(driver.findElement(By.xpath(updatedXpath)));
            WebElement formxpath = driver.findElement(By.xpath(updatedXpath));
            formxpath.click();
            dropDownList.selectByVisibleText(valueToSelect);

            System.out.println("Selected Text : " + valueToSelect + " from : " + updatedXpath);
            Assert.assertTrue("Selected Text : " + valueToSelect + " from : " + updatedXpath, true);
        }
        catch (Exception e) {
            System.out.println("Failed to select from dropdown list by text using Xpath - " + e.getMessage());
            Assert.fail("\n[ERROR] Failed to select text : " + valueToSelect + " from dropdown list by Xpath  ---  " + e.getMessage());
        }
    }
    //endregion

    //region <sendKeys>
    public static void sendKeys(String choice) {

        try {
            Actions action = new Actions(driver);

            switch (choice.toUpperCase()) {
                case "ARROW DOWN": {
                    action.sendKeys(Keys.ARROW_DOWN);
                    action.perform();
                    System.out.println("Pressed : " + choice);
                    Assert.assertTrue("Pressed : " + choice, true);
                    break;
                }
                case "ENTER": {
                    action.sendKeys(Keys.ENTER);
                    action.perform();
                    System.out.println("Pressed : " + choice);
                    Assert.assertTrue("Pressed : " + choice, true);
                    break;
                }
                case "TAB": {
                    action.sendKeys(Keys.TAB);
                    action.perform();
                    System.out.println("Pressed : " + choice);
                    Assert.assertTrue("Pressed : " + choice, true);
                    break;
                }
                case "COPY ALL": {
                    action.sendKeys(Keys.CONTROL, "a"); //Ctrl+a
                    action.sendKeys(Keys.CONTROL, "c"); //Ctrl+c
                    action.perform();
                    System.out.println("Pressed : " + choice);
                    Assert.assertTrue("Pressed : " + choice, true);
                    break;
                }
                case "REFRESH": {
                    action.keyDown(Keys.CONTROL);
                    action.sendKeys(Keys.F5);
                    action.keyUp(Keys.CONTROL);
                    action.perform();
                    System.out.println("Pressed : " + choice);
                    Assert.assertTrue("Pressed : " + choice, true);
                    break;
                }
            }
        }
        catch (Exception e) {
            System.out.println("Failed to send keypress : \"" + choice + "\"");
            Assert.fail("\n[ERROR] Failed to send keypress : \"" + choice + "\" --- " + e.getMessage());
        }
    }
    //endregion

    //region <sliderLeft>
    public static void sliderLeft(By element, String errorMessage) {

        try {

            WebElement slider = driver.findElement(element);

            for (int i = 0; i <= 30; i++) {
                slider.sendKeys(Keys.ARROW_LEFT);
            }
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to move Slider Left ---  " + e.getMessage());
        }
    }
    //endregion

    //region <sliderRight>
    public static void sliderRight(By element, String errorMessage) {

        try {

            WebElement slider = driver.findElement(element);

            for (int i = 0; i <= 30; i++) {
                slider.sendKeys(Keys.ARROW_RIGHT);
            }
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to move Slider Left ---  " + e.getMessage());
        }
    }
    //endregion

    //region <switchToDefaultContent>
    public static void switchToDefaultContent() {

        try {
            driver.switchTo().defaultContent();
            System.out.println("Switched to default content");
            Assert.assertTrue("Switched to default content", true);
        }
        catch (Exception e) {
            log("Failed to switch to default content", "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to switch to default content - " + e.getMessage());
        }
    }
    //endregion

    //region <switchToAlert>
    public static void switchToAlert() {

        try {
            System.out.println("Attempting to switch to alert box");

            //Get a handle to the open alert, prompt or confirmation
            Alert alert = driver.switchTo().alert();

            System.out.println("Switched to Alert successfully");
        }
        catch (Exception e) {
            log("Failed to switch to Alert", "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to switch to Alert --- " + e.getMessage());
        }
    }
    //endregion

    //region <switchToTab>
    public static void switchToTab(int tab) {

        try {
            ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
            driver.switchTo().window(tabs.get(tab));

            System.out.println("Switched to window : " + driver.getTitle());
            Assert.assertTrue("Switched to window : " + driver.getTitle(), true);
        }
        catch (Exception e) {
            log("Failed to switch to new tab", "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to switch to new tab - " + e.getMessage());
        }
    }
    //endregion

    //region <switchToTabOrWindow>
    public static void switchToTabOrWindow() {

        try {
            String winHandleBefore = driver.getWindowHandle();

            for (String winHandle : driver.getWindowHandles()) {
                driver.switchTo().window(winHandle);
                System.out.println("Switched to window : " + driver.getTitle());
                Assert.assertTrue("Switched to window : " + driver.getTitle(), true);
            }
        }
        catch (Exception e) {
            log("Failed to switch to new tab", "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to switch to new tab - " + e.getMessage());
        }
    }
    //endregion

    //region <switchToPreviousTab>
    public static void switchToPreviousTab() {

        try {
            //Switch back to first tab
            driver.switchTo().window(driver.getWindowHandle());

            System.out.println("Switched To Previous Tab - " + driver.getTitle());
            Assert.assertTrue("Switched To Previous Tab - " + driver.getTitle(), true);
        }
        catch (Exception e) {
            log("Failed to Switch To Previous Tab", "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to Switch To Previous Tab - " + e.getMessage());
        }
    }
    //endregion

    //region <switchToFrameByXpath>
    public static void switchToFrameByXpath(String frameXpath) {

        boolean switchSuccessful;
        try {
            WebDriver frame = new WebDriverWait(driver, WaitTimeout).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frameXpath));
            frame.switchTo().activeElement();
            switchSuccessful = true;
            System.out.println("Switched to frame - " + frameXpath);
            Assert.assertTrue("Switched to frame - " + frameXpath, true);
        }
        catch (Exception e) {
            switchSuccessful = false;
            log("Failed to switch to frame by Xpath - " + frameXpath, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to switch to frame by Xpath - " + frameXpath + " - " + e.getMessage());
        }

        switchToElement(switchSuccessful);
    }

    private static boolean switchToElement(boolean switchSuccessful) {
        return switchSuccessful;
    }
    //endregion

    //region <switchToFrame>
    public static void switchToFrame(By frame_, String errorMessage) {

        boolean switchSuccessful;
        try {
            WebDriver frame = new WebDriverWait(driver, WaitTimeout).until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(frame_));
            frame.switchTo().activeElement();
            switchSuccessful = true;
            System.out.println("Switched to frame - " + frame_);
            Assert.assertTrue("Switched to frame - " + frame_, true);
        }
        catch (Exception e) {
            switchSuccessful = false;
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to switch to frame - " + frame_ + " - " + e.getMessage());
        }

        switchToElement_(switchSuccessful);
    }

    private static boolean switchToElement_(boolean switchSuccessful) {
        return switchSuccessful;
    }
    //endregion

    //region <takeScreenShot>
//    public static void takeScreenShot(String screenshotDescription, boolean isError) {
//
//        String imageFilePathString = "";
//
//        try {
//            StringBuilder imageFilePathBuilder = new StringBuilder();
//
//            File screenshotFile = new File(screenshotPath); //public static String screenshotPath = property.returnPropVal_Global(global_fileName, "screenshotPath");
//            System.out.println("Screenshots relative directory - " + screenshotFile.getPath());
//            relativeScreenShotPath = screenshotFile.getPath() + "/";
//
//            if (isError) {
//                relativeScreenShotPath += "FAILED_";
//            }
//            else {
//                relativeScreenShotPath += "PASSED_";
//            }
//
//            relativeScreenShotPath += screenshotDescription + "_" + timestamp() + ".png";
//
//            imageFilePathBuilder.append(relativeScreenShotPath);
//
//            imageFilePathString = imageFilePathBuilder.toString();
//
//            File screenShot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
//            FileUtils.copyFile(screenShot, new File(imageFilePathString));
//        }
//        catch (Exception e) {
//            log("Could not take screenshot - " + imageFilePathString + " - " + e.getMessage(), "ERROR",  "text");
//        }
//    }
    //endregion

    //region <timestamp>
    public static String timestamp() {
        return new SimpleDateFormat("dd.MM.yyyy.HH:mm").format(new Date());
    }
    //endregion

    //region <uploadFile>
    public static void uploadFile(By element, String filePath, String errorMessage) {

        try {
            waitForElement(element, errorMessage);
            WebElement uploadElement = driver.findElement(element); //element = "Choose File" button locator

            //enter the file path onto the file-selection input field
            String fileToUpLoad;
            File fPath = new File(filePath);
            fileToUpLoad = fPath.getAbsolutePath();
            uploadElement.sendKeys(fileToUpLoad); //filePath = src/main/resources/web/uploadFiles/manifestFile.pdf (to get relative Path)

            System.out.println("Uploaded file : " + filePath);
            Assert.assertTrue("Uploaded file : " + filePath, true);
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to upload file - " + e.getMessage());
        }
    }
    //endregion

    //region <validateSubstringIsPresent>
    public static void validateSubstringIsPresent(String string, String substring, String errorMessage) {

        boolean isFound = string.contains(substring);
        if (isFound) {
            System.out.println("Substring: \"" + substring + "\" is present");
            Assert.assertTrue("Substring: \"" + substring + "\" is present", true);
        }
        else {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Substring: \"" + substring + "\" is not present");
        }
    }
    //endregion

    //region <waitForAlertPopup>
    public static void waitForAlertPopup(Integer timeout, String errorMessage) {

        boolean alertDisplayed = false;
        try {
            int waitCount = 0;
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

            while (!alertDisplayed && waitCount < timeout) {
                try {
                    WebDriverWait wait = new WebDriverWait(driver, 1);
                    if ((wait.until(ExpectedConditions.alertIsPresent())) != null) {
                        alertDisplayed = true;
                        break;
                    }
                }
                catch (Exception e) {
                    alertDisplayed = false;
                }

                waitCount++;
            }
            if (waitCount == WaitTimeout) {
                GetElementFound1(alertDisplayed);
                log(errorMessage, "ERROR",  "text");
                Assert.fail("\n[ERROR] Reached TimeOut whilst waiting for alert Popup");
            }
        }
        catch (Exception e) {
            alertDisplayed = false;
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Didn't alert Popup --- " + e.getMessage());
        }
        try {
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Did Not Find alert Popup --- " + e.getMessage());
        }

        GetElementFound(alertDisplayed);
    }

    public static boolean waitForAlertPopup(Integer timeout) {

        boolean alertDisplayed = false;
        try {
            int waitCount = 0;
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

            while (!alertDisplayed && waitCount < timeout) {
                try {
                    WebDriverWait wait = new WebDriverWait(driver, 1);
                    if ((wait.until(ExpectedConditions.alertIsPresent())) != null) {
                        alertDisplayed = true;
                        break;
                    }
                }
                catch (Exception e) {
                    alertDisplayed = false;
                }

                waitCount++;
            }
        }
        catch (Exception e) {
            alertDisplayed = false;
        }

        try {
            driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        }
        catch (Exception e) {
            log("Failed to wait for Alert popup", "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to wait for Alert popup --- " + e.getMessage());
        }

        return alertDisplayed;
    }
    //endregion

    //region <To update an Xpath value from the .properties file>
    public static String updateXpathValueWithString(String xp, String value) {
        String xpath = xp.replace("value", value);
        return xpath;
    }

    public static String updateXpathSecondValueWithString(String xp, String value) {
        return xp.replace("secondString", value);
    }
    //endregion

    //region <waitForElementToBeDisplayed>
    public static void waitForElementToBeDisplayed(String elementXpath, String valueToSelect, Integer timeout, String errorMessage) {

        boolean elementDisplayed = false;
        String updatedXpath;
        try {
            int waitCount = 0;
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            while (!elementDisplayed && waitCount < timeout) {
                try {
                    updatedXpath = updateXpathValueWithString(elementXpath, valueToSelect); //update default value saved in .properties

                    if ((driver.findElement(By.xpath(updatedXpath)).isDisplayed())) {
                        elementDisplayed = true;
                        break;
                    }
                }
                catch (Exception e) {
                    elementDisplayed = false;
                }
                waitCount++;
            }
        }
        catch (Exception e) {
            elementDisplayed = false;
            log(errorMessage, "ERROR", "text");
            Assert.fail("\n[ERROR] Failed to wait for element by Xpath to be displayed: '" + elementXpath);
        }

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        GetElementFound(elementDisplayed);
    }

    public static void waitForElementToBeDisplayed(String elementXpath, String valueToSelect, String secondValueToSelect, Integer timeout, String errorMessage) {

        boolean elementDisplayed = false;
        String updatedXpath = "";
        String updatedSecondXpath = "";
        try {
            int waitCount = 0;
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            while (!elementDisplayed && waitCount < timeout) {
                try {
                    updatedXpath = updateXpathValueWithString(elementXpath, valueToSelect); //update default value saved in .properties
                    updatedSecondXpath = updateXpathSecondValueWithString(updatedXpath, secondValueToSelect); //update default value saved in .properties

                    if ((driver.findElement(By.xpath(updatedSecondXpath)).isDisplayed())) {
                        elementDisplayed = true;
                        break;
                    }
                }
                catch (Exception e) {
                    elementDisplayed = false;
                }
                waitCount++;
            }
        }
        catch (Exception e) {
            elementDisplayed = false;
            log(errorMessage, "ERROR", "text");
            Assert.fail("\n[ERROR] Failed to wait for element by Xpath: " + updatedXpath + "' and second element by Xpath: " + updatedSecondXpath + " --- " + e.getMessage());
        }

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        GetElementFound(elementDisplayed);
    }

    public static void waitForElementToBeDisplayed(By element, Integer timeout, String errorMessage) {

        boolean elementDisplayed = false;
        try {
            int waitCount = 0;
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            while (!elementDisplayed && waitCount < timeout) {
                try {
                    WebDriverWait wait = new WebDriverWait(driver, 1);

                    if ((driver.findElement(element).isDisplayed())) {
                        elementDisplayed = true;
                        break;
                    }
                }
                catch (Exception e) {
                    elementDisplayed = false;
                }
                waitCount++;
            }
        }
        catch (Exception e) {
            elementDisplayed = false;
            log(errorMessage, "ERROR", "text");
            Assert.fail("\n[ERROR] Failed to wait for element to be displayed: '" + element);
        }

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        GetElementFound(elementDisplayed);
    }
    //endregion

    //region <waitForElementToBeClickable>
    public static void waitForElementToBeClickable(By element, Integer timeoutInSeconds, String errorMessage) {

        try {
            waitForElement(element, errorMessage);
            WebDriverWait wait = new WebDriverWait(driver, timeoutInSeconds);
            wait.until(ExpectedConditions.elementToBeClickable(element));

            System.out.println("Waited for element to be clickable: " + element);
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to wait for element to be clickable: " + element + "' --- " + e.getMessage());
        }
    }
    //endregion

    //region <waitForElementToBeVisibleClickable>
    /**
     * This method is to wait Page to load
     * using JavaScript, then wait for an element on the page
     * to be visible and clickable
     */
    public static void waitForElementToBeVisibleClickable(By element, Integer timeout, String errorMessage) {

        try {
            waitForPageToLoad();

            waitForElementToBeDisplayed(element, timeout, "Failed to wait for " + errorMessage + " to be visible");

            waitForElementToBeClickable(element, timeout, "Failed to wait for " + errorMessage + " to be clickable");
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to wait for page to load then click a visible & clickable element --- " + element + "' - " + e.getMessage());
        }
    }
    //endregion

    //region <waitForElementBooleanToBeDisplayed>
    /**
     * Dynamic boolean method to wait for an element to be Displayed. Mostly used in 'if statement' to check
     * if an element is present, without using Assert.fail()
     * */
    public static boolean waitForElementBooleanToBeDisplayed(By element, Integer timeout, String errorMessage) {

        boolean elementDisplayed = false;
        try {
            int waitCount = 0;
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            while (!elementDisplayed && waitCount < timeout) {
                try {
                    WebDriverWait wait = new WebDriverWait(driver, 1);

                    if ((driver.findElement(element).isDisplayed())) {
                        elementDisplayed = true;
                        break;
                    }
                }
                catch (Exception e) {
                    elementDisplayed = false;
                }
                waitCount++;
            }
        }
        catch (Exception e) {
            elementDisplayed = false;
            log(errorMessage, "ERROR", "text");
        }

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        return elementDisplayed;
    }

    public static boolean waitForElementBooleanToBeDisplayed(String elementXpath, String valueToSelect, Integer timeout, String errorMessage) {

        boolean elementDisplayed = false;
        String updatedXpath;
        try {
            int waitCount = 0;
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            while (!elementDisplayed && waitCount < timeout) {
                try {
                    WebDriverWait wait = new WebDriverWait(driver, 1);
                    updatedXpath = updateXpathValueWithString(elementXpath, valueToSelect); //update default value saved in .properties

                    if ((driver.findElement(By.xpath(updatedXpath)).isDisplayed())) {
                        elementDisplayed = true;
                        break;
                    }
                }
                catch (Exception e) {
                    elementDisplayed = false;
                }
                waitCount++;
            }
        }
        catch (Exception e) {
            elementDisplayed = false;
            log(errorMessage, "ERROR", "text");
        }

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        return elementDisplayed;
    }
    //endregion

    //region <waitForOption>
    /**
     * Dynamic boolean method to wait for an element's value. Mostly used in 'if statement' to check
     * if an element is present, without using Assert.fail()
     * */
    public static boolean waitForOption(String elementXpath, String valueToWaitFor, Integer timeout) {

        boolean elementFound = false;
        String updatedXpath;

        try {
            int waitCount = 0;
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            updatedXpath = updateXpathValueWithString(elementXpath, valueToWaitFor); //update default value saved in .properties

            while (!elementFound && waitCount < timeout) {
                try {
                    WebDriverWait wait = new WebDriverWait(driver, 1);
                    if (wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(updatedXpath))) != null) {
                        elementFound = true;
                        break;
                    }
                }
                catch (Exception e) {
                    elementFound = false;
                }

                waitCount++;
            }
        }
        catch (Exception e) {
            elementFound = false;
            log("Failed to wait for element --- " + elementXpath, "ERROR",  "text");
        }

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        return elementFound;
    }

    public static boolean waitForOption(String elementXpath, String valueToWaitFor) {

        boolean elementFound = false;
        String updatedXpath;

        driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

        try {
            WebDriverWait wait = new WebDriverWait(driver, 1);
            updatedXpath = updateXpathValueWithString(elementXpath, valueToWaitFor); //update default value saved in .properties

            if (wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(updatedXpath))) != null) {
                elementFound = true;
            }
        }
        catch (Exception e) {
            elementFound = false;
            log("Failed to wait for element --- " + elementXpath, "ERROR",  "text");
        }

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        return elementFound;
    }
    //endregion

    //region <waitForOptionByXpath>
    /**
     * Dynamic method to wait for an element Xpath. Using a dynamic Xpath of the element,
     * we just specify the value to select
     * */
    public static void waitForOptionByXpath(String elementXpath, String valueToSelect, String errorMessage) {

        boolean elementFound = false;
        String updatedXpath = "";
        try {
            int waitCount = 0;
            while (!elementFound && waitCount < WaitTimeout) {
                try {
                    WebDriverWait wait = new WebDriverWait(driver, 1);
                    updatedXpath = updateXpathValueWithString(elementXpath, valueToSelect); //update default value saved in .properties

                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(updatedXpath)));
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(updatedXpath)));
                    if (wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(updatedXpath))) != null) {
                        elementFound = true;
                        log("Found element by Xpath: " + updatedXpath, "INFO",  "text");
                        break;
                    }
                }
                catch (Exception e) {
                    elementFound = false;
                }

                waitCount++;
            }
            if (waitCount == WaitTimeout) {
                GetElementFound1(elementFound);
                log(errorMessage, "ERROR",  "text");
                Assert.fail("\n[ERROR] Reached TimeOut whilst waiting for element by Xpath: '" + updatedXpath);
            }
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to wait for element by Xpath --- " + updatedXpath + "' - " + e.getMessage());
        }

        GetElementFound(elementFound);
    }

    /*Dynamic method to wait for an element Xpath. Using a dynamic Xpath of the element, we just specify the 2 values to select*/
    public static void waitForOptionByXpath(String elementXpath, String valueToSelect, String secondValueToSelect, String errorMessage) {

        boolean elementFound = false;
        String updatedXpath = "";
        String updatedSecondXpath = "";
        try {
            int waitCount = 0;
            while (!elementFound && waitCount < WaitTimeout) {
                try {
                    WebDriverWait wait = new WebDriverWait(driver, 1);
                    updatedXpath = updateXpathValueWithString(elementXpath, valueToSelect); //update default value saved in .properties
                    updatedSecondXpath = updateXpathSecondValueWithString(updatedXpath, secondValueToSelect); //update default value saved in .properties

                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(updatedSecondXpath)));
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(updatedSecondXpath)));
                    if (wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(updatedSecondXpath))) != null) {
                        elementFound = true;
                        Assert.assertTrue("Found element by Xpath : " + updatedSecondXpath, true);
                        break;
                    }
                }
                catch (Exception e) {
                    elementFound = false;
                }

                waitCount++;
            }
            if (waitCount == WaitTimeout) {
                GetElementFound1(elementFound);
                log(errorMessage, "ERROR",  "text");
                Assert.fail("\n[ERROR] Reached TimeOut whilst waiting for element by Xpath: '" + updatedSecondXpath);
            }
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to wait for element by Xpath: " + updatedXpath + "' and second element by Xpath: " + updatedSecondXpath + " --- " + e.getMessage());
        }

        GetElementFound(elementFound);
    }

    public static void waitForOptionByXpath(String elementXpath, String valueToSelect, String secondValueToSelect, Integer timeout, String errorMessage) {

        boolean elementFound = false;
        String updatedXpath = "";
        String updatedSecondXpath = "";
        try {
            int waitCount = 0;
            while (!elementFound && waitCount < timeout) {
                try {
                    WebDriverWait wait = new WebDriverWait(driver, 1);
                    updatedXpath = updateXpathValueWithString(elementXpath, valueToSelect); //update default value saved in .properties
                    updatedSecondXpath = updateXpathSecondValueWithString(updatedXpath, secondValueToSelect); //update default value saved in .properties

                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(updatedSecondXpath)));
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(updatedSecondXpath)));
                    if (wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(updatedSecondXpath))) != null) {
                        elementFound = true;
                        Assert.assertTrue("Found element by Xpath : " + updatedSecondXpath, true);
                        break;
                    }
                }
                catch (Exception e) {
                    elementFound = false;
                }

                waitCount++;
            }
            if (waitCount == timeout) {
                GetElementFound1(elementFound);
                log(errorMessage, "ERROR",  "text");
                Assert.fail("\n[ERROR] Reached TimeOut whilst waiting for element by Xpath: '" + updatedSecondXpath);
            }
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to wait for element by Xpath: " + updatedXpath + "' and second element by Xpath: " + updatedSecondXpath + " --- " + e.getMessage());
        }

        GetElementFound(elementFound);
    }

    public static void waitForOptionByXpath(String elementXpath, String valueToSelect, Integer timeout, String errorMessage) {

        boolean elementFound = false;
        String updatedXpath = "";
        try {
            int waitCount = 0;
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);
            while (!elementFound && waitCount < timeout) {
                try {
                    WebDriverWait wait = new WebDriverWait(driver, 1);
                    updatedXpath = updateXpathValueWithString(elementXpath, valueToSelect); //update default value saved in .properties

                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(updatedXpath)));
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(updatedXpath)));
                    if (wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(updatedXpath))) != null) {
                        elementFound = true;
                        Assert.assertTrue("Found element by Xpath : " + updatedXpath, true);
                        break;
                    }
                }
                catch (Exception e) {
                    elementFound = false;
                }

                waitCount++;
            }
            if (waitCount == timeout) {
                GetElementFound1(elementFound);
                log(errorMessage, "ERROR",  "text");
                Assert.fail("\n[ERROR] Reached TimeOut whilst waiting for element by Xpath: '" + updatedXpath);
            }
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to wait for element by Xpath --- " + updatedXpath + "' - " + e.getMessage());
        }

        GetElementFound(elementFound);
    }
    //endregion

    //region <waitForOptionBooleanByXpath>
    /**
     * Dynamic boolean method to wait for an element's value. Mostly used in 'if statement' to check
     * if an element is present, without using Assert.fail()
     * */
    public static boolean waitForOptionBooleanByXpath(String elementXpath, String valueToSelect, String errorMessage) {

        boolean elementFound = false;
        String updatedXpath = "";
        try {
            int waitCount = 0;
            while (!elementFound && waitCount < WaitTimeout) {
                try {
                    WebDriverWait wait = new WebDriverWait(driver, 1);
                    updatedXpath = updateXpathValueWithString(elementXpath, valueToSelect); //update default value saved in .properties

                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(updatedXpath)));
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(updatedXpath)));
                    if (wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(updatedXpath))) != null) {
                        elementFound = true;
                        Assert.assertTrue("Found element by Xpath: " + updatedXpath, true);
                        break;
                    }
                }
                catch (Exception e) {
                    elementFound = false;
                }
                waitCount++;
            }
            if (waitCount == WaitTimeout) {
                GetElementFound1(elementFound);
                System.out.println("Reached TimeOut whilst waiting for element by Xpath : '" + updatedXpath + "'");
            }
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to wait for element by Xpath: " + updatedXpath + "' --- " + e.getMessage());
        }

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        return elementFound;
    }

    /**
     * Dynamic boolean method to wait for an element's value. Mostly used in 'if statement' to check
     * if an element is present, without using Assert.fail()
     * */
    public static boolean waitForOptionBooleanByXpath(String elementXpath, String valueToWaitFor, Integer timeout) {

        boolean elementFound = false;
        String updatedXpath = "";
        try {
            int waitCount = 0;
            driver.manage().timeouts().implicitlyWait(1, TimeUnit.SECONDS);

            while (!elementFound && waitCount < timeout) {
                try {
                    WebDriverWait wait = new WebDriverWait(driver, 1);
                    updatedXpath = updateXpathValueWithString(elementXpath, valueToWaitFor); //update default value saved in .properties

                    if (wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(updatedXpath))) != null) {
                        elementFound = true;
                        log("Found element by Xpath: " + updatedXpath, "INFO",  "text");
                        break;
                    }
                }
                catch (Exception e) {
                    elementFound = false;
                }
                waitCount++;
            }
        }
        catch (Exception e) {
            elementFound = false;
            log("Failed to wait for element by Xpath --- \"" + updatedXpath, "ERROR",  "text");
        }

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);

        return elementFound;
    }
    //endregion

    //region <waitForElementByXpath>
    public static void waitForElementByXpath(String elementXpath, String errorMessage) {

        boolean elementFound = false;
        try {
            int waitCount = 0;
            while (!elementFound && waitCount < WaitTimeout) {
                try {
                    WebDriverWait wait = new WebDriverWait(driver, 1);

                    wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath(elementXpath)));
                    wait.until(ExpectedConditions.elementToBeClickable(By.xpath(elementXpath)));
                    if (wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(elementXpath))) != null) {
                        elementFound = true;
                        System.out.println("Found element by Xpath : " + elementXpath);
                        Assert.assertTrue("Found element by Xpath : " + elementXpath, true);
                        break;
                    }
                }
                catch (Exception e) {
                    elementFound = false;
                    System.out.println(errorMessage);
                    Assert.fail("\n[ERROR] Did Not Find element by Xpath : " + elementXpath + "' - " + e.getMessage());
                }
                //Thread.sleep(500);
                waitCount++;
            }
            if (waitCount == WaitTimeout) {
                GetElementFound1(elementFound);
                System.out.println("Reached TimeOut whilst waiting for element by Xpath : '" + elementXpath + "'");
            }

        }
        catch (Exception e) {
            System.out.println(errorMessage);
            Assert.fail("\n[ERROR] Failed to wait for element by Xpath --- " + elementXpath + "' - " + e.getMessage());
        }

        GetElementFound(elementFound);
    }

    private static boolean GetElementFound(boolean elementFound) {
        return elementFound;
    }

    private static boolean GetElementFound1(boolean elementFound) {
        return elementFound;
    }
    //endregion

    //region <waitForElement>
    public static void waitForElement(By element, String errorMessage) {

        boolean elementFound = false;
        try {
            int waitCount = 0;
            while (!elementFound && waitCount < WaitTimeout) {
                try {
                    WebDriverWait wait = new WebDriverWait(driver, 1);

                    wait.until(ExpectedConditions.presenceOfElementLocated(element));
                    wait.until(ExpectedConditions.elementToBeClickable(element));
                    if (wait.until(ExpectedConditions.visibilityOfElementLocated(element)) != null) {
                        elementFound = true;
                        System.out.println("Found element : " + element);
                        break;
                    }
                }
                catch (Exception e) {
                    elementFound = false;
                    System.out.println(errorMessage);
                    Assert.fail("\n[ERROR] Did Not Find element : " + element + "' - " + e.getMessage());
                }
                //Thread.sleep(500);
                waitCount++;
            }
            if (waitCount == WaitTimeout) {
                GetElementFound1_(elementFound);
                System.out.println("Reached TimeOut whilst waiting for element : '" + element + "'");
            }

        }
        catch (Exception e) {
            System.out.println(errorMessage);
            Assert.fail("\n[ERROR] Failed to wait for element --- " + element);
        }

        GetElementFound_(elementFound);
    }

    private static boolean GetElementFound_(boolean elementFound) {
        return elementFound;
    }

    private static boolean GetElementFound1_(boolean elementFound) {
        return elementFound;
    }
    //endregion

    //region <waitForPageToLoad>
    /**
     * This method is to wait Page to load
     * using JavaScript
     */
    public static void waitForPageToLoad() {

        ExpectedCondition<Boolean> expectation = new ExpectedCondition<Boolean>() {

            public Boolean apply(WebDriver driver) {
                return ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");
            }
        };
        try {
            Thread.sleep(2000);
            WebDriverWait wait = new WebDriverWait(driver, 30);
            wait.until(expectation);

            System.out.println("Waited Page to load successfully");
        }
        catch (Throwable error) {
            Assert.fail("\n[ERROR] Timeout waiting for Page Load Request to complete.");
        }
    }
    //endregion

    //region <waitForPageToLoadAndClickElementByActions>
    /**
     * This method is to wait Page to load
     * using JavaScript, then click element using Actions
     */
    public static void waitForPageToLoadAndClickElementByActions(By element, String errorMessage) {

        try {
            waitForPageToLoad();
            waitForPageToLoad();

            clickElementByActions(element, errorMessage);

            waitForPageToLoad();
        }
        catch (Exception e) {
            log(errorMessage, "ERROR",  "text");
            Assert.fail("\n[ERROR] Failed to wait for page to load then click on element --- " + element + "' - " + e.getMessage());
        }
    }
    //endregion
}
