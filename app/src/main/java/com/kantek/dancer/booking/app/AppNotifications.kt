package com.kantek.dancer.booking.app

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import android.os.Bundle
import android.support.core.extensions.safe
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.kantek.dancer.booking.R
import com.kantek.dancer.booking.app.AppNotifications.Companion.Id.CHANNEL_ID_BOOKING
import com.kantek.dancer.booking.app.AppNotifications.Companion.Id.CHANNEL_ID_CHAT
import com.kantek.dancer.booking.domain.model.firebase.FireBaseCloudMessage
import com.kantek.dancer.booking.presentation.MainAct
import com.kantek.dancer.booking.presentation.helper.AppNavigator
import java.util.concurrent.atomic.AtomicInteger

class AppNotifications(private val mContext: Context) {

    val id: Int
        get() = c.incrementAndGet()

    private fun pushNotification(
        cloudMessage: FireBaseCloudMessage,
        pendingIntent: PendingIntent?,
        chanelID: String = Id.CHANNEL_ID_DEFAULT,
        notificationID: Int? = id
    ) {
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(mContext, chanelID)
            .setColor(ContextCompat.getColor(mContext, R.color.colorPrimary))
            .setTicker(mContext.getString(R.string.app_name))
            .setAutoCancel(true)
            .setContentTitle(cloudMessage.title)
            .setContentText(cloudMessage.body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setLights(Color.MAGENTA, 3000, 3000)
            .setVibrate(longArrayOf(500, 500))
            .setSound(defaultSoundUri)
            .setWhen(System.currentTimeMillis())
            .setSmallIcon(R.drawable.ic_stat_notification_def)

        if (!cloudMessage.image.isNullOrEmpty()) {
            val bitmap = getBitmapFromURL(cloudMessage.image)
            if (bitmap != null)
                notificationBuilder.apply {
                    setLargeIcon(bitmap)
                    setStyle(
                        NotificationCompat.BigPictureStyle().bigPicture(bitmap).bigLargeIcon(bitmap)
                    )
                }
        } else notificationBuilder.setStyle(
            NotificationCompat.BigTextStyle().bigText(cloudMessage.body)
        )

        if (pendingIntent != null) notificationBuilder.setContentIntent(pendingIntent)

        val notificationManager =
            mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            @SuppressLint("WrongConstant") val channel = NotificationChannel(
                chanelID,
                mContext.getString(R.string.app_name),
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.enableLights(true)
            channel.lightColor = Color.CYAN
            channel.enableVibration(true)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(notificationID ?: id, notificationBuilder.build())
    }

    private fun getBitmapFromURL(imageURL: String): Bitmap? {
        val futureTarget = Glide.with(mContext)
            .asBitmap()
            .load(imageURL)
            .optionalFitCenter()
            .submit()
        return futureTarget.get()
    }

    private fun getIntent(data: Bundle): Intent {
        return Intent(mContext, MainAct::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
            putExtra("data", data)
        }
    }

    fun defaultNotify(it: FireBaseCloudMessage) {
        val intent = Intent(mContext, MainAct::class.java)
        val pendingIntent = getPendingIntent(it, intent)
        pushNotification(it, pendingIntent)
    }

    fun bookingNotify(it: FireBaseCloudMessage) {
        val pendingIntent = getPendingIntent(it, getIntent(Bundle().apply {
            putInt(AppNavigator.Companion.ArgKey.BOOKING_ID, it.contact_request_id.safe())
        }))
        pushNotification(it, pendingIntent, CHANNEL_ID_BOOKING, it.contact_request_id)
    }

    fun chatNotify(cloudMessage: FireBaseCloudMessage) {
        val data = Bundle().apply {
            putInt(
                AppNavigator.Companion.ArgKey.CONTACT_REQUEST_ID,
                cloudMessage.contact_request_id
            )
        }
        val pendingIntent = getPendingIntent(cloudMessage, getIntent(data))
        pushNotification(
            cloudMessage,
            pendingIntent,
            CHANNEL_ID_CHAT,
            cloudMessage.room_id
        )
    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private fun getPendingIntent(
        fireBaseMessage: FireBaseCloudMessage?,
        intent: Intent
    ): PendingIntent? {
        if (fireBaseMessage == null) return null
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.getActivity(
                mContext, System.currentTimeMillis().toInt(), intent, PendingIntent.FLAG_IMMUTABLE
                        or PendingIntent.FLAG_UPDATE_CURRENT
            )
        } else {
            PendingIntent.getActivity(
                mContext,
                System.currentTimeMillis().toInt(),
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
        }
    }

    /**
     * @param id~ Cancel notification
     */
    fun cancelNotification(id: Int) {
        val notificationManager =
            mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(id)
    }

    fun cancelAll() {
        getManager().cancelAll()
    }

    companion object {
        private val c = AtomicInteger(0)

        object Id {
            const val CHANNEL_ID_DEFAULT = "channel_default"
            const val CHANNEL_ID_BOOKING = "channel_booking"
            const val CHANNEL_ID_CHAT = "channel_chat"
        }
    }

    private fun getManager(): NotificationManager {
        return mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }
}
