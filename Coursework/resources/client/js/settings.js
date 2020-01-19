
function pageLoad() {

    let studentsHTML = `<table style="width:100%"> ` +
        `<tr>` +
        `<th>Id</th>` +
        `<th>Name</th>` +
        `<th>Surname Initial</th>` +
        `<th>Password</th>` +
        `<th>Parent ID</th>` +
        `<th class="last">Options</th>` +
        `</tr>`;

    fetch('/student/list', {method: 'get'}
    ).then(response => response.json()
    ).then(students => {

        /// *** show the user's info at top separately************
        for (let student of students) {
            studentsHTML += `<tr>` +
                `<td align = "center"><input type="text" value = ${student.studentID}></td>` +
                `<td align = "center"><input type="text" value =${student.studentName}></td>` +
                `<td align = "center"><input type="text" value =${student.studentSurnameInitial}></td>` +
                `<td align = "center"><input type="text" value =${student.studentPassword}></td>` +
                `<td align = "center"><input type="text" value =${student.parentID}></td>` +
                `<td align = "center" class="last">` +
                `<button class='editButton' data-id='${student.studentID}'>Edit</button>` +
                `<button class='deleteButton' data-id='${student.studentID}'>Delete</button>` +
                `</td>` +
                `</tr>`;


        }
        studentsHTML += `</table>`;


        document.getElementById("listDiv").innerHTML = studentsHTML;



        let editButtons = document.getElementsByClassName("editButton");
        for (let button of editButtons) {
            button.addEventListener("click", editstudent);

        }

        let deleteButtons = document.getElementsByClassName("deleteButton");
        for (let button of deleteButtons) {
            button.addEventListener("click", deletestudent);
        }
    });








  //  document.getElementById("saveButton").addEventListener("click", saveEditstudent);
   // document.getElementById("cancelButton").addEventListener("click", cancelEditstudent)
}


function editstudent(event) {
alert("hi")
    const studentID = event.target.getAttribute("student.studentID");

    if (id === null) {
        alert("hi3")
        document.getElementById("editHeading").innerHTML = 'Add new student:';

        document.getElementById("studentID").value = '';
        document.getElementById("studentTitle").value = '';
        document.getElementById("studentSurnameInitial").value = '';
        document.getElementById("studentPassword").value = '';

        document.getElementById("listDiv").style.display = 'none';
        document.getElementById("editDiv").style.display = 'block';

    } else {
        fetch('/student/list/' + id, {method: 'get'}
        ).then(response => response.json()
        ).then(student => {
            alert("hi2")
            if (student.hasOwnProperty('error')) {
                alert(student.error);
            } else {

                document.getElementById("editHeading").innerHTML = 'Editing ' + student.name + ':';

                document.getElementById("studentID").value = student.id;
                document.getElementById("studentTitle").value = student.name;
                document.getElementById("studentSurname").value = student.studentSurnameInitial;
                document.getElementById("studentPassword").value = student.password;

                document.getElementById("listDiv").style.display = 'none';
                document.getElementById("editDiv").style.display = 'block';

            }
        });
    }
}
function saveEditstudent(event) {

    event.preventDefault();

    if (document.getElementById("studentID").value.trim() === '') {
        alert("Please provide a student id.");
        return;
    }

    if (document.getElementById("studentTitle").value.trim() === '') {
        alert("Please provide a student title.");
        return;
    }

    if (document.getElementById("studentSurname").value.trim() === '') {
        alert("Please provide a student surname.");
        return;
    }

    if (document.getElementById("studentPassword").value.trim() === '') {
        alert("Please provide a student password.");
        return;
    }
    const id = document.getElementById("studentId").value;
    const form = document.getElementById("studentForm");
    const formData = new FormData(form);

    let apiPath = '';
    if (id === '') {
        apiPath = '/student/add';
    } else {
        apiPath = '/student/update';
    }

    fetch(apiPath, {method: 'post', body: formData}
    ).then(response => response.json()
    ).then(responseData => {

        if (responseData.hasOwnProperty('error')) {
            alert(responseData.error);
        } else {
            document.getElementById("listDiv").style.display = 'block';
            document.getElementById("editDiv").style.display = 'none';
            pageLoad();
        }
    });
}
function cancelEditstudent(event) {

    event.preventDefault();

    document.getElementById("listDiv").style.display = 'block';
    document.getElementById("editDiv").style.display = 'none';

}
//*************************
function deletestudent(event) {

    const ok = confirm("Are you sure?");

    if (ok === true) {

         let studentID = event.target.getAttribute("data-id");
         alert(studentID)
        let formData = new FormData();
        formData.append("studentID", studentID);

        fetch('/student/delete', {method: 'post', body: formData}
        ).then(response => response.json()
        ).then(responseData => {

                if (responseData.hasOwnProperty('error')) {
                    alert(responseData.error);
                } else {
                    pageLoad();
                }
            }
        );
    }
}


/*set positions of buttons correctly
log in/out
edit accounts
 */
