package com.example.shoppermova.ui.screens.home.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DirectionsCar
import androidx.compose.material.icons.outlined.DirectionsCarFilled
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.shoppermova.models.http.RideOption
import com.example.shoppermova.ui.CircularIndeterminateProgressBar
import com.example.shoppermova.ui.currencyFormatter
import com.example.shoppermova.ui.screens.home.HomeAction
import com.example.shoppermova.ui.screens.home.HomeState
import com.example.shoppermova.ui.theme.Green1
import com.example.shoppermova.ui.theme.Green3

@Composable
fun SheetChooseDriver(state: HomeState, onAction: (HomeAction) -> Unit, navController: NavController) {
    val localDensity = LocalDensity.current

    Column (
        modifier = Modifier
            .background(color = Color.White)
            .onGloballyPositioned { coordinates ->
                with(localDensity) {
                    onAction(HomeAction.OnChangeBottomSheetHeight(coordinates.size.height.toDp()))
                }
            },
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        if (state.rideEstimate != null) {
            LazyColumn (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = Color.White)
                    .padding(4.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(
                    state.rideEstimate?.options ?: emptyList()
                ) { driverOption ->
                    DriverCard(driverOption, state, onAction)
                }
            }
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .border(BorderStroke(2.dp, Green1), RoundedCornerShape(24.dp)),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column (
                    modifier = Modifier
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                ) {
                    Text(
                        text = if (state.selectedRideOption != null) currencyFormatter(state.selectedRideOption.value) else currencyFormatter(00.0),
                        modifier = Modifier,
                    )
                    Text(
                        text = if (state.selectedRideOption != null) state.selectedRideOption.name else "Selecione um motorista",
                        modifier = Modifier,
                        fontSize = 14.sp,
                        fontWeight = FontWeight(400),
                        color = Color.Gray,
                        lineHeight = 16.sp,
                    )
                }

                Button (
                    onClick = {
                        if (
                            state.selectedRideOption != null
                            && !state.isConfirmingRide
                        ) {
                            onAction(HomeAction.OnConfirmRide(navController))
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Green1,
                        contentColor = Color.White
                    ),
                    shape = RoundedCornerShape(24.dp),
                    contentPadding = PaddingValues(16.dp),
                    modifier = Modifier
                        .width(113.dp)
                        .height(53.dp),
                    enabled = (state.rideEstimate === null) || (state.selectedRideOption != null)
                ) {
                    if (
                        state.rideEstimate !== null
                        && !state.isConfirmingRide
                    ) {
                        Text(
                            "Confirmar",
                            fontSize = 18.sp,
                            fontWeight = FontWeight(500),
                        )
                    }
                    else {
                        CircularIndeterminateProgressBar()
                    }
                }
            }
        }

    }
}

@Composable
fun DriverCard(driverOption: RideOption, state: HomeState, onAction: (HomeAction) -> Unit) {
    val configuration = LocalConfiguration.current

    val screenWidth = configuration.screenWidthDp.dp.value

    Row (
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = {
                    onAction(HomeAction.OnSelectDriverOption(driverOption))
                }
            )
            .padding(horizontal = 4.dp)
            .background(
                color = if(state.selectedRideOption == driverOption) Green3 else Color.White,
                shape = RoundedCornerShape(8.dp)
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Column (
            modifier = Modifier
                .width((screenWidth * 0.7f).dp)
                .padding(8.dp),
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text(
                    text = driverOption.name,
                    modifier = Modifier,
                    fontSize = 18.sp,
                    fontWeight = FontWeight(500),
                )

                RatingStar(
                    rating = driverOption.review.rating.toFloat(),
                    maxRating = 5,
                )
            }

            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Outlined.DirectionsCar,
                    contentDescription = "Icon",
                    tint = Green1,
                    modifier = Modifier
                        .size(24.dp)
                )

                Text(
                    text = driverOption.vehicle,
                    modifier = Modifier
                        .padding(bottom = 4.dp),
                    fontSize = 14.sp,
                    fontWeight = FontWeight(400),
                    color = Color.Gray,
                    lineHeight = 16.sp,
                )
            }

            Text(
                text = driverOption.description,
                modifier = Modifier,
                fontSize = 12.sp,
                fontWeight = FontWeight(400),
                color = Color.Gray,
                lineHeight = 16.sp,
            )
        }

        Column (
            modifier = Modifier
                .wrapContentWidth()
                .padding(8.dp),
            horizontalAlignment = Alignment.End,
        ) {
            Text(
                text = currencyFormatter(driverOption.value),
                modifier = Modifier
                    .width(IntrinsicSize.Max)
                    .padding(horizontal = 8.dp),
                fontSize = 16.sp,
                fontWeight = FontWeight(500),
                lineHeight = 16.sp,
            )
        }
    }
}


