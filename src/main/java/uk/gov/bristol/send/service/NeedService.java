package uk.gov.bristol.send.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.eclipse.collections.impl.block.factory.HashingStrategies;
import org.eclipse.collections.impl.utility.ListIterate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.gov.bristol.send.SendUtilities;
import uk.gov.bristol.send.model.Need;
import uk.gov.bristol.send.model.NeedStatement;
import uk.gov.bristol.send.repo.NeedsRepository;


@Service
public class NeedService {

    @Autowired
    NeedsRepository needsRepository;

    @Autowired
    SendUtilities sendUtilities;

    public NeedService(NeedsRepository needsRepository, SendUtilities sendUtilities) {
        this.needsRepository = needsRepository;
        this.sendUtilities = sendUtilities;
    }

    public List<Need> getAllNeeds() throws Exception {
        Iterable<Need> iterNeeds = needsRepository.findAll();
        List<Need> needList = sendUtilities.iterableNeedsToList(iterNeeds);

        if ((needList != null) && (needList.size() > 0)) {
            return needList;
        } else {
            throw new Exception("No needs found");
        }
    }

    public List<Need> getNeedsByAreaId(String needAreaId) throws Exception {
        Iterable<Need> iterNeeds = needsRepository.findByAreaId(needAreaId);
        List<Need> needList = sendUtilities.iterableNeedsToList(iterNeeds);

        if ((needList != null) && (needList.size() > 0)) {
            return needList;
        } else {
            throw new Exception("No needs found for given area id: " + needAreaId);
        }
    }

    public List<Need> getNeedsBySubAreaId(String needSubAreaId) throws Exception {
        Iterable<Need> iterNeeds = needsRepository.findBySubAreaId(needSubAreaId);
        List<Need> needList = sendUtilities.iterableNeedsToList(iterNeeds);

        if ((needList != null) && (needList.size() > 0)) {
            return needList;
        } else {
            throw new Exception("No needs found for given sub area id: " + needSubAreaId);
        }
    }

    public List<Need> getAllNeedAreas() throws Exception {
        Iterable<Need> iterNeeds = needsRepository.findAll();
        List<Need> needList = sendUtilities.iterableNeedsToList(iterNeeds);
        List<Need> needsAreaList = ListIterate.distinct(needList, HashingStrategies.fromFunction(Need::getAreaId));  

        if ((needsAreaList != null) && (needsAreaList.size() > 0)) {
            return needsAreaList;
        } else {
            throw new Exception("No need areas found!");
        }
    }
    
    public List<Need> getAllNeedSubAreas() throws Exception {
        Iterable<Need> iterNeeds = needsRepository.findAll();
        List<Need> subAreaList = sendUtilities.iterableNeedsToList(iterNeeds);
        
        if ((subAreaList != null) && (subAreaList.size() > 0)) {
            return subAreaList;
        } else {
            throw new Exception("No need sub areas found!");
        }
    }
 
    public List<NeedStatement> getNeedStatementsForSubArea(String subAreaId, boolean simulateDbFailure) throws Exception {
        Iterable<NeedStatement> iterNeedStatements = needsRepository.findStatementsForSubArea(subAreaId);
        List<NeedStatement> needStatementsList = sendUtilities.iterableStatementsToList(iterNeedStatements);
            
        if ((needStatementsList != null) && (needStatementsList.size() > 0) && !simulateDbFailure) {
            return needStatementsList;
        } else {
            throw new Exception("Test query failed!");
        }
    }

    public List<NeedStatement> getNeedStatementById(String needStatementNumber) throws Exception {
        return needsRepository.findNeedStatementByStatementNumber(needStatementNumber);
    }

    public String findCurrentNeedLevel(List<NeedStatement> needStatements){
        String currentLevel = "";        
    
        for(int i=0; i< needStatements.size(); i++){
           String level = needStatements.get(i).getStatementLevel();
           if(level == null){
              currentLevel = "";
           }else if(level.equals("c")){
              currentLevel = "C";
           }else if(level.equals("b") && !currentLevel.equals("C")){
              currentLevel = "B";
           }else if(level.equals("a") && !currentLevel.equals("B") && !currentLevel.equals("C")){
              currentLevel = "A";
           }            
        }    
        
        return currentLevel;
    }

    public Map<String, String> findNeedLevelForSubAreas(List<NeedStatement> needStatements){
        Map<String, String> subAreaLevels = new HashMap<String, String>();
        List<NeedStatement> needStatementsList = ListIterate.distinct(needStatements, HashingStrategies.fromFunction(NeedStatement::getSubAreaId));  

        for(int i=0; i< needStatementsList.size(); i++){
            String subAreaId = needStatementsList.get(i).getSubAreaId();
            List<NeedStatement> statementsInGroup = needStatements.stream()
            .filter(n -> n.getSubAreaId().equals(subAreaId))
            .collect(Collectors.toList()); 
            
            subAreaLevels.put(subAreaId, findCurrentNeedLevel(statementsInGroup));
        }    

        return subAreaLevels;
    }
    
   public List<NeedStatement> getSelectedNeedStatementsGroupByAreaId(List<NeedStatement>  selectedNeedStatements ) {	   
	    Map<String,List<NeedStatement>> needStatementsGroupedItems = selectedNeedStatements.stream()
	            .collect(Collectors.groupingBy(NeedStatement::getAreaId));	            
	    selectedNeedStatements.clear();
	    
	    needStatementsGroupedItems.forEach((id, value) -> { 
	    	selectedNeedStatements.addAll(value);
	    });
	   
	    return selectedNeedStatements;	   
   }

}
