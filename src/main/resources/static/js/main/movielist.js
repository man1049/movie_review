let page = localStorage.getItem("movielist") == null || localStorage.getItem("movielist") === "undefined"
         ? 1 : parseInt(localStorage.getItem("movielist"));

let backpage = 0;
let searchKeyWord = "";

let $tr = $('.page-table-tr');
let $sinput = $('.search-input')


$(function () {
    console.log(page)
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

// 페이지 번호
function pageNum(pageNum) {
    page = pageNum != null ? pageNum : page
    console.log(page)
    localStorage.setItem("movielist",page)

    if(page === 1){
        $tr.html(
            "<td class='nowpage'>1</td>"+
            "<td onClick='pageNum(2)'>2</td>"+
            "<td onClick='pageNum(3)'>3</td>"+
            "<td onClick='pageNum(4)'>4</td>"+
            "<td onClick='pageNum(5)'>5</td>"+
            "<td onClick='pageNum(6)'>6</td>"+
            "<td onClick='pageNum(7)'>7</td>"+
            "<td onClick='pageNum(8)'>8</td>"+
            "<td onClick='pageNum(9)'>9</td>"+
            "<td onClick='pageNum(10)'>10</td>"
        );
        movieListPage();
    }else if(page===2){
        $tr.html(
            "<td onClick='pageNum(1)'>1</td>"+
            "<td class='nowpage'>2</td>"+
            "<td onClick='pageNum(3)'>3</td>"+
            "<td onClick='pageNum(4)'>4</td>"+
            "<td onClick='pageNum(5)'>5</td>"+
            "<td onClick='pageNum(6)'>6</td>"+
            "<td onClick='pageNum(7)'>7</td>"+
            "<td onClick='pageNum(8)'>8</td>"+
            "<td onClick='pageNum(9)'>9</td>"+
            "<td onClick='pageNum(10)'>10</td>"
        );
        movieListPage();
    }else if(page===3){
        $tr.html(
            "<td onClick='pageNum(1)'>1</td>"+
            "<td onClick='pageNum(2)'>2</td>"+
            "<td class='nowpage'>3</td>"+
            "<td onClick='pageNum(4)'>4</td>"+
            "<td onClick='pageNum(5)'>5</td>"+
            "<td onClick='pageNum(6)'>6</td>"+
            "<td onClick='pageNum(7)'>7</td>"+
            "<td onClick='pageNum(8)'>8</td>"+
            "<td onClick='pageNum(9)'>9</td>"+
            "<td onClick='pageNum(10)'>10</td>"
        );
        movieListPage();
    }else if(page===4){
        $tr.html(
            "<td onClick='pageNum(1)'>1</td>"+
            "<td onClick='pageNum(2)'>2</td>"+
            "<td onClick='pageNum(3)'>3</td>"+
            "<td class='nowpage'>4</td>"+
            "<td onClick='pageNum(5)'>5</td>"+
            "<td onClick='pageNum(6)'>6</td>"+
            "<td onClick='pageNum(7)'>7</td>"+
            "<td onClick='pageNum(8)'>8</td>"+
            "<td onClick='pageNum(9)'>9</td>"+
            "<td onClick='pageNum(10)'>10</td>"
        );
        movieListPage();
    }else if(page===5){
        $tr.html(
            "<td onClick='pageNum(1)'>1</td>"+
            "<td onClick='pageNum(2)'>2</td>"+
            "<td onClick='pageNum(3)'>3</td>"+
            "<td onClick='pageNum(4)'>4</td>"+
            "<td class='nowpage'>5</td>"+
            "<td onClick='pageNum(6)'>6</td>"+
            "<td onClick='pageNum(7)'>7</td>"+
            "<td onClick='pageNum(8)'>8</td>"+
            "<td onClick='pageNum(9)'>9</td>"+
            "<td onClick='pageNum(10)'>10</td>"
        );
        movieListPage();
    }else{
        $tr.html("");
        let i = page-4;

        while (i <= page+4){
            if(i !== page){
                $tr.append(
                    "<td onClick='pageNum("+i+")'>"+i+"</td>"
                )
            }else{
                $tr.append(
                    "<td class='nowpage'>"+i+"</td>"
                );
            }
            i++
        }
        movieListPage();
    }
}

// 페이지 버튼을 누를 시 페이지에 맞는 영화목록
function movieListPage() {
    $.ajax({
        type: "GET",
        url: "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json",
        data: {
            key: "ceabdcb6d52d7eb5709bbb09dc253b97",
            itemPerPage: "20",
            curPage: page,
            movieNm: searchKeyWord
        },
        dataType: "json",
        success:function (data) {
            let list = data.movieListResult.movieList;

            if((list == null && page === 1) || (list === "" && page === 1) || (list.size === 0 && page === 1)){
                alert("검색 결과가 없습니다.")
                searchKeyWord = ""
                pageNum(1)
            }else if(list === "" || list.size === 0){
                alert("없는 페이지입니다.");
                pageNum(backpage);
            }else{
                backpage = page
                // 영화 목록을 다시 부르기위해 초기화 함
                $('.movielist').html("")

                for(let i = 0; i<20; i++){
                    let director
                    try{
                        director = list[i].directors[0].peopleNm;
                    }catch (e) {
                        director = "";
                    }
                    $('.movielist')
                        .append(
                            "<div class=\"movielist-posters\" onclick='detail_view("+list[i].movieCd+")'\">\n" +
                            "   <img class=\"movielist-posters-img\" src=\"resources/images/movieposter/default.png\">\n" +
                            "   <div class=\"movielist-posters-title\">\n" +
                            "       <span>"+list[i].movieNm+"</span>\n" +
                            "   </div>\n" +
                            "</div>"
                        )
                }
            }
        }
    });
}

function detail_view(code) {
    location.href = ('detaile?code='+code);
}