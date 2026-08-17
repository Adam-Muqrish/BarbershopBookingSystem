/* feedback.js - Feedback page DOM controller (star rating input) */
(function () {
  "use strict";

  document.addEventListener("DOMContentLoaded", function () {
    const stars = document.querySelectorAll(".star");
    const ratingInput = document.getElementById("rating-input");
    if (!stars.length || !ratingInput) return;

    let currentRating = parseInt(ratingInput.value) || 0;

    function updateStars(val) {
      stars.forEach(function (s, idx) {
        s.classList.toggle("text-yellow-400", idx < val);
        s.classList.toggle("text-gray-400", idx >= val);
      });
    }

    stars.forEach(function (s, idx) {
      s.addEventListener("click", function () {
        currentRating = idx + 1;
        ratingInput.value = currentRating;
        updateStars(currentRating);
      });
      s.addEventListener("mouseover", function () {
        updateStars(idx + 1);
      });
      s.addEventListener("mouseout", function () {
        updateStars(currentRating);
      });
    });

    updateStars(currentRating);
  });
})();