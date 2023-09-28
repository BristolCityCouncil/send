package uk.gov.bristol.send.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.gov.bristol.send.SendUtilities;
import uk.gov.bristol.send.fileupload.bean.FileUpload;
import uk.gov.bristol.send.fileupload.bean.FileUploadResponse;
import uk.gov.bristol.send.fileupload.model.UploadedFileInfo;
import uk.gov.bristol.send.model.Assessment;
import uk.gov.bristol.send.model.NeedStatement;
import uk.gov.bristol.send.model.Provision;
import uk.gov.bristol.send.model.ProvisionCodesLookUp;
import uk.gov.bristol.send.model.SelectedProvision;
import uk.gov.bristol.send.repo.AssessmentsRepository;

@Service
public class AssessmentService {

    @Autowired
    AssessmentsRepository assessmentsRepository;

    @Autowired 
    NeedService needService;

    @Autowired
    SendUtilities sendUtilities;
    
    @Autowired
    ProvisionService provisionService;

    private static final Double DEFAULT_WEEKLY_CAP_FOR_DIFFERENT_TYPES = 32.00;
    private static final Double EMPTY_HOURS_PER_WEEK = 0.00;  
    private static final Double TOTAL_WEEKS_ANNUM = 45.5;

    public AssessmentService(AssessmentsRepository assessmentsRepository, SendUtilities sendUtilities, NeedService needService, ProvisionService provisionService) {
        this.assessmentsRepository = assessmentsRepository;
        this.sendUtilities = sendUtilities;
        this.needService = needService;
        this.provisionService = provisionService;
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
    
    public Assessment updateAssessmentWithRequestingFundingProvisions(Assessment assessment, List<SelectedProvision> selectedProvisions, String subAreaId, String provisionStatementId) throws Exception{    	        
	    // get the selected provisions matching subArea and statement, to update the assessment.	
	   	    
	    for(SelectedProvision selectedProvision : selectedProvisions) {
	    	if(selectedProvision.getSubAreaId().startsWith(subAreaId) && selectedProvision.getProvisionStatementId().startsWith(provisionStatementId)) {
	    		selectedProvision.setRequestingFunding(true);
	    	} 
        }   
	    assessment.setSelectedProvisions(selectedProvisions);

        final Assessment newAssessment = assessmentsRepository.save(assessment);
        
        if (newAssessment != null) {
            return newAssessment;
        } else {
            throw new Exception("Could not update assessment with requesting Funding provisions");
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
    
    public List<SelectedProvision> getSelectedProvisionsGroupByProvisionStatementId(List<SelectedProvision> selectedProvisions ) {	   
        selectedProvisions.sort(Comparator.comparing(a -> String.valueOf(a.getProvisionStatementId())));   
	   
	    return selectedProvisions;	   
    }
    
    public List<SelectedProvision> getSelectedProvisionsRequestingForFunding(List<SelectedProvision> selectedProvisions ) {	       	
        selectedProvisions = selectedProvisions
	            .stream()
	            .filter(s -> s.isRequestingFunding() )	            
	            .collect(Collectors.toList()); 
	   
	    return selectedProvisions;	   
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
    
    public void saveAssessment(Assessment assessment){
    	assessmentsRepository.save(assessment);
    }
    
    public void calculateTotalProvisionsAnnualCost(Assessment assessment, List<SelectedProvision> selectedProvisions)
			throws Exception {		
		final DecimalFormat df = new DecimalFormat("0.00");
		Double totalCostPerWeek = 0.00;
		Double totalHoursPerWeek = 0.00; 
		Double totalAnnualCost = 0.00; 
		Double weeklyCapInHoursForDifferentTypes = 0.00;
		
		for(SelectedProvision selectedProvision : selectedProvisions) {
			Provision provision = provisionService.getProvisionForStatement(selectedProvision.getProvisionStatementId());
			ProvisionCodesLookUp provisionCodesLookUp = provisionService.getProvisionCodesLookUp(provision.getCode());
			weeklyCapInHoursForDifferentTypes = provisionCodesLookUp.getWeeklyCapInHoursForDifferentTypes() != null ? provisionCodesLookUp.getWeeklyCapInHoursForDifferentTypes() : DEFAULT_WEEKLY_CAP_FOR_DIFFERENT_TYPES;
			
			Double  hoursPerWeek = StringUtils.isNotEmpty(provision.getHoursPerWeek()) && NumberUtils.isParsable(provision.getHoursPerWeek())? Double.parseDouble(provision.getHoursPerWeek()) : EMPTY_HOURS_PER_WEEK;
			totalHoursPerWeek += hoursPerWeek; 
			
			Double costPerHr = provisionCodesLookUp.getCostPerHour();
			Double costPerWeek = hoursPerWeek * costPerHr;
			totalCostPerWeek += costPerWeek;
		}           
		
		//Calculation is performed only if weekly CAPS are not exceeded 
		if(totalHoursPerWeek > weeklyCapInHoursForDifferentTypes) {
			assessment.setTotalHourlyCapExceeded(true);
			assessment.setTotalAnnualCost(null);
		} else {          
			assessment.setTotalHourlyCapExceeded(false);
		    totalAnnualCost = totalCostPerWeek * TOTAL_WEEKS_ANNUM;
		    assessment.setTotalAnnualCost(df.format(totalAnnualCost));
		}
	}
    
    public void calculateTotalWeeklyHourPerCodeForSelectedProvisions(Assessment assessment, List<SelectedProvision> selectedProvisions)
			throws Exception {		
		assessment.setTypeHourlyCapExceeded(false);
		
		for(int i=0; i< selectedProvisions.size();i++) {
			Provision provision = provisionService.getProvisionForStatement(selectedProvisions.get(i).getProvisionStatementId());
			ProvisionCodesLookUp provisionCodesLookUp = provisionService.getProvisionCodesLookUp(provision.getCode());
			Double  hoursPerWeek = StringUtils.isNotEmpty(provision.getHoursPerWeek()) && NumberUtils.isParsable(provision.getHoursPerWeek()) ? Double.parseDouble(provision.getHoursPerWeek()) : EMPTY_HOURS_PER_WEEK;
			String code = provision.getCode();
			
			for(int j= 0; j< selectedProvisions.size();j++) {
				provision = provisionService.getProvisionForStatement(selectedProvisions.get(j).getProvisionStatementId());
				if(code.equals(provision.getCode()) && j!= i) {				
					hoursPerWeek  = hoursPerWeek + (StringUtils.isNotEmpty(provision.getHoursPerWeek()) &&  NumberUtils.isParsable(provision.getHoursPerWeek())? Double.parseDouble(provision.getHoursPerWeek()) : EMPTY_HOURS_PER_WEEK);
				}				
			}
			
			if(provisionCodesLookUp.getWeeklyCapInHoursForThisType() !=null && hoursPerWeek > provisionCodesLookUp.getWeeklyCapInHoursForThisType()) {
				assessment.setTypeHourlyCapExceeded(true);
				return;
			}
			
		}    
		
	}
	
	public List<UploadedFileInfo> deleteUploadedFileInfo(Assessment assessment, List<UploadedFileInfo> uploadedFiles, String id) throws Exception {
		// get the Uploaded FileInfo except the matching id and update the assessment with the updated List	
	    List<UploadedFileInfo> updatedUploadedFiles = uploadedFiles
    	            .stream()
    	            .filter(s -> !(s.getId().equals(id)) )	            
    	            .collect(Collectors.toList());  
	    
	    assessment.setUploadedFilesInfo(updatedUploadedFiles);
		saveAssessment(assessment);
	    
	    return updatedUploadedFiles;
	    		   
    }

    public String getUploadFolderIdForUPN(Assessment assessment){        
        return assessment.getUploadFolderId();
    }
	
	public void saveUploadedFileInfo(Assessment assessment, FileUploadResponse fileUploadReresponse, FileUpload fileUpload) throws Exception{
		List<UploadedFileInfo> uploadedFiles = assessment.getUploadedFilesInfo();
		if(uploadedFiles == null) {
			uploadedFiles = new ArrayList<UploadedFileInfo>();
		}
		
		UploadedFileInfo uploadedFileInfo = new UploadedFileInfo();
		
		uploadedFileInfo.setId(fileUploadReresponse.getId());
		uploadedFileInfo.setDocumentTypeName(fileUpload.getUploadDocumentType());
		uploadedFileInfo.setFileSize(fileUploadReresponse.getFileSize());
		uploadedFileInfo.setFileName(fileUploadReresponse.getFileName());		
		uploadedFileInfo.setFileContentType(StringUtils.substringAfterLast(fileUploadReresponse.getFileName(), "."));		
						
		uploadedFiles.add(uploadedFileInfo);
					
		assessment.setUploadedFilesInfo(uploadedFiles);
		saveAssessment(assessment);
	}

}
