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
</head>
<!-- jQuery (optional jika Bootstrap v5 tak perlu) -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<!-- Bootstrap Bundle JS (termasuk Popper.js) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>

<body class="nk-body" data-sidebar-collapse="lg" data-navbar-collapse="lg">
    <div class="nk-app-root">
        <div class="nk-main">
            <%@ include file="/WEB-INF/views/includes/adminNav.jsp" %>

            <div class="nk-wrap">
                <%-- <%@ include file="/WEB-INF/views/includes/adminHeader.jsp"%> --%>
                 <div class="nk-content">
                    <div class="container">
                        <div class="nk-content-inner">
                            <div class="nk-content-body">
                                <div class="nk-block-head">
                                    <div class="nk-block-head-between flex-wrap gap g-2">
                                        <div class="nk-block-head-content">
                                            <h2 class="nk-block-title">Appointment List</h1>
                                                <nav>
                                                    <ol class="breadcrumb breadcrumb-arrow mb-0">
                                                        <li class="breadcrumb-item"><a href="#">Home</a></li>
                                                        <li class="breadcrumb-item"><a href="#">Appointment</a></li>
                                                        <li class="breadcrumb-item active" aria-current="page">Appointment List</li>
                                                    </ol>
                                                </nav>
                                        </div>
                                    </div><!-- .nk-block-head-between -->
                                </div><!-- .nk-block-head -->
                                <div class="nk-block">
                                    <div class="card">
                                        <table class="datatable-init table" data-nk-container="table-responsive">
                                            <thead class="table-light">
                                                <tr>
                                                    <th class="tb-col">
                                                        <span class="overline-title">Customer Name</span>
                                                    </th>
                                                    <th class="tb-col">
                                                        <span class="overline-title">Appointment Date</span>
                                                    </th>
                                                    <th class="tb-col">
                                                        <span class="overline-title">Appointment Time</span>
                                                    </th>
                                                    <th class="tb-col tb-col-xl">
                                                        <span class="overline-title">Customer Type</span>
                                                    </th>
                                                    <th class="tb-col tb-col-xl">
                                                        <span class="overline-title">Service Status</span>
                                                    </th>
                                                    <!-- <th class="tb-col tb-col-xl">
                                                        <span class="overline-title">Loyalty Point</span>
                                                    </th> -->
                                                    <th class="tb-col tb-col-end">
                                                        <span class="overline-title">Action</span>
                                                    </th>
                                                    
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="a" items="${appointmentList}">
										            <tr>
										                <td>${a.customerName}</td>
										                <td>${a.appointmentDate}</td>
										                <td>${a.appointmentTime}</td>
										                <td>${a.custType}</td> 
										                <td>
														    <c:choose>
														        <c:when test="${fn:toLowerCase(a.serviceStatus) == 'pending'}">
														            <form action="UpdateServiceStatus" method="post" style="display:inline;">
														                <input type="hidden" name="appointmentId" value="${a.appointmentId}" />
														                <button type="submit" class="btn btn-warning btn-sm">
														                    Pending
														                </button>
														            </form>
														        </c:when>
														        <c:otherwise>
														            <button class="btn btn-success btn-sm" disabled>
														                Done
														            </button>
														        </c:otherwise>
														    </c:choose>
														</td>
										                <%-- <td>${a.loyaltyPoint}</td> --%>
										                <td class="tb-col tb-col-end">
											                <div class="dropdown">
											                    <a href="#" class="btn btn-sm btn-icon btn-zoom me-n1" data-bs-toggle="dropdown">
											                        <em class="icon ni ni-more-v"></em>
											                    </a>
											                    <div class="dropdown-menu dropdown-menu-sm dropdown-menu-end">
											                        <div class="dropdown-content py-1">
											                            <ul class="link-list link-list-hover-bg-primary link-list-md">
											                                <li>
											                                    <a href="editAppointment?appointmentId=${a.appointmentId}">
											                                        <em class="icon ni ni-eye"></em>
											                                        <span>Edit Appointment</span>
											                                    </a>
											                                </li>
											                            </ul>
											                        </div>
											                    </div>
											                </div>
											            </td>
										            </tr>
										        </c:forEach>
                                            </tbody>
                                        </table>
                                    </div><!-- .card -->
                                </div><!-- .nk-block -->
                            </div>
                        </div>
                    </div>
                </div>
            </div> <!-- .nk-wrap -->
        </div> <!-- .nk-main -->
    </div> <!-- .nk-app-root -->
</body>
</html>