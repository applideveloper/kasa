//============ページ区切り[アップロード画面]============
$(document).on("pageinit", "#uploadPage", function(){

	//アップロードボタン選択時の処理
	$("#uploadButton").on("click", function() {
		var title=$("#title").val();
		if(title.length === 0){
			alert("タイトルを入力してください");
			return;
		}

		var filename=$("#fileInput").val();
		if(filename.length === 0){
			alert("ファイルを選択してください");
			return;
		}
		$("#uploadForm").submit();
	});

});

//============ページ区切り[写真リスト画面]============
$(document).on("pageinit", "#listPage", function(){

	//リスト項目選択時の処理
	$(document).on("click","#photoList a",function(){
		var id = $(this).attr("id");
		location.href = "/detail?id=" + id;
	});

});

