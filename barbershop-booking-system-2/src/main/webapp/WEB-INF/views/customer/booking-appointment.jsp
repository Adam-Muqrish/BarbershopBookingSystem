<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="com.hugi.barbershop.customer.model.Customer"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql" %>
<%@ taglib prefix="x" uri="http://java.sun.com/jsp/jstl/xml" %>
<%@ include file="/WEB-INF/views/includes/header.jsp"%>
<body class="flex flex-col h-screen justify-between bg-yellow-100">
	<%@ include file="/WEB-INF/views/includes/nav.jsp"%>
	<main class="flex flex-col items-center mx-3 my-12 flex-grow">
		<div class="bg-white rounded-lg shadow-lg p-6 w-full max-w-5xl mx-auto">
			<h1 class="text-3xl font-bold mb-6">Book Appointment</h1>
			<form action="booking" method="post">
				<!-- Booking For -->
				<div class="mb-6">
					<label class="block mb-1 font-semibold" for="booking-for">Booking
						For:</label> <input id="booking-for" name="booking-for" type="text"
						class="w-full border rounded px-3 py-2 shadow focus:outline-none focus:ring-2 focus:ring-yellow-400" />
				</div>
				<!-- Date -->
				<label class="block font-semibold mb-2">Select Date:</label>
				<div class="relative mb-6">
					<input id="date" name="date" type="date"
						class="w-full border rounded px-3 py-2 shadow appearance-none focus:outline-none focus:ring-2 focus:ring-yellow-400"
						value="${selectedDate}" min="${selectedDate}" /> <span
						class="absolute right-3 top-2.5 text-gray-500"> <i
						class="fa fa-calendar"></i>
					</span>
				</div>
				<!-- Time Selection -->
				<div class="mb-6">
					<label class="block mb-1 font-semibold" for="selected-time">Booking
						Time:</label> <input id="selected-time" name="selected-time" type="text"
						class="w-full border rounded px-3 py-2 shadow focus:outline-none focus:ring-2 focus:ring-yellow-400 mb-2"
						value="--:-- --" readonly />
					<div class="grid grid-cols-1 gap-1">
						<c:set var="slots"
							value="${fn:split('10:00 am,10:30 am,11:00 am,11:30 am,12:00 pm,12:30 pm,1:00 pm,1:30 pm,2:00 pm,2:30 pm,3:00 pm,3:30 pm,4:00 pm,4:30 pm,5:00 pm,5:30 pm,6:00 pm,6:30 pm,7:00 pm,7:30 pm,8:00 pm,8:30 pm,9:00 pm,9:30 pm', ',')}" />
						<div class="grid grid-cols-6 gap-1">
							<c:forEach var="slot" items="${slots}">
								<label> <input type="radio" name="slot" value="${slot}"
									class="mr-1"
									<c:if test="${not empty unavailableBarbersBySlot[fn:trim(slot)] && fn:length(unavailableBarbersBySlot[fn:trim(slot)]) eq fn:length(barbers)}">disabled</c:if> />
									${slot}
								</label>
							</c:forEach>
						</div>
					</div>
				</div>
				<!-- Customer Category -->
				<div class="mb-6">
					<label class="block mb-1 font-semibold" for="category">Customer
						Category:</label>
					<div class="relative">
						<select id="category" name="category"
							class="w-full border rounded px-3 py-2 pr-10 shadow appearance-none focus:outline-none focus:ring-2 focus:ring-yellow-400">
							<option>Child</option>
							<option>Teen</option>
							<option>Adult</option>
							<option>Senior</option>
						</select> <span
							class="pointer-events-none absolute right-3 top-2.5 text-gray-400">
							<svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5"
								fill="none" viewBox="0 0 24 24" stroke="currentColor">
						        <path stroke-linecap="round" stroke-linejoin="round"
									stroke-width="2" d="M19 9l-7 7-7-7" />
						      </svg>
						</span>
					</div>
				</div>
				<!-- Select Barber -->
				<div class="mb-6">
					<label class="block mb-1 font-semibold" for="barber">Select
						Barber:</label>
					<div class="relative">
						<select id="barber" name="barber"
							class="w-full border rounded px-3 py-2 pr-10 shadow appearance-none focus:outline-none focus:ring-2 focus:ring-yellow-400">
							<option selected disabled>-- Select Barber --</option>
							<c:forEach var="barber" items="${barbers}">
								<c:if test="${barber ne null and not empty barber.staffId and not empty barber.staffName}">
									<option value="${barber.staffId}">${barber.staffName}</option>
								</c:if>
							</c:forEach>
						</select> <span
							class="pointer-events-none absolute right-3 top-2.5 text-gray-400">
							<svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5"
								fill="none" viewBox="0 0 24 24" stroke="currentColor">
						        <path stroke-linecap="round" stroke-linejoin="round"
									stroke-width="2" d="M19 9l-7 7-7-7" />
						      </svg>
						</span>
					</div>
				</div>
				<!-- Button -->
				<div class="flex">
					<button type="submit"
						class="bg-blue-500 text-white hover:bg-white hover:text-blue-500 border border-blue-500 px-5 py-2 rounded shadow focus:outline-none focus:ring-2 focus:ring-blue-400 cursor-pointer">
						Book Appointment</button>
				</div>
			</form>
		</div>
	</main>
	<script>
	    // Initialize unavailable barbers data
	    let unavailableBarbersBySlot;
	    try {
	        unavailableBarbersBySlot = JSON.parse('${fn:replace(unavailableBarbersBySlotJson, "'", "\\'")}');
	    } catch (e) {
	        console.error('Error parsing JSON:', e);
	        unavailableBarbersBySlot = {};
	    }
	
	    // Date change handler
	    document.getElementById('date').addEventListener('change', function() {
		    const selectedDate = this.value;
		    const today = new Date().toISOString().split('T')[0];
		
		    // Validate selected date
		    if (selectedDate < today) {
		        alert("Cannot select a past date");
		        this.value = today;
		        return;
		    }
		    
		    // Make AJAX call instead of page reload
		    fetch('booking?date=' + selectedDate + '&ajax=true')
		        .then(response => response.json())
		        .then(data => {
		            // Update the unavailable barbers data
		            unavailableBarbersBySlot = data.unavailableBarbersBySlot;
		            
		            // Reset all time slots
		            document.querySelectorAll('input[name="slot"]').forEach(radio => {
		                radio.checked = false;
		                const isFullyBooked = data.unavailableBarbersBySlot[radio.value] && 
		                    data.unavailableBarbersBySlot[radio.value].length === data.totalBarbers;
		                radio.disabled = isFullyBooked;
		            });
		            
		            // Reset selected time and barber
		            document.getElementById('selected-time').value = '--:-- --';
		            document.getElementById('barber').selectedIndex = 0;
		        })
		        .catch(error => console.error('Error:', error));
		});
	
	    // Slot selection handler
	    function updateBarberAvailability(slot) {
	        try {
	            const unavailable = unavailableBarbersBySlot[slot] || [];
	            const barberSelect = document.getElementById('barber');
	            const selectedDate = document.getElementById('date').value;
	            
	            // Reset barber selection
	            barberSelect.selectedIndex = 0;
	            
	            // Update barber availability
	            Array.from(barberSelect.options).forEach(opt => {
	                if (opt.value === "-- Select Barber --") {
	                    opt.style.display = "";
	                    return;
	                }
	                
	                const isUnavailable = unavailable.includes(opt.value);
	                opt.style.display = isUnavailable ? "none" : "";
	                if (isUnavailable) {
	                    opt.disabled = true;
	                } else {
	                    opt.disabled = false;
	                }
	            });
	
	            // Update selected time display
	            const selectedTimeInput = document.getElementById('selected-time');
	            if (selectedTimeInput) {
	                selectedTimeInput.value = slot;
	            }
	        } catch (e) {
	            console.error('Error updating barber availability:', e);
	        }
	    }
	
	    // Add listeners to radio buttons
	    document.querySelectorAll('input[name="slot"]').forEach(radio => {
	        radio.addEventListener('change', function() {
	            updateBarberAvailability(this.value);
	        });
	    });
	
	    // Form submission validation
	    document.querySelector('form').addEventListener('submit', function(e) {
	        const barberSelect = document.getElementById('barber');
	        const selectedOption = barberSelect.options[barberSelect.selectedIndex];
	        const selectedSlot = document.querySelector('input[name="slot"]:checked');
	        
	        if (!selectedSlot) {
	            alert("Please select a time slot");
	            e.preventDefault();
	            return;
	        }
	        
	        if (barberSelect.selectedIndex === 0) {
	            alert("Please select a barber");
	            e.preventDefault();
	            return;
	        }
	        
	        if (selectedOption.disabled || selectedOption.style.display === "none") {
	            alert("Please select an available barber");
	            e.preventDefault();
	            return;
	        }
	    });
	
	    // Initialize on page load
	    window.addEventListener('DOMContentLoaded', function() {
            // Set minimum date to today
		    const dateInput = document.getElementById('date');
		    const today = new Date().toISOString().split('T')[0];
		    dateInput.min = today;
	        
	        // Initialize selected slot if any
	        const checkedSlot = document.querySelector('input[name="slot"]:checked');
	        if (checkedSlot) {
	            updateBarberAvailability(checkedSlot.value);
	        }
	    });
	    
	    // Force reload from server when navigating via back/forward
	    window.addEventListener('pageshow', function(event) {
		  if (event.persisted) {
		       window.location.reload();
		  }
		 });
	</script>
	<%@ include file="/WEB-INF/views/includes/footer.jsp"%>
</body>
