package uk.gov.bristol.send.model;

public class NeedStatement {
    private String areaId;
    private String areaLabel;
    private String subAreaId;
    private String subAreaLabel;
    private String subCategoryId;
    private String subCategoryLabel;
    private String statementGroupNumber;
    private String statementNumber;
    private String statementLabel;
    private String statementLevel;
     

    public NeedStatement(String areaId, String areaLabel, String subAreaId, String subAreaLabel, String subCategoryId, String subCategoryLabel,
            String statementGroupNumber, String statementNumber, String statementLabel, String statementLevel) {
        this.areaId = areaId;
        this.areaLabel = areaLabel;                
        this.subAreaId = subAreaId;
        this.subAreaLabel = subAreaLabel;
        this.subCategoryId = subCategoryId;
        this.subCategoryLabel = subCategoryLabel;
        this.statementGroupNumber = statementGroupNumber;
        this.statementNumber = statementNumber;
        this.statementLabel = statementLabel;
        this.statementLevel = statementLevel;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getareaLabel() {
        return areaLabel;
    }

    public void setAreaLabel(String areaLabel) {
        this.areaLabel = areaLabel;
    }

    public String getSubAreaId() {
        return subAreaId;
    }

    public void setSubAreaId(String subAreaId) {
        this.subAreaId = subAreaId;
    }

    public String getSubAreaLabel() {
        return subAreaLabel;
    }

    public void setSubAreaLabel(String subAreaLabel) {
        this.subAreaLabel = subAreaLabel;
    }

    public String getSubCategoryId() {
        return subCategoryId;
    }

    public void setSubCategoryId(String subCategoryId) {
        this.subCategoryId = subCategoryId;
    }

    public String getStatementLabel() {
        return statementLabel;
    }

    public void setStatementLabel(String statementLabel) {
        this.statementLabel = statementLabel;
    }

    public String getStatementLevel() {
        return statementLevel;
    }

    public void setStatementLevel(String statementLevel) {
        this.statementLevel = statementLevel;
    }

    public String getSubCategoryLabel() {
        return subCategoryLabel;
    }

    public void setSubCategoryLabel(String subCategoryLabel) {
        this.subCategoryLabel = subCategoryLabel;
    }

    public String getStatementGroupNumber() {
        return statementGroupNumber;
    }

    public void setStatementGroupNumber(String statementGroupNumber) {
        this.statementGroupNumber = statementGroupNumber;
    }

    public String getStatementNumber() {
        return statementNumber;
    }

    public void setStatementNumber(String statementNumber) {
        this.statementNumber = statementNumber;
    }

}
