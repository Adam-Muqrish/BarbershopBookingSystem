/* edit-appointment.js - Edit appointment page DOM controller (slot status, barber filter) */
(function () {
  "use strict";

  document.addEventListener("DOMContentLoaded", function () {
    const data = window.EDIT_APPOINTMENT_DATA || {};
    let unavailableBarbersBySlot = Object.assign({}, data.unavailableBarbersBySlot || {});
    const barbersList = data.barbers || [];
    const originalDate = data.originalDate || "";
    const originalTime = data.originalTime || "";
    const originalBarberId = data.originalBarberId || 0;

    const dateInput = document.getElementById("date");
    const selectedTime = document.getElementById("selected-time");
    const barberSelect = document.getElementById("barber");
    const notice = document.getElementById("slot-notice");
    let currentTimeVal = 0;

    function filterBarbersForSlot(slot, selectedDate) {
      if (!barberSelect) return;
      const unavailableForSlot = unavailableBarbersBySlot[slot] || [];

      Array.from(barberSelect.options).forEach(function (opt) {
        if (opt.value === "") return;

        const optBarberId = parseInt(opt.getAttribute("data-id"));
        const isUnavailable = unavailableForSlot.indexOf(optBarberId) !== -1;
        const isOriginalCombo = selectedDate === originalDate && slot === originalTime && optBarberId === originalBarberId;

        if (isUnavailable && !isOriginalCombo) {
          opt.style.display = "none";
          opt.disabled = true;
        } else {
          opt.style.display = "";
          opt.disabled = false;
        }
      });

      if (barberSelect.options[barberSelect.selectedIndex] && barberSelect.options[barberSelect.selectedIndex].disabled) {
        barberSelect.selectedIndex = 0;
      }
    }

    function updateSlotStatus(selectedDate, totalBarbers) {
      const radios = document.querySelectorAll(".slot-radio");
      const today = new Date().toISOString().split("T")[0];
      const isToday = selectedDate === today;

      if (isToday) {
        const now = new Date();
        currentTimeVal = now.getHours() + now.getMinutes() / 60;
      } else {
        currentTimeVal = 0;
      }

      radios.forEach(function (radio) {
        const slotValStr = radio.value;
        const unavailableList = unavailableBarbersBySlot[slotValStr] || [];
        const isOriginalSlot = selectedDate === originalDate && slotValStr === originalTime;
        let isFull = unavailableList.length >= totalBarbers;
        if (isOriginalSlot) isFull = false;

        let isTimePassed = false;
        if (isToday) {
          const parts = slotValStr.trim().split(" ");
          const hm = parts[0].split(":");
          let h = parseInt(hm[0]);
          const m = parseInt(hm[1] || 0);
          const ampm = parts[1];

          if (ampm.toLowerCase() === "pm" && h !== 12) h += 12;
          else if (ampm.toLowerCase() === "am" && h === 12) h = 0;
          const slotVal = h + m / 60;

          if (isOriginalSlot) {
            isTimePassed = false;
          } else if (slotVal < currentTimeVal + 0.5) {
            isTimePassed = true;
          }
        }

        radio.disabled = isFull || isTimePassed;
      });

      const checkedRadio = document.querySelector('input[name="slot"]:checked');
      if (checkedRadio && checkedRadio.disabled) {
        checkedRadio.checked = false;
        if (selectedTime) selectedTime.value = "--:-- --";
        filterBarbersForSlot("", selectedDate);
        if (notice) {
          notice.textContent = "The previously selected time is no longer available. Please choose a new time slot.";
          notice.classList.remove("hidden");
        }
      }
    }

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
    }

    document.querySelectorAll(".slot-radio").forEach(function (radio) {
      radio.addEventListener("change", function () {
        const slot = this.value;
        if (selectedTime) selectedTime.value = slot;
        filterBarbersForSlot(slot, dateInput ? dateInput.value : "");
        if (notice) {
          notice.textContent = "";
          notice.classList.add("hidden");
        }
      });
    });

    const checkedRadio = document.querySelector('input[name="slot"]:checked');
    if (checkedRadio) {
      if (selectedTime) selectedTime.value = checkedRadio.value;
      checkedRadio.dispatchEvent(new Event("change"));
    }

    if (dateInput && dateInput.value) {
      fetch("/booking/unavailable?date=" + dateInput.value)
        .then(function (r) {
          return r.json();
        })
        .then(function (result) {
          unavailableBarbersBySlot = result.unavailableBarbersBySlot;
          const totalBarbersCount = barbersList.length || Object.keys(result.unavailableBarbersBySlot).reduce(function (max, slot) {
            return Math.max(max, (result.unavailableBarbersBySlot[slot] || []).length);
          }, 0);
          updateSlotStatus(dateInput.value, totalBarbersCount);
        })
        .catch(function () {
          updateSlotStatus(dateInput.value, barbersList.length);
        });
    }
  });
})();