////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2010, Andreas Mueller.
// All rights reserved. This program and the accompanying materials
// are made available under the terms of the Eclipse Public License v1.0
// which accompanies this distribution, and is available at
// http://www.eclipse.org/legal/epl-v10.html
//
// Contributors:
//     Andreas Mueller - initial API and implementation
////////////////////////////////////////////////////////////////////////////////
package com.crudetech.geometry.geom2d;

import com.crudetech.geometry.geom.*;
import com.crudetech.lang.ArgumentOutOfBoundsException;

import static java.lang.Math.cos;
import static java.lang.Math.sin;


public final class Matrix2d implements ToleranceComparable<Matrix2d> {
    private static final int Dimension = 3;
    final double m00, m01, m02;
    final double m10, m11, m12;
    final double m20, m21, m22;

    public static final Matrix2d Identity = new Matrix2d();

    public Matrix2d(double m00, double m01, double m02,
                    double m10, double m11, double m12,
                    double m20, double m21, double m22) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
    }

    public Matrix2d() {
        this(
                1.0, 0.0, 0.0,
                0.0, 1.0, 0.0,
                0.0, 0.0, 1.0
        );
    }

    public Matrix2d(double[][] rowMajorMatrix) {
        this(
                rowMajorMatrix[0][0], rowMajorMatrix[0][1], rowMajorMatrix[0][2],
                rowMajorMatrix[1][0], rowMajorMatrix[1][1], rowMajorMatrix[1][2],
                rowMajorMatrix[2][0], rowMajorMatrix[2][1], rowMajorMatrix[2][2]
        );
        if (rowMajorMatrix.length != Dimension) throw new IllegalArgumentException();
    }

    public Matrix2d(Matrix2d rhs) {
        this(
                rhs.m00, rhs.m01, rhs.m02,
                rhs.m10, rhs.m11, rhs.m12,
                rhs.m20, rhs.m21, rhs.m22
        );
    }

    public double get(MatrixRow row, MatrixColumn column) {
        switch (row.getRowIndex()) {
            case 0:
                switch (column.getColumnIndex()) {
                    case 0:
                        return m00;
                    case 1:
                        return m01;
                    case 2:
                        return m02;
                    default:
                        throw new ArgumentOutOfBoundsException();
                }
            case 1:
                switch (column.getColumnIndex()) {
                    case 0:
                        return m10;
                    case 1:
                        return m11;
                    case 2:
                        return m12;
                    default:
                        throw new ArgumentOutOfBoundsException();
                }
            case 2:
                switch (column.getColumnIndex()) {
                    case 0:
                        return m20;
                    case 1:
                        return m21;
                    case 2:
                        return m22;
                    default:
                        throw new ArgumentOutOfBoundsException();
                }
            default:
                throw new ArgumentOutOfBoundsException();
        }
    }

    private double[] toColumnMajorArray() {
        double[] columnMajorMatrix = new double[]{
                m00, m10, m20,
                m01, m11, m21,
                m02, m12, m22,
        };
        return columnMajorMatrix;
    }

    @Override
    public int hashCode(Tolerance tol) {
        return FloatCompare.hashCode(toColumnMajorArray(), tol.getVectorTolerance());
    }

    @Override
    public boolean equals(Matrix2d rhs, Tolerance tol) {
        return FloatCompare.equals(toColumnMajorArray(), rhs.toColumnMajorArray(), tol.getVectorTolerance());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Matrix2d matrix2d = (Matrix2d) o;

        return equals(matrix2d, Tolerance2d.getGlobalTolerance());
    }

    @Override
    public int hashCode() {
        return hashCode(Tolerance2d.getGlobalTolerance());
    }

    public Matrix2d multiply(Matrix2d rhs) {
        return new Matrix2d(
                (m00 * rhs.m00 + m01 * rhs.m10 + m02 * rhs.m20), (m00 * rhs.m01 + m01 * rhs.m11 + m02 * rhs.m21), (m00 * rhs.m02 + m01 * rhs.m12 + m02 * rhs.m22),
                (m10 * rhs.m00 + m11 * rhs.m10 + m12 * rhs.m20), (m10 * rhs.m01 + m11 * rhs.m11 + m12 * rhs.m21), (m10 * rhs.m02 + m11 * rhs.m12 + m12 * rhs.m22),
                (m20 * rhs.m00 + m21 * rhs.m10 + m22 * rhs.m20), (m20 * rhs.m01 + m21 * rhs.m11 + m22 * rhs.m21), (m20 * rhs.m02 + m21 * rhs.m12 + m22 * rhs.m22)
        );
    }

    private double[] multiply(double x, double y, double w) {
        return new double[]{
                (m00 * x + m01 * y + m02 * w),
                (m10 * x + m11 * y + m12 * w),
                (m20 * x + m21 * y + m22 * w),
        };
    }

    @SuppressWarnings({"StringConcatenation", "HardCodedStringLiteral", "MagicCharacter"})
    @Override
    public String toString() {
        return "Matrix2d{" +
                Matrix.toRowMajorString(toColumnMajorArray(), Dimension) +
                '}';
    }

    public Matrix2d transpose() {
        return new Matrix2d(
                m00, m10, m20,
                m01, m11, m21,
                m02, m12, m22
        );
    }

    public static Matrix2d createTranslation(Vector2d translation) {
        return new Matrix2d(
                1, 0, translation.getX(),
                0, 1, translation.getY(),
                0, 0, 1
        );
    }

    public Point2d multiply(Point2d pt) {
        double[] result = multiply(pt.getX(), pt.getY(), 1);
        return new Point2d(result[0], result[1]);
    }

    public static Matrix2d createRotationInRadians(double angle) {
        return new Matrix2d(
                cos(angle), -sin(angle), 0,
                sin(angle), cos(angle), 0,
                0, 0, 1
        );
    }

    public Vector2d multiply(Vector2d vec) {
        double[] result = multiply(vec.getX(), vec.getY(), 0);
        return new Vector2d(result[0], result[1]);
    }

    public static Matrix2d createScaleX(double scaleX) {
        return createScale(scaleX, 1.0);
    }

    public static Matrix2d createScaleY(double scaleY) {
     return createScale(1.0, scaleY);
    }
    public static Matrix2d createScale(double scaleX, double scaleY) {
        return new Matrix2d(
                scaleX, 0.0, 0.0,
                0.0, scaleY, 0.0,
                0.0, 0.0, 1.0
        );
    }
}

/*
public final class Matrix2d implements ToleranceComparable<Matrix2d> {
    private final double[] columnMajorMatrix;
    private static final int Dimension = 3;

    public static final Matrix2d Identity = new Matrix2d();

    public Matrix2d() {
        this(new double[]{
                1, 0, 0,
                0, 1, 0,
                0, 0, 1
        });
    }

    Matrix2d(double[] columnMajorMatrix) {
        if (columnMajorMatrix.length / Dimension != Dimension) throw new IllegalArgumentException();
        this.columnMajorMatrix = columnMajorMatrix;
    }

    public Matrix2d(double[][] rowMajorMatrix) {
        this(flattenToColumnMajor(rowMajorMatrix, Dimension));
    }

    public Matrix2d(Matrix2d rhs) {
        columnMajorMatrix = rhs.columnMajorMatrix.clone();
    }


    public double get(MatrixRow row, MatrixColumn column) {
        return columnMajorMatrix[getOffset(row.getRowIndex(), column.getColumnIndex())];
    }

    private static int getOffset(int row, int column) {
        return getColumnMajorOffset(row, column, Dimension);
    }


    @Override
    public int hashCode(Tolerance tol) {
        return FloatCompare.hashCode(columnMajorMatrix, tol.getVectorTolerance());
    }

    @Override
    public boolean equals(Matrix2d rhs, Tolerance tol) {
        return FloatCompare.equals(columnMajorMatrix, rhs.columnMajorMatrix, tol.getVectorTolerance());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Matrix2d matrix2d = (Matrix2d) o;

        return equals(matrix2d, Tolerance2d.getGlobalTolerance());
    }

    @Override
    public int hashCode() {
        return hashCode(Tolerance2d.getGlobalTolerance());
    }

    public Matrix2d multiply(Matrix2d rhs) {
        double[] result = Matrix.multiply(columnMajorMatrix, rhs.columnMajorMatrix, Dimension);

        return new Matrix2d(result);
    }

    @SuppressWarnings({"StringConcatenation", "HardCodedStringLiteral", "MagicCharacter"})
    @Override
    public String toString() {
        return "Matrix2d{" +
                Matrix.toRowMajorString(columnMajorMatrix, Dimension) +
                '}';
    }

    public Matrix2d transpose() {
        double[] newColumnMajorMatrix = columnMajorMatrix.clone();
        Matrix.transpose(newColumnMajorMatrix, Dimension);
        return new Matrix2d(newColumnMajorMatrix);

    }

    public static Matrix2d createTranslation(Vector2d translation) {
        return new Matrix2d(new double[]{
                1, 0, 0,
                0, 1, 0,
                translation.getX(), translation.getY(), 1
        });
    }

    public Point2d multiply(Point2d pt) {
        double[] result = Matrix.multiply(columnMajorMatrix, new double[]{pt.getX(), pt.getY(), 1}, Dimension);
        return new Point2d(result[0], result[1]);
    }

    public static Matrix2d createRotationInRadians(double angle) {
        return new Matrix2d(new double[]{
                cos(angle), sin(angle), 0,
                -sin(angle), cos(angle), 0,
                0, 0, 1,
        });
    }

    public Vector2d multiply(Vector2d vec) {
        double[] result = Matrix.multiply(columnMajorMatrix, new double[]{vec.getX(), vec.getY(), 0}, Dimension);
        return new Vector2d(result[0], result[1]);
    }
}
* */