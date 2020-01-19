function pageLoad() {

    let rightAnswer = ""
    let listquestion = ""
    let answers = ["", "", "", ""]
    let numberOfQuestions = 0
    let i = 0
    fetch('/question/list' , {method: 'get'}
    ).then(response => response.json()
    ).then(questions => {

        for (let question of questions) {
            numberOfQuestions = numberOfQuestions + 1

        };
      let randomAnswer =0
        let randomQuestion = Math.floor(Math.random() * (numberOfQuestions))

        let count = 0

        for (let question of questions) {

            if (count == randomQuestion) {
                listquestion += question.question
                rightAnswer = question.answer
               answers[0] = rightAnswer
            }

            count++
        };

//******************* Maybe a search so theres no repeats?*****************
        count = 0
        while(count < 3) {
        for (let question of questions) {
                    randomAnswer = Math.floor(Math.random() * (numberOfQuestions))
           // alert(randomAnswer)
           // alert(count)
            if (count == randomAnswer && count <3) {

                           // alert(count)
                    answers[count + 1] = question.answer
                    count = count + 1
                }
            }

        }


        document.getElementById("questionDiv").innerHTML = listquestion
        document.getElementById("answerDiv").innerHTML =  answers


    })
}
