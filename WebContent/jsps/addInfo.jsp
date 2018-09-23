<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>添加信息</title>
	</head>
	<body>
		<h1>添加信息</h1>
		
		<form action="<c:url value='/StudentServlet?action=addInfo'/>" method="post">
			<table>
				<tr>
					<td>学生姓名：</td>
					<td><input type="text" name="studName" /></td>
				</tr>
				<tr>
					<td>书&emsp;&emsp;名1：</td>
					<td><input type="text" name="bookName" /></td>
				</tr>
				<tr>
					<td>价&emsp;&emsp;格：</td>
					<td><input type="text" name="bookPrice" /></td>
				</tr>
				<tr>
					<td>书&emsp;&emsp;名2：</td>
					<td><input type="text" name="bookName" /></td>
				</tr>
				<tr>
					<td>价&emsp;&emsp;格：</td>
					<td><input type="text" name="bookPrice" /></td>
				</tr>
				<tr>
					<td>书&emsp;&emsp;名3：</td>
					<td><input type="text" name="bookName" /></td>
				</tr>
				<tr>
					<td>价&emsp;&emsp;格：</td>
					<td><input type="text" name="bookPrice" /></td>
				</tr>
				
				<tr>
					<td></td>
					<td><input type="submit" value="添加"  /></td>
				</tr>
			</table>
		</form>
	</body>
</html>