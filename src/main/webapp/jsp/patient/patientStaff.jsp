<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename='message'/>

<html>
<head>
    <title><fmt:message key="message.patient.staff"/></title>
    <link rel="icon" href="../../resources/image/favicon.ico" type="image/ico">
    <link rel="stylesheet" href="<c:url value="../../resources/style/style.css"/>" type="text/css"/>
    <link rel="stylesheet" href="<c:url value="../../resources/style/style1.css"/>" type="text/css">
</head>
<body>
<div class="container">
    <header>
        <jsp:include page="../header.jsp"/>
        <div class="linkRef">
            <a href="${pageContext.request.contextPath}/controller/?command=logout"><fmt:message
                    key="message.logout"/></a>
        </div>
    </header>
</div>
<div class="container1">
    <nav>
        <form name="goToProfile" method="GET" action="${pageContext.request.contextPath}/controller/">
            <input type="hidden" name="command" value="redirect"/>
            <input type="hidden" name="redirectTo" value="patientProfile"/>
            <input type="submit" name="goToProfile" value="<fmt:message key='message.patient.profile'/>"/>
        </form>

        <form name="goToMyCard" method="GET" action="${pageContext.request.contextPath}/controller/">
            <input type="hidden" name="command" value="redirect"/>
            <input type="hidden" name="redirectTo" value="patientMainPage"/>
            <input type="submit" name="goToMyCard" value="<fmt:message key='message.patient.usercard'/>"/>
        </form>

        <form name="goToStaff" method="GET" action="${pageContext.request.contextPath}/controller/">
            <input type="hidden" name="command" value="redirect"/>
            <input type="hidden" name="redirectTo" value="patientStaff"/>
            <input type="submit" name="goToMyDoctors" value="<fmt:message key='message.patient.staff'/>"/>
        </form>
    </nav>

    <div>
        <h3><fmt:message key="message.patient.staff.contacts"/>:</h3>
        <c:choose>
            <c:when test="${not empty sessionScope.patientStaffList}">
                <table border="1px">
                    <tr>
                        <th><fmt:message key="message.patient.doctor"/></th>
                        <th><fmt:message key="message.profile.phone"/></th>
                        <th><fmt:message key="message.profile.email"/></th>
                    </tr>
                    <c:forEach items="${sessionScope.patientStaffList}" var="staff">
                        <tr>
                            <td>
                                    ${staff.name}
                                    ${staff.surname}
                            </td>
                            <td>
                                    ${staff.phone}
                            </td>
                            <td>
                                    ${staff.email}
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:when>
            <c:otherwise>
                <fmt:message key="message.patient.staff.contacts.empty"/>
            </c:otherwise>
        </c:choose>
    </div>

    <footer>
        <jsp:include page="../footer.jsp"/>
    </footer>
</div>

</body>
</html>
