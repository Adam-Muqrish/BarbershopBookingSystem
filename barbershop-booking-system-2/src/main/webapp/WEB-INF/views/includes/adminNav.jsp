<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Hugi Barbershop System</title>
<link rel="shortcut icon" href="./images/hugiBarber.jpg">
<link rel="stylesheet"
	href="./resources/assetsAdmin/css/style.css?v1.1.2">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">

</head>
<body class="nk-body" data-sidebar-collapse="lg"
	data-navbar-collapse="lg">
	<!-- Root -->
	<div class="nk-sidebar nk-sidebar-fixed is-theme" id="sidebar">
		<div class="nk-sidebar-element nk-sidebar-head">
			<div class="nk-sidebar-brand">
				<a href="adminIndex" class="logo-link">
					<div class="logo-wrap" style="width: 150px; height: 100px;">
						<img class="logo-img logo-light"
							src="./././resources/uploads/hugiBarber1.png" alt=""
							style="width: 150px; height: 100px;">
					</div>
				</a>
			</div>
		</div>

		<div class="nk-sidebar-element nk-sidebar-body">
			<div class="nk-sidebar-content">
				<div class="nk-sidebar-menu" data-simplebar>
					<ul class="nk-menu">
						<li class="nk-menu-heading"><h6 class="overline-title">Hugi
								Barbershop</h6></li>
						<li class="nk-menu-item"><a href="listCustomer"
							class="nk-menu-link"><span class="nk-menu-icon"><em
									class="icon ni ni-user"></em></span><span class="nk-menu-text">Customer</span></a></li>
						<li class="nk-menu-item"><a href="listBarber"
							class="nk-menu-link"><span class="nk-menu-icon"><em
									class="fas fa-scissors"></em></span><span class="nk-menu-text">Barber</span></a></li>
						<li class="nk-menu-item"><a href="listAppointment"
							class="nk-menu-link"><span class="nk-menu-icon"><em
									class="icon ni ni-calendar-booking"></em></span><span
								class="nk-menu-text">Appointment</span></a></li>
						<c:if test="${sessionScope.staffRole == 'Admin'}">
						<li class="nk-menu-item"><a href="listTransaction"
							class="nk-menu-link"><span class="nk-menu-icon"><em
									class="icon ni ni-cc-alt2"></em></span><span class="nk-menu-text">Transaction</span></a></li>
						</c:if>
						<!-- <li class="nk-menu-item has-sub"><a href="#"
							class="nk-menu-link nk-menu-toggle"><span
								class="nk-menu-icon"><em class="icon ni ni-reports"></em></span><span
								class="nk-menu-text">Sales Report</span></a>
							<ul class="nk-menu-sub">
								<li class="nk-menu-item"><a
									href="/html/apps/invoice/invoice-list.html"
									class="nk-menu-link"><span class="nk-menu-text">Daily
											Report</span></a></li>
								<li class="nk-menu-item"><a
									href="/html/apps/invoice/invoice-preview.html"
									class="nk-menu-link"><span class="nk-menu-text">Monthly
											Report</span></a></li>
								<li class="nk-menu-item"><a
									href="/html/apps/invoice/invoice-preview.html"
									class="nk-menu-link"><span class="nk-menu-text">Yearly
											Report</span></a></li>
							</ul></li> -->
						<li class="nk-menu-item"><a href="listFeedback"
							class="nk-menu-link"><span class="nk-menu-icon"><em
									class="icon ni ni-chat-circle"></em></span><span class="nk-menu-text">Feedback</span></a></li><br><br><br><br><br>
						<!-- <li class="nk-menu-divider" style="border-top: 1px solid #999999; margin: 0;"></li>
						<li class="nk-menu-item" style="margin-top: 4px;">
						    <a href="logout" class="nk-menu-link">
						        <span class="nk-menu-icon"><em class="icon ni ni-user"></em></span>
						        <span class="nk-menu-text">Log Out</span>
						    </a>
						</li> -->
					</ul>
				</div>
			</div>
		</div>
	</div>
	<!-- .nk-app-root -->
</body>

</html>