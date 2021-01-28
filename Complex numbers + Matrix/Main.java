package com.company;


public class Main {

    public static void main(String[] args) {
        Complex a = new Complex(5, -3);
        Complex b = new Complex(2.5, 10.2);

        System.out.println("Complex1: " + a.getAlgebraicForm() + " || " + a.getTrigonometricForm());
        System.out.println("Complex2: " + b.getAlgebraicForm() + " || " + b.getTrigonometricForm());

        System.out.println("Add: " + a.add(b).getAlgebraicForm());
        System.out.println("Sub: " + a.sub(b).getAlgebraicForm());
        System.out.println("Multiply: " + a.multiply(b).getAlgebraicForm());
        System.out.println("Divide: " + a.divide(b).getAlgebraicForm());
        System.out.println("Equals: " + a.equals(b));


        Matrix matrix_1 = new Matrix(2, 3);
        Matrix matrix_2 = new Matrix(3, 2);
        Matrix matrix_3 = new Matrix(3, 3);
        Matrix matrix_4 = new Matrix(3, 3);


        int num = 1;
        for (int i = 0; i < 2; i++)
            for(int j = 0; j < 3; j++)
                matrix_1.setValue(i, j, new Complex(num++));
        num = 1;
        for (int i = 0; i < 3; i++)
            for(int j = 0; j < 2; j++)
                matrix_2.setValue(i, j, new Complex(num++));

        for (int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                matrix_3.setValue(i, j, new Complex(i + j + 1));

        for (int i = 0; i < 3; i++)
            for(int j = 0; j < 3; j++)
                matrix_4.setValue(i, j, new Complex((int)(Math.random() * 10)));

        System.out.println("\nMatrix1: ");
        matrix_1.printMatrix();

        System.out.println("\nThe transposed Matrix1: ");
        matrix_1.T().printMatrix();

        System.out.println("\nMatrix2: ");
        matrix_2.printMatrix();

        System.out.println("\nMatrix1 * Matrix2: ");
        matrix_1.dot(matrix_2).printMatrix();

        System.out.println("\nMatrix1 * 5: ");
        matrix_1.multiplyByNumber(5).printMatrix();

        System.out.println("\nMatrix2 + 3: ");
        matrix_2.addNumber(3).printMatrix();

        System.out.println("\nMatrix3: ");
        matrix_3.printMatrix();

        System.out.println("Determinant of Matrix3: " + matrix_3.calculateDeterminant().getAlgebraicForm());

        System.out.println("\nMatrix4: ");
        matrix_4.printMatrix();

        System.out.println("Determinant of Matrix4: " + matrix_4.calculateDeterminant().getAlgebraicForm());

        System.out.println("\nMatrix4 + 5 - 3*i: ");
        matrix_4.addNumber(new Complex(5, -3)).printMatrix();
    }
}
