package src.game;

import java.io.*;
import java.util.*;

import src.cards.*;
import src.player.*;

/**
 * 跑得快游戏主体，单例类
 */
public class Game {
    private RealPlayer[] realPlayers = new RealPlayer[3];
    Poker[] poker = Poker.values();
    private static Game game = new Game();
    private ArrayList<Poker[]> playedCardsList = new ArrayList<>();
    private ArrayList<Integer> playedRoundList = new ArrayList<>();
    private int roundTag = 0;
    private int roundCount = 1;
    // private int playerFisrtTag = 1;

    private Game() {
        realPlayers[0] = new RealPlayer();
        realPlayers[1] = new RealPlayer();
        realPlayers[2] = new RealPlayer();
    }

    public static Game getGame() {
        return game;
    }

    public void startGame() throws IOException {
        roundTag = 1;

        System.out.println("\n\n------------\n*游戏开始！*\n------------");
        this.deal();
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String inputStr = null;

        Round:
        do {
            System.out.println("\n------------------玩家" + roundTag + "的牌为-------------------");
            realPlayers[roundTag - 1].readCard(true);
            switch (roundCount) {
                case 1: {
                    System.out.print("请玩家" + roundTag + "输入指令执行操作(1.看牌 2.出牌):");
                    break;
                }
                case 2: {
                    if (CardType.canCardPlay(playedCardsList.get(0), realPlayers[roundTag - 1].getHandCards())) {
                        System.out.print("请玩家" + roundTag + "输入指令执行操作(1.看牌 2.出牌):");
                    } else {
                        System.out.print("请玩家" + roundTag + "输入指令执行操作(1.看牌 3.要不起):");
                    }
                    break;
                }
                case 3: {
                    int i = 1;
                    if (playedCardsList.get(i) == null) {
                        i = 0;
                    }
                    if (CardType.canCardPlay(playedCardsList.get(i), realPlayers[roundTag - 1].getHandCards())) {
                        System.out.print("请玩家" + roundTag + "输入指令执行操作(1.看牌 2.出牌):");
                    } else {
                        System.out.print("请玩家" + roundTag + "输入指令执行操作(1.看牌 3.要不起):");
                    }
                    break;

                }
                default: {
                    int i = playedCardsList.size() - 1;
                    do {
                        if (playedCardsList.get(i) == null) {
                            i--;
                        } else {
                            break;
                        }
                    } while (i > playedCardsList.size() - 3);

                    if (i == (playedCardsList.size() - 3)
                            || CardType.canCardPlay(playedCardsList.get(i), realPlayers[roundTag - 1].getHandCards())) {
                        System.out.print("请玩家" + roundTag + "输入指令执行操作(1.看牌 2.出牌):");
                    } else {
                        System.out.print("请玩家" + roundTag + "输入指令执行操作(1.看牌 3.要不起):");
                    }
                    break;
                }
            }

            while ((inputStr = br.readLine()) != null) {
                if (inputStr.matches("[1-3]")) {
                    if (inputStr.equals("1")) {
                        continue Round;
                    } else if (inputStr.equals("2")) {
                        System.out.print("请输入需要出的牌，以“,”分隔：");
                        PlayCards:
                        while ((inputStr = br.readLine()) != null) {
                            inputStr = inputStr.replaceAll(" ", "");// 删除字符串中多余的空格
                            if (inputStr.matches("(([0-9]{1,2}|(A|J|K|Q)),)*([0-9]{1,2}|(A|J|K|Q))")) {
                                String[] playCardsStr = inputStr.split(",");
                                Poker[] playedCards = new Poker[playCardsStr.length];
                                String playInfo = new String();
                                if (realPlayers[roundTag - 1].haveCards(playCardsStr, playedCards)) {
                                    if (playedCardsSuccess(playedCards.clone(), playInfo)) {
                                        realPlayers[roundTag - 1].playCards(playedCards);
                                        System.out
                                                .println("-----------------玩家" + roundTag + "所出的牌为-----------------\n");
                                        System.out.print(CardType.judgeCardType(playedCards) + "：");
                                        for (Poker poker : playedCards) {
                                            System.out.print(poker.getCardName() + " ");
                                        }
                                        System.out.println("\n\n-------------------------------------------------");

                                        //System.out.print(playInfo);
                                        if (this.isGameEnd(realPlayers[roundTag - 1])) {
                                            this.endGame();
                                        }
                                    } else {
                                        System.out.println(
                                                "------------------------------------\n*出牌失败，您所选择的牌不符合规则！*\n------------------------------------");
                                        continue Round;
                                    }

                                } else {
                                    System.out.print("*出牌失败，您的手牌没有完整包含这些牌* 请重新输入您需要出的牌：");
                                    continue PlayCards;
                                }
                                break PlayCards;
                            } else {
                                System.out.print("*牌组输入不合法*，请重新输入您需要出的牌：");
                                continue PlayCards;
                            }
                        }
                        if (roundTag == 3) {
                            roundTag = 1;
                        } else {
                            roundTag++;
                        }
                        continue Round;
                    } else {
                        playedCardsSuccess(null, null);
                        if (roundTag == 3) {
                            roundTag = 1;
                        } else {
                            roundTag++;
                        }
                        continue Round;
                    }
                } else {
                    System.out.print("*指令输入无效* 请重新输入：");
                }
            }
        } while (roundTag != 0);
    }

    private void endGame() {
        System.out.println("*游戏结束，玩家" + roundTag + "获得胜利！*");
        System.exit(0);
    }

    /**
     * 判断游戏是否结束
     *
     * @param realPlayer 当前出牌的玩家
     * @return
     */
    private boolean isGameEnd(RealPlayer realPlayer) {
        Poker[] playerCards = realPlayer.getHandCards();
        for (Poker poker : playerCards) {
            if (poker != null) {
                return false;
            }
        }
        return true;
    }


    /**
     * 发牌
     */
    private void deal() {
        Poker[][] playerCards = {new Poker[16], new Poker[16], new Poker[16]};
        shuffleCards(poker);
        for (int i = 0, j = 0; i < poker.length; i = i + 3, j++) {
            playerCards[0][j] = poker[i];
            playerCards[1][j] = poker[i + 1];
            playerCards[2][j] = poker[i + 2];
        }
        realPlayers[0].takeHandCards(playerCards[0]);
        realPlayers[1].takeHandCards(playerCards[1]);
        realPlayers[2].takeHandCards(playerCards[2]);
    }

    /**
     * 洗牌
     *
     * @param poker 需要洗的牌组
     */
    private static void shuffleCards(Poker[] poker) {
        int shuffleTimes = 500;//打乱次数
        do {
            Poker cachePoker = null;// 交换数值时的缓存变量
            for (int i = 0; i < poker.length - 1; i++) {
                double rand = Math.random() * (poker.length - 1);
                int j = (int) rand;
                cachePoker = poker[j];
                poker[j] = poker[j + 1];
                poker[j + 1] = cachePoker;
            }

        } while ((shuffleTimes--) != 0);
    }

    private boolean playedCardsSuccess(Poker[] playedCards, String playInfo) {
        if (playedCards == null) {// 不要
            this.playedCardsList.add(playedCards);
            this.playedRoundList.add(roundTag);
            roundCount++;
            return true;
        } else {
            if (playCardsRule(playedCards, playInfo)) {
                this.playedCardsList.add(playedCards);
                this.playedRoundList.add(roundTag);
                roundCount++;
                return true;
            } else {
                return false;
            }
        }
    }

    private boolean playCardsRule(Poker[] playedCards, String playInfo) {
        boolean result = false;
        CardType cardType;
        int cardTypSize, cardCompNum;

        switch (playedCards.length) {
            case 3: {
                // 三张且手牌只剩三张牌时
                if ((cardType = CardType.judgeCardType(playedCards)) != null) {
                    // System.out.println(cardType);
                    if (realPlayers[roundTag - 1].getHandCardsNum() == 3) {
                        result = true;
                    }
                }
                break;
            }
            case 4: {
                if ((cardType = CardType.judgeCardType(playedCards)) != null) {
                    // System.out.println(cardType);
                    result = true;
                    // 排除掉三张且手牌不只剩四张牌的情况
                    if (cardType == CardType.三带二 && realPlayers[roundTag - 1].getHandCardsNum() != 4) {
                        result = false;
                    }
                }
                break;
            }
            default: {
                if ((cardType = CardType.judgeCardType(playedCards)) != null) {
                    // System.out.println(cardType);
                    result = true;
                }
            }
        }

        if (cardType != null) {
            cardTypSize = cardType.getTypeSize();
            cardCompNum = cardType.getCompNum();
            playInfo = cardType.toString();
        } else {
            playInfo = "不符合出牌规则";
            return result;
        }

        // 如果上游或者上上游出了牌就要进行大小判断
        switch (roundCount) {
            case 1: {// 第一轮出牌不要比较大小
                return result;
            }
            case 2: {// 第二轮直接比较大小
                // 首先判断牌的种类是否相等
                if (CardType.judgeCardType(playedCardsList.get(roundCount - 2)) != cardType) {
                    result = false;
                } else {
                    // 种类相等的情况下，判断牌的只数是否相等
                    if (CardType.judgeCardType(playedCardsList.get(roundCount - 2)).getCompNum() != cardCompNum) {
                        result = false;
                    } else {
                        // 接下来，判断牌的大小
                        if (CardType.judgeCardType(playedCardsList.get(roundCount - 2)).getTypeSize() >= cardTypSize) {
                            result = false;
                        }
                    }
                }
                break;
            }
            default: {// 第三轮以及之后则要看上游或上上游有没有出牌
                if (playedCardsList.get(roundCount - 2) != null) {
                    // 首先判断牌的种类是否相等
                    if (CardType.judgeCardType(playedCardsList.get(roundCount - 2)) != cardType) {
                        result = false;
                    } else {
                        // 种类相等的情况下，判断牌的只数是否相等
                        if (CardType.judgeCardType(playedCardsList.get(roundCount - 2)).getCompNum() != cardCompNum) {
                            result = false;
                        } else {
                            // 接下来，判断牌的大小
                            if (CardType.judgeCardType(playedCardsList.get(roundCount - 2))
                                    .getTypeSize() >= cardTypSize) {
                                result = false;
                            }
                        }
                    }
                } else if (playedCardsList.get(roundCount - 3) != null && playedCardsList.get(roundCount - 2) == null) {
                    // 首先判断牌的种类是否相等
                    if (CardType.judgeCardType(playedCardsList.get(roundCount - 3)) != cardType) {
                        result = false;
                    } else {
                        // 种类相等的情况下，判断牌的只数是否相等
                        if (CardType.judgeCardType(playedCardsList.get(roundCount - 3)).getCompNum() != cardCompNum) {
                            result = false;
                        } else {
                            // 接下来，判断牌的大小
                            if (CardType.judgeCardType(playedCardsList.get(roundCount - 3))
                                    .getTypeSize() >= cardTypSize) {
                                result = false;
                            }
                        }
                    }
                } else if (playedCardsList.get(roundCount - 3) == null && playedCardsList.get(roundCount - 2) == null) {
                    // 上游和上上游都不要的情况下，则不用判断，直接出牌
                }
            }
        }
        return result;
    }

    /**
     * 此方法用于遍历一个玩家的手牌是否有炸弹
     *
     * @deprecated 测试方法
     */
    private void goThroughPlayerCards(RealPlayer realPlayer) {
        double fourTag = 0.0;
        double dealCount = 0.0;
        int fourCount = 0;
        do {
            this.deal();
            int poker = (realPlayer.getHandCards())[0].getCardSize();
            for (int i = 1; i < (realPlayer.getHandCards()).length; i++) {
                if ((realPlayer.getHandCards())[i].getCardSize() == poker) {
                    fourCount++;
                } else {
                    fourCount = 1;
                    poker = (realPlayer.getHandCards())[i].getCardSize();
                }
                if (fourCount == 4) {
                    fourTag++;
                }
            }
            System.out.println("这是第" + ((int) (++dealCount)) + "次洗牌，现在出现过" + (int) fourTag + "次炸弹，概率为"
                    + (fourTag / dealCount * 100) + "%");
        } while (fourTag != 10000.0);
    }
}