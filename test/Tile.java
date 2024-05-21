package test;

import java.util.Random;

public class Tile {
    public final char letter;
    public final int score;

    private Tile(char letter, int score) {
        this.letter = letter;
        this.score = score;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + letter;
        result = prime * result + score;
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Tile other = (Tile) obj;
        if (letter != other.letter)
            return false;
        if (score != other.score)
            return false;
        return true;
    }

    public static class Bag {
        private final int[] maxQuantities = { 9, 2, 2, 4, 12, 2, 3, 2, 9, 1, 1, 4, 2, 6, 8, 2, 1, 6, 4, 6, 4, 2, 2, 1,
                2, 1 };
        private final Tile[] tiles;
        private int[] quantities = maxQuantities.clone();
        private int size;

        private static Bag bagSingleton = null;

        private Bag() {
            for (int c : maxQuantities) {
                this.size += c;
            }
            tiles = new Tile[] {
                    new Tile('A', 1),
                    new Tile('B', 3),
                    new Tile('C', 3),
                    new Tile('D', 2),
                    new Tile('E', 1),
                    new Tile('F', 4),
                    new Tile('G', 2),
                    new Tile('H', 4),
                    new Tile('I', 1),
                    new Tile('J', 8),
                    new Tile('K', 5),
                    new Tile('L', 1),
                    new Tile('M', 3),
                    new Tile('N', 1),
                    new Tile('O', 1),
                    new Tile('P', 3),
                    new Tile('Q', 10),
                    new Tile('R', 1),
                    new Tile('S', 1),
                    new Tile('T', 1),
                    new Tile('U', 1),
                    new Tile('V', 4),
                    new Tile('W', 4),
                    new Tile('X', 8),
                    new Tile('Y', 4),
                    new Tile('Z', 10)
            };
        }

        public Tile getRand() {
            if (size > 0) {
                Random rand = new Random();
                int randNum = rand.nextInt(quantities.length);
                while (quantities[randNum] == 0) {
                    randNum = rand.nextInt(quantities.length);
                }
                quantities[randNum]--;
                size -= 1;
                return tiles[randNum];
            }
            return null;
        }

        public Tile getTile(char c) {
            if (c >= 'A' && c <= 'Z' && quantities[c - 'A'] > 0) {
                quantities[c - 'A']--;
                size -= 1;
                return tiles[c - 'A'];
            }
            return null;
        }

        public int size() {
            return this.size;
        }

        public void put(Tile t) {
            int index = t.letter - 'A';
            if (quantities[index] < maxQuantities[index]) {
                quantities[index]++;
                size++;
            }
        }

        public int[] getQuantities() {
            return quantities.clone();
        }

        public static Bag getBag() {
            if (bagSingleton == null) {
                bagSingleton = new Bag();
            }
            return bagSingleton;
        }
    }
}
