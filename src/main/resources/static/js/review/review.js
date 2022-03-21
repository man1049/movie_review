let param = $('#review_code').val();
let commentPage = 0
let commentSize = 10
let nickname = $('#nickname').val();
let isCommentWriting = false;

$(function () {
    $.ajax({
        type: "GET",
        url: "/review/comments/page",
        data: {
            code: param,
            page: commentPage,
            size: commentSize,
            nickname: nickname
        },
        dataType: "json",
        success: function (list) {
            for(let i = 0; i<list.length; i++){
                $('.comments-container-scroll')
                    .append(
                        "<div class=\"comments-container-contents\">" +
                        "                        <div class=\"comment\">" +
                        "                            <div class=\"comment-nickname\">" +
                        "                                "+ list[i].nickname +
                        "                            </div>" +
                        "                            <div class=\"comment-text\">" +
                        "                                "+list[i].comment +
                        "                            </div>\n" +
                        (list[i].mycomment === true ?
                            "<div class='comment-delete'>" +
                            "<span onclick='commentDelete("+list[i].id+")'>X</span>"+
                            "</div>"
                            :
                            "<div class='comment-delete'></div>")+
                        "                        </div>" +
                        "                    </div>"
                    );
            }
            if(list.length > 0){
                commentPage++;
            }
        }
    })
})

$('.movies-container').on("click",function () {
    location.href = "/detail/"+$('#code').val();
})

$('#pageup').on("click",function () {
    $('html').animate({scrollTop : 0}, 400);
})

$('#pagedown').on("click",function () {
    $('html').animate({scrollTop : $('html').outerHeight()}, 400);
})

$('.comments-container').scroll(function (e) {

    let scrollTop = $(this).scrollTop();
    let scrollHeight = $(this).height();
    let contentHeight = $('.comments-container-scroll').height();

    if(scrollTop == contentHeight - scrollHeight){
        $.ajax({
            type: "GET",
            url: "/review/comments/page",
            data: {
                code: param,
                page: commentPage,
                size: commentSize,
                nickname: nickname
            },
            dataType: "json",
            success: function (list) {
                for(let i = 0; i<list.length; i++){
                    $('.comments-container-scroll')
                        .append(
                            "<div class=\"comments-container-contents\">" +
                            "                        <div class=\"comment\">" +
                            "                            <div class=\"comment-nickname\">" +
                            "                                "+ list[i].nickname +
                            "                            </div>" +
                            "                            <div class=\"comment-text\">" +
                            "                                "+list[i].comment +
                            "                            </div>\n" +
                            (list[i].mycomment === true ?
                                "<div class='comment-delete'>" +
                                "<span onclick='commentDelete("+list[i].id+")'>X</span>"+
                                "</div>"
                                :
                                "<div class='comment-delete'></div>")+
                            "                        </div>" +
                            "                    </div>"
                        );
                }
                if(list.length > 0){
                    commentPage++;
                }
            }
        })

    }
})

function commentDelete(id) {
    var conf = confirm("삭제하시겠습니까?")
    if(conf){
        $.ajax({
            type: "GET",
            url: "/review/comment/delete",
            data: {
                id: id,
            },
            success: function () {
                alert("댓글이 삭제되었습니다.")
                location.href = "/review/"+param
            }
        })
    }
}

$('.updown-container-up').on("click",function () {
    $.ajax({
        type: "GET",
        url: "/review/up",
        data: {
            code : param
        },
        success: function (data) {
            if(data.res){
                $('#up_count').html(data.upcount)
                $('#down_count').html(data.downcount)
            }else{
                alert("이미 추천또는 비추천을 했습니다.")
            }
        }
    });
})

$('.updown-container-down').on("click",function () {
    $.ajax({
        type: "GET",
        url: "/review/down",
        data: {
            code : param
        },
        success: function (data) {
            if(data.res){
                $('#up_count').html(data.upcount)
                $('#down_count').html(data.downcount)
            }else{
                alert("이미 추천또는 비추천을 했습니다.")
            }
        }
    });
})

function lengthCheck() {
    let commentLength = $(".write-text").val().length
    if(commentLength > 23){
        alert("23자까지만 작성 가능합니다.")
        return false;
    }else if(commentLength === 0){
        alert("댓글을 입력해주세요")
        return false;
    }else if(isCommentWriting){
        alert("댓글을 전송중입니다.")
    }else{
        isCommentWriting = true
        return true;
    }
}

let ismodify = false
// 리뷰 수정
$('.modify-container-on').on("click", function () {
    if(!ismodify){
        $('.contents').summernote({focus: true});
        $('.review-modify-container').append("<span class=\"modify-container-save\" onclick='save()'>저장하기</span>")
        ismodify = true;

        $('.note-editable').css({
            "display": "flex",
            "flex-direction": "column"
        })
        $('.note-editable > p').css({
            "display": "flex",
            "flex-direction": "column"
        })
    }
})



// 리뷰 수정 후 저장
function save() {
    $.ajax({
        type: "POST",
        url: "/review/modify",
        data: {
            code: param,
            content: $('.note-editable').html()
        },
        success: function (e) {
            $(".contents").summernote('code');
            $('.contents').summernote('destroy');
            ismodify = false;
            $('.modify-container-save').html("").css({
                "margin":"0"
            });
        }
    })
}

$('.review-delete').on("click", function () {
    var isReviewDelete = confirm("리뷰를 삭제하시겠습니까?");
    if(isReviewDelete){
        $.ajax({
            type:"POST",
            data: {
                code: param,
                nickname: nickname,
            },
            url: "/review/delete",
            success: function (is) {
                if(is){
                    alert("리뷰가 삭제되었습니다.")
                    location.href = "/reviewlist"
                }else{
                    alert("잘못된 요청입니다.")
                }

            }
        })
    }
})