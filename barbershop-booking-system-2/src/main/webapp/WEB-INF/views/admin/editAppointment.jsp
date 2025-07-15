<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hugi Barbershop System</title>
    <link rel="shortcut icon" href="/uploads/hugiBarber.jpg">
    <link rel="stylesheet" href="/resources/assetsAdmin/css/style.css?v1.1.2">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<c:set var="timeSlots" value="10:00 am,10:30 am,11:00 am,11:30 am,12:00 pm,12:30 pm,1:00 pm,1:30 pm,2:00 pm,2:30 pm,3:00 pm,3:30 pm,4:00 pm,4:30 pm,5:00 pm,5:30 pm,6:00 pm,6:30 pm,7:00 pm,7:30 pm,8:00 pm,8:30 pm,9:00 pm,9:30 pm" />
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
                <div class="nk-content">
                    <div class="container">
                        <div class="nk-content-inner">
                            <div class="nk-content-body">
                                <div class="nk-block-head">
                                    <div class="nk-block-head-between flex-wrap gap g-2">
                                        <div class="nk-block-head-content">
                                            <h2 class="nk-block-title">Edit Appointment</h2>
                                            <nav>
                                                <ol class="breadcrumb breadcrumb-arrow mb-0">
                                                    <li class="breadcrumb-item"><a href="#">Home</a></li>
                                                    <li class="breadcrumb-item"><a href="#">Appointment</a></li>
                                                    <li class="breadcrumb-item active" aria-current="page">Edit Appointment</li>
                                                </ol>
                                            </nav>
                                        </div>
                                    </div>
                                </div>

                                <div class="nk-block">
                                    <form action="editAppointment" method="post">
                                    <input type="hidden" name="appointmentId" value="${appointment.appointmentId}">
                                        <div class="row g-gs">
                                            <div class="col-xxl-9">
                                                <div class="gap gy-4">
                                                    <div class="gap-col">
                                                        <div class="card card-gutter-md">
                                                            <div class="card-body">
                                                                <div class="row g-gs">
                                                                    <div class="col-lg-12">
                                                                        <div class="form-group">
                                                                            <label class="form-label">Booking For:</label>
                                                                            <div class="form-control-wrap">
                                                                                <input type="text" name="custBookFor" class="form-control" value="${appointment.custBookFor}">
                                                                            </div>
                                                                        </div>
                                                                    </div>

                                                                    <div class="col-lg-6">
                                                                        <div class="form-group">
                                                                            <label class="form-label">Select Date</label>
                                                                            <div class="form-control-wrap">
                                                                                <input type="date" name="appointmentDate" class="form-control"
																                   value="${appointment.appointmentDate}"
																                   min="<%= java.time.LocalDate.now() %>">
                                                                            </div>
                                                                        </div>
                                                                    </div>

                                                                    <div class="col-lg-6">
                                                                        <div class="form-group">
                                                                            <label class="form-label">Booking Time</label>
                                                                            <div class="form-control-wrap">
																				<select name="appointmentTime" class="form-control"  style="background-color: #ffffff; color: #000;">
																				    <c:forEach var="time" items="${fn:split(timeSlots, ',')}">
																					    <option value="${fn:trim(time)}" ${fn:trim(appointment.appointmentTime) == fn:trim(time) ? 'selected' : ''}>
																					        ${fn:trim(time)}
																					    </option>
																					</c:forEach>
																				</select>
                                                                            </div>
                                                                        </div>
                                                                    </div>

                                                                    <div class="col-lg-6">
                                                                        <div class="form-group">
                                                                            <label class="form-label">Customer Category</label>
                                                                            <div class="form-control-wrap">
                                                                                <input type="text" name="custType" class="form-control" value="${appointment.custType}" readonly>
                                                                            </div>
                                                                        </div>
                                                                    </div>

                                                                    <div class="col-lg-6">
                                                                        <div class="form-group">
                                                                            <label class="form-label">Select Barber</label>
                                                                            <div class="form-control-wrap">
                                                                                <select name="staffId" class="form-control" style="background-color: #ffffff; color: #000;">
																				    <c:forEach var="barber" items="${barberList}">
																				        <option value="${barber.staffId}" ${appointment.barberId == barber.staffId ? 'selected' : ''}>
																				            ${barber.staffName}
																				        </option>
																				    </c:forEach>
																				</select>
                                                                            </div>
                                                                        </div>
                                                                    </div>
                                                                </div> <!-- .row -->
                                                            </div> <!-- .card-body -->
                                                        </div> <!-- .card -->
                                                    </div>
                                                    <div class="gap-col">
                                                        <ul class="d-flex align-items-center gap g-3">
                                                            <li>
                                                                <button type="submit" class="btn btn-primary">Save Changes</button>
                                                            </li>
                                                            <li>
                                                                <a href="listAppointment" class="btn border-0">Cancel</a>
                                                            </li>
                                                        </ul>
                                                    </div>
                                                </div> <!-- .gap -->
                                            </div> <!-- .col -->
                                        </div> <!-- .row -->
                                    </form>
                                </div> <!-- .nk-block -->
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
</body>
</html>
