import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.PatternSyntaxException;

public class Student_System extends JFrame
{
    private JPanel MainPanel;

    private JTable table1;
    private JScrollPane scrool1;
    private JButton student_list_button;
    private JButton add_student_button;
    private JButton delete_student_button;
    private JButton edit_student;
    private JLabel student_name_label;
    private JLabel student_surname_label;
    private JTextField student_name_input;
    private JTextField student_surname_input;
    private JLabel student_born_year_label;
    private JTextField student_born_year_input;
    private JLabel student_points_label;
    private JTextField student_points_input;
    private JLabel student_index_label;
    private JTextField student_index_input;
    private JButton editstudent;
    private JPanel edit_panel;
    private JComboBox comboBox1;
    private JTextField filter_field;
    private JButton filter_button;
    private JButton sort_button;
    private JButton add_points_button;
    private JButton exportCsvButton;
    private JButton importCsvButton;

    public static Clas c;


    public Student_System(String title, Clas a)
    {
        super(title);

        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(MainPanel);

        this.c=a;
        Clas studentTableModel =a;

        student_list_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {

                table1.setModel(studentTableModel);
                table1.setAutoCreateRowSorter(true);



                //add student button visibility
                add_student_button.setVisible(true);
                delete_student_button.setVisible(true);
                edit_student.setVisible(true);
                filter_field.setVisible(true);
                filter_button.setVisible(true);
            }
        });


        add_student_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {


                if(c.maxStudentNumber>c.studentsList.size())
                {
                    JFrame frame = new StudentMenu("Add Student", studentTableModel, table1);
                    frame.setSize(600, 400);
                    frame.setVisible(true);
                }
                else
                {
                    JOptionPane.showMessageDialog(table1,"Group is full");
                }
            }
        });


        delete_student_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                Clas model= (Clas) table1.getModel();

                if(table1.getSelectedRowCount()==1)
                {
                    int index = table1.getSelectedRow();
                    c.studentsList.remove(index);
                    model.fireTableRowsDeleted(index,index);
                }
                else
                {
                    if(table1.getRowCount()==0)
                    {

                       JOptionPane.showMessageDialog(table1,"Table is empty");
                    }
                    else
                    {
                        JOptionPane.showMessageDialog(table1,"Please select single row");
                    }
                }
            }
        });
        edit_student.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                super.mouseClicked(e);
                Clas model= (Clas) table1.getModel();


                int i = table1.getSelectedRow();
                if (i>=0)
                {
                    comboBox1.setModel(new DefaultComboBoxModel<>(Student.StudentCondition.values()));
                    comboBox1.setSelectedIndex(3);
                    student_index_input.setText(model.getValueAt(i, 0).toString());
                    student_name_input.setText(model.getValueAt(i, 1).toString());
                    student_surname_input.setText(model.getValueAt(i, 2).toString());
                    student_born_year_input.setText(model.getValueAt(i, 3).toString());
                    student_points_input.setText(model.getValueAt(i, 5).toString());

                    edit_panel.setVisible(true);
                }
                else
                {
                    edit_panel.setVisible(false);
                    JOptionPane.showMessageDialog(table1,"Please select single row");

                }

            }
        });
        editstudent.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {

                int i = table1.getSelectedRow();

                Student a = new Student( student_name_input.getText(),
                        student_surname_input.getText(),
                        Integer.parseInt(student_born_year_input.getText()),
                        Double.parseDouble(student_points_input.getText()),
                        Integer.parseInt(student_index_input.getText()));

                a.studentCondition = (Student.StudentCondition) comboBox1.getSelectedItem();
                c.studentsList.set(i,a);


                edit_panel.setVisible(false);



            }
        });
        edit_student.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

               edit_panel.setVisible(true);
            }
        });

        filter_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                final TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(studentTableModel);
                table1.setRowSorter(sorter);

                String text = filter_field.getText();
                if(text.length() == 0) {
                    sorter.setRowFilter(null);
                }
                else
                {
                    try
                    {
                        sorter.setRowFilter(RowFilter.regexFilter(text));
                    }
                    catch(PatternSyntaxException pse)
                    {
                        System.out.println("Bad regex pattern");
                    }
                }
            }

        });
        sort_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                TableRowSorter<TableModel> sorter = new TableRowSorter<>(table1.getModel());
                table1.setRowSorter(sorter);
                List<RowSorter.SortKey> sortKeys = new ArrayList<>();

                int columnIndexToSort = 2;
                sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.ASCENDING));

                sorter.setSortKeys(sortKeys);
                sorter.sort();

            }
        });


        add_points_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Clas model= (Clas) table1.getModel();

                String points = JOptionPane.showInputDialog("points");
                Double p = Double.parseDouble(points);

                int i = table1.getSelectedRow();
                Student s=c.studentsList.get(i);
                s.points+=p;
                model.fireTableCellUpdated(i,i);
                c.studentsList.set(i,s);

            }
        });
        exportCsvButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                ExportCSVManager export_csv= new ExportCSVManager();
                export_csv.exportToCSV(table1,c.groupName);
            }
        });
        importCsvButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                ExportCSVManager import_classes_csv= new ExportCSVManager();
                String name=c.groupName+".csv";
                DefaultTableModel new_model= import_classes_csv.importCSV(table1,name);

                c.studentsList= new ArrayList<Student>();
                for(int i=0; i < new_model.getRowCount();i++)
                {
                    Student a=new Student
                    (new_model.getValueAt(i,1).toString(),
                     new_model.getValueAt(i,2).toString(),
                     Integer.parseInt(new_model.getValueAt(i,3).toString()),
                     Double.parseDouble(new_model.getValueAt(i,5).toString()),
                     Integer.parseInt(new_model.getValueAt(i,0).toString()));

                    c.studentsList.add(i,a);
                    table1.setModel(c);

                }


            }
        });
    }





}
