<head>
    <link rel="stylesheet" href="bootstrap/css/bootstrap.css">
    <link rel="stylesheet" href="css/glavni.css">
        <script src="js/jquery-3.4.1.js"></script>  
    <script src="bootstrap/js/bootstrap.js"></script>

  
    <meta name="viewport" content="width=device-width, initial-scale=1">

</head>

<div class="Tabla container-fluid">
    <div class="row">
        <div class ="polje col" id="1"></div>
        <div class ="polje col" id="2"></div>
        <div class ="polje col" id="3"></div>
    </div>
     <div class="row">
         <div class ="polje col" id='4'></div>
        <div class ="polje col" id='5'></div>
        <div class ="polje col" id="6"></div>
    </div>
    <div class="row">
        <div class ="polje col" id="7"></div>
        <div class ="polje col" id='8'></div>
        <div class ="polje col" id="9"></div> 
   </div>
    
</div>


<?php

/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function onSocketFailure(string $message, $socket = null) {
    if(is_resource($socket)) {
        $message .= ": " . socket_strerror(socket_last_error($socket));
    }
    die($message);
}
$socket = socket_create(AF_INET, SOCK_STREAM, SOL_TCP);




if(!is_resource($socket)){
    onSocketFailure("Neuspesno kreiranje socketa");
}

socket_connect($socket, "127.0.0.1", 11000)
        or onSocketFailure("Server se ne odaziva", $socket);
$str = "Vozdra!\r\n". chr(0);
//socket_write($socket, $str, strlen($str));
socket_send($socket, $str, strlen($str), MSG_EOF);

?>
<p><?php echo socket_read($socket, strlen($str) +5);?></p>

<?php 

 socket_shutdown($socket, 2);
        socket_close($socket);
  
?>

  <script src="js/glavni.js"></script>