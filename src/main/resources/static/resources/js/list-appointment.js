/* list-appointment.js - Admin appointment list DOM controller (available times loader, past-date guard) */
(function () {
  "use strict";

  document.addEventListener("DOMContentLoaded", function () {
    const dateInput = document.getElementById("editDate");
    if (dateInput) {
      const today = new Date().toISOString().split("T")[0];
      dateInput.setAttribute("min", today);
      dateInput.addEventListener("change", function () {
        if (this.value && this.value < today) {
          this.value = "";
          alert("Cannot select a past date.");
        }
      });
      updateAvailableTimes();
    }
  });

  function isTimeInPast(dateStr, timeStr) {
    if (!dateStr || !timeStr) return false;
    try {
      const timeParts = timeStr.split(" ");
      const hm = timeParts[0].split(":");
      let h = parseInt(hm[0]);
      const minute = hm[1];
      const ampm = timeParts[1].toLowerCase();

      if (ampm === "pm" && h !== 12) h += 12;
      if (ampm === "am" && h === 12) h = 0;

      const appointmentDateTime = new Date(dateStr + " " + String(h).padStart(2, "0") + ":" + minute);
      return appointmentDateTime < new Date();
    } catch (e) {
      return false;
    }
  }

  function updateAvailableTimes() {
    const date = document.getElementById("editDate").value;
    const barberId = document.getElementById("editBarber").value;
    const timeSelect = document.getElementById("editTime");
    const appointmentId = document.querySelector('input[name="appointmentId"]') ? document.querySelector('input[name="appointmentId"]').value : "";
    const currentlySelectedTime = timeSelect.value;

    timeSelect.innerHTML = '<option value="">Loading available times...</option>';

    if (!date) {
      timeSelect.innerHTML = '<option value="">Select Time</option>';
      return;
    }
    if (!barberId) {
      timeSelect.innerHTML = '<option value="">Select a barber first</option>';
      return;
    }

    let url = "/api/available-times?date=" + date + "&barberId=" + barberId;
    if (appointmentId) {
      url += "&excludeAppointmentId=" + appointmentId;
    }

    fetch(url)
      .then(function (response) {
        if (!response.ok) {
          throw new Error("Request failed (" + response.status + ")");
        }
        return response.json();
      })
      .then(function (times) {
        if (times.length === 0) {
          timeSelect.innerHTML = '<option value="">No available times</option>';
          return;
        }
        timeSelect.innerHTML = '<option value="">Select Time</option>';

        const today = new Date().toISOString().split("T")[0];
        times.forEach(function (time) {
          if (date === today && isTimeInPast(date, time)) {
            return;
          }
          const option = document.createElement("option");
          option.value = time;
          option.textContent = time;
          timeSelect.appendChild(option);
        });

        const originalTimeInput = document.getElementById("originalTime");
        const originalTime = originalTimeInput ? originalTimeInput.value : "";
        if (originalTime && Array.from(timeSelect.options).some(function (o) { return o.value === originalTime; })) {
          timeSelect.value = originalTime;
        } else if (currentlySelectedTime && Array.from(timeSelect.options).some(function (o) { return o.value === currentlySelectedTime; })) {
          timeSelect.value = currentlySelectedTime;
        }
      })
      .catch(function (error) {
        console.error("Error:", error);
        timeSelect.innerHTML = '<option value="">Error loading times. Please try again.</option>';
      });
  }
})();