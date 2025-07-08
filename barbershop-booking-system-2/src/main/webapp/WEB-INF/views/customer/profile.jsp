<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.hugi.barbershop.customer.model.Customer"%>
<%@ page import="java.io.File"%>
<%@ include file="/WEB-INF/views/includes/header.jsp"%>

<body class="flex flex-col h-screen justify-between bg-yellow-100">
	<%@ include file="/WEB-INF/views/includes/nav.jsp"%>

	<%
        if (customer == null) {
    %>
	<main class="flex-grow flex items-center justify-center">
		<p class="text-red-500 text-lg">Error: Customer data not found.</p>
	</main>
	<%
        } else {
    %>
	<main class="flex flex-col items-center mx-3 my-20 flex-grow">
		<div
			class="w-full max-w-3xl p-6 rounded-2xl bg-gradient-to-br from-white/70 to-white/90 shadow-xl backdrop-blur-md border border-white/30">
			<h1
				class="text-2xl font-extrabold mb-6 text-gray-800 flex items-center gap-2">
				<svg xmlns="http://www.w3.org/2000/svg" class="h-9 w-9" fill="none"
					viewBox="0 0 24 24" stroke="currentColor" style="color: #101820;">
                        <path stroke-linecap="round"
						stroke-linejoin="round" stroke-width="2"
						d="M5.121 17.804A13.937 13.937 0 0112 15c2.5 0 4.847.655 6.879 1.804M15 10a3 3 0 11-6 0 3 3 0 016 0z" />
                    </svg>
				My Profile
			</h1>

			<div class="flex flex-col md:flex-row gap-6">
				<!-- Left box -->
				<div
					class="bg-gray-100 rounded-xl p-4 flex flex-col items-center w-full md:w-1/3">
					<div
						class="w-20 h-20 bg-gray-300 rounded-full mb-3 overflow-hidden flex items-center justify-center">
						<%
						if (!showDefaultAvatar) {
						%>
						<img
						    src="<%=request.getContextPath()%>/resources/uploads/<%=picture%>"
						    class="w-full h-full rounded-full object-cover"
						    alt="Profile picture" />
						<%
						} else {
						%>
						<svg class="w-12 h-12 text-gray-400" fill="currentColor"
							viewBox="0 0 20 20" xmlns="http://www.w3.org/2000/svg">
					            <path fill-rule="evenodd"
								d="M10 9a3 3 0 100-6 3 3 0 000 6zm-7 9a7 7 0 1114 0H3z"
								clip-rule="evenodd"></path>
					        </svg>
						<%
						}
						%>
					</div>
					<p class="text-sm font-medium text-gray-600">Loyalty Points</p>
					<p
						class="text-2xl font-extrabold text-green-600 transition-all duration-300 transform hover:scale-110 hover:text-amber-500">
						${loyaltyPoints}</p>
				</div>

				<!-- Right box -->
				<div class="flex-1 space-y-4">
					<div>
						<p class="text-sm text-gray-500">Name</p>
						<p class="text-lg font-semibold text-gray-800"><%=customer.getCustName()%></p>
					</div>
					<div>
						<p class="text-sm text-gray-500">Phone Number</p>
						<p class="text-lg font-semibold text-gray-800"><%=customer.getCustPhoneNumber()%></p>
					</div>
					<div>
						<p class="text-sm text-gray-500">Email</p>
						<p class="text-lg font-semibold text-gray-800"><%=customer.getCustEmail()%></p>
					</div>
				</div>
			</div>

			<a href="profile-management" class="block mt-6">
				<button
					class="w-full bg-green-500 text-white border border-green-500 hover:bg-white hover:text-green-500 rounded transition duration-300 flex-1 cursor-pointer py-2 px-4 shadow-md">
					Edit Profile</button>
			</a>
		</div>
	</main>
	<%
        }
    %>

	<%@ include file="/WEB-INF/views/includes/footer.jsp"%>

	<!-- Scroll to top button -->
	<button onclick="window.scrollTo({ top: 0, behavior: 'smooth' });"
		class="fixed bottom-5 right-5 bg-[#101820] text-white p-3 rounded-full shadow-lg hover:bg-gray-800 transition z-50">
		↑</button>
</body>
