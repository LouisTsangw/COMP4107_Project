class Deck {
    private val cards = mutableListOf<Card>()
    private val discardPile = mutableListOf<Card>()
    val suit = Suit.values().random()
    val value = (1..13).random()
    init {
        listOf(
            CardType.ATTACK to 20,
            CardType.DODGE to 20,
            CardType.PEACH to 5,
            CardType.ACEDIA to 2,
            CardType.EQUIPMENT to 20  // 假设生成3张装备卡
        ).forEach { (type, count) ->
            repeat(count) {
                when (type) {
                    CardType.EQUIPMENT -> {
                        // 八卦阵：梅花2，其他装备可类似扩展
                        cards.add(Card(type, suit, value))
                    }
                    else -> {
                        cards.add(Card(type, suit, value))
                    }
                }
            }
        }
        shuffle()
    }
    fun shuffle() = cards.shuffle()

    fun draw(count: Int): List<Card> {
        if (cards.size < count) {
            cards.addAll(discardPile)
            discardPile.clear()
            shuffle()
        }
        return (1..count).map { cards.removeAt(0) }
    }

    fun discard(cards: List<Card>) {
        discardPile.addAll(cards)
    }
}