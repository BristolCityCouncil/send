<#import "/spring.ftl" as spring/>
<#include "./includes/header.ftlh">

  <div class="row-fluid">
    <div class="span12">

      <header class="step-header">
        <h1 class="step-header__title step-header__two-cols">
        <span><@spring.message "application.title"/></span>
        <span class="step-header__title__steps">          
          <span id="upn">UPN: ${assessment.upn}</span>
        </span>
        </h1>
        <h2 class="step-header__subtitle"><@spring.message "assessment.title"/></h2>
      </header>
	  <#assign needSelected = "false">
      <div class="assessment">  
      
      <#list needAreas as needArea>              
          <h2 class="padded">${needArea.areaLabel}</h2> 
            
          <div class="needs-heading"> 
			      <h3>Needs</h3>
          </div>

          <div class="provisions-heading">
			      <h3>Provisions</h3>  
          </div> 		  
          <#list needSubAreas as needSubArea>
          	<#assign provisionCounter = 0>
          
            <#-- does the current need have a need level set? -->
            <#list needsMap as key, value>  
              <#if key == needSubArea.subAreaId>
                <#assign keyFound=key>                
                <#assign valueFound=value>
              </#if>
            </#list>

            <#-- do needs links -->       
            <#if keyFound?? && keyFound==needSubArea.subAreaId && needSubArea.areaId == needArea.areaId>
              <div class="needs-links" id="needsPageLink_${needSubArea.subAreaId}">                                           
                <p><a href="${pathPrefix}/needs?assessmentId=${assessment.id}&amp;subAreaId=${needSubArea.subAreaId}&amp;selectedStatementIds=" id="needshref_${needSubArea.subAreaId}">${needSubArea.subAreaLabel}</a></p>     
              <#assign needSelected = "true">
                  <div class="info info--affirm info--reverse" >
                  <div class="info__title"><@spring.message "yourAssessments.assessment.selectedNeedLevel"/></div>
                  <div id="maximumNeedLevel_${needSubArea.subAreaId}" class="info__copy">Block ${valueFound}</div>
                </div>
              </div>  
            <#elseif needSubArea.areaId == needArea.areaId > 
              <div class="needs-links" id="needsPageLink_${needSubArea.subAreaId}">                                           
                <p><a href="${pathPrefix}/needs?assessmentId=${assessment.id}&amp;subAreaId=${needSubArea.subAreaId}&amp;selectedStatementIds=" id="needshref_${needSubArea.subAreaId}">${needSubArea.subAreaLabel}</a></p>     
              </div>  
            </#if>                        

            <#-- do provisions links -->       
            <#if keyFound?? && keyFound == needSubArea.subAreaId && needSubArea.areaId == needArea.areaId>
              <div class="provisions-links" id="provisionPageLink_${needSubArea.subAreaId}">
                <p><a href="${pathPrefix}/provisions?assessmentId=${assessment.id}&amp;areaId=${needArea.areaId}&amp;subAreaId=${needSubArea.subAreaId}&amp;needLevel=${valueFound}" id="provisionhref_${needSubArea.subAreaId}">${needSubArea.subAreaLabel}</a></p>     
             
              <#if selectedProvisions??>
                <#list selectedProvisions as selectedProvision>               
				  <#if selectedProvision.subAreaId == needSubArea.subAreaId>
				   <#assign provisionCounter++>
					 <div class="info info--affirm">
						<div class="info__title" id="selectedProvisionTitle_${needSubArea.subAreaId}_${provisionCounter}">
							${selectedProvision.provisionGroup}
						</div>
						<div class="info__copy" id="selectedProvisionType_${needSubArea.subAreaId}_${provisionCounter}">
						 <#if selectedProvision.provisionTypeLabel??>
							${selectedProvision.provisionTypeLabel}
						 </#if>
						</div>
						<div id="selectedProvisionText__${needSubArea.subAreaId}_${provisionCounter}" >
							${selectedProvision.specificProvision}
						</div>
						
						 <div class="provisions-links" id="selectedProvisionLinks_${needSubArea.subAreaId}_${provisionCounter}">
						  <a href="${pathPrefix}/provisions?assessmentId=${assessment.id}&amp;areaId=${needArea.areaId}&amp;subAreaId=${needSubArea.subAreaId}&amp;needLevel=${valueFound}" id="editProvisionLink_${needSubArea.subAreaId}_${provisionCounter}">Edit</a>              
     					<a href="javascript:void(0)" onclick="javascript:confirmRemoval('${selectedProvision.subAreaId}', '${selectedProvision.provisionStatementId}')" id="removeProvisionLink_${needSubArea.subAreaId}_${provisionCounter}">Remove</a>	      					
						 </div>
					 </div>
				  </#if>
				</#list>
			  </#if>
			</div>  	            
            <#elseif needSubArea.areaId == needArea.areaId>
              <div class="provisions-links" id="provisionPageLink_${needSubArea.subAreaId}">
                <p><a href="javascript:void(0)" class="link-disabled" disabled="disabled" id="provisionhref_${needSubArea.subAreaId}">${needSubArea.subAreaLabel}</a></p>     
              </div>  
            </#if>  
          </#list>          
      </#list> 

	  <#if needSelected?? && needSelected == "true">	  			  
	  	 <form action="downloadPDF" method="POST">          
	        <input type="hidden" id="assessmentId" name="assessmentId" value="${assessment.id}">	
	         <#if RequestParameters.simulateDownloadPDFFailure?? && RequestParameters.simulateDownloadPDFFailure == "true">   
	             <input type="hidden" name="simulateDownloadPDFFailure" value="true">
	          <#else>
	             <input type="hidden" name="simulateDownloadPDFFailure" value="false">
	         </#if>
          <br/>
	        <button class="newcta newcta--icon newcta--download" type="submit" value="Submit">Download as PDF</button>
	     </form>	       
      <#else>  
    	<div>
      <br/>   	      
			<button class="newcta newcta--icon newcta--download" type="submit" value="Submit" disabled="disabled">Download as PDF</button>
		</div>	     
      </#if>
      <br/>    
      <a class="send-button" id="back-button" href="${pathPrefix}"><button class="newcta" type="button">Back</button></a>     

      </div>
 
    </div>  
  </div>
<#include "./includes/footer.ftlh">