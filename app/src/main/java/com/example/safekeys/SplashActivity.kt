package com.example.safekeys

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.safekeys.ui.theme.SafekeysTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock

class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SafekeysTheme {
                SplashScreen()
            }
        }

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }, 3000) // 3 seconds delay
    }
}

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier.fillMaxSize().background(
            brush = Brush.verticalGradient(
                colors = listOf(Color(0xFFB3E5FC), Color(0xFFE1F5FE)) // 柔和的浅蓝色渐变
            )
        ),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Icon(
                imageVector = Icons.Filled.Lock,
                contentDescription = "Lock Icon",
                tint = Color.Black,
                modifier = Modifier.size(64.dp)
            )
            Text(
                text = "SafeKeys",
                fontSize = 32.sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Center
            )
            Text(
                text = "安全加密，尽在掌握",
                fontSize = 16.sp,
                color = Color.Black,
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SplashScreenPreview() {
    SafekeysTheme {
        SplashScreen()
    }
}
