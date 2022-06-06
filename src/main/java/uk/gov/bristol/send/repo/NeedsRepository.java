package uk.gov.bristol.send.repo;

import uk.gov.bristol.send.model.Need;
import uk.gov.bristol.send.model.NeedStatement;

import com.azure.spring.data.cosmos.repository.Query;
import com.azure.spring.data.cosmos.repository.CosmosRepository;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;

@Repository
public interface NeedsRepository extends CosmosRepository<Need, String> {
    List<Need> findByAreaId(String areaId);
    List<Need> findBySubAreaId(String needSubAreaId);    
    Optional<Need> findById(String id);
 
    @Query("SELECT s.subCategoryLabel, s.subCategoryId FROM needs n JOIN s IN n.subCategory WHERE n.subAreaId = @subAreaId")
    List<Need> findSubCategoryLabel(@Param("subAreaId") String subAreaId);
 
    @Query("SELECT n.areaId as areaId, n.areaLabel as areaLabel, n.subAreaId AS subAreaId, n.subAreaLabel AS subAreaLabel, sc.subCategoryId AS subCategoryId, sc.subCategoryLabel AS subCategoryLabel, st.statementGroupNumber AS statementGroupNumber, sg.statementNumber AS statementNumber, sg.statementLabel AS statementLabel, sg.statementLevel AS statementLevel FROM needs n JOIN sc IN n.subCategory	JOIN st IN sc.statementGroup JOIN sg IN st.statements WHERE n.subAreaId = @subAreaId")
    List<NeedStatement> findStatementsForSubArea(@Param("subAreaId") String subAreaId);

    // should be only a single result but using a list not optional because cosmosrepository returns single objects in a list
    @Query("SELECT n.areaId as areaId, n.areaLabel as areaLabel, n.subAreaId AS subAreaId, n.subAreaLabel AS subAreaLabel, s.subCategoryId AS subCategoryId, s.subCategoryLabel AS subCategoryLabel, t.statementGroupNumber AS statementGroupNumber, a.statementNumber AS statementNumber, a.statementLabel AS statementLabel, a.statementGroup AS statementGroup, a.statementLevel AS statementLevel FROM needs n JOIN s IN n.subCategory JOIN t IN s.statementGroup JOIN a IN t.statements WHERE a.statementNumber = @needStatementNumber")
    List<NeedStatement> findNeedStatementByStatementNumber(@Param("needStatementNumber") String needStatementNumber);
}
