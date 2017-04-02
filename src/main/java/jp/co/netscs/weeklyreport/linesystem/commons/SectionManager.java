package jp.co.netscs.weeklyreport.linesystem.commons;

public interface SectionManager {
	
	public AbstractSectionService targetSection(String section);
	
	public void registSection(AbstractSectionService target);

}
