package com.alpharays.mymedicommfma.communityv2.community_app.presentation.common.share_app

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.core.graphics.drawable.toBitmap
import com.alpharays.mymedicommfma.R
import com.alpharays.mymedicommfma.communityv2.MedCommToast
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.BottomSheetColor
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.fontSize
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.manRopeFontFamily
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.size
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.spacing

const val APP_LINK = ""

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShareAppsBottomSheet(sharingMessage: String, messageData: String = "", openShareAppBottomSheet: () -> Unit) {
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    ModalBottomSheet(
        modifier = Modifier
            .imePadding()
            .navigationBarsPadding(),
        onDismissRequest = { openShareAppBottomSheet() },
        sheetState = bottomSheetState,
        containerColor = BottomSheetColor,
        windowInsets = WindowInsets.ime
    ) {
        ShareAppScreen(
            sharingMessage = sharingMessage,
            messageData = messageData,
            dismissBottomSheet = { openShareAppBottomSheet() }
        )
        Spacer(modifier = Modifier.height(64.dp))
    }
}

@Composable
fun ShareAppScreen(sharingMessage: String, messageData: String, dismissBottomSheet: () -> Unit) {
    Column(
        modifier = Modifier
            .background(BottomSheetColor)
            .padding(MaterialTheme.spacing.medSmall),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ShareTextAndCloseRow(sharingMessage) { dismissBottomSheet() }
        ShareLinkCardView(messageData)
        ShareableAppsRow(messageData)
    }
}

@Composable
fun ShareTextAndCloseRow(sharingMessage: String, dismissBottomSheet: () -> Unit) {
    val style = TextStyle(
        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
        fontFamily = manRopeFontFamily,
        fontWeight = FontWeight.W700,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.87f),
        textAlign = TextAlign.Start
    )
    Row(
        modifier = Modifier.padding(
            bottom = MaterialTheme.spacing.medium,
            start = MaterialTheme.spacing.small,
            end = MaterialTheme.spacing.small
        ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            modifier = Modifier.weight(1f),
            text = sharingMessage,
            style = style
        )

        Icon(
            modifier = Modifier
                .size(MaterialTheme.size.smallIconSize)
                .clickable { dismissBottomSheet() },
            painter = painterResource(id = R.drawable.close_bottom_sheet_icon),
            contentDescription = "close bottom sheet",
            tint = Color.Unspecified
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ShareLinkCardView(messageData: String) {
    var cardHeight by remember { mutableIntStateOf(0) }
    val style1 = TextStyle(
        fontFamily = manRopeFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = MaterialTheme.typography.bodySmall.fontSize,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
    )
    val style2 = TextStyle(
        fontFamily = manRopeFontFamily,
        fontWeight = FontWeight.W600,
        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
    )

    var copyToClipBoard by remember { mutableStateOf(false) }
    val clipboardManager: ClipboardManager = LocalClipboardManager.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = MaterialTheme.spacing.medium,
                start = MaterialTheme.spacing.small,
                end = MaterialTheme.spacing.small
            ),
        colors = CardDefaults.elevatedCardColors(containerColor = Color(0xFF2d3136)),
        shape = RoundedCornerShape(MaterialTheme.size.small),
        border = BorderStroke(1.dp, Color.White.copy(alpha = 0.3f))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = MaterialTheme.spacing.medium,
                    vertical = MaterialTheme.spacing.small
                )
                .onSizeChanged { newSize ->
                    cardHeight = newSize.height
                },
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = MaterialTheme.spacing.small),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Share Link",
                    style = style1
                )

                VerticalDivider(
                    modifier = Modifier
                        .padding(horizontal = MaterialTheme.spacing.small)
                        .heightIn(
                            MaterialTheme.size.default,
                            with(LocalDensity.current) { cardHeight.toDp() }
                        )
                )

                Text(
                    modifier = Modifier.basicMarquee(Int.MAX_VALUE),
                    text = APP_LINK,
                    style = style2
                )
            }

            Image(
                modifier = Modifier
                    .size(MaterialTheme.size.smallIconSize)
                    .clickable { copyToClipBoard = true },
                painter = painterResource(id = R.drawable.copy_paste_icon),
                contentDescription = "copy paste url link"
            )
        }
    }

    val context = LocalContext.current

    if (copyToClipBoard) {
        MedCommToast.showToast(context, "Copied to clipboard")
        clipboardManager.setText(AnnotatedString((APP_LINK.plus("\n$messageData"))))
        copyToClipBoard = false
    }
}

fun getShareableApps(context: Context): List<ResolveInfo> {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, APP_LINK)
    }
    return context.packageManager.queryIntentActivities(
        shareIntent,
        PackageManager.MATCH_DEFAULT_ONLY
    )
}

@Composable
fun ShareableAppsRow(messageData: String) {
    val context = LocalContext.current
    val shareableApps = remember { getShareableApps(context) }
    val style = TextStyle(
        fontSize = MaterialTheme.fontSize.small,
        fontFamily = manRopeFontFamily,
        fontWeight = FontWeight.W700,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.87f),
    )

    LazyRow(
        modifier = Modifier.padding(MaterialTheme.spacing.small),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small)
    ) {
        items(shareableApps) { app ->
            val appIcon = remember { app.loadIcon(context.packageManager) }
            val appName = remember { app.loadLabel(context.packageManager).toString() }
            Column(
                modifier = Modifier.padding(end = MaterialTheme.spacing.avgLessSmall),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    modifier = Modifier
                        .size(MaterialTheme.size.large1)
                        .clip(CircleShape)
                        .clickable {
                            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                                type = "text/plain"
                                val text = APP_LINK.plus("\n$messageData")
                                putExtra(Intent.EXTRA_TEXT, text)
                                `package` = app.activityInfo.packageName
                            }
                            context.startActivity(shareIntent)
                        },
                    bitmap = appIcon.toBitmap().asImageBitmap(),
                    contentDescription = "Share with $appName"
                )
                Text(
                    modifier = Modifier.padding(top = MaterialTheme.spacing.extraSmall),
                    text = appName,
                    style = style
                )
            }
        }
    }
}