$(function() {
    $("#${modelName}_add").click(function(){
        getIframe('/${modelName?uncap_first}/add');
    });
    $(".${modelName}_del").each(function(index){
        $(this).on("click",function(){
            var data = $(this).attr('id');
            getdel("/${modelName?uncap_first}/del?id="+data,"DELETE",data);
        });
    });
    $(".${modelName}_edit").click(function(){
        getIframe('/${modelName?uncap_first}/edit?id='+$(this).attr('id'));
    });
    $(".${modelName}_details").click(function(){
        getIframe('/${modelName?uncap_first}/details?id='+$(this).attr('id'));
    });
})