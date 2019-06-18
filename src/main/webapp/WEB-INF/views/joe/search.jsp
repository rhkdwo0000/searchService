<%@page import="java.util.ArrayList"%>
<%@page import="kr.co.search.db.NewsArticleDTO2"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
<jsp:useBean id="NewsAricleDAO" class="kr.co.search.db.NewsArticleDAO" scope="page"></jsp:useBean>

<!--제이쿼리-->
<script type="text/javascript" src="/searchService/resources/JS/jquery.min.js"></script>

<style type="text/css">

.paging .hide {display:block;height:0;width:0;font-size:0;line-height:0;margin:0;padding:0;overflow:hidden;}

.paging{padding:19px;text-align:center;}

.paging a{display:inline-block;width:23px;height:23px;padding-top:2px;vertical-align:middle;}

.paging a:hover{text-decoration:underline;}

.paging .btn_arr{text-decoration:none;}

.paging .btn_arr, .paging .on{margin:0 3px;padding-top:0;border:1px solid #ddd; border-radius:30px;

/* background:url(/front/img/com/btn_paging.png) no-repeat; */}

.paging .on{padding-top:1px;height:22px;color:#fff;font-weight:bold;background:#000;}

.paging .on:hover{text-decoration:none;}

</style>

<% 
/* 검색한 뉴스기사 리스트 */
ArrayList<NewsArticleDTO2> newsList = (ArrayList<NewsArticleDTO2>)session.getAttribute("list");
int tempPage = 0;



System.out.println(newsList.size());

if(request.getAttribute("page") == null || request.getAttribute("page").equals("null") || Integer.valueOf((String)request.getAttribute("page")) > (newsList.size()/10)+1){
	tempPage =1 ;
}else{
	tempPage = Integer.valueOf((String)request.getAttribute("page"));
}


String mostFirst = "<<";
String mostEnd = ">>";


String text = (String)request.getAttribute("text");
String content = (String)request.getAttribute("content");
String title = (String)request.getAttribute("title");
String year = (String)request.getAttribute("year");
String month = (String)request.getAttribute("month");
String day = (String)request.getAttribute("day");
String yearSecond = (String)request.getAttribute("yearSecond");
String monthSecond = (String)request.getAttribute("monthSecond");
String daySecond = (String)request.getAttribute("daySecond");

ArrayList<String> newspaper = (ArrayList)request.getAttribute("newspaper");

pageContext.setAttribute("tempPage", tempPage);
%>



<script type="text/javascript">
/* 페이지 시작시 우선실행 함수 */
window.onload=function(){
	/*신문사 선택유지*/
	var myArray = new Array();
	/*선택 신문사 리스트*/
	var js_newspaper = document.getElementsByName("newspaper");
	
	<%
	for (int i = 0; i < newspaper.size(); i++) {%>
	myArray.push('<%=newspaper.get(i) %>');

	<%}%>
	  
	/*선택 신문사 체크유지*/
	for (var i = 0; i < js_newspaper.length; i++) {
		for (var j = 0; j < myArray.length; j++) {
			if (js_newspaper[i].value == myArray[j]) {
				js_newspaper[i].setAttribute("checked", "true");
			}
		}
	}
	
	 	/*지정 날짜 유지*/
		$("#year").val("<%=year %>").prop("selected", true);
		$("#month").val("<%=month %>").prop("selected", true);
		$("#day").val("<%=day %>").prop("selected", true);
		$("#yearSecond").val("<%=yearSecond %>").prop("selected", true);
		$("#monthSecond").val("<%=monthSecond %>").prop("selected", true);
		$("#daySecond").val("<%=daySecond %>").prop("selected", true);
};

/* 검색 버튼 눌렀을때 */
function search() {
	var myArray = new Array();
	var temp =  document.getElementsByName("newspaper");
	
	
	if ( $('input[name="newspaper"]').is(":checked")){    
		
		
	
	 if (document.getElementById("search").value.trim() == "") {
		alert("검색어를 입력하세요");
	 }else{
		 
	if ($("#content").prop("checked") == true || $("#title").prop("checked") == true) {
		
		var formName = document.formName;
		
		for (var i = 0; i < temp.length; i++) {
			
			if (temp[i].getAttribute("checked") != "checked") {
				myArray.push(temp[i].getAttribute("value"));
			}

		}
			formName.action = "search?search="
					+ document.getElementById("search").value
					+ "&content="+ document.getElementById("content").value 
					+ "&title="+ document.getElementById("title").getAttribute("checked")
					+ "&year=" + document.getElementById("year").value 
					+ "&month=" + document.getElementById("month").value 
					+ "&day=" + document.getElementById("day").value
					+ "&yearSecond=" + document.getElementById("yearSecond").value
					+ "&monthSecond="+ document.getElementById("monthSecond").value 
					+ "&daySecond=" + document.getElementById("daySecond").value 
					+ "&newspaper=" + myArray
					+ "&page=null";
			formName.submit();

		} else {
			alert("제목or내용은 필수체크사항입니다");
		}
	}
	
		
	}else{
		alert("신문사를 한개이상 체크하세요");
	}
	 
}

/* 모든신문사 전체 선택 */
function allSeleteButtonMethod() {
	$("input[name=newspaper]").prop("checked",true);
}

/* 모든신문사 전체 취소 */
function allCancelButtonMethod() {
	$("input[name=newspaper]").prop("checked",false);
	
}
</script>
</head>
<body>

<div class="position-relative overflow-hidden p-3 p-md-5 m-md-3 text-center bg-light">
  <div class="col-md-5 p-lg-5 mx-auto my-5" style="text-align: center;">
   <form name ="formName" class="form-inline mt-2 mt-md-0"  method="get">
   <input type="hidden" value="null" name = "page">
         <% 
         if(text != null){%>
		<input class="form-control mr-sm-2" type="text" id ="search" name = "search" value = "<%=text%>"placeholder="기사를 검색하세요" aria-label="Search"  style="width: 400px; height: 30px;">
        <%  }%>
		
		<h3>선택검색</h3>
		<% 
         if(title != null){%>
	<input type="checkbox" id = "title"name="title" value="title" checked="checked">제목	
	<%  }else{%>
	<input type="checkbox" id = "title"name="title" value="title" >제목	
	<% }%>
	 
        <% if(content != null){%>
	<input type="checkbox" id = "content"name="content" value="content" checked="checked">내용
	<%  }else{%>
	<input type="checkbox" id = "content"name="content" value="content" >내용
	
	<% }%>
		<h3>신문사선택</h3>
		
	<%  ArrayList<String> list = NewsAricleDAO.newspaperSelect(); 
		for(int i = 0; i <list.size(); i++){
		%>
	<input type="checkbox" name="newspaper"  value="<%=list.get(i)%>" style="font-size: xx-small;"><%= list.get(i) %>
		
			<% }%>
			
	<input type="button" id = "allSeleteButton" value = "모두선택" onclick="allSeleteButtonMethod()">
	<input type="button" id = "allCancelButton" value = "모두취소" onclick="allCancelButtonMethod()">
			<h3>날짜선택</h3>
			<select id = "year" name ="year">
				<option value ="2019">2019</option>
				<option value ="2018">2018</option>
				<option value ="2017">2017</option>
				<option value ="2016">2016</option>
				<option value ="2015">2015</option>
				<option value ="2014">2014</option>
				<option value ="2013">2013</option>
				<option value ="2012">2012</option>
				<option value ="2011">2011</option>
				<option value ="2010">2010</option>
			</select>
			<select id = "month" name ="month">
				<option value ="1">1</option>
				<option value ="2">2</option>
				<option value ="3">3</option>
				<option value ="4">4</option>
				<option value ="5">5</option>
				<option value ="6">6</option>
				<option value ="7">7</option>
				<option value ="8">8</option>
				<option value ="9">9</option>
				<option value ="10">10</option>
				<option value ="11">11</option>
				<option value ="12">12</option>
			</select>
			<select id = "day" name ="day">
				<option value = "1">1</option>
				<option value = "2">2</option>
				<option value = "3">3</option>
				<option value = "4">4</option>
				<option value = "5">5</option>
				<option value = "6">6</option>
				<option value = "7">7</option>
				<option value = "8">8</option>
				<option value = "9">9</option>
				<option value = "10">10</option>
				<option value = "11">11</option>
				<option value = "12">12</option>
				<option value = "13">13</option>
				<option value = "14">14</option>
				<option value = "15">15</option>
				<option value = "16">16</option>
				<option value = "17">17</option>
				<option value = "18">18</option>
				<option value = "19">19</option>
				<option value = "20">20</option>
				<option value = "21">21</option>
				<option value = "22">22</option>
				<option value = "23">23</option>
				<option value = "24">24</option>
				<option value = "25">25</option>
				<option value = "26">26</option>
				<option value = "27">27</option>
				<option value = "28">28</option>
				<option value = "29">29</option>
				<option value = "30">30</option>
				<option value = "31">31</option>
			</select>
			<label>~</label>
			<select id = "yearSecond" name ="yearSecond">
				<option value ="2019">2019</option>
				<option value ="2018">2018</option>
				<option value ="2017">2017</option>
				<option value ="2016">2016</option>
				<option value ="2015">2015</option>
				<option value ="2014">2014</option>
				<option value ="2013">2013</option>
				<option value ="2012">2012</option>
				<option value ="2011">2011</option>
				<option value ="2010">2010</option>
			</select>
			<select id = "monthSecond" name ="monthSecond">
				<option value ="1">1</option>
				<option value ="2">2</option>
				<option value ="3">3</option>
				<option value ="4">4</option>
				<option value ="5">5</option>
				<option value ="6">6</option>
				<option value ="7">7</option>
				<option value ="8">8</option>
				<option value ="9">9</option>
				<option value ="10">10</option>
				<option value ="11">11</option>
				<option value ="12">12</option>
			</select>
			<select id = "daySecond" name ="daySecond">
				<option value = "1">1</option>
				<option value = "2">2</option>
				<option value = "3">3</option>
				<option value = "4">4</option>
				<option value = "5">5</option>
				<option value = "6">6</option>
				<option value = "7">7</option>
				<option value = "8">8</option>
				<option value = "9">9</option>
				<option value = "10">10</option>
				<option value = "11">11</option>
				<option value = "12">12</option>
				<option value = "13">13</option>
				<option value = "14">14</option>
				<option value = "15">15</option>
				<option value = "16">16</option>
				<option value = "17">17</option>
				<option value = "18">18</option>
				<option value = "19">19</option>
				<option value = "20">20</option>
				<option value = "21">21</option>
				<option value = "22">22</option>
				<option value = "23">23</option>
				<option value = "24">24</option>
				<option value = "25">25</option>
				<option value = "26">26</option>
				<option value = "27">27</option>
				<option value = "28">28</option>
				<option value = "29">29</option>
				<option value = "30">30</option>
				<option value = "31">31</option>
			</select>
	</form>
	<br><br>
	<button class="btn btn-outline-success my-2 my-sm-0"  onclick="search()" style="width: 130px; height: 40px;">검색</button>
  </div>
  <div class="product-device shadow-sm d-none d-md-block"></div>
  <div class="product-device product-device-2 shadow-sm d-none d-md-block"></div>
</div>

<br>
<br>  

<div>
	<c:set var="dto" value="${dto}" />
	<c:set var="total" value="${total}" />
	<table style="border: 1px solid #444444; width: 200px; height: 100px;margin-left: auto; margin-right: auto;">
		<tr style="border: 1px solid #444444; width: 250px; height: 20px;">
			<td style="border: 1px solid #444444; width: 250px; height: 20px;"><h5>전체데이터</h5></td>
		</tr>
		<tr style="border: 1px solid #444444; width: 250px; height: 20px;">
			<td style="border: 1px solid #444444; width: 250px; height: 20px;"><h5>검색시간 : ${dto.timeTaken}초</h5></td>
		</tr>
		<tr style="border: 1px solid #444444; width: 250px; height: 20px;">
			<td style="border: 1px solid #444444; width: 250px; height: 20px;"><h5>검색결과 : ${total}개</h5></td>
		</tr>
	</table>
	
	<c:set var="if_list" value="${list}" />
	<c:choose>   
	<c:when test="${fn:length(list) ne 0}"><!--넘어온 리스트길이가 0이랑 다르면  -->
	
	<c:choose>
	<c:when test="${tempPage eq 0 or tempPage eq 1}"><!--페이지가 0이거나 1일경우  -->
	
	<c:choose>
	<c:when test="${fn:length(list) le 10}"><!-- 리스트길이가 12보다 작거나 같을경우 -->
	
		<c:forEach items="${list}" var="list">
		
<%-- 			<c:choose> --%>
<%-- 					<c:when test="${empty if_list.image_url}"> --%>
					
<!-- 						<table style="border: 1px solid #444444; margin-left: auto; margin-right: auto;"> -->
<!-- 								<tr style="border: 1px solid #444444;"> -->
<%-- 									<td rowspan='3' style="border: 1px solid #444444; width: 150px;"><h5>SCORE : ${list.score}</h5></td> --%>
<%-- 									<td rowspan='3' style="border: 1px solid #444444; width: 150px;"><a href = "/search/joe/link.jsp?id=${list.news_article_id}"><img src="${pageContext.request.contextPath}/resources/image/1.jpg" style="width: 110px; height: 80px; "/></a></td> --%>
<%-- 									<td style="border: 1px solid #444444; width: 450px;"><a href = "/search/joe/link.jsp?id=${list.news_article_id}"><h3>${list.title}</h3></a></td> --%>
<!-- 								</tr> -->
<!-- 								<tr> -->
<%-- 									<td style="border: 1px solid #444444; width: 450px"><h5>${list.content}</h5></td> --%>
<!-- 								</tr> -->
<!-- 								<tr> -->
<%-- 									<td style="border: 1px solid #444444; width: 450px"><h5>${list.newspaper}</h5><h5>${list.release_date}</h5></td> --%>
<!-- 								</tr>  -->
								
<!-- 						</table>		 -->
<%-- 					</c:when> --%>
					
<%-- 					<c:otherwise> --%>
							<table style="border: 1px solid #444444; margin-left: auto; margin-right: auto;">
								<tr style="border: 1px solid #444444;">
									<td rowspan='3' style="border: 1px solid #444444; width: 150px;"><h5>SCORE : ${list.score}</h5></td>
									<td rowspan='3' style="border: 1px solid #444444; width: 150px;"><a href = "/searchService/joe/link.jsp?id=${list.news_article_id}"><img src="${list.image_url}" style="width: 110px; height: 80px; "/></a></td>
									<td style="border: 1px solid #444444; width: 450px"><a href = "/searchService/joe/link.jsp?id=${list.news_article_id}"><h3>${list.title}</h3></a></td>
								</tr>
								<tr>
									<td style="border: 1px solid #444444; width: 450px"><h5>${list.content}</h5></td>
								</tr>
								<tr>
									<td style="border: 1px solid #444444; width: 450px"><h5>${list.newspaper}</h5><h5>${list.release_date}</h5></td>
								</tr> 
								
						</table>	
<%-- 					</c:otherwise> --%>
<%-- 			</c:choose> --%>
			
		</c:forEach>
		</c:when>
		
			<c:when test="${fn:length(list) gt 10}"><!--리스트길이가 12보다 클경우  -->
			
			<c:forEach begin="0" end="9" items="${list}" var="list">
<%-- 			<c:choose> --%>
<%-- 					<c:when test="${empty if_list.image_url}"> --%>
					
<!-- 						<table style="border: 1px solid #444444; margin-left: auto; margin-right: auto;"> -->
<!-- 								<tr style="border: 1px solid #444444;"> -->
<%-- 									<td rowspan='3' style="border: 1px solid #444444; width: 150px;"><h5>SCORE : ${list.score}</h5></td> --%>
<%-- 									<td rowspan='3' style="border: 1px solid #444444; width: 150px;"><a href = "/search/joe/link.jsp?id=${list.news_article_id}"><img src="${pageContext.request.contextPath}/resources/image/1.jpg" style="width: 110px; height: 80px; "/></a></td> --%>
<%-- 									<td style="border: 1px solid #444444; width: 450px;"><a href = "/search/joe/link.jsp?id=${list.news_article_id}"><h3>${list.title}</h3></a></td> --%>
<!-- 								</tr> -->
<!-- 								<tr> -->
<%-- 									<td style="border: 1px solid #444444; width: 450px"><h5>${list.content}</h5></td> --%>
<!-- 								</tr> -->
<!-- 								<tr> -->
<%-- 									<td style="border: 1px solid #444444; width: 450px"><h5>${list.newspaper}</h5><h5>${list.release_date}</h5></td> --%>
<!-- 								</tr>  -->
								
<!-- 						</table>		 -->
<%-- 					</c:when> --%>
					
<%-- 					<c:otherwise> --%>
							<table style="border: 1px solid #444444; margin-left: auto; margin-right: auto;">
								<tr style="border: 1px solid #444444;">
									<td rowspan='3' style="border: 1px solid #444444; width: 150px;"><h5>SCORE : ${list.score}</h5></td>
									<td rowspan='3' style="border: 1px solid #444444; width: 150px;"><a href = "/searchService/joe/link.jsp?id=${list.news_article_id}"><img src="${list.image_url}" style="width: 110px; height: 80px; "/></a></td>
									<td style="border: 1px solid #444444; width: 450px"><a href = "/searchService/joe/link.jsp?id=${list.news_article_id}"><h3>${list.title}</h3></a></td>
								</tr>
								<tr>
									<td style="border: 1px solid #444444; width: 450px"><h5>${list.content}</h5></td>
								</tr>
								<tr>
									<td style="border: 1px solid #444444; width: 450px"><h5>${list.newspaper}</h5><h5>${list.release_date}</h5></td>
								</tr> 
								
						</table>	
<%-- 					</c:otherwise> --%>
<%-- 				</c:choose> --%>
			
			</c:forEach>
			</c:when>
			
			</c:choose>
		</c:when>
		
		<c:when test="${tempPage gt 1}"><!-- 페이지가 1보다 클경우 -->
		 <c:choose>
     		 <c:when test="${tempPage le fn:length(list)/10}"> <!-- 페이지가 리스트사이즈를 12로 나눈 값보다 작거나 같을경우 -->
				<c:forEach begin="${((tempPage-1)*10)}" end="${tempPage*10-1}" items="${list}" var="list">
<%-- 			<c:choose> --%>
<%-- 					<c:when test="${empty if_list.image_url}"> --%>
					
<!-- 						<table style="border: 1px solid #444444; margin-left: auto; margin-right: auto;"> -->
<!-- 								<tr style="border: 1px solid #444444;"> -->
<%-- 									<td rowspan='3' style="border: 1px solid #444444; width: 150px;"><h5>SCORE : ${list.score}</h5></td> --%>
<%-- 									<td rowspan='3' style="border: 1px solid #444444; width: 150px;"><a href = "/search/joe/link.jsp?id=${list.news_article_id}"><img src="${pageContext.request.contextPath}/resources/image/1.jpg" style="width: 110px; height: 80px; "/></a></td> --%>
<%-- 									<td style="border: 1px solid #444444; width: 450px;"><a href = "/search/joe/link.jsp?id=${list.news_article_id}"><h3>${list.title}</h3></a></td> --%>
<!-- 								</tr> -->
<!-- 								<tr> -->
<%-- 									<td style="border: 1px solid #444444; width: 450px"><h5>${list.content}</h5></td> --%>
<!-- 								</tr> -->
<!-- 								<tr> -->
<%-- 									<td style="border: 1px solid #444444; width: 450px"><h5>${list.newspaper}</h5><h5>${list.release_date}</h5></td> --%>
<!-- 								</tr>  -->
								
<!-- 						</table>		 -->
<%-- 					</c:when> --%>
					
<%-- 					<c:otherwise> --%>
							<table style="border: 1px solid #444444; margin-left: auto; margin-right: auto;">
								<tr style="border: 1px solid #444444;">
									<td rowspan='3' style="border: 1px solid #444444; width: 150px;"><h5>SCORE : ${list.score}</h5></td>
									<td rowspan='3' style="border: 1px solid #444444; width: 150px;"><a href = "/searchService/joe/link.jsp?id=${list.news_article_id}"><img src="${list.image_url}" style="width: 110px; height: 80px; "/></a></td>
									<td style="border: 1px solid #444444; width: 450px"><a href = "/searchService/joe/link.jsp?id=${list.news_article_id}"><h3>${list.title}</h3></a></td>
								</tr>
								<tr>
									<td style="border: 1px solid #444444; width: 450px"><h5>${list.content}</h5></td>
								</tr>
								<tr>
									<td style="border: 1px solid #444444; width: 450px"><h5>${list.newspaper}</h5><h5>${list.release_date}</h5></td>
								</tr> 
								
						</table>	
<%-- 					</c:otherwise> --%>
<%-- 			</c:choose> --%>
				</c:forEach>
				</c:when>
					<c:when test="${tempPage gt fn:length(list)/10  && fn:length(list)%10 gt 0 }"><!-- 페이지가 리스트길이를 10로 나누값보다 크고 리스트사이즈를 12로나눈 나머지가 0보다 클경우  -->
          			  <c:forEach begin="${(tempPage-1)*10 }"  items="${list}" var="list">
<%-- 			<c:choose> --%>
<%-- 					<c:when test="${empty if_list.image_url}"> --%>
					
<!-- 						<table style="border: 1px solid #444444; margin-left: auto; margin-right: auto;"> -->
<!-- 								<tr style="border: 1px solid #444444;"> -->
<%-- 									<td rowspan='3' style="border: 1px solid #444444; width: 150px;"><h5>SCORE : ${list.score}</h5></td> --%>
<%-- 									<td rowspan='3' style="border: 1px solid #444444; width: 150px;"><a href = "/search/joe/link.jsp?id=${list.news_article_id}"><img src="${pageContext.request.contextPath}/resources/image/1.jpg" style="width: 110px; height: 80px; "/></a></td> --%>
<%-- 									<td style="border: 1px solid #444444; width: 450px;"><a href = "/search/joe/link.jsp?id=${list.news_article_id}"><h3>${list.title}</h3></a></td> --%>
<!-- 								</tr> -->
<!-- 								<tr> -->
<%-- 									<td style="border: 1px solid #444444; width: 450px"><h5>${list.content}</h5></td> --%>
<!-- 								</tr> -->
<!-- 								<tr> -->
<%-- 									<td style="border: 1px solid #444444; width: 450px"><h5>${list.newspaper}</h5><h5>${list.release_date}</h5></td> --%>
<!-- 								</tr>  -->
								
<!-- 						</table>		 -->
<%-- 					</c:when> --%>
					
<%-- 					<c:otherwise> --%>
							<table style="border: 1px solid #444444; margin-left: auto; margin-right: auto;">
								<tr style="border: 1px solid #444444;">
									<td rowspan='3' style="border: 1px solid #444444; width: 150px;"><h5>SCORE : ${list.score}</h5></td>
									<td rowspan='3' style="border: 1px solid #444444; width: 150px;"><a href = "/searchService/joe/link.jsp?id=${list.news_article_id}"><img src="${list.image_url}" style="width: 110px; height: 80px; "/></a></td>
									<td style="border: 1px solid #444444; width: 450px"><a href = "/searchService/joe/link.jsp?id=${list.news_article_id}"><h3>${list.title}</h3></a></td>
								</tr>
								<tr>
									<td style="border: 1px solid #444444; width: 450px"><h5>${list.content}</h5></td>
								</tr>
								<tr>
									<td style="border: 1px solid #444444; width: 450px"><h5>${list.newspaper}</h5><h5>${list.release_date}</h5></td>
								</tr> 
								
						</table>	
<%-- 					</c:otherwise> --%>
<%-- 			</c:choose> --%>
				</c:forEach>
				</c:when>
				
				
				</c:choose>
				</c:when>
				</c:choose>
		</c:when>
		</c:choose>
</div>

<div class="paging">
  <a href="#" class="btn_arr prev" id ="fourB" ><%=mostFirst %></a>     
  <a href="#" id ="firstB">1</a><!-- D : 활성화페이지일 경우 : on 처리 -->
  <a href="#" id ="secondB">2</a>
  <a href="#" id ="thirdB">3</a>
  <a href="#" class="btn_arr next" id ="fiveE" ><%=mostEnd %></a>            
</div>
 
 
</body>
<script type="text/javascript">


$(function() {/* tempPage에 넘어오는 값과 list의 길이에 따른 페이지 처리 */
   var firstBtn = document.getElementById("firstB");
   var secondBtn = document.getElementById("secondB");
   var thirdBtn = document.getElementById("thirdB");
   var fourBtn = document.getElementById("fourB");
   var fiveBtn = document.getElementById("fiveE");
   var lastPage;
   <%if (newsList.size() == 0) {
	   
			} else {%>

	page = <%=tempPage%>

	//console.log("0");
	secondBtn.innerHTML = page; 
	secondBtn.setAttribute("class", "on");
	if (page==1) {
 	  console.log("1");
 	  firstBtn.innerHTML = "";
 	 fourBtn.innerHTML = "";
 	fourBtn.setAttribute("href","#");
 	fourBtn.setAttribute("onclick","fowardMoveCancel(event)");
 	
 	
	
	if ((<%=newsList.size()/10%> > 0 && <%=newsList.size()%10%> > 0)|| (<%=newsList.size()/10%> > 1 && <%=newsList.size()%10%> >= 0)) {
		if (<%=newsList.size()/10%> > 0 && <%=newsList.size()%10%> > 0) {
		lastPage = <%=(newsList.size()/10)+1%>
		}else if(<%=newsList.size()/10%> > 1 && <%=newsList.size()%10%> >= 0){
			lastPage = <%=(newsList.size()/10)%>
		}

		fiveBtn.setAttribute("href", "paging?page=" +lastPage+ "&content=" + encodeURI('<%=content%>') + "&search=" + encodeURI('<%=text%>') + "&title=" +encodeURI('<%=title%>')+
  	 			"&year=" +'<%=year%>'+ "&month=" +'<%=month%>'+ "&day=" +'<%=day%>'+ "&yearSecond=" +'<%=yearSecond%>'+ "&monthSecond=" +'<%=monthSecond%>'+ "&daySecond=" +'<%=daySecond%>'+ "&newspaper=" +encodeURI(myArray))
	}
 	
 	
 	
 	
	}else{
	//    console.log("2");
  	 firstBtn.innerHTML = page-1;
  	var myArray = new Array();
  		 
  <%	for (int i = 0; i < newspaper.size(); i++) {%>
  		myArray.push('<%=newspaper.get(i)%>')
	<%}%>
  	
  	 	firstBtn.setAttribute("href","paging?page=" +'<%=(tempPage - 1)%>' + "&content=" + encodeURI('<%=content%>') + "&search=" + encodeURI('<%=text%>') + "&title=" +encodeURI('<%=title%>')+
  	 			"&year=" +'<%=year%>'+ "&month=" +'<%=month%>'+ "&day=" +'<%=day%>'+ "&yearSecond=" +'<%=yearSecond%>'+ "&monthSecond=" +'<%=monthSecond%>'+ "&daySecond=" +'<%=daySecond%>'+ "&newspaper=" +encodeURI(myArray));
  	 	
  	 	
  		fourBtn.setAttribute("href", "paging?page=" +"null" + "&content=" + encodeURI('<%=content%>') + "&search=" + encodeURI('<%=text%>') + "&title=" +encodeURI('<%=title%>')+
  	 			"&year=" +'<%=year%>'+ "&month=" +'<%=month%>'+ "&day=" +'<%=day%>'+ "&yearSecond=" +'<%=yearSecond%>'+ "&monthSecond=" +'<%=monthSecond%>'+ "&daySecond=" +'<%=daySecond%>'+ "&newspaper=" +encodeURI(myArray))
	
	
	if ((<%=newsList.size()/10%> > 0 && <%=newsList.size()%10%> > 0)|| (<%=newsList.size()/10%> > 1 && <%=newsList.size()%10%> >= 0)) {
		if (<%=newsList.size()/10%> > 0 && <%=newsList.size()%10%> > 0) {
		lastPage = <%=(newsList.size()/10)+1%>
		}else if(<%=newsList.size()/10%> > 1 && <%=newsList.size()%10%> >= 0){
			lastPage = <%=(newsList.size()/10)%>
		}

		fiveBtn.setAttribute("href", "paging?page=" +lastPage+ "&content=" + encodeURI('<%=content%>') + "&search=" + encodeURI('<%=text%>') + "&title=" +encodeURI('<%=title%>')+
  	 			"&year=" +'<%=year%>'+ "&month=" +'<%=month%>'+ "&day=" +'<%=day%>'+ "&yearSecond=" +'<%=yearSecond%>'+ "&monthSecond=" +'<%=monthSecond%>'+ "&daySecond=" +'<%=daySecond%>'+ "&newspaper=" +encodeURI(myArray))
	}
	
	
	}
	
	

	
	
		if (page < <%=newsList.size() / 10%> + <%=newsList.size() % 10%> && page <= <%=newsList.size() / 10%>){
	// console.log("3");
   		thirdBtn.innerHTML = page+1;
   		var myArray = new Array();
 		 
   	  <%	for (int i = 0; i < newspaper.size(); i++) {%>
   	  		myArray.push('<%=newspaper.get(i)%>')
   		<%}%>
   		thirdBtn.setAttribute("href","paging?page=" +'<%=(tempPage + 1)%>' + "&content=" + encodeURI('<%=content%>') + "&search=" + encodeURI('<%=text%>') + "&title=" +encodeURI('<%=title%>')+
  	 			"&year=" +'<%=year%>'+ "&month=" +'<%=month%>'+ "&day=" +'<%=day%>'+ "&yearSecond=" +'<%=yearSecond%>'+ "&monthSecond=" +'<%=monthSecond%>'+ "&daySecond=" +'<%=daySecond%>'+ "&newspaper=" +encodeURI(myArray));
   		if ((<%=newsList.size()/10%> > 0 && <%=newsList.size()%10%> > 0)|| (<%=newsList.size()/10%> > 1 && <%=newsList.size()%10%> >= 0)) {
   			if (<%=newsList.size()/10%> > 0 && <%=newsList.size()%10%> > 0) {
   			lastPage = <%=(newsList.size()/10)+1%>
   			}else if(<%=newsList.size()/10%> > 1 && <%=newsList.size()%10%> >= 0){
   				lastPage = <%=(newsList.size()/10)%>
   			}

   			fiveBtn.setAttribute("href", "paging?page=" +lastPage+ "&content=" + encodeURI('<%=content%>') + "&search=" + encodeURI('<%=text%>') + "&title=" +encodeURI('<%=title%>')+
   	  	 			"&year=" +'<%=year%>'+ "&month=" +'<%=month%>'+ "&day=" +'<%=day%>'+ "&yearSecond=" +'<%=yearSecond%>'+ "&monthSecond=" +'<%=monthSecond%>'+ "&daySecond=" +'<%=daySecond%>'+ "&newspaper=" +encodeURI(myArray))
   		}
   		
   		
		} else {
			console.log("4");
			thirdBtn.innerHTML = "";
			fiveBtn.innerHTML = "";
			fiveBtn.setAttribute("href","#");
			fiveBtn.setAttribute("onclick","gaurdMoveCancel(event)");
		}
<%}%>
   
   
   
	});

/* 맨처음이동 클릭시 상단으로 이동방지(맨앞으로 이동이 활성화가 안되었을때만) */
function fowardMoveCancel(event) {

	event.preventDefault();

	//event

	}
/* 맨뒤이동 클릭시 상단으로 이동방지(맨뒤로 이동이 활성화가 안되었을때만)  */
function gaurdMoveCancel(event) {

	event.preventDefault();

	//event

	}
	

	
	
	
	
	

</script>
</html>




