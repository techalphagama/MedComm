package com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.full_post_screen

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreInterceptKeyBeforeSoftKeyboard
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.alpharays.mymedicommfma.R
import com.alpharays.mymedicommfma.common.connectivity.ConnectivityObserver
import com.alpharays.mymedicommfma.communityv2.MedCommRouter
import com.alpharays.mymedicommfma.communityv2.MedCommRouter.COMMENT_ADDED
import com.alpharays.mymedicommfma.communityv2.MedCommRouter.SUCCESS_CODE
import com.alpharays.mymedicommfma.communityv2.MedCommToast
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.CELEBRATE_OPTION
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.FUNNY_OPTION
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.INSIGHTFUL_OPTION
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.LIKE_OPTION
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.LOVE_OPTION
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.SUPPORT_OPTION
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.all_comm_posts.CommunityPost
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.comments.all_comments.CommentData
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.CommentsViewModel
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.CommunityViewModel
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.PostSharedViewModel
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.all_posts_screen.CommunityPostReactionOptions
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.all_posts_screen.CommunityPostReactions
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.all_posts_screen.ComposablePopReactionsRow
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.all_posts_screen.ReactionOptions
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.all_posts_screen.ReactionPainters
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.all_posts_screen.fullPostInitLoad
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.navigation.CommunityAppScreens
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.FocusedTextColor
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.OnPrimaryFixed
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.Primary400
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.Primary500
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.manRopeFontFamily
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.size
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.spacing
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.workSansFontFamily

@Composable
fun CommunityFullPostScreen(
    navController: NavController,
    addComment: Boolean,
    communityViewModel: CommunityViewModel = hiltViewModel(),
    commentsViewModel: CommentsViewModel = hiltViewModel(),
    postSharedViewModel: PostSharedViewModel = hiltViewModel(),
) {
    LaunchedEffect(Unit) {
        commentsViewModel.getAllComments()
    }
    val allCommentsResponse by commentsViewModel.allCommentsStateFlow.collectAsStateWithLifecycle()
    val allCommentsData = allCommentsResponse.data
    val currentPost by postSharedViewModel.postContentState.collectAsStateWithLifecycle()
    val cardBorderBrush = Brush.linearGradient(colors = listOf(OnPrimaryFixed, Color.Transparent, Color.Transparent, OnPrimaryFixed))

    Scaffold(
        modifier = Modifier.navigationBarsPadding(),
        topBar = {
            CommentsTopBarComposable(navController)
        },
        bottomBar = {
            CommentsBottomBarComposable(openCommentTextField = addComment, viewModel = commentsViewModel)
        },
        containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = .95f)
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(MaterialTheme.spacing.extraSmall)
        ) {
            PostComposableContent(
                navController = navController,
                cardBorderBrush = cardBorderBrush,
                post = currentPost ?: CommunityPost(),
                allCommentsData = allCommentsData,
                communityViewModel = communityViewModel,
                postSharedViewModel = postSharedViewModel
            )
        }
    }
}

@Composable
fun CommentsTopBarComposable(navController: NavController) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(Primary400),
        elevation = CardDefaults.cardElevation(defaultElevation = MaterialTheme.spacing.avgExtraSmall),
        shape = RoundedCornerShape(MaterialTheme.spacing.default)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.avgSmall),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .padding(MaterialTheme.spacing.small)
                    .clickable {
                        navController.navigate(CommunityAppScreens.CommunityScreen.route) {
                            popUpTo(0)
                        }
                    },
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = "close post"
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                modifier = Modifier.padding(8.dp),
                imageVector = Icons.Default.MoreVert,
                contentDescription = "more options"
            )
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CommentsBottomBarComposable(
    viewModel: CommentsViewModel,
    openCommentTextField: Boolean
) {
    val addCommentStatus by viewModel.newCommentStateFlow.collectAsStateWithLifecycle()
    val context = LocalContext.current
    var userComment by remember { mutableStateOf("") }
    val painter = painterResource(id = R.drawable.doctor_profile_ph)
    val focusManager = LocalFocusManager.current
    var isFocused by remember { mutableStateOf(true) }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusRequester = remember { FocusRequester() }
    LaunchedEffect(openCommentTextField) {
        if (openCommentTextField) {
            focusRequester.requestFocus()
//            keyboardController?.show()
        } else {
            keyboardController?.hide()
        }
    }
    var topPadding by remember { mutableStateOf(0.dp) }
    var verticalAlignment by remember { mutableStateOf(Alignment.CenterVertically) }
    var addCommentToPost by remember { mutableStateOf(false) }
    val style1 = TextStyle(
        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
        fontFamily = manRopeFontFamily,
        fontWeight = FontWeight.W500,
        color = FocusedTextColor,
        textAlign = TextAlign.Start
    )
    val style2 = TextStyle(
        textAlign = TextAlign.End,
        color = FocusedTextColor,
        fontWeight = FontWeight.W600
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(Color.White),
        shape = RoundedCornerShape(0.dp)
    ) {
        BackHandler(enabled = isFocused) {
            focusManager.clearFocus()
            isFocused = false
        }
        HorizontalDivider(
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.small),
            color = MaterialTheme.colorScheme.surface.copy(alpha = .08f)
        )
        Row(
            modifier = Modifier
                .padding(horizontal = 4.dp, vertical = 2.dp)
                .fillMaxWidth()
                .padding(horizontal = 6.dp),
            verticalAlignment = verticalAlignment
        ) {
            Image(
                painter = painter,
                contentDescription = "doctor avatar",
                modifier = Modifier
                    .border(1.dp, Color.Gray, RoundedCornerShape(MaterialTheme.size.large))
                    .size(MaterialTheme.size.large)
                    .clip(RoundedCornerShape(MaterialTheme.size.large))
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding()
                    .focusRequester(focusRequester)
                    .onFocusChanged {
                        isFocused = it.isFocused
                    }
                    .onPreInterceptKeyBeforeSoftKeyboard {
                        if (it.key.keyCode == MedCommRouter.KEYBOARD_CLOSE_ON_BACK_PRESS_KEY_CODE) {
                            focusManager.clearFocus()
                        }
                        true
                    },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                value = userComment,
                onValueChange = { newText ->
                    userComment = newText
                },
                placeholder = {
                    Text(text = "Leave your thoughts here ...", style = style1)
                },
                textStyle = style1,
                maxLines = 5,
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                }),
            )
        }

        AnimatedVisibility(visible = userComment.isNotEmpty()) {
            topPadding = 10.dp
            verticalAlignment = Alignment.Top
            HorizontalDivider()
            Text(
                text = "Post",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.medSmall)
                    .clickable { addCommentToPost = true },
                style = style2
            )
        }
    }

    if(addCommentToPost) {
        // add new comment and get All updated comments
        viewModel.addNewComment(userComment)
        addCommentToPost = false
        userComment = ""
        isFocused = false
        focusManager.clearFocus()
        keyboardController?.hide()
    }

    addCommentStatus.data?.success?.let {
        if(it == SUCCESS_CODE){
            LaunchedEffect(Unit) {
                MedCommToast.showToast(context, addCommentStatus.data?.newCommentData ?: COMMENT_ADDED)
            }
        }
    }
}

@Composable
fun PostComposableContent(
    navController: NavController,
    communityViewModel: CommunityViewModel?,
    postSharedViewModel: PostSharedViewModel,
    post: CommunityPost,
    allCommentsData: List<CommentData>?,
    cardBorderBrush: Brush,
) {
    val context = LocalContext.current
    val style = TextStyle(
        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
        fontFamily = manRopeFontFamily,
        fontWeight = FontWeight.W600,
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f),
        textAlign = TextAlign.Start
    )
    val commentsValid = !post.comments.isNullOrEmpty()
    val likesValid = post.reactions?.let { reactions ->
        listOf(
            reactions.like,
            reactions.love,
            reactions.celebrate,
            reactions.support,
            reactions.insightful,
            reactions.funny
        ).sumOf { it?.size ?: 0 }
    } ?: 0
//    val scope = rememberCoroutineScope()
//    val pagerState = rememberPagerState(
//        initialPage = 0,
//        pageCount = {
//            if(likesValid != 0 && commentsValid) 2
//            else if(likesValid != 0 || commentsValid) 1
//            else 0
//        }
//    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.spacing.extraSmall)
            .border(1.dp, cardBorderBrush, RoundedCornerShape(MaterialTheme.size.small)),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 1f)),
        shape = RoundedCornerShape(MaterialTheme.size.small)
    ) {
        Column(
            modifier = Modifier.padding(
                start = MaterialTheme.spacing.small,
                end = MaterialTheme.spacing.small,
                top = MaterialTheme.spacing.small,
                bottom = MaterialTheme.spacing.extraSmall
            )
        ) {
            CommunityFullPostUpperRow(context, post)
            CommunityFullPostContent(post)
            CommunityFullPostLowerRow(post)
            AnimatedVisibility(visible = communityViewModel != null) {
                Column {
                    CommunityFullPostLastRow(
                        context = context,
                        currentPost = post,
                        postSharedViewModel = postSharedViewModel,
                        navController = navController,
                        viewModel = communityViewModel!!,
                    )
                    if(commentsValid || likesValid != 0) HorizontalDivider()
                    AnimatedVisibility(visible = likesValid != 0 ) {
                        Column(
                            horizontalAlignment = Alignment.Start
                        ) {
                            Row(
                                modifier = Modifier.padding(
                                    top = MaterialTheme.spacing.smallest,
                                    start = MaterialTheme.spacing.lessSmall,
                                    bottom = MaterialTheme.spacing.extraSmall
                                )
                            ) {
                                Text(text = "Reactions", style = style)
                            }

                            LazyRow(modifier = Modifier.padding(bottom = MaterialTheme.spacing.medium)) {
                                items(postSharedViewModel.getTopTwoValidReactions()){ item ->
                                    val reactionPainter = item.reaction
                                    val painterId = when(reactionPainter){
                                        ReactionPainters.Like.getLowercaseName() -> { R.drawable.like_reaction }
                                        ReactionPainters.Love.getLowercaseName() -> { R.drawable.love_reaction}
                                        ReactionPainters.Funny.getLowercaseName() -> { R.drawable.funny_reaction }
                                        ReactionPainters.Celebrate.getLowercaseName() -> { R.drawable.celebrate_reaction }
                                        ReactionPainters.Support.getLowercaseName() -> { R.drawable.support_reaction }
                                        ReactionPainters.Insightful.getLowercaseName() -> { R.drawable.insightful_reaction }
                                        else -> { R.drawable.not_liked }
                                    }
                                    Box(
                                        modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium)
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.doctor_profile_ph),
                                            contentDescription = "doctor avatar",
                                            modifier = Modifier
                                                .padding(end = MaterialTheme.spacing.small)
                                                .border(
                                                    1.dp,
                                                    Color.Gray,
                                                    RoundedCornerShape(MaterialTheme.size.large)
                                                )
                                                .size(MaterialTheme.size.large)
                                                .clip(RoundedCornerShape(MaterialTheme.size.large))
                                        )

//                                Image(
//                                    painter = painterResource(id = painterId),
//                                    contentDescription = "reaction",
//                                    contentScale = ContentScale.Crop,
//                                    modifier = Modifier
//                                        .padding(
//                                            start = MaterialTheme.spacing.small,
//                                            end = MaterialTheme.spacing.small,
//                                            top = MaterialTheme.spacing.small
//                                        )
//                                        .background(
//                                            BluishGray,
//                                            RoundedCornerShape(MaterialTheme.size.small)
//                                        )
//                                        .size(MaterialTheme.size.medSmall)
//                                        .clip(RoundedCornerShape(MaterialTheme.size.small))
//                                        .align(Alignment.BottomEnd)
//                                )

                                        Image(
                                            painter = painterResource(id = painterId),
                                            contentDescription = "reaction",
                                            contentScale = ContentScale.Fit,
                                            modifier = Modifier
                                                .padding(
                                                    start = MaterialTheme.spacing.small,
                                                    end = MaterialTheme.spacing.small,
                                                    top = MaterialTheme.spacing.small
                                                )
//                                        .background(Color(0xFFC2FFF7), CircleShape)
                                                .size(MaterialTheme.size.medSmall)
                                                .clip(CircleShape)
                                                .align(Alignment.BottomEnd)
                                        )
                                    }
                                }
                            }
                        }
                    }
                    AnimatedVisibility(visible = commentsValid) {
                        Column(verticalArrangement = Arrangement.Top) {
                            Row(
                                modifier = Modifier.padding(
                                    start = MaterialTheme.spacing.lessSmall,
                                    bottom = MaterialTheme.spacing.extraSmall
                                )
                            ) {
                                Text(text = "Comments", style = style)
                            }

                            ComposableAllComments(
                                navController = navController,
                                currentPostId = post.postId,
                                allCommentsData = allCommentsData
                            )
                        }

                        // TODO: add horizontal pager
//                Row(modifier = Modifier.padding(vertical = MaterialTheme.spacing.medSmall, horizontal = MaterialTheme.spacing.extraSmall)) {
//                    if(pagerState.pageCount == 1) {
//                        Text(
//                            modifier = Modifier
//                                .padding(end = MaterialTheme.spacing.small)
//                                .clickable {
//                                    scope.launch {
//                                        pagerState.animateScrollToPage(0)
//                                    }
//                                },
//                            text = if(likesValid != 0) "Likes" else "Comments",
//                            style = style
//                        )
//                    }
//                    else {
//                        Text(
//                            modifier = Modifier
//                                .padding(end = MaterialTheme.spacing.small)
//                                .clickable {
//                                    scope.launch {
//                                        pagerState.animateScrollToPage(0)
//                                    }
//                                },
//                            text = "Likes",
//                            style = style
//                        )
//                        Text(
//                            modifier = Modifier
//                                .padding(end = MaterialTheme.spacing.small)
//                                .clickable {
//                                    scope.launch {
//                                        pagerState.animateScrollToPage(1)
//                                    }
//                                },
//                            text = "Comments",
//                            style = style
//                        )
//                    }
//                }
//                HorizontalPager(state = pagerState, verticalAlignment = Alignment.Top) { page ->
//                    if(pagerState.pageCount == 1) {
//                        if(likesValid != 0) {
//                            ComposableAllLikes(navController = navController, currentPostId = post._id)
//                        }
//                        else{
//                            ComposableAllComments(navController = navController, currentPostId = post._id, allCommentsData = allCommentsData)
//                        }
//                    }
//                    else {
//                        if(page == 0) {
//                            ComposableAllLikes(navController = navController, currentPostId = post._id)
//                        }
//                        if(page == 1) {
//                            ComposableAllComments(navController = navController, currentPostId = post._id, allCommentsData = allCommentsData)
//                        }
//                    }
                    }
                }
            }
        }
    }
}

@Composable
fun CommunityFullPostUpperRow(context: Context, post: CommunityPost) {
    val style1 = TextStyle(
        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
        fontFamily = manRopeFontFamily,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.87f),
        textAlign = TextAlign.Start
    )
    val style2 = TextStyle(
        fontSize = MaterialTheme.typography.bodySmall.fontSize,
        fontFamily = manRopeFontFamily,
        fontWeight = FontWeight.W500,
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.6f),
        textAlign = TextAlign.Start
    )
    Row(
        verticalAlignment = Alignment.Top,
        modifier = Modifier.padding(MaterialTheme.spacing.lessSmall)
    ) {
        Image(
            modifier = Modifier.size(MaterialTheme.size.large3),
            painter = painterResource(id = R.drawable.doctor_profile_ph),
            contentDescription = "doctor avatar"
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(
//                    start = MaterialTheme.spacing.avgSmall,
                    end = MaterialTheme.spacing.lessSmall
                ),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                modifier = Modifier.padding(MaterialTheme.spacing.extraSmall),
                text = post.posterName ?: "--",
                style = style1
            )

            Text(
                modifier = Modifier.padding(start = MaterialTheme.spacing.extraSmall),
                text = post.aboutDoc ?: "--",
                maxLines = 1,
                style = style2
            )
        }

        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier.clickable {
                MedCommToast.showToast(context, "Coming Soon...")
            }
        ) {
            Icon(
                modifier = Modifier
                    .padding(end = MaterialTheme.spacing.lessSmall)
                    .size(MaterialTheme.size.smallIconSize),
                imageVector = Icons.Default.PersonAdd,
                contentDescription = "connect",
                tint = Color.Blue.copy(alpha = .8f)
            )
            Text(
                text = "Follow",
                style = style1
            )
        }
    }
}

@Composable
fun CommunityFullPostContent(post: CommunityPost) {
    val style1 = TextStyle(
        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
        fontFamily = manRopeFontFamily,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.87f),
        textAlign = TextAlign.Start
    )
    val style2 = TextStyle(
        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
        fontFamily = manRopeFontFamily,
        fontWeight = FontWeight.W400,
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.87f),
        textAlign = TextAlign.Start
    )

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                bottom = MaterialTheme.spacing.avgSmall,
                start = MaterialTheme.spacing.extraSmall,
                end = MaterialTheme.spacing.extraSmall
            )
            .clip(RoundedCornerShape(MaterialTheme.spacing.lessSmall)),
        colors = CardDefaults.cardColors(containerColor = Primary500.copy(alpha = .1f)),
        shape = RoundedCornerShape(MaterialTheme.spacing.lessSmall)
    ) {
        Column(modifier = Modifier.padding(MaterialTheme.spacing.avgSmall)) {
            Text(
                text = post.postTitle ?: "--",
                style = style1
            )
            Text(
                modifier = Modifier.padding(top = MaterialTheme.spacing.medium),
                text = post.postContent ?: "Nothing to see here .... ",
                style = style2
            )
        }
    }
}

@Composable
fun CommunityFullPostLowerRow(post: CommunityPost) {
    val commentsCount = post.comments?.size ?: 0
    val style = TextStyle(
        fontSize = MaterialTheme.typography.bodySmall.fontSize,
        fontFamily = manRopeFontFamily,
        fontWeight = FontWeight.W400,
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.87f),
        textAlign = TextAlign.Start
    )

    AnimatedVisibility(visible = commentsCount != 0) {
        Row(
            modifier = Modifier
                .padding(
                    bottom = MaterialTheme.spacing.medSmall,
                    start = MaterialTheme.spacing.lessSmall,
                    end = MaterialTheme.spacing.lessSmall
                )
                .fillMaxWidth(),
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.End
        ) {
            Text(text = "$commentsCount Comments", style = style)
        }
    }
}

@Composable
fun CommunityFullPostLastRow(
    context: Context,
    currentPost: CommunityPost,
    isInternetAvailable: ConnectivityObserver.Status = ConnectivityObserver.Status.Available,
    viewModel: CommunityViewModel,
    postSharedViewModel: PostSharedViewModel,
    navController: NavController,
) {
    var likePainterId by remember { mutableIntStateOf(R.drawable.not_liked) }
    var painterIdText by remember { mutableStateOf(LIKE_OPTION) }

    LaunchedEffect(currentPost) {
        val (painterId, reactionText) = getReactionDetails(currentPost.myReaction?.reaction)
        likePainterId = painterId
        painterIdText = reactionText
    }

    val likePainter = painterResource(id = likePainterId)
    val commentPainter = painterResource(id = R.drawable.comment)
    val repostPainter = painterResource(id = R.drawable.repost)
    val sendPainter = painterResource(id = R.drawable.send)
    var pressOffset by remember { mutableStateOf(IntOffset.Zero) }
    var isReactionsVisible by remember { mutableStateOf(false) }
    var itemHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current
    val style = TextStyle(
        fontSize = MaterialTheme.typography.labelSmall.fontSize,
        fontFamily = workSansFontFamily,
        fontWeight = FontWeight.W500,
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.87f),
        textAlign = TextAlign.Start
    )

    val reactionsLazyList : ArrayList<CommunityPostReactions> = ArrayList()
    reactionsLazyList.add(CommunityPostReactions(likePainter, CommunityConstants.LIKE_PAINTER_CONTENT_DSC, painterIdText, style, ReactionOptions.LIKE))
    reactionsLazyList.add(CommunityPostReactions(commentPainter, CommunityConstants.COMMENT_PAINTER_CONTENT_DSC, CommunityConstants.COMMENT_OPTION, style, ReactionOptions.COMMENT))
    reactionsLazyList.add(CommunityPostReactions(repostPainter, CommunityConstants.REPOST_PAINTER_CONTENT_DSC, CommunityConstants.REPOST_OPTION, style, ReactionOptions.REPOST))
    reactionsLazyList.add(CommunityPostReactions(sendPainter, CommunityConstants.SEND_PAINTER_CONTENT_DSC, CommunityConstants.SEND_OPTION, style, ReactionOptions.SEND))

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .onSizeChanged {
                itemHeight = with(density) { it.height.toDp() }
            },
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalDivider(
            modifier = Modifier.padding(start = MaterialTheme.spacing.small, end = MaterialTheme.spacing.small, bottom = MaterialTheme.spacing.extraSmall),
            color = MaterialTheme.colorScheme.surface.copy(alpha = .08f)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MaterialTheme.spacing.extraSmall),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween, // TODO: icons not getting at the end - (SpaceAround) not working
            verticalAlignment = Alignment.CenterVertically
        ) {
            reactionsLazyList.forEach { item ->
                CommunityPostReactionOptions(
                    modifier = Modifier.weight(1f),
                    style = item.style.copy(fontWeight = if(likePainterId != R.drawable.not_liked && item.reactionOptions == ReactionOptions.LIKE) FontWeight.Bold else style.fontWeight),
                    painter = if(item.reactionOptions == ReactionOptions.LIKE) likePainter else item.painter,
                    contentDescription = item.cd,
                    imageText = item.imageText,
                    onSingleTap = { offSet ->
                        when(item.reactionOptions) {
                            ReactionOptions.LIKE -> {
                                if (isInternetAvailable == ConnectivityObserver.Status.Available) {
                                    val prevLikePainterId = R.drawable.not_liked
                                    likePainterId = if(likePainterId == prevLikePainterId){
                                        R.drawable.like_reaction
                                    } else{
                                        R.drawable.not_liked
                                    }
                                    val isLiked = likePainterId == R.drawable.like_reaction
                                    painterIdText = LIKE_OPTION
                                    viewModel.updatePostReaction(currentPost.postId, if(isLiked) LIKE_OPTION else "NULL")
                                } else {
                                    MedCommToast.showToast(context,
                                        CommunityConstants.CAN_NOT_REACT_NO_CONNECTION
                                    )
                                }
                            }
                            ReactionOptions.COMMENT -> {
                                // Don't do anything
                            }
                            ReactionOptions.REPOST -> {
                                if (isInternetAvailable == ConnectivityObserver.Status.Available) {
                                    fullPostInitLoad(postSharedViewModel, currentPost, navController, false, isRepost = true)
                                } else {
                                    MedCommToast.showToast(context,
                                        CommunityConstants.CAN_NOT_REPOST_NO_CONNECTION
                                    )
                                }
                            }
                            ReactionOptions.SEND -> {
                                if (isInternetAvailable == ConnectivityObserver.Status.Available) {
                                    // TODO:
                                } else {
                                    MedCommToast.showToast(context,
                                        CommunityConstants.CAN_NOT_SEND_NO_CONNECTION
                                    )
                                }
                            }
                        }
                    },
                    onLongPress = { offSet ->
                        if(item.reactionOptions == ReactionOptions.LIKE){
                            isReactionsVisible = true
                            pressOffset = IntOffset(offSet.x.toInt(), offSet.y.toInt())
                        }
                    }
                )
            }
        }
    }

    AnimatedVisibility(visible = isReactionsVisible) {
        ComposablePopReactionsRow(
            context = context,
            pressOffset = pressOffset,
            itemHeight = itemHeight,
            density = density,
            onDismiss = { isReactionsVisible = false },
            onReactionSelected = { reactionText, reactionTextPainter ->
                likePainterId = reactionTextPainter
                painterIdText = reactionText
                viewModel.updatePostReaction(currentPost.postId, reactionText)
            }
        )
    }
}

fun getReactionDetails(reaction: String?): Pair<Int, String> {
    return when (reaction) {
        ReactionPainters.Like.getLowercaseName() -> Pair(R.drawable.like_reaction, LIKE_OPTION)
        ReactionPainters.Love.getLowercaseName() -> Pair(R.drawable.love_reaction, LOVE_OPTION)
        ReactionPainters.Support.getLowercaseName() -> Pair(R.drawable.support_reaction, SUPPORT_OPTION)
        ReactionPainters.Celebrate.getLowercaseName() -> Pair(R.drawable.celebrate_reaction, CELEBRATE_OPTION)
        ReactionPainters.Funny.getLowercaseName() -> Pair(R.drawable.funny_reaction, FUNNY_OPTION)
        ReactionPainters.Insightful.getLowercaseName() -> Pair(R.drawable.insightful_reaction, INSIGHTFUL_OPTION)
        else -> Pair(R.drawable.not_liked, LIKE_OPTION)
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ComposableAllComments(navController: NavController, currentPostId: String?, allCommentsData: List<CommentData?>?) {
    val context = LocalContext.current
    val style = TextStyle(
        fontSize = MaterialTheme.typography.bodySmall.fontSize,
        fontFamily = manRopeFontFamily,
        fontWeight = FontWeight.W400,
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.87f),
        textAlign = TextAlign.Start
    )
    val annotatedStyle = SpanStyle(
        fontSize = MaterialTheme.typography.bodySmall.fontSize,
        fontFamily = workSansFontFamily,
        fontWeight = FontWeight.W400,
        color = Primary500
    )

    LazyColumn(modifier = Modifier.fillMaxWidth()) {
        items(allCommentsData ?: emptyList()) { comment ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = MaterialTheme.spacing.extraSmall,
                        vertical = MaterialTheme.spacing.small
                    ),
                verticalAlignment = Alignment.Top
            ) {
                val commentId = comment?.commentId
                Image(
                    modifier = Modifier
                        .padding(top = MaterialTheme.spacing.avgLessSmall)
                        .size(MaterialTheme.size.defaultIconSize),
                    painter = painterResource(id = R.drawable.doctor_profile_ph),
                    contentDescription = "profile icon",
                )

                Card(
                    colors = CardDefaults.cardColors(containerColor = Primary500.copy(alpha = .1f)),
                    modifier = Modifier.padding(start = MaterialTheme.spacing.extraSmall),
                    shape = RoundedCornerShape(MaterialTheme.spacing.small)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(MaterialTheme.spacing.avgLessSmall),
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(bottom = MaterialTheme.spacing.extraSmall)
                                .basicMarquee(Int.MAX_VALUE),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Row(modifier = Modifier.weight(1f), verticalAlignment = Alignment.CenterVertically) {
                                Text(
                                    text = comment?.commentedByUserName ?: "--",
                                    style = style.copy(fontWeight = FontWeight.W500, fontSize = MaterialTheme.typography.bodyMedium.fontSize)
                                )

                                Text(
                                    modifier = Modifier.padding(MaterialTheme.spacing.smallest),
                                    text = " - ",
                                    style = style.copy(fontWeight = FontWeight.W400)
                                )

                                Text(
                                    text = comment?.commentTime ?: "--",
                                    style = style
                                )
                            }

                            Icon(
                                modifier = Modifier.size(MaterialTheme.size.defaultIconSize),
                                imageVector = Icons.Default.MoreVert,
                                contentDescription = "",
                                tint = Color.Unspecified
                            )
                        }

                        Row(
                            modifier = Modifier.padding(bottom = MaterialTheme.spacing.small)
                        ) {
                            Text(
                                text = comment?.commentContent ?: "--",
                                style = style.copy(fontWeight = FontWeight.W500)
                            )
                        }

                        Row(
                            modifier = Modifier.padding(bottom = MaterialTheme.spacing.small)
                        ) {
                            AnimatedVisibility(
                                visible = !comment?.likes.isNullOrEmpty(),
                                modifier = Modifier.padding(end = MaterialTheme.spacing.medSmall)
                            ){
                                ClickableLikeAndReply(
                                    context = context,
                                    currentPostId = currentPostId,
                                    commentId = commentId,
                                    navController = navController,
                                    annotatedStyle = annotatedStyle,
                                    isReply = false,
                                    clickableTagTextAnnotation = "Likes",
                                    clickableText = (comment?.likes?.size ?: "").toString(),
                                )
                            }

                            AnimatedVisibility(
                                visible = !comment?.replies.isNullOrEmpty(),
                                modifier = Modifier.padding(end = MaterialTheme.spacing.medSmall)
                            ){
                                ClickableLikeAndReply(
                                    context = context,
                                    currentPostId = currentPostId,
                                    commentId = commentId,
                                    navController = navController,
                                    annotatedStyle = annotatedStyle,
                                    isReply = true,
                                    clickableTagTextAnnotation = "Replies",
                                    clickableText = (comment?.replies?.size ?: "").toString(),
                                )
                            }

                            AnimatedVisibility(visible = true){
                                ClickableLikeAndReply(
                                    context = context,
                                    currentPostId = currentPostId,
                                    commentId = commentId,
                                    navController = navController,
                                    annotatedStyle = annotatedStyle,
                                    isReply = false,
                                    clickableTagTextAnnotation = "Reply",
                                    clickableText = "",
                                    showReplyBox = { MedCommToast.showToast(context, "Coming soon...") }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ClickableLikeAndReply(
    context: Context,
    currentPostId: String?,
    commentId: String?,
    navController: NavController,
    annotatedStyle: SpanStyle,
    isReply: Boolean,
    clickableTagTextAnnotation: String,
    clickableText: String,
    showReplyBox: () -> Unit = {}
) {
    val replyEnabled = clickableTagTextAnnotation == "Reply"
    ClickableText(
        text = buildAnnotatedString {
            withStyle(style = if(isReply && !replyEnabled) annotatedStyle.copy(textDecoration = TextDecoration.Underline) else annotatedStyle) {
                append(clickableTagTextAnnotation)
            }
            pushStringAnnotation(tag = clickableTagTextAnnotation, annotation = clickableTagTextAnnotation)
            if(!replyEnabled){
                withStyle(style = annotatedStyle.copy(fontWeight = FontWeight.W600)) {
                    append(" . $clickableText")
                }
            }
            pop()
        },
        onClick = {
            // TODO: create a new screen with nested indices to show replies on a comment
            if(isReply){
                navController.navigate(CommunityAppScreens.CommentReplyFullScreen.route + "/$currentPostId" + "/$commentId")
            }
            else if(replyEnabled){
                showReplyBox()
            }
            else{
                MedCommToast.showToast(context, "Coming soon!!!")
            }
        }
    )
}

@Preview
@Composable
fun CommentsScreenPreview() {
//    Surface(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(id = R.drawable.celebrate_reaction),
            contentDescription = "reaction",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier
                .size(MaterialTheme.size.medSmall)
                .background(Color.Black)
                .clip(CircleShape)
        )
//    }
}