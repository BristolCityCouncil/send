package uk.gov.bristol.send.repo;

import uk.gov.bristol.send.model.Assessment;
import uk.gov.bristol.send.model.NeedStatement;

import com.azure.spring.data.cosmos.repository.Query;
import com.azure.spring.data.cosmos.repository.CosmosRepository;

import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.List;


@Repository
public interface AssessmentsRepository extends CosmosRepository<Assessment, String> {
    List<Assessment> findByUpn(String Upn);

    List<Assessment> findByOwner(String owner);

    Optional<Assessment> findById(String id);

    @Query("SELECT * FROM assessments a WHERE a.id = @assessmentId AND a.owner = @ownerEmail")
    List<Assessment> getByIdForOwner(@Param("assessmentId") String assessmentId, @Param("ownerEmail") String ownerEmail);

    @Query("SELECT s.subAreaId AS subAreaId, s.subAreaLabel AS subAreaLabel, s.subCategoryId AS subCategoryId, s.subCategoryLabel AS subCategoryLabel, s.statementGroupNumber AS statementGroupNumber, s.statementNumber AS statementNumber, s.statementLabel AS statementLabel, s.statementLevel AS statementLevel	FROM assessments a JOIN s IN a.selectedNeedStatements WHERE a.id = @assessmentId")    
    List<NeedStatement> getSelectedNeedStatements(@Param("assessmentId") String assessmentId);

    @Query("SELECT * FROM assessments a WHERE a.owner = @ownerEmail ORDER BY a._ts DESC")
    List<Assessment> findByOwnerOrdered(@Param("ownerEmail") String ownerEmail);
}