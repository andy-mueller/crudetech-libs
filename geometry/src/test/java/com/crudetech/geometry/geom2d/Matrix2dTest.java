////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2011, Andreas Mueller.
// All rights reserved. This program and the accompanying materials
// are made available under the terms of the Eclipse Public License v1.0
// which accompanies this distribution, and is available at
// http://www.eclipse.org/legal/epl-v10.html
//
// Contributors:
//      Andreas Mueller - initial API and implementation
////////////////////////////////////////////////////////////////////////////////
package com.crudetech.geometry.geom2d;

import com.crudetech.geometry.geom.RadianAngles;
import com.crudetech.junit.feature.Equivalent;
import com.crudetech.junit.feature.Feature;
import com.crudetech.junit.feature.Features;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static com.crudetech.geometry.geom.Matrix.column;
import static com.crudetech.geometry.geom.Matrix.row;
import static com.crudetech.geometry.geom2d.matcher.Matrix2dIsIdentity.isIdentity;
import static com.crudetech.geometry.geom2d.matcher.ToleranceEx.withGlobalTol;
import static com.crudetech.matcher.ThrowsException.doesThrow;
import static java.lang.Math.sin;
import static java.lang.Math.sqrt;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


@RunWith(Features.class)
public class Matrix2dTest {
    @Test
    public void defaultCtorCreatesIdentityMatrix() {
        Matrix2d mx = new Matrix2d();

        assertThat(mx.get(row(0), column(0)), is(1.0));
        assertThat(mx.get(row(0), column(1)), is(0.0));
        assertThat(mx.get(row(0), column(2)), is(0.0));

        assertThat(mx.get(row(1), column(0)), is(0.0));
        assertThat(mx.get(row(1), column(1)), is(1.0));
        assertThat(mx.get(row(1), column(2)), is(0.0));

        assertThat(mx.get(row(2), column(0)), is(0.0));
        assertThat(mx.get(row(2), column(1)), is(0.0));
        assertThat(mx.get(row(2), column(2)), is(1.0));

        assertThat(mx, isIdentity(withGlobalTol()));
    }

    @Test
    public void rowMajorCtor() {
        Matrix2d mx = new Matrix2d(new double[][]{
                {3, 4, 5},
                {1, 2, 3},
                {0, 0, 1},
        });

        assertThat(mx.get(row(0), column(0)), is(3.0));
        assertThat(mx.get(row(0), column(1)), is(4.0));
        assertThat(mx.get(row(0), column(2)), is(5.0));

        assertThat(mx.get(row(1), column(0)), is(1.0));
        assertThat(mx.get(row(1), column(1)), is(2.0));
        assertThat(mx.get(row(1), column(2)), is(3.0));

        assertThat(mx.get(row(2), column(0)), is(0.0));
        assertThat(mx.get(row(2), column(1)), is(0.0));
        assertThat(mx.get(row(2), column(2)), is(1.0));
    }

    @Test
    public void copyCtorCopies() {
        Matrix2d mx = new Matrix2d(new double[][]{
                {3, 4, 5},
                {1, 2, 3},
                {0, 0, 1},
        });
        Matrix2d mxCopy = new Matrix2d(mx);

        assertThat(mxCopy, is(mx));
    }

    @Test
    public void identityConstantIsIdentityMatrix() {
        assertThat(Matrix2d.Identity, isIdentity(withGlobalTol()));
    }

    @Test
    public void rowIndexOutOfRangeThrows() {
        final Matrix2d mx = new Matrix2d();

        Runnable doGetWithRowOutOfBounds = new Runnable() {
            @Override
            public void run() {
                mx.get(row(3), column(1));
            }
        };

        assertThat(doGetWithRowOutOfBounds, doesThrow(IllegalArgumentException.class));
    }

    @Test
    public void rowIndexIsNegativeThrows() {
        final Matrix2d mx = new Matrix2d();

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
        final Matrix2d mx = new Matrix2d();

        Runnable doGetWithColOutOfBounds = new Runnable() {
            @Override
            public void run() {
                mx.get(row(1), column(3));
            }
        };

        assertThat(doGetWithColOutOfBounds, doesThrow(IllegalArgumentException.class));
    }

    @Test
    public void colIndexIsNegativeThrows() {
        final Matrix2d mx = new Matrix2d();

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
        Matrix2d a = new Matrix2d(new double[][]{
                {3, 4, 5},
                {1, 2, 3},
                {0, 0, 1},
        });
        Matrix2d b = new Matrix2d(new double[][]{
                {6, 7, 2},
                {5, 4, 5},
                {0, 0, 1},
        });

        Matrix2d expected = new Matrix2d(new double[][]{
                {38, 37, 31},
                {16, 15, 15},
                {0, 0, 1},
        });

        assertThat(a.multiply(b), is(expected));
    }

    @Test
    public void transposingFlipsMatrix() {
        Matrix2d m = new Matrix2d(new double[][]{
                {6, 7, 2},
                {5, 4, 5},
                {0, 0, 1},
        });
        Matrix2d m2 = new Matrix2d(m);

        Matrix2d expected = new Matrix2d(new double[][]{
                {6, 5, 0},
                {7, 4, 0},
                {2, 5, 1},
        });

        Matrix2d trans = m.transpose();
        assertThat(trans, is(expected));
        assertThat("Original Matrix is not changed", m2, is(m));
    }

    @Test
    public void translationMovesPoint() {
        Point2d pt = new Point2d(4, 3);

        Matrix2d mx = Matrix2d.createTranslation(new Vector2d(3, 4));

        Point2d result = mx.multiply(pt);

        assertThat(result, is(new Point2d(7, 7)));
    }

    @Test
    public void rotationMovesPoint() {
        Point2d pt = new Point2d(0.5 * sqrt(3), -0.5);

        Matrix2d mx = Matrix2d.createRotationInRadians(30 * Math.PI / 180);

        Point2d result = mx.multiply(pt);

        assertThat(result, is(new Point2d(1.0, 0.0)));
    }

    @Test
    public void rotationAndTranslationMovesPoint() {
        Point2d pt = new Point2d(0.5 * sqrt(3), -0.5);

        Matrix2d trans = Matrix2d.createTranslation(new Vector2d(3, 4));
        Matrix2d rot = Matrix2d.createRotationInRadians(30 * Math.PI / 180);

        Matrix2d mx = trans.multiply(rot);

        Point2d result = mx.multiply(pt);

        assertThat(result, is(new Point2d(4.0, 4.0)));
    }

    @Test
    public void rotationAndTranslationOnlyRotatesVector() {
        Vector2d vec = new Vector2d(0.5 * sqrt(3), -0.5);

        Matrix2d trans = Matrix2d.createTranslation(new Vector2d(3, 4));
        Matrix2d rot = Matrix2d.createRotationInRadians(30 * Math.PI / 180);

        Matrix2d mx = trans.multiply(rot);

        Vector2d result = mx.multiply(vec);

        assertThat(result, is(new Vector2d(1.0, 0.0)));
    }

    @Test
    public void translationDoesNotChangeVector() {
        Vector2d vec = new Vector2d(1, 3);
        Matrix2d trans = Matrix2d.createTranslation(new Vector2d(3, 4));

        Vector2d result = trans.multiply(vec);

        assertThat(result, is(new Vector2d(1, 3)));
    }

    @Test
    public void xScaleScalesXCoordinateOfPoint() {
        Point2d p = new Point2d(2, 1);

        Matrix2d scaleX = Matrix2d.createScaleX(0.5);

        Point2d result = scaleX.multiply(p);

        assertThat(result, is(new Point2d(1, 1)));
    }

    @Test
    public void yScaleScalesYCoordinateOfPoint() {
        Point2d p = new Point2d(2, 2);

        Matrix2d scaleY = Matrix2d.createScaleY(0.5);

        Point2d result = scaleY.multiply(p);

        assertThat(result, is(new Point2d(2, 1)));
    }

    @Test
    public void scaleRespectsCenter() {
        Point2d center = new Point2d(1, 1);

        Matrix2d scale = Matrix2d.createScale(2, 3, center);

        assertThat(new Point2d(2, 2).transformBy(scale), is(new Point2d(3, 4)));
    }

    @Test
    public void scaleRespectsCenter2() {
        Point2d center = new Point2d(2, 2);

        Matrix2d scale = Matrix2d.createScale(2, 3, center);

        assertThat(new Point2d(1, 1).transformBy(scale), is(new Point2d(0, -1)));
    }

    @Test
    public void scaleRespectsOffsetCenter() {
        Point2d center = new Point2d(1, 1);

        Matrix2d trans = Matrix2d.createTranslation(1, 2);

        Matrix2d scale = Matrix2d.createScale(2, 3, center);

        Matrix2d mx = Matrix2d.preMultiply(trans, scale);

        assertThat(new Point2d(2, 2).transformBy(mx), is(new Point2d(4, 6)));
    }

    @Test
    public void inverse() {
        Matrix2d m = new Matrix2d(new double[][]{
                {6, 7, 2},
                {5, 4, 5},
                {0, 0, 1},
        });

        Matrix2d i = m.inverse();

        assertThat(i.multiply(m), is(Matrix2d.Identity));
    }

    @Test
    public void preMultAddsNextXFormGlobally() {
        Point2d p = new Point2d(1, 1);

        Matrix2d rot45 = Matrix2d.createRotationInRadians(RadianAngles.k45);
        Matrix2d trans11 = Matrix2d.createTranslation(1, 1);

        Matrix2d mx = Matrix2d.preMultiply(trans11, rot45);

        Point2d actual = mx.multiply(p);

        assertThat(actual, is(new Point2d(1, 1 + sqrt(2))));
    }

    @Test
    public void postMultAddsNextXFormOnLastXform() {
        Point2d p = new Point2d(1, 1);

        Matrix2d rot45 = Matrix2d.createRotationInRadians(RadianAngles.k45);
        Matrix2d trans11 = Matrix2d.createTranslation(1, 1);

        Matrix2d mx = Matrix2d.postMultiply(rot45, trans11);

        Point2d actual = mx.multiply(p);

        assertThat(actual, is(new Point2d(0, 2 * sqrt(2))));
    }

    @Test
    public void postMultWithScaleAddsNextXFormOnLastXform() {
        Point2d p = new Point2d(1, 1);

        Matrix2d rot45 = Matrix2d.createRotationInRadians(RadianAngles.k45);
        Matrix2d trans11 = Matrix2d.createTranslation(1, 1);
        Matrix2d scale = Matrix2d.createScale(2, 2);

        Matrix2d mx = Matrix2d.postMultiply(rot45, trans11, scale);

        Point2d actual = mx.multiply(p);

        assertThat(actual, is(new Point2d(0, 3 * sqrt(2))));
    }

    @Test
    public void rotatesPointBy45() {
        final double sin45 = sin(RadianAngles.k45);
        Point2d p = new Point2d(3 * sin45, 3 * sin45);

        Matrix2d rot45 = Matrix2d.createRotationInRadians(RadianAngles.k45);
        Matrix2d trans21 = Matrix2d.createTranslation(2, 1);

        Matrix2d mx = trans21.multiply(rot45);
//        Matrix2d mx = rot45.multiply(trans21);

        Point2d actual = mx.multiply(p);

        assertThat(actual, is(new Point2d(2, 4)));
    }

    @Feature(Equivalent.class)
    public static Equivalent.Factory<Matrix2d> equivalentFeature() {
        return new Equivalent.Factory<Matrix2d>() {
            private final double[][] rawMx = new double[][]{
                    {3, 4, 32},
                    {4, 1, 45},
                    {3, 333, 4}

            };

            @Override
            public Matrix2d createItem() {
                return new Matrix2d(rawMx.clone());
            }

            @Override
            public List<Matrix2d> createOtherItems() {
                List<Matrix2d> others = new ArrayList<Matrix2d>();
                others.add(Matrix2d.Identity);

                for (int i = 0; i < 3; ++i) {
                    for (int j = 0; j < 3; ++j) {
                        double[][] data = rawMx.clone();
                        data[i][j] += Tolerance2d.getGlobalTolerance().getVectorTolerance();
                        others.add(new Matrix2d(data));
                    }

                }
                return others;
            }
        };
    }

}
