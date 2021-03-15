package com.api.library.domain;

public enum PatronType {
    RESEARCHER{
        @Override
        public boolean allowedToHold(long amountOfCurrentHolds) {
            return true;
        }
    }, NORMAL {
        @Override
        public boolean allowedToHold(long amountOfCurrentHolds) {
            return amountOfCurrentHolds < 5;
        }
    };

    public abstract boolean allowedToHold(long amountOfCurrentHolds);
}
