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

import com.crudetech.collections.Iterables;
import com.crudetech.functional.UnaryFunction;
import com.crudetech.geometry.geom.Tolerance;
import com.crudetech.geometry.geom.ToleranceComparer;
import com.crudetech.lang.ArgumentOutOfBoundsException;
import com.crudetech.lang.Compare;

import java.util.ArrayList;
import java.util.List;

import static com.crudetech.query.Query.from;


public class Polygon2d extends AbstractToleranceComparable2d<Polygon2d> implements Curve2d{
    private final List<Point2d> points = new ArrayList<Point2d>();

    public Polygon2d(Iterable<Point2d> pts) {
        Iterables.copy(pts, this.points);

        if(points.size() < 3){
            throw new ArgumentOutOfBoundsException("pts", "A Polygon has at least three corner points!");
        }

        if(points.get(0).equals(points.get(points.size()-1))){
            points.remove(points.size()-1);
        }
    }

    @Override
    public int hashCode(Tolerance tol) {
        return Iterables.hashCode(points, new ToleranceComparer<Point2d>(tol));
    }
    @Override
    public boolean equals(Polygon2d rhs, Tolerance tol) {
        return Compare.equals(this.points, rhs.points, new ToleranceComparer<Point2d>(tol));
    }

    @Override
    public Polygon2d transformBy(final Matrix2d xform) {
        UnaryFunction<Point2d, Point2d> transformBy = new UnaryFunction<Point2d, Point2d>() {
            @Override
            public Point2d execute(Point2d point2d) {
                return point2d.transformBy(xform);
            }
        };
        Iterable<Point2d> newPol = from(points).select(transformBy);
        return new Polygon2d(newPol);
    }

    public Iterable<Point2d> getCornerPoints() {
        return points;
    }

    public int getNumberOfCorners() {
        return points.size();
    }
}
