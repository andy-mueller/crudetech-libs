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
package com.crudetech.matcher;


import org.junit.Test;

import java.awt.geom.AffineTransform;

import static com.crudetech.matcher.AffineTransformIsEqual.equalTo;
import static com.crudetech.matcher.AffineTransformIsEqual.identity;
import static com.crudetech.matcher.FloatingPointMatcher.withTol;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;

public class AffineTransformIsEqualFixture {

   @Test
   public void isEqualTo(){
       AffineTransform mx2 = AffineTransform.getRotateInstance(Math.PI/4);
       AffineTransform mx1 = AffineTransform.getRotateInstance(Math.PI/4);

       assertThat(mx1, is(equalTo(mx2, withTol(1e-6))));
   }
   @Test
   public void notEqualTo(){
       AffineTransform mx2 = AffineTransform.getRotateInstance(Math.PI/4);
       AffineTransform mx1 = AffineTransform.getRotateInstance(Math.PI/4);

       mx1.scale(1.00001, 1);

       assertThat(mx1, is(not(equalTo(mx2, withTol(1e-6)))));
   }
    @Test
    public void isidentityWithTol(){
       assertThat(new AffineTransform(), is(identity(withTol(1e-6))));
   }
    @Test
    public void isidentity(){
       assertThat(new AffineTransform(), is(identity()));
   }
}