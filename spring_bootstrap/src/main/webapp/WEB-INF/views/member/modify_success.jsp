<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script>
	alert("회원정보 수정에 성공했습니다. \n 상세페이지로 이동합니다.");
	
	location.href="<%=request.getContextPath()%>/member/detail.do?id=${id}";
	window.opener.location.reload(true);
</script>