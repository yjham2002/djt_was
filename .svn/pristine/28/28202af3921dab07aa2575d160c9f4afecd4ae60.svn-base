<div id="jFileUploadArea<?= $fileIndex ?>">
	<img id="btnFileUpload<?= $fileIndex ?>" src="/admin/inc/imgUpload/bt_attach.gif" alt="Attach a file"/>
	<input type="hidden" id="uploaded_files<?= $fileIndex ?>" name="uploaded_<?= $fileName ?>" NOT_NULL=""  value="<?=$filePath?>" title="Upload File"/>
	<input type="hidden" id="fileNumber<?= $fileIndex ?>" name="fileNumber_<?= $fileName ?>" NOT_NULL=""  value="<?=$fileNumber?>" title="Upload File"/>
	<input type="file" id="files<?= $fileIndex ?>" name="<?= $fileName ?>" NOT_NULL="" title="Upload File" style="display:none;"/>
	

	<span id="divPreviewFile<?= $fileIndex ?>" style="display:none;">
		<div id="priviewFileScale<?= $fileIndex ?>" style="position:absolute; background-image:url('/admin/inc/imgUpload/btn_x.gif'); width:300px; height:300px; background-repeat:no-repeat; background-size:contain; background-position:center center; display:none;"></div>
		<img class="imgSample" src="" width="25" height="21" id="previewFile<?= $fileIndex ?>"/>
		<img onclick="javascript:previewFileDelete('<?= $fileIndex ?>');" src="/admin/inc/imgUpload/btn_x.gif" style="cursor:hand;" id="previewDelete<?= $fileIndex ?>" />
	</span>

	<span id="divFileResult<?= $fileIndex ?>" class="validation4"></span>
</div>

<script type="text/javascript">

    $(document).ready(function() {
        var index = '<?= $fileIndex ?>';
        var filePath = '<?= $filePath ?>';

		previewFileBind(index, "filePath", filePath);

    });
    
</script>