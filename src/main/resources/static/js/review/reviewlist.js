let searchKeyWord = "";
let reviewListPage = 1;
let isScrollLoading = false;
let isScrollLoadingBreak = false;
let $keyword = $('#keyword');
let $keywordValue
let $searchValue



$(function () {
    $(window).scroll(function (e) {

        let scrollTop = $(window).scrollTop();
        let scrollHeight = $(window).height();
        let contentHeight = $('body').height();
        if($keywordValue == null){
            $keywordValue = ""
        }
        if($searchValue == null){
            $searchValue = ""
        }

        if(scrollTop > (contentHeight - scrollHeight)-150 && !isScrollLoading && !isScrollLoadingBreak){
            isScrollLoading = true;
            $.ajax({
                type: "GET",
                url: "/reviewlist/page",
                data: {
                    page: reviewListPage,
                    keyword: $keywordValue,
                    search: $searchValue
                },
                dataType: "json",
                success: function (list) {
                    for(let i = 0; i<list.length; i++){
                        let star = ""
                        for(let x = 0; x<list[i].star ; x++){
                            star += "★";
                        }
                        $('.reviewlist')
                            .append(
                                "<div class=\"reviewlist-review\" onclick=\"review_view("+ list[i].num +")\">"+
                                "            <img class=\"reviewlist-review-img\" src=\""+ list[i].thumbnail +"\">\n" +
                                "            <div class=\"reviewlist-review-container\">\n" +
                                "                <span class=\"reviewlist-review-container-title\">"+list[i].title+"</span>\n" +
                                "                <div class=\"reviewlist-review-container-detail\">\n" +
                                "                    <div class=\"detail-title\">평점</div>\n" +
                                "                    <div class=\"detail-content\">"+ star +"</div>\n" +
                                "\n" +
                                "                    <div class=\"detail-title\">추천수</div>\n" +
                                "                    <div class=\"detail-content\">" + list[i].up + "</div>\n" +
                                "\n" +
                                "                    <div class=\"detail-title\">닉네임</div>\n" +
                                "                    <div class=\"detail-content\">" + list[i].nickname + "</div>\n" +
                                "\n" +
                                "                    <div class=\"detail-title\">영화명</div>\n" +
                                "                    <div class=\"detail-content\"><span>"+ list[i].moviename +"</span></div>\n" +
                                "\n" +
                                "                </div>\n" +
                                "            </div>\n" +
                                "        </div>"
                            );
                    }
                    if(list.length > 0){
                        reviewListPage++;
                    }else{
                        isScrollLoadingBreak = true
                    }
                    isScrollLoading = false;
                }
            })

        }
    })
})



/*
$(function () {
    pageNum();
})
$sinput.keypress(function (e) {
    // enter의 키코드는 13
    if(e.keyCode === 13){
        search();

    }
})
function search() {
    searchKeyWord = $sinput.val()
    if(searchKeyWord == null || searchKeyWord === ""){
        alert("검색어를 입력해주세요");
    }else{
        backpage = page
        pageNum(1)
    }
}
 */


// 검색기능

function search() {
    reviewListPage = 0;
    $keywordValue = $keyword.val()
    $searchValue = $('#search').val()
    isScrollLoadingBreak = false
    isScrollLoading = false

    $.ajax({
        type: "GET",
        url: "/reviewlist/page",
        data: {
            page: reviewListPage,
            keyword: $keywordValue,
            search: $searchValue
        },
        dataType: "json",
        success: function (list) {
            $('.reviewlist').html("")
            for(let i = 0; i<list.length; i++){
                let star = ""
                for(let x = 0; x<list[i].star ; x++){
                    star += "★";
                }
                $('.reviewlist')
                    .append(
                        "<div class=\"reviewlist-review\" onclick=\"review_view("+ list[i].num +")\">"+
                        "            <img class=\"reviewlist-review-img\" src=\""+ list[i].thumbnail +"\">\n" +
                        "            <div class=\"reviewlist-review-container\">\n" +
                        "                <span class=\"reviewlist-review-container-title\">"+list[i].title+"</span>\n" +
                        "                <div class=\"reviewlist-review-container-detail\">\n" +
                        "                    <div class=\"detail-title\">평점</div>\n" +
                        "                    <div class=\"detail-content\">"+ star +"</div>\n" +
                        "\n" +
                        "                    <div class=\"detail-title\">추천수</div>\n" +
                        "                    <div class=\"detail-content\">" + list[i].up + "</div>\n" +
                        "\n" +
                        "                    <div class=\"detail-title\">닉네임</div>\n" +
                        "                    <div class=\"detail-content\">" + list[i].nickname + "</div>\n" +
                        "\n" +
                        "                    <div class=\"detail-title\">영화명</div>\n" +
                        "                    <div class=\"detail-content\"><span>"+ list[i].moviename +"</span></div>\n" +
                        "\n" +
                        "                </div>\n" +
                        "            </div>\n" +
                        "        </div>"
                    );
            }
            if(list.length > 0){
                reviewListPage++;
            }else{
                isScrollLoadingBreak = true
            }
            isScrollLoading = false;
        }
    })
}

$("#search_button").on("click", function () {
    search();
})

$keyword.keypress(function (e) {
    if(e.keyCode === 13) {
        search();
    }
})




function review_view(reviewNum) {
    location.href = 'review/'+reviewNum;
}