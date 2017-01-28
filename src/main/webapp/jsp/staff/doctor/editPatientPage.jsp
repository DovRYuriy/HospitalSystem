<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="dt" uri="/WEB-INF/date-format.tld" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename='message'/>

<html>
<head>
    <title><fmt:message key="message.doctor.service"/></title>
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
        <form name="goToStaffPage" method="GET" action="${pageContext.request.contextPath}/controller/">
            <input type="hidden" name="command" value="redirect"/>
            <input type="hidden" name="redirectTo" value="doctorMainPage"/>
            <input type="submit" name="goToStaff" value="<fmt:message key='message.back'/>"/>
        </form>
    </nav>
    <div class="main">
        <c:choose>
            <c:when test="${empty sessionScope.notFound}">
                <h3><fmt:message key="message.doctor.patient.service"/>:</h3>
                <h3>${sessionScope.person.name} ${sessionScope.person.surname}</h3>
                <h4><fmt:message key="message.chamber"/> № ${sessionScope.chamber.number} </h4>
                <div>
                    <c:if test="${not empty sessionScope.errorPD}">
                        <fmt:message key="message.remove.persondiagnosis.failed"/>
                    </c:if>
                </div>
                <table border="1px">
                    <tr>
                        <th><fmt:message key="message.patient.date"/></th>
                        <th><fmt:message key="message.patient.dischargedate"/></th>
                        <th><fmt:message key="message.patient.diagnosis"/></th>
                        <th><fmt:message key="message.prescription.drugs"/></th>
                        <th><fmt:message key="message.prescription.procedure"/></th>
                        <th><fmt:message key="message.prescription.operation"/></th>
                        <th><fmt:message key="message.crud.remove"/></th>
                    </tr>
                    <c:forEach items="${sessionScope.patientDiagnosis}" var="item">
                        <tr>
                            <td><dt:dateFormat date="${item.date}" locale="${sessionScope.language}"/></td>
                            <td>
                                <c:if test="${not empty item.dischargeDate}">
                                    <dt:dateFormat date="${item.dischargeDate}" locale="${sessionScope.language}"/>
                                </c:if>
                            </td>
                            <td>
                                    ${item.diagnosis.name}<br/>
                                    ${item.diagnosis.description}
                            </td>
                            <td>
                                    ${item.prescription.drugs}<br/>
                                <c:if test="${empty item.dischargeDate}">
                                    <c:if test="${not empty item.prescription.drugs}">
                                        <form name="giveDrugs" action="${pageContext.request.contextPath}/controller/"
                                              method="POST">
                                            <input type="hidden" name="command" value="makeStaffAction"/>
                                            <input type="hidden" name="whatAction" value="drugs"/>
                                            <input type="hidden" name="prescriptionId"
                                                   value="${item.prescription.idPrescription}"/>
                                            <input type="submit" name="make"
                                                   value="<fmt:message key="message.give"/>!"/>
                                        </form>
                                    </c:if>
                                </c:if>
                            </td>
                            <td>
                                    ${item.prescription.procedure}<br/>
                                <c:if test="${empty item.dischargeDate}">
                                    <c:if test="${not empty item.prescription.procedure}">
                                        <form name="makeProcedure"
                                              action="${pageContext.request.contextPath}/controller/"
                                              method="POST">
                                            <input type="hidden" name="command" value="makeStaffAction"/>
                                            <input type="hidden" name="whatAction" value="procedure"/>
                                            <input type="hidden" name="prescriptionId"
                                                   value="${item.prescription.idPrescription}"/>
                                            <input type="submit" name="make"
                                                   value="<fmt:message key="message.make"/>!"/>
                                        </form>
                                    </c:if>
                                </c:if>
                            </td>
                            <td>
                                    ${item.prescription.operation}<br/>
                                <c:if test="${empty item.dischargeDate}">
                                    <c:if test="${not empty item.prescription.operation}">
                                        <form name="makeOperation"
                                              action="${pageContext.request.contextPath}/controller/"
                                              method="POST">
                                            <input type="hidden" name="command" value="makeStaffAction"/>
                                            <input type="hidden" name="whatAction" value="operation"/>
                                            <input type="hidden" name="prescriptionId"
                                                   value="${item.prescription.idPrescription}"/>
                                            <input type="submit" name="make"
                                                   value="<fmt:message key="message.make"/>!"/>
                                        </form>
                                    </c:if>
                                </c:if>
                            </td>
                            <td>
                                <c:if test="${empty sessionScope.removeNotAllowed}">
                                    <c:if test="${empty item.dischargeDate}">
                                        <form name="removeForm" action="${pageContext.request.contextPath}/controller/"
                                              method="POST">
                                            <input type="hidden" name="command" value="removePersonDiagnosis"/>
                                            <input type="hidden" name="idPatient" value="${item.patient.idPerson}"/>
                                            <input type="hidden" name="idStaff" value="${item.doctor.idPerson}"/>
                                            <input type="hidden" name="idDiagnosis"
                                                   value="${item.diagnosis.idDiagnosis}"/>
                                            <input type="hidden" name="idPrescription"
                                                   value="${item.prescription.idPrescription}"/>
                                            <input type="submit" name="submitRemove"
                                                   value="<fmt:message key='message.crud.remove'/>">
                                        </form>
                                    </c:if>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
                <div>
                    <form name="setNewDiagnosis" method="GET" action="${pageContext.request.contextPath}/controller/">
                        <input type="hidden" name="command" value="redirect"/>
                        <input type="hidden" name="redirectTo" value="setDiagnosisPage"/>
                        <input class="regButton" type="submit" name="goToRegister"
                               value="<fmt:message key='message.set.diagnosis'/>"/>
                    </form>
                </div>
            </c:when>
            <c:otherwise>
                <h3><fmt:message key="message.invalid.person"/></h3>
            </c:otherwise>
        </c:choose>
    </div>
    <footer>
        <jsp:include page="../../footer.jsp"/>
    </footer>
</div>
</body>
</html>
