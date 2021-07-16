package com.aaronevans.paidtogo.modelmocks;

import java.util.Random;

/**
 * Created by leandro on 10/11/17.
 */

public class Position<T extends Number> {
    T score;
    User participant;
    int ordinal;

    public static <T extends Number> Position mock(T score) {
        Position<T> mock = new Position<>();
        mock.participant = User.mock();
        mock.score = score;
        mock.ordinal = Math.abs(new Random().nextInt()) % 30;
        return mock;
    }

    public T getScore() {
        return score;
    }

    public User getParticipant() {
        return participant;
    }

    public int getOrdinal() {
        return ordinal;
    }
}
