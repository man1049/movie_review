
// ajax로 json 데이터파싱해서 보여주기
let todayBoxOffice
let data

$(function () {

    let nowday = new Date();
    let year = nowday.getFullYear();
    let month = nowday.getMonth() + 1;
    let day = ('0' + nowday.getDate()).slice(-2)-1;

    if(day < 10){
        day = '0'+day
    }
    if(month < 10){
        month = '0'+(nowday.getMonth() + 1);
    }

    let nowdateString = year + month + day;


    nowday.setDate(nowday.getDate()-7)
    year = nowday.getFullYear();
    month = nowday.getMonth() + 1;
    day = ('0' + nowday.getDate()).slice(-2)-1;

    if(day < 10){
        day = '0'+day
    }
    if(month < 10){
        month = '0'+(nowday.getMonth() + 1);
    }
    if(month > 12){
        month = "01"
    }

    let weekdateString = year + month + day;

    new Splide( '.banner-slide',{
        type: 'loop', // 페이지 끝에 도달시 처음으로
        autoplay: 'paused', // 자동페이지 넘김
        pauseOnHover: 'true', // 마우스 hover시 자동페이지넘김 정지
        arrows: false, // 양쪽 화살표
        pagination: false // 페이지 라디오버튼
    }).mount();



    /*
    var $container = $(".today-top10");
    var $scroller = $(".today-top10");
    bindDragScroll($container, $scroller);

    var $container = $(".week-top10");
    var $scroller = $(".week-top10");
    bindDragScroll($container, $scroller);
    */


    $.ajax({
        type: "GET",
        url: "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json",
        data: {
            key: "ceabdcb6d52d7eb5709bbb09dc253b97",
            targetDt: nowdateString
        },
        dataType: "json",
        success:function (today) {
            todayBoxOffice = today;
            list = today.boxOfficeResult.dailyBoxOfficeList;

            for(let i = 0; i<10; i++) {
                $('.today-top10').append(
                    "<li class=\"splide__slide\">"+
                    "   <div class=\"today-top10-posters\">\n" +
                    "       <img class=\"today-top10-posters-img\" src=\"resources/images/movieposter/default.png\" onclick=\"detaile("+list[i].movieCd+")\">\n" +
                    "       <div class=\"today-top10-posters-title\">" +
                    "           <span>"+list[i].movieNm+"</span>" +
                    "       </div>\n" +
                    "   </div>"+
                    "</li>"
                )

            }

            new Splide( '.today-slide', {
                type: 'loop',
                perPage: 4,
                perMove: 1,
                pagination: false,
                arrows: false
            }).mount();
            //rankDetaile(0);
        }
    });

    $.ajax({
        type: "GET",
        url: "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchWeeklyBoxOfficeList.json",
        data: {
            key: "ceabdcb6d52d7eb5709bbb09dc253b97",
            targetDt: weekdateString
        },
        dataType: "json",
        success:function (week) {
            weekBoxOffice = week;
            list = week.boxOfficeResult.weeklyBoxOfficeList;
            for(let i = 0; i<10; i++) {
                $('.week-top10').append(
                    "<li class=\"splide__slide\">"+
                    "   <div class=\"week-top10-posters\">\n" +
                    "       <img class=\"week-top10-posters-img\" src=\"resources/images/movieposter/default.png\" onclick=\"detaile("+list[i].movieCd+")\">\n" +
                    "       <div class=\"week-top10-posters-title\">" +
                    "           <span>"+list[i].movieNm+"</span>" +
                    "       </div>\n" +
                    "   </div>"+
                    "</li>"
                )

            }
            new Splide( '.week-slide', {
                type: 'loop',
                perPage: 4,
                perMove: 1,
                pagination: false,
                arrows: false
            }).mount();
            //rankDetaile(0);
        }
    });

});

function detaile(code) {
    location.href="detaile?code="+code
}
/*
function rankDetaile(i) {
    $.ajax({
        type: "GET",
        url: "http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json",
        data: {
            key: "ceabdcb6d52d7eb5709bbb09dc253b97",
            movieCd: todayBoxOffice.boxOfficeResult.dailyBoxOfficeList[i].movieCd
        },
        dataType: "json",
        success:function (detaile) {

            data = detaile.movieInfoResult.movieInfo
            let min
            let hour
            if(data.showTm/60 > 0){
                hour = Math.floor(data.showTm/60);
                min = data.showTm-(hour*60);
            }

            try{
                director = data.directors[0].peopleNm
            }catch (e) {
                director = "정보없음"
            }

            $('.div-list-container-detaile').html(
                "<div class='detaile-img'>\n" +
                "                        <img src='resources/images/movieposter/"+data.movieCd+".png'>\n" +
                "                    </div>\n" +
                "                <div class='detaile-contents'>\n" +
                "                    <div class='detaile-contents-title'>\n" +
                "                        <div class='title-kor'>"+data.movieNm+"</div>\n" +
                "                        <div class='title-en'>"+data.movieNmEn+"</div>\n" +
                "                    </div>\n" +
                "                    <div class='detaile-contents-top'>\n" +
                "                        <div class='top-title'>개봉일</div>\n" +
                "                        <div class='top-contents'>"+data.openDt+"</div>\n" +
                "\n" +
                "                        <div class='top-title'>제작상태</div>\n" +
                "                        <div class='top-contents'>"+data.prdtStatNm+"</div>\n" +
                "\n" +
                "                        <div class='top-title'>영화구분</div>\n" +
                "                        <div class='top-contents'>"+data.typeNm+"</div>\n" +
                "                    </div>\n" +
                "                    <div class='detaile-contents-middle'>\n" +
                "                        <div class='middle-title'>관람등급</div>\n" +
                "                        <div class='middle-contents'>"+data.audits[0].watchGradeNm+"</div>\n" +
                "\n" +
                "                        <div class='middle-title'>상영시간</div>\n" +
                "                        <div class='middle-contents'>"+ (hour > 0 ? (hour+'시간'+min+'분'): (min+'분')) + "</div>\n" +
                "\n" +
                "                        <div class='middle-title'>제작국가</div>\n" +
                "                        <div class='middle-contents'>"+(data.nations[0].nationNm == null? ("정보없음"):(data.nations[0].nationNm)) +"</div>\n" +
                "                    </div>\n" +
                "                    <div class='detaile-contents-bottom'>\n" +
                "                        <div class='bottom-title'>감독</div>\n" +
                "                        <div class='bottom-contents'>"+director+"</div>\n" +
                "\n" +
                "                        <div class='bottom-title'>장르</div>\n" +
                "                        <div class='bottom-contents'>"+data.genres[0].genreNm+"</div>\n" +
                "\n" +
                "                    </div>\n" +
                "                </div>"

            )
        }
    })
}
*/
