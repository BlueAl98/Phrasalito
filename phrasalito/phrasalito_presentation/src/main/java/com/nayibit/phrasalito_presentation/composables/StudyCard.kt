package com.nayibit.phrasalito_presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nayibit.phrasalito_presentation.screens.phraseScreen.PhraseUi

@Composable
fun LanguagePhraseCard(
    phrase: PhraseUi,
    modifier: Modifier = Modifier, 
    onEvent : () -> Unit = {}
) {
    var isTranslationVisible by remember { mutableStateOf(false) }

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            phrase.color,
                            phrase.color.copy(alpha = 0.7f),
                            phrase.color.copy(alpha = 0.4f)
                        )
                    )
                )
                .padding(24.dp),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Target Language Title
                Text(
                    text = phrase.targetLanguage,
                    style = MaterialTheme.typography.headlineLarge.copy(
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold
                    ),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 16.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Translation with toggle visibility
                Card(
                    modifier = Modifier
                        .fillMaxWidth(0.9f)
                        .padding(vertical = 8.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f))
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = if (isTranslationVisible) phrase.translation
                            else "•".repeat(phrase.translation.length.coerceAtLeast(5)),
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Medium
                            ),
                            color = Color.DarkGray,
                            modifier = Modifier.weight(1f)
                        )

                        IconButton(
                            onClick = { isTranslationVisible = !isTranslationVisible }
                        ) {
                            Icon(
                                imageVector = if (isTranslationVisible) Icons.Default.Visibility
                                else Icons.Default.VisibilityOff,
                                contentDescription = if (isTranslationVisible) "Hide translation"
                                else "Show translation",
                                tint = phrase.color
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Example section
                if (phrase.example.isNotEmpty()) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
                            .padding(vertical = 8.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.8f))
                    ) {
                        Column(
                            modifier = Modifier.padding(16.dp)
                        ) {
                            Row {
                                Text(
                                    text = "Example:",
                                    style = MaterialTheme.typography.titleSmall.copy(
                                        fontWeight = FontWeight.Bold
                                    ),
                                    color = phrase.color
                                )
                                Spacer(modifier = Modifier.weight(1f))

                                Icon(
                                    modifier = Modifier.clickable { onEvent() },
                                    imageVector = Icons.Default.PlayArrow,
                                    contentDescription = "Play",
                                    tint = phrase.color
                                )


                            }
                            Text(
                                text = phrase.example,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.DarkGray,
                                textAlign = TextAlign.Start
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun LanguagePhraseCardLandscape(
    phrase: PhraseUi,
    modifier: Modifier = Modifier,
    onEvent : () -> Unit = {}
) {
    var isTranslationVisible by remember { mutableStateOf(false) }

    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.horizontalGradient(
                        colors = listOf(
                            phrase.color,
                            phrase.color.copy(alpha = 0.7f),
                            phrase.color.copy(alpha = 0.4f)
                        )
                    )
                )
                .padding(20.dp),
            contentAlignment = Alignment.Center
        ) {

            Row(
                modifier = Modifier.fillMaxSize(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Left side - Target Language
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(end = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = phrase.targetLanguage,
                        style = MaterialTheme.typography.headlineMedium.copy(
                            fontSize = 28.sp,
                            fontWeight = FontWeight.Bold
                        ),
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }

                // Right side - Translation and Example
                Column(
                    modifier = Modifier
                        .weight(2f)
                        .fillMaxHeight(),
                    verticalArrangement = Arrangement.Center
                ) {
                    // Translation Card
                    Card(
                        modifier = Modifier.fillMaxWidth(0.95f),
                        shape = RoundedCornerShape(12.dp),
                        colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.9f))
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(12.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = if (isTranslationVisible) phrase.translation
                                else "•".repeat(phrase.translation.length.coerceAtLeast(5)),
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    fontSize = 16.sp
                                ),
                                color = Color.DarkGray,
                                modifier = Modifier.weight(1f)
                            )

                            IconButton(
                                onClick = { isTranslationVisible = !isTranslationVisible }
                            ) {
                                Icon(
                                    imageVector = if (isTranslationVisible) Icons.Default.Visibility
                                    else Icons.Default.VisibilityOff,
                                    contentDescription = if (isTranslationVisible) "Hide translation"
                                    else "Show translation",
                                    tint = phrase.color
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // Example Card
                    if (phrase.example.isNotEmpty()) {
                        Card(
                            modifier = Modifier.fillMaxWidth(0.95f),
                            shape = RoundedCornerShape(12.dp),
                            colors = CardDefaults.cardColors(containerColor = Color.White.copy(alpha = 0.8f))
                        ) {
                            Column(
                                modifier = Modifier.fillMaxWidth().padding(12.dp)
                            ) {
                                Row {
                                    Text(
                                        text = "Example:",
                                        style = MaterialTheme.typography.titleSmall.copy(
                                            fontWeight = FontWeight.Bold
                                        ),
                                        color = phrase.color
                                    )
                                    Spacer(modifier = Modifier.weight(1f))

                                       Icon(
                                           modifier = Modifier.clickable { onEvent() },
                                           imageVector = Icons.Default.PlayArrow,
                                           contentDescription = "Play",
                                           tint = phrase.color
                                       )

                                }


                                Text(
                                    text = phrase.example,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color.DarkGray
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun AdaptiveLanguageCard(
    modifier: Modifier = Modifier,
    phrase: PhraseUi,
    isLandscape: Boolean = false,
    onEvent : () -> Unit = {}
) {
    if (isLandscape) {
        LanguagePhraseCardLandscape(phrase = phrase, modifier = modifier, onEvent = onEvent)
    } else {
        LanguagePhraseCard(phrase = phrase, modifier = modifier, onEvent = onEvent)
    }
}

