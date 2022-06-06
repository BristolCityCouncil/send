package uk.gov.bristol.send.model;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.GeneratedValue;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import org.springframework.data.annotation.Id;


@Container(containerName = "provisions")
public class Provision{
    @Id
    @GeneratedValue
    @PartitionKey
    public String id;
    private int formVersion;     
    private String specificProvision;
    private String grade;
    private String level;
    private String hours;
    private String code;
    private String costPerHr;
    private String total;
    private String annualAmount;  
    private String provisionStatementId;
    private String provisionTypeId;
    private String provisionTypeLabel;
    private String provisionTypeNumber;
    private String provisionGroupId;
    private String provisionGroup;
    
     

    public Provision() {
    }

    public Provision(int formVersion, String level,  String provisionGroupId, String provisionGroup,  String provisionTypeNumber, String provisionTypeId, String provisionTypeLabel, String provisionStatementId, String specificProvision){
        this.formVersion = formVersion;                         
        this.level = level;              
        this.provisionGroup = provisionGroup;
        this.provisionGroupId = provisionGroupId;  
        this.provisionTypeId = provisionTypeId;
        this.provisionTypeLabel = provisionTypeLabel;
        this.provisionTypeNumber = provisionTypeNumber;                
        this.specificProvision = specificProvision;
        this.provisionStatementId = provisionStatementId; 
    }

    public Provision(int formVersion) {
        this.formVersion = formVersion;
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

    public void setFormVersion(int formVersion) {
        this.formVersion = formVersion;
    }

    public String getSpecificProvision() {
        return specificProvision;
    }

    public void setSpecificProvision(String specificProvision) {
        this.specificProvision = specificProvision;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getHours() {
        return hours;
    }

    public void setHours(String hours) {
        this.hours = hours;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCostPerHr() {
        return costPerHr;
    }

    public void setCostPerHr(String costPerHr) {
        this.costPerHr = costPerHr;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getAnnualAmount() {
        return annualAmount;
    }

    public void setAnnualAmount(String annualAmount) {
        this.annualAmount = annualAmount;
    }

    public String getProvisionStatementId() {
        return provisionStatementId;
    }

    public void setProvisionStatementId(String provisionStatementId) {
        this.provisionStatementId = provisionStatementId;
    }    

    public String getProvisionTypeId() {
        return provisionTypeId;
    }

    public void setProvisionTypeId(String provisionTypeId) {
        this.provisionTypeId = provisionTypeId;
    }
 
    public String getProvisionTypeLabel() {
        return provisionTypeLabel;
    }

    public void setProvisionTypeLabel(String provisionTypeLabel) {
        this.provisionTypeLabel = provisionTypeLabel;
    }

    public String getProvisionTypeNumber() {
        return provisionTypeNumber;
    }

    public void setProvisionTypeNumber(String provisionTypeNumber) {
        this.provisionTypeNumber = provisionTypeNumber;
    }  
    
    public String getProvisionGroupId() {
        return provisionGroupId;
    }

    public void setProvisionGroupId(String provisionGroupId) {
        this.provisionGroupId = provisionGroupId;
    } 
    
    public String getProvisionGroup() {
        return provisionGroup;
    }

    public void setProvisionGroup(String provisionGroup) {
        this.provisionGroup = provisionGroup;
    } 
}