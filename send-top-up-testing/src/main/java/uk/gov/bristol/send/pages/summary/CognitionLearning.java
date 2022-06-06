package uk.gov.bristol.send.pages.summary;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CognitionLearning {

    @FindBy(id = "needsPageLink_academicLearning")
    private WebElement aleNeedsTitle;

    @FindBy(id = "provisionPageLink_academicLearning")
    private WebElement aleProvisionsTitle;

    public void clickAleNeedsLink() {
        aleNeedsTitle.findElement(By.linkText("Academic learning and employability")).click();
    }

    public void clickAleProvisionsLink() {
        aleProvisionsTitle.findElement(By.linkText("Academic learning and employability")).click();
    }
}
