<?php

$servername="localhost:3306";
$dBUsername="root";
$dBPassword="Tutorzr0ck";
$dBName="tutorialschema";

$conn = mysqli_connect($servername, $dBUsername, $dBPassword, $dBName);

if(!$conn){
    die("Connection Failed: ");
    
}
else{

}