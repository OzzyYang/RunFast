package src.cards;

public enum Poker {
    黑桃A("黑桃A", "Spade", 14.0f), 红心A("红心A", "Heart", 14.1f), 梅花A("梅花A", "Club", 14.2f), 红心2("红心2", "Heart", 15.0f),
    黑桃3("黑桃3", "Spade", 3.0f), 红心3("红心3", "Heart", 3.1f), 梅花3("梅花3", "Club", 3.2f), 方块3("方块3", "Diamond", 3.3f),
    黑桃4("黑桃4", "Spade", 4.0f), 红心4("红心4", "Heart", 4.1f), 梅花4("梅花4", "Club", 4.2f), 方块4("方块4", "Diamond", 4.3f),
    黑桃5("黑桃5", "Spade", 5.0f), 红心5("红心5", "Heart", 5.1f), 梅花5("梅花5", "Club", 5.2f), 方块5("方块5", "Diamond", 5.3f),
    黑桃6("黑桃6", "Spade", 6.0f), 红心6("红心6", "Heart", 6.1f), 梅花6("梅花6", "Club", 6.2f), 方块6("方块6", "Diamond", 6.3f),
    黑桃7("黑桃7", "Spade", 7.0f), 红心7("红心7", "Heart", 7.1f), 梅花7("梅花7", "Club", 7.2f), 方块7("方块7", "Diamond", 7.3f),
    黑桃8("黑桃8", "Spade", 8.0f), 红心8("红心8", "Heart", 8.1f), 梅花8("梅花8", "Club", 8.2f), 方块8("方块8", "Diamond", 8.3f),
    黑桃9("黑桃9", "Spade", 9.0f), 红心9("红心9", "Heart", 9.1f), 梅花9("梅花9", "Club", 9.2f), 方块9("方块9", "Diamond", 9.3f),
    黑桃10("黑桃10", "Spade", 10.0f), 红心10("红心10", "Heart", 10.1f), 梅花10("梅花10", "Club", 10.2f), 方块10("方块10", "Diamond", 10.3f),
    黑桃J("黑桃J", "Spade", 11.0f), 红心J("红心J", "Heart", 11.1f), 梅花J("梅花J", "Club", 11.2f), 方块J("方块J", "Diamond", 11.3f),
    黑桃Q("黑桃Q", "Spade", 12.0f), 红心Q("红心Q", "Heart", 12.1f), 梅花Q("梅花Q", "Club", 12.2f), 方块Q("方块Q", "Diamond", 12.3f),
    黑桃K("黑桃K", "Spade", 13.0f), 红心K("红心K", "Heart", 13.1f), 梅花K("梅花K", "Club", 13.2f), 方块K("方块K", "Diamond", 13.3f);

    private final String cardName;
    private final String cardSuit;
    private final float cardSize;

    Poker(String name, String suit, float size) {
        this.cardName = name;
        this.cardSuit = suit;
        this.cardSize = size;
    }


    public String getCardName() {
        return cardName;
    }

    public String getCardSuit() {
        return cardSuit;
    }

    public float getCardSize() {
        return cardSize;
    }


    @Override
    public String toString() {
        return this.getCardName();
    }

    /**
     * 比较这张扑克牌与指定扑克牌的大小，数值相等则按照花色排列：黑桃<红心<梅花<方块
     *
     * @param other 指定扑克牌
     * @return 这张大则返回1，这张小则返回-1。相等则返回0
     */
    public int comparing(Poker other) {
        Poker self = this;
        if (self.cardSize - other.cardSize < 0) {
            return -1;
        } else if (self.cardSize - other.cardSize > 0) {
            return 1;
        } else {
            return 0;
        }
    }
}