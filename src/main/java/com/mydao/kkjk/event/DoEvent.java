package com.mydao.kkjk.event;

import java.util.EventListener;
import java.util.EventObject;

public class DoEvent {

	public static class ResultEvent extends EventObject{
		private static final long serialVersionUID = 1L;
		private ResultInfo result_info;

		public ResultEvent(Object source,ResultInfo result) {
			super(source);
			this.result_info = result;
		}

		public void setResultData(ResultInfo result) {
			this.result_info = result;
		}

		public ResultInfo getResultData() {
			return this.result_info;
		}
	}

	public interface ResultListener extends EventListener{
		public void ResultEvent(ResultEvent event);
	}

	public static class MatchEvent extends EventObject{
		private static final long serialVersionUID = 1L;
		private MatchInfo match_info;

		public MatchEvent(Object source,MatchInfo match) {
			super(source);
			this.match_info = match;
		}

		public void setMatchData(MatchInfo match) {
			this.match_info = match;
		}

		public MatchInfo getMatchData() {
			return this.match_info;
		}
	}

	public interface MatchListener extends EventListener{
		public void MatchEvent(MatchEvent event);
	}
}
