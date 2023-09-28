package uk.gov.bristol.send.pages.physicalsensory.provisionsmpmm;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.bristol.send.Assessment;
import uk.gov.bristol.send.SENDException;
import uk.gov.bristol.send.pages.comminteraction.provisionselc.SpecialistProfessionalPage;

@Component
public class AdditionalStaffMpmmPage extends SpecialistProfessionalPage {

    @FindBy(id = "dropdown-PTID3-6")
    private WebElement dropDown1;

    @FindBy(id = "dropdown-PTID3-7")
    private WebElement dropDown2;

    @FindBy(id = "dropdown-PTID3-8")
    private WebElement dropDown3;

    @FindBy(id = "dropdown-PTID3-11")
    private WebElement dropDown4;

    @FindBy(id = "dropdown-PTID3-12")
    private WebElement dropDown5;

    @Autowired
    private Assessment assessment;


    private static final String OPTION_1 = "In class Staff Suppport for vision and hearing needs";
    private static final String OPTION_2 = "Medical condition related staff support";
    private static final String OPTION_3 = "Staff Support for Sensory processing needs";
    private static final String OPTION_4 = "Support over transition between educational settings (pre-16)";
    private static final String OPTION_5 = "Support over transition between educational settings (post-16)";

    public AdditionalStaffMpmmPage(WebDriver webDriver) {
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
            default : throw new SENDException("Provision type was not correctly supplied - " + provisionType);
        }
        if (provisionText == null) {
            throw new SENDException("Provision text description was not found on the page for " + provisionType);
        }

        setAssessmentProvision(provisionType, provisionText);
    }

}
