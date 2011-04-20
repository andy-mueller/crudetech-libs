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
package com.crudetech.junit.categories.stubs;

import com.crudetech.junit.categories.Categories;
import com.crudetech.junit.categories.CategoriesTest;
import com.crudetech.junit.categories.IncludeCategory;
import com.crudetech.junit.categories.TestNamePattern;
import org.junit.runner.RunWith;

@RunWith(Categories.class)
@IncludeCategory(MethodCategory.class)
@TestNamePattern(CategoriesTest.TestClassPattern)
public class CategoryOnMethodsSuite {
}
