interface State {
    fun playTurn(player: Player, players: List<Player>)
}

class HealthyState : State {
    override fun playTurn(player: Player, players: List<Player>) {
        player.attack(players)
    }
}

class UnhealthyState : State {
    override fun playTurn(player: Player, players: List<Player>) {
        if (player.handCards.size >= 2) {
            if (player.name == "LiuBei" && player.hp == 1) {
                player.handCards = player.handCards.drop(2).toMutableList()
                player.hp++
                println("[Benevolence] ${player.name} gives away two cards and recovers 1 HP, now his HP is ${player.hp}. ")
                println("${player.name} is now healthy. ")
            } else {
                player.handCards = player.handCards.drop(2).toMutableList()
            }
        }
        player.attack(players)
    }
}