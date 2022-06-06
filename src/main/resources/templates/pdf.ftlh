<html>
<head>
  <meta http-equiv="Content-type" content="text/html;charset=UTF-8"/>
  <style type="text/css">


    @page {             
        @bottom-center {content: element(footer)}   
    }
    #footer {position: running(footer);}
    #pagenumber:before {content: counter(page);}
    #pagecount:before {content: counter(pages);}
   

    *,*::before, *::after{box-sizing: border-box;}

    body{
      font-family: Arial, Helvetica, sans-serif;
      font-size: 14px;
      /** line-height: 1.2em;  **/
    }

    h1,h2,h3,h4,p{
      display: block;
      margin: 0px;
      padding: 0px;
      text-align: left;
    }

    p{
      line-height: 0.2em;
    }

    .icon {
      float: right;
    }

    h1 {
      font-size: 22px;
      font-weight: bold;
      margin-bottom: 22px;
    }

    h2{
      font-size: 20px;
      font-weight: bold;
      margin-bottom: 20px;
    }

    h3{
      font-size: 17px;
      font-weight: bold;
      line-height: 22px;
      margin-bottom: 0px;
    }

    h4{
      font-size: 14px;
      font-weight: bold;      
      line-height: 20px;
      margin-bottom: 0px;
    }

    .mb-14{
      margin-bottom: 14px;
    }

    .mb-17{
      margin-bottom: 17px;
    }

    .bold {
      font-weight: bold;
    }

    .need_level {
      padding-bottom: 1.5em;
    }

    .need_statement{      
      padding-bottom: 25px;
    }

    .provision_statement{
      padding-top: 5px;
      padding-bottom: 25px;
    }

    .header {
      display: inline-block;
      vertical-align: top;
      text-align: left; /* optional */
      width: 79%; 
      font-size: 14px;   
      font-weight: bold;       
      padding-bottom: 60px;   
    }

    .icon{
      display: inline-block;
      vertical-align: top;
      text-align: right; /* optional */
      width: 20%;       
    }

    .footer-left{
        text-align: left;
        float: left;
        width:79%;  
        padding-bottom: 50px;      
    }

    .footer-right{
        text-align: right;
        float: right;
        width: 20%;  
        padding-bottom: 50px;      
    }

    .content{
      padding-bottom: 30px;
      padding-top: 40px;      
    }

  </style>

</head>

<body>

<div class="content">   

    <div id="footer">
      <hr/>
      <div class="footer-left">UPN: ${yourAssessment.upn}<br/>
      School name: ${yourAssessment.schoolName?html}<br/><br/><br/></div>
      <div class="footer-right">Page <span id="pagenumber" /> of <span id="pagecount" /></div>
    </div>

    <div class="header">
      UPN: ${yourAssessment.upn?html}<br/>
      School name: ${yourAssessment.schoolName?html}<br/>
      Assessment ID: ${yourAssessment.id?html}<br/>
      Edited on: ${yourAssessment.modifiedDate?html}<br/>
      Author: ${ownerName?html}<br/>
    </div>

    <div class="icon">
      <img src="https://style.bristol.gov.uk/images/logo-bcc.png" />
    </div>
    
    <div class="assessment">
      <h1>Selected needs</h1>   
      <#list needStatements as needStatement> 
        <#if needStatement?index == 0 || needStatements[needStatement?index - 1].subAreaId != needStatement.subAreaId>
          <h1>${needStatement.areaLabel?html}</h1>    
        </#if>                
          
        <#if needStatement?index == 0 || needStatements[needStatement?index - 1].subAreaId != needStatement.subAreaId>
          <h2>${needStatement.subAreaLabel?html}</h2>                  
        </#if>
        
        <#if needStatement?index == 0 || needStatements[needStatement?index - 1].subCategoryId != needStatement.subCategoryId>
          <div class="need_statement">
            <h3>${needStatement.subCategoryLabel?html}</h3>                   
            ${needStatement.statementLabel?html}
          </div>
        </#if>
               
        <#if needStatement?is_last || needStatements[needStatement?index + 1].subAreaId != needStatement.subAreaId>     
            <!-- get correct level for subArea --> 
            <#list needLevels as key, value>  
              <#if key == needStatement.subAreaId>
              <h2>Planned Provisions available: Block ${value?capitalize}</h2>  
              </#if>
            </#list>                   
            <br/> 
        </#if>
                       
      </#list>
           
	  <h1>Planned  provision</h1>
	  <#list provisions as provision>	   
	        <h3>${provision.provisionGroup?html}</h3> 
	        <div class="provision_statement">                         
	          <h4>${provision.provisionTypeLabel?html}</h4>         
	          ${provision.specificProvision?html}   
	        </div> 	     
	  </#list>        
      
    </div>  

</div>
   
</body>
</html>