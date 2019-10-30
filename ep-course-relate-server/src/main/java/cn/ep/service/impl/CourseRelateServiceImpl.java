package cn.ep.service.impl;

import cn.ep.mapper.CourseCommentMapper;
import cn.ep.mapper.CourseScoreMapper;
import cn.ep.service.CourseRelateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CourseRelateServiceImpl implements CourseRelateService {
    @Autowired
    private CourseCommentMapper courseCommentMapper;

    @Autowired
    private CourseScoreMapper courseScoreMapper;


}
