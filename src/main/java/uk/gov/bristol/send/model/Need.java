package uk.gov.bristol.send.model;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.GeneratedValue;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import org.springframework.data.annotation.Id;


@Container(containerName = "needs")
public class Need{
    @Id
    @GeneratedValue
    @PartitionKey
    public String id;     
    private int formVersion;
    private int areaNumber;    
    private String areaId;
    private String areaLabel;
    private int subAreaNumber;
    private String subAreaId;
    private String subAreaLabel;
    private String subCategoryNumber;
    private String subCategoryId;
    private String subCategoryLabel;
     

    public Need() {
    }

    public Need(int formVersion, int areaNumber, String areaId, String areaLabel, int subAreaNumber, String subAreaId, String subAreaLabel) {
        this.formVersion = formVersion;
        this.areaNumber = areaNumber;
        this.areaId = areaId;
        this.areaLabel = areaLabel;
        this.subAreaNumber = subAreaNumber;
        this.subAreaId = subAreaId;
        this.subAreaLabel = subAreaLabel;    
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
		this.id = id;
	}

    public int getAreaNumber() {
        return areaNumber;
    }


    public String getAreaId() {
        return areaId;
    }


    public String getAreaLabel() {
        return areaLabel;
    }

 
    public int getSubAreaNumber() {
        return subAreaNumber;
    }


    public String getSubAreaId() {
        return subAreaId;
    }


    public String getSubAreaLabel() {
        return subAreaLabel;
    }


    public String getSubCategoryNumber() {
        return subCategoryNumber;
    }


    public String getSubCategoryId() {
        return subCategoryId;
    }


    public String getSubCategoryLabel() {
        return subCategoryLabel;
    }

}
