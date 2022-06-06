package uk.gov.bristol.send.model;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.GeneratedValue;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import org.springframework.data.annotation.Id;


@Container(containerName = "provisionsLookUp")
public class ProvisionLookUp{
    @Id
    @GeneratedValue
    @PartitionKey
    public String id;        
    private int formVersion;
    private String subAreaId;
	private String provisionGroups;
         
 
    public ProvisionLookUp() {
    }

    public ProvisionLookUp(int formVersion, String subAreaId, String provisionGroups) {
        this.formVersion = formVersion;
        this.subAreaId = subAreaId;
        this.provisionGroups = provisionGroups;        
    }

    public String getId() {
        return id;
    }

    public int getFormVersion() {
        return formVersion;
    }

    public void setFormVersion(int formVersion) {
        this.formVersion = formVersion;
    }

    public String getSubAreaId() {
        return subAreaId;
    }

    public void setSubAreaId(String subAreaId) {
        this.subAreaId = subAreaId;
    }
 
    public String getProvisionGroups() {
        return provisionGroups;
    }

    public void setProvisionGroups(String provisionGroups) {
        this.provisionGroups = provisionGroups;
    } 
     
}	