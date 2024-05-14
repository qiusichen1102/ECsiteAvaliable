$(document).ready(function(){
    $('h1').hover(function(){
        $(this).css('background-color','#ff0000')
    })
})


function fileCheck(){
	var file = document.getElementsByName('filename')[0].value;
	if(file.length>0){
		document.getElementById('importButton').disabled=false;
	}else{
		document.getElementById('importButton').disabled=true;
	}

}
