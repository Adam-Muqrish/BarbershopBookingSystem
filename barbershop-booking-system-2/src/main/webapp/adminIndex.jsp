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
                                            <div class="card-body d-flex flex-column justify-content-between">
                                                <div>
                                                    <div class="card-title">
                                                        <h4 class="title mb-1">Total Hugi's Barbershop    Sales</h4>
                                                    </div>
                                                    <div class="my-3">
                                                        <div class="amount h2 fw-bold text-primary">
														    RM <fmt:formatNumber value="${totalSales}" type="number" minFractionDigits="2" maxFractionDigits="2"/>
														</div>
                                                    </div>
                                                    <a href="listTransaction" class="btn btn-sm btn-primary">View Sales</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <!-- Card 2 -->
                                    <div class="col-xl-4">
                                        <div class="card h-100">
                                            <div class="card-body d-flex flex-column justify-content-between">
                                                <div>
                                                    <div class="card-title">
                                                        <h4 class="title mb-1">Total Hugi's Barbershop Customer</h4>
                                                    </div>
                                                    <div class="my-3">
                                                        <div class="amount h2 fw-bold text-primary">
									                        ${customerCount} People
									                    </div>
                                                    </div>
                                                    <a href="listCustomer" class="btn btn-sm btn-primary">View Customer</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <!-- Card 3 -->
                                    <div class="col-xl-4">
                                        <div class="card h-100">
                                            <div class="card-body d-flex flex-column justify-content-between">
                                                <div>
                                                    <div class="card-title">
                                                        <h4 class="title mb-1">Total Hugi's Barbershop Appointment</h4>
                                                    </div>
                                                    <div class="my-3">
                                                        <div class="amount h2 fw-bold text-primary">${totalAppointments}  Appointments</div>
                                                    </div>
                                                    <a href="listAppointment" class="btn btn-sm btn-primary">View Appointment</a>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <!-- Table -->
                                    <div class="col-xxl-6">
                                        <div class="card h-100">
                                            <div class="card-body flex-grow-0 py-2">
                                                <div class="card-title-group">
                                                    <div class="card-title">
                                                        <h4 class="title">List Of Customer</h4>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="table-responsive">
                                                <table class="table table-middle mb-0">
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
													            <tr><td colspan="3">No customers found.</td></tr>
													        </c:otherwise>
													    </c:choose>
													</tbody>
                                                </table>
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
	<%
	if (request.getAttribute("loginError") != null) {
	%>
	<script>
    alert("<%=request.getAttribute("loginError")%>
		");
	</script>
	<%
	}
	%>

	<%
	String loginSuccess = (String) session.getAttribute("loginSuccess");
	if (loginSuccess != null) {
	%>
	<script>
    alert("<%=loginSuccess%>");
</script>
	<%
	session.removeAttribute("loginSuccess"); // padam supaya popup tak ulang
	}
	%>
	
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
