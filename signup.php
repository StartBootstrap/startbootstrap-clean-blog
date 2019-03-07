<?php
    require "header.php";
?>



  <!-- Page Header -->
  <header class="masthead" style="background-image: url('img/post-bg.jpg')">
    <div class="overlay"></div>
    <div class="container">
      <div class="row">
        <div class="col-lg-8 col-md-10 mx-auto">
          <div class="post-heading">
            <h1>Student Portal</h1>
            <h2 class="subheading"></h2>
          </div>
        </div>
      </div>
    </div>
  </header>

  <!-- Post Content -->
 
    <div class="container">
      <div class="row">
        <div class="col-lg-8 col-md-10 mx-auto">
            
            <form action="includes/signup.inc.php" method="post">
          
            <input type="text" name="uid" placeholder="Username">
            <br><input type="text" name="mail" placeholder="Email">
            <br><input type="password" name="pwd" placeholder="Password">
            <br><input type="password" name="pwd-repeat" placeholder="Confirm Password">
            <br><button type="submit" name="signup-submit">Signup</button>
            
        </form>
            
        </div>
      </div>
    </div>
 

  <hr>

<?php
    require "footer.php";
?>