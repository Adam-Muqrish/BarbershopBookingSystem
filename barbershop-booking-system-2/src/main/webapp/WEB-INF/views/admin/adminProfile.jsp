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
                                    <div class="nk-block-head-content">
                                        <div class="d-flex flex-column flex-md-row align-items-md-center">
                                            <div class="media media-huge media-circle">
                                                <img src="/resources/uploads/avatar/a.jpg" class="img-thumbnail" alt="Profile Picture">
                                            </div>
                                            <div class="mt-3 mt-md-0 ms-md-3">
                                                <h3 class="title mb-1">${staff.staffName}</h3>
                                                <span class="small">
                                                    ${staff.staffRole == 'Admin' ? 'Administrator' : staff.staffRole}
                                                </span>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="gap-col">
                                        <ul class="d-flex gap g-2">
                                            <li class="d-none d-md-block">
                                                <a href="editProfileAdmin" class="btn btn-soft btn-primary">
                                                    <em class="icon ni ni-edit"></em><span>Edit Profile</span>
                                                </a>
                                            </li>
                                            <li class="d-md-none">
                                                <a href="./html/user-manage/user-edit.html" class="btn btn-soft btn-primary btn-icon">
                                                    <em class="icon ni ni-edit"></em>
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>

                            <div class="nk-block">
                                <div class="tab-content">
                                    <div class="tab-pane show active">
                                        <div class="card card-gutter-md">
                                            <div class="card-row card-row-lg col-sep col-sep-lg">
                                                <div class="card-aside">
                                                    <div class="card-body">
                                                        <div class="bio-block">
                                                            <h4 class="bio-block-title">Details</h4>
                                                            <ul class="list-group list-group-borderless small">
                                                                <li class="list-group-item">
                                                                    <span class="title fw-medium w-40 d-inline-block">Full Name:</span>
                                                                    <span class="text">${staff.staffName}</span>
                                                                </li>
                                                                <li class="list-group-item">
                                                                    <span class="title fw-medium w-40 d-inline-block">Email:</span>
                                                                    <span class="text">${staff.staffEmail}</span>
                                                                </li>
                                                                <li class="list-group-item">
                                                                    <span class="title fw-medium w-40 d-inline-block">Phone Number:</span>
                                                                    <span class="text">${staff.staffPhoneNumber}</span>
                                                                </li>
                                                            </ul>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="card-content col-sep">
                                                    <div class="card-body">
                                                        <div class="bio-block">
                                                            <h4 class="bio-block-title">Description</h4>
                                                            <p><strong>Hey, I'm ${staff.staffName}.</strong> ${staff.description}</p>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div> <!-- .tab-pane -->
                                </div> <!-- .tab-content -->
                            </div> <!-- .nk-block -->
                        </div>
                    </div>
                </div>
            </div> <!-- .nk-wrap -->
        </div> <!-- .nk-main -->
    </div> <!-- .nk-app-root -->
</div>

<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
<c:if test="${param.success == 'true'}">
    <script>
        Swal.fire({
          icon: 'success',
          title: 'Profile Updated!',
          text: 'Save Changes Successfully!',
        });
    </script>
</c:if>

</body>
</html>
