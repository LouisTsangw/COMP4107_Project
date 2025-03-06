import kotlin.random.Random

object PlayerFactory {
    private val names = mutableListOf("LiuBei", "CaoCao", "GuanYu", "ZhouYu", "LvBu", "ZhenJi", "SunQuan", "SunCe", "SunShangxiang", "ZhugeLiang", "DiaoChan", "MaChao", "XiahouDun", "XiahouYuan", "Simayi")
    private val specialCards = listOf("Acedia", "Heroism")

    private val hpMap = mapOf(
        "LiuBei" to 1,
        "CaoCao" to 4,
        "GuanYu" to 4,
        "ZhouYu" to 3,
        "LvBu" to 4,
        "ZhenJi" to 3,
        "SunQuan" to 4,
        "SunCe" to 4,
        "SunShangxiang" to 3,
        "ZhugeLiang" to 3,
        "DiaoChan" to 3,
        "MaChao" to 4,
        "XiahouDun" to 4,
        "XiahouYuan" to 4,
        "Simayi" to 4
    )

    fun createPlayers(numParticipants: Int): List<Player> {
        require(numParticipants in 4..10) { "Unsupported number of players" }
        val distribution = IDENTITY_DISTRIBUTION[numParticipants] as Map<Identity, Int>
        val playerNames = names.shuffled().toMutableList()

        return buildList {
            distribution.forEach { (identity, count) ->
                val selectedNames = mutableListOf<String>()
                repeat(count) {
                    val name = playerNames.random()
                    selectedNames.add(name)

                    val strategy = when(identity) {
                        Identity.LORD -> LordStrategy()
                        Identity.LOYALIST -> LoyalistStrategy()
                        Identity.REBEL -> RebelStrategy()
                        Identity.SPY -> SpyStrategy(name)
                    }
                    val hp = hpMap[name] ?: throw IllegalArgumentException("Unknown character: $name")

                    add(Player(
                        name = name,
                        hp = hp,
                        identity = identity,
                        strategy = strategy,
                        general = General(name, hp),
                        deck = Deck()
                    ).apply {
                        // Modify point 1: Use the handCards property
                        // Modify point 2: Create Card objects
                        handCards.addAll(List(4) {
                            when (Random.nextInt(4)) {
                                0 -> Card(CardType.ATTACK, randomSuit(), (1..13).random())
                                1 -> Card(CardType.DODGE, randomSuit(), (1..13).random())
                                2 -> Card(CardType.EQUIPMENT, randomSuit(), (1..13).random())
                                else -> Card(CardType.PEACH, randomSuit(), (1..13).random())
                            }
                        })
                        if (identity == Identity.LORD) state = if (hp > 1) HealthyState() else UnhealthyState()
                    })
                }
                playerNames.removeAll(selectedNames)
            }
        }.shuffled()
    }

    // 辅助方法：随机选择花色
    private fun randomSuit() = Suit.values().random()
}