package uk.gov.bristol.send;

import io.cucumber.spring.ScenarioScope;
import org.springframework.stereotype.Component;
import uk.gov.bristol.send.pages.NeedsPage;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Component
@ScenarioScope
public class Assessment {

    private NeedsPage needsPage;

    private Map<NeedsPage, Map<String, String>> allProvisions = new HashMap<>();
    private Map<NeedsPage, Map<String, String>> allProvisionsTransient = new HashMap<NeedsPage, Map<String, String>>();
    private Map<NeedsPage, Set<String>> allProvisionsDelete = new HashMap<>();
    private Set<String> deleteSet = new HashSet<>();

    public NeedsPage getNeedsPage() {
        return needsPage;
    }

    public void setNeedsPage(NeedsPage needsPage) {
        this.needsPage = needsPage;
    }

    public Map<String, String> getAllProvisions(NeedsPage needsPage) {
        return allProvisions.get(needsPage);
    }

    /**
     * Values held are transient until either Save is clicked or the popup OK button is clicked.
     * Then transient values are moved into elcProvisions Map
     * @param provisions Map<String, String>
     */
    public void setAllProvisions(Map<String, String> provisions) {
        if (!allProvisionsTransient.containsKey(needsPage)) {
            allProvisionsTransient.put(needsPage, provisions);
        } else {
            allProvisionsTransient.forEach((provisionPage, keyVals) -> {
                // if key exists then add keyVals to existing key
                Map<String, String> provisionMap = allProvisionsTransient.get(needsPage);
                provisionMap.putAll(provisions);
            });
        }
    }

    public void removeProvision(String provisionType) {
        deleteSet.add(provisionType);
        allProvisionsDelete.put(needsPage, deleteSet);
    }

    /**
     * Values not yet saved are held but discarded if 'Back' button is pressed before 'Save'
     */
    public void clearTransientValues() {
        allProvisionsTransient.clear();
    }

    private void saveAllProvisions(Map<String, String> persistedProvisions,
                                   Map<String, String> transientProvisions,
                                   Set<String> deletedProvisions) {
        persistedProvisions.putAll(transientProvisions);
        for (String removeItem : deletedProvisions) {
            persistedProvisions.remove(removeItem);
        }
    }

    public void saveTransientValues() {
        persistProvisions();
    }

    private void persistProvisions() {
        // each key/value => DropDown Title/DropDown SelectItem
        allProvisionsTransient.forEach((provisionPage, keyVals) -> {
            // if key exists then add keyVals to existing key
            if (allProvisions.containsKey(provisionPage)) {
                Map<String, String> stringStringMap = allProvisions.get(provisionPage);
                stringStringMap.putAll(keyVals);
            } else {
                // add new key and associated keyVals
                allProvisions.put(provisionPage, allProvisionsTransient.get(provisionPage));
            }
        });

        allProvisionsDelete.forEach((needsPage, deleteKey) -> {
            Map<String, String> provisionsMap = allProvisions.get(needsPage);
            deleteKey.forEach(s -> {
                provisionsMap.remove(s);
            });
        });

    }

}
