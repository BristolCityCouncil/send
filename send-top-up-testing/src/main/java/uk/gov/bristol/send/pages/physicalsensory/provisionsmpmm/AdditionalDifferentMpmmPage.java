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
public class AdditionalDifferentMpmmPage extends SpecialistProfessionalPage {

    @FindBy(id = "dropdown-PTID8-1")
    private WebElement dropDown1;

    @FindBy(id = "dropdown-PTID8-2")
    private WebElement dropDown2;

    @FindBy(id = "dropdown-PTID8-3")
    private WebElement dropDown3;

    @FindBy(id = "dropdown-PTID8-4")
    private WebElement dropDown4;

    @FindBy(id = "dropdown-PTID8-5")
    private WebElement dropDown5;

    @Autowired
    private Assessment assessment;

    private static final String OPTION_1 = "Training";
    private static final String OPTION_2 = "Post-16 specific provision";
    private static final String OPTION_3 = "VI specific provision";
    private static final String OPTION_4 = "HI Specific Provision";
    private static final String OPTION_5 = "Educational/ communication resources";

    public AdditionalDifferentMpmmPage(WebDriver webDriver) {
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
