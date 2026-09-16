package com.example.a0_301

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.a0_301.ui.theme.A0301Theme
import kotlin.random.Random

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            A0301Theme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    DecisionApp(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun DecisionApp(modifier: Modifier = Modifier) {
    var resultText by remember { mutableStateOf("Should I go???!!!!") }
    var lastWasYes by remember { mutableStateOf<Boolean?>(null) }
    var lastButton by remember { mutableStateOf<String?>(null) }
    var streakCount by remember { mutableStateOf(0) }

    val backgroundColor by animateColorAsState(
        targetValue = when (lastWasYes) {
            true -> Color(0xFFC8E6C9)
            false -> Color(0xFFFFCDD2)
            null -> Color.White
        },
        label = "backgroundColor"
    )

    fun decide(probability: Double): Boolean {
        return Random.nextDouble() < probability
    }

    fun handleClick(probability: Double, buttonId: String) {
        streakCount = if (lastButton == buttonId) streakCount + 1 else 1
        lastButton = buttonId

        val yes = decide(probability)
        lastWasYes = yes
        resultText = if (yes) "Yes! Go there!" else "No, skip it."
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp)
    ) {
        Text(text = "Student ID: 1859509 | CCID: rishan", fontSize = 14.sp)

        Spacer(modifier = Modifier.height(24.dp))

        Text(text = resultText, fontSize = 24.sp)

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { handleClick(0.5, "Yes") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Text("Yes", fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { handleClick(0.25, "Maybe") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Text("Maybe", fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { handleClick(0.10, "No") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 4.dp)
        ) {
            Text("No", fontSize = 20.sp)
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = if (lastButton == null) "No clicks yet" else "$lastButton clicked: $streakCount times",
            fontSize = 16.sp,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center
        )
    }
}

@Preview(showBackground = true)
@Composable
fun DecisionAppPreview() {
    A0301Theme {
        DecisionApp()
    }
}