


function pageLoad() {
    document.getElementById("loggedInDetails").innerHTML = ""
    checkLogin()

}

//***************************************************************** the cookies. thing seems to break it?
function checkLogin(){

    let username = document.cookie
  alert(username)

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

        let userType = document.cookie.substring(0, document.cookie.indexOf('=') -8)
       alert(userType)

       if (userType == "stu"){
           let teacherScreen = document.getElementById('loggedInTeacher')
           teacherScreen.style.visibility = 'hidden';
       } else if (userType == "tea"){
           let studentScreen = document.getElementById("loggedInStudent")
           studentScreen.style.visibility = 'hidden';
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

                document.getElementById("loggedInDetails").innerHTML = ""
                window.location.href = '/client/index.html';
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

                document.getElementById("loggedInDetails").innerHTML = ""

                window.location.href = '/client/index.html';
           }
        })

    }
}