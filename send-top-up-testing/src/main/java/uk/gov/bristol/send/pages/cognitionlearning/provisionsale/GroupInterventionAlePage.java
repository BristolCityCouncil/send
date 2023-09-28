package uk.gov.bristol.send.pages.cognitionlearning.provisionsale;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.bristol.send.Assessment;
import uk.gov.bristol.send.SENDException;
import uk.gov.bristol.send.pages.comminteraction.provisionselc.GroupInterventionPage;
import uk.gov.bristol.send.pages.comminteraction.provisionselc.SpecialistProfessionalPage;

@Component
public class GroupInterventionAlePage extends GroupInterventionPage {

    @FindBy(id = "dropdown-PTID2-1")
    private WebElement dropDown1;

    @FindBy(id = "dropdown-PTID2-2")
    private WebElement dropDown2;

    @FindBy(id = "dropdown-PTID2-3")
    private WebElement dropDown3;

    @FindBy(id = "dropdown-PTID2-5")
    private WebElement dropDown4;

    @FindBy(id = "dropdown-PTID2-6")
    private WebElement dropDown5;

    @Autowired
    private Assessment assessment;

    private static final String OPTION_1 = "Generalisation and reinforcement";
    private static final String OPTION_2 = "1:1 Intervention";
    private static final String OPTION_3 = "Small group Intervention";
    private static final String OPTION_4 = "Post-16 tutoring";
    private static final String OPTION_5 = "Post-16 exam support";


    public GroupInterventionAlePage(WebDriver webDriver) {
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
            default : throw new SENDException("Provision type was not correctly supplied");
        }
        if (provisionText == null) {
            throw new SENDException("Provision text description was not found on the page for " + provisionType);
        }
        setAssessmentProvision(provisionType, provisionText);
    }

}
