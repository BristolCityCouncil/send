package uk.gov.bristol.send.pages.cognitionlearning.provisionsale;

import org.openqa.selenium.WebDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import uk.gov.bristol.send.Assessment;
import uk.gov.bristol.send.pages.comminteraction.provisionselc.SpecialistProfessionalPage;

import java.util.HashMap;
import java.util.Map;

@Component
public class SpecialistProfessionalAlePage extends SpecialistProfessionalPage {

    @Autowired
    private Assessment assessment;

    public SpecialistProfessionalAlePage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    protected void setAssessmentProvision(String provisionType, String provisionText) {
        Map<String, String> provisionMap = new HashMap<>();
        provisionMap.put(provisionType, provisionText);
        assessment.setAllProvisions(provisionMap);
    }
}
