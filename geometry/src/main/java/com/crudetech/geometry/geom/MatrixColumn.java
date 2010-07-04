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

/**
 * When implemented it represents a matrix column index. It is intended to be used
 * through the static {@link com.crudetech.geometry.geom.Matrix#column(int)} methods
 * to enhance the safety and readability of index based get operations in a matrix.
 * <p>
 * <pre>
 * import static com.crudetech.geometry.geom.Matrix.column;
 * import static com.crudetech.geometry.geom.Matrix.column;
 * import com.crudetech.geometry.geom3d.Matrix3d;
 *
 * Matrix3d mx = Matrix3d.Identity;
 * double value12 = mx.get(row(1), column(2));
 * </pre>
 */
public interface MatrixColumn {
    int getColumnIndex();
}
