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
package com.crudetech.lang;


class DiceRoll extends ComparableBoundedValue<Integer, DiceRoll>{
    public DiceRoll(int value) {
        super(value, 1, 7);
    }
    public DiceRoll(){
        super(0,  1, 7);
    }

    static DiceRoll of(int value) {
        return new DiceRoll(value);
    }


}
