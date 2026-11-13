package com.example.lab13_animaciones

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.animation.animateColorAsState

// Colores tipo "Arcade" (sin tildes)
val PurpuraNeon = Color(0xFF8B5CF6)
val AzulNeon = Color(0xFF3B82F6)
val RosaNeon = Color(0xFFEC4899)
val VerdeNeon = Color(0xFF10B981)

@Composable
fun EjercicioFinal(modifier: Modifier = Modifier) {
    var puntuacion by remember { mutableIntStateOf(0) }
    var saltando by remember { mutableStateOf(false) }
    var mostrarParticulas by remember { mutableStateOf(false) }
    var colorFondo by remember { mutableStateOf(Color(0xFF09090B)) } // Fondo animado

    // 1. Animación del salto (Posición)
    val alturaSalto by animateDpAsState(
        targetValue = if (saltando) 0.dp else 180.dp,
        animationSpec = spring(
            dampingRatio = Spring.DampingRatioHighBouncy,
            stiffness = Spring.StiffnessLow
        ),
        label = "salto"
    )

    // 2. Animación infinita de caída (Posición)
    val infiniteTransition = rememberInfiniteTransition(label = "obstaculo")
    val posicionYObstaculo by infiniteTransition.animateFloat(
        initialValue = -100f,
        targetValue = 600f,
        animationSpec = infiniteRepeatable(
            animation = tween(2500, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "caidaObstaculo"
    )

    // 3. Rotación del obstáculo (NUEVO - Rotación)
    val rotacionObstaculo by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "rotacion"
    )

    // 4. Color del obstáculo (Color)
    val colorObstaculo by infiniteTransition.animateColor(
        initialValue = Color(0xFFEF4444),
        targetValue = Color(0xFFF59E0B),
        animationSpec = infiniteRepeatable(
            animation = tween(300),
            repeatMode = RepeatMode.Reverse
        ),
        label = "colorObstaculo"
    )

    // 5. Resplandor del jugador (Tamaño)
    val brillo by infiniteTransition.animateFloat(
        initialValue = 0.8f,
        targetValue = 1.2f,
        animationSpec = infiniteRepeatable(
            animation = tween(800),
            repeatMode = RepeatMode.Reverse
        ),
        label = "brillo"
    )

    // 6. Animaciones para las partículas (Escala y Opacidad)
    val escalaParticula by animateFloatAsState(
        targetValue = if (mostrarParticulas) 1f else 0f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioHighBouncy),
        label = "escalaParticula"
    )
    val opacidadParticula by animateFloatAsState(
        targetValue = if (mostrarParticulas) 1f else 0f,
        animationSpec = tween(300),
        label = "opacidadParticula"
    )

    // 7. Sacudida del jugador (NUEVO - Shake)
    val sacudida by animateFloatAsState(
        targetValue = if (saltando) 10f else 0f,
        animationSpec = spring(dampingRatio = Spring.DampingRatioHighBouncy),
        label = "sacudida"
    )

    // 8. Animación del color del fondo (NUEVO - Color)
    val colorFondoAnimado by animateColorAsState(
        targetValue = colorFondo,
        animationSpec = tween(500),
        label = "fondoAnimado"
    )

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(colorFondoAnimado), // Fondo animado
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "PUNTOS: $puntuacion",
            color = VerdeNeon,
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Área de juego
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(450.dp)
                .background(Color(0xFF18181B), RoundedCornerShape(20.dp))
                .border(
                    border = BorderStroke(
                        width = 3.dp,
                        brush = Brush.linearGradient(listOf(PurpuraNeon, AzulNeon))
                    ),
                    shape = RoundedCornerShape(20.dp)
                )
                .clip(RoundedCornerShape(20.dp))
        ) {
            // Obstáculo (con rotación)
            Box(
                modifier = Modifier
                    .offset(x = 0.dp, y = posicionYObstaculo.dp)
                    .size(60.dp)
                    .align(Alignment.TopCenter)
                    .rotate(rotacionObstaculo) // Rotación añadida
                    .background(colorObstaculo, RoundedCornerShape(12.dp))
            )

            // Jugador (con resplandor y sacudida)
            Box(
                modifier = Modifier
                    .offset(x = sacudida.dp, y = alturaSalto) // Sacudida en X
                    .size(60.dp)
                    .align(Alignment.BottomCenter)
                    .background(AzulNeon, CircleShape)
                    .border(3.dp, Color.White.copy(alpha = brillo), CircleShape)
            )
        }

        // Espacio reservado para las partículas (evita que la pantalla se mueva)
        Box(
            modifier = Modifier
                .height(60.dp) // Altura fija para que no empuje nada
                .padding(top = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            if (escalaParticula > 0f) {
                Text(
                    text = "✨ ¡BIEN! ✨",
                    color = RosaNeon,
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.graphicsLayer {
                        scaleX = escalaParticula
                        scaleY = escalaParticula
                        alpha = opacidadParticula
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                if (!saltando) {
                    saltando = true
                    mostrarParticulas = true
                    puntuacion += 10
                    // Cambiamos el color del fondo al saltar
                    colorFondo = if (colorFondo == Color(0xFF09090B)) Color(0xFF1E3A8A) else Color(0xFF09090B)
                } else {
                    saltando = false
                    mostrarParticulas = false
                }
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = if (saltando) VerdeNeon else PurpuraNeon,
                contentColor = Color.White
            ),
            shape = RoundedCornerShape(16.dp),
            modifier = Modifier
                .height(60.dp)
                .width(220.dp)
        ) {
            Text(
                text = if (saltando) "¡SALTANDO!" else "SALTAR",
                fontSize = 20.sp,
                fontWeight = FontWeight.Black
            )
        }
    }
}