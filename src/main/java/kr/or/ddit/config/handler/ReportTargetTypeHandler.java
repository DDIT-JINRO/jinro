package kr.or.ddit.config.handler;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;		// mybatis에 enum타입을 인식시켜주기 위함
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;			// mybatis에 AlarmType Enum을 인식하도록 설정

import kr.or.ddit.util.report.service.ReportTargetType;

@MappedTypes(ReportTargetType.class)
public class ReportTargetTypeHandler extends BaseTypeHandler<ReportTargetType>{

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, ReportTargetType parameter, JdbcType jdbcType)
			throws SQLException {
		ps.setString(i, parameter.getCode());	// enum에서 code값으로 변환 -> 삽입용
	}

	@Override
	public ReportTargetType getNullableResult(ResultSet rs, String columnName) throws SQLException {
		return ReportTargetType.fromCode(rs.getString(columnName));	// alarmTargetType 의 select된 결과를 enum으로 변환
	}

	@Override
	public ReportTargetType getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		return ReportTargetType.fromCode(rs.getString(columnIndex));
	}

	@Override
	public ReportTargetType getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		return ReportTargetType.fromCode(cs.getString(columnIndex));
	}

}
