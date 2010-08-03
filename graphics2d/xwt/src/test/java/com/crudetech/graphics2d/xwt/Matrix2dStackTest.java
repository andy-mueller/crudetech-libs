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

import com.crudetech.event.EventListener;
import com.crudetech.event.EventObject;
import com.crudetech.geometry.geom.RadianAngles;
import com.crudetech.geometry.geom2d.Matrix2d;
import com.crudetech.geometry.geom2d.Point2d;
import com.crudetech.geometry.geom2d.Vector2d;
import org.junit.Test;

import static java.lang.Math.sqrt;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;


public class Matrix2dStackTest {
    @Test
    public void defaultIsIdentity() {
        Matrix2dStack stack = new Matrix2dStack();

        assertThat(stack.peek(), is(Matrix2d.Identity));
    }

    @Test
    public void popRestoresPreviousTransformation() {
        Matrix2dStack stack = new Matrix2dStack();

        stack.pusXForm(Matrix2d.createRotationInRadians(Math.PI / 3));
        assertThat(stack.peek(), is(Matrix2d.createRotationInRadians(Math.PI / 3)));

        stack.pop();
        assertThat(stack.peek(), is(Matrix2d.Identity));
    }

    @Test
    public void translationsAddUp() {
        Matrix2dStack stack = new Matrix2dStack();

        stack.pushTranslation(new Vector2d(10.0, 10.0));
        stack.pushTranslation(new Vector2d(4.0, 6.0));

        Point2d pt = new Point2d(2, 3);
        Point2d actual = pt.transformBy(stack.peek());


        assertThat(actual, is(new Point2d(16, 19)));
    }

    @Test
    public void pushingAndPoppingTranslationsIsSymmetric() {
        Matrix2dStack stack = new Matrix2dStack();
        stack.pushTranslation(new Vector2d(10.0, 10.0));

        Matrix2d[] expected = stack.toArray();


        stack.pushTranslation(new Vector2d(4.0, 6.0));

        stack.pop();

        Matrix2d[] actual = stack.toArray();

        assertThat(actual, is(expected));
    }

    @Test
    public void pushRotationAddsRotation() {
        Matrix2dStack stack = new Matrix2dStack();

        stack.pushRotationInRadians(Math.PI / 4);

        Point2d actual = new Point2d(2, 2).transformBy(stack.peek());

        Point2d expected = new Point2d(0, 2 * Math.sqrt(2));

        assertThat(actual, is(expected));
    }

    @Test
    public void pushingAndPoppingRotationIsSymmetric() {
        Matrix2dStack stack = new Matrix2dStack();
        stack.pushTranslation(new Vector2d(10.0, 10.0));

        Matrix2d[] expected = stack.toArray();


        stack.pushRotationInRadians(Math.PI);

        stack.pop();

        Matrix2d[] actual = stack.toArray();

        assertThat(actual, is(expected));
    }

    @Test
    public void scaleCentersOnLocalAnchor() {
        CoordinateSystemStack stack = new Matrix2dStack();

        stack.pushTranslation(3, 4);
        stack.pushScale(2.0, 3.0, new Point2d(2, 2));

        Point2d actual = new Point2d(1, 1).transformBy(stack.peek());

        assertThat(actual, is(new Point2d(3, 3)));
    }

    @Test
    public void postMultWithScaleAddsNextXFormOnLastXform(){
        final Point2d p = new Point2d(1, 1);

        Matrix2dStack stack = new Matrix2dStack();
        stack.pushRotationInRadians(RadianAngles.k45);
        stack.pushTranslation(1, 1);
        stack.pushScale(2, 2);

        Point2d actual = stack.peek().multiply(p);

        assertThat(actual, is(new Point2d(0, 3*sqrt(2))));
    }

    @Test
    public void clearMakesStackEmptyAndDoesNotRaiseEvents() {
        EventListener<EventObject<NotifyingStack<Matrix2d>>> listener = mock(EventListener.class);
        Matrix2dStack stack = new Matrix2dStack(asList(Matrix2d.Identity, Matrix2d.Identity));
        stack.getPopEvent().addListener(listener);
        stack.getPushEvent().addListener(listener);

        stack.clear();

        assertThat(stack.isEmpty(), is(true));
        verify(listener, never()).onEvent((EventObject<NotifyingStack<Matrix2d>>) any());
    }
}

