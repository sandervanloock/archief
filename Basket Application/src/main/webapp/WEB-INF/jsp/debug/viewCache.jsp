<%@ page session="false" contentType="text/html; charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
	<title>Cache statistics</title>
</head>
<body>

	<h3>Caching overview: application</h3>
	<form method="get">
		<c:if test="${flushed}">
			<div style="color: green">All caches have been flushed. <a href="?key=resto">refresh</a></div>
		</c:if>
		<c:if test="${not flushed}">
			<input type="submit" value="Flush all caches" />
		</c:if>
		<input type="hidden" name="key" value="resto" />
		<input type="hidden" name="flush" value="true" />
	</form>

	<div>
		<table border="1" cellpadding="4">
			<tr>
				<th>Application</th>
				<th>Group</th>
				<th>Items</th>
			</tr>
		</table>
	</div>

	<h4>${empty caches ? 'Object caching disabled' : 'Object cache'}</h4>
	<c:forEach var="cache" items="${caches}">

		<div style="float: left; display: block; clear: none; margin-right: 20px; text-align: center;">
			<strong>${cache.name}</strong> <a href="?key=resto&clearCacheName=${cache.name}">clear</a>
			<table border="1" cellpadding="3">
				<%--<tr><td>Items</td><td>${cache.size}</td></tr>--%>
				<%--<c:set var="stats" value="${cache.statistics}" />--%>
				<%--<c:set var="hits" value="${stats.cacheHits}" />--%>
				<%--<c:set var="misses" value="${stats.cacheMisses}" />--%>
				<%--<tr><td>Hits</td><td>${hits}</td></tr>--%>
				<%--<tr><td>Misses</td><td>${stats.cacheMisses}</td></tr>--%>
				<%--<tr><td>Ratio</td><td><fmt:formatNumber value="${( ( hits * 1.0 / ( hits + misses ) ) * 100 )}"--%>
				                                        <%--maxFractionDigits="2" minFractionDigits="2" /> %</td></tr>--%>
				<%--<tr><td>Evictions</td><td>${stats.evictionCount}</td></tr>--%>
				<%--<tr><td>Avg get time</td><td><fmt:formatNumber value="${stats.averageGetTime}"--%>
				                                        <%--maxFractionDigits="2" minFractionDigits="2" /> ms</td></tr>--%>
			</table>
		</div>
	</c:forEach>

</body>
</html>