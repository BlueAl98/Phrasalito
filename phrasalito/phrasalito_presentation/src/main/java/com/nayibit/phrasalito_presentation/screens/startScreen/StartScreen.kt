package com.nayibit.phrasalito_presentation.screens.startScreen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nayibit.phrasalito_presentation.R
import com.nayibit.phrasalito_presentation.composables.AnimatedIllustration
import com.nayibit.phrasalito_presentation.composables.DotsIndicator
import com.nayibit.phrasalito_presentation.composables.OnboardingColors
import com.nayibit.phrasalito_presentation.composables.isLandscape
import com.nayibit.phrasalito_presentation.composables.rememberNotificationPermissionHandler
import com.nayibit.phrasalito_presentation.screens.exerciseScreen.LandscapeContent
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

    OnboardingScreen(
        state = state,
        onEvent = onEvent
    )
}


@Composable
fun OnboardingScreen(
    modifier: Modifier = Modifier,
    state: StartStateUi,
    onEvent: (StartUiEvent) -> Unit,
    colors: OnboardingColors = OnboardingColors()
) {

    val pagerState = rememberPagerState(
        initialPage = state.currentPage,
        pageCount = { state.totalpages }
    )

    LaunchedEffect(state.currentPage) {
        pagerState.animateScrollToPage(state.currentPage)
    }



    Box(modifier = modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.onPrimary)
        .padding(horizontal = 15.dp, vertical = 15.dp)
        ){

      if (isLandscape())
         ContentLandscape(
             state = state,
             onEvent = onEvent,
             colors = colors,
             pagerState = pagerState)
        else
          ContentPortrait(
              state = state,
              onEvent = onEvent,
              colors = colors,
              pagerState = pagerState)
    }

}


@Composable
fun ContentLandscape(
    modifier: Modifier = Modifier,
    state: StartStateUi,
    onEvent: (StartUiEvent) -> Unit,
    colors: OnboardingColors,
    pagerState: PagerState
    ) {

    Row (modifier.fillMaxSize()){


        HorizontalPager(
            modifier = Modifier.weight(0.50f),
            state = pagerState,
            userScrollEnabled = false
        ) { page ->

            Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()){

            when (page) {
                    0 -> AnimatedIllustration(colors = colors)
                    1 -> AnimatedIllustration(colors = colors, mainImageVector = Icons.Filled.NotificationsActive)
                }
            }
        }

        Column (modifier
            .weight(0.50f)
            .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
            ) {

            when(pagerState.currentPage){
                0 -> {
                    Text(
                        text = stringResource(R.string.welcome_title),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = colors.textPrimary,
                        textAlign = TextAlign.Center,
                        lineHeight = 36.sp
                    )


                    Text(
                        text = stringResource(R.string.description_welcome),
                        fontSize = 16.sp,
                        color = colors.textSecondary,
                        textAlign = TextAlign.Center,
                        lineHeight = 24.sp
                    )

                }
                1 -> {
                    Text(
                        text = stringResource(R.string.notification_title),
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = colors.textPrimary,
                        textAlign = TextAlign.Center,
                        lineHeight = 36.sp
                    )
                    Text(
                        text = stringResource(R.string.notification_description),
                        fontSize = 16.sp,
                        color = colors.textSecondary,
                        textAlign = TextAlign.Center,
                        lineHeight = 24.sp
                    )
                }
            }


            DotsIndicator(
                totalDots = state.totalpages,
                currentDot = state.currentPage,
                colors = colors
            )


            Button(
                onClick = {
                    if (state.totalpages > state.currentPage + 1) {
                        onEvent(StartUiEvent.NextPage)
                    }else{
                        onEvent(StartUiEvent.InsertSkipTutorial)
                    }
                },
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
                        text = stringResource(R.string.next),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }
        }

    }

    }


@Composable
fun ContentPortrait(
    modifier: Modifier = Modifier,
    state: StartStateUi,
    onEvent: (StartUiEvent) -> Unit,
    colors: OnboardingColors,
    pagerState: PagerState
    ) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            modifier = Modifier.weight(0.75f),
            state = pagerState,
            userScrollEnabled = false
        ) { page ->
            when (page) {
                0 -> WelcomeTab()
                1 -> PermisionTab(colors = colors)
            }
        }

        // Buttons
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .weight(0.25f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Dots Indicator
            DotsIndicator(
                totalDots = state.totalpages,
                currentDot = state.currentPage,
                colors = colors
            )

            Spacer(modifier = Modifier.height(30.dp))

            // Primary Button
            Button(
                onClick = {
                    if (state.totalpages > state.currentPage + 1) {
                        onEvent(StartUiEvent.NextPage)
                    }else{
                        onEvent(StartUiEvent.InsertSkipTutorial)
                    }
                },
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
                        text = stringResource(R.string.next),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))
    }

}




@Composable
fun PermisionTab(modifier: Modifier = Modifier, colors: OnboardingColors) {
    val permissionState = rememberNotificationPermissionHandler()

    LaunchedEffect(permissionState.shouldShowSettings) {
        if (permissionState.shouldShowSettings) {
            permissionState.openSettings()
        }
    }

    Column (modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.SpaceEvenly){
         AnimatedIllustration(
             colors = colors,
             mainImageVector = Icons.Filled.NotificationsActive
         )

        Text(
            text = "Activa el permiso para recibir notificaciones",
            fontSize = 16.sp,
            color = colors.textSecondary,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp
        )

        Button(onClick = {
         permissionState.requestPermission()
        }) {
            Text("Somehing")
        }

    }
}


@Composable
fun WelcomeTab(modifier: Modifier = Modifier,
     colors: OnboardingColors = OnboardingColors(),
     title: String = "",
     description: String = "") {

    Column (Modifier.fillMaxSize(),
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
            text = stringResource(R.string.welcome_title),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = colors.textPrimary,
            textAlign = TextAlign.Center,
            lineHeight = 36.sp
        )

        Text(
            text = stringResource(R.string.description_welcome),
            fontSize = 16.sp,
            color = colors.textSecondary,
            textAlign = TextAlign.Center,
            lineHeight = 24.sp
        )

    }
}
