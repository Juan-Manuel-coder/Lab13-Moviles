package com.example.lab13_animaciones

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.graphicsLayer
import com.example.lab13_animaciones.ui.theme.Lab13_animacionesTheme

// Paleta de colores personalizada
val FondoOscuro = Color(0xFF0F172A) // Azul muy oscuro (casi negro)
val TextoBlanco = Color(0xFFF8FAFC)
val AzulBrillante = Color(0xFF3B82F6)
val MoradoBrillante = Color(0xFF8B5CF6)
val BotonFondo = Color(0xFF1E293B)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Lab13_animacionesTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = FondoOscuro
                ) { innerPadding ->
                    Ejercicio4(modifier = Modifier.padding(innerPadding))
                }
            }
        }
    }
}

@Composable
fun Ejercicio1(modifier: Modifier = Modifier) {
    var visible by remember { mutableStateOf(true) }

    // Animación para que el cuadro respire suavemente cuando está visible
    val infiniteTransition = rememberInfiniteTransition(label = "pulso")
    val escala by infiniteTransition.animateFloat(
        initialValue = 0.95f,
        targetValue = 1.05f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "escala"
    )

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Laboratorio 13 - Animaciones",
            color = TextoBlanco,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 48.dp)
        )

        Button(
            onClick = { visible = !visible },
            colors = ButtonDefaults.buttonColors(
                containerColor = MoradoBrillante, // <-- Cambia el color de fondo del botón
                contentColor = Color.White // El texto será blanco para que resalte
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .height(50.dp)
                .width(200.dp)
        ) {
            Text(
                text = if (visible) "Ocultar Cuadro" else "Mostrar Cuadro",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Cuadro con degradado y animación de entrada/salida
        AnimatedVisibility(
            visible = visible,
            enter = fadeIn(animationSpec = tween(500)) +
                    slideInVertically(
                        initialOffsetY = { it * 2 },
                        animationSpec = spring(dampingRatio = Spring.DampingRatioMediumBouncy)
                    ) +
                    scaleIn(initialScale = 0.5f),
            exit = fadeOut(animationSpec = tween(300)) +
                    slideOutVertically(targetOffsetY = { it * 2 }) +
                    scaleOut(targetScale = 0.5f)
        ) {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .graphicsLayer {
                        scaleX = escala
                        scaleY = escala
                    }
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(AzulBrillante, MoradoBrillante)
                        )
                    )
            ) {
                Text(
                    text = "¡Animado!",
                    color = TextoBlanco,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}