import kotlin.random.Random

//enum class Suit(val description: String) {
//    HEARTS("红心"), DIAMONDS("方块"), CLUBS("梅花"), SPADES("黑桃")
//}
//enum class EquipmentType {
//    WEAPON,
//    ARMOR,
//    MOUNT
//}

abstract class Equipment(
    final override val name: String,
    final override var hp: Int,
//    final override val gender: String,
    val suit: Suit,
    val rank: String,
    val type: String
) : General(name, hp) {

    // 抽象方法，子类必须实现
    abstract override fun performAction()

    // 公共属性
    open val attackRange: Int? = null
}

// 修改 EightTrigrams 的 performAction 为判定方法
class EightTrigrams(general: General) : Equipment(
    name = "八卦阵",
    hp = 0,
//    gender =gender,
    suit = Suit.CLUBS,
    rank = "2",
    type = "ARMOR"
) {
    fun performJudgment(): Boolean {
        val success = Random.nextBoolean()
        println("触发八卦阵判定（花色：${suit.description}）")
        if (success) {
            println("${name} 闪避成功！")
        } else {
            hp -= 1
            println("${name} 判定失败，HP: ${hp}")
        }
        return success
    }

    override fun performAction() {} // 保留抽象实现
}

// 黑马装备（MOUNT 类型）
class BlackHorse(general: General) : Equipment(
    name = "黑马",
    hp = 0,
    suit = Suit.SPADES,
    rank = "1",
    type = "MOUNT"
) {
    override fun performAction() {
        println("黑马生效中...")
    }
}

// 青龙偃月刀（WEAPON 类型）
class GreenDragonBlade(general: General) : Equipment(
    name = "青龙偃月刀",
    hp = 0,
    suit = Suit.HEARTS,
    rank = "12",
    type = "WEAPON"
) {
    override fun performAction() {
        println("青龙偃月刀生效中...")
    }
}

class ZhugeCrossbow(general: General) : Equipment(
    name = "諸葛連弩",
    hp = 0,
    suit = Suit.CLUBS,
    rank = "A",
    type = "WEAPON"
) {
    override fun performAction() {
        println("使用諸葛連弩（花色：${suit.description}）")
        hp -= 2
        println("${name} 受到攻擊，當前 HP: ${hp}")
    }

    // 使用【殺】攻擊目標
    fun useKill(target: General) {
        if (canUseKill()) {
            println("${name} 使用【殺】攻擊 ${target.name}")
            target.hp -= 1
            println("${target.name} 的當前 HP: ${target.hp}")
        } else {
            println("${name} 無法使用【殺】，因為沒有足夠的牌")
        }
    }

    private fun canUseKill(): Boolean {
        return true
    }
}

class QingLongBlade(general: General) : Equipment(
    name = "青龍刃",
    hp = 0,
    suit = Suit.SPADES,
    rank = "5",
    type = "WEAPON"
) {
    override val attackRange: Int
        get() = 3 // 攻擊範圍為 3

    override fun performAction() {
        val success = Random.nextBoolean()
        println("觸發青龍刃判定（花色：${suit.description}）")
        if (success) {
            println("${name} 閃避成功！")
        } else {
            hp -= 1
            println("${name} 判定失敗，當前 HP: ${hp}")
        }
    }

    // 使用【殺】攻擊目標
    fun useKill(target: General, isDodged: Boolean) {
        println("${name} 使用【殺】攻擊 ${target.name}")
        if (isDodged) {
            println("${target.name} 使用【閃】閃避攻擊！")
            println("你可以再次使用【殺】攻擊 ${target.name}")
        } else {
            println("${target.name} 被【殺】擊中！")
        }
    }
}

class SerpentHalberd(general: General) : Equipment(
    name = "丈八蛇矛",
    hp = 0,
    suit = Suit.SPADES,
    rank = "Q",
    type = "WEAPON"
) {
    override fun performAction() {
        println("使用丈八蛇矛（花色：${suit.description}）")
        hp -= 2
        println("${name} 受到攻擊，當前 HP: ${hp}")
    }

    // 使用兩張手牌當作【殺】
    fun useAsKill(card1: Card, card2: Card, targetDistance: Int) {
        val attackRange = 3
        println("${name} 嘗試使用 $card1 和 $card2 當作【殺】")

        if (targetDistance <= attackRange) {
            println("目標在攻擊範圍內，執行攻擊")
        } else {
            println("目標超出攻擊範圍，無法執行攻擊")
        }
    }
}

class GenderDoubleSwords(general: General) : Equipment(
    name = "雌雄雙股劍",
    hp = 0,
    suit = Suit.SPADES,
    rank = "2",
    type = "WEAPON"
) {
    override val attackRange: Int
        get() = 2 // 攻擊範圍為 2

    override fun performAction() {
        val success = Random.nextBoolean()
        println("觸發雌雄雙股劍判定（花色：${suit.description}）")
        if (success) {
            println("${name} 閃避成功！")
        } else {
            hp -= 1
            println("${name} 判定失敗，當前 HP: ${hp}")
        }
    }

    // 使用【殺】攻擊目標
    fun useKill(target: General) {
        println("${name} 使用【殺】攻擊 ${target.name}")

        if (target != target) {
            println("${target.name}，請選擇以下選項：")
            println("1. 棄置一張牌")
            println("2. 讓 ${name} 抽一張牌")
            val choice = Random.nextInt(1, 3)
            when (choice) {
                1 -> println("${target.name} 選擇棄置一張牌")
                2 -> println("${target.name} 選擇讓 ${name} 抽一張牌")
            }
        } else {
            println("${target.name} 不是有效的【殺】目標")
        }
    }
}

class BlueSteelBlade(general: General) : Equipment(
    name = "青釭劍",
    hp = 0,
    suit = Suit.SPADES,
    rank = "6",
    type = "WEAPON"
) {
    override fun performAction() {
        println("使用青釭劍（花色：${suit.description}）")
        hp -= 2
        println("${name} 受到攻擊，當前 HP: ${hp}")
    }

    // 使用【殺】攻擊目標
    fun useKill(target: General) {
        println("${name} 使用【殺】攻擊 ${target.name}")
        println("${target.name} 的防具無效")
        target.hp -= 1
        println("${target.name} 的當前 HP: ${target.hp}")
    }
}


//// 诸葛连弩
//class ZhugeCrossbow(general: General) : Equipment(general, Suit.CLUBS, "A") { // 梅花A
//    override fun performAction() {
//        println("Using Zhuge Crossbow with suit: ${suit.description} ")
//        hp -= 2 // Assume the attack reduces HP by 2
//        println("$name is attacked, current HP is $hp")
//    }
//
//    // 使用【杀】攻击目标
//    fun useKill(target: General) {
//        if (canUseKill()) {
//            println("$name uses 【杀】 on ${target.name}.")
//            target.hp -= 1 // 假设每次使用【杀】减少目标 HP 1
//            println("${target.name}'s current HP is ${target.hp}.")
//        } else {
//            println("$name cannot use 【杀】 because there are no cards left.")
//        }
//    }
//
//    private fun canUseKill(): Boolean {
//        return true
//    }
//}
//
//
//// 青龙刃
//class QingLongBlade(general: General) : Equipment(general, Suit.SPADES, "5") {
//    override val attackRange: Int
//        get() = 3 // 青龙刃的攻击范围为 3
//    override fun performAction() {
//        val judgement = Random.nextBoolean() // 50% chance
//        println("Triggering the Qing Long Blade with suit: ${suit.description} ")
//        if (judgement) {
//            println("$name dodged the attack with the Qing Long Blade.")
//        } else {
//            hp -= 1 // Assume the attack reduces HP by 1
//            println("$name can't dodge the attack, current HP is $hp")
//        }
//    }
//
//    // 使用【杀】攻击目标
//    fun useKill(target: General, isDodged: Boolean) {
//        println("$name is using 【杀】 on ${target.name}.")
//        if (isDodged) {
//            println("${target.name} used 【闪】 to dodge the attack!")
//            println("You can use another 【杀】 on ${target.name}.")
//        } else {
//            println("${target.name} was hit by the 【杀】!")
//        }
//    }
//}
//// 丈八蛇矛
//class SerpentHalberd(general: General) : Equipment(general, Suit.SPADES, "Q") { // 黑桃Q
//    override fun performAction() {
//        println("Using Serpent Halberd with suit: ${suit.description} ")
//        hp -= 2 // Assume the attack reduces HP by 2
//        println("$name is attacked, current HP is $hp")
//    }
//
//    // 使用两张手牌当作【杀】
//    fun useAsKill(card1: Card, card2: Card, targetDistance: Int) {
//        val attackRange = 3
//        println("$name is attempting to use $card1 and $card2 as a 【杀】.")
//
//        // 检查目标距离是否在攻击范围内
//        if (targetDistance <= attackRange) {
//            println("Target is within range. Executing attack with $card1 and $card2.")
//            // 假设每次使用【杀】减少目标 HP 1
//            // 这里可以添加攻击逻辑，例如计算伤害、目标等
//        } else {
//            println("Target is out of range. Cannot execute attack.")
//        }
//    }
//}
//
//
//// 雌雄双股剑
//class GenderDoubleSwords(general: General) : Equipment(general, Suit.SPADES, "2") { // 黑桃2
//    override val attackRange: Int
//        get() = 2 // 攻击范围为 2
//
//    override fun performAction() {
//        val judgement = Random.nextBoolean() // 50% chance
//        println("Triggering the Gender Double Swords with suit: ${suit.description} ")
//        if (judgement) {
//            println("$name dodged the attack with the Gender Double Swords.")
//        } else {
//            hp -= 1 // Assume the attack reduces HP by 1
//            println("$name can't dodge the attack, current HP is $hp")
//        }
//    }
//
//    // 使用【杀】攻击目标
//    fun useKill(target: General) {
//        println("$name is using 【杀】 on ${target.name}.")
//
//        // 检查性别是否不同
//        if (gender != target.gender) {
//            println("${target.name}, you can choose one of the following options:")
//            println("1. Discard a card.")
//            println("2. Let ${name} draw a card.")
//            // 这里可以添加逻辑来处理目标的选择
//            // 例如，模拟目标选择
//            val choice = Random.nextInt(1, 3) // 随机选择 1 或 2
//            when (choice) {
//                1 -> println("${target.name} chose to discard a card.")
//                2 -> println("${target.name} chose to let ${name} draw a card.")
//            }
//        } else {
//            println("${target.name} is not a valid target for 【杀】.")
//        }
//    }
//}
//
//// 青釭剑
//class BlueSteelBlade(general: General) : Equipment(general, Suit.SPADES, "6") { // 黑桃6
//    override fun performAction() {
//        println("Using Blue Steel Blade with suit: ${suit.description} ")
//        hp -= 2 // Assume the attack reduces HP by 2
//        println("$name is attacked, current HP is $hp")
//    }
//
//    // 使用【杀】攻击目标
//    fun useKill(target: General) {
//        println("$name is using 【kill】 on ${target.name}.")
//
//        // 锁定技：令目标的防具无效
//        println("${target.name}'s armor is now ineffective due to the lock effect of the Blue Steel Blade.")
//
//        // 假设目标受到伤害
//        target.hp -= 1 // 假设每次使用【杀】减少目标 HP 1
//        println("${target.name}'s current HP is ${target.hp}.")
//    }
//}
//
////麒麟弓
//class UnicornBow(general: General) : Equipment(general, Suit.HEARTS, "5") {
//    override val attackRange: Int
//        get() = 4 // 麒麟弓的攻击范围为 4
//
//    override fun performAction() {
//        val judgement = Random.nextBoolean() // 50% chance
//        println("Triggering the Unicorn Bow with suit: ${suit.description} ")
//        if (judgement) {
//            println("$name dodged the attack with the Unicorn Bow.")
//        } else {
//            hp -= 1 // Assume the attack reduces HP by 1
//            println("$name can't dodge the attack, current HP is $hp")
//        }
//    }
//
//    // 使用【杀】攻击目标
//    fun useKill(target: General, isDodged: Boolean) {
//        println("$name is using 【杀】 on ${target.name}.")
//        if (isDodged) {
//            println("${target.name} used 【闪】 to dodge the attack!")
//            println("You can use another 【杀】 on ${target.name}.")
//        } else {
//            println("${target.name} was hit by the 【杀】!")
//            // 造成伤害后，允许弃置目标的坐骑牌
//            if (target.mountCards.isNotEmpty()) {
//                val discardedMount = target.mountCards.first() // 假设弃置第一张坐骑牌
//                target.mountCards.remove(discardedMount)
//                println("${target.name} discarded their mount card: ${discardedMount.name}.")
//            } else {
//                println("${target.name} has no mount cards to discard.")
//            }
//        }
//    }
//}
//
////贯石斧
//class RockCleavingAxe(general: General) : Equipment(general, Suit.DIAMONDS, "5") {
//    override val attackRange: Int
//        get() = 3 // 贯石斧的攻击范围为 3
//
//    // 使用【杀】攻击目标
//    fun useKill(target: General, isDodged: Boolean, cardsInHand: MutableList<Card>) {
//        println("$name is using 【杀】 on ${target.name}.")
//        if (isDodged) {
//            println("${target.name} used 【闪】 to dodge the attack!")
//            // 如果目标使用了【闪】抵消攻击
//            if (cardsInHand.size >= 2) {
//                // 棄置兩張牌
//                val discardedCards = cardsInHand.take(2)
//                cardsInHand.removeAll(discardedCards)
//                println("$name discarded two cards: ${discardedCards.joinToString { it.name }}.")
//                println("The 【杀】 still deals damage to ${target.name}!")
//                // 这里可以添加造成伤害的逻辑
//            } else {
//                println("Not enough cards to discard, the 【杀】 does not deal damage.")
//            }
//        } else {
//            println("${target.name} was hit by the 【杀】!")
//            // 这里可以添加造成伤害的逻辑
//        }
//    }
//
//    override fun performAction() {
//        val judgement = Random.nextBoolean() // 50% chance
//        println("Triggering the Rock Cleaving Axe with suit: ${suit.description} ")
//        if (judgement) {
//            println("$name dodged the attack with the Rock Cleaving Axe.")
//        } else {
//            hp -= 1 // Assume the attack reduces HP by 1
//            println("$name can't dodge the attack, current HP is $hp")
//        }
//    }
//}
//
//
////方天画戟
//class HeavenScorcherHalberd(general: General) : Equipment(general, Suit.DIAMONDS, "Q") {
//    override val attackRange: Int
//        get() = 3 // 方天画戟的攻击范围为 3
//
//    // 锁定技：若你使用【杀】是你最后的手牌，则此【杀】可以多选择两个目标
//    fun useKill(targets: List<General>, isLastCard: Boolean) {
//        if (isLastCard) {
//            println("$name is using 【杀】 on multiple targets: ${targets.joinToString { it.name }}.")
//            for (target in targets) {
//                println("${target.name} was hit by the 【杀】!")
//            }
//        } else {
//            println("$name is using 【杀】 on ${targets.first().name}.")
//            println("${targets.first().name} was hit by the 【杀】!")
//        }
//    }
//
//    override fun performAction() {
//        val judgement = Random.nextBoolean() // 50% chance
//        println("Triggering the Heaven Scorcher Halberd with suit: ${suit.description} ")
//        if (judgement) {
//            println("$name dodged the attack with the Heaven Scorcher Halberd.")
//        } else {
//            hp -= 1 // Assume the attack reduces HP by 1
//            println("$name can't dodge the attack, current HP is $hp")
//        }
//    }
//}
//
//// 大宛
//class DaYuan(general: General) : Equipment(general, Suit.SPADES, "K") {
//    override val attackRange: Int
//        get() = 1 // 爪黄飞电的攻击范围为 1
//
//    // 计算与其他角色的距离时，始终 -1
//    fun calculateDistance(baseDistance: Int): Int {
//        return baseDistance - 1
//    }
//
//    override fun performAction() {
//        println("$name is using DaYuan.")
//        // 其他逻辑可以在这里添加
//    }
//}
//
//// 绝影
//class TheShadow(general: General) : Equipment(general, Suit.SPADES, "5") {
//    override val attackRange: Int
//        get() = 1 // 爪黄飞电的攻击范围为 1
//
//    // 计算与其他角色的距离时，始终 +1
//    fun calculateDistance(baseDistance: Int): Int {
//        return baseDistance + 1
//    }
//
//    override fun performAction() {
//        println("$name is using TheShadow.")
//        // 其他逻辑可以在这里添加
//    }
//}
//
//// 爪黄飞电
//class ZhuHuangFeiDian(general: General) : Equipment(general, Suit.HEARTS, "K") {
//    override val attackRange: Int
//        get() = 1 // 爪黄飞电的攻击范围为 1
//
//    // 计算与其他角色的距离时，始终 +1
//    fun calculateDistance(baseDistance: Int): Int {
//        return baseDistance + 1
//    }
//
//    override fun performAction() {
//        println("$name is using 爪黄飞电.")
//        // 其他逻辑可以在这里添加
//    }
//}
//
//
//// 赤兔
//class RedHare(general: General) : Equipment(general, Suit.HEARTS, "5") {
//    override val attackRange: Int
//        get() = 1 // 爪黄飞电的攻击范围为 1
//
//    // 计算与其他角色的距离时，始终 -1
//    fun calculateDistance(baseDistance: Int): Int {
//        return baseDistance - 1
//    }
//
//    override fun performAction() {
//        println("$name is using RedHare.")
//        // 其他逻辑可以在这里添加
//    }
//}
//
//// 的卢
//class DiLu(general: General) : Equipment(general, Suit.HEARTS, "K") {
//    override val attackRange: Int
//        get() = 1 // 爪黄飞电的攻击范围为 1
//
//    // 计算与其他角色的距离时，始终 +1
//    fun calculateDistance(baseDistance: Int): Int {
//        return baseDistance + 1
//    }
//
//    override fun performAction() {
//        println("$name is using DiLu.")
//        // 其他逻辑可以在这里添加
//    }
//}
//
//// 紫骍
//class ZiXing(general: General) : Equipment(general, Suit.DIAMONDS, "K") {
//    override val attackRange: Int
//        get() = 1 // 爪黄飞电的攻击范围为 1
//
//    // 计算与其他角色的距离时，始终 -1
//    fun calculateDistance(baseDistance: Int): Int {
//        return baseDistance - 1
//    }
//
//    override fun performAction() {
//        println("$name is using ZiXing.")
//        // 其他逻辑可以在这里添加
//    }
//}

