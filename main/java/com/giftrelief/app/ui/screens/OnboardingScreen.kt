package com.giftrelief.app.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VolunteerActivism
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.giftrelief.app.ui.components.PrimaryButton
import com.giftrelief.app.ui.theme.GiftGreen
import com.giftrelief.app.ui.theme.GiftGreenDark
import com.giftrelief.app.ui.theme.GiftGreenLight
import com.giftrelief.app.ui.theme.GiftTextSecondary

@Composable
fun OnboardingScreen(
    onGetStarted: () -> Unit,
    onAlreadyHaveAccount: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(horizontal = 28.dp, vertical = 32.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "GiftRelief",
            color = GiftGreenDark,
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        Text(
            text = "Together, we can bring hope and relief to those who need it most.",
            textAlign = TextAlign.Center,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(28.dp))

        // Illustration placeholder — replace with an Image() using a real
        // illustration asset dropped into res/drawable.
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .weight(1f, fill = false)
                .background(GiftGreenLight, RoundedCornerShape(24.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Filled.VolunteerActivism,
                contentDescription = "Volunteers helping community",
                tint = GiftGreen,
                modifier = Modifier.size(96.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        PageIndicator(pageCount = 4, currentPage = 0)

        Spacer(modifier = Modifier.height(24.dp))

        PrimaryButton(text = "Get Started", onClick = onGetStarted)

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(onClick = onAlreadyHaveAccount) {
            Text("I already have an account", color = GiftTextSecondary)
        }
    }
}

@Composable
private fun PageIndicator(pageCount: Int, currentPage: Int) {
    Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
        repeat(pageCount) { index ->
            Box(
                modifier = Modifier
                    .size(if (index == currentPage) 10.dp else 8.dp)
                    .background(
                        if (index == currentPage) GiftGreenDark else GiftGreenLight,
                        CircleShape
                    )
            )
        }
    }
}
