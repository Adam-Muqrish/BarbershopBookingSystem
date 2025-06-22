<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="com.hugi.barbershop.customer.model.Customer"%>
<%
Customer customer = (Customer) session.getAttribute("customer");
String bookingFor = (String) session.getAttribute("bookingFor");
String bookingDate = (String) session.getAttribute("bookingDate");
String selectedTime = (String) session.getAttribute("selectedTime");
String category = (String) session.getAttribute("category");
String selectedBarber = (String) session.getAttribute("selectedBarber");
Double price = (Double) session.getAttribute("price");
if (price == null) {
	price = 0.0;
}
%>
<%@ include file="/WEB-INF/views/includes/header.jsp"%>
<body class="flex flex-col h-screen justify-between bg-yellow-100">
	<main
		class="flex flex-col items-center mx-3 my-4 flex-grow justify-center">
		<div class="bg-white rounded-lg shadow-lg p-6 w-full max-w-2xl">
			<h1 class="text-2xl font-bold mb-4">Payment for Your Appointment</h1>
			<!-- Appointment Details -->
			<div class="bg-gray-100 rounded-md p-6 mb-6">
				<h2 class="text-lg font-semibold mb-2">Appointment Details:</h2>
				<p class="text-sm mb-1">
					<span class="font-semibold">Book For:</span>
					<%=bookingFor != null ? bookingFor : "N/A"%></p>
				<p class="text-sm mb-1">
					<span class="font-semibold">Customer Category:</span>
					<%=category != null ? category : "N/A"%></p>
				<p class="text-sm mb-1">
					<span class="font-semibold">Date:</span>
					<%=bookingDate != null ? bookingDate : "N/A"%></p>
				<p class="text-sm mb-1">
					<span class="font-semibold">Time:</span>
					<%=selectedTime != null ? selectedTime : "N/A"%></p>
				<p class="text-sm mb-1">
					<span class="font-semibold">Barber:</span>
					<%=selectedBarber != null ? selectedBarber : "N/A"%></p>
				<p class="text-sm">
					<span class="font-semibold">Amount:</span> RM
					<%=String.format("%.2f", price)%></p>
			</div>
			<!-- Payment Information -->
			<form action="payment" method="post" id="paymentForm">
				<input type="hidden" name="bookingKey"
					value="<%=session.getAttribute("bookingKey")%>">
				<!-- Hidden fields for bank info, set by JS before submit -->
				<input type="hidden" name="bankName" id="bankNameHidden"> <input
					type="hidden" name="bankHolderName" id="bankHolderNameHidden"
					value="<%=customer != null ? customer.getCustName() : ""%>">
				<div class="bg-gray-100 rounded-md p-6 mb-6">
					<h2 class="text-lg font-semibold mb-2">Payment Information:</h2>
					<p class="text-sm font-semibold mb-2">Select Payment Method:</p>
					<div class="flex items-center mb-2">
						<input type="radio" id="cash" name="payment-method" value="cash"
							class="h-4 w-4 text-blue-600 focus:ring-blue-500 border-gray-300"
							onchange="togglePaymentDetails()" /> <label for="cash"
							class="ml-2 text-sm text-gray-700">Cash</label>
					</div>
					<div class="flex items-center mb-4">
						<input type="radio" id="online-banking" name="payment-method"
							value="online-banking" checked
							class="h-4 w-4 text-blue-600 focus:ring-blue-500 border-gray-300"
							onchange="togglePaymentDetails()" /> <label for="online-banking"
							class="ml-2 text-sm text-gray-700"> Online Banking
							(Toyyib Pay) </label>
					</div>
					<!-- Online Banking Details -->
					<div id="online-banking-details" class="mt-2 text-sm text-gray-700">
						<p>You will be redirected to Toyyib Pay for secure online
							payment.</p>
					</div>
					<!-- Cash Details -->
					<div id="cash-details" class="mt-2 text-sm text-gray-700 hidden">
						<p>Please proceed to the counter and pay in cash upon arrival.</p>
					</div>
				</div>
				<button type="submit" id="payButton"
					class="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 w-full">
					Pay Now</button>
			</form>
			<!-- Loading Modal for Cash Payment -->
			<div id="loadingModal"
				class="fixed inset-0 bg-gray-600 bg-opacity-50 flex items-center justify-center hidden z-50">
				<div class="bg-white p-6 rounded-lg shadow-xl max-w-md w-full">
					<div class="flex flex-col items-center">
						<div
							class="w-16 h-16 border-t-4 border-blue-500 border-solid rounded-full animate-spin mb-4"></div>
						<h3 class="text-lg font-medium text-gray-900 mb-2">Processing
							Your Payment</h3>
						<p class="text-gray-600 text-center">Please wait while we
							confirm your cash payment...</p>
					</div>
				</div>
			</div>
			<!-- Mock Bank Payment Modal (bank selection here) -->
			<div id="bankModal"
				class="fixed inset-0 bg-gray-600 bg-opacity-50 flex items-center justify-center hidden z-50">
				<div class="bg-white p-6 rounded-lg shadow-xl max-w-md w-full">
					<div class="flex justify-between items-center mb-4">
						<h3 class="text-lg font-medium text-gray-900">Toyyib Pay
							Gateway</h3>
						<button type="button" id="closeBank"
							class="text-gray-400 hover:text-gray-500">
							<svg class="h-6 w-6" fill="none" viewBox="0 0 24 24"
								stroke="currentColor">
					         	<path stroke-linecap="round" stroke-linejoin="round"
									stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
					        </svg>
						</button>
					</div>
					<div class="border-t border-gray-200 pt-4">
						<div class="mb-4">
							<p class="font-semibold text-sm mb-2">Amount to Pay:</p>
							<p class="text-lg font-bold">
								RM
								<%=String.format("%.2f", price)%></p>
						</div>
						<div class="mb-4">
							<label class="font-semibold text-sm mb-2">Select Bank:</label> <select
								id="bankNameModal"
								class="w-full px-3 py-2 border border-gray-300 rounded-md">
								<option>Maybank</option>
								<option>CIMB Bank</option>
								<option>Public Bank</option>
								<option>Hong Leong Bank</option>
								<option>RHB Bank</option>
							</select>
						</div>
						<button id="confirmBankPayment"
							class="w-full bg-green-600 text-white px-4 py-2 rounded hover:bg-green-700">
							Confirm Payment</button>
					</div>
				</div>
			</div>
			<!-- JavaScript to toggle payment details and validate form -->
			<script>
				function togglePaymentDetails() {
			        const isCash = document.getElementById('cash').checked;
			        document.getElementById('cash-details').classList.toggle('hidden', !isCash);
			        document.getElementById('online-banking-details').classList.toggle('hidden', isCash);
			        // Update button text based on payment method
			        const payButton = document.getElementById('payButton');
			        payButton.innerText = isCash ? 'Confirm Cash Payment' : 'Pay Now';
			    }
			    document.getElementById('paymentForm').addEventListener('submit', function(e) {
			        e.preventDefault();
			        const isCash = document.getElementById('cash').checked;
			        const isOnlineBanking = document.getElementById('online-banking').checked;
			        if (!isCash && !isOnlineBanking) {
			            alert('Please select a payment method');
			            return;
			        }
			        if (isCash) {
			            document.getElementById('loadingModal').classList.remove('hidden');
			            setTimeout(() => {
			                this.submit();
			            }, 5000);
			        } else {
			            document.getElementById('bankModal').classList.remove('hidden');
			        }
			    });
			    document.getElementById('closeBank').addEventListener('click', function() {
			        document.getElementById('bankModal').classList.add('hidden');
			    });
			    document.getElementById('confirmBankPayment').addEventListener('click', function() {
			        // Set selected bank to hidden field before submit
			        const selectedBank = document.getElementById('bankNameModal').value;
			        document.getElementById('bankNameHidden').value = selectedBank;
			        // Customer name is already set in hidden field
			        this.innerHTML = 'Processing...';
			        this.disabled = true;
			        setTimeout(() => {
			            document.getElementById('bankModal').classList.add('hidden');
			            document.getElementById('paymentForm').submit();
			        }, 2000);
			    });
			    window.onload = togglePaymentDetails;
			    window.addEventListener('pageshow', function(event) {
			        if (event.persisted) {
			            window.location.reload();
			        }
			    });
			</script>
		</div>
	</main>
</body>