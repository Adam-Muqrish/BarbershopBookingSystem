/* register.js - Login/register page DOM controller (sliding panel, modal, validation, password toggle, phone mask) */
(function () {
  "use strict";

  document.addEventListener("DOMContentLoaded", function () {
    /* ---------- Sliding panel + modal ---------- */
    const slidingPanel = document.getElementById("sliding-panel");
    const registerFormContainer = document.getElementById("register-form-container");
    const loginFormContainer = document.getElementById("login-form-container");
    const toggleLoginBtn = document.getElementById("toggle-login");
    const toggleRegisterBtn = document.getElementById("toggle-register");
    const modalOverlay = document.getElementById("modal-overlay");
    const modalContent = document.getElementById("modal-content");

    function showModal(success, message, redirectUrl) {
      if (!modalContent || !modalOverlay) return;
      modalContent.innerHTML = success
        ? '<div class="modal-checkmark"><svg viewBox="0 0 64 64"><circle class="circle" cx="32" cy="32" r="28"/><polyline class="check" points="18,34 28,44 46,22"/></svg></div><h3 class="text-green-600 text-xl font-semibold mb-2">Success</h3><div class="text-gray-700">' + message + "</div>"
        : '<div class="modal-xmark"><svg viewBox="0 0 64 64"><circle class="circle" cx="32" cy="32" r="28"/><line class="x" x1="22" y1="22" x2="42" y2="42"/><line class="x" x1="42" y1="22" x2="22" y2="42"/></svg></div><h3 class="text-red-600 text-xl font-semibold mb-2">Failed</h3><div class="text-gray-700">' + message + "</div>";
      modalOverlay.classList.add("show");
      setTimeout(function () {
        modalOverlay.classList.remove("show");
        if (success && redirectUrl) window.location.href = redirectUrl;
      }, 2000);
    }

    const data = window.REGISTER_DATA || {};
    if (data.justLoggedIn) showModal(true, "Login successful! Redirecting...", "/index");
    else if (data.successMessage) showModal(true, data.successMessage);
    else if (data.error) showModal(false, data.error);

    if (toggleRegisterBtn) {
      toggleRegisterBtn.addEventListener("click", function () {
        if (slidingPanel) slidingPanel.classList.remove("right");
        if (loginFormContainer) loginFormContainer.classList.add("inactive");
        if (registerFormContainer) registerFormContainer.classList.remove("inactive");
        toggleRegisterBtn.style.display = "none";
        if (toggleLoginBtn) toggleLoginBtn.style.display = "block";
      });
    }

    if (toggleLoginBtn) {
      toggleLoginBtn.addEventListener("click", function () {
        if (slidingPanel) slidingPanel.classList.add("right");
        if (registerFormContainer) registerFormContainer.classList.add("inactive");
        if (loginFormContainer) loginFormContainer.classList.remove("inactive");
        if (toggleRegisterBtn) toggleRegisterBtn.style.display = "block";
        toggleLoginBtn.style.display = "none";
      });
    }

    /* ---------- Autofill test credentials into the login form ---------- */
    const autofillTestBtn = document.getElementById("autofillTestBtn");
    if (autofillTestBtn) {
      autofillTestBtn.addEventListener("click", function () {
        const loginForm = document.getElementById("login-form");
        if (!loginForm) return;
        const emailInput = loginForm.querySelector('input[name="email"]');
        const passwordInput = document.getElementById("loginPassword");
        if (emailInput) emailInput.value = "user1@gmail.com";
        if (passwordInput) passwordInput.value = "password";
      });
    }

    /* ---------- Phone input: digits only ---------- */
    const phoneInput = document.querySelector('input[name="phone"]');
    if (phoneInput) {
      phoneInput.addEventListener("input", function () {
        this.value = this.value.replace(/[^0-9]/g, "");
      });
    }

    /* ---------- Password visibility toggle ---------- */
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

    /* ---------- Registration form validation ---------- */
    const form = document.getElementById("register-form");
    if (!form) return;

    const nameInput = document.getElementById("registerName");
    const emailInput = document.getElementById("registerEmail");
    const phoneInputField = document.getElementById("registerPhone");
    const passwordInput = document.getElementById("registerPassword");
    const confirmInput = document.getElementById("registerConfirmPassword");
    const matchSpan = document.getElementById("passwordMatch");

    const emailRegex = /^[\w-\.]+@([\w-]+\.)+[\w-]{2,4}$/;
    const MIN_PASSWORD_LENGTH = 8;

    function setError(fieldId, message) {
      const span = document.querySelector('[data-error-for="' + fieldId + '"]');
      const input = document.getElementById(fieldId);
      if (span) {
        span.textContent = message;
        span.classList.remove("hidden");
      }
      if (input) {
        input.classList.add("border-red-500");
        input.classList.remove("border-gray-300");
      }
    }

    function clearError(fieldId) {
      const span = document.querySelector('[data-error-for="' + fieldId + '"]');
      const input = document.getElementById(fieldId);
      if (span) {
        span.textContent = "";
        span.classList.add("hidden");
      }
      if (input) {
        input.classList.remove("border-red-500");
        input.classList.add("border-gray-300");
      }
    }

    function validateName() {
      const value = nameInput.value.trim();
      if (!value) {
        setError("registerName", "Name is required.");
        return false;
      }
      if (value.length < 2) {
        setError("registerName", "Name must be at least 2 characters.");
        return false;
      }
      clearError("registerName");
      return true;
    }

    function validateEmail() {
      const value = emailInput.value.trim();
      if (!value) {
        setError("registerEmail", "Email is required.");
        return false;
      }
      if (!emailRegex.test(value)) {
        setError("registerEmail", "Please enter a valid email address.");
        return false;
      }
      clearError("registerEmail");
      return true;
    }

    function validatePhone() {
      const value = phoneInputField.value.trim();
      if (!value) {
        setError("registerPhone", "Phone number is required.");
        return false;
      }
      if (!/^\d{7,15}$/.test(value)) {
        setError("registerPhone", "Enter 7-15 digits only.");
        return false;
      }
      clearError("registerPhone");
      return true;
    }

    function validatePassword() {
      const value = passwordInput.value;
      if (!value) {
        setError("registerPassword", "Password is required.");
        return false;
      }
      if (value.length < MIN_PASSWORD_LENGTH) {
        setError("registerPassword", "Password must be at least " + MIN_PASSWORD_LENGTH + " characters.");
        return false;
      }
      clearError("registerPassword");
      return true;
    }

    function validateConfirm() {
      const value = confirmInput.value;
      if (!value) {
        confirmInput.classList.remove("border-red-500");
        confirmInput.classList.add("border-gray-300");
        if (matchSpan) {
          matchSpan.textContent = "";
          matchSpan.classList.add("hidden");
        }
        return false;
      }
      if (value !== passwordInput.value) {
        confirmInput.classList.add("border-red-500");
        confirmInput.classList.remove("border-gray-300");
        if (matchSpan) {
          matchSpan.textContent = "Passwords do not match.";
          matchSpan.classList.remove("hidden", "text-green-600");
          matchSpan.classList.add("text-red-600");
        }
        return false;
      }
      confirmInput.classList.remove("border-red-500");
      confirmInput.classList.add("border-gray-300");
      if (matchSpan) {
        matchSpan.textContent = "Passwords match.";
        matchSpan.classList.remove("hidden", "text-red-600");
        matchSpan.classList.add("text-green-600");
      }
      return true;
    }

    function scorePassword(value) {
      let score = 0;
      if (value.length >= MIN_PASSWORD_LENGTH) score++;
      if (/[a-z]/.test(value) && /[A-Z]/.test(value)) score++;
      if (/\d/.test(value)) score++;
      if (/[^A-Za-z0-9]/.test(value)) score++;
      return score;
    }

    function updateStrength() {
      const value = passwordInput.value;
      const bars = document.querySelectorAll("#passwordStrength .strength-bar");
      const text = document.getElementById("strengthText");
      if (!value) {
        bars.forEach(function (b) {
          b.style.backgroundColor = "";
        });
        if (text) text.textContent = "";
        return;
      }
      const score = scorePassword(value);
      const colors = ["#ef4444", "#f97316", "#eab308", "#22c55e", "#22c55e"];
      const labels = ["Very weak", "Weak", "Fair", "Strong", "Very strong"];
      const filled = Math.max(1, Math.min(4, score));
      bars.forEach(function (b, i) {
        b.style.backgroundColor = i < filled ? colors[score] : "#e5e7eb";
      });
      if (text) {
        text.textContent = "Strength: " + labels[Math.min(score, 4)];
        text.style.color = colors[score];
      }
    }

    nameInput.addEventListener("input", validateName);
    nameInput.addEventListener("blur", validateName);
    emailInput.addEventListener("input", validateEmail);
    emailInput.addEventListener("blur", validateEmail);
    phoneInputField.addEventListener("input", validatePhone);
    phoneInputField.addEventListener("blur", validatePhone);
    passwordInput.addEventListener("input", function () {
      validatePassword();
      updateStrength();
      if (confirmInput.value) validateConfirm();
    });
    passwordInput.addEventListener("blur", validatePassword);
    confirmInput.addEventListener("input", validateConfirm);
    confirmInput.addEventListener("blur", validateConfirm);

    form.addEventListener("submit", function (e) {
      const ok = validateName() & validateEmail() & validatePhone() & validatePassword() & validateConfirm();
      if (!ok) e.preventDefault();
    });
  });
})();