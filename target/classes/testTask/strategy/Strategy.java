package testTask.strategy;

import java.util.List;
import java.util.TreeMap;

public class Strategy {
    private final  TreeMap<Integer, Integer> askMap = new TreeMap<>();
    private final  TreeMap<Integer, Integer> bidMap = new TreeMap<>();
    private final StringBuilder builder = new StringBuilder();

    public Strategy() {
    }

    public void lineStrategy(String line) {
        char operation = line.charAt(0);
        switch (operation) {
        case 'u':
            updateStrategy(line);
            return;
        case 'q':
            queryStrategy(line);
            return;
        case 'o':
            orderStrategy(line);
            return;
        default:
            throw new RuntimeException(
                    String.format("There is no such strategy %s!", line));
        }
    }

    public String getResult() {
        return builder.toString();
    }

    private void queryStrategy(String line) {
        if (line.contains("best_bid")) {
            builder.append(bidMap.lastKey()).append(",")
                    .append(bidMap.lastEntry().getValue()).append(System.lineSeparator());
        } else if (line.contains("best_ask")) {
            builder.append(askMap.firstKey()).append(",")
                    .append(askMap.firstEntry().getValue()).append(System.lineSeparator());
        } else if (line.contains("size")) {
            int price = Integer.parseInt(line.substring(7));
            if (bidMap.containsKey(price)) {
                builder.append(bidMap.get(price)).append(System.lineSeparator());
            } else if (askMap.containsKey(price)) {
                builder.append(askMap.get(price)).append(System.lineSeparator());
            } else {
                builder.append(0).append(System.lineSeparator());
            }
        }
    }

    private void orderStrategy(String line) {
        if (line.contains("sell")) {
            int sell = Integer.parseInt(line.substring(7));
            while (sell != 0) {
                int size = bidMap.lastEntry().getValue() - sell;
                if (size <= 0) {
                    sell = Math.abs(size);
                    bidMap.remove(bidMap.lastKey());
                } else {
                    sell = 0;
                    bidMap.put(bidMap.lastKey(), size);
                }
            }
        } else {
            int buy = Integer.parseInt(line.substring(6));
            while (buy != 0) {
                int size = askMap.firstEntry().getValue() - buy;
                if (size <= 0) {
                    buy = Math.abs(size);
                    askMap.remove(askMap.firstKey());
                } else {
                    buy = 0;
                    askMap.put(askMap.firstKey(), size);
                }
            }
        }
    }

    private void updateStrategy(String line) {
        int lastIndex = line.lastIndexOf(",");
        String substring = line.substring(line.indexOf(",") + 1, lastIndex);
        int index = substring.indexOf(",");
        int price = Integer.parseInt(substring, 0, index, 10);
        int size = Integer.parseInt(substring, index + 1, substring.length(), 10);
        if (line.contains("ask")) {
            if (size == 0) {
                askMap.remove(price);
            } else {
                askMap.put(price, size);
            }
        } else {
            if (size == 0) {
                bidMap.remove(price);
            } else {
                bidMap.put(price, size);
            }
        }
    }
}
