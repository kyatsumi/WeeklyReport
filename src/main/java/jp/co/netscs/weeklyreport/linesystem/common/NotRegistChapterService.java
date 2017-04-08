package jp.co.netscs.weeklyreport.linesystem.common;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.linecorp.bot.model.message.Message;

import jp.co.netscs.weeklyreport.linesystem.common.annotation.Chapter;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.LinePostInfoDto;
import jp.co.netscs.weeklyreport.linesystem.common.util.LineBotConstant;

@Chapter(name = LineBotConstant.CHAPTER_END, startScene = LineBotConstant.UNKNOWN_SCENE)
public abstract class NotRegistChapterService extends AbstractChapterSceneService {

	public NotRegistChapterService(@Autowired ChapterManager manager) {
		super(manager);
	}
	
	public abstract List<Message> unknownScene(LinePostInfoDto lineInfo);

}
