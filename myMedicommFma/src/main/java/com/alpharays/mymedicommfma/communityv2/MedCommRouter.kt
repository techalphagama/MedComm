package com.alpharays.mymedicommfma.communityv2

import android.content.Context
import android.content.Intent
import com.alpharays.mymedicommfma.communityv2.community_app.CommunityActivity
import com.alpharays.mymedicommfma.communityv2.community_app.data.di.MedicoCommunityInjector
import com.alpharays.mymedicommfma.dummywork.DummyActivity

object MedCommRouter {

    const val PATIENT_NAME = "Patient name"
    const val PATIENT_DISEASE = "Patient disease"
    const val PATIENT_AGE = "Patient age"
    const val PATIENT_GENDER = "Patient gender"
    const val PATIENT_CONTACT = "Patient contact"
    const val DATE_OR_TIME = "Date or Time"

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

    const val MEDICO_DOC_ID = "medicoDocIdSharedPref"
    const val MEDICO_DOC_ID_KEY = "medicoDocIdKey"

    const val BALLOON_STATUS_COUNT = "balloonCountShown"
    const val BALLOON_STATUS_COUNT_KEY = "balloonCountShownKey"

    const val UNEXPECTED_ERROR = "Unexpected error!"
    const val SOMETHING_WENT_WRONG = "Something went wrong"
    const val NO_CONNECTION_MSG = "Check your connection, then refresh the page."
    const val INTERNAL_SERVER_ERROR = "internal server error"
    const val NO_CONNECTION = "No connection"
    const val CURRENT_POST_ID = "CURRENT_POST_ID"
    const val READ_FULL_POST: String = " ... Read more"
    const val READ_MORE_BODY_TAG: String = "READ_MORE_BODY_TAG"
    const val EMPTY: String = ""
    const val MEDICO_DATABASE = "medico_database"

    const val DEFAULT_POST_BODY_LIMIT: Int = 50

    const val TOKEN_KEYWORD = "token"
    const val SUCCESS_CODE = "1"
    const val COMMENT_ADDED = "Comment added successfully"
    const val DOC_ID_KEYWORD = "docId"
    const val KEYBOARD_CLOSE_ON_BACK_PRESS_KEY_CODE = 17179869184

    // endpoints
    // auth screen
    const val AUTH_BYPASS = "auth/bypass"

    // home screen
    const val GET_ALL_APPOINTMENTS = "doc/appointments"
    const val ADD_NEW_APPOINTMENT = "doc/appointmententry"

    // profile screen
    const val GET_PROFILE = "/doc/getprofile"
    const val UPDATE_PROFILE = "doc/Updateprofile"
    const val CURR_DOCS_POSTS =
        "doc/allposts"   // for currently logged in user - to access one's profile and posts
    const val OTHER_DOC_POSTS =
        "doc/currentdocposts"  // : for user's profile who is not the current logged in user - to see other's profile and posts

    // room db tables
    const val MEDICO_APPOINTMENTS_TABLE = "medico_appointments_table"
    const val MEDICO_PROFILE_TABLE = "medico_profile_table"
    const val MEDICO_MESSAGES_TABLE = "medico_messages_table"

    // navigation routes
    const val AUTH_SCREEN_ROUTE = "auth_screen"
    const val HOME_SCREEN_ROUTE = "home_screen"
    const val PROFILE_SCREEN_ROUTE = "profile_screen"
    const val UPDATE_PROFILE_SCREEN_ROUTE = "update_profile_screen"
    const val PATIENT_SCREEN_ROUTE = "patient_screen"
    const val APPOINTMENT_START_SCREEN_ROUTE = "appointment_start_screen"
    const val APPOINTMENT_COMPLETE_SCREEN_ROUTE = "appointment_complete_screen"


    lateinit var context: Context

    fun startDummyActivity(context: Context) {
        // Start the DummyActivity
        val intent = Intent(context, CommunityActivity::class.java)
        context.startActivity(intent)
    }


    fun initiateMedCommRouter(context: Context) {
        this.context = context
    }

    fun getCommunityInjector() =
        MedicoCommunityInjector()

}