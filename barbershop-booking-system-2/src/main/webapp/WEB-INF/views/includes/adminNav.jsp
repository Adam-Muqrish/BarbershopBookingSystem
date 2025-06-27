<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Hugi Barbershop System</title>
<link rel="shortcut icon" href="./images/hugiBarber.jpg">
<link rel="stylesheet"
	href="./resources/assetsAdmin/css/style.css?v1.1.2">
</head>
<body class="nk-body" data-sidebar-collapse="lg"
	data-navbar-collapse="lg">
	<!-- Root -->
	<div class="nk-sidebar nk-sidebar-fixed is-theme" id="sidebar">
		<div class="nk-sidebar-element nk-sidebar-head">
			<div class="nk-sidebar-brand">
				<a href="adminIndex.jsp" class="logo-link">
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
									class="icon ni ni-scissors"></em></span><span class="nk-menu-text">Barber</span></a></li>
						<li class="nk-menu-item"><a href="listAppointment"
							class="nk-menu-link"><span class="nk-menu-icon"><em
									class="icon ni ni-calendar-booking"></em></span><span
								class="nk-menu-text">Appointment</span></a></li>
						<li class="nk-menu-item"><a href="listTransaction"
							class="nk-menu-link"><span class="nk-menu-icon"><em
									class="icon ni ni-cc-alt2"></em></span><span class="nk-menu-text">Transaction</span></a></li>
						<li class="nk-menu-item has-sub"><a href="#"
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
							</ul></li>
						<li class="nk-menu-item"><a href="listFeedback"
							class="nk-menu-link"><span class="nk-menu-icon"><em
									class="icon ni ni-chat-circle"></em></span><span class="nk-menu-text">Feedback</span></a></li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<!-- .nk-app-root -->
</body>

</html>