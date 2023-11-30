import java.io.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;

class StockAnalysis {
    static class Trade {
        String tradeNo;
        String tradeTime;
        String secBoard;
        String secCode;
        double price;
        int volume;
        double value;

        public Trade(String tradeNo, String tradeTime, String secBoard, String secCode, double price, int volume, double value) {
            this.tradeNo = tradeNo;
            this.tradeTime = tradeTime;
            this.secBoard = secBoard;
            this.secCode = secCode;
            this.price = price;
            this.volume = volume;
            this.value = value;
        }

        public String getSecCode() {
            return secCode;
        }
    }

    static class TradeStats {
        double openPrice;
        double closePrice;
        int totalVolume;
        double totalValue;
        String firstTradeTime;
        String lastTradeTime;

        void accept(Trade trade) {
            if (firstTradeTime == null || trade.tradeTime.compareTo(firstTradeTime) < 0) {
                firstTradeTime = trade.tradeTime;
                openPrice = trade.price;
            }
            if (lastTradeTime == null || trade.tradeTime.compareTo(lastTradeTime) >= 0) {
                lastTradeTime = trade.tradeTime;
                closePrice = trade.price;
            }
            totalVolume += trade.volume;
            totalValue += trade.value;
        }

        TradeStats combine(TradeStats other) {
            if (other.firstTradeTime.compareTo(this.firstTradeTime) < 0) {
                this.openPrice = other.openPrice;
                this.firstTradeTime = other.firstTradeTime;
            }
            if (other.lastTradeTime.compareTo(this.lastTradeTime) >= 0) {
                this.closePrice = other.closePrice;
                this.lastTradeTime = other.lastTradeTime;
            }
            this.totalVolume += other.totalVolume;
            this.totalValue += other.totalValue;
            return this;
        }

        double getPriceChangePercent() {
            return 100.0 * (closePrice - openPrice) / openPrice;
        }
    }

    public static void main(String[] args) {
        String fileName = "/Users/siren/Documents/AlgorithmCourse/BiddingResultsJava/src/trades.txt";

        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            Map<String, TradeStats> tradeStatsMap = stream
                    .map(line -> line.split("\t"))
                    .filter(parts -> parts.length >= 9)
                    .filter(parts -> "TQBR".equals(parts[2]) || "FQBR".equals(parts[2]))
                    .map(parts -> new Trade(
                            parts[0], // TRADENO
                            parts[1], // TRADETIME
                            parts[2], // SECBOARD
                            parts[3], // SECCODE
                            Double.parseDouble(parts[4]), // PRICE
                            Integer.parseInt(parts[5]), // VOLUME
                            Double.parseDouble(parts[8]) // VALUE
                    ))
                    .collect(Collectors.toMap(
                            Trade::getSecCode,
                            trade -> {
                                TradeStats stats = new TradeStats();
                                stats.accept(trade);
                                return stats;
                            },
                            TradeStats::combine));

            System.out.println("Top 10 Securities with Highest Price Increase:");
            tradeStatsMap.entrySet().stream()
                    .sorted(Comparator.comparingDouble(e -> -e.getValue().getPriceChangePercent()))
                    .limit(10)
                    .forEach(entry -> outputTradeStats(entry));

            System.out.println("\nTop 10 Securities with Highest Price Decrease:");
            tradeStatsMap.entrySet().stream()
                    .sorted(Comparator.comparingDouble(e -> e.getValue().getPriceChangePercent()))
                    .limit(10)
                    .forEach(entry -> outputTradeStats(entry));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void outputTradeStats(Map.Entry<String, TradeStats> entry) {
        TradeStats stats = entry.getValue();
        System.out.printf("SecCode: %s, Price Change: %.2f%%, Total Volume: %d, Total Value: %.2f%n",
                entry.getKey(), stats.getPriceChangePercent(), stats.totalVolume, stats.totalValue);
    }
}
