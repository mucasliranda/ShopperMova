package com.example.shoppermova.ui.screens.rides.componets

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AccessTime
import androidx.compose.material.icons.outlined.DirectionsCar
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Route
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppermova.models.http.Driver
import com.example.shoppermova.models.http.Ride
import com.example.shoppermova.ui.currencyFormatter
import com.example.shoppermova.ui.theme.Green1
import com.example.shoppermova.ui.theme.Green3

@SuppressLint("DefaultLocale")
@Composable
fun RideCard(ride: Ride) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Green3, shape = RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp))
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text (
                    text = ride.date,
                    fontSize = 16.sp,
                    modifier = Modifier,
                    color = Green1,
                    fontWeight = FontWeight.W500,
                )

                Text (
                    text = currencyFormatter(String.format("%.2f", ride.value).toDouble()),
                    fontSize = 14.sp,
                    modifier = Modifier,
                    color = Green1,
                    fontWeight = FontWeight.W500,
                )
            }

            Column(
                modifier = Modifier
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {

                Row (
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    TitleAndValue(
                        icon = Icons.Outlined.DirectionsCar,
                        title = "Motorista: ",
                        value = ride.driver.name
                    )
                }

                TitleAndValue(
                    icon = Icons.Outlined.LocationOn,
                    title = "Origem: ",
                    value = ride.origin
                )

                TitleAndValue(
                    icon = Icons.Outlined.LocationOn,
                    title = "Destino: ",
                    value = ride.destination
                )

                Row (
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TitleAndValue(
                        icon = Icons.Outlined.Route,
                        value = ride.distance.toInt().toString() + " km"
                    )

                    TitleAndValue(
                        icon = Icons.Outlined.AccessTime,
                        value = ride.duration
                    )
                }
            }
        }
    }

}

// w 395
// h 216

@Composable
fun RideCardSkeleton() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(216.dp)
            .shimmerEffect()
    )
}

@Composable
fun TitleAndValue(title: String? = null, value: String, icon: ImageVector) {
    Row(
        modifier = Modifier,
        verticalAlignment = Alignment.Top
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "Icon",
            tint = Green1,
            modifier = Modifier
                .size(24.dp)
        )

        Spacer(modifier = Modifier.width(2.dp))

        if (title != null) {
            Text(
                text = title,
                fontSize = 16.sp,
                fontWeight = FontWeight.W500,
                color = Color.Gray
            )
        }

        Text(
            text = value,
            fontSize = 16.sp,
            fontWeight = FontWeight.W400,
            color = Color.Gray
        )
    }
}

@Preview(showBackground = false)
@Composable
fun RideCardPreview() {
    val ride = Ride(
        id = 15,
        date = "2024-05-29T03:17:16",
        origin = "9742 S Mill Street, 245, Lawton, 54293-2748",
        destination = "2452 Gorczany Route, 166, Lynchworth, 53681-7950",
        distance = 62.40090183164641,
        duration = "0:15",
        driver = Driver(
            id = 3,
            name = "James Bond"
        ),
        value = 251.27014376395837
    )

    RideCard(ride = ride)
}

fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition()
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(1000)
        )
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                Color(0xFFB8B5B5),
                Color(0xFF8F8B8B),
                Color(0xFFB8B5B5),
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat()),
        ),
        shape = RoundedCornerShape(12.dp)
    )
        .onGloballyPositioned {
            size = it.size
        }
}
