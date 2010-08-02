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

import com.crudetech.geometry.geom.RadianAngles;
import org.junit.Ignore;
import org.junit.Test;

import static com.crudetech.geometry.geom.Matrix.column;
import static com.crudetech.geometry.geom.Matrix.row;
import static com.crudetech.geometry.geom2d.matcher.ToleranceEx.withGlobalTol;
import static com.crudetech.geometry.geom3d.matcher.Matrix3dIsIdentity.isIdentity;
import static com.crudetech.matcher.FloatingPointMatcher.equalTo;
import static com.crudetech.matcher.FloatingPointMatcher.withTol;
import static com.crudetech.matcher.ThrowsException.doesThrow;
import static java.lang.Math.sqrt;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class Matrix3dTest {
    @Test
    public void defaultCtorCreatesIdentityMatrix() {
        Matrix3d mx = new Matrix3d();

        assertThat(mx.isIdentity(), is(true));

        assertThat(mx.get(row(0), column(0)), is(1.0));
        assertThat(mx.get(row(0), column(1)), is(0.0));
        assertThat(mx.get(row(0), column(2)), is(0.0));
        assertThat(mx.get(row(0), column(3)), is(0.0));

        assertThat(mx.get(row(1), column(0)), is(0.0));
        assertThat(mx.get(row(1), column(1)), is(1.0));
        assertThat(mx.get(row(1), column(2)), is(0.0));
        assertThat(mx.get(row(1), column(3)), is(0.0));

        assertThat(mx.get(row(2), column(0)), is(0.0));
        assertThat(mx.get(row(2), column(1)), is(0.0));
        assertThat(mx.get(row(2), column(2)), is(1.0));
        assertThat(mx.get(row(2), column(3)), is(0.0));

        assertThat(mx.get(row(3), column(0)), is(0.0));
        assertThat(mx.get(row(3), column(1)), is(0.0));
        assertThat(mx.get(row(3), column(2)), is(0.0));
        assertThat(mx.get(row(3), column(3)), is(1.0));

        assertThat(mx, isIdentity(withGlobalTol()));
    }

    @Test
    public void rowMajorCtor() {
        Matrix3d mx = new Matrix3d(new double[][]{
                {3, 4, 5, 4},
                {1, 2, 3, 4},
                {0, 0, 1, 4},
                {3, 4, 5, 2},
        });

        assertThat(mx.get(row(0), column(0)), is(3.0));
        assertThat(mx.get(row(0), column(1)), is(4.0));
        assertThat(mx.get(row(0), column(2)), is(5.0));
        assertThat(mx.get(row(0), column(3)), is(4.0));

        assertThat(mx.get(row(1), column(0)), is(1.0));
        assertThat(mx.get(row(1), column(1)), is(2.0));
        assertThat(mx.get(row(1), column(2)), is(3.0));
        assertThat(mx.get(row(1), column(3)), is(4.0));

        assertThat(mx.get(row(2), column(0)), is(0.0));
        assertThat(mx.get(row(2), column(1)), is(0.0));
        assertThat(mx.get(row(2), column(2)), is(1.0));
        assertThat(mx.get(row(2), column(3)), is(4.0));

        assertThat(mx.get(row(3), column(0)), is(3.0));
        assertThat(mx.get(row(3), column(1)), is(4.0));
        assertThat(mx.get(row(3), column(2)), is(5.0));
        assertThat(mx.get(row(3), column(3)), is(2.0));
    }

    @Test
    public void identityConstantIsIdentityMatrix() {
        assertThat(Matrix3d.Identity, isIdentity(withGlobalTol()));
    }

    @Test
    public void rowIndexOutOfRangeThrows() {
        final Matrix3d mx = new Matrix3d();

        Runnable doGetWithRowOutOfBounds = new Runnable() {
            @Override
            public void run() {
                mx.get(row(4), column(1));
            }
        };

        assertThat(doGetWithRowOutOfBounds, doesThrow(IllegalArgumentException.class));
    }

    @Test
    public void rowIndexIsNegativeThrows() {
        final Matrix3d mx = new Matrix3d();

        Runnable doGetWithRowOutOfBounds = new Runnable() {
            @Override
            public void run() {
                mx.get(row(-1), column(1));
            }
        };

        assertThat(doGetWithRowOutOfBounds, doesThrow(IllegalArgumentException.class));
    }

    @Test
    public void colIndexOutOfRangeThrows() {
        final Matrix3d mx = new Matrix3d();

        Runnable doGetWithColOutOfBounds = new Runnable() {
            @Override
            public void run() {
                mx.get(row(1), column(4));
            }
        };

        assertThat(doGetWithColOutOfBounds, doesThrow(IllegalArgumentException.class));
    }

    @Test
    public void colIndexIsNegativeThrows() {
        final Matrix3d mx = new Matrix3d();

        Runnable doGetWithColOutOfBounds = new Runnable() {
            @Override
            public void run() {
                mx.get(row(1), column(-1));
            }
        };

        assertThat(doGetWithColOutOfBounds, doesThrow(IllegalArgumentException.class));
    }


    @Test
    public void multiplyDoesPreMult() {
        Matrix3d a = new Matrix3d(new double[][]{
                {3, 4, 5, 4},
                {1, 2, 3, 3},
                {3, 2, 1, 7},
                {2, 3, 4, 1},
        });
        Matrix3d b = new Matrix3d(new double[][]{
                {6, 7, 2, 2},
                {5, 4, 5, 5},
                {9, 4, 3, 4},
                {9, 2, 6, 1},
        });

        Matrix3d expected = new Matrix3d(new double[][]{
                {119, 65, 65, 50},
                {70, 33, 39, 27},
                {100, 47, 61, 27},
                {72, 44, 37, 36},
        });

        assertThat(a.multiply(b), is(expected));
    }

    @Test
    public void transposingFlipsMatrix() {
        Matrix3d m = new Matrix3d(new double[][]{
                {6, 7, 2, 2},
                {5, 4, 5, 1},
                {0, 0, 1, 7},
                {2, 1, 2, 9},
        });
        Matrix3d m2 = new Matrix3d(m);

        Matrix3d expected = new Matrix3d(new double[][]{
                {6, 5, 0, 2},
                {7, 4, 0, 1},
                {2, 5, 1, 2},
                {2, 1, 7, 9},
        });

        Matrix3d trans = m.transpose();
        assertThat(trans, is(expected));
        assertThat("Original Matrix is not changed", m2, is(m));
    }


    @Test
    public void translationMovesPoint() {
        Point3d pt = new Point3d(4, 3, 1);

        Matrix3d mx = Matrix3d.createTranslation(new Vector3d(3, 4, 6));

        Point3d result = mx.multiply(pt);

        assertThat(result, is(new Point3d(7, 7, 7)));
    }

    @Test
    public void rotationMovesPoint() {
        Point3d pt = new Point3d(0.5 * sqrt(3), -0.5, 0.0);

        Matrix3d mx = Matrix3d.createRotationAroundZAxisInRadians(30 * Math.PI / 180);

        Point3d result = mx.multiply(pt);

        assertThat(result, is(new Point3d(1.0, 0.0, 0.0)));
    }

    @Test
    public void rotationAndTranslationMovesPoint() {
        Point3d pt = new Point3d(0.5 * sqrt(3), -0.5, 0);

        Matrix3d trans = Matrix3d.createTranslation(new Vector3d(3, 4, 0.0));
        Matrix3d rot = Matrix3d.createRotationAroundZAxisInRadians(30 * Math.PI / 180);

        Matrix3d mx = trans.multiply(rot);

        Point3d result = mx.multiply(pt);

        assertThat(result, is(new Point3d(4.0, 4.0, 0.0)));
    }

    @Test
    public void rotationAndTranslationOnlyRotatesVector() {
        Vector3d pt = new Vector3d(0.5 * sqrt(3), -0.5, 0);

        Matrix3d trans = Matrix3d.createTranslation(new Vector3d(3, 4, 0.0));
        Matrix3d rot = Matrix3d.createRotationAroundZAxisInRadians(30 * Math.PI / 180);

        Matrix3d mx = trans.multiply(rot);

        Vector3d result = mx.multiply(pt);

        assertThat(result.getX(), is(equalTo(1.0, withTol(1e-12))));
        assertThat(result.getY(), is(equalTo(0.0, withTol(1e-12))));
        assertThat(result.getZ(), is(equalTo(0.0, withTol(1e-12))));
    }

    @Test
    public void translationDoesNotChangeVector() {
        Vector3d vec = new Vector3d(1, 3, 0);
        Matrix3d trans = Matrix3d.createTranslation(new Vector3d(3, 4, 0));

        Vector3d result = trans.multiply(vec);

        assertThat(result.getX(), is(equalTo(1.0, withTol(1e-12))));
        assertThat(result.getY(), is(equalTo(3.0, withTol(1e-12))));
        assertThat(result.getZ(), is(equalTo(0.0, withTol(1e-12))));
    }

    @Test
    public void inverse() {
        Matrix3d m = new Matrix3d(new double[][]{
                {6, 7, 2, 2},
                {5, 4, 5, 1},
                {0, 0, 1, 7},
                {2, 1, 2, 9},
        });

        Matrix3d i = m.inverse();

        assertThat(m.multiply(i), is(Matrix3d.Identity));
    }

    @Test
    public void rotationAroundXAxisRotatesPoint(){
        Vector3d v = new Vector3d(0.0, 0.5 * sqrt(3), -0.5);

        Matrix3d rotX = Matrix3d.createRotationAroundXAxisInRadians(30 * Math.PI / 180);
        Vector3d result = rotX.multiply(v);

        assertThat(result, is(new Vector3d(0.0, 1.0, 0.0)));
    }
    @Test
    public void rotationAroundYAxisRotatesPoint(){
        Vector3d v = new Vector3d(0.5 * sqrt(3), 0.0, 0.5);

        Matrix3d rotY = Matrix3d.createRotationAroundYAxisInRadians(30 * Math.PI / 180);
        Vector3d result = rotY.multiply(v);

        assertThat(result, is(new Vector3d(1.0, 0.0, 0.0)));
    }
    @Test
    @Ignore
    public void rotationAroundArbitraryAxisRotatesPoint(){
        Vector3d v = new Vector3d(0.5 * sqrt(3), 0.0, 0.5);

        Matrix3d rot = Matrix3d.createRotationInRadians(new Point3d(2,5,7), new Vector3d(1,2,3), RadianAngles.k30);
        Vector3d result = rot.multiply(v);

        assertThat(result, is(new Vector3d(1.0, 0.0, 0.0)));
    }

    @Test
    public void xScaleScalesXCoordinateOfPoint(){
        Point3d p = new Point3d(2,1,6);

        Matrix3d scaleX = Matrix3d.createScaleX(0.5);

        Point3d result = scaleX.multiply(p);

        assertThat(result, is(new Point3d(1, 1, 6)));
    }
    @Test
    public void yScaleScalesYCoordinateOfPoint(){
        Point3d p = new Point3d(2,2,6);

        Matrix3d scaleY = Matrix3d.createScaleY(0.5);

        Point3d result = scaleY.multiply(p);

        assertThat(result, is(new Point3d(2, 1, 6)));
    }
    @Test
    public void zScaleScalesZCoordinateOfPoint(){
        Point3d p = new Point3d(2,2,6);

        Matrix3d scaleZ = Matrix3d.createScaleZ(0.5);

        Point3d result = scaleZ.multiply(p);

        assertThat(result, is(new Point3d(2, 2, 3)));
    }
}
