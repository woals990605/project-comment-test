// 1. 이벤트 리스너
$("#btn-join").click(() => {
    join();
});


$("#btn-login").click(() => {
    login();
});

$("#btn-update").click(() => {
    update();
});

// 2. 기능

// 회원가입 함수
let join = async () => {

    // (1) username, password, email, address 찾아서 자바스크립트 오브젝트로 만들기
    let joinDto = {
        username: $("#username").val(),
        password: $("#password").val(),
        email: $("#email").val(),
    }

    // (2) 자바스크립트 오브젝트 -> JSON 변환 (통신의 표준이 JSON!!)
    // let userJson = JSON.stringify(userDto);

    // (3) fetch 요청
    let response = await fetch("/join", {
        method: 'POST',
        body: JSON.stringify(joinDto),
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        }
    });

    let responseParse = await response.json();

    // (4) 회원가입이 잘되면 알림창을 띄우고 로그인 페이지로 이동
    if (responseParse.code == 1) {
        alert("회원가입에 성공하였습니다.");
        location.href = "/loginForm";
    } else {
        alert("회원가입에 실패하였습니다.");
    }
}

// 로그인 함수
let login = async () => {

    // checkBox의 체크여부를 제이쿼리에서 확인하는 법
    let checked = $("#remember").is(':checked');

    // console.log(checked);

    let loginDto = {
        username: $("#username").val(),
        password: $("#password").val(),
        remember: checked ? "on" : "off"
    }

    console.log(loginDto);

    let response = await fetch("/login", {
        method: 'POST',
        body: JSON.stringify(loginDto),
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        }
    });

    let responseParse = await response.json();

    if (responseParse.code == 1) {
        alert("로그인에 성공하셨습니다.");
        location.href = "/";
    } else {
        alert("로그인에 실패하였습니다.");
    }
}

// 회원정보 수정 함수
async function update() {
    let updateDto = {
        password: $("#password").val(),
        email: $("#email").val(),

    }

    let id = $("#id").val();

    let response = await fetch(`/s/api/user/${id}`, {
        headers: {
            'Content-Type': 'application/json;charset=utf-8'
        },
        method: 'PUT',
        body: JSON.stringify(updateDto)
    });

    let responseParse = await response.json();

    // console.log(responseParse);

    if (responseParse.code == 1) {
        alert("수정이 완료되었습니다.");
        location.href = `/s/user/${id}`;
    } else {
        alert("수정에 실패했습니다.");
    }
}