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

socket_bind($socket, "127.0.0.1");

if(!is_resource($socket)){
    onSocketFailure("Neuspesno kreiranje socketa");
}

socket_connect($socket, "chat.stackoverflow.com", 6667)
        or onSocketFailure("Server se ne odaziva", $socket);

socket_write($socket, "Vozdra!");




socket_close($socket);