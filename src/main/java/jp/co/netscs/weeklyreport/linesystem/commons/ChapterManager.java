package jp.co.netscs.weeklyreport.linesystem.commons;

import jp.co.netscs.weeklyreport.linesystem.commons.dtos.LineChapterDto;

public interface ChapterManager {
	
	public AbstractChapterSceneService targetSection(String section);
	
	public void registSection(AbstractChapterSceneService target);
	
	public LineChapterDto keywordMatchCapchar(String keyword);

}
