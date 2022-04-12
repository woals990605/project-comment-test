// 1. 이벤트 리스너
$("#btn-logout").click(() => {
    logout();
});

// 2. 기능

// 로그아웃 함수
async function logout() {
    let response = await fetch("/logout");
    let responseParse = await response.json(); // json을 javascript 오브젝트로 파싱

    if (responseParse.code == 1) {
        alert("로그아웃 되었습니다.");
        location.href = "/";
    } else {
        alert("로그아웃에 실패하였습니다.");
    }
}