package com.example.shoppermova.ui.screens.home

import android.R.attr
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.SheetValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberStandardBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.shoppermova.ui.screens.home.components.RatingStar
import com.example.shoppermova.ui.theme.Background
import com.example.shoppermova.ui.theme.Green1
import com.example.shoppermova.ui.theme.Green3
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch
import org.koin.compose.viewmodel.koinViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import androidx.compose.ui.text.style.TextOverflow
import android.R.attr.maxLines
import android.widget.Toast
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.filled.DirectionsCar
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.shoppermova.R
import com.example.shoppermova.ui.navigation.Destinations
import com.example.shoppermova.ui.screens.home.components.CustomMapMarker
import com.example.shoppermova.ui.screens.home.components.SheetChooseDriver
import com.example.shoppermova.ui.screens.home.components.SheetPeek
import com.example.shoppermova.ui.screens.home.components.SheetWhereTo
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerComposable
import com.google.maps.android.compose.MarkerState
import kotlinx.coroutines.delay

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnrememberedMutableState")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    navController: NavController
) {
    val context = LocalContext.current

    val state by viewModel.state.collectAsState()

    fun onAction(action: HomeAction) {
        viewModel.onAction(action)
    }

    val scaffoldState = rememberBottomSheetScaffoldState(
        bottomSheetState = rememberStandardBottomSheetState(
            skipHiddenState = true,
            initialValue = SheetValue.PartiallyExpanded,
        ),
    )

    LaunchedEffect (state.bottomSheetHeight) {
        state.cameraPositionState.move(
            update = CameraUpdateFactory.scrollBy(0f, (state.bottomSheetHeight.value * 1.3).toFloat()),
        )
    }

    LaunchedEffect(true) {
        viewModel.showToastMessage.collect { message ->
            if(message.isNotEmpty()) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }
        }
    }

    LaunchedEffect (state.rideEstimate) {
        if (state.rideEstimate != null) {
            scaffoldState.bottomSheetState.expand()
        }
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetContent = {
            when (state.pageStep) {
                PageStep.ChooseDriver -> {
                    SheetChooseDriver(state, (::onAction), navController)
                }
                PageStep.ChooseDestination -> {
                    SheetWhereTo(state, (::onAction))
                }
                else -> {
                    SheetPeek(state, (::onAction), scaffoldState)
                }
            }
        },
        sheetPeekHeight = 120.dp,
        sheetContainerColor = Color.White,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            if (state.pageStep == PageStep.ChooseDriver) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 8.dp)
                        .zIndex(5f),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Button(
                        onClick = {
                            onAction(HomeAction.OnCleanInputs())
                            onAction(HomeAction.OnDismissSelectDriver())
                        },
                        modifier = Modifier.height(56.dp),
                        shape = RoundedCornerShape(50),
                        colors = ButtonColors(
                            containerColor = Color.White,
                            contentColor = Green1,
                            disabledContainerColor = Color.Black,
                            disabledContentColor = Color.Black,
                        ),
                        contentPadding = PaddingValues(0.dp),
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "",
                            modifier = Modifier.size(28.dp)
                        )
                    }

                    Column (
                        modifier = Modifier
                            .background(color = Color.White, shape = RoundedCornerShape(25))
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                    ) {
                        Row (
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Icon(
                                Icons.Filled.Home,
                                contentDescription = "",
                                tint = Green1,
                                modifier = Modifier.size(16.dp)
                            )

                            Text (
                                text = state.fromText,
                                fontSize = 12.sp,
                                fontWeight = FontWeight(600),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                            )
                        }

                        Row (
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            Icon(
                                Icons.Filled.LocationOn,
                                contentDescription = "",
                                tint = Green1,
                                modifier = Modifier.size(16.dp)
                            )

                            Text (
                                text = state.toText,
                                fontSize = 12.sp,
                                fontWeight = FontWeight(600),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }
            }

            else {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp, horizontal = 8.dp)
                        .zIndex(5f),
                    horizontalArrangement = Arrangement.Absolute.Right,
                ) {
                    Button(
                        onClick = {
                            navController.navigate(Destinations.RIDES.route)
                        },
                        modifier = Modifier.height(56.dp),
                        shape = RoundedCornerShape(50),
                        colors = ButtonColors(
                            containerColor = Color.White,
                            contentColor = Green1,
                            disabledContainerColor = Color.Black,
                            disabledContentColor = Color.Black,
                        ),
                        contentPadding = PaddingValues(0.dp),
                    ) {
                        Icon(
                            Icons.Filled.DirectionsCar,
                            contentDescription = "",
                            modifier = Modifier.size(28.dp)
                        )
                    }
                }
            }

            GoogleMap(
                modifier = Modifier.fillMaxSize(),
                cameraPositionState = state.cameraPositionState,
            ) {
                if (state.pageStep == PageStep.ChooseDriver) {
                    state.ridePoints?.let { ridePoints ->
                        Polyline(
                            points = ridePoints,
                        )
                    }

                    state.rideEstimate?.origin?.let { origin ->
                        MarkerComposable (
                            state = MarkerState(origin.toLatLng()),
                            onClick = { marker ->
                                state.cameraPositionState.move(
                                    update = CameraUpdateFactory.newLatLng(marker.position),
                                )
                                state.cameraPositionState.move(
                                    update = CameraUpdateFactory.scrollBy(0f, state.bottomSheetHeight.value + 120f),
                                )
                                true
                            }
                        ) {
                            Icon(
                                Icons.Filled.Home,
                                contentDescription = "",
                                modifier = Modifier.size(32.dp),
                                tint = Green1
                            )
                        }
                    }

                    state.rideEstimate?.destination?.let { destination ->
                        MarkerComposable (
                            state = MarkerState(destination.toLatLng()),
                            onClick = { marker ->
                                state.cameraPositionState.move(
                                    update = CameraUpdateFactory.newLatLng(marker.position),
                                )
                                state.cameraPositionState.move(
                                    update = CameraUpdateFactory.scrollBy(0f, state.bottomSheetHeight.value + 120f),
                                )
                                true
                            }
                        ) {
                            Icon(
                                Icons.Filled.LocationOn,
                                contentDescription = "",
                                modifier = Modifier.size(32.dp),
                                tint = Green1
                            )
                        }
                    }
                }
            }
        }
    }
}
