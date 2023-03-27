---
layout: default
title: Test
---

<p>Join me in listening to living prophets and Apostles this weekend.  <a href="https://newsroom.churchofjesuschrist.org/event/april-2023-general-conference?lang=eng" target="_blank">Learn More</a></p>
General Conference is in:
<div id="countdown"></div>

<script>

// Set the date to countdown to conference (Saturday morning session)

var countDownDate = new Date("Apr 1, 2023 10:00:00").getTime();

// Update the countdown every hour (3600000 at the bottom is one hour)

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

  document.getElementById("countdown").innerHTML = days + " days";

  // If the countdown is finished, display a message

  if (timeRemaining < 0) {

    clearInterval(countdownInterval);

    document.getElementById("countdown").innerHTML = "EXPIRED";

  }

}, 3600000);

</script>
