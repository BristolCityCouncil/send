package uk.gov.bristol.send.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@PropertySource("classpath:application.properties")
public class HomePage {

    @Value( "${emailAddress}" )
    private String username;

    @Value( "${userPassword}" )
    private String password;

    @FindBy(id = "upn")
    private WebElement inputUPN;

    @FindBy(id = "i0116")
    private WebElement emailField;

    @FindBy(id = "i0118")
    private WebElement passwordField;

    @FindBy(id = "idSIButton9")
    private WebElement nextButton;

    @FindBy(id = "schoolName")
    private WebElement inputSchool;

    @FindBy(id = "submit")
    private WebElement createNew;

    @FindBy(linkText = "Edit")
    private WebElement viewEditLink;

    @FindBy(linkText = "Delete")
    private WebElement deleteLink;

    @FindBy(id = "schoolNameInvalid")
    private WebElement schoolInvalidMsg;

    @FindBy(id = "upnInUseError")
    private WebElement upnExistsMsg;

    @FindBy(id = "upnInvalid")
    private WebElement upnInvalidMsg;

    @FindBy(className = "info__title")
    private WebElement assessmentUPN;

    private String applicationURL = "";

    private WebDriver webDriver;

    private boolean phaseTwo;

    private static final String DEFAULT_URL = "https://bcuatappbcaw005-send.azurewebsites.net/top-up-assessment";

    public HomePage(WebDriver webDriver) {
        this.webDriver = webDriver;
        PageFactory.initElements(webDriver, this);

        applicationURL = System.getProperty("baseURL");
        if (applicationURL == null) {
            applicationURL = DEFAULT_URL;
        }
    }

    public void setApplicationURL(String applicationURL){
        this.applicationURL = applicationURL;
    }

    public String getApplicationURL(){
        return this.applicationURL;
    }

    public void typeUPN(String valueIn) {
        inputUPN.sendKeys(valueIn);
    }

    public void typeSchoolName(String valueIn) {
        inputSchool.sendKeys(valueIn);
    }

    public void enterEmail(String email) {
        emailField.sendKeys(email);
    }
    public void enterPassword(String password) {
        passwordField.sendKeys(password);
    }
    public void clickNext() {
        nextButton.click();
    }

    public WebElement getCreateNewButton() {
        return createNew;
    }
    public void clickCreateNew() {
        createNew.click();
    }

    public void clickViewEdit(){
        viewEditLink.click();
    }

    public void clickDelete() {
        deleteLink.click();
    }

    public String getSchoolErrorMsg() {
        return schoolInvalidMsg.getText();
    }

    public String getUpnExistsMsg() {
        return upnExistsMsg.getText();
    }

    public String getUpnInvalidMsg() {
        return upnInvalidMsg.getText();
    }

    public WebElement getAssessmentUPN() {
        return assessmentUPN;
    }

    public void setPhaseTwo(boolean phaseTwo){
        this.phaseTwo = phaseTwo;
    }

    public void load(){

        if((phaseTwo) && (!applicationURL.contains("devPhase=two"))){
            applicationURL = applicationURL + "?devPhase=two";
        }

        if((!phaseTwo) && (applicationURL.contains("devPhase=two"))){
            applicationURL = applicationURL.replace("devPhase=two", "devPhase=");
        }
        this.webDriver.get(applicationURL);

        if (webDriver.getCurrentUrl().contains("login.microsoftonline.com")) {

            WebElement emailAddress = new WebDriverWait(webDriver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(emailField));
            emailAddress.sendKeys(this.username);
            clickNext();

            WebElement passwordWait = new WebDriverWait(webDriver, Duration.ofSeconds(5)).until(ExpectedConditions.elementToBeClickable(passwordField));
            passwordWait.sendKeys(this.password);
            clickNext();
            WebElement updatedButton = new WebDriverWait(webDriver, Duration.ofSeconds(10)).until(ExpectedConditions.elementToBeClickable(By.id("idBtn_Back")));
            updatedButton.click();
        }
    }

}
