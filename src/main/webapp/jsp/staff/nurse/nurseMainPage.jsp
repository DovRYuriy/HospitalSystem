<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="dt" uri="/WEB-INF/date-format.tld" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename='message'/>

<html>
<head>
    <title><fmt:message key="message.doctor.patients"/></title>
    <link rel="icon" href="../../../resources/image/favicon.ico" type="image/ico">
    <link rel="stylesheet" href="<c:url value="../../../resources/style/style.css"/>" type="text/css">
    <link rel="stylesheet" href="<c:url value="../../../resources/style/style1.css"/>" type="text/css">
</head>
<body>
<div class="container">
    <header>
        <jsp:include page="../../header.jsp"/>
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
            <input type="hidden" name="redirectTo" value="nurseProfile"/>
            <input type="submit" name="goToProfile" value="<fmt:message key='message.patient.profile'/>"/>
        </form>

        <form name="goToMyPatients" method="GET" action="${pageContext.request.contextPath}/controller/">
            <input type="hidden" name="command" value="redirect"/>
            <input type="hidden" name="redirectTo" value="nurseMainPage"/>
            <input type="submit" name="goToStaff" value="<fmt:message key='message.doctor.patients'/>"/>
        </form>
    </nav>
    <div class="main">
        <h3><fmt:message key="message.doctor.patient.service"/>:</h3>
        <c:choose>
            <c:when test="${not empty sessionScope.patientDiagnosis}">
                <table border="1px">
                    <tr>
                        <th><fmt:message key="message.nurse.patient"/></th>
                        <th><fmt:message key="message.patient.date"/></th>
                        <th><fmt:message key="message.patient.diagnosis"/></th>
                        <th><fmt:message key="message.prescription.drugs"/></th>
                        <th><fmt:message key="message.prescription.procedure"/></th>
                    </tr>
                    <c:forEach items="${sessionScope.patientDiagnosis}" var="item">
                        <tr>
                            <td>
                                    ${item.patient.name}<br/>
                                    ${item.patient.surname}
                            </td>
                            <td><dt:dateFormat date="${item.date}" locale="${sessionScope.language}"/></td>
                            <td>
                                    ${item.diagnosis.name}<br/>
                                    ${item.diagnosis.description}
                            </td>
                            <td>
                                    ${item.prescription.drugs}<br/>
                                <c:if test="${not empty item.prescription.drugs}">
                                    <form name="giveDrugs" action="${pageContext.request.contextPath}/controller/"
                                          method="POST">
                                        <input type="hidden" name="command" value="makeStaffAction"/>
                                        <input type="hidden" name="whatAction" value="drugs"/>
                                        <input type="hidden" name="prescriptionId"
                                               value="${item.prescription.idPrescription}"/>
                                        <input type="submit" name="make" value="<fmt:message key="message.give"/>!"/>
                                    </form>
                                </c:if>
                            </td>
                            <td>
                                    ${item.prescription.procedure}<br/>
                                <c:if test="${not empty item.prescription.procedure}">
                                    <form name="makeProcedure" action="${pageContext.request.contextPath}/controller/"
                                          method="POST">
                                        <input type="hidden" name="command" value="makeStaffAction"/>
                                        <input type="hidden" name="whatAction" value="procedure"/>
                                        <input type="hidden" name="prescriptionId"
                                               value="${item.prescription.idPrescription}"/>
                                        <input type="submit" name="make" value="<fmt:message key="message.make"/>!"/>
                                    </form>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:when>
            <c:otherwise>
                <h3><fmt:message key="message.doctor.patients.empty"/></h3>
            </c:otherwise>
        </c:choose>

    </div>
    <footer>
        <jsp:include page="../../footer.jsp"/>
    </footer>
</div>
</body>
</html>
