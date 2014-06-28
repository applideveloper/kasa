<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/include.html" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>

<body>

<!--  ============[メニュー画面]============ -->

<%
//ユーザー名の取得
UserService userService = UserServiceFactory.getUserService();
User user = userService.getCurrentUser();

//ログアウトリンクの作成
String logoutStr = "";
if(user != null) {
	logoutStr = user.getEmail() +
		"<a href=" +
		userService.createLogoutURL(request.getRequestURI()) +
		"> ログアウト</a>";
}
%>

<div data-role="page" id="menuPage">

	<!-- ヘッダーエリア -->
	<div data-role="header">
		<h1>メニュー</h1>
	</div>

	<!-- コンテンツエリア -->
	<div data-role="content">
		<p class="smallText"><%=logoutStr %></p>
		<p>
			<a data-role="button" href="/upload.jsp">写真を登録</a>
		</p>
		<p>
			<a data-role="button" href="/list">写真を見る</a>
		</p>
	</div>

</div>

</body>
</html>