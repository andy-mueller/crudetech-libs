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
package com.crudetech.geometry.geom3d;

import com.crudetech.junit.feature.Equivalent;
import com.crudetech.junit.feature.Feature;
import com.crudetech.junit.feature.Features;
import org.junit.runner.RunWith;

import java.util.List;

import static java.util.Arrays.asList;

@RunWith(Features.class)
public class Vector3dTest {
    @Feature(Equivalent.class)
    public static Equivalent.Factory<Vector3d> equivalentFeature() {
        return new Equivalent.Factory<Vector3d>() {
            @Override
            public Vector3d createItem() {
                return new Vector3d(1,2,3);
            }

            @Override
            public List<Vector3d> createOtherItems() {
                final double tol = Tolerance3d.getGlobalTolerance().getVectorTolerance()*1.00000001;

                return asList(
                        new Vector3d(1+tol,2,3),
                        new Vector3d(1,2+tol,3),
                        new Vector3d(1,2,3+tol),
                        new Vector3d(1+tol,2,3+tol),
                        new Vector3d(1+tol,2+tol,3),
                        new Vector3d(1,2+tol,3+tol),
                        new Vector3d(1+tol,2+tol,3+tol)
                );
            }
        };
    }
}
