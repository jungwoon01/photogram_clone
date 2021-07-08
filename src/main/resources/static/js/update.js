// (1) 회원정보 수정
function update(userId, event) {

    let data = $("#profileUpdate").serialize();

    event.preventDefault(); // 폼태그 액션을 막기

    console.log(data);

    $.ajax({
        type:"put",
        url:'/api/user/'+userId,
        data: data,
        contentType:"application/x-www-form-urlencoded; charset=utf-8",
        dataType:"json"
    }).done(res=>{
        console.log("update 성공", res)
        location.href = `/user/${userId}`;
    }).fail(error=>{
        alert(JSON.stringify(error.responseJSON.data))
    });
}