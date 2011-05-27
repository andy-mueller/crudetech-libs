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

/**
 * This class offers simple methods for common boolean checks,
 * typically done in if clauses. Its main purpose is to improve
 * readability.
 * <pre>
 *     import static com.crudetech.lang.If.*;
 *
 *     ...
 *     void foo(Object[] array){
 *         if(isNotNullOrEmpty(array)){
 *             ...
 *         }
 *     }
 * </pre>
 */
public class If {
    private If(){}
    public static boolean isNull(Object o){
        return o == null;
    }
    public static boolean isNotNull(Object o){
        return !isNull(o);
    }
    public static boolean isEmpty(CharSequence str){
        return str.length() == 0;
    }
    public static boolean isNullOrEmpty(CharSequence str){
        return isNull(str) || isEmpty(str);
    }
    public static boolean isNotNullOrEmpty(CharSequence str){
        return !isNullOrEmpty(str);
    }
    public static boolean isNotEmpty(CharSequence str){
        return !isEmpty(str);
    }

    public static boolean isEmpty(Object[] o){
        return o.length == 0;
    }
    public static boolean isNullOrEmpty(Object[] o){
        return isNull(o) || isEmpty(o);
    }
    public static boolean isNotNullOrEmpty(Object[] o){
        return !isNullOrEmpty(o);
    }

    public static boolean isNotEmpty(Object[] o){
        return !isEmpty(o);
    }
    public static boolean isEmpty(Iterable<?> i){
        return !i.iterator().hasNext();
    }
    public static boolean isNotEmpty(Iterable<?> i){
        return !isEmpty(i);
    }
    public static boolean isNotNullOrEmpty(Iterable<?> i){
        return !isNull(i) && !isEmpty(i);
    }
    public static boolean isNullOrEmpty(Iterable<?> i){
        return !isNotNullOrEmpty(i);
    }
    public static boolean isNot(boolean b){
        return !b;
    }
    public static boolean is(boolean b){
        return b;
    }
}
