<html>
<head>
  <meta http-equiv="Content-type" content="text/html;charset=UTF-8"/>
  <style type="text/css">


    @page {             
        @bottom-center {content: element(footer)} ;
        size:landscape;  
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

    .p_border{
      border-width: 1px 1px 1px 1px;
	  border-color: #eaeaea;
	  border-style: solid;
	  padding: 10px 10px 10px 10px;
	  font-size: 14px;      
      line-height: 16px;  
    }

    .icon {
      float: right;
    }

    h1 {
      font-size: 22px;      
      line-height: 48px;          
    }

    h2{
      font-size: 20px;
      font-weight: bold;
      line-height: 22px;
      color: #676973;
    }

    h3{
      font-size: 17px;   
      line-height: 24px;     
    }

    h4{
      font-size: 14px;
      font-weight: bold;      
      line-height: 18px;   
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
      padding-top: 12px;
      padding-bottom: 12px;
    }

    .provision_statement{
      padding-top: 5px;
      padding-bottom: 25px;
    }

    .header {
      display: inline-block;
      vertical-align: top;
      text-align: left; /* optional */
      width: 70%; 
      font-size: 14px;              
      padding-bottom: 30px;   
      line-height: 16px;
    }
   
    .bolded { font-weight: bold; }

    .icon{
      display: inline-block;
      vertical-align: top;
      text-align: right; /* optional */
      width: 20%;       
    }
	
	.footer{	         
        border-top: 1px solid #b1b4b6; 
        margin-top: 10px; 
        font-size: 12px;   
        line-height: 14px; 
        color: #676973;
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

    .column-right{
      float: right;
      width: 40%;
      border-left: 1px solid #b1b4b6; 
      padding-left: 20px;            
    }

    .column-left{
      float: left;
      width: 60%;
      border-right: 1px solid #b1b4b6; 
      padding-right: 20px;       
    }

    .content{
      padding-bottom: 20px;
      padding-top: 30px;      
    }

  </style>

</head>

<body>

<div class="content">   

    <div id="footer" class="footer">
      
      <div class="footer-left">
      	<span>UPN:  </span> <span>${yourAssessment.upn} </span>
        <span>School name: </span> <span >${yourAssessment.schoolName?html} </span><br/><br/><br/>
      </div>
      <div class="footer-right">Page <span id="pagenumber" /> of <span id="pagecount" /></div>
    </div>

    <p class="header">
      <span>UPN: </span> <span class="bolded" > ${yourAssessment.upn?html} </span>
      <span>School name: </span> <span class="bolded" >${yourAssessment.schoolName?html}</span><br/>     
      <span>Edited on: </span> <span class="bolded" >${yourAssessment.modifiedDate?html}</span>
      <span>by </span> <span class="bolded" >${ownerName?html}</span><br/>
    </p>

    <div class="icon">
      <img src="https://style.bristol.gov.uk/images/logo-bcc.png" />
    </div>
    
    <div class="assessment">
		<h1>Summary</h1>
		<p class="p_border">This document is a summary of the need statements you have selected, and the provisions chosen to meet those needs. 
			You can use this document to check the summary against the child or young person’s support plan, or with anyone else who can’t access the application, before you move onto the funding request for this assessment.  </p>
		<br/> 
	
	    <div class="column-left">
	      <h2>Selected needs</h2>   
	      <#list needStatements as needStatement> 
	        <#if needStatement?index == 0 || needStatements[needStatement?index - 1].subAreaId != needStatement.subAreaId>
	          <h3>${needStatement.areaLabel?html}</h3>    
	        </#if>                
	          
	        <#if needStatement?index == 0 || needStatements[needStatement?index - 1].subAreaId != needStatement.subAreaId>
	          <h3>${needStatement.subAreaLabel?html}</h3>                  
	        </#if>
	        
	        <#if needStatement?index == 0 || needStatements[needStatement?index - 1].subCategoryId != needStatement.subCategoryId>
	          <div class="need_statement">
	            <h4>${needStatement.subCategoryLabel?html}</h4>                   
	            ${needStatement.statementLabel?html}
	          </div>
	        </#if>
	       	                       
	      </#list>
	    </div>

	    <div class="column-right">       
		  <h2>Planned  provision</h2>
		  <#list provisions as provision>	   
		        <h3>${provision.provisionGroup?html}</h3> 
		        <h4>${provision.provisionTypeLabel?html}</h4>
		        <div class="provision_statement">                         		                   
		          ${provision.specificProvision?html}   
		        </div> 	     
		  </#list>        
	    </div>

    </div>  

</div>
   
</body>
</html>