$(function(){
	initMap();
});
	
function initMap(){
	
	var areaArr = new Array(); // 지역을 담는 배열
	// DB에서 값을 받아와서 areaArr에 넣어줘야합니다.
	areaArr.push(
//				이름				위도					경도
		{loc : '내집' , lat : '37.5666805' , lng : '126.9784147'},
		{loc : '내집' , lat : '37.5766805' , lng : '126.9884147'},
		{loc : '내집' , lat : '37.5776805' , lng : '126.9894147'}
	);
		
		let markers = new Array(); // 마커 정보를 담는 배열
		let infoWindows = new Array(); // 정보창을 담는 배열
		
		// 지도 기본 포커스
	var map = new naver.maps.Map('mainboard_map', {
		center: new naver.maps.LatLng(37.5666805, 126.9784147), //초기 지도 값
		zoom: 17
	})
		    
		
	// 마커넣기 
	for(var i = 0 ; i < areaArr.length ; i++){
	
		var marker = new naver.maps.Marker({
			map: map,
			title: areaArr[i].loc, // 이름
			position: new naver.maps.LatLng(areaArr[i].lat, areaArr[i].lng) // 위도와 경도
		});
		
		//마커의 정보창
		
		var contentString = [
			// 띄울 정보와 창 HTML을 작성합니다.
			'<div class="iw_inner">',
			'   <h3>서울특별시청</h3>',
			'   <p>서울특별시 중구 태평로1가 31 | 서울특별시 중구 세종대로 110 서울특별시청<br />',
			'       <img src="" /><br />',
			'       02-120 | 공공,사회기관 &gt; 특별,광역시청<br />',
			'       <a href="http://www.seoul.go.kr" target="_blank">www.seoul.go.kr/</a>',
			'   </p>',
			'</div>'
			].join('');
			
		//정보창의 css
			var infowindow = new naver.maps.InfoWindow({
			    content: contentString,
			    maxWidth: 140,
			    backgroundColor: "#eee",
			    borderColor: "#2db400",
			    borderWidth: 5,
			    anchorSize: new naver.maps.Size(30, 30),
			    anchorSkew: true,
			    anchorColor: "#eee",
			    pixelOffset: new naver.maps.Point(20, -20)
			});
		
			markers.push(marker); // 생성한 마커를 생성했던 배열에 넣는다.
			infoWindows.push(infowindow); // 생성한 정보창 css를 생성했던 배열에 넣는다.
		}
	function getClickHandler(seq) {
		
        return function(e) {  // 마커를 클릭하는 부분
            var marker = markers[seq], // 클릭한 마커의 시퀀스로 찾는다.
                infoWindow = infoWindows[seq]; // 클릭한 마커의 시퀀스로 찾는다

            if (infoWindow.getMap()) {
                infoWindow.close();
            } else {
                infoWindow.open(map, marker); // 표출
            }
		}
	}

	for (var i=0, ii=markers.length; i<ii; i++) {
		console.log(markers[i] , getClickHandler(i));
    	naver.maps.Event.addListener(markers[i], 'click', getClickHandler(i)); // 클릭한 마커 핸들러
	}
}