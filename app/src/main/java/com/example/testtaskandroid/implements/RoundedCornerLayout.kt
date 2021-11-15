package com.example.testtaskandroid.implements

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.widget.LinearLayout
import com.example.testtaskandroid.R


class RoundedCornerLayout : LinearLayout {
    private var maskBitmap: Bitmap? = null
    private var paint: Paint? = null
    private var cornerRadius = 0f

    constructor(context: Context) : super(context) {
        init(context, null, 0)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(context, attrs, defStyle)
    }

    private fun init(context: Context, attrs: AttributeSet?, defStyle: Int) {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        setWillNotDraw(false)
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        if (maskBitmap == null) {
            // This corner radius assumes the image width == height and you want it to be circular
            // Otherwise, customize the radius as needed

            cornerRadius = getResources().getDimensionPixelSize(R.dimen.radius).toFloat()
            maskBitmap = createMask(width, height)
        }
        canvas.drawBitmap(maskBitmap!!, 0f, 0f, paint)
    }

    private fun createMask(width: Int, height: Int): Bitmap {
        val mask = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(mask)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint.color = Color.parseColor("#1a1e24") // TODO set your background color as needed
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paint)
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
        canvas.drawRoundRect(
            RectF(0f,
                0f,
                width.toFloat(),
                height.toFloat()),
            cornerRadius,
            cornerRadius,
            paint
        )
        canvas.drawRect(width.toFloat(), 0f, cornerRadius, height.toFloat(), paint)

        return mask
    }
}