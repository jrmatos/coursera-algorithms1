import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final int[][] closedOpenMatrix;
    private final int[][] wqfMappingIndexMatrix;
    private final int n;
    private final WeightedQuickUnionUF wqf;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("Grid size cannot be zero or negative");
        }

        this.n = n;
        int wqfCurrentIndex = 0;
        closedOpenMatrix = new int[n][n]; // 0 or 1 matrix
        // matrix containing the indices for WeightedQuickUnionUF, this is because wqf is a flat array, so we need
        // a way to map a matrix to this flat array.
        // Ex: Given N equal to 5 -> (1,1) = 0, (1,2) = 2 ... (4,1) = 20, (4,4) = 24
        wqfMappingIndexMatrix = new int[n][n];
        wqf = new WeightedQuickUnionUF(n * n + 2); // including two more for the top and bottom virtual sites

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                closedOpenMatrix[i][j] = 0; // 0 means closed
                wqfMappingIndexMatrix[i][j] = wqfCurrentIndex++;
            }
        }
    }


    // opens the site (row, col) if it is not open already
    public void open(int _row, int _col) {
        // By convention, the row and column indices are integers between 1 and n, where (1, 1) is the upper-left site
        int row = _row - 1;
        int col = _col - 1;

        if (!areIndicesValid(row, col)) throw new IllegalArgumentException("Invalid index provided");

        debug("Opening: " + printIndices(row, col));

        if (closedOpenMatrix[row][col] == 0) { // if still closed
            // union with TOP, if valid in bounds and already open
            if (areIndicesValid(row - 1, col)) {
                if (closedOpenMatrix[row - 1][col] == 1) {
                    debug("connecting to TOP: " + printIndices(row - 1, col));
                    wqf.union(wqfMappingIndexMatrix[row][col], wqfMappingIndexMatrix[row - 1][col]);
                } else {
                    debug("Could not connect to TOP because it's not open: " + printIndices(row - 1, col));
                }
            } else {
                debug("Could not connect to TOP because indices are out of bounds: " + printIndices(row - 1, col));
            }

            // union with RIGHT, if valid in bounds and already open
            if (areIndicesValid(row, col + 1)) {
                if (closedOpenMatrix[row][col + 1] == 1) {
                    debug("connecting to RIGHT: " + printIndices(row, col + 1));
                    wqf.union(wqfMappingIndexMatrix[row][col], wqfMappingIndexMatrix[row][col + 1]);
                } else {
                    debug("Could not connect to RIGHT because it's not open: " + printIndices(row, col + 1));
                }
            } else {
                debug("Could not connect to RIGHT because indices are out of bounds: " + printIndices(row, col + 1));
            }

            // union with BOTTOM, if valid in bounds and already open
            if (areIndicesValid(row + 1, col)) {
                if (closedOpenMatrix[row + 1][col] == 1) {
                    debug("connecting to BOTTOM: " + printIndices(row + 1, col));
                    wqf.union(wqfMappingIndexMatrix[row][col], wqfMappingIndexMatrix[row + 1][col]);
                } else {
                    debug("Could not connect to BOTTOM because it's not open: " + printIndices(row + 1, col));
                }
            } else {
                debug("Could not connect to BOTTOM because indices are out of bounds: " + printIndices(row + 1, col));
            }

            // union with LEFT, if valid in bounds and already open
            if (areIndicesValid(row, col - 1)) {
                if (closedOpenMatrix[row][col - 1] == 1) {
                    debug("connecting to LEFT: " + printIndices(row, col - 1));
                    wqf.union(wqfMappingIndexMatrix[row][col], wqfMappingIndexMatrix[row][col - 1]);
                } else {
                    debug("Could not connect to LEFT because it's not open: " + printIndices(row, col - 1));
                }
            } else {
                debug("Could not connect to LEFT because indices are out of bounds: " + printIndices(row, col - 1));
            }

            closedOpenMatrix[row][col] = 1;
        } else {
            debug("Already opened");
        }
    }


    // is the site (row, col) open?
    public boolean isOpen(int _row, int _col) {
        int row = _row - 1;
        int col = _col - 1;

        if (!areIndicesValid(row, col)) throw new IllegalArgumentException("Invalid index provided");

        return closedOpenMatrix[row][col] == 1;
    }

    // is the site (row, col) full (connected to top)?
    public boolean isFull(int _row, int _col) {
        int row = _row - 1;
        int col = _col - 1;

        if (!areIndicesValid(row, col)) throw new IllegalArgumentException("Invalid index provided");

        if (closedOpenMatrix[row][col] == 0) return false;

        int virtualTopIndex = (n * n + 2) - 1;

        // introduce virtual top site
        for (int i = 0; i < n; i++) {
            if (closedOpenMatrix[0][i] == 1) { // is open
                wqf.union(virtualTopIndex, wqfMappingIndexMatrix[0][i]);
            }
        }

        return wqf.find(wqfMappingIndexMatrix[row][col]) == wqf.find(virtualTopIndex);
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        int count = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (closedOpenMatrix[i][j] == 1) count++;
            }
        }

        return count;
    }

    // does the system percolate?
    public boolean percolates() {
        int virtualTopIndex = (n * n + 2) - 1;
        int virtualBottomIndex = (n * n + 2) - 2;

        debug("virtualTopIndex " + virtualTopIndex);
        debug("virtualBottomIndex " + virtualBottomIndex);

        if (numberOfOpenSites() == 0) return false;

        // introduce virtual top site
        for (int i = 0; i < n; i++) {
            if (closedOpenMatrix[0][i] == 1) { // is open
                wqf.union(virtualTopIndex, wqfMappingIndexMatrix[0][i]);
            }
        }

        // introduce virtual bottom site
        for (int i = 0; i < n; i++) {
            if (closedOpenMatrix[n-1][i] == 1) { // is open
                wqf.union(virtualBottomIndex, wqfMappingIndexMatrix[n - 1][i]);
            }
        }

        return wqf.find(virtualTopIndex) == wqf.find(virtualBottomIndex);
    }

    private boolean isIndexValid(int index) {
        return !(index < 0 || index >= n);
    }

    private boolean areIndicesValid(int row, int col) {
        return isIndexValid(row) && isIndexValid(col);
    }

    private String printIndices(int row, int col) {
        return "(" + row + "," + col + ")";
    }

    private void debug(String str) {
        boolean VERBOSE = false;
        if (VERBOSE) {
            System.out.println(str);
        }
    }


    // test client (optional)
    public static void main(String[] args) {
        Percolation percolation = new Percolation(3);
        System.out.println(percolation.isFull(1,1));
        percolation.open(1,1);
        System.out.println(percolation.isFull(1,1));
        System.out.println(percolation.isFull(1,1));
    }
}