<#import "/spring.ftl" as spring/>
<#include "./includes/header.ftlh">

  <div class="row-fluid">
    <div class="span12">

      <header class="step-header">
        <h1 class="step-header__title step-header__two-cols">
	        <span>Assessments > Overview</span>
	        <span class="step-header__title__steps">          
	          <span id="upn">UPN: ${assessment.upn}</span>
	        </span>       
        </h1>
        <h2 class="step-header__subtitle"><@spring.message "overview.title"/></h2>
      </header>
      
      <div class="info__copy"><@spring.message "overview.body"/></div>
      
      <div><h2><@spring.message "overview.heading1"/></h2></div>
      <div class="info__copy"><@spring.message "overview.heading1.body"/></div>
      <div class="section-break--visible"></div>
      
      <div class="provisions-links" id="assessmentPageLink_${assessment.id}">
       	<a href="${pathPrefix}/summary?assessmentId=${assessment.id}" id="assessmenthref_${assessment.id}"><@spring.message "overview.assess.link.label"/></a>              
      </div>
      <div class="section-break--visible"></div>      
      
	 <#-- do Tasks and links -->       
     <div>
	     <div>	     
	
	      <h2><@spring.message "overview.task.heading"/></h2>
	
	      <ol class="app-task-list">
	      
	        <li>
	          <h3 class="app-task-list__section">
	            <span class="app-task-list__section-number">1. <@spring.message "overview.task1.heading"/></span> 
	          </h3>
	          <div class="section-break--visible"></div>
	          <ul class="app-task-list__items">
	            <li class="app-task-list__item">            
	              <span class="app-task-list__task-name">
	                <a href="#" aria-describedby="" id="task1-href"><@spring.message "overview.task1.link.label"/></a>
	              </span>	 
	            </li>             	                     
	          </ul>
	        </li>
	
	        <li>
	          <h3 class="app-task-list__section">
	            <span class="app-task-list__section-number">2. <@spring.message "overview.task2.heading"/></span>
	          </h3>
	          <div class="section-break--visible"></div>
	          <ul class="app-task-list__items">	
	          	<li class="app-task-list__item">         
	              <span class="app-task-list__task-name">
	                <a href="#" aria-describedby="" id="task2-href">
	                   <@spring.message "overview.task2.link.label"/>
	                </a>
	              </span>
	            </li>	              	                      
	          </ul>
	        </li>
	
	        <li>
	          <h3 class="app-task-list__section">
	            <span class="app-task-list__section-number">3. <@spring.message "overview.task3.heading"/></span> 
	          </h3>
	          <div class="section-break--visible"></div>
	          <ul class="app-task-list__items">
	          	<li class="app-task-list__item"> 	           
	              <span class="app-task-list__task-name">
	              	<a href="#" aria-describedby="" id="task3-href">
	              		<@spring.message "overview.task3.link.label"/>
	              	</a>	               	
	              </span>
	            </li>	             	           
	          </ul>
	        </li>
	        
	      </ol>
	    </div>
     </div>
     
     <div>
     	  <a class="send-button" id="back-button" href="${pathPrefix}"><button class="newcta" type="button">Back</button></a>     
		  <button class="newcta newcta--icon newcta--download" type="submit" value="Submit" disabled="disabled"><@spring.message "overview.submit.label"/></button>
     </div>
     
    </div>  
  </div>
<#include "./includes/footer.ftlh">