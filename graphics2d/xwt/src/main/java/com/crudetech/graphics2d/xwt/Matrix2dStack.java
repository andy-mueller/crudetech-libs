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

import com.crudetech.collections.ListStack;
import com.crudetech.event.Event;
import com.crudetech.event.EventObject;
import com.crudetech.geometry.geom2d.Matrix2d;
import com.crudetech.geometry.geom2d.Point2d;
import com.crudetech.geometry.geom2d.Vector2d;

import static com.crudetech.geometry.geom2d.Matrix2d.postMultiply;


class Matrix2dStack implements CoordinateSystemStack, NotifyingStack<Matrix2d>{
    private final NotifyingStackImp<Matrix2d> stack;
    Matrix2dStack(){
        stack = new NotifyingStackImp<Matrix2d>(new ListStack<Matrix2d>());
    }
    @Override
    public Event<EventObject<NotifyingStack<Matrix2d>>> getPushEvent() {
        return stack.getPushEvent();
    }

    @Override
    public Event<EventObject<NotifyingStack<Matrix2d>>> getPopEvent() {
        return stack.getPopEvent();
    }
    @Override
    public Matrix2d peek() {
        return stack.isEmpty() ? Matrix2d.Identity : stack.peek();
    }

    @Override
    public void pop() {
        stack.pop();
    }

    @Override
    public void pusXForm(Matrix2d xform) {
        Matrix2d peek = peek();
        stack.push(postMultiply(peek, xform));
    }

    @Override
    public boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    public Matrix2d[] toArray() {
        return stack.toArray(Matrix2d.class);
    }

    @Override
    public void pushTranslation(Vector2d translation) {
        pusXForm(Matrix2d.createTranslation(translation));
    }

    @Override
    public void pushRotationInRadians(double angle) {
        pusXForm(Matrix2d.createRotationInRadians(angle));
    }

    @Override
    public void pushScale(double scaleX, double scaleY) {
        final Point2d currentLocation = getCurrentLocation();
        final Matrix2d back  = peek().inverse();
        final Matrix2d scale = Matrix2d.createScale(scaleX, scaleY);

        Matrix2d m = scale.multiply(back);
        m = peek().multiply(m);

        pusXForm(Matrix2d.createScale(scaleX, scaleY));
//        pusXForm(m);
    }

    private Point2d getCurrentLocation() {
        return peek().getTranslation().toPoint2d();
    }

    @Override
    public void pushScale(double scaleX, double scaleY, Point2d center) {
        pusXForm(Matrix2d.createScale(scaleX, scaleY, center));
    }

    public void pushTranslation(double dx, double dy) {
        pushTranslation(new Vector2d(dx, dy));
    }
}
