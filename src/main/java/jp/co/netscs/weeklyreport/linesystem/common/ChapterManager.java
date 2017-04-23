package jp.co.netscs.weeklyreport.linesystem.common;

import jp.co.netscs.weeklyreport.linesystem.common.dtos.LineChapterDto;

public interface ChapterManager {
	
	public BaseChapterService targetChapter(String chapterName);
	
	public void registChapter(BaseChapterService target);
	
	public LineChapterDto getCapcharStartStatus(String chapterName);

}
