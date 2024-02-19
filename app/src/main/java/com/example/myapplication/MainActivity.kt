package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.myapplication.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    StopsList()
                }
            }
        }
    }
}

@Composable
fun StopsList() {
    var currentStopIndex by remember { mutableStateOf(0) }
    var distanceCovered by remember { mutableStateOf(0.0F) }
    var isMiles by remember { mutableStateOf(false) }

    val stops = listOf(
        "IIIT-D",
        "Kalkaji",
        "Greater Kailash",
        "Lajpat Nagar",
        "JLN Stadium",
        "India Gate",
        "Connaught Place",
        "Paharganj",
        "Chandani Chowk",
        "Red Fort",
        "RajGhat",
        "Pragati Maidan",
        "Sunder Nursery",
        "New Friends Col",
        "Humayun Tomb",
        "Khan Market",
        "Lotus Temple",
        "IIITD",
        )

    val distances = floatArrayOf(8.0F, 10.0F, 6.0F, 12.0F, 15.0F, 9.0F, 7.0F, 11.0F, 13.0F,10.0F, 6.0F, 12.0F, 15.0F, 9.0F, 7.0F, 11.0F, 13.0F) // Random distances for each stop
    var totD = 174.0F

    Column(
        verticalArrangement = Arrangement.SpaceAround,
        modifier = Modifier.padding(10.dp)
    ){
        LazyColumn(
            modifier =Modifier.fillMaxHeight(0.7f)
        ) {
            items(stops) {
                var currCol by remember { mutableStateOf(Color.Green) }
                var ind = stops.indexOf(it)
                if (ind > currentStopIndex) {
                    currCol = Color.Yellow
                } else {
                    currCol = Color.Green
                }
                StopBox(stopText = it, color = currCol)
                Spacer(modifier = Modifier.height(16.dp))
            }

        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            if (currentStopIndex < stops.size - 1) {
                LinearProgressBar(
                    distanceCovered = distanceCovered,
                    distanceLeft = totD - distanceCovered,
                    isMiles = isMiles
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    NextButton {
                        distanceCovered += distances[currentStopIndex]
                        currentStopIndex++
                    }
                    UnitButton {
                        isMiles = !isMiles
                    }
                }
            } else {
                // Destination reached
                Text("Destination Reached!")
            }
        }
    }
}

@Composable
fun StopBox(stopText: String, color: Color) {
    Box(
        modifier = Modifier
            .background(
                color = color,
                shape = CircleShape
            )
            .padding(8.dp)
            .fillMaxWidth(0.75f)
            .height(32.dp)
    ) {
        Text(
            text = stopText,
            color = Color.Black,
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp)
        )
    }
}

@Composable
fun LinearProgressBar(distanceCovered: Float, distanceLeft: Float,isMiles: Boolean) {
    LinearProgressIndicator(
        progress = distanceCovered / (distanceCovered + distanceLeft),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    )
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        if(isMiles){Text("Distance Covered: ${distanceCovered * 0.61} Miles")
            Spacer(modifier = Modifier.fillMaxWidth(0.2f))
            Text("Distance Left: ${distanceLeft*0.61} Miles")}
            else{Text("Distance Covered: $distanceCovered km")
            Spacer(modifier = Modifier.fillMaxWidth(0.2f))
            Text("Distance Left: $distanceLeft km")}
    }
}

@Composable
fun NextButton(onClick: () -> Unit) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .width(200.dp)
                .padding(top = 16.dp),
        ) {
            Text(text = "Next")
        }
}

@Composable
fun UnitButton(onClick: () -> Unit){
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(130.dp)
            .padding(top = 16.dp),
    ) {
        Text(text = "Change Unit")
    }
}

@Preview(showBackground = true)
@Composable
fun StopsListPreview() {
    MyApplicationTheme {
        StopsList()
    }
}