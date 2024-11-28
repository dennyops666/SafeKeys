package com.example.safekeys

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.safekeys.ui.theme.SafekeysTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff

@OptIn(ExperimentalMaterial3Api::class)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SafekeysTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MainScreen(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    val passwordLength = remember { mutableStateOf(10f) }
    val includeUppercase = remember { mutableStateOf(true) }
    val includeLowercase = remember { mutableStateOf(true) }
    val includeNumbers = remember { mutableStateOf(true) }
    val includeSymbols = remember { mutableStateOf(true) }
    val avoidAmbiguous = remember { mutableStateOf(false) }
    val minNumbers = remember { mutableStateOf(1) }
    val minSymbols = remember { mutableStateOf(1) }
    val generatedPasswordState = remember { mutableStateOf("") }
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current
    var showToast by remember { mutableStateOf(false) }
    val passwordVisibility = remember { mutableStateOf(true) }

    LaunchedEffect(Unit) {
        generatedPasswordState.value = generatePassword(
            length = passwordLength.value.toInt(),
            includeUppercase = includeUppercase.value,
            includeLowercase = includeLowercase.value,
            includeNumbers = includeNumbers.value,
            includeSymbols = includeSymbols.value,
            avoidAmbiguous = avoidAmbiguous.value,
            minNumbers = minNumbers.value,
            minSymbols = minSymbols.value
        )
    }

    if (showToast) {
        LaunchedEffect(Unit) {
            Toast.makeText(context, "密码已复制", Toast.LENGTH_SHORT).show()
            showToast = false
        }
    }

    val passwordStrengthColor = when {
        passwordLength.value < 8 -> Color.Red
        passwordLength.value < 12 -> Color(0xFFFFA500)
        else -> Color.Green
    }

    val passwordStrengthText = when {
        passwordLength.value < 8 -> "危险"
        passwordLength.value < 12 -> "安全"
        else -> "非常安全"
    }

    Column(
        modifier = modifier
            .padding(16.dp)
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End
        ) {
            Text(
                "SafeKeys",
                style = MaterialTheme.typography.titleLarge.copy(
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                ),
                color = Color.Black
            )
        }
        Text("密码", style = MaterialTheme.typography.titleLarge, color = Color.Black)
        OutlinedTextField(
            value = generatedPasswordState.value,
            onValueChange = {},
            modifier = Modifier.fillMaxWidth(),
            textStyle = LocalTextStyle.current.copy(color = Color.Black, fontSize = 20.sp),
            visualTransformation = if (passwordVisibility.value) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                Row {
                    IconButton(onClick = { passwordVisibility.value = !passwordVisibility.value }) {
                        Icon(
                            imageVector = if (passwordVisibility.value) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                            contentDescription = if (passwordVisibility.value) "隐藏密码" else "显示密码",
                            tint = Color.Black
                        )
                    }
                    IconButton(onClick = {
                        clipboardManager.setText(AnnotatedString(generatedPasswordState.value))
                        showToast = true
                    }) {
                        Icon(Icons.Filled.ContentCopy, contentDescription = "复制密码", tint = Color.Black)
                    }
                    IconButton(onClick = {
                        generatedPasswordState.value = generatePassword(
                            length = passwordLength.value.toInt(),
                            includeUppercase = includeUppercase.value,
                            includeLowercase = includeLowercase.value,
                            includeNumbers = includeNumbers.value,
                            includeSymbols = includeSymbols.value,
                            avoidAmbiguous = avoidAmbiguous.value,
                            minNumbers = minNumbers.value,
                            minSymbols = minSymbols.value
                        )
                    }) {
                        Icon(Icons.Filled.Refresh, contentDescription = "刷新密码", tint = Color.Black)
                    }
                }
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = Color.DarkGray,
                unfocusedBorderColor = Color.LightGray,
                cursorColor = Color.Black
            )
        )
        Text("长度: ${passwordLength.value.toInt()}", style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp), color = Color.Black)
        Text("安全程度: $passwordStrengthText", style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp), color = passwordStrengthColor)
        Slider(
            value = passwordLength.value,
            onValueChange = {
                passwordLength.value = it
                generatedPasswordState.value = generatePassword(
                    length = passwordLength.value.toInt(),
                    includeUppercase = includeUppercase.value,
                    includeLowercase = includeLowercase.value,
                    includeNumbers = includeNumbers.value,
                    includeSymbols = includeSymbols.value,
                    avoidAmbiguous = avoidAmbiguous.value,
                    minNumbers = minNumbers.value,
                    minSymbols = minSymbols.value
                )
            },
            valueRange = 1f..20f,
            steps = 19,
            colors = SliderDefaults.colors(
                thumbColor = passwordStrengthColor,
                activeTrackColor = passwordStrengthColor
            )
        )
        SwitchListTile("A-Z", includeUppercase)
        SwitchListTile("a-z", includeLowercase)
        SwitchListTile("0-9", includeNumbers)
        SwitchListTile("!@#\$%^&*", includeSymbols)
        SwitchListTile("避免混淆字符", avoidAmbiguous)
        NumberAdjuster("数字最少个数", minNumbers)
        NumberAdjuster("符号最少个数", minSymbols)
    }
}

@Composable
fun SwitchListTile(label: String, state: MutableState<Boolean>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp), color = Color.Black)
        Switch(
            checked = state.value,
            onCheckedChange = { state.value = it },
            colors = SwitchDefaults.colors(
                checkedThumbColor = Color.Black,
                uncheckedThumbColor = Color.Gray
            )
        )
    }
}

@Composable
fun NumberAdjuster(label: String, state: MutableState<Int>) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(label, style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp), color = Color.Black)
        Row {
            IconButton(onClick = { if (state.value > 0) state.value-- }) {
                Icon(Icons.Filled.Remove, contentDescription = "减少", tint = Color.Black)
            }
            Text(state.value.toString(), style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp), color = Color.Black)
            IconButton(onClick = { state.value++ }) {
                Icon(Icons.Filled.Add, contentDescription = "增加", tint = Color.Black)
            }
        }
    }
}

fun generatePassword(
    length: Int,
    includeUppercase: Boolean,
    includeLowercase: Boolean,
    includeNumbers: Boolean,
    includeSymbols: Boolean,
    avoidAmbiguous: Boolean,
    minNumbers: Int,
    minSymbols: Int
): String {
    val uppercaseChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
    val lowercaseChars = "abcdefghijklmnopqrstuvwxyz"
    val numberChars = "0123456789"
    val symbolChars = "!@#\$%^&*()_+[]{}|;:,.<>?"
    val ambiguousChars = "Il1O0"

    val charset = buildString {
        if (includeUppercase) append(uppercaseChars)
        if (includeLowercase) append(lowercaseChars)
        if (includeNumbers && minNumbers > 0) append(numberChars)
        if (includeSymbols && minSymbols > 0) append(symbolChars)
    }.filterNot { avoidAmbiguous && it in ambiguousChars }

    if (charset.isEmpty()) return ""

    val password = mutableListOf<Char>()

    if (includeUppercase) password.add(uppercaseChars.random())
    if (includeLowercase) password.add(lowercaseChars.random())
    repeat(minNumbers) { password.add(numberChars.random()) }
    repeat(minSymbols) { password.add(symbolChars.random()) }

    while (password.size < length) {
        val nextChar = charset.random()
        if (password.lastOrNull() != nextChar) {
            password.add(nextChar)
        }
    }

    return password.shuffled().joinToString("")
}