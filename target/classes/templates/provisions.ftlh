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
        <h1 class="step-header__subtitle">${subAreaLabel} | Level ${needLevel}</h1> 
      </header>

    <div style="padding-top:80px;">      
      <#list provisions as provision>

        <#if provision?index == 0 || provisions[provision?index - 1].provisionGroupId != provision.provisionGroupId>
          <h2>${provision.provisionGroup}</h2>      
        </#if>      

        <#-- if first need statement in group, do the group title and heading -->    
        <#if provision?index == 0 || provisions[provision?index - 1].provisionTypeNumber != provision.provisionTypeNumber>
        <div class="field">
			    <label class="field__label field__label--block" for="dropdown-${provision.provisionTypeId}" id="selectedProvisionLabel_${provision.provisionTypeId}">${provision.provisionTypeLabel}</label>
			    <div class="drop drop--block">
			      <select name="dropdown" id="dropdown-${provision.provisionTypeId}">
            <#-- check for selected provisions -->             
            <#if selectedProvisionIds?seq_contains(provision.provisionStatementId)>
              <#-- if current id is in selected list, do this -->
              <option>Select provision</option>
			        <option selected value="${provision.provisionStatementId}" title="${provision.specificProvision}">${provision.specificProvision?truncate(180)}</option>
            <#else>            
             <#-- if current id is not in selected list, do this -->
              <option selected>Select provision</option>
			        <option value="${provision.provisionStatementId}" title="${provision.specificProvision}">${provision.specificProvision?truncate(180)}</option>
            </#if>            
        <#else>
            <#-- check for selected provisions -->
            <#if selectedProvisionIds?seq_contains(provision.provisionStatementId)>
              <#-- if current id is in selected list, do this -->  
              <option selected value="${provision.provisionStatementId}" title="${provision.specificProvision}">${provision.specificProvision?truncate(180)}</option>
            <#else>
              <#-- if current id is not in selected list, do this -->
              <option value="${provision.provisionStatementId}" title="${provision.specificProvision}">${provision.specificProvision?truncate(180)}</option>
            </#if>            
			  </#if>
		  
		    <#-- close the provisions drop down  -->
        <#if provision?index == provisions?size -1 || provisions[provision?index + 1].provisionTypeNumber != provision.provisionTypeNumber>
  		      </select>
	  	      <div class="drop__background"></div>
		  		  </div> <#-- close div class drop--block -->
			    </div>  <#-- close div class field -->		  
        </#if>  
      
		  </#list>  


      <h2><@spring.message "provisions.selectedProvisions.title"/></h2>
      <p><@spring.message "provisions.selectedProvisions.info"/></p>
 
      <#list selectedProvisions as selectedProvision>
        <#if selectedProvision.specificProvision?has_content>
        <div class="info info--affirm">
          <div class="info__title" id="selectedProvisionTitle_${selectedProvision.provisionStatementId}">
            ${selectedProvision.provisionGroup}
          </div>
          <div class="info__copy" id="selectedProvisionText_${selectedProvision.provisionStatementId}">                      
            ${selectedProvision.specificProvision}
          </div> 
        </div>  
        </#if>  
      </#list>
     
    </div>

    <input type="hidden" id="assessmentId" name="assessmentId" value="${assessment.id}">
    <input type="hidden" id="subAreaId" name="subAreaId" value="${subAreaId}">
    <input type="hidden" id="needLevel" name="needLevel" value="${needLevel}">

    <div class="button-holder">
      <a class="send-button" id="back-button" href="${pathPrefix}/summary?assessmentId=${assessment.id}"><button class="newcta" type="button">Back</button></a> 
      <button class="newcta newcta--primary send-button-primary" type="submit" value="Submit" onclick="updateSelectedProvisions()">Save</button>
    </div>

   
  </div>    
  </div>
<#include "./includes/footer.ftlh">