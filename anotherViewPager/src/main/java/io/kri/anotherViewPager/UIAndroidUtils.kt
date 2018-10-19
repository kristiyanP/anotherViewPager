package io.kri.anotherViewPager

import android.content.Context


/**
 * @author Kristiyan Petrov
 */
object UIAndroidUtils {

    /**
     * get dimensions given resource  id
     *
     * @param context    context
     * @param resourceId resource id
     * @return value in px
     */
    fun Context.getDimenFromResource(resourceId: Int): Float {
        return resources.getDimension(resourceId)
    }
}
