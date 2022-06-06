package uk.gov.bristol.send.repo;

import uk.gov.bristol.send.model.ProvisionLookUp;
import com.azure.spring.data.cosmos.repository.Query;
import com.azure.spring.data.cosmos.repository.CosmosRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface ProvisionsLookUpRepository extends CosmosRepository<ProvisionLookUp, String> {    
    Optional<ProvisionLookUp> findById(String id);
    List<ProvisionLookUp> findBySubAreaId(String id);
         
    @Query("SELECT * FROM provisionsLookUp p WHERE p.subAreaId = @subAreaId")
    List<ProvisionLookUp> findProvisionIdsBySubAreaId(@Param("subAreaId") String subAreaId); 

}