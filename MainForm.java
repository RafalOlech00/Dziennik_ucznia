import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class MainForm extends JFrame{



    private JTable table;
    private JButton add_group;
    private JButton editGroupButton;
    private JButton deleteGroupButton;
    private JButton studentsButton;
    private JPanel main;
    private JButton refreshButton;
    private JTextField group_name_input;
    private JLabel group_name_label;
    private JLabel capability_label;
    private JTextField capability_input;
    private JPanel add_panel;
    private JButton add_button;
    private JPanel edit_panel;
    private JLabel group_name_edit_label;
    private JLabel capability_edit_label;
    private JButton edit_button;
    private JTextField group_name_edit_input;
    private JTextField capability_edit_input;
    private JButton exportCsvButton;
    private JButton importCsvButton;

    public static Container container= new Container();

    public MainForm(String title) throws IOException, ClassNotFoundException
    {


        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(main);

        try
        {
            ObjectInputStream wejscie = new ObjectInputStream( new FileInputStream(new File("serialized.file")));
            Container test2  = (Container) wejscie.readObject();
            container.classes = test2.classes;

            JOptionPane.showMessageDialog(table,"Data has been loaded successfully");
        }
        catch (FileNotFoundException ex)
                {
                    JOptionPane.showMessageDialog(table,"File not found, cannot load data");
                }
        catch (IOException ex)
                {
                    JOptionPane.showMessageDialog(table,"Cannot open file");
                }

        table.setModel(container);
        table.setAutoCreateRowSorter(true);


        studentsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Container model= (Container) table.getModel();
                int i = table.getSelectedRow();

                String key = model.getValueAt(i,0).toString();

                Clas a = model.classes.get(key);

                JFrame frame = new Student_System("STUDENT PANEL",a) ;
                frame.setSize(600,400);
                frame.setVisible(true);
            }
        });

        deleteGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {


                if(table.getSelectedRowCount()==1)
                {
                    int i = table.getSelectedRow();
                    String key = (String)table.getValueAt(i,0);

                    container.classes.remove(key);
                    container.fireTableDataChanged();
                }
                else
                {
                    if(table.getRowCount()==0)
                    {

                        JOptionPane.showMessageDialog(table,"Table is empty");
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(table,"Please select single row");
                    }
                }
            }
        });
        add_group.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                add_panel.setVisible(true);
            }
        });
        add_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String n=group_name_input.getText();
                String c=capability_input.getText();
                int cp = Integer.parseInt(c);

                container.addClass(n,cp);


                add_panel.setVisible(false);
            }
        });
        editGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                edit_panel.setVisible(true);

                int i = table.getSelectedRow();
                if (i>=0)
                {
                    group_name_edit_input.setText(container.getValueAt(i, 0).toString());
                    capability_edit_input.setText(container.getValueAt(i, 1).toString());
                }
                else
                {
                    edit_panel.setVisible(false);
                    JOptionPane.showMessageDialog(table,"Please select single row");
                }
            }
        });
        edit_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {

                int i = table.getSelectedRow();
                String tmp = table.getValueAt(table.getSelectedRow(),0).toString();

                String n= group_name_edit_input.getText();
                int cp=   Integer.parseInt(capability_edit_input.getText());

                container.classes.get(tmp).setName(n);
                container.classes.get(tmp).setCapability(cp);

                container.fireTableDataChanged();

                edit_panel.setVisible(false);
            }
        });
        exportCsvButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                ExportCSVManager export_csv= new ExportCSVManager();
                export_csv.exportToCSV(table,"Przedmioty");
            }
        });
        importCsvButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                ExportCSVManager import_classes_csv= new ExportCSVManager();
                DefaultTableModel new_model= import_classes_csv.importCSV(table,"Przedmioty.csv");

                table.setModel(new_model);

                for(int i=0; i < new_model.getRowCount();i++)
                {
                    Clas a=new Clas(new_model.getValueAt(i,0).toString(),Integer.parseInt(new_model.getValueAt(i,1).toString()));
                    container.classes.put(a.groupName,a);


                }


            }
        });
    }



    public static void main(String[] args) throws IOException, ClassNotFoundException{

        JFrame frame = new MainForm("WD");
        frame.setSize(800, 600);
        frame.setVisible(true);

        JPanel panel = new JPanel();
        panel.setBorder(BorderFactory.createEmptyBorder(30,30,10,30));
        panel.setLayout((new GridLayout(0,2)));

        frame.addWindowListener(new WindowAdapter()
        {
            final JLabel label = new JLabel();
            @Override
            public void windowClosing(WindowEvent e)
            {
                int result = JOptionPane.showConfirmDialog(frame,"Save data before exit", "SAVE",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE);
                if(result == JOptionPane.YES_OPTION)
                {
                    try
                    {
                        FileOutputStream file = new FileOutputStream("serialized.file");
                        ObjectOutputStream wyjscie = new ObjectOutputStream(file);
                        Container test = container;
                        wyjscie.writeObject(test);
                        System.exit(0);
                    }
                    catch (FileNotFoundException ex)
                    {
                        JOptionPane.showMessageDialog(label,"File not found, cannot load data");
                    }
                    catch (IOException ex)
                    {
                        JOptionPane.showMessageDialog(label,"Cannot open file");
                    }
                    System.exit(0);
                }

                else if (result == JOptionPane.NO_OPTION)
                {
                    System.exit(0);
                }
                else
                {

                }

            }
        });

/*
        //students

        Student s1 =new Student("Jakub", "Wrona", 1999,9,1);
        Student s2=new Student("Adam", "Kot", 1999,0,0);
        Student s3=new Student("Kuba", "Kot", 1998,5,3);
        Student s4=new Student("Zygmund", "Wróbel", 1999,10,4);
        Student s5=new Student("Anna", "Lis", 1997,8,5);


        Student s6=new Student("Michał", "Ptak", 1998,6,6);
        Student s7=new Student("Stefan", "Nowak", 1999,6,7);
        Student s8=new Student("krzysztof", "Kowal", 1997,7,8);


        List<Student> StudentListFirst = new ArrayList<>();
        StudentListFirst.add(s1);
        StudentListFirst.add(s2);

        List<Student> StudentListSecond = new ArrayList<>();
        StudentListSecond.add(s3);
        StudentListSecond.add(s4);
        StudentListSecond.add(s5);
        StudentListSecond.add(s6);



        container.addClass("lab1", 6);
        container.getClasses().get("lab1").setStudentsList(StudentListFirst);

        container.addClass("lab2", 4);
        container.getClasses().get("lab2").setStudentsList(StudentListSecond);

        container.addClass("lab3",5);

        */



      /*
        FileOutputStream file = new FileOutputStream("serialized.file");
        ObjectOutputStream wyjscie = new ObjectOutputStream(file);
        Container test  = container;
        wyjscie.writeObject(test);
        */










    }


}