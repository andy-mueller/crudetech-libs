package com.crudetech.lang;

import com.crudetech.collections.Iterables;
import org.junit.Test;

import java.util.List;

import static com.crudetech.junit.AssertThrows.assertThrows;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class EnumsTest {
    private static enum AnEnum{ Value0, Value1, Value2};
    @Test
    public void ofOrdinalReturnsAppropriateEnumValue(){
        assertThat(Enums.ofOrdinal(AnEnum.class, 0), is(AnEnum.Value0));
        assertThat(Enums.ofOrdinal(AnEnum.class, 1), is(AnEnum.Value1));
        assertThat(Enums.ofOrdinal(AnEnum.class, 2), is(AnEnum.Value2));
    }

    @Test
    public void ofOrdinalThrowsOnInvalidOrdinal(){
        Runnable ofOrdinalWithBadOrdinalArgument = new Runnable() {
            @Override
            public void run() {
                Enums.ofOrdinal(AnEnum.class, 3);
            }
        };

        assertThrows(ofOrdinalWithBadOrdinalArgument, IllegalArgumentException.class);
    }

    @Test
    public void iterateEnumValues(){
        List<AnEnum> values = Iterables.copy(Enums.iterableOf(AnEnum.Value0));

        assertThat(values, is(asList(AnEnum.Value0, AnEnum.Value1, AnEnum.Value2)));
    }
     @Test
    public void iterableCanBePartial(){
        List<AnEnum> values = Iterables.copy(Enums.iterableOf(AnEnum.Value1));

        assertThat(values, is(asList(AnEnum.Value1, AnEnum.Value2)));
    }
}
