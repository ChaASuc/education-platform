package cn.ep.service;

import cn.ep.bean.EpCourseUser;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface ICourseUserService {

    boolean insert(EpCourseUser courseUser);

    List<EpCourseUser> getListByCourseId(long courseId);

    List<EpCourseUser> getListByUserId(long userId);

    List<EpCourseUser> getListByCourseUser(EpCourseUser courseUser);

    EpCourseUser getByUserIdAndCourseId(long courseId, long userId);

    boolean subscription(long courseId,long userId);
}
