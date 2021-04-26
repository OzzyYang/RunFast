package player;

import game.*;

public class Player {
    protected Poker[] handCards;

    public Player() {

    }

    public Player(Poker[] handCards) {
        this.handCards = handCards;
    }

    public void takeHandCards(Poker[] cards) {
        this.handCards = cards;
        sortCard(cards, "ASC");
    }

    public Poker[] getHandCards() {
        return handCards.clone();
    }

    /**
     * 对牌组进行冒泡排序
     *
     * @param cards     需要排序的牌组
     * @param sortOrder ASC表示升序 DES表示降序
     */
    public static void sortCard(Poker[] cards, String sortOrder) {
        if (cards == null) return;
        if (!(sortOrder.equals("ASC") || sortOrder.equals("DES"))) {
            System.out.println("SortOrder ERROR!");
        }
        for (int i = 0; i < cards.length - 1; i++) {
            for (int j = 0; j < cards.length - 1; j++) {
                if ((sortOrder.equals("ASC") && cards[j].getCardSize() > cards[j + 1].getCardSize())
                        || (sortOrder.equals("DES") && cards[j].getCardSize() < cards[j + 1].getCardSize())) {
                    Poker cache = cards[j];// 交换数值时的缓存变量
                    cards[j] = cards[j + 1];
                    cards[j + 1] = cache;
                }
            }
        }
    }


    /**
     * 输出玩家的手牌
     *
     * @param horizontal true-横向输出，false-纵向输出
     */
    public void readCard(boolean horizontal) {
        if (!horizontal) {
            readCard();
            return;
        }
        for (Poker handCard : handCards) {
            if (handCard == null) {
                continue;
            }
            System.out.print(handCard.getCardName() + " ");
        }
        System.out.println("\n------------------------------------------------");
    }

    /**
     * 纵向输出手牌
     *
     * @deprecated 在IDEA中输出有问题
     */
    private void readCard() {
        System.out.println("------------------------------------------------");
        for (int i = 0; i < handCards.length; i++) {
            if (handCards[i] != null) {
                switch (handCards[i].getCardSuit()) {
                    case "Spade":
                        System.out.print("黑 ");
                        break;
                    case "Heart":
                        System.out.print("红 ");
                        break;
                    case "Club":
                        System.out.print("梅 ");
                        break;
                    case "Diamond":
                        System.out.print("方 ");
                        break;
                }
            }
        }
        System.out.println();
        for (int i = 0; i < handCards.length; i++) {
            if (handCards[i] != null) {
                switch (handCards[i].getCardSuit()) {
                    case "Spade":
                        System.out.print("桃 ");
                        break;
                    case "Heart":
                        System.out.print("心 ");
                        break;
                    case "Club":
                        System.out.print("花 ");
                        break;
                    case "Diamond":
                        System.out.print("块 ");
                        break;
                }
            }
        }
        System.out.println();
        for (int i = 0; i < handCards.length; i++) {
            if (handCards[i] != null) {
                switch (handCards[i].getCardSize()) {
                    case 10:
                        System.out.print("10");
                        break;
                    case 11:
                        System.out.print(" J");
                        break;
                    case 12:
                        System.out.print(" Q");
                        break;
                    case 13:
                        System.out.print(" K");
                        break;
                    case 14:
                        System.out.print(" A");
                        break;
                    case 15:
                        System.out.print(" 2 ");
                        break;
                    default: {
                        System.err.print(handCards[i].getCardSize() + " ");
                        break;
                    }
                }
            }
        }
        System.out.println();
        System.out.println("------------------------------------------------");
    }

    public boolean haveCards(String[] playCardsStr, Poker[] playedCards) {
        int[] playCardsSize = new int[playCardsStr.length];
        for (int i = 0; i < playCardsSize.length; i++) {
            // 如果是数字牌，但是不在2-10之间的牌则不存在
            if (playCardsStr[i].matches("[0-9]{1,2}")
                    && (Integer.parseInt(playCardsStr[i]) > 10 || Integer.parseInt(playCardsStr[i]) < 2)) {
                return false;
            }
            switch (playCardsStr[i]) {
                case "J":
                    playCardsSize[i] = 11;
                    break;
                case "Q":
                    playCardsSize[i] = 12;
                    break;
                case "K":
                    playCardsSize[i] = 13;
                    break;
                case "A":
                    playCardsSize[i] = 14;
                    break;
                case "2":
                    playCardsSize[i] = 15;
                    break;
                case "10":
                    playCardsSize[i] = 10;
                    break;
                default:
                    playCardsSize[i] = (Integer.parseInt(playCardsStr[i]));
            }
        }
        Poker[] handCardsNew = this.handCards.clone();
        PlayCardsSize:
        for (int i = 0; i < playCardsSize.length; i++) {
            HandCardsNew:
            for (int j = 0; j < handCardsNew.length; j++) {
                if (handCardsNew[j] == null) {
                    continue HandCardsNew;
                }
                if (playCardsSize[i] == handCards[j].getCardSize()) {
                    playedCards[i] = handCardsNew[j];
                    handCardsNew[j] = null;
                    continue PlayCardsSize;
                }
                if (j == handCards.length - 1) {
                    playedCards = null;
                    return false;
                }
            }
        }
        this.sortCard(playedCards, "ASC");
        return true;
    }

    public void playCards(Poker[] playCards) {
        PlayCards:
        for (int i = 0; i < playCards.length; i++) {
            HandCards:
            for (int j = 0; j < handCards.length; j++) {
                if (handCards[j] == null) {
                    continue HandCards;
                }
                if (playCards[i] == handCards[j]) {
                    handCards[j] = null;
                    continue PlayCards;
                }
            }
        }
    }

    /**
     * 拿回不符合规则的牌
     *
     * @param playedCards 打算出但是不符合规则的牌
     */
    public void handCardsBack(Poker[] playedCards) {
        for (int i = 0, j = -1; i < playedCards.length; i++) {

            while (true) {
                if (handCards[++j] == null) {
                    handCards[j] = playedCards[i];
                    break;
                }
            }
        }
        this.sortCard(this.handCards, "ASC");
    }

    /**
     * 返回剩余手牌数
     */
    public int getHandCardsNum() {
        int i = 0;
        for (Poker poker : handCards) {
            if (poker != null) {
                i++;
            }
        }
        return i;
    }

}