package com.alpharays.mymedicommfma.communityv2.community_app.domain.mappers

import com.alpharays.mymedicommfma.communityv2.community_app.data.dto.comments_dto.all_comments_dto.AllCommentsResponseDto
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.comments.all_comments.AllComments
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.comments.all_comments.CommentData
import com.alpharays.mymedicommfma.communityv2.community_app.domain.model.community_screen.comments.all_comments.Like
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

fun AllCommentsResponseDto.toAllCommentsDtoMapper(): AllComments? {
    val response = this.data
    return try {
        AllComments(
            allComments = response?.map {
                CommentData(
                    commentId = it?._id,
                    commentContent = it?.commentContent,
                    commentTime = it?.commentTime?.formattedCommentTime(),
                    commentedByUserId = it?.commentedByUserId,
                    commentedByUserName = it?.commentedByUserName,
                    likes = it?.likes?.map { like ->
                        Like(
                            likeId = like?._id,
                            likedByUserId = like?.likedByUserId,
                            likedByUserName = like?.likedByUserName
                        )
                    },
                    postId = it?.postId,
                    replies = it?.replies
                )
            }
        )
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

fun String.formattedCommentTime(): String? {
    val dateFormat = SimpleDateFormat("d MMMM, yy", Locale.getDefault())
    val originalFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
    originalFormat.timeZone = TimeZone.getDefault()
    val date: Date? =
    try {
        originalFormat.parse(this)
    }
    catch (e: Exception) {
        Date()
    }
    return dateFormat.format(date ?: "")
}