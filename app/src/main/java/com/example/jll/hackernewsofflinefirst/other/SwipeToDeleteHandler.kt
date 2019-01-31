package com.example.jll.hackernewsofflinefirst.other

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.support.v4.content.ContextCompat
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper
import com.example.jll.hackernewsofflinefirst.R
import com.example.jll.hackernewsofflinefirst.adapters.ArticlesAdapter
import android.opengl.ETC1.getHeight
import android.R.drawable.ic_delete
import android.graphics.*
import android.graphics.drawable.Drawable
import android.support.annotation.NonNull


abstract class SwipeToDeleteHandler internal constructor(internal var mContext: Context) : ItemTouchHelper.Callback() {
  private val mClearPaint: Paint
  private val mBackground: ColorDrawable
  private val backgroundColor: Int
  private val deleteDrawable: Drawable?
  private val intrinsicWidth: Int
  private val intrinsicHeight: Int


  init {
    mBackground = ColorDrawable()
    backgroundColor = Color.parseColor("#b80f0a")
    mClearPaint = Paint()
    mClearPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR)
    deleteDrawable = ContextCompat.getDrawable(mContext, R.drawable.ic_delete)
    intrinsicWidth = deleteDrawable!!.intrinsicWidth
    intrinsicHeight = deleteDrawable.intrinsicHeight


  }


  override fun getMovementFlags(@NonNull recyclerView: RecyclerView, @NonNull viewHolder: RecyclerView.ViewHolder): Int {
    return ItemTouchHelper.Callback.makeMovementFlags(0, ItemTouchHelper.LEFT)
  }

  override fun onMove(@NonNull recyclerView: RecyclerView, @NonNull viewHolder: RecyclerView.ViewHolder, @NonNull viewHolder1: RecyclerView.ViewHolder): Boolean {
    return false
  }

  override fun onChildDraw(@NonNull c: Canvas, @NonNull recyclerView: RecyclerView, @NonNull viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

    val itemView = viewHolder.itemView
    val itemHeight = itemView.height

    val isCancelled = dX == 0f && !isCurrentlyActive

    if (isCancelled) {
      clearCanvas(c, itemView.right + dX, itemView.top as Float, itemView.right as Float, itemView.bottom as Float)
      super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
      return
    }

    mBackground.color = backgroundColor
    mBackground.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
    mBackground.draw(c)

    val deleteIconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
    val deleteIconMargin = (itemHeight - intrinsicHeight) / 2
    val deleteIconLeft = itemView.right - deleteIconMargin - intrinsicWidth
    val deleteIconRight = itemView.right - deleteIconMargin
    val deleteIconBottom = deleteIconTop + intrinsicHeight


    deleteDrawable!!.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom)
    deleteDrawable.draw(c)

    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)


  }

  private fun clearCanvas(c: Canvas, left: Float?, top: Float?, right: Float?, bottom: Float?) {
    c.drawRect(left!!, top!!, right!!, bottom!!, mClearPaint)

  }

  override fun getSwipeThreshold(@NonNull viewHolder: RecyclerView.ViewHolder): Float {
    return 0.7f
  }
}
