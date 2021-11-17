package hu.ait.minesweeper.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import hu.ait.minesweeper.MainActivity
import hu.ait.minesweeper.R
import hu.ait.minesweeper.model.MinesweeperModel

class MinesweeperView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    lateinit var paintBackground: Paint
    lateinit var paintLine: Paint
    lateinit var paintNum: Paint

    lateinit var bitmapBackground: Bitmap
    lateinit var bitmapMine: Bitmap
    lateinit var bitmapFlag: Bitmap

    lateinit var bounds: Rect

    init {
        paintBackground = Paint()
        paintBackground.color = Color.rgb(85, 144, 248)
        paintBackground.style = Paint.Style.FILL

        paintLine = Paint()
        paintLine.color = Color.WHITE
        paintLine.style = Paint.Style.STROKE
        paintLine.strokeWidth = 5f

        paintNum = Paint()
        paintNum.color = Color.LTGRAY
        paintNum.textSize = 150f

        bounds = Rect()

    }

    init {
        bitmapMine = BitmapFactory.decodeResource(
            context!!.resources,
            R.drawable.bees)
        bitmapFlag = BitmapFactory.decodeResource(
            context!!.resources,
            R.drawable.flag
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        bitmapMine = Bitmap.createScaledBitmap(
        bitmapMine, width / 6, height / 6, false
        )

        bitmapFlag = Bitmap.createScaledBitmap(
        bitmapFlag, width / 6, height / 6, false
        )
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintBackground)

        drawHorizontalLines(canvas!!)
        drawVerticalLines(canvas!!)

        drawPlayers(canvas!!)
    }

    private fun drawHorizontalLines(canvas: Canvas) {
        canvas.drawRect(0f, 0f, width.toFloat(), height.toFloat(), paintLine)
        canvas.drawLine(0f, (height / 5).toFloat(), width.toFloat(), (height / 5).toFloat(),
            paintLine)
        canvas.drawLine(0f, (2 * height / 5).toFloat(), width.toFloat(),
            (2 * height / 5).toFloat(), paintLine)
        canvas.drawLine(0f, (3 * height / 5).toFloat(), width.toFloat(),
            (3 * height / 5).toFloat(), paintLine)
        canvas.drawLine(0f, (4 * height / 5).toFloat(), width.toFloat(),
            (4 * height / 5).toFloat(), paintLine)
        canvas.drawLine(0f, (5 * height / 5).toFloat(), width.toFloat(),
            (5 * height / 5).toFloat(), paintLine)
    }

    private fun drawVerticalLines(canvas: Canvas) {
        canvas.drawLine((width / 5).toFloat(), 0f, (width / 5).toFloat(), height.toFloat(),
            paintLine)
        canvas.drawLine((2 * width / 5).toFloat(), 0f, (2 * width / 5).toFloat(), height.toFloat(),
            paintLine)
        canvas.drawLine((3 * width / 5).toFloat(), 0f, (3 * width / 5).toFloat(), height.toFloat(),
            paintLine)
        canvas.drawLine((4 * width / 5).toFloat(), 0f, (4 * width / 5).toFloat(), height.toFloat(),
            paintLine)
        canvas.drawLine((5 * width / 5).toFloat(), 0f, (5 * width / 5).toFloat(), height.toFloat(),
            paintLine)
    }

    private fun drawPlayers(canvas: Canvas) {
        for (i in 0 until 5) {
            for (j in 0 until 5) {
                if (MinesweeperModel.getFieldContent(i, j) == MinesweeperModel.MINE) {
                    canvas.drawBitmap(bitmapMine, (i * width / 5 + width / 100).toFloat(),
                        (j * height / 5 + height / 60).toFloat(), null)
                } else if (MinesweeperModel.getFieldContent(i, j) == MinesweeperModel.FLAG) {
                    canvas.drawBitmap(bitmapFlag, (i * width / 5 + width / 100).toFloat(),
                        (j * height / 5 + height / 60).toFloat(), null)
                } else if (MinesweeperModel.getFieldContent(i, j) == MinesweeperModel.NUM) {
                    val centerX = (i * width / 5 + width / 20).toFloat()
                    val centerY = (j * height / 5 + height / 5.9).toFloat()
                    var num = MinesweeperModel.fieldMatrix[i][j].minesAround.toString()
                    canvas.drawText(num, centerX, centerY, paintNum)
                }
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {

            val tX = event.x.toInt() / (width / 5)
            val tY = event.y.toInt() / (height / 5)

            // check state of toggle button and place corresponding symbol
            if (tX < 5 && tY < 5 && (context as MainActivity).isFlagMode()) {
                MinesweeperModel.setFieldContent(tX, tY, MinesweeperModel.FLAG)
                if (tX == 2 && tY == 2) {
                    MinesweeperModel.fieldMatrix[2][2].isFlagged = true
                } else if (tX == 3 && tY == 3) {
                    MinesweeperModel.fieldMatrix[3][3].isFlagged = true
                } else if (tX == 0 && tY == 4) {
                    MinesweeperModel.fieldMatrix[0][4].isFlagged = true
                }
            }
            if (tX < 5 && tY < 5 && !(context as MainActivity).isFlagMode()) {
                MinesweeperModel.fieldMatrix[tX][tY].clicked = true
                if (tX < 5 && tY < 5 && MinesweeperModel.getFieldContent(tX, tY) == MinesweeperModel.EMPTY
                    && MinesweeperModel.fieldMatrix[tX][tY].mine) {
                    MinesweeperModel.setFieldContent(tX, tY, MinesweeperModel.MINE)
                } else {
                    MinesweeperModel.setFieldContent(tX, tY, MinesweeperModel.NUM)
                }
            }
            if (MinesweeperModel.getWinner() == 0) {
                (context as MainActivity).showWinnerMessage()
            } else if (MinesweeperModel.getWinner() == 1) {
                (context as MainActivity).showLoserMessage()
            }
            invalidate()
        }
        return true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val w = View.MeasureSpec.getSize(widthMeasureSpec)
        val h = View.MeasureSpec.getSize(heightMeasureSpec)
        val d = if (w == 0) h else if (h == 0) w else if (w < h) w else h
        setMeasuredDimension(d, d)
    }

    public fun resetGame() {
        MinesweeperModel.resetModel()
        invalidate()
    }

}