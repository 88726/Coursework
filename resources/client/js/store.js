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
    })

}

let buyButtons = document.getElementsByClassName("buyButton");
for (let buyButton of buyButtons) {
    alert("yup")
    buyButton.addEventListener("click", buyBackground);

}

function buyBackground(event){
    const backgroundID = event.target.getAttribute("student.studentID");
    let token = document.cookie.substring(document.cookie.indexOf(';') + 8)
alert("im in")
    fetch('/student/updateBackground', {method: 'post', body: token, backgroundID }
    ).then(response => response.json()
    ).then(responseData => {

        if (responseData.hasOwnProperty('error')) {
            alert(responseData.error);
        } else {
            alert("done")
        }
    });
}