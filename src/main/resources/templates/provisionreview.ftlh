<#import "/spring.ftl" as spring/>
<#include "./includes/header.ftlh">

  <div class="row-fluid">
    <div class="span12">
    
      <#-- Breadcrumb Start -->    
        <div>         
            <nav aria-label="Breadcrumb">
				<ul aria-label="Breadcrumb" class="breadcrumb breadcrumb-horizontal" role="navigation">
	                <li class="">
						<a href="/top-up-assessment" id="assessments-href">
							<@spring.message "breadcrumb.home.label"/>
						</a>
					</li>
					
					<li class="">
						<a href="/top-up-assessment/overview?assessmentId=${assessment.id}" id="overview-href">
							<@spring.message "breadcrumb.overview.label"/>
						</a>
					</li>
					                
	                <li class="active" aria-current="page">
	                	<@spring.message "breadcrumb.provisionreview.label"/>
	                </li>
                         
          			<span id="upn">UPN: ${assessment.upn}</span>  		 
            	</ul>	
            </nav>               		           			       	          	        
        </div>
      <#-- Breadcrumb End -->
      
      <h1 class="padded"><@spring.message "provisionreview.title"/></h1>
                
      <div class="slate slate--outline">
        <div class="slate__title">
            <@spring.message "provisionreview.action.title1"/>		
        </div>
        <div class="slate__title">         
			<@spring.message "provisionreview.action.title2"/>
        </div>
        <div class="slate__copy">
            <p>
                <@spring.message "provisionreview.action.info"/>
            </p>
           
        </div>
      </div>
                
      <div><@spring.message "provisionreview.selectedneeds.info"/></div>
      <#-- Selected Needs Start -->
       
      <div class="accordion">
	   
		<h2 class="accordion-heading">
		  <button type="button" class="accordion-button" id="accordion" aria-expanded="false">
		  <@spring.message "provisionreview.selectedneeds.heading"/>
		  <svg aria-hidden="true" focusable="false" viewBox="0 0 10 10">
		  <rect class="vert" height="8" width="2" y="1" x="4"></rect>
		  <rect height="2" width="8" y="4" x="1"></rect>
		  </svg>
		  </button>
		</h2>
		
        <div hidden>            
          <fieldset error" id="" class="field field--set field--set-option-block ">
          <div class="option-block">                  
		     <#list needStatements as needStatement>		      
		        <#if needStatement?index == 0 || needStatements[needStatement?index - 1].subAreaId != needStatement.subAreaId>
		          <h3>${needStatement.areaLabel}</h3>    
		        </#if>                
		          
		        <#if needStatement?index == 0 || needStatements[needStatement?index - 1].subAreaId != needStatement.subAreaId>
		          <h4>${needStatement.subAreaLabel}</h4>                  
		        </#if>
		        
		        <#if needStatement?index == 0 || needStatements[needStatement?index - 1].subCategoryId != needStatement.subCategoryId>
		          <div class="need_statement">
		            <h4>${needStatement.subCategoryLabel}</h4>                   
		            ${needStatement.statementLabel}
		          </div>
		        </#if>		               		        	                       
		     </#list>
	      </div>     
          </fieldset>
        </div>       
      <#-- Selected Needs End -->
	   <br/> 
	  </div>
	  <#-- Close the accordion div -->
      
      <#-- Selected Provisions Start -->
      <h2><@spring.message "provisionreview.selectedprovision.heading"/></h2>          
      
	  <fieldset class="field field--set" style="max-width: 90%">
        <legend class="field__label field__label--block" style="margin-bottom: 30px"><@spring.message "provisionreview.selectedprovision.legend"/></legend>                
    
      <#list provisions as provision>        
	   
        <#if provision.additionalProvision?string == "false">
			<#if provision.requestingFunding?string == "true">  	
            	<input class="field__input" type="checkbox" name="provision-checkbox-${provision.provisionStatementId}_${provision.subAreaId}" id="provision-checkbox-${provision?counter}" onchange="provisionReviewWasTicked()" checked/>
			<#else>
				<input class="field__input" type="checkbox" name="provision-checkbox-${provision.provisionStatementId}_${provision.subAreaId}" id="provision-checkbox-${provision?counter}" onchange="provisionReviewWasTicked()"/>	
			</#if>
        	<label class="field__label field__label--block" style="margin-bottom: 30px" for="provision-checkbox-${provision?counter}"><strong>${provision.provisionTypeLabel}</strong>
			<span class="field__extra" style="font-size: 100%">${provision.specificProvision}</span>
			</label>	        
       </#if>
      </#list>
      
      <#if hasAdditionalProvision?has_content && hasAdditionalProvision == "true">
      <div class="section-break--non-visible"> </div>
	      <div class="slate slate--outline">
	        <div class="slate__title">
	            <@spring.message "provisionreview.selected.additional.provision.heading"/>		
	        </div>	       
	        <div class="slate__copy">
	            <p>
	                <@spring.message "provisionreview.selected.additional.provision.info"/>
	            </p>	           
	        </div>
	      </div>
	      <#list provisions as provision>	        
	        <#if provision.additionalProvision?string == "true">
				<#if provision.requestingFunding?string == "true">
		    		<input class="field__input" type="checkbox" name="provision-checkbox-${provision.provisionStatementId}_${provision.subAreaId}" id="provision-checkbox-${provision?counter}" onchange="provisionReviewWasTicked()" checked/>
				<#else>
					<input class="field__input" type="checkbox" name="provision-checkbox-${provision.provisionStatementId}_${provision.subAreaId}" id="provision-checkbox-${provision?counter}" onchange="provisionReviewWasTicked()" />
				</#if>	
    	    	<label class="field__label field__label--block" style="margin-bottom: 30px" for="provision-checkbox-${provision?counter}"><strong>${provision.provisionTypeLabel}</strong>
					<span class="field__extra" style="font-size: 100%">${provision.specificProvision}</span>
				</label>         
	       </#if>		   
	      </#list>
	  </#if>
	  </fieldset>
	  <#-- Selected Provisions End -->
	  <#if assessment.provisionReviewStatus?has_content && assessment.provisionReviewStatus == "Confirmed">
	  	<#if assessment.totalHourlyCapExceeded?string == "true" || assessment.typeHourlyCapExceeded?string == "true"> 
		  <div>
		  	<h2 class="warning">
	        <span class="warning__icon"></span>
	        <span class="warning__copy">
	        	<@spring.message "provisionreview.funding.total.weekly.hours.exceeded"/> 
	        </span>   
	      	</h2>
	      </div> 
	  	<#elseif assessment.totalAnnualCost?has_content >
			<hr class="clear__confirm"/>
	  		<div><span><@spring.message "provisionreview.after.confirm.message"/></span><span class="padded">£${assessment.totalAnnualCost}</span> <div>
	  	</#if>	  	
     	<div class="button-holder">
     	  <a class="send-button" id="backButton" href="${pathPrefix}/overview?assessmentId=${assessment.id}"><button class="newcta" type="button" >Back</button></a>     
		  <button class="newcta newcta--primary send-button-primary" id="submitButton" type="submit" value="Submit" disabled="disabled" onclick="confirmProvisionReview('${assessment.id}')">Confirm</button>
     	</div>
	  <#else>
	  <hr class="clear__confirm"/>
	  	<div><@spring.message "provisionreview.before.confirm.message"/> <div>	  
     	<div class="button-holder">
     	  <a class="send-button" id="backButton" href="${pathPrefix}/overview?assessmentId=${assessment.id}"><button class="newcta" type="button">Back</button></a>     
		  <button class="newcta newcta--primary send-button-primary" id="submitButton" type="submit" value="Submit" disabled="disabled" onclick="confirmProvisionReview('${assessment.id}')"><@spring.message "provisionreview.confirm.button.label"/></button>
     	</div>
	  </#if>
    
  </div>
<#include "./includes/footer.ftlh">