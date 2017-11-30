
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>

<!DOCTYPE html>
<html>
<head>

	<link rel="icon" href="//cdn.shopify.com/s/files/1/0155/7645/t/177/assets/favicon.ico?11981592617154272979" type="image/ico" />
	<link href="https://plus.googlecom/108540024862647200608" rel="publisher"/>
	<link href="${contextPath}resources/css/cssbase-min.css" rel="stylesheet" type="text/css" media="all" />
	<link href='http://fonts.googleapis.com/css?family=Droid+Sans:400,700' rel='stylesheet'/>
	<link href="${contextPath}resources/css/fonts.css" rel="stylesheet" type="text/css" media="all" />
	<link href="${contextPath}resources/css/fontello-ie7.css" rel="stylesheet" type="text/css" media="all" />
	<link href="${contextPath}resources/css/fontello-embedded.css" rel="stylesheet" type="text/css" media="all" />
	<link href="${contextPath}resources/css/fontello.css" rel="stylesheet" type="text/css" media="all" />
	<link href="${contextPath}resources/css/style.css" rel="stylesheet" type="text/css" media="all" />
	<link href="${contextPath}resources/css/layout-colors.css" rel="stylesheet" type="text/css" media="all" />
	<link href="${contextPath}resources/css/responsive-style.css" rel="stylesheet" type="text/css" media="all" />
	<link href="${contextPath}resources/css/guia-do-programador-style.css" rel="stylesheet" type="text/css"  media="all"  />
  	<link href="${contextPath}resources/css/produtos.css" rel="stylesheet" type="text/css"  media="all"  />
	<link rel="canonical" href="http://www.casadocodigo.com.br/" />	


	<meta charset="UTF-8">
	<title >Listagem de Produto</title>
</head>


<body >
	<sec:authorize access="isAuthenticated()">
		<sec:authentication property="principal" var="user"/>
		<div>Olá ${user.name}</div>
	</sec:authorize>
	<br />
	<br />
	<sec:authorize access="hasRole('ROLE_ADMIN')">
		<c:url value="/products/form" var="formLink" />
		<a href="${formLink}">
			Cadastrar novo produto
		</a>
	</sec:authorize>
	<table id="cart-table">
		<c:forEach items="${products}" var="product">
			<tr>
				<td class="item-title">Título: ${product.title}</td>
				<td class="book-description">Descrição: ${product.description}</td>
				<td class="numeric-cell">Valores: 
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