package com.example.lab13_animaciones

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Ejercicio3(modifier: Modifier = Modifier) {
    var movido by remember { mutableStateOf(false) }

    val tamanio by animateDpAsState(
        targetValue = if (movido) 200.dp else 100.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "animacionTamanio"
    )

    val posicionX by animateDpAsState(
        targetValue = if (movido) 80.dp else 0.dp, // <-- CAMBIA 150.dp A 80.dp
        animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy),
        label = "animacionX"
    )
    val posicionY by animateDpAsState(
        targetValue = if (movido) 80.dp else 0.dp, // <-- CAMBIA 100.dp A 80.dp
        animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy),
        label = "animacionY"
    )

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Ejercicio 3: Tamaño y Posición",
            color = TextoBlanco,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Contenedor para delimitar el área de movimiento
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(450.dp)
                .background(Color(0xFF334155), RoundedCornerShape(16.dp))
                .border(2.dp, Color(0xFF64748B), RoundedCornerShape(16.dp))
                .padding(16.dp)
        ) {
            // Cuadro animado
            Box(
                modifier = Modifier
                    .offset(x = posicionX, y = posicionY)
                    .size(tamanio)
                    .background(AzulBrillante, RoundedCornerShape(12.dp))
                    .border(3.dp, Color(0xFF60A5FA), RoundedCornerShape(12.dp))
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = { movido = !movido },
            colors = ButtonDefaults.buttonColors(
                containerColor = AzulBrillante,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .height(50.dp)
                .width(200.dp)
        ) {
            Text(
                text = if (movido) "Volver al inicio" else "Mover y Crecer",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}