package com.nayibit.phrasalito_presentation.screens.startScreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nayibit.phrasalito_presentation.composables.AnimatedIllustration
import com.nayibit.phrasalito_presentation.composables.DotsIndicator
import com.nayibit.phrasalito_presentation.composables.OnboardingColors
import kotlinx.coroutines.flow.Flow

@Composable
fun StartScreen(
    modifier: Modifier = Modifier,
    state: StartStateUi,
    eventFlow: Flow<StartUiEvent>,
    onEvent: (StartUiEvent) -> Unit,
    navigation: () -> Unit
    ) {

    val context = LocalContext.current


    LaunchedEffect(Unit) {
        eventFlow.collect { event ->
            when (event) {
                is StartUiEvent.Navigate -> {
                    navigation()
                }
                is StartUiEvent.ShowToast -> {
                    Toast.makeText(context, event.message, Toast.LENGTH_SHORT).show()
                }

                else -> {}
            }
        }
    }

    OnboardingScreen()


}

@Composable
fun OnboardingScreen(
    title: String = "Welcome to Your App",
    description: String = "Discover amazing features and\nstreamline your daily tasks",
    currentPage: Int = 0,
    totalPages: Int = 3,
    onNextClick: () -> Unit = {},
    onSkipClick: () -> Unit = {},
    colors: OnboardingColors = OnboardingColors()
) {

    var currentPageTest by remember { mutableIntStateOf(currentPage) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onPrimary)
            .padding(horizontal = 30.dp, vertical = 40.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Column (Modifier.weight(0.75f),
                verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally
                ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    AnimatedIllustration(colors = colors)
                }



                Text(
                    text = title,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    color = colors.textPrimary,
                    textAlign = TextAlign.Center,
                    lineHeight = 36.sp
                )

                Text(
                    text = description,
                    fontSize = 16.sp,
                    color = colors.textSecondary,
                    textAlign = TextAlign.Center,
                    lineHeight = 24.sp
                )
            }


            // Buttons
            Column(
                modifier = Modifier.fillMaxWidth().weight(0.25f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // Dots Indicator
                DotsIndicator(
                    totalDots = totalPages,
                    currentDot = currentPageTest,
                    colors = colors
                )

                Spacer(modifier = Modifier.height(30.dp))

                // Primary Button
                Button(
                    onClick = { currentPageTest++},
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(14.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    contentPadding = PaddingValues(0.dp),
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 4.dp,
                        pressedElevation = 8.dp
                    )
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        colors.primaryGradientStart,
                                        colors.primaryGradientEnd
                                    )
                                )
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Next",
                            fontSize = 16.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = Color.White
                        )
                    }
                }

                // Secondary Button
            /*    OutlinedButton(
                    onClick = onSkipClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp),
                    shape = RoundedCornerShape(14.dp),
                    border = ButtonDefaults.outlinedButtonBorder.copy(
                        width = 2.dp,
                        brush = Brush.linearGradient(
                            colors = listOf(
                                colors.buttonBorder,
                                colors.buttonBorder
                            )
                        )
                    ),
                    colors = ButtonDefaults.outlinedButtonColors(
                        contentColor = colors.primaryGradientStart
                    )
                ) {
                    Text(
                        text = "Skip",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }*/
            }

            Spacer(modifier = Modifier.height(20.dp))
        }
    }
}


/*
@Composable
fun CheckPermisions(
    context: Context,
    state: StartStateUi = StartStateUi(),
    hasPermission: (Boolean) -> Unit
){
    // States to track permission status
   // var hasPermission by remember { mutableStateOf(false) }
    var showSettingsDialog by remember { mutableStateOf(false) }


    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            hasPermission(true)
        } else {
            // Check if we should show rationale or if permission is permanently denied
            val activity = context as androidx.activity.ComponentActivity
            val shouldShowRationale = ActivityCompat.shouldShowRequestPermissionRationale(
                activity,
                Manifest.permission.POST_NOTIFICATIONS
            )
            if (!shouldShowRationale) {
                showSettingsDialog = true
            }
        }
    }

    if (showSettingsDialog) {
        AlertDialog(
            onDismissRequest = { showSettingsDialog = false },
            title = { Text("Permission Required") },
            text = {
                Text("Notification permission is required. Please enable it in Settings.")
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        // Open app settings
                        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                            data = Uri.fromParts("package", context.packageName, null)
                        }
                        context.startActivity(intent)
                        showSettingsDialog = false
                    }
                ) {
                    Text("Open Settings")
                }
            },
            dismissButton = {
                TextButton(onClick = { showSettingsDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    if (state.hasPermission) {
        // Show this when permission is granted
        Text(
            text = "✅ Notification permission granted!",
            style = MaterialTheme.typography.headlineSmall,
            color = MaterialTheme.colorScheme.primary
        )
    } else {
        // Show this when permission is not granted
        Text(
            text = "We need notification permission",
            style = MaterialTheme.typography.headlineSmall
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Step 2: Button to trigger permission request
        Button(
            onClick = {
               if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    permissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                }else{
                    hasPermission(true)
               }
            }
        ) {
            Text("Request Permission")
        }
    }


}

 */