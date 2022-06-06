package uk.gov.bristol.send.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.lang.Iterable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uk.gov.bristol.send.model.Assessment;
import uk.gov.bristol.send.model.NeedStatement;
import uk.gov.bristol.send.model.Provision;
import uk.gov.bristol.send.model.SelectedProvision;
import uk.gov.bristol.send.repo.AssessmentsRepository;

import uk.gov.bristol.send.SendUtilities;

@Service
public class AssessmentService {

    @Autowired
    AssessmentsRepository assessmentsRepository;

    @Autowired 
    NeedService needService;

    @Autowired
    SendUtilities sendUtilities;


    public AssessmentService(AssessmentsRepository assessmentsRepository, SendUtilities sendUtilities, NeedService needService) {
        this.assessmentsRepository = assessmentsRepository;
        this.sendUtilities = sendUtilities;
        this.needService = needService;
    }

    public List<Assessment> getAllAssessments() throws Exception {
        Iterable<Assessment> iterAssessments = assessmentsRepository.findAll();
        List<Assessment> assessmentsList = sendUtilities.iterableAssessmentsToList(iterAssessments);

        if ((assessmentsList != null) && (assessmentsList.size() > 0)) {
            return assessmentsList;
        } else {
            throw new Exception("No assessments found");
        }
    }

    public List<Assessment> getAssessmentsByUPN(String upn) throws Exception {
        Iterable<Assessment> iterAssessments = assessmentsRepository.findByUpn(upn);
        List<Assessment> assessmentsList = sendUtilities.iterableAssessmentsToList(iterAssessments);

        if ((assessmentsList != null) && (assessmentsList.size() > 0)) {
            return assessmentsList;
        } else {
            throw new Exception("No assessments found for given upn: " + upn);
        }
    }

    public Assessment getAssessmentByIdForOwner(String id, String owner) throws Exception {
        Iterable<Assessment> iterAssessments = assessmentsRepository.getByIdForOwner(id, owner);
        List<Assessment> assessmentsList = sendUtilities.iterableAssessmentsToList(iterAssessments);

        if ((assessmentsList != null) && (assessmentsList.size() == 1)) {
            return assessmentsList.get(0);
        } else {
            throw new Exception("No single assessment exists for given id: " + id + " and owner: " + owner);
        }
    }

    public List<Assessment> getAssessmentsByOwner(String owner) {
        Iterable<Assessment> iterAssessments = assessmentsRepository.findByOwnerOrdered(owner);
        List<Assessment> assessmentsList = sendUtilities.iterableAssessmentsToList(iterAssessments);

        if ((assessmentsList != null) && (assessmentsList.size() > 0)) {
            return assessmentsList;
        } else {
            return Collections.emptyList();
        }
    }

    public boolean isUpnAvailable(String upn){        
        Iterable<Assessment> iterAssessments = assessmentsRepository.findByUpn(upn);
        
        if(!iterAssessments.iterator().hasNext()){
            return true;           
        }else{
            return false;
        }
    }

    public Assessment createNewAssessment(Assessment assessment) throws Exception {
        final Assessment newAssessment = assessmentsRepository.save(assessment);
        
        if (newAssessment != null) {
            return newAssessment;
        } else {
            throw new Exception("Could not create new assessment");
        }
    }

    public List<NeedStatement> getSelectedNeedStatementsForSubArea(String assessmentId, String subAreaId){
        List<NeedStatement> selectedNeedStatements = getSelectedNeedStatements(assessmentId);
       
        //Filter down to subarea
        List<NeedStatement> selectedNeedStatementsForSubAreaId = selectedNeedStatements
         .stream()
         .filter(s -> s.getSubAreaId().startsWith(subAreaId))
         .collect(Collectors.toList());  
        
        if ((selectedNeedStatementsForSubAreaId != null) && (selectedNeedStatementsForSubAreaId.size() > 0)) {
            return selectedNeedStatementsForSubAreaId;
        }else{
            return Collections.emptyList();
        }
    }

    public List<String> getSelectedNeedStatementIdsForSubArea(String assessmentId, String subAreaId){
        List<NeedStatement> selectedNeedStatements = getSelectedNeedStatementsForSubArea(assessmentId, subAreaId);
        ArrayList<String> selectedIds = new ArrayList<String>();
       
        for(NeedStatement needStatement : selectedNeedStatements) {
            selectedIds.add(needStatement.getStatementNumber());
        } 
        return selectedIds;
    }

    public List<String> getSelectedGroupIdsForSubArea(String assessmentId, String subAreaId){        
        List<NeedStatement> selectedNeedStatements = getSelectedNeedStatementsForSubArea(assessmentId, subAreaId);
        ArrayList<String> selectedGroupIds = new ArrayList<String>();        
               
        for(NeedStatement needStatement : selectedNeedStatements) {
             selectedGroupIds.add(needStatement.getStatementGroupNumber());
        }

        return selectedGroupIds;
    }    

    public List<NeedStatement> getSelectedNeedStatements(String assessmentId){
        Iterable<NeedStatement> iterNeedStatements = assessmentsRepository.getSelectedNeedStatements(assessmentId); 
        List<NeedStatement> needStatementsList = sendUtilities.iterableStatementsToList(iterNeedStatements);

        if ((needStatementsList != null) && (needStatementsList.size() > 0)) {
            return needStatementsList;
        }else{
            return Collections.emptyList();
        }
    }

    public Assessment updateAssessmentWithSelectedNeedStatements(Assessment assessment, String selectedStatementIds, String subAreaId, Boolean deleteProvision) throws Exception{
        String[] selected = selectedStatementIds.split(",");

        List<NeedStatement> selectedNeedStatements = assessment.getSelectedNeedStatements();

        if(selectedNeedStatements == null){
            selectedNeedStatements = new ArrayList<NeedStatement>();        
        }else{
            // get the selected statements except those for the current subArea, as users may have deselected an option from current group
            selectedNeedStatements = assessment.getSelectedNeedStatements()
            .stream()
            .filter(s -> !s.getSubAreaId().startsWith(subAreaId))
            .collect(Collectors.toList());             
        }

        // add selected ids for the subarea to list of existing selected statements, providing the subarea has a value to add
        if(!selectedStatementIds.equals("-1")){
            for(int i=0; i<selected.length; i++){
                List<NeedStatement> ns = needService.getNeedStatementById(selected[i]);
                if(ns.size() == 1){
                    selectedNeedStatements.add(ns.get(0));
                }else{
                    throw new Exception("Need Statement with id: " + selected[i] + " either does not exist or has more than one entry in the database");
                }
            }
        }    
        
        // remove duplicates 
        List<NeedStatement> needStatementsFiltered = selectedNeedStatements.stream().distinct().collect(Collectors.toList());
        assessment.setSelectedNeedStatements(needStatementsFiltered);
        
        // Delete the selected provision from the assignment as the associated need is lowered 
        if(deleteProvision) {
	        List<SelectedProvision> selectedProvisions = assessment.getSelectedProvisions()
	            .stream()
	            .filter(s -> !s.getSubAreaId().startsWith(subAreaId))
	            .collect(Collectors.toList());             	        
	        assessment.setSelectedProvisions(selectedProvisions);
        }

        final Assessment newAssessment = assessmentsRepository.save(assessment);
        if (newAssessment != null) {
            return newAssessment;
        } else {
            throw new Exception("Could not update assessment");
        }
    }


    public Assessment updateAssessmentWithSelectedProvisionStatements(Assessment assessment, List<Provision> allProvisions, String selectedProvisionIds, String subAreaId, String needLevel) throws Exception{
        List<SelectedProvision> selectedProvisions = assessment.getSelectedProvisions();

        
        if(selectedProvisions == null){
            selectedProvisions = new ArrayList<SelectedProvision>();        
        }else{
            // get the selected statements except those for the current subArea, as users may have deselected an option from current group
            selectedProvisions = assessment.getSelectedProvisions()
            .stream()
            .filter(s -> !s.getSubAreaId().startsWith(subAreaId))
            .collect(Collectors.toList());             
        }
       
        //try around this?
        List<String> provisionList = Arrays.asList(selectedProvisionIds.split(",")); 

        for(Provision provision: allProvisions){
            if(provisionList.contains(provision.getProvisionStatementId())){
                selectedProvisions.add(new SelectedProvision(subAreaId, needLevel, provision.getProvisionGroup(), provision.getProvisionStatementId(), provision.getSpecificProvision(), provision.getProvisionTypeLabel()));                
            }
        }

        assessment.setSelectedProvisions(selectedProvisions);       

        final Assessment newAssessment = assessmentsRepository.save(assessment);
        if (newAssessment != null) {
            return newAssessment;
        } else {
            throw new Exception("Could not update assessment with selected provisions");
        }
    }
    
    public Assessment updateAssessmentWithRemovedProvisions(Assessment assessment, String subAreaId, String provisionStatementId) throws Exception{    	        
	    // get the selected provisions except the matching subArea and statement, to update the assessment.	
	    List<SelectedProvision> selectedProvisions = assessment.getSelectedProvisions()
    	            .stream()
    	            .filter(s -> !(s.getSubAreaId().startsWith(subAreaId) && s.getProvisionStatementId().startsWith(provisionStatementId)) )	            
    	            .collect(Collectors.toList());  
    	
        assessment.setSelectedProvisions(selectedProvisions);       

        final Assessment newAssessment = assessmentsRepository.save(assessment);
        
        if (newAssessment != null) {
            return newAssessment;
        } else {
            throw new Exception("Could not update assessment with removed provisions");
        }
    }


    public String[] getSelectedProvisionIdsForSubArea(String subAreaId, Assessment assessment){
        String[] selectedProvisionIds;
        List<SelectedProvision> selectedProvisions;

        // filter the selected provisons for this subArea
        selectedProvisions = assessment.getSelectedProvisions()
        .stream()
        .filter(s -> s.getSubAreaId().startsWith(subAreaId))
        .collect(Collectors.toList());  

        // Create a string array list of at least one blank element. 
        // Without this the provisions.ftlh freemarker code would be much more complex.
        if(selectedProvisions == null || selectedProvisions.isEmpty()){                                         
            selectedProvisions = List.of(new SelectedProvision("", "", "", "", "", ""));
            selectedProvisionIds = new String[]{""};              
        }else{
            selectedProvisionIds = new String[selectedProvisions.size()];
            for(int i=0; i<selectedProvisions.size(); i++){
                selectedProvisionIds[i]  = selectedProvisions.get(i).getProvisionStatementId();
            }
        }
        return selectedProvisionIds;
    }


    public List<SelectedProvision> getSelectedProvisionsForSubArea(String subAreaId, Assessment assessment){
        List<SelectedProvision> selectedProvisions;

        // filter the selected provisons for this subArea
        selectedProvisions = assessment.getSelectedProvisions()
        .stream()
        .filter(s -> s.getSubAreaId().startsWith(subAreaId))
        .collect(Collectors.toList());  
       
        if(selectedProvisions == null || selectedProvisions.isEmpty()){                                         
            selectedProvisions = List.of(new SelectedProvision("", "", "", "", "", ""));
        }
            
        return selectedProvisions;
    }
    
    public boolean areProvisionsSelectedForSubArea(String subAreaId, Assessment assessment){
    	
    	List<SelectedProvision> selectedProvisions = assessment.getSelectedProvisions()
    	        .stream()
    	        .filter(s -> s.getSubAreaId().startsWith(subAreaId))
    	        .collect(Collectors.toList()); 
    		 	
    	return (!selectedProvisions.isEmpty() ) ? true : false;
    	
    }


    public Boolean checkUpnPattern(String upn) throws Exception {
        System.out.println("Upn is: " + upn);
        if (upn != null) {
            Pattern pattern = Pattern.compile("^([A-Z]{1})([0-9]{12})([A-Z]?)$");
            Matcher matcher = pattern.matcher(upn);
            return matcher.matches();
        } else {
            return false;
        }
    }
    
    public void deleteAssessment(Assessment assessment){
    	assessmentsRepository.delete(assessment);
    }

}
