package com.alpharays.mymedicommfma.communityv2.community_app.community_utils

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.alpharays.mymedicommfma.R
import com.alpharays.mymedicommfma.common.connectivity.ConnectivityObserver
import com.alpharays.mymedicommfma.communityv2.MedCommRouter
import com.alpharays.mymedicommfma.communityv2.MedCommRouter.NO_CONNECTION
import com.alpharays.mymedicommfma.communityv2.MedCommRouter.ONE_TIME_POST_ID_KEY
import com.alpharays.mymedicommfma.communityv2.MedCommToast
import com.alpharays.mymedicommfma.communityv2.community_app.presentation.community_screen.CommunityViewModel

class CommunityUtils {
    companion object {
        fun setAuthToken(context: Context, token: String) {
            val authTokenSharedPref = context.getSharedPreferences(
                MedCommRouter.AUTH_TOKEN_SHARED_PREF,
                Context.MODE_PRIVATE
            )
            authTokenSharedPref.edit().putString(MedCommRouter.AUTH_TOKEN_KEY, token).apply()
        }

        fun getAuthToken(context: Context): String {
            val authTokenSharedPref = context.getSharedPreferences(
                MedCommRouter.AUTH_TOKEN_SHARED_PREF,
                Context.MODE_PRIVATE
            )
            return authTokenSharedPref.getString(MedCommRouter.AUTH_TOKEN_KEY, null).toString()
        }

        fun setOneTimePostId(context: Context, postId: String) {
            val postIdSharedPref = context.getSharedPreferences(
                MedCommRouter.ONE_TIME_POST_ID,
                Context.MODE_PRIVATE
            )
            postIdSharedPref.edit().putString(ONE_TIME_POST_ID_KEY, postId).apply()
        }

        fun getOneTimePostId(context: Context): String {
            val postIdSharedPref = context.getSharedPreferences(
                MedCommRouter.ONE_TIME_POST_ID,
                Context.MODE_PRIVATE
            )
            return postIdSharedPref.getString(ONE_TIME_POST_ID_KEY, "").toString()
        }

        @Composable
        fun <T> ComposableNoNetworkFound(
            context: Context,
            networkStatus: ConnectivityObserver.Status,
            modifier: Modifier,
            viewModel: T,
            toShow: Boolean = true,
        ) {
            var reLoadScreen by remember { mutableStateOf(false) }
            if (toShow) {
                val painter = painterResource(id = R.drawable.no_internet)
                Column(
                    modifier = modifier.padding(top = 10.dp, start = 5.dp, end = 5.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        painter = painter,
                        contentDescription = "No internet found",
                        tint = Color.Unspecified,
                        modifier = Modifier.size(200.dp)
                    )

                    Text(
                        modifier = modifier,
                        text = MedCommRouter.SOMETHING_WENT_WRONG,
                        style = TextStyle(
                            fontSize = 22.sp,
                            fontWeight = FontWeight.W600,
                            textAlign = TextAlign.Center
                        )
                    )

                    Text(
                        modifier = modifier,
                        text = MedCommRouter.NO_CONNECTION_MSG,
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontWeight = FontWeight.W300,
                            textAlign = TextAlign.Center
                        )
                    )

                    OutlinedButton(
                        shape = RoundedCornerShape(12.dp),
                        modifier = modifier,
                        onClick = {
                            reLoadScreen = true
                        }) {
                        Text(
                            text = "Refresh",
                            style = TextStyle(
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Medium,
                                fontFamily = FontFamily.Monospace
                            ),
                            color = Color.Blue
                        )
                    }
                }
            }

            if (reLoadScreen) {
                if (networkStatus == ConnectivityObserver.Status.Unavailable) {
                    MedCommToast.showToast(context, NO_CONNECTION)
                    reLoadScreen = false
                    return
                }
                ScreenReload(viewModel)
            }
        }

        @Composable
        fun <T> ScreenReload(viewModel: T) {
            LaunchedEffect(Unit) {
                when (viewModel) {
                    is CommunityViewModel -> {
                        viewModel.refreshCommunityPosts()
                    }
                }
            }
        }

        fun getMedicoColor(context: Context, color: Int): Int {
            return context.getColor(color)
        }
    }
}