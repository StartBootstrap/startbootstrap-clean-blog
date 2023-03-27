---
layout: default
title: Test
---

<div id="countdown"></div>

<script>

// Set the date to countdown to (in this example, December 31, 2023 at 11:59 PM)

var countDownDate = new Date("Dec 31, 2023 23:59:59").getTime();

// Update the countdown every second

var countdownInterval = setInterval(function() {

  // Get today's date and time

  var now = new Date().getTime();

  // Calculate the time remaining

  var timeRemaining = countDownDate - now;

  // Calculate days, hours, minutes and seconds remaining

  var days = Math.floor(timeRemaining / (1000 * 60 * 60 * 24));

  var hours = Math.floor((timeRemaining % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60));

  var minutes = Math.floor((timeRemaining % (1000 * 60 * 60)) / (1000 * 60));

  var seconds = Math.floor((timeRemaining % (1000 * 60)) / 1000);

  // Display the countdown

  document.getElementById("countdown").innerHTML = days + "d " + hours + "h " + minutes + "m " + seconds + "s ";

  // If the countdown is finished, display a message

  if (timeRemaining < 0) {

    clearInterval(countdownInterval);

    document.getElementById("countdown").innerHTML = "EXPIRED";

  }

}, 1000);

</script>
