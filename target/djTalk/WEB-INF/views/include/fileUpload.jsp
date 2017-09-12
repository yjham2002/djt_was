

<div id="jFileUploadArea${param.fileIndex }">
	<img id="btnFileUpload${param.fileIndex }" src="${properties.resources_path}/fileUpload/bt_attach.gif" alt="Attach a file" /> 
	
	<input type="hidden" id="uploaded_files${ param.fileIndex}" name="uploaded_${ param.fileName }" value="${ param.filePath }" title="Upload File" /> 
	<input type="file" id="files${param.fileIndex }"	name="${ param.fileName }" title="Upload File" style="display: none;" /> 
	
	<span id="divPreviewFile${param.fileIndex }" style="">
		<div id="priviewFileScale${param.fileIndex }" style="position: absolute; background-image: url('${param.includePath}/origin/${param.filePath }'); width: 300px; height: 200px; background-repeat: no-repeat; background-size: contain; background-position: center center; display: none;"></div>
		<img class="imgSample" src="" width="25" height="21"	id="previewFile${param.fileIndex }" /> 
		<img onclick="javascript:previewFileDelete('${param.fileIndex }');" src="${properties.resources_path}/fileUpload/btn_x.gif" style="cursor: hand;" id="previewDelete${param.fileIndex }" />
	</span> 
	<span id="divFileResult${param.fileIndex }" class="validation4"></span>
</div>


<script type="text/javascript">

    $(document).ready(function() {
        var index = '${param.fileIndex }';
        var filePath = '${param.filePath }';

        
		previewFileBind(index, "filePath", filePath);

    });
    
</script>