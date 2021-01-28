package com.company;


public class Matrix {
    private final int m, n;
    private Complex[][] matrix;


    public Matrix() {
        this.m = 0;
        this.n = 0;
        this.matrix = null;
    }

    public Matrix(int size) {
        this.m = size;
        this.n = 1;
        this.matrix = new Complex[1][m];
        for (int i = 0; i < this.m; i++)
            this.matrix[0][i] = new Complex();
    }

    public Matrix(int size1, int size2) {
        this.n = size1;
        this.m = size2;
        this.matrix = new Complex[n][m];
        for (int i = 0; i < this.n; i++)
            for (int j = 0; j < this.m; j++)
                this.matrix[i][j] = new Complex();
    }

    public int getRowsNumber() {
        return this.n;
    }

    public int getColumnsNumber() {
        return this.m;
    }

    public int[] shape() {
        return new int[]{this.n, this.m};
    }

    public void printShape() {
        int[] shape = this.shape();
        System.out.println("Shape: (" + shape[0] + ", " + shape[1] + ")");
    }

    public void setValue(int i, int j, Complex value) {
        if (i > this.n || i < 0 || j > this.m || j < 0)
            System.out.println("ERROR: Index was outside the bounds of the array");
        this.matrix[i][j] = value;
    }

    public Complex getValue(int i, int j) {
        if (i > this.n || i < 0 || j > this.m || j < 0)
            System.out.println("ERROR: Index was outside the bounds of the array");
        return this.matrix[i][j];
    }

    public void printMatrix() {
        for (int i = 0; i < this.n; i++) {
            for (int j = 0; j < this.m; j++)
                System.out.print(this.matrix[i][j].getAlgebraicForm() + "  ");
            System.out.println();
        }
    }

    public Matrix add(Matrix other_matrix) {
        if (this.n != other_matrix.getRowsNumber() || this.m != other_matrix.getColumnsNumber()) {
            System.out.println("Dimensions of these matrix's are different. Operation 'Add' is not possible");
            System.exit(-1);
        }
        Matrix result = new Matrix(this.n, this.m);
        for (int i = 0; i < this.n; i++)
            for (int j = 0; j < this.m; j++)
                result.setValue(i, j, this.getValue(i, j).add(other_matrix.getValue(i, j)));
        return result;
    }

    public Matrix T() {
        Matrix result = new Matrix(this.m, this.n);
        for (int i = 0; i < this.n; i++)
            for (int j = 0; j < this.m; j++)
                result.setValue(j, i, this.getValue(i, j));
        return result;
    }

    public Matrix dot(Matrix other_matrix) {
        if (this.getColumnsNumber() != other_matrix.getRowsNumber()) {
            System.out.println("ERROR: The number of columns in the first matrix is not equal to the number of rows" +
                    " in the second matrix");
            System.exit(-1);
        }
        Matrix result = new Matrix(this.getRowsNumber(), other_matrix.getColumnsNumber());
        for (int i = 0; i < this.getRowsNumber(); i++)
            for (int j = 0; j < other_matrix.getColumnsNumber(); j++) {
                Complex _sum = new Complex();
                for (int k = 0; k < this.getColumnsNumber(); k++)
                    _sum = _sum.add(this.getValue(i, k).multiply(other_matrix.getValue(k, j)));
                result.setValue(i, j, _sum);
            }
        return result;
    }

    private Matrix getMinor(Matrix matrix, int j) {
        int size = matrix.shape()[0] - 1, k =0, m = 0;
        Matrix minor = new Matrix(size, size);

       for (int n = 1; n < size + 1; n++)
            for (int l = 0; l < size + 1; l++) {
                if (l == j)
                    continue ;
                minor.setValue(k, m, matrix.getValue(n, l));
                if (m < size - 1)
                    m++;
                else{
                    k++;
                    m = 0;
                }
            }
        return minor;
    }

    private Complex getAlgebraicAddition (Matrix matrix, int j) {
        int sign = 1;

        if (j % 2 != 0)
            sign = -1;

        return this.getMinor(matrix, j).calculateDeterminant().multiply(new Complex(sign));
    }

    public Complex calculateDeterminant() {
        if (this.getRowsNumber() != this.getColumnsNumber()) {
            System.out.println("ERROR: It is not possible to calculate the determinant of a non-square matrix");
            System.exit(-1);
        }

        if (this.getRowsNumber() == 1)
            return this.getValue(0, 0);

        Complex result = new Complex();
        for (int j = 0; j < this.getRowsNumber(); j++) {
            Complex alg_addition = this.getAlgebraicAddition(this, j);
            result = result.add(this.getValue(0, j).multiply(alg_addition));
            }

        return result;
    }

    public Matrix multiplyByNumber(float number) {
        Matrix result = new Matrix(this.getRowsNumber(), this.getColumnsNumber());
        for (int i = 0; i < this.getRowsNumber(); i++)
            for (int j = 0; j < this.getColumnsNumber(); j++)
                result.setValue(i, j, this.getValue(i, j).multiply(new Complex(number)));
        return result;
    }

    public Matrix multiplyByNumber(Complex number) {
        Matrix result = new Matrix(this.getRowsNumber(), this.getColumnsNumber());
        for (int i = 0; i < this.getRowsNumber(); i++)
            for (int j = 0; j < this.getColumnsNumber(); j++)
                result.setValue(i, j, this.getValue(i, j).multiply(number));
        return result;
    }

    public Matrix addNumber(Complex number) {
        Matrix result = new Matrix(this.getRowsNumber(), this.getColumnsNumber());
        for (int i = 0; i < this.getRowsNumber(); i++)
            for (int j = 0; j < this.getColumnsNumber(); j++)
                result.setValue(i, j, this.getValue(i, j).add(number));
        return result;
    }

    public Matrix addNumber(float number) {
        Matrix result = new Matrix(this.getRowsNumber(), this.getColumnsNumber());
        for (int i = 0; i < this.getRowsNumber(); i++)
            for (int j = 0; j < this.getColumnsNumber(); j++)
                result.setValue(i, j, this.getValue(i, j).add(new Complex(number)));
        return result;
    }
}
