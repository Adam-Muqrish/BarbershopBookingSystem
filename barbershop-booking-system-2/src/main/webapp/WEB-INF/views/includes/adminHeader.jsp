<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Hugi Barbershop System</title>
<link rel="shortcut icon" href="/resources/uploads/hugiBarber1.png">
<link rel="stylesheet"
	href="/resources/assetsAdmin/css/style.css?v1.1.2">
</head>
<body>
	<!-- adminHeader.jsp -->
	<div class="nk-header nk-header-fixed">
		<div class="container-fluid">
			<div class="nk-header-wrap justify-end">
				<div class="nk-header-tools">
					<ul class="nk-quick-nav ms-2">
						<li class="dropdown">
							<a href="#" class="dropdown-toggle" data-bs-toggle="dropdown">
								<div class="media media-md media-circle">
									<img src="/resources/uploads/avatar/a.jpg" alt="" class="img-thumbnail">
								</div>
							</a>
							<div class="dropdown-menu dropdown-menu-md dropdown-menu-end">
								<div class="dropdown-content py-3 border-bottom border-light">
									<div class="media-group">
										<div class="media media-xl media-middle media-circle">
											<img src="/resources/uploads/avatar/a.jpg" alt="" class="img-thumbnail">
										</div>
										<div class="media-text">
											<div class="lead-text">Wesley Burland</div>
											<span class="sub-text">Administrator</span>
										</div>
									</div>
								</div>
								<div class="dropdown-content py-3 border-bottom border-light">
									<ul class="link-list">
										<li><a href="./html/adminProfile.html">
											<em class="icon ni ni-user"></em> <span>My Profile</span></a></li>
									</ul>
								</div>
								<div class="dropdown-content py-3">
									<ul class="link-list">
										<li><a href="./html/adminLogin.html">
											<em class="icon ni ni-signout"></em> <span>Log Out</span></a></li>
									</ul>
								</div>
							</div>
						</li>
					</ul>
				</div> <!-- .nk-header-tools -->
			</div> <!-- .nk-header-wrap -->
		</div> <!-- .container-fluid -->
	</div>

	<!-- JavaScript Section -->
	<script src="./assets/js/charts/ecommerce-chart.js"></script>
	<script src="./resources/jsAdmin/bundle.js"></script>
	<script src="./resources/jsAdmin/scripts.js"></script>

</body>
</html>