let url = location.search
let param = new URLSearchParams(url).get('code')
let director
var windowWidth
let dir_flag = false
let actor_flag = false
let chart_flag = false

/* 드래그스크롤 입니다. */

var $container = $(".comments-container");
var $scroller = $(".comments-container");
bindDragScroll($container, $scroller);

let commentPage = 0;
let commentSize = 15;

var commentChart;

$('.comments-container').scroll(function (e) {

    let scrollTop = $(this).scrollTop();
    let scrollHeight = $(this).height();
    let contentHeight = $('.comments-container-scroll').height();

    if(scrollTop == contentHeight - scrollHeight){
        $.ajax({
            type: "GET",
            url: "detaile/comments/page",
            data: {
                code: param,
                page: commentPage,
                size: commentSize

            },
            dataType: "json",
            success: function (list) {

                for(let i = 0; i<list.length; i++){
                    let star = "";
                    for(let x = 0; x<list[i].star; x++){
                        star += "★";
                    }

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
                            "                            <div class=\"comment-star\">" +
                            "                                "+star +
                            "                            </div>" +
                            "                        </div>" +
                            "                    </div>"
                        );
                }
                commentPage++;
            }
        })

    }
})

$(function (e) {

    /*
    // 좌우 스크롤입니다
    $(".detaile-container-director-movies").mousewheel(function(event, delta) {
        this.scrollLeft -= (delta * 100);
        event.preventDefault();
    });
    $(".detaile-container-actor-staffs").mousewheel(function(event, delta) {
        this.scrollLeft -= (delta * 100);
        event.preventDefault();
    });
    */

    $('.write-hidden-code').val(param)
    let star_avg = 0.0



    $.ajax({
        type: 'GET',
        url: 'http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieInfo.json',
        data : {
            key : 'ceabdcb6d52d7eb5709bbb09dc253b97',
            movieCd: param
        },
        dataType: "json",
        success: function (data) {
            let mv = data.movieInfoResult.movieInfo

            try{
                director = mv.directors[0].peopleNm
            }catch (e) {
                director = "정보없음"
            }

            let nation
            try{
                nation = mv.nations[0].nationNm
            }catch (e) {
                nation = "정보없음"
            }
            let watchGrade
            try{
                watchGrade = mv.audits[0].watchGradeNm
            }catch (e) {
                watchGrade = "정보없음"
            }

            let min
            let hour
            if(mv.showTm/60 > 0){
                hour = Math.floor(mv.showTm/60);
                min = mv.showTm-(hour*60);
            }


            //$('.detaile-container-top-img').html('<img src="'+code+'.png">')

            $('.detaile-table').html("<tr class=\"table-tr\">\n" +
                "                        <td class=\"table-td-title\">영화명</td>\n" +
                "                        <td class=\"table-td-content\">"+mv.movieNm+"</td>\n" +
                "                        <td class=\"table-td-title\">개봉연도</td>\n" +
                "                        <td class=\"table-td-content\">"+mv.openDt+"("+mv.prdtStatNm+")"+"</td>\n" +
                "                    </tr>\n" +
                "                    <tr class=\"table-tr\">\n" +
                "                        <td class=\"table-td-title\">감독명</td>\n" +
                "                        <td class=\"table-td-content\">"+director+"</td>\n" +
                "                        <td class=\"table-td-title\">제작국가</td>\n" +
                "                        <td class=\"table-td-content\">"+nation+"</td>\n" +
                "                    </tr>\n" +
                "                    <tr class=\"table-tr\">\n" +
                "                        <td class=\"table-td-title\">장르명</td>\n" +
                "                        <td class=\"table-td-content\">"+mv.genres[0].genreNm+"</td>\n" +
                "                        <td class=\"table-td-title\">상영시간</td>\n" +
                "                        <td class=\"table-td-content\">"+(min == null ? "정보없음" : (hour > 0 ? (hour+'시간'+min+'분'): (min+'분'))) + "</td>\n" +
                "                    </tr>\n" +
                "                    <tr class=\"table-tr\">\n" +
                "                        <td class=\"table-td-title\">심의정보</td>\n" +
                "                        <td class=\"table-td-content\">"+watchGrade+"</td>\n" +
                "                        <td class=\"table-td-title\">유저평점</td>\n" +
                "                        <td class=\"table-td-content-star\">"+(star_avg == 0.0? "평점없음": star_avg)+"</td>\n" +
                "                    </tr>")
            let actor = mv.actors


            if(actor.length === 0){
                $('.detaile-container-actor').html('')
                $('.detaile-container-actor').css({
                    'width':'0px',
                    'height':'0px'
                })
                actor_flag = true;
            }else{
                if(window.innerWidth > 1280){
                    $('.detaile-container-actor').css({
                        'width':'45vw',
                        'height':'15vw',
                        'display':'flex',
                        'flex-wrap':'wrap',
                        'margin-top': '2vw',
                    })

                }else{
                    $('.detaile-container-actor').css({
                        'width':'95vw',
                        'height': '27vw',
                        'display': 'flex',
                        'flex-wrap': 'wrap',
                        'margin-top': '6vw'
                    })
                }

                $('.detaile-container-actor').append(
                    "<div class=\"detaile-container-actor-title\">\n" +
                    "                배우\n" +
                    "            </div>\n" +
                    "            <div class=\"detaile-container-actor-staffs\"></div>"
                )
                for(let i = 0 ; i < Object.keys(actor).length; i++){
                    $('.detaile-container-actor-staffs')
                        .append(
                            "<div class=\"staffs-container\">"+
                            "<div class=\"staffs-container-img\">\n" +
                            ""+
                            "                    </div>\n" +
                            "                    <div class=\"staffs-container-title\">\n" +
                            "                        <div><span>"+actor[i].peopleNm+"</span></div>\n" +
                            "                        <div><span>"+actor[i].cast+"</span></div>\n" +
                            "                    </div>"+
                            "</div>"
                        );
                }

                var $container = $(".detaile-container-actor-staffs");
                var $scroller = $(".detaile-container-actor-staffs");
                bindDragScroll($container, $scroller);
            }



            $.ajax({
                type: 'GET',
                url: 'http://www.kobis.or.kr/kobisopenapi/webservice/rest/movie/searchMovieList.json',
                data : {
                    key : 'ceabdcb6d52d7eb5709bbb09dc253b97',
                    directorNm : director,
                    itemPerPage : 100
                },
                dataType: "json",
                success: function (data) {
                    let dir = data.movieListResult.movieList

                    if(dir.length === 0){
                        dir_flag = true
                        $('.detaile-container-director').html('')
                        $('.detaile-container-director').css({
                            'width':'0px',
                            'height':'0px'
                        })
                        if(actor_flag === false && window.innerWidth > 1280) {
                            $('.detaile-container-actor').css({
                                'width': '95vw',
                                'height': '15vw',
                                'display': 'flex',
                                'flex-wrap': 'wrap',
                                'margin-top': '2vw'
                            })
                        }else {
                            $('.detaile-container-actor').css({
                                'width': '95vw',
                                'height': '27vw',
                                'display': 'flex',
                                'flex-wrap': 'wrap',
                                'margin-top': '6vw'
                            })
                        }

                    }else{
                        if(actor_flag === true  && window.innerWidth > 1280){

                            $('.detaile-container-director').css({
                                'width':'95vw',
                                'height':'15vw',
                                'display':'flex',
                                'flex-wrap':'wrap',
                                'margin-top': '2vw'
                            })
                        }else if(actor_flag === false && window.innerWidth > 1280){
                            $('.detaile-container-director').css({
                                'width':'45vw',
                                'height':'15vw',
                                'display':'flex',
                                'flex-wrap':'wrap',
                                'margin-top': '2vw',
                                'margin-right': '2%'
                            })
                        }else{
                            $('.detaile-container-director').css({
                                'width':'95vw',
                                'height':'27vw',
                                'display':'flex',
                                'flex-wrap':'wrap',
                                'margin-top': '6vw'
                            })
                        }

                        $('.detaile-container-director').append(
                            "<div class=\"detaile-container-director-title\">\n" +
                            "                감독의 영화\n" +
                            "            </div>\n" +
                            "            <div class=\"detaile-container-director-movies\"></div>"
                        )
                        for(let i = 0; i < Object.keys(dir).length; i++){
                            $(".detaile-container-director-movies")
                                .append(
                                    "<div class=\"movies-container\">\n" +
                                    "                    <div class=\"movies-container-img\">\n" +
                                    "" +
                                    "                    </div>\n" +
                                    "                    <div class=\"movies-container-title\">\n" +
                                    "                        <div class='movies-name'><span>"+dir[i].movieNm+"</span></div>\n" +
                                    "                        <div class='movies-year'><span>("+dir[i].prdtYear+")</span></div>\n" +
                                    "                    </div>" +
                                    "</div>"
                                )
                        }
                        var $container = $(".detaile-container-director-movies");
                        var $scroller = $(".detaile-container-director-movies");
                        bindDragScroll($container, $scroller);
                    }
                }
            })
        }
    })

    $.ajax({
        type: "GET",
        url: "detaile/comments/info",
        data: {
            code: param
        },
        dataType: "json",
        success: function (comments) {
            commentChart = comments
            comments.star_avg == 'NaN'? star_avg = 0 : star_avg = comments.star_avg
            let list = comments.comments
            console.log(comments)
            $('.table-td-content-star').text(star_avg+'★');
            while(true){
                if(list.length === 0){
                    $('.detaile-userinfo').html('');
                    $('.detaile-userinfo').css({'height':'0px','margin-top':'0'});
                    $('.detaile-userinfo-title').html('');
                    $('.detaile-userinfo-title').css({'height':'0px','margin-top':'0'})
                    chart_flag = true;
                    break;
                }
                for(let i = 0; i<list.length; i++){
                    let star = "";
                    for(let x = 0; x<list[i].star; x++){
                        star += "★";
                    }

                    $('.comments-container-scroll')
                        .append(
                            "<div class=\"comments-container-contents\">" +
                            "                        <div class=\"comment\">" +
                            "                            <div class=\"comment-nickname\">" +
                            "                                "+ list[i].nickname +
                            "                            </div>" +
                            "                            <div class=\"comment-text\">" +
                            "                                "+list[i].text +
                            "                            </div>\n" +
                            "                            <div class=\"comment-star\">" +
                            "                                "+star +
                            "                            </div>" +
                            "                        </div>" +
                            "                    </div>"
                        );
                }
                commentPage++;

                google.charts.load('current', {'packages':['corechart']});
                google.charts.setOnLoadCallback(drawChart);
                function drawChart() {
                    var data = google.visualization.arrayToDataTable([
                        ['Task','gender'],
                        ['남자',     comments.man],
                        ['여자',      comments.woman],
                    ]);
                    var options = {
                        pieHole:0.4,
                        legend: {
                            position: 'bottom',
                            textStyle: {
                                color: 'white',
                                fontSize: 13
                            }
                        },
                        backgroundColor: 'black',
                        chartArea: {
                            width: '75%',
                            height: '75%'
                        },
                        tooltip: {
                            trigger: 'none'
                        },
                    };
                    var chart = new google.visualization.PieChart(document.getElementById('gender'));
                    chart.draw(data, options);



                    var data = google.visualization.arrayToDataTable([
                        ['Task','age'],
                        ['10',     comments.ages.a10],
                        ['20',      comments.ages.a20],
                        ['30',      comments.ages.a30],
                        ['40',      comments.ages.a40],
                        ['50~',      comments.ages.other],
                    ]);
                    var options = {
                        pieHole:0.4,
                        legend: {
                            position: 'bottom',
                            textStyle: {
                                color: 'white',
                                fontSize: 13
                            }
                        },
                        backgroundColor: 'black',
                        chartArea: {
                            width: '75%',
                            height: '75%'
                        },
                        tooltip: {
                            trigger: 'none'
                        }
                    };
                    var chart = new google.visualization.PieChart(document.getElementById('age'));
                    chart.draw(data, options);

                    var data = google.visualization.arrayToDataTable([
                        ['Task','stars'],
                        ['1',     comments.stars.s1],
                        ['2',      comments.stars.s2],
                        ['3',      comments.stars.s3],
                        ['4',      comments.stars.s4],
                        ['5',      comments.stars.s5],
                    ]);
                    var options = {
                        pieHole:0.4,
                        legend: {
                            position: 'bottom',
                            textStyle: {
                                color: 'white',
                                fontSize: 13
                            }
                        },
                        backgroundColor: 'black',
                        chartArea: {
                            width: '75%',
                            height: '75%'
                        },
                        tooltip: {
                            trigger: 'none'
                        }
                    };
                    var chart = new google.visualization.PieChart(document.getElementById('star'));
                    chart.draw(data, options);

                }


                break;
            }
        }
    })
})
$(window).resize(function (e) {
    var comments = commentChart
    windowWidth = window.innerWidth

    if(windowWidth > 1280){
        if(dir_flag === false){
            if(actor_flag === false){
                $('.detaile-container-director').css({
                    'width':'45vw',
                    'height':'15vw',
                    'display':'flex',
                    'flex-wrap':'wrap',
                    'margin-top': '2vw',
                    'margin-right': '2%'
                })
            }else{
                $('.detaile-container-director').css({
                    'width':'95vw',
                    'height':'15vw',
                    'display':'flex',
                    'flex-wrap':'wrap',
                    'margin-top': '2vw',
                    'margin-right': '0'
                })
            }

        }else{
            $('.detaile-container-director').css({
                'width':'0px',
                'height':'0px'
            })
        }

        if(actor_flag === false){
            if(dir_flag === false){
                $('.detaile-container-actor').css({
                    'width':'45vw',
                    'height':'15vw',
                    'display':'flex',
                    'flex-wrap':'wrap',
                    'margin-top': '2vw',
                })
            }else{
                $('.detaile-container-actor').css({
                    'width':'95vw',
                    'height':'15vw',
                    'display':'flex',
                    'flex-wrap':'wrap',
                    'margin-top': '2vw',
                })
            }

        }else{
            $('.detaile-container-actor').css({
                'width':'0px',
                'height':'0px'
            })
        }
    }else{

        if(dir_flag === false){
            $('.detaile-container-director').css({
                'width':'95vw',
                'height':'27vw',
                'display':'flex',
                'flex-wrap':'wrap',
                'margin-top': '3vw',
                'margin-right': '0'
            })
        }else{
            $('.detaile-container-director').css({
                'width':'0px',
                'height':'0px'
            })
        }

        if(actor_flag === false){
            $('.detaile-container-actor').css({
                'width':'95vw',
                'height':'27vw',
                'display':'flex',
                'flex-wrap':'wrap',
                'margin-top': '6vw',
            })
        }else{
            $('.detaile-container-actor').css({
                'width':'0px',
                'height':'0px'
            })
        }



    }
    if(chart_flag === false){
        google.charts.load('current', {'packages':['corechart']});
        google.charts.setOnLoadCallback(drawChart);
        function drawChart() {
            if(windowWidth > 1280){
                var data = google.visualization.arrayToDataTable([
                    ['Task','gender'],
                    ['남자',     comments.man],
                    ['여자',      comments.woman],
                ]);
            }else{
                var data = google.visualization.arrayToDataTable([
                    ['Task','gender'],
                    ['남',     comments.man],
                    ['여',      comments.woman],
                ]);
            }
            var options = {
                pieHole:0.4,
                legend: {
                    position: 'bottom',
                    textStyle: {
                        color: 'white',
                        fontSize: 13
                    }
                },
                backgroundColor: 'black',
                chartArea: {
                    width: '75%',
                    height: '75%'
                },
                tooltip: {
                    trigger: 'none'
                },
            };


            var chart = new google.visualization.PieChart(document.getElementById('gender'));
            chart.draw(data, options);


            if(windowWidth > 1280){
                var data = google.visualization.arrayToDataTable([
                    ['Task','age'],
                    ['10대',     comments.ages.a10],
                    ['20대',      comments.ages.a20],
                    ['30대',      comments.ages.a30],
                    ['40대',      comments.ages.a40],
                    ['50대',      comments.ages.other],
                ]);
            }else{
                var data = google.visualization.arrayToDataTable([
                    ['Task','age'],
                    ['10',     comments.ages.a10],
                    ['20',      comments.ages.a20],
                    ['30',      comments.ages.a30],
                    ['40',      comments.ages.a40],
                    ['50',      comments.ages.other],
                ]);
            }
            var options = {
                pieHole:0.4,
                legend: {
                    position: 'bottom',
                    textStyle: {
                        color: 'white',
                        fontSize: 13
                    }
                },
                backgroundColor: 'black',
                chartArea: {
                    width: '75%',
                    height: '75%'
                },
                tooltip: {
                    trigger: 'none'
                },
            };

            var chart = new google.visualization.PieChart(document.getElementById('age'));
            chart.draw(data, options);

            if(windowWidth > 1280){
                var chart = new google.visualization.PieChart(document.getElementById('age'));
                chart.draw(data, options);

                var data = google.visualization.arrayToDataTable([
                    ['Task','stars'],
                    ['1점',     comments.stars.s1],
                    ['2점',      comments.stars.s2],
                    ['3점',      comments.stars.s3],
                    ['4점',      comments.stars.s4],
                    ['5점',      comments.stars.s5],
                ]);
                var options = {
                    pieHole:0.4,
                    legend: {
                        position: 'bottom',
                        textStyle: {
                            color: 'white',
                            fontSize: 13
                        }
                    },
                    backgroundColor: 'black',
                    chartArea: {
                        width: '75%',
                        height: '75%'
                    },
                    tooltip: {
                        trigger: 'none'
                    }
                };
                var chart = new google.visualization.PieChart(document.getElementById('star'));
                chart.draw(data, options);
            }
        }
    }
})