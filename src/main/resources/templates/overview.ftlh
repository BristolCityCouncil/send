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
					                
	                <li class="active" aria-current="page">
	                	<@spring.message "breadcrumb.overview.label"/>
	                </li>
                         
          			<span id="upn">UPN: ${assessment.upn}</span>  		 
            	</ul>	
            </nav>               		           			       	          	        
        </div>
      <#-- Breadcrumb End -->
      
      <h1 class="padded" id="pageTitleId"><@spring.message "overview.title"/></h1>
          
      <div style="padding-bottom: 60px;padding-top: 40px;"><@spring.message "overview.body"/></div>
      
      <h2><@spring.message "overview.heading1"/></h2>
      <div style="padding-bottom: 20px;padding-top: 20px;"><@spring.message "overview.heading1.body"/></div>
      
      
      <div class="section-break--visible--double" id="assessmentPageLink_${assessment.id}" >
       	<a href="${pathPrefix}/summary?assessmentId=${assessment.id}" id="assessmenthref_${assessment.id}"><@spring.message "overview.assess.link.label"/></a>              
      </div>
     
      
	 <#--  Tasks and links Start-->       
     <div style="padding-top: 60px;">
	     	     	
		  <h2><@spring.message "overview.task.heading"/></h2>
		  <div style="padding-bottom: 20px;"><@spring.message "overview.task.body"/></div>
		
		  <h3> 1. <@spring.message "overview.task1.heading"/> </h3>
		  <div style="padding-bottom: 20px; margin-left: 22px"><@spring.message "overview.task1.text"/></div>
		  <div class="section-break--visible--double">
		  	<a href="${pathPrefix}/provisionReview?assessmentId=${assessment.id}"  id="task1-href" ><@spring.message "overview.task1.link.label"/></a>
		  </div> 
		     	       
		  <h3 style="padding-top: 10px;"> 2. <@spring.message "overview.task2.heading"/></h3>
		  <div style="padding-bottom: 20px; margin-left: 22px"><@spring.message "overview.task2.text"/></div>
		  <div class="section-break--visible--double" >
		  	<a href="${pathPrefix}/uploadSupportDocuments?assessmentId=${assessment.id}" id="task2-href" "><@spring.message "overview.task2.link.label"/></a>
		  </div>
		   
		  <h3 style="padding-top: 10px;"> 3. <@spring.message "overview.task3.heading"/></h3>
		  <div style="padding-bottom: 20px; margin-left: 22px"><@spring.message "overview.task3.text"/></div>
		  <div class="section-break--visible--double" >	        
		  	<a href="${pathPrefix}/submitYourApplication?assessmentId=${assessment.id}" id="task3-href" ><@spring.message "overview.task3.link.label"/></a>
		  </div>	               	
	     
     </div>
     <#--  Tasks and links End-->
     
     <div class="button-holder">
     	  <a class="send-button" id="backButton" href="${pathPrefix}"><button class="newcta" type="button">Back</button></a>     		  
     </div>
     
    </div>  
  </div>
<#include "./includes/footer.ftlh">