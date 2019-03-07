<?php
if (isset($_POST['login-submit'])){
    require 'dbh.inc.php';
    
    $mailUid = $_POST['mailuid'];
    $password = $_POST['pwd'];
    
    if(empty($mailUid) || empty($password)){
        header("Location: ../signup.php?error=emptyFields");
        exit();
    }
    else{
        $sql = "SELECT * FROM users WHERE uidUsers=? OR emailUsers=?;";
        $stmt = mysqli_stmt_init($conn);
        if(!mysqli_stmt_prepare($stmt, $sql)){
           header("Location: ../signup.php?error=sqlError");
            exit(); 
        }
        else{
            mysqli_stmt_bind_param($stmt, "ss", $mailUid, $mailUid);
            mysqli_stmt_execute($stmt);
            $result = mysqli_stmt_get_result($stmt);
            
            if($row = mysqli_fetch_assoc($result)){
                $pwdCheck = password_verify($password, $row['pwdUsers']);
                
                if($pwdCheck == false){
                    header("Location: ../login.php?error=wrongPassword");
                    exit();
                }
                else if($pwdCheck == true){
                    session_start();
                    $_SESSION['userId'] = $row['idUsers'];
                    $_SESSION['userName'] = $row['uidUsers'];
                    header("Location: ../login.php?loginSuccess");
                    exit();
                    
                }
                else{
                    header("Location: ../login.php?error=wrongPassword");
                    exit();
                }
            }
            else{
                header("Location: ../login.php?error=noUser");
                exit();
            }
        }
    }
}

else{
    header("Location: ../signup.php?");
    exit();
}