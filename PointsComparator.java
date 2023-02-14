import java.util.Comparator;

public class PointsComparator implements Comparator<Student> {

    @Override
    public int compare(Student s1, Student s2 ) {
        return -Double.compare(s1.getPoints(), s2.getPoints());
    }

}