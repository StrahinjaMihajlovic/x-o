/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$('.polje').click(function(e){
    /*$.ajax({url:"Igra.php",type:'POST', data:{polje: prom}; success: function (data, textStatus, jqXHR) {
            for(i=1; i<data.length; i++){
                $("#"+i).append('<img class="slika" src="slike/'+data[i-1]+'></img>"'); //za pocetak mozemo slati
                //svaki put celu matricu, on se ovde prima kao string, tako da bi string 'xooxooxxx' se parsirao u matricu 3x3
                //i tako ce da se iscrta na monitoru
                console.log(data[i]);
            }
        }});*/
    e.preventDefault();
    var prom =  $(this).attr('id');
    
    console.log(prom);
    $.post('Igra.php', {polje: prom}, function(data) {
        for(i=1; i<=9; i++){
            if(data[i] == '1'){
                $('#'+i).append("<img src='slike/x.jpeg'></img>")
               
            }
        }
    });
});