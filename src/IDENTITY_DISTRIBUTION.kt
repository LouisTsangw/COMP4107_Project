import kotlin.random.Random

val IDENTITY_DISTRIBUTION = mapOf(
    4 to mapOf(Identity.LORD to 1, Identity.LOYALIST to 1, Identity.REBEL to 1, Identity.SPY to 1),
    5 to mapOf(Identity.LORD to 1, Identity.LOYALIST to 1, Identity.REBEL to 2, Identity.SPY to 1),
    7 to mapOf(Identity.LORD to 1, Identity.LOYALIST to 2, Identity.REBEL to 3, Identity.SPY to 1),
    8 to run {
        val rebelCount = if (Random.nextBoolean()) 3 else 4
        val spyCount = if (rebelCount == 3) 2 else 1
        mapOf(
            Identity.LORD to 1,
            Identity.LOYALIST to 2,
            Identity.REBEL to rebelCount,
            Identity.SPY to spyCount
        ).also {
            require(it.values.sum() == 8) { "Invalid player count configuration" }
        }
    },
    8 to run {
        val rebelCount = if (Random.nextBoolean()) 4 else 3
        val spyCount = if (rebelCount == 4) 1 else 2
        mapOf(
            Identity.LORD to 1,
            Identity.LOYALIST to 2,
            Identity.REBEL to rebelCount,
            Identity.SPY to spyCount
        ).also {
            require(it.values.sum() == 8) { "Invalid player count configuration" }
        }
    },
    9 to mapOf(Identity.LORD to 1, Identity.LOYALIST to 3, Identity.REBEL to 4, Identity.SPY to 1),
    10 to mapOf(Identity.LORD to 1, Identity.LOYALIST to 3, Identity.REBEL to 4, Identity.SPY to 2)
)