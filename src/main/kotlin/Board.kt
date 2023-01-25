interface Board<PointType> {
    operator fun get(x: Int, y: Int): PointType

    operator fun set(x: Int, y: Int, pointType: PointType)

    fun direct(): MutableList<MutableList<PointType>>
}