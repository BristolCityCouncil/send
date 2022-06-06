package uk.gov.bristol.send;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;

import uk.gov.bristol.send.exceptions.SendAuthServiceException;
import uk.gov.bristol.send.exceptions.SendException;
import uk.gov.bristol.send.exceptions.SendPDFDownloadException;
import uk.gov.bristol.send.model.Assessment;
import uk.gov.bristol.send.model.Need;
import uk.gov.bristol.send.model.NeedStatement;
import uk.gov.bristol.send.model.Owner;
import uk.gov.bristol.send.model.Provision;
import uk.gov.bristol.send.model.SelectedProvision;
import uk.gov.bristol.send.service.AssessmentService;
import uk.gov.bristol.send.service.AuthenticationService;
import uk.gov.bristol.send.service.NeedService;
import uk.gov.bristol.send.service.PDFService;
import uk.gov.bristol.send.service.ProvisionService;

@Controller
@PropertySource("classpath:messages.properties")
public class SendController {

    private Logger LOGGER = LoggerFactory.getLogger(SendController.class);

    @Autowired
    AssessmentService assessmentService;

    @Autowired
    AuthenticationService authenticationService;

    @Autowired
    NeedService needService;

    @Autowired
    ProvisionService provisionService;

    @Autowired
    PDFService pdfService;

    @Value("${yourAssessments.assessments.new.upn.upnInUse}")
    private String UPN_IN_USE;

    private static final String pathPrefix = "/top-up-assessment";


    public SendController(AuthenticationService authenticationService, AssessmentService assessmentService, NeedService needService, ProvisionService provisionService, PDFService pdfService){
        super();
        this.authenticationService = authenticationService;
        this.assessmentService = assessmentService;
        this.needService = needService;   
        this.provisionService = provisionService; 
        this.pdfService = pdfService;        
    }

    @RequestMapping(value = { "/" }, method = RequestMethod.GET)
    public String goHome(HttpServletRequest request, Model model) {  
        return "redirect:" + pathPrefix;
    }    

    @RequestMapping(value = { pathPrefix }, method = RequestMethod.GET)
    public String yourAssessments(HttpServletRequest request, Model model) {     
    	Owner owner = getLoggedInUser(request);
        try {        	
            
            List<Assessment> yourAssessments = assessmentService.getAssessmentsByOwner(owner.getOwnerEmail());
            model.addAttribute("yourAssessments", yourAssessments);
            model.addAttribute("currentUser", owner);
            model.addAttribute("upnError", "");
            model.addAttribute("pathPrefix", pathPrefix);
        } catch (Exception e) {           
            throw new SendException("Unable to get assessments from database" + e.getMessage() );           
        }
        return "home";
    }


    @RequestMapping(value = { pathPrefix }, method = RequestMethod.POST)
	public String newAssessment(HttpServletRequest request, Model model, String upn, String schoolName) {
		Owner owner = getLoggedInUser(request);
		String returnView = "home";
		try {
			if (!assessmentService.isUpnAvailable(upn)) {
				model.addAttribute("upnError", UPN_IN_USE);
				List<Assessment> yourAssessments = assessmentService.getAssessmentsByOwner(owner.getOwnerEmail());
				model.addAttribute("yourAssessments", yourAssessments);
				model.addAttribute("currentUser", owner);
				model.addAttribute("upn", upn);
                model.addAttribute("pathPrefix", pathPrefix);
			} else if ((assessmentService.checkUpnPattern(upn)) && (schoolName.trim().length() > 2)) {
				List<NeedStatement> selectNeedStatements = List.of();
				List<SelectedProvision> selectedProvisions = List.of();
				Assessment assessment = new Assessment(1, upn, owner.getOwnerEmail(), "new", schoolName,
						selectNeedStatements, selectedProvisions);
				assessmentService.createNewAssessment(assessment);
				returnView = singleAssessment(request, model, assessment.getId());
			}

		} catch (Exception e) {
			throw new SendException("Unable to write new assessment to database: " + e.getMessage());
		}

		return returnView;
	}
    
    @RequestMapping(value = { pathPrefix + "/deleteAssessment" }, params = { "assessmentId"}, method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK) 
    public String deleteAssessment(HttpServletRequest request, Model model, @RequestParam("assessmentId") String assessmentId) {           
    	Owner owner = getLoggedInUser(request);
        try{           
        	Assessment assessment = assessmentService.getAssessmentByIdForOwner(assessmentId, owner.getOwnerEmail());
        	assessmentService.deleteAssessment(assessment);
           
        }catch(Exception e){
            throw new SendException("Unable to delete Assessment from database: " + e.getMessage() );
        }
        
        return yourAssessments(request, model);
    }


    @RequestMapping(value = { pathPrefix + "/summary" }, method = RequestMethod.GET)
    public String singleAssessment(HttpServletRequest request, Model model, @RequestParam String assessmentId) {
    	Owner owner = getLoggedInUser(request);
        try {
            Assessment assessment = assessmentService.getAssessmentByIdForOwner(assessmentId, owner.getOwnerEmail());
            // Redirect user if they're not the assessment owner            
            if (!new String(assessment.getOwner()).equals(owner.getOwnerEmail())) {
                return "redirect:/";
            }
            List<Need> needAreas = needService.getAllNeedAreas();
            List<Need> needSubAreas = needService.getAllNeedSubAreas();
            List<SelectedProvision> selectedProvisions = assessment.getSelectedProvisions();

            Map<String, String> needsMap = new HashMap<>();
            
            // work out which sub areas have selected needs and the need level            
            for(int i=0; i<needSubAreas.size(); i++){
                String subAreaId = needSubAreas.get(i).getSubAreaId();
                List<NeedStatement> needStatements = assessmentService.getSelectedNeedStatementsForSubArea(assessmentId, subAreaId);
                String needLevel = needService.findCurrentNeedLevel(needStatements);
                if(needLevel.length() > 0){
                    needsMap.put(subAreaId, needLevel);
                }    
            }    
            model.addAttribute("needsMap", needsMap);
            model.addAttribute("needAreas", needAreas);
            model.addAttribute("needSubAreas", needSubAreas);
            model.addAttribute("assessment", assessment);
            model.addAttribute("currentUser", owner);
            model.addAttribute("selectedProvisions", selectedProvisions);
            model.addAttribute("pathPrefix", pathPrefix);
        } catch (Exception e) {
            throw new SendException("Unable to get assessments from database: " + e.getMessage() );  
        }
        return "summary";
    }


    @RequestMapping(value = pathPrefix + "/needs", params = { "assessmentId", "subAreaId" }, method = RequestMethod.GET)
    public String doNeeds(HttpServletRequest request, Model model, @RequestParam("assessmentId") String assessmentId, @RequestParam("subAreaId") String subAreaId) {
    	Owner owner = getLoggedInUser(request);
        try {
            Assessment assessment = assessmentService.getAssessmentByIdForOwner(assessmentId, owner.getOwnerEmail());    
            boolean simulateDbFailure =  StringUtils.isNotEmpty(request.getParameter("simulateDbFailure")) && request.getParameter("simulateDbFailure").equals("true") ? Boolean.TRUE: Boolean.FALSE;
            List<NeedStatement> needStatements = needService.getNeedStatementsForSubArea(subAreaId, simulateDbFailure); 
            List<NeedStatement> selectedStatements = assessmentService.getSelectedNeedStatementsForSubArea(assessmentId, subAreaId);             
            List<String> selectedStatementNumbers = assessmentService.getSelectedNeedStatementIdsForSubArea(assessmentId, subAreaId);
            List<String> selectedGroupIds = assessmentService.getSelectedGroupIdsForSubArea(assessmentId, subAreaId);
            String maximumNeedLevel = needService.findCurrentNeedLevel(selectedStatements);
            String subAreaLabel = needStatements.get(0).getSubAreaLabel(); 
            
            model.addAttribute("assessment", assessment);
            model.addAttribute("needStatements", needStatements);
            model.addAttribute("currentUser", owner);
            model.addAttribute("subAreaLabel", subAreaLabel);
            model.addAttribute("subAreaId", subAreaId);
            model.addAttribute("selectedStatementIds", selectedStatementNumbers);
            model.addAttribute("selectedGroupIds", selectedGroupIds);    
            model.addAttribute("maximumNeedLevel", maximumNeedLevel);           
            model.addAttribute("hasSelectedProvisions", assessmentService.areProvisionsSelectedForSubArea(subAreaId, assessment));            
            model.addAttribute("pathPrefix", pathPrefix);
          
        } catch (Exception e) {
            throw new SendException("Unable to get needs from database: " + e.getMessage() );
        }        
        return "needs";
    }


    @RequestMapping(value = { pathPrefix + "/updateNeeds" }, params = { "assessmentId", "subAreaId", "selectedStatementIds", "deleteProvision"}, method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK) 
    public void updateNeeds(HttpServletRequest request, Model model, @RequestParam("assessmentId") String assessmentId, @RequestParam("subAreaId") String subAreaId,  @RequestParam("selectedStatementIds") String selectedStatementIds, Boolean deleteProvision) {           
    	Owner owner = getLoggedInUser(request);
        try{            
            Assessment assessment = assessmentService.getAssessmentByIdForOwner(assessmentId, owner.getOwnerEmail());
            assessment = assessmentService.updateAssessmentWithSelectedNeedStatements(assessment, selectedStatementIds, subAreaId, deleteProvision);            
        }catch(Exception e){
            throw new SendException("Unable to update needs in database: " + e.getMessage() );
        }
           
    }  


    @RequestMapping(value = pathPrefix + "/provisions", params = { "assessmentId", "areaId", "subAreaId", "needLevel"}, method = RequestMethod.GET)
    public String doProvisions(HttpServletRequest request, Model model, @RequestParam("assessmentId") String assessmentId, @RequestParam("areaId") String areaId, @RequestParam("subAreaId") String subAreaId, @RequestParam("needLevel") String needLevel) {
    	Owner owner = getLoggedInUser(request);            
        try {            
            Assessment assessment = assessmentService.getAssessmentByIdForOwner(assessmentId, owner.getOwnerEmail());
            List<Need> needs = needService.getNeedsBySubAreaId(subAreaId);           
            String areaLabel = needs.get(0).getAreaLabel();           
            String subAreaLabel = needs.get(0).getSubAreaLabel(); 
            List<Provision> provisions = provisionService.getProvisionsForSubArea(subAreaId, needLevel.toUpperCase());            
            List<SelectedProvision> selectedProvisions = assessmentService.getSelectedProvisionsForSubArea(subAreaId, assessment);  
            String[] selectedProvisionIds = assessmentService.getSelectedProvisionIdsForSubArea(subAreaId, assessment);            
            model.addAttribute("assessment", assessment);
            model.addAttribute("currentUser", owner);
            model.addAttribute("areaLabel", areaLabel);              
            model.addAttribute("subAreaLabel", subAreaLabel);
            model.addAttribute("subAreaId", subAreaId);
            model.addAttribute("needLevel", needLevel);
            model.addAttribute("provisions", provisions);
            model.addAttribute("selectedProvisions", selectedProvisions);
            model.addAttribute("selectedProvisionIds", selectedProvisionIds);
            model.addAttribute("pathPrefix", pathPrefix);
        } catch (Exception e) {           
            throw new SendException("Unable to get provisions from database: " + e.getMessage() );
        }
        
        return "provisions";
    }


    @RequestMapping(value = { pathPrefix + "/updateProvisions" }, params = { "assessmentId", "subAreaId", "selectedProvisionIds", "needLevel"}, method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK) 
    public void updateProvisions(HttpServletRequest request, Model model, @RequestParam("assessmentId") String assessmentId, @RequestParam("subAreaId") String subAreaId,  @RequestParam("selectedProvisionIds") String selectedProvisionIds, @RequestParam("needLevel") String needLevel) {           
    	Owner owner = getLoggedInUser(request);
    	
        try{
            Assessment assessment = assessmentService.getAssessmentByIdForOwner(assessmentId, owner.getOwnerEmail());
            List<Provision> allProvisions = provisionService.getAllProvisions();
            assessment = assessmentService.updateAssessmentWithSelectedProvisionStatements(assessment, allProvisions, selectedProvisionIds, subAreaId, needLevel);            
        }catch(Exception e){
            throw new SendException("Unable to update provisions in database: " + e.getMessage() );
        }
           
    }
    
    
    @RequestMapping(value = { pathPrefix + "/deleteProvision" }, params = { "assessmentId", "subAreaId", "provisionStatementId"}, method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK) 
    public String deleteProvision(HttpServletRequest request, Model model, @RequestParam("assessmentId") String assessmentId, @RequestParam("subAreaId") String subAreaId,  @RequestParam("provisionStatementId") String provisionStatementId) {           
    	Owner owner = getLoggedInUser(request);
        try{
            Assessment assessment = assessmentService.getAssessmentByIdForOwner(assessmentId, owner.getOwnerEmail());
            assessment = assessmentService.updateAssessmentWithRemovedProvisions( assessment,  subAreaId,  provisionStatementId); 
            singleAssessment(request, model, assessmentId);
        }catch(Exception e){
            throw new SendException("Unable to update provisions in database: " + e.getMessage() );
        }
        
        return "summary";           
    }


    @RequestMapping(value = { pathPrefix + "/downloadPDF" }, params = {"assessmentId"}, method = RequestMethod.POST)
    @ResponseStatus(value = HttpStatus.OK)
    public void downloadPDF(HttpServletRequest request, HttpServletResponse response, @RequestParam("assessmentId") String assessmentId){
        //Do an owner check before preceding
    	Owner owner = getLoggedInUser(request);
        try{
           
            Assessment assessment = assessmentService.getAssessmentByIdForOwner(assessmentId, owner.getOwnerEmail());
            List<NeedStatement> selectedNeedStatements = needService.getSelectedNeedStatementsGroupByAreaId(assessment.getSelectedNeedStatements());            
            List<SelectedProvision> selectedProvisions = assessment.getSelectedProvisions();
            Map<String, String> needLevels = needService.findNeedLevelForSubAreas(selectedNeedStatements);            

            Map<String, Object> pdfModel = new HashMap<>();        
            pdfModel.put("ownerName", owner.getOwnerName());
            pdfModel.put("needStatements", selectedNeedStatements);
            pdfModel.put("yourAssessment", assessment);
            pdfModel.put("provisions", selectedProvisions);
            pdfModel.put("needLevels", needLevels);          
            String outputFileName = assessment.getUpn();
                         
            pdfService.savePdfWithFooter(outputFileName, pdfModel, response, request.getParameter("simulateDownloadPDFFailure"));           

        }catch(Exception e){                       
            throw new SendPDFDownloadException("Unable to create pdf: " + e.getMessage() );
        }            
      
    }
    
    @RequestMapping(value = { pathPrefix + "/overview" }, method = RequestMethod.GET)
    public String overview(HttpServletRequest request, Model model, @RequestParam String assessmentId, @RequestParam String devPhase ) {
    	Owner owner = getLoggedInUser(request);
        try {
            Assessment assessment = assessmentService.getAssessmentByIdForOwner(assessmentId, owner.getOwnerEmail());
            // Redirect user if they're not the assessment owner            
            if (!new String(assessment.getOwner()).equals(owner.getOwnerEmail())) {
                return "redirect:" + pathPrefix;
            }
            // Return to error page if the page is NOT assessed with the correct devPhase Parameter 
            if(StringUtils.isEmpty(devPhase) || !devPhase.equalsIgnoreCase("two")) {
            	return "error";
            }
            
            model.addAttribute("assessment", assessment);
            model.addAttribute("pathPrefix", pathPrefix);
        } catch (Exception e) {
            throw new SendException("Unable to get assessments from database: " + e.getMessage() );  
        }
        return "overview";
    }

    
	private Owner getLoggedInUser(HttpServletRequest request) {
		Owner owner;
		try {
			  owner = authenticationService.getLoggedInUser(request);
		} catch (Exception e) {		  
		    throw new SendAuthServiceException("Could not Authenticate User: " + e.getMessage() );
		}
		return owner;
	}
    
    @ExceptionHandler(SendAuthServiceException.class)
    public String handleAuthenticationServiceException(Model model){        
    	model.addAttribute("pathPrefix", pathPrefix);
        return "authenticationservicefailure";
    }
    
    @ExceptionHandler(SendException.class)
    public String handleGenericException(Model model){
    	model.addAttribute("pathPrefix", pathPrefix);
        return "sendfailure";
    }
    
    @ExceptionHandler(SendPDFDownloadException.class)
    public String handlePDFDownloadException(Model model){
    	model.addAttribute("pathPrefix", pathPrefix);
        return "downloadpdffailure";
    }    
  
    protected void setLogger(Logger logger){
        LOGGER = logger;
    }

}
