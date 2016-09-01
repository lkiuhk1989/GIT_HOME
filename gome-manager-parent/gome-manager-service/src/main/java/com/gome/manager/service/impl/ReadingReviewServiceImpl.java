package com.gome.manager.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.gome.manager.dao.ReadingReviewMapper;
import com.gome.manager.domain.ReadingReview;
import com.gome.manager.domain.ReadingSupport;
import com.gome.manager.service.ReadingReviewService;

/**
 * 
 * 导读service接口实现类.
 * 
 * <pre>
 * 修改日期        修改人    修改原因
 * 2015年11月09日    caowei    新建
 * </pre>
 */
@Service("readingReviewService")
public class ReadingReviewServiceImpl implements ReadingReviewService {
	@Resource
	ReadingReviewMapper readingReviewMapper;

	@Override
	public List<ReadingReview> queryReviewList(ReadingReview review) {
		// TODO Auto-generated method stub
		List<ReadingReview> reviewList = readingReviewMapper.queryReviewList(review);
		if(reviewList!=null && reviewList.size()>0){
			for (int i = 0; i < reviewList.size(); i++) {
				String createTime = reviewList.get(i).getCreateTime();
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
				long time = 0;
				try {
					time = sdf.parse(createTime).getTime();//分钟
				} catch (ParseException e) {
					e.printStackTrace();
				}
				Date now = new Date();
				long time2 = now.getTime();//分钟
				long cha = (time2-time)/(1000*60);
				if(cha==0){
					reviewList.get(i).setCreateTime("刚刚");
				}else if(cha>0 && cha<60){
					reviewList.get(i).setCreateTime(cha+"分钟前");
				}else if(cha <1440){
					long hour = (time2-time)/(1000*60*60);
					reviewList.get(i).setCreateTime(hour+"小时前");
				}else{
					long day = (time2-time)/(1000*60*60*24);
					reviewList.get(i).setCreateTime(day+"天前");
				}
			}
		}
		return reviewList;
	}

	@Override
	public int addReview(ReadingReview readingReview) {
		// TODO Auto-generated method stub
		String floor = readingReviewMapper.queryFloor(readingReview);
		readingReview.setNumfloor(floor);
		readingReviewMapper.addReview(readingReview);
		return 1;
	}

	@Override
	public int addSupport(ReadingSupport readingSupport) {
		// TODO Auto-generated method stub
		readingReviewMapper.addSupport(readingSupport);
		return 1;
	}

	@Override
	public int queryIfSupport(ReadingSupport readingSupport) {
		// TODO Auto-generated method stub
		return readingReviewMapper.queryIfSupport(readingSupport);
	}

	@Override
	public int querySupportTotal(ReadingSupport readingSupport) {
		// TODO Auto-generated method stub
		return readingReviewMapper.querySupportTotal(readingSupport);
	}
	
}
