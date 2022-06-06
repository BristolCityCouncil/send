package uk.gov.bristol.send;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
public class Assessment {

    private String upn;

    private String elcSelectedNeedLevel;

    private String elcProvisionType;

    private Map<String, String> elcProvisions = new HashMap<>();

    private Map<String, String> elcProvisionsTransient = new HashMap<>();
    private Set<String> elcProvisionsTransientDelete = new HashSet<>();

    public String getElcProvisionType() {
        return elcProvisionType;
    }

    public void setElcProvisionType(String elcProvisionType) {
        this.elcProvisionType = elcProvisionType;
    }

    public String getUpn() {
        return upn;
    }

    public void setUpn(String upn) {
        this.upn = upn;
    }

    public String getElcSelectedNeedLevel() {
        return elcSelectedNeedLevel;
    }

    public void setElcSelectedNeedLevel(String elcSelectedNeedLevel) {
        this.elcSelectedNeedLevel = elcSelectedNeedLevel;
    }

    /**
     * Method to initialise all the values from the state held.
     * Used at the end of every scenario to prevent values being propagated.
     */
    public void clearValues() {
        this.elcSelectedNeedLevel = "";
        this.elcProvisionType = "";
        this.elcProvisions.clear();
        this.elcProvisionsTransient.clear();
        this.elcProvisionsTransientDelete.clear();
    }
    /**
     * Return a Map of ProvisionType / ProvisionText
     * @return
     */
    public Map<String, String> getElcProvisions() {
        return elcProvisions;
    }

    /**
     * Values held are transient until either Save is clicked or the popup OK button is clicked.
     * Then transient values are moved into elcProvisions Map
     * @param provisionType key
     * @param provisionText value
     */
    public void setElcProvisions(String provisionType, String provisionText) {
        elcProvisionsTransient.put(provisionType, provisionText);
    }

    public void removeElcProvision(String provisionType) {
        elcProvisionsTransientDelete.add(provisionType);
    }
    /**
     * Values not yet saved are held but discarded if 'Back' button is pressed before 'Save'
     */
    public void clearTransientValues() {
        elcProvisionsTransient.clear();
    }

    public void saveTransientValues() {
        elcProvisions.putAll(elcProvisionsTransient);
        for (String key : elcProvisionsTransientDelete) {
            elcProvisions.remove(key);
        }
    }
}
