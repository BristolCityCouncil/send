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
	                	<@spring.message "submitYourApplication.title"/>
	                </li>
                         
          			<span id="upn">UPN: ${assessment.upn}</span>  		 
            	</ul>	
            </nav>               		           			       	          	        
        </div>
      <#-- Breadcrumb End -->
      
      <h1 class="padded" id="pageTitleId"><@spring.message "submitYourApplication.title"/></h1>
          
	  <div class="slate slate--outline">
	    <div class="slate__title" id="slateTitleId">
	        <@spring.message "submitYourApplication.tc.title1"/>
	    </div>
	   <div style="padding-bottom: 20px;"> </div>
	    <div class="slate__copy">
	    	<p>
	            <@spring.message "submitYourApplication.tc.title2"/>
	        </p>
	        
	        <ul>		       
	       		 <li class="submitTcType"><@spring.message "submitYourApplication.tc.text1"/></li>
	       		 <li class="submitTcType"><@spring.message "submitYourApplication.tc.text2"/></li>		       		
	       		 <li class="submitTcType"><@spring.message "submitYourApplication.tc.text3"/></li>	       		        	
			</ul>
	       
	    </div>
	  </div>
 	  
		
	  <form id="submit" action="${pathPrefix}/submitApplication" method="post" style="margin-bottom: 60px;">
	     <input type="hidden" id="assessmentId" name="assessmentId" value="${assessment.id}">
	     <div style="padding-top: 60px;">
	     	<input class="field__input"  type="checkbox" name="selecttc" id="tc-checkbox" onchange="enableOrDisableSubmit(this)" />
	     	<label class="field__label field__label--block"  for="tc-checkbox"><@spring.message "submitYourApplication.tc.label"/></label>
	     </div>
	     <div class="button-holder">
	     	  <a class="send-button" id="backButton" href="${pathPrefix}/overview?assessmentId=${assessment.id}"><button class="newcta" type="button">Back</button></a>     
			  <button class="newcta newcta--primary" type="submit" value="Submit" id="submitButton" disabled="disabled"><@spring.message "submitYourApplication.submit.label"/></button>
	     </div>
	  </form>

    </div>  
  </div>
<#include "./includes/footer.ftlh">