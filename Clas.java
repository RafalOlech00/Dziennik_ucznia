
import javax.swing.table.AbstractTableModel;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Collections;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;

    public class Clas extends AbstractTableModel implements Serializable
    {
        private final String[] COLUMNS = {"INDEX", "NAME", "SURNAME","BORN","CONDITION","POINTS",};
        public String groupName;
        public int maxStudentNumber;
        public List<Student> studentsList = new ArrayList<Student>();

        public Clas(String name, int number)
        {
            this.groupName=name;
            this.maxStudentNumber=number;

        }
        public Clas(String name, int number,List<Student> studentsList)
        {
            this.groupName=name;
            this.maxStudentNumber=number;
            this.studentsList=studentsList;

        }


        public void setName(String name){this.groupName=name;}
        public void setCapability(int number ){this.maxStudentNumber=number;}

        public String getName(){return this.groupName;}
        public int getMax(){return this.maxStudentNumber;}

  public Boolean findStudent(String name,String surname, List<Student> studentsList) {

            for (Student s : studentsList)
            {
                if ( s.getName().equals(name) && s.getSurname().equals(surname) )
                {
                    return false;
                }
            }
            return true;
        }

        public void addStudent(Student s)
        {
            int size=this.studentsList.size();
            boolean isExist = findStudent(s.name,s.surname,studentsList);

            if(size>=maxStudentNumber)
            {
                System.out.println(String.format("Group is full"));
            }
            else if(!isExist)
            {
                System.out.println(String.format("Student already exits"));
            }
            else
            {
                studentsList.add(s);
                System.out.println(String.format("Student added"));
            }
        }

        public void addPoints(int index, double p)
        {
            for (Student s : studentsList)
            {
                if ( s.getIndex()==index )
                {
                    s.points+=p;
                }

            }

        }

        public void getStudent(int index)
        {
            for (Student s : studentsList)
            {
                if ( s.getIndex()==index )
                {
                    if(s.points<=0)
                        studentsList.remove(s);
                }

            }

        }

        public void changeCondition(int index, Student.StudentCondition con)
        {
            for (Student s : studentsList)
            {
                if ( s.getIndex()==index )
                {
                    s.studentCondition=con;
                }

            }

        }

        public void removePoints(int index, double p)
        {
            for (Student s : studentsList)
            {
                if ( s.getIndex()==index )
                {
                    s.points-=p;
                    if(s.points<=0)
                    {
                        studentsList.remove(s);
                    }
                }

            }

        }

        public void search(String surname)
        {
            System.out.println("//");
            for (Student s : studentsList)
            {
                if ( s.getSurname().equals(surname) )
                {

                    System.out.println(String.format(s.toString()));

                }

            }
            System.out.println("//");
        }


        public void partSearch(String exp) {
            String s = ".*" + exp + ".*";
            List<Student> result = studentsList.stream()
                    .filter(student -> student.getSurname().matches(s))
                    .collect(Collectors.toList());

            for(Student st: result)
            {
                System.out.println(String.format(st.toString()));
            }
            System.out.println("\n");
        }

        public void countByCondition(Student.StudentCondition con)
        {
            int counter=0;
            for (Student s : studentsList)
            {
                if ( s.getCondition()==con )
                {
                    counter++;
                }
            }
            System.out.println(counter);
        }

        public void summary()
        {
            for (Student s : studentsList)
            {
                System.out.println(String.format(s.toString()));
            }
            System.out.println("\n");
        }

        public void sortByName()
        {
            Collections.sort(studentsList);
        }

        public void max()
        {
            Collections.max(studentsList);
        }

        public void sortByPoints()
        {

            PointsComparator pointsComparator =new PointsComparator();
            Collections.sort(studentsList,pointsComparator);
        }

        public int getSize() {return this.studentsList.size();}
        public String getClassName() {return this.groupName;}
        public int getGroupSize() {return this.maxStudentNumber;}


        public void setStudentsList(List<Student> studentList) {
            this.studentsList = studentList;
        }

        public double capability()
        {
            return 100*(double)this.studentsList.size()/(double)this.maxStudentNumber;
        }

        public int student_amount()
        {
            return this.studentsList.size();
        }

        @Override
        public int getRowCount() {
            return studentsList.size();
        }

        @Override
        public int getColumnCount() {
            return COLUMNS.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return switch(columnIndex)
                    {
                        case 0 -> studentsList.get(rowIndex).getIndex();
                        case 1 -> studentsList.get(rowIndex).getName();
                        case 2 -> studentsList.get(rowIndex).getSurname();
                        case 3 -> studentsList.get(rowIndex).getBornYear();                        case 4 -> studentsList.get(rowIndex).getCondition();
                        case 5 -> studentsList.get(rowIndex).getPoints();
                        default ->"-";
                    };
        }

        @Override
        public String getColumnName(int column)
        {
            return COLUMNS[column];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex)
        {
            if(getValueAt(0,columnIndex)!=null)
            {
                return getValueAt(0,columnIndex).getClass();
            }
            return Object.class;
        }


    }


