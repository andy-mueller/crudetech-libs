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
package com.crudetech.geometry.geom;

import com.crudetech.lang.ArgumentOutOfBoundsException;

/**
 * A set of basic matrix operations that serve as a workhorse
 * for concrete implementations such as {@link com.crudetech.geometry.geom2d.Matrix2d}
 * or {@link com.crudetech.geometry.geom3d.Matrix3d}.
 */
public class Matrix {
    public static MatrixRow row(final int row) {
        return new MatrixRow() {
            @Override
            public int getRowIndex() {
                return row;
            }
        };
    }

    public static MatrixColumn column(final int column) {
        return new MatrixColumn() {
            @Override
            public int getColumnIndex() {
                return column;
            }
        };
    }

    public static int getColumnMajorOffset(int row, int column, int numRows) {
        if (row >= numRows) throw new ArgumentOutOfBoundsException();
        if (row < 0) throw new ArgumentOutOfBoundsException();
        if (column >= numRows) throw new ArgumentOutOfBoundsException();
        if (column < 0) throw new ArgumentOutOfBoundsException();


        return row + column * numRows;
    }

    public static CharSequence toRowMajorString(double[] columnMajorMatrix, int rowCount) {
        if (columnMajorMatrix == null || columnMajorMatrix.length % rowCount != 0) throw new IllegalArgumentException();

        final int colCount = columnMajorMatrix.length / rowCount;
        StringBuilder b = new StringBuilder("[");
        for (int row = 0; row < rowCount; ++row) {
            b.append('[');
            for (int col = 0; col < colCount; ++col) {
                b.append(columnMajorMatrix[getColumnMajorOffset(row, col, rowCount)]).append(", ");
            }
            b.delete(b.length() - 2, b.length()).append(']');
        }
        b.append("]");
        return b;
    }
}
