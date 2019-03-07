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
            
            <?php
                if(isset($_SESSION['userId'])){
                    echo '<p>You are logged in!</p>
                    <form action="includes/logout.inc.php" method="post">
                    <button type="submit" name="logout-submit">Logout</button>
                    </form>
                    ';
                }
                else{
                    echo '<p>You are currently logged out. Please log in to see your personalized content.</p>
                    <form action="includes/login.inc.php" method="post">
                    <input type="text" name="mailuid" placeholder="email-address or UID">
                    <br><input type="password" name="pwd" placeholder="password">
                    <br><button type="submit" name="login-submit">Login</button>
                    </form>
                    <a href="signup.php">Register</a>';
                    
                }
            
            ?>
            
            <!-- Simple Login Form -->
            
          
            
        </div>
      </div>
    </div>
 

  <hr>

<?php
    require "footer.php";
?>