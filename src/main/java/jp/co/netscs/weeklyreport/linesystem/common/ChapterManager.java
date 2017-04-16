package jp.co.netscs.weeklyreport.linesystem.common;

import jp.co.netscs.weeklyreport.linesystem.common.dtos.LineChapterDto;

public interface ChapterManager {
	
	public BaseChapterService targetSection(String section);
	
	public void registSection(BaseChapterService target);
	
	public LineChapterDto keywordMatchCapchar(String keyword);

}
