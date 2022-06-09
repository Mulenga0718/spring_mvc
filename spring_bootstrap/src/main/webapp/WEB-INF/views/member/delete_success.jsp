<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<script>

	alert("회원삭제에 성공하였습니다. \n 회원리스트 페이지로 이동합니다.");
	
	if(window.opener)window.opener.location.reload(true);
	window.close();

</script>