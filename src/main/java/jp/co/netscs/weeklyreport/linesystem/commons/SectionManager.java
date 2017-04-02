package jp.co.netscs.weeklyreport.linesystem.commons;

public interface SectionManager {
	
	public AbstractChapterSceneService targetSection(String section);
	
	public void registSection(AbstractChapterSceneService target);

}
