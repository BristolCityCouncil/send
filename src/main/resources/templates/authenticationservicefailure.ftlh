<#import "/spring.ftl" as spring/>
<#include "./includes/header.ftlh">
  <div class="row-fluid">
    <div class="span12">   
      <header class="step-header">
        <h1 class="step-header__title step-header__two-cols">
        <span><@spring.message "application.title"/></span>        
        </h1>        
      </header>
        <div class="open-validation">
            <div class="open-validation__title">
                <h1 id="pageTitleId"><@spring.message "application.send.failure.title"/></h1>
            </div>
            <div class="open-validation__error">
             	<p><@spring.message "application.auth.failure.body1"/></p>                     
             	<p><@spring.message "application.send.failure.body2"/></p>
             	<p><@spring.message "application.send.failure.body3"/></p>                                                                       
                <h3> <a href="${pathPrefix}"><@spring.message "application.auth.failure.link.label"/></a></h3>                                      
            </div>
        </div>
    </div>
  </div>
<#include "./includes/footer.ftlh">