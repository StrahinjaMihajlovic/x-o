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
socket_bind($socket, '127.0.0.1', 11002);



if(!is_resource($socket)){
    onSocketFailure("Neuspesno kreiranje socketa");
}

socket_connect($socket, "127.0.0.1", 11000)
        or onSocketFailure("Server se ne odaziva", $socket);

$str = '' .$_POST['polje']  . '';
$str .= '' .$_POST['igr'] ;
//echo $str;
//socket_send($socket, $str, strlen($str), MSG_EOF);
socket_write($socket, $str, strlen($str));
socket_shutdown($socket, 1);
 echo socket_read($socket, strlen($str) + 10);


socket_shutdown($socket, 2);
       socket_close($socket);
        
       