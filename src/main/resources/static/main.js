var selectedStatements = "";

// Validate upn format
function validateUpn() {
  document.getElementById("submit").disabled = true;
  let upn = document.getElementById("upn").value.trim();
  let inputError = false;

  // Check school name first
  let schoolName = document.getElementById("schoolName").value.trim();
  if(schoolName.length < 3){
    showSchoolNameError(true);   
    inputError = true;
  }else{
    showSchoolNameError(false);
  }

  // Reset the submit button
  document.getElementById("submit").disabled = false;

  const isValidUpn = new RegExp(/^([A-Z]{1})([0-9]{12})([A-Z]?)$/);
  // Check that a UPN exists and meets the required format
  if (!upn || !isValidUpn.test(upn)) {
    showUpnError(true);        
    inputError = true;    
  } else {
    showUpnError(false);
  }

  if(!inputError){
    return true;
  }else{
    return false;
  }

}


function showSchoolNameError(display){
  if(display) {
    document.getElementById("schoolName-field").classList.add("field--error");
    document.getElementById("schoolNameInvalid").classList.add("field--error");
    document.getElementById("schoolNameInvalid").style.display = "block";    
  }else{
    document.getElementById("schoolName-field").classList.remove("field--error");
    document.getElementById("schoolNameInvalid").classList.remove("field--error");
    document.getElementById("schoolNameInvalid").style.display = "none";      
  }  
}

// Display invalid upn error messages
function showUpnError(display) {
  if (display) {
    document.getElementById("upn-field").classList.add("field--error");
    document.getElementById("upnInvalid").style.display = "block";
    document.getElementById("upnInvalidHint").style.display = "block";     
  } else {
    document.getElementById("upn-field").classList.remove("field--error");
    document.getElementById("upnInvalid").style.display = "none";
    document.getElementById("upnInvalidHint").style.display = "none";      
  } 
  
  // If present don't display this div as any change should clear errors until upn is submitted again
  document.getElementById("upnInUseError").innerHTML = "";
  document.getElementById("upnInUseError").style.display = "none";
}

// Handle displaying the error returned from server side if the upn is already in use
function checkUpnInUse(){  
  // hide any existing upnInvalid messages 
  document.getElementById("upnInvalid").style.display = "none";
  document.getElementById("upnInvalidHint").style.display = "none"; 
  
  if((document.getElementById("upnInUseError").innerHTML).length < 1){
    document.getElementById("upn-field").classList.remove("field--error");
    document.getElementById("upnInUseError").style.display = "none";
  }else{        
    document.getElementById("upnInUseError").style.display = "block";
    document.getElementById("upn").value = document.getElementById("upnContainer").innerHTML;
    document.getElementById("upn-field").classList.add("field--error");
  }
}


function toggleClearButton(groupId){
  document.getElementById(groupId + "_clear").disabled = !needsGroupHasChecked(groupId);
}


function needsGroupHasChecked(groupId){
  var allDivs = $(".level-t, .level-a, .level-b, .level-c");
   
  // do the divs for this group
  for (var j = 0; j < allDivs.length; j++) {  
    for (var k= 0; k < allDivs[j].childNodes.length; k++) {
      elementToCheck = allDivs[j].childNodes[k]; 
      if((elementToCheck.checked)  && (elementToCheck.getAttribute("name") === groupId)) {         
        return true;        
      }      
    }  
  } 
  return false;  
}


function findSelectedNeedStatements(){   
  // find all the level a,b,c statement parent divs
  var allDivs = $(".level-t, .level-a, .level-b, .level-c");

  // Search through list of statements and create a new array of ids that are not in the group that was passed in.
  var selectedStatements = "";
  
  // do the divs for this group
  for (var j = 0; j < allDivs.length; j++) {  
    for (var k= 0; k < allDivs[j].childNodes.length; k++) {
      elementToCheck = allDivs[j].childNodes[k]; 
      if(elementToCheck.checked) {
        selectedStatements = selectedStatements + elementToCheck.getAttribute("id") + ",";              
      }
    }  
  }  
  return selectedStatements;

}


function enableClearButton(groupId){
  if(document.getElementById(groupId + "_clear").disabled = true){    
    document.getElementById(groupId + "_clear").disabled = false;
  }
}


function clearSelectedStatements(groupId){
  const radios = $('input:radio[name=' + groupId + ']');

  for (let i = 0; i < radios.length; i++){    
    $(radios[i]).prop('checked', false);
  }  
  document.getElementById(groupId + "_clear").disabled = true; 
}


 /* 
 	If the need level is lowering AND if any provision is already selected, then update needs with delete proviion, if the User confirm deletetion.
 	If the need level is lowering AND if any provision is already selected, then Refresh the needs page without the latest change, if the User NOT confirm the deletetion.
  	If the need level is Not lowering, continue with update needs but do not delete any associated provision.
  	
  */
function updateSelectedNeeds(hasSelectedProvisions, maximumNeedLevel){ 
  subAreaId = document.getElementById("subAreaId").value; 
  assessmentId = document.getElementById("assessmentId").value;

  var selectedStatements = findSelectedNeedStatements();

  // There will be no selected statements when saving this sub area page
  if(selectedStatements.length < 1){
    selectedStatements = "-1";
  } 

  // Disable the back link temporarily to ensure the update completes before user can navigate away from the page
  document.getElementById("back-button").style.pointerEvents="none";
  
  var currentMaxLevel = findMaxLevel();
  if(hasSelectedProvisions == "true" && currentMaxLevel < maximumNeedLevel){	
	  let userConfirmation = confirm("Are you sure you want to remove the provisions related to this need category?");
	  if (userConfirmation) {
		  const params = jQuery.param({
    		assessmentId: assessmentId,
    		subAreaId: subAreaId, 
    		selectedStatementIds: selectedStatements,
    		deleteProvision: true
  		  });
		jQuery.post('updateNeeds', 
	    params, 
	    function() { 
	      location.reload(); 
	  	});
	  } else{
		   location.reload();
	  }
  } else{
		const params = jQuery.param({
    		assessmentId: assessmentId,
    		subAreaId: subAreaId, 
    		selectedStatementIds: selectedStatements,
    		deleteProvision: false
  		});
		jQuery.post('updateNeeds', 
	    params, 
	    function() { 
	      location.reload(); 
		});
  }

  // use javascript to update max level for the user without collapsing the accordians by reloading the page
  showOrHideMaxLevel(currentMaxLevel);  
}


function findMaxLevel() {
  var maxLevel = "";
  
  var divA = $('.level-a');
  var divB = $('.level-b');
  var divC = $('.level-c');

  let hasA = false;
  let hasB = false;
  let hasC = false;
  
  hasA = hasCheckedChildElement(divA);
  hasB = hasCheckedChildElement(divB);
  hasC = hasCheckedChildElement(divC);

  
  if(hasC == true){
    maxLevel = "Block C";
  }else if(hasB == true && maxLevel != "Block C"){
    maxLevel = "Block B";
  }else if(hasA == true && maxLevel == ""){
    maxLevel = "Block A";
  }
  return maxLevel;
  
}

function showOrHideMaxLevel(maxLevel) {
 
  if(maxLevel != ""){
    $('#maximumNeedLevel').html(maxLevel);
    $('#maxNeedSelected').show();    
    $('#maxNeedNotSelected').hide();
  }else{
    $('#maxNeedNotSelected').show();
    $('#maxNeedSelected').hide();
  } 
  
}


function hasCheckedChildElement(baseElement){
  var elementToCheck;
    
  for (var i = 0; i < baseElement.length; i++) {    
      for (var j= 0; j < baseElement[i].childNodes.length; j++) {
        elementToCheck = baseElement[i].childNodes[j];        
        if (elementToCheck.checked) {
            return true;
        }    
      }
  }
}


function updateSelectedProvisions(){   
  var allDropDowns = $("select[id*='dropdown']");
  subAreaId = document.getElementById("subAreaId").value; 
  assessmentId = document.getElementById("assessmentId").value;
  needLevel = document.getElementById("needLevel").value;

  this.selectedProvisionStatements = "";

  for (var i = 0; i < allDropDowns.length; i++) {  
    var select = allDropDowns[i];  
    var text = select.options[select.selectedIndex].text;
    if(select.selectedIndex != 0) {
      this.selectedProvisionStatements = this.selectedProvisionStatements + select.options[select.selectedIndex].value + ",";          
    }
      
  }

  const params = jQuery.param({
      assessmentId: assessmentId,
      subAreaId: subAreaId,    
      selectedProvisionIds: this.selectedProvisionStatements,      
      needLevel: needLevel
  });

  // Disable the back link temporarily to ensure the update completes before user can navigate away from the page
  document.getElementById("back-button").style.pointerEvents="none";

  jQuery.post("updateProvisions", 
    params, 
    function() { 
      location.reload(); 
  });
    
}


// Accordions
var elements = document.querySelectorAll(
  ".aui .accordion h2.accordion-heading"
);
for (var i = 0; i < elements.length; i++) {
  let btn = elements[i].querySelector("button");
  let target = elements[i].nextElementSibling;
  btn.onclick = function (e) {
    e.preventDefault;
    let expanded = btn.getAttribute("aria-expanded") === "true";
    btn.setAttribute("aria-expanded", !expanded);
    target.hidden = expanded;
  };
}


function confirmRemoval(subAreaId, provisionStatementId) {    
  assessmentId = document.getElementById("assessmentId").value;
  let userConfirmation = confirm("Are you sure you want to remove this provision?");

  if (userConfirmation) {  

	  const params = jQuery.param({
	      assessmentId: assessmentId,
	      subAreaId: subAreaId,    
	      provisionStatementId: provisionStatementId
	  });

    jQuery.post("deleteProvision",
      params,
      function() {
      location.reload();
    });
  }
}


function confirmAssessmentRemoval(pathPrefix, assessmentId, warningMessage) {
  if ( window.history.replaceState ) {
     window.history.replaceState( null, null, window.location.href );
  }    
  let userConfirmation = confirm(warningMessage);

  if (userConfirmation) {  

    const params = jQuery.param({
      assessmentId: assessmentId  
    });

    jQuery.post(pathPrefix + "/deleteAssessment",
      params,
      function() {
      location.reload();
    });

  }
}

