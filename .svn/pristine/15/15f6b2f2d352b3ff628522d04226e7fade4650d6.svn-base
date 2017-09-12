function previewFileDelete(index) {
    $('#divPreviewFile' + index).hide();
	$('#previewFile' + index).attr('src', '');

	if ($.browser.msie) {
		// ie 일때 input[type=file] init.
		$('#files' + index).replaceWith( $('#files' + index).clone(true) );
		$('#uploaded_files' + index).val("");
		$('#fileNumber' + index).val("0");
		
	} else {
		// other browser 일때 input[type=file] init.
		$('#files' + index).val("");
		$('#uploaded_files' + index).val("");
		$('#fileNumber' + index).val("0");
	}
}

function initFileUpload(index)
{

	$("#btnFileUpload" + index).css("cursor", "pointer").click(function(){
		$("#files" + index).trigger("click");
	});

	$("#files" + index).change(function(){
		if (this.files && this.files[0]) {
			var reader = new FileReader();

			reader.onload = function (e) {
				previewFileBind(index, "object", e.target.result);
			}

			reader.readAsDataURL(this.files[0]);
		}
	});
}

function destoryFileUpload(index)
{
	$("#btnFileUpload" + index).unbind("click");
	$("#files" + index).unbind("change");
	$("#jFileUploadArea" + index).remove();
	
}

function previewFileBind(index, dataType, data) {

	if(dataType == "filePath")
	{
		var ext = data.substring(data.length -3, data.length).toLowerCase();
		if (ext && /^(jpg|png|jpeg|gif)$/.test(ext)) {
//			$('#previewFile' + index).attr('src', "" + data);
			$('#previewFile' + index).attr('src', "http://192.168.0.81:8205/100/" + data);
			$('#priviewFileScale' + index).css('background-image', "" + data + ")");
			$('#uploaded_files' + index).val(data);
			$('#divPreviewFile' + index).show();
			setScaleViewEvent(index);
		}
	}
	else
	{
		if (data.indexOf("image/") > -1) {
			$('#priviewFileScale' + index).css('background-image', "url(" + data + ")");
			$('#previewFile' + index).attr('src', data);	    
			$('#divPreviewFile' + index).show();
			setScaleViewEvent(index);
		}
	}
}


function setScaleViewEvent(index)
{
	$('#previewFile' + index).unbind("hover");
	$('#previewFile' + index).hover(
		function()
		{
			// var left = event.clientX + 10;
			// var top = event.clientY;
			var top = $(this).offset().top - ($('#priviewFileScale' + index).height() / 2);
			var left = $(this).offset().left + $(this).width() - 300;
			$('#priviewFileScale' + index)
				.css("top", top)
				.css("left", left)
				.show();
		},
		function()
		{
			$('#priviewFileScale' + index).hide();
		}
	);

}
