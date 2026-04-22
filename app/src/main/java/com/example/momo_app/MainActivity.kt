package com.example.momo_app

import androidx.compose.ui.tooling.preview.Preview
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MoMoAppTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    Scaffold(
                        topBar = { MoMoTopBar() }
                    ) { innerPadding ->
                        MoMoCalcScreen(
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun MoMoCalcScreen(modifier: Modifier = Modifier) {

    var amountInput by remember { mutableStateOf("") }

    val numericAmount = amountInput.toDoubleOrNull()
    val isError = amountInput.isNotEmpty() && numericAmount == null
    val fee = (numericAmount ?: 0.0) * 0.03
    val formattedFee = "UGX %,.0f".format(fee)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = "MoMo Fee Calculator",
            style = MaterialTheme.typography.headlineMedium,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(24.dp))

        AmountInput(
            amount = amountInput,
            onAmountChange = { amountInput = it },
            isError = isError
        )

        Spacer(modifier = Modifier.height(16.dp))

        Surface(
            shape = MaterialTheme.shapes.medium,
            color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.15f),
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Transaction Fee: $formattedFee",
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}


@Composable
fun AmountInput(
    amount: String,
    onAmountChange: (String) -> Unit,
    isError: Boolean
) {
    Column(modifier = Modifier.fillMaxWidth()) {

        TextField(
            value = amount,
            onValueChange = onAmountChange,
            isError = isError,
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Enter Amount") }
        )

        if (isError) {
            Text(
                text = "Please enter numbers only",
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MoMoTopBar() {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "MoMo App",
                style = MaterialTheme.typography.headlineMedium
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}



// Colors
val NavyBlue = Color(0xFF003087)
val BrandGold = Color(0xFFC9A227)
val White = Color(0xFFFFFFFF)
val LightGrey = Color(0xFFF4F6FB)
val DarkSurface = Color(0xFF1A1A2E)
val DarkBackground = Color(0xFF0D1B3E)
val ErrorRed = Color(0xFFC0392B)

// Light Theme
private val LightColors = lightColorScheme(
    primary = NavyBlue,
    onPrimary = White,
    secondary = BrandGold,
    background = LightGrey,
    surface = White,
    onSurface = DarkSurface,
    error = ErrorRed
)

// Dark Theme
private val DarkColors = darkColorScheme(
    primary = BrandGold,
    onPrimary = NavyBlue,
    background = DarkBackground,
    surface = DarkSurface,
    onSurface = White,
    error = ErrorRed
)

// Typography
val AppTypography = Typography(
    headlineMedium = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 26.sp
    ),
    bodyLarge = TextStyle(
        fontWeight = FontWeight.SemiBold,
        fontSize = 16.sp
    ),
    bodyMedium = TextStyle(
        fontSize = 14.sp
    ),
    bodySmall = TextStyle(
        fontSize = 12.sp
    )
)

// Shapes
val AppShapes = Shapes(
    medium = RoundedCornerShape(16.dp)
)

// Theme Wrapper
@Composable
fun MoMoAppTheme(content: @Composable () -> Unit) {
    val colors = if (isSystemInDarkTheme()) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,
        typography = AppTypography,
        shapes = AppShapes,
        content = content
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true)
@Composable
fun MoMoAppPreview() {
    MoMoAppTheme {
        Surface(modifier = Modifier.fillMaxSize()) {
            Scaffold(
                topBar = { MoMoTopBar() }
            ) { innerPadding ->
                MoMoCalcScreen(
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }
}