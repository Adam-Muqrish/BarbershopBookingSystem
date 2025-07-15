<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.hugi.barbershop.customer.model.Customer"%>
<%@ include file="/WEB-INF/views/includes/header.jsp"%>
<script>
function loadAppointments(page) {
    const xhr = new XMLHttpRequest();
    xhr.onreadystatechange = function() {
        if (this.readyState === 4 && this.status === 200) {
            document.getElementById("appointment-container").innerHTML = this.responseText;
        }
    };
    xhr.open("GET", "view-appointment?page=" + page + "&ajax=true", true);
    xhr.send();
}

// Modal logic
let selectedAppointmentId = null;

function showCancelModal(appointmentId) {
    selectedAppointmentId = appointmentId;
    document.getElementById('cancelModal').classList.remove('hidden');
}

window.addEventListener('DOMContentLoaded', function() {
    document.getElementById('cancelModalNo').onclick = function() {
        document.getElementById('cancelModal').classList.add('hidden');
        selectedAppointmentId = null;
    };

    document.getElementById('cancelModalYes').onclick = function() {
        if (selectedAppointmentId) {
            document.getElementById('cancelForm-' + selectedAppointmentId).submit();
        }
        document.getElementById('cancelModal').classList.add('hidden');
        selectedAppointmentId = null;
    };
});
</script>
<body class="flex flex-col h-screen justify-between bg-yellow-100">
    <%@ include file="/WEB-INF/views/includes/nav.jsp"%>
    <main class="flex flex-col items-center mx-3 my-4 flex-grow justify-center">
        <div class="bg-white rounded-lg shadow-lg p-6 w-full max-w-5xl mx-auto">
            <h1 class="text-2xl font-bold mb-6">Your Current Appointments:</h1>
            <!-- Modal HTML moved here -->
            <div id="cancelModal"
                class="fixed inset-0 flex items-center justify-center bg-black bg-opacity-40 z-50 hidden">
                <div class="bg-white rounded-lg shadow-lg p-6 w-80">
                    <h2 class="text-lg font-semibold mb-4">Cancel Appointment</h2>
                    <p class="mb-6">Are you sure you want to cancel this appointment?</p>
                    <div class="flex justify-end gap-2">
                        <button id="cancelModalNo"
                            class="px-4 py-1 rounded bg-gray-300 hover:bg-gray-400">No</button>
                        <button id="cancelModalYes"
                            class="px-4 py-1 rounded bg-red-500 text-white hover:bg-red-600">Yes</button>
                    </div>
                </div>
            </div>
            <div id="appointment-container">
                <!-- This will be populated with appointment data -->
                <jsp:include page="appointment-list-partial.jsp" />
            </div>
        </div>
    </main>
    <%@ include file="/WEB-INF/views/includes/footer.jsp"%>
</body>
