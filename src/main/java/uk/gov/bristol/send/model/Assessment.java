package uk.gov.bristol.send.model;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.GeneratedValue;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;
import java.util.List;


@Container(containerName = "assessments")
public class Assessment {
    @Id
    @GeneratedValue
    @PartitionKey
    private String id;      
    private int formVersion;
    private String upn;
    private String owner;
    private String status;
    private String schoolName;
    @JsonProperty("_ts")
    private String modifiedDate;
    private List<NeedStatement> selectedNeedStatements;
    private List<SelectedProvision> selectedProvisions;

    public Assessment() {
    }

    public Assessment(int formVersion, String upn, String owner, String status, String schoolName, List<NeedStatement> selectedNeedStatements, List<SelectedProvision> selectedProvisions) {
        this.formVersion = formVersion;
        this.upn = upn;
        this.owner = owner;
        this.status = status;
        this.schoolName = schoolName;
        this.selectedNeedStatements = selectedNeedStatements;
        this.selectedProvisions = selectedProvisions;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getFormVersion() {
        return formVersion;
    }

    public String getUpn() {
        return upn;
    }

    public String getOwner() {
        return owner;
    }

    public String getStatus() {
        return status;
    }

    public void setSchoolName(String schoolName){
        this.schoolName = schoolName;
    }

    public String getSchoolName(){
        return schoolName;
    }

    public String setModifiedDate(String modifiedDate) {
        return this.modifiedDate = modifiedDate;
    }

    public String getModifiedDate() {
        try {
            long longTime = Long.parseLong(modifiedDate);
            DateTime dt = new DateTime(longTime * 1000);
            return dt.toString("dd/MM/yyyy");
        } catch (NumberFormatException nfe) {
            return "";
        }
    }
    
    public List<NeedStatement> getSelectedNeedStatements() {
        return selectedNeedStatements;
    }

    public void setSelectedNeedStatements(List<NeedStatement> selectedNeedStatements) {
        this.selectedNeedStatements = selectedNeedStatements;
    }

    public List<SelectedProvision> getSelectedProvisions() {
        return selectedProvisions;
    }

    public void setSelectedProvisions(List<SelectedProvision> selectedProvisions) {
        this.selectedProvisions = selectedProvisions;
    }    

    @Override
    public String toString() {
        return "AssessmentEntity [id=" + id + ", formVersion=" + formVersion + ", UPN=" + upn + ", owner=" + owner
                + ", status=" + status + ", modifiedDate=" + modifiedDate + "]\n";
    }
}