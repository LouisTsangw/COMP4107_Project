import kotlin.random.Random

open class General(
    open val name: String,
    open var hp: Int,
//    open val gender: String,
    open var dodgeRate: Double = 0.0  // 默认闪避率
) {
    // 装备列表
    val equipments = mutableListOf<Equipment>()

    // 闪避判定逻辑
    fun checkDodge(): Boolean {
        val eightTrigrams = equipments.filterIsInstance<EightTrigrams>().firstOrNull()
        return eightTrigrams?.performJudgment() ?: (Random.nextDouble() < dodgeRate)
    }

    // 执行动作
    open fun performAction() {
        println("$name 执行了动作")
    }
}