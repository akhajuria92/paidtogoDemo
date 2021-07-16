package com.aaronevans.paidtogo.ui.main.leaderboard;

import com.aaronevans.paidtogo.modelmocks.LeaderBoard;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.subjects.ReplaySubject;

/**
 * Created by leandro on 7/11/17.
 */

public class LeaderBoardModel implements LeaderBoardContract.Model {
    private ReplaySubject<List<LeaderBoard>> mLeaderBoardsStream = ReplaySubject.create();

    private List<LeaderBoard> mLeaderBoards = new ArrayList<>();
    {
        //Call APIs here
        mLeaderBoards.add(LeaderBoard.localMock());
        mLeaderBoards.add(LeaderBoard.mock());
        mLeaderBoards.add(LeaderBoard.mock());
        mLeaderBoards.add(LeaderBoard.mock());
        mLeaderBoardsStream.onNext(mLeaderBoards);
    }

    public Observable<List<LeaderBoard>> getLeaderBoardsStream() {
        return mLeaderBoardsStream.publish();
    }
}