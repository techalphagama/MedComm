package com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.new_post_screen

import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.alpharays.mymedicommfma.common.connectivity.ConnectivityObserver
import com.alpharays.mymedicommfma.communityv2.MedCommToast
import com.alpharays.mymedicommfma.communityv2.community_app.community_utils.CommunityConstants.COMMUNITY_SCREEN_ROUTE
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.newpost.AddNewCommunityPost
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.CommunityViewModel
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.BluishGray
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.FocusedTextColor
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.Primary200
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.Primary400
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.manRopeFontFamily
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.size
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.theme.spacing

@Composable
fun AddNewCommunityPostScreen(
    navController: NavController,
    repostTitle: String = "",
    repostContent : String = "",
    communityViewModel: CommunityViewModel = hiltViewModel(),
) {
    val isInternetAvailable = ConnectivityObserver.Status.Available
//    val isInternetAvailable by communityViewModel.networkStatus.collectAsStateWithLifecycle()
    var postTitle by remember { mutableStateOf(repostTitle) }
    var postContent by remember { mutableStateOf(repostContent) }
    val color = Color(0xFFF5F6FF)
    var postImageSelected by remember { mutableStateOf<Bitmap?>(null) }
    val width = LocalConfiguration.current.screenWidthDp.dp
    val height = LocalConfiguration.current.screenHeightDp.dp / 2

    Scaffold(
        modifier = Modifier.navigationBarsPadding().fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 1f),
        topBar = {
            ComposableNewPostTopBar(
                navController = navController,
                communityViewModel = communityViewModel,
                postTitle = postTitle,
                postContent = postContent,
                isInternetAvailable = isInternetAvailable
            )
        },
        bottomBar = {
            ComposableNewPostBottomBar {
                postImageSelected = it
            }
        }
    ) { paddingValues ->
        Surface(
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 1f),
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(
                    horizontal = MaterialTheme.spacing.medium,
                    vertical = MaterialTheme.spacing.avgSmall
                )
        ) {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(2) {
                    ComposablePostOutlinedTextField(it == 0) { textValue ->
                        if (it == 0) postTitle = textValue
                        else postContent = textValue
                    }
                }
                item {
                    Box(modifier = Modifier
                        .widthIn(MaterialTheme.size.default, width)
                        .heightIn(MaterialTheme.size.default, height)){
                        postImageSelected?.let {
                            Image(
                                modifier = Modifier.fillMaxSize(),
                                painter = BitmapPainter(it.asImageBitmap()),
                                contentDescription = "select post image",
                            contentScale = ContentScale.Fit
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ComposablePostOutlinedTextField(
    isTitle: Boolean,
    onPostTextField: (String) -> Unit,
) {
    val textColor0 = Color(0xFF006372)
    val textColor = Color(0xFF003D46)
    val postHeadingLabel = if (isTitle) "Add a title" else "Add a description"
    var postInputField by remember { mutableStateOf("") }
    val color = MaterialTheme.colorScheme.onPrimary
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    val style = if (isTitle) {
        TextStyle(
            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
            fontWeight = FontWeight.W400,
            color = FocusedTextColor,
            fontFamily = manRopeFontFamily
        )
    } else {
        TextStyle(
            fontSize = MaterialTheme.typography.bodySmall.fontSize,
            fontWeight = FontWeight.Normal,
            color = FocusedTextColor,
            fontFamily = manRopeFontFamily
        )
    }

    OutlinedTextField(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = MaterialTheme.spacing.small)
            .imePadding(),
        value = postInputField,
        onValueChange = { newText ->
            postInputField = newText
            onPostTextField(postInputField)
        },
        label = {
            Text(text = postHeadingLabel, style = style)
        },
        textStyle = TextStyle(
            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
            color = color,
            fontFamily = manRopeFontFamily
        ),
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = BluishGray,
            unfocusedBorderColor = Color.LightGray,
        ),
        keyboardActions = KeyboardActions(onDone = {
            keyboardController?.hide()
            focusManager.clearFocus()
        })
    )
}

@Composable
fun ComposableNewPostTopBar(
    navController: NavController,
    postTitle: String,
    postContent: String,
    communityViewModel: CommunityViewModel,
    isInternetAvailable: ConnectivityObserver.Status?,
) {
    val response by communityViewModel.addNewCommunityPostStateFlow.collectAsStateWithLifecycle()
    var postCreated by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val interactionSource = remember { MutableInteractionSource() }
    val color = Color(0xFF71FFEC)
    val ripple = rememberRipple(bounded = false, radius = 24.dp, color = color)
    val style = TextStyle(
        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
        fontFamily = manRopeFontFamily,
        fontWeight = FontWeight.W600,
        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.87f),
    )

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(Primary400),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp),
        shape = RoundedCornerShape(MaterialTheme.spacing.default)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .padding(MaterialTheme.spacing.small)
                    .size(MaterialTheme.size.defaultIconSize)
                    .clickable {
                        navController.navigate(COMMUNITY_SCREEN_ROUTE) {
                            popUpTo(0)
                        }
                    },
                imageVector = Icons.Default.Close,
                contentDescription = "close post",
            )
            Text(
                modifier = Modifier
                    .padding(MaterialTheme.spacing.small)
                    .weight(1f),
                text = "Create a post",
                style = style
            )

            Box(
                modifier = Modifier
                    .clickable(
                        interactionSource = interactionSource,
                        indication = ripple,
                    ) {
                        if (isInternetAvailable != ConnectivityObserver.Status.Available) {
                            MedCommToast.showToast(context, "No connection")
                            return@clickable
                        }
                        if (postTitle.isEmpty() || postContent.isEmpty()) {
                            MedCommToast.showToast(context, "Post title/content can not be empty")
                            return@clickable
                        }
                        if (postContent.length < 20) {
                            MedCommToast.showToast(
                                context,
                                "Post Content must greater than 20 characters"
                            )
                            return@clickable
                        }
                        val post = AddNewCommunityPost(postTitle, postContent)
                        communityViewModel.addNewCommunityPost(post)
                    }
                    .padding(MaterialTheme.spacing.small)
            ) {
                Text(
                    modifier = Modifier.padding(horizontal = MaterialTheme.spacing.extraSmall),
                    text = "Post",
                    style = style.copy(
                        fontWeight = FontWeight.W700,
                        fontSize = MaterialTheme.typography.bodyLarge.fontSize
                    )
                )
            }
        }
    }

    val isSuccess = response.newPostResponse?.success?.toIntOrNull() ?: 0
    LaunchedEffect(isSuccess) {
        if (isSuccess == 1 && !postCreated) {
            postCreated = true
            MedCommToast.showToast(context, "Post created successfully")
            navController.navigate(COMMUNITY_SCREEN_ROUTE) {
                popUpTo(0)
            }
        }
    }
}

@Composable
fun ComposableNewPostBottomBar(postImageSelected: (Bitmap) -> Unit) {
    var isExpandedMoreOptions by remember { mutableStateOf(false) }
    val interactionSource by remember { mutableStateOf(MutableInteractionSource()) }
    val shape = RoundedCornerShape(
        topStart = MaterialTheme.spacing.medium,
        topEnd = MaterialTheme.spacing.medium,
        bottomEnd = MaterialTheme.spacing.default,
        bottomStart = MaterialTheme.spacing.default
    )
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = shape,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp),
        colors = CardDefaults.cardColors(Primary200)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.medSmall),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.End
            ) {
                SelectPostImageOptions { bitmap ->
                    postImageSelected(bitmap)
                }

                Icon(
                    modifier = Modifier
                        .padding(
                            start = MaterialTheme.spacing.avgSmall,
                            end = MaterialTheme.spacing.lessSmall
                        )
                        .size(MaterialTheme.size.mediumIconSize2)
                        .rotate(if (!isExpandedMoreOptions) 180f else 0f)
                        .clickable(
                            indication = null,
                            interactionSource = interactionSource,
                            onClick = { isExpandedMoreOptions = !isExpandedMoreOptions }
                        ),
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "close more options",
                    tint = BluishGray
                )

//                if (isExpandedMoreOptions) {
//                    Icon(
//                        modifier = Modifier
//                            .padding(start = 10.dp, end = 5.dp)
//                            .size(30.dp)
//                            .clickable {
//                                isExpandedMoreOptions = false
//                            },
//                        imageVector = Icons.Default.ArrowDropDown,
//                        contentDescription = "close more options"
//                    )
//                } else {
//                    Icon(
//                        modifier = Modifier
//                            .padding(horizontal = MaterialTheme.spacing.small)
//                            .size(30.dp)
//                            .clickable { isExpandedMoreOptions = true },
//                        imageVector = Icons.Default.ArrowDropUp,
//                        contentDescription = "open more options",
//                        tint = BluishGray
//                    )
//                }
            }

            AnimatedVisibility(visible = isExpandedMoreOptions) {
                Column {
                    HorizontalDivider(
                        modifier = Modifier
                            .fillMaxWidth(0.95f)
                            .padding(top = 5.dp)
                    )
                    ComposableNewPostExpandedOptions(
                        Modifier.padding(
                            start = MaterialTheme.spacing.medSmall,
                            end = MaterialTheme.spacing.medSmall,
                            bottom = MaterialTheme.spacing.avgSmall,
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun SelectPostImageOptions(onImageSelected: (Bitmap) -> Unit) {
    val style = TextStyle(
        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
        fontWeight = FontWeight.W400,
        fontFamily = manRopeFontFamily,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.87f),
    )
    val context = LocalContext.current
    var openCamera by remember { mutableStateOf(false) }
    var openGallery by remember { mutableStateOf(false) }

    // ***********************************************************************
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    val bitmapCameraLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview(),
        onResult = { newImage ->
            bitmap = newImage
        }
    )
    val bitmapCameraPermissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            bitmapCameraLauncher.launch()
        }
    }

    val galleryPhotoIcon = Icons.Default.AddPhotoAlternate
    val galleryCd = ImageSelectionOptions.GalleryImageSelection.name
    val galleryIconName = "Add photo"
    ComposableNewPostMediaColumn(galleryPhotoIcon, galleryCd, galleryIconName) {
        openGallery = it == 0
        openCamera = it == 1
    }

    val cameraPhotoIcon = Icons.Default.AddAPhoto
    val cameraCd = ImageSelectionOptions.CameraImageSelection.name
    val cameraIconName = "Capture"
    ComposableNewPostMediaColumn(cameraPhotoIcon, cameraCd, cameraIconName) {
        openGallery = it == 0
        openCamera = it == 1
    }

    if(openCamera){
        val permissionCheckResult =
            ContextCompat.checkSelfPermission(context, android.Manifest.permission.CAMERA)

        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
            // The permission is already granted
            bitmapCameraLauncher.launch()
        } else {
            // Launches the permission request
            bitmapCameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
        }
        openCamera = false
    }

    LaunchedEffect(bitmap){
        bitmap?.let(onImageSelected)
    }
    // ***********************************************************************

    // Image Picker
//    var pickedPhotoUri by remember { mutableStateOf(value = Uri.EMPTY) }
//    val activityResultLauncher =
//        rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
//            val pickedPhoto = uri ?: return@rememberLauncherForActivityResult
//            pickedPhotoUri = pickedPhoto
//            onImageSelected(pickedPhotoUri)
//        }
//
//    val permissionLauncher = rememberLauncherForActivityResult(
//        ActivityResultContracts.RequestPermission()
//    ) {
//        if (it) {
////            Toast.makeText(context, "Permission Granted", Toast.LENGTH_SHORT).show()
//        } else {
//            Toast.makeText(context, "Permission Denied", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    val testApplicationId = "com.alpharays.mymedicommfma"
//    val file = context.createImageFile()
//    val uri = FileProvider.getUriForFile(
//        Objects.requireNonNull(context),
//        "$testApplicationId.provider", file
//    )
//
//    val cameraLauncher = rememberLauncherForActivityResult(
//        contract = ActivityResultContracts.TakePicture(),
//        onResult = { success ->
//            if (success) {
//                pickedPhotoUri = uri
//                onImageSelected(pickedPhotoUri)
//            }
//        }
//    )
//
//    val imageVectorA = Icons.Default.AddPhotoAlternate
//    val cdGallery = ImageSelectionOptions.GalleryImageSelection.name
//    val iconNameA = "Add photo"
//    ComposableNewPostMediaColumn(imageVectorA, cdGallery, iconNameA) {
//        openGallery = it == 0
//        openCamera = it == 1
//    }
//
//
//    val imageVectorB = Icons.Default.AddAPhoto
//    val cdCamera = ImageSelectionOptions.CameraImageSelection.name
//    val iconNameB = "Capture"
//    ComposableNewPostMediaColumn(imageVectorB, cdCamera, iconNameB) {
//        openGallery = it == 0
//        openCamera = it == 1
//    }
//
//    if (openCamera) {
//        val permissionCheckResult =
//            ContextCompat.checkSelfPermission(
//                context,
//                Manifest.permission.CAMERA
//            )
//        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
//            cameraLauncher.launch(uri)
//        } else {
//             Request a permission
//            permissionLauncher.launch(Manifest.permission.CAMERA)
//        }
//        openCamera = false
//    }
//
//    if (openGallery) {
//        val permissionCheckResult =
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                ContextCompat.checkSelfPermission(
//                    context,
//                    Manifest.permission.READ_MEDIA_IMAGES
//                )
//            } else {
//                ContextCompat.checkSelfPermission(
//                    context,
//                    Manifest.permission.READ_EXTERNAL_STORAGE
//                )
//            }
//        if (permissionCheckResult == PackageManager.PERMISSION_GRANTED) {
//            activityResultLauncher.launch("image/*")
//        } else {
//            // Request a permission
//            val permission = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                Manifest.permission.READ_MEDIA_IMAGES
//            } else {
//                Manifest.permission.READ_EXTERNAL_STORAGE
//            }
//            permissionLauncher.launch(permission)
//        }
//        openGallery = false
//    }
}

enum class ImageSelectionOptions {
    GalleryImageSelection, CameraImageSelection
}

@Composable
fun ComposableNewPostMediaColumn(
    imageVector: ImageVector,
    cd: String,
    text: String,
    onClickResponse: (Int) -> Unit,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(horizontal = MaterialTheme.spacing.extraSmall)
            .clickable {
                if (cd == ImageSelectionOptions.GalleryImageSelection.name) {
                    onClickResponse(0)
                } else {
                    onClickResponse(1)
                }
            }
    ) {
        Icon(
            modifier = Modifier
                .padding(MaterialTheme.spacing.extraSmall)
                .size(MaterialTheme.size.defaultIconSize),
            imageVector = imageVector,
            contentDescription = cd,
            tint = MaterialTheme.colorScheme.surface
        )
        Text(
            text = text,
            style = TextStyle(
                fontSize = MaterialTheme.typography.bodySmall.fontSize,
                color = MaterialTheme.colorScheme.surface,
                fontFamily = manRopeFontFamily
            )
        )
    }
}

@Composable
fun ComposableNewPostExpandedOptions(modifier: Modifier) {
    var checked by remember { mutableStateOf(true) }
    val style = TextStyle(
        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
        fontWeight = FontWeight.W600,
        fontFamily = manRopeFontFamily
    )

    Column(modifier = modifier) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 15.dp, start = 10.dp, end = 10.dp), // TODO: add Material theme
            text = "Comment",
            style = style
        )


        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 5.dp, start = 10.dp, end = 14.dp) // TODO: add Material theme
                    .weight(1f),
                text = "Enabling comment will allow others to comment on your post",
                style = TextStyle(
                    fontSize = 14.sp, // TODO: add Material theme
                    fontWeight = FontWeight.Normal
                )
            )

            Switch(
                checked = checked,
                onCheckedChange = { checked = it },
                thumbContent = if (checked) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Check,
                            contentDescription = null,
                            modifier = Modifier.size(SwitchDefaults.IconSize),
                            tint = Color.Blue
                        )
                    }
                } else {
                    null
                },
                colors = SwitchDefaults.colors(
                    checkedThumbColor = Color.White,
                    uncheckedThumbColor = Color.LightGray,
                    checkedTrackColor = Color.Blue,
                    uncheckedTrackColor = Color.White
                )
            )
        }
    }
}

@Preview
@Composable
fun AddNewCommunityPostScreenPreview() {
//    AddNewCommunityPostScreen(rememberNavController())
}