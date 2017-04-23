package jp.co.netscs.weeklyreport.linesystem.common;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.persister.walking.spi.WalkingException;
import org.springframework.stereotype.Component;

import jp.co.netscs.weeklyreport.linesystem.common.dtos.LineChapterDto;
import jp.co.netscs.weeklyreport.linesystem.common.util.LineBotConstant;

@Component
public class ChapterManagerImpl implements ChapterManager {
	
	private List<BaseChapterService> chapterList = new ArrayList<>();

	/**
	 * 指定したチャプター名のインスタンスを取得する
	 * @throws WalkingException 指定した名前のチャプターが存在しない場合
	 */
	public BaseChapterService targetChapter(String chapterName) {
		BaseChapterService targetChapter = chapterList.stream()
			.filter(src -> src.getChapterName().equals(chapterName))
			.findFirst()
			.orElseThrow( () -> new WalkingException("対象のChapterが存在しません。 " + chapterName));
		return targetChapter;
	}

	@Override
	public void registChapter(BaseChapterService target) {
		
		String chapterName = target.getChapterName();
		if (chapterName == null || chapterName.isEmpty()) {
			throw new WalkingException(target.getClass().getName() + ": Chapter名がnullか空文字です");
		}
		
		boolean isOverlap  = chapterList.stream()
			.map(src -> src.getChapterName())
			.anyMatch(src -> src.equals(chapterName));
		
		if(isOverlap) {
			throw new WalkingException("登録されるセクションが重複しています。　チャプター名:" + chapterName);
		}
		
		chapterList.add(target);
	}

	/**
	 * チャプター名に一致するインスタンスが存在する場合はチャプターの最初のシーン情報を返す
	 * チャプターが存在しない場合はnullを返す
	 */
	@Override
	public LineChapterDto getCapcharStartStatus(String chapterName) {
		BaseChapterService target = chapterList.stream()
			.filter(src -> src.getChapterName().equals(chapterName))
			.findFirst()
			.orElse(null);
		
		if (target == null) {
			return null;
		}
		
		return LineChapterDto.builder().chapter(target.getChapterName()).scene(target.getStartSceneName()).responseScene(LineBotConstant.UNKNOWN_SCENE).build();
	}

}
