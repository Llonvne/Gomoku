import BoardXPluginType.*
import PointType.*

class BoardX(
    boardSize: Int, initialPluginList: MutableList<BoardXPlugin> = mutableListOf()
) : Board<PointType> {

    var pluginList: MutableList<BoardXPlugin> = initialPluginList

    private val board: Board<PointType>
    private val typedPlugins get() = pluginList.groupBy { it.getPluginType() }


    init {
        board = BoardImpl(boardSize)

        initialPlugin(SystemPlug)

        initialPlugin(CreatePlug)

        initialPlugin(RuntimePlug)

        initialPlugin(EndPlug)



        createPlugin(SystemPlug)

        createPlugin(CreatePlug)

        createPlugin(RuntimePlug)

        createPlugin(EndPlug)
    }


    private fun initialPlugin(type: BoardXPluginType) {
        getTypedSortedPluginList(type).forEach {
            println("[Plugin Loader] ${it.javaClass.simpleName} loading ...")
            it.init(this)
        }
    }

    private fun createPlugin(type: BoardXPluginType) {
        getTypedSortedPluginList(type).forEach { it.onCreate(this) }
    }

    private fun getTypedSortedPluginList(type: BoardXPluginType): List<BoardXPlugin> {
        return typedPlugins[type]?.sortedByDescending { it.getPluginPriority() } ?: listOf()
    }

    override fun get(x: Int, y: Int): PointType {
        pluginList.forEach { it.onGet(x, y, this) }
        return board[x, y]
    }

    override fun set(x: Int, y: Int, pointType: PointType) {
        pluginList.forEach { it.beforeSetting(x, y, toPlayerType(pointType), this) }
        board[x, y] = pointType
        pluginList.forEach { it.onSet(x, y, toPlayerType(pointType), this) }
    }

    override fun direct(): MutableList<MutableList<PointType>> {
        return board.direct()
    }

    private fun toPlayerType(pointType: PointType): PlayerType {
        return when (pointType) {
            Black -> PlayerType.Black
            White -> PlayerType.White
            Empty -> throw IllegalArgumentException("无法将 PointType.Empty 转换为 PlayerType")
        }
    }
}