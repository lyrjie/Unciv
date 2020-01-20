package com.unciv.ui.utils

import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.scenes.scene2d.Touchable
import com.badlogic.gdx.scenes.scene2d.ui.Cell
import com.badlogic.gdx.scenes.scene2d.ui.Label
import com.badlogic.gdx.scenes.scene2d.ui.Table
import com.badlogic.gdx.scenes.scene2d.ui.TextButton
import com.badlogic.gdx.utils.Align
import com.unciv.models.translations.tr

/**
 * Base class for all Popups, i.e. Tables that get rendered in the middle of a screen and on top of everything else
 */
open class Popup(val screen: CameraStageBaseScreen): Table(CameraStageBaseScreen.skin) {
    init {
        background = ImageGetter.getBackground(ImageGetter.getBlue().lerp(Color.BLACK, 0.5f))

        this.pad(20f)
        this.defaults().pad(5f)
    }

    /**
     * Displays the Popup on the screen. Will not open the popup if another one is already open.
     */
    fun open() {
        if (screen.hasOpenPopups()) return;
        pack()
        center(screen.stage)
        screen.stage.addActor(this)
    }

    open fun close() {
        remove()
    }

    fun addGoodSizedLabel(text: String, size:Int=18): Cell<Label> {
        val label = text.toLabel(fontSize = size)
        label.setWrap(true)
        label.setAlignment(Align.center)
        return add(label).width(screen.stage.width / 2)
    }

    fun addButton(text: String, action: () -> Unit): Cell<TextButton> {
        val button = TextButton(text.tr(), skin).apply { color = ImageGetter.getBlue() }
        button.onClick(action)
        return add(button).apply { row() }
    }

    fun addSquareButton(text: String, action: () -> Unit): Cell<Table> {
        val button = Table()
        button.add(text.toLabel())
        button.onClick(action)
        button.touchable = Touchable.enabled
        return add(button).apply { row() }
    }

    fun addCloseButton() = addButton("Close") { close() }
}
