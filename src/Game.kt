class WTKGame(private val numPlayers: Int, private val rounds: Int) {
    private val deck = Deck()
    private val players = PlayerFactory.createPlayers(numPlayers)
    private var acediaCardHolder: Player? = null

    init {
//        // 初始化发牌
//        players.forEach { it.handCards.addAll(deck.draw(4)) }

        // 初始化角色信息
        players.forEach {
            println("${it.name} ID: ${it.identity}, HP: ${it.hp}")
            println("Card: ${it.handCards.joinToString()}")
        }
    }

    fun start() {
        repeat(rounds) { round ->
            println("\n=== 第 ${round + 1} 回合 ===")
            players.forEach { player ->
                if (player.hp <= 0) {
                    println("${player.name} 已出局")
                    return@forEach
                }

//                handleAcediaEffect(player)
                handleCardDrawing(player)
                handleStateTransition(player)
                handleEquipcard(player)
                player.state.playTurn(player, players)
                handleDiscarding(player)
            }
        }
    }
    fun handleEquipcard(player: Player) {
        player.autoEquip()  // 新增自动装备检测
    }

    private fun handleCardDrawing(player: Player) {
        if (!player.skipRound) {
            val drawnCards = deck.draw(2)
            player.handCards.addAll(drawnCards)
            println("${player.name} 抽牌: ${drawnCards.joinToString()}")
            println("Cards: ${player.handCards.joinToString()}")
        }
    }

    private fun handleDiscarding(player: Player) {
        val discardCount = player.handCards.size - player.hp
        if (discardCount > 0) {
            player.handCards.shuffle() // Shuffle the hand cards
            val toDiscard = player.handCards.takeLast(discardCount).toMutableList()
            player.handCards.removeAll(toDiscard)
            deck.discard(toDiscard)
            println("${player.name} 弃牌: ${toDiscard.joinToString()}")
        }
    }

    private fun handleStateTransition(player: Player) {
        if (player.identity == Identity.LORD) {
            player.state = if (player.hp > 1) HealthyState() else UnhealthyState()
        }
    }
}