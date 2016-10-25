package org.tc.services.usercourse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.tc.dao.usercourse.UserCourseDaoInterface;
import org.tc.models.usercourse.UserCourse;

import java.util.List;

@Service
public class UserCourseService implements UserCourseServiceInterface {
    @Autowired
    private UserCourseDaoInterface userCourseDao;

    @Override
    public void create(UserCourse newUserCourse) {
        userCourseDao.create(newUserCourse);
    }

    @Override
    public void delete(UserCourse userCourse) {
        userCourseDao.delete(userCourse);
    }

    @Override
    public List<UserCourse> getAll() {
        return userCourseDao.getAll();
    }

    @Override
    public List<UserCourse> getByUserId(int id) {
        return userCourseDao.getByUserId(id);
    }

    @Override
    public List<UserCourse> getByCourseId(int id) {
        return userCourseDao.getByCourseId(id);
    }

    @Override
    public void update(UserCourse changedUserCourse) {
        userCourseDao.update(changedUserCourse);
    }
}
