package com.xemplar.games.android.resource.ui;

public interface ProgressReporter {
	public void postProgress(long progress);
	public void onFinish();
	public void onStart();
}
