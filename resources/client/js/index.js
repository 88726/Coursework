


function pageLoad() {
    document.getElementById("loggedInDetails").innerHTML = ""
    checkLogin()

}

//***************************************************************** the cookies. thing seems to break it?
function checkLogin(){

    let username = document.cookie
   alert(document.cookie)

    let logInHTML = '';

    if (username == "") {
        alert("uh")
        let teacherScreen = document.getElementById('loggedInTeacher')
        teacherScreen.style.visibility = 'hidden';

        let studentScreen = document.getElementById("loggedInStudent")

        studentScreen.style.visibility = 'hidden';


   logInHTML = "Please log in"
//*********************************************** change for teacher and student views*****
  //  } else if {

    } else {

        let token = document.cookie.substring(document.cookie.indexOf(';') + 8)
        alert(token)
     alert("logged in")

        try{
        fetch('/student/get' + token, {method: 'get'}
        ).then(response => response.json()
        ).then(student => {
            alert("student")
        })
        }catch(err){
            fetch('/teacher/get' + token, {method: 'get'}
            ).then(response => response.json()
            ).then(teacher => {
                alert("teacher")
            })
            }
    logInHTML = "Logged in as " + username + ". <a href='/client/login.html?logout'>Log out</a>";

}

document.getElementById("loggedInDetails").innerHTML = logInHTML;

//}
//******************************************************

    }

function logout() {

    try {
        fetch("/student/logout", {method: 'post'}
        ).then(response => response.json()
        ).then(responseData => {
            if (responseData.hasOwnProperty('error')) {

                alert(responseData.error);

            } else {
                alert("done")
                Cookies.remove("studentName");
                Cookies.remove("token");

                window.location.href = '/client/index.html';
                document.getElementById("loggedInDetails").innerHTML = ""
            }
        });
    } catch {
        fetch("/teacher/logout", {method: 'post'}
        ).then(response => response.json()
        ).then(responseData => {
            if (responseData.hasOwnProperty('error')) {

                alert(responseData.error);

            } else {
                alert("done")
                Cookies.remove("teacherSurname");
                Cookies.remove("token");

                window.location.href = '/client/index.html';
                document.getElementById("loggedInDetails").innerHTML = ""
            }
        })

    }
}