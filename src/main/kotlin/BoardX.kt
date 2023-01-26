import PointType.*
import BoardXPluginType.*

class BoardX(
    private val boardSize: Int, var initialPluginList: MutableList<BoardXPlugin> = mutableListOf()
) : Board<PointType> {

    var pluginList: MutableList<BoardXPlugin> = initialPluginList

    private val board: Board<PointType>
    private val typedPlugins get() = pluginList.groupBy { it.getPluginType() }


    init {
        board = BoardImpl(boardSize)

        loader();

        system()

        create()


        runtime()
    }

    private fun loader() {
        pluginList = load()
    }

    private fun system() {
        getTypedSortedPluginList(SystemPlug).forEach { it.initByBoardX(this) }
        getTypedSortedPluginList(SystemPlug).forEach { it.onCreate(this) }
    }

    private fun create() {
        getTypedSortedPluginList(CreatePlug).forEach { it.initByBoardX(this) }
        getTypedSortedPluginList(CreatePlug).forEach { it.onCreate(this) }
    }

    private fun runtime() {
        getTypedSortedPluginList(RuntimePlug).forEach { it.initByBoardX(this) }
        getTypedSortedPluginList(RuntimePlug).forEach { it.onCreate(this) }
    }

    fun getTypedSortedPluginList(type: BoardXPluginType): List<BoardXPlugin> {
        return typedPlugins[type]?.sortedByDescending { it.getPluginPriority() } ?: listOf()
    }

    override fun get(x: Int, y: Int): PointType {
        pluginList.forEach { it.onGet(x, y, this) }
        return board[x, y]
    }

    override fun set(x: Int, y: Int, pointType: PointType) {
        if (pluginList.all { it.beforeSetting(x, y, toPlayerType(pointType), this) }) {
            board[x, y] = pointType
            pluginList.forEach { it.onSet(x, y, toPlayerType(pointType), this) }
        }
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