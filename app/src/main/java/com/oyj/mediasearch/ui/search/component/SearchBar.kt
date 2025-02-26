package com.oyj.mediasearch.ui.search.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.oyj.mediasearch.R

@Composable
fun SearchBar(
    query: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
    ) {
        OutlinedTextField(
            value = query,
            singleLine = true,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = stringResource(id = R.string.text_enter_search_word),
                    color = Color.Gray
                )
            },
            trailingIcon = {
                if (query.isNotEmpty()) {
                    Icon(
                        imageVector = Icons.Outlined.Clear,
                        contentDescription = stringResource(id = R.string.text_search_word_clear_icon),
                        modifier = Modifier.clickable { onValueChange("") }
                    )
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.CenterVertically)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SearchBarIsEmptyPreView() {
    val query = ""
    SearchBar(
        query = query,
        onValueChange = {}
    )
}

@Preview(showBackground = true)
@Composable
private fun SearchBarIsNotEmptyPreView() {
    val query = "search"
    SearchBar(
        query = query,
        onValueChange = {}
    )
}

