package jp.co.netscs.weeklyreport.linesystem.view;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.linecorp.bot.model.message.Message;
import com.linecorp.bot.model.message.TextMessage;

import jp.co.netscs.weeklyreport.linesystem.common.ChapterManager;
import jp.co.netscs.weeklyreport.linesystem.common.annotation.Chapter;
import jp.co.netscs.weeklyreport.linesystem.common.annotation.Scene;
import jp.co.netscs.weeklyreport.linesystem.common.dtos.LinePostInfoDto;
import jp.co.netscs.weeklyreport.linesystem.common.entitis.UserEntity;
import jp.co.netscs.weeklyreport.linesystem.common.util.LineBotConstant;

@Chapter(name = LineBotConstant.CHAPTER_REPORTVIEW, startScene = LineBotConstant.REPORTVIEW_SCENE_VIEW)
public class ReportViewServiceImpl extends ReportViewService {

	protected ReportViewServiceImpl(@Autowired ChapterManager manager) {
		super(manager);
	}

	@Override
	@Scene(name = LineBotConstant.REPORTVIEW_SCENE_VIEW)
	public List<Message> myReportView(LinePostInfoDto lineInfo, UserEntity userInfo) {
		Message message = new TextMessage("日付:2017/04/08\n17:30~18:00 自社にてミーティング\n18:30~20:30 新人歓迎会\n日付:2017/04/XX\nここに入力した内容を表示します。\n最大文字数は１日につき１６０文字です。");
		return Arrays.asList(message);
	}
	
	
	
	

}
