package plugins.essentialX.io

import board.PlayerType

interface InputConnector {
    fun start()

    fun set(x: Int, y: Int, player: PlayerType)

    fun admitDefault(player: PlayerType)
}