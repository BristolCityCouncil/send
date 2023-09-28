package uk.gov.bristol.send.model;

import java.util.List;

import org.joda.time.DateTime;
import org.springframework.data.annotation.Id;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.GeneratedValue;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import com.fasterxml.jackson.annotation.JsonProperty;

import uk.gov.bristol.send.fileupload.model.UploadedFileInfo;


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
    private String provisionReviewStatus;
   
    private String totalAnnualCost;
    private boolean totalHourlyCapExceeded;
    private boolean typeHourlyCapExceeded;
    private String uploadFolderId;
    private List<UploadedFileInfo> uploadedFilesInfo;
    
    private String submittedDate;
    private String applicationStatus;

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
    
	public String getProvisionReviewStatus() {
		return provisionReviewStatus;
	}

	public void setProvisionReviewStatus(String provisionReviewStatus) {
		this.provisionReviewStatus = provisionReviewStatus;
	}		

	public String getTotalAnnualCost() {
		return totalAnnualCost;
	}

	public void setTotalAnnualCost(String totalAnnualCost) {
		this.totalAnnualCost = totalAnnualCost;
	}		

	public boolean isTotalHourlyCapExceeded() {
		return totalHourlyCapExceeded;
	}

	public void setTotalHourlyCapExceeded(boolean totalHourlyCapExceeded) {
		this.totalHourlyCapExceeded = totalHourlyCapExceeded;
	}		

	public boolean isTypeHourlyCapExceeded() {
		return typeHourlyCapExceeded;
	}

	public void setTypeHourlyCapExceeded(boolean typeHourlyCapExceeded) {
		this.typeHourlyCapExceeded = typeHourlyCapExceeded;
	}
		
	public String getUploadFolderId() {
        if(uploadFolderId == null || uploadFolderId.trim().isEmpty()){
            this.uploadFolderId = this.upn + " " + this.schoolName;
        }
		return uploadFolderId;
	}

	public void setUploadFolderId(String uploadFolderId) {
		this.uploadFolderId = uploadFolderId;
	}	

	public List<UploadedFileInfo> getUploadedFilesInfo() {
		return uploadedFilesInfo;
	}

	public void setUploadedFilesInfo(List<UploadedFileInfo> uploadedFilesInfo) {
		this.uploadedFilesInfo = uploadedFilesInfo;
	}		

	public String getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(String submittedDate) {
		this.submittedDate = submittedDate;
	}

	public String getApplicationStatus() {
		return applicationStatus;
	}

	public void setApplicationStatus(String applicationStatus) {
		this.applicationStatus = applicationStatus;
	}

	@Override
    public String toString() {
        return "AssessmentEntity [id=" + id + ", formVersion=" + formVersion + ", UPN=" + upn + ", owner=" + owner
                + ", status=" + status + ", modifiedDate=" + modifiedDate + "]\n";
    }
}