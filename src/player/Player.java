package src.player;

import src.cards.Poker;

import java.util.*;

/**
 * 游戏玩家，包括真人和机器人
 *
 * @author Ozzy Yang
 * @since 2021-04-27
 */
public abstract class Player {
    protected LinkedList<Poker> handCards;
    protected final String playerName;

    public Player(LinkedList<Poker> handCards, String playerName) {
        this.handCards = handCards;
        this.playerName = playerName;
    }

    /**
     * 对手牌进行排序，使用的排序算法为插入排序
     */
    public void sortCards() {

    }

    /**
     * 出牌
     */
    public abstract void playCards();

    /**
     * 输出玩家的手牌
     *
     * @param horizontal true-横向输出，false-纵向输出
     */
    public void readCard(boolean horizontal) {
        System.out.printf("---------------------%s的手牌--------------------------\n", this.playerName);
        if (!horizontal) {
            readCard();
        } else {
            StringBuilder output = new StringBuilder();
            for (Poker handCard : handCards) {
                switch (handCard.getCardSuit()) {
                    case "Spade":
                        output.append("|").append("♠ ").append(handCard.getCardSize()).append("| ");
                        break;
                    case "Diamond":
                        output.append("|").append("♦ ").append(handCard.getCardSize()).append("| ");
                        break;
                    case "Heart":
                        output.append("|").append("♥ ").append(handCard.getCardSize()).append("| ");
                        break;
                    case "Club":
                        output.append("|").append("♣ ").append(handCard.getCardSize()).append("| ");
                        break;
                }
            }
            System.out.println(output.toString());
        }
        System.out.println("---------------------------------------------------------");
    }

    /**
     * 纵向输出手牌
     *
     * @deprecated 在IDEA中输出有问题
     */
    private void readCard() {
        Iterator<Poker> handCardsIter = handCards.iterator();
        StringBuilder line1 = new StringBuilder();
        StringBuilder line2 = new StringBuilder();
        StringBuilder line3 = new StringBuilder();
        while (handCardsIter.hasNext()) {
            Poker handCard = handCardsIter.next();
            switch (handCard.getCardSuit()) {
                case "Spade":
                    line1.append("|♠| ");
                    line2.append("|桃| ");
                    break;
                case "Heart":
                    line1.append("|♥| ");
                    line2.append("|心| ");
                    break;
                case "Club":
                    line1.append("|♣| ");
                    line2.append("|花| ");
                    break;
                case "Diamond":
                    line1.append("|♦| ");
                    line2.append("|块| ");
                    break;
            }
            switch (handCard.getCardSize()) {
                case 10:
                    line3.append("|10| ");
                    break;
                case 11:
                    line3.append("|J| ");
                    break;
                case 12:
                    line3.append("|Q| ");
                    break;
                case 13:
                    line3.append("|K| ");
                    break;
                case 14:
                    line3.append("|A| ");
                    break;
                case 15:
                    line3.append("|2| ");
                    break;
                default: {
                    line3.append("|").append(handCard.getCardSize()).append("| ");
                    break;
                }
            }
        }
        System.out.println(line1.toString());
        //System.out.println(line2.toString());
        System.out.println(line3.toString());
    }
}



