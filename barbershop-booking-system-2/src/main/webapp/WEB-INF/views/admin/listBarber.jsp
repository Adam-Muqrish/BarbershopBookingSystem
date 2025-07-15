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
</head>

<!-- jQuery & Bootstrap Bundle -->
<script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
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
                <div class="nk-content">
                    <div class="container">
                        <div class="nk-content-inner">
                            <div class="nk-content-body">
                                <div class="nk-block-head">
                                    <div class="nk-block-head-between flex-wrap gap g-2">
                                        <div class="nk-block-head-content">
                                            <h2 class="nk-block-title">Barber List</h2>
                                            <nav>
                                                <ol class="breadcrumb breadcrumb-arrow mb-0">
                                                    <li class="breadcrumb-item"><a href="#">Home</a></li>
                                                    <li class="breadcrumb-item"><a href="#">Barber</a></li>
                                                    <li class="breadcrumb-item active" aria-current="page">Barbers List</li>
                                                </ol>
                                            </nav>
                                        </div>
                                        <div class="nk-block-head-content">
										    <c:if test="${sessionScope.staffRole == 'Admin'}">
										        <ul class="d-flex">
										            <li>
										                <a href="#" class="btn btn-md d-md-none btn-primary" data-bs-toggle="modal" data-bs-target="#addUserModal">
										                    <em class="icon ni ni-plus"></em>
										                    <span>Add</span>
										                </a>
										            </li>
										            <li>
										                <a href="#" class="btn btn-primary d-none d-md-inline-flex" data-bs-toggle="modal" data-bs-target="#addUserModal">
										                    <em class="icon ni ni-plus"></em>
										                    <span>Register New Staff</span>
										                </a>
										            </li>
										        </ul>
										    </c:if>
										</div>
                                    </div>
                                </div>

                                <div class="nk-block">
                                    <div class="card">
                                        <table class="datatable-init table" data-nk-container="table-responsive">
                                            <thead class="table-light">
                                                <tr>
                                                    <th><span class="overline-title">Name</span></th>
                                                    <th><span class="overline-title">Phone Number</span></th>
                                                    <th><span class="overline-title">Description</span></th>
                                                    <th><span class="overline-title">Admin In Charge</span></th>
                                                    <th><span class="overline-title">Action</span></th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <c:forEach var="barber" items="${barberList}">
                                                    <tr>
                                                        <td>${barber.staffName}</td>
                                                        <td>${barber.staffPhoneNumber}</td>
                                                        <td>${barber.description}</td>
                                                        <td>${barber.adminName}</td>
                                                        <td>
                                                            <div class="dropdown">
                                                                <a href="#" class="btn btn-sm btn-icon btn-zoom me-n1" data-bs-toggle="dropdown">
                                                                    <em class="icon ni ni-more-v"></em>
                                                                </a>
                                                                <div class="dropdown-menu dropdown-menu-sm dropdown-menu-end">
                                                                    <div class="dropdown-content py-1">
                                                                        <ul class="link-list link-list-hover-bg-primary link-list-md">
                                                                            <li>
                                                                                <a href="ViewBarberDetails?staffId=${barber.staffId}">
                                                                                    <em class="icon ni ni-eye"></em>
                                                                                    <span>View Details</span>
                                                                                </a>
                                                                            </li>
                                                                        </ul>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div> <!-- .nk-content-body -->
                        </div> <!-- .nk-content-inner -->
                    </div> <!-- .container -->
                </div> <!-- .nk-content -->
            </div> <!-- .nk-wrap -->

        </div> <!-- .nk-main -->
    </div> <!-- .nk-app-root -->

    <!-- Register Staff Modal -->
    <div class="modal fade" id="addUserModal" tabindex="-1" aria-labelledby="addUserModalLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered">
            <div class="modal-content">
                <div class="modal-header">
                    <h4 class="modal-title" id="addUserModalLabel">Register New Staff</h4>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form action="registerStaff" method="post" enctype="multipart/form-data">
                        <div class="row g-3">
                            <!-- Email -->
					        <div class="col-lg-12">
					            <div class="form-group">
					                <label class="form-label">Email Address</label>
					                <div class="form-control-wrap">
					                    <input type="email" name="email" class="form-control" placeholder="Email address" required>
					                </div>
					            </div>
					        </div>
					
					        <!-- Password -->
					        <div class="col-lg-6">
					            <div class="form-group">
					                <label class="form-label">Password</label>
					                <div class="form-control-wrap">
					                    <input type="password" name="password" class="form-control" placeholder="Password" required>
					                </div>
					            </div>
					        </div>
					
					        <!-- Confirm Password -->
					        <div class="col-lg-6">
					            <div class="form-group">
					                <label class="form-label">Confirm Password</label>
					                <div class="form-control-wrap">
					                    <input type="password" name="confirmPassword" class="form-control" placeholder="Confirm Password" required>
					                </div>
					            </div>
					        </div>
					
					        <!-- Role -->
					        <div class="col-lg-12">
					            <div class="form-group">
					                <label class="form-label">Role</label>
					                <div class="form-control-wrap">
					                    <select name="role" class="js-select" data-search="true" data-sort="false" required>
					                        <option value="">Select a role</option>
					                        <option value="Admin">Administrator</option>
					                        <option value="Barber">Barber</option>
					                    </select>
					                </div>
					            </div>
					        </div>
                            <div class="col-lg-12">
                                <div class="d-flex gap g-2">
                                    <div class="gap-col">
                                        <button class="btn btn-primary" type="submit">Register</button>
                                    </div>
                                    <div class="gap-col">
                                        <button type="button" class="btn border-0" data-bs-dismiss="modal">Cancel</button>
                                    </div>
                                </div>
                            </div>
                        </div> <!-- .row -->
                    </form>
                </div>
            </div>
        </div>
    </div> <!-- .modal -->

	<!-- SweetAlert2 Library -->
<script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>

<!-- SweetAlert2: Success -->
<c:if test="${not empty sessionScope.registerSuccess}">
    <script>
        Swal.fire({
            icon: 'success',
            title: 'Success',
            text: '${sessionScope.registerSuccess}',
            showConfirmButton: false,
            timer: 2500
        });
    </script>
    <c:remove var="registerSuccess" scope="session"/>
</c:if>

<!-- SweetAlert2: Failed -->
<c:if test="${not empty sessionScope.registerFailed}">
    <script>
        Swal.fire({
            icon: 'error',
            title: 'Failed',
            text: '${sessionScope.errorMessage != null ? sessionScope.errorMessage : "Register Failed!"}',
            showConfirmButton: false,
            timer: 2500
        });
    </script>
    <c:remove var="registerFailed" scope="session"/>
    <c:remove var="errorMessage" scope="session"/>
</c:if>


<script src="/resources/jsAdmin/bundle.js"></script>
    <script src="/resources/jsAdmin/scripts.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.min.js"></script>

</body>
</html>
