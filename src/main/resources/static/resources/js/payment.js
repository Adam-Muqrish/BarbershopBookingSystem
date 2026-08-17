/* payment.js - Payment page DOM controller (payment method toggle, submit states) */
(function () {
  "use strict";

  document.addEventListener("DOMContentLoaded", function () {
    const price = window.PAYMENT_DATA && typeof window.PAYMENT_DATA.price !== "undefined" ? window.PAYMENT_DATA.price : 15.0;
    let isPaymentSubmitting = false;

    const paymentForm = document.getElementById("paymentForm");
    const payButton = document.getElementById("payButton");
    const paySpinner = document.getElementById("paySpinner");
    const payButtonText = document.getElementById("payButtonText");
    const confirmBank = document.getElementById("confirmBank");
    const cashEl = document.getElementById("cash");
    const onlineEl = document.getElementById("online");

    function setPayButtonProcessing(processing) {
      if (!payButton) return;
      payButton.disabled = processing;
      if (paySpinner) paySpinner.classList.toggle("hidden", !processing);
      if (payButtonText) {
        payButtonText.textContent = processing ? "Processing..." : (price === 0 ? "Proceed" : "Pay Now");
      }
    }

    function togglePaymentDetails() {
      if (!cashEl) return; // Free appointment: payment methods are hidden
      const isCash = cashEl.checked;
      const cashDetails = document.getElementById("cash-details");
      const onlineDetails = document.getElementById("online-details");
      if (cashDetails) cashDetails.classList.toggle("hidden", !isCash);
      if (onlineDetails) onlineDetails.classList.toggle("hidden", isCash);

      if (payButtonText) {
        payButtonText.innerText = price === 0 ? "Proceed" : (isCash ? "Confirm Cash Payment" : "Pay Now");
      }
    }

    if (price === 0 && payButtonText) {
      payButtonText.innerText = "Proceed";
    }

    if (paymentForm) {
      paymentForm.addEventListener("submit", function (e) {
        if (isPaymentSubmitting) return;

        // Free appointment: no payment method selection required
        if (price === 0) {
          e.preventDefault();
          isPaymentSubmitting = true;
          setPayButtonProcessing(true);
          setTimeout(function () {
            paymentForm.submit();
          }, 600);
          return;
        }

        const paymentMethod = document.querySelector('input[name="paymentMethod"]:checked');
        if (!paymentMethod) {
          e.preventDefault();
          alert("Please select a payment method (Cash or Online Banking)");
          return;
        }

        if (onlineEl && onlineEl.checked) {
          e.preventDefault();
          const bankModal = document.getElementById("bankModal");
          if (bankModal) bankModal.classList.remove("hidden");
          return;
        }

        // Cash: show loading state before submitting
        e.preventDefault();
        isPaymentSubmitting = true;
        setPayButtonProcessing(true);
        setTimeout(function () {
          paymentForm.submit();
        }, 600);
      });
    }

    if (confirmBank) {
      confirmBank.addEventListener("click", function () {
        const btn = this;
        const spinner = document.getElementById("bankSpinner");
        const text = document.getElementById("bankConfirmText");
        btn.disabled = true;
        if (spinner) spinner.classList.remove("hidden");
        if (text) text.textContent = "Processing...";

        const bankSelect = document.getElementById("bankSelect");
        const hiddenBankName = document.getElementById("hiddenBankName");
        if (bankSelect && hiddenBankName) hiddenBankName.value = bankSelect.value;

        setTimeout(function () {
          isPaymentSubmitting = true;
          paymentForm.submit();
        }, 1200);
      });
    }

    if (cashEl) {
      cashEl.addEventListener("change", togglePaymentDetails);
    }
    if (onlineEl) {
      onlineEl.addEventListener("change", togglePaymentDetails);
    }
  });
})();