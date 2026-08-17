/* admin-index.js - Admin dashboard DOM controller (sales chart) */
(function () {
  "use strict";

  document.addEventListener("DOMContentLoaded", function () {
    const ctx = document.getElementById("salesChart");
    if (!ctx) return;

    const rawData = window.ADMIN_INDEX_DATA && window.ADMIN_INDEX_DATA.salesByDay ? window.ADMIN_INDEX_DATA.salesByDay : {};
    const dayLabels = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
    const days = ["SUNDAY", "MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY"];
    const salesData = days.map(function (day) {
      return rawData[day] || 0;
    });

    new Chart(ctx, {
      type: "line",
      data: {
        labels: dayLabels,
        datasets: [{
          label: "Sales (RM)",
          data: salesData,
          backgroundColor: "rgba(54, 162, 235, 0.2)",
          borderColor: "rgba(54, 162, 235, 1)",
          borderWidth: 1,
          tension: 0.4
        }]
      },
      options: {
        responsive: true,
        maintainAspectRatio: false,
        scales: {
          y: {
            beginAtZero: true
          }
        }
      }
    });
  });
})();