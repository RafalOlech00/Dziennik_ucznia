import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Container extends AbstractTableModel {

    private final String[] COLUMNS = {"GROUP NAME", "CAPABILITY","AMOUNT","%",};
    public Map<String, Clas> classes;

    public Container()
    {
        classes = new HashMap<String,Clas>();
    }
    public Container(Container c)
    {
        this.classes=c.classes;

    }

    public Container(Map<String,Clas> classes)
    {
        this.classes=classes;
    }

    public void addClass(String key, int max)
    {

        Clas c = new Clas(key, max);
        this.classes.put(key, c);
    }

    public void removeClass(String name)
    {
        classes.remove(name);
    }

    public List<Clas> findEmpty()
    {
        List<Clas> emptyClasses = new ArrayList<>();
        this.classes.forEach((key, value) ->
        {
            if (value.getSize() == 0) {
                emptyClasses.add(value);
            }
        });

        System.out.println("Empty classes:");
        for(Clas c: emptyClasses)
        {
            System.out.println(c.groupName);
        }

        return emptyClasses;
    }

    public void summary()
    {
        classes.forEach((k, v)
                -> System.out.println((k + " : Name " + v.getClassName() + " Actual Amount " + v.getSize() +
                " Fill : " + ((double)v.getSize()/(double)v.getGroupSize()*100) + "%")));
    }



    public Map<String, Clas> getClasses() {
        return classes;
    }

    @Override
    public int getRowCount()
    {
        return classes.size();
    }

    @Override
    public int getColumnCount()
    {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex)
    {
        int i=0;
        Object tmp = null;

        for(Map.Entry<String,Clas> entry : classes.entrySet())
        {
            if(i == rowIndex)
            {
                return switch(columnIndex)
                        {
                            case 0 -> entry.getValue().getName();
                            case 1 -> entry.getValue().getMax();
                            case 2 -> entry.getValue().student_amount();
                            case 3 -> entry.getValue().capability();
                            default ->"-";
                        };
            }
            i++;
        }
        return 0;

    }
    @Override
    public String getColumnName(int column)
    {
        return COLUMNS[column];
    }



}
