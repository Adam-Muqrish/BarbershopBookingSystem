/* list-barber.js - Staff list page DOM controller (auto-open register modal on error flag) */
(function () {
  "use strict";

  document.addEventListener("DOMContentLoaded", function () {
    const hasErrorFlag = document.getElementById("has-error-flag");
    if (hasErrorFlag && hasErrorFlag.value === "true") {
      const modalEl = document.getElementById("addBarberModal");
      if (modalEl) {
        new bootstrap.Modal(modalEl).show();
      }
    }
  });
})();