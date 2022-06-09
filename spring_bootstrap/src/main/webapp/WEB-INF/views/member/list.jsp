<%@page import="com.jsp.command.PageMaker"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.jsp.dto.MemberVO"%>
<%@page import="java.util.List"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>


	<div>
		<!-- Main content -->
<section class="content-header">
	<div class="container-fluid">
		<div class="row md-2">
			<div class="col-sm-6">
				<h1>회원목록</h1>
			</div>
			<div class="col-sm-6">
				<ol class="breadcrumb float-sm-right">
					<li class="breadcrumb-item"><a href="list.do"> <i
							class="fa fa-dashboard"></i>회원관리
					</a></li>
					<li class="breadcrumb-item active">목록</li>
				</ol>
			</div>
		</div>
	</div>
</section>

<section class="content">
	<div class="card">
		<div class="card-header with-border">
			<button type="button" class="btn btn-primary" onclick="OpenWindow('registForm.do','회원등록',800,800)">회원등록</button>
			<div id="keyword" class="card-tools" style="width: 550px;">
				<div class="input-group row">
					<!-- search bar -->
					<!-- sort num -->
					<select class="form-control col-md-3" name="perPageNum"
						id="perPageNum" onchange="list_go(1)">
						<c:set var="perpagenum" value="${pageMaker.getCri().getPerPageNum()}"/>
						
						<option value="10" ${perpagenum ==10? "selected" : "" }>정렬개수</option>
						<option value="2" ${perpagenum ==2? "selected" : "" }>2개씩</option>
						<option value="3" ${perpagenum ==3? "selected" : "" }>3개씩</option>
					 	<option value="5" ${perpagenum ==5? "selected" : "" }>5개씩</option>
					</select>

					<!-- search bar -->
					<select class="form-control col-md-3" name="searchType"
						id="searchType">
						<option value="" >검색구분</option>
						<option value="i" ${param.searchType =='i'?"selected":"" }>아이디</option>
						<option value="n" ${param.searchType =='n'?"selected":"" }>이 름</option>
						<option value="p" ${param.searchType =='p'?"selected":"" }>전화번호</option>
						<option value="e" ${param.searchType =='e'?"selected":"" }>이메일</option>
					</select>
					<!-- keyword -->
					<input class="form-control" type="text" name="keyword"
						placeholder="검색어를 입력하세요." value="" /> <span
						class="input-group-append">
						<button class="btn btn-primary" type="button" id="searchBtn"
							data-card-widget="search" onclick="list_go(-1)">
							<i class="fa fa-fw fa-search"></i>
						</button>
					</span>
					<!-- end : search bar -->
				</div>
			</div>
		</div>
		<div class="card-body" style="text-align: center; height: 76vh;">
			<div class="row">
				<div class="col-sm-12">
					<table class="table table-bordered">
						<tr>
							<th>사진</th>
							<th>아이디</th>
							<th>패스워드</th>
							<th>이 름</th>
							<th>이메일</th>
							<th>전화번호</th>
							<th>등록날짜</th>
							<!-- yyyy-MM-dd  -->
						</tr>
							<c:choose>
						<c:when test="${not empty memberList}">
						
						<c:forEach items="${memberList}"  var ="member">
						<tr>
							<td onclick="OpenWindow('detail.do?id=${member.id}', '회원상세', 700,800)" style="cursor: pointer; margin:0px; padding:0px;">
							<span class="manPicture" data_id="${member.id }" style="display:block;width:40px;height:40px;margin:0 auto;"></span>
							</td>
							<td>${member.id}</td>
							<td>${member.pwd}</td>
							<td>${member.name}</td>
							<td>${member.email}</td>
							<td>${member.phone}</td>
							<td><fmt:formatDate value="${member.regdate}"
									pattern="yyyy-MM-dd" /></td>
						
						</tr>
						</c:forEach>
						</c:when>
							<c:otherwise>
					
							
						
						<tr>
							<td colspan='7' class="text-center">해당내용이 없습니다.</td>
						</tr>
							</c:otherwise>
						</c:choose>
						

					</table>
				</div>
				<!-- col-sm-12 -->
			</div>
			<!-- row -->
		</div>
		<!-- card-body -->
		<div class="card-footer">
			<!-- pagination -->
			<%@ include file= "/WEB-INF/views/common/pagination.jsp"%>
			
			</div>
		</div>
	</section>
</div>

		
		<script type="text/javascript">
		window.onload = function(){
			MemberPictureThumb("<%=request.getContextPath()%>");
		}
		 

		</script>
		<%@ include file="/WEB-INF/include/footer.jsp" %>
		
		<!-- Control Sidebar -->
		<aside class="control-sidebar control-sidebar-dark">
			<!-- Control sidebar content goes here -->
			<div class="p-3">
				<h5>Title</h5>
				<p>Sidebar content</p>
			</div>
		</aside>
		<!-- /.control-sidebar -->

		<!-- Main Footer -->
		<footer class="main-footer">
			<!-- To the right -->
			<div class="float-right d-none d-sm-inline">Anything you want</div>
			<!-- Default to the left -->
			<strong>Copyright &copy; 2014-2021 <a
				href="https://adminlte.io">AdminLTE.io</a>.
			</strong> All rights reserved.
		</footer>
	</div>