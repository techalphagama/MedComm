package com.alpharays.mymedicommfma.communityv2

import android.content.Context
import android.widget.Toast

class MedCommToast {
    companion object {
        fun showToast(context: Context, msg: String) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }
}