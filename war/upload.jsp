<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include.html" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreServiceFactory" %>
<%@ page import="com.google.appengine.api.blobstore.BlobstoreService" %>

<body>

<!--  ============[アップロード画面]============ -->

<%
//アップロード先URLの取得
BlobstoreService blobstoreService =
			BlobstoreServiceFactory.getBlobstoreService();
String uploadUrl = blobstoreService.createUploadUrl("/upload");
%>

<div data-role="page" id="uploadPage">

	<!-- ヘッダーエリア -->
	<div data-role="header">
		<a data-icon="arrow-l" href="photo.jsp">戻る </a>
		<h1>登録</h1>
	</div>

	<!-- コンテンツエリア -->
	<div data-role="content" class="middleText">
		<form data-ajax="false" action="<%=uploadUrl %>"
		method="post" enctype="multipart/form-data"
		id="uploadForm" >
			タイトル<br />
			<input type="text" name="title" id="title"><br />
			説明<br />
			<textarea cols="40" rows="8" name="comment"
			id="comment"></textarea>
			<br />
			<input type="file"  accept="image/*"
			capture="filesystem" name="photo" id="fileInput">
			<br />
			<input type="button" value="アップロード"
			id="uploadButton">
		</form>
	</div>

</div>

</body>
</html>