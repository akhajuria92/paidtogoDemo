package com.aaronevans.paidtogo.modelmocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by leandro on 10/11/17.
 */

public class LeaderBoard {
    String name;
    List<Position> participants;
    Position currentUserPosition;

    public static LeaderBoard localMock() {
        LeaderBoard mock = new LeaderBoard();
        mock.name = "Paidtogo Local";
        mock.participants = mockParticipants();
        mock.currentUserPosition = Position.mock(2283);
        mock.currentUserPosition.participant = User.localMock();
        mock.participants.get(mock.currentUserPosition.ordinal).participant = mock.currentUserPosition.participant;
        return mock;
    }

    public static LeaderBoard mock() {
        LeaderBoard mock = new LeaderBoard();
        mock.name = "InfinixSoft Exercise";
        mock.participants = mockParticipants();
        mock.currentUserPosition = Position.mock(33.6f);
        mock.currentUserPosition.participant = User.localMock();
        mock.participants.get(mock.currentUserPosition.ordinal).participant = mock.currentUserPosition.participant;
        return mock;
    }

    private static List<Position> mockParticipants() {
        List<Position> mock = new ArrayList<>();
        Random r = new Random();
        for (int i=0; i<30; i++) mock.add(Position.mock(r.nextInt()));
        return mock;
    }

    public String getName() {
        return name;
    }

    public List<Position> getParticipants() {
        return participants;
    }

    public Position getCurrentUserPosition() {
        return currentUserPosition;
    }
}
