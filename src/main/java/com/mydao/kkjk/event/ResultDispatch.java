package com.mydao.kkjk.event;


import com.mydao.kkjk.service.Program;

public class ResultDispatch implements DoEvent.ResultListener {

	@Override
	public void ResultEvent(DoEvent.ResultEvent event) {
		Program.ResultInfoDelegate(event.getResultData());
	}
}
