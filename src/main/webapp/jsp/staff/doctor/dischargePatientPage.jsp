<%@ page isELIgnored="false" contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.language}"/>
<fmt:setBundle basename='message'/>

<html>
<head>
    <title><fmt:message key="message.doctor.discharge.title"/></title>
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
                <h3><fmt:message
                        key="message.doctor.discharge.patient"/> ${sessionScope.personDiagnosis.patient.name} ${sessionScope.personDiagnosis.patient.surname}</h3>
                <br/>
                <form name="dischargePatient" id="usrform" action="${pageContext.request.contextPath}/controller/" method="POST"
                      class="formBox2">
                    <input type="hidden" name="command" value="dischargePatient">
                    <h3><fmt:message key="message.doctor.final.diagnosis"/>:</h3>
                    <input type="text" name="diagnosis" value="${sessionScope.personDiagnosis.diagnosis.name}"
                           placeholder="<fmt:message key='message.patient.diagnosis'/>"/>
                    <br/>
                    <textarea rows="4" cols="50" name="description"
                              form="usrform">${sessionScope.personDiagnosis.diagnosis.description}</textarea><br/><br/>

                    <input type="text" name="drugs"
                           value="${sessionScope.personDiagnosis.prescription.drugs}"
                           placeholder="<fmt:message key='message.prescription.drugs'/>"/><br/><br/>
                    <input type="text" name="procedure"
                           value="${sessionScope.personDiagnosis.prescription.procedure}"
                           placeholder="<fmt:message key='message.prescription.procedure'/>"/><br/><br/>
                    <input type="text" name="operation"
                           value="${sessionScope.personDiagnosis.prescription.operation}"
                           placeholder="<fmt:message key='message.prescription.operation'/>"/><br/><br/>

                    <input type="submit" name="dischargePtnt" value="<fmt:message key="message.doctor.discharge"/>">
                </form>
            </c:when>
            <c:otherwise>
                <h3><fmt:message key="message.invalid.person"/></h3>
            </c:otherwise>
        </c:choose>
    </div>
</div>
</body>
</html>
