/* view-appointment.js - Current appointment page DOM controller (cancel confirmation modal) */
(function () {
  "use strict";

  document.addEventListener("DOMContentLoaded", function () {
    let cancelId = null;

    window.showCancelModal = function (id) {
      cancelId = id;
      const modal = document.getElementById("cancelModal");
      if (modal) modal.classList.remove("hidden");
    };

    window.hideCancelModal = function () {
      const modal = document.getElementById("cancelModal");
      if (modal) modal.classList.add("hidden");
    };

    const confirmBtn = document.getElementById("confirmCancelBtn");
    if (confirmBtn) {
      confirmBtn.addEventListener("click", function () {
        if (cancelId) {
          const form = document.getElementById("cancelForm-" + cancelId);
          if (form) form.submit();
        }
      });
    }
  });
})();