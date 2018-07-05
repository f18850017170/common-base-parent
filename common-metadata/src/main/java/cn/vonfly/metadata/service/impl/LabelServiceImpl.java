package cn.vonfly.metadata.service.impl;

import cn.vonfly.metadata.dao.LabelDao;
import cn.vonfly.metadata.model.Label;
import cn.vonfly.metadata.service.ILabelService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LabelServiceImpl implements ILabelService {
	@Autowired
	private LabelDao labelDao;
	@Override
	public int insert(Label label) {
		return labelDao.insert(label);
	}

	@Override
	public PageInfo<Label> selectAll() {
		PageHelper.startPage(1,2);
		PageInfo<Label> result = new PageInfo(labelDao.selectAll());
		return result;
	}
}
