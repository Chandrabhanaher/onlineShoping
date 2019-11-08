package biz.gina.southernbreezetour.ui.view.notification

import android.app.Activity
import android.content.Context
import android.graphics.drawable.LayerDrawable
import android.view.MenuItem
import demo.pc.demoshopcart.badge.SetNotificationCount

class NotificationCountSetClass : Activity() {
    companion object {
        private var icon: LayerDrawable? = null

        fun setAddToCart(context: Context, item: MenuItem, numMessages: Int) {
            icon = item.icon as LayerDrawable
            SetNotificationCount.setBadgeCount(context, icon!!, NotificationCountSetClass.setNotifyCount(numMessages))

        }

        fun setNotifyCount(numMessages: Int): Int {
            return numMessages

        }
    }


}//constructor
