package com.nayibit.phrasalito_presentation.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.nayibit.phrasalito_presentation.model.IconItem


@Composable
fun DynamicIconSection(
    items: List<IconItem>,
    modifier: Modifier = Modifier,
    columns : Int = 3
) {

    if (!isLandscape()) {
        // 🔹 Portrait: Row of icons
        LazyRow(
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            items(items.size) { index ->
                IconWithTitle(item = items[index])
            }
        }
    } else {
        // 🔹 Landscape: Grid layout
        LazyVerticalGrid(
            columns = GridCells.Fixed(columns),
            modifier = modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items) { item ->
                IconWithTitle(item)
            }
        }
    }
}



@Composable
fun IconWithTitle(item: IconItem) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(8.dp)
    ) {
        Icon(
            imageVector = item.icon,
            contentDescription = item.title,
            tint = item.color,
            modifier = Modifier
                .size(48.dp)
                .padding(bottom = 4.dp)
                .clickable { item.onClick() }
        )
        Text(
            text = item.title,
            fontSize = 14.sp,
            fontWeight = FontWeight.Medium
        )
    }

}

@Composable
@Preview
fun DemoScreen() {
    val icons = listOf(
        IconItem(Icons.Default.Home, "Home", Color(0xFF4CAF50)),
        IconItem(Icons.Default.Settings, "Settings", Color(0xFF2196F3)),
        IconItem(Icons.Default.Favorite, "Favorites", Color(0xFFE91E63)),
        IconItem(Icons.Default.Person, "Profile", Color(0xFFFF9800))
    )

    DynamicIconSection(items = icons)
}
