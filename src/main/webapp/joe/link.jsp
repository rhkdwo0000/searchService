<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:useBean id="NewsAricleDAO" class="kr.co.search.db.NewsArticleDAO" scope="page"></jsp:useBean>
<jsp:useBean id="NewsAricleDTO" class="kr.co.search.db.NewsArticleDTO" scope="page"></jsp:useBean>

<%
	String articleID = request.getParameter("id");
NewsAricleDTO =  NewsAricleDAO.selectId(articleID);

System.out.println(articleID);



System.out.println(NewsAricleDTO.getTitle());
System.out.println(NewsAricleDTO.getCrawlingUrl());
System.out.println(NewsAricleDTO.getReleaseDate());
System.out.println(NewsAricleDTO.getImageUrl());
System.out.println(NewsAricleDTO.getContent());

%>

<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<table style="border: 1px solid #444444;  margin-left: auto; margin-right: auto;">
		<tr style="border: 1px solid #444444; width: 150px; height:50px;">
			<td style="border: 1px solid #444444; width: 550px; height:50px;"><%=NewsAricleDTO.getTitle() %></td>
			<td style="border: 1px solid #444444; width: 200px; height:50px;"><a href ='<%=NewsAricleDTO.getCrawlingUrl() %>'>본문링크</a></td>
		</tr>
		<tr style="border: 1px solid #444444;">
			<td colspan='2' style="border: 1px solid #444444; width: 750px; height:50px;"><%=NewsAricleDTO.getReleaseDate() %></td>
		</tr>
		<tr style="border: 1px solid #444444;">
			<%if(NewsAricleDTO.getImageUrl()!=null) {%>
			<td colspan='2' style="border: 1px solid #444444; width: 750px; height:250px;"><img src='<%=NewsAricleDTO.getImageUrl() %>'width="300px" height="300px">  </td>
			<%}else{ %>
			<td colspan='2' style="border: 1px solid #444444; width: 750px; height:250px;"></td>
			<%} %>
		</tr>
		<tr style="border: 1px solid #444444;">
			<td colspan='2' style="border: 1px solid #444444; width: 750px; height:250px;"><%=NewsAricleDTO.getContent() %></td>
		</tr>
		<tr style="border: 1px solid #444444;">
			<td colspan='2' style="border: 1px solid #444444; width: 750px; height:50px;"><%=NewsAricleDTO.getNewspaper() %></td>
		</tr>

	</table>

</body>
</html>