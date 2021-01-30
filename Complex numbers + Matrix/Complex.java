package com.company;


public class Complex {
    private double real, imaginary;

    public Complex add(Complex number) {
        return new Complex(this.real + number.real, this.imaginary + number.imaginary);
    }

    public Complex sub(Complex number) {
        return new Complex(this.real - number.real, this.imaginary - number.imaginary);
    }

    public Complex multiply(Complex number) {
        return new Complex(this.real * number.real - this.imaginary * number.imaginary,
                this.real * number.imaginary + this.imaginary * number.real);
    }

    public Complex divide(Complex number) {
        double real = (this.real * number.real + this.imaginary * number.imaginary) /
                (Math.pow(number.real, 2) + Math.pow(number.imaginary, 2));
        double imaginary = (this.imaginary * number.real - this.real * number.imaginary) /
                (Math.pow(number.real, 2) + Math.pow(number.imaginary, 2));
        return new Complex(real, imaginary);
    }

    public boolean equals(Complex number) {
        return this.real == number.real && this.imaginary == number.imaginary;
    }

    public String getAlgebraicForm() {
        if (this.imaginary >= 0f)      // please use {} in if statements
            if (this.imaginary != 0.0)       //never check double numbers against srict equality
                return String.format("%.02f + %.02f*i", this.real, this.imaginary);
            else
                return String.format("%.02f", this.real);
        else
            if (this.imaginary != 0.0)
                return String.format("%.02f - %.02f*i", this.real, -this.imaginary);
            else
                return String.format("%.02f", this.real);
    }

    public String getTrigonometricForm () {
        double r = Math.sqrt(Math.pow(this.real, 2) + Math.pow(this.imaginary, 2));
        double angle = Math.toDegrees(Math.atan(this.imaginary / this.real));
        return String.format("%.02f(cos(%.02f) + i*sin(%.02f))", r, angle, angle);
    }

    Complex() {
        this.real = 0.0;
        this.imaginary = 0.0;
    }

    Complex(double real) {
        this.real = real;
        this.imaginary = 0.0;
    }

    Complex(double real, double imaginary) {
        this.real = real;
        this.imaginary = imaginary;
    }

    public double getReal() {
        return real;
    }

    public void setReal(double real) {
        this.real = real;
    }

    public double getImaginary() {
        return imaginary;
    }

    public void setImaginary(double imaginary) {
        this.imaginary = imaginary;
    }
}
