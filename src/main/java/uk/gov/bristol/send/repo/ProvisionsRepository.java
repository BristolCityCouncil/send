package uk.gov.bristol.send.repo;

import uk.gov.bristol.send.model.Provision;

import com.azure.spring.data.cosmos.repository.Query;
import com.azure.spring.data.cosmos.repository.CosmosRepository;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface ProvisionsRepository extends CosmosRepository<Provision, String> {    
    Optional<Provision> findById(String id);
 
    @Query("SELECT p.id AS id, p.provisionGroup AS provisionGroup, p.provisionGroupId AS provisionGroupId, ps.specificProvision AS specificProvision, ps.provisionStatementId AS provisionStatementId, t.provisionTypeNumber AS provisionTypeNumber, ps.provisionTypeId AS provisionTypeId, t.provisionTypeLabel AS provisionTypeLabel, ps.level AS level, ps.formVersion as formVersion FROM provisions p JOIN t IN p.provisionsType JOIN ps IN t.provisions")
    List<Provision> findAll();

    @Query("SELECT p.id AS id, p.provisionGroup AS provisionGroup, p.provisionGroupId AS provisionGroupId, ps.specificProvision AS specificProvision, ps.provisionStatementId AS provisionStatementId, t.provisionTypeNumber AS provisionTypeNumber, ps.provisionTypeId AS provisionTypeId, t.provisionTypeLabel AS provisionTypeLabel, ps.level AS level, ps.formVersion as formVersion, ps.code AS code, ps.hoursPerWeek AS hoursPerWeek FROM provisions p JOIN t IN p.provisionsType JOIN ps IN t.provisions WHERE ps.provisionStatementId = @provisionStatementId")
    List<Provision> findProvisionsByStatementId(@Param("provisionStatementId") String provisionStatementId); 
}
