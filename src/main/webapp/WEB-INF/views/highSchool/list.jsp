<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>고등학교 목록</title>
<style>
    /* 링크처럼 보이도록 커서 변경 */
    .school-name-link {
        cursor: pointer;
        color: blue;
        text-decoration: underline;
    }
</style>
</head>
<body>
    <h1>고등학교 목록</h1>
    <table border="1">
        <thead>
            <tr>
                <th>ID</th>
                <th>이름</th>
                <th>주소</th>
                <th>전화번호</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="school" items="${highSchools}">
                <tr>
                    <td>${school.hsId}</td>
                    <td>
                        <%-- 학교명 클릭 시 상세 페이지로 이동하는 링크 추가 --%>
                        <a href="${pageContext.request.contextPath}/test/highDetail?hsId=${school.hsId}" class="school-name-link">
                            ${school.hsName}
                        </a>
                    </td>
                    <td>${school.hsAddr}</td>
                    <td>${school.hsTel}</td>
                </tr>
            </c:forEach>
            <c:if test="${empty highSchools}">
                <tr>
                    <td colspan="4">등록된 고등학교 정보가 없습니다.</td>
                </tr>
            </c:if>
        </tbody>
    </table>
</body>
</html>