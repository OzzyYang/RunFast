package game;

import java.util.ArrayList;

public enum CardType {

    单张(1, 1), 对子(1, 1), 顺子(1, 1), 连对(1, 1), 飞机(1, 1), 三带二(1, 1), 四带三(1, 1), 炸弹(1, 1);

    private int typeSize, compNum;

    private CardType(int typeSize, int compNum) {
        this.typeSize = typeSize;
        this.compNum = compNum;

    }

    public int getTypeSize() {
        return typeSize;
    }

    private void setTypeSize(int typeSize) {
        this.typeSize = typeSize;
    }

    public int getCompNum() {
        return compNum;
    }

    private void setCompNum(int compNum) {
        this.compNum = compNum;
    }

    public static boolean canCardPlay(Poker[] playedCards, Poker[] handCards) {
        CardType cardType = judgeCardType(playedCards);
        int cardTypeSize = cardType.getTypeSize(), cardCompNum = cardType.getCompNum();
        switch (cardType) {
            case 单张: {
                int i = handCards.length - 1;
                do {
                    if (handCards[i] == null) {
                        i--;
                        continue;
                    }
                    if (handCards[i].getCardSize() > cardTypeSize) {
                        return true;
                    } else {
                        break;
                    }
                } while (true);
                break;
            }
            case 对子: {
                if (CardType.findDiepai(handCards, new ArrayList<>(), 2) > cardTypeSize) {
                    return true;
                }
                break;
            }
            case 顺子: {
                if (CardType.findShunzi(handCards, new ArrayList<>(), cardCompNum) > cardTypeSize) {
                    return true;
                }
                break;
            }
            case 连对: {
                if (CardType.findLiandui(handCards, new ArrayList<>(), cardCompNum) > cardTypeSize) {
                    return true;
                }
                break;
            }
            case 飞机: {
                if (CardType.findFeiji(handCards, new ArrayList<>(), cardCompNum) > cardTypeSize) {
                    return true;
                }

                break;
            }
            case 三带二: {
                if (CardType.findDiepai(handCards, new ArrayList<>(), 3) > cardTypeSize) {
                    return true;
                }

                break;
            }
            case 四带三: {
                if (CardType.findDiepai(handCards, new ArrayList<>(), 4) > cardTypeSize) {
                    return true;
                }
                break;
            }
            case 炸弹: {
                if (CardType.findDiepai(handCards, new ArrayList<>(), 4) > cardTypeSize) {
                    return true;
                }
                break;
            }
        }
        return false;

    }

    /**
     * 寻找玩家手牌中的数量为指定值的飞机
     * 
     * @param cards   玩家的手牌
     * @param list    存放遍历到的飞机列表
     * @param compNum 每一个飞机的数量
     * @return 返回遍历到最大的飞机的最小牌的值
     */
    public static int findFeiji(Poker[] cards, ArrayList<Poker[]> list, int compNum) {
        ArrayList<Poker[]> sanzhangList = new ArrayList<>();
        if (CardType.findDiepai(cards, sanzhangList, 3) == 0 | sanzhangList.size() < compNum) {
            return 0;
        }

        int count = 1, sanzhangTag = 3, startTag = 1;
        Poker poker = (sanzhangList.get(0))[0];
        Poker[] sanzhang = new Poker[compNum * 3];
        sanzhang[0] = sanzhangList.get(0)[0];
        sanzhang[1] = sanzhangList.get(0)[1];
        sanzhang[2] = sanzhangList.get(0)[2];
        do {
            for (int i = startTag; i < sanzhangList.size(); i++) {
                if ((sanzhangList.get(i))[0].getCardSize() == poker.getCardSize() + 1) {
                    count++;
                    sanzhang[sanzhangTag++] = sanzhangList.get(i)[0];
                    sanzhang[sanzhangTag++] = sanzhangList.get(i)[1];
                    sanzhang[sanzhangTag++] = sanzhangList.get(i)[2];
                    poker = sanzhangList.get(i)[0];
                    if (count == compNum) {
                        startTag = i - compNum + 3;
                        list.add(sanzhang.clone());
                        poker = sanzhangList.get(startTag - 1)[0];
                        sanzhang[0] = sanzhangList.get(startTag - 1)[0];
                        sanzhang[1] = sanzhangList.get(startTag - 1)[1];
                        sanzhang[2] = sanzhangList.get(startTag - 1)[2];
                        sanzhangTag = 3;
                        count = 1;
                        break;
                    }
                } else {
                    poker = sanzhangList.get(i)[0];
                    if (i == sanzhangList.size() - 1) {
                        startTag = sanzhangList.size();
                    } else {
                        startTag = i + 1;
                    }
                    sanzhang[0] = sanzhangList.get(i)[0];
                    sanzhang[1] = sanzhangList.get(i)[1];
                    sanzhang[2] = sanzhangList.get(i)[2];
                    sanzhangTag = 3;
                    count = 1;
                }
            }
        } while (startTag != sanzhangList.size());

        if (list.isEmpty()) {
            return 0;
        } else {
            return list.get(list.size() - 1)[0].getCardSize();// 返回最大的飞机的最小的三张的值
        }

    }

    /**
     * 寻找玩家手牌中的数量为指定值的连对
     * 
     * @param cards   玩家的手牌
     * @param list    存放遍历到的连对列表
     * @param compNum 每一个连对的数量
     * @return 返回遍历到最大的连对的最小牌的值
     */
    public static int findLiandui(Poker[] cards, ArrayList<Poker[]> list, int compNum) {
        ArrayList<Poker[]> duiziList = new ArrayList<>();
        if (CardType.findDiepai(cards, duiziList, 2) == 0 | duiziList.size() < compNum) {
            return 0;
        }

        int count = 1, lianduiTag = 2, startTag = 1;
        Poker poker = (duiziList.get(0))[0];
        Poker[] liandui = new Poker[compNum * 2];
        liandui[0] = duiziList.get(0)[0];
        liandui[1] = duiziList.get(0)[1];
        do {
            for (int i = startTag; i < duiziList.size(); i++) {
                if ((duiziList.get(i))[0].getCardSize() == poker.getCardSize() + 1) {
                    count++;
                    liandui[lianduiTag++] = duiziList.get(i)[0];
                    liandui[lianduiTag++] = duiziList.get(i)[1];
                    poker = duiziList.get(i)[0];
                    if (count == compNum) {
                        startTag = i - compNum + 3;
                        list.add(liandui.clone());
                        poker = duiziList.get(startTag - 1)[0];
                        liandui[0] = duiziList.get(startTag - 1)[0];
                        liandui[1] = duiziList.get(startTag - 1)[1];
                        lianduiTag = 2;
                        count = 1;
                        break;
                    }
                } else {
                    poker = duiziList.get(i)[0];
                    if (i == duiziList.size() - 1) {
                        startTag = duiziList.size();
                    } else {
                        startTag = i + 1;
                    }
                    liandui[0] = duiziList.get(i)[0];
                    liandui[1] = duiziList.get(i)[1];
                    lianduiTag = 2;
                    count = 1;
                }
            }
        } while (startTag != duiziList.size());

        if (list.isEmpty()) {
            return 0;
        } else {
            return list.get(list.size() - 1)[0].getCardSize();// 返回最大的连对的最小的对子的值
        }

    }

    /**
     * 寻找玩家手牌中数量为指定值的顺子
     * 
     * @param cards   玩家的手牌
     * @param list    存放遍历到的顺子列表
     * @param compNum 每一个顺子的数量
     * @return 返回遍历到最大的顺子的最小牌的值
     */
    public static int findShunzi(Poker[] cards, ArrayList<Poker[]> list, int compNum) {
        if (cards.length < 5 | compNum < 5) {
            return 0;
        }
        int count = 1, cardCompTag = 0, startTag = 1;
        Poker[] shunziComp = new Poker[compNum];
        Poker poker = null;
        for (int i = 0; i < cards.length; i++) {
            if (cards[i] != null) {
                poker = shunziComp[cardCompTag++] = cards[i];
                startTag = i + 1;
                break;
            }
        }

        while (startTag != cards.length - 1) {
            for (int i = startTag; i < cards.length; i++) {
                if (cards[i] == null) {
                    continue;
                }
                // 牌的大小重复则跳过
                if (cards[i].getCardSize() == poker.getCardSize()) {
                    if (i == cards.length - 1) {
                        startTag = cards.length - 1;
                    } else {
                        startTag = i + 1;
                    }
                    continue;
                }
                if (cards[i] != Poker.红心2 && cards[i].getCardSize() == poker.getCardSize() + 1) {
                    count++;
                    poker = cards[i];
                    shunziComp[cardCompTag++] = poker;
                    if (count == compNum) {
                        list.add(shunziComp.clone());
                        for (int j = cards.length - 1; j >= 0; j--) {
                            if (cards[j] == null) {
                                continue;
                            }
                            if (cards[j].getCardSize() == shunziComp[0].getCardSize()) {
                                startTag = j + 2;
                                break;
                            }
                        }
                        poker = cards[startTag - 1];
                        shunziComp[0] = poker;
                        cardCompTag = 1;
                        count = 1;
                        break;
                    }
                } else {
                    poker = cards[i];
                    if (i == cards.length - 1) {
                        startTag = cards.length - 1;
                    } else {
                        startTag = i + 1;
                    }
                    shunziComp[0] = cards[i];
                    cardCompTag = 1;
                    count = 1;
                }
            }

        }
        if (list.isEmpty()) {
            return 0;
        } else {
            return list.get(list.size() - 1)[0].getCardSize();
        }

    }

    /**
     * 此方法用于遍历玩家手牌寻找对应数量的叠牌
     * 
     * @param cards    玩家的手牌
     * @param list     存放遍历到的叠牌
     * @param aimCount 叠牌的数量
     * @return 返回遍历到组合的最大值
     */
    public static int findDiepai(Poker[] cards, ArrayList<Poker[]> list, int aimCount) {
        int maxSize = 0, count = 1, cardCompTag = 0;
        Poker[] cardComp = new Poker[aimCount];
        Poker poker = null;
        for (int i = 0; i < cards.length; i++) {
            if (cards[i] != null) {
                poker = cardComp[cardCompTag++] = cards[i];
                break;
            }
        }
        for (int i = 1; i < cards.length; i++) {
            if (cards[i] == null) {
                continue;
            }
            if (poker.getCardSize() == cards[i].getCardSize() && poker != cards[i]) {
                count++;
                cardComp[cardCompTag++] = poker = cards[i];
                if (count == aimCount) {
                    cardCompTag = 0;
                    maxSize = poker.getCardSize();
                    list.add(cardComp.clone());
                }
            } else {
                poker = cards[i];
                count = 1;
                cardCompTag = 0;
                poker = cardComp[cardCompTag++] = cards[i];
            }
        }
        return maxSize;
    }

    public static CardType judgeCardType(Poker[] cards) {
        switch (cards.length) {
            case 1: {
                单张.setTypeSize(cards[0].getCardSize());
                return 单张;
            }
            case 2: {
                if (CardType.goThroughCards(cards) == 2 && cards[0].getCardSize() == cards[1].getCardSize()) {
                    对子.setTypeSize(cards[0].getCardSize());
                    return 对子;
                }
                break;
            }
            case 3: {
                // 三张且手牌只剩三张牌时
                if (CardType.goThroughCards(cards) == 3) {
                    三带二.setTypeSize(cards[0].getCardSize());
                    return 三带二;
                }
                break;
            }
            case 4: {
                if (CardType.isLiandui(cards)) {
                    连对.setTypeSize(cards[0].getCardSize());
                    连对.setCompNum(2);// 返回有几个对子
                    return 连对;
                }
                // 三张且手牌只剩四张牌时
                if (CardType.goThroughCards(cards) == 3) {
                    Poker card = cards[0];
                    for (int i = 1; i < cards.length; i++) {
                        if (card.getCardSize() == cards[i].getCardSize()) {
                            三带二.setTypeSize(card.getCardSize());
                            break;
                        } else {
                            card = cards[i];
                        }
                    }
                    return 三带二;
                }
                if (CardType.goThroughCards(cards) == 4) {
                    炸弹.setTypeSize(cards[0].getCardSize());
                    return 炸弹;
                }
                break;
            }
            case 5: {
                if (CardType.goThroughCards(cards) == 3) {
                    Poker card = cards[0];
                    for (int i = 1; i < cards.length; i++) {
                        if (card.getCardSize() == cards[i].getCardSize()) {
                            三带二.setTypeSize(card.getCardSize());
                            break;
                        } else {
                            card = cards[i];
                        }
                    }
                    return 三带二;
                }
                if (CardType.isShunzi(cards)) {
                    顺子.setTypeSize(cards[0].getCardSize());
                    顺子.setCompNum(cards.length);// 返回有几个顺子
                    return 顺子;
                }
                break;
            }
            default: {
                if (CardType.isLiandui(cards)) {
                    连对.setTypeSize(cards[0].getCardSize());
                    连对.setCompNum(cards.length / 2);// 返回有几个对子
                    return 连对;
                }
                if (CardType.isShunzi(cards)) {
                    顺子.setTypeSize(cards[0].getCardSize());
                    return 顺子;
                }

                Integer minThree = 0, threeCount = 0;
                if (CardType.isFeiji(cards, minThree, threeCount)) {
                    飞机.setTypeSize(minThree);
                    飞机.setCompNum(threeCount);
                    return 飞机;
                }

                Integer fourSize = 0;
                if (CardType.isSidaisan(cards, fourSize)) {
                    四带三.setTypeSize(fourSize);
                    return 四带三;
                }

            }
        }

        return null;

    }

    public static int goThroughCards(Poker[] cards) {
        int result = 1, count = 1;
        Poker poker = cards[0];
        for (int i = 1; i < cards.length; i++) {
            if (poker.getCardSize() == cards[i].getCardSize()) {
                count++;
                if (count > result) {
                    result = count;
                }
            } else {
                count = 1;
                poker = cards[i];
            }
        }
        return result;
    }

    public static boolean isShunzi(Poker[] cards) {
        if (cards.length < 5 | cards[cards.length - 1] == Poker.红心2) {
            return false;
        }
        if (CardType.goThroughCards(cards) == 1) {
            for (int i = 0; i < cards.length - 1; i++) {
                // 只要一个不连续，则说明不包含顺子
                if (cards[i].getCardSize() != cards[i + 1].getCardSize() - 1) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public static boolean isLiandui(Poker[] cards) {
        if (cards.length % 2 != 0) {
            return false;
        } else {
            for (int i = 0; i < cards.length - 2; i = i + 2) {
                // 只要一个不连续，则说明不包含顺子
                if (cards[i].getCardSize() != cards[i + 1].getCardSize()) {
                    return false;
                }
                if (cards[i + 1].getCardSize() != cards[i + 2].getCardSize() - 1) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * 判断牌是不是飞机
     * 
     * @param cards      要判断的牌组
     * @param minThree   记录飞机中最小三张的值
     * @param threeCount 记录飞机中有多少个三张
     */
    public static boolean isFeiji(Poker[] cards, Integer minThree, Integer threeCount) {
        int count = 1;
        Poker poker = cards[0];
        minThree = 0;
        threeCount = 0;
        for (int i = 1; i < cards.length; i++) {
            if (poker.getCardSize() == cards[i].getCardSize()) {
                count++;
                if (count == 3) {
                    threeCount++;
                    // 记录飞机中最小的三张的值
                    if (threeCount == 1) {
                        minThree = poker.getCardSize();
                    }
                    // 如果一个三张的下面三张牌不是三张，说明飞机结束，直接退出此循环
                    if (i < cards.length - 4 && cards[i].getCardSize() != cards[i + 3].getCardSize() - 1) {
                        break;
                    }
                    count = 1;
                }
            } else {
                count = 1;
                poker = cards[i];
            }
        }
        if (threeCount >= 2) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 判断是不是四带三
     * 
     * @param cards    要判断的牌组
     * @param fourSize 炸弹的值
     */
    public static boolean isSidaisan(Poker[] cards, Integer fourSize) {
        if (CardType.goThroughCards(cards) == 4 && cards.length == 7) {
            int count = 1;
            Poker poker = cards[0];
            for (int i = 1; i < cards.length; i++) {
                if (poker.getCardSize() == cards[i].getCardSize()) {
                    count++;
                    if (count == 4) {
                        fourSize = poker.getCardSize();
                        return true;
                    }
                } else {
                    count = 1;
                    poker = cards[i];
                }
            }
        }
        return false;
    }

}