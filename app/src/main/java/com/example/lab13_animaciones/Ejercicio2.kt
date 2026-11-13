package com.example.lab13_animaciones

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
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
fun Ejercicio2(modifier: Modifier = Modifier) {
    var cambiarColor by remember { mutableStateOf(false) }

    val colorFondo by animateColorAsState(
        targetValue = if (cambiarColor) Color(0xFF10B981) else Color(0xFF3B82F6),
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioMediumBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "colorAnimado"
    )

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Ejercicio 2: Animación de Color",
            color = TextoBlanco,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 48.dp)
        )

        Box(
            modifier = Modifier
                .size(200.dp)
                .background(colorFondo, RoundedCornerShape(24.dp))
        ) {
            Text(
                text = if (cambiarColor) "Verde" else "Azul",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        Spacer(modifier = Modifier.height(40.dp))

        Button(
            onClick = { cambiarColor = !cambiarColor },
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
                text = "Cambiar Color",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}