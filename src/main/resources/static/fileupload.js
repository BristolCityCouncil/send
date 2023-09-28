
function deleteUploadedFile(assessmentId, fileId) {     
    const params = jQuery.param({
       assessmentId: assessmentId,
       fileId: fileId
   });
     jQuery.post("deleteFile", 
     params,     
       function() {
       location.reload();
     });
 }
   
   
 function uploadDocument(){
   var formData = new FormData();
   const uploadInput = document.getElementById("filePicker"); 
    
   //The file must be stored both in the formData and passed separately in the ajax request for the spring controller to find the correct method
   var file = uploadInput.files[0];
   formData.append('file', uploadInput.files[0]);
   var supportDocType = $("#supportTypeDropdown").val(); 
   formData.append("supportDocType", supportDocType);
   var assessmentId = $("#assessmentId").val()
   formData.append("assessmentId", assessmentId);
   showLoadingOverlay();
 
   $.ajax({
     url: 'uploadSupportDocument',
     method: 'POST',
     type: "POST",
     enctype: 'multipart/form-data',
     data: formData,
     file: file,
     accept: "application/json",
     processData: false,
     contentType: false,
     cache: false,
     timeout: 600000,
     success: function (jsonResponse) {
       location.reload();
     },
     error: function (jsonResponse) {
       showUploadError(jsonResponse);
     }
   });
 
   // Hide the file picker and reset the sub type, until the next sub type has been selected.
   $("#filePickerNoFile").css("display","none");
   $("#filePickerButton").css("display","none"); 
   $('#supportTypeDropdown').val("");  
 }
 
 function subTypeChanged(){
   // Show the file picker if the user has picked a document sub type
   if($("#supportTypeDropdown").val() !== ""){
     $("#filePickerButton").css("display","inline");
     $("#filePickerNoFile").css("display","inline");
   }
 }
 
 function showUploadError(jsonResponse){   
   // Check for 'truthy' value, i.e not a null, blank or undefined value. This check required in case user has a previous error, then cancels file upload. 
   if(jsonResponse.responseJSON.errorMessage){
        clearFileUploadError();
        $('#errorFileName').text(jsonResponse.responseJSON.uploadDocumentType); 
        $('#errorFileReason').text(jsonResponse.responseJSON.errorMessage);
        $("#uploadFailed").css("display","block"); 
   } 
 }
 
 function clearFileUploadError(){
   $('#errorFileName').text(""); 
   $('#errorFileReason').text("");
   $("#uploadFailed").css("display","none"); 
 }

 function showLoadingOverlay() {  
  let loading = document.getElementById("loading-overlay"); // Assign loading overlay to variable
  loading.style.display = "block"; // Show the loading overlay  
  loading.setAttribute("aria-busy", "true");  
}	
	