let menuheight

let menuToggle = false
$(function () {
    menuheight = $('.nav-button-menu').innerHeight();
    $('.nav-button-menu').css({
        'top':menuheight*(-1)
    })
})

function navbutton() {
    if (menuToggle === false) {
        $('.nav-button-menu').css({
            'top': '0'
        })
        menuToggle = true
    }else{
        $('.nav-button-menu').css({
            'top':menuheight*(-1)
        })
        menuToggle = false
    }
}

$(window).resize(function () {
    menuheight = $('.nav-button-menu').innerHeight();
    if(menuToggle === false){
        $('.nav-button-menu').css({
            'top':menuheight*(-1)
        })
    }else{
        $('.nav-button-menu').css({
            'top':'0'
        })
    }
})