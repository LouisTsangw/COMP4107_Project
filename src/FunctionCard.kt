//        // 随机分配ACEDIA卡
//        val acediaPlayer = players.random()
//        acediaPlayer.handCards.add(
//            Card(CardType.ACEDIA, Suit.values().random(), 0)
//        )
//        acediaPlayer.skipRound = true
//        acediaCardHolder = acediaPlayer


private fun handleAcediaEffect(player: Player) {
    if (player.handCards.any { it.type == CardType.ACEDIA }) {
        println("${player.name} 持有ACEDIA卡")
        if (!attemptDodge(player)) {
            player.skipRound = true
            println("${player.name} 本回合无法行动")
        }
        player.handCards.removeIf { it.type == CardType.ACEDIA }
    }
}

private fun attemptDodge(player: Player): Boolean {
    return if (player.handCards.any { it.type == CardType.DODGE }) {
        val dodgeCard = player.handCards.first { it.type == CardType.DODGE }
        player.handCards.remove(dodgeCard)
        println("${player.name} 使用 ${dodgeCard} 抵消ACEDIA效果")
        true
    } else {
        false
    }
}