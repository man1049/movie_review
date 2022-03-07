let page = 1;
let backpage = 0;
let searchKeyWord = "";
$(function () {
    movieListPage();
    /*
    console.log("시작")
    $.ajax({
        type: "GET",
        url: "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json",
        data: {
            key: "ceabdcb6d52d7eb5709bbb09dc253b97",
            itemPerPage: "20"
        },
        dataType: "json",
        success:function (data) {
            let list = data.movieListResult.movieList;

            console.log(data)
            console.log(list)
            for(let i = 0; i<20; i++){
                let director
                try{
                    director = list[i].directors[0].peopleNm;
                }catch (e) {
                    director = "";
                }
                $('.list-table')
                    .append("<tr onclick='alert("+list[i].movieCd+")'>" +
                    "<td>"+list[i].movieNm+"</td>"+ // 이름
                    "<td>"+list[i].genreAlt+"</td>"+ // 장르
                    "<td>"+director+"</td>"+ // 감독
                    "<td>"+list[i].openDt+"</td>"+ // 개봉일
                    "<td>"+list[i].prdtStatNm+"</td>"+ // 제작상태
                    "<td>"+list[i].nationAlt+"</td>"+ // 제작국가
                    "</tr>")
            }
        }
    });
    */
});
$('.search').keypress(function (e) {
    // enter의 키코드는 13
    if(e.keyCode === 13){
        search();

    }
})
function search() {
    searchKeyWord = $('.search').val()
    if(searchKeyWord == null || searchKeyWord == ""){
        alert("검색어를 입력해주세요");
    }else{
        backpage = page
        pageNum(1)
    }
}
// 페이지 번호
function pageNum(pageNum) {
    page = pageNum
    if(page == 1){
        $('.page-table-tr').html(
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
    }else if(page==2){
        $('.page-table-tr').html(
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
    }else if(page==3){
        $('.page-table-tr').html(
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
    }else if(page==4){
        $('.page-table-tr').html(
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
    }else if(page==5){
        $('.page-table-tr').html(
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
        $('.page-table-tr').html("");
        for(let i = page-4; i<=page+5; i++ ){
            if(i > page+5){
                break;
            }
            if(i == page){
                $('.page-table-tr').append(
                    "<td class='nowpage'>"+i+"</td>"
                );
            }else{
                $('.page-table-tr').append(
                    "<td onClick='pageNum("+i+")'>"+i+"</td>"
                );
            }
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
            movieNm: searchKeyWord,
            repNationCd: 22041011
        },
        dataType: "json",
        success:function (data) {
            let list = data.movieListResult.movieList;

            if((list == null && page == 1) || (list == "" && page == 1) || (list.size == 0 && page == 1)){
                alert("검색 결과가 없습니다.")
                searchKeyWord = ""
                pageNum(1)
            }else if(list == null || list == "" || list.size == 0){
                alert("없는 페이지입니다.");
                pageNum(backpage);
            }else{
                backpage = page
                // 목록 초기화
                $('.list-table').html("<th>이름</th>\n" +
                    "                <th>장르</th>\n" +
                    "                <th>감독</th>\n" +
                    "                <th>개봉일</th>\n" +
                    "                <th>제작상태</th>\n" +
                    "                <th>제작국가</th>")

                for(let i = 0; i<20; i++){
                    let director
                    try{
                        director = list[i].directors[0].peopleNm;
                    }catch (e) {
                        director = "";
                    }
                    $('.list-table')
                        .append("<tr onclick=detail_view("+list[i].movieCd+")>" +
                            "<td>"+list[i].movieNm+"</td>"+ // 이름
                            "<td>"+list[i].genreAlt+"</td>"+ // 장르
                            "<td>"+director+"</td>"+ // 감독
                            "<td>"+list[i].openDt+"</td>"+ // 개봉일
                            "<td>"+list[i].prdtStatNm+"</td>"+ // 제작상태
                            "<td>"+list[i].nationAlt+"</td>"+ // 제작국가
                            "</tr>")
                }
            }
        }
    });
}

function detail_view(code) {
    location.href = ('detaile?code='+code);
}