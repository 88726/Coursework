function pageLoad() {

    let storeHTML = `<table id= "storetable" style="width:100%"> ` +
        `<tr>` +
        `<th>Price</th>` +
        `<th class="last">Image</th>` +
        `</tr>`;

    fetch('/background/list', {method: 'get'}
    ).then(response => response.json()
    ).then(backgrounds => {

        for (let background of backgrounds) {
            storeHTML += `<tr>` +
              //  `<td align = "center">${background.backgroundID}</td>` +
                `<td align = "center">${background.backgroundPrice}</td>` +
                `<td align = "center">${background.backgroundImage}</td>` +
                `<td align = "center" class="last">` + `</td>` +
                `<td align = "center" class="last">` +
                `<button class='buyButton' data-id='${background.backgroundID}'>Purchase</button>` +
                `</td>` +
                `</tr>`;


        }
        storeHTML += `</table>`;


        document.getElementById("storeDiv").innerHTML = storeHTML;


    let buyButtons = document.getElementsByClassName("buyButton");
    for (let button of buyButtons) {

        button.addEventListener("click", buyBackground);

    }
    })

}


//**********************************************
function buyBackground(event){
    const backgroundID = event.target.getAttribute("data-id");
   // alert(backgroundID)
    let token = document.cookie.substring(document.cookie.indexOf(';') + 8)
    let studentPoints = 0
    let backgroundPrice = 0
    let studentID
    fetch('/student/list', {method: 'get'}
    ).then(response => response.json()
    ).then(students => {
        for (let student of students) {
            if (student.token == token){
                studentID = student.studentID
                 studentPoints = student.totalPoints

            }
        }

       alert(studentPoints)



        fetch('/background/list', {method: 'get'}
        ).then(response => response.json()
        ).then(backgrounds => {

            for (let background of backgrounds) {
                if (background.backgroundID == backgroundID){
                     backgroundPrice = background.backgroundPrice
                }
            }
            alert(backgroundPrice)
        let totalPoints = studentPoints - backgroundPrice
            alert(totalPoints)

          /*  let formData = new FormData();
            formData.append("studentID", studentID);
            formData.append("totalPoints", totalPoints);

            fetch('/student/updatePoints', {method: 'post', body: formData }
            ).then(response => response.json()
            ).then(responseData => {
                if (responseData.hasOwnProperty('error')) {
                    alert(responseData.error);
                } else {
                           alert("done")

                }
            })*/

            let formData = new FormData();
            formData.append("studentID", studentID);
            formData.append("backgroundID", backgroundID);


            fetch('/student/updateBackground', {method: 'post', body: formData }
        ).then(response => response.json()
        ).then(responseData => {

            if (responseData.hasOwnProperty('error')) {
                alert(responseData.error);
            } else {
        //        alert("done")

            }
        });
        })

})}