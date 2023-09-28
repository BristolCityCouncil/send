package uk.gov.bristol.send.model;

import com.azure.spring.data.cosmos.core.mapping.Container;
import com.azure.spring.data.cosmos.core.mapping.GeneratedValue;
import com.azure.spring.data.cosmos.core.mapping.PartitionKey;
import org.springframework.data.annotation.Id;

@Container(containerName = "provisionCodesLookUp")
public class ProvisionCodesLookUp {
	@Id
	@GeneratedValue
	@PartitionKey
	public String id;
	private int formVersion;
	private String type;
	private String description;
	private Double costPerHour;
	private String hnbBudget;
	private Double weeklyCapInHoursForThisType;
	private Double weeklyCapInHoursForDifferentTypes;
	private String popUp;
	private Double agreedPercentContribution;
	private String calculationNotes;

	public ProvisionCodesLookUp() {
	}

	public ProvisionCodesLookUp(int id, int formVersion, String type, String description, Double costPerHour,
			String hnbBudget, Double weeklyCapInHoursForThisType, Double WeeklyCapInHoursForDifferentTypes,
			String PopUp, Double agreedPercentContribution, String calculationNotes) {
		this.formVersion = formVersion;
		this.description = description;
		this.costPerHour = costPerHour;
		this.hnbBudget = hnbBudget;
		this.weeklyCapInHoursForThisType = weeklyCapInHoursForThisType;
		this.weeklyCapInHoursForDifferentTypes = WeeklyCapInHoursForDifferentTypes;
		this.popUp = PopUp;
		this.agreedPercentContribution = agreedPercentContribution;
		this.calculationNotes = calculationNotes;
	}

	public String getId() {
		return id;
	}

	public int getFormVersion() {
		return formVersion;
	}

	public String getDescription() {
		return description;
	}

	public Double getCostPerHour() {
		return costPerHour;
	}

	public String getHnbBudget() {
		return hnbBudget;
	}

	public String getType() {
		return type;
	}

	public Double getWeeklyCapInHoursForThisType() {
		return weeklyCapInHoursForThisType;
	}

	public Double getWeeklyCapInHoursForDifferentTypes() {
		return weeklyCapInHoursForDifferentTypes;
	}

	public String getPopUp() {
		return popUp;
	}

	public Double getAgreedPercentContribution() {
		return agreedPercentContribution;
	}

	public String getCalculationNotes() {
		return calculationNotes;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setFormVersion(int formVersion) {
		this.formVersion = formVersion;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setCostPerHour(Double costPerHour) {
		this.costPerHour = costPerHour;
	}

	public void setHnbBudget(String hnbBudget) {
		this.hnbBudget = hnbBudget;
	}

	public String calculationNotes() {
		return calculationNotes;
	}
}