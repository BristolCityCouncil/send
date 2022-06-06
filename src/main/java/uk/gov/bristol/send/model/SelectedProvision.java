package uk.gov.bristol.send.model;


public class SelectedProvision{
    private String subAreaId;
	private String needLevel;
    private String provisionGroup;
    private String provisionStatementId;
    private String specificProvision;
    private String provisionTypeLabel;
 
    public SelectedProvision() {
    }

    public SelectedProvision(String subAreaId, String needLevel, String provisionGroup, String provisionStatementId, String specificProvision, String provisionTypeLabel) {
        this.subAreaId = subAreaId;
        this.needLevel = needLevel;
        this.provisionGroup = provisionGroup;
        this.provisionStatementId = provisionStatementId;        
        this.specificProvision = specificProvision;
        this.provisionTypeLabel = provisionTypeLabel;
    }

    public String getSubAreaId() {
        return subAreaId;
    }

    public void setSubAreaId(String subAreaId) {
        this.subAreaId = subAreaId;
    }

    public String getProvisionGroup() {
        return provisionGroup;
    }

    public void setProvisionGroup(String provisionGroup) {
        this.provisionGroup = provisionGroup;
    } 

    public String getProvisionStatementId() {
        return provisionStatementId;
    }

    public void setProvisionStatementId(String provisionStatementId) {
        this.provisionStatementId = provisionStatementId;
    } 

    public String getNeedLevel() {
        return needLevel;
    }

    public void setNeedLevel(String needLevel) {
        this.needLevel = needLevel;
    }  

    public String getSpecificProvision() {
        return specificProvision;
    }

    public void setSpecificProvision(String specificProvision) {
        this.specificProvision = specificProvision;
    }

	public String getProvisionTypeLabel() {
		return provisionTypeLabel;
	}

	public void setProvisionTypeLabel(String provisionTypeLabel) {
		this.provisionTypeLabel = provisionTypeLabel;
	} 
     
}
