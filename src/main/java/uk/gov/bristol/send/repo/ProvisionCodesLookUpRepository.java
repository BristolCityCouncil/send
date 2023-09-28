package uk.gov.bristol.send.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.azure.spring.data.cosmos.repository.CosmosRepository;
import com.azure.spring.data.cosmos.repository.Query;

import uk.gov.bristol.send.model.ProvisionCodesLookUp;

@Repository
public interface ProvisionCodesLookUpRepository extends CosmosRepository<ProvisionCodesLookUp, String> {
	Optional<ProvisionCodesLookUp> findById(String id);

	@Query("SELECT p.id AS id, p.CostPerHour AS costPerHour,  p.Type AS type, p.WeeklyCapInHoursForThisType AS weeklyCapInHoursForThisType, p.WeeklyCapInHoursForDifferentTypes AS weeklyCapInHoursForDifferentTypes FROM ProvisionCodesLookUp p WHERE p.Type = @type")
	List<ProvisionCodesLookUp> findProvisionCodesLookUpByType(@Param("type") String type);

}