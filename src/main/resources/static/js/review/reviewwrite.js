
$(function () {
    $('#summernote').summernote({
        height: "800",
        focus: true,
        lang: 'ko-KR',
        spellCheck: false,
        backColor: 'white',
        toolbar: [
            // [groupName, [list of button]]
            ['fontname', ['fontname']],
            ['fontsize', ['fontsize']],
            ['style', ['bold', 'italic', 'underline','strikethrough', 'clear']],
            ['color', ['forecolor','color']],
            ['table', ['table']],
            ['para', ['ul', 'ol', 'paragraph']],
            ['height', ['height']],
            ['insert',['picture','link','video']],
            ['view', ['fullscreen', 'help']]
        ],
        fontNames: ['Arial', 'Arial Black', 'Comic Sans MS', 'Courier New','맑은 고딕','궁서','굴림체','굴림','돋움체','바탕체'],
        fontSizes: ['8','9','10','11','12','14','16','18','20','22','24','28','30','36','50','72'],
        callbacks: {
            onImageUpload: function (files) {
                for(var i = files.length -1 ; i >= 0; i--){
                    uploadSummernoteImageFile(files[i],this);
                }
            }
        }
    });
    $('.note-editable').css({
        "display": "flex",
        "flex-direction": "column"
    })
});

function uploadSummernoteImageFile(file, el) {
    data = new FormData();
    data.append("file", file);
    data.append("code", $('#code').val())
    $.ajax({
        data : data,
        type : "POST",
        url : "/review/write/img",
        contentType : false,
        enctype : 'multipart/form-data',
        processData : false,
        success : function(data) {
            $(el).summernote('editor.insertImage', data);
        }
    });
}

$('.cancle').on('click',function () {
    window.close();
})

function checkInput() {
    let textCount = $('.note-editable').text().length
    let title = $('.title').val()

    if(title === ""){
        alert("제목을 입력해주세요")
        return false;
    }else if(textCount === 0){
        alert("내용을 입력해주세요")
        return false;
    }else if(title.length > 17){
        alert("제목의 길이가 너무 깁니다.")
        return false;
    }else if(textCount.length> 1500){
        alert("내용의 길이가 너무 깁니다.")
        return false;
    }else{
        return true;
    }
}

$('.thumbnail').on('change',function () {
    let fileName = $('.thumbnail').val();
    $('.filename').val(fileName);
})