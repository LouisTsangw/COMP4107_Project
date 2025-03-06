interface AttackStrategy {
    fun whomToAttack(players: List<Player>, self: Player): Player?
    fun getTargetDescription(target: Player): String
}

class LordStrategy : AttackStrategy, Subject {
    private val observers = mutableListOf<Observer>()

    override fun whomToAttack(players: List<Player>, self: Player) =
        players.firstOrNull { it.identity == Identity.REBEL }

    override fun getTargetDescription(target: Player) = "a rebel, ${target.name}"

    override fun registerObserver(observer: Observer) {
        observers.add(observer)
    }

    override fun notifyObservers(successfulDodge: Boolean) {
        observers.forEach { it.update(successfulDodge) }
    }
}

class LoyalistStrategy : AttackStrategy {
    override fun whomToAttack(players: List<Player>, self: Player) =
        players.firstOrNull { it.identity == Identity.REBEL }

    override fun getTargetDescription(target: Player) = "a rebel, ${target.name}"
}

class RebelStrategy : AttackStrategy {
    override fun whomToAttack(players: List<Player>, self: Player) =
        players.firstOrNull { it.identity == Identity.LORD }

    override fun getTargetDescription(target: Player) = "the lord, ${target.name}"
}

class SpyStrategy(private val spyName: String, private var riskLevel: Double = 50.0) : AttackStrategy, Observer {
    override fun whomToAttack(players: List<Player>, self: Player) =
        players.firstOrNull { it.identity == Identity.REBEL }

    override fun getTargetDescription(target: Player) = "a rebel, ${target.name}"

    override fun update(successfulDodge: Boolean) {
        riskLevel = if (successfulDodge) riskLevel * 0.5 else riskLevel * 1.5
        println("$spyName on Lord's Risk Level: ${riskLevel.toInt()} ")
    }
}