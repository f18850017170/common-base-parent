package cn.vonfly.metadata.dao;

import cn.vonfly.metadata.model.Label;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
//可以引入通用mapper
@Mapper
public interface LabelDao {
	int insert(Label label);
	List<Label> selectAll();
}
