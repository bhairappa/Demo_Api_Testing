package pojos.courses;

import java.util.List;

public class GetCoursesResponse {
    private Dashboard dashboard;
    private List<Course> courses;

    public Dashboard getDashboard() { return dashboard; }
    public void setDashboard(Dashboard dashboard) { this.dashboard = dashboard; }

    public List<Course> getCourses() { return courses; }
    public void setCourses(List<Course> courses) { this.courses = courses; }
}