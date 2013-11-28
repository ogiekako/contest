package tmp.marathon78;


import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Random;

public class TwistedGameTester {
    static final int[] dr = {-1, 0, 1, 0};
    static final int[] dc = {0, 1, 0, -1};

    Problem prob;

    String errorMessage;

    private int getnTiles() {
        return prob.N;
    }

    private void setnTiles(int nTiles) {
        this.prob.N = nTiles;
    }

    private HashMap<Integer, int[]> getPlacedTiles() {
        return prob.placedTiles;
    }

    private void setPlacedTiles(HashMap<Integer, int[]> placedTiles) {
        this.prob.placedTiles = placedTiles;
    }

    private int[] getValidPlacements() {
        return prob.validPlacements;
    }

    private void setValidPlacements(int[] validPlacements) {
        this.prob.validPlacements = validPlacements;
    }

    private int getTileIndex() {
        return prob.tileIndex;
    }

//    private void setTileIndex(int tileIndex) {
//        this.prob.tileIndex = tileIndex;
//    }

    private int[] getTilePlace() {
        return prob.tilePlace;
    }

    private Chain[] getChains() {
        return prob.chains;
    }

    private void setChains(Chain[] chains) {
        this.prob.chains = chains;
    }

    class Chain {
        public int chainLength;
        // active contacts are the ones to be filled with the next tile (they
        // point beyond the chain)
        public int[][] activeContacts;
        public boolean looped;

        public Chain() {
            chainLength = 0;
            looped = false;
        }

        public Chain(int contactPlace, int contactIndex) {
            chainLength = 0;
            looped = false;
            activeContacts = new int[2][2];

            activeContacts[0][0] = contactPlace;
            activeContacts[0][1] = contactIndex;

            activeContacts[1][0] = adjacentPlace(contactPlace, contactIndex / 2);
            activeContacts[1][1] = matchingContact(contactIndex);

            // and extend both contacts
            extend(0);
            extend(1);
        }

        public void extend(int end) {
            if (looped) {
                return;
            }
            // knowing that the state of the board changed, try to extend the
            // chain from the given end
            while (true) {
                // check whether there is a tile placed at the next cell of this
                // contact
                if (!getPlacedTiles().containsKey(activeContacts[end][0])) {
                    break;
                }

                if (dump) {
                    prob.dmp.print(placeToString(activeContacts[end][0], prob.N) + " "
                            + activeContacts[end][1] + " ");
                }
                // replace this contact with the one it's connected to
                activeContacts[end][1] = connectedContact(
                        getPlacedTiles().get(activeContacts[end][0]),
                        activeContacts[end][1]);
                // length is incremented when the wire connection is used
                ++chainLength;
                if (dump) {
                    prob.dmp.println(activeContacts[end][1] + " R");
                }

                // check whether the chain looped - connected to its other
                // active contact
                if (activeContacts[end][0] == activeContacts[1 - end][0]
                        && activeContacts[end][1] == activeContacts[1 - end][1]) {
                    looped = true;
                    break;
                }

                int dir = activeContacts[end][1] / 2;
                int nextPlace = adjacentPlace(activeContacts[end][0], dir);
                activeContacts[end][0] = nextPlace;
                activeContacts[end][1] = matchingContact(activeContacts[end][1]);
            }
        }
    }

    // ----------------------------------------------------------------------------------
    public static int[] rotateTile(int[] tile, int rotation) {
        int[] rotatedTile = new int[8];
        for (int i = 0; i < 8; ++i) {
            rotatedTile[i] = (tile[i] + 2 * rotation) % 8;
        }
        return rotatedTile;
    }

    // ----------------------------------------------------------------------------------
    int cellPlace(int row, int col) {
        return row * (2 * getnTiles() + 1) + col;
    }

    // ----------------------------------------------------------------------------------
    int adjacentPlace(int place, int dir) {
        return place + dr[dir] * (2 * getnTiles() + 1) + dc[dir];
    }

    // ----------------------------------------------------------------------------------
    int matchingContact(int contactIndex) {
        // if two tiles are adjacent, what's the index of the matching contact
        if (contactIndex < 2 || contactIndex == 4 || contactIndex == 5) {
            return 5 - contactIndex;
        }
        return 9 - contactIndex;
    }

    // ----------------------------------------------------------------------------------
    int connectedContact(int[] tile, int contactIndex) {
        // given the tile description, return the contact connected to the given
        // one
        for (int i = 0; i < 8; ++i) {
            if (tile[i] == contactIndex) {
                return tile[i + 1 - 2 * (i % 2)];
            }
        }
        return -1;
    }

    // ----------------------------------------------------------------------------------
    boolean isValidMove(int row, int col, int rot) {
        if (rot < 0 || rot > 3) {
            errorMessage = "ROT must be between 0 and 3, inclusive.";
            return false;
        }
        int movePlace = cellPlace(row, col);
        if (getPlacedTiles().containsKey(movePlace)) {
            errorMessage = "Placed tiles must not overlap.";
            return false;
        }
        for (int i = 0; i < 4; ++i) {
            if (getValidPlacements()[i] == movePlace) {
                return true;
            }
        }
        errorMessage = "Invalid tile position.";
        return false;
    }

    // ----------------------------------------------------------------------------------
    boolean isValidReturn(String move) {
        // must be formatted as "ROW COL ROT"
        errorMessage = "";
        if (move.equals("GIVE UP")) {
            return true;
        }
        int row = -1, col = -1, rot = -1;
        try {
            String[] sp = move.split(" ");
            if (sp.length != 3) {
                errorMessage = "Your return must be formatted as \"ROW COL ROT\".";
                return false;
            }
            row = Integer.parseInt(sp[0]);
            col = Integer.parseInt(sp[1]);
            rot = Integer.parseInt(sp[2]);
        } catch (Exception e) {
            errorMessage = "Your return must be formatted as \"ROW COL ROT\".";
            return false;
        }
        return isValidMove(row, col, rot);
    }

    static class Problem {
        int N;
        Random rnd;
        public HashMap<Integer, int[]> placedTiles = new HashMap<Integer, int[]>();
        public int[] validPlacements;
        public Chain[] chains;
        public int tileIndex;
        public int[] tilePlace;

        PrintWriter dmp;

        Problem(String seed, PrintWriter dmp) {
            this.dmp = dmp;
            try {
                rnd = SecureRandom.getInstance("SHA1PRNG");
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }
            rnd.setSeed(Long.parseLong(seed));

            N = 10000;
            if (seed.equals("1")) {
                N = 10;
            }
            if (seed.equals("2")) {
                N = 100;
            }
            if (seed.equals("3")) {
                N = 1000;
            }

            tilePlace = new int[N];

            if (dump) {
                dmp.println(N);
            }
        }

        // ----------------------------------------------------------------------------------
        int[] generateRandomTile() {
            // a random permutation of 0..7
            int[] permutation = new int[8];
            int i, j, tmp;
            for (i = 0; i < 8; ++i) {
                permutation[i] = i;
            }
            for (i = 0; i < 7; ++i) {
                j = rnd.nextInt(8 - i) + i;
                if (i != j) {
                    tmp = permutation[i];
                    permutation[i] = permutation[j];
                    permutation[j] = tmp;
                }
            }

            if (tileIndex == 0) initializeBoard(permutation);

            tileIndex++;


            return permutation;
        }

        private void initializeBoard(int[] tile) {
            int row = N, col = N;
            tilePlace[tileIndex] = cellPlace(row, col);
            placedTiles.put(tilePlace[tileIndex], tile);
            dumpTile(tilePlace[tileIndex], tile);

            // with only one tile placed, there are no active contacts yet,
            // and all placements adjacent to the first tile are valid
            validPlacements = new int[4];
            for (int i = 0; i < 4; ++i) {
                validPlacements[i] = cellPlace(row + dr[i], col + dc[i]);
            }
        }

        void dumpTile(int place, int[] tile) {
            if (dump) {
                for (int i = 0; i < 4; ++i) {
                    dmp.println(placeToString(place, N) + " " + tile[2 * i] + " "
                            + tile[2 * i + 1] + " B");
                }
            }
        }

        int cellPlace(int row, int col) {
            return row * (2 * N + 1) + col;
        }

        private int getScore() {
            return Math.max(chains[0].chainLength,
                    chains[1].chainLength);
        }

        public void extend() {
            for (int i = 0; i < 4; ++i) {
                chains[i / 2].extend(i % 2);
            }
        }

//        public boolean placeTile(int[] tile, String move) {
//        }
    }

    // ----------------------------------------------------------------------------------
    public double runTest(String seed, PrintWriter dmp) {
        instance = new TwistedGame();
        try {
            prob = new Problem(seed, dmp);

            // place first tile ourselves, pass it to solution but ignore the
            // return
            int[] tile = prob.generateRandomTile();
            init(getnTiles(), tile);
//            initializeBoard(getTilePlace(), tile);

            // let the solution place second tile
            if (firstStep(prob)) {
                return 0;
            }

            for (; getTileIndex() < getnTiles(); ) {
                if (oneStep(prob)) {
                    return
                            prob.getScore();
                }
            }
            addFatalError("Chain 0 = " + getChains()[0].chainLength);
            addFatalError("Chain 1 = " + getChains()[1].chainLength);
            return prob.getScore();
        } catch (Exception e) {
            System.err
                    .println("An exception occurred while trying to get your program's results.");
            e.printStackTrace();
            return 0;
        }
    }

    private boolean firstStep(Problem prob) throws IOException {
        int[] tile; int row; int col;
        tile = prob.generateRandomTile();
        String move = placeTile(tile);
//        return prob.placeTile(tile, move);
        if (move.equals("GIVE UP")) {
            addFatalError("You gave up. Tile index = " + 1 + " (0-based).");
            return true;
        }

        if (!isValidReturn(move)) {
            addFatalError(errorMessage + " Tile index = " + getTileIndex()
                    + " (0-based).");
            return true;
        }
        row = Integer.valueOf(move.split(" ")[0]);
        col = Integer.valueOf(move.split(" ")[1]);
        int rot = Integer.valueOf(move.split(" ")[2]);
        getTilePlace()[getTileIndex()] = cellPlace(row, col);
        getPlacedTiles().put(getTilePlace()[getTileIndex()], rotateTile(tile, rot));
        prob.dumpTile(getTilePlace()[getTileIndex()], rotateTile(tile, rot));

        // figure out what the active wires and contacts are going to be
        // detect which side of the tiles is common
        int i, j;
        for (i = 0; i < 4; ++i) {
            if (getValidPlacements()[i] == getTilePlace()[getTileIndex()]) {
                break;
            }
        }

        // the contacts on this side are: side * 2 (+1) for first tile
        setChains(new Chain[2]);
        for (j = 0; j < 2; ++j) {
            getChains()[j] = new Chain(getTilePlace()[0], i * 2 + j);
        } return false;
    }

    private boolean oneStep(Problem prob) throws IOException {
        int[] tile; String move; if (looped(getChains())) return true;

        calcValidPlacement(getChains());

        tile = prob.generateRandomTile();
        move = placeTile(tile);
        if (moveAfter2(tile, move)) return true; return false;
    }

    private void calcValidPlacement(Chain[] chains) {
        int i;// update valid placements
        for (i = 0; i < 4; ++i) {
            if (chains[i / 2].looped) {
                getValidPlacements()[i] = -1;
            } else {
                getValidPlacements()[i] = chains[i / 2].activeContacts[i % 2][0];
            }
        }
    }

    private boolean looped(Chain[] chains) {
        // check if there is a possible move
        if (chains[0].looped && chains[1].looped) {
            addFatalError("No possible moves for tile index = "
                    + getTileIndex() + " (0-based).");
            addFatalError("Chain 0 = " + chains[0].chainLength);
            addFatalError("Chain 1 = " + chains[1].chainLength);
            return true;
        } return false;
    }

    private boolean moveAfter2(int[] tile, String move) {
        int[] tilePlace = getTilePlace();
        int row; int col; int rot; int i; if (move.equals("GIVE UP")) {
            addFatalError("You gave up. Tile index = " + getTileIndex()
                    + " (0-based).");
            addFatalError("Chain 0 = " + prob.chains[0].chainLength);
            addFatalError("Chain 1 = " + prob.chains[1].chainLength);
            return true;
        }

        if (!isValidReturn(move)) {
            addFatalError(errorMessage + " Tile index = " + getTileIndex()
                    + " (0-based).");
            addFatalError("Chain 0 = " + prob.chains[0].chainLength);
            addFatalError("Chain 1 = " + prob.chains[1].chainLength);
            return true;
        }
        row = Integer.valueOf(move.split(" ")[0]);
        col = Integer.valueOf(move.split(" ")[1]);
        rot = Integer.valueOf(move.split(" ")[2]);

        tilePlace[getTileIndex()] = cellPlace(row, col);
        getPlacedTiles().put(tilePlace[getTileIndex()], rotateTile(tile, rot));
        prob.dumpTile(tilePlace[getTileIndex()], rotateTile(tile, rot));

        // and extend chains taking into account the new tile
        prob.extend();

        return false;
    }

//    private void initializeBoard(int[] tilePlace, int[] tile) {
//        int row = getnTiles(), col = getnTiles();
//        tilePlace[getTileIndex()] = cellPlace(row, col);
//        getPlacedTiles().put(tilePlace[getTileIndex()], tile);
//        prob.dumpTile(tilePlace[getTileIndex()], tile);
//
//        // with only one tile placed, there are no active contacts yet,
//        // and all placements adjacent to the first tile are valid
//        setValidPlacements(new int[4]);
//        for (int i = 0; i < 4; ++i) {
//            getValidPlacements()[i] = cellPlace(row + dr[i], col + dc[i]);
//        }
//    }

//    private void dumpTile(int i, int[] tile) {

//    }

    // ------------- visualization part
    // -----------------------------------------------------
    static String exec, dumpFile = "module/src/" + "res.dmp";
    static boolean dump = true;
    static Process proc;
    InputStream is;
    OutputStream os;
    //    PrintWriter dmp;
    BufferedReader br;

    TwistedGame instance;

    // ----------------------------------------------------------------------------------
    int init(int nTiles, int[] firstTile) throws IOException {
//		StringBuffer sb = new StringBuffer();
//		sb.append(N + "\n");
//		sb.append(tileToString(firstTile)).append('\n');
//		os.write(sb.toString().getBytes());
//		os.flush();
//		return 0;
        return instance.init(nTiles, firstTile);
    }

    // ----------------------------------------------------------------------------------
    String placeTile(int[] tile) throws IOException {
//		StringBuffer sb = new StringBuffer();
//		sb.append(tileToString(tile)).append('\n');
//		os.write(sb.toString().getBytes());
//		os.flush();
//		return br.readLine();
        return instance.placeTile(tile);
    }

    // ----------------------------------------------------------------------------------
    public TwistedGameTester(String seed) {
        try {
            // interface for runTest
            if (exec != null) {
                try {
                    Runtime rt = Runtime.getRuntime();
                    proc = rt.exec(exec);
                    os = proc.getOutputStream();
                    is = proc.getInputStream();
                    br = new BufferedReader(new InputStreamReader(is));
                    new ErrorReader(proc.getErrorStream()).start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            PrintWriter dmp = null;
            if (dumpFile != null) {
                try {
                    dmp = new PrintWriter(new FileWriter(dumpFile));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Score = " + runTest(seed, dmp));
            if (proc != null)
                try {
                    proc.destroy();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            if (dumpFile != null)
                try {
                    dmp.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // ----------------------------------------------------------------------------------
    public static void main(String[] args) {
        String seed = "1";
//		dump = false;
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-seed")) {
                seed = args[++i];
            } else if (args[i].equals("-exec")) {
                exec = args[++i];
            } else if (args[i].equals("-dump")) {
                dump = true;
                dumpFile = args[++i];
            }
        }
        TwistedGameTester tg = new TwistedGameTester(seed);
    }

    // ----------------------------------------------------------------------------------
    String tileToString(int[] tile) {
        StringBuilder sb = new StringBuilder();
        sb.append(tile[0]);
        for (int i = 1; i < 8; ++i) {
            sb.append(" ");
            sb.append(tile[i]);
        }
        return sb.toString();
    }

    // ----------------------------------------------------------------------------------
    static String placeToString(int place, int N) {
        return (place / (2 * N + 1)) + " " + (place % (2 * N + 1));
    }

    // ----------------------------------------------------------------------------------


    // ----------------------------------------------------------------------------------
    void addFatalError(String message) {
        System.out.println(message);
    }
}

class ErrorReader extends Thread {
    InputStream error;

    public ErrorReader(InputStream is) {
        error = is;
    }

    public void run() {
        try {
            byte[] ch = new byte[50000];
            int read;
            while ((read = error.read(ch)) > 0) {
                String s = new String(ch, 0, read);
                System.out.print(s);
                System.out.flush();
            }
        } catch (Exception e) {
        }
    }
}