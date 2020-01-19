function pageLoad() {
    let pointsHTML = `<table style="width:100%" id="leaderboardTable"> ` +
        `<tr>` +
        `<th>day points</th>` +
        `<th>date</th>` +
        `<th>student name</th>` +
        `<th class="last">student surname initial</th>` +
        `</tr>`;
//**********************************************************
    fetch('/points/list', {method: 'get'}
    ).then(response => response.json()
    ).then(points => {

        /// *** show the user's info at top separately************
        for (let point of points) {
            pointsHTML += `<tr>` +
                `<td align = "center">${point.daysPoints}</td>` +
                `<td align = "center">${point.date}</td>` +
                `<td align = "center">${point.studentName}</td>` +
                `<td align = "center">${point.studentSurnameInitial}</td>` +
                `<td align = "center" class="last">` +
                `</td>` +
                `</tr>`;


        }
        pointsHTML += `</table>`;


        document.getElementById("leaderboardDiv").innerHTML = pointsHTML;
         sortTable()



    })


}

function sortTable() {
    var table = document.getElementById("leaderboardTable");

    var switched = true;
    while (switched) {
        var rows = table.rows;
        switched = false;

        // if the table has a header, i should = 1 and not 0
        for (var i = 1; i < rows.length - 1; i++) {

            var x = rows[i].getElementsByTagName("td")[0];
            var y = rows[i + 1].getElementsByTagName("td")[0];

            if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
                rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
                switched = true;
            }
        }
    }

}