package cn.vonfly.metadata.service;

import cn.vonfly.metadata.model.Label;
import com.github.pagehelper.PageInfo;

public interface ILabelService {
	int insert(Label label);
	PageInfo<Label> selectAll();
}
