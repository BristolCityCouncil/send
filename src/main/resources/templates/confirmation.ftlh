<#import "/spring.ftl" as spring/>
<#include "./includes/header.ftlh">

  <div class="row-fluid">
    <div class="span12">
      
      <h1 class="padded" id="pageTitleId"><@spring.message "confirmation.title"/></h1>
          
      <div style="padding-bottom: 50px;padding-top: 10px;"><@spring.message "confirmation.body1"/></div>
                                       
      <div><span><@spring.message "confirmation.upn"/>  </span> <span>${assessment.upn} </span></div>
      <div><span><@spring.message "confirmation.school.name"/> </span> <span>${assessment.schoolName}</span></div>
      <div><span><@spring.message "confirmation.submitted.on"/></span> <span >${submittedDate?string("dd/MM/yyyy")}</span></div>
                
      <div style="padding-bottom: 50px;padding-top: 20px;"><@spring.message "confirmation.body2"/>
      	<a href='<@spring.message "confirmation.further.info.link"/>'  target="_blank" ><@spring.message "confirmation.further.info.link.label"/></a><div>
      </div>
     
      <div class="button-holder">
     	  <a class="send-button" id="backButton" href="${pathPrefix}"><button class="newcta" type="button"><@spring.message "confirmation.back"/></button></a>     		  
      </div>           
      <div style="padding-bottom: 40px;"></div>
     
    </div>  
  </div>
<#include "./includes/footer.ftlh">