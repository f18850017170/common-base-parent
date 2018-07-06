package cn.vonfly.config.mybatis;

import cn.vonfly.common.dto.enumcode.EnumHandle;
import cn.vonfly.common.dto.enumcode.SimpleStatus;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Arrays;

@MappedTypes(SimpleStatus.class)
public class BaseEnumTypeHandler<E extends Enum & EnumHandle> extends BaseTypeHandler<E> {
	private final Class<E> type;

	public BaseEnumTypeHandler(Class<E> type) {
		if (type == null) {
			throw new IllegalArgumentException("Type argument cannot be null");
		}
		this.type = type;
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
		if (jdbcType == null) {
			ps.setInt(i, parameter.code());
		} else {
			ps.setObject(i, parameter.code(), jdbcType.TYPE_CODE); // see r3589
		}
	}

	@Override
	public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
		Integer s = rs.getInt(columnName);
		return s == null ? null : this.codeOf(type, s);
	}

	@Override
	public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		Integer s = rs.getInt(columnIndex);
		return s == null ? null : this.codeOf(type, s);
	}

	@Override
	public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		Integer s = cs.getInt(columnIndex);
		return s == null ? null : this.codeOf(type, s);
	}

	private E codeOf(Class<E> type, int code) {
		try {
			return Arrays.stream(type.getEnumConstants()).filter(e -> e.code() == code).iterator().next();

		} catch (Exception ex) {
			throw new IllegalArgumentException(
					"Cannot convert " + code + " to " + type.getSimpleName() + " by code value.", ex);
		}
	}
}
