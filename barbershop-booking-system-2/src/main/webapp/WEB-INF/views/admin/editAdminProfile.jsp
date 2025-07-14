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

<body>
<div class="nk-app-root">
    <div class="nk-main">
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
                                <form action="editProfileAdmin" method="post">
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
</body>
</html>
