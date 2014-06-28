<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include.html" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="jp.co.staffnet.html5.photo.PhotoData" %>

<body>

<!--  ============[写真リスト画面]============ -->

<%
//写真リストデータの受け取り
ArrayList<PhotoData> list =
	(ArrayList<PhotoData>)request.getAttribute("photoData");

//写真リスト表示HTMLの作成
StringBuilder buf = new StringBuilder();
for(PhotoData data : list){
	buf.append("<li><a id='");
	buf.append(data.id);
	buf.append("'>");
	buf.append("<img src='data:image/jpg;base64,");
	buf.append(data.thumnailStr);
	buf.append("'/><h3>");
	buf.append(data.title);
	buf.append("</h3><p>");
	buf.append(data.mailAddress);
	buf.append("<br/>");
	buf.append(data.dateStr);
	buf.append("</p></a></li>");
}
String listHtml = buf.toString();

//写真リストデータが無い場合の処理
if(listHtml.length() == 0){
	listHtml = "<h3>写真データがありません</h3>";
}
%>

<div data-role="page" id="listPage">

	<!-- ヘッダーエリア -->
	<div data-role="header">
		<a data-icon="arrow-l" href="/photo.jsp">戻る </a>
		<h1>写真リスト</h1>
	</div>

	<!-- コンテンツエリア -->
	<div data-role="content">
		<ul data-role="listview" id="photoList">
		<%=listHtml %>
		</ul>
	</div>

</div>

</body>
</html>