package com.example.lab13_animaciones

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.togetherWith
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
fun Ejercicio4(modifier: Modifier = Modifier) {
    // Estado para controlar qué pantalla mostrar: 0 = Cargando, 1 = Contenido, 2 = Error
    var estado by remember { mutableStateOf(0) }

    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Ejercicio 4: AnimatedContent",
            color = TextoBlanco,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        // Contenedor donde ocurre la transición
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(250.dp)
                .background(Color(0xFF334155), RoundedCornerShape(16.dp)),
            contentAlignment = Alignment.Center
        ) {
            AnimatedContent(
                targetState = estado,
                transitionSpec = {
                    // Transición: La nueva pantalla entra desde abajo y la anterior sale hacia arriba
                    (slideInVertically { height -> height } + fadeIn()) togetherWith
                            (slideOutVertically { height -> -height } + fadeOut())
                },
                label = "transicionEstados"
            ) { targetState ->
                when (targetState) {
                    0 -> PantallaEstado(
                        titulo = "Cargando...",
                        mensaje = "Espera un momento por favor.",
                        color = Color(0xFFF59E0B) // Naranja
                    )
                    1 -> PantallaEstado(
                        titulo = "¡Contenido Listo!",
                        mensaje = "Aquí está la información que pediste.",
                        color = Color(0xFF10B981) // Verde
                    )
                    else -> PantallaEstado(
                        titulo = "Error",
                        mensaje = "Algo salió mal. Intenta de nuevo.",
                        color = Color(0xFFEF4444) // Rojo
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Botón para avanzar al siguiente estado
        Button(
            onClick = {
                // Ciclo: 0 -> 1 -> 2 -> 0
                estado = if (estado == 2) 0 else estado + 1
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = AzulBrillante,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
                .height(50.dp)
                .width(220.dp)
        ) {
            Text(
                text = "Cambiar Estado",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun PantallaEstado(titulo: String, mensaje: String, color: Color) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = titulo,
            color = color,
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = mensaje,
            color = TextoBlanco,
            fontSize = 16.sp
        )
    }
}