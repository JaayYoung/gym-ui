$(function() {
$(window).scroll(function(){
				$(".top").show();
				if($(window).scrollTop()<=300){
					$(".top").hide();
				}
			});
			
   $(".top").click(function(){
       $("html").animate({"scrollTop": "0px"},100); //IE,FF
       $("body").animate({"scrollTop": "0px"},100); //Webkit
       });
 });