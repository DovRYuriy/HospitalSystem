<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="dt" uri="/WEB-INF/date-format.tld" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename='message'/>

<html>
<head>
    <title><fmt:message key="message.patient.usercard"/></title>
    <link rel="icon" href="../../resources/image/favicon.ico" type="image/ico">
    <link rel="stylesheet" href="<c:url value="../../resources/style/style.css"/>" type="text/css">
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
    <div>
        <h1><fmt:message key="message.patient.hello"/>, ${sessionScope.user.name} ${sessionScope.user.surname}.</h1>
    </div>
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
        <h3><fmt:message key="message.patient.history"/>:</h3>
        <c:choose>
            <c:when test="${not empty sessionScope.patientInfo}">
                <table border="1px">
                    <tr>
                        <th><fmt:message key="message.patient.doctor"/></th>
                        <th><fmt:message key="message.patient.diagnosis"/></th>
                        <th><fmt:message key="message.prescription.drugs"/></th>
                        <th><fmt:message key="message.prescription.procedure"/></th>
                        <th><fmt:message key="message.prescription.operation"/></th>
                        <th><fmt:message key="message.patient.date"/></th>
                        <th><fmt:message key="message.patient.dischargedate"/></th>
                    </tr>
                    <c:forEach items="${sessionScope.patientInfo}" var="item">
                        <tr>
                            <td>
                                    ${item.doctor.name}<br/>
                                    ${item.doctor.surname}
                            </td>
                            <td>
                                    ${item.diagnosis.name}<br/>
                                    ${item.diagnosis.description}
                            </td>
                            <td>
                                    ${item.prescription.drugs}
                            </td>
                            <td>
                                    ${item.prescription.procedure}
                            </td>
                            <td>
                                    ${item.prescription.operation}
                            </td>
                            <td>
                                <dt:dateFormat date="${item.date}" locale="${sessionScope.language}"/>
                            </td>
                            <td>
                                <c:if test="${not empty item.dischargeDate}">
                                    <dt:dateFormat date="${item.dischargeDate}" locale="${sessionScope.language}"/>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:when>
            <c:otherwise>
                <h3><fmt:message key="message.patient.history.empty"/>.</h3>
            </c:otherwise>

        </c:choose>
    </div>

    <footer>
        <jsp:include page="../footer.jsp"/>
    </footer>
</div>

</body>

</html>