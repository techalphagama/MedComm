package com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.all_posts_screen

import android.content.Context
import android.view.MotionEvent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.PersonAdd
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.pointerInteropFilter
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.alpharays.mymedicommfma.R
import com.alpharays.mymedicommfma.common.connectivity.ConnectivityObserver
import com.alpharays.mymedicommfma.communityv2.MedCommRouter.DEFAULT_POST_BODY_LIMIT
import com.alpharays.mymedicommfma.communityv2.MedCommRouter.EMPTY
import com.alpharays.mymedicommfma.communityv2.MedCommRouter.NO_CONNECTION
import com.alpharays.mymedicommfma.communityv2.MedCommRouter.READ_FULL_POST
import com.alpharays.mymedicommfma.communityv2.MedCommRouter.READ_MORE_BODY_TAG
import com.alpharays.mymedicommfma.communityv2.MedCommToast
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.CAN_NOT_COMMENT_NO_CONNECTION
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.CAN_NOT_REACT_NO_CONNECTION
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.CAN_NOT_REPOST_NO_CONNECTION
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.CAN_NOT_SEND_NO_CONNECTION
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.CELEBRATE_OPTION
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.COMMENT_OPTION
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.COMMENT_PAINTER_CONTENT_DSC
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.FUNNY_OPTION
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.INSIGHTFUL_OPTION
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.LIKE_OPTION
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.LIKE_PAINTER_CONTENT_DSC
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.LOVE_OPTION
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.REPOST_OPTION
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.REPOST_PAINTER_CONTENT_DSC
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.SEND_OPTION
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.SEND_PAINTER_CONTENT_DSC
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.SUPPORT_OPTION
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityUtils
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityUtils.Companion.ComposableNoNetworkFound
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.all_comm_posts.CommunityPost
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.common.custom_top_appbar.CustomTopAppBar
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.common.sidedrawer.ModalNavigationDrawerScreen
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.CommunityViewModel
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.PostSharedViewModel
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.navigation.CommunityAppScreens
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.BluishGray
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.FocusedTextColor
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.OnSurfaceHighEmphasis
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.Primary100
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.Primary500
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.Primary600
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.manRopeFontFamily
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.size
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.spacing
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.workSansFontFamily
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommunityScreen(
    navController: NavController,
    communityViewModel: CommunityViewModel = hiltViewModel(),
    postSharedViewModel: PostSharedViewModel = hiltViewModel()
) {
    // drawerState remember variable
    val drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val context = LocalContext.current
    val pullRefreshState = rememberPullToRefreshState()

    val isRefreshing by communityViewModel.isRefreshing.collectAsStateWithLifecycle()

    // check if pullRefresh is refreshing or not - on each refresh, hit the api if network status is available
    LaunchedEffect(pullRefreshState.isRefreshing) {
        if (pullRefreshState.isRefreshing && isRefreshing == false) {
            communityViewModel.refresh()
        }
    }

    LaunchedEffect(isRefreshing) {
        isRefreshing?.let {
            if (it) {
                pullRefreshState.startRefresh()
            }
            else  {
                pullRefreshState.endRefresh()
            }
        }
    }

    // collect all the community posts
    val allPostsState by communityViewModel.allCommunityPostsStateFlow.collectAsStateWithLifecycle()
    val communityPostsList = allPostsState.data?.allPosts ?: emptyList()

    // collect network status
    val networkStatus by communityViewModel.appNetworkStatus.collectAsState(
        /**
         * does not matter what we set as initial network status - not atleast in our usecase
         */
        initial = if (communityViewModel.currentNetworkStatus) ConnectivityObserver.Status.Available else ConnectivityObserver.Status.Unavailable
    )

    ModalNavigationDrawer(
        modifier = Modifier,
        drawerState = drawerState,
        content = {
            Scaffold(
                modifier = Modifier
                    .navigationBarsPadding()
                    .imePadding()
                    .fillMaxSize(),
                topBar = {
                    CustomTopAppBar(drawerState = drawerState, showLogo = true, navController = navController)
                },
                containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = .95f)
            ) { innerPadding ->
                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                        .nestedScroll(pullRefreshState.nestedScrollConnection)
                ) {
                    ComposableCommunityPosts(
                        navController = navController,
                        networkStatus = networkStatus,
                        communityViewModel = communityViewModel,
                        postSharedViewModel = postSharedViewModel,
                        communityPostsList = communityPostsList
                    )
                    PullToRefreshContainer(
                        state = pullRefreshState,
                        modifier = Modifier.align(Alignment.TopCenter)
                    )
                }
            }
        },
        drawerContent = {
            ModalNavigationDrawerScreen(
                isLoggedIn = false,
                navController = navController,
                onSign = { },
                onSignOut = { }
            )
        }
    )
}

@Composable
fun ComposableCommunityPosts(
    navController: NavController,
    networkStatus: ConnectivityObserver.Status,
    communityPostsList: List<CommunityPost>,
    communityViewModel: CommunityViewModel,
    postSharedViewModel: PostSharedViewModel,
) {
    val context = LocalContext.current
    val cardBorderBrush = Brush.sweepGradient(colors = listOf(BluishGray, Color.Transparent, BluishGray, Color.Transparent))
//    val cardBorderBrush = Brush.linearGradient(colors = listOf(BluishGray, Color.Transparent, Color.Transparent, BluishGray))

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                vertical = MaterialTheme.spacing.avgSmall,
                horizontal = MaterialTheme.spacing.extraSmall
            ),
        contentAlignment = Alignment.TopCenter
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            itemsIndexed(communityPostsList) { index, currentPost ->
                Column(
                    modifier = Modifier.padding(bottom = MaterialTheme.spacing.small),
                    verticalArrangement = Arrangement.SpaceBetween,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = MaterialTheme.spacing.extraSmall)
                            .border(
                                1.dp,
                                cardBorderBrush,
                                RoundedCornerShape(MaterialTheme.size.small)
                            ),
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 1f)
                        ),
                        shape = RoundedCornerShape(MaterialTheme.size.small)
                    ) {
                        Column(
                            modifier = Modifier.padding(MaterialTheme.spacing.extraSmall),
                            verticalArrangement = Arrangement.SpaceBetween,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            PostHeader(
                                context = context,
                                posterName = currentPost.posterName ?: "",
                                aboutPoster = currentPost.aboutDoc ?: "",
                                onFollow = {}
                            )

                            PostBody(
                                context = context,
                                currentPost = currentPost,
                                viewModel = postSharedViewModel,
                                navController = navController,
                                networkStatus = networkStatus
                            )

                            PostCommentsFooter(
                                currentPost = currentPost,
                                navController = navController,
                                viewModel = postSharedViewModel
                            )

                            PostFooter(
                                context = context,
                                viewModel = communityViewModel,
                                navController = navController,
                                postSharedViewModel = postSharedViewModel,
                                networkStatus = networkStatus,
                                currentPost = currentPost,
                            )
                        }
                    }

                    AnimatedVisibility(visible = index != communityPostsList.size - 1) {
                        HorizontalDivider(
                            modifier = Modifier
                                .padding(horizontal = MaterialTheme.spacing.extraSmall)
                                .clip(RoundedCornerShape(MaterialTheme.spacing.avgExtraSmall)),
                            thickness = MaterialTheme.spacing.avgExtraSmall,
                            color = OnSurfaceHighEmphasis.copy(alpha = .2f)
                        )
                    }
                }
            }
        }

        AnimatedVisibility(visible = networkStatus == ConnectivityObserver.Status.Unavailable) {
            ComposableNoNetworkFound(
                context = context,
                networkStatus = networkStatus,
                modifier = Modifier,
                viewModel = communityViewModel
            )
        }

        LaunchedEffect(key1 = networkStatus) {
            if (networkStatus == ConnectivityObserver.Status.Lost) {
                MedCommToast.showToast(context, NO_CONNECTION)
            }
        }

        Icon(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(MaterialTheme.spacing.small)
                .size(MaterialTheme.size.large2)
                .background(
                    Primary100.copy(alpha = .5f),
                    RoundedCornerShape(MaterialTheme.spacing.medSmall)
                )
                .clip(RoundedCornerShape(MaterialTheme.spacing.medSmall))
                .border(0.5.dp, Color.Black, RoundedCornerShape(MaterialTheme.spacing.medSmall))
                .clickable {
                    navController.navigate(CommunityAppScreens.AddNewCommunityPostScreen.route) {
                        // TODO: handle nav backstack in whole app : NO IDEA, how to deal with backstack in JP
                        launchSingleTop = true
                    }
                },
            imageVector = Icons.Default.Add,
            contentDescription = "Add new community currentPost",
            tint = Color.Blue
        )
    }
}

@Composable
fun PostHeader(
    context: Context,
    posterName: String,
    aboutPoster: String,
    onFollow: () -> Unit
) {
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
                text = posterName,
                style = style1
            )

            Text(
                modifier = Modifier.padding(start = MaterialTheme.spacing.extraSmall),
                text = aboutPoster,
                maxLines = 1,
                style = style2
            )
        }

        Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier.clickable {
                onFollow()
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
fun PostBody(
    context: Context,
    currentPost: CommunityPost,
    viewModel: PostSharedViewModel,
    navController: NavController?,
    networkStatus: ConnectivityObserver.Status,
) {
    val color = CommunityUtils.getMedicoColor(context, R.color.bluish_gray)
//    val interactionSource by remember { mutableStateOf(MutableInteractionSource()) }
//    val ripple = rememberRipple(bounded = false, radius = 0.dp, color = Color(color))
    val style = TextStyle(
        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
        fontFamily = manRopeFontFamily,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.87f),
        textAlign = TextAlign.Start
    )
    val bodySpanStyle = SpanStyle(
        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
        fontFamily = manRopeFontFamily,
        fontWeight = FontWeight.W400,
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.87f)
    )
    val annotationSpanStyle = SpanStyle(
        color = Primary600,
        fontWeight = FontWeight.Medium,
        fontSize = MaterialTheme.typography.bodySmall.fontSize,
        fontFamily = manRopeFontFamily
    )

    val lengthCondition = (currentPost.postContent?.length ?: 0) > DEFAULT_POST_BODY_LIMIT
    var showFullPost by remember { mutableStateOf(!lengthCondition) }
    var readMore by remember {
        mutableStateOf(if (lengthCondition) READ_FULL_POST else EMPTY)
    }
    val string = if (showFullPost) currentPost.postContent else currentPost.postContent?.take(70)
    val annotatedText = buildAnnotatedString {
        withStyle(style = bodySpanStyle) {
            append(string)
        }
        pushStringAnnotation(tag = READ_MORE_BODY_TAG, annotation = READ_MORE_BODY_TAG)
        withStyle(style = annotationSpanStyle) {
            append(readMore)
        }
        pop()
    }

    Card(
        modifier = Modifier
            .pointerInput(true) {
                detectTapGestures(
                    onTap = {
                        if (networkStatus == ConnectivityObserver.Status.Available) {
                            setupFullScreenView(viewModel, currentPost, navController, false)
                        } else {
                            MedCommToast.showToast(context, NO_CONNECTION)
                        }
                    }
                )
            }
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
                text = currentPost.postTitle ?: "--",
                style = style
            )
            ClickableText(
                modifier = Modifier.padding(top = MaterialTheme.spacing.medium),
                text = annotatedText,
                onClick = { _ ->
                    if(readMore.isNotEmpty() && !showFullPost){
                        showFullPost = true
                        readMore = ""
                    }
                    else{
                        if (networkStatus == ConnectivityObserver.Status.Available) {
                            setupFullScreenView(viewModel, currentPost, navController, false)
                        } else {
                            MedCommToast.showToast(context, NO_CONNECTION)
                        }
                    }
                }
            )
        }
    }
}

@Composable
fun PostCommentsFooter(
    currentPost: CommunityPost,
    navController: NavController,
    viewModel: PostSharedViewModel
) {
    val reactionsCount = currentPost.reactions?.run {
        listOf(like, love, celebrate, support, insightful, funny).sumOf { it?.size ?: 0 }
    } ?: 0
    val commentsCount = currentPost.comments?.size
    val style = TextStyle(
        fontSize = MaterialTheme.typography.bodySmall.fontSize,
        fontFamily = manRopeFontFamily,
        fontWeight = FontWeight.W400,
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.87f),
        textAlign = TextAlign.Start
    )

    AnimatedVisibility(visible = reactionsCount != 0 || commentsCount != 0) {
        Row(
            modifier = Modifier.padding(
                bottom = MaterialTheme.spacing.medSmall,
                start = MaterialTheme.spacing.lessSmall,
                end = MaterialTheme.spacing.lessSmall
            ),
            verticalAlignment = Alignment.Top
        ) {
            AnimatedVisibility(visible = reactionsCount != 0) {
                ClickableTextNavigateToFullScreen(
                    text = "$reactionsCount Reactions",
                    style = style,
                    currentPost = currentPost,
                    navController = navController,
                    isComment = false,
                    viewModel = viewModel,
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            AnimatedVisibility(visible = commentsCount != 0) {
                ClickableTextNavigateToFullScreen(
                    text = "$commentsCount Comments",
                    style = style,
                    currentPost = currentPost,
                    navController = navController,
                    isComment = true,
                    viewModel = viewModel,
                )
            }
        }
    }
}

@Composable
fun ClickableTextNavigateToFullScreen(
    text: String,
    style: TextStyle,
    currentPost: CommunityPost,
    navController: NavController,
    isComment: Boolean,
    viewModel: PostSharedViewModel,
) {
    ClickableText(
        text = AnnotatedString(text),
        style = style,
        onClick = {
            setupFullScreenView(viewModel, currentPost, navController, isComment)
        }
    )
}

fun setupFullScreenView(
    viewModel: PostSharedViewModel,
    currentPost: CommunityPost,
    navController: NavController?,
    isComment: Boolean,
    isRepost: Boolean = false,
) {
    // TODO: if no connection, then user is not able to go to full screen view of currentPost - fix it later
    viewModel.setCurrentPostState(currentPost) // store current clicked currentPost in datastore
    val route = if(!isRepost) {
        CommunityAppScreens.CommunityFullPostScreen.route + "/${currentPost.postId}" + "/$isComment"
    }
    else {
        CommunityAppScreens.CommunityRepostScreen.route
    }
    navController?.navigate(route)
}

@Composable
fun PostFooter(
    context: Context,
    currentPost: CommunityPost,
    networkStatus: ConnectivityObserver.Status = ConnectivityObserver.Status.Available,
    showCommentsAgain: Boolean = true,
    navController: NavController,
    viewModel: CommunityViewModel,
    postSharedViewModel: PostSharedViewModel
) {
    val userCurrentReaction = currentPost.myReaction
    var (currentPainterId, painterIdText) = when (userCurrentReaction?.reaction) {
        ReactionPainters.Like.getLowercaseName() -> {
            Pair(ReactionPainters.Like.getReactionPainterId(), LIKE_OPTION)
        }

        ReactionPainters.Love.getLowercaseName() -> {
            Pair(ReactionPainters.Love.getReactionPainterId(), LOVE_OPTION)
        }

        ReactionPainters.Support.getLowercaseName() -> {
            Pair(ReactionPainters.Support.getReactionPainterId(), SUPPORT_OPTION)
        }

        ReactionPainters.Celebrate.getLowercaseName() -> {
            Pair(ReactionPainters.Celebrate.getReactionPainterId(), CELEBRATE_OPTION)
        }

        ReactionPainters.Funny.getLowercaseName() -> {
            Pair(ReactionPainters.Funny.getReactionPainterId(), FUNNY_OPTION)
        }

        ReactionPainters.Insightful.getLowercaseName() -> {
            Pair(ReactionPainters.Insightful.getReactionPainterId(), INSIGHTFUL_OPTION)
        }

        else -> {
            Pair(R.drawable.not_liked, LIKE_OPTION)
        }
    }
    val likePainter = painterResource(id = currentPainterId)
    val commentPainter = painterResource(id = R.drawable.comment)
    val repostPainter = painterResource(id = R.drawable.repost)
    val sendPainter = painterResource(id = R.drawable.send)
    var pressOffset by remember { mutableStateOf(IntOffset.Zero) }
    var showAllReactions by remember { mutableStateOf(false) }
    var itemHeight by remember { mutableStateOf(0.dp) }
    val density = LocalDensity.current
    val style = TextStyle(
        fontSize = MaterialTheme.typography.labelSmall.fontSize,
        fontFamily = workSansFontFamily,
        fontWeight = FontWeight.W500,
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.87f),
        textAlign = TextAlign.Start
    )

    val reactionsLazyList by remember {
        val initialList = listOf(
            CommunityPostReactions(likePainter, LIKE_PAINTER_CONTENT_DSC, painterIdText, style, ReactionOptions.LIKE),
            CommunityPostReactions(commentPainter, COMMENT_PAINTER_CONTENT_DSC, COMMENT_OPTION, style, ReactionOptions.COMMENT),
            CommunityPostReactions(repostPainter, REPOST_PAINTER_CONTENT_DSC, REPOST_OPTION, style, ReactionOptions.REPOST),
            CommunityPostReactions(sendPainter, SEND_PAINTER_CONTENT_DSC, SEND_OPTION, style, ReactionOptions.SEND)
        )
        mutableStateOf(initialList)
    }

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
            modifier = Modifier.padding(
                start = MaterialTheme.spacing.small,
                end = MaterialTheme.spacing.small,
                bottom = MaterialTheme.spacing.extraSmall
            ),
            color = MaterialTheme.colorScheme.surface.copy(alpha = .08f)
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = MaterialTheme.spacing.extraSmall),
            horizontalArrangement = Arrangement.Absolute.SpaceBetween, // TODO: icons not getting at the end - (SpaceAround) not working
            verticalAlignment = Alignment.CenterVertically
        ) {
            reactionsLazyList.forEach{ item ->
                CommunityPostReactionOptions(
                    modifier = Modifier.weight(1f),
                    style = item.style.copy(fontWeight = if(currentPainterId != R.drawable.not_liked && item.reactionOptions == ReactionOptions.LIKE) FontWeight.Bold else style.fontWeight),
                    painter = if(item.reactionOptions == ReactionOptions.LIKE) likePainter else item.painter,
                    contentDescription = item.cd,
                    imageText = item.imageText,
                    onSingleTap = { offSet ->
                        when(item.reactionOptions) {
                            ReactionOptions.LIKE -> {
                                if (networkStatus == ConnectivityObserver.Status.Available) {
                                    val prevLikePainterId = R.drawable.not_liked
                                    currentPainterId = if(currentPainterId == prevLikePainterId) {
                                        R.drawable.like_reaction
                                    }
                                    else {
                                        R.drawable.not_liked
                                    }
                                    val isLiked = currentPainterId == R.drawable.like_reaction
                                    painterIdText = LIKE_OPTION
                                    viewModel.updatePostReaction(currentPost.postId, if(isLiked) LIKE_OPTION else "NULL")
                                } else {
                                    MedCommToast.showToast(context, CAN_NOT_REACT_NO_CONNECTION)
                                }
                            }
                            ReactionOptions.COMMENT -> {
                                if (networkStatus == ConnectivityObserver.Status.Available) {
                                    if (showCommentsAgain) {
                                        setupFullScreenView(postSharedViewModel, currentPost, navController, true)
                                    }
                                } else {
                                    MedCommToast.showToast(context, CAN_NOT_COMMENT_NO_CONNECTION)
                                }
                            }
                            ReactionOptions.REPOST -> {
                                if (networkStatus == ConnectivityObserver.Status.Available) {
                                    setupFullScreenView(postSharedViewModel, currentPost, navController, false, isRepost = true)
                                } else {
                                    MedCommToast.showToast(context, CAN_NOT_REPOST_NO_CONNECTION)
                                }
                            }
                            ReactionOptions.SEND -> {
                                if (networkStatus == ConnectivityObserver.Status.Available) {
                                    MedCommToast.showToast(context, "Coming soon...")
                                } else {
                                    MedCommToast.showToast(context, CAN_NOT_SEND_NO_CONNECTION)
                                }
                            }
                        }
                    },
                    onLongPress = { offSet ->
                        if(item.reactionOptions == ReactionOptions.LIKE) {
                            showAllReactions = true
                            pressOffset = IntOffset(offSet.x.toInt(), offSet.y.toInt())
                        }
                    }
                )
            }
        }
    }

    AnimatedVisibility(visible = showAllReactions) {
        AllReactionsPopUp(
            pressOffset = pressOffset,
            itemHeight = itemHeight,
            density = density,
            onDismiss = { showAllReactions = false },
            onReactionSelected = { reactionText, reactionTextPainter ->
                currentPainterId = reactionTextPainter
                painterIdText = reactionText
                viewModel.updatePostReaction(currentPost.postId, reactionText)
            }
        )
    }
}

enum class ReactionOptions {
    LIKE, COMMENT, REPOST, SEND
}

data class CommunityPostReactions(
    val painter: Painter,
    val cd: String,
    val imageText: String,
    val style: TextStyle,
    val reactionOptions: ReactionOptions
)

@Composable
fun CommunityPostReactionOptions(
    modifier : Modifier,
    style: TextStyle,
    painter: Painter,
    contentDescription: String,
    imageText: String,
    onSingleTap: (Offset) -> Unit = {},
    onLongPress: (Offset) -> Unit = {},
) {
    Column(
        modifier = modifier
            .padding(
                horizontal = MaterialTheme.spacing.medSmall,
                vertical = MaterialTheme.spacing.lessSmall
            )
            .pointerInput(true) { // TODO: need to figure out what does key1 in pointerInput means?
                detectTapGestures(
                    onLongPress = { offset ->
                        onLongPress(offset)
                    },
                    onTap = { offset ->
                        onSingleTap(offset)
                    }
                )
            },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            modifier = Modifier
                .padding(bottom = MaterialTheme.spacing.extraSmall)
                .size(MaterialTheme.size.smallestIconSize),
            painter = painter,
            contentDescription = contentDescription
        )
        Box(contentAlignment = Alignment.Center) {
            Text(text = "INSIGHTFUL", style = style.copy(color = Color.Transparent))
            Text(text = imageText, style = style)
        }
    }
}

@Composable
fun ComposableExpandedComment(focusRequester: FocusRequester, onDismiss: () -> Unit) {
    var userComment by remember { mutableStateOf("") }
    val painter = painterResource(id = R.drawable.doctor_profile_ph)
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current
    val style = TextStyle(
        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
        fontFamily = manRopeFontFamily,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.87f),
        textAlign = TextAlign.Start
    )

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }

    AnimatedVisibility(
        visible = true,
        enter = expandVertically(), // Animation for entering
        exit = shrinkVertically() // Animation for exiting
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 4.dp, vertical = 2.dp)
                .fillMaxWidth()
                .border(1.dp, Color.LightGray, RoundedCornerShape(10.dp))
                .padding(horizontal = 6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(35.dp)
                    .border(0.5.dp, Color.Gray, RoundedCornerShape(50.dp)),
                painter = painter,
                contentDescription = "CD"
            )

            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .imePadding()
                    .focusRequester(focusRequester),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent
                ),
                value = userComment,
                onValueChange = { newText ->
                    userComment = newText
                },
                placeholder = {
                    Text(text = "Leave your thoughts here...", style = style)
                },
                textStyle = style.copy(color = FocusedTextColor),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                    focusManager.clearFocus()
                })
            )
        }
    }
}

enum class ReactionPainters(private val drawableId: Int, private val painterName: String) {
    Like(R.drawable.like_reaction, LIKE_OPTION),
    Love(R.drawable.love_reaction, LOVE_OPTION),
    Celebrate(R.drawable.celebrate_reaction, CELEBRATE_OPTION),
    Support(R.drawable.support_reaction, SUPPORT_OPTION),
    Insightful(R.drawable.insightful_reaction, INSIGHTFUL_OPTION),
    Funny(R.drawable.funny_reaction, FUNNY_OPTION);

    fun getReactionPainterId(): Int {
        return drawableId
    }

    fun getReactionPainterName(): String {
        return painterName
    }

    fun getLowercaseName(): String {
        return getReactionPainterName().replaceFirstChar { it.lowercase() }
    }
}

@Composable
fun AllReactionsPopUp(
    pressOffset: IntOffset,
    itemHeight: Dp,
    density: Density,
    onReactionSelected : (String, Int) -> Unit,
    onDismiss: () -> Unit,
) {
    val reactionPainters = ReactionPainters.entries
//    val borderColor = Color(0xFFCECECE)
//    val shape = RoundedCornerShape(MaterialTheme.spacing.small)
//    val marginAboveIcon = MaterialTheme.size.large2
    val itemHeightPx = with(density) { itemHeight.roundToPx() }
//    val marginAboveIconPx = with(density) { marginAboveIcon.roundToPx() }
    val yOffset = pressOffset.y + itemHeightPx //+ marginAboveIconPx
//    val cardBorderBrush = Brush.sweepGradient(colors = listOf(BluishGray, Color.Transparent, BluishGray, Color.Transparent))

    Popup(
        onDismissRequest = onDismiss,
        offset = IntOffset(pressOffset.x, yOffset),
        properties = PopupProperties(
            focusable = true,
            dismissOnClickOutside = true,
            dismissOnBackPress = true,
            excludeFromSystemGesture = true
        ),
    ) {
        Card(
            modifier = Modifier
                .padding(horizontal = MaterialTheme.spacing.small)
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.small),
            elevation = CardDefaults.cardElevation(defaultElevation = MaterialTheme.spacing.extraSmall),
            border = BorderStroke(MaterialTheme.spacing.oneDp, BluishGray.copy(alpha = .6f))
        ){
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 1f),
                        RoundedCornerShape(MaterialTheme.spacing.small)
                    )
                    .padding(MaterialTheme.spacing.medSmall)
                    .clip(RoundedCornerShape(MaterialTheme.spacing.small)),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                reactionPainters.forEachIndexed { index, painterId ->
                    AnimatedVisibilityWithDelay(
                        painterId = painterId.getReactionPainterId(),
                        index = index,
                        onComplete = {
                            onReactionSelected(painterId.getReactionPainterName(), painterId.getReactionPainterId())
                            onDismiss()
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AnimatedVisibilityWithDelay(
    painterId: Int,
    index: Int,
    onComplete: () -> Unit
) {
    // Define animation specs for enter animation
    val enterAnimation = slideInHorizontally(initialOffsetX = { -20 }) + fadeIn()
    val exitAnimation = slideOutHorizontally(targetOffsetX = { -20 }) + fadeOut()

    // Use AnimatedVisibility for each image
    var isPressed by remember { mutableStateOf(false) }
    val scale by animateFloatAsState(targetValue = if (isPressed) 1.5f else 1f, label = "")

    LaunchedEffect(key1 = Unit) {
        delay(index * 3L) // Staggered delay, adjust the multiplier as needed
    }

    AnimatedVisibility(
        visible = true,
        enter = enterAnimation,
        exit = exitAnimation
    ) {
        Image(
            painter = painterResource(id = painterId),
            modifier = Modifier
                .wrapContentSize(unbounded = true)
                .graphicsLayer {
                    scaleX = scale
                    scaleY = scale
                }
                .size(MaterialTheme.size.mediumIconSize)
                .animateContentSize() // Animate size changes
                .pointerInteropFilter { motionEvent ->
                    when (motionEvent.action) {
                        MotionEvent.ACTION_DOWN -> {
                            // User has started pressing
                            isPressed = true
                        }

                        MotionEvent.ACTION_UP, MotionEvent.ACTION_CANCEL -> {
                            // User has stopped pressing
                            isPressed = false
                            onComplete()
                        }
                    }
                    true
                },
            contentDescription = null
        )
    }
}