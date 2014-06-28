<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include.html" %>

<body>

<!--  ============[写真明細画面]============ -->

<%
//写真データの受け取り
String title = (String)request.getAttribute("title");
String comment = (String)request.getAttribute("comment");
String imageStr = (String)request.getAttribute("imageStr");
%>

<div data-role="page" id="photoPage">

	<!-- ヘッダーエリア -->
	<div data-role="header">
		<a data-icon="arrow-l" href="/list">戻る </a>
		<h1><%=title %></h1>
		<a data-icon="home" href="/photo.jsp">トップ</a>
	</div>

	<!-- コンテンツエリア -->
	<div data-role="content" >
		<p><%=comment %></p>
		<img src="data:image/jpg;base64,<%=imageStr %>" />
	</div>

</div>

</body>
</html>