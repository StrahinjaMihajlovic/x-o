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
      var igrac = $('#vrednost').text();
      //console.log(igrac);
    
   
    $.post('Igra.php', {polje: prom, igr: igrac}, function(data) {
      /*  for(i=1; i<=9; i++){
            if(data[i] == '1'){
                $('#'+i).append("<img src='slike/x.jpeg'></img>")
               
            }*/
        
        console.log(data);
       $('.polje').empty();
        var o;
        var i;
        for(i = 0; i<9; i++){
           o = data.charAt(i);
           console.log(data[i]);
               
            
            if(o.valueOf() === "x".valueOf()){
            
            $('#'+ (i+1)).html("<img src='slike/x.jpg' class='slika'></img>");
            }else if(o.valueOf() === "o".valueOf()){
                $('#'+ (i+1)).html("<img src='slike/o.jpeg' class='slika'></img>");
            }else if(o.valueOf() === 't'.valueOf()){
                window.alert('pobedio je igrac X');
                break;
            }else if(o.valueOf() === 'r'.valueOf()){
                window.alert('pobedio je igrac O');
            }
        }
        
    });
});