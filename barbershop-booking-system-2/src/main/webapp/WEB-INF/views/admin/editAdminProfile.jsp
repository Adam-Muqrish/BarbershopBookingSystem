<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Edit Admin Profile - Hugi Barbershop</title>
    <link rel="shortcut icon" href="/uploads/hugiBarber.jpg">
    <link rel="stylesheet" href="/resources/assetsAdmin/css/style.css?v1.1.2">
</head>
<style>
    .nk-wrap {
      padding-top: 60px; /* Changed from 0px !important */
    }
    .first-row {
        margin-top: 30px; /* Added space below header */
    }
</style>
<body>
<div class="nk-app-root">
    <div class="nk-main">
		<%@ include file="/WEB-INF/views/includes/adminHeader.jsp"%>
        <%@ include file="/WEB-INF/views/includes/adminNav.jsp" %>

        <div class="nk-wrap">
            <div class="nk-content">
                <div class="container">
                    <div class="nk-content-inner">
                        <div class="nk-content-body">

                            <div class="nk-block-head">
                                <h2>Edit Admin Profile</h2>
                            </div>

                            <div class="nk-block">
                                <form action="editProfileAdmin" method="post" enctype="multipart/form-data">
                                    <input type="hidden" name="staffId" value="${staff.staffId}">

                                    <div class="form-group">
                                        <label>Full Name</label>
                                        <input type="text" name="staffName" value="${staff.staffName}" required class="form-control">
                                    </div>

                                    <div class="form-group">
                                        <label>Email</label>
                                        <input type="email" name="staffEmail" value="${staff.staffEmail}" required class="form-control">
                                    </div>

                                    <div class="form-group">
                                        <label>Phone Number</label>
                                        <input type="text" name="staffPhoneNumber" value="${staff.staffPhoneNumber}" required class="form-control">
                                    </div>
                                    
                                    <div class="form-group">
									    <label>Profile Picture</label>
									    <input type="file" name="staffPicture" accept="image/*" class="form-control">
									</div>

                                    <div class="form-group">
                                        <label>Description</label>
                                        <textarea name="description" rows="4" class="form-control">${staff.description}</textarea>
                                    </div>

                                    <button type="submit" class="btn btn-primary mt-3">Save Changes</button>
                                    <a href="adminProfile" class="btn btn-secondary mt-3">Cancel</a>
                                </form>
                            </div>

                        </div>
                    </div>
                </div>
            </div> 
        </div>
    </div>
</div>

<script src="/resources/jsAdmin/bundle.js"></script>
    <script src="/resources/jsAdmin/scripts.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"></script>
    
</body>
</html>
