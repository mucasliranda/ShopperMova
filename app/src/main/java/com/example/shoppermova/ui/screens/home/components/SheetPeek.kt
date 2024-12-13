package com.example.shoppermova.ui.screens.home.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomSheetScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.shoppermova.ui.screens.home.HomeAction
import com.example.shoppermova.ui.screens.home.HomeState
import com.example.shoppermova.ui.screens.home.PageStep
import com.example.shoppermova.ui.theme.Background
import com.example.shoppermova.ui.theme.Green1
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SheetPeek(state: HomeState, onAction: (HomeAction) -> Unit, scaffoldState: BottomSheetScaffoldState) {
    val scope = rememberCoroutineScope()

    Row (
        modifier = Modifier,
    ) {
        OutlinedTextField(
            value = "",
            onValueChange = {  },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp)
                .clickable {
                    scope.launch {
                        onAction(HomeAction.OnChangePageStep(PageStep.ChooseDestination))
                        scaffoldState.bottomSheetState.expand()
                    }
                },
            placeholder = {
                Text(
                    color = Green1,
                    text = "Para onde vamos?",
                    fontWeight = FontWeight(500)
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                disabledTextColor = MaterialTheme.colorScheme.onSurface,
                disabledBorderColor = Color.Transparent,
                disabledPlaceholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                disabledContainerColor = Background,
            ),
            shape = RoundedCornerShape(25),
            enabled = false,
        )
    }
}
