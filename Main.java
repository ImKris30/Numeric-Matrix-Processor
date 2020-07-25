package processor;
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean set = true;
        while(set) {
            System.out.println("1. Add matrices");
            System.out.println("2. Multiply matrix to a constant");
            System.out.println("3. Multiply matrices");
            System.out.println("4. Transpose matrix");
            System.out.println("5. Calculate a determinant");
            System.out.println("6. Inverse matrix");
            System.out.println("0. Exit");
            System.out.print("Your choice: > ");
            int choice = sc.nextInt();
            switch (choice) {
                case 1:
                    System.out.print("Enter size of first matrix: > ");
                    int row1 = sc.nextInt();
                    int col1 = sc.nextInt();
                    System.out.println("Enter first matrix:");
                    double[][] matrix1 = createMatrix(row1, col1);
                    System.out.print("Enter size of second matrix: > ");
                    int row2 = sc.nextInt();
                    int col2 = sc.nextInt();
                    System.out.println("Enter second matrix:");
                    double[][] matirx2 = createMatrix(row2, col2);
                    if (row1 == row2 && col1 == col2) {
                        double[][] matrix3 = new double[row1][col1];
                        sumMatrix(matrix1, matirx2, matrix3, row1, col1);
                        System.out.println("The addition result is:");
                        printMatrix(matrix3, row1, col1);
                        break;
                    } else {
                        System.out.println("ERROR");
                    }
                    break;

                case 2:
                    System.out.print("Enter size of matrix: > ");
                    int row3 = sc.nextInt();
                    int col3 = sc.nextInt();
                    System.out.println("Enter matrix:");
                    double[][] matrix = createMatrix(row3, col3);
                    System.out.println("Enter the constant");
                    int num = sc.nextInt();
                    multiplyByConstant(matrix, row3, col3, num);
                    System.out.println("The multiplication result is:");
                    printMatrix(matrix, row3, col3);
                    break;
                case 3:
                    System.out.print("Enter size of first matrix: > ");
                    int row4 = sc.nextInt();
                    int col4 = sc.nextInt();
                    System.out.println("Enter first matrix:");
                    double[][] matrixOne = createMatrix(row4, col4);
                    System.out.print("Enter size of second matrix: > ");
                    int row5 = sc.nextInt();
                    int col5 = sc.nextInt();
                    System.out.println("Enter second matrix:");
                    double[][] matirxTwo = createMatrix(row5, col5);
                    if (col4 == row5) {
                        double[][] matrixResult = new double[row4][col5];
                        matrixMultiply(matrixResult, matrixOne, matirxTwo, row4, col4, row5, col5);
                        System.out.println("The multiplication result is:");
                        printMatrix(matrixResult, row4, col5);
                    } else {
                        System.out.println("ERROR");
                    }
                    break;
                case 4:
                    System.out.println("1. Main diagonal \n2. Side diagonal \n3. Vertical line\n4. Horizontal line");
                    int ch = sc.nextInt();
                    findTranspose(ch);
                    break;
                case 5:
                    System.out.print("Enter size of matrix: > ");
                    double D;
                    int row = sc.nextInt();
                    int col = sc.nextInt();
                    System.out.println("Enter matrix:");
                    double[][] matrixDet = createMatrix(row, col);
                    if (row == col) {
                        D = determinant(matrixDet, row);
                    } else {
                        System.out.println("ERROR");
                        break;
                    }
                    System.out.println("The result is:");
                    System.out.println(D);
                    break;
                case 6:
                    System.out.println("Enter matrix size: >");
                    int r = sc.nextInt();
                    int c = sc.nextInt();
                    if ( r != c) {
                        System.out.println("ERROR");
                        break;
                    }
                    System.out.println("Enter matrix:");
                    double[][] m = createMatrix(r, c);

                    double [][]adj = new double[r][r];
                    double[][] inv = new double[r][r];
                    adjoint(m, adj, r);
                    if(inverse(m, inv, r)) {
                        printMatrix(inv, r, c);
                    }
                    break;
                case 0:
                    set = false;
                    break;
            }
        }

    }
    static boolean inverse(double A[][], double [][]inverse, int N)
    {
        // Find determinant of A[][]
        double det = determinant(A, N);
        if (det == 0)
        {
            System.out.print("ERROR");
            return false;
        }

        // Find adjoint
        double [][]adj = new double[N][N];
        adjoint(A, adj, N);

        // Find Inverse using formula "inverse(A) = adj(A)/det(A)"
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                inverse[i][j] = adj[i][j]/(double)det;

        return true;
    }
    static void adjoint(double A[][],double [][]adj, int N)
    {
        if (N == 1)
        {
            adj[0][0] = 1;
            return;
        }

        // temp is used to store cofactors of A[][]
        int sign = 1;
        double [][]temp = new double[N][N];

        for (int i = 0; i < N; i++)
        {
            for (int j = 0; j < N; j++)
            {
                // Get cofactor of A[i][j]
                cofactor(A, temp, i, j, N);

                // sign of adj[j][i] positive if sum of row
                // and column indexes is even.
                sign = ((i + j) % 2 == 0)? 1: -1;

                // Interchanging rows and columns to get the
                // transpose of the cofactor matrix
                adj[j][i] = (sign)*(determinant(temp, N-1));
            }
        }
    }
    public static double determinant(double[][] matrix, int n) {
        double D = 0;
        if (n == 1) {
            return matrix[0][0];
        }
        double temp[][] = new double[n][n];
        int sign = 1;
        for (int f = 0; f < n; f++) {
            cofactor(matrix, temp, 0, f, n);
            D += sign * matrix[0][f] * determinant(temp, n - 1);
            sign = -sign;
        }
        return D;
    }
    public static void cofactor(double[][] matrix, double[][] temp, int p, int q, int n) {
        int i = 0;
        int  j = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (row != p && col != q) {
                    temp[i][j++] = matrix[row][col];
                    if (j == n - 1) {
                        j = 0;
                        i++;
                    }
                }
            }
        }
    }
    public static void findTranspose(int choice) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter matrix size: > ");
        int row = sc.nextInt();
        int col = sc.nextInt();
        System.out.println("Enter matrix:");
        double matrix[][] = createMatrix(row, col);
        switch(choice) {
            case 1:
                for (int i = 0; i < row; i++) {
                    for (int j = i; j < col; j++) {
                        double temp = matrix[i][j];
                        matrix[i][j] = matrix[j][i];
                        matrix[j][i] = temp;
                    }
                }
                System.out.println("The result is:");
                printMatrix(matrix, row, col);
                break;
            case 2:
                for (int i = 0, w = row - 1; i < row; i++, w--) {
                    for (int j = 0, k = col - 1; j < col - i; j++, k--) {
                        double temp = matrix[k][w];
                        matrix[k][w] = matrix[i][j];
                        matrix[i][j] = temp;
                    }
                }
                System.out.println("The result is:");
                printMatrix(matrix, row, col);
                break;
            case 3:
                for (int i = 0; i < row; i++) {
                    int val = col - 1;
                    for (int j = 0; j < val; j++, val--) {
                        double temp = matrix[i][j];
                        matrix[i][j] = matrix[i][val];
                        matrix[i][val] = temp;
                    }
                }
                System.out.println("The result is:");
                printMatrix(matrix, row, col);
                break;
            case 4:
                int val = row - 1;
                for (int i = 0; i < val; i++, val--) {
                    for (int j = 0; j < col; j++) {
                        double temp = matrix[i][j];
                        matrix[i][j] = matrix[val][j];
                        matrix[val][j] = temp;
                    }
                }
                System.out.println("The result is:");
                printMatrix(matrix, row, col);
                break;
        }
    }
    public static void matrixMultiply(double[][] matrixRes, double[][] matrix1, double[][] matrix2, int row1, int col1, int row2, int col2) {
        for (int i = 0; i < row1; i++) {
            for (int j = 0; j < col2; j++) {
                matrixRes[i][j] = 0;
                for (int k = 0; k < row2; k++) {
                    matrixRes[i][j] += matrix1[i][k] * matrix2[k][j];
                }
            }
        }
    }
    public static void multiplyByConstant(double[][] matrix, int row, int col, int num) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                matrix[i][j] = matrix[i][j] * num;
            }
        }
    }
    public static double[][] createMatrix(int row, int col) {
        Scanner sc = new Scanner(System.in);
        double[][] matix = new double[row][col];
         for (int i = 0; i < row; i++) {
             for (int j = 0; j < col; j++) {
                 matix[i][j] = sc.nextDouble();
             }
         }
         return matix;
    }
    public static void sumMatrix(double[][] matrix1, double[][] matrix2, double[][] matrix3, int row1, int col1) {
        for (int i = 0; i < row1; i++) {
            for (int j = 0; j < col1; j++) {
                matrix3[i][j] = matrix1[i][j] + matrix2[i][j];
            }
        }
    }
    public static void printMatrix(double[][] Matrix, int row, int col) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {

                System.out.printf("%.2f", Matrix[i][j]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
