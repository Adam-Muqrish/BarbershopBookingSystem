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
<!-- jQuery (optional jika Bootstrap v5 tak perlu) -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<!-- Bootstrap Bundle JS (termasuk Popper.js) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<style>
    .nk-wrap {
      padding-top: 60px; /* Changed from 0px !important */
    }
    .first-row {
        margin-top: 30px; /* Added space below header */
    }
</style>
<body class="nk-body" data-sidebar-collapse="lg" data-navbar-collapse="lg">
    <div class="nk-app-root">
        <div class="nk-main">
			<%@ include file="/WEB-INF/views/includes/adminHeader.jsp"%>
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
													    <span class="overline-title">Payment Status</span>
													</th>
                                                    <th class="tb-col tb-col-xl">
                                                        <span class="overline-title">Service Status</span>
                                                    </th>
                                                    <!-- <th class="tb-col tb-col-xl">
                                                        <span class="overline-title">Loyalty Point</span>
                                                    </th> -->
                                                    <c:if test="${fn:toLowerCase(sessionScope.staffRole) == 'admin'}">
										                <th class="tb-col tb-col-end"><span class="overline-title">Action</span></th>
										            </c:if>
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
															    <c:when test="${fn:toLowerCase(a.paymentMethod) == 'online banking'}">
															        <span class="badge text-bg-success">Completed</span>
															    </c:when>
															    <c:when test="${fn:toLowerCase(a.paymentMethod) == 'cash'}">
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
															    </c:when>
															</c:choose>
														</td>
										                <td>
													    <c:choose>
													        <c:when test="${fn:toLowerCase(a.serviceStatus) == 'done'}">
													            <button class="btn btn-success btn-sm" disabled>Done</button>
													        </c:when>
													
													        <c:when test="${fn:toLowerCase(a.serviceStatus) == 'cancelled'}">
													            <span class="badge bg-danger">Cancelled</span>
													        </c:when>
													
													        <c:when test="${fn:toLowerCase(a.serviceStatus) == 'pending'}">
													            <c:choose>
													                <c:when test="${fn:toLowerCase(a.paymentStatus) == 'pending'}">
													                    <button class="btn btn-secondary btn-sm" disabled>Pending</button>
													                </c:when>
													
													                <c:when test="${fn:toLowerCase(a.paymentStatus) == 'done' || fn:toLowerCase(a.paymentStatus) == 'completed'}">
																	    <c:choose>
																	        <c:when test="${fn:toLowerCase(sessionScope.staffRole) == 'admin'}">
																	            <form action="UpdateServiceStatus" method="post" class="confirm-update-service">
																	                <input type="hidden" name="appointmentId" value="${a.appointmentId}" />
																	                <button type="button" class="btn btn-warning btn-sm btn-service">Pending</button>
																	            </form>
																	        </c:when>
																	
																	        <c:otherwise>
																	            <button class="btn btn-warning btn-sm" disabled>Pending</button>
																	        </c:otherwise>
																	    </c:choose>
																	</c:when>
													            </c:choose>
													        </c:when>
													    </c:choose>
													</td>
										                <%-- <td>${a.loyaltyPoint}</td> --%>
										                <c:if test="${fn:toLowerCase(sessionScope.staffRole) == 'admin'}">
														    <td class="tb-col tb-col-end">
														        <div class="dropdown">
														            <a href="#" class="btn btn-sm btn-icon btn-zoom me-n1 ${fn:toLowerCase(a.serviceStatus) == 'done' ? 'disabled' : ''}" 
														               data-bs-toggle="dropdown" 
														               <c:if test="${fn:toLowerCase(a.serviceStatus) == 'done'}"> onclick="return false;" </c:if> >
														                <em class="icon ni ni-more-v"></em>
														            </a>
														            <c:if test="${fn:toLowerCase(a.serviceStatus) != 'done'}">
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
														            </c:if>
														        </div>
														    </td>
														</c:if>
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
    <script src="/resources/jsAdmin/bundle.js"></script>
    <script src="/resources/jsAdmin/scripts.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"></script>
    
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

	<c:if test="${param.success eq 'updated'}">
	    <script>
	        Swal.fire({
	            icon: 'success',
	            title: 'Success!',
	            text: 'Appointment updated successfully.',
	            showConfirmButton: false,
	            timer: 1500
	        });
	    </script>
	</c:if>
	
	<c:if test="${param.error eq 'updatefail'}">
	    <script>
	        Swal.fire({
	            icon: 'error',
	            title: 'Failed!',
	            text: 'Failed to update appointment.',
	            showConfirmButton: false,
	            timer: 2000
	        });
	    </script>
	</c:if>
    
    
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