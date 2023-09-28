<#import "/spring.ftl" as spring/>
<#include "./includes/header.ftlh">

  <div class="row-fluid">
    <div class="span12">
		
    	<div>         
           <nav aria-label="Breadcrumb">
				<ul aria-label="Breadcrumb" class="breadcrumb" role="navigation">
	                 <li class="active" aria-current="page">
	                	<@spring.message "breadcrumb.home.label"/>
	                </li>											
            	</ul>	
            </nav>              		           			       	          	        
        </div>
      <h1  class="padded"><@spring.message "yourAssessments.assessments.new.title"/></h1>
	   
                
      <form id="newAssessment" action="${pathPrefix}" method="post" onsubmit="return validateUpn()" style="margin-bottom: 60px;">
        <#if upnError?has_content>
          <div id="upn-field" class="field field--error">
            <label class="field__label field__label--block" for="upn"><@spring.message "yourAssessments.assessments.new.upn.label"/></label>
            <input onfocus="showUpnError(false)" type="text" id="upn" class="field__input" name="upn" maxlength="14"/>
            <div id="upnInvalid" class="field__message field__message--error" style="display: none"><@spring.message "yourAssessments.assessments.new.upn.invalid"/></div>
            <div id="upnInvalidHint" class="field__message field__message--error" style="display: none"><@spring.message "yourAssessments.assessments.new.upn.invalid.hint"/></div>
            <div id="upnInUseError" class="field__message field__message--error" style="display: block">${upnError}</div>
          </div>
        <#else>  
          <div id="upn-field" class="field">
            <label class="field__label field__label--block" for="upn"><@spring.message "yourAssessments.assessments.new.upn.label"/></label>
            <input onfocus="showUpnError(false)" type="text" id="upn" class="field__input" name="upn" maxlength="14"/>
            <div id="upnInvalid" class="field__message field__message--error"><@spring.message "yourAssessments.assessments.new.upn.invalid"/></div>
            <div id="upnInvalidHint" class="field__message field__message--error"><@spring.message "yourAssessments.assessments.new.upn.invalid.hint"/></div>
            <div id="upnInUseError" class="field__message field__message--error" style="display: none"></div>
          </div>  
        </#if>        
        <div id="schoolName-field" class="field">
          <label class="field__label field__label--block" for="schoolName"><@spring.message "yourAssessments.assessments.new.schoolName.label"/></label>
          <input onchange="showSchoolNameError(false)" type="text" id="schoolName" class="field__input" name="schoolName"/>
          <div id="schoolNameInvalid" class="field__message field__message--error"><@spring.message "yourAssessments.assessments.new.schoolName.invalid"/></div>          
        </div>
        <div class="field">
                       
        <button id="submit" class="newcta newcta--primary"><@spring.message "yourAssessments.assessments.new.button.label"/></button>
        </div>
      </form>
      
      <h2><@spring.message "yourAssessments.assessments.title"/></h2>
      <#if (yourAssessments?size > 0)>
        <#list yourAssessments as yourAssessment>
          <div class="info">
            <div class="info__title">UPN: ${yourAssessment.upn}</div>                                
            <div class="info__copy">School name: ${yourAssessment.schoolName}</div>
            <div class="info__copy">Edited on: ${yourAssessment.modifiedDate}</div>
            
			<#assign warningMessage><@spring.messageArgs code="yourAssessments.assessment.delete.confirm" args=['${yourAssessment.upn}']/></#assign>
			
            <div class="home-links">
            	
            	<a href="${pathPrefix}/overview?assessmentId=${yourAssessment.id}"><@spring.message "yourAssessments.assessment.edit.link"/></a>            	
            	<a href="javascript:void(0)" style="color:red;" onclick="javascript:confirmAssessmentRemoval('${pathPrefix}', '${yourAssessment.id}', '${warningMessage}')" id="removeAssessmentLink_${yourAssessment.id}"><@spring.message "yourAssessments.assessment.delete.link"/></a>
            </div>
          </div>
        </#list>
      <#else>
        <p><@spring.message "yourAssessments.assessment.none"/></p>
      </#if>
    </div>
  </div>

<script type="text/javascript">$(window).load(checkUpnInUse());</script>  

<#include "./includes/footer.ftlh">