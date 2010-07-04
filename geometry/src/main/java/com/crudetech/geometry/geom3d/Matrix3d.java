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
package com.crudetech.geometry.geom3d;

import com.crudetech.geometry.geom.*;
import com.crudetech.lang.ArgumentOutOfBoundsException;

import static java.lang.Math.cos;
import static java.lang.Math.sin;


public final class Matrix3d implements ToleranceComparable<Matrix3d> {
    private final static int Dimension = 4;
    public final static Matrix3d Identity = new Matrix3d();

    final double m00, m01, m02, m03;
    final double m10, m11, m12, m13;
    final double m20, m21, m22, m23;
    final double m30, m31, m32, m33;

    public Matrix3d(double m00, double m01, double m02, double m03,
                    double m10, double m11, double m12, double m13,
                    double m20, double m21, double m22, double m23,
                    double m30, double m31, double m32, double m33) {
        this.m00 = m00;
        this.m01 = m01;
        this.m02 = m02;
        this.m03 = m03;
        this.m10 = m10;
        this.m11 = m11;
        this.m12 = m12;
        this.m13 = m13;
        this.m20 = m20;
        this.m21 = m21;
        this.m22 = m22;
        this.m23 = m23;
        this.m30 = m30;
        this.m31 = m31;
        this.m32 = m32;
        this.m33 = m33;
    }


    public Matrix3d() {
        this(
                1.0, 0.0, 0.0, 0.0,
                0.0, 1.0, 0.0, 0.0,
                0.0, 0.0, 1.0, 0.0,
                0.0, 0.0, 0.0, 1.0
        );
    }

    Matrix3d(double[][] rowMajorMatrix) {
        this(
                rowMajorMatrix[0][0], rowMajorMatrix[0][1], rowMajorMatrix[0][2], rowMajorMatrix[0][3],
                rowMajorMatrix[1][0], rowMajorMatrix[1][1], rowMajorMatrix[1][2], rowMajorMatrix[1][3],
                rowMajorMatrix[2][0], rowMajorMatrix[2][1], rowMajorMatrix[2][2], rowMajorMatrix[2][3],
                rowMajorMatrix[3][0], rowMajorMatrix[3][1], rowMajorMatrix[3][2], rowMajorMatrix[3][3]
        );
        if (rowMajorMatrix.length != Dimension) throw new IllegalArgumentException();
    }

    public Matrix3d(Matrix3d rhs) {
        this(
                rhs.m00, rhs.m01, rhs.m02, rhs.m03,
                rhs.m10, rhs.m11, rhs.m12, rhs.m13,
                rhs.m20, rhs.m21, rhs.m22, rhs.m23,
                rhs.m30, rhs.m31, rhs.m32, rhs.m33
        );
    }

    @Override
    public int hashCode(Tolerance tol) {
        return FloatCompare.hashCode(toColumnMajorArray(), tol.getVectorTolerance());
    }

    private double[] toColumnMajorArray() {
        double[] columnMajorMatrix = new double[]{
                m00, m10, m20, m30,
                m01, m11, m21, m31,
                m02, m12, m22, m32,
                m03, m13, m23, m33,
        };
        return columnMajorMatrix;
    }

    @Override
    public boolean equals(Matrix3d rhs, Tolerance tol) {
        return FloatCompare.equals(toColumnMajorArray(), rhs.toColumnMajorArray(), tol.getVectorTolerance());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Matrix3d matrix3d = (Matrix3d) o;

        return equals(matrix3d, Tolerance3d.getGlobalTolerance());
    }

    @Override
    public int hashCode() {
        return hashCode(Tolerance3d.getGlobalTolerance());
    }

    @SuppressWarnings({"StringConcatenation", "HardCodedStringLiteral", "MagicCharacter"})
    @Override
    public String toString() {
        return "Matrix3d{" +
                Matrix.toRowMajorString(toColumnMajorArray(), Dimension) +
                '}';
    }

    public Matrix3d multiply(Matrix3d rhs) {
        return new Matrix3d(
                (m00 * rhs.m00 + m01 * rhs.m10 + m02 * rhs.m20 + m03 * rhs.m30), (m00 * rhs.m01 + m01 * rhs.m11 + m02 * rhs.m21 + m03 * rhs.m31), (m00 * rhs.m02 + m01 * rhs.m12 + m02 * rhs.m22 + m03 * rhs.m32), (m00 * rhs.m03 + m01 * rhs.m13 + m02 * rhs.m23 + m03 * rhs.m33),
                (m10 * rhs.m00 + m11 * rhs.m10 + m12 * rhs.m20 + m13 * rhs.m30), (m10 * rhs.m01 + m11 * rhs.m11 + m12 * rhs.m21 + m13 * rhs.m31), (m10 * rhs.m02 + m11 * rhs.m12 + m12 * rhs.m22 + m13 * rhs.m32), (m10 * rhs.m03 + m11 * rhs.m13 + m12 * rhs.m23 + m13 * rhs.m33),
                (m20 * rhs.m00 + m21 * rhs.m10 + m22 * rhs.m20 + m23 * rhs.m30), (m20 * rhs.m01 + m21 * rhs.m11 + m22 * rhs.m21 + m23 * rhs.m31), (m20 * rhs.m02 + m21 * rhs.m12 + m22 * rhs.m22 + m23 * rhs.m32), (m20 * rhs.m03 + m21 * rhs.m13 + m22 * rhs.m23 + m23 * rhs.m33),
                (m30 * rhs.m00 + m31 * rhs.m10 + m32 * rhs.m20 + m33 * rhs.m30), (m30 * rhs.m01 + m31 * rhs.m11 + m32 * rhs.m21 + m33 * rhs.m31), (m30 * rhs.m02 + m31 * rhs.m12 + m32 * rhs.m22 + m33 * rhs.m32), (m30 * rhs.m03 + m31 * rhs.m13 + m32 * rhs.m23 + m33 * rhs.m33)
        );

    }

    private double[] multiply(double x, double y, double z, double w) {
        return new double[]{
                (m00 * x + m01 * y + m02 * z + m03 * w),
                (m10 * x + m11 * y + m12 * z + m13 * w),
                (m20 * x + m21 * y + m22 * z + m23 * w),
                (m30 * x + m31 * y + m32 * z + m33 * w),
        };
    }

    public Point3d multiply(Point3d pt) {
        double[] resultColumnMajorMatrix = multiply(pt.getX(), pt.getY(), pt.getZ(), 1);
        return new Point3d(resultColumnMajorMatrix[0], resultColumnMajorMatrix[1], resultColumnMajorMatrix[2]);
    }

    public Matrix3d transpose() {
        return new Matrix3d(
                m00, m10, m20, m30,
                m01, m11, m21, m31,
                m02, m12, m22, m32,
                m30, m13, m23, m33
        );
    }

    public static Matrix3d createTranslation(Vector3d translation) {
        return new Matrix3d(
                1, 0, 0, translation.getX(),
                0, 1, 0, translation.getY(),
                0, 0, 1, translation.getZ(),
                0, 0, 0, 1
        );
    }


    public static Matrix3d createRotationAroundZAxisInRadians(double angle) {
        return new Matrix3d(
                cos(angle), -sin(angle), 0, 0,
                sin(angle), cos(angle), 0, 0,
                0, 0, 1, 0,
                0, 0, 0, 1
        );
    }

    public Vector3d multiply(Vector3d vector3d) {
        double[] resultColumnMajorMatrix = multiply(vector3d.getX(), vector3d.getY(), vector3d.getZ(), 0);

        return new Vector3d(resultColumnMajorMatrix[0], resultColumnMajorMatrix[1], resultColumnMajorMatrix[2]);
    }

    public Matrix3d inverse() {
        final double det = determinant();
        return new Matrix3d(
                (m12 * m23 * m31 - m13 * m22 * m31 + m13 * m21 * m32 - m11 * m23 * m32 - m12 * m21 * m33 + m11 * m22 * m33) / det,
                (m03 * m22 * m31 - m02 * m23 * m31 - m03 * m21 * m32 + m01 * m23 * m32 + m02 * m21 * m33 - m01 * m22 * m33) / det,
                (m02 * m13 * m31 - m03 * m12 * m31 + m03 * m11 * m32 - m01 * m13 * m32 - m02 * m11 * m33 + m01 * m12 * m33) / det,
                (m03 * m12 * m21 - m02 * m13 * m21 - m03 * m11 * m22 + m01 * m13 * m22 + m02 * m11 * m23 - m01 * m12 * m23) / det,
                (m13 * m22 * m30 - m12 * m23 * m30 - m13 * m20 * m32 + m10 * m23 * m32 + m12 * m20 * m33 - m10 * m22 * m33) / det,
                (m02 * m23 * m30 - m03 * m22 * m30 + m03 * m20 * m32 - m00 * m23 * m32 - m02 * m20 * m33 + m00 * m22 * m33) / det,
                (m03 * m12 * m30 - m02 * m13 * m30 - m03 * m10 * m32 + m00 * m13 * m32 + m02 * m10 * m33 - m00 * m12 * m33) / det,
                (m02 * m13 * m20 - m03 * m12 * m20 + m03 * m10 * m22 - m00 * m13 * m22 - m02 * m10 * m23 + m00 * m12 * m23) / det,
                (m11 * m23 * m30 - m13 * m21 * m30 + m13 * m20 * m31 - m10 * m23 * m31 - m11 * m20 * m33 + m10 * m21 * m33) / det,
                (m03 * m21 * m30 - m01 * m23 * m30 - m03 * m20 * m31 + m00 * m23 * m31 + m01 * m20 * m33 - m00 * m21 * m33) / det,
                (m01 * m13 * m30 - m03 * m11 * m30 + m03 * m10 * m31 - m00 * m13 * m31 - m01 * m10 * m33 + m00 * m11 * m33) / det,
                (m03 * m11 * m20 - m01 * m13 * m20 - m03 * m10 * m21 + m00 * m13 * m21 + m01 * m10 * m23 - m00 * m11 * m23) / det,
                (m12 * m21 * m30 - m11 * m22 * m30 - m12 * m20 * m31 + m10 * m22 * m31 + m11 * m20 * m32 - m10 * m21 * m32) / det,
                (m01 * m22 * m30 - m02 * m21 * m30 + m02 * m20 * m31 - m00 * m22 * m31 - m01 * m20 * m32 + m00 * m21 * m32) / det,
                (m02 * m11 * m30 - m01 * m12 * m30 - m02 * m10 * m31 + m00 * m12 * m31 + m01 * m10 * m32 - m00 * m11 * m32) / det,
                (m01 * m12 * m20 - m02 * m11 * m20 + m02 * m10 * m21 - m00 * m12 * m21 - m01 * m10 * m22 + m00 * m11 * m22) / det
        );
    }

    public double determinant() {
        return m03 * m12 * m21 * m30 - m02 * m13 * m21 * m30 - m03 * m11 * m22 * m30 + m01 * m13 * m22 * m30 +
                m02 * m11 * m23 * m30 - m01 * m12 * m23 * m30 - m03 * m12 * m20 * m31 + m02 * m13 * m20 * m31 +
                m03 * m10 * m22 * m31 - m00 * m13 * m22 * m31 - m02 * m10 * m23 * m31 + m00 * m12 * m23 * m31 +
                m03 * m11 * m20 * m32 - m01 * m13 * m20 * m32 - m03 * m10 * m21 * m32 + m00 * m13 * m21 * m32 +
                m01 * m10 * m23 * m32 - m00 * m11 * m23 * m32 - m02 * m11 * m20 * m33 + m01 * m12 * m20 * m33 +
                m02 * m10 * m21 * m33 - m00 * m12 * m21 * m33 - m01 * m10 * m22 * m33 + m00 * m11 * m22 * m33;
    }


    public boolean isIdentity() {
        return isIdentity(Tolerance3d.getGlobalTolerance());
    }

    public boolean isIdentity(Tolerance tol) {
        return equals(Identity, tol);
    }

    public double get(MatrixRow matrixRow, MatrixColumn matrixColumn) {
        switch (matrixRow.getRowIndex()) {
            case 0:
                switch (matrixColumn.getColumnIndex()) {
                    case 0:
                        return m00;
                    case 1:
                        return m01;
                    case 2:
                        return m02;
                    case 3:
                        return m03;
                    default:
                        throw new ArgumentOutOfBoundsException();
                }
            case 1:
                switch (matrixColumn.getColumnIndex()) {
                    case 0:
                        return m10;
                    case 1:
                        return m11;
                    case 2:
                        return m12;
                    case 3:
                        return m13;
                    default:
                        throw new ArgumentOutOfBoundsException();
                }
            case 2:
                switch (matrixColumn.getColumnIndex()) {
                    case 0:
                        return m20;
                    case 1:
                        return m21;
                    case 2:
                        return m22;
                    case 3:
                        return m23;
                    default:
                        throw new ArgumentOutOfBoundsException();
                }
            case 3:
                switch (matrixColumn.getColumnIndex()) {
                    case 0:
                        return m30;
                    case 1:
                        return m31;
                    case 2:
                        return m32;
                    case 3:
                        return m33;
                    default:
                        throw new ArgumentOutOfBoundsException();
                }
            default:
                throw new ArgumentOutOfBoundsException();
        }
    }

    public static Matrix3d createRotationAroundXAxisInRadians(double angle) {
        return new Matrix3d(
                1.0, 0.0, 0.0, 0.0,
                0.0, cos(angle), -sin(angle), 0.0,
                0.0, sin(angle), cos(angle), 0.0,
                0.0, 0.0, 0.0, 1.0
        );
    }

    public static Matrix3d createRotationAroundYAxisInRadians(double angle) {
        return new Matrix3d(
                cos(angle), 0.0, sin(angle), 0.0,
                0.0, 1.0, 0.0, 0.0,
                -sin(angle), 0.0, cos(angle), 0.0,
                0.0, 0.0, 0.0, 1.0
        );
    }

    public static Matrix3d createScaleX(double scaleX) {
        return createScale(scaleX, 1.0, 1.0);
    }

    public static Matrix3d createScale(double scaleX, double scaleY, double scaleZ) {
        return new Matrix3d(
                scaleX, 0.0, 0.0, 0.0,
                0.0, scaleY, 0.0, 0.0,
                0.0, 0.0, scaleZ, 0.0,
                0.0, 0.0, 0.0, 1.0
        );
    }

    public static Matrix3d createScaleY(double scaleY) {
        return createScale(1.0, scaleY, 1.0);
    }

    public static Matrix3d createScaleZ(double scaleZ) {
        return createScale(1.0, 1.0, scaleZ);
    }
}