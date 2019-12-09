
function pageLoad() {

    let teachersHTML = `<table style="width:100%"> ` +
        `<tr>` +
        `<th>Id</th>` +
        `<th>Title</th>` +
        `<th>Surname</th>` +
        `<th>Password</th>` +
        `<th class="last">Options</th>` +
        `</tr>`;

    fetch('/teacher/list', {method: 'get'}
    ).then(response => response.json()
    ).then(teachers => {

        for (let teacher of teachers) {
            teachersHTML += `<tr>` +
                `<td>${teacher.teacherID}</td>` +
                `<td>${teacher.teacherTitle}</td>` +
                `<td>${teacher.teacherSurnameInitial}</td>` +
                `<td>${teacher.teacherPassword}</td>` +
                `<td class="last">` +
                `<button class='editButton' data-id='${teacher.teacherID}'>Edit</button>` +
                `<button class='deleteButton' data-id='${teacher.teacherID}'>Delete</button>` +
                `</td>` +
                `</tr>`;


        }
        teachersHTML += `</table>`;

        //alert(teachersHTML);
        document.getElementById("listDiv").innerHTML = teachersHTML;

    });



    let editButtons = document.getElementsByClassName("editButton");
    for (let button of editButtons) {
        button.addEventListener("click", editTeacher);
    }

    let deleteButtons = document.getElementsByClassName("deleteButton");
    for (let button of deleteButtons) {
        button.addEventListener("click", deleteTeacher);
    }


    document.getElementById("saveButton").addEventListener("click", saveEditTeacher);
    document.getElementById("cancelButton").addEventListener("click", cancelEditTeacher)
}


function editTeacher(event) {

    const id = event.target.getAttribute("data-id");

    if (id === null) {

        document.getElementById("editHeading").innerHTML = 'Add new Teacher:';

        document.getElementById("teacherId").value = '';
        document.getElementById("teacherTitle").value = '';
        document.getElementById("teacherSurname").value = '';
        document.getElementById("teacherPassword").value = '';

        document.getElementById("listDiv").style.display = 'none';
        document.getElementById("editDiv").style.display = 'block';

    } else {
        fetch('/teacher/get/' + id, {method: 'get'}
        ).then(response => response.json()
        ).then(teacher => {

            if (teacher.hasOwnProperty('error')) {
                alert(teacher.error);
            } else {

                document.getElementById("editHeading").innerHTML = 'Editing ' + teacher.name + ':';

                document.getElementById("teacherId").value = teacher.Id;
                document.getElementById("teacherTitle").value = teacher.Title;
                document.getElementById("teacherSurname").value = teacher.Surname;
                document.getElementById("teacherPassword").value = teacher.Password;

                document.getElementById("listDiv").style.display = 'none';
                document.getElementById("editDiv").style.display = 'block';

            }
        });
    }
}
function saveEditTeacher(event) {

    event.preventDefault();

    if (document.getElementById("teacherID").value.trim() === '') {
        alert("Please provide a teacher id.");
        return;
    }

    if (document.getElementById("teacherTitle").value.trim() === '') {
        alert("Please provide a teacher title.");
        return;
    }

    if (document.getElementById("teacherSurname").value.trim() === '') {
        alert("Please provide a teacher surname.");
        return;
    }

    if (document.getElementById("teacherPassword").value.trim() === '') {
        alert("Please provide a teacher password.");
        return;
    }
    const id = document.getElementById("teacherId").value;
    const form = document.getElementById("teacherForm");
    const formData = new FormData(form);

    let apiPath = '';
    if (id === '') {
        apiPath = '/teacher/add';
    } else {
        apiPath = '/teacher/update';
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
function cancelEditTeacher(event) {

    event.preventDefault();

    document.getElementById("listDiv").style.display = 'block';
    document.getElementById("editDiv").style.display = 'none';

}
function deleteTeacher(event) {

    const ok = confirm("Are you sure?");

    if (ok === true) {

        let id = event.target.getAttribute("data-id");
        let formData = new FormData();
        formData.append("id", id);

        fetch('/teacher/delete', {method: 'post', body: formData}
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

/*
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>revision site</title>
    <script src="/client/js/index.js"></script>
</head>
<body onload="pageLoad()">

<div id="content">

    <div id="pageHeader">
        <div class="floatRight"><button id="newButton" class="editButton">Add Teacher</button></div>
    </div>

    <div id="listDiv"></div>

    <div id="editDiv">
        <h2 id="editHeading"></h2>
        <form id="teacherForm">

            <div>
                <label for="teacherId" class="label">Id:</label>
                <input type="text" name="Id" id="teacherID">
            </div>
            <div>
                <label for="teacherTitle" class="label">Title:</label>
                <input type="text" name="Title" id="teacherTitle">
            </div>
            <div>
                <label for="teacherSurname" class="label">Surname:</label>
                <input type="text" name="Surname" id="teacherSurname">
            </div>
            <div>
                <label for="teacherPassword" class="label">Password:</label>
                <input type="text" name="Password" id="teacherPassword">
            </div>
            <div>
                <input type="submit" value="Save" id="saveButton">
                <input type="submit" value="Cancel" id="cancelButton">
            </div>

        </form>
    </div>
</div>
</body>


</html>
 */