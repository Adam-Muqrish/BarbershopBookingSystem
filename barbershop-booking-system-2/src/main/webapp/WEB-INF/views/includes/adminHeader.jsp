<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
	<div class="nk-header nk-header-fixed" style="position: fixed; top: 0; left: 0; width: 100%; z-index: 1000; background: white;">
    <div class="container-fluid px-0">
        <div class="nk-header-wrap d-flex justify-content-end align-items-center" >

            <!-- Avatar + Dropdown -->
            <ul class="nk-quick-nav d-flex align-items-center mb-0">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-bs-toggle="dropdown" style="display: flex; align-items: center; gap: 10px;">
                        <div class="media media-md media-circle">
                            <img src="/resources/uploads/avatar/a.jpg" alt="User Avatar" 
                                 class="img-thumbnail" 
                                 style="border-radius: 50%; width: 40px; height: 40px; object-fit: cover; border: 2px solid #fff;">
                        </div>
                    </a>
                    <div class="dropdown-menu dropdown-menu-md dropdown-menu-end mt-2" style="min-width: 220px;">
                        <div class="dropdown-content py-3 border-bottom border-light">
                            <div class="media-group d-flex align-items-center">
                                <div class="media media-xl media-middle media-circle me-3">
                                    <img src="/resources/uploads/avatar/a.jpg" alt="" 
                                         class="img-thumbnail" 
                                         style="border-radius: 50%; width: 50px; height: 50px; object-fit: cover;">
                                </div>
                                <div class="media-text">
                                    <div class="lead-text fw-bold"> ${sessionScope.staffName}</div>
                                    <c:choose>
								        <c:when test="${sessionScope.staffRole == 'Admin'}">
								            <span class="sub-text text-muted">Administrator</span>
								        </c:when>
								        <c:otherwise>
								            <span class="sub-text text-muted">${sessionScope.staffRole}</span>
								        </c:otherwise>
								    </c:choose>
                                </div>
                            </div>
                        </div>
                        <div class="dropdown-content py-2 border-bottom border-light">
                            <ul class="link-list">
                                <li>
                                    <a href="viewAdminProfile">
                                        <em class="icon ni ni-user"></em>
                                        <span>My Profile</span>
                                    </a>
                                </li>
                            </ul>
                        </div>
                        <div class="dropdown-content py-2">
                            <ul class="link-list">
                                <li>
                                    <a href="logout">
                                        <em class="icon ni ni-signout"></em>
                                        <span>Log Out</span>
                                    </a>
                                </li>
                            </ul>
                        </div>
                    </div>
                </li>
            </ul>
            <!-- End Avatar -->
        </div>
    </div>
</div>

	<!-- JavaScript Section -->
    <!-- Moved scripts to BOTTOM of page -->
    <script src="/resources/jsAdmin/bundle.js"></script>
    <script src="/resources/jsAdmin/scripts.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"></script>
	

</body>
</html>