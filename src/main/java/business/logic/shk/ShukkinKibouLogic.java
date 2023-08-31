/**
 * ファイル名：ShukkinKibouLogic.java
 *
 * 変更履歴
 * 1.0  2010/10/06 Kazuya.Naraki
 */
package business.logic.shk;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import business.db.dao.shk.ShukkinKibouDao;
import business.dto.LoginUserDto;
import business.dto.shk.ShukkinKibouKakuninDto;
import business.dto.shk.ShukkinKibouNyuuryokuDto;
import business.logic.utils.CheckUtils;

/**
 * 説明：希望出勤日入力処理のロジック
 * 
 * @author naraki
 *
 */
public class ShukkinKibouLogic {

	public Map<String, List<ShukkinKibouNyuuryokuDto>> getShukkinKibouNyuuryokuDtoMap(String shainId, String yearMonth)
			throws SQLException {

		// 戻り値
		Map<String, List<ShukkinKibouNyuuryokuDto>> shukkinKibouNyuuryokuDtoMap = new LinkedHashMap<String, List<ShukkinKibouNyuuryokuDto>>();

		// Dao
		ShukkinKibouDao dao = new ShukkinKibouDao();

		// シフト情報を取得する。
		List<ShukkinKibouNyuuryokuDto> shukkinKibouNyuuryokuDtoList = dao.getShiftTblData(shainId, yearMonth);

		String oldShainId = "";

		// 一時領域
		List<ShukkinKibouNyuuryokuDto> tmpList = new ArrayList<ShukkinKibouNyuuryokuDto>();

		// DB取得より取得する値を各社員づつ区切る
		for (ShukkinKibouNyuuryokuDto dto : shukkinKibouNyuuryokuDtoList) {
			if (CheckUtils.isEmpty(oldShainId)) {
				// 社員IDが空のとき（初回）
				oldShainId = dto.getShainId();

				// 取得した値を戻り値のリストにセットする。
				tmpList.add(dto);

			} else {
				if (oldShainId.equals(dto.getShainId())) {
					// 同一社員のデータ
					// 取得した値を戻り値のリストにセットする。
					tmpList.add(dto);
				} else {
					// 別社員のデータのとき
					// 前の社員分をマップにつめる
					shukkinKibouNyuuryokuDtoMap.put(oldShainId, tmpList);

					// oldShainId を入れ替える
					oldShainId = dto.getShainId();

					// 新しい社員のデータを追加していく
					tmpList = new ArrayList<ShukkinKibouNyuuryokuDto>();
					tmpList.add(dto);
				}
			}
		}

		if (!CheckUtils.isEmpty(oldShainId)) {
			// 最後分を追加する
			shukkinKibouNyuuryokuDtoMap.put(oldShainId, tmpList);
		}

		return shukkinKibouNyuuryokuDtoMap;
	}

	/**
	 * シフトテーブルのデータを登録・更新する。
	 * 
	 * @param tsukibetsuShiftDtoListList 月別シフト一覧
	 * @return 基本シフトマップ
	 * @author naraki
	 * @throws SQLException
	 */
	public void registShukkinKibouNyuuryoku(List<List<ShukkinKibouNyuuryokuDto>> shukkinKibouNyuuryokuDtoListList,
			LoginUserDto loginUserDto) throws SQLException {

		// Dao
		ShukkinKibouDao dao = new ShukkinKibouDao();
		// コネクション
		Connection connection = dao.getConnection();

		// トランザクション処理
		connection.setAutoCommit(false);

		try {
			for (List<ShukkinKibouNyuuryokuDto>  shukkinKibouNyuuryokuDtoList : shukkinKibouNyuuryokuDtoListList) {
				// 人数分のループ
				for (ShukkinKibouNyuuryokuDto shukkinKibouNyuuryokuDto : shukkinKibouNyuuryokuDtoList) {
					// 日数分ループ

					// 社員ID
					String shainId = shukkinKibouNyuuryokuDto.getShainId();
					// 対象年月
					String yearMonthDay = shukkinKibouNyuuryokuDto.getYearMonthDay();

					// レコードの存在を確認する
					boolean isData = dao.isData(shainId, yearMonthDay);

					if (isData) {
						// 更新
						dao.updateShiftTbl(shukkinKibouNyuuryokuDto, loginUserDto);
					} else {
						// 登録
						dao.registShiftTbl(shukkinKibouNyuuryokuDto, loginUserDto);
					}

				}
			}

		} catch (SQLException e) {
			// ロールバック処理
			connection.rollback();
			// 切断
			connection.close();

			throw e;
		}

		// コミット
		connection.commit();
		// 切断
		connection.close();

	}

	/**
	 * 出勤希望確認画面に表示するリストを取得する。 戻り値・・・社員分の希望シフトリストのリスト
	 * 
	 * @param yearMonth 年月
	 * @return 出勤希望Dtoリストのリスト
	 * @author naraki
	 */
	public List<List<ShukkinKibouKakuninDto>> getShukkinKibouKakuninDtoList(String yearMonth) throws SQLException {

		// Dao
		ShukkinKibouDao dao = new ShukkinKibouDao();

		// シフト情報を取得する。
		List<List<ShukkinKibouKakuninDto>> kakuninDtoListList = dao.getShiftTblListList(yearMonth);

		return kakuninDtoListList;
	}
}
