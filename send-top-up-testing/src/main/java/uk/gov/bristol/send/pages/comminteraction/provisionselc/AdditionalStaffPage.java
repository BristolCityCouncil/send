package uk.gov.bristol.send.pages.comminteraction.provisionselc;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.bristol.send.Assessment;
import uk.gov.bristol.send.SENDException;

@Component
public class AdditionalStaffPage extends SpecialistProfessionalPage {

    @FindBy(id = "dropdown-PTID3-1")
    private WebElement dropDown1;

    @FindBy(id = "dropdown-PTID3-2")
    private WebElement dropDown2;

    @FindBy(id = "dropdown-PTID3-3")
    private WebElement dropDown3;

    @FindBy(id = "dropdown-PTID3-4")
    private WebElement dropDown4;

    @FindBy(id = "dropdown-PTID3-10")
    private WebElement dropDown5;

    @FindBy(id = "dropdown-PTID3-13")
    private WebElement dropDown6;

    @Autowired
    private Assessment assessment;


    private static final String OPTION_1 = "General In class learning support pre-16";
    private static final String OPTION_2 = "General In lesson learning support post-16";
    private static final String OPTION_3 = "In-Class staff support for social interaction";
    private static final String OPTION_4 = "In-Class staff support for social, emotional needs and development";
    private static final String OPTION_5 = "Post-16 support";
    private static final String OPTION_6 = "Life skills/functional skills provision";

    public AdditionalStaffPage(WebDriver webDriver) {
        super(webDriver);
        PageFactory.initElements(webDriver, this);
    }

    public void selectByIndex(String provisionType, String indexStr) {
        String provisionText = null;
        switch (provisionType) {
            case OPTION_1:
                provisionText = selectByIndex(dropDown1, indexStr);
                break;
            case OPTION_2:
                provisionText = selectByIndex(dropDown2, indexStr);
                break;
            case OPTION_3:
                provisionText = selectByIndex(dropDown3, indexStr);
                break;
            case OPTION_4:
                provisionText = selectByIndex(dropDown4, indexStr);
                break;
            case OPTION_5:
                provisionText = selectByIndex(dropDown5, indexStr);
                break;
            case OPTION_6:
                provisionText = selectByIndex(dropDown6, indexStr);
                break;
            default : throw new SENDException("Provision type was not correctly supplied");
        }
        if (provisionText == null) {
            throw new SENDException("Provision text description was not found on the page for " + provisionType);
        }

        setAssessmentProvision(provisionType, provisionText);
    }

}
