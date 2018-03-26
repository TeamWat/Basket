$('table tr').click(function() {
    
    //$('table tr td a').addClass('btn-hidden');
    //$(this).children('td').children('a').removeClass('btn-hidden');

    // アニメーション
    $('table tr td a').addClass('btn-hidden');
    $('table tr td a').removeClass('btn-visible');
    $(this).children('td').children('a').addClass('btn-visible');    
    
});