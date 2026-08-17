/* edit-profile.js - Edit profile page DOM controller (password toggle, confirm match, image preview) */
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
        if (eyeIcon) eyeIcon.classList.toggle("hidden", !isPassword);
        if (eyeOffIcon) eyeOffIcon.classList.toggle("hidden", isPassword);

        button.setAttribute("aria-label", isPassword ? "Hide password" : "Show password");
      });
    });

    const passwordInput = document.getElementById("password");
    const confirmInput = document.getElementById("confirm-password");
    const matchSpan = document.getElementById("passwordMatch");
    if (passwordInput && confirmInput && matchSpan) {
      function updateMatch() {
        const value = confirmInput.value;
        if (!value) {
          matchSpan.textContent = "";
          matchSpan.classList.add("hidden");
          confirmInput.classList.remove("border-red-500");
          return;
        }
        if (value !== passwordInput.value) {
          matchSpan.textContent = "Passwords do not match.";
          matchSpan.classList.remove("hidden", "text-green-600");
          matchSpan.classList.add("text-red-600");
          confirmInput.classList.add("border-red-500");
        } else {
          matchSpan.textContent = "Passwords match.";
          matchSpan.classList.remove("hidden", "text-red-600");
          matchSpan.classList.add("text-green-600");
          confirmInput.classList.remove("border-red-500");
        }
      }
      passwordInput.addEventListener("input", updateMatch);
      confirmInput.addEventListener("input", updateMatch);
      confirmInput.addEventListener("blur", updateMatch);
    }

    const fileInput = document.getElementById("image");
    if (fileInput) {
      const preview = document.getElementById("imagePreview");
      const fallback = document.getElementById("imagePreviewFallback");
      const fileNameDisplay = document.getElementById("fileNameDisplay");

      fileInput.addEventListener("change", function () {
        const file = this.files && this.files[0];
        if (fileNameDisplay) fileNameDisplay.innerText = file ? file.name : "No file chosen";
        if (!file) return;

        const allowed = ["jpg", "jpeg", "png"];
        const ext = (file.name.split(".").pop() || "").toLowerCase();
        if (allowed.indexOf(ext) === -1) {
          alert(ext + " file type not allowed. Please upload a JPG, JPEG, or PNG file.");
          this.value = "";
          if (fileNameDisplay) fileNameDisplay.innerText = "No file chosen";
          return;
        }

        if (preview) {
          preview.src = URL.createObjectURL(file);
          preview.classList.remove("hidden");
        }
        if (fallback) {
          fallback.classList.add("hidden");
        }
      });
    }
  });
})();