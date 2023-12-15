package uwr.dbozek.quizkt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uwr.dbozek.quizkt.ui.theme.QuizKtTheme
import java.util.Arrays
import java.util.Collections

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

data class Question(
    val questionText: String,
    val answers: List<String>,
    val correctAnswerIndex: Int
    )

@Composable
fun MyCard(text: String) {
    Card(
        modifier = Modifier
            .height(100.dp)
            .fillMaxWidth()

    ) {
        Text(text = text, modifier = Modifier.padding(16.dp), textAlign = TextAlign.Center, fontSize = 16.sp)
    }
}

@Composable
fun MyRadioButton(text: String){
    Row (modifier = Modifier
        .padding(vertical = 8.dp)
        .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
        RadioButton(selected = true, onClick = { }, modifier = Modifier.padding(end = 8.dp))
        Text(text = text, fontSize = 14.sp)
    }
}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MyApp(){

    var questionIndex by remember { mutableStateOf(0) }
    var selectedAnswerIndex by remember { mutableStateOf(-1) }
    var points by remember { mutableStateOf(0) }
    var showEndScreen by remember { mutableStateOf(false)}


    val allquestions: List<Question> = listOf(
        Question("What is the SI unit of force?", listOf("Newton (N)","Joule (J)","Watt (W)","Pascal (Pa)"),0),
        Question("What is the formula for acceleration?", listOf("v = u + at", "F = ma", "E = mc^2", "F = G(m1*m2/r^2)"), 0),
        Question("Which of the following is a vector quantity?", listOf("Speed", "Distance", "Velocity", "Mass"), 2),
        Question("What is the conservation of energy?", listOf("Energy cannot be created nor destroyed, only transformed", "Energy can be created from nothing", "Energy can be destroyed", "None of the above"), 0),
        Question("What is the Big O notation for linear search?", listOf("O(n)", "O(log n)", "O(n^2)", "O(1)"), 0),
        Question("Which data structure uses LIFO (Last In, First Out) principle?", listOf("Stack", "Queue", "Linked List", "Tree"), 0),
        Question("What is the formula for work done?", listOf("W = Fd cos(θ)", "W = mgh", "W = F/a", "W = QV"), 0),
        Question("What is the unit of electrical resistance?", listOf("Ohm", "Ampere", "Volt", "Watt"), 0),
        Question("What is the formula for Ohm's Law?", listOf("V = IR", "P = IV", "V = I^2R", "P = V^2/R"), 0),
        Question("Which sorting algorithm has the best average time complexity?", listOf("Bubble Sort","Merge Sort","Quick Sort","Selection Sort"), 1),
        Question("What does HTTP stand for in web development?", listOf("Hypertext Transfer Protocol","Hypertext Transmission Protocol","Hypertext Terminal Process","Hypertext Transfer Process"), 0),
        Question("What is the function of a firewall?", listOf("To enhance computer processing speed","To create network connections","To protect a network from unauthorized access","To debug programming code"), 2),
        Question("What does IDE stand for in software development?", listOf("Integrated Development Environment","Integrated Design Environment","Integrated Debugging Environment","Interchangeable Development Environment"), 0),
        Question("What is the purpose of inheritance in object-oriented programming?", listOf("To restrict access to certain class members","To allow a new class to inherit properties and behavior from an existing class","To create multiple instances of a class","To define the structure of a class"), 1)

    )

    var selectedQuestions: List<Question> = allquestions
    Collections.shuffle(selectedQuestions)
    selectedQuestions = selectedQuestions.subList(0,10)


    val currentQuestion = selectedQuestions[questionIndex]

    val onAnswerSelected: (Int) ->Unit = { answerIndex -> selectedAnswerIndex = answerIndex}
    val progress = ((questionIndex + 1) / selectedQuestions.size.toFloat()).coerceIn(0f,1f)



    if(showEndScreen){
        EndScreen(points = points) {
            showEndScreen = false
            points = 0
        }
    }else{
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(34.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            Text(text = "Question ${questionIndex+1} of 10",
                fontSize = 30.sp,
                color = Color.Black,
            )

            LinearProgressIndicator(
                progress = progress,
                modifier = Modifier.fillMaxWidth()
            )
            MyCard(text = currentQuestion.questionText)

            Card(
                modifier = Modifier.fillMaxWidth()

            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    currentQuestion.answers.forEachIndexed { index, answer ->

                        Card (
                            modifier = Modifier
                                .padding(12.dp)
                                .padding(vertical = 2.dp)
                                .fillMaxWidth(),
                            shape = RoundedCornerShape(8.dp),
                            colors = CardDefaults.cardColors(
                                containerColor = if (index == selectedAnswerIndex) Color(0xFFefd6ff) else Color(0xFFdbd6ff)
                            ),
                            onClick = {

                                onAnswerSelected(index)
                            }

                        ){
                            //MyRadioButton(text = answer)
                            Row (modifier = Modifier
                                .padding(vertical = 8.dp)
                                .fillMaxWidth(), verticalAlignment = Alignment.CenterVertically){
                                RadioButton(selected = if (index == selectedAnswerIndex){true}else{false}, onClick = { onAnswerSelected(index) }, modifier = Modifier.padding(end = 8.dp))
                                Text(text = answer, fontSize = 14.sp)
                            }


                        }

                    }
                }
            }
            Spacer(modifier = Modifier.weight(1f))
            Button(

                onClick = {
                    if(selectedAnswerIndex != -1) {
                        if(currentQuestion.correctAnswerIndex == selectedAnswerIndex){
                            points = points + 10
                        }

                        if(questionIndex < selectedQuestions.size - 1){
                            questionIndex++
                            selectedAnswerIndex = -1}
                        else{
                            showEndScreen = true
                            questionIndex = 0


                            selectedAnswerIndex = -1
                        }}
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 18.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text = "Next",
                    fontSize = 18.sp)
            }
        }
    }
}




@Composable
fun EndScreen(points: Int, onRestart: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ){
        Text(
            text = "End of quiz!",
            fontSize = 24.sp,
            color = Color.Black
        )
        Text(
            text = "Points: $points",
            fontSize = 18.sp,
            color = Color.Black
        )
        Button(
            onClick = { onRestart() }    ,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 18.dp),
            shape = MaterialTheme.shapes.medium) {
            Text(text = "Restart")
            
        }
    }
}



@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview() {
   //MyApp()

}