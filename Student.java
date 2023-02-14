import java.io.Serializable;

public class Student implements Comparable<Student>, Serializable
{

    public String name;
    public String surname;
    public int bornYear;
    double points;
    int index;

    public transient StudentCondition studentCondition;
    public enum StudentCondition {Oczekujący, Chory, Nieobecny, Obecny}

    public String getName() {return this.name;}
    public String getSurname() {return this.surname;}
    public double getPoints() {return this.points;}
    public int getIndex() {return this.index;}
    public int getBornYear() {return this.bornYear;}
    public StudentCondition getCondition() {return this.studentCondition;}
    public void update(int index,String name,String Surname,double points,int born)
    {
        this.index=index;
        this.name=name;
        this.surname=surname;
        this.bornYear=born;
        this.points=points;


    }


    public Student(String name,String surname,int bornYear,double points,int index)
    {
        this.name=name;
        this.surname=surname;
        this.bornYear=bornYear;
        this.points=points;
        this.studentCondition=StudentCondition.Oczekujący;
        this.index=index;
    }

    @Override
    public int compareTo(Student o)
    {
        return this.surname.compareTo(o.surname);
    }

    public void printInfo()
    {
        System.out.println(String.format("%s %s","Name:", this.name));
        System.out.println(String.format("%s %s","Surname:", this.surname));
        System.out.println(String.format("%s %s","Born:", this.bornYear));
        System.out.println(String.format("%s %s","Points:", this.points));
        System.out.println(String.format("%s %s","Status:", this.studentCondition));
    }

    public String toString() {
        return (surname + " " + name + " Born:" + bornYear + " Status:" + studentCondition + " Points:" + points + " Index: " + index);

    }


}
