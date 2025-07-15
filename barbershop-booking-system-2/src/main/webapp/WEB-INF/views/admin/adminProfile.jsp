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
    <style>
        .nk-wrap {
          padding-top: 60px;
        }
        .first-row {
            margin-top: 30px;
        }
        .profile-header {
            display: flex;
            justify-content: space-between;
            align-items: center;
            margin-bottom: 20px;
            width: 100%;
        }
        .profile-info {
            display: flex;
            flex-direction: column;
            align-items: flex-start;
        }
        .profile-image {
            width: 150px;
            height: 150px;
            border-radius: 50%;
            object-fit: cover;
            border: 3px solid #fff;
            box-shadow: 0 2px 10px rgba(0,0,0,0.1);
            margin-bottom: 15px;
        }
        .centered-image {
            display: flex;
            justify-content: center;
            margin-bottom: 15px;
        }
    </style>
</head>

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
                            <div class="centered-image">
							    <img src="<%=request.getContextPath()%>/resources/uploads/avatar/${staff.staffPicture}" class="profile-image" alt="Profile Picture">
							</div>
                            
                            <div class="profile-header">
                                <div class="profile-info">
                                    <h3 class="title mb-1"> ${staff.staffRole == 'Admin' ? 'Administrator' : staff.staffRole}</h3>
                                </div>
                                <div>
                                    <a href="editProfileAdmin" class="btn btn-soft btn-primary">
                                        <em class="icon ni ni-edit"></em><span>Edit Profile</span>
                                    </a>
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

<script src="/resources/jsAdmin/bundle.js"></script>
    <script src="/resources/jsAdmin/scripts.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"></script>

</body>
</html>