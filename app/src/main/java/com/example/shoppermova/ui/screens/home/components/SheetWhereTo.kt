package com.example.shoppermova.ui.screens.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.shoppermova.ui.screens.home.HomeAction
import com.example.shoppermova.ui.screens.home.HomeState
import com.example.shoppermova.ui.screens.home.Inputs
import com.example.shoppermova.ui.theme.Background
import com.example.shoppermova.ui.theme.Green1

@Composable
fun SheetWhereTo(state: HomeState, onAction: (HomeAction) -> Unit) {
    LaunchedEffect (state.pageStep) {
        onAction(HomeAction.OnInputFocusRequest(Inputs.From))
    }

    Column (
        modifier = Modifier
            .height(480.dp)
            .background(color = Background),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color.White)
                .padding(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            OutlinedTextField(
                value = state.fromText,
                onValueChange = { onAction(HomeAction.OnChangeFromText(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .focusRequester(state.inputFocusRequester.from)
                    .onFocusChanged { if (it.isFocused) onAction(HomeAction.OnChangeSelectInput("from")) },
                placeholder = {
                    Text(
                        color = Green1,
                        text = "Insira seu local de embarque",
                        fontWeight = FontWeight(500)
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Background,
                    focusedContainerColor = Background,

                    focusedBorderColor = Green1,
                    unfocusedBorderColor = Color.Transparent
                ),
                shape = RoundedCornerShape(25),
                trailingIcon = {
                    if (state.fromText.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = "Search",
                            tint = Green1,
                            modifier = Modifier.clickable {
                                onAction(HomeAction.OnChangeFromText(""))
                            }
                        )
                    }
                },
                readOnly = true
            )

            OutlinedTextField(
                value = state.toText,
                onValueChange = { onAction(HomeAction.OnChangeToText(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
                    .focusRequester(state.inputFocusRequester.to)
                    .onFocusChanged { if (it.isFocused) onAction(HomeAction.OnChangeSelectInput("to")) },
                placeholder = {
                    Text(
                        color = Green1,
                        text = "Insira seu destino",
                        fontWeight = FontWeight(500)
                    )
                },
                colors = OutlinedTextFieldDefaults.colors(
                    unfocusedContainerColor = Background,
                    focusedContainerColor = Background,

                    focusedBorderColor = Green1,
                    unfocusedBorderColor = Color.Transparent
                ),
                shape = RoundedCornerShape(25),
                trailingIcon = {
                    if (state.toText.isNotEmpty()) {
                        Icon(
                            imageVector = Icons.Outlined.Close,
                            contentDescription = "Search",
                            tint = Green1,
                            modifier = Modifier.clickable {
                                onAction(HomeAction.OnChangeToText(""))
                            }
                        )
                    }
                },
                readOnly = true
            )
        }

        LazyColumn (
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .padding(vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(
                state.searchedLocations
            ) { location ->
                val locationTitle = location.split(",")[0]
                Column (
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = {
                                if (state.selectedInput == "from") {
                                    onAction(HomeAction.OnChangeFromText(location))
                                    onAction(HomeAction.OnInputFocusRequest(Inputs.To))
                                } else {
                                    onAction(HomeAction.OnChangeToText(location))
                                }
                            }
                        )
                        .padding(horizontal = 8.dp),
                ) {
                    Text (
                        text = locationTitle,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        fontSize = 16.sp,
                        fontWeight = FontWeight(500),
                    )

                    Text (
                        text = location,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp),
                        fontSize = 14.sp,
                        fontWeight = FontWeight(400),
                        color = Color.Gray,
                        lineHeight = 16.sp,
                    )
                }
            }
        }
    }
}
