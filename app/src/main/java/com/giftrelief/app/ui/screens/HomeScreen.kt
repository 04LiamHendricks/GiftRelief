package com.giftrelief.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachMoney
import androidx.compose.material.icons.filled.CloudOff
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Update
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.giftrelief.app.ui.theme.GiftGreen
import com.giftrelief.app.ui.theme.GiftGreenDark
import com.giftrelief.app.ui.theme.GiftGreenLight
import com.giftrelief.app.ui.theme.GiftTextSecondary

private data class Feature(val icon: ImageVector, val label: String)

private val coreFeatures = listOf(
    Feature(Icons.Filled.VolunteerActivism, "Make Donations (One-time & Recurring)"),
    Feature(Icons.Filled.AttachMoney, "Multiple Currencies (ZAR, USD, EUR)"),
    Feature(Icons.Filled.VisibilityOff, "Anonymous Donations"),
    Feature(Icons.Filled.People, "Volunteer Registration"),
    Feature(Icons.Filled.Update, "Relief Projects & Updates"),
    Feature(Icons.Filled.NotificationsActive, "Push Notifications"),
    Feature(Icons.Filled.CloudOff, "Offline Access & Sync"),
    Feature(Icons.Filled.Language, "Multilingual Support"),
    Feature(Icons.Filled.Receipt, "Tax Certificate (Placeholder)")
)

@Composable
fun HomeScreen(onOpenSettings: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 24.dp, vertical = 28.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "GiftRelief",
                color = GiftGreenDark,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            IconButton(onClick = onOpenSettings) {
                Icon(Icons.Filled.Settings, contentDescription = "Settings")
            }
        }

        Text(
            text = "A mobile humanitarian assistance and disaster-relief platform",
            textAlign = TextAlign.Start,
            style = MaterialTheme.typography.bodyMedium,
            color = GiftTextSecondary
        )

        Spacer(modifier = Modifier.height(20.dp))

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(GiftGreenLight, RoundedCornerShape(16.dp))
                .padding(20.dp)
        ) {
            Text(
                text = "Our Mission",
                color = GiftGreenDark,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Connecting generous hearts with communities in need. Together, we bring hope, assistance and relief when it matters most.",
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.fillMaxWidth()
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "CORE FEATURES",
            color = GiftGreenDark,
            fontWeight = FontWeight.Bold,
            style = MaterialTheme.typography.titleMedium
        )

        Spacer(modifier = Modifier.height(12.dp))

        coreFeatures.forEach { feature ->
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Icon(
                    imageVector = feature.icon,
                    contentDescription = null,
                    tint = GiftGreen,
                    modifier = Modifier.padding(end = 12.dp)
                )
                Text(text = feature.label, style = MaterialTheme.typography.bodyLarge)
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
