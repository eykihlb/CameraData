package com.mydao.kkjk.event;


import com.mydao.kkjk.service.Program;

public class MatchDispatch implements DoEvent.MatchListener {

	@Override
	public void MatchEvent(DoEvent.MatchEvent event) {
		Program.MatchInfoDelegate(event.getMatchData());
	}
}
