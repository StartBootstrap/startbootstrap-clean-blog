<?php

$servername="localhost:3306";
$dBUsername="root";
$dBPassword="";
$dBName="userLogin";

$conn = mysqli_connect($servername, $dBUsername, $dBPassword, $dBName);

if(!$conn){
    die("Connection Failed: ");
    
}
else{

}