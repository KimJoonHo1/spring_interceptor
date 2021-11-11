<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="<%=request.getContextPath() %>/resources/js/daumpost.js"></script>
<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
<script>
	
	function register() {
		addr1 = $("#addr1").val();
      	addr2 = $("#addr2").val();
      	addr3 = $("#addr3").val();
      	addr = addr1 + "/" + addr2 + "/" + addr3;
      	$("#addr1").val(addr);
      	$("#fo").submit();
	}
    
</script>
</head>
<body>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

	<c:import url="../default/header.jsp" />
	<div align="center">
		<form action="register" method="post" id="fo">
			<table border="1">
				<tr>
					<td><input type="text" name="id" placeholder="아이디"><br>
						<input type="text" name="pw" placeholder="비밀번호"><br>
						<input type="text" id="addr1" name="addr" placeholder="우편번호" readonly>
						<input type="button" onclick="daumPost()" value="우편번호 찾기"><br/>
						<input type="text" id="addr2" placeholder="주 소" readonly><br/>
						<input type="text" id="addr3" placeholder="상 세 주 소"><br/>
						<hr> <input type="button" value="회원가입" onclick="register()">
					</td>
				</tr>
			</table>
		</form>
	</div>
	<c:import url="../default/footer.jsp" />
</body>
</html>