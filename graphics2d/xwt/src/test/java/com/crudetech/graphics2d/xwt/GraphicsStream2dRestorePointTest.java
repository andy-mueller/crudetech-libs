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
package com.crudetech.graphics2d.xwt;

import com.crudetech.geometry.geom2d.Matrix2d;
import org.junit.Ignore;
import org.junit.Test;

import static com.crudetech.matcher.RangeIsEqual.equalTo;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class GraphicsStream2dRestorePointTest {
    @Test @Ignore
    public void restoresXformAfterPush() {
        GraphicsContext ctx = mock(GraphicsContext.class);
        when(ctx.getTransform()).thenReturn(Matrix2d.Identity);

        GraphicsStream2d stream = new GraphicsStream2d(ctx);
        stream.getCoordinateSystemStack().pushRotationInRadians(Math.PI);
        stream.getCoordinateSystemStack().pushScale(2, 4);

        Iterable<Matrix2d> originalStack = asList(stream.getCoordinateSystemStack().toArray());

        GraphicsStream2d.RestorePoint rp = stream.createRestorePoint();
        try {
            stream.getCoordinateSystemStack().pushTranslation(10, 42);
        } finally {
            rp.restore();
        }

        assertThat(originalStack, is(equalTo(stream.getCoordinateSystemStack())));
    }
}
