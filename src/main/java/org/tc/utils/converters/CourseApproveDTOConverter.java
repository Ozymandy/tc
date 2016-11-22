package org.tc.utils.converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.tc.dto.course.CourseApproveDTO;
import org.tc.models.Course;
import org.tc.services.course.CourseService;
import org.tc.services.decision.DecisionService;
import org.tc.services.role.RoleService;
import org.tc.services.user.UserService;

@Component
public class CourseApproveDTOConverter {
    @Autowired
    private UserService userService;
    @Autowired
    private CourseService courseService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private DecisionService decisionService;

    public CourseApproveDTO convert(Course source) {
        CourseApproveDTO dto = new CourseApproveDTO();
        dto.setId(source.getId());
        dto.setCourseName(source.getName());
        dto.setDescription(source.getDescription());
        dto.setLinks(source.getLinks());
        dto.setAttendeeCount(source.getAttendeeCourse().size());
        dto.setSubscribersCount(source.getSubscribers().size());
        dto.setCategoryName(source.getCategory().getCategoryName());
        dto.setOwnerEmail(source.getOwner().getEmail());
        dto.setAverageGrade(courseService.getAverageGrade(source));
        dto.setDepartmentManager(roleService.getDepartmentManager().getUsername());
        dto.setKnowledgeManager(roleService.getKnowledgeManager().getUsername());
        dto.setDepartmentManager(userService.isDepartmentManager());
        dto.setVoted(userService.isVoted(source));
        dto.setDepartmentManagerDecision(decisionService.getDepartmentManagerDecision(source)
                .map(decision -> decision.getDecision().getValue()).orElse(null));
        dto.setKnowledgeManagerDecision(decisionService.getKnowledgeManagerDecision(source)
                .map(decision -> decision.getDecision().getValue()).orElse(null));
        dto.setKnowledgeManager(userService.isKnowLedgeManager());
        return dto;
    }
}
