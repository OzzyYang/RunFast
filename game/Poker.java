package game;

public enum Poker {
    黑桃A("黑桃A", "Spade", 14), 红心A("红心A", "Heart", 14), 梅花A("梅花A", "Club", 14), 红心2("红心2", "Heart", 15),
    黑桃3("黑桃3", "Spade", 3), 红心3("红心3", "Heart", 3), 梅花3("梅花3", "Club", 3), 方块3("方块3", "Diamond", 3),
    黑桃4("黑桃4", "Spade", 4), 红心4("红心4", "Heart", 4), 梅花4("梅花4", "Club", 4), 方块4("方块4", "Diamond", 4),
    黑桃5("黑桃5", "Spade", 5), 红心5("红心5", "Heart", 5), 梅花5("梅花5", "Club", 5), 方块5("方块5", "Diamond", 5),
    黑桃6("黑桃6", "Spade", 6), 红心6("红心6", "Heart", 6), 梅花6("梅花6", "Club", 6), 方块6("方块6", "Diamond", 6),
    黑桃7("黑桃7", "Spade", 7), 红心7("红心7", "Heart", 7), 梅花7("梅花7", "Club", 7), 方块7("方块7", "Diamond", 7),
    黑桃8("黑桃8", "Spade", 8), 红心8("红心8", "Heart", 8), 梅花8("梅花8", "Club", 8), 方块8("方块8", "Diamond", 8),
    黑桃9("黑桃9", "Spade", 9), 红心9("红心9", "Heart", 9), 梅花9("梅花9", "Club", 9), 方块9("方块9", "Diamond", 9),
    黑桃10("黑桃10", "Spade", 10), 红心10("红心10", "Heart", 10), 梅花10("梅花10", "Club", 10), 方块10("方块10", "Diamond", 10),
    黑桃J("黑桃J", "Spade", 11), 红心J("红心J", "Heart", 11), 梅花J("梅花J", "Club", 11), 方块J("方块J", "Diamond", 11),
    黑桃Q("黑桃Q", "Spade", 12), 红心Q("红心Q", "Heart", 12), 梅花Q("梅花Q", "Club", 12), 方块Q("方块Q", "Diamond", 12),
    黑桃K("黑桃K", "Spade", 13), 红心K("红心K", "Heart", 13), 梅花K("梅花K", "Club", 13), 方块K("方块K", "Diamond", 13);

    private final String cardName;
    private final String cardSuit;
    private final int cardSize;

    private Poker(String name, String suit, int size) {
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

    public int getCardSize() {
        return cardSize;
    }




}