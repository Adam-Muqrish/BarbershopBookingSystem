<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hugi Barbershop System - Customer Profile</title>
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/uploads/hugiBarber.jpg">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/assetsAdmin/css/style.css?v1.1.2">

    <!-- Bootstrap JS -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
</head>
<body class="nk-body" data-sidebar-collapse="lg" data-navbar-collapse="lg">
    <div class="nk-app-root">
        <div class="nk-main">
            <%@ include file="/WEB-INF/views/includes/adminNav.jsp" %>

            <div class="nk-wrap">
                <div class="nk-content">
                    <div class="container">
                        <div class="nk-content-inner">
                            <div class="nk-content-body">
                                <div class="nk-block-head">
								    <div class="nk-block-head-between flex-wrap gap g-2 align-items-start">
								        <div class="nk-block-head-content w-100">
								            <div class="d-flex justify-content-center">
								                <div class="media media-huge media-circle">
								                    <c:choose>
								                        <c:when test="${not empty customer.custPicture}">
								                            <img src="${pageContext.request.contextPath}/uploads/${customer.custPicture}" 
								                                 class="img-thumbnail" 
								                                 alt="Customer Picture"
								                                 style="width: 150px; height: 150px; object-fit: cover; border-radius: 50%;">
								                        </c:when>
								                        <c:otherwise>
								                            <img src="${pageContext.request.contextPath}/uploads/default-profile.png" 
								                                 class="img-thumbnail" 
								                                 alt="No Picture"
								                                 style="width: 150px; height: 150px; object-fit: cover; border-radius: 50%;">
								                        </c:otherwise>
								                    </c:choose>
								                </div>
								            </div>
								        </div>
								    </div>
								</div>
								<!-- .nk-block-head -->
								<div style="margin-top: 30px;"></div>
                                <div class="nk-block" style="margin-top: 5rem;">
                                    <div class="tab-content" id="myTabContent">
                                        <div class="tab-pane show active" id="tab-1" tabindex="0">
                                            <div class="card card-gutter-md">
                                                <div class="card-row card-row-lg col-sep col-sep-lg">
                                                    <div class="card-aside">
                                                        <div class="card-body">
                                                            <div class="bio-block">
                                                                <h4 class="bio-block-title">Details</h4>
                                                                <ul class="list-group list-group-borderless small">
																    <li class="list-group-item d-flex">
																        <span class="fw-medium" style="min-width: 130px;">Full Name:</span>
																        <span class="text text-break">${customer.custName}</span>
																    </li>
																    <li class="list-group-item d-flex">
																        <span class="fw-medium" style="min-width: 130px;">Email:</span>
																        <span class="text text-break">${customer.custEmail}</span>
																    </li>
																    <li class="list-group-item d-flex">
																        <span class="fw-medium" style="min-width: 130px;">Phone Number:</span>
																        <span class="text text-break">${customer.custPhoneNumber}</span>
																    </li>
																    <li class="list-group-item d-flex">
																        <span class="fw-medium" style="min-width: 130px;">Loyalty Points:</span>
																        <span class="text">${customer.loyaltyPoints}</span>
																    </li>
																</ul>
                                                            </div><!-- .bio-block -->
                                                        </div><!-- .card-body -->
                                                    </div>
                                                </div><!-- .card-row -->
                                            </div><!-- .card -->
                                        </div><!-- .tab-pane -->
                                    </div><!-- .tab-content -->
                                </div><!-- .nk-block -->
                            </div><!-- .nk-content-body -->
                        </div><!-- .nk-content-inner -->
                    </div><!-- .container -->
                </div><!-- .nk-content -->
            </div><!-- .nk-wrap -->
        </div><!-- .nk-main -->
    </div><!-- .nk-app-root -->
</body>
</html>
