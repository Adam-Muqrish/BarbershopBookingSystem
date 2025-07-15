<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hugi Barbershop System</title>
    <link rel="shortcut icon" href="/uploads/hugiBarber.jpg">
    <link rel="stylesheet" href="/resources/assetsAdmin/css/style.css?v1.1.2">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    
</head>
<!-- jQuery (optional jika Bootstrap v5 tak perlu) -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

<!-- Bootstrap Bundle JS (termasuk Popper.js) -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"></script>
<style>
    .nk-wrap {
      padding-top: 60px; /* Changed from 0px !important */
    }
    .first-row {
        margin-top: 30px; /* Added space below header */
    }
</style>
<body class="nk-body" data-sidebar-collapse="lg" data-navbar-collapse="lg">
    <div class="nk-app-root">
        <div class="nk-main">
			<%@ include file="/WEB-INF/views/includes/adminHeader.jsp"%>
            <%@ include file="/WEB-INF/views/includes/adminNav.jsp" %>

            <div class="nk-wrap">
                <%-- <%@ include file="/WEB-INF/views/includes/adminHeader.jsp"%> --%>
                 <div class="nk-content">
                    <div class="container">
                        <div class="nk-content-inner">
                            <div class="nk-content-body">
                                <div class="nk-block-head">
                                    <div class="nk-block-head-between flex-wrap gap g-2">
                                        <div class="nk-block-head-content">
                                            <h2 class="nk-block-title">Feedback List</h1>
                                                <nav>
                                                    <ol class="breadcrumb breadcrumb-arrow mb-0">
                                                        <li class="breadcrumb-item"><a href="#">Home</a></li>
                                                        <li class="breadcrumb-item"><a href="#">Feedback</a></li>
                                                        <li class="breadcrumb-item active" aria-current="page">Feedback List</li>
                                                    </ol>
                                                </nav>
                                        </div>
                                        <div class="nk-block-head-content">
                                            <ul class="d-flex">
                                                <li>
                                                    <a href="#" class="btn btn-md d-md-none btn-primary" data-bs-toggle="modal" data-bs-target="#addUserModal">
                                                        <em class="icon ni ni-plus"></em>
                                                        <span>Add</span>
                                                    </a>
                                                </li>
                                            </ul>
                                        </div>
                                    </div><!-- .nk-block-head-between -->
                                </div><!-- .nk-block-head -->
                                <div class="nk-block">
                                    <div class="card">
                                        <table class="datatable-init table" data-nk-container="table-responsive">
                                            <thead class="table-light">
                                                <tr>
                                                	<th class="tb-col">
                                                        <span class="overline-title">Name</span>
                                                    </th>
                                                    <th class="tb-col">
                                                        <span class="overline-title">Comments</span>
                                                    </th>
                                                    <th class="tb-col">
                                                        <span class="overline-title">Ratings</span>
                                                    </th>
                                                    <!-- <th class="tb-col tb-col-end" data-sortable="false">
                                                        <span class="overline-title">Action</span>
                                                    </th> -->
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="fb" items="${feedbackList}">
										            <tr>
										                <td>${fb.customerName}</td>
										                <td>${fb.comments}</td>
										                <td>
														    <c:forEach begin="1" end="5" var="i">
														        <i class="${i <= fb.rating ? 'fas fa-star text-warning' : 'far fa-star text-warning'}"></i>
														    </c:forEach>
														</td>
										                <%-- <td class="tb-col tb-col-end">
											                <div class="dropdown">
											                    <a href="#" class="btn btn-sm btn-icon btn-zoom me-n1" data-bs-toggle="dropdown">
											                        <em class="icon ni ni-more-v"></em>
											                    </a>
											                    <div class="dropdown-menu dropdown-menu-sm dropdown-menu-end">
											                        <div class="dropdown-content py-1">
											                            <ul class="link-list link-list-hover-bg-primary link-list-md">
											                                <li>
											                                    <a href="feedbackDetail?feedbackId=${fb.feedbackId}">
											                                        <em class="icon ni ni-eye"></em>
											                                        <span>View Details</span>
											                                    </a>
											                                </li>
											                            </ul>
											                        </div>
											                    </div>
											                </div>
											            </td> --%>
										            </tr>
										        </c:forEach>
                                            </tbody>
                                        </table>
                                    </div><!-- .card -->
                                </div><!-- .nk-block -->
                            </div>
                        </div>
                    </div>
                </div>
            </div> <!-- .nk-wrap -->
        </div> <!-- .nk-main -->
    </div> <!-- .nk-app-root -->
    <script src="/resources/jsAdmin/bundle.js"></script>
    <script src="/resources/jsAdmin/scripts.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"></script>
</body>
</html>