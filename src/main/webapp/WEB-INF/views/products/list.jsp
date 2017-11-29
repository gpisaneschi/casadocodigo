<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Listagem de Produto</title>
</head>
<body>
	<table>
		<c:forEach items="${products}" var="product">
			<tr>
				<td>Título: ${product.title}</td>
				<td>Descrição: ${product.description}</td>
				<td>Valores: 
					<c:forEach items="${product.prices}" var="price" >
						[${price.value} - ${price.bookType}]
					</c:forEach>
				</td>
				<td>
					<c:url value="/products/${product.id}" var="linkDetalhar"></c:url>
					<a href="${linkDetalhar}">
						Detalhar
					</a>
				</td>
			</tr>
		</c:forEach>
	</table>
</body>
</html>