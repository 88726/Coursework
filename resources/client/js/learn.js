function pageLoad() {
    let random = Math.random() * 2
    fetch('/question/get/' + random, {method: 'get'}
    ).then(response => response.json()
    ).then(questions => {

        /// *** show the user's info at top separately************
        for (let question of questions) {
            document.getElementById("learnDiv").innerHTML = question;

        }


    })


}
