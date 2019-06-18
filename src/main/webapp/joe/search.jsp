<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<jsp:useBean id="NewsAricleDAO" class="kr.co.search.db.NewsArticleDAO" scope="page"></jsp:useBean>
<meta charset="UTF-8">
<title>Insert title here</title>
 <!-- 폰트 -->
<link href="https://fonts.googleapis.com/css?family=Cute+Font|Do+Hyeon|Jua|Noto+Sans+KR|Stylish" rel="stylesheet">

<!--제이쿼리-->
<script type="text/javascript" src="/searchService/resources/JS/jquery.min.js"></script>
<!-- <!--부트 스트랩 자바스크립트--> 
<!-- <script type="text/javascript" src="/search/resources/JS/bootstrap.bundle.js"></script> -->
<!-- <script type="text/javascript" src="/search/resources/JS/bootstrap.bundle.min.js"></script> -->
<!-- <script type="text/javascript" src="/search/resources/JS/bootstrap.js"></script> -->
<!-- <script type="text/javascript" src="/search/resources/JS/bootstrap.min.js"></script> -->

<!-- <!--부트 스트랩 CSS--> 
<!-- <link rel="stylesheet" type="text/css" href="/search/resources/CSS/bootstrap-grid.css" /> -->
<!-- <link rel="stylesheet" type="text/css" href="/search/resources/CSS/bootstrap-grid.min.css" /> -->
<!-- <link rel="stylesheet" type="text/css" href="/search/resources/CSS/bootstrap-reboot.css" /> -->
<!-- <link rel="stylesheet" type="text/css" href="/search/resources/CSS/bootstrap-reboot.min.css" /> -->
<!-- <link rel="stylesheet" type="text/css" href="/search/resources/CSS/bootstrap.css" /> -->
<!-- <link rel="stylesheet" type="text/css" href="/search/resources/CSS/bootstrap.min.css" /> -->

<script type="text/javascript">
// 	function searchButton() {
		 
// 		if ($("#searchWord").val() == "") {
// 		} else {
// 			$.ajax({
// 				url : "search?search=" + $("#searchWord").val() ,
// 				Type : "POST",
// 				success : function (result) {
// 					$("#navbarCollapse").empty();
// 					$("#navbarCollapse").append(result);
// 				}
// 			})
// 		}
// 	}
	
	
// 	function enter() {
// 		if (event.keyCode == 13) {
// 			if ($("#searchWord").val() == "") {
// 				alert("check");
// 			}else{
// 				alert("check2");
// 				$.ajax({
// 					url : "search?search=" + $("#searchWord").val() ,
// 					Type : "POST",
// 					success : function (result) {
// 						$("#navbarCollapse").empty();
// 						$("#navbarCollapse").append(result);
// 					}
// 				})
// 			}
// 		}
// 	}


window.onload=function(){
    // 페이지가 로딩된 후 실행해야 되는 코드를 추가한다.
var today = new Date();

 var mm = today.getMonth()+1;
 var yy = today.getDate();
 var hhhh = today.getFullYear()

	
	$("#year").val(hhhh).prop("selected", true);
	$("#month").val(mm).prop("selected", true);
	$("#day").val(yy).prop("selected", true);
	$("#yearSecond").val(hhhh).prop("selected", true);
	$("#monthSecond").val(mm).prop("selected", true);
	$("#daySecond").val(yy).prop("selected", true);
};

function allSeleteButtonMethod() {
	$("input[name=newspaper]").prop("checked",true);
}

function allCancelButtonMethod() {
	$("input[name=newspaper]").prop("checked",false);
}

function search() {
	
	var myArray = new Array();
	var temp =  document.getElementsByName("newspaper");
	
	if (parseInt($("#year").val()) == parseInt($("#yearSecond").val())) {
		if (parseInt($("#month").val()) == parseInt($("#monthSecond").val())) {
			if (parseInt($("#day").val()) <= parseInt($("#daySecond").val())) {
				if ( $("input[name='newspaper']").is(":checked")){  
					
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
				
				
			}else{
				alert("'일' 형식이 잘못되었습니다.");
			}
		}else if(parseInt($("#month").val()) > parseInt($("#monthSecond").val())){
				alert("'월' 형식이 잘못되었습니다.");
		}else{
				if ( $("input[name='newspaper']").is(":checked")){  
					
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
	}else if (parseInt($("#year").val()) < parseInt($("#yearSecond").val())) {
		if ( $("input[name='newspaper']").is(":checked")){  
			
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
		
	}else{
		alert("'년' 형식이 잘못되었습니다.");
		
	}
		
		
		
	
	
	
	 
}
</script>
</head>

<body>
<div style="text-align: center;">
	<img src="../resources/image/1.jpg">º
</div>

<div class="position-relative overflow-hidden p-3 p-md-5 m-md-3 text-center bg-light">
  <div class="col-md-5 p-lg-5 mx-auto my-5" style="text-align: center;">
   <form name = "formName" class="form-inline mt-2 mt-md-0" method="get">
   		<input type="hidden" value="null" name = "page">
		<input class="form-control mr-sm-2" type="text" id ="search" name = "search" placeholder="기사를 검색하세요" aria-label="Search"  style="width: 400px; height: 30px;">
	
		<br>
		<h3>선택검색</h3>
	<input type="checkbox" id = "title" name="title" value="title">제목	
	<input type="checkbox"  id = "content" name="content" value="content" checked>내용

		<h3>신문사선택</h3>
		<%  ArrayList<String> list = NewsAricleDAO.newspaperSelect(); 
		for(int i = 0; i <list.size(); i++){
		%>
		
	<input type="checkbox" name="newspaper" checked="checked" value="<%=list.get(i)%>" style="font-size: xx-small;"><%= list.get(i) %>
		
			<% }%>
	<input type="button" id = "allSeleteButton" value = "모두선택" onclick="allSeleteButtonMethod();">
	<input type="button" id = "allCancelButton" value = "모두취소" onclick="allCancelButtonMethod();">
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
<div class="collapse navbar-collapse" id="navbarCollapse" style="text-align: center;">
	
</div>
</body>
</html>