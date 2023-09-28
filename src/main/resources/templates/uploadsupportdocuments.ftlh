<#import "/spring.ftl" as spring/>
<#include "./includes/header.ftlh">
<script type="text/javascript">
    $(document).ready(function() {
        const uploadInput = document.getElementById("filePickerButton");
        uploadInput.addEventListener('change', uploadDocument, false);
    });
</script>
  <div class="row-fluid">
    <div class="span12">

	<#-- overlay for uploads -->
	<div id="loading-overlay" aria-live="polite" aria-label="Uploading file. Please wait." style="display: none;">
	  	<div></div>  <!-- these empty divs improve the appearance of the loading overlay -->
 	 	<div></div>
 	 	<div></div>
  		<div></div>
  		<div></div>
		<div></div>
	</div>  <!-- close overlay div --> 

    
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
	                	<@spring.message "breadcrumb.uploadsupportdocuments.label"/>
	                </li>
                         
          			<span id="upn">UPN: ${assessment.upn}</span>  		 
            	</ul>	
            </nav>               		           			       	          	        
        </div>
      <#-- Breadcrumb End -->
      
      <h1 class="padded" id="pageTitleId"><@spring.message "uploadsupportdocuments.title"/></h1>
          
	  <div class="slate slate--outline">
	    <div class="slate__title" id="slateTitleId">
	        <@spring.message "uploadsupportdocuments.action.title1"/>		
	    </div>
	   
	    <div class="slate__copy">
	    	<p>
	            <@spring.message "uploadsupportdocuments.action.info1"/>
	        </p>
	        <p>
	            <@spring.message "uploadsupportdocuments.action.info2"/>
	        </p>
	       
	        <ul>		       
	       		 <li class="uploadTypes">Support plan (mandatory)</li>
	       		 <li class="uploadTypes">Whole school provision map (mandatory)</li>		       		
	       		 <li class="uploadTypes">Funding application request (mandatory)</li>
	       		 <li class="uploadTypes">Educational Psychologist report</li>
	       		 <li class="uploadTypes">Speech and language therapy report</li>
	       		 <li class="uploadTypes">Occupational therapy report</li>
	       		 <li class="uploadTypes">Paediatrician’s report</li>
	       		 <li class="uploadTypes">CAMHS report/involvement</li>		       		
	       		 <li class="uploadTypes">GP report/involvement</li>
	       		 <li class="uploadTypes">Other</li>	        	
			</ul>
	       
	    </div>
	  </div>
     
     <h2><@spring.message "uploadsupportdocuments.subtitle"/></h2>    
     
     <h3 class="docupload__heading"><@spring.message "file.upload.heading"/></h3>
     <span class="docupload__subheading"><@spring.message "file.upload.subheading"/></span>
     
   
     <div class="section-break--non-visible" > </div>
     	
	<#-- Render the uploaded files -->
		
	<#-- The body of the upload fields -->
	<div class="docupload__body docupload__field_body docupload__mode">
		<#if uploadedFiles?? >
			<#list uploadedFiles as uploadedFile>
				<div class="docupload__file docupload__file--uploaded docupload__file--success" id="uploadSuccess_${uploadedFile?index + 1}">
					<div class="docupload__filename" id="successFileName_${uploadedFile?index + 1}">
						${uploadedFile.documentTypeName}
						<span class="filesize" id="successFileSize_${uploadedFile?index + 1}">${uploadedFile.fileSize}</span>
						<span id="successFileContentType_${uploadedFile?index + 1}">${uploadedFile.fileContentType}</span>
					</div>
																
					<div class="docupload__actions">
						<div class="docupload__filestatus" id="fileStatus_${uploadedFile?index + 1}">Uploaded&nbsp;<span class="icon icon--bcc icon--bcc--tick"></span></div>
						<div  class="docupload__fileremove" id="fileremoveLink_${uploadedFile.id}">					
							<a href="javascript:void(0)" onclick="javascript:deleteUploadedFile('${assessment.id}','${uploadedFile.id}')" id="file-remove-href_${uploadedFile.id}">Remove</a>
						</div>
					</div>
				</div>
			</#list>
		</#if>

		<#-- display errors at end of list, above the file upload -->
		<div class="docupload__file docupload__file--uploaded docupload__file--error" id="uploadFailed" style="display:none;">
			<div class="docupload__filename" id="errorFileName" style="display:inline;"></div>
							
			<div class="docupload__actions">
				<div class="docupload__filestatus" id="errorFileReason" style="display:inline;"></div>
				<a href="javascript:void(0)" onclick="javascript:clearFileUploadError()">Dismiss</a>
			</div>
		</div>

	</div>		
	
	<input type="hidden" id="assessmentId" name="assessmentId" value="${assessment.id}">	
	<div class="docupload__field_body docupload__mode_advanced"> <!-- start display fileupload parent div -->
	<div class="docupload__file"  style="border-top: 0px;">	
		<label for="fileUploadsSubtypes"><@spring.message "file.upload.subtypes.label"/></label>
		<div class="drop--send" style="display:inline;">  	
			<select name="subType" id="supportTypeDropdown" class="fileUploadSelect" onchange="subTypeChanged()" style="height: 43px; background: white;">
			 	<option disabled selected value>Please select</option>
			 	<#list subtypes  as key, value>
        			<option value="${key}">${value}</option>
	    		</#list>
			</select>			
		</div> 

		<!-- error div -->
		<div class="docupload__field__choose__file" style="display: inline;">
	 		<label for="filePicker" id="filePickerButton" class="cta docupload__uploadbutton" style="margin-top:2px; display: none" >Choose File<input type='file' id='filePicker' name="uploadedFile" style="display: none"/></label>
			<span id="filePickerNoFile" style="display: none;">No file selected</span>
		</div>
	</div>	
	</div> <!-- close display fileupload parent div -->
		
    <div class="button-holder">
    	<a class="send-button" id="backButton" href="${pathPrefix}/overview?assessmentId=${assessment.id}"><button class="newcta" type="button">Back</button></a>     		 
    </div>
     
    </div>  
  </div>
<#include "./includes/footer.ftlh">