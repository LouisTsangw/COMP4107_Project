sealed class CardType {
    object ATTACK : CardType()
    object DODGE : CardType()
    object PEACH : CardType()
    object ACEDIA : CardType()
    object EQUIPMENT : CardType()  // 新增装备类型
}

data class Card(
    val type: CardType,
    val suit: Suit,
    val value: Int
) {
    override fun toString(): String {
        val valueStr = when(value) {
            1 -> "A"
            11 -> "J"
            12 -> "Q"
            13 -> "K"
            else -> value.toString()
        }
        return "${type::class.simpleName}(${suit.name} $valueStr)"
    }
}