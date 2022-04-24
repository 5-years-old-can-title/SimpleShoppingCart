<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	Hello，目前購物車內的物品
	${ myCart }
	
	
	<form action="<%=request.getContextPath()%>/ShoppingCartServlet" method="post">
		<label>鉛筆: <input type="number" name="pencil"></label><br>
		<label>原子筆: <input type="number" name="pen"></label><br>
		<label>橡皮擦: <input type="number" name="eraser"></label><br>
		<button type="submit">加入購物車</button>
	</form>
	
	<p style="color: red;">${ errMsg }</p>
	
	
</body>
</html>