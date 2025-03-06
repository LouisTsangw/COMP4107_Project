import kotlin.random.Random

data class Player(
    val name: String,
    var hp: Int,
    var handCards: MutableList<Card> = mutableListOf(),
    val equipment: MutableList<Equipment> = mutableListOf(),
    val identity: Identity,
    val strategy: AttackStrategy,
    val general: General,
    val deck: Deck,
    var state: State = HealthyState(),
    var skipRound: Boolean = false
){
    init {
        handCards = mutableListOf()
    }
    fun hasAttackCard() = handCards.any { it.type == CardType.ATTACK }

    fun attack(players: List<Player>) {
        if (!hasAttackCard()) {
            println("$name 没有攻击牌")
            return
        }

        strategy.whomToAttack(players.filter { it != this }, this)?.let { target ->
            val attackCard = handCards.first { it.type == CardType.ATTACK }
            val attacktarget = strategy.getTargetDescription(target)
            handCards.remove(attackCard)

            if (target.hp <= 0) {
                attack(players)
                return
            }

            println("$name 使用 $attackCard 攻击 $attacktarget")

            val dodged = Random.nextDouble() < 0.5 && target.handCards.any { it.type == CardType.DODGE }

            if (dodged) {
                val dodgeCard = target.handCards.first { it.type == CardType.DODGE }
                target.handCards.remove(dodgeCard)
                println("${target.name} 使用 $dodgeCard 闪避攻击")
                if (target.identity == Identity.LORD) (strategy as? LordStrategy)?.notifyObservers(true) else { }
            } else {
                target.hp--
                println("${target.name} 未能闪避，当前HP: ${target.hp}")
                if (target.identity == Identity.LORD) (strategy as? LordStrategy)?.notifyObservers(false) else { }
            }
        }
    }
    fun autoEquip() {
        // 遍歷手牌中的裝備卡
        handCards.filter { it.type == CardType.EQUIPMENT }.forEach { card ->
            when (card.suit to card.value) {
                // 八卦陣：梅花2，類型為 ARMOR
                Suit.CLUBS to 2 -> {
                    // 檢查是否已裝備同類型裝備
                    val existingArmor = equipment.filterIsInstance<EightTrigrams>().firstOrNull()
                    if (existingArmor != null) {
                        equipment.remove(existingArmor) // 卸下舊裝備
                    }
                    // 裝備新裝備
                    equipment.add(EightTrigrams(general))
                    println("${name} 裝備了${EightTrigrams(general).name}（類型：${EightTrigrams(general).type}）")
                    handCards.remove(card)
                    deck.discard(listOf(card))
                }

                // 黑馬裝備：黑桃1，類型為 MOUNT
                Suit.SPADES to 1 -> {
                    val existingMount = equipment.filter { it.type == "MOUNT" }.firstOrNull()
                    if (existingMount != null) {
                        equipment.remove(existingMount)
                    }
                    equipment.add(BlackHorse(general))
                    println("${name} 裝備了${BlackHorse(general).name}（類型：${BlackHorse(general).type}）")
                    handCards.remove(card)
                    deck.discard(listOf(card))
                }

                // 青龍偃月刀：紅心12，類型為 WEAPON
                Suit.HEARTS to 12 -> {
                    val existingWeapon = equipment.filter { it.type == "WEAPON" }.firstOrNull()
                    if (existingWeapon != null) {
                        equipment.remove(existingWeapon)
                    }
                    equipment.add(GreenDragonBlade(general))
                    println("${name} 裝備了${GreenDragonBlade(general).name}（類型：${GreenDragonBlade(general).type}）")
                    handCards.remove(card)
                    deck.discard(listOf(card))
                }

                // 諸葛連弩：梅花A，類型為 WEAPON
                Suit.CLUBS to 1 -> {
                    val existingWeapon = equipment.filter { it.type == "WEAPON" }.firstOrNull()
                    if (existingWeapon != null) {
                        equipment.remove(existingWeapon)
                    }
                    equipment.add(ZhugeCrossbow(general))
                    println("${name} 裝備了${ZhugeCrossbow(general).name}（類型：${ZhugeCrossbow(general).type}）")
                    handCards.remove(card)
                    deck.discard(listOf(card))
                }

                // 青龍刃：黑桃5，類型為 WEAPON
                Suit.SPADES to 5 -> {
                    val existingWeapon = equipment.filter { it.type == "WEAPON" }.firstOrNull()
                    if (existingWeapon != null) {
                        equipment.remove(existingWeapon)
                    }
                    equipment.add(QingLongBlade(general))
                    println("${name} 裝備了${QingLongBlade(general).name}（類型：${QingLongBlade(general).type}）")
                    handCards.remove(card)
                    deck.discard(listOf(card))
                }

                // 丈八蛇矛：黑桃Q，類型為 WEAPON
                Suit.SPADES to 12 -> {
                    val existingWeapon = equipment.filter { it.type == "WEAPON" }.firstOrNull()
                    if (existingWeapon != null) {
                        equipment.remove(existingWeapon)
                    }
                    equipment.add(SerpentHalberd(general))
                    println("${name} 裝備了${SerpentHalberd(general).name}（類型：${SerpentHalberd(general).type}）")
                    handCards.remove(card)
                    deck.discard(listOf(card))
                }

                // 雌雄雙股劍：黑桃2，類型為 WEAPON
                Suit.SPADES to 2 -> {
                    val existingWeapon = equipment.filter { it.type == "WEAPON" }.firstOrNull()
                    if (existingWeapon != null) {
                        equipment.remove(existingWeapon)
                    }
                    equipment.add(GenderDoubleSwords(general))
                    println("${name} 裝備了${GenderDoubleSwords(general).name}（類型：${GenderDoubleSwords(general).type}）")
                    handCards.remove(card)
                    deck.discard(listOf(card))
                }

                // 青釭劍：黑桃6，類型為 WEAPON
                Suit.SPADES to 6 -> {
                    val existingWeapon = equipment.filter { it.type == "WEAPON" }.firstOrNull()
                    if (existingWeapon != null) {
                        equipment.remove(existingWeapon)
                    }
                    equipment.add(BlueSteelBlade(general))
                    println("${name} 裝備了${BlueSteelBlade(general).name}（類型：${BlueSteelBlade(general).type}）")
                    handCards.remove(card)
                    deck.discard(listOf(card))
                }

//                // 麒麟弓：紅心5，類型為 WEAPON
//                Suit.HEARTS to 5 -> {
//                    val existingWeapon = equipment.filter { it.type == "WEAPON" }.firstOrNull()
//                    if (existingWeapon != null) {
//                        equipment.remove(existingWeapon)
//                    }
//                    equipment.add(UnicornBow(general))
//                    println("${name} 裝備了麒麟弓（類型：WEAPON）")
//                    handCards.remove(card)
//                    deck.discard(listOf(card))
//                }
//
//                // 貫石斧：方塊5，類型為 WEAPON
//                Suit.DIAMONDS to 5 -> {
//                    val existingWeapon = equipment.filter { it.type == "WEAPON" }.firstOrNull()
//                    if (existingWeapon != null) {
//                        equipment.remove(existingWeapon)
//                    }
//                    equipment.add(RockCleavingAxe(general))
//                    println("${name} 裝備了貫石斧（類型：WEAPON）")
//                    handCards.remove(card)
//                    deck.discard(listOf(card))
//                }
//
//                // 方天畫戟：方塊Q，類型為 WEAPON
//                Suit.DIAMONDS to 12 -> {
//                    val existingWeapon = equipment.filter { it.type == "WEAPON" }.firstOrNull()
//                    if (existingWeapon != null) {
//                        equipment.remove(existingWeapon)
//                    }
//                    equipment.add(HeavenScorcherHalberd(general))
//                    println("${name} 裝備了方天畫戟（類型：WEAPON）")
//                    handCards.remove(card)
//                    deck.discard(listOf(card))
//                }
//
//                // 大宛：黑桃K，類型為 MOUNT
//                Suit.SPADES to 13 -> {
//                    val existingMount = equipment.filter { it.type == "MOUNT" }.firstOrNull()
//                    if (existingMount != null) {
//                        equipment.remove(existingMount)
//                    }
//                    equipment.add(DaYuan(general))
//                    println("${name} 裝備了大宛（類型：MOUNT）")
//                    handCards.remove(card)
//                    deck.discard(listOf(card))
//                }
//
//                // 絕影：黑桃5，類型為 MOUNT
//                Suit.SPADES to 5 -> {
//                    val existingMount = equipment.filter { it.type == "MOUNT" }.firstOrNull()
//                    if (existingMount != null) {
//                        equipment.remove(existingMount)
//                    }
//                    equipment.add(TheShadow(general))
//                    println("${name} 裝備了絕影（類型：MOUNT）")
//                    handCards.remove(card)
//                    deck.discard(listOf(card))
//                }
//
//                // 爪黃飛電：紅心K，類型為 MOUNT
//                Suit.HEARTS to 13 -> {
//                    val existingMount = equipment.filter { it.type == "MOUNT" }.firstOrNull()
//                    if (existingMount != null) {
//                        equipment.remove(existingMount)
//                    }
//                    equipment.add(ZhuHuangFeiDian(general))
//                    println("${name} 裝備了爪黃飛電（類型：MOUNT）")
//                    handCards.remove(card)
//                    deck.discard(listOf(card))
//                }
//
//                // 赤兔：紅心5，類型為 MOUNT
//                Suit.HEARTS to 5 -> {
//                    val existingMount = equipment.filter { it.type == "MOUNT" }.firstOrNull()
//                    if (existingMount != null) {
//                        equipment.remove(existingMount)
//                    }
//                    equipment.add(RedHare(general))
//                    println("${name} 裝備了赤兔（類型：MOUNT）")
//                    handCards.remove(card)
//                    deck.discard(listOf(card))
//                }
//
//                // 的盧：紅心K，類型為 MOUNT
//                Suit.HEARTS to 13 -> {
//                    val existingMount = equipment.filter { it.type == "MOUNT" }.firstOrNull()
//                    if (existingMount != null) {
//                        equipment.remove(existingMount)
//                    }
//                    equipment.add(DiLu(general))
//                    println("${name} 裝備了的盧（類型：MOUNT）")
//                    handCards.remove(card)
//                    deck.discard(listOf(card))
//                }
//
//                // 紫騂：方塊K，類型為 MOUNT
//                Suit.DIAMONDS to 13 -> {
//                    val existingMount = equipment.filter { it.type == "MOUNT" }.firstOrNull()
//                    if (existingMount != null) {
//                        equipment.remove(existingMount)
//                    }
//                    equipment.add(ZiXing(general))
//                    println("${name} 裝備了紫騂（類型：MOUNT）")
//                    handCards.remove(card)
//                    deck.discard(listOf(card))
//                }

//                // 其他裝備...
//                else -> {
//                    println("未知裝備卡：${card}")
//                }
            }
        }
    }
}