import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class SparseMatrix {
    private int[] values;      // 非零值数组
    private int[] rowPointers; // 行指针数组
    private int[] colIndices;  // 列索引数组
    private int n;             // 矩阵大小（假设是 n x n 的矩阵）

    // 构造函数，用于构建CSR表示的稀疏矩阵
    public SparseMatrix(int[][] matrix) {
        List<Integer> valList = new ArrayList<>();
        List<Integer> colIndexList = new ArrayList<>();
        List<Integer> rowPointerList = new ArrayList<>();

        rowPointerList.add(0);
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                if (matrix[i][j] != 0) {
                    valList.add(matrix[i][j]);
                    colIndexList.add(j);
                }
            }
            rowPointerList.add(valList.size());
        }

        this.values = valList.stream().mapToInt(i -> i).toArray();
        this.colIndices = colIndexList.stream().mapToInt(i -> i).toArray();
        this.rowPointers = rowPointerList.stream().mapToInt(i -> i).toArray();
        this.n = matrix.length;
    }

    // 新的构造函数，用于乘法结果
    public SparseMatrix(int[] values, int[] rowPointers, int[] colIndices, int n) {
        this.values = values;
        this.rowPointers = rowPointers;
        this.colIndices = colIndices;
        this.n = n;
    }

    // 实现矩阵乘法
    public SparseMatrix multiply(SparseMatrix other) {
        List<Integer> resultValues = new ArrayList<>();
        List<Integer> resultColIndices = new ArrayList<>();
        List<Integer> resultRowPointers = new ArrayList<>();
        resultRowPointers.add(0);

        for (int i = 0; i < n; i++) {
            int[] tempRow = new int[other.n];
            for (int j = rowPointers[i]; j < rowPointers[i + 1]; j++) {
                int colA = colIndices[j];
                int valA = values[j];
                for (int k = other.rowPointers[colA]; k < other.rowPointers[colA + 1]; k++) {
                    int colB = other.colIndices[k];
                    tempRow[colB] += valA * other.values[k];
                }
            }
            for (int j = 0; j < other.n; j++) {
                if (tempRow[j] != 0) {
                    resultValues.add(tempRow[j]);
                    resultColIndices.add(j);
                }
            }
            resultRowPointers.add(resultValues.size());
        }
        return new SparseMatrix(
                resultValues.stream().mapToInt(i -> i).toArray(),
                resultRowPointers.stream().mapToInt(i -> i).toArray(),
                resultColIndices.stream().mapToInt(i -> i).toArray(),
                n
        );
    }

    // 获取矩阵元素
    public int get(int row, int col) {
        for (int i = rowPointers[row]; i < rowPointers[row + 1]; i++) {
            if (colIndices[i] == col) {
                return values[i];
            }
        }
        return 0; // 如果该位置是零，返回0
    }

    // 打印矩阵
    public void printMatrix() {
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(get(i, j) + " ");
            }
            System.out.println();
        }
    }

    // 打印原始矩阵
    public void printOriginalMatrix(int[][] matrix) {
        System.out.println("Original Matrix:");
        for (int[] row : matrix) {
            for (int val : row) {
                System.out.print(val + " ");
            }
            System.out.println();
        }
    }

    // 打印CSR格式
    public void printCSRFormat() {
        System.out.println("CSR Format:");
        System.out.println(" - Values: " + Arrays.toString(values));
        System.out.println(" - Row Pointers: " + Arrays.toString(rowPointers));
        System.out.println(" - Column Indices: " + Arrays.toString(colIndices));
        System.out.println(" ");
    }

    // 主方法，用于测试
    public static void main(String[] args) {
        int[][] matrixData1 = {
                {1, 0, 0},
                {0, 2, 0},
                {0, 0, 3}
        };

        int[][] matrixData2 = {
                {4, 0, 0},
                {0, 5, 0},
                {0, 0, 6}
        };

        SparseMatrix matrix1 = new SparseMatrix(matrixData1);
        SparseMatrix matrix2 = new SparseMatrix(matrixData2);

        // 打印原始矩阵和CSR格式
        matrix1.printOriginalMatrix(matrixData1);
        matrix1.printCSRFormat();
        matrix2.printOriginalMatrix(matrixData2);
        matrix2.printCSRFormat();

        // 进行矩阵乘法
        SparseMatrix resultMatrix = matrix1.multiply(matrix2);

        // 打印乘法结果
        System.out.println("Matrix after multiplication:");
        resultMatrix.printMatrix();
    }
}
