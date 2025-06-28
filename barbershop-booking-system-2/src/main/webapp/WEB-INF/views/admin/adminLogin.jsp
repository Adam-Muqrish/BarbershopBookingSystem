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
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.0/css/all.min.css">
</head>
<body class="nk-body" data-sidebar-collapse="lg"
	data-navbar-collapse="lg">
	<div class="nk-app-root">
		<div class="nk-main">
			<%-- <%@ include file="/WEB-INF/views/includes/adminNav.jsp"%> --%>

			<div class="nk-wrap">
				<%-- <%@ include file="/WEB-INF/views/includes/adminHeader.jsp"%> --%>
				<div class="nk-main">
					<div class="nk-wrap align-items-center justify-content-center">
						<div class="container p-2 p-sm-4">
							<div class="wide-xs mx-auto">
								<div class="text-center mb-5">
									<div class="brand-logo mb-1">
										<a href="./html/index-ecommerce.html" class="logo-link">
											<div class="logo-wrap">
												<img class="logo-img logo-light"
													src="./images/hugiBarber.jpg"
													srcset="./images/logo2x.png 2x" alt=""> <img
													class="logo-img logo-dark" src="./images/hugiBarber1.jpg"
													srcset="./images/logo-dark2x.png 2x" alt=""> <img
													class="logo-img logo-icon" src="./images/hugiBarber1.jpg"
													srcset="./images/logo-icon2x.png 2x" alt="">
											</div>
										</a>
									</div>
								</div>
								<div class="card card-gutter-lg rounded-4 card-auth">
									<div class="card-body">
										<div class="nk-block-head">
											<div class="nk-block-head-content">
												<h3 class="nk-block-title mb-1">Login to Barber Account</h3>
												<p class="small">Please sign-in to your account and
													start the adventure.</p>
											</div>
										</div>
										<form action="adminLogin" method="post">
										<input type="hidden" name="action" value="staffLogin">
											<div class="row gy-3">
											<% String error = (String) request.getAttribute("loginError");
											   if (error != null) { %>
											   <div class="col-12">
											       <div class="alert alert-danger" style="color: red; font-weight: bold;">
											           <%= error %>
											       </div>
											   </div>
											<% } %>
												<div class="col-12">
													<div class="form-group">
														<label for="username" class="form-label">Email</label>
														<div class="form-control-wrap">
															<input type="text" class="form-control" name="email" id="username" placeholder="Enter username">
														</div>
													</div>
													<!-- .form-group -->
												</div>
												<div class="col-12">
													<div class="form-group">
														<label for="password" class="form-label">Password</label>
														<div class="form-control-wrap">
															<input type="password" class="form-control" name="password" id="password" placeholder="Enter password">
														</div>
													</div>
													<!-- .form-group -->
												</div>
												<!-- 
                                        <div class="col-12">
                                            <div class="d-flex flex-wrap justify-content-between">
                                                <div class="form-check form-check-sm">
                                                    <input class="form-check-input" type="checkbox" value="" id="rememberMe">
                                                    <label class="form-check-label" for="rememberMe"> Remember Me </label>
                                                </div>
                                                <a href="./html/auths/auth-reset-classic.html" class="small">Forgot Password?</a>
                                            </div>
                                        </div>
                                         -->	
												<div class="col-12">
													<div class="d-grid">
														<button type="submit" class="btn btn-primary">
															Login</button>
													</div>
												</div>
											</div>
											<!-- .row -->
										</form>
										<!-- 	
	                                <div class="my-3 text-center">
	                                    <h6 class="overline-title overline-title-sep"><span>OR</span></h6>
	                                </div>
	                                <div class="row g-2">
	                                    <div class="col-sm-6">
	                                        <a href="#" class="btn btn-outline-light w-100">
	                                            <img src="./images/icon/d.png" alt="" class="icon">
	                                            <span class="fw-medium">With Google</span>
	                                        </a>
	                                    </div>
	                                    <div class="col-sm-6">
	                                        <a href="#" class="btn btn-outline-light w-100">
	                                            <img src="./images/icon/b.png" alt="" class="icon">
	                                            <span class="fw-medium">With Facebook</span>
	                                        </a>
	                                    </div>
	                                </div>
                                 -->
										<!-- .row -->
									</div>
									<!-- .card-body -->
								</div>
								<!-- .card -->
								<!-- 
                        <div class="text-center mt-5">
                            <p class="small">Don't have an account? <a href="./html/auths/auth-register-classic.html">Register</a></p>
                        </div>
                         -->
							</div>
							<!-- .col -->
						</div>
						<!-- .container -->
					</div>
				</div>
			</div>
			<!-- .nk-wrap -->
		</div>
		<!-- .nk-main -->
	</div>
	<!-- .nk-app-root -->


</body>
</html>