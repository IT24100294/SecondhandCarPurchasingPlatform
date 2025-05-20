<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Error Page</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .error-container {
            text-align: center;
            padding: 50px;
            margin-top: 100px;
        }
        .error-code {
            font-size: 72px;
            color: #dc3545;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="error-container">
            <div class="error-code">
                ${pageContext.response.status}
            </div>
            <h2>Oops! Something went wrong</h2>
            <p class="lead">
                <c:choose>
                    <c:when test="${pageContext.response.status == 404}">
                        The page you're looking for doesn't exist.
                    </c:when>
                    <c:otherwise>
                        An unexpected error occurred. Please try again later.
                    </c:otherwise>
                </c:choose>
            </p>
            <a href="${pageContext.request.contextPath}/" class="btn btn-primary">Go to Home</a>
        </div>
    </div>
</body>
</html> 