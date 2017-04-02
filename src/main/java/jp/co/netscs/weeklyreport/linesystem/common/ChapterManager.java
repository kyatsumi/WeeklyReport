package jp.co.netscs.weeklyreport.linesystem.common;

import jp.co.netscs.weeklyreport.linesystem.common.dtos.LineChapterDto;

public interface ChapterManager {
	
	public AbstractChapterSceneService targetSection(String section);
	
	public void registSection(AbstractChapterSceneService target);
	
	public LineChapterDto keywordMatchCapchar(String keyword);

}
