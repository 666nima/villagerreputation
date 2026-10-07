# 村民声望 / Villager Reputation 1.0.0

Minecraft **1.20.1** · Forge **47.4.6–47.x** · Java **17** · modId `villagerreputation`

独立可选模组，移植村民助手1.0.6的声望与价格玩法。**不依赖村民助手或贸易台**，不提供未来交易预览、等级锁、刷新、补货、升级加速或捕获。

## 中文：精确行为
1. `VILLAGER_HURT` 声望事件：接收该事件的村民当前生命比例严格 **<50%** 时，把原版对事件来源UUID的 `MINOR_NEGATIVE +25` 改为 `MAJOR_POSITIVE +(50-floor(生命比例×100))`；正好半血或更高保持原版。判断的是**事件接收村民**当前生命，而非统一按攻击者或受击村民推断。事件传播、数量上限、权重及衰减仍由原版gossip系统处理。
2. `VILLAGER_KILLED` 声望事件：把对事件来源UUID的原版 `MAJOR_NEGATIVE +25` 改为 `MAJOR_POSITIVE +25`。属于其他村民接收到的死亡相关事件，不是让死亡实体复活或自动给所有玩家加好感。
3. `updateSpecialPrices` 两处特殊价格增量均变为 `-abs(value)`，整数最小值原样保留。原版负声望造成的正增量（加价）因此变成折扣；原来已经为负的正声望折扣与村庄英雄折扣仍保持负值。**负面gossip并没有被全量改成正面或删除**。
4. `MerchantOffer.getCostA` 的需求项原本是 `max(0, floor(基础付款数量×demand×priceMultiplier))`；移除该需求项的零下限，使负需求也能降低价格。正需求仍可加价，未删除需求系统。
5. 低于半血的村民收到实体事件13时取消该事件的原版愤怒粒子；不替换为爱心，不改变伤害/生命或其他实体事件。
6. **保留原版最终价格 clamp(…, 1, 物品最大堆叠数)**，正常非空基础付款至少支付一个原物品，不实现免费交易。第二付款项、商品、库存次数与经验不由本模组改写。

上述目标已对照Minecraft1.20.1/Forge47.4.6实际原版映射字节码，迁移原有公式与注入目标，不凭“攻击降价”一句推断全部玩法。

## 安装、合装与存档
单人安装于客户端实例，含集成服务器。建议多人客户端和服务器安装匹配版本：gossip/付款计算在服务器决定，受伤粒子在客户端处理；仅客户端不会改变服务器价格。无新增方块、物品或自定义网络协议。

可单独使用，也可与 **villagerhelper 1.0.7** 合装；二者不再重复注入声望/需求玩法。**不要与助手1.0.6或更早版本合装**，旧助手已有相同注入。兼容并非任意整合包运行时保证，其他价格模组可能冲突。

卸载不会清除已持久化的正面gossip，也不会自动反向抵消曾写入的声望值、需求或特殊价格。原版后续计算/更新继续生效，但已有正面声望可能继续折扣；不能承诺卸载即原价。先备份存档，本模组不提供自动回滚，不改正式世界。

## English
Standalone optional extraction of helper 1.0.6's reputation/price mechanics. **No helper or trade-hub dependency**, and no preview, locks, rerolls, restock, promotion or capture functionality.

- For a `VILLAGER_HURT` event, the **receiving villager's** current health ratio strictly below 0.5 converts the source UUID's vanilla `MINOR_NEGATIVE +25` to `MAJOR_POSITIVE +(50-floor(ratio*100))`. At/above half health vanilla behavior remains. Vanilla gossip weighting, caps, propagation and decay remain.
- For `VILLAGER_KILLED`, the receiving villager records `MAJOR_POSITIVE +25` instead of `MAJOR_NEGATIVE +25` for the event source. This does not revive a dead villager or reward all players.
- Both special-price increments become `-abs(value)` (Integer.MIN_VALUE retained). Negative-reputation surcharges become discounts; existing positive-reputation/Hero discounts remain negative. This does not erase or globally invert all negative gossip.
- The demand component in `getCostA` no longer clamps to zero before addition. Negative demand can reduce cost; positive demand still increases it. The **final vanilla 1..max-stack clamp remains**, retaining at least one original nonempty payment item.
- Entity event 13's angry particles are suppressed for below-half-health villagers, without replacing them with hearts. Damage, other events, second costs, items, stock uses and XP remain unchanged.

Install on the singleplayer client/integrated server; matching multiplayer client/server installs are recommended for consistent server reputation/prices and client particles. Client-only installation does not change a remote server's prices. No custom content or network protocol is registered. Co-install only with **helper 1.0.7+**, not 1.0.6 or older which still contain the same injections. Other price mods may conflict.

Uninstalling does **not** erase persisted positive gossip, demand or special prices, nor automatically subtract prior reputation gains. Future vanilla calculations resume, but persisted reputation can still cause discounts. Back up saves; no automatic rollback is provided.

## Build / 构建
JDK17 and Python3 (`python`), initial dependency network access. No sibling project required.

`gradlew.bat --no-daemon clean build reobfJar` (Windows)

`sh gradlew --no-daemon clean build reobfJar` (Linux/macOS)

Output: `build/libs/villagerreputation-1.0.0.jar`. `tools/convert_srg.py` is required for the Mixin annotation processor and production shadow/refmap mappings.

## Verification limits / 未测边界
Compilation, reobfuscation, bytecode/metadata/refmap integrity, pure strategy checks and isolated target-transformation probes are not gameplay tests. No GUI, real transactions, save round-trips, multiplayer or arbitrary-modpack acceptance is claimed. No official instance installation or public upload performed; author performs in-game acceptance.

## License / 许可证
Original code **BSD-3-Clause**, Copyright (c) 2026 **666nima**. Original Gradle wrapper/Forge MDK notices and separate third-party licenses retained in `LICENSES` and `THIRD_PARTY_NOTICES.md`.
