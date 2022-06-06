package uk.gov.bristol.send.pages.comminteraction.provisionsrlc;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.bristol.send.Assessment;
import uk.gov.bristol.send.pages.comminteraction.provisionselc.SpecialistProfessionalPage;

@Component
public class SpecialistProfessionalRlcPage extends SpecialistProfessionalPage {

    @Autowired
    private Assessment assessment;

    public SpecialistProfessionalRlcPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected void setAssessmentProvision(String provisionType, String provisionText) {
        assessment.setRlcProvisions(provisionType, provisionText);
    }
}
