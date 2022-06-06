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
        <h2 class="step-header__subtitle">${subAreaLabel}</h2>
      </header>

      <div class="accordion">
        <#list needStatements as needStatement>
          <#-- if first need statement in group, do the group title and heading -->        
          <#if needStatement?index == 0 || needStatements[needStatement?index - 1].subCategoryId != needStatement.subCategoryId>
            <h2 class="accordion-heading">
              <button type="button" class="accordion-button" id="${needStatement.statementGroupNumber}_accordion" aria-expanded="false">
              ${needStatement.subCategoryLabel}
              <svg aria-hidden="true" focusable="false" viewBox="0 0 10 10">
              <rect class="vert" height="8" width="2" y="1" x="4"></rect>
              <rect height="2" width="8" y="4" x="1"></rect>
              </svg>
              </button>
            </h2>
           <div hidden>
             <h3>Select the most accurate statement</h3>
              <p class="expected">
                <strong>Expected</strong>
                ${needStatement.statementLabel}
              </p>
              <fieldset aria-describedby="${needStatement.statementGroupNumber}-error" id="${needStatement.statementGroupNumber}" class="field field--set field--set-option-block ">
              <div class="option-block">          
          <#else>
          <#-- now check need statements for their level -->
            <#if needStatement.statementLevel == "e">
              <h3>Select the most accurate statement</h3>
              <p class="expected">
                <strong>Expected</strong>
                ${needStatement.statementLabel}
              </p>
            <#else>

            <div class="option-block-item level-${needStatement.statementLevel}">
            <#-- Check selected statements to determine how to display the current need statement -->
            <#if selectedStatementIds?has_content>        
              <#if selectedStatementIds?seq_contains(needStatement.statementNumber)>
                <input class="field__input" type="radio" name="${needStatement.statementGroupNumber}" id="${needStatement.statementNumber}" onchange="toggleClearButton('${needStatement.statementGroupNumber}')" checked />              
              <#else>                
                <input class="field__input" type="radio" name="${needStatement.statementGroupNumber}" id="${needStatement.statementNumber}" onchange="toggleClearButton('${needStatement.statementGroupNumber}')" />    
              </#if>                        
            <#else>
              <input class="field__input" type="radio" name="${needStatement.statementGroupNumber}" id="${needStatement.statementNumber}" onchange="toggleClearButton('${needStatement.statementGroupNumber}')" />
            </#if> 
            <#-- display the statement level for this need statement-->        
            <label class="field__label field__label--block" for="${needStatement.statementNumber}">${needStatement.statementLabel}</label>            
            </div>
          </#if>  <#-- closing if tag for every statement other than the title row -->

          <#if needStatement?is_last || needStatements[needStatement?index + 1].statementGroupNumber != needStatement.statementGroupNumber>
            <#if selectedGroupIds?seq_contains(needStatement.statementGroupNumber)> 
              <div class="right-button-holder">
                <button class="newcta" id="${needStatement.statementGroupNumber}_clear" onclick="clearSelectedStatements('${needStatement.statementGroupNumber}')">Clear selection</button>              
              </div> 
            <#else>
              <div class="right-button-holder">
                <button class="newcta" id="${needStatement.statementGroupNumber}_clear" onclick="clearSelectedStatements('${needStatement.statementGroupNumber}')" disabled>Clear selection</button>              
              </div>               
            </#if>
          </#if>

          <#if needStatement?is_last || needStatements[needStatement?index + 1].subCategoryId != needStatement.subCategoryId >
            </div>
            </fieldset>
            </div>
          </#if>
        </#if>          
        </#list>        
      </div>

      <h2><@spring.message "needs.needLevel.heading"/></h2>
      
      <#if maximumNeedLevel?has_content>
        <div id="maxNeedSelected" class="info info--affirm info--reverse">
          <div class="info__title"><@spring.message "needs.needLevel.title"/></div>          
          <div id="maximumNeedLevel" class="info__copy"><@spring.messageArgs code="needs.needLevel.selected.value" args=['${maximumNeedLevel}']/></div>
        </div>
        <div id="maxNeedNotSelected" style="display:none"><@spring.message "needs.needLevel.notSelected"/></div>
      <#else>
        <div id="maxNeedSelected" style="display:none" class="info info--affirm info--reverse">
          <div class="info__title"><@spring.message "needs.needLevel.title"/></div>
          <div id="maximumNeedLevel" class="info__copy">${maximumNeedLevel}</div>
        </div>
        <div id="maxNeedNotSelected"><@spring.message "needs.needLevel.notSelected"/></div>
      </#if>

      <input type="hidden" id="assessmentId" name="assessmentId" value="${assessment.id}">
      <input type="hidden" id="subAreaId" name="subAreaId" value="${subAreaId}">

      <div class="button-holder">
        <a class="send-button" id="back-button" href="${pathPrefix}/summary?assessmentId=${assessment.id}"><button class="newcta" type="button">Back</button></a> 
        <button class="newcta newcta--primary send-button-primary" type="submit" value="Submit" onclick="updateSelectedNeeds('${hasSelectedProvisions?c}', '${maximumNeedLevel}')">Save</button>
      </div>

    </div>
  </div>
<#include "./includes/footer.ftlh">