function pageLoad() {

    if(window.location.search === '?logOut') {
        document.getElementById('content').innerHTML = '<h1>Logging out, please wait...</h1>';
        logout();
    } else {
        document.getElementById("loginButton").addEventListener("click", login);
    }

}

function login(event) {

    event.preventDefault();

    const form = document.getElementById("loginForm");
    const formData = new FormData(form);

    if (student.checked == true) {
        fetch("/student/login", {method: 'post', body: formData}
        ).then(response => response.json()
        ).then(responseData => {

            if (responseData.hasOwnProperty('error')) {
                alert(responseData.error);
            } else {
                Cookies.set("studentName", responseData.studentName);
                Cookies.set("token", responseData.token);

                window.location.href = '/client/index.html';
            }
        });
    }
    else if (teacher.checked == true) {
        fetch("/teacher/login", {method: 'post', body: formData}
        ).then(response => response.json()
        ).then(responseData => {

            if (responseData.hasOwnProperty('error')) {
                alert(responseData.error);
            } else {
                //TEST THIS BIT!!*********************************
                Cookies.set("teacherSurname", responseData.studentName);
                Cookies.set("token", responseData.token);

                window.location.href = '/client/index.html';
            }
        });
    }
}





