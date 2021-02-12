package com.api.library.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class BookInstanceTest {

    @Nested
    class AcceptToBeHoldTo {
        @Test
        @DisplayName("It should accept to be hold to anyone if circulatingType is free CIRCULATING")
        void itShouldAcceptToBeHoldToAnyoneIfCirculatingTypeIsFreeCirculating() {
            BookInstance bookInstance = new BookInstance(new Book("12312313", "Alice", BigDecimal.TEN), CirculationType.CIRCULATING);
            Patron normal = new Patron(PatronType.NORMAL);
            Patron researcher = new Patron(PatronType.RESEARCHER);

            assertTrue(bookInstance.acceptToBeHoldTo(normal));
            assertTrue(bookInstance.acceptToBeHoldTo(researcher));
        }

        @Test
        @DisplayName("It should not accept to be hold to patron normal if circulatingType is restricted")
        void itShouldNotAcceptToBeHoldToPatronNormalIfCirculatingTypeIsRestricted() {
            BookInstance bookInstance = new BookInstance(new Book("12312313", "Alice", BigDecimal.TEN), CirculationType.RESTRICTED);
            Patron normal = new Patron(PatronType.NORMAL);

            assertFalse(bookInstance.acceptToBeHoldTo(normal));
        }

        @Test
        @DisplayName("It should accept to be hold to patron researcher even if circulatingType is restricted")
        void itShouldAcceptToBeHoldToPatronResearcherEvenIfCirculatingTypeIsRestricted() {
            BookInstance bookInstance = new BookInstance(new Book("12312313", "Alice", BigDecimal.TEN), CirculationType.RESTRICTED);
            Patron normal = new Patron(PatronType.RESEARCHER);

            assertTrue(bookInstance.acceptToBeHoldTo(normal));
        }
    }
}