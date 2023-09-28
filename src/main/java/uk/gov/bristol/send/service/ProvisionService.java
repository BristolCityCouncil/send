package uk.gov.bristol.send.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uk.gov.bristol.send.SendUtilities;
import uk.gov.bristol.send.model.Provision;
import uk.gov.bristol.send.model.ProvisionCodesLookUp;
import uk.gov.bristol.send.model.ProvisionLookUp;
import uk.gov.bristol.send.repo.ProvisionCodesLookUpRepository;
import uk.gov.bristol.send.repo.ProvisionsLookUpRepository;
import uk.gov.bristol.send.repo.ProvisionsRepository;


@Service
public class ProvisionService {

    @Autowired
    ProvisionsRepository provisionsRepository;

    @Autowired
    ProvisionsLookUpRepository provisionsLookUpRepository;

    @Autowired
    SendUtilities sendUtilities;
    
    @Autowired
    ProvisionCodesLookUpRepository provisionCodesLookUpRepository;

    public ProvisionService(ProvisionsRepository provisionsRepository, ProvisionsLookUpRepository provisionsLookUpRepository, SendUtilities sendUtilities, ProvisionCodesLookUpRepository provisionCodesLookUpRepository) {
        this.provisionsRepository = provisionsRepository;
        this.provisionsLookUpRepository = provisionsLookUpRepository;
        this.sendUtilities = sendUtilities;
        this.provisionCodesLookUpRepository = provisionCodesLookUpRepository;
    }


    public List<Provision> getAllProvisions() throws Exception {
        List<Provision> provisionList = provisionsRepository.findAll();

        if ((provisionList != null) && (provisionList.size() > 0)) {
        	provisionList.sort(Comparator.comparing(a -> Integer.valueOf(a.getId())));
            return provisionList;
        } else {
            throw new Exception("Database error: No provisions found");
        }
    }


    public ArrayList<Provision> getProvisionsForSubArea(String subAreaId, String level) throws Exception {
        List<ProvisionLookUp> provisionsLookUpList = provisionsLookUpRepository.findBySubAreaId(subAreaId);
        List<Provision> allProvisionList = provisionsRepository.findAll();
    
        String provisionGroupIdString = provisionsLookUpList.get(0).getProvisionGroups();
        String[] provisionGroupIds =  provisionGroupIdString.split(",");

        ArrayList<Provision> provisionList = new ArrayList<Provision>();
       
        final String regex;
        
        if(level.equals("A")){
            regex = "^A$";
        }else if(level.equals("B")){
            regex = "^A$|^B$";
        }else if(level.equals("C")){
            regex = "^A$|^B$|^C$";
        }else{
            throw new Exception("Unable to fetch provisions. The need level: " + level + " is not valid");
        }    

        try{
            for(String provisionId : provisionGroupIds){    
               List<Provision> provisionsInGroup = allProvisionList.stream()
                .filter(p -> p.getProvisionTypeId().equals(provisionId) && p.getLevel().matches(regex))
                .collect(Collectors.toList());                   
                   
                if(provisionsInGroup.size() > 0){
                    provisionList.addAll(provisionsInGroup);                
                }    
            }    
        }catch(NullPointerException npe){
            throw new NullPointerException("Unable to fetch provisions for sub area id: " + subAreaId + ". This string of group ids may be corrupted: " + provisionGroupIdString);
        }

        
        if (provisionList.size() > 0) {
            return provisionList;
        } else {
            throw new Exception("No provisions found for given sub area id: " + subAreaId);
        }

    }   
    
    public Provision getProvisionForStatement(String provisionStatementId) throws Exception {
    	List<Provision> matchedProvisionList = provisionsRepository.findProvisionsByStatementId(provisionStatementId);
    	
    	if (!matchedProvisionList.isEmpty()) {
            return matchedProvisionList.get(0);
        } else {
        	throw new Exception("No provisions found for given StatementId: " + provisionStatementId);
        }
    	    	
    }
    
    public ProvisionCodesLookUp getProvisionCodesLookUp(String type) throws Exception {
    	List<ProvisionCodesLookUp> matchedProvisionCodesLookUpList = provisionCodesLookUpRepository.findProvisionCodesLookUpByType(type);
    	
    	if (!matchedProvisionCodesLookUpList.isEmpty()) {
            return matchedProvisionCodesLookUpList.get(0);
        } else {
        	throw new Exception("No provisions Codes LookUp found for given type: " + type);
        }
    	
    }
 
}