<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hugi Barbershop System</title>
    <link rel="shortcut icon" href="/uploads/hugiBarber.jpg">
    <link rel="stylesheet" href="/resources/assetsAdmin/css/style.css?v1.1.2">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>

<style>

.nk-wrap {
    padding-top: 60px;
}

@media (max-width: 768px) {
    .nk-wrap {
        padding-top: 80px;
    }
}

</style>

<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

<body class="nk-body" data-sidebar-collapse="lg" data-navbar-collapse="lg">
<div class="nk-app-root">
    <div class="nk-main">
        <%@ include file="/WEB-INF/views/includes/adminHeader.jsp"%>
        <%@ include file="/WEB-INF/views/includes/adminNav.jsp" %>

        <div class="nk-wrap">
            <div class="nk-content">
                <div class="container">
                    <div class="nk-content-inner">
                        <div class="nk-content-body">
                            <div class="nk-block-head">
                                <h2 class="nk-block-title">Appointment List</h2>
                            </div>
                            <div class="nk-block">
                                <div class="card">
                                    <table class="datatable-init table">
                                        <thead>
                                        <tr>
                                            <th>Customer Name</th>
                                            <th>Appointment Date</th>
                                            <th>Appointment Time</th>
                                            <th>Payment Type</th>
                                            <th>Customer Category</th>
                                            <th>Payment Status</th>
                                            <th>Service Status</th>
                                            <c:if test="${fn:toLowerCase(sessionScope.staffRole) == 'admin'}">
                                                <th>Action</th>
                                            </c:if>
                                        </tr>
                                        </thead>
                                        <tbody>
                                        <c:forEach var="a" items="${appointmentList}">
                                            <tr>
                                                <td>${a.customerName}</td>
                                                <td>${a.appointmentDate}</td>
                                                <td>${a.appointmentTime}</td>
                                                <td>${a.paymentMethod}</td>
                                                <td>${a.custType}</td>

                                                <td>
                                                    <c:choose>
                                                        <c:when test="${fn:toLowerCase(a.paymentStatus) == 'pending'}">
                                                            <form action="UpdatePaymentStatus" method="post" class="confirm-update-payment">
                                                                <input type="hidden" name="appointmentId" value="${a.appointmentId}" />
                                                                <button type="button" class="btn btn-warning btn-sm btn-payment">Pending</button>
                                                            </form>
                                                        </c:when>
                                                        <c:otherwise>
                                                            <span class="badge text-bg-success">Completed</span>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>

                                                <td>
                                                    <c:choose>
                                                        <c:when test="${fn:toLowerCase(a.serviceStatus) == 'done'}">
                                                            <span class="badge text-bg-success">Done</span>
                                                        </c:when>
                                                        <c:when test="${fn:toLowerCase(a.serviceStatus) == 'cancelled'}">
                                                            <span class="badge bg-danger">Cancelled</span>
                                                        </c:when>
                                                        <c:when test="${fn:toLowerCase(a.serviceStatus) == 'pending'}">
                                                            <c:choose>
                                                                <c:when test="${fn:toLowerCase(a.paymentStatus) == 'pending'}">
                                                                    <span class="badge bg-secondary">Pending</span>
                                                                </c:when>
                                                                <c:otherwise>
                                                                    <c:if test="${fn:toLowerCase(sessionScope.staffRole) == 'admin'}">
                                                                        <form action="UpdateServiceStatus" method="post" class="confirm-update-service">
                                                                            <input type="hidden" name="appointmentId" value="${a.appointmentId}" />
                                                                            <button type="button" class="btn btn-warning btn-sm btn-service">Pending</button>
                                                                        </form>
                                                                    </c:if>
                                                                    <c:if test="${fn:toLowerCase(sessionScope.staffRole) != 'admin'}">
                                                                        <span class="badge bg-warning">Pending</span>
                                                                    </c:if>
                                                                </c:otherwise>
                                                            </c:choose>
                                                        </c:when>
                                                    </c:choose>
                                                </td>

                                                <c:if test="${fn:toLowerCase(sessionScope.staffRole) == 'admin'}">
                                                    <td>
                                                        <div class="dropdown">
                                                            <a href="#" class="btn btn-sm btn-icon" data-bs-toggle="dropdown">
                                                                <em class="icon ni ni-more-v"></em>
                                                            </a>
                                                            <div class="dropdown-menu">
                                                                <ul class="link-list">
                                                                    <li><a href="editAppointment?appointmentId=${a.appointmentId}">Edit Appointment</a></li>
                                                                </ul>
                                                            </div>
                                                        </div>
                                                    </td>
                                                </c:if>
                                            </tr>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

    </div>
</div>

<script src="/resources/jsAdmin/bundle.js"></script>
<script src="/resources/jsAdmin/scripts.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

<script>
$(".btn-payment").click(function(e){
    e.preventDefault();
    Swal.fire({
        title: 'Are you sure?',
        text: "Confirm payment completed?",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, confirm it!'
    }).then((result) => {
        if (result.isConfirmed) {
            $(this).closest("form").submit();
        }
    });
});

$(".btn-service").click(function(e){
    e.preventDefault();
    Swal.fire({
        title: 'Are you sure?',
        text: "Confirm service done?",
        icon: 'question',
        showCancelButton: true,
        confirmButtonColor: '#3085d6',
        cancelButtonColor: '#d33',
        confirmButtonText: 'Yes, done!'
    }).then((result) => {
        if (result.isConfirmed) {
            $(this).closest("form").submit();
        }
    });
});
</script>

</body>
</html>
