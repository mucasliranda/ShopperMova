package com.example.shoppermova.ui.screens.rides

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppermova.ui.theme.Green1
import org.koin.compose.viewmodel.koinViewModel
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.shoppermova.ui.screens.rides.componets.RideCard
import com.example.shoppermova.ui.screens.rides.componets.RideCardSkeleton
import com.example.shoppermova.ui.theme.Green3

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RidesScreen(
    viewModel: RidesViewModel = koinViewModel(),
    navController: NavController
) {
    val context = LocalContext.current

    val state by viewModel.state.collectAsState()

    fun onAction(action: RidesAction) {
        viewModel.onAction(action)
    }

    LaunchedEffect(true) {
        viewModel.onGetRides()

        viewModel.showToastMessage.collect { message ->
            if(message.isNotEmpty()) {
                Toast.makeText(context, message, Toast.LENGTH_LONG).show()
            }
        }
    }

    Scaffold (
        modifier = Modifier,
        topBar = {
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Green3)
                    .padding(horizontal = 8.dp, vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        navController.popBackStack()
                    },
                    shape = RoundedCornerShape(50),
                    colors = ButtonColors(
                        containerColor = Color.Transparent,
                        contentColor = Green1,
                        disabledContainerColor = Color.Black,
                        disabledContentColor = Color.Black,
                    ),
                    contentPadding = PaddingValues(0.dp),
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back Icon",
                        tint = Green1,
                        modifier = Modifier
                            .size(24.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = "Viagens",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W500,
                    color = Green1
                )
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        ) {
            SearchBar(
                modifier = Modifier
                    .fillMaxWidth(),
                query = state.searchDriver,
                onQueryChange = {
                    onAction(RidesAction.OnChangeSearchDriver(it))
                },
                onSearch = {
//                    onAction(RidesAction.OnChangeIsSearchOptionsActive(false))
                },
                active = state.isSearchOptionsActive,
                onActiveChange = {
                    onAction(RidesAction.OnChangeIsSearchOptionsActive(it))
                },
                placeholder = {
                    Text(
                        text = "Escolher motorista",
                    )
                },
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = "Search Icon"
                    )
                },
                trailingIcon = {
                    if (state.isSearchOptionsActive) {
                        Icon(
                            modifier = Modifier
                                .clickable {
                                    if (state.searchDriver.isNotEmpty()) {
                                        onAction(RidesAction.OnChangeSearchDriver(""))
                                    } else {
                                        onAction(RidesAction.OnChangeIsSearchOptionsActive(false))
                                    }
                                },
                            imageVector = Icons.Default.Close,
                            contentDescription = "Close Icon",
                        )
                    }
                },
            ) {
                LazyColumn (
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    items(
                        state.driversOptions
                    ) { driver ->
                        Row (
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    onAction(RidesAction.OnFilterByDriver(driver))
                                }
                                .padding(16.dp)
                        ) {
                            Text(
                                text = driver.name,
                            )
                        }
                    }
                }
            }

            LazyColumn (
                modifier = Modifier
                    .fillMaxSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                if (state.rides.isEmpty()) {
                    items(
                        5
                    ) {
                        RideCardSkeleton()
                    }
                }
                else {
                    items(
                        state.rides
                    ) { ride ->
                        RideCard(ride = ride)
                    }
                }
            }
        }
    }
}


