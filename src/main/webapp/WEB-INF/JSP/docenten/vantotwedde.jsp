<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c'%>
<%@taglib uri='http://java.sun.com/jsp/jstl/fmt' prefix='fmt'%>
<%@taglib uri='http://vdab.be/tags' prefix='v'%>
<!doctype html>

<html lang='nl'>
	<head>
		<v:head title='Docenten van tot wedde'/>

		<style>
			td:first-child, td:last-child
			{
				text-align:right;
			}
		</style>
	</head>

	<body>
		<v:menu/>

		<h1>Docenten van tot wedde</h1>

		<form>
			<label>
				Van:
				<span>${fouten.van}</span>
				<input name='van' value='${param.van}' type='number' min='0' step='0.01' required autofocus>
			</label>

			<label>
				Tot:
				<span>${fouten.tot}</span>
				<input name='tot' type='number' value='${param.tot}' min='0' step='0.01'required>
			</label>
			
			<input type='submit' value='Zoeken'>
		</form>

		<c:if test="${not empty param and empty fouten and empty docenten}">
			Geen docenten gevonden
		</c:if>

		<c:if test="${not empty docenten}">
			<table>
				<thead>
					<tr>
						<th>Nummer</th><th>Naam</th><th>Wedde</th>
					</tr>
				</thead>
	
				<tbody>
					<c:forEach items='${docenten}' var='docent'>
						<tr>
							<td>${docent.id}</td>
							<td>${docent.naam}</td>
							<td><fmt:formatNumber value='${docent.wedde}' minFractionDigits='2' maxFractionDigits='2'/></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</c:if>
	</body>
</html>
