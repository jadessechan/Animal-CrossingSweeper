package hu.ait.minesweeper.model

import java.util.*

object MinesweeperModel {

    public val FLAG: Short = 0
    public val MINE: Short = 1
    public val NUM: Short = 3
    public val EMPTY: Short = 4

    data class Field(var type: Short, var minesAround: Int, var isFlagged: Boolean, var mine: Boolean,
                     var clicked: Boolean)

    val fieldMatrix: Array<Array<Field>> = arrayOf(
        arrayOf(
            Field(NUM, 0, false,false, false),
            Field(NUM, 0, false, false, false),
            Field(NUM, 0, false, false, false),
            Field(NUM, 1, false, false, false),
            Field(MINE, 0, false, true, false)),
        arrayOf(
            Field(NUM, 0, false, false, false),
            Field(NUM, 1, false, false, false),
            Field(NUM, 1, false, false, false),
            Field(NUM, 1, false, false, false),
            Field(NUM, 1, false, false, false)),
        arrayOf(
            Field(NUM, 0, false, false, false),
            Field(NUM, 1, false, false, false),
            Field(MINE, 0, false, true, false),
            Field(NUM, 2, false, false, false),
            Field(NUM, 1, false, false, false)),
        arrayOf(
            Field(NUM, 0, false, false, false),
            Field(NUM, 1, false, false, false),
            Field(NUM, 2, false, false, false),
            Field(MINE, 0, false, true, false),
            Field(NUM, 1, false, false, false)),
        arrayOf(
            Field(NUM, 0, false, false, false),
            Field(NUM, 0, false, false, false),
            Field(NUM, 1, false, false, false),
            Field(NUM, 1, false, false, false),
            Field(NUM, 1, false, false, false)))

    fun getFieldContent(x: Int, y: Int) = fieldMatrix[x][y].type

    fun setFieldContent(x: Int, y: Int, content: Short) {
        fieldMatrix[x][y].type = content
    }

    fun getWinner(): Int {
        var win = 0
        var lose = 1
        // WIN: flags all the mines
        if (fieldMatrix[2][2].isFlagged && fieldMatrix[3][3].isFlagged && fieldMatrix[0][4].isFlagged) {
            return win
        }
        // LOSE: flags the wrong spot
        for (i in 0 until 5) {
            for (j in 0 until 5) {
                if (fieldMatrix[i][j].type == FLAG && !(fieldMatrix[i][j].mine)) {
                    return lose
                }
                // touches a mine in 'try' mode
                else if (fieldMatrix[i][j].clicked && fieldMatrix[i][j].type == MINE) {
                    return lose
                }
            }
        }
        return -1
    }

    fun resetModel() {
        for (i in 0 until 5) {
            for (j in 0 until 5) {
                fieldMatrix[i][j].type = EMPTY
                fieldMatrix[i][j].clicked = false
            }
        }
        fieldMatrix[2][2].isFlagged = false
        fieldMatrix[3][3].isFlagged = false
        fieldMatrix[0][4].isFlagged = false
    }

}