package com.alpharays.mymedicommfma.communityv2.community_app.presentation.message_screen.direct_message

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.VideoCall
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.alpharays.mymedicommfma.R
import com.alpharays.mymedicommfma.communityv2.MedCommToast
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityUtils
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.messages_screen.get_all_chats.MessageResponse
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.navigation.CommunityAppScreens
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.BluishGray
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.manRopeFontFamily
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.spacing
import kotlinx.coroutines.launch

@Composable
fun DirectMessageScreen(
    navController: NavController,
    chatId: String,
    chatUserName: String,
    chatUserImage: String,
    viewModel: DirectMessageViewModel = hiltViewModel(),
) {
    val response by viewModel.getAllChatsStateFlow.collectAsStateWithLifecycle()
    val allMessages = (response.data?.chatsData ?: emptyList()).getOrNull(0)?.messages ?: emptyList() // TODO: FOR NOW : AS SAID BY RAMAN
    val currentUserCheckId = response.data?.senderCheckId
    LaunchedEffect(chatId) {
        if(chatId.isNotEmpty()){
            viewModel.getAllMessages(chatId)
        }
    }
    Surface(modifier = Modifier.fillMaxSize()) {
        ComposableDirectMessageScreen(navController, chatUserName, chatUserImage, allMessages, currentUserCheckId, viewModel)
    }
}

@Composable
fun ComposableDirectMessageScreen(
    navController: NavController,
    chatUserName: String,
    chatUserImage: String,
    allMessages: List<MessageResponse?>,
    currentUserCheckId: String?,
    viewModel: DirectMessageViewModel
) {
    Scaffold(
        modifier = Modifier.navigationBarsPadding(),
        topBar = {
            ComposableUserTopBar(navController, chatUserName, chatUserImage)
        },
        bottomBar = {
            ComposableUserBottomBar(navController, viewModel)
        },
        containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 1f)
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 2.dp)
        ) {
            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .border(1.dp, Color(0xFF6F6F6F), RoundedCornerShape(2.dp))
            )
            ComposableUserMessages(navController, currentUserCheckId, allMessages)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ComposableUserTopBar(navController: NavController, chatUserName: String, chatUserImage: String) {
    val style = TextStyle(
        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
        fontFamily = manRopeFontFamily,
        fontWeight = FontWeight.W500,
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.87f),
    )
    val painter = painterResource(id = R.drawable.doctor_profile_ph)
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 10.dp, bottom = 2.dp, end = 5.dp, start = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            modifier = Modifier
                .clickable {
                    navController.navigate(CommunityAppScreens.MessageInboxScreen.route) {
                        popUpTo(0) // TODO: 0 ? honto dis ka ? (for real?)
//                        navController.popBackStack()
                    }
                }
                .padding(end = MaterialTheme.spacing.small),
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "go back icon",
            tint = BluishGray
        )
        Image(
            modifier = Modifier
                .padding(start = 5.dp)
                .size(45.dp)
                .border(1.dp, BluishGray, RoundedCornerShape(20.dp))
                .clip(RoundedCornerShape(20.dp)),
            painter = painter,
            contentDescription = "User avatar"
        )

        Text(
            modifier = Modifier
                .padding(start = 15.dp)
                .weight(1f)
                .basicMarquee(Int.MAX_VALUE),
            text = chatUserName,
            maxLines = 1,
            style = style
        )

        Icon(
            modifier = Modifier.padding(start = 12.dp),
            imageVector = Icons.Default.VideoCall,
            contentDescription = "Video call",
            tint = BluishGray
        )

        Icon(
            modifier = Modifier.padding(start = 8.dp),
            imageVector = Icons.Default.Phone,
            contentDescription = "Voice call",
            tint = BluishGray
        )

        Icon(
            modifier = Modifier.padding(start = 8.dp),
            imageVector = Icons.Default.MoreVert,
            contentDescription = "More options",
            tint = BluishGray
        )
    }
}

@Composable
fun ComposableUserMessages(
    navController: NavController,
    currentUserCheckId: String?,
    allMessages: List<MessageResponse?>
) {
    val messageState = rememberLazyListState()
    val messageScope = rememberCoroutineScope()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .paint(
                painterResource(id = R.drawable.chat_background),
                contentScale = ContentScale.Crop,
                alpha = 0.3f
            )
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = messageState
        ) {
            items(allMessages) { chatsResponse ->
                if (currentUserCheckId == chatsResponse?.senderId) {
                    ComposableSenderCard(Modifier, chatsResponse)
                } else {
                    ComposableReceiverCard(Modifier, chatsResponse)
                }
            }
        }

        LaunchedEffect(key1 = allMessages.size) {
            messageScope.launch {
                messageState.animateScrollToItem(allMessages.size)
            }
        }
    }
}

@Composable
fun ComposableSenderCard(modifier: Modifier, message: MessageResponse?) {
    Box(
        modifier
            .padding(start = 30.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        Card(
            modifier.padding(10.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF80CBC4)),
            shape = RoundedCornerShape(15.dp, 0.dp, 15.dp, 15.dp)
        ) {
            Column(modifier.padding(10.dp)) {
                Text(
                    text = message?.messageContent ?: "",
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.87f),
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun ComposableReceiverCard(modifier: Modifier, message: MessageResponse?) {
    Box(modifier.fillMaxSize()) {
        Card(
            modifier.padding(10.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0XFFA9D9FF)),
            shape = RoundedCornerShape(0.dp, 15.dp, 15.dp, 15.dp)
        ) {
            Column(modifier.padding(10.dp)) {
                Text(
                    text = message?.messageContent ?: "",
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.87f),
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun ComposableUserBottomBar(
    navController: NavController,
    viewModel: DirectMessageViewModel,
) {
    var messageText by remember { mutableStateOf("") }
    var sendMessageIconVisible = messageText.isNotEmpty()
    val rowHeight = LocalConfiguration.current.screenHeightDp.dp
    val maxHeight = LocalDensity.current.run { (0.5f * rowHeight.value).toDp() }
    var isAttachmentsVisible by remember { mutableStateOf(false) }
    val animationTime = 300
    val keyString = "cur_message_details"

    val messageData = remember {
        mutableStateMapOf<String, MessageResponse>()
    }
    val context = LocalContext.current

    Column {
        AnimatedVisibility(
            visible = isAttachmentsVisible,
            enter = expandVertically(
                expandFrom = Alignment.Bottom,
                animationSpec = tween(
                    durationMillis = animationTime,
                    easing = LinearEasing
                )
            ),
            exit = shrinkVertically(
                shrinkTowards = Alignment.Bottom,
                animationSpec = tween(
                    durationMillis = animationTime,
                    easing = LinearEasing
                )
            )
        ) {
            ComposableAttachmentItems()
        }


        Row(
            modifier = Modifier
                .padding(top = 0.dp, bottom = 10.dp, start = 10.dp, end = 10.dp)
                .heightIn(0.dp, maxHeight),
            verticalAlignment = Alignment.Bottom
        ) {
            BasicTextField(
                modifier = Modifier
                    .weight(1f)
                    .imePadding()
                    .border(1.dp, Color.Black, RoundedCornerShape(10.dp))
                    .padding(15.dp)
                    .clickable {
                        isAttachmentsVisible = false
                    },
                value = messageText,
                onValueChange = { newText ->
                    sendMessageIconVisible = newText.isNotEmpty()
                    messageText = newText
                },
                textStyle = TextStyle(
                    fontSize = 14.sp,
                    color = Color(0xFF330091)
                ),
                decorationBox = { innerTextField ->
                    Row(
                        Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.weight(1f)) {
                            if (messageText.isEmpty()) {
                                Text(
                                    "Enter your message",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        color = Color.Gray
                                    ),
                                    modifier = Modifier.align(Alignment.CenterStart)
                                )
                            }
                            innerTextField()
                        }

                        Icon(
                            imageVector = Icons.Default.AttachFile,
                            contentDescription = "Attachments",
                            modifier = Modifier.clickable {
                                isAttachmentsVisible = !isAttachmentsVisible
                            }
                        )
                    }
                }
            )

            AnimatedVisibility(visible = sendMessageIconVisible) {
                Icon(
                    modifier = Modifier
                        .size(50.dp)
                        .padding(start = 15.dp, end = 5.dp)
                        .clickable {
                            MedCommToast.showToast(context, "Coming Soon...")
                        },
                    imageVector = Icons.AutoMirrored.Filled.Send,
                    contentDescription = "Send message"
                )
            }
        }
    }
}

@Composable
fun ComposableAttachmentItems() {
    val context = LocalContext.current
    val color = CommunityUtils.getMedicoColor(context, R.color.bluish_gray)
    Surface(
        modifier = Modifier
            .wrapContentSize()
            .padding(16.dp)
            .border(1.dp, Color(color), RoundedCornerShape(15.dp))
            .clip(RoundedCornerShape(15.dp)),
        color = Color(0xFFCFF9FF)
    ) {
        Column {
            Row(
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                ComposableLottieComposition(
                    Modifier.weight(1f),
                    "Document",
                    R.raw.edit_prescription_lottie
                )
                ComposableLottieComposition(Modifier.weight(1f), "Camera", R.raw.camera_lottie)
                ComposableLottieComposition(Modifier.weight(1f), "Gallery", R.raw.gallery_lottie)
            }
            Row(
                modifier = Modifier.padding(bottom = 10.dp)
            ) {
                ComposableLottieComposition(Modifier.weight(1f), "Audio", R.raw.audio_lottie)
                ComposableLottieComposition(Modifier.weight(1f), "Contact", R.raw.contact_lottie)
            }
        }
    }
}

@Composable
fun ComposableLottieComposition(
    modifier: Modifier,
    attachmentName: String,
    attachmentId: Int,
) {
    Column(
        modifier.padding(5.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(attachmentId))
        LottieAnimation(
            modifier = Modifier.size(120.dp),
            composition = composition,
            iterations = LottieConstants.IterateForever,
            speed = 0.8f,
            contentScale = ContentScale.Fit
        )
        Text(
            text = attachmentName,
            style = TextStyle(
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                fontFamily = FontFamily.Monospace
            )
        )
    }
}