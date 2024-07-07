package com.alpharays.mymedicommfma.communityv2

import android.content.Context
import android.content.Intent
import com.alpharays.mymedicommfma.communityv2.community_app.CommunityActivity

object MedCommRouter {


    const val API_SAFE_KEY = "key"
    const val API_SAFE_KEY_VALUE = "rueth8934otwurhrtgw0th30wow39u932tj"

    const val BASE_URL = "https://medico-ny1q.onrender.com/"
    const val SOCKET_BASE_URL = "https://medico-socket-server.onrender.com/"

    const val APP_TAG = "APP_TAG"
    const val APP_TAG_ERROR = "APP_TAG_ERROR"

    const val SHARED_PREFERENCE_NAME = "SHARED_PREFERENCE_MEDICO_COMMUNITY"

    const val AUTH_TOKEN_SHARED_PREF = "authTokenSharedPrefHighPriority"
    const val AUTH_TOKEN_KEY = "authToken"

    const val ONE_TIME_POST_ID = "one_time_current_post_id_shared_pref"
    const val ONE_TIME_POST_ID_KEY = "one_time_current_post_id_shared_pref_key_value"


    const val UNEXPECTED_ERROR = "Unexpected error!"
    const val SOMETHING_WENT_WRONG = "Something went wrong"
    const val NO_CONNECTION_MSG = "Check your connection, then refresh the page."
    const val NO_CONNECTION = "No connection"
    const val READ_FULL_POST: String = " ... Read more"
    const val READ_MORE_BODY_TAG: String = "READ_MORE_BODY_TAG"
    const val EMPTY: String = ""

    const val DEFAULT_POST_BODY_LIMIT: Int = 50

    const val TOKEN_KEYWORD = "token"
    const val SUCCESS_CODE = "1"
    const val COMMENT_ADDED = "Comment added successfully"
    const val KEYBOARD_CLOSE_ON_BACK_PRESS_KEY_CODE = 17179869184


    lateinit var context: Context

    fun startDummyActivity(context: Context) {
        // Start the DummyActivity
        val intent = Intent(context, CommunityActivity::class.java)
        context.startActivity(intent)
    }


    fun initiateMedCommRouter(context: Context) {
        this.context = context
    }


}