/* admin-forms.js - Shared admin form DOM controller (password visibility toggle, phone digit mask) */
(function () {
  "use strict";

  document.addEventListener("DOMContentLoaded", function () {
    document.querySelectorAll(".password-toggle").forEach(function (button) {
      button.addEventListener("click", function () {
        const input = document.getElementById(button.getAttribute("data-target"));
        if (!input) return;

        const isPassword = input.type === "password";
        input.type = isPassword ? "text" : "password";

        const eyeIcon = button.querySelector(".eye-icon");
        const eyeOffIcon = button.querySelector(".eye-off-icon");
        if (eyeIcon) eyeIcon.style.display = isPassword ? "none" : "inline-block";
        if (eyeOffIcon) eyeOffIcon.style.display = isPassword ? "inline-block" : "none";

        button.setAttribute("aria-label", isPassword ? "Hide password" : "Show password");
      });
    });

    const phoneInput = document.querySelector('input[name="phone"]');
    if (phoneInput) {
      phoneInput.addEventListener("input", function () {
        this.value = this.value.replace(/[^0-9]/g, "");
      });
    }
  });
})();