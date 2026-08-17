/* booking.js - Booking page DOM controller (slot status, barber filter, client-side validation) */
(function () {
  "use strict";

  document.addEventListener("DOMContentLoaded", function () {
    const data = window.BOOKING_DATA || {};
    let unavailableBarbersBySlot = data.unavailableBarbersBySlot || {};

    const dateInput = document.getElementById("date");
    const notice = document.getElementById("slot-notice");
    const selectedTime = document.getElementById("selected-time");
    const barberSelect = document.getElementById("barber");
    const form = document.querySelector('form[action="/booking"]');
    const bookInput = document.getElementById("booking-for");

    function updateSlotStatus(selectedDate, totalBarbers) {
      const radios = document.querySelectorAll(".slot-radio");
      const today = new Date().toISOString().split("T")[0];
      let currentTimeVal = 0;
      const isToday = selectedDate === today;

      if (isToday) {
        const now = new Date();
        currentTimeVal = now.getHours() + now.getMinutes() / 60;
      }

      radios.forEach(function (radio) {
        radio.checked = false;

        const unavailableCount = (unavailableBarbersBySlot[radio.value] || []).length;
        const isFull = unavailableCount >= totalBarbers;

        let isTimePassed = false;
        if (isToday) {
          const parts = radio.value.trim().split(" ");
          const hm = parts[0].split(":");
          let h = parseInt(hm[0]);
          const m = parseInt(hm[1] || 0);
          const ampm = parts[1];

          if (ampm.toLowerCase() === "pm" && h !== 12) h += 12;
          else if (ampm.toLowerCase() === "am" && h === 12) h = 0;
          const slotVal = h + m / 60;

          if (slotVal < currentTimeVal + 0.5) isTimePassed = true;
        }

        radio.disabled = isFull || isTimePassed;
      });

      if (selectedTime) selectedTime.value = "--:-- --";
      if (barberSelect) barberSelect.selectedIndex = 0;
      if (notice) notice.classList.remove("hidden");
    }

    document.querySelectorAll(".slot-radio").forEach(function (radio) {
      radio.addEventListener("change", function () {
        const slot = this.value;
        if (selectedTime) selectedTime.value = slot;
        if (notice) notice.classList.add("hidden");
        if (!barberSelect) return;

        const unavailableForSlot = unavailableBarbersBySlot[slot] || [];
        Array.from(barberSelect.options).forEach(function (opt) {
          if (opt.value === "") return;
          const isUnavailable = unavailableForSlot.some(function (id) {
            return id.toString() === opt.value.toString();
          });
          opt.style.display = isUnavailable ? "none" : "";
          opt.disabled = isUnavailable;
        });
        barberSelect.selectedIndex = 0;
      });
    });

    if (dateInput) {
      dateInput.addEventListener("change", function () {
        const selectedDate = this.value;
        fetch("/booking/unavailable?date=" + selectedDate)
          .then(function (r) {
            return r.json();
          })
          .then(function (result) {
            unavailableBarbersBySlot = result.unavailableBarbersBySlot;
            updateSlotStatus(selectedDate, result.totalBarbers);
          });
      });

      if (dateInput.value) {
        fetch("/booking/unavailable?date=" + dateInput.value)
          .then(function (r) {
            return r.json();
          })
          .then(function (result) {
            unavailableBarbersBySlot = result.unavailableBarbersBySlot;
            updateSlotStatus(dateInput.value, result.totalBarbers);
          });
      }
    }

    if (form && bookInput) {
      const errorSpan = document.getElementById("booking-for-error");
      const nameRegex = /^[A-Za-z][A-Za-z .'-]{1,49}$/;

      function setError(message) {
        errorSpan.textContent = message;
        errorSpan.classList.remove("hidden");
        bookInput.classList.add("border-red-500");
        bookInput.classList.remove("border-gray-300");
      }

      function clearError() {
        errorSpan.textContent = "";
        errorSpan.classList.add("hidden");
        bookInput.classList.remove("border-red-500");
        bookInput.classList.add("border-gray-300");
      }

      function validate() {
        const value = bookInput.value.trim();
        if (!value) {
          setError("Please enter who the appointment is for.");
          return false;
        }
        if (!nameRegex.test(value)) {
          setError("Enter a valid name using letters only (e.g., John Doe).");
          return false;
        }
        clearError();
        return true;
      }

      bookInput.addEventListener("input", validate);
      bookInput.addEventListener("blur", validate);

      form.addEventListener("submit", function (event) {
        if (!validate()) {
          event.preventDefault();
          bookInput.focus();
          return;
        }

        const btn = document.getElementById("bookButton");
        if (btn) {
          btn.disabled = true;
          btn.classList.add("opacity-60", "cursor-not-allowed");
          btn.innerHTML = '<span class="inline-block w-4 h-4 border-2 border-white border-t-transparent rounded-full animate-spin align-middle"></span> Booking...';
        }
      });
    }
  });
})();