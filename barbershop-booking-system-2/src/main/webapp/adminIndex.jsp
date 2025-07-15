<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.List" %>
<%@ page import="com.hugi.barbershop.customer.model.Customer" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.HashMap" %>


<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hugi Barbershop System</title>
    <link rel="shortcut icon" href="/uploads/hugiBarber.jpg">
    <link rel="stylesheet" href="/resources/assetsAdmin/css/style.css?v1.1.2">
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    
</head>

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
                
                    <div class="container-fluid">
                        <div class="nk-content-inner">
                            <div class="nk-content-body">
                                <%-- Semua content kau di sini --%>

                                <div class="row g-gs first-row">
                                    <!-- Card 1 -->
                                    <div class="col-xl-4">
									    <div class="card h-100">
									        <div class="card-body d-flex flex-column justify-content-center text-center">
									            <div class="card-title mb-3">
									                <h4 class="title mb-1">Total Hugi's Barbershop Sales</h4>
									            </div>
									            <div class="my-3">
									                <i class="fas fa-coins fa-3x mb-3 text-primary"></i>
									                <div class="amount h2 fw-bold text-primary">
									                    RM <fmt:formatNumber value="${totalSales}" type="number" minFractionDigits="2" maxFractionDigits="2"/>
									                </div>
									            </div>
									        </div>
									    </div>
									</div>
                                    <!-- Card 2 -->
                                    <div class="col-xl-4">
									    <div class="card h-100">
									        <div class="card-body d-flex flex-column justify-content-center text-center">
									            <div class="card-title mb-3">
									                <h4 class="title mb-1">Total Hugi's Barbershop Customer</h4>
									            </div>
									            <div class="my-3">
									                <i class="fas fa-users fa-3x mb-3 text-primary"></i>
									                <div class="amount h2 fw-bold text-primary">
									                    ${customerCount} People
									                </div>
									            </div>
									        </div>
									    </div>
									</div>
                                    <!-- Card 3 -->
                                    <div class="col-xl-4">
									    <div class="card h-100">
									        <div class="card-body d-flex flex-column justify-content-center text-center">
									            <div class="card-title mb-3">
									                <h4 class="title mb-1">Total Hugi's Barbershop Appointment</h4>
									            </div>
									            <div class="my-3">
									                <i class="fas fa-calendar-check fa-3x mb-3 text-primary"></i>
									                <div class="amount h2 fw-bold text-primary">${totalAppointments} Appointments</div>
									            </div>
									        </div>
									    </div>
									</div>
                                    <!-- Table -->
                                    <div class="col-xxl-6">
									    <div class="card h-100">
									        <div class="card-body flex-grow-0 py-3">
									            <div class="d-flex align-items-center mb-3">
									                <i class="fas fa-users fa-lg text-primary me-3"></i>
									                <h4 class="title mb-0">List of Customers</h4>
									            </div>
									            <div class="table-responsive">
									                <table class="table table-hover table-striped table-bordered mb-0 rounded-3 overflow-hidden">
									                    <thead class="table-light table-head-sm">
									                        <tr>
									                            <th>Name</th>
									                            <th>Email</th>
									                            <th>Phone Number</th>
									                        </tr>
									                    </thead>
									                    <tbody>
									                        <c:choose>
									                            <c:when test="${not empty customerList}">
									                                <c:forEach var="cust" items="${customerList}">
									                                    <tr>
									                                        <td>${cust.custName}</td>
									                                        <td>${cust.custEmail}</td>
									                                        <td>${cust.custPhoneNumber}</td>
									                                    </tr>
									                                </c:forEach>
									                            </c:when>
									                            <c:otherwise>
									                                <tr><td colspan="3" class="text-center">No customers found.</td></tr>
									                            </c:otherwise>
									                        </c:choose>
									                    </tbody>
									                </table>
									            </div>
									        </div>
									    </div>
									</div>
                                    <!-- Sales Chart Card -->
									<div class="col-12">
									    <div class="card h-100">
									        <div class="card-body">
									            <h4 class="title mb-3">Weekly Sales (By Day)</h4>
									            <canvas id="salesChart"></canvas>
									        </div>
									    </div>
									</div>
                                </div> <!-- .row -->
                            </div>
                        </div>
                    </div>
                </div> <!-- .nk-content -->
            </div> <!-- .nk-wrap -->
        </div> <!-- .nk-main -->
    </div> <!-- .nk-app-root -->

	<!-- JavaScript Section -->
	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

<% if (request.getAttribute("successMessage") != null) { %>
<script>
Swal.fire({
    icon: 'success',
    title: 'Success!',
    text: '<%= request.getAttribute("successMessage") %>'
});
</script>
<% } else if (request.getAttribute("error") != null) { %>
<script>
Swal.fire({
    icon: 'error',
    title: 'Oops...',
    text: '<%= request.getAttribute("error") %>'
});
</script>
<% } else if (session.getAttribute("loginError") != null) { %>
<script>
Swal.fire({
    icon: 'error',
    title: 'Login Failed',
    text: '<%= session.getAttribute("loginError") %>'
});
<% session.removeAttribute("loginError"); %>
</script>
<% } else if (session.getAttribute("loginSuccess") != null) { %>
<script>
Swal.fire({
    icon: 'success',
    title: 'Login Successful!',
    text: '<%= session.getAttribute("loginSuccess") %>'
});
<% session.removeAttribute("loginSuccess"); %>
</script>
<% } %>
	
	<%
    Map<String, Double> salesByDay = (Map<String, Double>) request.getAttribute("salesByDay");
	%>

<script>
    const dayLabels = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
    const salesData = [
        <%= salesByDay.get("SUNDAY") %>,
        <%= salesByDay.get("MONDAY") %>,
        <%= salesByDay.get("TUESDAY") %>,
        <%= salesByDay.get("WEDNESDAY") %>,
        <%= salesByDay.get("THURSDAY") %>,
        <%= salesByDay.get("FRIDAY") %>,
        <%= salesByDay.get("SATURDAY") %>
    ];

    const ctx = document.getElementById('salesChart').getContext('2d');
    new Chart(ctx, {
        type: 'bar',
        data: {
            labels: dayLabels,
            datasets: [{
                label: 'Sales (RM)',
                data: salesData,
                backgroundColor: 'rgba(54, 162, 235, 0.6)',
                borderColor: 'rgba(54, 162, 235, 1)',
                borderWidth: 1
            }]
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true,
                    ticks: {
                        callback: value => 'RM' + value
                    }
                },
                x: {
                    title: {
                        display: true,
                        text: 'Day of Week'
                    }
                }
            }
        }
    });
</script>


<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>


</body>
</html>
