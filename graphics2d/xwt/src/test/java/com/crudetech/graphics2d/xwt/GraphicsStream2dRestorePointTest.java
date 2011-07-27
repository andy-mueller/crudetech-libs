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
package com.crudetech.graphics2d.xwt;

import com.crudetech.geometry.geom2d.Matrix2d;
import org.junit.Test;

import static com.crudetech.matcher.RangeIsEqual.equalTo;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class GraphicsStream2dRestorePointTest {
    @Test
    public void restoresXformAfterPush() {
        GraphicsContext ctx = mock(GraphicsContext.class);
        when(ctx.getTransform()).thenReturn(Matrix2d.Identity);

        GraphicsStream2d stream = new GraphicsStream2d(ctx);
        stream.getCoordinateSystemStack().pushRotationInRadians(Math.PI);
        stream.getCoordinateSystemStack().pushScale(2, 4);

        Iterable<Matrix2d> originalStack = asList(stream.getCoordinateSystemStack().toArray());

        try (GraphicsStream2d.RestorePoint rp = stream.createRestorePoint()) {
            stream.getCoordinateSystemStack().pushTranslation(10, 42);
        }

        assertThat(originalStack, is(equalTo(stream.getCoordinateSystemStack())));
    }

    @Test
    public void restoresXformAfterPop() {
        GraphicsContext ctx = mock(GraphicsContext.class);
        when(ctx.getTransform()).thenReturn(Matrix2d.Identity);

        GraphicsStream2d stream = new GraphicsStream2d(ctx);
        stream.getCoordinateSystemStack().pushRotationInRadians(Math.PI);
        stream.getCoordinateSystemStack().pushScale(2, 4);

        Iterable<Matrix2d> originalStack = asList(stream.getCoordinateSystemStack().toArray());

        try (GraphicsStream2d.RestorePoint rp = stream.createRestorePoint()) {
            stream.getCoordinateSystemStack().pop();
        }

        assertThat(originalStack, is(equalTo(stream.getCoordinateSystemStack())));
    }

    @Test
    public void restoresPenStackAfterPush() {
        GraphicsContext ctx = mock(GraphicsContext.class);
        when(ctx.getPen()).thenReturn(new Pen(2));
        when(ctx.getTransform()).thenReturn(Matrix2d.Identity);

        GraphicsStream2d stream = new GraphicsStream2d(ctx);
        stream.getPenStack().push(new Pen(3));
        stream.getPenStack().push(new Pen(1));
        stream.getPenStack().push(new Pen(5));
        Iterable<Pen> originalStack = asList(stream.getPenStack().toArray(Pen.class));

        try (GraphicsStream2d.RestorePoint rp = stream.createRestorePoint()) {
            stream.getPenStack().push(new Pen(4));
        }

        assertThat(originalStack, is(equalTo(stream.getPenStack())));
    }

    @Test
    public void restoresPenStackAfterPop() {
        GraphicsContext ctx = mock(GraphicsContext.class);
        when(ctx.getPen()).thenReturn(new Pen(42));
        when(ctx.getTransform()).thenReturn(Matrix2d.Identity);

        GraphicsStream2d stream = new GraphicsStream2d(ctx);
        stream.getPenStack().push(new Pen(3));
        stream.getPenStack().push(new Pen(1));
        stream.getPenStack().push(new Pen(5));
        Iterable<Pen> originalStack = asList(stream.getPenStack().toArray(Pen.class));

        try (GraphicsStream2d.RestorePoint rp = stream.createRestorePoint()) {
            stream.getPenStack().pop();
        }

        assertThat(originalStack, is(equalTo(stream.getPenStack())));
    }

    @Test
    public void restoresFontStackAfterPush() {
        GraphicsContext ctx = mock(GraphicsContext.class);
        when(ctx.getPen()).thenReturn(new Pen(2));
        when(ctx.getTransform()).thenReturn(Matrix2d.Identity);

        GraphicsStream2d stream = new GraphicsStream2d(ctx);
        stream.getFontStack().push(new Font("Arial", FontStyle.Bold, 23));
        stream.getFontStack().push(new Font("Courier", FontStyle.Plain, 19));
        stream.getFontStack().push(new Font("Arial", FontStyle.Italic, 8));
        Iterable<Font> originalStack = asList(stream.getFontStack().toArray(Font.class));

        try (GraphicsStream2d.RestorePoint rp = stream.createRestorePoint()) {
            stream.getFontStack().push(new Font("Times", FontStyle.Plain, 23));
        }

        assertThat(originalStack, is(equalTo(stream.getFontStack())));
    }

    @Test
    public void restoresFontStackAfterPop() {
        GraphicsContext ctx = mock(GraphicsContext.class);
        when(ctx.getPen()).thenReturn(new Pen(2));
        when(ctx.getTransform()).thenReturn(Matrix2d.Identity);

        GraphicsStream2d stream = new GraphicsStream2d(ctx);
        stream.getFontStack().push(new Font("Arial", FontStyle.Bold, 23));
        stream.getFontStack().push(new Font("Courier", FontStyle.Plain, 19));
        stream.getFontStack().push(new Font("Arial", FontStyle.Italic, 8));
        Iterable<Font> originalStack = asList(stream.getFontStack().toArray(Font.class));

        try (GraphicsStream2d.RestorePoint rp = stream.createRestorePoint()) {
            stream.getFontStack().pop();
        }

        assertThat(originalStack, is(equalTo(stream.getFontStack())));
    }

    @Test
    public void restoresBrushStackAfterPush() {
        GraphicsContext ctx = mock(GraphicsContext.class);
        when(ctx.getPen()).thenReturn(new Pen(2));
        when(ctx.getTransform()).thenReturn(Matrix2d.Identity);

        GraphicsStream2d stream = new GraphicsStream2d(ctx);
        stream.getBrushStack().push(new SolidBrush(Color.Blue));
        stream.getBrushStack().push(new SolidBrush(Color.Black));
        stream.getBrushStack().push(new SolidBrush(Color.Green));
        Iterable<Brush> originalStack = asList(stream.getBrushStack().toArray(Brush.class));

        try (GraphicsStream2d.RestorePoint rp = stream.createRestorePoint()) {
            stream.getBrushStack().push(new SolidBrush(Color.Red));
        }

        assertThat(originalStack, is(equalTo(stream.getBrushStack())));
    }

    @Test
    public void restoresBrushStackAfterPop() {
        GraphicsContext ctx = mock(GraphicsContext.class);
        when(ctx.getPen()).thenReturn(new Pen(2));
        when(ctx.getTransform()).thenReturn(Matrix2d.Identity);

        GraphicsStream2d stream = new GraphicsStream2d(ctx);
        stream.getBrushStack().push(new SolidBrush(Color.Blue));
        stream.getBrushStack().push(new SolidBrush(Color.Black));
        stream.getBrushStack().push(new SolidBrush(Color.Green));
        Iterable<Brush> originalStack = asList(stream.getBrushStack().toArray(Brush.class));

        try (GraphicsStream2d.RestorePoint rp = stream.createRestorePoint()) {
            stream.getBrushStack().pop();
        }

        assertThat(originalStack, is(equalTo(stream.getBrushStack())));
    }
}
